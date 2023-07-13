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
import jp.mosp.platform.dto.human.HumanBinaryNormalDtoInterface;

/**
 * 人事汎用バイナリ通常情報登録クラス。
 */
public interface HumanBinaryNormalRegistBeanInterface extends BaseBeanInterface {
	
	/**
	 * 登録用DTOを取得する。
	 * @return 初期DTO
	 */
	HumanBinaryNormalDtoInterface getInitDto();
	
	/**
	 * 新規登録を行う。
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void insert(HumanBinaryNormalDtoInterface dto) throws MospException;
	
	/**
	 * 論理削除を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void delete(HumanBinaryNormalDtoInterface dto) throws MospException;
	
	/**
	 * 論理削除を行う。(人事汎用管理区分全般)<br>
	 * @param divisions 人事汎用管理区分
	 * @param viewKey 人事汎用管理表示区分
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void deleteDeadInputItem(Set<String> divisions, String viewKey) throws MospException;
	
	/**
	 * 更新を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	public void update(HumanBinaryNormalDtoInterface dto) throws MospException;
	
	/**
	 * 登録情報の妥当性を確認確認する。<br>
	 * 行インデックスがnullでない場合、エラーメッセージに行番号が加えられる。<br>
	 * @param dto 対象DTO
	 * @param row 行インデックス
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void validate(HumanBinaryNormalDtoInterface dto, Integer row) throws MospException;
}
