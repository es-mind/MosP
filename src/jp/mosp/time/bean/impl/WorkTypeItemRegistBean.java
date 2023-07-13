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
/**
 * 
 */
package jp.mosp.time.bean.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.CapsuleUtility;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.time.bean.WorkTypeItemRegistAddonBeanInterface;
import jp.mosp.time.bean.WorkTypeItemRegistBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dao.settings.WorkTypeDaoInterface;
import jp.mosp.time.dao.settings.WorkTypeItemDaoInterface;
import jp.mosp.time.dto.settings.WorkTypeDtoInterface;
import jp.mosp.time.dto.settings.WorkTypeItemDtoInterface;
import jp.mosp.time.dto.settings.impl.TmmWorkTypeItemDto;
import jp.mosp.time.entity.TimeDuration;
import jp.mosp.time.entity.WorkTypeEntity;
import jp.mosp.time.utils.TimeMessageUtility;
import jp.mosp.time.utils.TimeNamingUtility;
import jp.mosp.time.utils.TimeUtility;

/**
 * 勤務形態項目登録クラス。
 */
public class WorkTypeItemRegistBean extends PlatformBean implements WorkTypeItemRegistBeanInterface {
	
	/**
	 * 勤務形態項目マスタDAOクラス。<br>
	 */
	protected WorkTypeItemDaoInterface						dao;
	
	/**
	 * 勤務形態マスタDAOクラス。<br>
	 */
	protected WorkTypeDaoInterface							workTypeDao;
	
	/**
	 * 勤務形態項目配列。<br>
	 */
	protected String[]										codesWorkTypeItem	= { TimeConst.CODE_WORKSTART,
		TimeConst.CODE_WORKEND, TimeConst.CODE_WORKTIME, TimeConst.CODE_RESTTIME, TimeConst.CODE_RESTSTART1,
		TimeConst.CODE_RESTEND1, TimeConst.CODE_RESTSTART2, TimeConst.CODE_RESTEND2, TimeConst.CODE_RESTSTART3,
		TimeConst.CODE_RESTEND3, TimeConst.CODE_RESTSTART4, TimeConst.CODE_RESTEND4, TimeConst.CODE_FRONTSTART,
		TimeConst.CODE_FRONTEND, TimeConst.CODE_BACKSTART, TimeConst.CODE_BACKEND, TimeConst.CODE_OVERBEFORE,
		TimeConst.CODE_OVERPER, TimeConst.CODE_OVERREST, TimeConst.CODE_HALFREST, TimeConst.CODE_HALFRESTSTART,
		TimeConst.CODE_HALFRESTEND, TimeConst.CODE_WORK_TYPE_ITEM_DIRECT_START,
		TimeConst.CODE_WORK_TYPE_ITEM_DIRECT_END, TimeConst.CODE_WORK_TYPE_ITEM_EXCLUDE_NIGHT_REST,
		TimeConst.CODE_WORK_TYPE_ITEM_SHORT1_START, TimeConst.CODE_WORK_TYPE_ITEM_SHORT1_END,
		TimeConst.CODE_WORK_TYPE_ITEM_SHORT2_START, TimeConst.CODE_WORK_TYPE_ITEM_SHORT2_END,
		TimeConst.CODE_AUTO_BEFORE_OVERWORK };
	
	/**
	 * 項目長(勤務形態コード)。<br>
	 */
	protected static final int								LEN_WORK_TYPE_CODE	= 10;
	
	/**
	 * 勤務形態項目登録追加処理リスト。<br>
	 */
	protected List<WorkTypeItemRegistAddonBeanInterface>	addonBeans;
	
