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

import java.sql.Connection;
import java.util.List;

/**
 * DAOインターフェース。
 */
public interface BaseDaoInterface {
	
	/**
	 * {@link MospParams}、{@link Connection}をDAOに設定する。
	 * @param mospParams 設定するMosP処理情報
	 * @param connection 設定するデータベースコネクション
	 */
	void setInitParams(MospParams mospParams, Connection connection);
	
	/**
	 * 初期化。
	 * @throws MospException 実行時例外が発生した場合
	 */
	void initDao() throws MospException;
	
	/**
	 * ResultSetの内容を、DTOにマッピングする。<br>
	 * @return 検索結果(DTO)。
	 * @throws MospException SQL例外が発生した場合
	 */
	BaseDto mapping() throws MospException;
	
	/**
	 * ResultSetの内容を、DTOにマッピングし、リストとして返す。<br>
	 * @return 検索結果(DTOのList)。
	 * @throws MospException SQL例外が発生した場合
	 */
	List<?> mappingAll() throws MospException;
	
	/**
	 * @param id レコード識別ID
	 * @param isUpdate FOR UPDATEフラグ
	 * @return 検索結果(DTO)
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	BaseDto findForKey(long id, boolean isUpdate) throws MospException;
	
	/**
	 * 全レコードを取得する。<br>
	 * 但し、削除フラグが立っているものは対象外。<br>
	 * @return DTOリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<?> findAll() throws MospException;
	
	/**
	 * 挿入。
	 * @param baseDto 対象DTO
	 * @return 結果件数
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	int insert(BaseDtoInterface baseDto) throws MospException;
	
	/**
	 * 更新。
	 * @param baseDto 対象DTO
	 * @return 結果件数
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	int update(BaseDtoInterface baseDto) throws MospException;
	
	/**
	 * 物理削除。
	 * @param baseDto 対象DTO
	 * @return 結果件数
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	int delete(BaseDtoInterface baseDto) throws MospException;
	
	/**
	 * パラメータ設定。
	 * @param baseDto 更新対象DTO
	 * @param isInsert 挿入文フラグ(true：挿入文、false：更新文)
	 * @throws MospException SQL例外が発生した場合
	 */
	void setParams(BaseDtoInterface baseDto, boolean isInsert) throws MospException;
	
	/**
	 * テーブル名を取得する。<br>
	 * @param cls DAOクラス
	 * @return テーブル名文字列
	 * @throws MospException テーブル名の取得に失敗した場合
	 */
	String getTable(Class<?> cls) throws MospException;
	
	/**
	 * レコード識別IDの次の値を取得する。<br>
	 * @return レコード識別IDの次の値
	 * @throws MospException シーケンス操作に失敗した場合
	 */
	long nextRecordId() throws MospException;
	
}
