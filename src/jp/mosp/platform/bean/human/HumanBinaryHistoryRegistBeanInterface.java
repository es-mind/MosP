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

import java.util.Set;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dto.human.HumanBinaryHistoryDtoInterface;

/**
 * 人事汎用バイナリ履歴情報登録インターフェース。
 */
public interface HumanBinaryHistoryRegistBeanInterface extends BaseBeanInterface {
	
	/**
	 * 登録用DTOを取得する。
	 * @return 初期DTO
	 */
	HumanBinaryHistoryDtoInterface getInitDto();
	
	/**
	 * 登録処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	void insert(HumanBinaryHistoryDtoInterface dto) throws MospException;
	
	/**
	 * 更新処理を行う。<br>
	 * 画像以外全ての項目だけ更新する。
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	void update(HumanBinaryHistoryDtoInterface dto) throws MospException;
	
	/**
	 * 論理削除を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException  インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void delete(HumanBinaryHistoryDtoInterface dto) throws MospException;
	
	/**
	 * 論理削除を行う。(人事汎用管理区分全般)<br>
	 * @param divisions 人事汎用管理区分
	 * @param viewKey 人事汎用管理表示区分
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void deleteDeadInputItem(Set<String> divisions, String viewKey) throws MospException;
	
}
