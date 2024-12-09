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
package jp.mosp.time.utils;

import java.util.Date;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.MessageUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.platform.utils.PfNameUtility;
import jp.mosp.platform.utils.TransStringUtility;
import jp.mosp.time.constant.TimeMessageConst;
import jp.mosp.time.dto.settings.CutoffErrorListDtoInterface;
import jp.mosp.time.entity.TimeDuration;

/**
 * メッセージに関するユーティリティクラス。<br>
 * <br>
 * 勤怠管理モジュールにおいてサーバ側プログラムで作成されるメッセージは、
 * 全てこのクラスを通じて作成される(予定)。<br>
 * <br>
 */
public class TimeMessageUtility {
	
	/**
	 * メッセージコード(半休申請時通知)。<br>
	 * 承認されると、既に下書されている休憩が削除されます。<br>
	 */
	public static final String		MSG_I_HALF_HOLIDAY_REQUEST_NOTICE	= "TMI0002";
	
	/**
	 * メッセージコード(打刻成功時)。<br>
	 * %1%に%2%を%3%しました。<br>
	 */
	public static final String		MSG_RECORD_TIME						= "TMI0004";
	
	/**
	 * メッセージコード(付与日数等の小数が不正である場合)。<br>
	 * %1%には0.5で割り切れる数値を入力してください。<br>
	 */
	protected static final String	MSG_W_INDIVISIBLE					= "TMW0219";
	
	/**
	 * メッセージコード(始終業時刻)。<br>
	 * 終業時刻は始業時刻よりも後になるよう入力してください。<br>
	 */
	public static final String		MSG_W_WORK_TIME_ORDER				= "TMW0236";
	
	/**
	 * メッセージコード(勤務時間外に設定されている場合)。<br>
	 * 勤務時間外に%1%が設定されています。%1%は勤務時間内に含めて設定してください。<br>
	 */
	public static final String		MSG_W_WORK_TIME_OUT_CHECK			= "TMW0237";
	
	/**
	 * メッセージコード(範囲重複)。<br>
	 * 入力された%1%で重複している箇所があります。%1%の範囲が重複しないように入力してください。<br>
	 */
	public static final String		MSG_W_RANGE_DUPLICATE				= "TMW0238";
	
	/**
	 * メッセージコード(申請不可)。<br>
	 * 取得期限が切れています。%1%を選択し直してください。<br>
	 */
	public static final String		MSG_EXPIRED_LIMIT					= "TMW0244";
	
	/**
	 * メッセージコード(申請不可)。<br>
	 * %1%は%2%できません。%3%を選択し直してください。<br>
	 */
	public static final String		MSG_CAN_NOT_REQUEST					= "TMW0245";
	
	/**
	 * メッセージコード(設定適用不備)。<br>
	 * %1%には%2%が設定されていません。各種設定の登録と適用範囲の設定を行う必要があります。<br>
	 */
	public static final String		MSG_W_APPLICATION_DEFECT			= "TMW0246";
	
	/**
	 * メッセージコード(勤務時間外設定)。<br>
	 * 勤務時間外に%1%が設定されています。勤務時間内に含めて入力してください。<br>
	 */
	public static final String		MSG_W_PROTRUDE_FROM_WORK_TIME		= "TMW0247";
	
	/**
	 * メッセージコード(休暇残数不足)。<br>
	 * %1%の申請可能な%2%数を超過しています。休暇年月日または休暇種別を選択し直してください。<br>
	 */
	public static final String		MSG_W_SHORT_HOLIDAY_REMAIN			= "TMW0254";
	
	/**
	 * メッセージコード(休暇未付与)。<br>
	 * %1%は付与されていません。休暇年月日または休暇種別を選択し直してください。<br>
	 */
	public static final String		MSG_W_HOLIDAY_NOT_GRANTED			= "TMW0259";
	
	/**
	 * メッセージコード(申請未承認)。<br>
	 * %1%は%2%の承認が完了していません。下書を行うか承認がされてから申請を行ってください。<br>
	 */
	public static final String		MSG_W_REQUEST_NOT_COMPLETED			= "TMW0267";
	
	/**
	 * メッセージコード(終了が開始よりも前の場合)。<br>
	 * %1%終了は%2%開始より後となるようにしてください。<br>
	 */
	public static final String		MSG_W_END_BEFORE_START				= "TMW0280";
	
	/**
	 * 連続休暇申請期間中に%1%できません。%1%したい場合は管理者に連絡してください。<br>
	 */
	public static final String		MSG_W_DUPLICATE_HOLIDAYS			= "TMW0288";
	
	/**
	 * メッセージコード(既に登録されているため処理できなかった場合)。<br>
	 * %1%は既に%2%が登録されているため、%3%できません。勤怠詳細を確認してください。<br>
	 */
	public static final String		MSG_ALREADY_RECORDED				= "TMW0301";
	
	/**
	 * メッセージコード(開始時刻が登録されていないため処理できなかった場合)。<br>
	 * %1%の%2%は、%3%が登録されていないため%4%できません。勤怠詳細を確認してください。<br>
	 */
	protected static final String	MSG_START_NOT_RECORDED				= "TMW0302";
	
	/**
	 * メッセージコード(限度を超えているため処理できなかった場合)。<br>
	 * %1%の%2%は、限度を超えるため%3%できません。勤怠詳細を確認してください。<br>
	 */
	protected static final String	MSG_W_OVER_LIMIT					= "TMW0303";
	
	/**
	 * メッセージコード(時間範囲外)。<br>
	 * %1%は%2%内で入力してください。<br>
	 */
	public static final String		MSG_W_TIME_OUT_OF_RANGE				= "TMW0310";
	
	/**
	 * メッセージコード(時間帯が重複している場合)。<br>
	 * %1%は%2%と%3%の時間帯が重複しています。<br>
	 */
	public static final String		MSG_W_TIME_OVERLAP1					= "TMW0312";
	
	/**
	 * メッセージコード(時間帯が重複している場合)。<br>
	 * %1%と%2%の時間帯(%3%～%4%)が重複しています。<br>
	 */
	public static final String		MSG_W_TIME_OVERLAP2					= "TMW0314";
	
	/**
	 * メッセージコード(先に取り下げる必要がある場合)。<br>
	 * 先に%1%を取り下げてから%2%してください。<br>
	 */
	public static final String		MSG_W_DO_AFTER_WITHDRAW				= "TMW0316";
	
	/**
	 * メッセージコード(全休に対して前年度の有給休暇が半日残っている場合)。<br>
	 * 前年度分の有給休暇が0.5日残っています。全休を申請する場合は半日ずつ申請してください。<br>
	 */
	public static final String		MSG_W_PREVIOUS_PAID_HOLIDAY_REMAIN	= "TMW0317";
	
	/**
	 * メッセージコード(集計時エラー内容)。<br>
	 * %1%の%2%が%3%です。<br>
	 */
	protected static final String	MSG_CUTOFF_ERROR					= "TMW0318";
	
	/**
	 * メッセージコード(時短勤務の時刻の境界が不正な場合)。<br>
	 * %1%の%2%には%3%と同一の時刻を入力してください。<br>
	 */
	protected static final String	MSG_W_SHORT_TIME_BOUNDARY			= "TMW0321";
	
	/**
	 * メッセージコード(時短勤務区分の組合せが不正な場合)。<br>
	 * 時短時間1で無給を設定した場合、時短時間2でも必ず無給を設定するようにしてください。<br>
	 */
	protected static final String	MSG_W_SHORT_TYPE_PAIR				= "TMW0322";
	
	/**
	 * メッセージコード(遅刻か早退があるためポータルからの自己承認ができない場合)。<br>
	 * 遅刻か早退があるため、自己承認ができません。勤怠詳細から勤怠を申請してください。<br>
	 */
	protected static final String	MSG_W_SELF_APPROVE_FAILED			= "TMW0323";
	
	/**
	 * メッセージコード(別項目が設定されていることにより登録不可となる場合)。<br>
	 * %1%が%2%の場合、登録できません。<br>
	 */
	protected static final String	MSG_W_NOT_REGIST_FOR_ANOTHER_ITEM	= "TMW0330";
	
	/**
	 * メッセージコード(有給休暇を削除する際、休暇申請が存在した場合。)<br>
	 * 付与日：%1%は休暇年月日：%1%に休暇申請を申請しているため削除できません。休暇申請を取下してください。<br>
	 */
	public static final String		MSG_EXIST_HOLIDAY_REQUEST			= "TMW0331";
	
	/**
	 * メッセージコード(有給休暇を削除する際、有給休暇手動付与情報が存在した場合。)<br>
	 * 付与日：%1%は有効日：%2%に有給休暇手動付与を作成しているため削除できません。<br>
	 */
	public static final String		MSG_EXIST_PAID_HOLIDAY_TRANSACTION	= "TMW0332";
	
