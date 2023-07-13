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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.utils.MonthUtility;
import jp.mosp.time.bean.RequestUtilBeanInterface;
import jp.mosp.time.bean.ScheduleReferenceBeanInterface;
import jp.mosp.time.bean.ScheduleRegistBeanInterface;
import jp.mosp.time.bean.ScheduleUtilAddonBeanInterface;
import jp.mosp.time.bean.ScheduleUtilBeanInterface;
import jp.mosp.time.bean.TimeMasterBeanInterface;
import jp.mosp.time.dao.settings.ScheduleDateDaoInterface;
import jp.mosp.time.dto.settings.ApplicationDtoInterface;
import jp.mosp.time.dto.settings.DifferenceRequestDtoInterface;
import jp.mosp.time.dto.settings.ScheduleDateDtoInterface;
import jp.mosp.time.dto.settings.ScheduleDtoInterface;
import jp.mosp.time.dto.settings.SubstituteDtoInterface;
import jp.mosp.time.dto.settings.WorkOnHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.WorkTypeChangeRequestDtoInterface;
import jp.mosp.time.utils.TimeMessageUtility;
import jp.mosp.time.utils.TimeUtility;

/**
 * カレンダユーティリティ処理。<br>
 */
public class ScheduleUtilBean extends ScheduleUtilBaseBean implements ScheduleUtilBeanInterface {
	
	/**
	 * カレンダ日情報DAO。<br>
	 */
	protected ScheduleDateDaoInterface							scheduleDateDao;
	
	/**
	 * カレンダ管理参照クラス。<br>
	 */
	protected ScheduleReferenceBeanInterface					scheduleReference;
	
	/**
	 * カレンダ管理登録処理。<br>
	 * DTOの取得にのみ用いる。<br>
	 */
	protected ScheduleRegistBeanInterface						scheduleRegist;
	
	/**
	 * カレンダ日情報群(キー：カレンダコード)。<br>
	 */
	protected Map<String, Map<Date, ScheduleDateDtoInterface>>	scheduleMap;
	
	/**
	 * カレンダユーティリティ追加処理リスト。<br>
	 */
	protected List<ScheduleUtilAddonBeanInterface>				addonBeans;
	
	/**
	 * コードキー(カレンダユーティリティ追加処理)。<br>
	 */
	protected static final String								CODE_KEY_ADDONS	= "ScheduleUtilBeanAddons";
	
	
	/**
	 * {@link ScheduleUtilBean}を生成する。<br>
	 */
	public ScheduleUtilBean() {
		// 継承基のコンストラクタを実行
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// 継承基のメソッドを実行
		super.initBean();
		// DAOを準備
		scheduleDateDao = createDaoInstance(ScheduleDateDaoInterface.class);
		// 各種クラスの取得
		scheduleReference = createBeanInstance(ScheduleReferenceBean.class);
		scheduleRegist = createBeanInstance(ScheduleRegistBeanInterface.class);
		// フィールドの初期化
		scheduleMap = new HashMap<String, Map<Date, ScheduleDateDtoInterface>>();
	}
	
	@Override
	public ScheduleDtoInterface getSchedule(String personalId, Date targetDate) throws MospException {
		// カレンダコードを取得
		String scheduleCode = getScheduleCode(personalId, targetDate, true);
		// カレンダ情報(特殊カレンダ用)を準備
		ScheduleDtoInterface scheduleDto = getAddonScheduleDto(scheduleCode, targetDate);
		// 通常のカレンダを利用する場合
		if (isNormalSchedule(personalId, targetDate)) {
			// カレンダ情報を取得
			scheduleDto = scheduleReference.getScheduleInfo(scheduleCode, targetDate);
		}
		// カレンダ情報が取得できなかった場合
		if (scheduleDto == null) {
			// エラーメッセージを追加
			TimeMessageUtility.addErrorScheduleDefect(mospParams, targetDate);
			// 処理終了
			return null;
		}
		// カレンダユーティリティ追加処理毎に処理
		for (ScheduleUtilAddonBeanInterface addonBean : getAddonBeans()) {
			// 追加処理を実行
			scheduleDto = addonBean.getSchedule(scheduleDto, personalId, targetDate);
			// 追加処理に失敗した場合
			if (mospParams.hasErrorMessage()) {
				// nullを取得
				return null;
			}
		}
		// カレンダ情報を取得
		return scheduleDto;
	}
	
