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
package jp.mosp.time.portal.bean.impl;

import java.util.Date;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.platform.bean.portal.PortalBeanInterface;
import jp.mosp.platform.bean.portal.impl.PortalBean;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.platform.utils.PfNameUtility;
import jp.mosp.time.bean.ApplicationReferenceBeanInterface;
import jp.mosp.time.bean.TimeRecordBeanInterface;
import jp.mosp.time.bean.TimeRecordReferenceBeanInterface;
import jp.mosp.time.bean.TimeSettingReferenceBeanInterface;
import jp.mosp.time.bean.impl.TimeRecordBean;
import jp.mosp.time.dto.settings.TimeRecordDtoInterface;
import jp.mosp.time.entity.ApplicationEntity;
import jp.mosp.time.utils.TimeMessageUtility;
import jp.mosp.time.utils.TimeUtility;

/**
 * ポータル用タイムカード処理クラス。<br>
 */
public class PortalTimeCardBean extends PortalBean implements PortalBeanInterface {
	
	/**
	 * パス(ポータル用打刻機能JSP)。
	 */
	protected static final String	PATH_PORTAL_VIEW	= "/jsp/time/portal/portalTimeCard.jsp";
	
	/**
	 * パス(ポータル用打刻機能JS)。
	 */
	public static final String		JS_TIME				= "jsTime";
	
	/**
	 * ポータルパラメータキー(始業)。
	 */
	public static final String		RECODE_START_WORK	= "StartWork";
	
	/**
	 * ポータルパラメータキー(終業)。
	 */
	public static final String		RECODE_END_WORK		= "EndWork";
	
	/**
	 * ポータルパラメータキー(休憩入)。
	 */
	public static final String		RECODE_START_REST	= "StartRest";
	
	/**
	 * ポータルパラメータキー(休憩戻)。
	 */
	public static final String		RECODE_END_REST		= "EndRest";
	
	/**
	 * ポータルパラメータキー(定時終業)。
	 */
	public static final String		RECODE_REGULAR_END	= "RegularEnd";
	
	/**
	 * ポータルパラメータキー(残業有終業)。
	 */
	public static final String		RECODE_OVER_END		= "OverEnd";
	
	/**
	 * ポータルパラメータキー(出勤)。
	 */
	public static final String		RECODE_REGULAR_WORK	= "RegularWork";
	
	/**
	 * パラメータキー(押されたボタンの値)。
	 */
	public static final String		PRM_RECODE_TYPE		= "RecodeType";
	
	/**
	 * パラメータキー(ポータル出退勤ボタン表示)。
	 */
	public static final String		PRM_TIME_BUTTON		= "TimeButton";
	
	/**
	 * パラメータキー(ポータル休憩ボタン表示)。
	 */
	public static final String		PRM_REST_BUTTON		= "RestButton";
	
	/**
	 * パラメータキー(終業打刻時メッセージ文字列)。
	 */
	public static final String		PRM_RECORD_END_STR	= "RecordEndStr";
	
	
	/**
	 * {@link PortalBean#PortalBean()}を実行する。<br>
	 */
	public PortalTimeCardBean() {
		super();
	}
	
	@Override
	public void initBean() {
		// 処理無し
	}
	
	@Override
	public void show() throws MospException {
		// 勤怠一覧が利用できない場合
		if (TimeUtility.isAttendanceListAvailable(mospParams) == false) {
			// 処理無し
			return;
		}
		// ポータル用タイムカードを表示
		showPortalTimeCard();
	}
	
