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
package jp.mosp.platform.bean.message;

import java.util.List;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dto.message.MessageDtoInterface;

/**
 * メッセージテーブル検索インターフェース。
 */
public interface MessageSearchBeanInterface extends BaseBeanInterface {
	
	/**
	 * 検索条件からメッセージリストを取得する。<br><br>
	 * @return メッセージリスト
	 * @throws MospException インスタンスの取得、SQLの作成及び実行に失敗した場合
	 */
	List<MessageDtoInterface> getSearchList() throws MospException;
	
	/**
	 * @param targetYear セットする 対象年。
	 */
	void setTargetYear(int targetYear);
	
	/**
	 * @param targetMonth セットする 対象月。
	 */
	void setTargetMonth(int targetMonth);
	
	/**
	 * @param messageNo セットする メッセージNo。
	 */
	void setMessageNo(String messageNo);
	
	/**
	 * @param messageType セットする メッセージ区分。
	 */
	void setMessageType(String messageType);
	
	/**
	 * @param messageImportance セットする 重要度。
	 */
	void setMessageImportance(String messageImportance);
	
	/**
	 * @param messageTitle セットする メッセージタイトル。
	 */
	void setMessageTitle(String messageTitle);
	
	/**
	 * @param employeeName セットする 登録者氏名。
	 */
	void setEmployeeName(String employeeName);
	
	/**
	 * @param inactivateFlag セットする 有効無効フラグ。
	 */
	void setInactivateFlag(String inactivateFlag);
	
}