	@Override
	public ScheduleDateDtoInterface getScheduleDate(String personalId, Date targetDate) throws MospException {
		// カレンダ日情報を取得
		return getScheduleDate(personalId, targetDate, true);
	}
	
	@Override
	public ScheduleDateDtoInterface getScheduleDateNoMessage(String personalId, Date targetDate) throws MospException {
		// カレンダ日情報を取得
		return getScheduleDate(personalId, targetDate, false);
	}
	
	@Override
	public Map<Date, ScheduleDateDtoInterface> getScheduleDates(String personalId, Date startDate, Date endDate)
			throws MospException {
		// カレンダ日情報群を準備
		Map<Date, ScheduleDateDtoInterface> scheduleDates = new TreeMap<Date, ScheduleDateDtoInterface>();
		// 通常のカレンダを利用する場合
		if (isNormalSchedule(personalId, endDate)) {
			// カレンダ(通常のカレンダ)に登録されているカレンダ日情報群を取得
			scheduleDates.putAll(getScheduleDatesNormal(personalId, startDate, endDate));
		}
		// カレンダユーティリティ追加処理毎に処理
		for (ScheduleUtilAddonBeanInterface addonBean : getAddonBeans()) {
			// 追加処理の該当処理を実行(カレンダ日情報リストを取得)
			scheduleDates.putAll(addonBean.getScheduleDates(scheduleDates, personalId, startDate, endDate));
		}
		// カレンダ日情報群を取得
		return scheduleDates;
	}
	
	@Override
	public String getScheduledWorkTypeCode(String personalId, Date targetDate) throws MospException {
		// 勤務形態コードを取得
		return getWorkTypeCode(personalId, targetDate, true);
	}
	
	@Override
	public String getScheduledWorkTypeCodeNoMessage(String personalId, Date targetDate) throws MospException {
		// 勤務形態コードを取得
		return getWorkTypeCode(personalId, targetDate, false);
	}
	
	@Override
	public Map<Date, String> getScheduledWorkTypeCodes(String personalId, Date startDate, Date endDate)
			throws MospException {
		// 勤務形態コード群を準備
		Map<Date, String> scheduleMap = new HashMap<Date, String>();
		// カレンダ日情報群を取得(カレンダ日情報群に保持)
		getScheduleDates(personalId, startDate, endDate);
		// 対象日毎に処理
		for (Date targetDate : TimeUtility.getDateList(startDate, endDate)) {
			// 予定勤務形態コードを設定
			scheduleMap.put(targetDate, getWorkTypeCode(personalId, targetDate, false));
		}
		// 勤務形態コード群を取得
		return scheduleMap;
	}
	
	@Override
	public String getScheduledWorkTypeCode(String personalId, Date targetDate, RequestUtilBeanInterface requestUtil)
			throws MospException {
		// 勤務形態コードを準備
		String workTypeCode = MospConst.STR_EMPTY;
		// 通常のカレンダを利用する場合
		if (isNormalSchedule(personalId, targetDate)) {
			// 勤務形態コード(通常のカレンダ)を取得
			workTypeCode = getScheduledWorkTypeCodeNormal(personalId, targetDate, requestUtil);
		}
		// カレンダユーティリティ追加処理毎に処理
		for (ScheduleUtilAddonBeanInterface addonBean : getAddonBeans()) {
			// 追加処理の該当処理を実行(勤務形態コードを取得)
			workTypeCode = addonBean.getScheduledWorkTypeCode(workTypeCode, personalId, targetDate, requestUtil);
			// 追加処理に失敗した場合
			if (mospParams.hasErrorMessage()) {
				// 空文字を取得
				return MospConst.STR_EMPTY;
			}
		}
		// 勤務形態コードを取得
		return workTypeCode;
	}
	
