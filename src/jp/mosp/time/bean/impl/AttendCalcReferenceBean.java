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

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.time.base.TimeBean;
import jp.mosp.time.bean.AttendCalcReferenceBeanInterface;
import jp.mosp.time.bean.RequestUtilBeanInterface;
import jp.mosp.time.bean.TimeMasterBeanInterface;
import jp.mosp.time.bean.TimeSettingReferenceBeanInterface;
import jp.mosp.time.bean.WorkTypeReferenceBeanInterface;
import jp.mosp.time.dto.settings.ApplicationDtoInterface;
import jp.mosp.time.entity.RequestEntityInterface;
import jp.mosp.time.entity.TimeSettingEntityInterface;
import jp.mosp.time.entity.WorkTypeEntityInterface;

/**
 * 勤怠計算(日々)関連情報取得処理。<br>
 */
public class AttendCalcReferenceBean extends TimeBean implements AttendCalcReferenceBeanInterface {
	
	/**
	 * 勤怠関連マスタ参照処理。<br>
	 */
	protected TimeMasterBeanInterface								timeMaster;
	
	/**
	 * 勤怠設定参照処理。<br>
	 */
	protected TimeSettingReferenceBeanInterface						timeSettingRefer;
	
	/**
	 * 勤務形態参照処理。<br>
	 */
	protected WorkTypeReferenceBeanInterface						workTypeRefer;
	
	/**
	 * 設定適用情報群(キー：個人ID、勤務日)。<br>
	 */
	protected Map<String, Map<Date, ApplicationDtoInterface>>		applicationMap;
	
	/**
	 * 勤怠設定エンティティ群(キー：個人ID、勤務日)。<br>
	 */
	protected Map<String, Map<Date, TimeSettingEntityInterface>>	timeSettingMap;
	
	/**
	 * 勤務形態エンティティ群(キー：個人ID、勤務日)。<br>
	 */
	protected Map<String, Map<Date, WorkTypeEntityInterface>>		workTypeMap;
	
	/**
	 * 申請エンティティ群(キー：個人ID、勤務日)。<br>
	 */
	protected Map<String, Map<Date, RequestEntityInterface>>		requestMap;
	
	/**
	 * 勤務形態(翌日)コード群(キー：個人ID、勤務日)。<br>
	 * 勤務日の翌日の予定勤務形態コードを保持する。<br>
	 * <br>
	 */
	protected Map<String, Map<Date, String>>						nextWorkTypeMap;
	
	
	@Override
	public void initBean() throws MospException {
		// Beanを準備
		timeMaster = createBeanInstance(TimeMasterBeanInterface.class);
		timeSettingRefer = createBeanInstance(TimeSettingReferenceBeanInterface.class);
		workTypeRefer = createBeanInstance(WorkTypeReferenceBeanInterface.class);
		// 情報群を初期化
		applicationMap = new HashMap<String, Map<Date, ApplicationDtoInterface>>();
		timeSettingMap = new HashMap<String, Map<Date, TimeSettingEntityInterface>>();
		workTypeMap = new HashMap<String, Map<Date, WorkTypeEntityInterface>>();
		requestMap = new HashMap<String, Map<Date, RequestEntityInterface>>();
		nextWorkTypeMap = new HashMap<String, Map<Date, String>>();
	}
	
	@Override
	public TimeSettingEntityInterface getTimeSetting(String personalId, Date workDate) throws MospException {
		// 勤怠設定エンティティを勤怠設定エンティティ群から取得
		TimeSettingEntityInterface timeSetting = getObject(timeSettingMap, personalId, workDate);
		// 勤怠設定エンティティが取得できた場合
		if (timeSetting != null) {
			// 勤怠設定エンティティを取得
			return timeSetting;
		}
		// 設定適用情報を取得
		ApplicationDtoInterface application = getApplication(personalId, workDate);
		// 勤怠設定コードを準備
		String timeSettingCode = MospConst.STR_EMPTY;
		// 設定適用情報が取得できた場合
		if (application != null) {
			// 勤怠設定コードを設定
			timeSettingCode = application.getWorkSettingCode();
		}
		// 勤怠設定エンティティを取得し設定
		timeSetting = timeSettingRefer.getEntity(timeSettingCode, workDate);
		setObject(timeSettingMap, personalId, workDate, timeSetting);
		// 勤怠設定エンティティを取得
		return timeSetting;
	}
	
