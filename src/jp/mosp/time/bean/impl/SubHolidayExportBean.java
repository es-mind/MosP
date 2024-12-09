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
import jp.mosp.platform.bean.workflow.WorkflowIntegrateBeanInterface;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.utils.PfNameUtility;
import jp.mosp.time.bean.SubHolidayReferenceBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.constant.TimeFileConst;
import jp.mosp.time.dao.settings.SubHolidayRequestDaoInterface;
import jp.mosp.time.dao.settings.impl.TmdSubHolidayDao;
import jp.mosp.time.dto.settings.SubHolidayDtoInterface;
import jp.mosp.time.dto.settings.SubHolidayRequestDtoInterface;
import jp.mosp.time.utils.TimeRequestUtility;

/**
 * 代休データエクスポート処理。<br>
 */
public class SubHolidayExportBean extends TimeBaseExportBean {
	
	/**
	 * 代休申請DAO。<br>
	 */
	protected SubHolidayRequestDaoInterface		subHolidayRequestDao;
	
	/**
	 * 代休データ参照クラス。<br>
	 */
	protected SubHolidayReferenceBeanInterface	subHolidayReference;
	
	/**
	 * ワークフロー統括クラス。<br>
	 */
	protected WorkflowIntegrateBeanInterface	workflowIntegrate;
	
	
	/**
	 * {@link TimeBaseExportBean#TimeBaseExportBean()}を実行する。<br>
	 */
	public SubHolidayExportBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// 継承基のメソッドを実行
		super.initBean();
		// DAOを準備
		subHolidayRequestDao = createDaoInstance(SubHolidayRequestDaoInterface.class);
		// Beanを準備
		subHolidayReference = createBeanInstance(SubHolidayReferenceBeanInterface.class);
		workflowIntegrate = createBeanInstance(WorkflowIntegrateBeanInterface.class);
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
		// 社員毎に処理
		for (HumanDtoInterface humanDto : humanList) {
			// 代休情報リストを取得
			List<SubHolidayDtoInterface> dtos = subHolidayReference.getSubHolidayList(humanDto.getPersonalId(),
					firstDate, lastDate, TimeConst.HOLIDAY_TIMES_HALF);
			// 代休情報毎に処理
			for (SubHolidayDtoInterface dto : dtos) {
				// 代休CSVデータを取得しCSVデータリストに設定
				csvDataList.add(makeCsvData(dto, humanDto, fieldList, lastDate));
			}
		}
		// CSVデータリストを取得
		return csvDataList;
	}
	
	/**
	 * 代休CSVデータを取得する。<br>
	 * @param dto        代休情報
	 * @param humanDto   人事情報
	 * @param fieldList  エクスポートフィールド名称リスト(フィールド順序昇順)
	 * @param targetDate 対象日
	 * @return 代休CSVデータ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected String[] makeCsvData(SubHolidayDtoInterface dto, HumanDtoInterface humanDto, List<String> fieldList,
			Date targetDate) throws MospException {
		// 代休CSVデータを準備
		String[] csvData = getHumanCsvData(fieldList, humanDto, targetDate);
		// 勤務日及び代休種別を取得
		Date workDate = dto.getWorkDate();
		int subHolidayType = dto.getSubHolidayType();
		// 代休日配列を取得
		Date[] requestDates = getRequestDateArray(dto.getPersonalId(), workDate, dto.getTimesWork(), subHolidayType);
		// 出勤日を取得
		String csvWorkDate = DateUtility.getStringDate(workDate);
		// 代休種別を取得
		String csvSubHolidayType = getCsvSubholidayType(subHolidayType, dto.getSubHolidayDays());
		// 代休日1を取得
		String csvRequestDate1 = DateUtility.getStringDate(requestDates[0]);
		// 代休日2を取得
		String csvRequestDate2 = DateUtility.getStringDate(requestDates[1]);
		// CSVデータに値を設定
		setCsvValue(csvData, fieldList, TimeFileConst.FIELD_WORK_DATE, csvWorkDate);
		setCsvValue(csvData, fieldList, TmdSubHolidayDao.COL_SUB_HOLIDAY_TYPE, csvSubHolidayType);
		setCsvValue(csvData, fieldList, TimeFileConst.FIELD_REQUEST_DATE1, csvRequestDate1);
		setCsvValue(csvData, fieldList, TimeFileConst.FIELD_REQUEST_DATE2, csvRequestDate2);
		// 代休CSVデータを取得
		return csvData;
	}
	
	/**
	 * CSV出力用の代休種別を取得する。<br>
	 * @param subHolidayType 代休種別
	 * @param subHolidayDays 代休日数
	 * @return CSV出力用の代休種別
	 */
	protected String getCsvSubholidayType(int subHolidayType, double subHolidayDays) {
		// CSV出力用の代休種別を準備
		StringBuilder sb = new StringBuilder();
		// 代休種別略称を取得
		sb.append(TimeRequestUtility.getSubHolidayTypeAbbr(subHolidayType, mospParams));
		// 代休日数名称を取得
		String subHolidayDaysAbbr = TimeRequestUtility.getSubHolidayDaysAbbr(subHolidayDays, mospParams);
		// CSV出力用の代休種別を取得
		return sb.append(PfNameUtility.parentheses(mospParams, subHolidayDaysAbbr)).toString();
	}
	
	/**
	 * 代休日配列を取得する。<br>
	 * @param personalId     個人ID
	 * @param workDate       出勤日
	 * @param timesWork      出勤回数
	 * @param subHolidayType 代休種別
	 * @return 代休日配列
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected Date[] getRequestDateArray(String personalId, Date workDate, int timesWork, int subHolidayType)
			throws MospException {
		Date[] requestDateArray = new Date[2];
		List<SubHolidayRequestDtoInterface> subHolidayRequestList = subHolidayRequestDao.findForList(personalId,
				workDate, timesWork, subHolidayType);
		for (SubHolidayRequestDtoInterface subHolidayRequestDto : subHolidayRequestList) {
			if (!workflowIntegrate.isCompleted(subHolidayRequestDto.getWorkflow())) {
				continue;
			}
			// 全休である場合
			if (TimeRequestUtility.isHolidayRangeAll(subHolidayRequestDto)) {
				requestDateArray[0] = subHolidayRequestDto.getRequestDate();
				requestDateArray[1] = subHolidayRequestDto.getRequestDate();
			}
			// 半休である場合
			if (TimeRequestUtility.isHolidayRangeHalf(subHolidayRequestDto)) {
				if (requestDateArray[0] == null) {
					requestDateArray[0] = subHolidayRequestDto.getRequestDate();
				} else {
					requestDateArray[1] = subHolidayRequestDto.getRequestDate();
				}
			}
		}
		return requestDateArray;
	}
	
}
