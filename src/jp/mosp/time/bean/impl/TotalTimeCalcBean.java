/*
 * MosP - Mind Open Source Project    http://www.mosp.jp/
 * Copyright (C) MIND Co., Ltd.       http://www.e-mind.co.jp/
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.bean.system.PlatformMasterBeanInterface;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.platform.utils.WorkflowUtility;
import jp.mosp.time.base.TimeBean;
import jp.mosp.time.bean.AttendanceTransactionRegistBeanInterface;
import jp.mosp.time.bean.CutoffUtilBeanInterface;
import jp.mosp.time.bean.TimeMasterBeanInterface;
import jp.mosp.time.bean.TotalAbsenceRegistBeanInterface;
import jp.mosp.time.bean.TotalLeaveRegistBeanInterface;
import jp.mosp.time.bean.TotalOtherVacationRegistBeanInterface;
import jp.mosp.time.bean.TotalTimeCalcBeanInterface;
import jp.mosp.time.bean.TotalTimeEmployeeTransactionRegistBeanInterface;
import jp.mosp.time.bean.TotalTimeEntityReferenceBeanInterface;
import jp.mosp.time.bean.TotalTimeReferenceBeanInterface;
import jp.mosp.time.bean.TotalTimeRegistBeanInterface;
import jp.mosp.time.bean.TotalTimeTransactionRegistBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dto.settings.AttendanceDtoInterface;
import jp.mosp.time.dto.settings.CutoffDtoInterface;
import jp.mosp.time.dto.settings.CutoffErrorListDtoInterface;
import jp.mosp.time.dto.settings.DifferenceRequestDtoInterface;
import jp.mosp.time.dto.settings.HolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.OvertimeRequestDtoInterface;
import jp.mosp.time.dto.settings.SubHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.SubstituteDtoInterface;
import jp.mosp.time.dto.settings.TimeSettingDtoInterface;
import jp.mosp.time.dto.settings.TotalAbsenceDtoInterface;
import jp.mosp.time.dto.settings.TotalLeaveDtoInterface;
import jp.mosp.time.dto.settings.TotalOtherVacationDtoInterface;
import jp.mosp.time.dto.settings.TotalTimeDataDtoInterface;
import jp.mosp.time.dto.settings.TotalTimeDtoInterface;
import jp.mosp.time.dto.settings.WorkOnHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.WorkTypeChangeRequestDtoInterface;
import jp.mosp.time.entity.ApplicationEntity;
import jp.mosp.time.entity.CutoffEntityInterface;
import jp.mosp.time.entity.RequestDetectEntityInterface;
import jp.mosp.time.entity.TotalTimeEntityInterface;

/**
 * 勤怠集計クラス。<br>
 * <br>
 * {@link TotalTimeEntityInterface}を用いて勤怠集計を行い、
 * 集計結果を取得したりDBに登録したりする。<br>
 */
public class TotalTimeCalcBean extends TimeBean implements TotalTimeCalcBeanInterface {
	
	/**
	 * 所定労働時間及び法内残業時間の基準時間。<br>
	 */
	public static final int										PREDETERMINED_OVERTIME_WORK	= 40;
	
	/**
	 * 法定労働時間及び法定残業時間の基準時間。<br>
	 */
	public static final int										LEGAL_OVERTIME_WORK			= 45;
	
	/**
	 * 締日ユーティリティインターフェース参照。
	 */
	protected CutoffUtilBeanInterface							cutoffUtil;
	
	/**
	 * 特別休暇集計データ登録クラス。<br>
	 */
	protected TotalLeaveRegistBeanInterface						totalLeaveRegist;
	
	/**
	 * その他休暇集計データ登録クラス。<br>
	 */
	protected TotalOtherVacationRegistBeanInterface				totalOtherVacationRegist;
	
	/**
	 * 欠勤集計データ登録クラス。<br>
	 */
	protected TotalAbsenceRegistBeanInterface					totalAbsenceRegist;
	
	/**
	 * 勤怠集計データ登録クラス。<br>
	 */
	protected TotalTimeReferenceBeanInterface					totalTimeRefer;
	
	/**
	 * 勤怠集計データ登録クラス。<br>
	 */
	protected TotalTimeRegistBeanInterface						totalTimeRegist;
	
	/**
	 * 勤怠集計管理参照クラス。<br>
	 */
	protected TotalTimeTransactionRegistBeanInterface			totalTransRegist;
	
	/**
	 * 社員勤怠集計管理参照クラス。<br>
	 */
	protected TotalTimeEmployeeTransactionRegistBeanInterface	employeeTransRegist;
	
	/**
	 * 勤怠トランザクション登録クラス。<br>
	 */
	protected AttendanceTransactionRegistBeanInterface			attendanceTransactionRegist;
	
	/**
	 * 勤怠集計エンティティ取得クラス。<br>
	 */
	protected TotalTimeEntityReferenceBeanInterface				totalTimeEntityRefer;
	
	/**
	 * プラットフォームマスタ参照クラス。<br>
	 */
	protected PlatformMasterBeanInterface						platformMaster;
	
