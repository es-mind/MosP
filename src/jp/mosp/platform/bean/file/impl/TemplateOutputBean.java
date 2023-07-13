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
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.framework.utils.NameUtility;
import jp.mosp.orangesignal.OrangeSignalUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.file.ImportFieldReferenceBeanInterface;
import jp.mosp.platform.bean.file.ImportReferenceBeanInterface;
import jp.mosp.platform.bean.file.TemplateOutputBeanInterface;
import jp.mosp.platform.constant.PlatformFileConst;
import jp.mosp.platform.dto.file.ImportDtoInterface;
import jp.mosp.platform.dto.file.ImportFieldDtoInterface;
import jp.mosp.platform.utils.PfNameUtility;

/**
 * テンプレート出力クラス。
 */
public class TemplateOutputBean extends PlatformBean implements TemplateOutputBeanInterface {
	
	/**
	 * 拡張子(.csv)
	 */
	public static final String					FILENAME_EXTENSION_CSV	= ".csv";
	
	/**
	 * インポートマスタ参照クラス。
	 */
	protected ImportReferenceBeanInterface		importReference;
	
	/**
	 * インポートフィールドマスタ参照クラス。
	 */
	protected ImportFieldReferenceBeanInterface	importFieldReference;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public TemplateOutputBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		importReference = createBeanInstance(ImportReferenceBeanInterface.class);
		importFieldReference = createBeanInstance(ImportFieldReferenceBeanInterface.class);
	}
	
	@Override
	public void output(String importCode) throws MospException {
		ImportDtoInterface dto = importReference.findForKey(importCode);
		// 送出ファイルをMosP処理情報に設定
		mospParams.setFile(OrangeSignalUtility.getOrangeSignalParams(getTemplate(dto)));
		// 送出ファイル名をMosP処理情報に設定
		mospParams.setFileName(getFileName(dto));
	}
	
	/**
	 * テンプレート用リストを取得する。<br>
	 * @param dto 対象DTO
	 * @return テンプレート用リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected List<String[]> getTemplate(ImportDtoInterface dto) throws MospException {
		List<String[]> list = new ArrayList<String[]>();
		list.add(getHeader(dto));
		return list;
	}
	
	/**
	 * ヘッダ配列を取得する。<br>
	 * @param dto 対象DTO
	 * @return ヘッダ配列
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected String[] getHeader(ImportDtoInterface dto) throws MospException {
		return getHeader(dto.getImportTable(), importFieldReference.getImportFieldList(dto.getImportCode()));
	}
	
	/**
	 * ヘッダ配列を取得する。<br>
	 * @param importTable データ区分
	 * @param list リスト
	 * @return ヘッダ配列
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected String[] getHeader(String importTable, List<ImportFieldDtoInterface> list) throws MospException {
		String[] array = new String[list.size()];
		for (int i = 0; i < array.length; i++) {
			ImportFieldDtoInterface dto = list.get(i);
			// 汎用機能項目の場合
			String[] fieldNames = MospUtility.split(dto.getFieldName(), MospConst.APP_PROPERTY_SEPARATOR);
			// 分割できた場合
			if (fieldNames.length > 1) {
				StringBuilder sb = new StringBuilder(NameUtility.getName(mospParams, fieldNames[1])).append(
						PfNameUtility.parentheses(mospParams, NameUtility.getName(mospParams, fieldNames[0])));
				array[i] = sb.toString();
				continue;
			}
			// コードから取得
			array[i] = mospParams.getProperties().getCodeItemName(importTable, dto.getFieldName());
		}
		return array;
	}
	
	/**
	 * 送出ファイル名を取得する。<br>
	 * @param dto 対象DTO
	 * @return 送出ファイル名
	 */
	protected String getFileName(ImportDtoInterface dto) {
		StringBuffer sb = new StringBuffer();
		// インポートコード
		sb.append(dto.getImportCode());
		if (PlatformFileConst.FILE_TYPE_CSV.equals(dto.getType())) {
			// CSV
			sb.append(FILENAME_EXTENSION_CSV);
		}
		return sb.toString();
	}
	
}