	@Override
	public WorkTypeEntityInterface getWorkType(String personalId, Date workDate, String workTypeCode)
			throws MospException {
		// 勤務形態エンティティを勤務形態エンティティ群から取得
		WorkTypeEntityInterface workType = getObject(workTypeMap, personalId, workDate);
		// 勤務形態エンティティが取得できた場合
		if (workType != null) {
			// 勤務形態エンティティを取得
			return workType;
		}
		// 勤務形態エンティティを取得し設定
		workType = workTypeRefer.getWorkTypeEntity(workTypeCode, workDate);
		setObject(workTypeMap, personalId, workDate, workType);
		// 勤務形態エンティティを取得
		return workType;
	}
	
	@Override
	public RequestEntityInterface getRequest(String personalId, Date workDate) throws MospException {
		// 申請エンティティを申請エンティティ群から取得
		RequestEntityInterface request = getObject(requestMap, personalId, workDate);
		// 申請エンティティが取得できた場合
		if (request != null) {
			// 申請エンティティを取得
			return request;
		}
		// 申請ユーティリティを取得
		RequestUtilBeanInterface requestUtil = createBeanInstance(RequestUtilBeanInterface.class);
		// 申請ユーティリティに勤怠関連マスタ参照処理を設定
		requestUtil.setTimeMaster(timeMaster);
		// 申請エンティティを取得し設定
		request = requestUtil.getRequestEntity(personalId, workDate);
		setObject(requestMap, personalId, workDate, request);
		// 申請エンティティを取得
		return request;
	}
	
	@Override
	public String getNextWorkType(String personalId, Date workDate) throws MospException {
		// 勤務形態(翌日)コードを勤務形態(翌日)コードから取得
		String nextWorkType = getObject(nextWorkTypeMap, personalId, workDate);
		// 勤務形態(翌日)コードが取得できた場合
		if (MospUtility.isEmpty(nextWorkType) == false) {
			// 勤務形態(翌日)コードを取得
			return nextWorkType;
		}
		// 翌日を取得
		Date nextDate = addDay(workDate, 1);
		// 勤務形態(翌日)コードを取得し設定
		nextWorkType = getRequest(personalId, nextDate).getWorkType(true, true);
		setObject(nextWorkTypeMap, personalId, workDate, nextWorkType);
		// 勤務形態(翌日)コードを取得
		return nextWorkType;
	}
	
	/**
	 * 設定適用情報を取得する。<br>
	 * <br>
	 * @param personalId 個人ID
	 * @param workDate   勤務日
	 * @return 設定適用情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected ApplicationDtoInterface getApplication(String personalId, Date workDate) throws MospException {
		// 設定適用情報を取得
		ApplicationDtoInterface application = getObject(applicationMap, personalId, workDate);
		// 設定適用情報が取得できなかった場合
		if (application == null) {
			// 設定適用情報をDBから取得し設定
			application = timeMaster.getApplication(personalId, workDate);
			setObject(applicationMap, personalId, workDate, application);
		}
		// 設定適用情報を取得
		return application;
	}
	
	/**
	 * 情報群から情報を取得する。<br>
	 * @param map        情報群(キー：個人ID、勤務日)
	 * @param personalId 個人ID
	 * @param workDate   勤務日
	 * @return 情報
	 */
	protected <T> T getObject(Map<String, Map<Date, T>> map, String personalId, Date workDate) {
		// 個人IDで情報群を取得
		Map<Date, T> personalMap = map.get(personalId);
		// 個人IDで情報群を取得できなかった場合
		if (personalMap == null) {
			// nullを取得
			return null;
		}
		// 勤務日で情報を取得
		return personalMap.get(workDate);
	}
	
	/**
	 * 情報群に情報を設定する。<br>
	 * @param map        情報群(キー：個人ID、勤務日)
	 * @param personalId 個人ID
	 * @param workDate   勤務日
	 * @param obj        情報
	 */
	protected <T> void setObject(Map<String, Map<Date, T>> map, String personalId, Date workDate, T obj) {
		// 個人IDで情報群を取得
		Map<Date, T> personalMap = map.get(personalId);
		// 個人IDで情報群を取得できなかった場合
		if (personalMap == null) {
			// 情報群を初期化し追加
			personalMap = new HashMap<Date, T>();
			map.put(personalId, personalMap);
		}
		// 情報群に情報を設定
		personalMap.put(workDate, obj);
	}
	
}
