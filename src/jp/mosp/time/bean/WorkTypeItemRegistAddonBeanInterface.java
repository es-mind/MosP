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
package jp.mosp.time.bean;

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.WorkTypeItemDtoInterface;

/**
 * 勤務形態項目登録追加処理インターフェース。
 */
public interface WorkTypeItemRegistAddonBeanInterface {
	
	/**
	 * 登録用DTOを取得する。<br>
	 * @param dto 対象DTO
	 * @return 初期DTO
	 */
	WorkTypeItemDtoInterface getInitDto(WorkTypeItemDtoInterface dto);
	
	/**
	 * 単一新規登録を行う
	 * @param dto 対象DTO
	 * @throws MospException エラーが発生した場合
	 */
	void insert(WorkTypeItemDtoInterface dto) throws MospException;
	
	/**
	 * 新規登録を行う。<br>
	 * @param dtoList 対象DTOリスト
	 * @throws MospException エラーが発生した場合
	 */
	void insert(List<WorkTypeItemDtoInterface> dtoList) throws MospException;
	
	/**
	 * 履歴更新を行う。<br>
	 * @param dtoList 対象DTOリスト
	 * @throws MospException エラーが発生した場合
	 */
	void add(List<WorkTypeItemDtoInterface> dtoList) throws MospException;
	
	/**
	 * 単一更新処理を行う
	 * @param dto 対象DTO
	 * @throws MospException エラーが発生した場合
	 */
	void update(WorkTypeItemDtoInterface dto) throws MospException;
	
	/**
	 * 履歴更新を行う。<br>
	 * @param dtoList 対象DTOリスト
	 * @throws MospException エラーが発生した場合
	 */
	void update(List<WorkTypeItemDtoInterface> dtoList) throws MospException;
	
	/**
	 * 一括更新処理を行う。<br>
	 * @param idArray 対象レコード識別ID配列
	 * @param activateDate 有効日
	 * @param inactivateFlag 無効フラグ
	 * @throws MospException エラーが発生した場合
	 */
	void update(long[] idArray, Date activateDate, int inactivateFlag) throws MospException;
	
	/**
	 * 論理削除(履歴)を行う。<br>
	 * @param workTypeCode 勤務形態コード
	 * @param activateDate 有効日
	 * @throws MospException エラーが発生した場合
	 */
	void delete(String workTypeCode, Date activateDate) throws MospException;
	
	/**
	 * 勤務形態項目情報の妥当性確認を行う。<br>
	 * 時間チェック等。<br>
	 * @param itemList 勤務形態項目リスト
	 * @throws MospException エラーが発生した場合
	 */
	void checkItemTimeValidate(List<WorkTypeItemDtoInterface> itemList) throws MospException;
	
	/**
	 * 新規登録時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException エラーが発生した場合
	 */
	void checkInsert(WorkTypeItemDtoInterface dto) throws MospException;
	
	/**
	 * 履歴追加時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException エラーが発生した場合
	 */
	void checkAdd(WorkTypeItemDtoInterface dto) throws MospException;
	
	/**
	 * 履歴更新時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException エラーが発生した場合
	 */
	void checkUpdate(WorkTypeItemDtoInterface dto) throws MospException;
	
	/**
	 *  削除時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException エラーが発生した場合
	 */
	void checkDelete(WorkTypeItemDtoInterface dto) throws MospException;
	
	/**
	 * 登録情報の妥当性を確認する。
	 * @param dto 対象DTO
	 * @throws MospException エラーが発生した場合
	 */
	void validate(WorkTypeItemDtoInterface dto) throws MospException;
	
}
