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
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.framework.utils.NameUtility;
import jp.mosp.orangesignal.OrangeSignalUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.comparator.base.EmployeeCodeComparator;
import jp.mosp.platform.utils.MonthUtility;
import jp.mosp.platform.utils.PfNameUtility;
import jp.mosp.platform.utils.PlatformUtility;
import jp.mosp.time.bean.PaidHolidayUsageBeanInterface;
import jp.mosp.time.bean.PaidHolidayUsageExportBeanInterface;
import jp.mosp.time.comparator.report.PaidHolidayUsageComparator;
import jp.mosp.time.dto.settings.impl.PaidHolidayUsageDto;
import jp.mosp.time.utils.TimeNamingUtility;
import jp.mosp.time.utils.TimeUtility;

/**
 * 有給休暇取得状況確認情報出力処理。<br>
 */
public class PaidHolidayUsageExportBean extends PlatformBean implements PaidHolidayUsageExportBeanInterface {
	
	/**
	 * 出力ファイル名。<br>
	 */
	protected static final String			NAM_EXPORT_FILE	= "paidHolidayUsage.csv";
	
	/**
	 * 有給休暇取得状況確認情報作成処理。<br>
	 */
	protected PaidHolidayUsageBeanInterface	paidHolidayUsage;
	
	
	@Override
	public void initBean() throws MospException {
		// Beanを準備
		paidHolidayUsage = createBeanInstance(PaidHolidayUsageBeanInterface.class);
	}
	
	@Override
	public void export(String[] personalIds, int year, int month) throws MospException {
		// 対象日を取得
		Date targetDate = MonthUtility.getYearMonthLastDate(year, month);
		// 有給休暇取得状況確認情報を出力
		export(personalIds, targetDate);
	}
	
	@Override
	public void export(String[] personalIds, Date targetDate) throws MospException {
		// 有給休暇取得状況確認情報リストを準備
		List<PaidHolidayUsageDto> paidHolidayUsages = new ArrayList<PaidHolidayUsageDto>();
		// 個人ID毎に処理
		for (String personalId : personalIds) {
			// 有給休暇取得状況確認情報を作成しCSVデータリストに追加
			paidHolidayUsages.addAll(paidHolidayUsage.makePaidHolidayUsages(personalId, targetDate));
		}
		// ソート
		Collections.sort(paidHolidayUsages, new EmployeeCodeComparator());
		Collections.sort(paidHolidayUsages, new PaidHolidayUsageComparator());
		// 有給休暇取得状況確認情報リストをCSVデータリストに変換
		List<String[]> csvs = makeCsvs(paidHolidayUsages);
		// CSVデータリストをMosP処理情報に設定
		mospParams.setFile(OrangeSignalUtility.getOrangeSignalParams(csvs));
		// ファイル名設定
		mospParams.setFileName(PlatformUtility.getExportFileName(mospParams, NAM_EXPORT_FILE, targetDate, true));
	}
	
	/**
	 * 有給休暇取得状況確認情報CSVデータリストを作成する。<br>
	 * @param paidHolidayUsages 有給休暇取得状況確認情報リスト
	 * @return 有給休暇取得状況確認情報CSVデータリスト
	 */
	protected List<String[]> makeCsvs(List<PaidHolidayUsageDto> paidHolidayUsages) {
		// CSVデータリストを準備
		List<String[]> csvs = new ArrayList<String[]>();
		// ヘッダを追加
		csvs.add(makeCsvHeader());
		// 有給休暇取得状況確認情報毎に処理
		for (PaidHolidayUsageDto paidHolidayUsage : paidHolidayUsages) {
			// 有給休暇取得状況確認情報CSVデータを作成しCSVデータリストに追加
			csvs.add(makeCsv(paidHolidayUsage));
		}
		// CSVデータリストを取得
		return csvs;
	}
	