	@Override
	public String getScheduledWorkTypeCode(String personalId, Date targetDate, boolean useRequest)
			throws MospException {
		// 申請が不要な場合
		if (useRequest == false) {
			// 勤務形態コードを取得(エラーメッセージ要)
			return getWorkTypeCode(personalId, targetDate, true);
		}
		// 申請ユーティリティを準備
		RequestUtilBeanInterface requestUtil = createBeanInstance(RequestUtilBeanInterface.class);
		// 申請ユーティリティに各種申請を設定
		requestUtil.setRequests(personalId, targetDate);
		// 勤務形態コードを取得
		return getScheduledWorkTypeCode(personalId, targetDate, requestUtil);
	}
	
	@Override
	public Map<Date, ScheduleDateDtoInterface> getSubstitutedSchedules(String personalId,
			Map<Date, Date> substituteDates) throws MospException {
		// 振り替えられたカレンダ日情報群(キー：勤務日)を準備
		Map<Date, ScheduleDateDtoInterface> substitutedSchedules = new TreeMap<Date, ScheduleDateDtoInterface>();
		// 振替日毎に処理
		for (Entry<Date, Date> entry : substituteDates.entrySet()) {
			// 出勤日(勤務日)と振替日を取得
			Date workDate = entry.getKey();
			Date substituteDate = entry.getValue();
			// DBからカレンダ日情報を取得
			substitutedSchedules.put(workDate, getScheduleDateNoMessage(personalId, substituteDate));
		}
		// 振り替えられたカレンダ日情報群(キー：勤務日)を取得
		return substitutedSchedules;
	}
	
	@Override
	public String getScheduleName(String scheduleCode, Date targetDate) throws MospException {
		// カレンダ名称を取得
		String name = scheduleReference.getScheduleName(scheduleCode, targetDate);
		// カレンダユーティリティ追加処理毎に処理
		for (ScheduleUtilAddonBeanInterface addonBean : getAddonBeans()) {
			// 追加処理の該当処理を実行(カレンダ名称を取得)
			name = addonBean.getScheduleName(name, scheduleCode, targetDate);
			// 追加処理に失敗した場合
			if (mospParams.hasErrorMessage()) {
				// カレンダコードを取得
				return scheduleCode;
			}
		}
		// カレンダ名称を取得
		return name;
	}
	
	@Override
	public String getScheduleAbbr(String scheduleCode, Date targetDate) throws MospException {
		// カレンダ略称を取得
		String abbr = scheduleReference.getScheduleAbbr(scheduleCode, targetDate);
		// カレンダユーティリティ追加処理毎に処理
		for (ScheduleUtilAddonBeanInterface addonBean : getAddonBeans()) {
			// 追加処理の該当処理を実行(カレンダ略称を取得)
			abbr = addonBean.getScheduleAbbr(abbr, scheduleCode, targetDate);
			// 追加処理に失敗した場合
			if (mospParams.hasErrorMessage()) {
				// カレンダコードを取得
				return scheduleCode;
			}
		}
		// カレンダ略称を取得
		return abbr;
	}
	
	@Override
	public String[][] getCodedSelectArray(Date targetDate, boolean needBlank) throws MospException {
		// プルダウン用配列を取得
		String[][] selecrArrray = scheduleReference.getCodedSelectArray(targetDate, needBlank);
		// カレンダユーティリティ追加処理毎に処理
		for (ScheduleUtilAddonBeanInterface addonBean : getAddonBeans()) {
			// 追加処理の該当処理を実行(プルダウン用配列を取得)
			selecrArrray = addonBean.getCodedSelectArray(selecrArrray, targetDate, needBlank);
			// 追加処理に失敗した場合
			if (mospParams.hasErrorMessage()) {
				// 空の配列を取得
				return new String[0][];
			}
		}
		// プルダウン用配列を取得
		return selecrArrray;
	}
	
