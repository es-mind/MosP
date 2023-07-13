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
import java.util.Set;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.framework.utils.NameUtility;
import jp.mosp.platform.bean.workflow.WorkflowIntegrateBeanInterface;
import jp.mosp.platform.dto.file.ExportDtoInterface;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.utils.PfNameUtility;
import jp.mosp.platform.utils.PlatformUtility;
import jp.mosp.time.bean.HolidayRequestReferenceBeanInterface;
import jp.mosp.time.bean.ScheduleUtilBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.constant.TimeFileConst;
import jp.mosp.time.dao.settings.SubHolidayRequestDaoInterface;
import jp.mosp.time.dao.settings.SubstituteDaoInterface;
import jp.mosp.time.dao.settings.WorkOnHolidayRequestDaoInterface;
import jp.mosp.time.dto.settings.HolidayDtoInterface;
import jp.mosp.time.dto.settings.HolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.SubHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.SubstituteDtoInterface;
import jp.mosp.time.dto.settings.WorkOnHolidayRequestDtoInterface;
import jp.mosp.time.utils.TimeNamingUtility;
import jp.mosp.time.utils.TimeRequestUtility;
import jp.mosp.time.utils.TimeUtility;

/**
 * 休暇取得データエクスポート処理。<br>
 */
public class HolidayExportBean extends TimeBaseExportBean {
	
	/**
	 * 代休申請DAO。<br>
	 */
	protected SubHolidayRequestDaoInterface			subHolidayRequestDao;
	
	/**
	 * 休日出勤申請DAO。<br>
	 */
	protected WorkOnHolidayRequestDaoInterface		workOnHolidayRequestDao;
	
	/**
	 * 振替休日DAO。<br>
	 */
	protected SubstituteDaoInterface				substituteDao;
	
	/**
	 * ワークフロー統括クラス。<br>
	 */
	protected WorkflowIntegrateBeanInterface		workflowIntegrate;
	
	/**
	 * カレンダユーティリティ。<br>
	 */
	protected ScheduleUtilBeanInterface				scheduleUtil;
	
