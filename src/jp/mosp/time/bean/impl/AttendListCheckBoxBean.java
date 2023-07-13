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

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.workflow.WorkflowIntegrateBeanInterface;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;
import jp.mosp.platform.utils.WorkflowUtility;
import jp.mosp.time.bean.AttendListCheckBoxBeanInterface;
import jp.mosp.time.bean.TimeMasterBeanInterface;
import jp.mosp.time.bean.TotalTimeEmployeeTransactionReferenceBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dto.settings.TotalTimeEmployeeDtoInterface;
import jp.mosp.time.dto.settings.impl.AttendanceListDto;
import jp.mosp.time.entity.AttendListEntityInterface;
import jp.mosp.time.entity.RequestEntityInterface;
import jp.mosp.time.utils.AttendanceUtility;
import jp.mosp.time.utils.TimeUtility;

/**
 * 勤怠一覧チェックボックス要否設定処理。<br>
 */
public class AttendListCheckBoxBean extends PlatformBean implements AttendListCheckBoxBeanInterface {
	
	/**
	 * 社員勤怠集計管理参照処理。<br>
	 */
	protected TotalTimeEmployeeTransactionReferenceBeanInterface	totalTimeEmployeeTransRefer;
	
	/**
	 * ワークフロー統括処理。<br>
	 */
	protected WorkflowIntegrateBeanInterface						workflowIntegrate;
	
	/**
	 * 勤怠関連マスタ参照処理。<br>
	 * <br>
	 * {@link #setTimeMaster(TimeMasterBeanInterface)}で設定する必要がある。<br>
	 */
	protected TimeMasterBeanInterface								timeMaster;
	
	
	@Override
	public void initBean() throws MospException {
		// Beanを準備
		totalTimeEmployeeTransRefer = createBeanInstance(TotalTimeEmployeeTransactionReferenceBeanInterface.class);
		workflowIntegrate = createBeanInstance(WorkflowIntegrateBeanInterface.class);
	}
	
	@Override
	public void setCheckBox(List<AttendanceListDto> attendList, AttendListEntityInterface entity) throws MospException {
		// 勤怠一覧情報が存在しない場合
		if (MospUtility.isEmpty(attendList)) {
			// 処理終了
			return;
		}
		// 最終レコードを取得
		AttendanceListDto lastDto = MospUtility.getLastValue(attendList);
		// 締められている場合(最終レコードで判断)
		if (TimeUtility.isTightend(getTotalTimeEmployee(lastDto))) {
			// 勤怠一覧情報のチェックボックス要否を設定(チェックボックス不要)
			setAllCheckBox(attendList, false);
			// 処理終了
			return;
		}
		// 勤怠一覧区分が承認である場合
		if (AttendanceUtility.isListTypeApproval(lastDto.getListType())) {
			// チェックボックス要否を勤怠一覧情報に設定(承認用)
			setCheckBoxForApproval(attendList);
			// 処理終了
			return;
		}
		// 勤怠一覧情報毎に処理
		for (AttendanceListDto dto : attendList) {
			// チェックボックス要否を勤怠一覧情報に設定(勤怠用)
			setCheckBoxForAttendance(dto, entity);
		}
	}
	
	@Override
	public void setCheckBox(Map<String, AttendanceListDto> dtos, Map<String, AttendListEntityInterface> entities)
			throws MospException {
		// 勤怠一覧情報毎に処理
		for (Entry<String, AttendanceListDto> entry : dtos.entrySet()) {
			// 個人IDと勤怠一覧情報と勤怠一覧エンティティを取得
			String personalId = entry.getKey();
			AttendanceListDto dto = entry.getValue();
			AttendListEntityInterface entity = entities.get(personalId);
			// 勤怠一覧情報に勤怠一覧チェックボックス要否を設定
			setCheckBox(dto, entity);
		}
	}
	
