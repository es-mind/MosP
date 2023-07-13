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
package jp.mosp.platform.bean.human;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Set;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dto.human.HumanArrayDtoInterface;

/**
 * 人事汎用一覧情報登録インターフェース。
 */
public interface HumanArrayRegistBeanInterface extends BaseBeanInterface {
	
	/**
	 * 登録用DTOを取得する。
	 * @return 初期DTO
	 */
	HumanArrayDtoInterface getInitDto();
	
	/**
	 * 登録を行う。<br>
	 * 行IDの情報が存在しない場合は新規登録、存在する場合は更新を行う。<br>
	 * MosP処理情報からリクエストパラメータ群を取得し、人事汎用通常情報を登録する。<br>
	 * 登録対象人事汎用項目は、人事汎用管理区分、人事汎用管理表示区分を用いてMosP処理情報から取得する。<br>
	 * @param division 人事汎用管理区分
	 * @param viewKey 人事汎用管理表示区分
	 * @param personalId 個人ID
	 * @param activeDate 有効日
	 * @param rowId 行ID
	 * @param linkedHashMap レコード識別ID
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void regist(String division, String viewKey, String personalId, Date activeDate, int rowId,
			LinkedHashMap<String, Long> linkedHashMap) throws MospException;
	
	/**
	 * 登録処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	void insert(HumanArrayDtoInterface dto) throws MospException;
	
	/**
	 * 論理削除を行う。<br>
	 * @param division 人事汎用管理区分
	 * @param viewKey 人事汎用管理表示区分
	 * @param personalId 個人ID
	 * @param activeDate 有効日
	 * @param rowId 行ID
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void delete(String division, String viewKey, String personalId, Date activeDate, int rowId) throws MospException;
	
	/**
	 * 未使用管理項目の論理削除を行う。<br>
	 * @param divisions 人事汎用管理区分
	 * @param viewKey 人事汎用管理表示区分
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void deleteDeadInputItem(Set<String> divisions, String viewKey) throws MospException;
	
	/**
	 * 論理削除を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException  インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void delete(HumanArrayDtoInterface dto) throws MospException;
	
	/**
	 * 新規登録用の行IDを取得する。<br>
	 * @return 新規登録用行ID
	 * @throws MospException  インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	int getRowId() throws MospException;
	
	/**
	 * 論理削除を行う。（レコード識別ID使用）<br>
	 * @param division 人事汎用管理区分
	 * @param viewKey 人事汎用管理表示区分
	 * @param recordsMap レコード識別IDマップ
	 * @param rowId 行ID
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void delete(String division, String viewKey, int rowId, LinkedHashMap<String, Long> recordsMap)
			throws MospException;
}
