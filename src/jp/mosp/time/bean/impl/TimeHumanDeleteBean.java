/*
 * MosP - Mind Open Source Project
 * Copyright (C) esMind, LLC  https://www.e-s-mind.com/
 * 
 * This program is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation, either version 3
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package jp.mosp.time.bean.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.platform.bean.human.ExtraHumanDeleteBeanInterface;
import jp.mosp.platform.bean.human.HistoryBasicDeleteBeanInterface;
import jp.mosp.platform.bean.workflow.WorkflowReferenceBeanInterface;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.time.base.TimeBean;
import jp.mosp.time.bean.ApplicationReferenceBeanInterface;
import jp.mosp.time.bean.HolidayInfoReferenceBeanInterface;
import jp.mosp.time.bean.PaidHolidayTransactionReferenceBeanInterface;
import jp.mosp.time.bean.TotalTimeEmployeeTransactionReferenceBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.constant.TimeMessageConst;
import jp.mosp.time.utils.TimeNamingUtility;
import jp.mosp.time.utils.TimeUtility;

/**
 * 勤怠用人事基本情報削除クラス。<br>
 * 人事基本情報削除時に、<br>
 * 勤怠管理側で行うべき処理を実装する。<br>
 */
public class TimeHumanDeleteBean extends TimeBean implements ExtraHumanDeleteBeanInterface {
	
	/**
	 * ワークフロー参照クラス。
	 */
	protected WorkflowReferenceBeanInterface						workflowReference;
	
	/**
	 * 設定適用管理参照クラス。
	 */
	protected ApplicationReferenceBeanInterface						applicationReference;
	
	/**
	 * 有給休暇データ参照クラス。
	 */
	protected PaidHolidayTransactionReferenceBeanInterface			paidHolidayTransaction;
	
	/**
	 * 休暇データ参照クラス。
	 */
	protected HolidayInfoReferenceBeanInterface						holidayInfoReference;
	