	/**
	 * 勤怠関連マスタ参照クラス。<br>
	 */
	protected TimeMasterBeanInterface							timeMaster;
	
	
	/**
	 * {@link TimeBean#TimeBean()}を実行する。<br>
	 */
	public TotalTimeCalcBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// Bean及びDAO準備
		cutoffUtil = createBeanInstance(CutoffUtilBeanInterface.class);
		totalLeaveRegist = createBeanInstance(TotalLeaveRegistBeanInterface.class);
		totalOtherVacationRegist = createBeanInstance(TotalOtherVacationRegistBeanInterface.class);
		totalAbsenceRegist = createBeanInstance(TotalAbsenceRegistBeanInterface.class);
		totalTimeRefer = createBeanInstance(TotalTimeReferenceBeanInterface.class);
		totalTimeRegist = createBeanInstance(TotalTimeRegistBeanInterface.class);
		totalTransRegist = createBeanInstance(TotalTimeTransactionRegistBeanInterface.class);
		employeeTransRegist = createBeanInstance(TotalTimeEmployeeTransactionRegistBeanInterface.class);
		attendanceTransactionRegist = createBeanInstance(AttendanceTransactionRegistBeanInterface.class);
		// 勤怠集計エンティティ取得クラス
		totalTimeEntityRefer = createBeanInstance(TotalTimeEntityReferenceBeanInterface.class);
		// プラットフォームマスタ参照クラスを取得
		platformMaster = createBeanInstance(PlatformMasterBeanInterface.class);
		// 勤怠関連マスタ参照クラス
		timeMaster = createBeanInstance(TimeMasterBeanInterface.class);
		timeMaster.setPlatformMaster(platformMaster);
		// プラットフォームマスタ参照クラスを勤怠集計エンティティ取得クラスに設定
		totalTimeEntityRefer.setPlatformMasterBean(platformMaster);
		// 勤怠関連マスタ参照処理を設定
		totalTimeEntityRefer.setTimeMasterBean(timeMaster);
		cutoffUtil.setTimeMaster(timeMaster);
		attendanceTransactionRegist.setTimeMaster(timeMaster);
	}
	
	@Override
	public List<CutoffErrorListDtoInterface> tightening(int targetYear, int targetMonth, String cutoffCode)
			throws MospException {
		// 集計時エラー内容情報リストを準備
		List<CutoffErrorListDtoInterface> errorList = new ArrayList<CutoffErrorListDtoInterface>();
		// 勤怠集計エンティティリストを準備
		List<TotalTimeEntityInterface> entityList = new ArrayList<TotalTimeEntityInterface>();
		// 対象年月において対象締日コードが適用されている個人IDのセットを取得
		Set<String> personalIdSet = cutoffUtil.getCutoffPersonalIdSet(cutoffCode, targetYear, targetMonth);
		// 対象者がいない場合
		if (personalIdSet.isEmpty()) {
			// エラーメッセージを設定
			PfMessageUtility.addErrorEmployeeNotExist(mospParams);
			return errorList;
		}
		// 個人ID毎に処理
		for (String personalId : personalIdSet) {
			// 未締でない場合
			if (cutoffUtil.isNotTighten(personalId, targetYear, targetMonth) == false) {
				// 仮締対象外
				continue;
			}
			// 勤怠集計エンティティを取得
			TotalTimeEntityInterface entity = getTotalTimeEntity(personalId, targetYear, targetMonth, cutoffCode);
			// 勤怠集計エンティティリストに追加
			entityList.add(entity);
			// 各種申請及び振替休日情報から申請済でないものを除去
			removeUnusedRequests(entity, false);
			// 勤怠集計前の確認
			errorList.addAll(check(entity));
		}
		// 勤怠集計前の確認でエラーがあった場合
		if (errorList.isEmpty() == false) {
			// 集計時エラー内容情報リストを取得
			return errorList;
		}
		// 勤怠集計エンティティ毎に処理
		for (TotalTimeEntityInterface entity : entityList) {
			// 各種申請及び振替休日情報から承認済でないものを除去
			removeUnusedRequests(entity, true);
			// 勤怠集計
			entity.total();
			// 追加業務ロジック処理
			doAdditionalLogic(TimeConst.CODE_KEY_ADD_TOTALTIMECALCBEAN_TOTAL, entity, timeMaster);
		}
		// 勤怠集計エンティティ毎に処理
		for (TotalTimeEntityInterface entity : entityList) {
			// 仮締(DBへの登録)
			tightening(entity);
		}
		// 締日仮締
		tightenCutoff(targetYear, targetMonth, cutoffCode);
		// 集計時エラー内容情報リスト(空)を取得
		return errorList;
	}
	
	@Override
	public List<CutoffErrorListDtoInterface> tightening(String[] aryPersonalId, int targetYear, int targetMonth,
			String cutoffCode) throws MospException {
		// 集計時エラー内容情報リストを準備
		List<CutoffErrorListDtoInterface> errorList = new ArrayList<CutoffErrorListDtoInterface>();
		// 勤怠集計エンティティリストを準備
		List<TotalTimeEntityInterface> entityList = new ArrayList<TotalTimeEntityInterface>();
		// 個人ID毎に処理
		for (String personalId : aryPersonalId) {
			// 未締でない場合
			if (cutoffUtil.isNotTighten(personalId, targetYear, targetMonth) == false) {
				// 仮締対象外
				continue;
			}
			// 勤怠集計エンティティを取得
			TotalTimeEntityInterface entity = getTotalTimeEntity(personalId, targetYear, targetMonth, cutoffCode);
			// 勤怠集計エンティティリストに追加
			entityList.add(entity);
			// 各種申請及び振替休日情報から申請済でないものを除去
			removeUnusedRequests(entity, false);
			// 勤怠集計前の確認
			errorList.addAll(check(entity));
		}
		// 勤怠集計前の確認でエラーがあった場合
		if (errorList.isEmpty() == false) {
			// 集計時エラー内容情報リストを取得
			return errorList;
		}
		// 勤怠集計エンティティ毎に処理
		for (TotalTimeEntityInterface entity : entityList) {
			// 各種申請及び振替休日情報から承認済でないものを除去
			removeUnusedRequests(entity, true);
			// 勤怠集計
			entity.total();
			// 追加業務ロジック処理
			doAdditionalLogic(TimeConst.CODE_KEY_ADD_TOTALTIMECALCBEAN_TOTAL, entity, timeMaster);
		}
		// 勤怠集計エンティティ毎に処理
		for (TotalTimeEntityInterface entity : entityList) {
			// 仮締(DBへの登録)
			tightening(entity);
		}
		// 締日が設定されている社員全員が仮締である場合
		if (isAllTightened(targetYear, targetMonth, cutoffCode)) {
			// 締日仮締
			tightenCutoff(targetYear, targetMonth, cutoffCode);
		}
		// 集計時エラー内容情報リスト(空)を取得
		return errorList;
	}
	
	@Override
	public List<CutoffErrorListDtoInterface> tightening(String personalId, int targetYear, int targetMonth)
			throws MospException {
		// 未締でない場合
		if (cutoffUtil.isNotTighten(personalId, targetYear, targetMonth) == false) {
			// 仮締対象外
			return Collections.emptyList();
		}
		// 勤怠集計エンティティを取得
		TotalTimeEntityInterface entity = getTotalTimeEntity(personalId, targetYear, targetMonth);
		// 各種申請及び振替休日情報から申請済でないものを除去
		removeUnusedRequests(entity, false);
		// 勤怠集計前の確認
		List<CutoffErrorListDtoInterface> errorList = check(entity);
		// 勤怠集計前の確認でエラーがあった場合
		if (errorList.isEmpty() == false) {
			// 集計時エラー内容情報リストを取得
			return errorList;
		}
		// 各種申請及び振替休日情報から承認済でないものを除去
		removeUnusedRequests(entity, true);
		// 勤怠集計
		entity.total();
		// 追加業務ロジック処理
		doAdditionalLogic(TimeConst.CODE_KEY_ADD_TOTALTIMECALCBEAN_TOTAL, entity, timeMaster);
		// 仮締
		tightening(entity);
		// 集計時エラー内容情報リスト(空)を取得
		return errorList;
	}
	
	@Override
	public TotalTimeDataDtoInterface calc(String personalId, int targetYear, int targetMonth, boolean isCompleted)
			throws MospException {
		// 勤怠集計エンティティを取得
		TotalTimeEntityInterface entity = getTotalTimeEntity(personalId, targetYear, targetMonth);
		// 各種申請及び振替休日情報から承認済でないものを除去
		removeUnusedRequests(entity, isCompleted);
		// 勤怠集計
		TotalTimeDataDtoInterface dto = calc(entity);
		// 勤怠集計情報リストを取得
		return dto;
	}
	
	/**
	 * 勤怠集計エンティティを取得する。<br>
	 * <br>
	 * @param personalId  個人ID
	 * @param targetYear  対象年
	 * @param targetMonth 対象月
	 * @return 勤怠集計エンティティ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected TotalTimeEntityInterface getTotalTimeEntity(String personalId, int targetYear, int targetMonth)
			throws MospException {
		// 設定適用エンティティを取得
		ApplicationEntity application = timeMaster.getApplicationEntity(personalId, targetYear, targetMonth);
		// 締日エンティティを取得
		CutoffEntityInterface cutoff = application.getCutoffEntity();
		// 設定適用エンティティを取得
		return totalTimeEntityRefer.getTotalTimeEntity(personalId, targetYear, targetMonth, cutoff);
	}
	
	/**
	 * 勤怠集計エンティティを取得する。<br>
	 * <br>
	 * @param personalId  個人ID
	 * @param targetYear  対象年
	 * @param targetMonth 対象月
	 * @param cutoffCode  締日コード
	 * @return 勤怠集計エンティティ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected TotalTimeEntityInterface getTotalTimeEntity(String personalId, int targetYear, int targetMonth,
			String cutoffCode) throws MospException {
		// 締日情報を取得
		CutoffEntityInterface cutoff = timeMaster.getCutoff(cutoffCode, targetYear, targetMonth);
		// 設定適用エンティティを取得
		return totalTimeEntityRefer.getTotalTimeEntity(personalId, targetYear, targetMonth, cutoff);
	}
	
	/**
	 * 勤怠集計前の確認を行う。<br>
	 * <br>
	 * 未承認、勤怠未申請、残業未申請の確認を行う。<br>
	 * <br>
	 * @param entity 勤怠集計エンティティ
	 * @return 集計時エラー内容情報リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected List<CutoffErrorListDtoInterface> check(TotalTimeEntityInterface entity) throws MospException {
		// 個人IDと計算年月と締日コードを準備
		String personalId = entity.getPersonalId();
		int targetYear = entity.getCalculationYear();
		int targetMonth = entity.getCalculationMonth();
		String cutoffCode = entity.getCutoffCode();
		// 人事情報を取得
		HumanDtoInterface humanDto = platformMaster.getHuman(personalId, targetYear, targetMonth);
		// 締日情報を取得
		CutoffDtoInterface cutoffDto = timeMaster.getCutoff(cutoffCode, targetYear, targetMonth).getCutoffDto();
		// 未承認仮締を取得(締日情報が取得できなかった場合は有効)
		int noApproval = cutoffDto == null ? TimeConst.CODE_NO_APPROVAL_VALID : cutoffDto.getNoApproval();
		// 申請確認エンティティを取得
		RequestDetectEntityInterface detectEntity = totalTimeEntityRefer.getRequestDetectEntity(entity);
		// 即時フラグを準備(集計時エラー内容情報リストが必要なためfalse)
		boolean isImmediately = false;
		// 未承認仮締が有効である場合
		if (noApproval == TimeConst.CODE_NO_APPROVAL_VALID) {
			// 処理無し
		}
		// 未承認仮締が無効(残業事後申請可、残業事前申請のみ)である場合
		if (noApproval == TimeConst.CODE_NO_APPROVAL_AFTER_OVER_REQ
				|| noApproval == TimeConst.CODE_NO_APPROVAL_BEFORE_OVER_REQ) {
			// 未承認が存在するかを確認
			detectEntity.isApprovableExist(mospParams, isImmediately);
			// 勤怠未申請が存在するかを確認
			detectEntity.isAppliableExist(isImmediately);
			// 残業未申請
			detectEntity.isOvertimeNotAppliedExist(isImmediately);
		}
		// 未承認仮締が無効(残業申請無し)である場合
		if (noApproval == TimeConst.CODE_NO_APPROVAL_NO_OVER_REQ) {
			// 未承認が存在するかを確認
			detectEntity.isApprovableExist(mospParams, isImmediately);
			// 勤怠未申請が存在するかを確認
			detectEntity.isAppliableExist(isImmediately);
		}
		// 集計時エラー内容情報リストを取得
		return detectEntity.getCutoffErrorList(mospParams, humanDto);
	}
	
	/**
	 * 勤怠集計を行う。<br>
	 * <br>
	 * 計算のみで、データの登録は行わない。<br>
	 * 未締でない場合は、計算は行わずDBから勤怠集計情報を取得する。<br>
	 * <br>
	 * @param entity 勤怠集計エンティティ
	 * @return 勤怠集計情報(勤怠集計結果)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected TotalTimeDataDtoInterface calc(TotalTimeEntityInterface entity) throws MospException {
		// 個人IDと計算年月を準備
		String personalId = entity.getPersonalId();
		int targetYear = entity.getCalculationYear();
		int targetMonth = entity.getCalculationMonth();
		// 未締でない場合
		if (cutoffUtil.isNotTighten(personalId, targetYear, targetMonth) == false) {
			// DBから勤怠集計情報を取得
			return totalTimeRefer.findForKey(personalId, targetYear, targetMonth);
		}
		// 勤怠集計
		entity.total();
		// 追加業務ロジック処理
		doAdditionalLogic(TimeConst.CODE_KEY_ADD_TOTALTIMECALCBEAN_TOTAL, entity, timeMaster);
		// 勤怠集計情報を取得
		return getTotalTimeData(entity);
	}
	
	/**
	 * 仮締を行う。<br>
	 * <br>
	 * 勤怠集計及びデータの登録を行う。<br>
	 * 未締でない場合は、何もしない。<br>
	 * <br>
	 * @param entity     勤怠集計エンティティ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void tightening(TotalTimeEntityInterface entity) throws MospException {
		// 個人IDを準備
		String personalId = entity.getPersonalId();
		// 勤怠集計情報を取得して登録
		totalTimeRegist.regist(getTotalTimeData(entity));
		// 休暇集計情報を登録
		registVacation(entity);
		// 勤怠トランザクションを登録
		attendanceTransactionRegist.regist(personalId, entity.getAttendanceTransactionMap());
		// 計算年月と締日コードと集計日を準備
		int targetYear = entity.getCalculationYear();
		int targetMonth = entity.getCalculationMonth();
		String cutoffCode = entity.getCutoffCode();
		// 締日エンティティを取得
		CutoffEntityInterface cutoff = timeMaster.getCutoff(cutoffCode, targetYear, targetMonth);
		// 集計日を取得
		Date calculationDate = cutoff.getCutoffCalculationDate(targetYear, targetMonth, mospParams);
		// 仮締
		employeeTransRegist.draft(personalId, targetYear, targetMonth, cutoffCode, calculationDate);
	}
	
	/**
	 * 締日が設定されている社員全員が仮締であるかを確認する。<br>
	 * <br>
	 * 対象年月において対象締日コードが設定されている社員全員が
	 * 仮締状態となっている場合、trueを返す。<br>
	 * <br>
	 * @param targetYear  対象年
	 * @param targetMonth 対象月
	 * @param cutoffCode  締日コード
	 * @return 確認結果（true：締日が設定されている社員全員が仮締である、false：そうでない)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected boolean isAllTightened(int targetYear, int targetMonth, String cutoffCode) throws MospException {
		// 対象年月において対象締日コードが適用されている個人IDのセットを取得
		Set<String> personalIdSet = cutoffUtil.getCutoffPersonalIdSet(cutoffCode, targetYear, targetMonth);
		// 個人ID毎に処理
		for (String personalId : personalIdSet) {
			// 対象個人IDにつき対象年月が未締である場合
			if (cutoffUtil.isNotTighten(personalId, targetYear, targetMonth)) {
				// 締日が設定されている社員全員が仮締でないと判断
				return false;
			}
		}
		// 締日が設定されている社員全員が仮締であると判断
		return true;
	}
	
	/**
	 * 締日仮締を行う。<br>
	 * <br>
	 * 対象年月において対象締日コードが設定されている社員全員が
	 * 仮締状態となった場合、対象年月における対象締日コードを仮締とする。<br>
	 * <br>
	 * @param targetYear  対象年
	 * @param targetMonth 対象月
	 * @param cutoffCode  締日コード
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void tightenCutoff(int targetYear, int targetMonth, String cutoffCode) throws MospException {
		// 勤怠集計管理情報を準備
		TotalTimeDtoInterface dto = totalTransRegist.getInitDto();
		// DTOに値を設定
		dto.setCalculationYear(targetYear);
		dto.setCalculationMonth(targetMonth);
		dto.setCutoffCode(cutoffCode);
		// 締日エンティティを取得
		CutoffEntityInterface cutoff = timeMaster.getCutoff(cutoffCode, targetYear, targetMonth);
		// 集計日を取得し設定
		dto.setCalculationDate(cutoff.getCutoffCalculationDate(targetYear, targetMonth, mospParams));
		// 仮締をセット
		dto.setCutoffState(TimeConst.CODE_CUTOFF_STATE_TEMP_TIGHT);
		// 締日仮締
		totalTransRegist.draft(dto);
	}
	
	/**
	 * 休暇集計情報を登録する。<br>
	 * <br>
	 * @param entity 勤怠集計エンティティ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void registVacation(TotalTimeEntityInterface entity) throws MospException {
		// 個人IDと計算年月を準備
		String personalId = entity.getPersonalId();
		int targetYear = entity.getCalculationYear();
		int targetMonth = entity.getCalculationMonth();
		// 特別休暇集計情報を削除
		totalLeaveRegist.delete(personalId, targetYear, targetMonth);
		// 特別休暇回数群(キー：休暇コード)を取得
		Map<String, Float> specialHolidayDays = entity.getSpecialHolidayDays();
		Map<String, Integer> specialHolidayHours = entity.getSpecialHolidayHours();
		// 休暇コード毎に処理
		for (Entry<String, Float> entry : specialHolidayDays.entrySet()) {
			// 休暇コードを取得
			String holidayCode = entry.getKey();
			// 特別休暇集計情報を準備
			TotalLeaveDtoInterface dto = totalLeaveRegist.getInitDto();
			dto.setPersonalId(personalId);
			dto.setCalculationYear(targetYear);
			dto.setCalculationMonth(targetMonth);
			dto.setHolidayCode(holidayCode);
			dto.setTimes(entry.getValue());
			dto.setHours(specialHolidayHours.get(holidayCode));
			// 登録
			totalLeaveRegist.insert(dto);
		}
		// その他休暇集計情報を削除
		totalOtherVacationRegist.delete(personalId, targetYear, targetMonth);
		// その他休暇回数群(キー：休暇コード)を取得
		Map<String, Float> otherHolidayMap = entity.getOtherHolidayDays();
		Map<String, Integer> otherHolidayHours = entity.getOtherHolidayHours();
		// 休暇コード毎に処理
		for (Entry<String, Float> entry : otherHolidayMap.entrySet()) {
			// 休暇コードを取得
			String holidayCode = entry.getKey();
			// 特別休暇集計情報を準備
			TotalOtherVacationDtoInterface dto = totalOtherVacationRegist.getInitDto();
			dto.setPersonalId(personalId);
			dto.setCalculationYear(targetYear);
			dto.setCalculationMonth(targetMonth);
			dto.setHolidayCode(holidayCode);
			dto.setTimes(entry.getValue());
			dto.setHours(otherHolidayHours.get(holidayCode));
			// 登録
			totalOtherVacationRegist.insert(dto);
		}
		// 欠勤集計情報を削除
		totalAbsenceRegist.delete(personalId, targetYear, targetMonth);
		// 欠勤回数群(キー：休暇コード)を取得
		Map<String, Float> absenceMap = entity.getAbsenceDays();
		Map<String, Integer> absenceHours = entity.getAbsenceHours();
		// 休暇コード毎に処理
		for (Entry<String, Float> entry : absenceMap.entrySet()) {
			// 休暇コードを取得
			String holidayCode = entry.getKey();
			// 欠勤集計情報を準備
			TotalAbsenceDtoInterface dto = totalAbsenceRegist.getInitDto();
			dto.setPersonalId(personalId);
			dto.setCalculationYear(targetYear);
			dto.setCalculationMonth(targetMonth);
			dto.setAbsenceCode(holidayCode);
			dto.setTimes(entry.getValue());
			dto.setHours(absenceHours.get(holidayCode));
			// 登録
			totalAbsenceRegist.insert(dto);
		}
	}
	
	/**
	 * 各種申請及び振替休日情報から不要な情報を除去する。<br>
	 * <br>
	 * {@link TotalTimeCalcBean#check(TotalTimeEntityInterface)}
	 * 実行時には申請済申請(一次戻含む)が必要となる。<br>
	 * <br>
	 * {@link TotalTimeEntityInterface#total()}
	 * 実行時には承認済申請のみが必要となる。<br>
	 * (承認済でない申請は集計対象外。)<br>
	 * <br>
	 * @param entity      勤怠集計エンティティ
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ残す、false：申請済申請を残す)
	 */
	protected void removeUnusedRequests(TotalTimeEntityInterface entity, boolean isCompleted) {
		// 勤怠申請リストを準備
		List<AttendanceDtoInterface> attendanceList = new ArrayList<AttendanceDtoInterface>();
		// 休暇申請リストを準備
		List<HolidayRequestDtoInterface> holidayRequestList = new ArrayList<HolidayRequestDtoInterface>();
		// 休日出勤申請リストを準備
		List<WorkOnHolidayRequestDtoInterface> workOnHolidayRequestList = new ArrayList<WorkOnHolidayRequestDtoInterface>();
		// 残業申請リストを準備
		List<OvertimeRequestDtoInterface> overtimeRequestList = new ArrayList<OvertimeRequestDtoInterface>();
		// 勤務形態変更申請リストを準備
		List<WorkTypeChangeRequestDtoInterface> workTypeChangeRequestList = new ArrayList<WorkTypeChangeRequestDtoInterface>();
		// 時差出勤申請リストを準備
		List<DifferenceRequestDtoInterface> differenceRequestList = new ArrayList<DifferenceRequestDtoInterface>();
		// 振替休日データリストを準備
		List<SubstituteDtoInterface> substitubeList = new ArrayList<SubstituteDtoInterface>();
		// 代休申請リストを準備
		List<SubHolidayRequestDtoInterface> subHolidayRequestList = new ArrayList<SubHolidayRequestDtoInterface>();
		// 勤怠申請毎に処理
		for (AttendanceDtoInterface dto : entity.getAttendanceList()) {
			// 申請済である場合
			if (isWorkflowNeeded(entity.getWorkflowDto(dto.getWorkflow()), isCompleted)) {
				// 勤怠申請リストに追加
				attendanceList.add(dto);
			}
		}
		// 	休暇申請毎に処理
		for (HolidayRequestDtoInterface dto : entity.getHolidayRequestList()) {
			// 申請済である場合
			if (isWorkflowNeeded(entity.getWorkflowDto(dto.getWorkflow()), isCompleted)) {
				// 休暇申請リストに追加
				holidayRequestList.add(dto);
			}
		}
		// 休日出勤申請毎に処理
		for (WorkOnHolidayRequestDtoInterface dto : entity.getWorkOnHolidayRequestList()) {
			// 申請済である場合
			if (isWorkflowNeeded(entity.getWorkflowDto(dto.getWorkflow()), isCompleted)) {
				// 休日出勤申請リストに追加
				workOnHolidayRequestList.add(dto);
			}
		}
		// 残業申請毎に処理
		for (OvertimeRequestDtoInterface dto : entity.getOvertimeRequestList()) {
			// 申請済である場合
			if (isWorkflowNeeded(entity.getWorkflowDto(dto.getWorkflow()), isCompleted)) {
				// 残業申請リストに追加
				overtimeRequestList.add(dto);
			}
		}
		// 勤務形態変更申請毎に処理
		for (WorkTypeChangeRequestDtoInterface dto : entity.getWorkTypeChangeRequestList()) {
			// 申請済である場合
			if (isWorkflowNeeded(entity.getWorkflowDto(dto.getWorkflow()), isCompleted)) {
				// 勤務形態変更申請リストに追加
				workTypeChangeRequestList.add(dto);
			}
		}
		// 時差出勤申請毎に処理
		for (DifferenceRequestDtoInterface dto : entity.getDifferenceRequestList()) {
			// 申請済である場合
			if (isWorkflowNeeded(entity.getWorkflowDto(dto.getWorkflow()), isCompleted)) {
				// 時差出勤申請リストに追加
				differenceRequestList.add(dto);
			}
		}
		// 振替休日データ毎に処理
		for (SubstituteDtoInterface dto : entity.getSubstitubeList()) {
			// 申請済である場合
			if (isWorkflowNeeded(entity.getWorkflowDto(dto.getWorkflow()), isCompleted)) {
				// 振替休日データリストに追加
				substitubeList.add(dto);
			}
		}
		// 代休申請毎に処理
		for (SubHolidayRequestDtoInterface dto : entity.getSubHolidayRequestList()) {
			// 申請済である場合
			if (isWorkflowNeeded(entity.getWorkflowDto(dto.getWorkflow()), isCompleted)) {
				// 代休申請リストに追加
				subHolidayRequestList.add(dto);
			}
		}
		// 勤怠申請リストを設定
		entity.setAttendanceList(attendanceList);
		// 休暇申請リストを設定
		entity.setHolidayRequestList(holidayRequestList);
		// 休日出勤申請リストを設定
		entity.setWorkOnHolidayRequestList(workOnHolidayRequestList);
		// 残業申請リストを設定
		entity.setOvertimeRequestList(overtimeRequestList);
		// 勤務形態変更申請リストを設定
		entity.setWorkTypeChangeRequestList(workTypeChangeRequestList);
		// 時差出勤申請リストを設定
		entity.setDifferenceRequestList(differenceRequestList);
		// 振替休日データを設定
		entity.setSubstitubeList(substitubeList);
		// 代休申請リストを設定
		entity.setSubHolidayRequestList(subHolidayRequestList);
	}
	
	/**
	 * 対象ワークフロー情報が必要なワークフローであるかを確認する。<br>
	 * <br>
	 * ワークフローの状態で判断する。<br>
	 * <br>
	 * @param dto         対象ワークフロー情報
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ残す、false：申請済申請を残す)
	 * @return 確認結果(true：対象ワークフロー情報が必要である、false：そうでない)
	 */
	protected boolean isWorkflowNeeded(WorkflowDtoInterface dto, boolean isCompleted) {
		// 承認済申請のみ残す場合
		if (isCompleted) {
			// 承認済であるかを確認
			return WorkflowUtility.isCompleted(dto);
		}
		// 申請済(一次戻含む)であるかを確認
		return WorkflowUtility.isApplied(dto) || WorkflowUtility.isFirstReverted(dto);
	}
	
	/**
	 * 勤怠集計情報を取得する。<br>
	 * <br>
	 * {@link TotalTimeEntityInterface#total()}
	 * でエンティティのフィールドに設定された集計値を
	 * 勤怠集計情報に設定する。<br>
	 * <br>
	 * @param entity 勤怠集計エンティティ
	 * @return 勤怠集計情報
	 * @throws MospException 日付操作に失敗した場合
	 */
	protected TotalTimeDataDtoInterface getTotalTimeData(TotalTimeEntityInterface entity) throws MospException {
		// 個人IDと計算年月と締日コードを準備
		String personalId = entity.getPersonalId();
		int targetYear = entity.getCalculationYear();
		int targetMonth = entity.getCalculationMonth();
		String cutoffCode = entity.getCutoffCode();
		// 締日エンティティを取得
		CutoffEntityInterface cutoff = timeMaster.getCutoff(cutoffCode, targetYear, targetMonth);
		// 対象年月における締期間の集計日を取得
		Date calculationDate = cutoff.getCutoffCalculationDate(targetYear, targetMonth, mospParams);
		// 勤怠集計情報を取得
		TotalTimeDataDtoInterface dto = totalTimeRegist.getInitDto();
		// 勤怠集計情報に値を設定
		dto.setPersonalId(personalId);
		dto.setCalculationYear(targetYear);
		dto.setCalculationMonth(targetMonth);
		dto.setCalculationDate(calculationDate);
		dto.setWorkTime(entity.getWorkTime());
		dto.setTimesWorkDate(entity.getTimesWorkDate());
		dto.setTimesWork(entity.getTimesWork());
		dto.setLegalWorkOnHoliday(entity.getLegalWorkOnHoliday());
		dto.setSpecificWorkOnHoliday(entity.getSpecificWorkOnHoliday());
		dto.setTimesAchievement(entity.getTimesAchievement());
		dto.setTimesTotalWorkDate(entity.getTimesTotalWorkDate());
		dto.setDirectStart(entity.getDirectStart());
		dto.setDirectEnd(entity.getDirectEnd());
		dto.setRestTime(entity.getRestTime());
		dto.setRestLateNight(entity.getRestLateNight());
		dto.setRestWorkOnSpecificHoliday(entity.getRestWorkOnSpecificHoliday());
		dto.setRestWorkOnHoliday(entity.getRestWorkOnHoliday());
		dto.setPublicTime(entity.getPublicTime());
		dto.setPrivateTime(entity.getPrivateTime());
		dto.setMinutelyHolidayATime(entity.getMinutelyHolidayATime());
		dto.setMinutelyHolidayBTime(entity.getMinutelyHolidayBTime());
		dto.setOvertimeIn(entity.getOvertimeIn());
		dto.setOvertimeOut(entity.getOvertimeOut());
		dto.setOvertime(entity.getOvertime());
		dto.setLateNight(entity.getLateNight());
		dto.setNightWorkWithinPrescribedWork(entity.getNightWorkWithinPrescribedWork());
		dto.setNightOvertimeWork(entity.getNightOvertimeWork());
		dto.setNightWorkOnHoliday(entity.getNightWorkOnHoliday());
		dto.setWorkOnSpecificHoliday(entity.getWorkOnSpecificHoliday());
		dto.setWorkOnHoliday(entity.getWorkOnHoliday());
		dto.setDecreaseTime(entity.getDecreaseTime());
		dto.setFortyFiveHourOvertime(entity.getFortyFiveHourOvertime());
		dto.setTimesOvertime(entity.getTimesOvertime());
		dto.setTimesWorkingHoliday(entity.getTimesWorkingHoliday());
		dto.setLateDays(entity.getLateDays());
		dto.setLateThirtyMinutesOrMore(entity.getLateThirtyMinutesOrMore());
		dto.setLateLessThanThirtyMinutes(entity.getLateLessThanThirtyMinutes());
		dto.setLateTime(entity.getLateTime());
		dto.setLateThirtyMinutesOrMoreTime(entity.getLateThirtyMinutesOrMoreTime());
		dto.setLateLessThanThirtyMinutesTime(entity.getLateLessThanThirtyMinutesTime());
		dto.setTimesLate(entity.getTimesLate());
		dto.setLeaveEarlyDays(entity.getLeaveEarlyDays());
		dto.setLeaveEarlyThirtyMinutesOrMore(entity.getLeaveEarlyThirtyMinutesOrMore());
		dto.setLeaveEarlyLessThanThirtyMinutes(entity.getLeaveEarlyLessThanThirtyMinutes());
		dto.setLeaveEarlyTime(entity.getLeaveEarlyTime());
		dto.setLeaveEarlyThirtyMinutesOrMoreTime(entity.getLeaveEarlyThirtyMinutesOrMoreTime());
		dto.setLeaveEarlyLessThanThirtyMinutesTime(entity.getLeaveEarlyLessThanThirtyMinutesTime());
		dto.setTimesLeaveEarly(entity.getTimesLeaveEarly());
		dto.setTimesHoliday(entity.getTimesHoliday());
		dto.setTimesLegalHoliday(entity.getTimesLegalHoliday());
		dto.setTimesSpecificHoliday(entity.getTimesSpecificHoliday());
		dto.setTimesPaidHoliday(entity.getTimesPaidHoliday());
		dto.setPaidHolidayHour(entity.getPaidHolidayHour());
		dto.setTimesStockHoliday(entity.getTimesStockHoliday());
		dto.setTimesCompensation(entity.getTimesCompensation());
		dto.setTimesLegalCompensation(entity.getTimesLegalCompensation());
		dto.setTimesSpecificCompensation(entity.getTimesSpecificCompensation());
		dto.setTimesLateCompensation(entity.getTimesLateCompensation());
		dto.setTimesHolidaySubstitute(entity.getTimesHolidaySubstitute());
		dto.setTimesLegalHolidaySubstitute(entity.getTimesLegalHolidaySubstitute());
		dto.setTimesSpecificHolidaySubstitute(entity.getTimesSpecificHolidaySubstitute());
		dto.setTotalSpecialHoliday(entity.getTotalSpecialHoliday());
		dto.setSpecialHolidayHour(entity.getSpecialHolidayHour());
		dto.setTotalOtherHoliday(entity.getTotalOtherHoliday());
		dto.setOtherHolidayHour(entity.getOtherHolidayHour());
		dto.setTotalAbsence(entity.getTotalAbsence());
		dto.setAbsenceHour(entity.getAbsenceHour());
		dto.setTotalAllowance(entity.getTotalAllowance());
		dto.setSixtyHourOvertime(entity.getSixtyHourOvertime());
		dto.setWeekDayOvertime(entity.getWorkdayOvertimeOut());
		dto.setSpecificOvertime(entity.getPrescribedOvertimeOut());
		dto.setTimesAlternative(entity.getTimesAlternative());
		dto.setLegalCompensationOccurred(entity.getLegalCompensationOccurred());
		dto.setSpecificCompensationOccurred(entity.getSpecificCompensationOccurred());
		dto.setLateCompensationOccurred(entity.getLateCompensationOccurred());
		dto.setLegalCompensationUnused(entity.getLegalCompensationUnused());
		dto.setSpecificCompensationUnused(entity.getSpecificCompensationUnused());
		dto.setLateCompensationUnused(entity.getLateCompensationUnused());
		dto.setStatutoryHolidayWorkTimeIn(entity.getStatutoryHolidayWorkTimeIn());
		dto.setStatutoryHolidayWorkTimeOut(entity.getStatutoryHolidayWorkTimeOut());
		dto.setPrescribedHolidayWorkTimeIn(entity.getPrescribedHolidayWorkTimeIn());
		dto.setPrescribedHolidayWorkTimeOut(entity.getPrescribedHolidayWorkTimeOut());
		dto.setContractWorkTime(entity.getContractWorkTime());
		dto.setShortUnpaid(entity.getShortUnpaid());
		dto.setWeeklyOverFortyHourWorkTime(entity.getWeeklyOverFortyHourWorkTime());
		dto.setOvertimeInNoWeeklyForty(entity.getOvertimeInNoWeeklyForty());
		dto.setOvertimeOutNoWeeklyForty(entity.getOvertimeOutNoWeeklyForty());
		dto.setWeekDayOvertimeTotal(entity.getWorkdayOvertimeIn() + entity.getWorkdayOvertimeOut());
		dto.setWeekDayOvertimeInNoWeeklyForty(entity.getWeekDayOvertimeInNoWeeklyForty());
		dto.setWeekDayOvertimeOutNoWeeklyForty(entity.getWeekDayOvertimeOutNoWeeklyForty());
		dto.setWeekDayOvertimeIn(entity.getWorkdayOvertimeIn());
		dto.setPrescribedHolidayOvertimeIn(entity.getPrescribedHolidayOvertimeIn());
		dto.setGeneralIntItem1(entity.getGeneralIntItem1());
		dto.setGeneralIntItem2(entity.getGeneralIntItem2());
		dto.setGeneralIntItem3(entity.getGeneralIntItem3());
		dto.setGeneralIntItem4(entity.getGeneralIntItem4());
		dto.setGeneralIntItem5(entity.getGeneralIntItem5());
		dto.setGeneralDoubleItem1(entity.getGeneralDoubleItem1());
		dto.setGeneralDoubleItem2(entity.getGeneralDoubleItem2());
		dto.setGeneralDoubleItem3(entity.getGeneralDoubleItem3());
		dto.setGeneralDoubleItem4(entity.getGeneralDoubleItem4());
		dto.setGeneralDoubleItem5(entity.getGeneralDoubleItem5());
		dto.setGeneralValues(entity.getGeneralValues());
		// 所定勤務時間(勤務時間-残業時間(内残+外残)-法定休出時間)
		dto.setSpecificWorkTime(entity.getWorkTime() - entity.getOvertime() - entity.getWorkOnHoliday());
		// 各集計値の丸め
		round(dto, getTimeSettingDtoForRound(entity));
		// 追加業務ロジック処理
		doAdditionalLogic(TimeConst.CODE_KEY_ADD_TOTALTIMECALCBEAN_GETTOTALTIMEDATA, entity, dto);
		// 勤怠集計情報を取得
		return dto;
	}
	
	/**
	 * 各集計値の丸めを行う。<br>
	 * <br>
	 * @param dto    勤怠集計情報
	 * @param setDto 勤怠設定情報
	 */
	protected void round(TotalTimeDataDtoInterface dto, TimeSettingDtoInterface setDto) {
		// 勤怠設定情報が取得できなかった場合
		if (setDto == null) {
			// 処理無し
			return;
		}
		// 勤務時間
		dto.setWorkTime(roundMonthlyWork(dto.getWorkTime(), setDto));
		// 休憩時間
		dto.setRestTime(
				getRoundMinute(dto.getRestTime(), setDto.getRoundMonthlyRest(), setDto.getRoundMonthlyRestUnit()));
		// 深夜休憩時間
		dto.setRestLateNight(
				getRoundMinute(dto.getRestLateNight(), setDto.getRoundMonthlyRest(), setDto.getRoundMonthlyRestUnit()));
		// 所定休出休憩時間
		dto.setRestWorkOnSpecificHoliday(getRoundMinute(dto.getRestWorkOnSpecificHoliday(),
				setDto.getRoundMonthlyRest(), setDto.getRoundMonthlyRestUnit()));
		// 法定休出休憩時間
		dto.setRestWorkOnHoliday(getRoundMinute(dto.getRestWorkOnHoliday(), setDto.getRoundMonthlyRest(),
				setDto.getRoundMonthlyRestUnit()));
		// 公用外出時間
		dto.setPublicTime(getRoundMinute(dto.getPublicTime(), setDto.getRoundMonthlyPublic(),
				setDto.getRoundMonthlyPublicTime()));
		// 私用外出時間
		dto.setPrivateTime(getRoundMinute(dto.getPrivateTime(), setDto.getRoundMonthlyPrivate(),
				setDto.getRoundMonthlyPrivateTime()));
		// 法定内残業時間
		dto.setOvertimeIn(roundMonthlyWork(dto.getOvertimeIn(), setDto));
		// 法定外残業時間
		dto.setOvertimeOut(roundMonthlyWork(dto.getOvertimeOut(), setDto));
		// 残業時間
		dto.setOvertime(dto.getOvertimeIn() + dto.getOvertimeOut());
		// 深夜時間
		dto.setLateNight(roundMonthlyWork(dto.getLateNight(), setDto));
		// 深夜所定労働時間内時間
		dto.setNightWorkWithinPrescribedWork(roundMonthlyWork(dto.getNightWorkWithinPrescribedWork(), setDto));
		// 深夜時間外時間
		dto.setNightOvertimeWork(roundMonthlyWork(dto.getNightOvertimeWork(), setDto));
		// 深夜休日労働時間
		dto.setNightWorkOnHoliday(roundMonthlyWork(dto.getNightWorkOnHoliday(), setDto));
		// 所定休出時間
		dto.setWorkOnSpecificHoliday(roundMonthlyWork(dto.getWorkOnSpecificHoliday(), setDto));
		// 法定休出時間
		dto.setWorkOnHoliday(roundMonthlyWork(dto.getWorkOnHoliday(), setDto));
		// 減額対象時間
		dto.setDecreaseTime(getRoundMinute(dto.getDecreaseTime(), setDto.getRoundMonthlyDecrease(),
				setDto.getRoundMonthlyDecreaseTime()));
		// 45時間超残業時間
		dto.setFortyFiveHourOvertime(roundMonthlyWork(dto.getFortyFiveHourOvertime(), setDto));
		// 合計遅刻時間
		dto.setLateTime(
				getRoundMinute(dto.getLateTime(), setDto.getRoundMonthlyLate(), setDto.getRoundMonthlyLateUnit()));
		// 遅刻30分以上時間
		dto.setLateThirtyMinutesOrMoreTime(getRoundMinute(dto.getLateThirtyMinutesOrMoreTime(),
				setDto.getRoundMonthlyLate(), setDto.getRoundMonthlyLateUnit()));
		// 遅刻30分未満時間
		dto.setLateLessThanThirtyMinutesTime(getRoundMinute(dto.getLateLessThanThirtyMinutesTime(),
				setDto.getRoundMonthlyLate(), setDto.getRoundMonthlyLateUnit()));
		// 合計早退時間
		dto.setLeaveEarlyTime(getRoundMinute(dto.getLeaveEarlyTime(), setDto.getRoundMonthlyEarly(),
				setDto.getRoundMonthlyEarlyUnit()));
		// 早退30分以上時間
		dto.setLeaveEarlyThirtyMinutesOrMoreTime(getRoundMinute(dto.getLeaveEarlyThirtyMinutesOrMoreTime(),
				setDto.getRoundMonthlyEarly(), setDto.getRoundMonthlyEarlyUnit()));
		// セットする 早退30分未満時間
		dto.setLeaveEarlyLessThanThirtyMinutesTime(getRoundMinute(dto.getLeaveEarlyLessThanThirtyMinutesTime(),
				setDto.getRoundMonthlyEarly(), setDto.getRoundMonthlyEarlyUnit()));
		// 60時間超残業時間
		dto.setSixtyHourOvertime(roundMonthlyWork(dto.getSixtyHourOvertime(), setDto));
		// 平日時間外時間
		dto.setWeekDayOvertime(roundMonthlyWork(dto.getWeekDayOvertime(), setDto));
		// 所定休日時間外時間
		dto.setSpecificOvertime(roundMonthlyWork(dto.getSpecificOvertime(), setDto));
		// 所定労働時間内法定休日労働時間
		dto.setStatutoryHolidayWorkTimeIn(roundMonthlyWork(dto.getStatutoryHolidayWorkTimeIn(), setDto));
		// 所定労働時間外法定休日労働時間
		dto.setStatutoryHolidayWorkTimeOut(roundMonthlyWork(dto.getStatutoryHolidayWorkTimeOut(), setDto));
		// 所定労働時間内所定休日労働時間
		dto.setPrescribedHolidayWorkTimeIn(roundMonthlyWork(dto.getPrescribedHolidayWorkTimeIn(), setDto));
		// 所定労働時間外所定休日労働時間
		dto.setPrescribedHolidayWorkTimeOut(roundMonthlyWork(dto.getPrescribedHolidayWorkTimeOut(), setDto));
		// 所定勤務時間
		dto.setSpecificWorkTime(roundMonthlyWork(
				dto.getWorkTime() - dto.getOvertimeIn() - dto.getOvertimeOut() - dto.getWorkOnHoliday(), setDto));
		// 契約勤務時間
		dto.setContractWorkTime(roundMonthlyWork(dto.getContractWorkTime(), setDto));
		// 無給時短時間
		dto.setShortUnpaid(getRoundMinute(dto.getShortUnpaid(), setDto.getRoundMonthlyShortUnpaid(),
				setDto.getRoundMonthlyShortUnpaidUnit()));
		// 週40時間超勤務時間
		dto.setWeeklyOverFortyHourWorkTime(roundMonthlyWork(dto.getWeeklyOverFortyHourWorkTime(), setDto));
		// 週40時間超勤務時間
		dto.setOvertimeInNoWeeklyForty(roundMonthlyWork(dto.getOvertimeInNoWeeklyForty(), setDto));
		// 法定外残業時間(週40時間超除く)
		dto.setOvertimeOutNoWeeklyForty(roundMonthlyWork(dto.getOvertimeOutNoWeeklyForty(), setDto));
		// 平日残業合計時間
		dto.setWeekDayOvertimeTotal(dto.getWeekDayOvertimeIn() + dto.getWeekDayOvertime());
		// 平日時間内時間(週40時間超除く)
		dto.setWeekDayOvertimeInNoWeeklyForty(roundMonthlyWork(dto.getWeekDayOvertimeInNoWeeklyForty(), setDto));
		// 平日時間外時間(週40時間超除く)
		dto.setWeekDayOvertimeOutNoWeeklyForty(roundMonthlyWork(dto.getWeekDayOvertimeOutNoWeeklyForty(), setDto));
		// 平日時間内時間
		dto.setWeekDayOvertimeIn(roundMonthlyWork(dto.getWeekDayOvertimeIn(), setDto));
		// 汎用項目
		dto.setGeneralIntItem1(roundMonthlyWork(dto.getGeneralIntItem1(), setDto));
		dto.setGeneralIntItem2(roundMonthlyWork(dto.getGeneralIntItem2(), setDto));
		dto.setGeneralIntItem3(roundMonthlyWork(dto.getGeneralIntItem3(), setDto));
		dto.setGeneralIntItem4(roundMonthlyWork(dto.getGeneralIntItem4(), setDto));
		dto.setGeneralIntItem5(roundMonthlyWork(dto.getGeneralIntItem5(), setDto));
	}
	
	/**
	 * 集計値丸め用の勤怠設定情報を取得する。<br>
	 * <br>
	 * 勤怠集計エンティティに設定されている勤怠設定のうち、
	 * 最も新しいものを取得する。<br>
	 * <br>
	 * @param entity 勤怠集計エンティティ
	 * @return 勤怠設定情報
	 */
	protected TimeSettingDtoInterface getTimeSettingDtoForRound(TotalTimeEntityInterface entity) {
		// 勤怠設定情報群を取得
		Map<Date, TimeSettingDtoInterface> map = entity.getTimeSettingMap();
		// 対象日リストを取得
		List<Date> targetDateList = new ArrayList<Date>(map.keySet());
		// ソート(降順)
		Collections.sort(targetDateList);
		Collections.reverse(targetDateList);
		// 対象日毎に処理
		for (Date targetDate : targetDateList) {
			// 勤怠設定情報を取得
			TimeSettingDtoInterface dto = map.get(targetDate);
			// 勤怠設定情報が取得できた場合
			if (dto != null) {
				// 勤怠設定情報を取得
				return dto;
			}
		}
		// 勤怠設定情報が取得できなかった場合
		return null;
	}
	
	/**
	 * 丸めた(月勤務時間丸め)時間を取得する。<br>
	 * @param time 対象時間
	 * @param dto  勤怠設定情報
	 * @return 丸めた(月勤務時間丸め)時間
	 */
	protected int roundMonthlyWork(int time, TimeSettingDtoInterface dto) {
		return getRoundMinute(time, dto.getRoundMonthlyWork(), dto.getRoundMonthlyWorkUnit());
	}
	
}
