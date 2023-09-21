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

import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.OvertimeRequestDtoInterface;

/**
 * 残業申請登録追加処理インターフェース。<br>
 */
public interface OvertimeRequestRegistAddonBeanInterface {
	
	/**
	 * 登録用DTOを取得する。<br>
	 * @param dto 対象DTO
	 * @return 初期DTO
	 */
	OvertimeRequestDtoInterface getInitDto(OvertimeRequestDtoInterface dto);
	
	/**
	 * 新規登録を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException エラーが発生した場合
	 */
	void insert(OvertimeRequestDtoInterface dto) throws MospException;
	
	/**
	 * 一括更新処理を行う。<br>
	 * @param idArray 対象レコード識別ID配列
	 * @throws MospException エラーが発生した場合
	 */
	void update(long[] idArray) throws MospException;
	
	/**
	 * 登録処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException エラーが発生した場合
	 */
	void regist(OvertimeRequestDtoInterface dto) throws MospException;
	
	/**
	 * 履歴追加を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException エラーが発生した場合
	 */
	void add(OvertimeRequestDtoInterface dto) throws MospException;
	
	/**
	 * 削除処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException エラーが発生した場合
	 */
	void delete(OvertimeRequestDtoInterface dto) throws MospException;
	
	/**
	 * 一括取下処理を行う。<br>
	 * @param idArray 対象レコード識別ID配列
	 * @throws MospException エラーが発生した場合
	 */
	void withdrawn(long[] idArray) throws MospException;
	
	/**
	 * 登録情報の妥当性を確認する。
	 * @param dto 対象DTO
	 * @throws MospException エラーが発生した場合
	 */
	void validate(OvertimeRequestDtoInterface dto) throws MospException;
	
	/**
	 * 申請日決定時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException エラーが発生した場合
	 */
	void checkSetRequestDate(OvertimeRequestDtoInterface dto) throws MospException;
	
	/**
	 * 下書時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException エラーが発生した場合
	 */
	void checkDraft(OvertimeRequestDtoInterface dto) throws MospException;
	
	/**
	 * 申請時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException エラーが発生した場合
	 */
	void checkAppli(OvertimeRequestDtoInterface dto) throws MospException;
	
	/**
	 * 解除申請時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException エラーが発生した場合
	 */
	void checkCancelAppli(OvertimeRequestDtoInterface dto) throws MospException;
	
	/**
	 * 取下時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException エラーが発生した場合
	 */
	void checkWithdrawn(OvertimeRequestDtoInterface dto) throws MospException;
	
	/**
	 * 承認時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException エラーが発生した場合
	 */
	void checkApproval(OvertimeRequestDtoInterface dto) throws MospException;
	
	/**
	 * 解除承認時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException エラーが発生した場合
	 */
	void checkCancelApproval(OvertimeRequestDtoInterface dto) throws MospException;
	
	/**
	 * 取消時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException エラーが発生した場合
	 */
	void checkCancel(OvertimeRequestDtoInterface dto) throws MospException;
	
	/**
	 * 申請時の入力チェック。残業申請の申請期間チェック。<br>
	 * <p>
	 * 申請日がシステム日付から見て1ヶ月以上先の日付が入力されている場合、エラーメッセージを設定する。
	 * </p>
	 * @param dto 対象DTO
	 * @throws MospException エラーが発生した場合
	 */
	void checkPeriod(OvertimeRequestDtoInterface dto) throws MospException;
	
	/**
	 * 申請時の入力チェック。残業申請の重複チェック。<br>
	 * <p>
	 * 同日中に同じ残業区分が申請されている場合、エラーメッセージを設定する。
	 * </p>
	 * @param dto 対象DTO
	 * @throws MospException エラーが発生した場合
	 */
	void checkOvertimeOverlap(OvertimeRequestDtoInterface dto) throws MospException;
	
	/**
	 * 申請時の入力チェック。残業申請の項目の必須チェック。<br>
	 * <p>
	 * 必須の項目が入力されていない場合、エラーメッセージを設定する。
	 * </p>
	 * @param dto 対象DTO
	 * @throws MospException エラーが発生した場合
	 */
	void checkRequired(OvertimeRequestDtoInterface dto) throws MospException;
	
	/**
	 * 申請、下書き時の入力チェック。仮締チェック。<br>
	 * <p>
	 * 仮締されている場合、エラーメッセージを設定する。
	 * </p>
	 * @param dto 対象DTO
	 * @throws MospException エラーが発生した場合
	 */
	void checkTemporaryClosingFinal(OvertimeRequestDtoInterface dto) throws MospException;
	
}