	@Override
	public String[][] getSelectArray(Date targetDate, boolean needBlank) throws MospException {
		// プルダウン用配列を取得
		String[][] selecrArrray = scheduleReference.getSelectArray(targetDate, needBlank);
		// 追加処理
		for (ScheduleUtilAddonBeanInterface addonBean : getAddonBeans()) {
			// 追加処理の該当処理を実行(プルダウン用配列を取得)
			selecrArrray = addonBean.getSelectArray(selecrArrray, targetDate, needBlank);
			// 追加処理に失敗した場合
			if (mospParams.hasErrorMessage()) {
				// 空の配列を取得
				return new String[0][];
			}
		}
		// プルダウン用配列を取得
		return selecrArrray;
	}
	
	/**
	 * カレンダ(通常のカレンダ)に登録されている勤務形態コードを取得する。<br>
	 * 勤務形態に影響を及ぼす申請(振替休日、振出・休出申請、勤務形態変更申請、時差出勤申請)を考慮する。<br>
	 * (申請ユーティリティはこれらの申請を取得するためのみに用いる。)<br>
	 * 勤務形態コードの取得に失敗した場合、エラーメッセージを設定し、空文字を返す。<br>
	 * <br>
	 * @param personalId  個人ID
	 * @param targetDate  対象日
	 * @param requestUtil 申請ユーティリティ
	 * @return 勤務形態コード
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected String getScheduledWorkTypeCodeNormal(String personalId, Date targetDate,
			RequestUtilBeanInterface requestUtil) throws MospException {
		// 申請ユーティリティから勤務形態に影響を及ぼすの申請(承認済)を取得
		DifferenceRequestDtoInterface difference = requestUtil.getDifferenceDto(true);
		WorkTypeChangeRequestDtoInterface workTypeChange = requestUtil.getWorkTypeChangeDto(true);
		WorkOnHolidayRequestDtoInterface workOnHoliday = requestUtil.getWorkOnHolidayDto(true);
		List<SubstituteDtoInterface> substitutes = requestUtil.getSubstituteList(true);
		// 各種申請から勤務形態コードを取得
		String workTypeCode = getRequestedWorkTypeCode(difference, workTypeChange, workOnHoliday, substitutes);
		// 各種申請から勤務形態コードを取得できた場合
		if (MospUtility.isEmpty(workTypeCode) == false) {
			// 各種申請から勤務形態コードを取得
			return workTypeCode;
		}
		// カレンダ日情報を取得するための対象日を取得
		Date schedlueTargetDate = getScheduleTargetDate(targetDate, workOnHoliday);
		// カレンダ(通常のカレンダ)に登録されている勤務形態コードを取得
		return getScheduledWorkTypeCodeNormal(personalId, schedlueTargetDate, false);
	}
	
	/**
	 * カレンダ日情報を取得する。<br>
	 * @param personalId           個人ID
	 * @param targetDate           対象日
	 * @param isErrorMessageNeeded エラーメッセージ要否(true：エラーメッセージ要、false：不要)
	 * @return カレンダ日情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected ScheduleDateDtoInterface getScheduleDate(String personalId, Date targetDate, boolean isErrorMessageNeeded)
			throws MospException {
		// カレンダ日情報を準備
		ScheduleDateDtoInterface dto = null;
		// 通常のカレンダを利用する場合
		if (isNormalSchedule(personalId, targetDate)) {
			// カレンダ日情報(通常のカレンダ)を取得
			dto = getScheduleDateNormal(personalId, targetDate, isErrorMessageNeeded);
		}
		// カレンダユーティリティ追加処理毎に処理
		for (ScheduleUtilAddonBeanInterface addonBean : getAddonBeans()) {
			// 追加処理の該当処理を実行(カレンダ日情報を取得)
			dto = addonBean.getScheduleDate(dto, personalId, targetDate, isErrorMessageNeeded);
		}
		// カレンダ日情報を取得
		return dto;
	}
	
	/**
	 * カレンダ日情報(通常のカレンダ)を取得する。<br>
	 * カレンダ日情報を取得できなかった場合はnullを返す。<br>
	 * @param personalId           個人ID
	 * @param targetDate           対象日
	 * @param isErrorMessageNeeded エラーメッセージ要否(true：エラーメッセージ要、false：不要)
	 * @return カレンダ日情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected ScheduleDateDtoInterface getScheduleDateNormal(String personalId, Date targetDate,
			boolean isErrorMessageNeeded) throws MospException {
		// カレンダコードを取得
		String scheduleCode = getScheduleCode(personalId, targetDate, isErrorMessageNeeded);
		// カレンダ日情報群に保持されたカレンダ日情報を取得
		ScheduleDateDtoInterface dto = getStoredScheduleDate(scheduleCode, targetDate);
		// カレンダ日情報群に保持されたカレンダ日情報を取得できた場合
		if (dto != null) {
			// カレンダ日情報を取得
			return dto;
		}
		// DBからカレンダ日情報を取得
		dto = scheduleDateDao.findForKey(scheduleCode, targetDate);
		// カレンダ日情報を取得できなかった場合(エラーメッセージが必要である場合)
		if (dto == null && isErrorMessageNeeded) {
			// エラーメッセージを設定
			TimeMessageUtility.addErrorScheduleDefect(mospParams, targetDate);
		}
		// カレンダ日情報をカレンダ日情報群(キー：カレンダコード)に設定
		putScheduleDate(dto);
		// カレンダ日情報を取得
		return dto;
	}
	
	/**
	 * カレンダ(通常のカレンダ)に登録されているカレンダ日情報群を取得する。<br>
	 * カレンダ日情報リストの取得に失敗した場合でもエラーメッセージは設定せず、空リのストを返す。<br>
	 * @param personalId 個人ID
	 * @param startDate  期間開始日
	 * @param endDate    期間終了日
	 * @return カレンダ日情報群(キー：日)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected Map<Date, ScheduleDateDtoInterface> getScheduleDatesNormal(String personalId, Date startDate,
			Date endDate) throws MospException {
		// カレンダ日情報群を準備
		Map<Date, ScheduleDateDtoInterface> scheduleDates = new TreeMap<Date, ScheduleDateDtoInterface>();
		// 設定適用情報群を取得
		Map<Date, ApplicationDtoInterface> applications = timeMaster.getApplicationMap(personalId, startDate, endDate);
		// 期間中の日毎に処理
		for (Date targetDate : TimeUtility.getDateList(startDate, endDate)) {
			// 設定適用情報を取得
			ApplicationDtoInterface application = applications.get(targetDate);
			// 設定適用情報を取得できなかった場合
			if (application == null) {
				// 次の日へ
				continue;
			}
			// カレンダコードを取得
			String scheduleCode = application.getScheduleCode();
			// 保持カレンダ日情報群に保持されたカレンダ日情報を取得
			ScheduleDateDtoInterface scheduleDate = getStoredScheduleDate(scheduleCode, targetDate);
			// 保持カレンダ日情報群に保持されたカレンダ日情報を取得できた場合
			if (scheduleDate != null) {
				// カレンダ日情報群に追加
				scheduleDates.put(scheduleDate.getScheduleDate(), scheduleDate);
				// 次の日へ
				continue;
			}
			// 保持カレンダ日情報群にDBから取得したカレンダ日情報リストを設定
			putScheduleDates(scheduleCode, startDate, endDate);
			// 保持カレンダ日情報群に保持されたカレンダ日情報リストを再取得
			scheduleDate = getStoredScheduleDate(scheduleCode, targetDate);
			// 保持カレンダ日情報群に保持されたカレンダ日情報リストを取得できなかった場合
			if (scheduleDate == null) {
				// 次の日へ
				continue;
			}
			// カレンダ日情報群に追加
			scheduleDates.put(scheduleDate.getScheduleDate(), scheduleDate);
		}
		// カレンダ日情報群を取得
		return scheduleDates;
	}
	
	/**
	 * カレンダ日情報群に保持されたカレンダ日情報を取得する。<br>
	 * カレンダ日情報群に保持されていない場合はnullを返す。<br>
	 * @param scheduleCode カレンダコード
	 * @param targetDate   対象日
	 * @return カレンダ日情報
	 */
	protected ScheduleDateDtoInterface getStoredScheduleDate(String scheduleCode, Date targetDate) {
		// カレンダ日情報群(キー：カレンダコード)からカレンダ日情報郡(キー：日)を取得
		Map<Date, ScheduleDateDtoInterface> map = scheduleMap.get(scheduleCode);
		// カレンダ日情報郡(キー：日)が取得できなかった場合
		if (map == null) {
			// nullを取得
			return null;
		}
		// カレンダ日情報郡(キー：日)からカレンダ日情報を取得
		return map.get(targetDate);
	}
	
