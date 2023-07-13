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
package jp.mosp.platform.dao.system;

import jp.mosp.framework.base.BaseDaoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dto.system.PostalCodeDtoInterface;

/**
 * 郵便番号マスタDAOインターフェース。<br>
 */
public interface PostalCodeDaoInterface extends BaseDaoInterface {
	
	/**
	 * 郵便番号から郵便番号情報を取得する。
	 * @param postalCode 郵便番号
	 * @return 郵便番号情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	PostalCodeDtoInterface findForKey(String postalCode) throws MospException;
}
