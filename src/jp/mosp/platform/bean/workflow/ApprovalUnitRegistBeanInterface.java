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
package jp.mosp.platform.bean.workflow;

import java.util.Date;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dto.workflow.ApprovalUnitDtoInterface;

/**
 * 承認ユニットマスタ登録インターフェース。
 */
public interface ApprovalUnitRegistBeanInterface extends BaseBeanInterface {
	
	/**
	 * 登録用DTOを取得する。<br>
	 * @return 初期DTO
	 */
	ApprovalUnitDtoInterface getInitDto();
	
	/**
	 * 新規登録を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void insert(ApprovalUnitDtoInterface dto) throws MospException;
	
	/**
	 * 履歴追加を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void add(ApprovalUnitDtoInterface dto) throws MospException;
	
	/**
	 * 履歴更新を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void update(ApprovalUnitDtoInterface dto) throws MospException;
	
	/**
	 * 一括更新処理を行う。<br>
	 * @param idArray 対象レコード識別ID配列
	 * @param activateDate 有効日
	 * @param inactivateFlag 無効フラグ
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void update(long[] idArray, Date activateDate, int inactivateFlag) throws MospException;
	
	/**
	 * 論理削除(履歴)を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void delete(ApprovalUnitDtoInterface dto) throws MospException;
	
	/**
	 * 登録処理を行う。<br>
	 * 同一ユニットコード、有効日のレコードがあった場合は、論理削除の上、登録する。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void regist(ApprovalUnitDtoInterface dto) throws MospException;
	
	/**
	 * 登録情報の妥当性を確認確認する。<br>
	 * 行インデックスがnullでない場合、エラーメッセージに行番号が加えられる。<br>
	 * @param dto 対象DTO
	 * @param row 行インデックス
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void validate(ApprovalUnitDtoInterface dto, Integer row) throws MospException;
	
}
