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
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.time.bean.HolidayRequestReferenceBeanInterface;
import jp.mosp.time.bean.PaidHolidayDataReferenceBeanInterface;
import jp.mosp.time.constant.TimeFileConst;
import jp.mosp.time.dto.settings.HolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.PaidHolidayDataDtoInterface;
import jp.mosp.time.utils.TimeRequestUtility;
import jp.mosp.time.utils.TimeUtility;

/**
 * 有給休暇取得状況データエクスポート処理。<br>
 */
public class UsedPaidHolidayDataExportBean extends TimeBaseExportBean {
	
	/**
	 * 有給休暇情報参照処理。<br>
	 */
	protected PaidHolidayDataReferenceBeanInterface	paidHolidayRefer;
	
	/**
	 * 休暇申請参照処理。<br>
	 */
	protected HolidayRequestReferenceBeanInterface	holidayRequestRefer;
	
	
	/**
	 * {@link TimeBaseExportBean#TimeBaseExportBean()}を実行する。<br>
	 */
	public UsedPaidHolidayDataExportBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// 継承基のメソッドを実行
		super.initBean();
		// Beanを準備
		paidHolidayRefer = createBeanInstance(PaidHolidayDataReferenceBeanInterface.class);
		holidayRequestRefer = createBeanInstance(HolidayRequestReferenceBeanInterface.class);
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
		// 全社員の期間中に有効な休暇申請情報(有給休暇)群(キー：個人ID)を取得
		Map<String, Map<Date, List<HolidayRequestDtoInterface>>> requestMap = getHolidayRequestMap(firstDate, lastDate);
		// 人事情報毎に処理
		for (HumanDtoInterface humanDto : humanList) {
			// 休暇申請情報(有給休暇)群(キー：休暇取得日(有給休暇付与日))を取得
			Map<Date, List<HolidayRequestDtoInterface>> holidayMap = requestMap.get(humanDto.getPersonalId());
			// 対象となる休暇申請情報(有給休暇)が存在しない場合
			if (MospUtility.isEmpty(holidayMap)) {
				// 次の人事情報へ
				continue;
			}
			// 休暇取得日(有給休暇付与日)毎に処理
			for (Entry<Date, List<HolidayRequestDtoInterface>> entry : holidayMap.entrySet()) {
				Date acquisitionDate = entry.getKey();
				List<HolidayRequestDtoInterface> requests = entry.getValue();
				// 有給休暇取得状況CSVデータを取得しCSVデータリストに設定
				csvDataList.add(makeCsvData(acquisitionDate, requests, humanDto, fieldList, firstDate, lastDate));
			}
		}
		// CSVデータリストを取得
		return csvDataList;
	}
	
	/**
	 * 有給休暇取得状況CSVデータを取得する。<br>
	 * @param acquisitionDate 休暇取得日(有給休暇付与日)
	 * @param requests        休暇申請情報(有給休暇)リスト
	 * @param humanDto        人事情報
	 * @param fieldList       エクスポートフィールド名称リスト(フィールド順序昇順)
	 * @param firstDate       出力期間初日
	 * @param lastDate        出力期間最終日
	 * @return 有給休暇取得状況CSVデータ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected String[] makeCsvData(Date acquisitionDate, List<HolidayRequestDtoInterface> requests,
			HumanDtoInterface humanDto, List<String> fieldList, Date firstDate, Date lastDate) throws MospException {
		// 有給休暇取得状況CSVデータを準備
		String[] csvData = getHumanCsvData(fieldList, humanDto, lastDate);
		// 取得日を取得
		String csvAcquisitionDate = DateUtility.getStringDate(acquisitionDate);
		// 申請日数を取得
		double csvUsedDays = getUsedDays(requests);
		// 申請日を取得
		String csvAppliDate = MospUtility.concat(SEPARATOR_DATA, MospUtility.toArray(getAppliDates(requests)));
		// 有給休暇取得状況CSVデータに値を設定
		setCsvValue(csvData, fieldList, TimeFileConst.ACQUISITION_DATE, csvAcquisitionDate);
		setCsvValue(csvData, fieldList, TimeFileConst.USED_DAYS, csvUsedDays);
		setCsvValue(csvData, fieldList, TimeFileConst.APPLIE_DATE, csvAppliDate);
		// 有給休暇取得状況CSVデータを取得
		return csvData;
	}
	
	/**
	 * 申請日数を取得する。<br>
	 * @param requests 休暇申請情報(有給休暇)群
	 * @return 申請日数
	 */
	protected double getUsedDays(Collection<HolidayRequestDtoInterface> requests) {
		// 申請日数を準備
		double usedDays = 0F;
		// 休暇申請情報(有給休暇)毎に処理
		for (HolidayRequestDtoInterface request : requests) {
			// 時間単位休暇である場合
			if (TimeRequestUtility.isHolidayRangeHour(request)) {
				// 次の休暇申請情報(有給休暇)へ
				continue;
			}
			// 申請日数を設定
			usedDays += request.getUseDay();
		}
		// 申請日数を取得
		return usedDays;
	}
	
	/**
	 * 申請日文字列リストを取得する。<br>
	 * @param requests 休暇申請情報(有給休暇)群
	 * @return 申請日文字列リスト
	 */
	protected List<String> getAppliDates(Collection<HolidayRequestDtoInterface> requests) {
		// 申請日リストを準備
		List<String> appliDates = new ArrayList<String>();
		// 休暇申請情報(有給休暇)毎に処理
		for (HolidayRequestDtoInterface request : requests) {
			// 時間単位休暇である場合
			if (TimeRequestUtility.isHolidayRangeHour(request)) {
				// 次の休暇申請情報(有給休暇)へ
				continue;
			}
			// 申請日文字列を準備
			StringBuilder sb = new StringBuilder(DateUtility.getStringDate(request.getRequestStartDate()));
			// 休暇範囲略称を設定
			sb.append(MospConst.STR_SB_SPACE);
			sb.append(TimeUtility.getHolidayRangeAbbr(mospParams, request.getHolidayRange()));
			// 申請日リストに設定
			appliDates.add(sb.toString());
		}
		// 申請日リストを取得
		return appliDates;
	}
	
	/**
	 * 対象期間内に有効な有給休暇データを使用して取得した全社員の休暇申請Mapを取得する。
	 * @param startDate 期間開始日
	 * @param endDate 期間終了日
	 * @return 対象期間内の全社員の休暇申請Map
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected Map<String, Map<Date, List<HolidayRequestDtoInterface>>> getHolidayRequestMap(Date startDate,
			Date endDate) throws MospException {
		// 期間内に有効な有給休暇データを取得
		List<PaidHolidayDataDtoInterface> dtos = paidHolidayRefer.getPaidHolidayDataForTerm(startDate, endDate);
		// 返却Map準備
		Map<String, Map<Date, List<HolidayRequestDtoInterface>>> paidHolidayMap = new TreeMap<String, Map<Date, List<HolidayRequestDtoInterface>>>();
		// 期間内の全社員の有給休暇申請を取得
		Map<String, Map<Date, List<HolidayRequestDtoInterface>>> requestMap = getPersonalHolidayRequestMap(startDate,
				endDate);
		// 有給休暇付与データごとに処理
		for (PaidHolidayDataDtoInterface dto : dtos) {
			String personalId = dto.getPersonalId();
			Date acquisitionDate = dto.getAcquisitionDate();
			// 対象社員の休暇申請Mapを取得
			Map<Date, List<HolidayRequestDtoInterface>> map = requestMap.get(personalId);
			if (map == null) {
				// 存在しない場合、空のMapを用意
				map = new TreeMap<Date, List<HolidayRequestDtoInterface>>();
			}
			// 休暇申請Mapから、対象付与日の申請リストを取得。
			List<HolidayRequestDtoInterface> requestList = map.get(acquisitionDate);
			if (requestList == null) {
				// 存在しない場合、空のリストを用意。
				requestList = new ArrayList<HolidayRequestDtoInterface>();
			}
			// 返却用のMapから対象社員の休暇申請Mapを取得
			Map<Date, List<HolidayRequestDtoInterface>> resultMap = paidHolidayMap.get(personalId);
			if (resultMap == null) {
				// 存在しない場合、新規に用意
				resultMap = new TreeMap<Date, List<HolidayRequestDtoInterface>>();
			}
			// 休暇申請Mapに休暇申請リストをセット
			resultMap.put(acquisitionDate, requestList);
			// 返却用Mapに休暇申請Mapをセット
			paidHolidayMap.put(personalId, resultMap);
		}
		return paidHolidayMap;
	}
	
	/**
	 * 期間内の全社員の未承認以上の有給休暇の休暇申請をMapで取得する。
	 * @param startDate 期間開始日
	 * @param endDate 期間終了日
	 * @return <個人ID,<付与日,休暇申請リスト>>のMap
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected Map<String, Map<Date, List<HolidayRequestDtoInterface>>> getPersonalHolidayRequestMap(Date startDate,
			Date endDate) throws MospException {
		// 返却用Map用意
		Map<String, Map<Date, List<HolidayRequestDtoInterface>>> resultMap = new TreeMap<String, Map<Date, List<HolidayRequestDtoInterface>>>();
		// 対象期間内の全社員の未承認以上の有給休暇申請を取得する。
		List<HolidayRequestDtoInterface> dtos = holidayRequestRefer.getAppliedPaidHolidayRequests(startDate, endDate);
		// 休暇申請ごとに処理
		for (HolidayRequestDtoInterface dto : dtos) {
			String personalId = dto.getPersonalId();
			Date acquisitionDate = dto.getHolidayAcquisitionDate();
			// 返却用Mapに既に存在する、付与日をキーとした休暇申請Mapを個人IDで取得する。
			Map<Date, List<HolidayRequestDtoInterface>> map = resultMap.get(personalId);
			if (map == null) {
				// 返却用Mapに存在しない場合、新規で作成。
				map = new TreeMap<Date, List<HolidayRequestDtoInterface>>();
			}
			// 付与日をキーとした休暇申請Mapから、対象休暇申請の付与日と同じ付与日の休暇申請リストを取得。
			List<HolidayRequestDtoInterface> list = map.get(acquisitionDate);
			if (list == null) {
				// 存在しない場合、新規にリストを作成。
				list = new ArrayList<HolidayRequestDtoInterface>();
			}
			// 対象休暇申請をリストに追加
			list.add(dto);
			// 返却用Mapに追加。
			map.put(acquisitionDate, list);
			resultMap.put(personalId, map);
		}
		return resultMap;
	}
	
}
