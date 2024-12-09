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
package jp.mosp.platform.bean.human;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Set;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dto.human.HumanHistoryDtoInterface;

/**
 * 人事汎用履歴情報登録インターフェース。
 */
public interface HumanHistoryRegistBeanInterface extends BaseBeanInterface {
	
	/**
	 * 登録用DTOを取得する。<br>
	 * @return 初期DTO
	 */
	HumanHistoryDtoInterface getInitDto();
	
	/**
	 * 新規登録(履歴追加)を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	public void add(HumanHistoryDtoInterface dto) throws MospException;
	
	/**
	 * 履歴更新を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void update(HumanHistoryDtoInterface dto) throws MospException;
	
	/**
	 * 論理削除を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException  インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void delete(HumanHistoryDtoInterface dto) throws MospException;
	
	/**
	 * 論理削除を行う。<br>
	 * レコード識別IDで取得される情報に対して削除を行う。<br>
	 * @param idArray 対象レコード識別ID配列
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void delete(long[] idArray) throws MospException;
	
	/**
	 * 登録の妥当性を確認する。
	 * @param dto 人事汎用履歴情報
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	public void validate(HumanHistoryDtoInterface dto) throws MospException;
	
	/**
	 * 新規登録(履歴追加)を行う。【人事汎用】<br>
	 * 新規登録(履歴追加)を行う。<br>
	 * MosP処理情報からリクエストパラメータ群を取得し、人事汎用通常情報を登録する。<br>
	 * 登録対象人事汎用項目は、人事汎用管理区分、人事汎用管理表示区分を用いてMosP処理情報から取得する。<br>
	 * @param division 人事汎用管理区分
	 * @param viewKey 人事汎用管理表示区分
	 * @param personalId 個人ID
	 * @param activeDate 有効日
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void add(String division, String viewKey, String personalId, Date activeDate) throws MospException;
	
	/**
	 * 登録を行う。
	 * 既に情報が登録されている場合は更新する。
	 * @param dto  対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void regist(HumanHistoryDtoInterface dto) throws MospException;
	
	/**
	 * 未使用管理項目の論理削除を行う。<br>
	 * @param divisions 人事汎用管理区分
	 * @param viewKey 人事汎用管理表示区分
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void deleteDeadInputItem(Set<String> divisions, String viewKey) throws MospException;
	
	/**
	 * 履歴編集を行う。【人事汎用】<br>
	 * 履歴更新を行う。<br>
	 * MosP処理情報からリクエストパラメータ群を取得し、人事汎用通常情報を登録する。<br>
	 * 登録対象人事汎用項目は、人事汎用管理区分、人事汎用管理表示区分を用いてMosP処理情報から取得する。<br>
	 * @param division 人事汎用管理区分
	 * @param viewKey 人事汎用管理表示区分
	 * @param personalId 個人ID
	 * @param activeDate 有効日
	 * @param recordsMap レコード識別ID
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void update(String division, String viewKey, String personalId, Date activeDate,
			LinkedHashMap<String, Long> recordsMap) throws MospException;
	
	/**
	 * 論理削除を行う。【人事汎用】<br>
	 * @param division 人事汎用管理区分
	 * @param viewKey 人事汎用管理表示区分
	 * @param recordsMap レコード識別IDマップ（K:項目ID,V：レコード識別ID）
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void delete(String division, String viewKey, LinkedHashMap<String, Long> recordsMap) throws MospException;
	
}
