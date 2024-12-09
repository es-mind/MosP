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

import java.io.InputStream;
import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.bean.file.ImportBeanInterface;
import jp.mosp.platform.bean.file.PlatformFileBean;
import jp.mosp.platform.dto.file.ImportDtoInterface;
import jp.mosp.platform.dto.file.ImportFieldDtoInterface;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.platform.utils.PfNameUtility;
import jp.mosp.time.bean.HolidayRequestExecuteBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.constant.TimeFileConst;
import jp.mosp.time.dao.settings.impl.TmdHolidayRequestDao;
import jp.mosp.time.utils.TimeMessageUtility;

/**
 * 休暇申請インポートクラス。<br>
 */
public class HolidayRequestImportBean extends PlatformFileBean implements ImportBeanInterface {
	
	/**
	 * 休暇申請実行処理。<br>
	 */
	protected HolidayRequestExecuteBeanInterface holidayRequestExecute;
	
	
	/**
	 * {@link PlatformFileBean#PlatformFileBean()}を実行する。<br>
	 */
	public HolidayRequestImportBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// Beanを準備
		holidayRequestExecute = createBeanInstance(HolidayRequestExecuteBeanInterface.class);
	}
	
	@Override
	public int importFile(ImportDtoInterface importDto, InputStream requestedFile) throws MospException {
		// アップロードファイルを登録情報リストに変換
		List<String[]> dataList = getDataList(importDto, requestedFile);
		if (mospParams.hasErrorMessage()) {
			return 0;
		}
		// インポートフィールド情報リストを取得
		List<ImportFieldDtoInterface> fieldList = getImportFieldList(importDto.getImportCode());
		if (mospParams.hasErrorMessage()) {
			return 0;
		}
		// 登録情報リスト内の各登録情報長を確認
		checkCsvLength(fieldList, dataList);
		if (mospParams.hasErrorMessage()) {
			return 0;
		}
		// 社員コードを個人IDに変換
		convertEmployeeCodeIntoPersonalId(fieldList, dataList, TmdHolidayRequestDao.COL_REQUEST_START_DATE,
				PfNameUtility.activateDate(mospParams));
		if (mospParams.hasErrorMessage()) {
			return 0;
		}
		// インポート処理
		return importFile(fieldList, dataList);
	}
	
	/**
	 * インポート処理を行う。<br>
	 * 登録情報リストを、インポートフィールド情報リストに基づき
	 * 休暇申請情報に変換し、登録を行う。<br>
	 * @param fieldList インポートフィールド情報リスト
	 * @param dataList 登録情報リスト
	 * @return 登録件数
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected int importFile(List<ImportFieldDtoInterface> fieldList, List<String[]> dataList) throws MospException {
		int count = 0;
		for (int i = 0; i < dataList.size(); i++) {
			// インポート
			count += importData(fieldList, dataList.get(i), i);
		}
		// 登録件数取得
		return count;
	}
	
	/**
	 * 休暇申請情報をインポートする。<br>
	 * @param fieldList インポートフィールド情報リスト
	 * @param data      CSVデータ
	 * @param row       行インデックス
	 * @return 登録結果(0：インポートできなかった場合、1：インポートできた場合)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected int importData(List<ImportFieldDtoInterface> fieldList, String[] data, int row) throws MospException {
		// 日付を確認
		checkDate(fieldList, data, row);
		// 日付が妥当でない場合
		if (mospParams.hasErrorMessage()) {
			// 処理終了
			return 0;
		}
		// CSVデータから休暇申請の内容を取得
		String personalId = getFieldValue(TmdHolidayRequestDao.COL_PERSONAL_ID, fieldList, data);
		Date requestStartDate = getDateFieldValue(TmdHolidayRequestDao.COL_REQUEST_START_DATE, fieldList, data);
		Date requestEndDate = getDateFieldValue(TmdHolidayRequestDao.COL_REQUEST_END_DATE, fieldList, data);
		int holidayType1 = getIntegerFieldValue(TmdHolidayRequestDao.COL_HOLIDAY_TYPE1, fieldList, data);
		String holidayType2 = getFieldValue(TmdHolidayRequestDao.COL_HOLIDAY_TYPE2, fieldList, data);
		int holidayRange = getIntegerFieldValue(TmdHolidayRequestDao.COL_HOLIDAY_RANGE, fieldList, data);
		Date startTime = getTimestampFieldValue(TmdHolidayRequestDao.COL_START_TIME, fieldList, data);
		String requestReason = getFieldValue(TmdHolidayRequestDao.COL_REQUEST_REASON, fieldList, data);
		int hours = 0;
		// 申請終了日が存在しない場合
		if (MospUtility.isEmpty(requestEndDate)) {
			// 申請終了日に申請開始日を設定
			requestEndDate = requestStartDate;
		}
		// 半休である場合
		if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_AM || holidayRange == TimeConst.CODE_HOLIDAY_RANGE_PM) {
			// 申請終了日に申請開始日を設定(半休の連続取得は不可)
			requestEndDate = requestStartDate;
		}
		// 時間休である場合
		if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_TIME) {
			// 時休開始時刻が取得できなかった場合
			if (MospUtility.isEmpty(startTime)) {
				// エラーメッセージを設定
				String fieldName = mospParams.getProperties().getCodeItemName(
						TimeFileConst.CODE_IMPORT_TYPE_TMD_HOLIDAY_REQUEST, TmdHolidayRequestDao.COL_START_TIME);
				PfMessageUtility.addErrorRequired(mospParams, fieldName, row);
				// 処理終了
				return 0;
			}
			// 申請開始日及び申請終了日を設定(時休開始時刻の年月日とする)(24:00超の時間休のインポートは不可)
			requestStartDate = DateUtility.getDate(startTime);
			requestEndDate = requestStartDate;
			// 時休時間数に1を設定(時間単位休暇は1時間単位で申請)
			hours = 1;
		}
		// 申請(自己承認)
		holidayRequestExecute.apply(personalId, requestStartDate, requestEndDate, holidayType1, holidayType2,
				holidayRange, startTime, hours, requestReason, row);
		// 処理結果を確認
		if (mospParams.hasErrorMessage()) {
			// 処理終了
			return 0;
		}
		// 1を取得
		return 1;
	}
	
	/**
	 * 登録情報からフィールド(日付)に対応する値を取得する。<br>
	 * 取得できなかった場合、nullを返す。<br>
	 * @param fieldName フィールド名
	 * @param fieldList インポートフィールド情報リスト
	 * @param data      CSVデータ
	 * @return 値(日付)
	 */
	protected Date getTimestampFieldValue(String fieldName, List<ImportFieldDtoInterface> fieldList, String[] data) {
		// 登録情報から日付文字列を取得し日付に変換
		return DateUtility.getVariousDate(getFieldValue(fieldName, fieldList, data));
	}
	
	/**
	 * 日付チェック。<br>
	 * @param fieldList インポートフィールド情報リスト
	 * @param data      CSVデータ
	 * @param row       行インデックス
	 */
	protected void checkDate(List<ImportFieldDtoInterface> fieldList, String[] data, int row) {
		// CSVデータから申請開始日と申請終了日を取得
		Date requestStartDate = getDateFieldValue(TmdHolidayRequestDao.COL_REQUEST_START_DATE, fieldList, data);
		Date requestEndDate = getDateFieldValue(TmdHolidayRequestDao.COL_REQUEST_END_DATE, fieldList, data);
		// CSVデータから申請開始日を取得できなかった場合
		if (MospUtility.isEmpty(requestStartDate)) {
			// エラーメッセージを設定
			PfMessageUtility.addErrorRequired(mospParams, PfNameUtility.term(mospParams), row);
			// 処理終了
			return;
		}
		// 日付の順序が正しくない場合
		if (checkDateOrder(requestStartDate, requestEndDate, true) == false) {
			// エラーメッセージを設定
			TimeMessageUtility.addErrorHolidayOrderInvalid(mospParams, row);
		}
	}
	
}
