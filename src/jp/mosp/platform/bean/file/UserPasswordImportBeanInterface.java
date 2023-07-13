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
package jp.mosp.platform.bean.file;

import java.util.List;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dto.file.ImportFieldDtoInterface;

/**
 * ユーザパスワード情報インポートインターフェース。
 *
 */
public interface UserPasswordImportBeanInterface extends ImportBeanInterface, BaseBeanInterface {
	
	/**
	 * インポート処理を行う。<br>
	 * 登録情報リストを、インポートフィールド情報リストに基づき
	 * ユーザパスワード情報に変換し、登録を行う。
	 * <br>
	 * @param fieldList インポートフィールド情報リスト
	 * @param dataList  登録情報リスト
	 * @return 登録件数
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	int importFile(List<ImportFieldDtoInterface> fieldList, List<String[]> dataList) throws MospException;
	
}