	/**
	 * メッセージコード(勤務形態時間外)<br>
	 * %1%は勤務形態の時間内(%2%～%3%)に申請してください。<br>
	 */
	public static final String		MSG_W_OUT_OF_WOKR_TYP_TIME			= "TMW0340";
	
	/**
	 * メッセージコード（休日出勤時、休憩時間が0時間の場合の入力確認）<br>
	 * 休憩を入力してください。このまま申請する場合はキャンセルを押してください。<br>
	 */
	public static final String		MSG_REST_TIME_CHECK					= "TMW0343";
	
	/**
	 * メッセージコード（休日出勤時、休憩時間が0時間の場合）<br>
	 * 勤務時間が6時間を超えています。休憩を入力してください。<br>
	 */
	public static final String		MSG_REST_TIME_CHECK_ERROR			= "TMW0344";
	
	/**
	 * メッセージコード(有休取得情報出力時に分割付与等の可能性が有る場合。)<br>
	 * ダブルトラックまたは分割付与の可能性あり。<br>
	 */
	public static final String		MSG_W_PAID_HOLIDAY_SPLIT			= "TMW0342";
	
	/**
	 * メッセージコード(半日休暇と時間単位休暇の重複)<br>
	 * 入力された半休と時間休で重複している箇所があります。半休と時間休を同日に取得する場合、時間休は半休取得時の勤務形態の時間内(%1%)に申請してください。<br>
	 */
	public static final String		MSG_W_HALF_AND_HOULY_HOLIDAY		= "TMW0345";
	
	/**
	* メッセージコード(表示設定説明)。<br>
	* %1%の「%2%」の表示欄に反映される時間です。<br>
	*/
	public static final String		MSG_DISPLAY_SETTING_DESCRIPTION		= "TMI0011";
	
	/**
	 * メッセージコード(色設定説明)。<br>
	 * この時間を超えると、%1%の「%2%」に%3%色で時間が表示されます。<br>
	 */
	public static final String		MSG_COLOR_SETTING_DESCRIPTION		= "TMI0012";
	
	
	/**
	 * 他クラスからのインスタンス化を防止する。<br>
	 */
	private TimeMessageUtility() {
		// 処理無し
	}
	
	/**
	 * 集計しました。
	 * @param mospParams MosP処理情報
	 */
	public static void addMessageTotalSucceed(MospParams mospParams) {
		PfMessageUtility.addMessageSucceed(mospParams, getNameTotal(mospParams));
	}
	
	/**
	 * 集計できませんでした。エラー内容を確認の上、再度処理を行ってください。
	 * @param mospParams MosP処理情報
	 */
	public static void addMessageTotalFailed(MospParams mospParams) {
		PfMessageUtility.addMessageFailed(mospParams, getNameTotal(mospParams));
	}
	
	/**
	 * %1%の%2%が%3%です。(TMW0318)<br>
	 * 例：2013/05/01の勤怠が未申請です。(TMW0318)<br>
	 * @param mospParams MosP処理情報
	 * @param dto        集計時エラー内容情報
	 */
	public static void addErrorCutoff(MospParams mospParams, CutoffErrorListDtoInterface dto) {
		String date = DateUtility.getStringDate(dto.getDate());
		mospParams.addErrorMessage(MSG_CUTOFF_ERROR, date, dto.getType(), dto.getState());
	}
	
	/**
	 * itemNameが設定されていません。(パンくず名称)を利用するためには人事管理で設定を行う必要があります。(TMW0262)
	 * @param mospParams MosP処理情報
	 * @param itemName   項目名
	 */
	public static void addErrorUnsetHumanInfo(MospParams mospParams, String itemName) {
		// 最新のパンくず名称(表示している画面の名称)を取得
		String topicPathName = MospUtility.getTopicPathName(mospParams);
		// MosP処理情報にエラーメッセージを追加
		MessageUtility.addErrorMessage(mospParams, TimeMessageConst.MSG_UNSETTING, itemName, topicPathName,
				PfNameUtility.menuHumanManage(mospParams));
	}
	
	/**
	 * 時間単位取得が設定されていません。時間休を利用するためには有給休暇設定で設定を行う必要があります。(TMW0262)
	 * @param mospParams MosP処理情報
	 * @param row        対象行インデックス
	 */
	public static void addErrorUnsetHorlyPaidHoliday(MospParams mospParams, Integer row) {
		MessageUtility.addErrorMessage(mospParams, TimeMessageConst.MSG_UNSETTING, row,
				TimeNamingUtility.hourlyPaidHolidayFlag(mospParams), TimeNamingUtility.hourlyHoliday(mospParams),
				TimeNamingUtility.paidHolidaySetting(mospParams));
	}
	
	/**
	 * year年month月は既に月次処理が行われています。年月を変更してください。(TMW0272)
	 * @param mospParams MosP処理情報
	 * @param year       年
	 * @param month      月
	 */
	public static void addErrorTheMonthIsTighten(MospParams mospParams, int year, int month) {
		String rep = year + getNameYear(mospParams) + month + getNameMonth(mospParams);
		mospParams.addErrorMessage(TimeMessageConst.MSG_MONTHLY_TREATMENT, rep, getNameYearMonth(mospParams));
	}
	
	/**
	 * 打刻できませんでした。エラー内容を確認ください。
	 * @param mospParams MosP処理情報
	 */
	public static void addMessageRecordStartTimeFailed(MospParams mospParams) {
		PfMessageUtility.addMessageProcessJustFailed(mospParams, getNameRecordTime(mospParams));
	}
	
	/**
	 * 承認されると、既に下書されている休憩が削除されます。
	 * @param mospParams MosP処理情報
	 */
	public static void addHalfHolidayRequestNotice(MospParams mospParams) {
		MessageUtility.addMessage(mospParams, MSG_I_HALF_HOLIDAY_REQUEST_NOTICE);
	}
	
	/**
	 * 始業を打刻しました。<br>
	 * @param mospParams MosP処理情報
	 * @param recordTime 打刻時刻
	 */
	public static void addMessageRecordStartWork(MospParams mospParams, String recordTime) {
		mospParams.addMessage(MSG_RECORD_TIME, recordTime, getNameStartWork(mospParams), getNameRecordTime(mospParams));
	}
	
	/**
	 * 終業を打刻しました。<br>
	 * @param mospParams MosP処理情報
	 * @param recordTime 打刻時刻
	 */
	public static void addMessageRecordEndWork(MospParams mospParams, String recordTime) {
		mospParams.addMessage(MSG_RECORD_TIME, recordTime, getNameEndWork(mospParams), getNameRecordTime(mospParams));
	}
	
	/**
	 * 休憩入りを打刻しました。<br>
	 * @param mospParams MosP処理情報
	 * @param recordTime 打刻時刻
	 */
	public static void addMessageRecordStartRest(MospParams mospParams, String recordTime) {
		mospParams.addMessage(MSG_RECORD_TIME, recordTime, getNameStartRest(mospParams), getNameRecordTime(mospParams));
	}
	
	/**
	 * 休憩戻りを打刻しました。<br>
	 * @param mospParams MosP処理情報
	 * @param recordTime 打刻時刻
	 */
	public static void addMessageRecordEndRest(MospParams mospParams, String recordTime) {
		mospParams.addMessage(MSG_RECORD_TIME, recordTime, getNameEndRest(mospParams), getNameRecordTime(mospParams));
	}
	
	/**
	 * 私用外出入りを打刻しました。<br>
	 * @param mospParams MosP処理情報
	 * @param recordTime 打刻時刻
	 */
	public static void addMessageRecordStartPrivate(MospParams mospParams, String recordTime) {
		mospParams.addMessage(MSG_RECORD_TIME, recordTime, TimeNamingUtility.startPrivateGoOut(mospParams),
				getNameRecordTime(mospParams));
	}
	
	/**
	 * 私用外出戻りを打刻しました。<br>
	 * @param mospParams MosP処理情報
	 * @param recordTime 打刻時刻
	 */
	public static void addMessageRecordEndPrivate(MospParams mospParams, String recordTime) {
		mospParams.addMessage(MSG_RECORD_TIME, recordTime, TimeNamingUtility.endPrivateGoOut(mospParams),
				getNameRecordTime(mospParams));
	}
	
	/**
	 * 定時終業を打刻しました。<br>
	 * @param mospParams MosP処理情報
	 * @param recordTime 打刻時刻
	 */
	public static void addMessageRecordRegularEnd(MospParams mospParams, String recordTime) {
		mospParams.addMessage(MSG_RECORD_TIME, recordTime, getNameRegularEnd(mospParams),
				getNameRecordTime(mospParams));
	}
	
