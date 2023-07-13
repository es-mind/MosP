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

import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.WorkTypeChangeRequestDtoInterface;

/**
 * 勤務形態変更申請登録インターフェース。<br>
 */
public interface WorkTypeChangeRequestRegistAddonBeanInterface {
	
	/**
	 * 登録用DTOを取得する。<br>
	 * @param dto 対象DTO
	 * @throws MospException エラーが発生した場合
	 * @return 初期DTO
	 */
	WorkTypeChangeRequestDtoInterface getInitDto(WorkTypeChangeRequestDtoInterface dto) throws MospException;
	
	/**
	 * 登録処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException エラーが発生した場合
	 */
	void regist(WorkTypeChangeRequestDtoInterface dto) throws MospException;
	
	/**
	 * 一括更新処理を行う。<br>
	 * @param idArray 対象レコード識別ID配列
	 * @throws MospException エラーが発生した場合
	 */
	void update(long[] idArray) throws MospException;
	
	/**
	 * 削除処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException エラーが発生した場合
	 */
	void delete(WorkTypeChangeRequestDtoInterface dto) throws MospException;
	
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
	void validate(WorkTypeChangeRequestDtoInterface dto) throws MospException;
	
	/**
	 * 有効日設定時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException エラーが発生した場合
	 */
	void checkSetActivationDate(WorkTypeChangeRequestDtoInterface dto) throws MospException;
	
	/**
	 * 下書時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException エラーが発生した場合
	 */
	void checkDraft(WorkTypeChangeRequestDtoInterface dto) throws MospException;
	
	/**
	 * 申請時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException エラーが発生した場合
	 */
	void checkAppli(WorkTypeChangeRequestDtoInterface dto) throws MospException;
	
	/**
	 * 解除申請時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException エラーが発生した場合
	 */
	void checkCancelAppli(WorkTypeChangeRequestDtoInterface dto) throws MospException;
	
	/**
	 * 取下時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException エラーが発生した場合
	 */
	void checkWithdrawn(WorkTypeChangeRequestDtoInterface dto) throws MospException;
	
	/**
	 * 削除時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException エラーが発生した場合
	 */
	void checkDelete(WorkTypeChangeRequestDtoInterface dto) throws MospException;
	
	/**
	 * 承認時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException エラーが発生した場合
	 */
	void checkApproval(WorkTypeChangeRequestDtoInterface dto) throws MospException;
	
	/**
	 * 解除承認時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException エラーが発生した場合
	 */
	void checkCancelApproval(WorkTypeChangeRequestDtoInterface dto) throws MospException;
	
	/**
	 * 取消時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException エラーが発生した場合
	 */
	void checkCancel(WorkTypeChangeRequestDtoInterface dto) throws MospException;
	
	/**
	 * 申請時の入力チェック。カレンダチェック。<br>
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @throws MospException エラーが発生した場合
	 */
	void checkSchedule(String personalId, Date targetDate) throws MospException;
	
//	/**
//	 * カレンダ勤務形態コードを取得する。<br>
//	 * @param personalId 個人ID
//	 * @param targetDate 対象日
//	 * @return 勤務形態コード
//	 * @throws MospException エラーが発生した場合
//	 */
//	String getScheduledWorkTypeCode(String personalId, Date targetDate) throws MospException;
//
//	/**
//	 * カレンダ勤務形態名称を取得する。<br>
//	 * @param dto 対象DTO
//	 * @return 勤務形態名称
//	 * @throws MospException エラーが発生した場合
//	 */
//	String getScheduledWorkTypeName(WorkTypeChangeRequestDtoInterface dto) throws MospException;
//
//	/**
//	 * カレンダ勤務形態名称を取得する。<br>
//	 * @param personalId 個人ID
//	 * @param targetDate 対象日
//	 * @return 勤務形態名称
//	 * @throws MospException エラーが発生した場合
//	 */
//	String getScheduledWorkTypeName(String personalId, Date targetDate) throws MospException;
//
	/**
	 * 勤怠下書を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException エラーが発生した場合
	 */
	void draftAttendance(WorkTypeChangeRequestDtoInterface dto) throws MospException;
	
}
