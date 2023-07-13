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
import jp.mosp.platform.dto.system.ReceptionIcCardDtoInterface;

/**
 * カードID受付番号DAOインターフェース。
 */
public interface ReceptionIcCardDaoInterface extends BaseDaoInterface {
	
	/**
	 * 受付番号から受付番号情報を取得する。
	 * @param receiptNumber 受付番号(レコード識別ID)
	 * @return カード番号
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	ReceptionIcCardDtoInterface findForKey(long receiptNumber) throws MospException;
	
	/**
	 * カードIDから受付番号情報を取得する。
	 * @param icCardId カードID
	 * @return カード番号
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	ReceptionIcCardDtoInterface findForIcCardId(String icCardId) throws MospException;
}
