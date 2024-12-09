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
package jp.mosp.platform.bean.human.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.property.ConventionProperty;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.xml.ItemProperty;
import jp.mosp.framework.xml.TableItemProperty;
import jp.mosp.platform.bean.human.HumanGeneralBeanInterface;
import jp.mosp.platform.bean.human.HumanGeneralCheckBeanInterface;
import jp.mosp.platform.bean.human.HumanHistoryRegistBeanInterface;
import jp.mosp.platform.bean.human.HumanReferenceBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dao.human.HumanHistoryDaoInterface;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.dto.human.HumanHistoryDtoInterface;
import jp.mosp.platform.dto.human.impl.PfaHumanHistoryDto;
import jp.mosp.platform.human.constant.PlatformHumanConst;
import jp.mosp.platform.utils.PfMessageUtility;

/**
 * 人事汎用履歴情報登録クラス。
 */
public class HumanHistoryRegistBean extends HumanGeneralBean implements HumanHistoryRegistBeanInterface {
	
	/**
	 * 人事汎用履歴情報DAOクラス。<br>
	 */
	protected HumanHistoryDaoInterface			dao;
	
	/**
	 * 人事情報参照クラス。<br>
	 */
	protected HumanReferenceBeanInterface		humanReference;
	
	/**
	 * 人事汎用管理チェッククラス
	 */
	protected HumanGeneralCheckBeanInterface	humanGeneralCheckBean;
	
