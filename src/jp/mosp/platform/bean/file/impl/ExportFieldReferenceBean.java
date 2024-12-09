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
package jp.mosp.platform.bean.file.impl;

import java.util.ArrayList;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.file.ExportFieldReferenceBeanInterface;
import jp.mosp.platform.dao.file.ExportFieldDaoInterface;
import jp.mosp.platform.dto.file.ExportFieldDtoInterface;

/**
 * エクスポートフィールドマスタ参照クラス。
 */
public class ExportFieldReferenceBean extends PlatformBean implements ExportFieldReferenceBeanInterface {
	
	/**
	 * エクスポートフィールドマスタDAO。
	 */
	private ExportFieldDaoInterface dao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public ExportFieldReferenceBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		dao = createDaoInstance(ExportFieldDaoInterface.class);
	}
	
	@Override
	public List<ExportFieldDtoInterface> getExportFieldList(String exportCode) throws MospException {
		return dao.findForList(exportCode);
	}
	
	@Override
	public List<String> getExportFieldNameList(String exportCode) throws MospException {
		// エクスポートフィールド名称リストを準備
		List<String> list = new ArrayList<String>();
		// エクスポートフィールド情報毎に処理
		for (ExportFieldDtoInterface dto : getExportFieldList(exportCode)) {
			// エクスポートフィールド名称を追加
			list.add(dto.getFieldName());
		}
		// エクスポートフィールド名称リストを取得
		return list;
	}
	
	@Override
	public List<ExportFieldDtoInterface> getLikeStartNameList(String exportCode, String[] aryFieldName)
			throws MospException {
		// エクスポートコードとフィールド名称（前方一致）でエクスポートマスタリストを取得
		return dao.findLikeStartNameList(exportCode, aryFieldName);
	}
	
}
