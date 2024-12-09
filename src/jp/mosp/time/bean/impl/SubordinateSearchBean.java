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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.platform.bean.human.impl.HumanSubordinateBean;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.utils.PfNameUtility;
import jp.mosp.time.bean.SubordinateSearchBeanInterface;
import jp.mosp.time.bean.TimeMasterBeanInterface;
import jp.mosp.time.bean.TotalTimeCorrectionReferenceBeanInterface;
import jp.mosp.time.bean.TotalTimeEmployeeTransactionReferenceBeanInterface;
import jp.mosp.time.bean.TotalTimeTransactionReferenceBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dao.settings.TotalTimeDataDaoInterface;
import jp.mosp.time.dto.settings.SubordinateListDtoInterface;
import jp.mosp.time.dto.settings.TotalTimeCorrectionDtoInterface;
import jp.mosp.time.dto.settings.TotalTimeDataDtoInterface;
import jp.mosp.time.dto.settings.TotalTimeEmployeeDtoInterface;
import jp.mosp.time.dto.settings.impl.SubordinateListDto;
import jp.mosp.time.entity.ApplicationEntity;
import jp.mosp.time.entity.RequestDetectEntityInterface;
import jp.mosp.time.utils.TimeNamingUtility;

/**
 * 部下一覧検索処理。<br>
 */
public class SubordinateSearchBean extends HumanSubordinateBean implements SubordinateSearchBeanInterface {
	
	/**
	 * 勤怠集計データDAO。
	 */
	protected TotalTimeDataDaoInterface								totalTimeDataDao;
	
	/**
	 * 勤怠集計修正情報参照。
	 */
	protected TotalTimeCorrectionReferenceBeanInterface				totalTimeCorrection;
	
	/**
	 * 社員勤怠集計管理参照。
	 */
	protected TotalTimeEmployeeTransactionReferenceBeanInterface	totalTimeEmployeeTransaction;
	
	/**
	 * 勤怠集計管理参照クラス。
	 */
	protected TotalTimeTransactionReferenceBeanInterface			totalTimeTransactionRefer;
	
	/**
	 * 勤怠集計エンティティ取得クラス。<br>
	 */
	protected TotalTimeEntityReferenceBean							totalTimeEntityRefer;
	
	/**
	 * 勤怠関連マスタ参照クラス。<br>
	 */
	protected TimeMasterBeanInterface								timeMaster;
	
	/**
	 * 対象年。
	 */
	protected int													targetYear;
	
	/**
	 * 対象月。
	 */
	protected int													targetMonth;
	
	/**
	 * 未承認。
	 */
	protected String												approval;
	
	/**
	 * 未承認前日フラグ。
	 */
	protected String												approvalBeforeDay;
	