	@Override
	public void regist() throws MospException {
		// VOから値を受け取り変数に詰める
		String recodeType = getPortalParameter(PRM_RECODE_TYPE);
		// コマンド毎の処理
		if (recodeType.equals(RECODE_START_WORK)) {
			// 出勤
			recordStartWork();
		} else if (recodeType.equals(RECODE_END_WORK)) {
			// 退勤
			recordEndWork();
		} else if (recodeType.equals(RECODE_START_REST)) {
			// 休憩入
			recordStartRest();
		} else if (recodeType.equals(RECODE_END_REST)) {
			// 休憩戻
			recordEndRest();
		} else if (recodeType.equals(RECODE_REGULAR_END)) {
			// 定時終業
			recordRegularEnd();
		} else if (recodeType.equals(RECODE_OVER_END)) {
			// 残業有終業
			recordOverEnd();
		} else if (recodeType.equals(RECODE_REGULAR_WORK)) {
			// 出勤
			recordRegularWork();
		}
	}
	
	/**
	 * ポータル用タイムカードを表示する。<br>
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	protected void showPortalTimeCard() throws MospException {
		// ポータル用JSPパス追加
		addPortalViewList(PATH_PORTAL_VIEW);
		// ポータル用JS
		mospParams.addGeneralParam(JS_TIME, String.valueOf(getSystemTimeAndSecond().getTime()));
		// 対象個人ID(ログインユーザの個人ID)取得
		String personalId = mospParams.getUser().getPersonalId();
		// システム日付取得
		Date targetDate = getSystemDate();
		// 設定適用エンティティ取得
		ApplicationEntity application = getApplicationReferenceBean().getApplicationEntity(personalId, targetDate);
		// ポータル出退勤ボタン表示設定取得
		putPortalParameter(PRM_TIME_BUTTON, String.valueOf(application.getPortalTimeButtons()));
		// ポータル休憩ボタン表示設定取得
		putPortalParameter(PRM_REST_BUTTON, String.valueOf(application.getPortalRestButtons()));
		// 終業打刻時メッセージ文字列を設定(申請)
		putPortalParameter(PRM_RECORD_END_STR, PfNameUtility.application(mospParams));
		// ポータル出退勤ボタン表示設定が始業/終業である場合
		if (application.getPortalTimeButtons() == 1) {
			// 勤怠設定エンティティから終業打刻時承認状態設定を取得(デフォルト：申請)
			int endWorkAppliStatus = getTimeSettingRefer().getEntity(application.getTimeSettingDto())
				.getLimit(TimeRecordBean.TYPE_RS_END_WORK_APPLI_STATUS, TimeRecordBean.TYPE_RECORD_APPLY);
			// 終業打刻時承認状態設定が下書である場合
			if (endWorkAppliStatus == TimeRecordBean.TYPE_RECORD_DRAFT) {
				// 終業打刻時メッセージ文字列を設定(下書)
				putPortalParameter(PRM_RECORD_END_STR, PfNameUtility.draft(mospParams));
			}
		}
	}
	
	/**
	 * 始業を打刻する。<br>
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	protected void recordStartWork() throws MospException {
		TimeRecordDtoInterface dto = getTimeRecordReferenceBean().findForKey(mospParams.getUser().getPersonalId(),
				getSystemDate(), RECODE_START_WORK);
		// 始業打刻
		String recordTime = DateUtility.getStringTimeAndSecond(getTimeRecordBean().recordStartWork());
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			if (dto == null) {
				// 打刻失敗メッセージ設定
				PfMessageUtility.addMessageConfirmError(mospParams);
				return;
			}
			// 打刻失敗メッセージ設定
			TimeMessageUtility.addMessageRecordStartTimeFailed(mospParams);
			return;
		}
		// 打刻メッセージ設定
		TimeMessageUtility.addMessageRecordStartWork(mospParams, recordTime);
	}
	
	/**
	 * 終業を打刻する。<br>
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	protected void recordEndWork() throws MospException {
		// 終業打刻
		String recordTime = DateUtility.getStringTimeAndSecond(getTimeRecordBean().recordEndWork());
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			// 打刻失敗メッセージ設定
			PfMessageUtility.addMessageConfirmError(mospParams);
			return;
		}
		// 打刻メッセージ設定
		TimeMessageUtility.addMessageRecordEndWork(mospParams, recordTime);
	}
	
	/**
	 * 休憩入を打刻する。<br>
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	protected void recordStartRest() throws MospException {
		// 休憩入打刻
		String recordTime = DateUtility.getStringTimeAndSecond(getTimeRecordBean().recordStartRest());
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			// 打刻失敗メッセージ設定
			PfMessageUtility.addMessageConfirmError(mospParams);
			return;
		}
		// 打刻メッセージ設定
		TimeMessageUtility.addMessageRecordStartRest(mospParams, recordTime);
	}
	
	/**
	 * 休憩戻を打刻する。<br>
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	protected void recordEndRest() throws MospException {
		// 休憩戻打刻
		String recordTime = DateUtility.getStringTimeAndSecond(getTimeRecordBean().recordEndRest());
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			// 打刻失敗メッセージ設定
			PfMessageUtility.addMessageConfirmError(mospParams);
			return;
		}
		// 打刻メッセージ設定
		TimeMessageUtility.addMessageRecordEndRest(mospParams, recordTime);
	}
	
	/**
	 * 定時終業を打刻する。<br>
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	protected void recordRegularEnd() throws MospException {
		// 定時終業打刻
		String recordTime = DateUtility.getStringTimeAndSecond(getTimeRecordBean().recordRegularEnd());
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			// 打刻失敗メッセージ設定
			PfMessageUtility.addMessageConfirmError(mospParams);
			return;
		}
		if (!mospParams.getMessageList().isEmpty()) {
			return;
		}
		// 打刻メッセージ設定
		TimeMessageUtility.addMessageRecordRegularEnd(mospParams, recordTime);
	}
	
	/**
	 * 残業有終業を打刻する。<br>
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	protected void recordOverEnd() throws MospException {
		// 定時終業打刻
		getTimeRecordBean().recordOverEnd();
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			// 打刻失敗メッセージ設定
			PfMessageUtility.addMessageConfirmError(mospParams);
		}
	}
	
	/**
	 * 出勤を打刻する。<br>
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	protected void recordRegularWork() throws MospException {
		// 定時終業打刻
		String recordTime = DateUtility.getStringTimeAndSecond(getTimeRecordBean().recordRegularWork());
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			// 打刻失敗メッセージ設定
			PfMessageUtility.addMessageConfirmError(mospParams);
			return;
		}
		// 打刻メッセージ設定
		TimeMessageUtility.addMessageRecordRegularWork(mospParams, recordTime);
	}
	
	/**
	 * 設定適用参照クラスを取得する。<br>
	 * @return 設定適用参照ユーティリティクラス
	 * @throws MospException インスタンスの取得に失敗した場合
	 */
	protected ApplicationReferenceBeanInterface getApplicationReferenceBean() throws MospException {
		return createBeanInstance(ApplicationReferenceBeanInterface.class);
	}
	
	/**
	 * 勤怠設定参照処理を取得する。<br>
	 * @return 勤怠設定参照処理
	 * @throws MospException インスタンスの取得に失敗した場合
	 */
	protected TimeSettingReferenceBeanInterface getTimeSettingRefer() throws MospException {
		return createBeanInstance(TimeSettingReferenceBeanInterface.class);
	}
	
	/**
	 * 打刻クラスを取得する。<br>
	 * @return 打刻クラス
	 * @throws MospException インスタンスの取得に失敗した場合
	 */
	protected TimeRecordBeanInterface getTimeRecordBean() throws MospException {
		return createBeanInstance(TimeRecordBeanInterface.class);
	}
	
	/**
	 * 打刻データ参照クラスを取得する。<br>
	 * @return 打刻データ参照クラス
	 * @throws MospException インスタンスの取得に失敗した場合
	 */
	protected TimeRecordReferenceBeanInterface getTimeRecordReferenceBean() throws MospException {
		return createBeanInstance(TimeRecordReferenceBeanInterface.class);
	}
	
}
