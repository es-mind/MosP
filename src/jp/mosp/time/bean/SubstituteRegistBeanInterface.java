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
package jp.mosp.time.bean;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.SubstituteDtoInterface;

/**
 * 振替休日データ登録インターフェース。<br>
 */
public interface SubstituteRegistBeanInterface extends BaseBeanInterface {
	
	/**
	 * 登録用DTOを取得する。<br>
	 * @return 初期DTO
	 */
	SubstituteDtoInterface getInitDto();
	
	/**
	 * 新規登録を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void insert(SubstituteDtoInterface dto) throws MospException;
	
	/**
	 * 論理削除を行う。<br>
	 * @param workflow ワークフロー番号
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void delete(long workflow) throws MospException;
	
	/**
	 * 登録情報の妥当性を確認する。
	 * @param dto  対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void validate(SubstituteDtoInterface dto) throws MospException;
	
	/**
	 * 登録時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void checkRegist(SubstituteDtoInterface dto) throws MospException;
	
	/**
	 * インポート時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void checkImport(SubstituteDtoInterface dto) throws MospException;
	
	/**
	 * 入力チェック。振替日の妥当性チェック。<br>
	 * <p>
	 * 振替日が所定休日、法定休日またはその他の休暇の申請日を入力している場合、エラーメッセージを設定する。
	 * </p>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void checkTargetTransferDate(SubstituteDtoInterface dto) throws MospException;
	
	/**
	 * 入力チェック。振替日1と振替日2の範囲チェック。<br>
	 * <p>
	 * 振替日1と振替日2に入力されている年月日が申請日から起算して勤怠設定詳細画面の「振休取得期限(休出前)」「振休取得期限(休出後)」の期間をそれぞれ超えている場合、エラーメッセージを設定する。
	 * </p>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void checkTransferDateRange(SubstituteDtoInterface dto) throws MospException;
	
	/**
	 * 入力チェック。他の申請チェック。<br>
	 * <p>
	 * 振替日が全休を選択している状態でその振替日に他の申請が行われている場合、エラーメッセージを設定する。
	 * </p>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void checkRequest(SubstituteDtoInterface dto) throws MospException;
	
	/**
	 * 申請時の入力チェック。勤怠の申請チェック。<br>
	 * <p>
	 * 勤怠が申請されている場合、エラーメッセージを設定する。
	 * </p>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	void checkAttendance(SubstituteDtoInterface dto) throws MospException;
	
	/**
	 * 休職チェック。<br>
	 * <p>
	 * 休職している場合、エラーメッセージを設定する。
	 * </p>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	void checkSuspension(SubstituteDtoInterface dto) throws MospException;
	
	/**
	 * 退職チェック。<br>
	 * <p>
	 * 退職している場合、エラーメッセージを設定する。
	 * </p>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	void checkRetirement(SubstituteDtoInterface dto) throws MospException;
	
	/**
	 * 振替日の入力チェック。<br>
	 * @param dto 対象DTO
	 */
	void checkValidate(SubstituteDtoInterface dto);
}