	/**
	 * 締状態。
	 */
	protected String												calc;
	
	
	/**
	 * {@link HumanSubordinateBean#HumanSubordinateBean()}を実行する。<br>
	 */
	public SubordinateSearchBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// 継承基の処理を実行
		super.initBean();
		// 勤怠集計データDAO取得
		totalTimeDataDao = createDaoInstance(TotalTimeDataDaoInterface.class);
		// 勤怠集計修正情報参照クラス取得
		totalTimeCorrection = createBeanInstance(TotalTimeCorrectionReferenceBeanInterface.class);
		// 社員勤怠集計管理参照クラス取得
		totalTimeEmployeeTransaction = createBeanInstance(TotalTimeEmployeeTransactionReferenceBeanInterface.class);
		// 勤怠集計管理参照クラス取得
		totalTimeTransactionRefer = createBeanInstance(TotalTimeTransactionReferenceBeanInterface.class);
		// 勤怠集計エンティティ取得クラス取得
		totalTimeEntityRefer = createBeanInstance(TotalTimeEntityReferenceBean.class);
		// 勤怠関連マスタ参照クラスを取得
		timeMaster = createBeanInstance(TimeMasterBeanInterface.class);
		timeMaster.setPlatformMaster(platformMaster);
		// プラットフォームマスタ参照クラスを勤怠集計エンティティ取得クラスに設定
		totalTimeEntityRefer.setPlatformMasterBean(platformMaster);
		// 勤怠関連マスタ参照クラスを勤怠集計エンティティ取得クラスに設定
		totalTimeEntityRefer.setTimeMasterBean(timeMaster);
	}
	
	@Override
	public List<SubordinateListDtoInterface> getSubordinateList() throws MospException {
		// 部下一覧情報リスト取得
		return getSubordinateList(getHumanSubordinates(humanType, PlatformConst.WORKFLOW_TYPE_TIME));
	}
	
	/**
	 * 人事情報リストを基に、部下一覧情報リストを取得する。<br>
	 * <br>
	 * 検索条件(未承認、未承認)に合致する部下一覧情報のみ、リストに加える。<br>
	 * <br>
	 * @param humanList 人事情報リスト
	 * @return 部下一覧情報リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected List<SubordinateListDtoInterface> getSubordinateList(List<HumanDtoInterface> humanList)
			throws MospException {
		// 部下一覧リスト準備
		List<SubordinateListDtoInterface> subordinateList = new ArrayList<SubordinateListDtoInterface>();
		// 前日までフラグ(承認状態取得用)を取得
		boolean searchBeforeDay = approvalBeforeDay.equals(MospConst.CHECKBOX_ON);
		// 検索結果から部下一覧リストを作成
		for (HumanDtoInterface humanDto : humanList) {
			// 個人IDを取得
			String personalId = humanDto.getPersonalId();
			// 勤怠集計データを取得
			TotalTimeDataDtoInterface totalTimeDto = totalTimeDataDao.findForKey(personalId, targetYear, targetMonth);
			// 部下一覧情報DTOに設定
			SubordinateListDtoInterface dto = getSubordinateListDto(humanDto, targetYear, targetMonth, totalTimeDto,
					searchBeforeDay);
			// 部下一覧情報が検索条件に合致する場合
			if (isApprovalConditionMatch(dto) && isCalcConditionMatch(dto)) {
				// 部下一覧情報リストに追加
				subordinateList.add(dto);
			}
		}
		// 部下一覧情報リストを取得
		return subordinateList;
	}
	
	/**
	 * 承認状態表示名を取得する。<br>
	 * <br>
	 * @param isApprovableExist 未承認フラグ(true：未承認有、false：未承認無)
	 * @param isAppliableExist  未申請フラグ(true：勤怠未申請有、false：勤怠未申請無)
	 * @return 承認状態表示名
	 */
	protected String getApprovalStateName(boolean isApprovableExist, boolean isAppliableExist) {
		// 勤怠未申請有の場合(部下一覧等未申表示をする場合)
		if (isAppliableExist && isAppliableExistShow()) {
			// 未申
			return mospParams.getName("Ram", "Register");
		}
		// 未承認有の場合
		if (isApprovableExist) {
			// 有
			return PfNameUtility.exsistAbbr(mospParams);
		}
		// 無(未承認無で勤怠未申請無の場合)
		return PfNameUtility.notExsistAbbr(mospParams);
	}
	
	/**
	 * 承認状態表示クラスを取得する。<br>
	 * <br>
	 * @param isApprovableExist 未承認フラグ(true：未承認有、false：未承認無)
	 * @param isAppliableExist  未申請フラグ(true：勤怠未申請有、false：勤怠未申請無)
	 * @return 承認状態表示クラス
	 */
	protected String getApprovalStateClass(boolean isApprovableExist, boolean isAppliableExist) {
		// 勤怠未申請有か未承認有の場合
		if (isAppliableExist && isAppliableExistShow()) {
			// 赤文字
			return TimeConst.CSS_RED_LABEL;
		}
		// 未承認有の場合
		if (isApprovableExist) {
			// 赤文字
			return TimeConst.CSS_RED_LABEL;
		}
		// 青文字(未承認無で勤怠未申請無の場合)
		return TimeConst.CSS_BLUE_LABEL;
	}
	
	/**
	 * 部下一覧等未申表示をするかどうかを確認する。<br>
	 * <br>
	 * 一覧の未承認欄に「有り」と「無し」に加えて「未申」を
	 * 表示するかを確認する。<br>
	 * <br>
	 * @return 確認結果(true：未申表示をする、false：しない)
	 */
	protected boolean isAppliableExistShow() {
		return mospParams.getApplicationPropertyBool(TimeConst.APP_SHOW_APPLIABLE_EXIST);
	}
	
	/**
	 * 締日状態クラスを取得する。
	 * @param cutoffState 締日状態
	 * @return 締日状態クラス
	 */
	protected String getCutoffStateClass(int cutoffState) {
		// 締状態確認
		switch (cutoffState) {
			case TimeConst.CODE_CUTOFF_STATE_NOT_TIGHT:
				// 未締の場合(赤文字)
				return TimeConst.CSS_RED_LABEL;
			case TimeConst.CODE_CUTOFF_STATE_TEMP_TIGHT:
				// 仮締の場合(青文字)
				return TimeConst.CSS_BLUE_LABEL;
			case TimeConst.CODE_CUTOFF_STATE_TIGHTENED:
				// 確定の場合(黒文字)
				return TimeConst.CSS_BLACK_LABEL;
			default:
				return "";
		}
	}
	
	/**
	 * 部下一覧情報が未承認(検索条件)に合致するかを確認する。<br>
	 * <br>
	 * @param dto 対象部下一覧情報
	 * @return 確認結果(true：合致する、false：合致しない)
	 */
	protected boolean isApprovalConditionMatch(SubordinateListDtoInterface dto) {
		// 部下一覧情報が存在しない場合
		if (dto == null) {
			return false;
		}
		// 未承認(検索条件)が無い場合
		if (approval == null || approval.isEmpty()) {
			// 合致すると判断
			return true;
		}
		// 未承認(検索条件)を取得
		int condition = getInteger(approval);
		// 未承認(検索条件)が無しの場合
		if (condition == TimeConst.CODE_NOT_APPROVED_NONE) {
			// 部下一覧情報が未承認無であるかを確認
			return dto.isApprovableExist() == false;
		}
		// 未承認(検索条件)が有りの場合
		if (condition == TimeConst.CODE_NOT_APPROVED_EXIST) {
			// 部下一覧情報が未承認有であるかを確認
			return dto.isApprovableExist();
		}
		// 未承認(検索条件)が勤怠未申請有りの場合
		if (condition == TimeConst.CODE_NOT_TIME_APPROVED_EXIST) {
			// 部下一覧情報が未申請有であるかを確認
			return dto.isAppliableExist();
		}
		// 未承認(検索条件)が未承認・未申請有りの場合
		if (condition == TimeConst.CODE_NOT_CUTOFF_INFO_EXIST) {
			// 部下一覧情報が未承認有か未申請有であるかを確認
			return dto.isApprovableExist() || dto.isAppliableExist();
		}
		// 検索条件が何れでもない場合
		return false;
	}
	
	/**
	 * 部下一覧情報が締状態(検索条件)に合致するかを確認する。<br>
	 * <br>
	 * @param dto 対象部下一覧情報
	 * @return 確認結果(true：合致する、false：合致しない)
	 */
	protected boolean isCalcConditionMatch(SubordinateListDtoInterface dto) {
		// 部下一覧情報が存在しない場合
		if (dto == null) {
			return false;
		}
		// 締状態(検索条件)が無い場合
		if (calc == null || calc.isEmpty()) {
			// 合致すると判断
			return true;
		}
		//  部下一覧情報の締状態と締状態(検索条件)が合致するかを確認
		return calc.equals(String.valueOf(dto.getCutoffState()));
	}
	
	@Override
	public void setTargetYear(int targetYear) {
		this.targetYear = targetYear;
	}
	
	@Override
	public void setTargetMonth(int targetMonth) {
		this.targetMonth = targetMonth;
	}
	
	@Override
	public void setApproval(String approval) {
		this.approval = approval;
	}
	
	@Override
	public void setApprovalBeforeDay(String approvalBeforeDay) {
		this.approvalBeforeDay = approvalBeforeDay;
	}
	
	@Override
	public void setCalc(String calc) {
		this.calc = calc;
	}
	
	@Override
	public SubordinateListDtoInterface getSubordinateListDto(HumanDtoInterface humanDto, int year, int month,
			TotalTimeDataDtoInterface totalTimeDataDto, boolean searchBeforeDay) throws MospException {
		// 人事情報が取得できなかった場合
		if (humanDto == null) {
			return null;
		}
		// 設定適用エンティティを取得(年月指定時の基準日で)
		ApplicationEntity application = timeMaster.getApplicationEntity(humanDto, year, month);
		// 設定適用エンティティが無効である(必要な情報が揃っていない)場合
		if (application.isValid() == false) {
			return null;
		}
		// 締期間最終日取得
		Date cutoffLastDate = application.getCutoffEntity().getCutoffLastDate(year, month, mospParams);
		// 締最終日時点で入社していない又は締日最終日時点で設定適用がない場合
		if (!isEntered(humanDto.getPersonalId(), cutoffLastDate) || timeMaster
			.getApplication(getHumanInfo(humanDto.getPersonalId(), cutoffLastDate), cutoffLastDate) == null) {
			// 検索結果から除去
			return null;
		}
		// 部下一覧情報準備
		SubordinateListDtoInterface dto = new SubordinateListDto();
		// 対象年月を設定
		dto.setTargetYear(year);
		dto.setTargetMonth(month);
		// 部下一覧情報に人事情報を設定
		setHuman(dto, humanDto);
		// 部下一覧情報に勤怠集計データを設定
		setTotalTimeData(dto, totalTimeDataDto);
		// 部下一覧情報に承認状態を設定
		setApprovalState(dto, application.getCutoffDate(), searchBeforeDay);
		// 締状態設定
		setCutoffState(dto);
		// 勤怠修正情報設定
		setCorrection(dto);
		// 限度基準情報を設定
		setLimitStandard(dto, application.getWorkSettingCode());
		return dto;
	}
	
	@Override
	public void setHuman(SubordinateListDtoInterface dto, HumanDtoInterface humanDto) {
		if (humanDto == null) {
			return;
		}
		dto.setPersonalId(humanDto.getPersonalId());
		dto.setEmployeeCode(humanDto.getEmployeeCode());
		dto.setLastName(humanDto.getLastName());
		dto.setFirstName(humanDto.getFirstName());
		dto.setSectionCode(humanDto.getSectionCode());
	}
	
	/**
	 * 部下一覧情報に承認状態及び申請状態を設定する。<br>
	 * <br>
	 * @param dto             対象部下一覧情報
	 * @param cutoffDate      締日
	 * @param searchBeforeDay 前日までフラグ(承認状態取得用)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setApprovalState(SubordinateListDtoInterface dto, int cutoffDate, boolean searchBeforeDay)
			throws MospException {
		// 個人ID及び対象年月を取得
		String personalId = dto.getPersonalId();
		int targetYear = dto.getTargetYear();
		int targetMonth = dto.getTargetMonth();
		
		// 申請検出エンティティを取得
		RequestDetectEntityInterface entity = totalTimeEntityRefer.getRequestDetectEntity(personalId, targetYear,
				targetMonth, cutoffDate);
		
		// 前日までの場合
		if (searchBeforeDay) {
			// 対象期間をシステム日付前日までに設定
			entity.setBeforeDay(getSystemDate());
		}
		
		// 未承認フラグを取得
		boolean isApprovableExist = entity.isApprovableExist(mospParams, true);
		// 勤怠未申請フラグを取得
		boolean isAppliableExist = entity.isAppliableExist(true);
		// 承認状態設定
		dto.setApprovableExist(isApprovableExist);
		// 申請状況設定
		dto.setAppliableExist(isAppliableExist);
		// 承認状態表示名称設定
		dto.setApproval(getApprovalStateName(isApprovableExist, isAppliableExist));
		// 承認状態表示クラス設定
		dto.setApprovalStateClass(getApprovalStateClass(isApprovableExist, isAppliableExist));
	}
	
	/**
	 * 締状態を設定する。<br>
	 * @param dto 対象部下一覧情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setCutoffState(SubordinateListDtoInterface dto) throws MospException {
		// 個人ID及び対象年月を取得
		String personalId = dto.getPersonalId();
		int targetYear = dto.getTargetYear();
		int targetMonth = dto.getTargetMonth();
		// 締状態取得
		int cutoffState = getCutoffState(personalId, targetYear, targetMonth);
		// 締状態設定
		dto.setCutoffState(cutoffState);
		// 締状態表示名称設定
		dto.setCalc(getCodeName(cutoffState, TimeConst.CODE_CUTOFFSTATE));
		// 締状態表示クラス設定
		dto.setCutoffStateClass(getCutoffStateClass(cutoffState));
	}
	
	/**
	 * 勤怠修正情報を設定する。<br>
	 * @param dto 対象部下一覧情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setCorrection(SubordinateListDtoInterface dto) throws MospException {
		// 個人ID及び対象年月を取得
		String personalId = dto.getPersonalId();
		int targetYear = dto.getTargetYear();
		int targetMonth = dto.getTargetMonth();
		// 勤怠集計修正情報取得
		TotalTimeCorrectionDtoInterface totalTimeCorrectionDto = totalTimeCorrection
			.getLatestTotalTimeCorrectionInfo(personalId, targetYear, targetMonth);
		if (totalTimeCorrectionDto == null) {
			dto.setCorrection("");
			return;
		}
		if (totalTimeCorrectionDto.getCorrectionPersonalId().equals(dto.getPersonalId())) {
			dto.setCorrection(TimeNamingUtility.selfCorrectAbbr(mospParams));
			return;
		}
		dto.setCorrection(TimeNamingUtility.otherCorrectAbbr(mospParams));
	}
	
	@Override
	public void setTotalTimeData(SubordinateListDtoInterface dto, TotalTimeDataDtoInterface totalTimeDataDto)
			throws MospException {
		if (totalTimeDataDto == null) {
			return;
		}
		dto.setWorkDate(totalTimeDataDto.getTimesWorkDate());
		dto.setWorkTime(totalTimeDataDto.getWorkTime());
		dto.setRestTime(totalTimeDataDto.getRestTime());
		dto.setPrivateTime(totalTimeDataDto.getPrivateTime());
		dto.setLateTime(totalTimeDataDto.getLateTime());
		dto.setLeaveEarlyTime(totalTimeDataDto.getLeaveEarlyTime());
		dto.setLateLeaveEarlyTime(totalTimeDataDto.getLateTime() + totalTimeDataDto.getLeaveEarlyTime());
		dto.setOverTimeIn(totalTimeDataDto.getOvertimeIn());
		dto.setOverTimeOut(totalTimeDataDto.getOvertimeOut());
		dto.setWorkOnHolidayTime(totalTimeDataDto.getWorkOnHoliday());
		dto.setLateNightTime(totalTimeDataDto.getLateNight());
		dto.setPaidHoliday(totalTimeDataDto.getTimesPaidHoliday());
		dto.setPaidHolidayHour(totalTimeDataDto.getPaidHolidayHour());
		dto.setTotalSpecialHoliday(totalTimeDataDto.getTotalSpecialHoliday());
		dto.setTotalOtherHoliday(totalTimeDataDto.getTotalOtherHoliday());
		dto.setTimesCompensation(totalTimeDataDto.getTimesCompensation());
		dto.setAllHoliday(totalTimeDataDto.getTotalSpecialHoliday() + totalTimeDataDto.getTotalOtherHoliday()
				+ totalTimeDataDto.getTimesCompensation());
		dto.setAbsence(totalTimeDataDto.getTotalAbsence());
		dto.setTimesWork(totalTimeDataDto.getTimesWork());
		dto.setTimesLate(totalTimeDataDto.getTimesLate());
		dto.setTimesLeaveEarly(totalTimeDataDto.getTimesLeaveEarly());
		dto.setTimesOvertime(totalTimeDataDto.getTimesOvertime());
		dto.setTimesWorkingHoliday(totalTimeDataDto.getTimesWorkingHoliday());
		dto.setTimesLegalHoliday(totalTimeDataDto.getTimesLegalHoliday());
		dto.setTimesSpecificHoliday(totalTimeDataDto.getTimesSpecificHoliday());
		dto.setTimesHolidaySubstitute(totalTimeDataDto.getTimesHolidaySubstitute());
		// 追加業務ロジック処理
		doStoredLogic(TimeConst.CODE_KEY_ADD_SUBORDINATESEARCHBEAN_SETTOTALTIMEDATA, dto, totalTimeDataDto);
	}
	
	@Override
	public void setLimitStandard(SubordinateListDtoInterface dto, HumanDtoInterface humanDto) throws MospException {
		// 設定適用エンティティを取得(年月指定時の基準日で)
		ApplicationEntity applicationEntity = timeMaster.getApplicationEntity(humanDto, dto.getTargetYear(),
				dto.getTargetMonth());
		// 設定適用エンティティが無効である(必要な情報が揃っていない)場合
		if (applicationEntity.isValid() == false) {
			return;
		}
		// 限度基準情報を設定
		setLimitStandard(dto, applicationEntity.getWorkSettingCode());
	}
	
	/**
	 * 限度基準情報を設定する。<br>
	 * <br>
	 * @param dto             対象部下一覧情報
	 * @param workSettingCode 勤怠設定コード
	 */
	@SuppressWarnings("all")
	protected void setLimitStandard(SubordinateListDtoInterface dto, String workSettingCode) throws MospException {
		// 処理なし
	}
	
	/**
	 * 締状態を取得する。<br>
	 * @param personalId 個人ID
	 * @param targetYear 対象年
	 * @param targetMonth 対象月
	 * @return 締状態
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected int getCutoffState(String personalId, int targetYear, int targetMonth) throws MospException {
		// 社員勤怠集計管理情報取得
		TotalTimeEmployeeDtoInterface totalTimeEmployeeDto = totalTimeEmployeeTransaction.findForKey(personalId,
				targetYear, targetMonth);
		// 社員勤怠集計管理情報確認
		if (totalTimeEmployeeDto == null) {
			// 未締であると判断
			return TimeConst.CODE_CUTOFF_STATE_NOT_TIGHT;
		}
		// 締日コードを取得
		String cutoffCode = totalTimeEmployeeDto.getCutoffCode();
		// 勤怠集計管理情報から締状態を取得
		int cutoffState = totalTimeTransactionRefer.getStoredCutoffState(targetYear, targetMonth, cutoffCode);
		// 勤怠集計管理情報の締状態が確定である場合
		if (cutoffState == TimeConst.CODE_CUTOFF_STATE_TIGHTENED) {
			// 確定であると判断
			return TimeConst.CODE_CUTOFF_STATE_TIGHTENED;
		}
		// 社員勤怠集計管理情報から締状態を取得
		return totalTimeEmployeeDto.getCutoffState();
	}
	
}