	/**
	 * コードキー(勤務形態項目登録追加処理)。<br>
	 */
	protected static final String							CODE_KEY_ADDONS		= "WorkTypeItemRegistAddons";
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public WorkTypeItemRegistBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// DAO準備
		dao = createDaoInstance(WorkTypeItemDaoInterface.class);
		workTypeDao = createDaoInstance(WorkTypeDaoInterface.class);
		addonBeans = getAddonBeans();
	}
	
	@Override
	public WorkTypeItemDtoInterface getInitDto() {
		// DTOインスタンス作成
		WorkTypeItemDtoInterface dto = new TmmWorkTypeItemDto();
		// 追加処理群による処理
		for (WorkTypeItemRegistAddonBeanInterface addonBean : addonBeans) {
			// 追加処理による初期化DTO作成処理
			dto = addonBean.getInitDto(dto);
		}
		return dto;
	}
	
	@Override
	public void insert(WorkTypeItemDtoInterface dto) throws MospException {
		// DTO妥当性確認
		validate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 新規登録情報の検証
		checkInsert(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setTmmWorkTypeItemId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 追加処理群による処理
		for (WorkTypeItemRegistAddonBeanInterface addonBean : addonBeans) {
			// 追加処理による新規登録処理
			addonBean.insert(dto);
			// エラーが発生したら、そこで処理は終了
			if (mospParams.hasErrorMessage()) {
				return;
			}
		}
	}
	
	@Override
	public void insert(List<WorkTypeItemDtoInterface> dtoList) throws MospException {
		// 妥当性確認
		checkItemTimeValidate(dtoList);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 勤務形態項目情報毎に処理
		for (WorkTypeItemDtoInterface dto : dtoList) {
			// DTO妥当性確認
			validate(dto);
			if (mospParams.hasErrorMessage()) {
				return;
			}
			// 新規登録情報の検証
			checkInsert(dto);
			if (mospParams.hasErrorMessage()) {
				return;
			}
			// レコード識別ID最大値をインクリメントしてDTOに設定
			dto.setTmmWorkTypeItemId(dao.nextRecordId());
			// 登録処理
			dao.insert(dto);
		}
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 追加処理群による処理
		for (WorkTypeItemRegistAddonBeanInterface addonBean : addonBeans) {
			// 追加処理による一覧の新規登録処理
			addonBean.insert(dtoList);
			// エラーが発生したら、そこで処理は終了
			if (mospParams.hasErrorMessage()) {
				return;
			}
		}
	}
	
	@Override
	public void add(List<WorkTypeItemDtoInterface> dtoList) throws MospException {
		// 妥当性確認
		checkItemTimeValidate(dtoList);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 勤務形態項目情報毎に処理
		for (WorkTypeItemDtoInterface dto : dtoList) {
			// DTO妥当性確認
			validate(dto);
			if (mospParams.hasErrorMessage()) {
				return;
			}
			// 履歴追加情報の検証
			checkAdd(dto);
			if (mospParams.hasErrorMessage()) {
				return;
			}
			// レコード識別ID最大値をインクリメントしてDTOに設定
			dto.setTmmWorkTypeItemId(dao.nextRecordId());
			// 登録処理
			dao.insert(dto);
		}
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 追加処理群による処理
		for (WorkTypeItemRegistAddonBeanInterface addonBean : addonBeans) {
			// 追加処理による一覧の履歴追加処理
			addonBean.add(dtoList);
			// エラーが発生したら、そこで処理は終了
			if (mospParams.hasErrorMessage()) {
				return;
			}
		}
	}
	
	@Override
	public void update(WorkTypeItemDtoInterface dto) throws MospException {
		// DTO妥当性確認
		validate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 新規追加項目の場合
		if (dto.getTmmWorkTypeItemId() == 0L) {
			// レコード識別ID最大値をインクリメントしてDTOに設定
			dto.setTmmWorkTypeItemId(dao.nextRecordId());
			// 登録処理
			dao.insert(dto);
		} else {
			// 履歴更新情報の検証
			checkUpdate(dto);
			if (mospParams.hasErrorMessage()) {
				return;
			}
			// 論理削除
			logicalDelete(dao, dto.getTmmWorkTypeItemId());
			// レコード識別ID最大値をインクリメントしてDTOに設定
			dto.setTmmWorkTypeItemId(dao.nextRecordId());
			// 登録処理
			dao.insert(dto);
		}
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 追加処理群による処理
		for (WorkTypeItemRegistAddonBeanInterface addonBean : addonBeans) {
			// 追加処理による履歴更新処理
			addonBean.update(dto);
			// エラーが発生したら、そこで処理は終了
			if (mospParams.hasErrorMessage()) {
				return;
			}
		}
	}
	
	@Override
	public void update(List<WorkTypeItemDtoInterface> dtoList) throws MospException {
		// 妥当性確認
		checkItemTimeValidate(dtoList);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 勤務形態項目情報毎に処理
		for (WorkTypeItemDtoInterface dto : dtoList) {
			// DTO妥当性確認
			validate(dto);
			if (mospParams.hasErrorMessage()) {
				return;
			}
			// 新規追加項目の場合
			if (dto.getTmmWorkTypeItemId() == 0L) {
				// レコード識別ID最大値をインクリメントしてDTOに設定
				dto.setTmmWorkTypeItemId(dao.nextRecordId());
				// 登録処理
				dao.insert(dto);
				continue;
			}
			// 履歴更新情報の検証
			checkUpdate(dto);
			if (mospParams.hasErrorMessage()) {
				return;
			}
			// 論理削除
			logicalDelete(dao, dto.getTmmWorkTypeItemId());
			// レコード識別ID最大値をインクリメントしてDTOに設定
			dto.setTmmWorkTypeItemId(dao.nextRecordId());
			// 登録処理
			dao.insert(dto);
		}
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 追加処理群による処理
		for (WorkTypeItemRegistAddonBeanInterface addonBean : addonBeans) {
			// 追加処理による一覧履歴更新処理
			addonBean.update(dtoList);
			// エラーが発生したら、そこで処理は終了
			if (mospParams.hasErrorMessage()) {
				return;
			}
		}
	}
	
	@Override
	public void update(long[] idArray, Date activateDate, int inactivateFlag) throws MospException {
		// レコード識別ID配列の妥当性確認
		validateAryId(idArray);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 一括更新処理
		for (String code : getCodeList(idArray)) {
			for (String workTypeItemCode : getCodesAdditionalWorkTypeItem()) {
				// 対象勤務形態項目における有効日の情報を取得
				WorkTypeItemDtoInterface dto = dao.findForKey(code, activateDate, workTypeItemCode);
				// 存在確認(存在しなければ履歴追加、存在すれば履歴更新)
				if (dto == null) {
					// 対象勤務形態項目における有効日以前で最新の情報を取得
					dto = dao.findForInfo(code, activateDate, workTypeItemCode);
					// 対象勤務形態項目情報確認
					if (dto == null) {
						// 新規追加項目の場合
						continue;
					}
					// DTOに有効日、無効フラグを設定
					dto.setActivateDate(activateDate);
					dto.setInactivateFlag(inactivateFlag);
					// DTO妥当性確認
					validate(dto);
					// 履歴追加情報の検証
					checkAdd(dto);
					if (mospParams.hasErrorMessage()) {
						// エラーが存在したら履歴追加処理をしない
						continue;
					}
					// レコード識別ID最大値をインクリメントしてDTOに設定
					dto.setTmmWorkTypeItemId(dao.nextRecordId());
					// 登録処理
					dao.insert(dto);
				} else {
					// DTOに無効フラグを設定
					dto.setInactivateFlag(inactivateFlag);
					// DTO妥当性確認
					validate(dto);
					// 履歴更新情報の検証
					checkUpdate(dto);
					if (mospParams.hasErrorMessage()) {
						// エラーが存在したら履歴更新処理をしない
						continue;
					}
					// 論理削除
					logicalDelete(dao, dto.getTmmWorkTypeItemId());
					// レコード識別ID最大値をインクリメントしてDTOに設定
					dto.setTmmWorkTypeItemId(dao.nextRecordId());
					// 登録処理
					dao.insert(dto);
				}
			}
		}
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 追加処理群による処理
		for (WorkTypeItemRegistAddonBeanInterface addonBean : addonBeans) {
			// 追加処理による新規登録処理
			addonBean.update(idArray, activateDate, inactivateFlag);
			// エラーが発生したら、そこで処理は終了
			if (mospParams.hasErrorMessage()) {
				return;
			}
		}
	}
	
	@Override
	public void delete(String workTypeCode, Date activateDate) throws MospException {
		for (String workTypeItemCode : getCodesAdditionalWorkTypeItem()) {
			WorkTypeItemDtoInterface dto = dao.findForKey(workTypeCode, activateDate, workTypeItemCode);
			if (dto == null) {
				continue;
			}
			// DTO妥当性確認
			validate(dto);
			if (mospParams.hasErrorMessage()) {
				return;
			}
			checkDelete(dto);
			if (mospParams.hasErrorMessage()) {
				continue;
			}
			// 論理削除
			logicalDelete(dao, dto.getTmmWorkTypeItemId());
		}
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 追加処理群による処理
		for (WorkTypeItemRegistAddonBeanInterface addonBean : addonBeans) {
			// 追加処理による一覧履歴更新処理
			addonBean.delete(workTypeCode, activateDate);
			// エラーが発生したら、そこで処理は終了
			if (mospParams.hasErrorMessage()) {
				return;
			}
		}
	}
	
	@Override
	public String[] getCodesWorkTypeItem() {
		return CapsuleUtility.getStringArrayClone(codesWorkTypeItem);
	}
	
	@Override
	public String[] getCodesAdditionalWorkTypeItem() {
		// コードキーから追加アイテムの配列を取得
		String[][] additionalWorktypeItemArray = mospParams.getProperties()
			.getCodeArray(TimeConst.CODE_KEY_ADDITIONAL_WORKTYPE_ITEM, false);
		// 追加アイテム用にリストを準備
		List<String> workTypeAdditionalItemList = new ArrayList<String>();
		// 追加アイテムリストの作成
		for (String[] validateAryId : additionalWorktypeItemArray) {
			// 追加アイテムをリストに追加
			workTypeAdditionalItemList.add(validateAryId[1]);
		}
		// 勤務形態項目リストの準備
		List<String> workTypeItemList = new ArrayList<String>();
		// 勤務形態項目リストに勤務形態項目を追加
		workTypeItemList.addAll(Arrays.asList(codesWorkTypeItem));
		// 勤務形態項目リストに追加アイテムリストを追加
		workTypeItemList.addAll(workTypeAdditionalItemList);
		// 勤務形態項目配列を返却
		return CapsuleUtility.getStringArrayClone(workTypeItemList.toArray(new String[workTypeItemList.size()]));
	}
	
	/**
	 * 勤務形態項目情報の妥当性確認を行う。<br>
	 * 時間チェック等。<br>
	 * @param itemList 勤務形態項目リスト
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkItemTimeValidate(List<WorkTypeItemDtoInterface> itemList) throws MospException {
		// マップ準備
		Map<String, Date> map = new HashMap<String, Date>();
		// デフォルト時刻取得
		Date defaultTime = DateUtility.getDefaultTime();
		// 区分
		boolean isAutoBeforeOverwork = false;
		boolean isShort1TypePay = false;
		boolean isShort2TypePay = false;
		// マップ作製
		for (WorkTypeItemDtoInterface dto : itemList) {
			// 勤務形態項目コードを取得
			String workTypeItemCode = dto.getWorkTypeItemCode();
			// 項目値（時間）取得
			Date targetTime = dto.getWorkTypeItemValue();
			// map追加
			map.put(workTypeItemCode, targetTime);
			// 勤務前残業自動申請の場合
			if (workTypeItemCode.equals(TimeConst.CODE_AUTO_BEFORE_OVERWORK)) {
				if (dto.getPreliminary().equals(String.valueOf(MospConst.INACTIVATE_FLAG_ON))) {
					isAutoBeforeOverwork = true;
				}
			}
			// 割増休憩除外の場合
			if (workTypeItemCode.equals(TimeConst.CODE_WORK_TYPE_ITEM_EXCLUDE_NIGHT_REST)) {
				// 値が空の時、無効を設定する
				if (dto.getPreliminary().isEmpty()) {
					dto.setPreliminary(String.valueOf(MospConst.INACTIVATE_FLAG_ON));
				}
			}
			// 時短時間1給与区分の場合
			if (workTypeItemCode.equals(TimeConst.CODE_WORK_TYPE_ITEM_SHORT1_START)) {
				if (dto.getPreliminary().equals(WorkTypeEntity.CODE_PAY_TYPE_PAY)) {
					isShort1TypePay = true;
				}
			}
			// 時短時間1給与区分の場合
			if (workTypeItemCode.equals(TimeConst.CODE_WORK_TYPE_ITEM_SHORT2_START)) {
				if (dto.getPreliminary().equals(WorkTypeEntity.CODE_PAY_TYPE_PAY)) {
					isShort2TypePay = true;
				}
			}
			// 設定なしの場合
			if (targetTime.compareTo(defaultTime) == 0) {
				continue;
			}
			// 時間と分取得
			int time = DateUtility.getHour(targetTime, defaultTime);
			int minute = DateUtility.getMinute(targetTime);
			// 時は47まで
			if (time > 47) {
				// エラーメッセージを設定
				PfMessageUtility.addErrorOverLimit(mospParams, getWorkTypeItemName(workTypeItemCode), 47);
			}
			// 分は59まで
			if (minute > 59) {
				// エラーメッセージを設定
				PfMessageUtility.addErrorOverLimit(mospParams, getWorkTypeItemName(workTypeItemCode), 59);
			}
		}
		
		// 始業・終業・休憩時間取得
		Date startTime = map.get(TimeConst.CODE_WORKSTART);
		Date endTime = map.get(TimeConst.CODE_WORKEND);
		
		// 始業時刻が24時以降の場合
		if (startTime.compareTo(DateUtility.addDay(defaultTime, 1)) >= 0) {
			// エラーメッセージを設定
			TimeMessageUtility.addErrorWorkStartTimeOverLimit(mospParams);
			return;
		}
		// 始業と終業時刻チェック
		if (endTime.compareTo(startTime) < 0) {
			// エラーメッセージを設定
			TimeMessageUtility.addErrorWorkTimeOrder(mospParams);
			return;
		}
		
		// 休憩時間のチェック
		checkRestTimes(map);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		
		// 午前休・午後休
		Date frontStart = map.get(TimeConst.CODE_FRONTSTART);
		Date frontEnd = map.get(TimeConst.CODE_FRONTEND);
		Date backStart = map.get(TimeConst.CODE_BACKSTART);
		Date backEnd = map.get(TimeConst.CODE_BACKEND);
		// 午前休・午後休入力済の場合
		if (frontStart.compareTo(defaultTime) != 0 && backStart.compareTo(defaultTime) != 0) {
			// 午前休期間中に午後休開始×
			if (DateUtility.isTermContain(backStart, frontStart, frontEnd)
					&& !DateUtility.isSame(frontEnd, backStart)) {
				// エラーメッセージを設定
				TimeMessageUtility.addErrorRangeDuplicate(mospParams, TimeNamingUtility.pmRest(mospParams));
				return;
			}
			// 午後休期間中に午前休終了
			if (DateUtility.isTermContain(frontEnd, backStart, backEnd) && !DateUtility.isSame(frontEnd, backStart)) {
				// エラーメッセージを設定
				TimeMessageUtility.addErrorRangeDuplicate(mospParams, TimeNamingUtility.pmRest(mospParams));
				return;
			}
		}
		// 午前休入力済の場合
		if (frontStart.compareTo(defaultTime) != 0) {
			//午前休始→午前休終
			if (frontEnd.compareTo(frontStart) < 0) {
				// エラーメッセージを設定
				String afterName = TimeNamingUtility.amRestEndTime(mospParams);
				String beforeName = TimeNamingUtility.amRestStartTime(mospParams);
				PfMessageUtility.addErrorOrderInvalid(mospParams, afterName, beforeName);
				return;
			}
			// 始業時刻→＝午前休始
			if (frontStart.compareTo(startTime) < 0) {
				// エラーメッセージを設定
				String afterName = TimeNamingUtility.amRestStartTime(mospParams);
				String beforeName = TimeNamingUtility.startWorkTime(mospParams);
				PfMessageUtility.addErrorOrderInvalid(mospParams, afterName, beforeName);
				return;
			}
			// 午前休終→＝終業時刻
			if (endTime.compareTo(frontEnd) < 0) {
				// エラーメッセージを設定
				String afterName = TimeNamingUtility.endWorkTime(mospParams);
				String beforeName = TimeNamingUtility.amRestStartTime(mospParams);
				PfMessageUtility.addErrorOrderInvalid(mospParams, afterName, beforeName);
				return;
			}
		}
		// 午後休入力済の場合
		if (backStart.compareTo(defaultTime) != 0) {
			// 午後休始→午後休終
			if (backEnd.compareTo(backStart) < 0) {
				// エラーメッセージを設定
				String afterName = TimeNamingUtility.pmRestEndTime(mospParams);
				String beforeName = TimeNamingUtility.pmRestStartTime(mospParams);
				PfMessageUtility.addErrorOrderInvalid(mospParams, afterName, beforeName);
				return;
			}
			// 午後休終→＝終業時刻
			if (backEnd.compareTo(endTime) > 0) {
				// エラーメッセージを設定
				String afterName = TimeNamingUtility.endWorkTime(mospParams);
				String beforeName = TimeNamingUtility.pmRestEndTime(mospParams);
				PfMessageUtility.addErrorOrderInvalid(mospParams, afterName, beforeName);
				return;
			}
		}
		// 半休取得時休憩取得
		Date halfRestStart = map.get(TimeConst.CODE_HALFRESTSTART);
		Date halfRestEnd = map.get(TimeConst.CODE_HALFRESTEND);
		// 半休取得時休憩の休憩2、休憩3が入力済の場合
		if (halfRestStart.compareTo(defaultTime) != 0 && halfRestEnd.compareTo(defaultTime) != 0) {
			//休憩2→休憩3
			if (halfRestEnd.compareTo(halfRestStart) < 0) {
				// エラーメッセージを設定
				String afterName = TimeNamingUtility.halfHolidayRestEndTime(mospParams);
				String beforeName = TimeNamingUtility.halfHolidayRestStartTime(mospParams);
				PfMessageUtility.addErrorOrderInvalid(mospParams, afterName, beforeName);
				return;
			}
		}
		// 残業休憩取得時休憩取得
		int overPerMinute = TimeUtility.getMinutes(map.get(TimeConst.CODE_OVERPER));
		int overRestMinute = TimeUtility.getMinutes(map.get(TimeConst.CODE_OVERREST));
		// 時間(毎)より残業時間が同じ又は長い場合
		if (overPerMinute == 0 && overRestMinute > 0) {
			// エラーメッセージを設定
			String fieldName = TimeNamingUtility.restTime(mospParams);
			String rangeName = TimeNamingUtility.overtimeRest(mospParams);
			TimeMessageUtility.addErrorTimeOutOfRange(mospParams, fieldName, rangeName);
			return;
		}
		if (overPerMinute > 0 && overPerMinute <= overRestMinute) {
			// エラーメッセージを設定
			String fieldName = TimeNamingUtility.restTime(mospParams);
			String rangeName = TimeNamingUtility.overtimeRest(mospParams);
			TimeMessageUtility.addErrorTimeOutOfRange(mospParams, fieldName, rangeName);
			return;
		}
		// 時短確認
		Date short1StartTime = map.get(TimeConst.CODE_WORK_TYPE_ITEM_SHORT1_START);
		Date short1EndTime = map.get(TimeConst.CODE_WORK_TYPE_ITEM_SHORT1_END);
		Date short2StartTime = map.get(TimeConst.CODE_WORK_TYPE_ITEM_SHORT2_START);
		Date short2EndTime = map.get(TimeConst.CODE_WORK_TYPE_ITEM_SHORT2_END);
		boolean isShort1Set = short1StartTime != null && short1StartTime.compareTo(defaultTime) != 0
				|| short1EndTime != null && short1EndTime.compareTo(defaultTime) != 0;
		boolean isShort2Set = short2StartTime != null && short2StartTime.compareTo(defaultTime) != 0
				|| short2EndTime != null && short2EndTime.compareTo(defaultTime) != 0;
		// 時短時間1確認(開始時刻及び終了時刻がデフォルト時刻でない場合)
		if (isShort1Set) {
			// 時短勤務1開始時刻と始業時刻を比較
			if (short1StartTime.compareTo(startTime) != 0) {
				// エラーメッセージを設定
				TimeMessageUtility.addErrorShort1TimeBoundary(mospParams);
			}
			// 時短勤務1終了時刻と終業時刻の前後を確認
			if (short1EndTime.after(endTime)) {
				// エラーメッセージを設定
				TimeMessageUtility.addErrorShort1OutOfWorkTime(mospParams);
			}
			// 時短時間1開始終了前後確認
			if (short1EndTime.after(short1StartTime) == false) {
				// エラーメッセージを設定
				TimeMessageUtility.addErrorShort1EndBeforeStart(mospParams);
			}
		}
		// 時短時間2確認(開始時刻及び終了時刻がデフォルト時刻でない場合)
		if (isShort2Set) {
			// 時短勤務2終了時刻と終業時刻を比較
			if (short2EndTime.compareTo(endTime) != 0) {
				// エラーメッセージを設定
				TimeMessageUtility.addErrorShort2TimeBoundary(mospParams);
			}
			// 時短勤務2開始時刻と始業時刻の前後を確認
			if (short2StartTime.before(startTime)) {
				// エラーメッセージを設定
				TimeMessageUtility.addErrorShort2OutOfWorkTime(mospParams);
			}
			// 時短時間2開始終了前後確認
			if (short2EndTime.after(short2StartTime) == false) {
				// エラーメッセージを設定
				TimeMessageUtility.addErrorShort2EndBeforeStart(mospParams);
			}
		}
		// 時短勤務区分の組合せを確認
		if (isShort1Set && isShort2Set && !isShort1TypePay && isShort2TypePay) {
			// エラーメッセージを設定
			TimeMessageUtility.addErrorShortTypePair(mospParams);
		}
		// 勤務前残業自動申請有効可否確認
		checkAutoBeforeOverWork(isAutoBeforeOverwork, isShort1TypePay, short1StartTime, short1EndTime);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 追加処理群による処理
		for (WorkTypeItemRegistAddonBeanInterface addonBean : addonBeans) {
			// 追加処理による勤務形態項目情報の妥当性確認処理
			addonBean.checkItemTimeValidate(itemList);
			// エラーが発生したら、そこで処理は終了
			if (mospParams.hasErrorMessage()) {
				return;
			}
		}
	}
	
	/**
	 * 休憩時間妥当性確認
	 * @param map 勤務形態項目MAP
	 * @throws MospException インスタンスの生成或いはSQLの実行に失敗した場合
	 */
	protected void checkRestTimes(Map<String, Date> map) throws MospException {
		// 始業・終業・休憩時間取得
		int startTime = TimeUtility.getMinutes(map.get(TimeConst.CODE_WORKSTART));
		int endTime = TimeUtility.getMinutes(map.get(TimeConst.CODE_WORKEND));
		int rest1Start = TimeUtility.getMinutes(map.get(TimeConst.CODE_RESTSTART1));
		int rest1End = TimeUtility.getMinutes(map.get(TimeConst.CODE_RESTEND1));
		int rest2Start = TimeUtility.getMinutes(map.get(TimeConst.CODE_RESTSTART2));
		int rest2End = TimeUtility.getMinutes(map.get(TimeConst.CODE_RESTEND2));
		int rest3Start = TimeUtility.getMinutes(map.get(TimeConst.CODE_RESTSTART3));
		int rest3End = TimeUtility.getMinutes(map.get(TimeConst.CODE_RESTEND3));
		int rest4Start = TimeUtility.getMinutes(map.get(TimeConst.CODE_RESTSTART4));
		int rest4End = TimeUtility.getMinutes(map.get(TimeConst.CODE_RESTEND4));
		// 休憩始業配列
		int[] aryRestStart = { rest1Start, rest2Start, rest3Start, rest4Start };
		int[] aryRestEnd = { rest1End, rest2End, rest3End, rest4End };
		// 休憩(時間間隔)群を準備
		Set<TimeDuration> durations = new LinkedHashSet<TimeDuration>();
		// 休憩時刻確認
		for (int i = 0; i < aryRestStart.length; i++) {
			// 休憩開始及び終了時刻を取得
			int restStart = aryRestStart[i];
			int restEnd = aryRestEnd[i];
			// 開始・終了時刻が無い場合
			if (restStart == 0 && restEnd == 0) {
				continue;
			}
			// 始業が休憩開始時間より後の場合
			if (restStart < startTime) {
				// エラーメッセージを設定
				PfMessageUtility.addErrorOrderInvalid(mospParams, TimeNamingUtility.rest(mospParams, i + 1),
						TimeNamingUtility.startWorkTime(mospParams));
			}
			// 終業が休憩終了時間より前の場合
			if (endTime < restEnd) {
				// エラーメッセージを設定
				PfMessageUtility.addErrorOrderInvalid(mospParams, TimeNamingUtility.endWorkTime(mospParams),
						TimeNamingUtility.rest(mospParams, i + 1));
			}
			// 休憩(時間間隔)を取得
			TimeDuration duration = TimeDuration.getInstance(restStart, restEnd);
			// 休憩(時間間隔)が妥当でない場合
			if (duration.isValid() == false) {
				// エラーメッセージを設定
				TimeMessageUtility.addErrorEndBeforeStart(mospParams, TimeNamingUtility.rest(mospParams, i + 1));
			}
			// 休憩(時間間隔)を休憩(時間間隔)群に追加
			durations.add(TimeDuration.getInstance(restStart, restEnd));
		}
		// 休憩(時間間隔)が重複する場合
		if (TimeUtility.isOverlap(durations)) {
			// エラーメッセージを設定
			TimeMessageUtility.addErrorRangeDuplicate(mospParams, TimeNamingUtility.restTime(mospParams));
		}
	}
	
	/**
	 * 勤務前残業自動申請有効可否チェック
	 * @param isAutoBeforeOverwork 勤務前残業自動申請（true：有効、false：無効）
	 * @param isShort1TypePay 時短1有給区分（true：有給、false：無給）
	 * @param short1Start 時短1開始時刻
	 * @param short1End 時短1終了時刻
	 * @throws MospException インスタンスの生成に失敗した場合
	 */
	protected void checkAutoBeforeOverWork(boolean isAutoBeforeOverwork, boolean isShort1TypePay, Date short1Start,
			Date short1End) throws MospException {
		
		// 勤務前残業自動申請が無効の場合
		if (isAutoBeforeOverwork) {
			// チェックなし
			return;
		}
		// 時短時間1が未設定の場合、チェックなし
		if (short1Start.compareTo(DateUtility.getDefaultTime()) == 0
				&& short1End.compareTo(DateUtility.getDefaultTime()) == 0) {
			return;
		}
		// 時短時間1有給確認
		if (isShort1TypePay) {
			// 有給の場合、チェックなし
			return;
		}
		// 時短時間1が設定されており且つ無給の場合はエラー
		TimeMessageUtility.addErrorAnotherItemInvalid(mospParams);
	}
	
	/**
	 * 新規登録時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkInsert(WorkTypeItemDtoInterface dto) throws MospException {
		// 対象レコードの有効日が重複していないかを確認
		checkDuplicateInsert(dao.findForHistory(dto.getWorkTypeCode(), dto.getWorkTypeItemCode()));
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 追加処理群による処理
		for (WorkTypeItemRegistAddonBeanInterface addonBean : addonBeans) {
			// 追加処理による新規登録時の確認処理
			addonBean.checkInsert(dto);
			// エラーが発生したら、そこで処理は終了
			if (mospParams.hasErrorMessage()) {
				return;
			}
		}
	}
	
	/**
	 * 履歴追加時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkAdd(WorkTypeItemDtoInterface dto) throws MospException {
		// 対象レコードの有効日が重複していないかを確認
		checkDuplicateAdd(dao.findForKey(dto.getWorkTypeCode(), dto.getActivateDate(), dto.getWorkTypeItemCode()));
		// 無効フラグ確認
		if (isDtoActivate(dto)) {
			return;
		}
		// 履歴追加対象コードの履歴情報を取得
		List<WorkTypeItemDtoInterface> list = dao.findForHistory(dto.getWorkTypeCode(), dto.getWorkTypeItemCode());
		// 生じる無効期間による履歴追加確認要否を取得
		if (!needCheckTermForAdd(dto, list)) {
			// 無効期間は発生しない
			return;
		}
		// 追加処理群による処理
		for (WorkTypeItemRegistAddonBeanInterface addonBean : addonBeans) {
			// 追加処理による履歴追加時の確認処理
			addonBean.checkAdd(dto);
			// エラーが発生したら、そこで処理は終了
			if (mospParams.hasErrorMessage()) {
				return;
			}
		}
	}
	
	/**
	 * 履歴更新時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkUpdate(WorkTypeItemDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getTmmWorkTypeItemId());
		// 無効フラグ確認
		if (isDtoActivate(dto)) {
			return;
		}
		// 更新元データの無効フラグ確認
		if (!isDtoActivate(dao.findForKey(dto.getTmmWorkTypeItemId(), true))) {
			// 更新元データが更新前から無効であれば無効期間は発生しない
			return;
		}
		// 追加処理群による処理
		for (WorkTypeItemRegistAddonBeanInterface addonBean : addonBeans) {
			// 追加処理による履歴更新時の確認処理
			addonBean.checkUpdate(dto);
			// エラーが発生したら、そこで処理は終了
			if (mospParams.hasErrorMessage()) {
				return;
			}
		}
	}
	
	/**
	 *  削除時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkDelete(WorkTypeItemDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getTmmWorkTypeItemId());
		// 処理結果を確認
		if (mospParams.hasErrorMessage()) {
			// 処理終了
			return;
		}
		// 対象DTOの無効フラグ確認
		if (!isDtoActivate(dto)) {
			// 削除対象が無効であれば無効期間は発生しない
			return;
		}
		// 削除対象コードの履歴情報を取得
		List<WorkTypeItemDtoInterface> list = dao.findForHistory(dto.getWorkTypeCode(), dto.getWorkTypeItemCode());
		// 生じる無効期間による削除確認要否を取得
		if (!needCheckTermForDelete(dto, list)) {
			// 無効期間は発生しない
			return;
		}
		// 追加処理群による処理
		for (WorkTypeItemRegistAddonBeanInterface addonBean : addonBeans) {
			// 追加処理による削除時の確認処理
			addonBean.checkDelete(dto);
			// エラーが発生したら、そこで処理は終了
			if (mospParams.hasErrorMessage()) {
				return;
			}
		}
	}
	
	/**
	 * 勤務形態コードを取得する。
	 * @param idArray レコード識別ID配列
	 * @return 勤務形態コード
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected List<String> getCodeList(long[] idArray) throws MospException {
		// リスト準備
		List<String> list = new ArrayList<String>();
		// レコード識別IDからDTOを取得し、コードをリストへ追加
		for (long id : idArray) {
			// レコード識別IDから対象DTOを取得
			WorkTypeDtoInterface dto = (WorkTypeDtoInterface)workTypeDao.findForKey(id, false);
			// 対象コードをリストへ追加
			list.add(dto.getWorkTypeCode());
		}
		return list;
	}
	
	/**
	 * 登録情報の妥当性を確認する。
	 * @param dto 対象DTO
	 * @throws MospException エラーが発生した場合
	 */
	protected void validate(WorkTypeItemDtoInterface dto) throws MospException {
		// 勤務形態コード
		checkLength(dto.getWorkTypeCode(), LEN_WORK_TYPE_CODE, TimeNamingUtility.workTypeCode(mospParams), null);
		checkTypeCode(dto.getWorkTypeCode(), TimeNamingUtility.workTypeCode(mospParams), null);
		// 追加処理群による処理
		for (WorkTypeItemRegistAddonBeanInterface addonBean : addonBeans) {
			// 追加処理による削除時の確認処理
			addonBean.validate(dto);
		}
	}
	
	/**
	 * 項目名称を取得する。<br>
	 * @param worktypeItemCode 勤務形態項目コード
	 * @return 項目名称
	 */
	protected String getWorkTypeItemName(String worktypeItemCode) {
		String name = MospConst.STR_EMPTY;
		if (worktypeItemCode.equals(TimeConst.CODE_WORKSTART)) {
			name = TimeNamingUtility.startWork(mospParams);
		} else if (worktypeItemCode.equals(TimeConst.CODE_WORKEND)) {
			name = TimeNamingUtility.endWork(mospParams);
		} else if (worktypeItemCode.equals(TimeConst.CODE_WORKTIME)) {
			name = TimeNamingUtility.workTime(mospParams);
		} else if (worktypeItemCode.equals(TimeConst.CODE_RESTTIME)) {
			name = TimeNamingUtility.restTime(mospParams);
		} else if (worktypeItemCode.equals(TimeConst.CODE_RESTSTART1)) {
			name = TimeNamingUtility.rest(mospParams, 1);
		} else if (worktypeItemCode.equals(TimeConst.CODE_RESTEND1)) {
			name = TimeNamingUtility.rest(mospParams, 1);
		} else if (worktypeItemCode.equals(TimeConst.CODE_RESTSTART2)) {
			name = TimeNamingUtility.rest(mospParams, 2);
		} else if (worktypeItemCode.equals(TimeConst.CODE_RESTEND2)) {
			name = TimeNamingUtility.rest(mospParams, 2);
		} else if (worktypeItemCode.equals(TimeConst.CODE_RESTSTART3)) {
			name = TimeNamingUtility.rest(mospParams, 3);
		} else if (worktypeItemCode.equals(TimeConst.CODE_RESTEND3)) {
			name = TimeNamingUtility.rest(mospParams, 3);
		} else if (worktypeItemCode.equals(TimeConst.CODE_RESTSTART4)) {
			name = TimeNamingUtility.rest(mospParams, 4);
		} else if (worktypeItemCode.equals(TimeConst.CODE_RESTEND4)) {
			name = TimeNamingUtility.rest(mospParams, 4);
		} else if (worktypeItemCode.equals(TimeConst.CODE_FRONTSTART)) {
			name = TimeNamingUtility.amRest(mospParams);
		} else if (worktypeItemCode.equals(TimeConst.CODE_FRONTEND)) {
			name = TimeNamingUtility.amRest(mospParams);
		} else if (worktypeItemCode.equals(TimeConst.CODE_BACKSTART)) {
			name = TimeNamingUtility.pmRest(mospParams);
		} else if (worktypeItemCode.equals(TimeConst.CODE_BACKEND)) {
			name = TimeNamingUtility.pmRest(mospParams);
		} else if (worktypeItemCode.equals(TimeConst.CODE_OVERBEFORE)) {
			name = TimeNamingUtility.overtimeBeforeRest(mospParams);
		} else if (worktypeItemCode.equals(TimeConst.CODE_OVERPER)) {
			name = TimeNamingUtility.overtimeRest(mospParams);
		} else if (worktypeItemCode.equals(TimeConst.CODE_OVERREST)) {
			name = TimeNamingUtility.overtimeRest(mospParams);
		} else if (worktypeItemCode.equals(TimeConst.CODE_HALFREST)) {
			name = TimeNamingUtility.halfHolidayRest(mospParams);
		} else if (worktypeItemCode.equals(TimeConst.CODE_HALFRESTSTART)) {
			name = TimeNamingUtility.halfHolidayRest(mospParams);
		} else if (worktypeItemCode.equals(TimeConst.CODE_HALFRESTEND)) {
			name = TimeNamingUtility.halfHolidayRest(mospParams);
		} else if (worktypeItemCode.equals(TimeConst.CODE_WORK_TYPE_ITEM_DIRECT_START)) {
			name = TimeNamingUtility.directStart(mospParams);
		} else if (worktypeItemCode.equals(TimeConst.CODE_WORK_TYPE_ITEM_DIRECT_END)) {
			name = TimeNamingUtility.directEnd(mospParams);
		} else if (worktypeItemCode.equals(TimeConst.CODE_WORK_TYPE_ITEM_EXCLUDE_NIGHT_REST)) {
			name = mospParams.getName("ExcludeNightRest");
		} else if (worktypeItemCode.equals(TimeConst.CODE_WORK_TYPE_ITEM_SHORT1_START)) {
			name = mospParams.getName("ShortTime", "Time", "No1");
		} else if (worktypeItemCode.equals(TimeConst.CODE_WORK_TYPE_ITEM_SHORT1_END)) {
			name = mospParams.getName("ShortTime", "Time", "No1");
		} else if (worktypeItemCode.equals(TimeConst.CODE_WORK_TYPE_ITEM_SHORT2_START)) {
			name = mospParams.getName("ShortTime", "Time", "No2");
		} else if (worktypeItemCode.equals(TimeConst.CODE_WORK_TYPE_ITEM_SHORT2_END)) {
			name = mospParams.getName("ShortTime", "Time", "No2");
		} else if (worktypeItemCode.equals(TimeConst.CODE_AUTO_BEFORE_OVERWORK)) {
			name = TimeNamingUtility.registActualOvertimeBeforeWork(mospParams);
		}
		return name;
	}
	
	@Override
	public Date getDefaultTime(String hour, String minute) throws MospException {
		// 基準日取得
		Date defaultDate = DateUtility.getDefaultTime();
		return TimeUtility.getAttendanceTime(defaultDate, hour, minute, mospParams);
	}
	
	/**
	 * 勤務形態項目登録追加処理リストを取得する。<br>
	 * @return 勤務形態項目登録追加処理リスト
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	protected List<WorkTypeItemRegistAddonBeanInterface> getAddonBeans() throws MospException {
		// 勤怠設定追加処理リストを準備
		List<WorkTypeItemRegistAddonBeanInterface> addonBeans = new ArrayList<WorkTypeItemRegistAddonBeanInterface>();
		// 勤怠設定追加処理配列毎に処理
		for (String[] addon : mospParams.getProperties().getCodeArray(CODE_KEY_ADDONS, false)) {
			// 勤怠設定追加処理を取得
			String addonBean = addon[0];
			// 勤怠設定追加処理が設定されていない場合
			if (MospUtility.isEmpty(addonBean)) {
				// 次の勤怠設定追加処理へ
				continue;
			}
			// 勤怠設定追加処理を取得
			WorkTypeItemRegistAddonBeanInterface bean = (WorkTypeItemRegistAddonBeanInterface)createBean(addonBean);
			// 勤怠設定追加処理リストに値を追加
			addonBeans.add(bean);
		}
		// 勤怠設定追加処理リストを取得
		return addonBeans;
	}
	
}