	/**
	 * 休暇データ参照クラス。
	 */
	protected TotalTimeEmployeeTransactionReferenceBeanInterface	totalTimeEmployeeReference;
	
	
	/**
	 * {@link TimeHumanDeleteBean#TimeHumanDeleteBean()}を実行する。<br>
	 */
	public TimeHumanDeleteBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		workflowReference = createBeanInstance(WorkflowReferenceBeanInterface.class);
		applicationReference = createBeanInstance(ApplicationReferenceBeanInterface.class);
		paidHolidayTransaction = createBeanInstance(PaidHolidayTransactionReferenceBeanInterface.class);
		holidayInfoReference = createBeanInstance(HolidayInfoReferenceBeanInterface.class);
		totalTimeEmployeeReference = createBeanInstance(TotalTimeEmployeeTransactionReferenceBeanInterface.class);
	}
	
	@Override
	public Set<String> getTargetTable() {
		// セットの準備
		Set<String> lockTableSet = new HashSet<String>();
		return lockTableSet;
	}
	
	@Override
	public void humanDelete(HumanDtoInterface dto, boolean isAllDelete) {
		// 処理なし
	}
	
	@Override
	public void checkDelete(List<HumanDtoInterface> list, int deleateIndex, int deleteCase) throws MospException {
		switch (deleteCase) {
			case HistoryBasicDeleteBeanInterface.CASE_ONLY:
				// 削除対象DTO
				HumanDtoInterface deleateHumanDto = list.get(deleateIndex);
				// 期間終了日設定
				Date endDate = null;
				// 履歴が1件だけの場合
				// 各種申請がなされているか確認
				checkWorkflow(deleateHumanDto, endDate);
				// 仮締、確定により作成されるデータが存在するか確認
				checkCutoff(deleateHumanDto, endDate);
				// 勤怠データ(申請関連、締関連以外)が存在するか確認
				checkExist(deleateHumanDto, endDate);
				break;
			case HistoryBasicDeleteBeanInterface.CASE_OLDEST:
				// 消去対象がリストの中で一番古い履歴の場合
				// 削除対象DTO
				HumanDtoInterface humanDto = list.get(deleateIndex);
				// 削除対象一つ最新DTO
				HumanDtoInterface afterHumanDto = list.get(deleateIndex + 1);
				// 次履歴の有効日前日を取得
				endDate = DateUtility.addDay(afterHumanDto.getActivateDate(), -1);
				// 各種申請がなされているか確認
				checkWorkflow(humanDto, endDate);
				// 仮締、確定により作成されるデータが存在するか確認
				checkCutoff(humanDto, endDate);
				// 勤怠データ(申請関連、締関連以外)が存在するか確認
				checkExist(humanDto, endDate);
				break;
			default:
				break;
		}
	}
	
	/**
	 * 各種申請がなされているか確認する。<br>
	 * @param deleteHumanDto 削除対象DTO
	 * @param endDate 期間終了日
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkWorkflow(HumanDtoInterface deleteHumanDto, Date endDate) throws MospException {
		// 期間初日取得
		Date startDate = deleteHumanDto.getActivateDate();
		// 個人IDが設定されている、有効日の範囲内で情報を取得
		List<WorkflowDtoInterface> list = workflowReference.getPersonalList(deleteHumanDto.getPersonalId(), startDate,
				endDate, TimeUtility.getTimeFunctionSet());
		// 対象期間における有効ワークフロー情報リストを取得
		if (list.isEmpty() == false) {
			// データが存在する場合
			addDeleteTimeErrorMessage(mospParams.getName("WorkManage"), mospParams.getName("Application"));
			return;
		}
	}
	
	/**
	 * 勤怠データ(申請関連、締関連以外)が存在するか確認する。<br>
	 * @param deleteHumanDto 削除対象DTO
	 * @param endDate 期間終了日
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkExist(HumanDtoInterface deleteHumanDto, Date endDate) throws MospException {
		// 設定適用存在確認
		checkApplication(deleteHumanDto, endDate);
		// 有給休暇存在確認
		checkPaidHoliday(deleteHumanDto, endDate);
		// 特別休暇存在確認
		checkSpecialHoliday(deleteHumanDto, endDate);
		// その他休暇存在確認
		checkOtherHoliday(deleteHumanDto, endDate);
	}
	
	/**
	 * 仮締が未締の状態かを確認する。<br>
	 * @param deleteHumanDto 削除対象DTO
	 * @param endDate 期間終了日
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkCutoff(HumanDtoInterface deleteHumanDto, Date endDate) throws MospException {
		// 期間初日取得
		Date startDate = deleteHumanDto.getActivateDate();
		// 社員勤怠集計管理から指定期間中の履歴を取得
		if (totalTimeEmployeeReference.getCutoffTermState(deleteHumanDto.getPersonalId(), startDate, endDate)) {
			// データが存在する場合
			addDeleteTimeErrorMessage(mospParams.getName("WorkManage") + mospParams.getName("Total"),
					mospParams.getName("Information"));
		}
	}
	
	/**
	 * 設定適用情報に対象社員が適用されているか確認する。<br>
	 * @param deleteDto 削除対象DTO
	 * @param endDate 期間終了日
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkApplication(HumanDtoInterface deleteDto, Date endDate) throws MospException {
		// 期間初日取得
		Date startDate = deleteDto.getActivateDate();
		// 個人ID及び期間から、適用されている設定を日毎に取得
		if (applicationReference.hasPersonalApplication(deleteDto.getPersonalId(), startDate, endDate)) {
			// エラーメッセージを設定
			String employeeCode = deleteDto.getEmployeeCode();
			String fieldName = TimeNamingUtility.getApplication(mospParams);
			PfMessageUtility.addErrorHumanCanNotDelete(mospParams, employeeCode, fieldName);
		}
	}
	
	/**
	 * 有休休暇が対象社員に適用されているか確認する。<br>
	 * @param deleteDto 削除対象DTO
	 * @param endDate 期間終了日
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkPaidHoliday(HumanDtoInterface deleteDto, Date endDate) throws MospException {
		// 期間初日取得
		Date startDate = deleteDto.getActivateDate();
		// 個人ID及び期間から、適用されている設定を日毎に取得
		if (paidHolidayTransaction.hasPersonalApplication(deleteDto.getPersonalId(), startDate, endDate) == true) {
			// エラーメッセージを設定
			String employeeCode = deleteDto.getEmployeeCode();
			String fieldName = TimeNamingUtility.paidHolidayAbbr(mospParams);
			PfMessageUtility.addErrorHumanCanNotDelete(mospParams, employeeCode, fieldName);
		}
	}
	
	/**
	 * 特別休暇が対象社員に適用されているか確認する。<br>
	 * @param deleteDto 削除対象DTO
	 * @param endDate 期間終了日
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkSpecialHoliday(HumanDtoInterface deleteDto, Date endDate) throws MospException {
		// 期間初日取得
		Date startDate = deleteDto.getActivateDate();
		// 特別休暇データ取得
		if (holidayInfoReference.hasPersonalApplication(deleteDto.getPersonalId(), startDate, endDate,
				TimeConst.CODE_HOLIDAYTYPE_SPECIAL) == true) {
			// エラーメッセージを設定
			String employeeCode = deleteDto.getEmployeeCode();
			String fieldName = TimeNamingUtility.specialHoliday(mospParams);
			PfMessageUtility.addErrorHumanCanNotDelete(mospParams, employeeCode, fieldName);
		}
	}
	
	/**
	 * その他休暇が対象社員に適用されているか確認する。<br>
	 * @param deleteDto 削除対象DTO
	 * @param endDate 期間終了日
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkOtherHoliday(HumanDtoInterface deleteDto, Date endDate) throws MospException {
		// 期間初日取得
		Date startDate = deleteDto.getActivateDate();
		if (holidayInfoReference.hasPersonalApplication(deleteDto.getPersonalId(), startDate, endDate,
				TimeConst.CODE_HOLIDAYTYPE_OTHER) == true) {
			// エラーメッセージを設定
			String employeeCode = deleteDto.getEmployeeCode();
			String fieldName = TimeNamingUtility.otherHoliday(mospParams);
			PfMessageUtility.addErrorHumanCanNotDelete(mospParams, employeeCode, fieldName);
		}
	}
	
	/**
	 * 整合性がとれず削除できない場合のエラーメッセージ。
	 * [対象名]関連の[動作名]が存在するので削除できません。
	 * @param errorTargetName エラー対象名称
	 * @param activeName エラー動作名称
	 * 
	 */
	protected void addDeleteTimeErrorMessage(String errorTargetName, String activeName) {
		mospParams.addErrorMessage(TimeMessageConst.MSG_DELETE_RESON_CHECK, errorTargetName, activeName);
	}
	
}