	/**
	 * 保持カレンダ日情報群にDBから取得したカレンダ日情報リストを設定する。<br>
	 * @param scheduleCode カレンダコード
	 * @param startDate    期間開始日
	 * @param endDate      期間終了日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void putScheduleDates(String scheduleCode, Date startDate, Date endDate) throws MospException {
		// DBからカレンダ日情報リストを取得(リストに期間中の全ての日が含まれることは保証しない)
		List<ScheduleDateDtoInterface> list = scheduleDateDao.findForList(scheduleCode, startDate, endDate);
		// カレンダ日情報毎に処理
		for (ScheduleDateDtoInterface dto : list) {
			// 保持カレンダ日情報群にカレンダ日情報を設定
			putScheduleDate(dto);
		}
	}
	
	/**
	 * 保持カレンダ日情報群にカレンダ日情報を設定する。<br>
	 * @param dto カレンダ日情報
	 */
	protected void putScheduleDate(ScheduleDateDtoInterface dto) {
		// カレンダ日情が存在しない場合
		if (dto == null) {
			// 処理無し
			return;
		}
		// カレンダコード及び日を取得
		String scheduleCode = dto.getScheduleCode();
		Date scheduleDate = dto.getScheduleDate();
		// カレンダ日情報群(キー：カレンダコード)からカレンダ日情報郡(キー：日)を取得
		Map<Date, ScheduleDateDtoInterface> map = scheduleMap.get(scheduleCode);
		// カレンダ日情報郡(キー：日)が取得できなかった場合
		if (map == null) {
			// カレンダ日情報郡(キー：日)を準備しカレンダ日情報群(キー：カレンダコード)に設定
			map = new TreeMap<Date, ScheduleDateDtoInterface>();
			scheduleMap.put(scheduleCode, map);
		}
		// カレンダ日情報郡(キー：日)にカレンダ日情報を設定
		map.put(scheduleDate, dto);
	}
	