	/**
	 * 勤怠一覧情報に勤怠一覧チェックボックス要否を設定する。<br>
	 * 勤怠一覧区分は勤怠であることを前提とする。<br>
	 * @param dto    勤怠一覧情報
	 * @param entity 勤怠一覧エンティティ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setCheckBox(AttendanceListDto dto, AttendListEntityInterface entity) throws MospException {
		// 勤怠一覧情報か勤怠一覧エンティティが存在しない場合
		if (MospUtility.isEmpty(dto, entity)) {
			// 処理終了
			return;
		}
		// 締められている場合
		if (TimeUtility.isTightend(getTotalTimeEmployee(dto))) {
			// 勤怠一覧情報のチェックボックス要否を設定(チェックボックス不要)
			dto.setNeedCheckbox(false);
		}
		// チェックボックス要否を勤怠一覧情報に設定(勤怠用)
		setCheckBoxForAttendance(dto, entity);
	}
	
	/**
	 * チェックボックス要否を勤怠一覧情報に設定(勤怠用)する。<br>
	 * @param dto    勤怠一覧情報
	 * @param entity 勤怠一覧エンティティ
	 * @throws MospException オブジェクトの生成に失敗した場合
	 */
	protected void setCheckBoxForAttendance(AttendanceListDto dto, AttendListEntityInterface entity)
			throws MospException {
		// 申請エンティティを取得
		RequestEntityInterface requestEntity = entity.getRequestEntity(dto);
		// 対象となる承認状況群(有効な承認状況群(下書と取下以外))を取得
		Set<String> statuses = WorkflowUtility.getEffectiveStatuses();
		// 勤務日と予定される勤務形態コードを取得
		Date workDate = dto.getWorkDate();
		String workTypeCode = requestEntity.getWorkType(false, statuses);
		// 勤務形態コードが所定休日又は法定休日であるか全休(承認済)である場合
		if (TimeUtility.isHoliday(workTypeCode) || requestEntity.isAllHoliday(statuses)) {
			// チェックボックス要否を設定(不要)
			dto.setNeedCheckbox(false);
			// 処理終了
			return;
		}
		// 振出・休出申請があり承認済でない場合
		if (requestEntity.hasWorkOnHoliday(statuses) && requestEntity.hasWorkOnHoliday(true) == false) {
			// チェックボックス要否を設定(不要)
			dto.setNeedCheckbox(false);
			// 処理終了
			return;
		}
		// 勤怠申請がされている場合
		if (requestEntity.isAttendanceApplied()) {
			// チェックボックス要否を設定(不要)
			dto.setNeedCheckbox(false);
			// 処理終了
			return;
		}
		// 勤務日に退職か休職をしている場合
		if (entity.isRetireOrSusoension(workDate)) {
			// チェックボックス要否を設定(不要)
			dto.setNeedCheckbox(false);
			// 処理終了
			return;
		}
		// 予定される勤務形態コードが設定されていない場合(設定適用が取得できない日である場合等)
		if (MospUtility.isEmpty(workTypeCode)) {
			// チェックボックス要否を設定(不要)
			dto.setNeedCheckbox(false);
			// 処理終了
			return;
		}
		// チェックボックス要否を設定(必要)
		dto.setNeedCheckbox(true);
	}
	
	/**
	 * チェックボックス要否を勤怠一覧情報に設定(承認用)する。<br>
	 * @param dtos 勤怠一覧情報群
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setCheckBoxForApproval(Collection<AttendanceListDto> dtos) throws MospException {
		// 承認可能ワークフロー情報群(勤怠)を取得
		Map<Long, WorkflowDtoInterface> approvables = getApprovables();
		// 勤怠一覧情報毎に処理
		for (AttendanceListDto dto : dtos) {
			// チェックボックス要否を勤怠一覧情報に設定(承認用)
			dto.setNeedCheckbox(approvables.containsKey(dto.getWorkflow()));
		}
	}
	
	/**
	 * 勤怠一覧情報のチェックボックス要否を設定する。<b>
	 * @param dtos  勤怠一覧情報群
	 * @param value 設定する値
	 */
	protected void setAllCheckBox(Collection<AttendanceListDto> dtos, boolean value) {
		// 勤怠一覧情報毎に処理
		for (AttendanceListDto dto : dtos) {
			// チェックボックス要否を勤怠一覧情報に設定
			dto.setNeedCheckbox(value);
		}
	}
	
	/**
	 * 社員勤怠集計管理情報を取得する。<br>
	 * 勤怠一覧情報に対象年月が設定されている場合はその年月の、
	 * 設定されていない場合は勤務日が含まれる締月の情報を取得する。<br>
	 * 社員勤怠集計管理情報が取得できなかった場合は、nullを取得する。<br>
	 * @param dto 勤怠一覧情報
	 * @return 社員勤怠集計管理情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected TotalTimeEmployeeDtoInterface getTotalTimeEmployee(AttendanceListDto dto) throws MospException {
		// 個人IDと対象年月を取得
		String personalId = dto.getPersonalId();
		int targetYear = dto.getTargetYear();
		int targetMonth = dto.getTargetMonth();
		// 対象年月が設定されている場合
		if (targetYear != 0) {
			// 対象年月で社員勤怠集計管理情報を取得
			return totalTimeEmployeeTransRefer.findForKey(personalId, targetYear, targetMonth);
		}
		// 勤務日を取得
		Date workDate = dto.getWorkDate();
		// 勤務日が含まれる締月(年月の初日)を取得
		Date cutoffMonth = timeMaster.getCutoffForPersonalId(personalId, workDate).getCutoffMonth(workDate, mospParams);
		// 個人ID及び集計年月で社員勤怠集計管理情報を取得
		return totalTimeEmployeeTransRefer.getTotalTimeEmployee(personalId, cutoffMonth);
	}
	
	/**
	 * 承認可能ワークフロー情報マップ(勤怠)を取得する。<br>
	 * ログインユーザが承認可能な情報を取得する。<br>
	 * @return 承認可能ワークフロー情報マップ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected Map<Long, WorkflowDtoInterface> getApprovables() throws MospException {
		// ログインユーザ個人ID取得
		String personalId = MospUtility.getLoginPersonalId(mospParams);
		// 承認可能ワークフローリスト(勤怠)取得
		return workflowIntegrate.getApprovableMap(personalId, TimeConst.CODE_FUNCTION_WORK_MANGE);
	}
	
	@Override
	public void setTimeMaster(TimeMasterBeanInterface timeMaster) {
		// 勤怠関連マスタ参照処理を設定
		this.timeMaster = timeMaster;
	}
	
}
