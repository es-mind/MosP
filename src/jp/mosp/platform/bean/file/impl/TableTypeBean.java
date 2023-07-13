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
package jp.mosp.platform.bean.file.impl;

import java.util.ArrayList;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.file.TableTypeBeanInterface;
import jp.mosp.platform.utils.PlatformUtility;

/**
 * テーブル区分配列(インポート及びエクスポート)取得処理。<br>
 */
public class TableTypeBean extends PlatformBean implements TableTypeBeanInterface {
	
	/**
	 * コードキー(人事管理インポートテーブル区分)。<br>
	 */
	public static final String		CODE_KEY_HUMAN_IMPORT	= "HumanImportTableType";
	
	/**
	 * コードキー(人事管理エクスポートテーブル区分)。<br>
	 */
	public static final String		CODE_KEY_HUMAN_EXPORT	= "HumanExportTableType";
	
	/**
	 * コードキー(テーブル区分配列(インポート及びエクスポート)取得追加処理)。<br>
	 */
	protected static final String	CODE_KEY_ADD_TABLE_TYPE	= "AdditionlLogicTableType";
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public TableTypeBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// 処理無し
	}
	
	@Override
	public String[][] getTableTypeArray(String tableTypeCodeKey, boolean needBlank) throws MospException {
		// MosP設定情報からテーブル区分配列を取得
		String[][] tableTypes = PlatformUtility.getTableTypeArray(mospParams, tableTypeCodeKey, needBlank);
		// テーブル区分配列をリストに変換
		List<String[]> list = new ArrayList<String[]>(MospUtility.asList(tableTypes));
		// 追加業務ロジック処理(テーブル区分配列項目追加)
		doAdditionalLogic(CODE_KEY_ADD_TABLE_TYPE, tableTypeCodeKey, list);
		// テーブル区分配列を取得
		return MospUtility.toArrayArray(list);
	}
	
}
