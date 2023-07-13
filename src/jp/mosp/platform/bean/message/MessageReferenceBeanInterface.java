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
package jp.mosp.platform.bean.message;

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dto.message.MessageDtoInterface;

/**
 * メッセージテーブル参照インターフェース。
 */
public interface MessageReferenceBeanInterface extends BaseBeanInterface {
	
	/**
	 * メッセージNoからメッセージを取得する。<br>
	 * @param messageNo メッセージNo
	 * @return メッセージ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	MessageDtoInterface findForKey(String messageNo) throws MospException;
	
	/**
	 * 個人ID及び対象日から、メッセージリストを取得する。<br>
	 * 個人IDが設定されたメッセージ及びマスタ組合で設定されたメッセージを取得する。<br>
	 * メッセージリストを、重要度、公開開始日、メッセージNoでソートする。<br>
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @return メッセージリスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<MessageDtoInterface> getMessageList(String personalId, Date targetDate) throws MospException;
	
}
