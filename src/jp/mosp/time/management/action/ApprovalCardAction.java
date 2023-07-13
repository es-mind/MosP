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
package jp.mosp.time.management.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.BaseVo;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.framework.utils.RoleUtility;
import jp.mosp.framework.utils.TopicPathUtility;
import jp.mosp.platform.bean.workflow.WorkflowIntegrateBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dto.workflow.SubApproverDtoInterface;
import jp.mosp.platform.dto.workflow.WorkflowCommentDtoInterface;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.platform.utils.PfNameUtility;
import jp.mosp.platform.utils.WorkflowUtility;
import jp.mosp.time.base.TimeAction;
import jp.mosp.time.base.TimeBean;
import jp.mosp.time.bean.ApprovalCardAddonBeanInterface;
import jp.mosp.time.bean.RequestUtilBeanInterface;
import jp.mosp.time.bean.TimeApprovalBeanInterface;
import jp.mosp.time.bean.WorkTypeReferenceBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dto.settings.AttendanceCorrectionDtoInterface;
import jp.mosp.time.dto.settings.AttendanceDtoInterface;
import jp.mosp.time.dto.settings.DifferenceRequestDtoInterface;
import jp.mosp.time.dto.settings.GoOutDtoInterface;
import jp.mosp.time.dto.settings.HolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.ManagementRequestListDtoInterface;
import jp.mosp.time.dto.settings.OvertimeRequestDtoInterface;
import jp.mosp.time.dto.settings.RestDtoInterface;
import jp.mosp.time.dto.settings.SubHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.SubstituteDtoInterface;
import jp.mosp.time.dto.settings.TimeRecordDtoInterface;
import jp.mosp.time.dto.settings.WorkOnHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.WorkTypeChangeRequestDtoInterface;
import jp.mosp.time.entity.RequestEntityInterface;
import jp.mosp.time.management.vo.ApprovalCardVo;
import jp.mosp.time.portal.bean.impl.PortalTimeCardBean;

/**
 * 未承認情報一覧画面で確認した承認すべき申請情報の個別承認を行う。<br>
 * <br>
 * 以下のコマンドを扱う。<br>
 * <ul><li>
 * {@link #CMD_APPROVAL_CONFIRMATION_ATTENDANCE}
 * </li><li>
 * {@link #CMD_APPROVAL_CONFIRMATION_OVERTIME}
 * </li><li>
 * {@link #CMD_APPROVAL_CONFIRMATION_HOLIDAY}
 * </li><li>
 * {@link #CMD_APPROVAL_CONFIRMATION_WORKONHOLIDAY}
 * </li><li>
 * {@link #CMD_APPROVAL_CONFIRMATION_SUBHOLIDAY}
 * </li><li>
 * {@link #CMD_APPROVAL_CONFIRMATION_DIFFERENCE}
 * </li><li>
 * {@link #CMD_APPROVAL_CONFIRMATION_WORKTYPECHANGE}
 * </li><li>
 * {@link #CMD_APPROVAL_ATTENDANCE}
 * </li><li>
 * {@link #CMD_APPROVAL_OVERTIME}
 * </li><li>
 * {@link #CMD_APPROVAL_HOLIDAY}
 * </li><li>
 * {@link #CMD_APPROVAL_WORKONHOLIDAY}
 * </li><li>
 * {@link #CMD_APPROVAL_SUBHOLIDAY}
 * </li><li>
 * {@link #CMD_APPROVAL_DIFFERENCE}
 * </li><li>
 * {@link #CMD_APPROVAL_WORKTYPECHANGE}
 * </li><li>
 * {@link #CMD_ROLL}
 * </li><li>
 * {@link #CMD_APPROVAL}
 * </li><li>
 * {@link #CMD_REVERTING}
 * </li><li>
 * {@link #CMD_DELETE}
 * </li></ul>
 */
public class ApprovalCardAction extends TimeAction {
	
	/**
	 * 勤怠承認確認画面表示コマンド。<br>
	 * <br>
	 * 申請情報確認画面で申請カテゴリが日々勤怠のレコードを選択した際に実行。<br>
	 * 勤怠申請情報の表示欄ヘッダの背景色を#CCFFCCに切り替え、勤怠承認コメント欄がテキストボックスとなる。<br>
	 * 画面下部のボタン欄は取消ボタンがクリック可能な状態で表示される。<br>
	 */
	public static final String		CMD_APPROVAL_CONFIRMATION_ATTENDANCE		= "TM2320";
	
	/**
	 * 残業承認確認画面表示コマンド。<br>
	 * <br>
	 * 申請情報確認画面で申請カテゴリが残業申請のレコードを選択した際に実行。<br>
	 * 残業申請情報の表示欄ヘッダの背景色を#CCFFCCに切り替え、残業承認コメント欄がテキストボックスとなる。<br>
	 * 画面下部のボタン欄は取消ボタンがクリック可能な状態で表示される。<br>
	 */
	public static final String		CMD_APPROVAL_CONFIRMATION_OVERTIME			= "TM2330";
	
	/**
	 * 休暇承認確認画面表示コマンド。<br>
	 * <br>
	 * 申請情報確認画面で申請カテゴリが休暇申請のレコードを選択した際に実行。<br>
	 * 休暇申請情報の表示欄ヘッダの背景色を#CCFFCCに切り替え、休暇承認コメント欄がテキストボックスとなる。<br>
	 * 画面下部のボタン欄は取消ボタンがクリック可能な状態で表示される。<br>
	 */
	public static final String		CMD_APPROVAL_CONFIRMATION_HOLIDAY			= "TM2340";
	
	/**
	 * 休日出勤承認確認画面表示コマンド。<br>
	 * <br>
	 * 申請情報確認画面で申請カテゴリが休日出勤のレコードを選択した際に実行。<br>
	 * 休日出勤申請情報の表示欄ヘッダの背景色を#CCFFCCに切り替え、休日出勤承認コメント欄がテキストボックスとなる。<br>
	 * 画面下部のボタン欄は取消ボタンがクリック可能な状態で表示される。<br>
	 */
	public static final String		CMD_APPROVAL_CONFIRMATION_WORKONHOLIDAY		= "TM2350";
	
	/**
	 * 代休承認確認画面表示コマンド。<br>
	 * <br>
	 * 申請情報確認画面で申請カテゴリが代休申請のレコードを選択した際に実行。<br>
	 * 代休申請情報の表示欄ヘッダの背景色を#CCFFCCに切り替え、代休承認コメント欄がテキストボックスとなる。<br>
	 * 画面下部のボタン欄は取消ボタンがクリック可能な状態で表示される。<br>
	 */
	public static final String		CMD_APPROVAL_CONFIRMATION_SUBHOLIDAY		= "TM2360";
	
	/**
	 * 時差出勤承認確認画面表示コマンド。<br>
	 * <br>
	 * 申請情報確認画面で申請カテゴリが時差出勤申請のレコードを選択した際に実行。<br>
	 * 時差出勤申請情報の表示欄ヘッダの背景色を#CCFFCCに切り替え、時差出勤承認コメント欄がテキストボックスとなる。<br>
	 * 画面下部のボタン欄は取消ボタンがクリック可能な状態で表示される。<br>
	 */
	public static final String		CMD_APPROVAL_CONFIRMATION_DIFFERENCE		= "TM2370";
	
	/**
	 * 勤務形態変更承認確認画面表示コマンド。<br>
	 * <br>
	 * 申請情報確認画面で申請カテゴリが勤務形態変更申請のレコードを選択した際に実行。<br>
	 * 勤務形態変更申請情報の表示欄ヘッダの背景色を#CCFFCCに切り替え、勤務形態承認コメント欄がテキストボックスとなる。<br>
	 * 画面下部のボタン欄は取消ボタンがクリック可能な状態で表示される。<br>
	 */
	public static final String		CMD_APPROVAL_CONFIRMATION_WORKTYPECHANGE	= "TM2380";
	
	/**
	 * 勤怠承認画面表示コマンド。<br>
	 * <br>
	 * 未承認情報一覧画面で申請カテゴリが日々勤怠のレコードを選択した際に実行。<br>
	 * 勤怠申請情報の表示欄ヘッダの背景色を#CCFFCCに切り替え、勤怠承認コメント欄がテキストボックスとなる。<br>
	 */
	public static final String		CMD_APPROVAL_ATTENDANCE						= "TM2321";
	
	/**
	 * 残業承認画面表示コマンド。<br>
	 * <br>
	 * 残業承認画面表示コマンド。未承認情報一覧画面で申請カテゴリが残業申請のレコードを選択した際に実行。<br>
	 * 残業申請情報の表示欄ヘッダの背景色を#CCFFCCに切り替え、残業承認コメント欄がテキストボックスとなる。<br>
	 */
	public static final String		CMD_APPROVAL_OVERTIME						= "TM2331";
	
	/**
	 * 休暇承認画面表示コマンド。<br>
	 * <br>
	 * 未承認情報一覧画面で申請カテゴリが休暇申請のレコードを選択した際に実行。<br>
	 * 休暇申請情報の表示欄ヘッダの背景色を#CCFFCCに切り替え、休暇承認コメント欄がテキストボックスとなる。<br>
	 */
	public static final String		CMD_APPROVAL_HOLIDAY						= "TM2341";
	
	/**
	 * 休日出勤承認画面表示コマンド。<br>
	 * <br>
	 * 未承認情報一覧画面で申請カテゴリが休日出勤のレコードを選択した際に実行。<br>
	 * 休日出勤申請情報の表示欄ヘッダの背景色を#CCFFCCに切り替え、休日出勤承認コメント欄がテキストボックスとなる。<br>
	 */
	public static final String		CMD_APPROVAL_WORKONHOLIDAY					= "TM2351";
	
