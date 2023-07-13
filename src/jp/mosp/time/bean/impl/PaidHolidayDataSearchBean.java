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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.bean.human.EntranceReferenceBeanInterface;
import jp.mosp.platform.bean.human.HumanSearchBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.time.base.TimeApplicationBean;
import jp.mosp.time.bean.AttendanceTransactionReferenceBeanInterface;
import jp.mosp.time.bean.PaidHolidayDataGrantBeanInterface;
import jp.mosp.time.bean.PaidHolidayDataReferenceBeanInterface;
import jp.mosp.time.bean.PaidHolidayDataSearchBeanInterface;
import jp.mosp.time.bean.PaidHolidayGrantReferenceBeanInterface;
import jp.mosp.time.bean.PaidHolidayGrantRegistBeanInterface;
import jp.mosp.time.bean.ScheduleUtilBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dto.settings.AttendanceTransactionDtoInterface;
import jp.mosp.time.dto.settings.PaidHolidayDataDtoInterface;
import jp.mosp.time.dto.settings.PaidHolidayDataGrantListDtoInterface;
import jp.mosp.time.dto.settings.PaidHolidayGrantDtoInterface;
import jp.mosp.time.dto.settings.impl.PaidHolidayDataGrantListDto;
import jp.mosp.time.utils.TimeUtility;

/**
 * 有給休暇データ検索クラス。
 */
public class PaidHolidayDataSearchBean extends TimeApplicationBean implements PaidHolidayDataSearchBeanInterface {
	
	/**
	 * 人事情報検索クラス。
	 */
	protected HumanSearchBeanInterface						humanSearch;
	
	/**
	 * 人事入社情報参照クラス。
	 */
	protected EntranceReferenceBeanInterface				entranceReference;
	
	/**
	 * 有給休暇付与参照クラス。
	 */
	protected PaidHolidayGrantReferenceBeanInterface		paidHolidayGrantReference;
	
	/**
	 * 有給休暇付与登録クラス。
	 */
	protected PaidHolidayGrantRegistBeanInterface			paidHolidayGrantRegist;
	
	/**
	 * 有給休暇データ参照クラス。
	 */
	protected PaidHolidayDataReferenceBeanInterface			paidHolidayDataReference;
	
	/**
	 * カレンダユーティリティ。<br>
	 */
	protected ScheduleUtilBeanInterface						scheduleUtil;
	
	/**
	 * 有給休暇データ付与クラス。
	 */
	protected PaidHolidayDataGrantBeanInterface				paidHolidayDataGrant;
	
	/**
	 * 勤怠トランザクション参照クラス。
	 */
	protected AttendanceTransactionReferenceBeanInterface	attendanceTransactionReference;
	
	/**
	 * 有効日。
	 */
	protected Date											activateDate;
	private Date											entranceFromDate;
	private Date											entranceToDate;
	private String											employeeCode;
	private String											employeeName;
	private String											workPlaceCode;
	private String											employmentCode;
	private String											sectionCode;
	private String											positionCode;
	private String											paidHolidayCode;
	private String											grant;
	/**
	 * 出勤率計算。
	 */
	protected boolean										calcAttendanceRate;
	private Set<String>										personalIdSet;
	
	/**
	 * 未計算。<br>
	 */
	public static final int									NOT_CALCULATED			= 1;
	
	/**
	 * 未付与(計算済)。<br>
	 */
	public static final int									NOT_GRANTED_BUT_CALC	= 2;
	
	/**
	 * 未付与(付与済以外)。<br>
	 */
	public static final int									NOT_GRANTED				= 0;
	