	/**
	 * 勤務形態コードを取得する。<br>
	 * @param personalId           個人ID
	 * @param targetDate           対象日
	 * @param isErrorMessageNeeded エラーメッセージ要否(true：エラーメッセージ要、false：不要)
	 * @return 勤務形態コード
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected String getWorkTypeCode(String personalId, Date targetDate, boolean isErrorMessageNeeded)
			throws MospException {
		// 勤務形態コードを準備
		String workTypeCode = MospConst.STR_EMPTY;
		// 通常のカレンダを利用する場合
		if (isNormalSchedule(personalId, targetDate)) {
			// 勤務形態コード(通常のカレンダ)を取得
			workTypeCode = getScheduledWorkTypeCodeNormal(personalId, targetDate, isErrorMessageNeeded);
		}
		// カレンダユーティリティ追加処理毎に処理
		for (ScheduleUtilAddonBeanInterface addonBean : getAddonBeans()) {
			// 追加処理の該当処理を実行(勤務形態コードを取得)
			workTypeCode = addonBean.getScheduledWorkTypeCode(workTypeCode, personalId, targetDate,
					isErrorMessageNeeded);
		}
		// 勤務形態コードを取得
		return workTypeCode;
	}
	
	/**
	 * カレンダ(通常のカレンダ)に登録されている勤務形態コードを取得する。<br>
	 * 勤務形態コードを取得できなかった場合はnullを返す。<br>
	 * @param personalId           個人ID
	 * @param targetDate           対象日
	 * @param isErrorMessageNeeded エラーメッセージ要否(true：エラーメッセージ要、false：不要)
	 * @return 勤務形態コード
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected String getScheduledWorkTypeCodeNormal(String personalId, Date targetDate, boolean isErrorMessageNeeded)
			throws MospException {
		// カレンダ日情報(通常のカレンダ)を取得
		ScheduleDateDtoInterface dto = getScheduleDateNormal(personalId, targetDate, isErrorMessageNeeded);
		// カレンダ日情報を取得できなかった場合
		if (dto == null) {
			// 処理終了
			return MospConst.STR_EMPTY;
		}
		// 勤務形態コードを取得
		return dto.getWorkTypeCode();
	}
	
	/**
	 * 通常のカレンダを利用するか否かを確認する。<br>
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @return 確認結果(true：通常のカレンダを利用する、false：特殊カレンダを利用する)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected boolean isNormalSchedule(String personalId, Date targetDate) throws MospException {
		// カレンダコードを取得
		String scheduleCode = getScheduleCode(personalId, targetDate, false);
		// 通常のカレンダを利用するか否かを確認
		return MospUtility.isEmpty(scheduleCode) || scheduleCode.startsWith(CODE_PREFIX_ADDON_SCHEDULE) == false;
	}
	
	/**
	 * 特殊カレンダ用のカレンダ情報を取得する。<br>
	 * @param scheduleCode カレンダコード
	 * @param targetDate   対象日
	 * @return 特殊カレンダ用のカレンダ情報
	 * @throws MospException 日付操作に失敗した場合
	 */
	protected ScheduleDtoInterface getAddonScheduleDto(String scheduleCode, Date targetDate) throws MospException {
		// 対象日付が含まれる年度を取得
		int targetYear = MonthUtility.getFiscalYear(targetDate, mospParams);
		// 年を表す日付(年度の初日)を取得
		Date targetYearDate = MonthUtility.getYearDate(targetYear, mospParams);
		// カレンダ情報を準備
		ScheduleDtoInterface scheduleDto = scheduleRegist.getInitDto();
		// カレンダコード
		scheduleDto.setScheduleCode(scheduleCode);
		// 有効日
		scheduleDto.setActivateDate(targetYearDate);
		// カレンダ名称
		scheduleDto.setScheduleName(MospConst.STR_EMPTY);
		// カレンダ略称
		scheduleDto.setScheduleAbbr(MospConst.STR_EMPTY);
		// 年度
		scheduleDto.setFiscalYear(targetYear);
		// パターンコード
		scheduleDto.setPatternCode(MospConst.STR_EMPTY);
		// 勤務形態変更フラグ
		scheduleDto.setWorkTypeChangeFlag(MospConst.DELETE_FLAG_OFF);
		// カレンダ情報を取得
		return scheduleDto;
	}
	