	/**
	 * 代休承認画面表示コマンド。<br>
	 * <br>
	 * 未承認情報一覧画面で申請カテゴリが代休申請のレコードを選択した際に実行。<br>
	 * 代休申請情報の表示欄ヘッダの背景色を#CCFFCCに切り替え、代休承認コメント欄がテキストボックスとなる。<br>
	 */
	public static final String		CMD_APPROVAL_SUBHOLIDAY						= "TM2361";
	
	/**
	 * 時差出勤承認画面表示コマンド。<br>
	 * <br>
	 * 未承認情報一覧画面で申請カテゴリが時差出勤申請のレコードを選択した際に実行。<br>
	 * 時差出勤申請情報の表示欄ヘッダの背景色を#CCFFCCに切り替え、時差出勤承認コメント欄がテキストボックスとなる。<br>
	 */
	public static final String		CMD_APPROVAL_DIFFERENCE						= "TM2371";
	
	/**
	 * 勤務形態変更承認画面表示コマンド。<br>
	 * <br>
	 * 未承認情報一覧画面で申請カテゴリが勤務形態変更申請のレコードを選択した際に実行。<br>
	 * 勤務形態変更申請情報の表示欄ヘッダの背景色を#CCFFCCに切り替え、勤務形態変更承認コメント欄がテキストボックスとなる。<br>
	 */
	public static final String		CMD_APPROVAL_WORKTYPECHANGE					= "TM2381";
	
	/**
	 * 前次遷移コマンド。<br>
	 */
	public static final String		CMD_ROLL									= "TM2322";
	
	/**
	 * 承認コマンド。<br>
	 * <br>
	 * 各種コメント欄にコメントが入力されているかを確認後、現在表示している申請情報の承認を行う。<br>
	 * コメントが入力さずにボタンがクリックされた場合はエラーメッセージにて通知。処理は実行されない。<br>
	 */
	public static final String		CMD_APPROVAL								= "TM2325";
	
	/**
	 * 差戻コマンド。<br>
	 * <br>
	 * 各種コメント欄にコメントが入力されているかを確認後、現在表示している申請情報の差戻しを行う。<br>
	 * コメントが入力さずにボタンがクリックされた場合はエラーメッセージにて通知。処理は実行されない。<br>
	 */
	public static final String		CMD_REVERTING								= "TM2326";
	
	/**
	 * 承認解除コマンド。<br>
	 * <br>
	 * 現在表示している承認完了済の申請情報の承認解除を行う。該当レコードの状態を「○次戻し」または「取下」に切り替える。<br>
	 */
	public static final String		CMD_DELETE									= "TM2327";
	
	/**
	 * 区切文字(修正情報等)。
	 */
	public static final String		SEPARATOR									= " ";
	
	/**
	 * パラメータID(未承認(申請)確認詳細追加JSP)。
	 */
	public static final String		PRM_APPROVAL_EXTRA_JSP						= "prmApprovalExtraJsp";
	
	/**
	 * コードキー(追加処理)。<br>
	 */
	protected static final String	CODE_KEY_ADDONS								= "ApprovalCardAddons";
	
	
	/**
	 * {@link TimeAction#TimeAction()}を実行する。<br>
	 */
	public ApprovalCardAction() {
		super();
	}
	
	@Override
	protected BaseVo getSpecificVo() {
		return new ApprovalCardVo();
	}
	
	@Override
	protected BaseVo prepareVo(boolean useStoredVo, boolean useParametersMapper) throws MospException {
		// 継承基のメソッドを実行
		BaseVo vo = super.prepareVo(useStoredVo, useParametersMapper);
		// パラメータをVOにマッピングしない場合
		if (useParametersMapper == false) {
			// VOを取得
			return vo;
		}
		// 承認確認追加処理毎に処理
		for (ApprovalCardAddonBeanInterface addonBean : getAddonBeans()) {
			// 承認確認画面VOにリクエストパラメータを設定
			addonBean.mapping();
		}
		// VOを取得
		return vo;
	}
	
	@Override
	public void action() throws MospException {
		// 追加JSP設定
		setApprovalExtraJsp();
		// コマンド毎の処理
		if (mospParams.getCommand().equals(CMD_APPROVAL_CONFIRMATION_ATTENDANCE)) {
			// 勤怠承認確認画面表示
			prepareVo(false, false);
			approvalAttendance(true);
		} else if (mospParams.getCommand().equals(CMD_APPROVAL_CONFIRMATION_OVERTIME)) {
			// 残業承認確認画面表示
			prepareVo(false, false);
			approvalOvertime(true);
		} else if (mospParams.getCommand().equals(CMD_APPROVAL_CONFIRMATION_HOLIDAY)) {
			// 休暇承認確認画面表示
			prepareVo(false, false);
			approvalHoliday(true);
		} else if (mospParams.getCommand().equals(CMD_APPROVAL_CONFIRMATION_WORKONHOLIDAY)) {
			// 振出・休出承認確認画面表示
			prepareVo(false, false);
			approvalWorkOnHoliday(true);
		} else if (mospParams.getCommand().equals(CMD_APPROVAL_CONFIRMATION_SUBHOLIDAY)) {
			// 代休承認確認画面表示
			prepareVo(false, false);
			approvalSubHoliday(true);
		} else if (mospParams.getCommand().equals(CMD_APPROVAL_CONFIRMATION_DIFFERENCE)) {
			// 時差出勤確認承認画面表示
			prepareVo(false, false);
			approvalDifference(true);
		} else if (mospParams.getCommand().equals(CMD_APPROVAL_CONFIRMATION_WORKTYPECHANGE)) {
			// 勤務形態変更確認承認画面表示
			prepareVo(false, false);
			approvalWorkTypeChange(true);
		} else if (mospParams.getCommand().equals(CMD_APPROVAL_ATTENDANCE)) {
			// 勤怠承認画面表示
			prepareVo(false, false);
			approvalAttendance(false);
		} else if (mospParams.getCommand().equals(CMD_APPROVAL_OVERTIME)) {
			// 残業承認画面表示
			prepareVo(false, false);
			approvalOvertime(false);
		} else if (mospParams.getCommand().equals(CMD_APPROVAL_HOLIDAY)) {
			// 休暇承認画面表示
			prepareVo(false, false);
			approvalHoliday(false);
		} else if (mospParams.getCommand().equals(CMD_APPROVAL_WORKONHOLIDAY)) {
			// 振出・休出承認画面表示
			prepareVo(false, false);
			approvalWorkOnHoliday(false);
		} else if (mospParams.getCommand().equals(CMD_APPROVAL_SUBHOLIDAY)) {
			// 代休承認画面表示
			prepareVo(false, false);
			approvalSubHoliday(false);
		} else if (mospParams.getCommand().equals(CMD_APPROVAL_DIFFERENCE)) {
			// 時差出勤承認画面表示
			prepareVo(false, false);
			approvalDifference(false);
		} else if (mospParams.getCommand().equals(CMD_APPROVAL_WORKTYPECHANGE)) {
			// 勤務形態変更承認画面表示
			prepareVo(false, false);
			approvalWorkTypeChange(false);
		} else if (mospParams.getCommand().equals(CMD_APPROVAL)) {
			// 承認
			prepareVo();
			approval();
		} else if (mospParams.getCommand().equals(CMD_REVERTING)) {
			// 差戻
			prepareVo();
			reverting();
		} else if (mospParams.getCommand().equals(CMD_DELETE)) {
			// 承認解除
			prepareVo();
			cancel();
		} else if (mospParams.getCommand().equals(CMD_ROLL)) {
			// 遷移
			prepareVo();
			roll();
		}
	}
	
	/**
	 * 申請確認詳細追加JSPを設定する。
	 */
	protected void setApprovalExtraJsp() {
		// アドオン機能などで処理
	}
	
