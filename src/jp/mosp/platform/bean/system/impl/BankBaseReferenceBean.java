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
package jp.mosp.platform.bean.system.impl;

import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.system.BankBaseReferenceBeanInterface;
import jp.mosp.platform.dao.system.BankBaseDaoInterface;
import jp.mosp.platform.dto.system.BankBaseDtoInterface;

/**
 * 銀行マスタ参照クラス。
 */
public class BankBaseReferenceBean extends PlatformBean implements BankBaseReferenceBeanInterface {
	
	/**
	 * 銀行マスタDAOクラス。
	 */
	private BankBaseDaoInterface dao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public BankBaseReferenceBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		dao = createDaoInstance(BankBaseDaoInterface.class);
	}
	
	@Override
	public String[][] getSelectArray(String value, boolean needBlank) throws MospException {
		return getSelectArray(value, needBlank, true);
	}
	
	/**
	 * プルダウン用配列を取得する。
	 * @param value 検索値
	 * @param needBlank     空白行要否(true：空白行要、false：空白行不要)
	 * @param viewCode      コード表示(true：コード表示、false：コード非表示)
	 * @return プルダウン用配列
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected String[][] getSelectArray(String value, boolean needBlank, boolean viewCode) throws MospException {
		// 一覧取得
		List<BankBaseDtoInterface> list = dao.searchBankBaseInfo(value);
		// 一覧件数確認
		if (list.size() == 0) {
			// 対象データ無し
			return getNoObjectDataPulldown();
		}
		// コード最大長取得
		int length = getMaxCodeLength(list, viewCode);
		// プルダウン用配列及びインデックス準備
		String[][] array = prepareSelectArray(list.size(), needBlank);
		int idx = needBlank ? 1 : 0;
		// プルダウン用配列作成
		for (BankBaseDtoInterface dto : list) {
			// コード設定
			array[idx][0] = dto.getBankCode();
			// コード+名称
			array[idx++][1] = getCodedName(dto.getBankCode(), dto.getBankName(), length);
		}
		return array;
	}
	
	/**
	 * リスト中のDTOにおけるコード最大文字数を取得する。<br>
	 * @param list     対象リスト
	 * @param viewCode コード表示(true：コード表示、false：コード非表示)
	 * @return リスト中のDTOにおけるコード最大文字数
	 */
	protected int getMaxCodeLength(List<BankBaseDtoInterface> list, boolean viewCode) {
		// コード表示確認
		if (viewCode == false) {
			return 0;
		}
		// コード最大文字数
		int length = 0;
		// コード最大文字数確認
		for (BankBaseDtoInterface dto : list) {
			if (dto.getBankCode().length() > length) {
				length = dto.getBankCode().length();
			}
		}
		return length;
	}
	
	@Override
	public BankBaseDtoInterface findForKey(String code) throws MospException {
		return dao.findForKey(code);
	}
	
}