	/**
	 * カレンダユーティリティ追加処理リストを取得する。<br>
	 * @return カレンダユーティリティ追加処理リスト
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	protected List<ScheduleUtilAddonBeanInterface> getAddonBeans() throws MospException {
		// カレンダユーティリティ追加処理リストがnullでない場合
		if (addonBeans != null) {
			// カレンダユーティリティ追加処理リストを取得
			return addonBeans;
		}
		// 追加処理リストを準備
		List<ScheduleUtilAddonBeanInterface> addonBeans = new ArrayList<ScheduleUtilAddonBeanInterface>();
		// 追加処理設定配列毎に処理
		for (String[] addon : mospParams.getProperties().getCodeArray(CODE_KEY_ADDONS, false)) {
			// 追加処理クラス名を取得
			String modelClass = addon[0];
			// 追加処理クラス名が設定されていない場合
			if (MospUtility.isEmpty(modelClass)) {
				// 次の追加処理設定へ
				continue;
			}
			// 追加処理を取得
			ScheduleUtilAddonBeanInterface addonBean = createBean(ScheduleUtilAddonBeanInterface.class, modelClass);
			// 勤怠関連マスタ参照処理及び設定適用情報郡(キー：個人ID、勤務日)を設定
			addonBean.setTimeMaster(timeMaster);
			// 追加処理リストに追加
			addonBeans.add(addonBean);
		}
		// 追加処理リストを設定
		this.addonBeans = addonBeans;
		// 追加処理リストを取得
		return addonBeans;
	}
	
	@Override
	public void setTimeMaster(TimeMasterBeanInterface timeMaster) {
		this.timeMaster = timeMaster;
	}
	
}