	/**
	 * 出勤を打刻しました。<br>
	 * @param mospParams MosP処理情報
	 * @param recordTime 打刻時刻
	 */
	public static void addMessageRecordRegularWork(MospParams mospParams, String recordTime) {
		mospParams.addMessage(MSG_RECORD_TIME, recordTime, getNameRegularWork(mospParams),
				getNameRecordTime(mospParams));
	}
	
	/**
	 * 終業時刻を更新しました。<br>
	 * @param mospParams MosP処理情報
	 * @param recordTime 打刻時刻
	 */
	public static void addMessageUpdateEndWork(MospParams mospParams, String recordTime) {
		mospParams.addMessage(MSG_RECORD_TIME, recordTime, getNameEndWorkTime(mospParams), getNameUpdate(mospParams));
	}
	
	/**
	 * YYYY/MM/DDは既に始業が登録されているため、打刻できません。勤怠詳細を確認してください。(TMW0301)<br>
	 * @param mospParams MosP処理情報
	 * @param targetDate 対象日
	 */
	public static void addErrorStartWorkAlreadyRecorded(MospParams mospParams, Date targetDate) {
		mospParams.addErrorMessage(MSG_ALREADY_RECORDED, DateUtility.getStringDate(targetDate),
				getNameStartWork(mospParams), getNameRecordTime(mospParams));
	}
	
	/**
	 * YYYY/MM/DDは既に休憩入りが登録されているため、打刻できません。勤怠詳細を確認してください。(TMW0301)<br>
	 * @param mospParams MosP処理情報
	 * @param targetDate 対象日
	 */
	public static void addErrorStartRestAlreadyRecorded(MospParams mospParams, Date targetDate) {
		mospParams.addErrorMessage(MSG_ALREADY_RECORDED, DateUtility.getStringDate(targetDate),
				getNameStartRest(mospParams), getNameRecordTime(mospParams));
	}
	
	/**
	 * YYYY/MM/DDは既に私用外出入りが登録されているため、打刻できません。勤怠詳細を確認してください。(TMW0301)<br>
	 * @param mospParams MosP処理情報
	 * @param targetDate 対象日
	 */
	public static void addErrorStartPrivateAlreadyRecorded(MospParams mospParams, Date targetDate) {
		mospParams.addErrorMessage(MSG_ALREADY_RECORDED, DateUtility.getStringDate(targetDate),
				TimeNamingUtility.startPrivateGoOut(mospParams), getNameRecordTime(mospParams));
	}
	
	/**
	 * YYYY/MM/DDは既に終業が登録されているため、打刻できません。勤怠詳細を確認してください。(TMW0301)<br>
	 * @param mospParams MosP処理情報
	 * @param targetDate 対象日
	 */
	public static void addErrorEndWorkAlreadyRecorded(MospParams mospParams, Date targetDate) {
		mospParams.addErrorMessage(MSG_ALREADY_RECORDED, DateUtility.getStringDate(targetDate),
				getNameEndWork(mospParams), getNameRecordTime(mospParams));
	}
	
	/**
	 * YYYY/MM/DDの休憩戻りは、休憩入りが登録されていないため打刻できません。勤怠詳細を確認してください。(TMW0302)<br>
	 * @param mospParams MosP処理情報
	 * @param targetDate 対象日
	 */
	public static void addErrorStartRestNotRecorded(MospParams mospParams, Date targetDate) {
		mospParams.addErrorMessage(MSG_START_NOT_RECORDED, DateUtility.getStringDate(targetDate),
				getNameEndRest(mospParams), getNameStartRest(mospParams), getNameRecordTime(mospParams));
	}
	
	/**
	 * YYYY/MM/DDの私用外出戻りは、私用外出入りが登録されていないため打刻できません。勤怠詳細を確認してください。(TMW0302)<br>
	 * @param mospParams MosP処理情報
	 * @param targetDate 対象日
	 */
	public static void addErrorStartPrivateNotRecorded(MospParams mospParams, Date targetDate) {
		mospParams.addErrorMessage(MSG_START_NOT_RECORDED, DateUtility.getStringDate(targetDate),
				TimeNamingUtility.endPrivateGoOut(mospParams), TimeNamingUtility.startPrivateGoOut(mospParams),
				getNameRecordTime(mospParams));
	}
	
	/**
	 * YYYY/MM/DDの終業は、休憩戻りが登録されていないため打刻できません。勤怠詳細を確認してください。(TMW0302)<br>
	 * @param mospParams MosP処理情報
	 * @param targetDate 対象日
	 */
	public static void addErrorEndRestNotRecorded(MospParams mospParams, Date targetDate) {
		mospParams.addErrorMessage(MSG_START_NOT_RECORDED, DateUtility.getStringDate(targetDate),
				getNameEndWork(mospParams), getNameEndRest(mospParams), getNameRecordTime(mospParams));
	}
	
	/**
	 * YYYY/MM/DDの終業は、私用外出戻りが登録されていないため打刻できません。勤怠詳細を確認してください。(TMW0302)<br>
	 * @param mospParams MosP処理情報
	 * @param targetDate 対象日
	 */
	public static void addErrorEndPrivateNotRecorded(MospParams mospParams, Date targetDate) {
		mospParams.addErrorMessage(MSG_START_NOT_RECORDED, DateUtility.getStringDate(targetDate),
				getNameEndWork(mospParams), TimeNamingUtility.endPrivateGoOut(mospParams),
				getNameRecordTime(mospParams));
	}
	
	/**
	 * YYYY/MM/DDのprocessは、始業が登録されていないため打刻できません。勤怠詳細を確認してください。(TMW0302)<br>
	 * 例：2013/05/01の終業は、始業が登録されていないため打刻できません。勤怠詳細を確認してください。(TMW0302)<br>
	 * @param mospParams MosP処理情報
	 * @param targetDate 対象日
	 * @param process    打刻対象処理
	 */
	public static void addErrorStratWorkNotRecorded(MospParams mospParams, Date targetDate, String process) {
		mospParams.addErrorMessage(MSG_START_NOT_RECORDED, DateUtility.getStringDate(targetDate), process,
				getNameStartWork(mospParams), getNameRecordTime(mospParams));
	}
	
	/**
	 * YYYY/MM/DDの休憩入りは、限度を超えるため打刻できません。勤怠詳細を確認してください。(TMW0303)<br>
	 * @param mospParams MosP処理情報
	 * @param targetDate 対象日
	 */
	public static void addErrorRestOverLimit(MospParams mospParams, Date targetDate) {
		mospParams.addErrorMessage(MSG_W_OVER_LIMIT, DateUtility.getStringDate(targetDate),
				getNameStartRest(mospParams), getNameRecordTime(mospParams));
	}
	
	/**
	 * YYYY/MM/DDの私用外出休憩入りは、限度を超えるため打刻できません。勤怠詳細を確認してください。(TMW0303)<br>
	 * @param mospParams MosP処理情報
	 * @param targetDate 対象日
	 */
	public static void addErrorPrivateOverLimit(MospParams mospParams, Date targetDate) {
		mospParams.addErrorMessage(MSG_W_OVER_LIMIT, DateUtility.getStringDate(targetDate),
				TimeNamingUtility.startPrivateGoOut(mospParams), getNameRecordTime(mospParams));
	}
	
