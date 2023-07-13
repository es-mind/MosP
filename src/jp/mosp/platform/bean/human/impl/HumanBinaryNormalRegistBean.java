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
import java.util.List;
import java.util.Set;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.xml.TableItemProperty;
import jp.mosp.platform.bean.human.HumanBinaryNormalRegistBeanInterface;
import jp.mosp.platform.bean.human.HumanGeneralBeanInterface;
import jp.mosp.platform.bean.human.HumanReferenceBeanInterface;
import jp.mosp.platform.bean.human.base.PlatformHumanBean;
import jp.mosp.platform.dao.human.HumanBinaryNormalDaoInterface;
import jp.mosp.platform.dto.human.HumanBinaryNormalDtoInterface;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.dto.human.impl.PfaHumanBinaryNormalDto;
import jp.mosp.platform.human.constant.PlatformHumanConst;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.platform.utils.PfNameUtility;

/**
 * 人事汎用バイナリ通常情報登録クラス。
 */
public class HumanBinaryNormalRegistBean extends PlatformHumanBean implements HumanBinaryNormalRegistBeanInterface {
	
	/**
	 * 人事汎用バイナリ情報DAOクラス。<br>
	 */
	protected HumanBinaryNormalDaoInterface	dao;
	
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
	public HumanBinaryNormalRegistBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// 継承基の処理を実行
		super.initBean();
		// DAOを準備
		dao = createDaoInstance(HumanBinaryNormalDaoInterface.class);
		// Beanを準備
		humanGeneral = createBeanInstance(HumanGeneralBeanInterface.class);
		humanReference = createBeanInstance(HumanReferenceBeanInterface.class);
	}
	
	@Override
	public HumanBinaryNormalDtoInterface getInitDto() {
		return new PfaHumanBinaryNormalDto();
	}
	
	@Override
	public void delete(HumanBinaryNormalDtoInterface dto) throws MospException {
		// 削除情報の検証
		checkDelete(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 論理削除
		logicalDelete(dao, dto.getPfaHumanBinaryNormalId());
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
		List<HumanBinaryNormalDtoInterface> listDeleteItem = dao.findForInfoNotIn(list);
		
		// 論理削除
		for (HumanBinaryNormalDtoInterface dto : listDeleteItem) {
			// 削除
			delete(dto);
		}
		
	}
	
	@Override
	public void insert(HumanBinaryNormalDtoInterface dto) throws MospException {
		// DTOの妥当性確認
		validate(dto, null);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 新規登録情報の検証
		checkInsert(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setPfaHumanBinaryNormalId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void update(HumanBinaryNormalDtoInterface dto) throws MospException {
		// DTOの妥当性確認
		validate(dto, null);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 履歴更新情報の検証
		checkUpdate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 論理削除
		logicalDelete(dao, dto.getPfaHumanBinaryNormalId());
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setPfaHumanBinaryNormalId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	/**
	 * 更新時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkUpdate(HumanBinaryNormalDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getPfaHumanBinaryNormalId());
	}
	
	@Override
	public void validate(HumanBinaryNormalDtoInterface dto, Integer row) throws MospException {
		// 必須確認(人事汎用項目)
		checkRequired(dto.getHumanItemType(), dto.getHumanItemType(), row);
		// ファイル名文字数確認
		checkLength(dto.getFileName(), PlatformHumanConst.LEN_FILE_NAME, PfNameUtility.fileName(mospParams));
		// 人事入社情報取得
		Date entranceDate = getEntranceDate(dto.getPersonalId());
		// 人事入社情報確認
		if (entranceDate == null) {
			// 社員が入社していない場合のメッセージを追加
			PfMessageUtility.addErrorEmployeeNotJoin(mospParams);
			return;
		}
	}
	
	/**
	 * 削除時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkDelete(HumanBinaryNormalDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getPfaHumanBinaryNormalId());
	}
	
	/**
	 * 新規登録時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkInsert(HumanBinaryNormalDtoInterface dto) throws MospException {
		// 人事マスタ取得
		List<HumanDtoInterface> humanList = humanReference.getHistory(dto.getPersonalId());
		// 人事マスタがない場合
		if (humanList.isEmpty()) {
			// エラーメッセージを設定
			PfMessageUtility.addErrorPersonalBasisInfoNotExist(mospParams);
		}
	}
	
}