	/**
	 * 対象期間の文字列を取得する。<br>
	 * @param paidHolidayUsage 有給休暇取得状況確認情報
	 * @return 対象期間の文字列
	 */
	protected String getUsageTermString(PaidHolidayUsageDto paidHolidayUsage) {
		// 対象期間を準備
		StringBuilder sb = new StringBuilder();
		sb.append(DateUtility.getStringDate(paidHolidayUsage.getUsageFromDate()));
		sb.append(NameUtility.wave(mospParams));
		sb.append(DateUtility.getStringDate(paidHolidayUsage.getUsageToDate()));
		// 対象期間を取得
		return sb.toString();
	}
	
	/**
	 * 申請日の文字列を取得する。<br>
	 * @param paidHolidayUsage 有給休暇取得状況確認情報
	 * @return 申請日の文字列
	 */
	protected String getUseDatesString(PaidHolidayUsageDto paidHolidayUsage) {
		// 申請日を準備
		StringBuilder sb = new StringBuilder();
		// 申請日(キー：申請日、値：休暇範囲)毎に処理
		for (Entry<Date, Integer> entry : paidHolidayUsage.getUseDates().entrySet()) {
			// 申請日を追記
			sb.append(getStringDate(entry.getKey()));
			sb.append(MospConst.STR_SB_SPACE);
			sb.append(TimeUtility.getHolidayRangeAbbr(mospParams, entry.getValue()));
			sb.append(MospConst.APP_PROPERTY_SEPARATOR);
		}
		// 最後の文字(カンマ)を除去
		MospUtility.deleteLastChar(sb);
		// 申請日を取得
		return sb.toString();
	}
	
	/**
	 * 有給休暇取得状況確認情報CSVデータを作成する。<br>
	 * @param paidHolidayUsage 有給休暇取得状況確認情報
	 * @return 有給休暇取得状況確認情報CSVデータ
	 */
	protected String[] makeCsv(PaidHolidayUsageDto paidHolidayUsage) {
		// CSVデータを準備
		String[] csv = new String[10];
		// CSVデータを作成
		csv[0] = paidHolidayUsage.getEmployeeCode();
		csv[1] = MospUtility.getHumansName(paidHolidayUsage.getFirstName(), paidHolidayUsage.getLastName());
		csv[2] = paidHolidayUsage.getSectionName();
		csv[3] = getUsageTermString(paidHolidayUsage);
		csv[4] = String.valueOf(paidHolidayUsage.getShortDay());
		csv[5] = DateUtility.getStringDate(paidHolidayUsage.getAcquisitionDate());
		csv[6] = String.valueOf(paidHolidayUsage.getGivingDay());
		csv[7] = String.valueOf(paidHolidayUsage.getUseDay());
		csv[8] = getUseDatesString(paidHolidayUsage);
		csv[9] = paidHolidayUsage.getRemark();
		// CSVデータを取得
		return csv;
	}
	
	/**
	 * 有給休暇取得状況確認情報CSVデータリスト用ヘッダを作成する。<br>
	 * <ul>
	 * <li>0：社員コード</li>
	 * <li>1：氏名</li>
	 * <li>2：所属名称</li>
	 * <li>3：対象期間</li>
	 * <li>4：未消化日数(合算)</li>
	 * <li>5：取得日</li>
	 * <li>6：付与日数</li>
	 * <li>7：申請日数</li>
	 * <li>8：申請日</li>
	 * <li>9：備考</li>
	 * </ul>
	 * @return 有給休暇取得状況確認情報CSVデータリスト用ヘッダ
	 */
	protected String[] makeCsvHeader() {
		// ヘッダを準備
		String[] header = new String[10];
		// ヘッダを作成
		header[0] = PfNameUtility.employeeCode(mospParams);
		header[1] = PfNameUtility.fullName(mospParams);
		header[2] = PfNameUtility.sectionName(mospParams);
		header[3] = PfNameUtility.targetTerm(mospParams);
		header[4] = TimeNamingUtility.paidHolidayUsageShortTotal(mospParams);
		header[5] = TimeNamingUtility.acquisitionDate(mospParams);
		header[6] = TimeNamingUtility.givingDays(mospParams);
		header[7] = TimeNamingUtility.appliedDays(mospParams);
		header[8] = TimeNamingUtility.applicationDate(mospParams);
		header[9] = PfNameUtility.remarks(mospParams);
		// ヘッダを取得
		return header;
	}
	
}
