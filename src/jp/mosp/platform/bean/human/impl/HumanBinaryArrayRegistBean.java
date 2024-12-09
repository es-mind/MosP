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
import jp.mosp.framework.xml.TableItemProperty;
import jp.mosp.platform.bean.human.HumanBinaryArrayRegistBeanInterface;
import jp.mosp.platform.bean.human.HumanGeneralBeanInterface;
import jp.mosp.platform.bean.human.HumanReferenceBeanInterface;
import jp.mosp.platform.bean.human.base.PlatformHumanBean;
import jp.mosp.platform.dao.human.HumanBinaryArrayDaoInterface;
import jp.mosp.platform.dto.human.HumanBinaryArrayDtoInterface;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.dto.human.impl.PfaHumanBinaryArrayDto;
import jp.mosp.platform.human.constant.PlatformHumanConst;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.platform.utils.PfNameUtility;

/**
 * 人事汎用バイナリ一覧情報登録クラス。
 */
public class HumanBinaryArrayRegistBean extends PlatformHumanBean implements HumanBinaryArrayRegistBeanInterface {
	
	/**
	 * 人事兼通常報DAOクラス。<br>
	 */
	protected HumanBinaryArrayDaoInterface	dao;
	
	/**
	 * 人事汎用管理機能クラス。<br>
	 */
	protected HumanGeneralBeanInterface		humanGeneral;
	
	/**
	 * 人事情報参照クラス。<br>
	 */
	protected HumanReferenceBeanInterface	humanReference;
	
	
	/**
	 * {@link PlatformHumanBean#PlatformHumanBean()}を実行する。<br>
	 */
	public HumanBinaryArrayRegistBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// 継承基の処理を実行
		super.initBean();
		// DAOを準備
		dao = createDaoInstance(HumanBinaryArrayDaoInterface.class);
		// Beanを準備
		humanGeneral = createBeanInstance(HumanGeneralBeanInterface.class);
		humanReference = createBeanInstance(HumanReferenceBeanInterface.class);
	}
	
	@Override
	public HumanBinaryArrayDtoInterface getInitDto() {
		return new PfaHumanBinaryArrayDto();
	}
	
	@Override
	public void insert(HumanBinaryArrayDtoInterface dto) throws MospException {
		// DTOの妥当性確認
		validate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 人事マスタ取得
		HumanDtoInterface humanDto = humanReference.getHumanInfo(dto.getPersonalId(), dto.getActivateDate());
		// 人事マスタがない場合
		if (humanDto == null) {
			// エラーメッセージを設定
			PfMessageUtility.addErrorPersonalBasisInfoNotExist(mospParams);
			return;
		}
		// レコード識別ID最大値をインクリメントしてセットする
		dto.setPfaHumanBinaryArrayId(dao.nextRecordId());
		// 行ID設定
		dto.setHumanRowId(getRowId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void delete(HumanBinaryArrayDtoInterface dto) throws MospException {
		// 削除情報の検証
		checkDelete(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 論理削除
		logicalDelete(dao, dto.getPfaHumanBinaryArrayId());
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
		List<HumanBinaryArrayDtoInterface> listDeleteItem = dao.findForInfoNotIn(list);
		
		// 論理削除
		for (HumanBinaryArrayDtoInterface dto : listDeleteItem) {
			// 削除
			delete(dto);
		}
		
	}
	
	/**
	 * 削除の妥当性を確認する。
	 * @param dto 削除対象人事汎用一覧情報
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	private void checkDelete(HumanBinaryArrayDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getPfaHumanBinaryArrayId());
	}
	
	@Override
	public int getRowId() throws MospException {
		// 行ID最大値取得
		int rowId = dao.findForMaxRowId();
		rowId++;
		return rowId;
	}
	
	@Override
	public void update(HumanBinaryArrayDtoInterface dto) throws MospException {
		// DTOの妥当性確認
		validate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 更新対象DTO取得
		HumanBinaryArrayDtoInterface arrayDto = (HumanBinaryArrayDtoInterface)findForKey(dao,
				dto.getPfaHumanBinaryArrayId(), true);
		// 排他確認
		checkExclusive(arrayDto);
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getPfaHumanBinaryArrayId());
		if (mospParams.hasErrorMessage()) {
			// エラーが存在したら登録処理をしない
			return;
		}
		// 論理削除
		logicalDelete(dao, dto.getPfaHumanBinaryArrayId());
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setPfaHumanBinaryArrayId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	/**
	 * 登録情報の妥当性を確認確認する。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void validate(HumanBinaryArrayDtoInterface dto) throws MospException {
		// ファイル名文字数確認
		checkLength(dto.getFileName(), PlatformHumanConst.LEN_FILE_NAME, PfNameUtility.fileName(mospParams));
	}
	
}
