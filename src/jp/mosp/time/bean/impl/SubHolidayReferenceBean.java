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
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.framework.utils.NameUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.workflow.WorkflowIntegrateBeanInterface;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;
import jp.mosp.platform.utils.PlatformUtility;
import jp.mosp.platform.utils.WorkflowUtility;
import jp.mosp.time.bean.SubHolidayReferenceBeanInterface;
import jp.mosp.time.bean.TimeMasterBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dao.settings.AttendanceDaoInterface;
import jp.mosp.time.dao.settings.SubHolidayDaoInterface;
import jp.mosp.time.dao.settings.SubHolidayRequestDaoInterface;
import jp.mosp.time.dto.settings.AttendanceDtoInterface;
import jp.mosp.time.dto.settings.SubHolidayDtoInterface;
import jp.mosp.time.dto.settings.SubHolidayRequestDtoInterface;
import jp.mosp.time.entity.TimeSettingEntityInterface;
import jp.mosp.time.utils.TimeNamingUtility;
import jp.mosp.time.utils.TimeRequestUtility;

/**
 * 代休情報参照処理。<br>
 */
public class SubHolidayReferenceBean extends PlatformBean implements SubHolidayReferenceBeanInterface {
	
	/**
	 * 代休データDAO。
	 */
	protected SubHolidayDaoInterface			dao;
	
	/**
	 * 代休申請データDAO。
	 */
	protected SubHolidayRequestDaoInterface		subHolidayRequestDao;
	
	/**
	 * 勤怠データDAO。
	 */
	protected AttendanceDaoInterface			attendanceDao;
	
	/**
	 * ワークフロー統括クラス。
	 */
	protected WorkflowIntegrateBeanInterface	workflowIntegrate;
	
