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
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.time.bean.AttendanceListReferenceBeanInterface;
import jp.mosp.time.constant.TimeFileConst;
import jp.mosp.time.dto.settings.impl.AttendanceListDto;

/**
 * 出勤簿データエクスポート処理。<br>
 */
public class AttendanceBookExportBean extends TimeBaseExportBean {
	
	/**
	 * 勤怠一覧参照処理。<br>
	 */
	protected AttendanceListReferenceBeanInterface attendanceListRefer;
	
	
	/**
	 * {@link TimeBaseExportBean#TimeBaseExportBean()}を実行する。<br>
	 */
	public AttendanceBookExportBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// 継承基のメソッドを実行
		super.initBean();
		// Beanを準備
		attendanceListRefer = createBeanInstance(AttendanceListReferenceBeanInterface.class);
	}
	
	@Override
	protected List<String[]> makeCsvDataList(List<String> fieldList, Date firstDate, Date lastDate, String cutoffCode,
			String workPlaceCode, String employmentContractCode, String sectionCode, boolean needLowerSection,
			String positionCode) throws MospException {
		// CSVデータリストを準備
		List<String[]> csvDataList = new ArrayList<String[]>();
		// 人事情報群を取得
		List<HumanDtoInterface> humanList = getHumanList(firstDate, lastDate, cutoffCode, workPlaceCode,
				employmentContractCode, sectionCode, needLowerSection, positionCode);
		// 出力対象月数を取得
		int months = getTargetMonths(firstDate, lastDate, cutoffCode);
		// 出力対象初月を取得
		Date firstMonth = timeMaster.getCutoff(cutoffCode, firstDate).getCutoffMonth(firstDate, mospParams);
		// 締日を取得
		int cutoffDate = timeMaster.getCutoff(cutoffCode, firstDate).getCutoffDate();
		// 人事情報毎に処理
		for (HumanDtoInterface humanDto : humanList) {
			// 出力対象月毎に処理
			for (int i = 0; i < months; i++) {
				// 出力対象年月を取得
				Date targetMonth = DateUtility.addMonth(firstMonth, i);
				// 出勤簿CSVデータを取得しCSVデータリストに設定
				csvDataList.addAll(makeCsvData(humanDto, fieldList, targetMonth, cutoffDate));
			}
		}
		// CSVデータリストを取得
		return csvDataList;
	}
	
	/**
	 * 出勤簿CSVデータを取得する。<br>
	 * @param humanDto    人事情報
	 * @param fieldList   エクスポートフィールド名称リスト(フィールド順序昇順)
	 * @param targetMonth 出力対象年月
	 * @param cutoffDate  締日
	 * @return 出勤簿CSVデータ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected List<String[]> makeCsvData(HumanDtoInterface humanDto, List<String> fieldList, Date targetMonth,
			int cutoffDate) throws MospException {
		// 出勤簿CSVデータを準備
		List<String[]> csvData = new ArrayList<String[]>();
		// 個人IDを取得
		String personalId = humanDto.getPersonalId();
		// 出力対象年月を取得
		int year = DateUtility.getYear(targetMonth);
		int month = DateUtility.getMonth(targetMonth);
		// 勤怠一覧情報(出勤簿)リストを取得
		List<AttendanceListDto> dtos = attendanceListRefer.getActualList(personalId, year, month, cutoffDate);
		// エラーメッセージを削除(設定適用情報が取得できなかった場合等)
		mospParams.getErrorMessageList().clear();
		// 勤怠一覧情報(出勤簿)毎に処理
		for (AttendanceListDto dto : dtos) {
			csvData.add(makeCsvData(dto, humanDto, fieldList, targetMonth));
		}
		// 出勤簿CSVデータを取得
		return csvData;
	}
	
	/**
	 * 出勤簿CSVデータを取得する。<br>
	 * @param dto        勤怠一覧情報(出勤簿)
	 * @param humanDto   人事情報
	 * @param fieldList  エクスポートフィールド名称リスト(フィールド順序昇順)
	 * @param targetDate 対象日(所属名称の取得に用いる)
	 * @return 出勤簿CSVデータ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected String[] makeCsvData(AttendanceListDto dto, HumanDtoInterface humanDto, List<String> fieldList,
			Date targetDate) throws MospException {
		// 勤務日を取得
		Date workDate = dto.getWorkDate();
		// 出勤簿CSVデータを準備
		String[] csvData = getHumanCsvData(fieldList, humanDto, workDate);
		// 出勤簿CSVデータに値を設定
		setCsvValue(csvData, fieldList, TimeFileConst.FIELD_SHEDULE_DAY, DateUtility.getStringDateAndDay(workDate));
		setCsvValue(csvData, fieldList, TimeFileConst.FIELD_WORK_TYPE_CODE, dto.getWorkTypeCode());
		setCsvValue(csvData, fieldList, TimeFileConst.FIELD_WORK_TYPE_ABBR, dto.getWorkTypeAbbr());
		setCsvValue(csvData, fieldList, TimeFileConst.FIELD_START_TIME, dto.getStartTimeString());
		setCsvValue(csvData, fieldList, TimeFileConst.FIELD_END_TIME, dto.getEndTimeString());
		setCsvValue(csvData, fieldList, TimeFileConst.FIELD_WORK_TIME, dto.getWorkTimeString());
		setCsvValue(csvData, fieldList, TimeFileConst.FIELD_REST_TIME, dto.getRestTimeString());
		setCsvValue(csvData, fieldList, TimeFileConst.FIELD_PRIVATE_TIME, dto.getPrivateTimeString());
		setCsvValue(csvData, fieldList, TimeFileConst.FIELD_LATE_EARLY_TIME, dto.getLateLeaveEarlyTimeString());
		setCsvValue(csvData, fieldList, TimeFileConst.FIELD_OVER_TIME_IN, dto.getOvertimeInString());
		setCsvValue(csvData, fieldList, TimeFileConst.FIELD_OVER_TIME_OUT, dto.getOvertimeOutString());
		setCsvValue(csvData, fieldList, TimeFileConst.FIELD_WORK_ON_HOLIDAY, dto.getHolidayWorkTimeString());
		setCsvValue(csvData, fieldList, TimeFileConst.FIELD_LAST_NIGHT, dto.getLateNightTimeString());
		setCsvValue(csvData, fieldList, TimeFileConst.FIELD_TIME_REMARKS,
				MospUtility.concat(dto.getRemark(), dto.getTimeComment()));
		// 出勤簿CSVデータを取得
		return csvData;
	}
	
	/**
	 * 出力対象月数を取得する。<br>
	 * @param firstDate  出力期間初日
	 * @param lastDate   出力期間最終日
	 * @param cutoffCode 締日コード
	 * @return 出力対象月数
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected int getTargetMonths(Date firstDate, Date lastDate, String cutoffCode) throws MospException {
		// 出力対象年月を取得
		Date firstMonth = timeMaster.getCutoff(cutoffCode, firstDate).getCutoffMonth(firstDate, mospParams);
		Date lastMonth = timeMaster.getCutoff(cutoffCode, lastDate).getCutoffMonth(lastDate, mospParams);
		// 出力対象年月の月数差を取得
		int monthDifference = DateUtility.getMonthDifference(firstMonth, lastMonth);
		// 出力対象月数(月数差+1)を取得
		return ++monthDifference;
	}
	
}