	/**
	 * 休暇申請参照処理。<br>
	 */
	protected HolidayRequestReferenceBeanInterface	holidayRequestRefer;
	
	
	/**
	 * {@link TimeBaseExportBean#TimeBaseExportBean()}を実行する。<br>
	 */
	public HolidayExportBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// 継承基のメソッドを実行
		super.initBean();
		// DAO及びBeanの準備
		subHolidayRequestDao = createDaoInstance(SubHolidayRequestDaoInterface.class);
		workOnHolidayRequestDao = createDaoInstance(WorkOnHolidayRequestDaoInterface.class);
		substituteDao = createDaoInstance(SubstituteDaoInterface.class);
		workflowIntegrate = createBeanInstance(WorkflowIntegrateBeanInterface.class);
		holidayRequestRefer = createBeanInstance(HolidayRequestReferenceBeanInterface.class);
		scheduleUtil = createBeanInstance(ScheduleUtilBeanInterface.class);
		scheduleUtil.setTimeMaster(timeMaster);
	}
	
	@Override
	protected List<String[]> makeCsvDataList(List<String> fieldList, Date firstDate, Date lastDate, String cutoffCode,
			String workPlaceCode, String employmentContractCode, String sectionCode, boolean needLowerSection,
			String positionCode) throws MospException {
		// CSVデータリストを準備
		List<String[]> list = new ArrayList<String[]>();
		// 人事情報リストを取得
		List<HumanDtoInterface> humanList = getHumanList(firstDate, lastDate, cutoffCode, workPlaceCode,
				employmentContractCode, sectionCode, needLowerSection, positionCode);
		// 人事情報を設定
		addHumanData(list, fieldList, humanList, lastDate);
		// 有給休暇情報付加
		addPaidHolidayData(list, fieldList, humanList, firstDate, lastDate);
		// ストック休暇情報付加
		addStockHolidayData(list, fieldList, humanList, firstDate, lastDate);
		// 代休情報付加
		addSubHolidayData(list, fieldList, humanList, firstDate, lastDate);
		// 振替休日情報付加
		addSubstituteHolidayData(list, fieldList, humanList, firstDate, lastDate);
		// 休暇情報付加
		addHolidayData(list, fieldList, humanList, firstDate, lastDate);
		// 追加業務ロジック処理(休暇取得データエクスポート処理：CSVデータリスト作成)
		doStoredLogic(TimeConst.CODE_KEY_ADD_HOLIDAYEXPORTBEAN_GETCSVDATALIST, humanList, list, fieldList, firstDate,
				lastDate);
		// CSVデータリストを取得
		return list;
	}
	
	/**
	 * ヘッダを取得する。<br>
	 * @param exportDto   エクスポート情報
	 * @param fieldList   エクスポートフィールド名称リスト(フィールド順序昇順)
	 * @return ヘッダ
	 */
	@Override
	protected String[] getHeader(ExportDtoInterface exportDto, List<String> fieldList, Date targetDate)
			throws MospException {
		// ヘッダを準備
		String[] header = new String[fieldList.size()];
		// 対象日における最新の休暇種別情報群を取得
		Set<HolidayDtoInterface> holidays = timeMaster.getHolidaySet(targetDate);
		// インデックスを準備
		int i = 0;
		// フィールド毎に処理
		for (String field : fieldList) {
			// フィールドのタイトルを設定
			header[i++] = getFieldTitle(field, holidays);
		}
		// 追加業務ロジック処理(休暇取得データエクスポート処理：ヘッダ付加)
		doStoredLogic(TimeConst.CODE_KEY_ADD_HOLIDAYEXPORTBEAN_ADDHEADER, header, fieldList);
		// ヘッダを取得
		return header;
	}
	
	/**
	 * CSVデータリストに有給休暇情報を付加する。<br>
	 * @param csvDataList CSVデータリスト
	 * @param fieldList   エクスポートフィールド名称リスト(フィールド順序昇順)
	 * @param humanList   人事情報リスト
	 * @param firstDate   出力期間初日
	 * @param lastDate    出力期間最終日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void addPaidHolidayData(List<String[]> csvDataList, List<String> fieldList,
			List<HumanDtoInterface> humanList, Date firstDate, Date lastDate) throws MospException {
		// 有給休暇(全休)か有給休暇(半休)か有給休暇(午前)か有給休暇(午後)か有給休暇(時休)が含まれない場合
		if (PlatformUtility.isTheFieldExit(fieldList, TimeFileConst.FIELD_PAID_HOLIDAY_ALL,
				TimeFileConst.FIELD_PAID_HOLIDAY_HALF, TimeFileConst.FIELD_PAID_HOLIDAY_AM,
				TimeFileConst.FIELD_PAID_HOLIDAY_PM, TimeFileConst.FIELD_PAID_HOLIDAY_TIME) == false) {
			// 処理無し
			return;
		}
		// インデックスを準備
		int i = 0;
		// 人事情報毎に処理
		for (HumanDtoInterface human : humanList) {
			// 有給休暇(全休)と有給休暇(半休)と有給休暇(午前)と有給休暇(午後)と有給休暇(時休)を準備
			int paidHolidayAll = 0;
			int paidHolidayHalf = 0;
			int paidHolidayTime = 0;
			int paidHolidayAm = 0;
			int paidHolidayPm = 0;
			// 休暇申請情報(承認済：有給休暇)リストを取得
			List<HolidayRequestDtoInterface> list = holidayRequestRefer.getCompletedRequests(human.getPersonalId(),
					firstDate, lastDate, TimeConst.CODE_HOLIDAYTYPE_HOLIDAY, TimeConst.CODE_HOLIDAYTYPE2_PAID);
			// 休暇申請情報(承認済：有給休暇)毎に処理
			for (HolidayRequestDtoInterface dto : list) {
				// 全休の場合
				if (TimeRequestUtility.isHolidayRangeAll(dto)) {
					paidHolidayAll++;
				}
				// 午前休の場合
				if (TimeRequestUtility.isHolidayRangeAm(dto)) {
					paidHolidayHalf++;
					paidHolidayAm++;
				}
				// 午後休の場合
				if (TimeRequestUtility.isHolidayRangePm(dto)) {
					paidHolidayHalf++;
					paidHolidayPm++;
				}
				// 時間休の場合
				if (TimeRequestUtility.isHolidayRangeHour(dto)) {
					paidHolidayTime++;
				}
			}
			// CSVデータ取得
			String[] csvData = csvDataList.get(i++);
			// CSVデータに有給休暇(全休)を設定
			setCsvValue(csvData, fieldList, TimeFileConst.FIELD_PAID_HOLIDAY_ALL, paidHolidayAll);
			// CSVデータに有給休暇(半休)を設定
			setCsvValue(csvData, fieldList, TimeFileConst.FIELD_PAID_HOLIDAY_HALF, paidHolidayHalf);
			// CSVデータに有給休暇(午前)を設定
			setCsvValue(csvData, fieldList, TimeFileConst.FIELD_PAID_HOLIDAY_AM, paidHolidayAm);
			// CSVデータに有給休暇(午後)を設定
			setCsvValue(csvData, fieldList, TimeFileConst.FIELD_PAID_HOLIDAY_PM, paidHolidayPm);
			// CSVデータに有給休暇(時休)を設定
			setCsvValue(csvData, fieldList, TimeFileConst.FIELD_PAID_HOLIDAY_TIME, paidHolidayTime);
		}
	}
	
	/**
	 * CSVデータリストにストック休暇情報を付加する。<br>
	 * @param csvDataList CSVデータリスト
	 * @param fieldList   エクスポートフィールド名称リスト(フィールド順序昇順)
	 * @param humanList   人事情報リスト
	 * @param firstDate   出力期間初日
	 * @param lastDate    出力期間最終日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void addStockHolidayData(List<String[]> csvDataList, List<String> fieldList,
			List<HumanDtoInterface> humanList, Date firstDate, Date lastDate) throws MospException {
		// ストック休暇(全休)かストック休暇(午前)かストック休暇(午後)かストック休暇(半休)が含まれない場合
		if (PlatformUtility.isTheFieldExit(fieldList, TimeFileConst.FIELD_STOCK_HOLIDAY_ALL,
				TimeFileConst.FIELD_STOCK_HOLIDAY_AM, TimeFileConst.FIELD_STOCK_HOLIDAY_PM,
				TimeFileConst.FIELD_STOCK_HOLIDAY_HALF) == false) {
			// 処理無し
			return;
		}
		// インデックスを準備
		int i = 0;
		// 人事情報毎に処理
		for (HumanDtoInterface human : humanList) {
			// ストック休暇(全休)とストック休暇(半休)を準備
			int stockHolidayAll = 0;
			int stockHolidayHalf = 0;
			int stockHolidayAm = 0;
			int stockHolidayPm = 0;
			// 休暇申請情報(承認済：ストック休暇)リストを取得
			List<HolidayRequestDtoInterface> list = holidayRequestRefer.getCompletedRequests(human.getPersonalId(),
					firstDate, lastDate, TimeConst.CODE_HOLIDAYTYPE_HOLIDAY, TimeConst.CODE_HOLIDAYTYPE2_STOCK);
			// 休暇申請情報(承認済：ストック休暇)毎に処理
			for (HolidayRequestDtoInterface dto : list) {
				// 全休の場合
				if (TimeRequestUtility.isHolidayRangeAll(dto)) {
					stockHolidayAll++;
				}
				// 午前休の場合
				if (TimeRequestUtility.isHolidayRangeAm(dto)) {
					stockHolidayHalf++;
					stockHolidayAm++;
				}
				// 午後休の場合
				if (TimeRequestUtility.isHolidayRangePm(dto)) {
					stockHolidayHalf++;
					stockHolidayPm++;
				}
			}
			// CSVデータ取得
			String[] csvData = csvDataList.get(i++);
			// CSVデータにストック休暇(全休)を設定
			setCsvValue(csvData, fieldList, TimeFileConst.FIELD_STOCK_HOLIDAY_ALL, stockHolidayAll);
			// CSVデータにストック休暇(半休)を設定
			setCsvValue(csvData, fieldList, TimeFileConst.FIELD_STOCK_HOLIDAY_HALF, stockHolidayHalf);
			// CSVデータにストック休暇(午前)を設定
			setCsvValue(csvData, fieldList, TimeFileConst.FIELD_STOCK_HOLIDAY_AM, stockHolidayAm);
			// CSVデータにストック休暇(午後)を設定
			setCsvValue(csvData, fieldList, TimeFileConst.FIELD_STOCK_HOLIDAY_PM, stockHolidayPm);
		}
	}
	
	/**
	 * CSVデータリストに代休情報を付加する。<br>
	 * @param csvDataList CSVデータリスト
	 * @param fieldList   エクスポートフィールド名称リスト(フィールド順序昇順)
	 * @param humanList   人事情報リスト
	 * @param firstDate   出力期間初日
	 * @param lastDate    出力期間最終日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void addSubHolidayData(List<String[]> csvDataList, List<String> fieldList,
			List<HumanDtoInterface> humanList, Date firstDate, Date lastDate) throws MospException {
		// 代休休暇(全休)か代休休暇(午前)か代休休暇(午後)か代休休暇(半休)が含まれない場合
		if (PlatformUtility.isTheFieldExit(fieldList, TimeFileConst.FIELD_SUB_HOLIDAY_ALL,
				TimeFileConst.FIELD_SUB_HOLIDAY_AM, TimeFileConst.FIELD_SUB_HOLIDAY_PM,
				TimeFileConst.FIELD_SUB_HOLIDAY_HALF) == false) {
			// 処理無し
			return;
		}
		// インデックスを準備
		int i = 0;
		// 人事情報毎に処理
		for (HumanDtoInterface human : humanList) {
			// 代休休暇(全休)と代休休暇(半休)を準備
			int subHolidayAll = 0;
			int subHolidayHalf = 0;
			int subHolidayAm = 0;
			int subHolidayPm = 0;
			// 代休申請情報リストを取得
			List<SubHolidayRequestDtoInterface> list = subHolidayRequestDao.findForTerm(human.getPersonalId(),
					firstDate, lastDate);
			// 代休申請情報毎に処理
			for (SubHolidayRequestDtoInterface dto : list) {
				// 承認済でない場合
				if (workflowIntegrate.isCompleted(dto.getWorkflow()) == false) {
					// 次の代休申請へ
					continue;
				}
				// 全休の場合
				if (TimeRequestUtility.isHolidayRangeAll(dto)) {
					subHolidayAll++;
				}
				// 午前休の場合
				if (TimeRequestUtility.isHolidayRangeAm(dto)) {
					subHolidayHalf++;
					subHolidayAm++;
				}
				// 午後休の場合
				if (TimeRequestUtility.isHolidayRangePm(dto)) {
					subHolidayHalf++;
					subHolidayPm++;
				}
			}
			// CSVデータ取得
			String[] csvData = csvDataList.get(i++);
			// CSVデータに代休休暇(全休)を設定
			setCsvValue(csvData, fieldList, TimeFileConst.FIELD_SUB_HOLIDAY_ALL, subHolidayAll);
			// CSVデータに代休休暇(半休)を設定
			setCsvValue(csvData, fieldList, TimeFileConst.FIELD_SUB_HOLIDAY_HALF, subHolidayHalf);
			// CSVデータに代休休暇(午前)を設定
			setCsvValue(csvData, fieldList, TimeFileConst.FIELD_SUB_HOLIDAY_AM, subHolidayAm);
			// CSVデータに代休休暇(午後)を設定
			setCsvValue(csvData, fieldList, TimeFileConst.FIELD_SUB_HOLIDAY_PM, subHolidayPm);
		}
	}
	
	/**
	 * CSVデータリストに振替休日情報を付加する。<br>
	 * 半日振替は考慮しておらず、半日振替休日であっても1とカウントする。<br>
	 * @param csvDataList CSVデータリスト
	 * @param fieldList   エクスポートフィールド名称リスト(フィールド順序昇順)
	 * @param humanList   人事情報リスト
	 * @param firstDate   出力期間初日
	 * @param lastDate    出力期間最終日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void addSubstituteHolidayData(List<String[]> csvDataList, List<String> fieldList,
			List<HumanDtoInterface> humanList, Date firstDate, Date lastDate) throws MospException {
		// 振替休日(全休)が含まれない場合
		if (PlatformUtility.isTheFieldExit(fieldList, TimeFileConst.FIELD_SUBSTITUTE_HOLIDAY_ALL) == false) {
			// 処理無し
			return;
		}
		// インデックスを準備
		int i = 0;
		// 人事情報毎に処理
		for (HumanDtoInterface human : humanList) {
			// 振替休日(全休)を準備
			int substituteAll = 0;
			// 振替休日情報リストを取得
			List<SubstituteDtoInterface> list = substituteDao.findForTerm(human.getPersonalId(), firstDate, lastDate);
			// 振替休日情報毎に処理
			for (SubstituteDtoInterface dto : list) {
				// 承認済でない場合
				if (workflowIntegrate.isCompleted(dto.getWorkflow()) == false) {
					// 次の振替休日情報へ
					continue;
				}
				// 振出・休出申請を取得
				WorkOnHolidayRequestDtoInterface workOnHolidayRequestDto = workOnHolidayRequestDao
					.findForKeyOnWorkflow(dto.getPersonalId(), dto.getSubstituteDate());
				// 振替休日に振出・休出申請が存在しない場合
				if (workOnHolidayRequestDto == null) {
					// 振替休日を加算
					substituteAll++;
					// 次の振替休日情報へ
					continue;
				}
				// 振出・休出申請が承認済でない場合
				if (workflowIntegrate.isCompleted(workOnHolidayRequestDto.getWorkflow()) == false) {
					// 振替休日を加算
					substituteAll++;
				}
			}
			// CSVデータ取得
			String[] csvData = csvDataList.get(i++);
			// CSVデータに振替休日(全休)を設定
			setCsvValue(csvData, fieldList, TimeFileConst.FIELD_SUBSTITUTE_HOLIDAY_ALL, substituteAll);
		}
	}
	
	/**
	 * CSVデータリストに休暇情報を付加する。<br>
	 * @param csvDataList CSVデータリスト
	 * @param fieldList   エクスポートフィールド名称リスト(フィールド順序昇順)
	 * @param humanList   人事情報リスト
	 * @param firstDate   出力期間初日
	 * @param lastDate    出力期間最終日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void addHolidayData(List<String[]> csvDataList, List<String> fieldList, List<HumanDtoInterface> humanList,
			Date firstDate, Date lastDate) throws MospException {
		// 休暇フィールドインデックス群(キー：フィールド名称)を取得
		Map<String, Integer> holidayIndexes = getHolidayIndexes(fieldList);
		// 休暇フィールドが出力不要である場合
		if (MospUtility.isEmpty(holidayIndexes)) {
			// 処理終了
			return;
		}
		// 人事情報毎に処理
		for (int i = 0; i < humanList.size(); i++) {
			// 人事情報を取得
			HumanDtoInterface human = humanList.get(i);
			// 休暇フィールド毎に処理
			for (Entry<String, Integer> entry : holidayIndexes.entrySet()) {
				// 休暇数を取得
				int count = getHolidayCount(entry.getKey(), human, firstDate, lastDate);
				// CSVデータ取得
				String[] csvData = csvDataList.get(i);
				// 休暇数を設定
				csvData[entry.getValue().intValue()] = MospUtility.getString(count);
			}
		}
	}
	
	/**
	 * 休暇数(特別休暇・その他休暇・欠勤)を取得する。<br>
	 * @param fieldName エクスポートフィールド名称
	 * @param human     人事情報
	 * @param firstDate 出力期間初日
	 * @param lastDate  出力期間最終日
	 * @return 休暇数(特別休暇・その他休暇・欠勤)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected int getHolidayCount(String fieldName, HumanDtoInterface human, Date firstDate, Date lastDate)
			throws MospException {
		// 休暇数を準備
		int count = 0;
		// フィールド名を区切文字で分割(0：休暇種別、1：休暇コード、2：休暇範囲(文字列))
		String[] array = MospUtility.split(fieldName, SEPARATOR_DATA);
		int holidayType = MospUtility.getInt(array[0]);
		String holidayCode = array[1];
		String range = array[2];
		// 休暇申請情報(承認済)リストを取得
		List<HolidayRequestDtoInterface> list = holidayRequestRefer.getCompletedRequests(human.getPersonalId(),
				firstDate, lastDate, holidayType, holidayCode);
		// 休暇申請毎に処理
		for (HolidayRequestDtoInterface dto : list) {
			// フィールドが全休で且つ申請の範囲が全休の場合
			if (MospUtility.isEqual(range, TimeFileConst.FIELD_ALL) && TimeRequestUtility.isHolidayRangeAll(dto)) {
				// 申請開始日取得
				Date targetDate = dto.getRequestStartDate();
				// 申請開始日が開始日より前の場合
				if (dto.getRequestStartDate().before(firstDate)) {
					// 開始日に設定
					targetDate = firstDate;
				}
				// 申請終了日取得
				Date targetEndDate = dto.getRequestEndDate();
				// 申請終了日が終了日より後の場合
				if (dto.getRequestEndDate().after(lastDate)) {
					// 終了日に設定
					targetEndDate = lastDate;
				}
				while (!targetDate.after(targetEndDate)) {
					// 休暇申請可能の場合
					if (canHolidayRequest(dto.getPersonalId(), targetDate)) {
						count++;
					}
					// 1日加算
					targetDate = addDay(targetDate, 1);
				}
			}
			// フィールドが半休で且つ申請の範囲が午前休・午後休の場合
			if (MospUtility.isEqual(range, TimeFileConst.FIELD_HALF) && TimeRequestUtility.isHolidayRangeHalf(dto)) {
				// 休暇数を設定
				count++;
			}
			// フィールドが午前休で且つ申請の範囲が午前休の場合
			if (MospUtility.isEqual(range, TimeFileConst.FIELD_AM) && TimeRequestUtility.isHolidayRangeAm(dto)) {
				// 休暇数を設定
				count++;
			}
			// フィールドが午後休で且つ申請の範囲が午後休の場合
			if (MospUtility.isEqual(range, TimeFileConst.FIELD_PM) && TimeRequestUtility.isHolidayRangePm(dto)) {
				// 休暇数を設定
				count++;
			}
			// 時間休の場合
			if (MospUtility.isEqual(range, TimeFileConst.FIELD_HOUR) && TimeRequestUtility.isHolidayRangeHour(dto)) {
				// 休暇数を設定
				count++;
			}
		}
		// 休暇数を取得
		return count;
		
	}
	
	/**
	 * 休暇フィールドインデックス群(キー：フィールド名称)を取得する。<br>
	 * 特別休暇・その他休暇・欠勤に対するインデックスを取得する。<br>
	 * @param fieldList エクスポートフィールド名称リスト(フィールド順序昇順)
	 * @return 休暇フィールドインデックス群(キー：フィールド名称)
	 */
	protected Map<String, Integer> getHolidayIndexes(List<String> fieldList) {
		// 休暇フィールドインデックス群(キー：フィールド名称)を準備
		Map<String, Integer> holidayIndexes = new HashMap<String, Integer>();
		// 休暇用フィールド名称の区切文字を準備
		String separotor = SEPARATOR_DATA;
		// 休暇フィールドの頭文字列を準備
		String special = new StringBuilder().append(TimeConst.CODE_HOLIDAYTYPE_SPECIAL).append(separotor).toString();
		String other = new StringBuilder().append(TimeConst.CODE_HOLIDAYTYPE_OTHER).append(separotor).toString();
		String absence = new StringBuilder().append(TimeConst.CODE_HOLIDAYTYPE_ABSENCE).append(separotor).toString();
		// エクスポートフィールド名称毎に処理
		for (int fieldIndex = 0; fieldIndex < fieldList.size(); fieldIndex++) {
			// フィールド名を取得
			String fieldName = fieldList.get(fieldIndex);
			// フィールド名が休暇フィールドの頭文字列で始まる(特別休暇・その他休暇・欠勤である)場合
			if (fieldName.startsWith(special) || fieldName.startsWith(other) || fieldName.startsWith(absence)) {
				// 休暇フィールドインデックス群(キー：フィールド名称)に設定
				holidayIndexes.put(fieldName, fieldIndex);
			}
		}
		// 休暇フィールドインデックス群(キー：フィールド名称)を取得
		return holidayIndexes;
	}
	
	/**
	 * フィールドのタイトルを取得する。<br>
	 * @param fieldName フィールド名称
	 * @param holidays  休暇種別情報群
	 * @return フィールドのタイトル
	 */
	protected String getFieldTitle(String fieldName, Set<HolidayDtoInterface> holidays) {
		// コード名称からフィールドのタイトルを取得
		String title = getCodeName(fieldName, TimeFileConst.CODE_EXPORT_TYPE_HOLIDAY_REQUEST_DATA);
		// コード名称からフィールドのタイトルが取得できた場合
		if (MospUtility.isEqual(fieldName, title) == false) {
			// フィールドのタイトルを取得
			return title;
		}
		// フィールド名称を区切文字で分割(0：休暇種別、1：休暇コード、2：休暇範囲(文字列))
		String[] array = MospUtility.split(fieldName, SEPARATOR_DATA);
		int holidayType = MospUtility.getInt(array[0]);
		String holidayCode = array[1];
		String range = array[2];
		// 休暇情報を取得
		HolidayDtoInterface holidayDto = TimeUtility.getHolidayDto(holidays, holidayCode, holidayType);
		// 休暇情報を取得できなかった場合
		if (MospUtility.isEmpty(holidayDto)) {
			// フィールドのタイトルを取得
			return NameUtility.cornerParentheses(mospParams, holidayCode);
		}
		// フィールドのタイトルを取得を準備
		StringBuilder sb = new StringBuilder(holidayDto.getHolidayName());
		// 休暇範囲部分を準備
		String rangeTitle = MospConst.STR_EMPTY;
		// 全休の場合
		if (MospUtility.isEqual(range, TimeFileConst.FIELD_ALL)) {
			// 休暇範囲部分に全休を設定
			rangeTitle = TimeNamingUtility.holidayRangeAll(mospParams);
		}
		// 半休の場合
		if (MospUtility.isEqual(range, TimeFileConst.FIELD_HALF)) {
			// 休暇範囲部分に半休を設定
			rangeTitle = TimeNamingUtility.holidayHalf(mospParams);
		}
		// 午前休の場合
		if (MospUtility.isEqual(range, TimeFileConst.FIELD_AM)) {
			// 休暇範囲部分に午前休を設定
			rangeTitle = TimeNamingUtility.anteMeridiem(mospParams);
		}
		// 午後休の場合
		if (MospUtility.isEqual(range, TimeFileConst.FIELD_PM)) {
			// 休暇範囲部分に午後休を設定
			rangeTitle = TimeNamingUtility.postMeridiem(mospParams);
		}
		// 時間休の場合
		if (MospUtility.isEqual(range, TimeFileConst.FIELD_HOUR)) {
			// 休暇範囲部分に時休を設定
			rangeTitle = TimeNamingUtility.holidayRangeHourAbbr(mospParams);
		}
		// 休暇範囲部分が設定されている場合
		if (MospUtility.isEmpty(rangeTitle) == false) {
			// フィールドのタイトルに休暇範囲部分を設定
			sb.append(PfNameUtility.parentheses(mospParams, rangeTitle));
		}
		// フィールドのタイトルに休暇コードを設定
		sb.append(NameUtility.cornerParentheses(mospParams, holidayCode));
		// フィールドのタイトルを取得
		return sb.toString();
	}
	
	/**
	 * 休暇申請可能かどうか確認する。<br>
	 * 連続休暇を取得した場合に、期間内に休日や休日出勤日が含まれる可能性があるため、
	 * 対象日が休暇対象日であるかを確認する。<br>
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @return 確認結果(true：対象日は休暇申請可能である、false：そうでない)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected boolean canHolidayRequest(String personalId, Date targetDate) throws MospException {
		// 申請日の勤務形態コードを取得
		String workTypeCode = scheduleUtil.getScheduledWorkTypeCode(personalId, targetDate, true);
		// 勤務形態が休日か休日出勤であるかを確認
		return TimeRequestUtility.isNotHolidayForConsecutiveHolidays(workTypeCode) == false;
	}
	
}
