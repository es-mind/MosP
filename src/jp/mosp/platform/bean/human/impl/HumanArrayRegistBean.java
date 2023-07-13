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
package jp.mosp.platform.bean.human.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.property.ConventionProperty;
import jp.mosp.framework.xml.ItemProperty;
import jp.mosp.framework.xml.TableItemProperty;
import jp.mosp.platform.bean.human.HumanArrayRegistBeanInterface;
import jp.mosp.platform.bean.human.HumanGeneralBeanInterface;
import jp.mosp.platform.bean.human.HumanGeneralCheckBeanInterface;
import jp.mosp.platform.bean.human.base.PlatformHumanBean;
import jp.mosp.platform.dao.human.HumanArrayDaoInterface;
import jp.mosp.platform.dto.human.HumanArrayDtoInterface;
import jp.mosp.platform.dto.human.impl.PfaHumanArrayDto;
import jp.mosp.platform.human.constant.PlatformHumanConst;

/**
 * 人事汎用一覧情報登録クラス。
 */
public class HumanArrayRegistBean extends PlatformHumanBean implements HumanArrayRegistBeanInterface {
	
	/**
	 * 人事兼通常報DAOクラス。<br>
	 */
	protected HumanArrayDaoInterface			dao;
	
	/**
	 * 人事汎用管理機能クラス。<br>
	 */
	protected HumanGeneralBeanInterface			humanGeneral;
	
	/**
	 * 人事汎用管理チェッククラス
	 */
	protected HumanGeneralCheckBeanInterface	humanGeneralCheckBean;
	
	
	/**
	 * {@link PlatformHumanBean#PlatformHumanBean()}を実行する。<br>
	 */
	public HumanArrayRegistBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// 継承基の処理を実行
		super.initBean();
		// DAOを準備
		dao = createDaoInstance(HumanArrayDaoInterface.class);
		// Beanを準備
		humanGeneral = createBeanInstance(HumanGeneralBeanInterface.class);
		humanGeneralCheckBean = createBeanInstance(HumanGeneralCheckBeanInterface.class);
		
	}
	
	@Override
	public HumanArrayDtoInterface getInitDto() {
		return new PfaHumanArrayDto();
	}
	
	@Override
	public void regist(String division, String viewKey, String personalId, Date activeDate, int rowId,
			LinkedHashMap<String, Long> recordsMap) throws MospException {
		// 入力チェック
		humanGeneralCheckBean.validate(division, viewKey);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		
		// 行番号取得
		int newRowId = getRowId();
		// 人事汎用項目情報リストを取得
		List<TableItemProperty> tableItemList = humanGeneral.getTableItemList(division, viewKey);
		//人事汎用項目毎に処理
		for (TableItemProperty tableItem : tableItemList) {
			// 人事汎用一覧情報項目名を取得
			String[] itemNames = tableItem.getItemNames();
			String[] itemKeys = tableItem.getItemKeys();
			// 人事汎用一覧情報項目名毎に処理
			for (int i = 0; i < itemNames.length; i++) {
				HumanArrayDtoInterface dto;
				
				String itemName = itemNames[i];
				// 空の場合
				if (itemName.isEmpty()) {
					continue;
				}
				// レコード識別IDの取得
				Long recordId = recordsMap.get(itemName);
				
				// MosP処理情報から値取得
				String value = mospParams.getRequestParam(itemName);
				
				// 値がない場合
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
				
				// レコード識別ID確認
				if (recordId == null) {
					int rowRegistId = rowId;
					
					// 新規登録の場合
					if (rowRegistId == 0) {
						rowRegistId = newRowId;
					}
					
					// DTOに設定
					dto = getInitDto();
					dto.setPersonalId(personalId);
					dto.setHumanItemType(itemName);
					dto.setHumanRowId(rowRegistId);
					dto.setActivateDate(activeDate);
					dto.setHumanItemValue(value);
					// 新規登録
					insert(dto);
					// MosP処理情報設定
					mospParams.addGeneralParam(PlatformHumanConst.PRM_HUMAN_ARRAY_ROW_ID, String.valueOf(newRowId));
					if (mospParams.hasErrorMessage()) {
						return;
					}
				} else {
					// 人事汎用一覧情報取得
					dto = (HumanArrayDtoInterface)dao.findForKey(recordId, false);
					// 値を設定
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
	
	@Override
	public void delete(String division, String viewKey, int rowId, LinkedHashMap<String, Long> recordsMap)
			throws MospException {
		// 人事汎用項目情報リストを取得
		List<TableItemProperty> tableItemList = humanGeneral.getTableItemList(division, viewKey);
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
				// レコード識別IDの取得
				Long recordId = recordsMap.get(itemName);
				
				// 人事汎用通常情報取得
				HumanArrayDtoInterface dto = (HumanArrayDtoInterface)dao.findForKey(recordId, false);
				// レコード識別ID確認
				if (dto == null) {
					// 処理なし
					continue;
				} else {
					// 削除
					delete(dto);
					if (mospParams.hasErrorMessage()) {
						return;
					}
				}
			}
		}
	}
	
	@Override
	public void delete(String division, String viewKey, String personalId, Date activeDate, int rowId)
			throws MospException {
		// 人事汎用項目情報リストを取得
		List<TableItemProperty> tableItemList = humanGeneral.getTableItemList(division, viewKey);
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
				// 人事汎用通常情報取得
				HumanArrayDtoInterface dto = dao.findForKey(personalId, itemName, rowId);
				// レコード識別ID確認
				if (dto == null) {
					// 処理なし
					continue;
				} else {
					// 削除
					delete(dto);
					if (mospParams.hasErrorMessage()) {
						return;
					}
				}
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
		List<HumanArrayDtoInterface> listDeleteItem = dao.findForInfoNotIn(list);
		
		// 論理削除
		for (HumanArrayDtoInterface dto : listDeleteItem) {
			// 削除
			delete(dto);
		}
		
	}
	
	@Override
	public void delete(HumanArrayDtoInterface dto) throws MospException {
		// 削除情報の検証
		checkDelete(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 論理削除
		logicalDelete(dao, dto.getPfaHumanArrayId());
	}
	
	@Override
	public int getRowId() throws MospException {
		// 行ID最大値取得
		int rowId = dao.findForMaxRowId();
		rowId++;
		return rowId;
	}
	
	/**
	 * 削除の妥当性を確認する。
	 * @param dto 削除対象人事汎用一覧情報
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	private void checkDelete(HumanArrayDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getPfaHumanArrayId());
	}
	
	@Override
	public void insert(HumanArrayDtoInterface dto) throws MospException {
		// レコード識別ID最大値をインクリメントしてセットする
		dto.setPfaHumanArrayId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	/**
	 * 更新処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void update(HumanArrayDtoInterface dto) throws MospException {
		// 更新対象DTO取得
		HumanArrayDtoInterface arrayDto = (HumanArrayDtoInterface)findForKey(dao, dto.getPfaHumanArrayId(), true);
		// 排他確認
		checkExclusive(arrayDto);
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getPfaHumanArrayId());
		if (mospParams.hasErrorMessage()) {
			// エラーが存在したら登録処理をしない
			return;
		}
		// 論理削除
		logicalDelete(dao, dto.getPfaHumanArrayId());
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setPfaHumanArrayId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
}