	/**
	 * 申請確認詳細或いは未承認確認画面(勤怠)表示処理を行う。<br>
	 * @param isConfirmation 申請確認詳細フラグ
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	protected void approvalAttendance(boolean isConfirmation) throws MospException {
		// VO準備
		ApprovalCardVo vo = (ApprovalCardVo)mospParams.getVo();
		// 申請確認詳細フラグ設定(未承認情報詳細)
		vo.setConfirmation(isConfirmation);
		// 申請カテゴリ設定(勤怠)
		vo.setAttendance(true);
		// 初期値設定
		setInitValues();
		// 申請情報設定
		setRequestValues();
	}
	
	/**
	 * 申請確認詳細或いは未承認確認画面(残業)表示処理を行う。<br>
	 * @param isConfirmation 申請確認詳細フラグ
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	protected void approvalOvertime(boolean isConfirmation) throws MospException {
		// VO準備
		ApprovalCardVo vo = (ApprovalCardVo)mospParams.getVo();
		// 申請確認詳細フラグ設定(未承認情報詳細)
		vo.setConfirmation(isConfirmation);
		// 申請カテゴリ設定(残業)
		vo.setOvertime(true);
		// 初期値設定
		setInitValues();
		// 申請情報設定
		setRequestValues();
	}
	
	/**
	 * 申請確認詳細或いは未承認確認画面(休暇)表示処理を行う。<br>
	 * @param isConfirmation 申請確認詳細フラグ
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	protected void approvalHoliday(boolean isConfirmation) throws MospException {
		// VO準備
		ApprovalCardVo vo = (ApprovalCardVo)mospParams.getVo();
		// 申請確認詳細フラグ設定(未承認情報詳細)
		vo.setConfirmation(isConfirmation);
		// 申請カテゴリ設定(休暇)
		vo.setHoliday(true);
		// 初期値設定
		setInitValues();
		// 申請情報設定
		setRequestValues();
	}
	
	/**
	 * 申請確認詳細或いは未承認確認画面(振出休出)表示処理を行う。<br>
	 * @param isConfirmation 申請確認詳細フラグ
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	protected void approvalWorkOnHoliday(boolean isConfirmation) throws MospException {
		// VO準備
		ApprovalCardVo vo = (ApprovalCardVo)mospParams.getVo();
		// 申請確認詳細フラグ設定(未承認情報詳細)
		vo.setConfirmation(isConfirmation);
		// 申請カテゴリ設定(振出休出)
		vo.setWorkOnHoliday(true);
		// 初期値設定
		setInitValues();
		// 申請情報設定
		setRequestValues();
	}
	
	/**
	 * 申請確認詳細或いは未承認確認画面(代休)表示処理を行う。<br>
	 * @param isConfirmation 申請確認詳細フラグ
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	protected void approvalSubHoliday(boolean isConfirmation) throws MospException {
		// VO準備
		ApprovalCardVo vo = (ApprovalCardVo)mospParams.getVo();
		// 申請確認詳細フラグ設定(未承認情報詳細)
		vo.setConfirmation(isConfirmation);
		// 申請カテゴリ設定(代休)
		vo.setSubHoliday(true);
		// 初期値設定
		setInitValues();
		// 申請情報設定
		setRequestValues();
	}
	
	/**
	 * 申請確認詳細或いは未承認確認画面(時差出勤)表示処理を行う。<br>
	 * @param isConfirmation 申請確認詳細フラグ
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	protected void approvalDifference(boolean isConfirmation) throws MospException {
		// VO準備
		ApprovalCardVo vo = (ApprovalCardVo)mospParams.getVo();
		// 申請確認詳細フラグ設定(未承認情報詳細)
		vo.setConfirmation(isConfirmation);
		// 申請カテゴリ設定(時差出勤)
		vo.setDifference(true);
		// 初期値設定
		setInitValues();
		// 申請情報設定
		setRequestValues();
	}
	
	/**
	 * 申請確認詳細或いは未承認確認画面(勤務形態変更)表示処理を行う。<br>
	 * @param isConfirmation 申請確認詳細フラグ
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	protected void approvalWorkTypeChange(boolean isConfirmation) throws MospException {
		// VO取得
		ApprovalCardVo vo = (ApprovalCardVo)mospParams.getVo();
		// 申請確認詳細フラグ設定(未承認情報詳細)
		vo.setConfirmation(isConfirmation);
		// 申請カテゴリ設定(勤務形態変更)
		vo.setWorkTypeChange(true);
		// 初期値設定
		setInitValues();
		// 申請情報設定
		setRequestValues();
	}
	
	/**
	 * 承認処理を行う。<br>
	 * @throws MospException 例外処理発生時
	 */
	protected void approval() throws MospException {
		// VO準備
		ApprovalCardVo vo = (ApprovalCardVo)mospParams.getVo();
		// 承認処理
		time().timeApproval().approve(vo.getWorkflow(), getWorkflowComment());
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			// 登録失敗メッセージを設定
			PfMessageUtility.addMessageInsertFailed(mospParams);
			return;
		}
		// コミット
		commit();
		// 承認成功メッセージ設定
		addApprovalMessage();
		// 申請情報設定
		setRequestValues();
	}
	
	/**
	 * 差戻処理を行う。<br>
	 * @throws MospException 例外処理発生時
	 */
	protected void reverting() throws MospException {
		// VO準備
		ApprovalCardVo vo = (ApprovalCardVo)mospParams.getVo();
		// 勤怠関連申請承認クラス取得
		TimeApprovalBeanInterface timeApproval = time().timeApproval();
		// 対象ワークフローが解除承認可能であるかを確認
		WorkflowDtoInterface workflowdto = reference().workflow().getLatestWorkflowInfo(vo.getWorkflow());
		boolean isCancelApprovable = WorkflowUtility.isCancelApply(workflowdto);
		boolean isCancelWithDrawn = WorkflowUtility.isCancelWithDrawnApply(workflowdto);
		// 解除承認可能の場合
		if (isCancelApprovable || isCancelWithDrawn) {
			// 解除取下処理
			timeApproval.cancelRevert(vo.getWorkflow(), vo.getTxtCancelComment());
		} else {
			// 差戻処理
			timeApproval.revert(vo.getWorkflow(), getWorkflowComment());
		}
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			// 登録失敗メッセージを設定
			PfMessageUtility.addMessageInsertFailed(mospParams);
			return;
		}
		// コミット
		commit();
		if (isCancelApprovable) {
			// 解除取下成功メッセージ設定
			addReleaseSendingBackMessage();
		} else {
			// 差戻成功メッセージ設定
			addSendingBackMessage();
		}
		// 申請情報設定
		setRequestValues();
	}
	
	/**
	 * 承認解除処理を行う。<br>
	 * @throws MospException 例外処理発生時
	 */
	protected void cancel() throws MospException {
		// VO準備
		ApprovalCardVo vo = (ApprovalCardVo)mospParams.getVo();
		// 勤怠関連申請承認クラス取得
		TimeApprovalBeanInterface timeApproval = time().timeApproval();
		// ワークフロー統合クラス取得
		WorkflowIntegrateBeanInterface workflowIntegrate = reference().workflowIntegrate();
		// 対象ワークフロー番号からワークフロー情報を取得
		WorkflowDtoInterface dto = reference().workflow().getLatestWorkflowInfo(vo.getWorkflow());
		if (workflowIntegrate.isCompleted(dto)) {
			// 承認済の場合
			// 承認解除
			timeApproval.cancel(vo.getWorkflow(), null);
		} else if (workflowIntegrate.isCancelApprovable(dto) || workflowIntegrate.isCancelWithDrawnApprovable(dto)) {
			// 解除承認可能の場合
			// 解除承認
			timeApproval.cancelApprove(vo.getWorkflow(), vo.getTxtCancelComment());
		}
		// 承認解除結果確認
		if (mospParams.hasErrorMessage()) {
			// 履歴削除失敗メッセージを設定
			PfMessageUtility.addMessageDeleteHistoryFailed(mospParams);
			return;
		}
		// コミット
		commit();
		// 承認解除成功メッセージを設定
		PfMessageUtility.addMessageSucceed(mospParams, PfNameUtility.cancelApproval(mospParams));
		// 承認解除後ワークフロー情報取得
		WorkflowDtoInterface workflowDto = reference().workflow().getLatestWorkflowInfo(vo.getWorkflow());
		if (workflowDto == null) {
			// ワークフロー情報がないなら
			if (vo.isConfirmation()) {
				// 申請確認詳細の場合申請一覧画面へ戻る
				mospParams.setNextCommand(RequestListAction.CMD_RE_SHOW);
				return;
			} else {
				// 未承認管理詳細の場合、未承認管理一覧画面へ戻る
				mospParams.setNextCommand(ApprovalListAction.CMD_RE_SHOW);
				return;
			}
		}
		// 申請情報設定
		setRequestValues();
	}
	
	/**
	 * 前次遷移を行う。<br>
	 */
	protected void roll() {
		// VO取得
		ApprovalCardVo vo = (ApprovalCardVo)mospParams.getVo();
		long workflow = 0;
		String command = "";
		String searchMode = mospParams.getRequestParam(TimeConst.PRM_TRANSFER_SEARCH_MODE);
		if (TimeConst.SEARCH_BACK.equals(searchMode)) {
			// 前の場合
			workflow = vo.getPrevWorkflow();
			command = vo.getPrevCommand();
		} else if (TimeConst.SEARCH_NEXT.equals(searchMode)) {
			// 次の場合
			workflow = vo.getNextWorkflow();
			command = vo.getNextCommand();
		}
		mospParams.addGeneralParam(TimeConst.PRM_ROLL_ARRAY, vo.getRollArray());
		// MosP処理情報に対象ワークフローを設定
		setTargetWorkflow(workflow);
		// 連続実行コマンド設定
		mospParams.setNextCommand(command);
	}
	
	/**
	 * 各種申請情報を取得し、設定する。<br>
	 * 申請カテゴリで設定する申請を判断し、
	 * 申請確認詳細フラグとワークフロー情報でボタン要否を判断する。<br>
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	protected void setRequestValues() throws MospException {
		// VO準備
		ApprovalCardVo vo = (ApprovalCardVo)mospParams.getVo();
		// 対象ワークフロー番号取得
		long workflow = vo.getWorkflow();
		// 申請カテゴリ毎に申請情報を設定
		if (vo.isAttendance()) {
			// 勤怠申請情報設定
			setAttendanceValues();
			// その他申請情報設定
			setOtherRequestValues();
		} else if (vo.isOvertime()) {
			// 残業申請情報取得及び設定
			setOvertimeValues(timeReference().overtimeRequest().findForWorkflow(workflow));
		} else if (vo.isHoliday()) {
			// 休暇申請情報設定
			setHolidayValues(timeReference().holidayRequest().findForWorkflow(workflow));
		} else if (vo.isWorkOnHoliday()) {
			// 振出休出申請情報設定
			setWorkOnHolidayValues(timeReference().workOnHolidayRequest().findForWorkflow(workflow));
		} else if (vo.isSubHoliday()) {
			// 代休申請情報設定
			setSubHolidayValues(timeReference().subHolidayRequest().findForWorkflow(workflow));
		} else if (vo.isDifference()) {
			// 時差出勤申請情報設定
			setDifferenceValues(timeReference().differenceRequest().findForWorkflow(workflow));
		} else if (vo.isWorkTypeChange()) {
			// 勤務形態変更申請情報設定
			setWorkTypeChangeValues(timeReference().workTypeChangeRequest().findForWorkflow(workflow));
		}
		// ボタン要否フラグ設定
		setButtonFlag();
		// 承認確認追加処理毎に処理
		for (ApprovalCardAddonBeanInterface addonBean : getAddonBeans()) {
			// 承認確認画面VOに初期値を設定
			addonBean.setVoFields();
		}
	}
	
	/**
	 * VOに初期値を設定する。<br>
	 * 対象ワークフロー番号を取得し、設定する。<br>
	 * ワークフロー情報から人事情報を取得し、設定する。<br>
	 * 各種フラグ等を初期化する。<br>
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	public void setInitValues() throws MospException {
		// VO準備
		ApprovalCardVo vo = (ApprovalCardVo)mospParams.getVo();
		// 表示コマンド確認
		if (vo.isConfirmation()) {
			// パンくずリスト名称設定(申請確認詳細の場合)
			TopicPathUtility.setTopicPathName(mospParams, vo.getClassName(), getNameRequestCard());
		}
		// 対象ワークフロー番号取得
		long workflow = getTargetWorkflow();
		// 対象ワークフロー番号を設定
		vo.setWorkflow(workflow);
		// 対象ワークフロー番号からワークフロー情報を取得
		WorkflowDtoInterface workflowDto = reference().workflow().getLatestWorkflowInfo(workflow);
		// 存在確認
		checkSelectedDataExist(workflowDto);
		// 対象個人ID取得
		String personalId = workflowDto.getPersonalId();
		// 対象日取得
		Date targetDate = workflowDto.getWorkflowDate();
		// 人事情報設定
		setEmployeeInfo(personalId, targetDate);
		// 対象日設定
		vo.setLblYear(DateUtility.getStringYear(targetDate));
		vo.setLblMonth(DateUtility.getStringMonth(targetDate));
		vo.setLblDay(DateUtility.getStringDay(targetDate));
		// 前ワークフロー・次ワークフロー設定
		setRollWorkflow();
		// 勤怠詳細追加JSPリストを設定
		vo.setAddonJsps(getAddonJsps());
		// 承認確認追加処理毎に処理
		for (ApprovalCardAddonBeanInterface addonBean : getAddonBeans()) {
			// 承認確認画面VOに初期値を設定
			addonBean.initVoFields();
		}
	}
	
	/**
	 * 勤怠申請情報を取得し、設定する。<br>
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	protected void setAttendanceValues() throws MospException {
		// VO準備
		ApprovalCardVo vo = (ApprovalCardVo)mospParams.getVo();
		// 対象個人ID、対象日、対象ワークフロー取得
		String personalId = vo.getPersonalId();
		Date targetDate = vo.getTargetDate();
		long workflow = vo.getWorkflow();
		// ワークフロー情報取得
		WorkflowDtoInterface workflowDto = reference().workflow().getLatestWorkflowInfo(workflow);
		// 勤怠データ取得
		AttendanceDtoInterface dto = timeReference().attendance().findForWorkflow(workflow);
		// 申請情報及びワークフロー情報確認
		if (dto == null || workflowDto == null) {
			return;
		}
		// 始業時刻準備
		StringBuffer lblStartTime = new StringBuffer();
		lblStartTime.append(getStringTimeMinutes(dto.getStartTime()));
		// 打刻始業時刻を取得
		TimeRecordDtoInterface recodeStartDto = timeReference().timeRecord().findForKey(personalId, targetDate,
				PortalTimeCardBean.RECODE_START_WORK);
		// 打刻始業時刻がある場合
		if (recodeStartDto != null) {
			// 【】内に打刻始業時刻を設定
			lblStartTime.append(mospParams.getName("FrontWithCornerParentheses"));
			lblStartTime.append(DateUtility.getStringTimeAndSecond(recodeStartDto.getRecordTime(), dto.getWorkDate()));
			lblStartTime.append(mospParams.getName("BackWithCornerParentheses"));
		}
		// 終業時刻準備
		StringBuffer lblEndTime = new StringBuffer();
		lblEndTime.append(DateUtility.getStringHour(dto.getEndTime(), dto.getWorkDate()));
		lblEndTime.append(mospParams.getName("Hour"));
		lblEndTime.append(getStringMinute(dto.getEndTime()));
		lblEndTime.append(mospParams.getName("Minutes"));
		// 終業打刻時刻を取得
		TimeRecordDtoInterface recodeEndDto = timeReference().timeRecord().findForKey(personalId, targetDate,
				PortalTimeCardBean.RECODE_END_WORK);
		// 打刻終業時刻がある場合
		if (recodeEndDto != null) {
			// 【】内に打刻終業時刻を設定
			lblEndTime.append(mospParams.getName("FrontWithCornerParentheses"));
			lblEndTime.append(DateUtility.getStringTimeAndSecond(recodeEndDto.getRecordTime(), dto.getWorkDate()));
			lblEndTime.append(mospParams.getName("BackWithCornerParentheses"));
		}
		// 出退勤情報設定
		vo.setLblStartTime(lblStartTime.toString());
		vo.setLblEndTime(lblEndTime.toString());
		vo.setLblWorkTime(getTimeTimeFormat(dto.getWorkTime()));
		// 勤務形態設定
		vo.setLblWorkType(getWorkTypeAbbrStartTimeEndTime(dto));
		// 直行直帰
		String directWorkManage = "";
		if (dto.getDirectStart() == 1) {
			directWorkManage = mospParams.getName("DirectStart") + SEPARATOR;
		}
		if (dto.getDirectEnd() == 1) {
			directWorkManage = directWorkManage + mospParams.getName("DirectEnd");
		}
		vo.setLblDirectWorkManage(directWorkManage);
		// 始業忘れ/その他
		String checkWorkStart = "";
		if (dto.getForgotRecordWorkStart() == 1) {
			checkWorkStart = mospParams.getName("ForgotRecordWorkStart") + SEPARATOR;
		}
		if (dto.getNotRecordWorkStart() == 1) {
			checkWorkStart = checkWorkStart + mospParams.getName("Others");
		}
		vo.setLblCheckWorkStart(checkWorkStart);
		vo.setLblRemarks(dto.getRemarks());
		// 無給時短時間設定
		vo.setLblUnpaidShortTime(getTimeTimeFormat(dto.getShortUnpaid()));
		// 勤怠コメント設定
		vo.setLblTimeComment(dto.getTimeComment());
		// 休憩情報
		vo.setLblRestTime(getTimeTimeFormat(dto.getRestTime()));
		vo.setLblNightRestTime(getTimeTimeFormat(dto.getNightRestTime()));
		vo.setLblPublicTime(getTimeTimeFormat(dto.getPublicTime()));
		vo.setLblPrivateTime(getTimeTimeFormat(dto.getPrivateTime()));
		// 遅刻早退情報
		vo.setLblLateTime(getTimeTimeFormat(dto.getLateTime()));
		vo.setLblLateReason(getCodeName(dto.getLateReason(), TimeConst.CODE_REASON_OF_LATE));
		vo.setLblLateCertificate(getCodeName(dto.getLateCertificate(), TimeConst.CODE_ALLOWANCE));
		vo.setLblLateComment(dto.getLateComment());
		vo.setLblLeaveEarlyTime(getTimeTimeFormat(dto.getLeaveEarlyTime()));
		vo.setLblLeaveEarlyReason(getCodeName(dto.getLeaveEarlyReason(), TimeConst.CODE_REASON_OF_LEAVE_EARLY));
		vo.setLblLeaveEarlyCertificate(getCodeName(dto.getLeaveEarlyCertificate(), TimeConst.CODE_ALLOWANCE));
		vo.setLblLeaveEarlyComment(dto.getLeaveEarlyComment());
		// 割増情報
		vo.setLblOvertime("");
		vo.setLblOverTimeIn(getTimeTimeFormat(dto.getOvertime()));
		vo.setLblOverTimeOut(getTimeTimeFormat(dto.getOvertimeOut()));
		vo.setLblLateNightTime(getTimeTimeFormat(dto.getLateNightTime()));
		vo.setLblSpecificWorkTimeIn(getTimeTimeFormat(dto.getSpecificWorkTime()));
		vo.setLblLegalWorkTime(getTimeTimeFormat(dto.getLegalWorkTime()));
		vo.setLblHolidayWorkTime(getTimeTimeFormat(dto.getSpecificWorkTime() + dto.getLegalWorkTime()));
		vo.setLblDecreaseTime(getTimeTimeFormat(dto.getDecreaseTime()));
		// 休憩情報取得
		List<RestDtoInterface> restList = timeReference().rest().getRestList(personalId, targetDate,
				TimeBean.TIMES_WORK_DEFAULT);
		// 休憩情報設定
		for (RestDtoInterface restDto : restList) {
			switch (restDto.getRest()) {
				case 1:
					vo.setLblRestTime1(
							getTimeWaveFormat(restDto.getRestStart(), restDto.getRestEnd(), restDto.getWorkDate()));
					break;
				case 2:
					vo.setLblRestTime2(
							getTimeWaveFormat(restDto.getRestStart(), restDto.getRestEnd(), restDto.getWorkDate()));
					break;
				case 3:
					vo.setLblRestTime3(
							getTimeWaveFormat(restDto.getRestStart(), restDto.getRestEnd(), restDto.getWorkDate()));
					break;
				case 4:
					vo.setLblRestTime4(
							getTimeWaveFormat(restDto.getRestStart(), restDto.getRestEnd(), restDto.getWorkDate()));
					break;
				case 5:
					vo.setLblRestTime5(
							getTimeWaveFormat(restDto.getRestStart(), restDto.getRestEnd(), restDto.getWorkDate()));
					break;
				case 6:
					vo.setLblRestTime6(
							getTimeWaveFormat(restDto.getRestStart(), restDto.getRestEnd(), restDto.getWorkDate()));
					break;
				default:
					break;
			}
		}
		// 公用外出情報取得
		List<GoOutDtoInterface> goOutList = timeReference().goOut().getPublicGoOutList(personalId, targetDate);
		// 公用外出情報設定
		for (GoOutDtoInterface goOutDto : goOutList) {
			switch (goOutDto.getTimesGoOut()) {
				case 1:
					vo.setLblPublicTime1(getTimeWaveFormat(goOutDto.getGoOutStart(), goOutDto.getGoOutEnd(),
							goOutDto.getWorkDate()));
					break;
				case 2:
					vo.setLblPublicTime2(getTimeWaveFormat(goOutDto.getGoOutStart(), goOutDto.getGoOutEnd(),
							goOutDto.getWorkDate()));
					break;
				default:
					break;
			}
		}
		// 私用外出情報取得
		goOutList = timeReference().goOut().getPrivateGoOutList(personalId, targetDate);
		// 私用外出情報設定
		for (GoOutDtoInterface goOutDto : goOutList) {
			switch (goOutDto.getTimesGoOut()) {
				case 1:
					vo.setLblPrivateTime1(getTimeWaveFormat(goOutDto.getGoOutStart(), goOutDto.getGoOutEnd(),
							goOutDto.getWorkDate()));
					break;
				case 2:
					vo.setLblPrivateTime2(getTimeWaveFormat(goOutDto.getGoOutStart(), goOutDto.getGoOutEnd(),
							goOutDto.getWorkDate()));
					break;
				default:
					break;
			}
		}
		// 勤怠データ修正情報取得
		AttendanceCorrectionDtoInterface correctionDto = timeReference().attendanceCorrection()
			.getLatestAttendanceCorrectionInfo(personalId, targetDate, TimeBean.TIMES_WORK_DEFAULT);
		if (correctionDto != null) {
			// 修正情報作成
			StringBuffer sb = new StringBuffer();
			sb.append(mospParams.getName("Corrector"));
			sb.append(mospParams.getName("Colon"));
			sb.append(reference().human().getHumanName(correctionDto.getCorrectionPersonalId(),
					correctionDto.getCorrectionDate()));
			sb.append(SEPARATOR);
			sb.append(mospParams.getName("Day"));
			sb.append(mospParams.getName("Hour"));
			sb.append(mospParams.getName("Colon"));
			sb.append(DateUtility.getStringDateAndDayAndTime(correctionDto.getCorrectionDate()));
			// 修正情報設定
			vo.setLblCorrectionHistory(sb.toString());
		}
		// ワークフローコメント情報取得及び確認
		WorkflowCommentDtoInterface commentDto = reference().workflowComment().getLatestWorkflowCommentInfo(workflow);
		if (commentDto == null) {
			return;
		}
		// ワークフローコメント情報設定
		String approver = getWorkflowOperator(commentDto);
		String state = geWorkflowtStatus(commentDto);
		vo.setLblAttendanceApprover(approver);
		vo.setLblAttendanceState(state);
		vo.setLblAttendanceComment(commentDto.getWorkflowComment());
		vo.setLblCancelApprover(approver);
		vo.setLblCancelState(state);
	}
	
	/**
	 * 勤怠申請以外の申請情報を取得し、設定する。<br>
	 * 但し、承認済みの申請のみを設定対象とする。<br>
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	protected void setOtherRequestValues() throws MospException {
		// VO準備
		ApprovalCardVo vo = (ApprovalCardVo)mospParams.getVo();
		// 申請ユーティリティクラス準備
		RequestUtilBeanInterface requestUtil = timeReference().requestUtil();
		// 各種申請情報取得
		requestUtil.setRequests(vo.getPersonalId(), vo.getTargetDate());
		// 対象個人ID及び対象日取得
		String personalId = vo.getPersonalId();
		Date targetDate = vo.getTargetDate();
		// ワークフロー統合クラス準備
		WorkflowIntegrateBeanInterface workflowIntegrate = reference().workflowIntegrate();
		// 残業申請情報設定
		setOvertimeValues(requestUtil.getOverTimeList(true));
		// 休暇申請情報設定
		setHolidayValues(requestUtil);
		// 振出休出申請取得
		WorkOnHolidayRequestDtoInterface workOnHolidayDto = timeReference().workOnHolidayRequest()
			.findForKeyOnWorkflow(personalId, targetDate);
		// 振出休出申請及びワークフロー状況確認
		if (workOnHolidayDto != null && workflowIntegrate.isCompleted(workOnHolidayDto.getWorkflow())) {
			// 振出休出申請情報設定
			setWorkOnHolidayValues(workOnHolidayDto);
		}
		// 代休申請取得
		List<SubHolidayRequestDtoInterface> subHolidayList = timeReference().subHolidayRequest()
			.getSubHolidayRequestList(personalId, targetDate);
		// 代休申請毎に処理
		for (SubHolidayRequestDtoInterface subHolidayDto : subHolidayList) {
			// 代休申請及びワークフロー状況確認
			if (workflowIntegrate.isCompleted(subHolidayDto.getWorkflow())) {
				// 代休申請情報設定
				setSubHolidayValues(subHolidayDto);
				break;
			}
		}
		// 時差出勤申請取得
		DifferenceRequestDtoInterface differenceDto = timeReference().differenceRequest()
			.findForKeyOnWorkflow(personalId, targetDate);
		// 時差出勤申請及びワークフロー状況確認
		if (differenceDto != null && workflowIntegrate.isCompleted(differenceDto.getWorkflow())) {
			// 時差出勤申請情報設定
			setDifferenceValues(differenceDto);
		}
	}
	
	/**
	 * 残業申請情報を設定する。<br>
	 * @param dto 残業申請情報
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	protected void setOvertimeValues(OvertimeRequestDtoInterface dto) throws MospException {
		List<OvertimeRequestDtoInterface> list = new ArrayList<OvertimeRequestDtoInterface>();
		list.add(dto);
		setOvertimeValues(list);
	}
	
	/**
	 * 残業申請情報を設定する。<br>
	 * @param list 残業申請情報リスト
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	protected void setOvertimeValues(List<OvertimeRequestDtoInterface> list) throws MospException {
		// VO取得
		ApprovalCardVo vo = (ApprovalCardVo)mospParams.getVo();
		// ワークフロー統合クラス取得
		WorkflowIntegrateBeanInterface workflowIntegrate = reference().workflowIntegrate();
		AttendanceDtoInterface attendanceDto = timeReference().attendance().findForKey(vo.getPersonalId(),
				vo.getTargetDate());
		boolean isAttendanceApprovable = false;
		if (attendanceDto != null) {
			WorkflowDtoInterface workflowDto = workflowIntegrate.getLatestWorkflowInfo(attendanceDto.getWorkflow());
			if (workflowDto != null
					&& (workflowIntegrate.isApprovable(workflowDto) || workflowIntegrate.isCompleted(workflowDto))) {
				isAttendanceApprovable = true;
			}
		}
		String[] aryLblOvertimeType = new String[list.size()];
		String[] aryLblOvertimeSchedule = new String[list.size()];
		String[] aryLblOvertimeResult = new String[list.size()];
		String[] aryLblOvertimeReason = new String[list.size()];
		String[] aryLblOvertimeState = new String[list.size()];
		String[] aryLblOvertimeComment = new String[list.size()];
		String[] aryLblOvertimeApprover = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			OvertimeRequestDtoInterface dto = list.get(i);
			int overtimeType = dto.getOvertimeType();
			// 残業申請情報設定
			aryLblOvertimeType[i] = getOvertimeTypeName(overtimeType);
			aryLblOvertimeSchedule[i] = getTimeTimeFormat(dto.getRequestTime());
			aryLblOvertimeResult[i] = mospParams.getName("Hyphen");
			if (isAttendanceApprovable) {
				int result = 0;
				if (overtimeType == TimeConst.CODE_OVERTIME_WORK_BEFORE) {
					// 勤務前残業
					result = attendanceDto.getOvertimeBefore();
				} else if (overtimeType == TimeConst.CODE_OVERTIME_WORK_AFTER) {
					// 勤務後残業
					result = attendanceDto.getOvertimeAfter();
				}
				aryLblOvertimeResult[i] = getTimeTimeFormat(result);
			}
			aryLblOvertimeReason[i] = dto.getRequestReason();
			// ワークフローコメント情報の取得
			WorkflowCommentDtoInterface commentDto = reference().workflowComment()
				.getLatestWorkflowCommentInfo(dto.getWorkflow());
			if (commentDto == null) {
				aryLblOvertimeState[i] = "";
				aryLblOvertimeComment[i] = "";
				aryLblOvertimeApprover[i] = "";
				continue;
			}
			// ワークフローコメント情報設定
			aryLblOvertimeState[i] = geWorkflowtStatus(commentDto);
			aryLblOvertimeComment[i] = commentDto.getWorkflowComment();
			aryLblOvertimeApprover[i] = getWorkflowOperator(commentDto);
		}
		// VOに項目を設定
		vo.setLblOvertimeType(aryLblOvertimeType);
		vo.setLblOvertimeSchedule(aryLblOvertimeSchedule);
		vo.setLblOvertimeResult(aryLblOvertimeResult);
		vo.setLblOvertimeReason(aryLblOvertimeReason);
		vo.setLblOvertimeState(aryLblOvertimeState);
		vo.setLblOvertimeComment(aryLblOvertimeComment);
		vo.setLblOvertimeApprover(aryLblOvertimeApprover);
		if (vo.isOvertime()) {
			vo.setLblCancelState(aryLblOvertimeState[0]);
			vo.setLblCancelApprover(aryLblOvertimeApprover[0]);
		}
	}
	
	/**
	 * 休暇申請情報を取得し、設定する。<br>
	 * @param dto 休暇申請情報
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	protected void setHolidayValues(HolidayRequestDtoInterface dto) throws MospException {
		List<HolidayRequestDtoInterface> list = new ArrayList<HolidayRequestDtoInterface>();
		list.add(dto);
		setHolidayValues(list);
	}
	
	/**
	 * 休暇申請情報を取得し、設定する。<br>
	 * @param requestUtil 申請ユーティリティ
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	protected void setHolidayValues(RequestUtilBeanInterface requestUtil) throws MospException {
		// 振出・休出申請取得
		WorkOnHolidayRequestDtoInterface workOnHolidayRequestDto = requestUtil.getWorkOnHolidayDto(true);
		if (workOnHolidayRequestDto == null
				|| workOnHolidayRequestDto.getSubstitute() == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_ON) {
			// 休暇申請情報設定
			setHolidayValues(requestUtil.getHolidayList(true));
		}
	}
	
	/**
	 * 休暇申請情報を取得し、設定する。<br>
	 * @param list 休暇申請情報リスト
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	protected void setHolidayValues(List<HolidayRequestDtoInterface> list) throws MospException {
		// VO取得
		ApprovalCardVo vo = (ApprovalCardVo)mospParams.getVo();
		String[] aryLblHolidayDate = new String[list.size()];
		String[] aryLblHolidayType = new String[list.size()];
		String[] aryLblHolidayLength = new String[list.size()];
		String[] aryLblHolidayTime = new String[list.size()];
		String[] aryLblHolidayReason = new String[list.size()];
		String[] aryLblHolidayState = new String[list.size()];
		String[] aryLblHolidayApprover = new String[list.size()];
		String[] aryLblHolidayComment = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			HolidayRequestDtoInterface dto = list.get(i);
			// 休暇申請情報設定
			StringBuffer sb = new StringBuffer();
			sb.append(getStringDateAndDay(dto.getRequestStartDate()));
			sb.append(mospParams.getName("Wave"));
			sb.append(getStringDateAndDay(dto.getRequestEndDate()));
			aryLblHolidayDate[i] = sb.toString();
			aryLblHolidayType[i] = getHolidayTypeName(dto.getHolidayType1(), dto.getHolidayType2(),
					dto.getRequestStartDate());
			aryLblHolidayLength[i] = getHolidayRange(dto.getHolidayRange());
			aryLblHolidayTime[i] = getTimeWaveFormat(dto.getStartTime(), dto.getEndTime(), dto.getRequestStartDate());
			aryLblHolidayReason[i] = dto.getRequestReason();
			// ワークフローコメント情報取得
			WorkflowCommentDtoInterface commentDto = reference().workflowComment()
				.getLatestWorkflowCommentInfo(dto.getWorkflow());
			if (commentDto == null) {
				aryLblHolidayState[i] = "";
				aryLblHolidayApprover[i] = "";
				aryLblHolidayComment[i] = "";
				continue;
			}
			// ワークフローコメント情報設定
			aryLblHolidayState[i] = geWorkflowtStatus(commentDto);
			aryLblHolidayApprover[i] = getWorkflowOperator(commentDto);
			aryLblHolidayComment[i] = commentDto.getWorkflowComment();
		}
		// VOに項目を設定
		vo.setLblHolidayDate(aryLblHolidayDate);
		vo.setLblHolidayType(aryLblHolidayType);
		vo.setLblHolidayLength(aryLblHolidayLength);
		vo.setLblHolidayTime(aryLblHolidayTime);
		vo.setLblHolidayReason(aryLblHolidayReason);
		vo.setLblHolidayState(aryLblHolidayState);
		vo.setLblHolidayComment(aryLblHolidayComment);
		vo.setLblHolidayApprover(aryLblHolidayApprover);
		if (vo.isHoliday()) {
			vo.setLblCancelState(aryLblHolidayState[0]);
			vo.setLblCancelApprover(aryLblHolidayApprover[0]);
		}
	}
	
	/**
	 * 振出休出申請情報を取得し、設定する。<br>
	 * @param dto 振出休出申請情報
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	protected void setWorkOnHolidayValues(WorkOnHolidayRequestDtoInterface dto) throws MospException {
		// VO準備
		ApprovalCardVo vo = (ApprovalCardVo)mospParams.getVo();
		// 対象ワークフロー番号取得
		long workflow = dto.getWorkflow();
		// 振出休出申請情報設定
		String workRange = "";
		int substitute = dto.getSubstitute();
		if (substitute == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_AM) {
			// 午前の場合
			workRange = mospParams.getName("AnteMeridiem");
		} else if (substitute == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_PM) {
			// 午後の場合
			workRange = mospParams.getName("PostMeridiem");
		}
		vo.setLblWorkOnHolidayDate(getStringDateAndDay(dto.getRequestDate()) + workRange);
		vo.setLblWorkOnHolidayTime(getWorkOnHolidaySchedule(dto));
		vo.setLblWorkOnHolidayReason(dto.getRequestReason());
		vo.setLblWorkOnHolidayTransferDate("");
		// 振替休日リスト取得
		List<SubstituteDtoInterface> substituteList = timeReference().substitute().getSubstituteList(workflow);
		for (SubstituteDtoInterface substituteDto : substituteList) {
			StringBuffer sb = new StringBuffer();
			sb.append(getStringDateAndDay(substituteDto.getSubstituteDate()));
			int substituteRange = substituteDto.getSubstituteRange();
			if (substituteRange == TimeConst.CODE_HOLIDAY_RANGE_AM) {
				// 午前休の場合
				sb.append(mospParams.getName("AnteMeridiem"));
			} else if (substituteRange == TimeConst.CODE_HOLIDAY_RANGE_PM) {
				// 午後休の場合
				sb.append(mospParams.getName("PostMeridiem"));
			}
			vo.setLblWorkOnHolidayTransferDate(sb.toString());
			break;
		}
		// ワークフローコメント情報取得及び確認
		WorkflowCommentDtoInterface commentDto = reference().workflowComment().getLatestWorkflowCommentInfo(workflow);
		if (commentDto == null) {
			return;
		}
		// ワークフローコメント情報設定
		String approver = getWorkflowOperator(commentDto);
		String state = geWorkflowtStatus(commentDto);
		vo.setLblWorkOnHolidayApprover(approver);
		vo.setLblWorkOnHolidayState(state);
		vo.setLblWorkOnHolidayComment(commentDto.getWorkflowComment());
		if (vo.isWorkOnHoliday()) {
			vo.setLblCancelApprover(approver);
			vo.setLblCancelState(state);
		}
	}
	
	/**
	 * 代休申請情報を取得し、設定する。<br>
	 * @param dto 代休申請情報
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	protected void setSubHolidayValues(SubHolidayRequestDtoInterface dto) throws MospException {
		// VO準備
		ApprovalCardVo vo = (ApprovalCardVo)mospParams.getVo();
		// 代休申請情報設定
		vo.setLblSubHolidayDate(getStringDateAndDay(dto.getRequestDate()));
		vo.setLblSubHolidayWorkDate(getStringDateAndDay(dto.getWorkDate()));
		// 対象ワークフロー番号取得
		long workflow = dto.getWorkflow();
		// ワークフローコメント情報取得及び確認
		WorkflowCommentDtoInterface commentDto = reference().workflowComment().getLatestWorkflowCommentInfo(workflow);
		if (commentDto == null) {
			return;
		}
		// ワークフローコメント情報設定
		String approver = getWorkflowOperator(commentDto);
		String state = geWorkflowtStatus(commentDto);
		vo.setLblSubHolidayApprover(approver);
		vo.setLblSubHolidayState(state);
		vo.setLblSubHolidayComment(commentDto.getWorkflowComment());
		if (vo.isSubHoliday()) {
			vo.setLblCancelApprover(approver);
			vo.setLblCancelState(state);
		}
	}
	
	/**
	 * 時差出勤申請情報を取得し、設定する。<br>
	 * @param dto 時差出勤申請情報
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	protected void setDifferenceValues(DifferenceRequestDtoInterface dto) throws MospException {
		// VO準備
		ApprovalCardVo vo = (ApprovalCardVo)mospParams.getVo();
		// 時差出勤申請情報設定
		vo.setLblDifferenceDate(getStringDateAndDay(dto.getRequestDate()));
		vo.setLblDifferenceWorkType(getBeforeDifferenceRequestWorkTypeAbbr());
		vo.setLblDifferenceWorkTime(timeReference().differenceRequest().getDifferenceTime(dto));
		vo.setLblDifferenceReason(dto.getRequestReason());
		// 対象ワークフロー番号取得
		long workflow = dto.getWorkflow();
		// ワークフローコメント情報取得及び確認
		WorkflowCommentDtoInterface commentDto = reference().workflowComment().getLatestWorkflowCommentInfo(workflow);
		if (commentDto == null) {
			return;
		}
		// ワークフローコメント情報設定
		String approver = getWorkflowOperator(commentDto);
		String state = geWorkflowtStatus(commentDto);
		vo.setLblDifferenceApprover(approver);
		vo.setLblDifferenceState(state);
		vo.setLblDifferenceComment(commentDto.getWorkflowComment());
		if (vo.isDifference()) {
			vo.setLblCancelApprover(approver);
			vo.setLblCancelState(state);
		}
	}
	
	/**
	 * 勤務形態変更申請情報を取得し、設定する。<br>
	 * @param dto 勤務形態変更申請情報
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	protected void setWorkTypeChangeValues(WorkTypeChangeRequestDtoInterface dto) throws MospException {
		// VO取得
		ApprovalCardVo vo = (ApprovalCardVo)mospParams.getVo();
		// 勤務形態変更申請情報設定
		vo.setLblWorkTypeChangeDate(getStringDateAndDay(dto.getRequestDate()));
		vo.setLblWorkTypeChangeBeforeWorkType(time().workTypeChangeRequestRegist().getScheduledWorkTypeName(dto));
		vo.setLblWorkTypeChangeAfterWorkType(
				timeReference().workType().getWorkTypeAbbrAndTime(dto.getWorkTypeCode(), dto.getRequestDate()));
		vo.setLblWorkTypeChangeReason(dto.getRequestReason());
		WorkflowCommentDtoInterface commentDto = reference().workflowComment()
			.getLatestWorkflowCommentInfo(dto.getWorkflow());
		if (commentDto == null) {
			return;
		}
		// ワークフローコメント情報設定
		String approver = getWorkflowOperator(commentDto);
		String state = geWorkflowtStatus(commentDto);
		vo.setLblWorkTypeChangeApprover(approver);
		vo.setLblWorkTypeChangeState(state);
		vo.setLblWorkTypeChangeComment(commentDto.getWorkflowComment());
		if (vo.isWorkTypeChange()) {
			vo.setLblCancelApprover(approver);
			vo.setLblCancelState(state);
		}
	}
	
	/**
	 * ボタン要否を設定する。<br>
	 * 申請確認詳細フラグ及びワークフロー情報で、判断する。<br>
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	protected void setButtonFlag() throws MospException {
		// VO準備
		ApprovalCardVo vo = (ApprovalCardVo)mospParams.getVo();
		// ワークフロー統合クラス取得
		WorkflowIntegrateBeanInterface workflowIntegrate = reference().workflowIntegrate();
		// ワークフロー情報を取得
		WorkflowDtoInterface dto = reference().workflow().getLatestWorkflowInfo(vo.getWorkflow());
		// 存在確認
		checkSelectedDataExist(dto);
		// ログインユーザ個人ID取得
		String personalId = mospParams.getUser().getPersonalId();
		// 申請確認詳細フラグ及びワークフロー状況確認
		if (vo.isConfirmation()) {
			// 承認解除ボタン不要
			vo.setNeedCancelButton(false);
			// 承認差戻ボタン不要
			vo.setNeedApproveButton(false);
			// 対象個人IDにつき対象日付が未締でない場合
			if (!timeReference().cutoffUtil().isNotTighten(vo.getPersonalId(), dto.getWorkflowDate())) {
				return;
			}
			if (workflowIntegrate.isCompleted(dto) || workflowIntegrate.isCancelApprovable(dto)
					|| workflowIntegrate.isCancelWithDrawnApprovable(dto)) {
				// 承認済又は解除承認可能の場合
				// 勤怠以外の場合
				if (vo.isAttendance() == false) {
					// 同じ日に取下、下書、一次戻以外の勤怠申請がある場合
					if (timeReference().approvalInfo().isExistAttendanceTargetDate(vo.getPersonalId(),
							dto.getWorkflowDate())) {
						return;
					}
				}
				// ログインユーザがスーパーユーザ又はログインユーザが操作者(最終承認者)の場合
				if (RoleUtility.isSuper(mospParams) || workflowIntegrate.isApprover(dto, personalId)) {
					vo.setNeedCancelButton(true);
					return;
				}
			} else if (workflowIntegrate.isApprovable(dto)) {
				// 承認可能の場合
				// ログインユーザがスーパーユーザ又はログインユーザが操作者(最終承認者)の場合
				if (RoleUtility.isSuper(mospParams) || workflowIntegrate.isApprover(dto, personalId)) {
					vo.setNeedApproveButton(true);
					return;
				}
				// システム日付における代理情報を取得
				List<SubApproverDtoInterface> subApproverList = reference().subApprover().findForSubApproverId(
						personalId, PlatformConst.WORKFLOW_TYPE_TIME, getSystemDate(), getSystemDate());
				// 代理情報毎に処理
				for (SubApproverDtoInterface subApproverDto : subApproverList) {
					// 代理元が操作者であるかを確認
					if (workflowIntegrate.isApprover(dto, subApproverDto.getPersonalId())) {
						vo.setNeedApproveButton(true);
						return;
					}
				}
			}
		} else {
			// 対象個人IDにつき対象日付が未締でない場合
			if (!timeReference().cutoffUtil().isNotTighten(vo.getPersonalId(), dto.getWorkflowDate())) {
				// 承認可能でなければ承認差戻ボタン不要
				vo.setNeedApproveButton(false);
				// 解除承認可能でなければ承認解除差戻ボタン不要
				vo.setNeedCancelApproveButton(false);
				return;
			}
			if (workflowIntegrate.isApprovable(dto)) {
				// 承認可能の場合
				// 承認解除差戻ボタン不要
				vo.setNeedCancelApproveButton(false);
				// ログインユーザがスーパーユーザ又はログインユーザが操作者(最終承認者)の場合
				if (RoleUtility.isSuper(mospParams) || workflowIntegrate.isApprover(dto, personalId)) {
					vo.setNeedApproveButton(true);
					return;
				}
				// システム日付における代理情報を取得
				List<SubApproverDtoInterface> subApproverList = reference().subApprover().findForSubApproverId(
						personalId, PlatformConst.WORKFLOW_TYPE_TIME, getSystemDate(), getSystemDate());
				// 代理情報毎に処理
				for (SubApproverDtoInterface subApproverDto : subApproverList) {
					// 代理元が操作者であるかを確認
					if (workflowIntegrate.isApprover(dto, subApproverDto.getPersonalId())) {
						vo.setNeedApproveButton(true);
						return;
					}
				}
			} else if (workflowIntegrate.isCancelApprovable(dto)
					|| workflowIntegrate.isCancelWithDrawnApprovable(dto)) {
				// 解除承認可能の場合
				// ログインユーザがスーパーユーザ又はログインユーザが操作者(最終承認者)の場合
				if (RoleUtility.isSuper(mospParams) || workflowIntegrate.isApprover(dto, personalId)) {
					vo.setNeedCancelApproveButton(true);
					return;
				}
				// システム日付における代理情報を取得
				List<SubApproverDtoInterface> subApproverList = reference().subApprover().findForSubApproverId(
						personalId, PlatformConst.WORKFLOW_TYPE_TIME, getSystemDate(), getSystemDate());
				// 代理情報毎に処理
				for (SubApproverDtoInterface subApproverDto : subApproverList) {
					// 代理元が操作者であるかを確認
					if (workflowIntegrate.isApprover(dto, subApproverDto.getPersonalId())) {
						vo.setNeedCancelApproveButton(true);
						return;
					}
				}
			}
			vo.setNeedApproveButton(false);
			vo.setNeedCancelApproveButton(false);
		}
	}
	
	/**
	 * 前ワークフロー・次ワークフローを設定する。<br>
	 */
	protected void setRollWorkflow() {
		// VO取得
		ApprovalCardVo vo = (ApprovalCardVo)mospParams.getVo();
		Object object = mospParams.getGeneralParam(TimeConst.PRM_ROLL_ARRAY);
		if (object != null) {
			vo.setRollArray((BaseDtoInterface[])object);
		}
		if (vo.getRollArray() == null || vo.getRollArray().length == 0) {
			return;
		}
		int i = 0;
		for (BaseDtoInterface baseDto : vo.getRollArray()) {
			ManagementRequestListDtoInterface dto = (ManagementRequestListDtoInterface)baseDto;
			if (vo.getWorkflow() == dto.getWorkflow()) {
				break;
			}
			i++;
		}
		// 前ワークフロー設定
		if (i > 0) {
			BaseDtoInterface baseDto = vo.getRollArray()[i - 1];
			ManagementRequestListDtoInterface dto = (ManagementRequestListDtoInterface)baseDto;
			vo.setPrevWorkflow(dto.getWorkflow());
			vo.setPrevCommand(getRollCommand(dto.getRequestType()));
		}
		// 次ワークフロー設定
		if (i + 1 < vo.getRollArray().length) {
			BaseDtoInterface baseDto = vo.getRollArray()[i + 1];
			ManagementRequestListDtoInterface dto = (ManagementRequestListDtoInterface)baseDto;
			vo.setNextWorkflow(dto.getWorkflow());
			vo.setNextCommand(getRollCommand(dto.getRequestType()));
		}
	}
	
	/**
	 * 前次遷移用コマンドを取得する。<br>
	 * @param requestType 申請区分
	 * @return コマンド
	 */
	protected String getRollCommand(String requestType) {
		if (CMD_APPROVAL_CONFIRMATION_ATTENDANCE.equals(mospParams.getCommand())
				|| CMD_APPROVAL_CONFIRMATION_OVERTIME.equals(mospParams.getCommand())
				|| CMD_APPROVAL_CONFIRMATION_HOLIDAY.equals(mospParams.getCommand())
				|| CMD_APPROVAL_CONFIRMATION_WORKONHOLIDAY.equals(mospParams.getCommand())
				|| CMD_APPROVAL_CONFIRMATION_SUBHOLIDAY.equals(mospParams.getCommand())
				|| CMD_APPROVAL_CONFIRMATION_DIFFERENCE.equals(mospParams.getCommand())
				|| CMD_APPROVAL_CONFIRMATION_WORKTYPECHANGE.equals(mospParams.getCommand())) {
			// 勤怠承認確認画面表示・
			// 残業承認確認画面表示・
			// 振出・休出承認確認画面表示・
			// 代休承認確認画面表示・
			// 時差出勤確認承認画面表示・
			// 勤務形態変更確認承認画面表示の場合
			if (TimeConst.CODE_FUNCTION_WORK_MANGE.equals(requestType)) {
				// 勤怠申請の場合
				return CMD_APPROVAL_CONFIRMATION_ATTENDANCE;
			} else if (TimeConst.CODE_FUNCTION_OVER_WORK.equals(requestType)) {
				// 残業申請の場合
				return CMD_APPROVAL_CONFIRMATION_OVERTIME;
			} else if (TimeConst.CODE_FUNCTION_VACATION.equals(requestType)) {
				// 休暇申請の場合
				return CMD_APPROVAL_CONFIRMATION_HOLIDAY;
			} else if (TimeConst.CODE_FUNCTION_WORK_HOLIDAY.equals(requestType)) {
				// 振出・休出申請の場合
				return CMD_APPROVAL_CONFIRMATION_WORKONHOLIDAY;
			} else if (TimeConst.CODE_FUNCTION_COMPENSATORY_HOLIDAY.equals(requestType)) {
				// 代休申請の場合
				return CMD_APPROVAL_CONFIRMATION_SUBHOLIDAY;
			} else if (TimeConst.CODE_FUNCTION_DIFFERENCE.equals(requestType)) {
				// 時差出勤申請の場合
				return CMD_APPROVAL_CONFIRMATION_DIFFERENCE;
			} else if (TimeConst.CODE_FUNCTION_WORK_TYPE_CHANGE.equals(requestType)) {
				// 勤務形態変更申請の場合
				return CMD_APPROVAL_CONFIRMATION_WORKTYPECHANGE;
			}
		} else if (CMD_APPROVAL_ATTENDANCE.equals(mospParams.getCommand())
				|| CMD_APPROVAL_OVERTIME.equals(mospParams.getCommand())
				|| CMD_APPROVAL_HOLIDAY.equals(mospParams.getCommand())
				|| CMD_APPROVAL_WORKONHOLIDAY.equals(mospParams.getCommand())
				|| CMD_APPROVAL_SUBHOLIDAY.equals(mospParams.getCommand())
				|| CMD_APPROVAL_DIFFERENCE.equals(mospParams.getCommand())
				|| CMD_APPROVAL_WORKTYPECHANGE.equals(mospParams.getCommand())) {
			// 勤怠承認画面表示・
			// 残業承認画面表示・
			// 休暇承認画面表示・
			// 振出・休出承認画面表示・
			// 代休承認画面表示・
			// 時差出勤承認画面表示・
			// 勤務形態変更承認画面表示の場合
			if (TimeConst.CODE_FUNCTION_WORK_MANGE.equals(requestType)) {
				// 勤怠申請の場合
				return CMD_APPROVAL_ATTENDANCE;
			} else if (TimeConst.CODE_FUNCTION_OVER_WORK.equals(requestType)) {
				// 残業申請の場合
				return CMD_APPROVAL_OVERTIME;
			} else if (TimeConst.CODE_FUNCTION_VACATION.equals(requestType)) {
				// 休暇申請の場合
				return CMD_APPROVAL_HOLIDAY;
			} else if (TimeConst.CODE_FUNCTION_WORK_HOLIDAY.equals(requestType)) {
				// 振出・休出申請の場合
				return CMD_APPROVAL_WORKONHOLIDAY;
			} else if (TimeConst.CODE_FUNCTION_COMPENSATORY_HOLIDAY.equals(requestType)) {
				// 代休申請の場合
				return CMD_APPROVAL_SUBHOLIDAY;
			} else if (TimeConst.CODE_FUNCTION_DIFFERENCE.equals(requestType)) {
				// 時差出勤申請の場合
				return CMD_APPROVAL_DIFFERENCE;
			} else if (TimeConst.CODE_FUNCTION_WORK_TYPE_CHANGE.equals(requestType)) {
				// 勤務形態変更申請の場合
				return CMD_APPROVAL_WORKTYPECHANGE;
			}
		}
		return "";
	}
	
	/**
	 * VOからワークフローコメントを取得する。<br>
	 * @return ワークフローコメント
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected String getWorkflowComment() throws MospException {
		// VO取得
		ApprovalCardVo vo = (ApprovalCardVo)mospParams.getVo();
		if (vo.isNeedCancelApproveButton()) {
			return vo.getTxtCancelComment();
		}
		// ワークフローコメント準備
		String workflowComment = null;
		// ワークフローコメント取得
		if (vo.isAttendance()) {
			workflowComment = vo.getTxtAttendanceComment();
		} else if (vo.isOvertime()) {
			workflowComment = vo.getTxtOverTimeComment();
		} else if (vo.isHoliday()) {
			workflowComment = vo.getTxtHolidayComment();
		} else if (vo.isWorkOnHoliday()) {
			workflowComment = vo.getTxtWorkOnHolidayComment();
		} else if (vo.isSubHoliday()) {
			workflowComment = vo.getTxtCompensationComment();
		} else if (vo.isDifference()) {
			workflowComment = vo.getTxtDifferenceComment();
		} else if (vo.isWorkTypeChange()) {
			workflowComment = vo.getTxtWorkTypeChangeComment();
		}
		return workflowComment;
	}
	
	/**
	 * 勤務形態名称【HH:MM～HH:MM】を取得する。
	 * 勤怠情報がなければ空文字を返す。
	 * @param dto 勤怠情報
	 * @return 勤務形態(名称【HH:MM～HH:MM】)
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected String getWorkTypeAbbrStartTimeEndTime(AttendanceDtoInterface dto) throws MospException {
		WorkTypeReferenceBeanInterface workType = timeReference().workType();
		// 勤怠情報確認
		if (dto == null) {
			return "";
		}
		// 勤務形態取得
		String workTypeCode = dto.getWorkTypeCode();
		// 時差出勤の場合
		if (isDifferenceWorkType(workTypeCode)) {
			return mospParams.getProperties().getCodeItemName(TimeConst.CODE_DIFFERENCE_TYPE, dto.getWorkTypeCode());
		}
		// 法定休日労働・所定休日労働の場合
		if (TimeConst.CODE_WORK_ON_LEGAL_HOLIDAY.equals(workTypeCode)
				|| TimeConst.CODE_WORK_ON_PRESCRIBED_HOLIDAY.equals(workTypeCode)) {
			return workType.getParticularWorkTypeName(workTypeCode);
		}
		// リクエストユーティリティ準備
		RequestUtilBeanInterface requestUtil = timeReference().requestUtil();
		requestUtil.setRequests(dto.getPersonalId(), dto.getWorkDate());
		// 申請エンティティを取得
		RequestEntityInterface entity = requestUtil.getRequestEntity(dto.getPersonalId(), dto.getWorkDate());
		// 勤務形態略称を取得
		return timeReference().workType().getWorkTypeAbbrAndTime(workTypeCode, dto.getWorkDate(),
				entity.isAmHoliday(false), entity.isPmHoliday(false));
	}
	
	/**
	 * 時差出勤の勤務形態コードであるか確認する。
	 * @param workTypeCode 勤務形態コード
	 * @return 確認結果(true：時差出勤の勤務形態コード、false：時差出勤ではない勤務形態コード)
	 */
	protected boolean isDifferenceWorkType(String workTypeCode) {
		return TimeConst.CODE_DIFFERENCE_TYPE_A.equals(workTypeCode)
				|| TimeConst.CODE_DIFFERENCE_TYPE_B.equals(workTypeCode)
				|| TimeConst.CODE_DIFFERENCE_TYPE_C.equals(workTypeCode)
				|| TimeConst.CODE_DIFFERENCE_TYPE_D.equals(workTypeCode)
				|| TimeConst.CODE_DIFFERENCE_TYPE_S.equals(workTypeCode);
	}
	
	/**
	 * 承認確認追加処理リストを取得する。<br>
	 * @return 承認確認追加処理リスト
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	protected List<ApprovalCardAddonBeanInterface> getAddonBeans() throws MospException {
		// 承認確認追加処理リストを準備
		List<ApprovalCardAddonBeanInterface> addonBeans = new ArrayList<ApprovalCardAddonBeanInterface>();
		// 承認確認追加処理配列毎に処理
		for (String[] addon : getCodeArray(CODE_KEY_ADDONS, false)) {
			// 承認確認追加処理を取得
			String addonBean = addon[0];
			// 承認確認追加処理が設定されていない場合
			if (MospUtility.isEmpty(addonBean)) {
				// 次の承認確認追加処理へ
				continue;
			}
			// 承認確認追加処理を取得
			ApprovalCardAddonBeanInterface bean = (ApprovalCardAddonBeanInterface)platform().createBean(addonBean);
			// 承認確認追加処理リストに値を追加
			addonBeans.add(bean);
		}
		// 承認確認追加処理リストを取得
		return addonBeans;
	}
	
	/**
	 * 追加JSPリストを取得する。<br>
	 * @return 追加JSPリスト
	 */
	protected List<String> getAddonJsps() {
		// 追加JSPリストを準備
		List<String> addonJsps = new ArrayList<String>();
		// 追加処理配列毎に処理
		for (String[] addon : getCodeArray(CODE_KEY_ADDONS, false)) {
			// 追加JSPを取得
			String addonJsp = addon[1];
			// 追加JSPが設定されていない場合
			if (MospUtility.isEmpty(addonJsp)) {
				// 次の追加JSPへ
				continue;
			}
			// 追加JSPリストに値を追加
			addonJsps.add(addonJsp);
		}
		// 追加JSPリストを取得
		return addonJsps;
	}
	
	/**
	 * ワークフロー操作者名を取得する。<br>
	 * @param dto ワークフローコメント情報
	 * @return ワークフロー操作者名
	 * @throws MospException 操作者名の取得に失敗した場合
	 */
	protected String getWorkflowOperator(WorkflowCommentDtoInterface dto) throws MospException {
		return reference().human().getHumanName(dto.getPersonalId(), dto.getWorkflowDate());
	}
	
	/**
	 * ワークフロー状況を取得する。<br>
	 * @param dto ワークフローコメント情報
	 * @return ワークフロー状況
	 */
	protected String geWorkflowtStatus(WorkflowCommentDtoInterface dto) {
		return getStatusStageValueView(dto.getWorkflowStatus(), dto.getWorkflowStage());
	}
	
	/**
	 * 申請確認詳細名称を取得する。<br>
	 * @return 申請確認詳細
	 */
	protected String getNameRequestCard() {
		return mospParams.getName("RequestCardVo");
	}
	
}
