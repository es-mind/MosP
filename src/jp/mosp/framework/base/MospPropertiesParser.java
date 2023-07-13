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
package jp.mosp.framework.base;

import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.property.MospProperties;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.xml.MospPropertiesBuilder;
import jp.mosp.framework.xml.MospPropertiesBuilderInterface;

/**
 * MosP設定情報を作成する。<br>
 * <br>
 * WEB-INF/xml以下で「.xml」で終わるファイルを対象とし、
 * MosP設定情報を作成する。<br>
 * MosP設定情報ファイルはファイル名の自然順序付けに依ってソートされる。<br>
 * 但し、「mosp.xml」は、必ず先頭となる。<br>
 * 同様のキーを持つ要素が存在した場合、当該要素は上書きされる。<br>
 * <br>
 * MosP設定情報ファイルは、ドキュメント要素としてMosP要素を持たなくてはならない。<br>
 * MosP要素の子要素は、「key」属性を持たなくてはならない。<br>
 */
public final class MospPropertiesParser {
	
	/**
	 * 他クラスからのインスタンス化を防止する。<br>
	 */
	private MospPropertiesParser() {
		// 処理無し
	}
	
	/**
	 * MosP設定情報を作成する。<br>
	 * @param docBase MosPアプリケーションが配置されている実際のパス
	 * @return MosP設定情報
	 * @throws MospException MosP設定情報の作成に失敗した場合
	 */
	public static MospProperties parseMospProperties(String docBase) throws MospException {
		// インスタンスの生成
		MospPropertiesBuilderInterface builder = new MospPropertiesBuilder();
		// MosP設定情報生成
		MospProperties mospProperties = builder.build(docBase);
		// ドキュメントベース設定
		mospProperties.setApplicationProperty(MospConst.APP_DOCBASE, docBase);
		// 設定ファイル読込時間取得
		String propertyTime = String.valueOf(DateUtility.getSystemTimeAndSecond().getTime());
		// 設定ファイル読込時間設定
		mospProperties.setApplicationProperty(MospConst.APP_PROPERTY_TIME, propertyTime);
		return mospProperties;
	}
	
}
