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
import java.util.List;
import java.util.Set;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.property.ViewConfigProperty;
import jp.mosp.framework.xml.TableItemProperty;
import jp.mosp.platform.bean.human.HumanBinaryHistoryRegistBeanInterface;
import jp.mosp.platform.bean.human.HumanGeneralBeanInterface;
import jp.mosp.platform.bean.human.HumanReferenceBeanInterface;
import jp.mosp.platform.dao.human.HumanBinaryHistoryDaoInterface;
import jp.mosp.platform.dto.human.HumanBinaryHistoryDtoInterface;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.dto.human.impl.PfaHumanBinaryHistoryDto;
import jp.mosp.platform.human.constant.PlatformHumanConst;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.platform.utils.PfNameUtility;

/**
 * 人事汎用バイナリ履歴情報登録クラス。
 */
public class HumanBinaryHistoryRegistBean extends HumanGeneralBean implements HumanBinaryHistoryRegistBeanInterface {
	
	/**
	 * 人事汎用履歴情報DAOクラス。<br>
	 */
	protected HumanBinaryHistoryDaoInterface	dao;
	
	/**
	 * 人事情報参照クラス。<br>
	 */
	protected HumanReferenceBeanInterface		humanReference;
	
	/**
	 * 人事汎用クラス。
	 */
	protected HumanGeneralBeanInterface			humanGeneral;
	
	
	/**
	 * {@link HumanGeneralBean#HumanGeneralBean()}を実行する。<br>
	 */
	public HumanBinaryHistoryRegistBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// 継承基の処理を実行
		super.initBean();
		// DAOを準備
		dao = createDaoInstance(HumanBinaryHistoryDaoInterface.class);
		// Beanを準備
		humanReference = createBeanInstance(HumanReferenceBeanInterface.class);
		humanGeneral = createBeanInstance(HumanGeneralBeanInterface.class);
	}
	
	@Override
	public HumanBinaryHistoryDtoInterface getInitDto() {
		return new PfaHumanBinaryHistoryDto();
	}
	
	@Override
	public void delete(HumanBinaryHistoryDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getPfaHumanBinaryHistoryId());
		// 論理削除
		logicalDelete(dao, dto.getPfaHumanBinaryHistoryId());
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
		List<HumanBinaryHistoryDtoInterface> listDeleteItem = dao.findForInfoNotIn(list);
		
		// 論理削除
		for (HumanBinaryHistoryDtoInterface dto : listDeleteItem) {
			// 削除
			delete(dto);
		}
		
	}
	
	@Override
	public void update(HumanBinaryHistoryDtoInterface dto) throws MospException {
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
		logicalDelete(dao, dto.getPfaHumanBinaryHistoryId());
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setPfaHumanBinaryHistoryId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void insert(HumanBinaryHistoryDtoInterface dto) throws MospException {
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
		dto.setPfaHumanBinaryHistoryId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	/**
	 * 登録の妥当性確認。
	 * @param dto 人事汎用バイナリ履歴情報
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	public void validate(HumanBinaryHistoryDtoInterface dto) throws MospException {
		// ファイル名文字数確認
		checkLength(dto.getFileName(), PlatformHumanConst.LEN_FILE_NAME, PfNameUtility.fileName(mospParams));
		// 人事汎用管理区分設定情報取得
		ViewConfigProperty viewConfig = mospParams.getProperties().getViewConfigProperties()
			.get(dto.getHumanItemType());
		// 情報確認
		if (viewConfig != null) {
			boolean isHumanExist = viewConfig.isHumanExist();
			if (isHumanExist) {
				return;
			}
		}
		// 人事マスタ取得
		HumanDtoInterface humanDao = humanReference.getHumanInfo(dto.getPersonalId(), dto.getActivateDate());
		// 人事マスタがある場合
		if (humanDao != null) {
			return;
		}
		// エラーメッセージを設定
		PfMessageUtility.addErrorPersonalBasisInfoNotExist(mospParams);
	}
	
	/**
	 * 履歴追加時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkAdd(HumanBinaryHistoryDtoInterface dto) throws MospException {
		// 対象レコードの有効日が重複していないかを確認
		checkDuplicateAdd(dao.findForKey(dto.getPersonalId(), dto.getHumanItemType(), dto.getActivateDate()));
	}
	
	/**
	 * 履歴更新時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkUpdate(HumanBinaryHistoryDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getPfaHumanBinaryHistoryId());
		// 前データ取得
		HumanBinaryHistoryDtoInterface oldDto = dao.findForKey(dto.getPersonalId(), dto.getHumanItemType(),
				dto.getActivateDate());
		// 画像を設定
		dto.setHumanItemBinary(oldDto.getHumanItemBinary());
	}
	
}
