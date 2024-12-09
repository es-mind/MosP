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
package jp.mosp.platform.dao.message;

import java.util.Date;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.BaseDaoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dto.message.MessageDtoInterface;

/**
 * メッセージテーブルDAOインターフェース。<br>
 */
public interface MessageDaoInterface extends BaseDaoInterface {
	
	/**
	 * 検索条件(メッセージNo)。
	 */
	String	SEARCH_MESSAGE_NO			= "messageNo";
	
	/**
	 * 検索条件(公開開始日)。
	 */
	String	SEARCH_START_DATE			= "startDate";
	
	/**
	 * 検索条件(公開終了日)。
	 */
	String	SEARCH_END_DATE				= "endDate";
	
	/**
	 * 検索条件(メッセージ区分)。
	 */
	String	SEARCH_MESSAGE_TYPE			= "messageType";
	
	/**
	 * 検索条件(重要度)。
	 */
	String	SEARCH_MESSAGE_IMPORTANCE	= "messageImportance";
	
	/**
	 * 検索条件(メッセージタイトル)。
	 */
	String	SEARCH_MESSAGE_TITLE		= "messageTitle";
	
	/**
	 * 検索条件(社員名)。
	 */
	String	SEARCH_EMPLOYEE_NAME		= "employeeName";
	
	/**
	 * 検索条件(有効無効フラグ)。
	 */
	String	SEARCH_INACTIVATE_FLAG		= "inactivateFlag";
	
	
	/**
	 * メッセージを取得する。<br>
	 * メッセージNoで有効であるメッセージを取得する。<br>
	 * @param messageNo メッセージNo
	 * @return メッセージ
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	MessageDtoInterface findForKey(String messageNo) throws MospException;
	
	/**
	 * 最大メッセージNoを取得する。<br>
	 * @return 最大メッセージNo
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	long getMaxMessageNo() throws MospException;
	
	/**
	 * メッセージリストを取得する。<br>
	 * 対象日(公開開始日から公開終了日まで)で有効である。<br>
	 * 選択された各マスタのコードの組み合わせに紐づいたメッセージリストを取得する。<br>
	 * @param workPlaceCode 勤務地コード
	 * @param employmentContractCode 雇用契約コード
	 * @param sectionCode 所属コード
	 * @param positionCode 職位コード
	 * @param targetDate 対象日
	 * @return メッセージリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<MessageDtoInterface> findForMaster(String workPlaceCode, String employmentContractCode, String sectionCode,
			String positionCode, Date targetDate) throws MospException;
	
	/**
	 * メッセージリストを取得する。<br>
	 * 対象日(公開開始日から公開終了日まで)で有効である。<br>
	 * 個人IDに紐づいたメッセージリストを取得する。<br>
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @return メッセージリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<MessageDtoInterface> findForPersonalId(String personalId, Date targetDate) throws MospException;
	
	/**
	 * 条件による検索。
	 * <p>
	 * 検索条件からメッセージリストを取得する。
	 * </p>
	 * @param param 検索条件
	 * @return メッセージリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<MessageDtoInterface> findForSearch(Map<String, Object> param) throws MospException;
	
	/**
	 * 検索条件取得。
	 * @return メッセージ検索条件マップ
	 */
	Map<String, Object> getParamsMap();
	
}
