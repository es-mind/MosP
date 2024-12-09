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

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;
import jp.mosp.time.dto.settings.AttendanceDtoInterface;
import jp.mosp.time.dto.settings.GoOutDtoInterface;
import jp.mosp.time.dto.settings.RestDtoInterface;

/**
 * 勤怠データ登録インターフェース。<br>
 */
public interface AttendanceRegistBeanInterface extends BaseBeanInterface {
	
	/**
	 * 登録用DTOを取得する。<br>
	 * @return 初期DTO
	 */
	AttendanceDtoInterface getInitDto();
	
	/**
	 * 登録処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void regist(AttendanceDtoInterface dto) throws MospException;
	
	/**
	 * 登録時の確認処理を行う。
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void checkValidate(AttendanceDtoInterface dto) throws MospException;
	
	/**
	 * 下書時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void checkDraft(AttendanceDtoInterface dto) throws MospException;
	
	/**
	 * 申請時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void checkAppli(AttendanceDtoInterface dto) throws MospException;
	
	/**
	 * 解除申請時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void checkCancelAppli(AttendanceDtoInterface dto) throws MospException;
	
	/**
	 * 削除時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void checkDelete(AttendanceDtoInterface dto) throws MospException;
	
	/**
	 * 承認時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void checkApproval(AttendanceDtoInterface dto) throws MospException;
	
	/**
	 * 解除申請承認時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void checkCancelApproval(AttendanceDtoInterface dto) throws MospException;
	
	/**
	 * 承認解除時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void checkCancel(AttendanceDtoInterface dto) throws MospException;
	
	/**
	 * 始業・終業必須確認。
	 * @param dto 対象DTO
	 */
	void checkTimeExist(AttendanceDtoInterface dto);
	
	/**
	 * 残業のチェック。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void checkOvertime(AttendanceDtoInterface dto) throws MospException;
	
	/**
	 * 私用外出チェック。
	 * @param dto 対象DTO
	 * @param restList 休憩リスト
	 * @param privateList 私用外出リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	void checkPrivateGoOut(AttendanceDtoInterface dto, List<RestDtoInterface> restList,
			List<GoOutDtoInterface> privateList) throws MospException;
	
	/**
	 * 申請、下書き時の入力チェック。勤怠の申請チェック。<br>
	 * <p>
	 * 勤怠が申請されている場合、エラーメッセージを設定する。
	 * </p>
	 * @param dto 対象DTO
	 * @param workDto 対象ワークフローDTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	void checkApprover(AttendanceDtoInterface dto, WorkflowDtoInterface workDto) throws MospException;
	
	/**
	 * 勤怠の削除処理。<br>
	 * @param personalId 個人ID
	 * @param workDate 勤務日
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	void delete(String personalId, Date workDate) throws MospException;
	
	/**
	 * 時間休申請時の休憩、公用外出、私用外出重複確認
	 * @param personalId 個人ID
	 * @param workDate 勤務日
	 * @param restList 休憩リスト
	 * @param goOutPublicList 公用外出リスト
	 * @param goOutPrivateList 私用外出リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	void checkHolidayTime(String personalId, Date workDate, List<RestDtoInterface> restList,
			List<GoOutDtoInterface> goOutPublicList, List<GoOutDtoInterface> goOutPrivateList) throws MospException;
	
	/**
	 * 勤怠関連マスタ参照処理を設定する。<br>
	 * 勤怠関連マスタ参照処理を処理間で共有するために用いる。<br>
	 * <br>
	 * @param timeMaster 勤怠関連マスタ参照処理
	 */
	void setTimeMaster(TimeMasterBeanInterface timeMaster);
	
}