	/**
	 * 付与済。<br>
	 */
	public static final int									GRANTED					= 3;
	
	
	/**
	 * コンストラクタ。
	 */
	public PaidHolidayDataSearchBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		super.initBean();
		humanSearch = createBeanInstance(HumanSearchBeanInterface.class);
		entranceReference = createBeanInstance(EntranceReferenceBeanInterface.class);
		paidHolidayGrantReference = createBeanInstance(PaidHolidayGrantReferenceBeanInterface.class);
		paidHolidayGrantRegist = createBeanInstance(PaidHolidayGrantRegistBeanInterface.class);
		paidHolidayDataReference = createBeanInstance(PaidHolidayDataReferenceBeanInterface.class);
		scheduleUtil = createBeanInstance(ScheduleUtilBeanInterface.class);
		paidHolidayDataGrant = createBeanInstance(PaidHolidayDataGrantBeanInterface.class);
		attendanceTransactionReference = createBeanInstance(AttendanceTransactionReferenceBeanInterface.class);
	}
	
	@Override
	public List<PaidHolidayDataGrantListDtoInterface> getSearchList() throws MospException {
		// 有給休暇データ一覧準備
		List<PaidHolidayDataGrantListDtoInterface> list = new ArrayList<PaidHolidayDataGrantListDtoInterface>();
		// 人事マスタリストを取得
		List<HumanDtoInterface> humanList = getHumanList();
		// 人事マスタリスト毎に処理
		for (HumanDtoInterface humanDto : humanList) {
			PaidHolidayDataGrantListDtoInterface dto = getDto(humanDto);
			if (dto == null) {
				continue;
			}
			// 有給休暇データ一覧に追加
			list.add(dto);
			paidHolidayGrantRegist(dto);
		}
		return list;
	}
	
	/**
	 * 人事マスタリストを取得する。<br>
	 * @return 人事マスタリスト
	 * @throws MospException インスタンスの取得、SQLの作成及び実行に失敗した場合
	 */
	protected List<HumanDtoInterface> getHumanList() throws MospException {
		// 人事情報検索クラスに検索条件を設定
		humanSearch.setTargetDate(activateDate);
		humanSearch.setEmployeeCode(employeeCode);
		humanSearch.setEmployeeCodeType(PlatformConst.SEARCH_FORWARD_MATCH);
		humanSearch.setEmployeeName(employeeName);
		humanSearch.setWorkPlaceCode(workPlaceCode);
		humanSearch.setEmploymentContractCode(employmentCode);
		humanSearch.setPositionCode(positionCode);
		humanSearch.setSectionCode(sectionCode);
		humanSearch.setStateType(PlatformConst.EMPLOYEE_STATE_PRESENCE);
		return humanSearch.search();
	}
	
	/**
	 * 有給休暇データ一覧DTOを取得する。<br>
	 * @param dto 対象DTO
	 * @return 有給休暇データ一覧DTO
	 * @throws MospException インスタンスの取得、SQLの作成及び実行に失敗した場合
	 */
	protected PaidHolidayDataGrantListDtoInterface getDto(HumanDtoInterface dto) throws MospException {
		// 個人ID
		if (!searchPersonalId(dto)) {
			return null;
		}
		// 入社日
		if (!searchEntranceDate(dto)) {
			return null;
		}
		// 有給休暇情報を取得
		if (!hasPaidHolidaySettings(dto.getPersonalId(), activateDate)) {
			return null;
		}
		// 付与区分が対象外かどうか確認
		if (isPaidHolidayTypeNot()) {
			return null;
		}
		// 有給休暇確認
		if (!searchPaidHoliday()) {
			return null;
		}
		// 有給休暇付与回数を取得
		int grantTimes = paidHolidayDataGrant.getGrantTimes(dto.getPersonalId(), activateDate);
		// 有給休暇付与日を取得
		Date grantDate = paidHolidayDataGrant.getGrantDate(dto.getPersonalId(), activateDate, grantTimes);
		// 有給休暇データからレコードを取得
		PaidHolidayDataDtoInterface paidHolidayDataDto = paidHolidayDataReference.findForKey(dto.getPersonalId(),
				grantDate, grantDate);
		// 付与状態確認
		if (!searchGrant(paidHolidayDataDto, dto.getPersonalId(), grantDate)) {
			return null;
		}
		return getDto(dto, paidHolidayDataDto, grantDate, grantTimes);
	}
	
	/**
	 * 有給休暇データ一覧DTOを取得する。<br>
	 * @param dto 対象DTO
	 * @param paidHolidayDataDto 有給休暇データDTO
	 * @param grantDate 付与日
	 * @param grantTimes 有給休暇付与回数
	 * @return 有給休暇データ一覧DTO
	 * @throws MospException インスタンスの取得、SQLの作成及び実行に失敗した場合
	 */
	protected PaidHolidayDataGrantListDtoInterface getDto(HumanDtoInterface dto,
			PaidHolidayDataDtoInterface paidHolidayDataDto, Date grantDate, int grantTimes) throws MospException {
		// 有給休暇データ一覧情報準備
		PaidHolidayDataGrantListDtoInterface paidHolidayDataGrantListDto = new PaidHolidayDataGrantListDto();
		// 開始日を取得
		Date startDate = getStartDate(dto, grantTimes, grantDate);
		// 終了日を取得
		Date endDate = getEndDate(startDate, grantTimes);
		// 出勤率設定
		setAttendanceRate(paidHolidayDataGrantListDto, dto, grantDate, startDate, endDate);
		// 有給休暇データ一覧情報設定
		setDto(paidHolidayDataGrantListDto, dto, paidHolidayDataDto, grantDate, startDate, endDate);
		// 有給休暇データ一覧に追加
		return paidHolidayDataGrantListDto;
	}
	
	/**
	 * 個人ID判断。
	 * @param dto 対象DTO
	 * @return 検索条件に一致する場合true、そうでない場合false
	 */
	protected boolean searchPersonalId(HumanDtoInterface dto) {
		return searchPersonalId(dto.getPersonalId());
	}
	
	/**
	 * 個人ID判断。
	 * @param personalId 個人ID
	 * @return 検索条件に一致する場合true、そうでない場合false
	 */
	protected boolean searchPersonalId(String personalId) {
		if (personalIdSet == null) {
			return true;
		}
		return personalIdSet.contains(personalId);
	}
	
	/**
	 * 入社日判断。
	 * @param dto 対象DTO
	 * @return 検索条件に一致する場合true、そうでない場合false
	 * @throws MospException 例外発生時
	 */
	protected boolean searchEntranceDate(HumanDtoInterface dto) throws MospException {
		return searchEntranceDate(dto.getPersonalId());
	}
	
	/**
	 * 入社日判断。
	 * @param personalId 個人ID
	 * @return 検索条件に一致する場合true、そうでない場合false
	 * @throws MospException 例外発生時
	 */
	protected boolean searchEntranceDate(String personalId) throws MospException {
		// 入社日自がない場合
		if (entranceFromDate == null) {
			return true;
		}
		// 入社日取得
		Date date = entranceReference.getEntranceDate(personalId);
		if (date == null) {
			return false;
		}
		// 検索期間に含まれているか確認
		return DateUtility.isTermContain(date, entranceFromDate, entranceToDate);
	}
	
	/**
	 * 付与区分が対象外であるかを確認する。<br>
	 * @return 確認結果(true：対象外である、false：対象外でない)
	 */
	protected boolean isPaidHolidayTypeNot() {
		return paidHolidayDto.getPaidHolidayType() == TimeConst.CODE_PAID_HOLIDAY_TYPE_NOT;
	}
	
	/**
	 * 有給休暇設定判断。<br>
	 * @return 検索条件に一致する場合true、そうでない場合false
	 */
	protected boolean searchPaidHoliday() {
		if (paidHolidayCode.isEmpty()) {
			return true;
		}
		return paidHolidayCode.equals(paidHolidayDto.getPaidHolidayCode());
	}
	
	/**
	 * 出勤率計算フラグ判断。
	 * @param dto 対象DTO
	 * @param personalId 個人ID
	 * @param grantDate 付与日
	 * @return 検索条件に一致する場合true、そうでない場合false
	 * @throws MospException 例外発生時
	 */
	protected boolean searchGrant(PaidHolidayDataDtoInterface dto, String personalId, Date grantDate)
			throws MospException {
		// 付与状態が指定されていない場合
		if (MospUtility.isEmpty(grant)) {
			// 検索条件に一致すると判断
			return true;
		}
		// 有給休暇付与情報を取得
		PaidHolidayGrantDtoInterface paidHolidayGrantDto = paidHolidayGrantReference.findForKey(personalId, grantDate);
		// 付与状態(検索条件)を取得
		int intGrant = MospUtility.getInt(grant);
		// 付与状態(検索条件)が未計算の場合
		if (intGrant == NOT_CALCULATED) {
			// 付与状態が未計算であるかを確認
			return isNotCalclated(dto, paidHolidayGrantDto);
		}
		// 付与状態が(検索条件)未付与(計算済)の場合
		if (intGrant == NOT_GRANTED_BUT_CALC) {
			// 付与状態が未付与(計算済)であるかを確認
			return isNotGrantedButCalc(dto, paidHolidayGrantDto);
		}
		// 付与状態が(検索条件)未付与の場合
		if (intGrant == NOT_GRANTED) {
			// 付与状態が未付与であるかを確認
			return isNotGranted(dto, paidHolidayGrantDto);
		}
		// 付与状態(検索条件)が付与済の場合
		if (intGrant == GRANTED) {
			// 付与状態が付与済であるかを確認
			return isGranted(dto, paidHolidayGrantDto);
		}
		return false;
	}
	
	/**
	 * 付与状態が未計算であるかを確認する。<br>
	 * @param dto      有給休暇情報
	 * @param grantDto 有給休暇付与情報
	 * @return 確認結果(true：付与状態が未計算である、false：そうでない)
	 */
	protected boolean isNotCalclated(PaidHolidayDataDtoInterface dto, PaidHolidayGrantDtoInterface grantDto) {
		// 有給休暇情報がある場合
		if (dto != null) {
			// 付与状態が未計算でないと判断
			return false;
		}
		// 有給休暇付与情報が無い場合
		if (grantDto == null) {
			// 付与状態が未計算であると判断
			return true;
		}
		// 有給休暇付与情報の付与状態が未計算(1)であるかどうかを確認
		return grantDto.getGrantStatus() == NOT_CALCULATED;
	}
	
	/**
	 * 付与状態が未付与(計算済)であるかを確認する。<br>
	 * @param dto      有給休暇情報
	 * @param grantDto 有給休暇付与情報
	 * @return 確認結果(true：付与状態が未付与(計算済)である、false：そうでない)
	 */
	protected boolean isNotGrantedButCalc(PaidHolidayDataDtoInterface dto, PaidHolidayGrantDtoInterface grantDto) {
		// 有給休暇情報がある場合
		if (dto != null) {
			// 付与状態が未付与(計算済)でないと判断
			return false;
		}
		// 有給休暇付与情報が無い場合
		if (grantDto == null) {
			// 付与状態が未付与(計算済)でないと判断
			return false;
		}
		// 有給休暇付与情報の付与状態が未付与(2)であるかどうかを確認
		return grantDto.getGrantStatus() == NOT_GRANTED_BUT_CALC;
	}
	
	/**
	 * 付与状態が付与済であるかを確認する。<br>
	 * @param dto      有給休暇情報
	 * @param grantDto 有給休暇付与情報
	 * @return 確認結果(true：付与状態が付与済である、false：そうでない)
	 */
	protected boolean isGranted(PaidHolidayDataDtoInterface dto, PaidHolidayGrantDtoInterface grantDto) {
		// 有給休暇情報がある場合
		if (dto != null) {
			// 付与状態が付与済であると判断
			return true;
		}
		// 有給休暇付与情報が無い場合
		if (grantDto == null) {
			// 付与状態が付与済でないと判断
			return false;
		}
		// 有給休暇付与情報の付与状態が付与済であるかどうかを確認
		return grantDto.getGrantStatus() == GRANTED;
	}
	
	/**
	 * 付与状態が未付与であるかを確認する。<br>
	 * @param dto      有給休暇情報
	 * @param grantDto 有給休暇付与情報
	 * @return 確認結果(true：付与状態が未付与である、false：そうでない)
	 */
	protected boolean isNotGranted(PaidHolidayDataDtoInterface dto, PaidHolidayGrantDtoInterface grantDto) {
		// 付与状態が付与済でないかを確認
		return isGranted(dto, grantDto) == false;
	}
	
	/**
	 * DTOに値を設定する。
	 * @param dto 対象DTO
	 * @param humanDto 人事マスタDTO
	 * @param paidHolidayDataDto 有給休暇データDTO
	 * @param grantDate 付与日付
	 * @param firstDate 開始日付
	 * @param lastDate 終了日付
	 */
	protected void setDto(PaidHolidayDataGrantListDtoInterface dto, HumanDtoInterface humanDto,
			PaidHolidayDataDtoInterface paidHolidayDataDto, Date grantDate, Date firstDate, Date lastDate) {
		dto.setPersonalId(humanDto.getPersonalId());
		dto.setEmployeeCode(humanDto.getEmployeeCode());
		dto.setLastName(humanDto.getLastName());
		dto.setFirstName(humanDto.getFirstName());
		dto.setGrantDate(grantDate);
		dto.setFirstDate(firstDate);
		dto.setLastDate(lastDate);
		dto.setAccomplish(mospParams.getName("Ram", "Accomplish"));
		if (isAccomplished(dto.getAttendanceRate())) {
			// 達成の場合
			dto.setAccomplish(mospParams.getName("Accomplish"));
		}
		if (grantDate == null) {
			dto.setAccomplish(mospParams.getName("Hyphen"));
		}
		if (dto.getAttendanceRate() == null) {
			dto.setAccomplish(mospParams.getName("Hyphen"));
		}
		if (!dto.getError().isEmpty()) {
			dto.setAccomplish(mospParams.getName("Hyphen"));
		}
		dto.setGrant(mospParams.getName("Ram", "Giving"));
		dto.setActivateDate(null);
		dto.setGrantDays(null);
		if (paidHolidayDataDto != null) {
			// 付与済の場合
			dto.setGrant(mospParams.getName("Giving", "Finish"));
			dto.setActivateDate(paidHolidayDataDto.getActivateDate());
			dto.setGrantDays(paidHolidayDataDto.getHoldDay());
		}
	}
	
	/**
	 * 出勤率を達成しているかどうかを確認する。<br>
	 * @param attendanceRate 出勤率
	 * @return 達成している場合true、そうでない場合false
	 */
	protected boolean isAccomplished(Double attendanceRate) {
		if (paidHolidayDto.getWorkRatio() <= 0) {
			// 有給休暇設定の出勤率が0以下である場合
			return true;
		}
		// 有給休暇設定の出勤率が0以下でない場合
		if (attendanceRate == null) {
			return false;
		}
		return Double.compare(attendanceRate.doubleValue() * 100, paidHolidayDto.getWorkRatio()) >= 0;
	}
	
	/**
	 * 有給休暇付与登録処理を行う。
	 * @param dto 対象DTO
	 * @throws MospException 例外発生時
	 */
	protected void paidHolidayGrantRegist(PaidHolidayDataGrantListDtoInterface dto) throws MospException {
		if (dto.getAttendanceRate() == null) {
			return;
		}
		// 出勤率が計算されている場合
		paidHolidayGrantRegist(dto.getPersonalId(), dto.getGrantDate());
	}
	
	/**
	 * 有給休暇付与登録処理を行う。
	 * @param personalId 個人ID
	 * @param grantDate 付与日
	 * @throws MospException 例外発生時
	 */
	protected void paidHolidayGrantRegist(String personalId, Date grantDate) throws MospException {
		PaidHolidayGrantDtoInterface dto = paidHolidayGrantReference.findForKey(personalId, grantDate);
		if (dto != null && dto.getGrantStatus() == GRANTED) {
			// 付与済の場合
			return;
		}
		if (dto == null) {
			dto = paidHolidayGrantRegist.getInitDto();
			dto.setPersonalId(personalId);
			dto.setGrantDate(grantDate);
		}
		dto.setGrantStatus(NOT_GRANTED_BUT_CALC);
		paidHolidayGrantRegist.regist(dto);
	}
	
	/**
	 * 開始日を取得する。<br>
	 * @param dto 対象DTO
	 * @param grantTimes 有給休暇付与回数
	 * @param grantDate 付与日
	 * @return 開始日
	 * @throws MospException 例外発生時
	 */
	protected Date getStartDate(HumanDtoInterface dto, int grantTimes, Date grantDate) throws MospException {
		return getStartDate(dto.getPersonalId(), grantTimes, grantDate);
	}
	
	@Override
	public Date getStartDate(String personalId, int grantTimes, Date grantDate) throws MospException {
		Date entranceDate = entranceReference.getEntranceDate(personalId);
		if (grantTimes <= 0) {
			// 0以下の場合
			return null;
		} else if (grantTimes == 1) {
			// 1の場合は入社日とする
			return entranceDate;
		}
		// 1より大きい場合
		Date startDate = paidHolidayDataGrant.getGrantDate(personalId, activateDate, grantTimes - 1, entranceDate);
		if (grantTimes >= 3) {
			// 3以上の場合
			return startDate;
		}
		// 3より小さい場合
		if (startDate != null) {
			return startDate;
		}
		Date targetDate = DateUtility.addYear(grantDate, -1);
		if (targetDate.after(entranceDate)) {
			// 入社日より後の場合
			return targetDate;
		}
		// 入社日以前の場合
		return entranceDate;
	}
	
	@Override
	public Date getEndDate(Date startDate, int grantTimes) {
		if (startDate == null) {
			return null;
		}
		if (grantTimes <= 0) {
			// 0以下の場合
			return null;
		} else if (grantTimes == 1) {
			// 1の場合は6ヶ月後の前日とする
			return addDay(DateUtility.addMonth(startDate, 6), -1);
		}
		// 1より大きい場合は1年後の前日とする
		return addDay(DateUtility.addYear(startDate, 1), -1);
	}
	
	/**
	 * 出勤率を設定する。
	 * @param dto 対象DTO
	 * @param humanDto 人事マスタDTO
	 * @param grantDate 付与日
	 * @param startDate 開始日
	 * @param endDate 終了日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setAttendanceRate(PaidHolidayDataGrantListDtoInterface dto, HumanDtoInterface humanDto,
			Date grantDate, Date startDate, Date endDate) throws MospException {
		setAttendanceRate(dto, humanDto.getPersonalId(), grantDate, startDate, endDate);
	}
	
	/**
	 * 出勤率を設定する。
	 * @param dto 対象DTO
	 * @param personalId 個人ID
	 * @param grantDate 付与日
	 * @param startDate 開始日
	 * @param endDate 終了日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setAttendanceRate(PaidHolidayDataGrantListDtoInterface dto, String personalId, Date grantDate,
			Date startDate, Date endDate) throws MospException {
		dto.setWorkDays(null);
		dto.setTotalWorkDays(null);
		dto.setAttendanceRate(null);
		dto.setError("");
		if (startDate == null) {
			return;
		}
		if (!calcAttendanceRate) {
			return;
		}
		int attendanceDays = 0;
		int totalWorkDays = 0;
		Date firstDate = startDate;
		Date lastDate = addDay(grantDate, -1);
		Set<Long> set = attendanceTransactionReference.findForMilliseconds(personalId, firstDate, lastDate);
		if (getNumberOfDays(firstDate, lastDate) != set.size()) {
			// 日数がセットの数と一致しない場合
			int milliseconds = 1000 * 60 * TimeConst.CODE_DEFINITION_HOUR * TimeConst.TIME_DAY_ALL_HOUR;
			long firstTime = firstDate.getTime();
			long lastTime = lastDate.getTime();
			for (long i = firstTime; i <= lastTime; i += milliseconds) {
				if (set.contains(i)) {
					continue;
				}
				Date date = new Date(i);
				
				// カレンダに登録されている勤務形態コードを取得
				String workTypeCode = scheduleUtil.getScheduledWorkTypeCodeNoMessage(personalId, date);
				// カレンダに登録されている勤務形態コードを取得できなかった場合
				if (MospUtility.isEmpty(workTypeCode)) {
					// 次の日へ
					continue;
				}
				// カレンダに登録されている勤務形態コードが所定休日又は法定休日である場合
				if (TimeUtility.isHoliday(workTypeCode)) {
					// 次の日へ
					continue;
				}
				totalWorkDays++;
			}
		}
		// 勤怠トランザクションの和を取得
		AttendanceTransactionDtoInterface attendanceTransactionDto = attendanceTransactionReference.sum(personalId,
				firstDate, lastDate);
		// 勤怠トランザクションの和がある場合
		if (attendanceTransactionDto != null) {
			attendanceDays += attendanceTransactionDto.getNumerator();
			totalWorkDays += attendanceTransactionDto.getDenominator();
		}
		// 付与日から算定期間末日までの日数
		int days = getNumberOfDays(grantDate, endDate);
		attendanceDays += days;
		totalWorkDays += days;
		dto.setWorkDays(attendanceDays);
		dto.setTotalWorkDays(totalWorkDays);
		dto.setAttendanceRate(getAttendanceRate(attendanceDays, totalWorkDays));
	}
	
	/**
	 * 出勤率を取得する。
	 * @param workDays 労働日数
	 * @param totalWorkDays 全労働日数
	 * @return 出勤率
	 */
	protected double getAttendanceRate(int workDays, int totalWorkDays) {
		if (totalWorkDays <= 0) {
			// 全労働日数が0以下の場合
			return 0;
		}
		BigDecimal dividend = new BigDecimal(workDays);
		BigDecimal divisor = new BigDecimal(totalWorkDays);
		BigDecimal quotient = dividend.divide(divisor, 3, RoundingMode.FLOOR);
		return quotient.doubleValue();
	}
	
	/**
	 * 日数を取得する。<br>
	 * @param firstDate 初日
	 * @param lastDate 末日
	 * @return 日数
	 */
	protected int getNumberOfDays(Date firstDate, Date lastDate) {
		long difference = lastDate.getTime() - firstDate.getTime();
		if (difference < 0) {
			// 0より小さい場合
			return 0;
		}
		long quotient = difference / (1000 * 60 * TimeConst.CODE_DEFINITION_HOUR * TimeConst.TIME_DAY_ALL_HOUR);
		return (int)quotient + 1;
	}
	
	@Override
	public void setActivateDate(Date activateDate) {
		this.activateDate = getDateClone(activateDate);
	}
	
	@Override
	public void setEntranceFromDate(Date entranceFromDate) {
		this.entranceFromDate = getDateClone(entranceFromDate);
	}
	
	@Override
	public void setEntranceToDate(Date entranceToDate) {
		this.entranceToDate = getDateClone(entranceToDate);
	}
	
	@Override
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}
	
	@Override
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	
	@Override
	public void setWorkPlaceCode(String workPlaceCode) {
		this.workPlaceCode = workPlaceCode;
	}
	
	@Override
	public void setEmploymentCode(String employmentCode) {
		this.employmentCode = employmentCode;
	}
	
	@Override
	public void setSectionCode(String sectionCode) {
		this.sectionCode = sectionCode;
	}
	
	@Override
	public void setPositionCode(String positionCode) {
		this.positionCode = positionCode;
	}
	
	@Override
	public void setPaidHolidayCode(String paidHolidayCode) {
		this.paidHolidayCode = paidHolidayCode;
	}
	
	@Override
	public void setGrant(String grant) {
		this.grant = grant;
	}
	
	@Override
	public void setCalcAttendanceRate(boolean calcAttendanceRate) {
		this.calcAttendanceRate = calcAttendanceRate;
	}
	
	@Override
	public void setPersonalIdSet(Set<String> personalIdSet) {
		this.personalIdSet = personalIdSet;
	}
	
}
