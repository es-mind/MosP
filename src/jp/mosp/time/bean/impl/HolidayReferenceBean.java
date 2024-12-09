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
import java.util.Set;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.time.bean.HolidayReferenceBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.constant.TimeFileConst;
import jp.mosp.time.dao.settings.HolidayDaoInterface;
import jp.mosp.time.dto.settings.HolidayDtoInterface;
import jp.mosp.time.utils.TimeNamingUtility;

/**
 * 休暇種別管理参照クラス。
 */
public class HolidayReferenceBean extends PlatformBean implements HolidayReferenceBeanInterface {
	
	/**
	 * 休暇種別管理DAO。
	 */
	protected HolidayDaoInterface dao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public HolidayReferenceBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		dao = createDaoInstance(HolidayDaoInterface.class);
	}
	
	@Override
	public HolidayDtoInterface getHolidayInfo(String holidayCode, Date targetDate, int holidayType)
			throws MospException {
		return dao.findForInfo(holidayCode, targetDate, holidayType);
	}
	
	@Override
	public List<HolidayDtoInterface> getHolidayHistory(String holidayCode, int holidayType) throws MospException {
		return dao.findForHistory(holidayCode, holidayType);
	}
	
	@Override
	public List<HolidayDtoInterface> getHolidayList(Date targetDate, int holidayType) throws MospException {
		return dao.findForActivateDate(targetDate, holidayType);
	}
	
	@Override
	public String[][] getSelectArray(Date targetDate, int holidayType, boolean needBlank) throws MospException {
		// プルダウン用配列(略称)取得
		return getSelectArray(targetDate, holidayType, true, needBlank);
	}
	
	@Override
	public String[][] getSelectArray(Date targetDate, int holidayType, boolean isAbbr, boolean needBlank)
			throws MospException {
		// 一覧取得
		List<HolidayDtoInterface> list = dao.findForActivateDate(targetDate, holidayType);
		// 一覧件数確認
		if (list.isEmpty()) {
			// 対象データ無し
			return getNoObjectDataPulldown();
		}
		// プルダウン用配列及びインデックス準備
		String[][] array = prepareSelectArray(list.size(), needBlank);
		int idx = needBlank ? 1 : 0;
		// 配列作成
		for (HolidayDtoInterface dto : list) {
			array[idx][0] = dto.getHolidayCode();
			array[idx][1] = isAbbr ? dto.getHolidayAbbr() : dto.getHolidayName();
			idx++;
		}
		return array;
	}
	
	@Override
	public String[][] getExportArray(Date targetDate) throws MospException {
		// 一覧取得
		List<HolidayDtoInterface> specialList = dao.findForExport(targetDate, TimeConst.CODE_HOLIDAYTYPE_SPECIAL);
		List<HolidayDtoInterface> otherList = dao.findForExport(targetDate, TimeConst.CODE_HOLIDAYTYPE_OTHER);
		List<HolidayDtoInterface> absenceList = dao.findForExport(targetDate, TimeConst.CODE_HOLIDAYTYPE_ABSENCE);
		// 一つのリストに設定
		List<HolidayDtoInterface> holidayList = new ArrayList<HolidayDtoInterface>(specialList);
		holidayList.addAll(otherList);
		holidayList.addAll(absenceList);
		// 一覧件数確認
		if (MospUtility.isEmpty(holidayList)) {
			// 対象データ無し
			return prepareSelectArray(0, false);
		}
		String comma = SEPARATOR_DATA;
		String allHoliday = TimeNamingUtility.holidayRangeAllWithParentheses(mospParams);
		String halfHoliday = TimeNamingUtility.holidayHalfWithParentheses(mospParams);
		String hourHoliday = TimeNamingUtility.hourlyHolidayAbbrWithParentheses(mospParams);
		String amHoliday = TimeNamingUtility.anteMeridiemWithParentheses(mospParams);
		String pmHoliday = TimeNamingUtility.postMeridiemWithParentheses(mospParams);
		// 配列準備
		List<String[]> arrays = new ArrayList<String[]>();
		// 配列作成
		for (HolidayDtoInterface dto : holidayList) {
			// 配列を準備
			String[] arrayAll = new String[2];
			String[] arrayHalf = new String[2];
			String[] arrayHour = new String[2];
			String[] arrayAm = new String[2];
			String[] arrayPm = new String[2];
			// コードを準備
			StringBuffer sb = new StringBuffer();
			sb.append(dto.getHolidayType());
			sb.append(comma);
			sb.append(dto.getHolidayCode());
			sb.append(comma);
			// 配列に値を設定
			arrayAll[0] = sb.toString() + TimeFileConst.FIELD_ALL;
			arrayAll[1] = dto.getHolidayAbbr() + allHoliday;
			arrayHalf[0] = sb.toString() + TimeFileConst.FIELD_HALF;
			arrayHalf[1] = dto.getHolidayAbbr() + halfHoliday;
			arrayHour[0] = sb.toString() + TimeFileConst.FIELD_HOUR;
			arrayHour[1] = dto.getHolidayAbbr() + hourHoliday;
			arrayAm[0] = sb.toString() + TimeFileConst.FIELD_AM;
			arrayAm[1] = dto.getHolidayAbbr() + amHoliday;
			arrayPm[0] = sb.toString() + TimeFileConst.FIELD_PM;
			arrayPm[1] = dto.getHolidayAbbr() + pmHoliday;
			// 配列をリストに追加
			arrays.add(arrayAll);
			arrays.add(arrayHalf);
			arrays.add(arrayHour);
			arrays.add(arrayAm);
			arrays.add(arrayPm);
		}
		// 追加業務ロジック処理(休暇種別情報参照処理：エクスポート項目用配列取得)
		doStoredLogic(TimeConst.CODE_KEY_ADD_HOLIDAYREFERENCEBEAN_GETEXPORTARRAY, arrays);
		// エクスポート項目用配列を取得
		return MospUtility.toArrayArray(arrays);
	}
	
	@Override
	public HolidayDtoInterface findForKey(String holidayCode, Date activateDate, int holidayType) throws MospException {
		return dao.findForKey(holidayCode, activateDate, holidayType);
	}
	
	@Override
	public String getHolidayAbbr(String holidayCode, Date targetDate, int holidayType) throws MospException {
		HolidayDtoInterface dto = dao.findForInfo(holidayCode, targetDate, holidayType);
		if (dto == null) {
			return holidayCode;
		}
		return dto.getHolidayAbbr();
	}
	
	@Override
	public String getHolidayType1NameForHolidayRequest(int type1, String type2) {
		if (type1 == TimeConst.CODE_HOLIDAYTYPE_HOLIDAY) {
			if (Integer.toString(TimeConst.CODE_HOLIDAYTYPE_HOLIDAY).equals(type2)) {
				// 有給休暇
				StringBuffer sb = new StringBuffer();
				sb.append(mospParams.getName("Salaried"));
				sb.append(mospParams.getName("Holiday"));
				return sb.toString();
			} else if (Integer.toString(TimeConst.CODE_HOLIDAYTYPE_STOCK).equals(type2)) {
				// ストック休暇
				StringBuffer sb = new StringBuffer();
				sb.append(mospParams.getName("Stock"));
				sb.append(mospParams.getName("Holiday"));
				return sb.toString();
			}
		} else if (type1 == TimeConst.CODE_HOLIDAYTYPE_SPECIAL) {
			// 特別休暇
			StringBuffer sb = new StringBuffer();
			sb.append(mospParams.getName("Specially"));
			return sb.toString();
		} else if (type1 == TimeConst.CODE_HOLIDAYTYPE_OTHER) {
			// その他休暇
			return mospParams.getName("Others");
		} else if (type1 == TimeConst.CODE_HOLIDAYTYPE_ABSENCE) {
			// 欠勤
			return mospParams.getName("Absence");
		}
		return "";
	}
	
	@Override
	public boolean isTimelyHoliday(Date activateDate) throws MospException {
		// 有効な休暇種別を取得
		Set<HolidayDtoInterface> set = dao.findForActivateDate(activateDate);
		// 有効な休暇種別情報毎に処理
		for (HolidayDtoInterface dto : set) {
			// 時間休利用の場合
			if (dto.getTimelyHolidayFlag() == MospConst.INACTIVATE_FLAG_OFF) {
				return true;
			}
		}
		return false;
	}
	
}