	/**
	 * 勤怠関連マスタ参照処理。<br>
	 */
	protected TimeMasterBeanInterface			timeMaster;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public SubHolidayReferenceBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// DAOとBeanを準備
		dao = createDaoInstance(SubHolidayDaoInterface.class);
		subHolidayRequestDao = createDaoInstance(SubHolidayRequestDaoInterface.class);
		attendanceDao = createDaoInstance(AttendanceDaoInterface.class);
		workflowIntegrate = createBeanInstance(WorkflowIntegrateBeanInterface.class);
		timeMaster = createBeanInstance(TimeMasterBeanInterface.class);
	}
	
	@Override
	public String[][] getSelectArray(String personalId, Date targetDate, String holidayRange,
			SubHolidayRequestDtoInterface dto) throws MospException {
		// 申請予定代休日数を取得
		float appliDays = TimeRequestUtility.getDays(MospUtility.getInt(holidayRange));
		// 代休申請日時点で有効な代休情報リストを取得
		List<SubHolidayDtoInterface> subHolidayList = getFindForList(personalId, targetDate, targetDate, appliDays);
		// リスト準備
		List<SubHolidayDtoInterface> list = new ArrayList<SubHolidayDtoInterface>();
		List<String> holidayRangeList = new ArrayList<String>();
		// 期間最終日時点で有効な代休データ毎に処理
		for (SubHolidayDtoInterface subHolidayDto : subHolidayList) {
			// 代休申請日数準備
			float count = 0;
			// 代休申請データリスト
			List<SubHolidayRequestDtoInterface> subHolidayRequestList = subHolidayRequestDao.findForList(personalId,
					subHolidayDto.getWorkDate(), subHolidayDto.getTimesWork(), subHolidayDto.getSubHolidayType());
			// 代休申請情報毎に処理
			for (SubHolidayRequestDtoInterface subHolidayRequestDto : subHolidayRequestList) {
				// 代休申請ワークフロ情報取得
				WorkflowDtoInterface subHolidayRequestWorkflowDto = workflowIntegrate
					.getLatestWorkflowInfo(subHolidayRequestDto.getWorkflow());
				// 申請済でない場合
				if (!WorkflowUtility.isApplied(subHolidayRequestWorkflowDto)) {
					continue;
				}
				// 対象代休申請データが同じ場合
				if (dto != null
						&& subHolidayRequestDto.getTmdSubHolidayRequestId() == dto.getTmdSubHolidayRequestId()) {
					continue;
				}
				// 代休申請日数に設定
				count += TimeRequestUtility.getDays(subHolidayRequestDto);
			}
			// 保有代休残日数（代休日数-代休申請(下書又は取下)）取得
			double notUseDay = subHolidayDto.getSubHolidayDays() - count;
			// 利用数より申請数の方が多い場合
			if (notUseDay < appliDays) {
				continue;
			}
			// リスト追加
			list.add(subHolidayDto);
			if (notUseDay >= TimeConst.CODE_HOLIDAY_RANGE_ALL) {
				// 残りが全休の場合
				holidayRangeList.add(TimeNamingUtility.holidayRangeAllWithCornerParentheses(mospParams));
			} else if (notUseDay >= TimeConst.HOLIDAY_TIMES_HALF) {
				// 残りが半休の場合
				holidayRangeList.add(TimeNamingUtility.holidayHalfWithCornerParentheses(mospParams));
			} else {
				// 対象データ無し
				return getNoObjectDataPulldown();
			}
		}
		// 対象データが無い場合
		if (list.size() != holidayRangeList.size()) {
			return getNoObjectDataPulldown();
		}
		// プルダウン配列準備
		String[][] array = prepareSelectArray(list.size(), false);
		int i = 0;
		// 残代休情報毎に処理
		for (SubHolidayDtoInterface subHolidayDto : list) {
			array[i][0] = new StringBuilder().append(getStringDate(subHolidayDto.getWorkDate())).append(SEPARATOR_DATA)
				.append(subHolidayDto.getSubHolidayType()).toString();
			StringBuilder sb = new StringBuilder();
			// 代休種別略称を取得
			String typeAbbr = TimeRequestUtility.getSubHolidayTypeAbbr(subHolidayDto.getSubHolidayType(), mospParams);
			sb.append(NameUtility.cornerParentheses(mospParams, typeAbbr));
			sb.append(getStringDate(subHolidayDto.getWorkDate()));
			sb.append(holidayRangeList.get(i));
			array[i][1] = sb.toString();
			i++;
		}
		return array;
	}
	
	@Override
	public List<SubHolidayDtoInterface> getFindForList(String personalId, Date startDate, Date endDate,
			double subHolidayDays) throws MospException {
		// 勤怠設定エンティティを取得
		TimeSettingEntityInterface timeSetting = timeMaster.getTimeSettingForPersonalId(personalId, startDate);
		// 代休取得開始日を取得
		Date subHolidayStartDate = DateUtility.addMonthAndDay(startDate, -timeSetting.getSubHolidayLimitMonth(),
				-timeSetting.getSubHolidayLimitDay());
		// （対象日-対象日時点の取得期限）～対象日までの代休情報リストを取得
		List<SubHolidayDtoInterface> list = getSubHolidayList(personalId, subHolidayStartDate, endDate, subHolidayDays);
		// 各代休毎の休日出勤日時点での取得期限を確認し、期間最終日より前の場合省く
		List<SubHolidayDtoInterface> list2 = new ArrayList<SubHolidayDtoInterface>();
		// 代休申請日時点で有効な代休データ毎に処理
		for (SubHolidayDtoInterface subHolidayDto : list) {
			// 代休勤務日時点の勤怠設定エンティティを取得
			TimeSettingEntityInterface timeSettingEntity = timeMaster.getTimeSettingForPersonalId(personalId,
					subHolidayDto.getWorkDate());
			// // 代休勤務日時点の勤怠設定から期限日取得
			Date subHolidayLimitDate = timeSettingEntity.getSubHolidayLimitDate(subHolidayDto);
			// 代休勤務日時点の代休期限日が休日出勤日より前の場合
			if (subHolidayLimitDate.compareTo(startDate) < 0) {
				continue;
			}
			// リスト追加
			list2.add(subHolidayDto);
		}
		// 残代休リスト準備
		List<SubHolidayDtoInterface> list3 = new ArrayList<SubHolidayDtoInterface>();
		// 代休データ毎に代休申請を確認し、利用している分省く
		for (SubHolidayDtoInterface subHolidayDto : list2) {
			// 代休申請日数を準備
			double count = 0;
			// 代休申請データリスト
			List<SubHolidayRequestDtoInterface> subHolidayRequestList = subHolidayRequestDao.findForList(personalId,
					subHolidayDto.getWorkDate(), subHolidayDto.getTimesWork(), subHolidayDto.getSubHolidayType());
			// 代休申請情報毎に処理
			for (SubHolidayRequestDtoInterface subHolidayRequestDto : subHolidayRequestList) {
				// 代休申請ワークフロ情報取得
				WorkflowDtoInterface subHolidayRequestWorkflowDto = workflowIntegrate
					.getLatestWorkflowInfo(subHolidayRequestDto.getWorkflow());
				// 申請していない場合
				if (WorkflowUtility.isApplied(subHolidayRequestWorkflowDto) == false) {
					continue;
				}
				// 代休申請日数に設定
				count += TimeRequestUtility.getDays(subHolidayRequestDto);
			}
			// 保有代休残日数（代休日数-代休申請(申請済)）取得
			double notUseDay = subHolidayDto.getSubHolidayDays() - count;
			// 利用数より申請数の方が多い場合
			if (notUseDay <= 0) {
				continue;
			}
			list3.add(subHolidayDto);
		}
		return list3;
	}
	
	@Override
	public List<SubHolidayDtoInterface> getSubHolidayList(String personalId, Date startDate, Date endDate,
			double subHolidayDays) throws MospException {
		// リスト準備
		List<SubHolidayDtoInterface> list = new ArrayList<SubHolidayDtoInterface>();
		// 代休データ毎に処理
		for (SubHolidayDtoInterface dto : dao.getSubHolidayList(personalId, startDate, endDate, subHolidayDays)) {
			// 勤怠情報が承認済でない場合
			if (isAttendanceCompleted(dto.getPersonalId(), dto.getWorkDate(), dto.getTimesWork()) == false) {
				continue;
			}
			// リスト追加
			list.add(dto);
		}
		return list;
	}
	
	@Override
	public Map<String, Map<Integer, List<SubHolidayDtoInterface>>> getSubHolidays(Collection<String> personalIds,
			Date workDate) throws MospException {
		// 代休情報群(キー：個人ID及び代休種別)を準備
		Map<String, Map<Integer, List<SubHolidayDtoInterface>>> map = new TreeMap<String, Map<Integer, List<SubHolidayDtoInterface>>>();
		// 代休情報リストを取得(勤怠申請が承認済でない情報を含む)
		List<SubHolidayDtoInterface> list = dao.findForPersonalIds(personalIds, workDate);
		// 代休情報群(キー：個人ID)に変換
		Map<String, Set<SubHolidayDtoInterface>> subHolidays = PlatformUtility.getPersonalIdMap(list);
		// 代休情報群毎に処理
		for (Entry<String, Set<SubHolidayDtoInterface>> entry : subHolidays.entrySet()) {
			// 個人ID及び代休情報群を取得
			String personalId = entry.getKey();
			Set<SubHolidayDtoInterface> dtos = entry.getValue();
			// 代休情報群(キー：個人ID及び代休種別)に設定
			map.put(personalId, getSubHolidayMap(dtos));
		}
		// 代休情報群(キー：個人ID及び代休種別)を取得
		return map;
	}
	
	@Override
	public Map<Integer, List<SubHolidayDtoInterface>> getSubHolidays(String personalId, Date firstDate, Date lastDate)
			throws MospException {
		// 代休情報リストを取得(勤怠申請が承認済でない情報を含む)
		List<SubHolidayDtoInterface> dtos = dao.findSubHolidayList(personalId, firstDate, lastDate);
		// 代休情報群(キー：代休種別)を取得
		return getSubHolidayMap(dtos);
	}
	
	/**
	 * 代休情報群(キー：代休種別)を取得する。<br>
	 * @param dtos 代休情報群 
	 * @return 代休情報群(キー：代休種別)
	 * @throws MospException オブジェクトの生成に失敗した場合
	 */
	protected Map<Integer, List<SubHolidayDtoInterface>> getSubHolidayMap(Collection<SubHolidayDtoInterface> dtos)
			throws MospException {
		// 代休情報群(キー：代休種別)を準備
		Map<Integer, List<SubHolidayDtoInterface>> subHolidayMap = new TreeMap<Integer, List<SubHolidayDtoInterface>>();
		// 代休情報毎に処理
		for (SubHolidayDtoInterface dto : dtos) {
			// マップからキーで値(List)を取得
			List<SubHolidayDtoInterface> value = MospUtility.getListValue(subHolidayMap, dto.getSubHolidayType());
			// 代休情報を設定
			value.add(dto);
		}
		// 代休情報群(キー：代休種別)を取得
		return subHolidayMap;
	}
	
	@Override
	public Float[] getBirthSubHolidayTimes(String personalId, Date startDate, Date endDate) throws MospException {
		// 代休発生回数準備
		Float[] days = { null, null, null };
		double prescribed = 0.0;
		double legal = 0.0;
		double midnight = 0.0;
		
		// 代休データリストを取得
		List<SubHolidayDtoInterface> list = dao.findSubHolidayList(personalId, startDate, endDate);
		// 代休データリスト毎に処理
		for (SubHolidayDtoInterface dto : list) {
			// 承認済勤怠情報がない場合
			if (isAttendanceCompleted(personalId, dto.getWorkDate(), dto.getTimesWork()) == false) {
				continue;
			}
			// 所定代休
			if (dto.getSubHolidayType() == TimeConst.CODE_PRESCRIBED_SUBHOLIDAY_CODE) {
				prescribed += dto.getSubHolidayDays();
				continue;
			}
			// 法定代休
			if (dto.getSubHolidayType() == TimeConst.CODE_LEGAL_SUBHOLIDAY_CODE) {
				legal += dto.getSubHolidayDays();
				continue;
			}
			// 深夜代休の場合
			if (dto.getSubHolidayType() == TimeConst.CODE_MIDNIGHT_SUBHOLIDAY_CODE) {
				midnight += dto.getSubHolidayDays();
				continue;
			}
		}
		days[0] = (float)prescribed;
		days[1] = (float)legal;
		days[2] = (float)midnight;
		return days;
	}
	
	/**
	 * 対象日の勤怠情報が承認済か確認する。<br>
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @param timesWork  勤務回数
	 * @return 確認結果（true：対象日の勤怠情報が承認済である、false：承認済でない）
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected boolean isAttendanceCompleted(String personalId, Date targetDate, int timesWork) throws MospException {
		// 勤怠データを取得
		AttendanceDtoInterface attendanceDto = attendanceDao.findForKey(personalId, targetDate, timesWork);
		// 勤怠データを取得できなかった場合
		if (MospUtility.isEmpty(attendanceDto)) {
			// 承認済でないと判断
			return false;
		}
		// 対象ワークフローが承認済であるかを確認
		return workflowIntegrate.isCompleted(attendanceDto.getWorkflow());
	}
	
}
