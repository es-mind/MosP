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
package jp.mosp.platform.bean.system.impl;

import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.system.BankBranchReferenceBeanInterface;
import jp.mosp.platform.dao.system.BankBranchDaoInterface;
import jp.mosp.platform.dto.system.BankBranchDtoInterface;

/**
 * 銀行支店マスタ参照クラス。
 */
public class BankBranchReferenceBean extends PlatformBean implements BankBranchReferenceBeanInterface {
	
	/**
	 * 銀行マスタDAOクラス。
	 */
	protected BankBranchDaoInterface dao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public BankBranchReferenceBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		dao = createDaoInstance(BankBranchDaoInterface.class);
	}
	
	@Override
	public String[][] getSelectArray(String bankCode, String value, boolean needBlank) throws MospException {
		return getSelectArray(bankCode, value, needBlank, true);
	}
	
	/**
	 * プルダウン用配列を取得する。
	 * @param bankCode 銀行コード
	 * @param value 検索値
	 * @param needBlank     空白行要否(true：空白行要、false：空白行不要)
	 * @param viewCode      コード表示(true：コード表示、false：コード非表示)
	 * @return プルダウン用配列
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected String[][] getSelectArray(String bankCode, String value, boolean needBlank, boolean viewCode)
			throws MospException {
		// 一覧取得
		List<BankBranchDtoInterface> list = dao.searchBankBranchInfo(bankCode, value);
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
		for (BankBranchDtoInterface dto : list) {
			// コード設定
			array[idx][0] = dto.getBranchCode();
			// コード+名称
			array[idx++][1] = getCodedName(dto.getBranchCode(), dto.getBranchName(), length);
		}
		return array;
	}
	
	/**
	 * リスト中のDTOにおけるコード最大文字数を取得する。<br>
	 * @param list     対象リスト
	 * @param viewCode コード表示(true：コード表示、false：コード非表示)
	 * @return リスト中のDTOにおけるコード最大文字数
	 */
	protected int getMaxCodeLength(List<BankBranchDtoInterface> list, boolean viewCode) {
		// コード表示確認
		if (viewCode == false) {
			return 0;
		}
		// コード最大文字数
		int length = 0;
		// コード最大文字数確認
		for (BankBranchDtoInterface dto : list) {
			if (dto.getBranchCode().length() > length) {
				length = dto.getBranchCode().length();
			}
		}
		return length;
	}
	
	@Override
	public BankBranchDtoInterface findForKey(String bankCode, String branchCode) throws MospException {
		return dao.findForKey(bankCode, branchCode);
	}
	
}
