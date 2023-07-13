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
package jp.mosp.platform.bean.system;

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dto.system.IcCardDtoInterface;

/**
 * ICカードマスタ参照インターフェース。
 */
public interface IcCardReferenceBeanInterface extends BaseBeanInterface {
	
	/**
	 * 個人IDと有効日からカード番号を取得する。
	 * @param personalId 個人ID
	 * @param activeDate 有効日
	 * @return カード番号
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<IcCardDtoInterface> findForHumanList(String personalId, Date activeDate) throws MospException;
	
	/**
	 * カードIDと有効日から個人IDを取得する。
	 * @param cardId カード番号
	 * @param activeDate 有効日
	 * @return 個人ID
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	IcCardDtoInterface findForCardIdInfo(String cardId, Date activeDate) throws MospException;
	
	/**
	 * カードIDと有効日からICカード情報を取得する。
	 * @param cardId カード番号
	 * @param activeDate 有効日
	 * @return ICカード情報
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	IcCardDtoInterface findForKey(String cardId, Date activeDate) throws MospException;
	
	/**
	 * 有効日から最新の情報を取得する。
	 * 有効日からICカード情報リストを取得する。
	 * @param activateDate 有効日
	 * @return ICカード情報リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<IcCardDtoInterface> findForActivateDate(Date activateDate) throws MospException;
	
	/**
	 * ICカードIDからICカード情報リストを取得する。
	 * @param icCardId ICカードID
	 * @return ICカード情報リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<IcCardDtoInterface> findForList(String icCardId) throws MospException;
	
}
