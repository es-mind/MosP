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
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.utils.PlatformUtility;
import jp.mosp.time.bean.ScheduleUtilBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.constant.TimeFileConst;
import jp.mosp.time.dao.settings.AttendanceDaoInterface;
import jp.mosp.time.dto.settings.AttendanceDtoInterface;

/**
 * 勤怠再申請対象者エクスポート処理。<br>
 */
public class AttendanceReapplicationExportBean extends TimeBaseExportBean {
	
	/**
	 * 勤怠データDAO。<br>
	 */
	protected AttendanceDaoInterface	attendanceDao;
	
	/**
	 * カレンダユーティリティ。<br>
	 */
	protected ScheduleUtilBeanInterface	scheduleUtil;
	
	
	/**
	 * {@link TimeBaseExportBean#TimeBaseExportBean()}を実行する。<br>
	 */
	public AttendanceReapplicationExportBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// 継承基のメソッドを実行
		super.initBean();
		// DAOを準備
		attendanceDao = createDaoInstance(AttendanceDaoInterface.class);
		// Beanを準備
		scheduleUtil = createBeanInstance(ScheduleUtilBeanInterface.class);
		scheduleUtil.setTimeMaster(timeMaster);
	}
	
	@Override
	protected List<String[]> makeCsvDataList(List<String> fieldList, Date firstDate, Date lastDate, String cutoffCode,
			String workPlaceCode, String employmentContractCode, String sectionCode, boolean needLowerSection,
			String positionCode) throws MospException {
		// CSVデータリストを準備
		List<String[]> csvDataList = new ArrayList<String[]>();
		// 人事情報群を取得
		List<HumanDtoInterface> humanList = getHumanList(lastDate, cutoffCode, workPlaceCode, employmentContractCode,
				sectionCode, needLowerSection, positionCode);
		// 人事情報群(キー：個人ID)を取得
		Map<String, HumanDtoInterface> humans = PlatformUtility.getPersonalIdDtoMap(humanList);
		// 出力対象となる勤怠情報リストを取得
		List<AttendanceDtoInterface> dtos = findAttendanceList(humanList, firstDate, lastDate);
		// 勤怠情報毎に処理
		for (AttendanceDtoInterface dto : dtos) {
			// 勤怠再申請対象者CSVデータを取得しCSVデータリストに設定
			csvDataList.add(makeCsvData(dto, humans.get(dto.getPersonalId()), fieldList, lastDate));
		}
		// CSVデータリストを取得
		return csvDataList;
	}
	
	/**
	 * 勤怠再申請対象者CSVデータを取得する。<br>
	 * @param dto        勤怠情報
	 * @param humanDto   人事情報
	 * @param fieldList  エクスポートフィールド名称リスト(フィールド順序昇順)
	 * @param targetDate 対象日
	 * @return 勤怠再申請対象者CSVデータ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected String[] makeCsvData(AttendanceDtoInterface dto, HumanDtoInterface humanDto, List<String> fieldList,
			Date targetDate) throws MospException {
		// 勤怠再申請対象者CSVデータを準備
		String[] csvData = getHumanCsvData(fieldList, humanDto, targetDate);
		// CSVデータに勤務日を設定
		setCsvValue(csvData, fieldList, TimeFileConst.FIELD_WORK_DATE, DateUtility.getStringDate(dto.getWorkDate()));
		// 勤怠再申請対象者CSVデータを取得
		return csvData;
	}
	
	/**
	 * 勤怠データリストを取得する。<br>
	 * @param list 人事マスタリスト
	 * @param firstDate 初日
	 * @param lastDate 末日
	 * @return 勤怠データリスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合s
	 */
	protected List<AttendanceDtoInterface> findAttendanceList(List<HumanDtoInterface> list, Date firstDate,
			Date lastDate) throws MospException {
		List<AttendanceDtoInterface> attendanceList = new ArrayList<AttendanceDtoInterface>();
		for (HumanDtoInterface dto : list) {
			List<AttendanceDtoInterface> attendanceDtoList = findAttendanceList(dto.getPersonalId(), firstDate,
					lastDate);
			if (!attendanceDtoList.isEmpty()) {
				attendanceList.addAll(attendanceDtoList);
			}
		}
		return attendanceList;
	}
	
	/**
	 * 勤怠データリストを取得する。<br>
	 * @param personalId 個人ID
	 * @param firstDate 初日
	 * @param lastDate 末日
	 * @return 勤怠データリスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected List<AttendanceDtoInterface> findAttendanceList(String personalId, Date firstDate, Date lastDate)
			throws MospException {
		List<AttendanceDtoInterface> list = new ArrayList<AttendanceDtoInterface>();
		for (AttendanceDtoInterface dto : attendanceDao.findForReapplicationExport(personalId, firstDate, lastDate)) {
			if (isReapplicationTarget(dto)) {
				// 再申請対象の場合
				list.add(dto);
			}
		}
		return list;
	}
	
	/**
	 * 翌日の勤務形態コードを取得する。<br>
	 * @param dto 対象DTO
	 * @return 翌日の勤務形態コード
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected String getTomorrowWorkTypeCode(AttendanceDtoInterface dto) throws MospException {
		return getWorkTypeCode(dto.getPersonalId(), addDay(dto.getWorkDate(), 1));
	}
	
	/**
	 * 勤務形態コードを取得する。<br>
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @return 勤務形態コード
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected String getWorkTypeCode(String personalId, Date targetDate) throws MospException {
		return scheduleUtil.getScheduledWorkTypeCode(personalId, targetDate, true);
	}
	
	/**
	 * 再申請対象判断。<br>
	 * @param dto 対象DTO
	 * @return 再申請対象の場合true、そうでない場合false
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected boolean isReapplicationTarget(AttendanceDtoInterface dto) throws MospException {
		return isReapplicationTarget(dto, getTomorrowWorkTypeCode(dto));
	}
	
	/**
	 * 再申請対象判断。<br>
	 * @param dto 対象DTO
	 * @param tomorrowWorkTypeCode 翌日勤務形態コード
	 * @return 再申請対象の場合true、そうでない場合false
	 */
	protected boolean isReapplicationTarget(AttendanceDtoInterface dto, String tomorrowWorkTypeCode) {
		return (dto.getLegalWorkTime() > 0 && !TimeConst.CODE_WORK_ON_LEGAL_HOLIDAY.equals(dto.getWorkTypeCode())
				&& !TimeConst.CODE_HOLIDAY_LEGAL_HOLIDAY.equals(tomorrowWorkTypeCode)
				&& !TimeConst.CODE_WORK_ON_LEGAL_HOLIDAY.equals(tomorrowWorkTypeCode))
				|| (addDay(dto.getWorkDate(), 1).before(dto.getEndTime()) && dto.getLegalWorkTime() == 0
						&& (TimeConst.CODE_HOLIDAY_LEGAL_HOLIDAY.equals(tomorrowWorkTypeCode)
								|| TimeConst.CODE_WORK_ON_LEGAL_HOLIDAY.equals(tomorrowWorkTypeCode)));
	}
	
}
