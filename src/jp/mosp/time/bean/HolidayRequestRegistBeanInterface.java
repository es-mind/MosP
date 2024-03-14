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

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.HolidayRequestDtoInterface;

/**
 * 休暇申請登録処理インターフェース。<br>
 */
public interface HolidayRequestRegistBeanInterface extends BaseBeanInterface {
	
	/**
	 * 登録用DTOを取得する。<br>
	 * @return 初期DTO
	 */
	HolidayRequestDtoInterface getInitDto();
	
	/**
	 * 登録処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void regist(HolidayRequestDtoInterface dto) throws MospException;
	
	/**
	 * 登録処理を行う。<br>
	 * @param dto 対象DTO
	 * @param row 行インデックス(エラーメッセージ用)
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void regist(HolidayRequestDtoInterface dto, Integer row) throws MospException;
	
	/**
	 * 削除処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void delete(HolidayRequestDtoInterface dto) throws MospException;
	
	/**
	 * 一括取下処理を行う。<br>
	 * @param idArray 対象レコード識別ID配列
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void withdrawn(long[] idArray) throws MospException;
	
	/**
	 * 申請日設定時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void checkSetRequestDate(HolidayRequestDtoInterface dto) throws MospException;
	
	/**
	 * 下書時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void checkDraft(HolidayRequestDtoInterface dto) throws MospException;
	
	/**
	 * 申請時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	void checkAppli(HolidayRequestDtoInterface dto) throws MospException;
	
	/**
	 * 申請時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @param row 行インデックス(エラーメッセージ用)
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	void checkAppli(HolidayRequestDtoInterface dto, Integer row) throws MospException;
	
	/**
	 * 解除申請時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void checkCancelAppli(HolidayRequestDtoInterface dto) throws MospException;
	
	/**
	 * 取下時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 */
	void checkWithdrawn(HolidayRequestDtoInterface dto);
	
	/**
	 * 承認時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	void checkApproval(HolidayRequestDtoInterface dto) throws MospException;
	
	/**
	 * 解除申請申請承認時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	void checkCancelApproval(HolidayRequestDtoInterface dto) throws MospException;
	
	/**
	 * 承認解除時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 */
	void checkCancel(HolidayRequestDtoInterface dto);
	
	/**
	 * 勤怠データを削除する。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	void deleteAttendance(HolidayRequestDtoInterface dto) throws MospException;
	
	/**
	 * 勤怠下書を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	void draftAttendance(HolidayRequestDtoInterface dto) throws MospException;
	
	/**
	 * 勤怠関連マスタ参照処理を設定する。<br>
	 * 勤怠関連マスタ参照処理を処理間で共有するために用いる。<br>
	 * <br>
	 * @param timeMaster 勤怠関連マスタ参照処理
	 */
	void setTimeMaster(TimeMasterBeanInterface timeMaster);
	
}