	/**
	 * %1%の時間と分をそれぞれ正しく入力してください。
	 * @param mospParams MosP処理情報
	 * @param targetName 確認対象名称
	 */
	public static void addErrorTimeFormat(MospParams mospParams, String targetName) {
		mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_FORMAT_CHECK, targetName);
	}
	
	/**
	 * %1%は%2%と%3%の時間帯が重複しています。(TMW0312)<br>
	 * @param mospParams MosP処理情報
	 * @param targetDate 対象日
	 * @param timeName1  時間名称1
	 * @param timeName2  時間名称2
	 */
	public static void addErrorOverlap1(MospParams mospParams, Date targetDate, String timeName1, String timeName2) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_TIME_OVERLAP1, DateUtility.getStringDateAndDay(targetDate),
				timeName1, timeName2);
	}
	
	/**
	 * 休憩と残前休憩の時間帯(%3%～%4%)が重複しています。(TMW0314)<br>
	 * @param mospParams MosP処理情報
	 * @param overlap    重複時間間隔
	 */
	public static void addErrorOverBefRestOverlapWithRest(MospParams mospParams, TimeDuration overlap) {
		addErrorOverlap2(mospParams, TimeNamingUtility.rest(mospParams),
				TimeNamingUtility.overtimeBeforeRest(mospParams), overlap);
	}
	
	/**
	 * 公用外出と残前休憩の時間帯(%3%～%4%)が重複しています。(TMW0314)<br>
	 * @param mospParams MosP処理情報
	 * @param overlap    重複時間間隔
	 */
	public static void addErrorOverBefRestOverlapWithPublic(MospParams mospParams, TimeDuration overlap) {
		addErrorOverlap2(mospParams, TimeNamingUtility.getPublicGoOut(mospParams),
				TimeNamingUtility.overtimeBeforeRest(mospParams), overlap);
	}
	
	/**
	 * 私用外出と残前休憩の時間帯(%3%～%4%)が重複しています。(TMW0314)<br>
	 * @param mospParams MosP処理情報
	 * @param overlap    重複時間間隔
	 */
	public static void addErrorOverBefRestOverlapWithPrivate(MospParams mospParams, TimeDuration overlap) {
		addErrorOverlap2(mospParams, TimeNamingUtility.getPrivateGoOut(mospParams),
				TimeNamingUtility.overtimeBeforeRest(mospParams), overlap);
	}
	
	/**
	 * 休憩と残業休憩の時間帯(%3%～%4%)が重複しています。(TMW0314)<br>
	 * @param mospParams MosP処理情報
	 * @param overlap    重複時間間隔
	 */
	public static void addErrorOvertimeRestOverlapWithRest(MospParams mospParams, TimeDuration overlap) {
		addErrorOverlap2(mospParams, TimeNamingUtility.rest(mospParams), TimeNamingUtility.overtimeRest(mospParams),
				overlap);
	}
	
	/**
	 * 休憩と残業休憩の時間帯(%3%～%4%)が重複しています。(TMW0314)<br>
	 * @param mospParams MosP処理情報
	 * @param overlap    重複時間間隔
	 */
	public static void addErrorOvertimeRestOverlapWithPublic(MospParams mospParams, TimeDuration overlap) {
		addErrorOverlap2(mospParams, TimeNamingUtility.getPublicGoOut(mospParams),
				TimeNamingUtility.overtimeRest(mospParams), overlap);
	}
	
	/**
	 * 休憩と残業休憩の時間帯(%3%～%4%)が重複しています。(TMW0314)<br>
	 * @param mospParams MosP処理情報
	 * @param overlap    重複時間間隔
	 */
	public static void addErrorOvertimeRestOverlapWithPrivate(MospParams mospParams, TimeDuration overlap) {
		addErrorOverlap2(mospParams, TimeNamingUtility.getPrivateGoOut(mospParams),
				TimeNamingUtility.overtimeRest(mospParams), overlap);
	}
	
	/**
	 * %1%と%2%の時間帯(%3%～%4%)が重複しています。(TMW0314)<br>
	 * @param mospParams MosP処理情報
	 * @param timeName1  時間名称1
	 * @param timeName2  時間名称2
	 * @param overlap    重複時間間隔
	 */
	public static void addErrorOverlap2(MospParams mospParams, String timeName1, String timeName2,
			TimeDuration overlap) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_TIME_OVERLAP2, timeName1, timeName2,
				TimeUtility.getStringColonTime(mospParams, overlap.getStartTime()),
				TimeUtility.getStringColonTime(mospParams, overlap.getEndTime()));
	}
	
	/**
	 * 先に%1%を取り下げてから%2%してください。(TMW0316)<br>
	 * @param mospParams      MosP処理情報
	 * @param applicationName 申請名称
	 * @param processName     処理名称
	 */
	public static void addErrorDoAfterRocess(MospParams mospParams, String applicationName, String processName) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_DO_AFTER_WITHDRAW, applicationName, processName);
	}
	
	/**
	 * 先に%1%の%2%を取り下げてから%1%してください。(TMW0316)<br>
	 * @param mospParams      MosP処理情報
	 * @param dateName        日付名称
	 * @param applicationName 申請名称
	 */
	public static void addErrorCancelAfterWithdraw(MospParams mospParams, String dateName, String applicationName) {
		// 置換文字列を準備
		StringBuilder sb = new StringBuilder();
		sb.append(dateName).append(PfNameUtility.of(mospParams)).append(applicationName);
		// エラーメッセージを設定
		addErrorDoAfterRocess(mospParams, sb.toString(), PfNameUtility.release(mospParams));
	}
	
	/**
	 * 先に出勤日の%1%を取り下げてから解除してください。(TMW0316)<br>
	 * @param mospParams      MosP処理情報
	 * @param applicationName 申請名称
	 */
	public static void addErrorCancelAfterWithdrawForWorkDay(MospParams mospParams, String applicationName) {
		addErrorCancelAfterWithdraw(mospParams, PfNameUtility.goingWorkDay(mospParams), applicationName);
	}
	
	/**
	 * 先に振替日の%1%を取り下げてから解除してください。(TMW0316)<br>
	 * @param mospParams      MosP処理情報
	 * @param applicationName 申請名称
	 */
	public static void addErrorCancelAfterWithdrawForSubstituteDay(MospParams mospParams, String applicationName) {
		addErrorCancelAfterWithdraw(mospParams, TimeNamingUtility.substituteDay(mospParams), applicationName);
	}
	
	/**
	 * 前年度分の有給休暇が0.5日残っています。全休を申請する場合は半日ずつ申請してください。(TMW0317)<br>
	 * @param mospParams MosP処理情報
	 * @param row        対象行インデックス
	 */
	public static void addErrorPreviousPaidHolidayRemain(MospParams mospParams, Integer row) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_PREVIOUS_PAID_HOLIDAY_REMAIN, row);
	}
	
	/**
	 * 時短時間1の開始時刻には始業時刻と同一の時刻を入力してください。(TMW0320)<br>
	 * @param mospParams MosP処理情報
	 */
	public static void addErrorShort1TimeBoundary(MospParams mospParams) {
		mospParams.addErrorMessage(MSG_W_SHORT_TIME_BOUNDARY, getNameShort1Time(mospParams),
				getNameStartTime(mospParams), getNameStartWorkTime(mospParams));
	}
	
	/**
	 * 時短時間2の終了時刻には終業時刻と同一の時刻を入力してください。(TMW0321)<br>
	 * @param mospParams MosP処理情報
	 */
	public static void addErrorShort2TimeBoundary(MospParams mospParams) {
		mospParams.addErrorMessage(MSG_W_SHORT_TIME_BOUNDARY, getNameShort2Time(mospParams), getNameEndTime(mospParams),
				getNameEndWorkTime(mospParams));
	}
	
	/**
	 * 時短時間1で無給を設定した場合、時短時間2でも必ず無給を設定するようにしてください。(TMW0322)<br>
	 * @param mospParams MosP処理情報
	 */
	public static void addErrorShortTypePair(MospParams mospParams) {
		mospParams.addErrorMessage(MSG_W_SHORT_TYPE_PAIR);
	}
	
	/**
	 * 遅刻か早退があるため、自己承認ができません。勤怠詳細から勤怠を申請してください。(TMW0323)<br>
	 * @param mospParams MosP処理情報
	 */
	public static void addErrorSelfApproveFailed(MospParams mospParams) {
		mospParams.addErrorMessage(MSG_W_SELF_APPROVE_FAILED);
	}
	
	/**
	 * %1%には設定適用が設定されていません。各種設定の登録と適用範囲の設定を行う必要があります。<br>
	 * @param mospParams MosP処理情報
	 * @param targetDate 対象日
	 */
	public static void addErrorApplicationDefect(MospParams mospParams, Date targetDate) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_APPLICATION_DEFECT, DateUtility.getStringDate(targetDate),
				TimeNamingUtility.getApplication(mospParams));
	}
	
	/**
	 * %1%には勤怠設定が設定されていません。各種設定の登録と適用範囲の設定を行う必要があります。<br>
	 * @param mospParams MosP処理情報
	 * @param targetDate 対象日
	 */
	public static void addErrorTimeSettingDefect(MospParams mospParams, Date targetDate) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_APPLICATION_DEFECT, DateUtility.getStringDate(targetDate),
				TimeNamingUtility.timeSetting(mospParams));
	}
	
	/**
	 * %1%には勤務形態が設定されていません。各種設定の登録と適用範囲の設定を行う必要があります。<br>
	 * @param mospParams MosP処理情報
	 * @param targetDate 対象日
	 */
	public static void addErrorWorkTypeDefect(MospParams mospParams, Date targetDate) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_APPLICATION_DEFECT, DateUtility.getStringDate(targetDate),
				TimeNamingUtility.workType(mospParams));
	}
	
	/**
	 * %1%にはカレンダが設定されていません。各種設定の登録と適用範囲の設定を行う必要があります。<br>
	 * @param mospParams MosP処理情報
	 * @param targetDate 対象日
	 */
	public static void addErrorScheduleDefect(MospParams mospParams, Date targetDate) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_APPLICATION_DEFECT, DateUtility.getStringDate(targetDate),
				TimeNamingUtility.calendar(mospParams));
	}
	
	/**
	 * %1%には有給休暇設定が設定されていません。各種設定の登録と適用範囲の設定を行う必要があります。<br>
	 * @param mospParams MosP処理情報
	 * @param targetDate 対象日
	 * @param row        対象行インデックス
	 */
	public static void addErrorPaidHolidayDefect(MospParams mospParams, Date targetDate, Integer row) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_APPLICATION_DEFECT, row, DateUtility.getStringDate(targetDate),
				TimeNamingUtility.paidHolidaySetting(mospParams));
	}
	
	/**
	 * 勤務時間外に%1%が設定されています。勤務時間内に含めて入力してください。(TMW0247)<br>
	 * @param mospParams MosP処理情報
	 * @param timeName   時間名称
	 */
	public static void addErrorProtrudeFromWorkTime(MospParams mospParams, String timeName) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_PROTRUDE_FROM_WORK_TIME, timeName);
	}
	
	/**
	 * 有給休暇の申請可能な日数を超過しています。休暇年月日または休暇種別を選択し直してください。(TMW0254)<br>
	 * @param mospParams MosP処理情報
	 * @param row        対象行インデックス
	 */
	public static void addErrorShortPaidHolidayRemainDays(MospParams mospParams, Integer row) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_SHORT_HOLIDAY_REMAIN, row,
				TimeNamingUtility.paidHoliday(mospParams), PfNameUtility.day(mospParams));
	}
	
	/**
	 * 有給休暇の申請可能な時間数を超過しています。休暇年月日または休暇種別を選択し直してください。(TMW0254)<br>
	 * @param mospParams MosP処理情報
	 * @param row        対象行インデックス
	 */
	public static void addErrorShortPaidHolidayRemainHours(MospParams mospParams, Integer row) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_SHORT_HOLIDAY_REMAIN, row,
				TimeNamingUtility.paidHoliday(mospParams), PfNameUtility.time(mospParams));
	}
	
	/**
	 * ストック休暇の申請可能な日数を超過しています。休暇年月日または休暇種別を選択し直してください。(TMW0254)<br>
	 * @param mospParams MosP処理情報
	 * @param row        対象行インデックス
	 */
	public static void addErrorShortStockHolidayRemainDays(MospParams mospParams, Integer row) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_SHORT_HOLIDAY_REMAIN, row,
				TimeNamingUtility.stockHoliday(mospParams), PfNameUtility.day(mospParams));
	}
	
	/**
	 * %1%の申請可能な日数を超過しています。休暇年月日または休暇種別を選択し直してください。(TMW0254)<br>
	 * @param mospParams MosP処理情報
	 * @param holidayName 休暇名称
	 * @param row        対象行インデックス
	 */
	public static void addErrorShortHolidayRemainDays(MospParams mospParams, String holidayName, Integer row) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_SHORT_HOLIDAY_REMAIN, row, holidayName,
				PfNameUtility.day(mospParams));
	}
	
	/**
	 * %1%の申請可能な時間数を超過しています。休暇年月日または休暇種別を選択し直してください。(TMW0254)<br>
	 * @param mospParams MosP処理情報
	 * @param holidayName 休暇名称
	 * @param row        対象行インデックス
	 */
	public static void addErrorShortHolidayRemainHours(MospParams mospParams, String holidayName, Integer row) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_SHORT_HOLIDAY_REMAIN, row, holidayName,
				PfNameUtility.time(mospParams));
	}
	
	/**
	 * %1%は付与されていません。休暇年月日または休暇種別を選択し直してください。(TMW0259)<br>
	 * @param mospParams  MosP処理情報
	 * @param holidayName 休暇名称
	 * @param row        対象行インデックス
	 */
	public static void addErrorHolidayNotGranted(MospParams mospParams, String holidayName, Integer row) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_HOLIDAY_NOT_GRANTED, row, holidayName);
	}
	
	/**
	 * YYYY/MM/DDは%2%の承認が完了していません。下書を行うか承認がされてから申請を行ってください。(TMW0267)<br>
	 * @param mospParams  MosP処理情報
	 * @param requestDate 申請日
	 * @param requestName 申請名称
	 * @param row         対象行インデックス
	 */
	public static void addErrorRequestNotCompleted(MospParams mospParams, Date requestDate, String requestName,
			Integer row) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_REQUEST_NOT_COMPLETED, row,
				DateUtility.getStringDate(requestDate), requestName);
	}
	
	/**
	 * YYYY/MM/DDは残業申請の承認が完了していません。下書を行うか承認がされてから申請を行ってください。(TMW0267)<br>
	 * @param mospParams  MosP処理情報
	 * @param requestDate 申請日
	 */
	public static void addErrorOvertimeNotCompleted(MospParams mospParams, Date requestDate) {
		addErrorRequestNotCompleted(mospParams, requestDate, TimeNamingUtility.overtimeRequest(mospParams), null);
	}
	
	/**
	 * YYYY/MM/DDは時差出勤申請の承認が完了していません。下書を行うか承認がされてから申請を行ってください。(TMW0267)<br>
	 * @param mospParams  MosP処理情報
	 * @param requestDate 申請日
	 */
	public static void addErrorDifferenceNotCompleted(MospParams mospParams, Date requestDate) {
		addErrorRequestNotCompleted(mospParams, requestDate, TimeNamingUtility.differenceRequest(mospParams), null);
	}
	
	/**
	 * YYYY/MM/DDは休暇申請の承認が完了していません。下書を行うか承認がされてから申請を行ってください。(TMW0267)<br>
	 * @param mospParams  MosP処理情報
	 * @param requestDate 申請日
	 */
	public static void addErrorHolidayNotCompleted(MospParams mospParams, Date requestDate) {
		addErrorRequestNotCompleted(mospParams, requestDate, TimeNamingUtility.holidayRequest(mospParams), null);
	}
	
	/**
	 * YYYY/MM/DDは振替休日の承認が完了していません。下書を行うか承認がされてから申請を行ってください。(TMW0267)<br>
	 * @param mospParams  MosP処理情報
	 * @param requestDate 申請日
	 */
	public static void addErrorSubstituteNotCompleted(MospParams mospParams, Date requestDate) {
		addErrorRequestNotCompleted(mospParams, requestDate, TimeNamingUtility.substituteHoliday(mospParams), null);
	}
	
	/**
	 * YYYY/MM/DDは代休申請の承認が完了していません。下書を行うか承認がされてから申請を行ってください。(TMW0267)<br>
	 * @param mospParams  MosP処理情報
	 * @param requestDate 申請日
	 */
	public static void addErrorSubHolidayNotCompleted(MospParams mospParams, Date requestDate) {
		addErrorRequestNotCompleted(mospParams, requestDate, TimeNamingUtility.subHolidayRequest(mospParams), null);
	}
	
	/**
	 * YYYY/MM/DDは勤務形態変更申請の承認が完了していません。下書を行うか承認がされてから申請を行ってください。(TMW0267)<br>
	 * @param mospParams  MosP処理情報
	 * @param requestDate 申請日
	 */
	public static void addErrorWorkTypeChangeNotCompleted(MospParams mospParams, Date requestDate) {
		addErrorRequestNotCompleted(mospParams, requestDate, TimeNamingUtility.workTypeChangeRequest(mospParams), null);
	}
	
	/**
	 * YYYY/MM/DDは既に他の申請が行われています。出勤日を選択し直してください。(TMW0277)<br>
	 * @param mospParams MosP処理情報
	 * @param targetDate 対象日
	 */
	public static void addErrorWorkOnHolidayOtherRequest(MospParams mospParams, Date targetDate) {
		MessageUtility.addErrorMessage(mospParams, TimeMessageConst.MSG_REQUEST_CHECK_9,
				DateUtility.getStringDate(targetDate), PfNameUtility.goingWorkDay(mospParams));
	}
	
	/**
	 * %1%終了は%2%開始より後となるようにしてください。(TMW0280)<br>
	 * @param mospParams MosP処理情報
	 * @param fieldName    フィールド名
	 */
	public static void addErrorEndBeforeStart(MospParams mospParams, String fieldName) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_END_BEFORE_START, fieldName, fieldName);
	}
	
	/**
	 * 時短時間1終了は時短時間1開始より後となるようにしてください。(TMW0280)<br>
	 * @param mospParams MosP処理情報
	 */
	public static void addErrorShort1EndBeforeStart(MospParams mospParams) {
		addErrorEndBeforeStart(mospParams, getNameShort1Time(mospParams));
	}
	
	/**
	 * 時短時間2終了は時短時間2開始より後となるようにしてください。(TMW0280)<br>
	 * @param mospParams MosP処理情報
	 */
	public static void addErrorShort2EndBeforeStart(MospParams mospParams) {
		addErrorEndBeforeStart(mospParams, getNameShort2Time(mospParams));
	}
	
	/**
	 * 有効日終了は有効日開始より後となるようにしてください。(TMW0280)<br>
	 * @param mospParams MosP処理情報
	 */
	public static void addErrorActivateDateEndBeforeStart(MospParams mospParams) {
		addErrorEndBeforeStart(mospParams, PfNameUtility.activateDate(mospParams));
	}
	
	/**
	 * %1%には0.5で割り切れる数値を入力してください。(TMW0219)<br>
	 * @param mospParams MosP処理情報
	 * @param fieldName  フィールド名
	 * @param row        対象行インデックス
	 */
	public static void addErrorIndivisible(MospParams mospParams, String fieldName, Integer row) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_INDIVISIBLE, row, fieldName);
	}
	
	/**
	 * 終業時刻は始業時刻よりも後になるよう入力してください。(TMW0236)<br>
	 * @param mospParams MosP処理情報
	 * @param row        対象行インデックス
	 */
	public static void addErrorWorkTimeOrder(MospParams mospParams, Integer row) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_WORK_TIME_ORDER, row);
	}
	
	/**
	 * 終業時刻は始業時刻よりも後になるよう入力してください。(TMW0236)<br>
	 * @param mospParams MosP処理情報
	 */
	public static void addErrorWorkTimeOrder(MospParams mospParams) {
		addErrorWorkTimeOrder(mospParams, null);
	}
	
	/**
	 * 勤務時間外に時短時間1が設定されています。時短時間1は勤務時間内に含めて設定してください。(TMW0237)<br>
	 * @param mospParams MosP処理情報
	 */
	public static void addErrorShort1OutOfWorkTime(MospParams mospParams) {
		mospParams.addErrorMessage(MSG_W_WORK_TIME_OUT_CHECK, getNameShort1Time(mospParams));
	}
	
	/**
	 * 勤務時間外に時短時間2が設定されています。時短時間2は勤務時間内に含めて設定してください。(TMW0237)<br>
	 * @param mospParams MosP処理情報
	 */
	public static void addErrorShort2OutOfWorkTime(MospParams mospParams) {
		mospParams.addErrorMessage(MSG_W_WORK_TIME_OUT_CHECK, getNameShort2Time(mospParams));
	}
	
	/**
	 * 入力された%1%で重複している箇所があります。%1%の範囲が重複しないように入力してください。<br>(TMW0238)<br>
	 * @param mospParams MosP処理情報
	 * @param fieldName  フィールド名
	 */
	public static void addErrorRangeDuplicate(MospParams mospParams, String fieldName) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_RANGE_DUPLICATE, fieldName);
	}
	
	/**
	 * 入力された半休と時間休で重複している箇所があります。半休と時間休を同日に取得する場合、時間休は半休取得時の勤務形態の時間内(%1%)に申請してください。(TMW0345)<br>
	 * @param mospParams    MosP処理情報
	 * @param startWorkTime 始業時刻
	 * @param endWorkTime   終業時刻
	 * @param row           対象行インデックス
	 * @throws MospException 日付の変換に失敗した場合
	 */
	public static void addErrorHalfAndHoulyHolidayDuplicate(MospParams mospParams, Date startWorkTime, Date endWorkTime,
			Integer row) throws MospException {
		// 置換文字列を準備
		Date standatdDate = DateUtility.getDefaultTime();
		String rep = TransStringUtility.getHourColonMinuteTerm(mospParams, startWorkTime, endWorkTime, standatdDate);
		// エラーメッセージを設定
		MessageUtility.addErrorMessage(mospParams, MSG_W_HALF_AND_HOULY_HOLIDAY, row, rep);
	}
	
	/**
	 * 連続休暇申請期間中に振替出勤申請できません。振替出勤申請したい場合は管理者に連絡してください。(TMW0288)<br>
	 * @param mospParams MosP処理情報
	 */
	public static void addErrorSubstituteDuplicateHolidays(MospParams mospParams) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_DUPLICATE_HOLIDAYS,
				TimeNamingUtility.substituteWorkAppli(mospParams));
	}
	
	/**
	 * %1%は%2%内で入力してください。(TMW0310)
	 * @param mospParams MosP処理情報
	 * @param fieldName  フィールド名
	 * @param rangeName  範囲名
	 */
	public static void addErrorTimeOutOfRange(MospParams mospParams, String fieldName, String rangeName) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_TIME_OUT_OF_RANGE, fieldName, rangeName);
	}
	
	/**
	 * YYYY/MM/DDは休暇の申請が行われているので勤怠の申請及び下書を行うことはできません。(TMW0279)<br>
	 * @param mospParams MosP処理情報
	 * @param targetDate 対象日
	 * @param row        対象行インデックス
	 */
	public static void addErrorNotApplicableForHoliday(MospParams mospParams, Date targetDate, Integer row) {
		mospParams.addErrorMessage(TimeMessageConst.MSG_REQUEST_CHECK_10,
				getRowedFieldName(mospParams, DateUtility.getStringDate(targetDate), row),
				TimeNamingUtility.holiday(mospParams), getNameWorkManage(mospParams));
	}
	
	/**
	 * YYYY/MM/DDは振替休日の申請が行われているので振替出勤の申請及び下書を行うことはできません。(TMW0279)<br>
	 * @param mospParams MosP処理情報
	 * @param targetDate 対象日
	 */
	public static void addErrorSubstituteIterate(MospParams mospParams, Date targetDate) {
		mospParams.addErrorMessage(TimeMessageConst.MSG_REQUEST_CHECK_10, DateUtility.getStringDate(targetDate),
				TimeNamingUtility.substituteHoliday(mospParams), TimeNamingUtility.substituteWork(mospParams));
	}
	
	/**
	 * YYYY/MM/DDは既に勤怠申請が行われています。勤務日を選択し直してください。(TMW0240)<br>
	 * @param mospParams MosP処理情報
	 * @param targetDate 対象日
	 * @param row        対象行インデックス
	 */
	public static void addErrorAlreadyApplyWork(MospParams mospParams, Date targetDate, Integer row) {
		mospParams.addErrorMessage(TimeMessageConst.MSG_REQUEST_CHECK_1,
				getRowedFieldName(mospParams, DateUtility.getStringDate(targetDate), row),
				getNameWorkManage(mospParams), getNameWorkDate(mospParams));
	}
	
	/**
	 * YYYY/MM/DDは既に勤怠申請が行われています。出勤日を選択し直してください。(TMW0240)<br>
	 * @param mospParams MosP処理情報
	 * @param targetDate 対象日
	 */
	public static void addErrorAlreadyApplyWorkForWorkOnHoliday(MospParams mospParams, Date targetDate) {
		MessageUtility.addErrorMessage(mospParams, TimeMessageConst.MSG_REQUEST_CHECK_1,
				DateUtility.getStringDate(targetDate), TimeNamingUtility.getWorkManage(mospParams),
				PfNameUtility.goingWorkDay(mospParams));
	}
	
	/**
	 * YYYY/MM/DDは既に休日出勤申請が行われています。出勤日を選択し直してください。(TMW0240)<br>
	 * @param mospParams MosP処理情報
	 * @param targetDate 対象日
	 */
	public static void addErrorAlreadyApplyWorkOnHolidayForWorkOnHoliday(MospParams mospParams, Date targetDate) {
		MessageUtility.addErrorMessage(mospParams, TimeMessageConst.MSG_REQUEST_CHECK_1,
				DateUtility.getStringDate(targetDate), TimeNamingUtility.workOnHolidayNotSubstitute(mospParams),
				PfNameUtility.goingWorkDay(mospParams));
	}
	
	/**
	 * 休始業時刻には24時より前の時間を入力してください。(PFW0105)<br>
	 * @param mospParams MosP処理情報
	 */
	public static void addErrorWorkStartTimeOverLimit(MospParams mospParams) {
		PfMessageUtility.addErrorCheckGeneral(mospParams, TimeNamingUtility.startWorkTime(mospParams),
				TimeNamingUtility.timeBeforeTwentyFour(mospParams), null);
	}
	
	/**
	 * 休憩戻は休憩入より後となるようにしてください。(PFW0217)<br>
	 * @param mospParams MosP処理情報
	 * @param row        対象行インデックス
	 */
	public static void addErrorRestTimeOrderInvalid(MospParams mospParams, Integer row) {
		PfMessageUtility.addErrorOrderInvalid(mospParams, getNameEndRest(mospParams), getNameStartRest(mospParams),
				row);
	}
	
	/**
	 * 申請終了日は申請開始日より後となるようにしてください。(PFW0217)<br>
	 * @param mospParams MosP処理情報
	 * @param row        対象行インデックス
	 */
	public static void addErrorHolidayOrderInvalid(MospParams mospParams, Integer row) {
		PfMessageUtility.addErrorOrderInvalid(mospParams, TimeNamingUtility.requestEndDate(mospParams),
				TimeNamingUtility.requestStartDate(mospParams), row);
	}
	
	/**
	 * 時短時間1が設定済みで且つ無給の場合、勤務前残業自動申請は有効に出来ません。(TMW0330)<br>
	 * @param mospParams MosP処理情報
	 */
	public static void addErrorAnotherItemInvalid(MospParams mospParams) {
		mospParams.addErrorMessage(MSG_W_NOT_REGIST_FOR_ANOTHER_ITEM);
	}
	
	/**
	 * 付与日：YYYY/MM/DDは休暇年月日：YYYY/MM/DDに休暇申請を申請しているため削除できません。休暇申請を取下してください。(TMW0331)<br>
	 * @param mospParams MosP処理情報
	 * @param acquisitionDate 有給休暇付与日
	 * @param requestDate 休暇申請日
	 */
	public static void addErrorNoDeleteForHolidayRequest(MospParams mospParams, Date acquisitionDate,
			Date requestDate) {
		String[] rep = { DateUtility.getStringDate(acquisitionDate), DateUtility.getStringDate(requestDate) };
		mospParams.addErrorMessage(MSG_EXIST_HOLIDAY_REQUEST, rep);
	}
	
	/**
	 * holidayNameは半休申請できません。休暇種別を選択し直してください。(TMW0245)<br>
	 * @param mospParams  MosP処理情報
	 * @param holidayName 休暇名称
	 */
	public static void addErrorHalfHolidayInvalid(MospParams mospParams, String holidayName) {
		addErrorHolidayRangeInvalid(mospParams, holidayName, TimeNamingUtility.holidayHalf(mospParams));
	}
	
	/**
	 * holidayNameは時間休申請できません。休暇種別を選択し直してください。(TMW0245)<br>
	 * @param mospParams  MosP処理情報
	 * @param holidayName 休暇名称
	 */
	public static void addErrorHourlyHolidayInvalid(MospParams mospParams, String holidayName) {
		addErrorHolidayRangeInvalid(mospParams, holidayName, TimeNamingUtility.hourlyHoliday(mospParams));
	}
	
	/**
	 * 取得期限が切れています。%1%を選択し直してください。(TMW0244)<br>
	 * @param mospParams MosP処理情報
	 * @param row        対象行インデックス
	 */
	public static void addErrorHolidayExpiredLimit(MospParams mospParams, Integer row) {
		MessageUtility.addErrorMessage(mospParams, MSG_EXPIRED_LIMIT, row, TimeNamingUtility.holidayDate(mospParams));
	}
	
	/**
	 * holidayNameはholidayRange申請できません。休暇種別を選択し直してください。(TMW0245)<br>
	 * @param mospParams   MosP処理情報
	 * @param holidayName  休暇名称
	 * @param holidayRange 休暇範囲名称
	 */
	public static void addErrorHolidayRangeInvalid(MospParams mospParams, String holidayName, String holidayRange) {
		// holidayRange申請文字列を準備
		StringBuilder range = new StringBuilder(holidayRange);
		range.append(PfNameUtility.application(mospParams));
		// MosP処理情報にエラーメッセージを追加
		MessageUtility.addErrorMessage(mospParams, MSG_CAN_NOT_REQUEST, holidayName, range.toString(),
				TimeNamingUtility.holidayType(mospParams));
	}
	
	/**
	 * 半休と時間休は同日に申請できません。休暇年月日または休暇範囲を選択し直してください。(TMW0245)<br>
	 * @param mospParams MosP処理情報
	 * @param row        対象行インデックス
	 */
	public static void addErrorHalfAndHourlyHoliday(MospParams mospParams, Integer row) {
		// 半休と時間休文字列を準備
		StringBuilder range = new StringBuilder(TimeNamingUtility.holidayHalf(mospParams));
		range.append(TimeNamingUtility.and(mospParams));
		range.append(TimeNamingUtility.hourlyHoliday(mospParams));
		// 同日に申請文字列を準備
		StringBuilder application = new StringBuilder(TimeNamingUtility.same(mospParams));
		application.append(PfNameUtility.day(mospParams));
		application.append(TimeNamingUtility.in(mospParams));
		application.append(PfNameUtility.application(mospParams));
		// 休暇年月日または休暇範囲文字列を準備
		StringBuilder correction = new StringBuilder(TimeNamingUtility.holidayDate(mospParams));
		correction.append(TimeNamingUtility.or(mospParams));
		correction.append(TimeNamingUtility.holidayRange(mospParams));
		// MosP処理情報にエラーメッセージを追加
		MessageUtility.addErrorMessage(mospParams, MSG_CAN_NOT_REQUEST, row, range.toString(), application.toString(),
				correction.toString());
	}
	
	/**
	 * 法定休日は半日振替できません。出勤日を選択し直してください。(TMW0245)<br>
	 * @param mospParams MosP処理情報
	 */
	public static void addErrorWorkOnHalfLegalHoliday(MospParams mospParams) {
		// MosP処理情報にエラーメッセージを設定
		MessageUtility.addErrorMessage(mospParams, MSG_CAN_NOT_REQUEST, TimeNamingUtility.legalHoliday(mospParams),
				TimeNamingUtility.getHalfSubstitute(mospParams), PfNameUtility.goingWorkDay(mospParams));
	}
	
	/**
	 * 法定休日は半日振替できません。振替日を選択し直してください。(TMW0245)<br>
	 * @param mospParams MosP処理情報
	 */
	public static void addErrorSubstituteHalfLegalHoliday(MospParams mospParams) {
		// MosP処理情報にエラーメッセージを設定
		MessageUtility.addErrorMessage(mospParams, MSG_CAN_NOT_REQUEST, TimeNamingUtility.legalHoliday(mospParams),
				TimeNamingUtility.getHalfSubstitute(mospParams), TimeNamingUtility.substituteDay(mospParams));
	}
	
	/**
	 * ストック休暇は時間休の申請ができません。休暇種別を選択し直してください。(TMW0245)<br>
	 * @param mospParams MosP処理情報
	 */
	public static void addErrorHourlyStockHoliday(MospParams mospParams) {
		// MosP処理情報にエラーメッセージを設定
		MessageUtility.addErrorMessage(mospParams, MSG_CAN_NOT_REQUEST, TimeNamingUtility.stockHoliday(mospParams),
				TimeNamingUtility.hourlyHolidayRequestForMessage(mospParams),
				TimeNamingUtility.holidayType(mospParams));
	}
	
	/**
	 * YYYY/MM/DDは出勤日ではありません。休暇年月日を選択し直してください。(TMW0242)<br>
	 * @param mospParams MosP処理情報
	 * @param targetDate 対象日
	 * @param row        対象行インデックス
	 */
	public static void addErrorNotWorkDayForHolidayRequest(MospParams mospParams, Date targetDate, Integer row) {
		// MosP処理情報にエラーメッセージを設定
		MessageUtility.addErrorMessage(mospParams, TimeMessageConst.MSG_REQUEST_CHECK_3, row,
				DateUtility.getStringDate(targetDate), PfNameUtility.goingWorkDay(mospParams),
				TimeNamingUtility.holidayDate(mospParams));
	}
	
	/**
	 * 出勤日は休日ではありません。出勤日を選択し直してください。(TMW0242)<br>
	 * @param mospParams MosP処理情報
	 */
	public static void addErrorWorkOnNotHoliday(MospParams mospParams) {
		// MosP処理情報にエラーメッセージを設定
		MessageUtility.addErrorMessage(mospParams, TimeMessageConst.MSG_REQUEST_CHECK_3,
				PfNameUtility.goingWorkDay(mospParams), TimeNamingUtility.dayOff(mospParams),
				PfNameUtility.goingWorkDay(mospParams));
	}
	
	/**
	 * 付与日：%1%は有効日：%2%に有給休暇手動付与を作成しているため削除できません。(TMW0332)<br>
	 * @param mospParams      MosP処理情報
	 * @param acquisitionDate 有給休暇付与日
	 * @param activateDate    有給手動付与有効日
	 */
	public static void addErrorNoDeleteForPaidHolidayTransaction(MospParams mospParams, Date acquisitionDate,
			Date activateDate) {
		String[] rep = { DateUtility.getStringDate(acquisitionDate), DateUtility.getStringDate(activateDate) };
		mospParams.addErrorMessage(MSG_EXIST_PAID_HOLIDAY_TRANSACTION, rep);
	}
	
	/**
	 * 時間休は勤務形態の時間内(%2%)に申請してください。(TMW0340)<br>
	 * @param mospParams    MosP処理情報
	 * @param startWorkTime 始業時刻
	 * @param endWorkTime   終業時刻
	 * @param row           対象行インデックス
	 * @throws MospException 日付の変換に失敗した場合
	 */
	public static void addErrorHorlyHolidayOutOfWorkTypeTime(MospParams mospParams, Date startWorkTime,
			Date endWorkTime, Integer row) throws MospException {
		MessageUtility.addErrorMessage(mospParams, MSG_W_OUT_OF_WOKR_TYP_TIME, row,
				TimeNamingUtility.hourlyHoliday(mospParams), TransStringUtility.getHourColonMinuteTerm(mospParams,
						startWorkTime, endWorkTime, DateUtility.getDefaultTime()));
	}
	
	/**
	 * 休憩を入力してください。このまま申請する場合はキャンセルを押してください。(TMW0343)<br>
	 * @param mospParams	MosP処理情報
	 */
	public static void addErrorCheckRestTimeForWorkOnHoliday(MospParams mospParams) {
		mospParams.addErrorMessage(MSG_REST_TIME_CHECK);
	}
	
	/**
	 * 勤務時間が6時間を超えています。休憩を入力してください。(TMW0344)<br>
	 * @param mospParams	MosP処理情報
	 */
	public static void addErrorShortRestTimeForWorkOnHoliday(MospParams mospParams) {
		mospParams.addErrorMessage(MSG_REST_TIME_CHECK_ERROR);
	}
	
	/**
	 * ダブルトラックまたは分割付与の可能性あり。<br>
	 * @param mospParams MosP処理情報
	 * @return メッセージ
	 */
	public static String getPaidHolidaySplit(MospParams mospParams) {
		return mospParams.getMessage(MSG_W_PAID_HOLIDAY_SPLIT);
	}
	
	/**
	 * 残業申請画面の「申請可能時間」の表示欄に反映される時間です。<br>
	 * @param mospParams MosP処理情報
	 * @return メッセージ
	 */
	public static String getLimitSettingDescription(MospParams mospParams) {
		return mospParams.getMessage(MSG_DISPLAY_SETTING_DESCRIPTION,
				TimeNamingUtility.overtimeRequestScreen(mospParams), TimeNamingUtility.applicableTime(mospParams));
	}
	
	/**
	 * この時間を超えると、勤怠一覧画面の「外残」に黄色で時間が表示されます。<br><br>
	 * @param mospParams MosP処理情報
	 * @return メッセージ
	 */
	public static String getAttentionSettingDescription(MospParams mospParams) {
		return mospParams.getMessage(MSG_COLOR_SETTING_DESCRIPTION, TimeNamingUtility.attendanceListScreen(mospParams),
				TimeNamingUtility.overtimeOutAbbr(mospParams), PfNameUtility.yellow(mospParams));
	}
	
	/**
	 * この時間を超えると、勤怠一覧画面の「外残」に赤色で時間が表示されます。<br><br>
	 * @param mospParams MosP処理情報
	 * @return メッセージ
	 */
	public static String getWarningSettingDescription(MospParams mospParams) {
		return mospParams.getMessage(MSG_COLOR_SETTING_DESCRIPTION, TimeNamingUtility.attendanceListScreen(mospParams),
				TimeNamingUtility.overtimeOutAbbr(mospParams), PfNameUtility.red(mospParams));
	}
	
	/**
	 * {@link MessageUtility#getRowedFieldName(MospParams, String, Integer)}
	 * を実行する。<br>
	 * @param mospParams MosP処理情報
	 * @param fieldName 対象フィールド名
	 * @param row       対象行インデックス
	 * @return 行番号が付加されたフィールド名
	 */
	protected static String getRowedFieldName(MospParams mospParams, String fieldName, Integer row) {
		// 行番号が付加されたフィールド名を取得
		return MessageUtility.getRowedFieldName(mospParams, fieldName, row);
	}
	
	/**
	 * 勤怠名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return ：勤怠名称
	 */
	public static String getNameWorkManage(MospParams mospParams) {
		return mospParams.getName("WorkManage");
	}
	
	/**
	 * 勤務日名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return ：勤務日名称
	 */
	public static String getNameWorkDate(MospParams mospParams) {
		return mospParams.getName("Work", "Day");
	}
	
	/**
	 * 勤務形態名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return ：勤務形態名称
	 */
	public static String getNameWorkType(MospParams mospParams) {
		return mospParams.getName("Work", "Form");
	}
	
	/**
	 * 集計名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return ：集計名称
	 */
	protected static String getNameTotal(MospParams mospParams) {
		return mospParams.getName("Total");
	}
	
	/**
	 * 年月名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return ：年月名称
	 */
	protected static String getNameYearMonth(MospParams mospParams) {
		return mospParams.getName("Year", "Month");
	}
	
	/**
	 * 年名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return ：年名称
	 */
	protected static String getNameYear(MospParams mospParams) {
		return mospParams.getName("Year");
	}
	
	/**
	 * 月名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return ：月名称
	 */
	protected static String getNameMonth(MospParams mospParams) {
		return mospParams.getName("Month");
	}
	
	/**
	 * 打刻名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 打刻名称
	 */
	protected static String getNameRecordTime(MospParams mospParams) {
		return mospParams.getName("RecordTime");
	}
	
	/**
	 * 更新名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 更新名称
	 */
	protected static String getNameUpdate(MospParams mospParams) {
		return mospParams.getName("Update");
	}
	
	/**
	 * 始業名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 始業名称
	 */
	protected static String getNameStartWork(MospParams mospParams) {
		return mospParams.getName("StartWork");
	}
	
	/**
	 * 終業名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 終業名称
	 */
	public static String getNameEndWork(MospParams mospParams) {
		return mospParams.getName("EndWork");
	}
	
	/**
	 * 休憩入名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 休憩入名称
	 */
	public static String getNameStartRest(MospParams mospParams) {
		return mospParams.getName("RestTime", "Into");
	}
	
	/**
	 * 休憩戻名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 休憩戻名称
	 */
	public static String getNameEndRest(MospParams mospParams) {
		return mospParams.getName("RestTime", "Return");
	}
	
	/**
	 * 定時終業名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 定時終業名称
	 */
	public static String getNameRegularEnd(MospParams mospParams) {
		return mospParams.getName("RegularTime", "EndWork");
	}
	
	/**
	 * 残業有終業名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 残業有終業名称
	 */
	public static String getNameOverEnd(MospParams mospParams) {
		return mospParams.getName("OvertimeWork", "EffectivenessExistence", "EndWork");
	}
	
	/**
	 * 出勤名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 出勤名称
	 */
	protected static String getNameRegularWork(MospParams mospParams) {
		return mospParams.getName("GoingWork");
	}
	
	/**
	 * 休憩1名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 休憩1名称
	 */
	protected static String getNameRest1Time(MospParams mospParams) {
		return mospParams.getName("Rest1");
	}
	
	/**
	 * 休憩2名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 休憩1名称
	 */
	protected static String getNameRest2Time(MospParams mospParams) {
		return mospParams.getName("Rest2");
	}
	
	/**
	 * 時短時間1名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 時短時間1名称
	 */
	protected static String getNameShort1Time(MospParams mospParams) {
		return mospParams.getName("ShortTime", "Time", "No1");
	}
	
	/**
	 * 時短時間2名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 時短時間2名称
	 */
	protected static String getNameShort2Time(MospParams mospParams) {
		return mospParams.getName("ShortTime", "Time", "No2");
	}
	
	/**
	 * 所定労働時間名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 所定労働時間名称
	 */
	protected static String getNamePrescribedWorkTime(MospParams mospParams) {
		return mospParams.getName("Prescribed", "Labor", "Time");
	}
	
	/**
	 * 開始時刻名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 開始時刻
	 */
	public static String getNameStartTime(MospParams mospParams) {
		return mospParams.getName("Start", "Moment");
	}
	
	/**
	 * 終了時刻名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 終了時刻
	 */
	public static String getNameEndTime(MospParams mospParams) {
		return mospParams.getName("End", "Moment");
	}
	
	/**
	 * 始業時刻名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 始業時刻名称
	 */
	public static String getNameStartWorkTime(MospParams mospParams) {
		return mospParams.getName("StartWork", "Moment");
	}
	
	/**
	 * 終業時刻名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 終業時刻名称
	 */
	public static String getNameEndWorkTime(MospParams mospParams) {
		return mospParams.getName("EndWork", "Moment");
	}
	
	/**
	 * 休憩名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 休憩名称
	 */
	public static String getNameRest(MospParams mospParams) {
		return mospParams.getName("RestTime");
	}
	
}