	/**
	 * 人事汎用クラス。
	 */
	protected HumanGeneralBeanInterface			humanGeneral;
	
	
	/**
	 * {@link HumanGeneralBean#HumanGeneralBean()}を実行する。<br>
	 */
	public HumanHistoryRegistBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// 継承基の処理を実行
		super.initBean();
		// DAOを準備
		dao = createDaoInstance(HumanHistoryDaoInterface.class);
		// Beanを準備
		humanReference = createBeanInstance(HumanReferenceBeanInterface.class);
		humanGeneral = createBeanInstance(HumanGeneralBeanInterface.class);
		humanGeneralCheckBean = createBeanInstance(HumanGeneralCheckBeanInterface.class);
	}
	
	@Override
	public HumanHistoryDtoInterface getInitDto() {
		return new PfaHumanHistoryDto();
	}
	
	@Override
	public void add(HumanHistoryDtoInterface dto) throws MospException {
		// DTOの妥当性確認
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
		dto.setPfaHumanHistoryId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void update(HumanHistoryDtoInterface dto) throws MospException {
		// DTOの妥当性確認
		validate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 履歴更新情報の検証
		checkUpdate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 論理削除
		logicalDelete(dao, dto.getPfaHumanHistoryId());
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setPfaHumanHistoryId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void delete(long[] idArray) throws MospException {
		// レコード識別ID配列の妥当性確認
		validateAryId(idArray);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 対象ID配列の中身を削除
		for (long id : idArray) {
			// 排他確認
			checkExclusive(dao, id);
			if (mospParams.hasErrorMessage()) {
				// エラーが存在したら削除処理をしない
				continue;
			}
			// 論理削除
			logicalDelete(dao, id);
		}
	}
	
	@Override
	public void validate(HumanHistoryDtoInterface dto) throws MospException {
		// 役職の場合
		if (dto.getHumanItemType().equals(PlatformConst.NAMING_TYPE_POST)) {
			// 履歴一覧取得
			List<HumanDtoInterface> list = humanReference.getHistory(dto.getPersonalId());
			// 影響を及ぼす期間取得
			Date startDate = dto.getActivateDate();
			Date endDate = null;
			// 履歴が存在する場合
			if (list.isEmpty() == false) {
				// 期間終了日取得
				endDate = getLastDate(dto.getActivateDate(), list);
			}
			// 役職存在確認
			checkNaming(dto.getHumanItemType(), dto.getHumanItemValue(), startDate, endDate, null);
			// 役職以外の場合
		} else {
			// 人事マスタ取得
			HumanDtoInterface humanDao = humanReference.getHumanInfo(dto.getPersonalId(), dto.getActivateDate());
			// 人事マスタがある場合
			if (humanDao != null) {
				return;
			}
			// エラーメッセージを設定
			PfMessageUtility.addErrorPersonalBasisInfoNotExist(mospParams);
		}
	}
	
	/**
	 * 情報が影響を及ぼす期間の最終日を取得する。<br>
	 * 対象コード履歴リストは、有効日の昇順で並んでいるものとする。<br>
	 * 対象日から見て次の履歴の有効日の前日を取得する。<br>
	 * 次の履歴が存在しない場合は、nullを返す。<br>
	 * 人事情報登録時マスタ整合性確認等に、用いる。<br>
	 * @param targetDate 対象日
	 * @param list 履歴一覧
	 * @return 一つ最新の履歴の有効日の前日
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected Date getLastDate(Date targetDate, List<HumanDtoInterface> list) throws MospException {
		// 最終日宣言
		Date lastDate = null;
		// 履歴リスト確認
		for (HumanDtoInterface dto : list) {
			// 有効日確認
			if (targetDate.compareTo(dto.getActivateDate()) >= 0) {
				// 対象日以前であればcontinue
				continue;
			}
			// 対象日より後で直後の履歴がある場合
			if (dto.getActivateDate() != null) {
				// 一つ最新の履歴の有効日前日を取得
				lastDate = DateUtility.addDay(dto.getActivateDate(), -1);
				break;
			}
		}
		return lastDate;
	}
	
	/**
	 * 履歴追加時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkAdd(HumanHistoryDtoInterface dto) throws MospException {
		// 対象レコードの有効日が重複していないかを確認
		checkDuplicateAdd(dao.findForKey(dto.getPersonalId(), dto.getHumanItemType(), dto.getActivateDate()));
	}
	
	/**
	 * 履歴更新時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkUpdate(HumanHistoryDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getPfaHumanHistoryId());
	}
	
	@Override
	public void delete(HumanHistoryDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getPfaHumanHistoryId());
		// 論理削除
		logicalDelete(dao, dto.getPfaHumanHistoryId());
	}
	
	@Override
	public void delete(String division, String viewKey, LinkedHashMap<String, Long> recordsMap) throws MospException {
		// 人事汎用項目情報リストを取得
		List<TableItemProperty> tableItemList = getTableItemList(division, viewKey);
		//人事汎用項目毎に処理
		for (TableItemProperty tableItem : tableItemList) {
			// 人事汎用項目名を取得
			String[] itemNames = tableItem.getItemNames();
			// 人事汎用項目名毎に処理
			for (String itemName : itemNames) {
				// 空の場合
				if (itemName.isEmpty()) {
					continue;
				}
				// レコード識別ID取得
				Long recordId = recordsMap.get(itemName);
				
				// レコード識別ID確認
				if (recordId == null) {
					// 処理なし
					continue;
				}
				// 人事汎用履歴情報取得
				HumanHistoryDtoInterface dto = (HumanHistoryDtoInterface)dao.findForKey(recordId, false);
				// 削除
				delete(dto);
			}
		}
	}
	
	@Override
	public void deleteDeadInputItem(Set<String> divisions, String viewKey) throws MospException {
		// 項目取得用
		List<String> list = new ArrayList<String>();
		
		// 人事汎用項目情報リストを取得
		for (String division : divisions) {
			List<TableItemProperty> tableItemList = humanGeneral.getTableItemList(division, viewKey);
			
			//人事汎用項目毎に処理
			for (TableItemProperty tableItem : tableItemList) {
				// 人事汎用項目名を取得
				String[] itemNames = tableItem.getItemNames();
				for (String itemName : itemNames) {
					list.add(itemName);
				}
			}
			
		}
		if (list.isEmpty()) {
			return;
		}
		
		// 取得した項目名以外のデータを取得
		List<HumanHistoryDtoInterface> listDeleteItem = dao.findForInfoNotIn(list);
		
		// 論理削除
		for (HumanHistoryDtoInterface dto : listDeleteItem) {
			// 削除
			delete(dto);
		}
		
	}
	
	@Override
	public void add(String division, String viewKey, String personalId, Date activeDate) throws MospException {
		
		// 入力チェック
		humanGeneralCheckBean.validate(division, viewKey);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 人事汎用項目情報リストを取得
		List<TableItemProperty> tableItemList = getTableItemList(division, viewKey);
		//人事汎用項目毎に処理
		for (TableItemProperty tableItem : tableItemList) {
			// 人事汎用項目名を取得
			String[] itemNames = tableItem.getItemNames();
			String[] itemKeys = tableItem.getItemKeys();
			// 人事汎用一覧情報項目名毎に処理
			for (int i = 0; i < itemNames.length; i++) {
				String itemName = itemNames[i];
				// 空の場合
				if (itemName.isEmpty()) {
					continue;
				}
				// MosP処理情報から値取得
				String value = mospParams.getRequestParam(itemName);
				if (value == null) {
					value = "";
				}
				// 人事汎用項目区分設定情報取得
				ConventionProperty conventionProperty = mospParams.getProperties().getConventionProperties()
					.get(PlatformHumanConst.KEY_DEFAULT_CONVENTION);
				
				ItemProperty itemProperty = conventionProperty.getItem(itemKeys[i]);
				
				// 項目形式がチェックボックス以外
				if (itemProperty.getType().equals(PlatformHumanConst.KEY_HUMAN_ITEM_TYPE_CHECK_BOX)) {
					// チェックボックスで且つ値が未設定の場合
					if (value.isEmpty()) {
						value = MospConst.CHECKBOX_OFF;
					}
				}
				
				// DTOに設定
				HumanHistoryDtoInterface dto = getInitDto();
				dto.setPersonalId(personalId);
				dto.setActivateDate(activeDate);
				dto.setHumanItemType(itemName);
				dto.setHumanItemValue(value);
				// 新規登録
				add(dto);
				if (mospParams.hasErrorMessage()) {
					return;
				}
			}
		}
		
	}
	
	@Override
	public void regist(HumanHistoryDtoInterface dto) throws MospException {
		// 人事汎用履歴情報取得
		HumanHistoryDtoInterface oldDto = dao.findForKey(dto.getPersonalId(), dto.getHumanItemType(),
				dto.getActivateDate());
		// ない場合
		if (oldDto == null) {
			// 履歴追加
			add(dto);
		} else {
			// DTOに設定
			oldDto.setHumanItemValue(dto.getHumanItemValue());
			// 更新
			update(oldDto);
		}
	}
	
	@Override
	public void update(String division, String viewKey, String personalId, Date activeDate,
			LinkedHashMap<String, Long> recordsMap) throws MospException {
		// 入力チェック
		humanGeneralCheckBean.validate(division, viewKey);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		
		// 人事汎用項目情報リストを取得
		List<TableItemProperty> tableItemList = getTableItemList(division, viewKey);
		//人事汎用項目毎に処理
		for (TableItemProperty tableItem : tableItemList) {
			// 人事汎用項目名を取得
			String[] itemNames = tableItem.getItemNames();
			String[] itemKeys = tableItem.getItemKeys();
			// 人事汎用一覧情報項目名毎に処理
			for (int i = 0; i < itemNames.length; i++) {
				String itemName = itemNames[i];
				// 空の場合
				if (itemName.isEmpty()) {
					continue;
				}
				// レコード識別ID取得
				Long recordId = recordsMap.get(itemName);
				
				// DTO初期化
				HumanHistoryDtoInterface dto = getInitDto();
				// MosP処理情報から値取得
				String value = mospParams.getRequestParam(itemName);
				// 値が未設定の場合
				if (value == null) {
					value = "";
				}
				// 人事汎用項目区分設定情報取得
				ConventionProperty conventionProperty = mospParams.getProperties().getConventionProperties()
					.get(PlatformHumanConst.KEY_DEFAULT_CONVENTION);
				
				ItemProperty itemProperty = conventionProperty.getItem(itemKeys[i]);
				
				// 項目形式がチェックボックス以外
				if (itemProperty.getType().equals(PlatformHumanConst.KEY_HUMAN_ITEM_TYPE_CHECK_BOX)) {
					// チェックボックスで且つ値が未設定の場合
					if (value.isEmpty()) {
						value = MospConst.CHECKBOX_OFF;
					}
				}
				
				// 履歴登録時には存在しなかった項目の場合
				if (recordId == null) {
					dto.setPersonalId(personalId);
					dto.setActivateDate(activeDate);
					dto.setHumanItemType(itemName);
					dto.setHumanItemValue(value);
					// 新規登録
					add(dto);
					if (mospParams.hasErrorMessage()) {
						return;
					}
					
					continue;
				}
				
				// 人事汎用履歴情報取得
				dto = (HumanHistoryDtoInterface)dao.findForKey(recordId, false);
				// レコード識別ID確認
				dto.setHumanItemValue(value);
				// 更新
				update(dto);
				if (mospParams.hasErrorMessage()) {
					return;
				}
			}
		}
	}
}
