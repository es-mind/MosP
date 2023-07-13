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
package jp.mosp.platform.utils;

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.MessageUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.framework.utils.RoleUtility;

/**
 * メッセージに関するユーティリティクラス。<br>
 * <br>
 * プラットフォームにおいてサーバ側プログラムで作成されるメッセージは、
 * 全てこのクラスを通じて作成される。<br>
 * <br>
 */
public class PfMessageUtility {
	
	/**
	 * メッセージコード(処理成功)。<br>
	 * %1%しました。<br>
	 */
	protected static final String	MSG_I_PROCESS_SUCCEED			= "PFI0001";
	
	/**
	 * メッセージコード(処理失敗)。<br>
	 * %1%できませんでした。エラー内容を確認の上、再度処理を行ってください。<br>
	 */
	protected static final String	MSG_I_PROCESS_FAILED			= "PFI0002";
	
	/**
	 * メッセージコード(履歴削除)。<br>
	 * 履歴を%1%件、削除しました。<br>
	 */
	protected static final String	MSG_I_DELETE_HISTORY			= "PFI0003";
	
	/**
	 * メッセージコード(インポート成功)。<br>
	 * %1%件、登録しました。<br>
	 */
	protected static final String	MSG_I_IMPORT_SUCCEED			= "PFI0004";
	
	/**
	 * メッセージコード(エラー内容確認)。<br>
	 * エラー内容を確認の上、再度処理を行ってください。<br>
	 */
	protected static final String	MSG_I_CONFIRM_ERROR				= "PFI0005";
	
	/**
	 * メッセージコード(処理失敗)。<br>
	 * %1%できませんでした。エラー内容を確認ください。<br>
	 */
	protected static final String	MSG_I_PROCESS_JUST_FAILED		= "PFI0006";
	
	/**
	 * メッセージコード(データ無し)。<br>
	 * 対象データが存在しません。<br>
	 */
	protected static final String	MSG_I_NO_DATA					= "PFI0102";
	
	/**
	 * メッセージコード(妥当性確認(入力値不正))。<br>
	 * 入力された%1%に誤りがあります。<br>
	 */
	protected static final String	MSG_W_INPUT_VALUE_INVALID		= "PFW0101";
	
	/**
	 * メッセージコード(妥当性確認(必須))。<br>
	 * %1%を正しく入力してください。<br>
	 */
	protected static final String	MSG_W_REQUIRED					= "PFW0102";
	
	/**
	 * メッセージコード(妥当性確認(チェックボックス))。<br>
	 * 必ず一件はチェックボックスを選択してください。<br>
	 */
	protected static final String	MSG_W_CHECK_BOX					= "PFW0103";
	
	/**
	 * メッセージコード(妥当性確認(日付))。<br>
	 * %1%は正しい日付を入力してください。<br>
	 */
	protected static final String	MSG_W_CHECK_DATE				= "PFW0104";
	
	/**
	 * メッセージコード(妥当性確認(汎用))。<br>
	 * %1%には%2%を入力してください。<br>
	 */
	protected static final String	MSG_W_CHECK_GENERAL				= "PFW0105";
	
	/**
	 * メッセージコード(妥当性確認(桁数))。<br>
	 * %1%には%2%桁で入力してください。<br>
	 */
	protected static final String	MSG_W_CHECK_DIGIT				= "PFW0106";
	
	/**
	 * メッセージコード(有効日未定)。<br>
	 * 有効日を決定してください。<br>
	 */
	protected static final String	MSG_W_ACTIVATE_DATE_NOT_SETTLED	= "PFW0107";
	
	/**
	 * メッセージコード(妥当性確認(半角英数))。<br>
	 * %1%には半角英数字を入力してください。<br>
	 */
	protected static final String	MSG_W_CHECK_ALP_NUM				= "PFW0111";
	
	/**
	 * メッセージコード(妥当性確認(数値))。<br>
	 * 数字を入力してください。<br>
	 */
	protected static final String	MSG_W_CHECK_NUMERIC_NO_OBJ		= "PFW0112";
	
	/**
	 * メッセージコード(妥当性確認(数値))。<br>
	 * %1%には数字を入力してください。<br>
	 */
	protected static final String	MSG_W_CHECK_NUMERIC				= "PFW0113";
	
	/**
	 * メッセージコード(妥当性確認(形式))。<br>
	 * 正しい形式の値を入力してください。<br>
	 */
	protected static final String	MSG_W_CHECK_FORM_NO_OBJ			= "PFW0114";
	
	/**
	 * メッセージコード(妥当性確認(形式))。<br>
	 * %1%には正しい形式の値を入力してください。<br>
	 */
	protected static final String	MSG_W_CHECK_FORM				= "PFW0115";
	
	/**
	 * メッセージコード(妥当性確認(日付))。<br>
	 * 正しい日付を入力してください。<br>
	 */
	protected static final String	MSG_W_CHECK_DATE_NO_OBJ			= "PFW0116";
	
	/**
	 * メッセージコード(妥当性確認(年))。<br>
	 * 年は%1%以上を入力してください。<br>
	 */
	protected static final String	MSG_W_CHECK_YEAR				= "PFW0117";
	
	/**
	 * メッセージコード(妥当性確認(時間))。<br>
	 * 正しい時間を入力してください。<br>
	 */
	protected static final String	MSG_W_CHECK_TIME				= "PFW0121";
	
	/**
	 * メッセージコード(妥当性確認(最低文字数))。<br>
	 * %1%は%2%文字以上を入力してください。<br>
	 */
	protected static final String	MSG_W_CHECK_MIN_LENGTH			= "PFW0122";
	
	/**
	 * メッセージコード(妥当性確認(最大文字数))。<br>
	 * %1%は%2%文字以内で入力してください。<br>
	 */
	protected static final String	MSG_W_CHECK_MAX_LENGTH			= "PFW0123";
	
	/**
	 * メッセージコード(妥当性確認(英数記号))。<br>
	 * %1%には半角英数字又は記号( _ . - @ )を入力してください。<br>
	 */
	protected static final String	MSG_W_CHECK_ALP_NUM_SIGN		= "PFW0125";
	
	/**
	 * メッセージコード(妥当性確認(バイト数))。<br>
	 * %1%は全角%2%桁または半角%3%桁以内で入力してください。<br>
	 */
	protected static final String	MSG_W_CHECK_BYTE_LENGTH			= "PFW0126";
	
	/**
	 * メッセージコード(フラグ値不正)。<br>
	 * %1%には、0か1を入力してください。<br>
	 */
	protected static final String	MSG_FLAG_INVALID				= "PFW0127";
	
	/**
	 * メッセージコード(限界値超)。<br>
	 * %1%には、%2%以上を入力してください。<br>
	 */
	protected static final String	MSG_UNDER_LIMIT					= "PFW0128";
	
	/**
	 * メッセージコード(限界値超)。<br>
	 * %1%には、%2%以下を入力してください。<br>
	 */
	protected static final String	MSG_OVER_LIMIT					= "PFW0129";
	
	/**
	 * メッセージコード(妥当性確認(小数))。<br>
	 * %1%は、整数部%2%桁以内、小数部%3%桁以内で入力してください。<br>
	 */
	protected static final String	MSG_W_INVALID_DECIMAL			= "PFW0130";
	
	/**
	 * メッセージコード(日付不正)。<br>
	 * %1%には存在する日付を正しい形式(例：2017/04/01)で入力してください。<br>
	 */
	protected static final String	MSG_W_DATE_INVALID				= "PFW0132";
	
	/**
	 * メッセージコード(桁数超過)。<br>
	 * %1%は%2%桁以内で入力してください。<br>
	 */
	protected static final String	MSG_W_OVER_DIGIT				= "PFW0140";
	
	/**
	 * メッセージコード(項目不在)。<br>
	 * 該当する%1%が存在しません。<br>
	 */
	protected static final String	MSG_W_NO_ITEM					= "PFW0201";
	
	/**
	 * メッセージコード(社員の状態確認(肯定)時)。<br>
	 * 該当する社員は%1%しています。<br>
	 */
	protected static final String	MSG_W_EMPLOYEE_IS				= "PFW0202";
	
	/**
	 * メッセージコード(社員の状態確認(否定)時)。<br>
	 * 該当する社員は%1%していません。<br>
	 */
	protected static final String	MSG_W_EMPLOYEE_IS_NOT			= "PFW0203";
	
	/**
	 * メッセージコード(重複登録)。<br>
	 * そのコードは既に存在しているため、別のコードを入力してください。<br>
	 */
	protected static final String	MSG_W_CODE_DUPLICATE			= "PFW0204";
	
	/**
	 * メッセージコード(履歴重複)。<br>
	 * その有効日における履歴が既に存在しているため、別の有効日を入力してください。<br>
	 */
	protected static final String	MSG_W_HISTRY_DUPLICATE			= "PFW0205";
	
	/**
	 * メッセージコード(排他)。<br>
	 * その情報は他のユーザによって更新されました。再度検索して、確認してください。<br>
	 */
	protected static final String	MSG_W_EXCLUSIVE					= "PFW0206";
	
	/**
	 * メッセージコード(コード使用済)。<br>
	 * コード：%1%は、社員：%2%が使用しています。社員情報を変更してください。<br>
	 */
	protected static final String	MSG_W_CODE_IS_USED				= "PFW0207";
	
	/**
	 * メッセージコード(所属使用済)。<br>
	 * コード：%1%は、所属：%2%が経路として使用しています。所属：%2%の上位所属を変更してください。<br>
	 */
	protected static final String	MSG_W_SECTION_IS_USED			= "PFW0208";
	
	/**
	 * メッセージコード(上位所属不正)。<br>
	 * 自らを経路に含めることはできません。上位所属を変更してください。<br>
	 */
	protected static final String	MSG_W_ROUTE_CONTAINS_ITSELF		= "PFW0209";
	
	/**
	 * メッセージコード(有効日以前コード不在)。<br>
	 * コード：%1%は、有効日以前の情報が存在しないため、一括更新できません。個別に履歴追加をしてください。<br>
	 */
	protected static final String	MSG_W_NO_CODE_BEFORE_DATE		= "PFW0210";
	
	/**
	 * メッセージコード(必須ロールユーザ不在)。<br>
	 * 本日時点で、必須ロールコード：%1%を設定しているユーザが、存在しなくなってしまいます。<br>
	 */
	protected static final String	MSG_W_NO_NEEDED_ROLE			= "PFW0211";
	
	/**
	 * メッセージコード(入社していないためユーザ作成不可)。<br>
	 * 社員コード：%1%は入社日が登録されていないため、アカウントを作成できません。<br>
	 */
	protected static final String	MSG_W_NOT_JOIN_FOR_ACCOUNT		= "PFW0212";
	
	/**
	 * メッセージコード(ある時点で無効)。<br>
	 * %1%：%2%は%3%時点で無効となります。<br>
	 */
	protected static final String	MSG_W_INACTIVE_AT_THAT_TIME		= "PFW0213";
	
	/**
	 * メッセージコード(選択コード不在)。<br>
	 * %1%：%2%は存在しないため、登録できません。<br>
	 */
	protected static final String	MSG_W_SELECTED_CODE_NOT_EXIST	= "PFW0214";
	
	/**
	 * メッセージコード(選択コード存在)。<br>
	 * %1%：%2%は既に存在しているため、登録できません。<br>
	 */
	protected static final String	MSG_W_SELECTED_CODE_EXIST		= "PFW0215";
	
	/**
	 * メッセージコード(入社情報削除不可)。<br>
	 * 該当する社員は既に退職しているため、入社情報を削除できません。<br>
	 */
	protected static final String	MSG_W_DELETE_ENTRANCE			= "PFW0216";
	
	/**
	 * メッセージコード(順序不正)。<br>
	 * %1%は%2%より後となるようにしてください。<br>
	 */
	protected static final String	MSG_W_ORDER_INVALID				= "PFW0217";
	
	/**
	 * メッセージコード(期間重複)。<br>
	 * %1%期間が重複しないように入力してください。<br>
	 */
	protected static final String	MSG_W_TERM_OVERLAP				= "PFW0218";
	
	/**
	 * メッセージコード(期間重複)。<br>
	 * %1%における社員コード：%2%の履歴情報は存在しません。<br>
	 */
	protected static final String	MSG_W_EMP_HISTORY_NOT_EXIST		= "PFW0219";
	
	/**
	 * メッセージコード(情報不在)。<br>
	 * 有効な%1%が存在しません。<br>
	 */
	protected static final String	MSG_W_VALID_DATA_NOT_EXIST		= "PFW0220";
	
	/**
	 * メッセージコード(ユニット使用)。<br>
	 * コード：%1%は、ルート：%2%が使用しています。ルート：%2%のルート設定を変更してください。<br>
	 */
	protected static final String	MSG_W_UNIT_IS_USED				= "PFW0222";
	
	/**
	 * メッセージコード(ルート使用)。<br>
	 * コード：%1%は、ルート適用：%2%が使用しています。ルート適用：%2%のルートを変更してください。<br>
	 */
	protected static final String	MSG_W_ROUTE_IS_USED				= "PFW0223";
	
	/**
	 * メッセージコード(社員コード重複)。<br>
	 * その社員コード：%1%は既に設定されています。コード：%2%を履歴編集、または履歴追加して設定してください。<br>
	 */
	protected static final String	MSG_W_EMPLOYEE_DUPLICATE		= "PFW0224";
	
	/**
	 * メッセージコード(適用範囲重複)。<br>
	 * その適用範囲は、既に設定されています。コード：%1%より、履歴を追加して設定してください。<br>
	 */
	protected static final String	MSG_W_APPLICATION_DUPLICATE		= "PFW0225";
	
	/**
	 * メッセージコード(権限不正)。<br>
	 * %1%における社員コード：%2%の情報を%3%する権限がありません。<br>
	 */
	protected static final String	MSG_W_NO_AUTHORITY				= "PFW0226";
	
	/**
	 * メッセージコード(取得不能)。<br>
	 * %1%を取得できませんでした。%2%を変更してください。<br>
	 */
	protected static final String	MSG_W_CAN_NOT_GET				= "PFW0227";
	
	/**
	 * メッセージコード(ユニット承認者不在)。<br>
	 * ユニットコード：%1%は、%2%時点で承認者が存在しません。<br>
	 */
	protected static final String	MSG_W_UNIT_HAVE_NO_APPROVER		= "PFW0228";
	
	/**
	 * メッセージコード(ワークフロー処理失敗)。<br>
	 * ワークフロー処理に失敗しました。ワークフローを確認してください。<br>
	 */
	protected static final String	MSG_W_WORKFLOW_PROCESS_FAILED	= "PFW0229";
	
	/**
	 * メッセージコード(社員未入社)。<br>
	 * %1%時点では社員コード：%2%は入社していません。日付または社員コードを選択し直してください。<br>
	 */
	protected static final String	MSG_W_EMPLOYEE_NOT_ENTERED		= "PFW0230";
	
	/**
	 * メッセージコード(社員退社済)。<br>
	 * %1%時点では社員コード：%2%は退職しています。日付または社員コードを選択し直してください。<br>
	 */
	protected static final String	MSG_W_EMPLOYEE_RETIRED			= "PFW0231";
	
	/**
	 * メッセージコード(履歴情報不在)。<br>
	 * %1%における%2%の履歴情報は存在しません。<br>
	 */
	protected static final String	MSG_W_HISTORY_NOT_EXIST			= "PFW0232";
	
	/**
	 * メッセージコード(検索条件未指定)。<br>
	 * 検索条件を指定してください。<br>
	 */
	protected static final String	MSG_W_SEARCH_CONDITION			= "PFW0234";
	
	/**
	 * メッセージコード(ユーザ不在)。<br>
	 * ユーザ情報が取得できないため、ログアウトしました。<br>
	 */
	public static final String		MSG_W_USER_NOT_EXIST			= "PFW0235";
	
	/**
	 * メッセージコード(異なる申請によるデータが存在)。<br>
	 * %1%は異なる申請により登録された%2%が存在するため、操作できません。<br>
	 */
	protected static final String	MSG_W_OTHER_DATA_EXIST			= "PFW0236";
	
	/**
	 * メッセージコード(未登録)。<br>
	 * 有効日：%1%時点で社員コード：%2%の%3%は登録されていません。<br>
	 */
	protected static final String	MSG_W_UNREGISTERED				= "PFW0237";
	
	/**
	 * メッセージコード(組み合わせ不正)。<br>
	 * %1%と%2%の組み合わせが、正しくありません。<br>
	 */
	protected static final String	MSG_W_INVALID_PAIR				= "PFW0238";
	
	/**
	 * メッセージコード(ユーザ数超過)。<br>
	 * %1%のロールを付加されたユーザ数が上限値(%2%)を超えないようにしてください。<br>
	 */
	protected static final String	MSG_W_EXCEED_USERS				= "PFW0239";
	
	/**
	 * メッセージコード(値不正)。<br>
	 * %1%：%2%には、正しい形式の値を設定してください。<br>
	 */
	protected static final String	MSG_W_INVALID_VALUE				= "PFW0240";
	
	/**
	 * メッセージコード(認証不正)。<br>
	 * ユーザIDあるいはパスワードに誤りがあります。<br>
	 */
	protected static final String	MSG_W_AUTH_FAILED				= "PFW9111";
	
	/**
	 * メッセージコード(パスワード不変)。<br>
	 * パスワードは前回入力値と違うものを入力してください。<br>
	 */
	protected static final String	MSG_W_PASSWORD_UNCHANGED		= "PFW9112";
	
	/**
	 * メッセージコード(パスワード有効期限切れ)。<br>
	 * パスワードの有効期限が切れています。パスワードを更新してください。<br>
	 */
	protected static final String	MSG_W_PASSWORD_EXPIRE			= "PFW9113";
	
	/**
	 * メッセージコード(旧パスワード不正)。<br>
	 * 現在のパスワードに誤りがあります。<br>
	 */
	protected static final String	MSG_W_OLD_PASSWORD				= "PFW9114";
	
	/**
	 * メッセージコード(新パスワード不正)。<br>
	 * 新しいパスワードとパスワード入力確認が異なります。<br>
	 */
	protected static final String	MSG_W_NEW_PASSWORD				= "PFW9115";
	
	/**
	 * メッセージコード(パスワード文字種)。<br>
	 * パスワードは英字と数字を組み合わせたものを設定してください。<br>
	 */
	protected static final String	MSG_W_PASSWORD_CHAR				= "PFW9116";
	
	/**
	 * メッセージコード(初期パスワード)。<br>
	 * パスワードは初期パスワードと異なるものを設定してください。<br>
	 */
	protected static final String	MSG_W_PASSWORD_INIT				= "PFW9117";
	
	/**
	 * メッセージコード(個人基本情報削除不能)。<br>
	 * 社員コード：%1%には%2%が設定されているため、削除することは出来ません。<br>
	 */
	protected static final String	MSG_W_HUMAN_CAN_NOT_DELETE		= "PFW9118";
	
	
	/**
	 * 他クラスからのインスタンス化を防止する。<br>
	 */
	private PfMessageUtility() {
		// 処理無し
	}
	
	/**
	 * %1%しました。<br>
	 * <br>
	 * @param mospParams MosP処理情報
	 * @param processName 処理名称
	 */
	public static void addMessageSucceed(MospParams mospParams, String processName) {
		MessageUtility.addMessage(mospParams, MSG_I_PROCESS_SUCCEED, processName);
	}
	
	/**
	 * 新規登録しました。<br>
	 * <br>
	 * @param mospParams MosP処理情報
	 */
	public static void addMessageNewInsertSucceed(MospParams mospParams) {
		addMessageSucceed(mospParams, PfNameUtility.newInsert(mospParams));
	}
	
	/**
	 * 登録しました。<br>
	 * <br>
	 * @param mospParams MosP処理情報
	 */
	public static void addMessageInsertSucceed(MospParams mospParams) {
		addMessageSucceed(mospParams, PfNameUtility.insert(mospParams));
	}
	
	/**
	 * 更新しました。<br>
	 * <br>
	 * @param mospParams MosP処理情報
	 */
	public static void addMessageUpdatetSucceed(MospParams mospParams) {
		addMessageSucceed(mospParams, PfNameUtility.update(mospParams));
	}
	
	/**
	 * 履歴追加しました。<br>
	 * <br>
	 * @param mospParams MosP処理情報
	 */
	public static void addMessageAddHistorySucceed(MospParams mospParams) {
		addMessageSucceed(mospParams, PfNameUtility.addHistory(mospParams));
	}
	
	/**
	 * 履歴編集しました。<br>
	 * <br>
	 * @param mospParams MosP処理情報
	 */
	public static void addMessageEditHistorySucceed(MospParams mospParams) {
		addMessageSucceed(mospParams, PfNameUtility.edtiHistory(mospParams));
	}
	
	/**
	 * 削除しました。<br>
	 * <br>
	 * @param mospParams MosP処理情報
	 */
	public static void addMessageDeleteSucceed(MospParams mospParams) {
		addMessageSucceed(mospParams, PfNameUtility.delete(mospParams));
	}
	
	/**
	 * %1%できませんでした。エラー内容を確認の上、再度処理を行ってください。<br>
	 * @param mospParams  MosP処理情報
	 * @param processName 処理名称
	 */
	public static void addMessageFailed(MospParams mospParams, String processName) {
		MessageUtility.addMessage(mospParams, MSG_I_PROCESS_FAILED, processName);
	}
	
	/**
	 * 登録できませんでした。エラー内容を確認の上、再度処理を行ってください。<br>
	 * @param mospParams MosP処理情報
	 */
	public static void addMessageInsertFailed(MospParams mospParams) {
		MessageUtility.addMessage(mospParams, MSG_I_PROCESS_FAILED, PfNameUtility.insert(mospParams));
	}
	
	/**
	 * 更新できませんでした。エラー内容を確認の上、再度処理を行ってください。<br>
	 * @param mospParams MosP処理情報
	 */
	public static void addMessageUpdateFailed(MospParams mospParams) {
		MessageUtility.addMessage(mospParams, MSG_I_PROCESS_FAILED, PfNameUtility.update(mospParams));
	}
	
	/**
	 * 削除できませんでした。エラー内容を確認の上、再度処理を行ってください。<br>
	 * @param mospParams MosP処理情報
	 */
	public static void addMessageDeleteFailed(MospParams mospParams) {
		MessageUtility.addMessage(mospParams, MSG_I_PROCESS_FAILED, PfNameUtility.delete(mospParams));
	}
	
	/**
	 * 履歴削除できませんでした。エラー内容を確認の上、再度処理を行ってください。<br>
	 * @param mospParams MosP処理情報
	 */
	public static void addMessageDeleteHistoryFailed(MospParams mospParams) {
		MessageUtility.addMessage(mospParams, MSG_I_PROCESS_FAILED, PfNameUtility.deleteHistory(mospParams));
	}
	
	/**
	 * 一括更新できませんでした。エラー内容を確認の上、再度処理を行ってください。<br>
	 * @param mospParams MosP処理情報
	 */
	public static void addMessageBatchUpdatetFailed(MospParams mospParams) {
		MessageUtility.addMessage(mospParams, MSG_I_PROCESS_FAILED, PfNameUtility.batchUpdate(mospParams));
	}
	
	/**
	 * 決定できませんでした。エラー内容を確認の上、再度処理を行ってください。<br>
	 * @param mospParams MosP処理情報
	 */
	public static void addMessageDecisiontFailed(MospParams mospParams) {
		MessageUtility.addMessage(mospParams, MSG_I_PROCESS_FAILED, PfNameUtility.decision(mospParams));
	}
	
	/**
	 * 検索できませんでした。エラー内容を確認の上、再度処理を行ってください。<br>
	 * @param mospParams  MosP処理情報
	 */
	public static void addMessageSearchFailed(MospParams mospParams) {
		MessageUtility.addMessage(mospParams, MSG_I_PROCESS_FAILED, PfNameUtility.search(mospParams));
	}
	
	/**
	 * 履歴を%1%件、削除しました。<br>
	 * <br>
	 * @param mospParams MosP処理情報
	 * @param count      履歴削除件数
	 */
	public static void addMessageDeleteHistory(MospParams mospParams, int count) {
		MessageUtility.addMessage(mospParams, MSG_I_DELETE_HISTORY, String.valueOf(count));
	}
	
	/**
	 * 履歴を全件、削除しました。<br>
	 * <br>
	 * @param mospParams MosP処理情報
	 */
	public static void addMessageDeleteAllHistory(MospParams mospParams) {
		MessageUtility.addMessage(mospParams, MSG_I_DELETE_HISTORY, PfNameUtility.all(mospParams));
	}
	
	/**
	 * %1%件、登録しました。<br>
	 * <br>
	 * @param mospParams MosP処理情報
	 * @param count      登録件数
	 */
	public static void addMessageImportSucceed(MospParams mospParams, int count) {
		MessageUtility.addMessage(mospParams, MSG_I_IMPORT_SUCCEED, String.valueOf(count));
	}
	
	/**
	 * エラー内容を確認の上、再度処理を行ってください。<br>
	 * <br>
	 * @param mospParams MosP処理情報
	 */
	public static void addMessageConfirmError(MospParams mospParams) {
		MessageUtility.addMessage(mospParams, MSG_I_CONFIRM_ERROR);
	}
	
	/**
	 * %1%できませんでした。エラー内容を確認ください。<br>
	 * <br>
	 * @param mospParams  MosP処理情報
	 * @param processName 処理名称
	 */
	public static void addMessageProcessJustFailed(MospParams mospParams, String processName) {
		MessageUtility.addMessage(mospParams, MSG_I_PROCESS_JUST_FAILED, processName);
	}
	
	/**
	 * 対象データが存在しません。<br>
	 * <br>
	 * @param mospParams MosP処理情報
	 */
	public static void addMessageNoData(MospParams mospParams) {
		MessageUtility.addMessage(mospParams, MSG_I_NO_DATA);
	}
	
	/**
	 * 入力された%1%に誤りがあります。(PFW0101)<br>
	 * <br>
	 * @param mospParams MosP処理情報
	 * @param fieldName  対象フィールド名
	 * @param row        対象行インデックス
	 */
	public static void addErrorInputValueInvalid(MospParams mospParams, String fieldName, Integer row) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_INPUT_VALUE_INVALID, row, fieldName);
	}
	
	/**
	 * 入力された%1%に誤りがあります。(PFW0101)<br>
	 * <br>
	 * @param mospParams MosP処理情報
	 * @param fieldName  対象フィールド名
	 */
	public static void addErrorInputValueInvalid(MospParams mospParams, String fieldName) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_INPUT_VALUE_INVALID, fieldName);
	}
	
	/**
	 * %1%を正しく入力してください。(PFW0102)<br>
	 * <br>
	 * @param mospParams MosP処理情報
	 * @param fieldName  対象フィールド名
	 */
	public static void addErrorRequired(MospParams mospParams, String fieldName) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_REQUIRED, fieldName);
	}
	
	/**
	 * %1%を正しく入力してください。(PFW0102)<br>
	 * <br>
	 * @param mospParams MosP処理情報
	 * @param fieldName  対象フィールド名
	 * @param row        対象行インデックス
	 */
	public static void addErrorRequired(MospParams mospParams, String fieldName, Integer row) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_REQUIRED, row, fieldName);
	}
	
	/**
	 * 有効日を正しく入力してください。(PFW0102)<br>
	 * <br>
	 * @param mospParams MosP処理情報
	 * @param row        対象行インデックス
	 */
	public static void addErrorActivateDateRequired(MospParams mospParams, Integer row) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_REQUIRED, row, PfNameUtility.activateDate(mospParams));
	}
	
	/**
	 * 必ず一件はチェックボックスを選択してください。(PFW0103)<br>
	 * @param mospParams MosP処理情報
	 */
	public static void addErrorRequireCheck(MospParams mospParams) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_CHECK_BOX);
	}
	
	/**
	 * %1%は正しい日付を入力してください。(PFW0104)<br>
	 * @param mospParams MosP処理情報
	 * @param fieldName  対象フィールド名
	 * @param row        対象行インデックス
	 */
	public static void addErrorCheckDate(MospParams mospParams, String fieldName, Integer row) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_CHECK_DATE, row, fieldName);
	}
	
	/**
	 * %1%は正しい日付を入力してください。(PFW0104)<br>
	 * @param mospParams MosP処理情報
	 * @param fieldName  対象フィールド名
	 */
	public static void addErrorCheckDate(MospParams mospParams, String fieldName) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_CHECK_DATE, fieldName);
	}
	
	/**
	 * %1%には%2%を入力してください。(PFW0105)<br>
	 * @param mospParams MosP処理情報
	 * @param fieldName  対象フィールド名
	 * @param type       文字種等
	 * @param row        対象行インデックス
	 */
	public static void addErrorCheckGeneral(MospParams mospParams, String fieldName, String type, Integer row) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_CHECK_GENERAL, row, fieldName, type);
	}
	
	/**
	 * %1%には%2%を入力してください。(PFW0105)<br>
	 * @param mospParams MosP処理情報
	 * @param fieldName  対象フィールド名
	 * @param type       文字種等
	 */
	public static void addErrorCheckGeneral(MospParams mospParams, String fieldName, String type) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_CHECK_GENERAL, fieldName, type);
	}
	
	/**
	 * %1%には%2%を入力してください。(PFW0105)<br>
	 * <br>
	 * 利用可能文字列を%2%に入れる。<br>
	 * <br>
	 * @param mospParams MosP処理情報
	 * @param fieldName  対象フィールド名
	 * @param availables 利用可能文字列リスト
	 * @param row        行インデックス
	 */
	public static void addErrorAvailableChars(MospParams mospParams, String fieldName, List<String> availables,
			Integer row) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_CHECK_GENERAL, row, fieldName,
				getAvailableCharString(mospParams, availables));
	}
	
	/**
	 * %1%には%2%を入力してください。(PFW0105)<br>
	 * <br>
	 * 利用可能数値を%2%に入れる。<br>
	 * <br>
	 * @param mospParams MosP処理情報
	 * @param fieldName  対象フィールド名
	 * @param availables 利用可能数値配列
	 * @param row        行インデックス
	 */
	public static void addErrorAvailableIntegers(MospParams mospParams, String fieldName, int[] availables,
			Integer row) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_CHECK_GENERAL, row, fieldName,
				MospUtility.toSeparatedString(availables, PfNameUtility.touten(mospParams)));
	}
	
	/**
	 * %1%には%2%以降を入力してください。(PFW0105)<br>
	 * @param mospParams MosP処理情報
	 * @param afterName  前にくるべき日付の名称
	 * @param beforeName 後にくるべき日付の名称
	 */
	public static void addErrorDateOrder(MospParams mospParams, String afterName, String beforeName) {
		StringBuilder sb = new StringBuilder(beforeName).append(PfNameUtility.onAndAfter(mospParams));
		MessageUtility.addErrorMessage(mospParams, MSG_W_CHECK_GENERAL, afterName, sb.toString());
	}
	
	/**
	 * %1%には%2%桁で入力してください。(PFW0106)<br>
	 * @param mospParams MosP処理情報
	 * @param fieldName  対象フィールド名
	 * @param digit      桁数
	 * @param row        対象行インデックス
	 */
	public static void addErrorCheckDigit(MospParams mospParams, String fieldName, int digit, Integer row) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_CHECK_DIGIT, row, fieldName, String.valueOf(digit));
	}
	
	/**
	 * %1%には%2%桁で入力してください。(PFW0106)<br>
	 * @param mospParams MosP処理情報
	 * @param fieldName  対象フィールド名
	 * @param digit      桁数
	 */
	public static void addErrorCheckDigit(MospParams mospParams, String fieldName, int digit) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_CHECK_DIGIT, fieldName, String.valueOf(digit));
	}
	
	/**
	 * 有効日を決定してください。(PFW0107)<br>
	 * @param mospParams MosP処理情報
	 */
	public static void addErrorActivateDateNotSettled(MospParams mospParams) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_ACTIVATE_DATE_NOT_SETTLED);
	}
	
	/**
	 * %1%には半角英数字を入力してください。(PFW0111)<br>
	 * <br>
	 * @param mospParams MosP処理情報
	 * @param fieldName  対象フィールド名
	 * @param row        対象行インデックス
	 */
	public static void addErrorCheckAlpNum(MospParams mospParams, String fieldName, Integer row) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_CHECK_ALP_NUM, row, fieldName);
	}
	
	/**
	 * %1%には半角英数字を入力してください。(PFW0111)<br>
	 * <br>
	 * @param mospParams MosP処理情報
	 * @param fieldName  対象フィールド名
	 */
	public static void addErrorCheckAlpNum(MospParams mospParams, String fieldName) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_CHECK_ALP_NUM, fieldName);
	}
	
	/**
	 * 数字を入力してください。(PFW0112)<br>
	 * <br>
	 * @param mospParams MosP処理情報
	 */
	public static void addErrorCheckNumeric(MospParams mospParams) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_CHECK_NUMERIC_NO_OBJ);
	}
	
	/**
	 * %1%には数字を入力してください。(PFW0113)<br>
	 * <br>
	 * @param mospParams MosP処理情報
	 * @param fieldName  対象フィールド名
	 * @param row        対象行インデックス
	 */
	public static void addErrorCheckNumeric(MospParams mospParams, String fieldName, Integer row) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_CHECK_NUMERIC, row, fieldName);
	}
	
	/**
	 * %1%には数字を入力してください。(PFW0113)<br>
	 * <br>
	 * @param mospParams MosP処理情報
	 * @param fieldName  対象フィールド名
	 */
	public static void addErrorCheckNumeric(MospParams mospParams, String fieldName) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_CHECK_NUMERIC, fieldName);
	}
	
	/**
	 * 正しい形式の値を入力してください。(PFW0114)<br>
	 * <br>
	 * @param mospParams MosP処理情報
	 */
	public static void addErrorCheckForm(MospParams mospParams) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_CHECK_FORM_NO_OBJ);
	}
	
	/**
	 * %1%には正しい形式の値を入力してください。(PFW0115)<br>
	 * <br>
	 * @param mospParams MosP処理情報
	 * @param fieldName  対象フィールド名
	 */
	public static void addErrorCheckForm(MospParams mospParams, String... fieldName) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_CHECK_FORM, fieldName);
	}
	
	/**
	 * %1%には正しい形式の値を入力してください。(PFW0115)<br>
	 * <br>
	 * @param mospParams MosP処理情報
	 * @param fieldName  対象フィールド名
	 * @param row        対象行インデックス
	 */
	public static void addErrorCheckForm(MospParams mospParams, String fieldName, Integer row) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_CHECK_FORM, row, fieldName);
	}
	
	/**
	 * 正しい日付を入力してください。(PFW0116)<br>
	 * <br>
	 * @param mospParams MosP処理情報
	 */
	public static void addErrorCheckDate(MospParams mospParams) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_CHECK_DATE_NO_OBJ);
	}
	
	/**
	 * 年は%1%以上を入力してください。(PFW0117)<br>
	 * <br>
	 * @param mospParams MosP処理情報
	 * @param minYear    年最小値
	 */
	public static void addErrorCheckYesr(MospParams mospParams, int minYear) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_CHECK_YEAR, String.valueOf(minYear));
	}
	
	/**
	 * 正しい時間を入力してください。(PFW0121)<br>
	 * <br>
	 * @param mospParams MosP処理情報
	 */
	public static void addErrorCheckTime(MospParams mospParams) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_CHECK_TIME);
	}
	
	/**
	 * %1%は%2%文字以内で入力してください。(PFW0123)
	 * @param mospParams MosP処理情報
	 * @param fieldName  対象フィールド名
	 * @param maxLength  最大文字数
	 * @param row        対象行インデックス
	 */
	public static void addErrorCheckMaxLength(MospParams mospParams, String fieldName, int maxLength, Integer row) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_CHECK_MAX_LENGTH, row, fieldName, String.valueOf(maxLength));
	}
	
	/**
	 * %1%には半角英数字又は記号( _ . - @ )を入力してください。(PFW0125)
	 * @param mospParams MosP処理情報
	 * @param fieldName  対象フィールド名
	 * @param row        対象行インデックス
	 */
	public static void addErrorCheckAlpNumSign(MospParams mospParams, String fieldName, Integer row) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_CHECK_ALP_NUM_SIGN, row, fieldName);
	}
	
	/**
	 * %1%には半角英数字又は記号( _ . - @ )を入力してください。(PFW0125)
	 * @param mospParams MosP処理情報
	 * @param fieldName  対象フィールド名
	 */
	public static void addErrorCheckAlpNumSign(MospParams mospParams, String fieldName) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_CHECK_ALP_NUM_SIGN, fieldName);
	}
	
	/**
	 * %1%は全角%2%桁または半角%3%桁以内で入力してください。(PFW0126)
	 * @param mospParams MosP処理情報
	 * @param fieldName  対象フィールド名
	 * @param maxLength  最大文字数
	 * @param row        対象行インデックス
	 */
	public static void addErrorCheckByteLength(MospParams mospParams, String fieldName, int maxLength, Integer row) {
		// 最大長の半分(切捨)を取得
		int halfLength = maxLength / 2;
		// エラーメッセージを設定
		MessageUtility.addErrorMessage(mospParams, MSG_W_CHECK_BYTE_LENGTH, row, fieldName, String.valueOf(halfLength),
				String.valueOf(maxLength));
	}
	
	/**
	 * 有効/無効には、0か1を入力してください。(PFW0127)
	 * @param mospParams MosP処理情報
	 * @param row        対象行インデックス
	 */
	public static void addErrorActivateOrInactivateInvalid(MospParams mospParams, Integer row) {
		MessageUtility.addErrorMessage(mospParams, MSG_FLAG_INVALID, row, PfNameUtility.inactivate(mospParams));
	}
	
	/**
	 * 削除フラグには、0か1を入力してください。(PFW0127)
	 * @param mospParams MosP処理情報
	 * @param row        対象行インデックス
	 */
	public static void addErrorDeleteFlagInvalid(MospParams mospParams, Integer row) {
		MessageUtility.addErrorMessage(mospParams, MSG_FLAG_INVALID, row, PfNameUtility.deleteFlag(mospParams));
	}
	
	/**
	 * %1%には、0か1を入力してください。(PFW0127)
	 * @param mospParams MosP処理情報
	 * @param fieldName  対象フィールド名
	 * @param row        対象行インデックス
	 */
	public static void addErrorFlagInvalid(MospParams mospParams, String fieldName, Integer row) {
		MessageUtility.addErrorMessage(mospParams, MSG_FLAG_INVALID, row, fieldName);
	}
	
	/**
	 * %1%には、%2%以上を入力してください。(PFW0128)
	 * @param mospParams MosP処理情報
	 * @param fieldName  対象フィールド名
	 * @param limit      対象限界値
	 * @param row        対象行インデックス
	 */
	public static void addErrorUnderLimit(MospParams mospParams, String fieldName, int limit, Integer row) {
		MessageUtility.addErrorMessage(mospParams, MSG_UNDER_LIMIT, row, fieldName, String.valueOf(limit));
	}
	
	/**
	 * %1%には、%2%以下を入力してください。(PFW0129)
	 * @param mospParams MosP処理情報
	 * @param fieldName  対象フィールド名
	 * @param limit      対象限界値
	 * @param row        対象行インデックス
	 */
	public static void addErrorOverLimit(MospParams mospParams, String fieldName, String limit, Integer row) {
		MessageUtility.addErrorMessage(mospParams, MSG_OVER_LIMIT, row, fieldName, limit);
	}
	
	/**
	 * %1%には、%2%以下を入力してください。(PFW0129)
	 * @param mospParams MosP処理情報
	 * @param fieldName  対象フィールド名
	 * @param limit      対象限界値
	 * @param row        対象行インデックス
	 */
	public static void addErrorOverLimit(MospParams mospParams, String fieldName, int limit, Integer row) {
		addErrorOverLimit(mospParams, fieldName, String.valueOf(limit), row);
	}
	
	/**
	 * %1%には、%2%以下を入力してください。(PFW0129)
	 * @param mospParams MosP処理情報
	 * @param fieldName  対象フィールド名
	 * @param limit      対象限界値
	 */
	public static void addErrorOverLimit(MospParams mospParams, String fieldName, int limit) {
		MessageUtility.addErrorMessage(mospParams, MSG_OVER_LIMIT, fieldName, String.valueOf(limit));
	}
	
	/**
	 * %1%は、整数部%2%桁以内、小数部%3%桁以内で入力してください。(PFW0130)<br>
	 * <br>
	 * @param mospParams   MosP処理情報
	 * @param fieldName    対象フィールド名
	 * @param integerDigit 整数部桁数
	 * @param decimalDigit 小数部桁数
	 * @param row          対象行インデックス
	 */
	public static void addErrorInvalidDecimal(MospParams mospParams, String fieldName, int integerDigit,
			int decimalDigit, Integer row) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_INVALID_DECIMAL, row, fieldName, String.valueOf(integerDigit),
				String.valueOf(decimalDigit));
	}
	
	/**
	 * %1%には存在する日付を正しい形式(例：2017/04/01)で入力してください。(PFW0132)<br>
	 * @param mospParams MosP処理情報
	 * @param fieldName  フィールド名称
	 * @param row        行インデックス
	 */
	public static void addErrorDateInvalid(MospParams mospParams, String fieldName, Integer row) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_DATE_INVALID, row, fieldName);
	}
	
	/**
	 * %1%は%2%桁以内で入力してください。(PFW0140)<br>
	 * <br>
	 * @param mospParams MosP処理情報
	 * @param fieldName  対象フィールド名
	 * @param maxDigit   最大桁数
	 * @param row        対象行インデックス
	 */
	public static void addErrorOverDigit(MospParams mospParams, String fieldName, int maxDigit, Integer row) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_OVER_DIGIT, row, fieldName, MospUtility.getString(maxDigit));
	}
	
	/**
	 * 該当する社員が存在しません。(PFW0201)<br>
	 * <br>
	 * @param mospParams MosP処理情報
	 */
	public static void addErrorEmployeeNotExist(MospParams mospParams) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_NO_ITEM, PfNameUtility.employee(mospParams));
	}
	
	/**
	 * コード：%1%は、社員：%2%が使用しています。社員情報を変更してください。(PFW0207)<br>
	 * <br>
	 * @param mospParams   MosP処理情報
	 * @param code         コード
	 * @param employeeCode 社員コード
	 */
	public static void addErrorCodeIsUsed(MospParams mospParams, String code, String employeeCode) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_CODE_IS_USED, code, employeeCode);
	}
	
	/**
	 * コード：%1%は、所属：%2%が経路として使用しています。所属：%2%の上位所属を変更してください。(PFW0208)<br>
	 * <br>
	 * @param mospParams   MosP処理情報
	 * @param code         コード(%1%)
	 * @param sectionCode  所属コード(%2%)
	 */
	public static void addErrorSectionIsUsed(MospParams mospParams, String code, String sectionCode) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_SECTION_IS_USED, code, sectionCode);
	}
	
	/**
	 * 自らを経路に含めることはできません。上位所属を変更してください。(PFW0209)<br>
	 * <br>
	 * @param mospParams   MosP処理情報
	 */
	public static void addErrorRouteContainsItself(MospParams mospParams) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_ROUTE_CONTAINS_ITSELF);
	}
	
	/**
	 * コード：%2%は、有効日以前の情報が存在しないため、一括更新できません。個別に履歴追加をしてください。(PFW0210)<br>
	 * <br>
	 * @param mospParams MosP処理情報
	 * @param code       コード
	 */
	public static void addErrorCodeNotExistBeforeDate(MospParams mospParams, String code) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_NO_CODE_BEFORE_DATE, code);
	}
	
	/**
	 * ユーザID：%2%は存在しないため、登録できません。(PFW0214)<br>
	 * <br>
	 * @param mospParams MosP処理情報
	 * @param userId ユーザID
	 * @param row    行インデックス
	 */
	public static void addErrorSelectedUserIdNotExist(MospParams mospParams, String userId, Integer row) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_SELECTED_CODE_NOT_EXIST, row, PfNameUtility.userId(mospParams),
				userId);
	}
	
	/**
	 * ロールコード：%2%は存在しないため、登録できません。(PFW0214)<br>
	 * @param mospParams MosP処理情報
	 * @param roleCode   ロールコード
	 * @param row        行インデックス
	 */
	public static void addErrorRoleCodeNotExist(MospParams mospParams, String roleCode, Integer row) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_SELECTED_CODE_NOT_EXIST, row,
				PfNameUtility.roleCode(mospParams), roleCode);
	}
	
	/**
	 * ワークフロー処理に失敗しました。ワークフローを確認してください。(PFW0229)<br>
	 * <br>
	 * @param mospParams MosP処理情報
	 */
	public static void addErrorWorkflowProcessFailed(MospParams mospParams) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_WORKFLOW_PROCESS_FAILED);
	}
	
	/**
	 * 該当する%1%が存在しません。(PFW0201)<br>
	 * @param mospParams MosP処理情報
	 * @param name       項目名
	 * @param row        行インデックス
	 */
	public static void addErrorNoItem(MospParams mospParams, String name, Integer row) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_NO_ITEM, row, name);
	}
	
	/**
	 * 該当する%1%が存在しません。(PFW0201)<br>
	 * @param mospParams MosP処理情報
	 * @param name       項目名
	 */
	public static void addErrorNoItem(MospParams mospParams, String name) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_NO_ITEM, name);
	}
	
	/**
	 * 該当するユーザが存在しません。(PFW0201)<br>
	 * @param mospParams MosP処理情報
	 */
	public static void addErrorNoUser(MospParams mospParams) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_NO_ITEM, PfNameUtility.user(mospParams));
	}
	
	/**
	 * 該当するロールが存在しません。(PFW0201)<br>
	 * @param mospParams MosP処理情報
	 * @param row        行インデックス
	 */
	public static void addErrorNoRole(MospParams mospParams, Integer row) {
		addErrorNoItem(mospParams, PfNameUtility.role(mospParams), row);
	}
	
	/**
	 * 該当するロール区分が存在しません。(PFW0201)<br>
	 * @param mospParams MosP処理情報
	 * @param row        行インデックス
	 */
	public static void addErrorNoRoleType(MospParams mospParams, Integer row) {
		addErrorNoItem(mospParams, PfNameUtility.roleType(mospParams), row);
	}
	
	/**
	 * 該当する人事情報が存在しません。(PFW0201)<br>
	 * @param mospParams MosP処理情報
	 * @param row        行インデックス
	 */
	public static void addErrorNoHuman(MospParams mospParams, Integer row) {
		addErrorNoItem(mospParams, PfNameUtility.humanInfo(mospParams), row);
	}
	
	/**
	 * 該当するエクスポート情報が存在しません。(PFW0201)<br>
	 * @param mospParams MosP処理情報
	 */
	public static void addErrorNoExportInfo(MospParams mospParams) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_NO_ITEM, PfNameUtility.exportInfo(mospParams));
	}
	
	/**
	 * 該当する社員は退職しています。(PFW0202)<br>
	 * @param mospParams MosP処理情報
	 * @param row        行インデックス
	 */
	public static void addErrorEmployeeRetired(MospParams mospParams, Integer row) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_EMPLOYEE_IS, row, PfNameUtility.retirement(mospParams));
	}
	
	/**
	 * 該当する社員は退職しています。(PFW0202)<br>
	 * @param mospParams MosP処理情報
	 */
	public static void addErrorEmployeeRetired(MospParams mospParams) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_EMPLOYEE_IS, PfNameUtility.retirement(mospParams));
	}
	
	/**
	 * 該当する社員は休職しています。(PFW0202)<br>
	 * @param mospParams MosP処理情報
	 * @param row        行インデックス
	 */
	public static void addErrorEmployeeSuspended(MospParams mospParams, Integer row) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_EMPLOYEE_IS, row, PfNameUtility.suspension(mospParams));
	}
	
	/**
	 * 該当する社員は休職しています。(PFW0202)<br>
	 * @param mospParams MosP処理情報
	 */
	public static void addErrorEmployeeSuspended(MospParams mospParams) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_EMPLOYEE_IS, PfNameUtility.suspension(mospParams));
	}
	
	/**
	 * 該当する社員は入社していません。(PFW0203)<br>
	 * @param mospParams MosP処理情報
	 */
	public static void addErrorEmployeeNotJoin(MospParams mospParams) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_EMPLOYEE_IS_NOT, PfNameUtility.entrance(mospParams));
	}
	
	/**
	 * そのコードは既に存在しているため、別のコードを入力してください。(PFW0204)<br>
	 * @param mospParams MosP処理情報
	 */
	public static void addErrorDuplicate(MospParams mospParams) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_CODE_DUPLICATE);
	}
	
	/**
	 * その有効日における履歴が既に存在しているため、別の有効日を入力してください。(PFW0205)<br>
	 * @param mospParams MosP処理情報
	 */
	public static void addErrorHistoryDuplicate(MospParams mospParams) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_HISTRY_DUPLICATE);
	}
	
	/**
	 * その情報は他のユーザによって更新されました。再度検索して、確認してください。(PFW0206)<br>
	 * @param mospParams MosP処理情報
	 */
	public static void addErrorExclusive(MospParams mospParams) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_EXCLUSIVE);
	}
	
	/**
	 * targetDate時点で、必須ロールコード：%2%を設定しているユーザが、存在しなくなってしまいます。(PFW0211)<br>
	 * @param mospParams MosP処理情報
	 * @param targetDate 対象日
	 */
	public static void addErrorNoNeededRole(MospParams mospParams, Date targetDate) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_NO_NEEDED_ROLE, DateUtility.getStringDate(targetDate),
				RoleUtility.getNeededRole(mospParams));
	}
	
	/**
	 * 社員コード：%1%は入社日が登録されていないため、アカウントを作成できません。(PFW0212)<br>
	 * @param mospParams   MosP処理情報
	 * @param employeeCode 社員コード
	 */
	public static void addErrorEmployeeNotJoinForAccount(MospParams mospParams, String employeeCode) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_NOT_JOIN_FOR_ACCOUNT, employeeCode);
	}
	
	/**
	 * %1%：%2%は%3%時点で無効となります。(PFW0213)<br>
	 * @param mospParams MosP処理情報
	 * @param fieldName  対象フィールド名
	 * @param targetCode 対象コード
	 * @param targetDate 対象日
	 * @param row        行インデックス
	 */
	public static void addErrorInactive(MospParams mospParams, String fieldName, String targetCode, Date targetDate,
			Integer row) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_INACTIVE_AT_THAT_TIME, row, fieldName, targetCode,
				DateUtility.getStringDate(targetDate));
	}
	
	/**
	 * %1%：%2%は%3%時点で無効となります。(PFW0213)<br>
	 * @param mospParams MosP処理情報
	 * @param fieldName  対象フィールド名
	 * @param targetCode 対象コード
	 * @param targetDate 対象日
	 */
	public static void addErrorInactive(MospParams mospParams, String fieldName, String targetCode, Date targetDate) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_INACTIVE_AT_THAT_TIME, fieldName, targetCode,
				DateUtility.getStringDate(targetDate));
	}
	
	/**
	 * %1%：%2%は存在しないため、登録できません。(PFW0214)<br>
	 * @param mospParams MosP処理情報
	 * @param fieldName  対象フィールド名
	 * @param targetCode 対象コード
	 * @param row        行インデックス
	 */
	public static void addErrorNotExist(MospParams mospParams, String fieldName, String targetCode, Integer row) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_SELECTED_CODE_NOT_EXIST, row, fieldName, targetCode);
	}
	
	/**
	 * %1%：%2%は存在しないため、登録できません。(PFW0214)<br>
	 * @param mospParams MosP処理情報
	 * @param fieldName  対象フィールド名
	 * @param targetCode 対象コード
	 */
	public static void addErrorNotExist(MospParams mospParams, String fieldName, String targetCode) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_SELECTED_CODE_NOT_EXIST, fieldName, targetCode);
	}
	
	/**
	 * %1%：%2%は既に存在しているため、登録できません。(PFW0215)<br>
	 * @param mospParams MosP処理情報
	 * @param fieldName  フィールド名
	 * @param code       コード
	 * @param row        行インデックス
	 */
	public static void addErrorSelectedCodeExist(MospParams mospParams, String fieldName, String code, Integer row) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_SELECTED_CODE_EXIST, row, fieldName, code);
	}
	
	/**
	 * %1%：%2%は既に存在しているため、登録できません。(PFW0215)<br>
	 * @param mospParams MosP処理情報
	 * @param fieldName  フィールド名
	 * @param code       コード
	 */
	public static void addErrorSelectedCodeExist(MospParams mospParams, String fieldName, String code) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_SELECTED_CODE_EXIST, fieldName, code);
	}
	
	/**
	 * ユーザID：userIdは既に存在しているため、登録できません。(PFW0215)<br>
	 * @param mospParams MosP処理情報
	 * @param userId     ユーザID
	 */
	public static void addErrorUserIdExist(MospParams mospParams, String userId) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_SELECTED_CODE_EXIST, PfNameUtility.userId(mospParams), userId);
	}
	
	/**
	 * 社員コード：employeeCodeは既に存在しているため、登録できません。(PFW0215)<br>
	 * @param mospParams   MosP処理情報
	 * @param employeeCode 社員コード
	 */
	public static void addErrorEmployeeCodeExist(MospParams mospParams, String employeeCode) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_SELECTED_CODE_EXIST, PfNameUtility.employeeCode(mospParams),
				employeeCode);
	}
	
	/**
	 * 該当する社員は既に退職しているため、入社情報を削除できません。(PFW0216)<br>
	 * @param mospParams MosP処理情報
	 */
	public static void addErrorDeleteEntrance(MospParams mospParams) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_DELETE_ENTRANCE);
	}
	
	/**
	 * %1%は%2%より後となるようにしてください。(PFW0217)<br>
	 * @param mospParams MosP処理情報
	 * @param afterName  順序が後の項目の名称
	 * @param beforeName 順序が前の項目の名称
	 * @param row        行インデックス
	 */
	public static void addErrorOrderInvalid(MospParams mospParams, String afterName, String beforeName, Integer row) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_ORDER_INVALID, row, afterName, beforeName);
	}
	
	/**
	 * %1%は%2%より後となるようにしてください。(PFW0217)<br>
	 * @param mospParams MosP処理情報
	 * @param afterName  順序が後の項目の名称
	 * @param beforeName 順序が前の項目の名称
	 */
	public static void addErrorOrderInvalid(MospParams mospParams, String afterName, String beforeName) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_ORDER_INVALID, afterName, beforeName);
	}
	
	/**
	 * %1%期間が重複しないように入力してください。(PFW0218)<br>
	 * @param mospParams MosP処理情報
	 * @param fieldName  フィールド名
	 */
	public static void addErrorTermOverlap(MospParams mospParams, String fieldName) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_TERM_OVERLAP, fieldName);
	}
	
	/**
	 * YYYY/MM/DDにおける社員コード：%2%の履歴情報は存在しません。(PFW0219)<br>
	 * @param mospParams   MosP処理情報
	 * @param targetDate   対象日
	 * @param employeeCode 社員コード
	 */
	public static void addErrorEmployeeHistoryNotExist(MospParams mospParams, Date targetDate, String employeeCode) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_EMP_HISTORY_NOT_EXIST, DateUtility.getStringDate(targetDate),
				employeeCode);
	}
	
	/**
	 * 有効な%1%が存在しません。(PFW0220)<br>
	 * @param mospParams MosP処理情報
	 * @param fieldName  フィールド名
	 */
	public static void addErrorValidDataNotExist(MospParams mospParams, String fieldName) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_VALID_DATA_NOT_EXIST, fieldName);
	}
	
	/**
	 * 有効な個人基本情報が存在しません。(PFW0220)<br>
	 * @param mospParams MosP処理情報
	 */
	public static void addErrorPersonalBasisInfoNotExist(MospParams mospParams) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_VALID_DATA_NOT_EXIST,
				PfNameUtility.personalBasisInfo(mospParams));
	}
	
	/**
	 * コード：%1%は、ルート：%2%が使用しています。ルート：%2%のルート設定を変更してください。(PFW0222)<br>
	 * @param mospParams MosP処理情報
	 * @param unitCode   ユニットコード
	 * @param routeCode  ルートコード
	 */
	public static void addErrorUnitIsUsed(MospParams mospParams, String unitCode, String routeCode) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_UNIT_IS_USED, unitCode, routeCode);
	}
	
	/**
	 * コード：%1%は、ルート適用：%2%が使用しています。ルート適用：%2%のルートを変更してください。(PFW0223)<br>
	 * @param mospParams           MosP処理情報
	 * @param routeCode            ルートコード
	 * @param routeApplicationCode ルート適用コード
	 */
	public static void addErrorRouteIsUsed(MospParams mospParams, String routeCode, String routeApplicationCode) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_ROUTE_IS_USED, routeCode, routeApplicationCode);
	}
	
	/**
	 * その社員コード：%1%は既に設定されています。コード：%2%を履歴編集、または履歴追加して設定してください。(PFW0224)<br>
	 * @param mospParams    MosP処理情報
	 * @param employeeCode  社員コード
	 * @param code          コード
	 */
	public static void addErrorEmployeeDuplicate(MospParams mospParams, String employeeCode, String code) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_EMPLOYEE_DUPLICATE, employeeCode, code);
	}
	
	/**
	 * その適用範囲は、既に設定されています。コード：%1%より、履歴を追加して設定してください。(PFW0225)<br>
	 * @param mospParams      MosP処理情報
	 * @param applicationCode 適用コード
	 */
	public static void addErrorApplicationDuplicate(MospParams mospParams, String applicationCode) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_APPLICATION_DUPLICATE, applicationCode);
	}
	
	/**
	 * YYYY/MM/DDにおける社員コード：%2%の情報を参照する権限がありません。(PFW0226)<br>
	 * @param mospParams    MosP処理情報
	 * @param targetDate   対象日
	 * @param employeeCode 社員コード
	 */
	public static void addErrorNoAuthorityToRefer(MospParams mospParams, Date targetDate, String employeeCode) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_NO_AUTHORITY, DateUtility.getStringDate(targetDate),
				employeeCode, PfNameUtility.reference(mospParams));
	}
	
	/**
	 * %1%を取得できませんでした。%2%を変更してください。(PFW0227)<br>
	 * @param mospParams MosP処理情報
	 * @param object     取得できなかった情報
	 * @param cause      変更すべき内容
	 */
	public static void addErrorCnaNotGet(MospParams mospParams, String object, String cause) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_CAN_NOT_GET, object, cause);
	}
	
	/**
	 * ユニットコード：%1%は、YYYY/MM/DD時点で承認者が存在しません。(PFW0228)<br>
	 * @param mospParams MosP処理情報
	 * @param unitCode   ユニットコード
	 * @param targetDate 対象日
	 */
	public static void addErrorUnitHaveNoApprover(MospParams mospParams, String unitCode, Date targetDate) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_UNIT_HAVE_NO_APPROVER, unitCode,
				DateUtility.getStringDate(targetDate));
	}
	
	/**
	 * YYYY/MM/DD時点では社員コード：%2%は入社していません。日付または社員コードを選択し直してください。(PFW0230)<br>
	 * @param mospParams   MosP処理情報
	 * @param targetDate   対象日
	 * @param employeeCode 社員コード
	 */
	public static void addErrorEmployeeNotEntered(MospParams mospParams, Date targetDate, String employeeCode) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_EMPLOYEE_NOT_ENTERED, DateUtility.getStringDate(targetDate),
				employeeCode);
	}
	
	/**
	 * YYYY/MM/DD時点では社員コード：%2%は退職しています。日付または社員コードを選択し直してください。(PFW0231)<br>
	 * @param mospParams   MosP処理情報
	 * @param targetDate   対象日
	 * @param employeeCode 社員コード
	 */
	public static void addErrorEmployeeRetired(MospParams mospParams, Date targetDate, String employeeCode) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_EMPLOYEE_RETIRED, DateUtility.getStringDate(targetDate),
				employeeCode);
	}
	
	/**
	 * YYYY/MM/DDにおける対象ユーザの履歴情報は存在しません。(PFW0232)<br>
	 * @param mospParams   MosP処理情報
	 * @param activateDate 有効日
	 * @param row          行インデックス
	 */
	public static void addErrorUserHistoryNotExist(MospParams mospParams, Date activateDate, Integer row) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_HISTORY_NOT_EXIST, row,
				DateUtility.getStringDate(activateDate), PfNameUtility.targetUser(mospParams));
	}
	
	/**
	 * YYYY/MM/DDにおける%2%の履歴情報は存在しません。(PFW0232)<br>
	 * @param mospParams MosP処理情報
	 * @param targetDate 対象日
	 * @param fieldName  フィールド名称
	 */
	public static void addErrorHistoryNotExist(MospParams mospParams, Date targetDate, String fieldName) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_HISTORY_NOT_EXIST, DateUtility.getStringDate(targetDate),
				fieldName);
	}
	
	/**
	 * YYYY/MM/DDにおける対象社員の履歴情報は存在しません。(PFW0232)<br>
	 * @param mospParams   MosP処理情報
	 * @param activateDate 有効日
	 */
	public static void addErrorEmployeeHistoryNotExist(MospParams mospParams, Date activateDate) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_HISTORY_NOT_EXIST, DateUtility.getStringDate(activateDate),
				PfNameUtility.targetEmployee(mospParams));
	}
	
	/**
	 * 検索条件を指定してください。(PFW0234)<br>
	 * @param mospParams MosP処理情報
	 */
	public static void addErrorSearchCondition(MospParams mospParams) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_SEARCH_CONDITION);
	}
	
	/**
	 * ユーザ情報が取得できないため、ログアウトしました。(PFW0235)<br>
	 * @param mospParams MosP処理情報
	 */
	public static void addErrorUserNotExist(MospParams mospParams) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_USER_NOT_EXIST);
	}
	
	/**
	 * YYYY/MM/DDは異なる申請により登録された住所情報が存在するため、操作できません。(PFW0236)<br>
	 * @param mospParams   MosP処理情報
	 * @param activateDate 有効日
	 * @param row          行インデックス
	 */
	public static void addErrorOtherAddressExist(MospParams mospParams, Date activateDate, Integer row) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_OTHER_DATA_EXIST, row, DateUtility.getStringDate(activateDate),
				PfNameUtility.addressInfo(mospParams));
	}
	
	/**
	 * YYYY/MM/DDは異なる申請により登録された電話情報が存在するため、操作できません。(PFW0236)<br>
	 * @param mospParams   MosP処理情報
	 * @param activateDate 有効日
	 * @param row          行インデックス
	 */
	public static void addErrorOtherPhoneExist(MospParams mospParams, Date activateDate, Integer row) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_OTHER_DATA_EXIST, row, DateUtility.getStringDate(activateDate),
				PfNameUtility.phoneInfo(mospParams));
	}
	
	/**
	 * ユーザIDとメールアドレスの組み合わせが、正しくありません。(PFW0238)<br>
	 * @param mospParams MosP処理情報
	 */
	public static void addErrorInvalidUserAndAddress(MospParams mospParams) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_INVALID_PAIR, PfNameUtility.userId(mospParams),
				PfNameUtility.mailAddress(mospParams));
	}
	
	/**
	 * %1%のロールを付加されたユーザ数が上限値(%2%)を超えないようにしてください。(PFW0239)<br>
	 * @param mospParams MosP処理情報
	 * @param roleName   ロール名称
	 * @param limit      上限値
	 */
	public static void addErrorExceedUsers(MospParams mospParams, String roleName, int limit) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_EXCEED_USERS, roleName, String.valueOf(limit));
	}
	
	/**
	 * %1%：%2%には、正しい形式の値を設定してください。(PFW0240)<br>
	 * @param mospParams MosP処理情報
	 * @param name       項目名
	 * @param value      値
	 * @param row          行インデックス
	 */
	public static void addErrorInvalidValue(MospParams mospParams, String name, String value, Integer row) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_INVALID_VALUE, row, name, value);
	}
	
	/**
	 * メールアドレス：%2%には、正しい形式の値を設定してください。(PFW0240)<br>
	 * @param mospParams MosP処理情報
	 * @param value      値
	 */
	public static void addErrorInvalidMailAddresss(MospParams mospParams, String value) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_INVALID_VALUE, PfNameUtility.mailAddress(mospParams), value);
	}
	
	/**
	 * %1%しました。<br>
	 * <br>
	 * @param mospParams  MosP処理情報
	 * @param processName 処理名称
	 * @return メッセージ
	 */
	public static String getSucceed(MospParams mospParams, String processName) {
		return mospParams.getMessage(MSG_I_PROCESS_SUCCEED, processName);
	}
	
	/**
	 * 下書しました。<br>
	 * <br>
	 * @param mospParams MosP処理情報
	 * @return メッセージ
	 */
	public static String getDraftSucceed(MospParams mospParams) {
		return mospParams.getMessage(MSG_I_PROCESS_SUCCEED, PfNameUtility.draft(mospParams));
	}
	
	/**
	 * 取下しました。<br>
	 * <br>
	 * @param mospParams MosP処理情報
	 * @return メッセージ
	 */
	public static String getWithdrawSucceed(MospParams mospParams) {
		return mospParams.getMessage(MSG_I_PROCESS_SUCCEED, PfNameUtility.withdraw(mospParams));
	}
	
	/**
	 * 対象データが存在しません。<br>
	 * <br>
	 * @param mospParams MosP処理情報
	 * @return メッセージ
	 */
	public static String getNoData(MospParams mospParams) {
		return mospParams.getMessage(MSG_I_NO_DATA);
	}
	
	/**
	 * ユーザIDあるいはパスワードに誤りがあります。(PFW9111)<br>
	 * @param mospParams MosP処理情報
	 */
	public static void addErrorAuthFailed(MospParams mospParams) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_AUTH_FAILED);
	}
	
	/**
	 * パスワードは前回入力値と違うものを入力してください。<br>
	 * @param mospParams MosP処理情報
	 */
	public static void addErrorPasswordUnchanged(MospParams mospParams) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_PASSWORD_UNCHANGED);
	}
	
	/**
	 * パスワードの有効期限が切れています。パスワードを更新してください。<br>
	 * @param mospParams MosP処理情報
	 */
	public static void addErrorPasswordExpire(MospParams mospParams) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_PASSWORD_EXPIRE);
	}
	
	/**
	 * 現在のパスワードに誤りがあります。<br>
	 * @param mospParams MosP処理情報
	 */
	public static void addErrorOldPassword(MospParams mospParams) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_OLD_PASSWORD);
	}
	
	/**
	 * 新しいパスワードとパスワード入力確認が異なります。<br>
	 * @param mospParams MosP処理情報
	 */
	public static void addErrorNewPassword(MospParams mospParams) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_NEW_PASSWORD);
	}
	
	/**
	 * パスワードは英字と数字を組み合わせたものを設定してください。<br>
	 * @param mospParams MosP処理情報
	 * @return メッセージ
	 */
	public static String getWarningPasswordChar(MospParams mospParams) {
		return mospParams.getMessage(MSG_W_PASSWORD_CHAR);
	}
	
	/**
	 * パスワードは初期パスワードと異なるものを設定してください。<br>
	 * @param mospParams MosP処理情報
	 */
	public static void addErrorPasswordInit(MospParams mospParams) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_PASSWORD_INIT);
	}
	
	/**
	 * パスワードは初期パスワードと異なるものを設定してください。<br>
	 * @param mospParams MosP処理情報
	 * @return メッセージ
	 */
	public static String getWarningPasswordInit(MospParams mospParams) {
		return mospParams.getMessage(MSG_W_PASSWORD_INIT);
	}
	
	/**
	 * パスワードは%2%文字以上を入力してください。<br>
	 * @param mospParams  MosP処理情報
	 * @param minPassword パスワード最低文字数
	 * @return メッセージ
	 */
	public static String getWarningPasswordMin(MospParams mospParams, String minPassword) {
		return mospParams.getMessage(MSG_W_CHECK_MIN_LENGTH, PfNameUtility.password(mospParams), minPassword);
	}
	
	/**
	 * 社員コード：%1%には%2%が設定されているため、削除することは出来ません。(PFW9118)<br>
	 * <br>
	 * @param mospParams   MosP処理情報
	 * @param employeeCode 社員コード
	 * @param fieldName    対象フィールド名
	 */
	public static void addErrorHumanCanNotDelete(MospParams mospParams, String employeeCode, String fieldName) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_HUMAN_CAN_NOT_DELETE, employeeCode, fieldName);
	}
	
	/**
	 * 利用可能文字列リストをメッセージ用文字列に変換する。<br>
	 * <br>
	 * ""の場合は、空白と出力する。<br>
	 * <br>
	 * @param mospParams MosP処理情報
	 * @param availables 利用可能文字列リスト
	 * @return メッセージ用文字列
	 */
	public static String getAvailableCharString(MospParams mospParams, List<String> availables) {
		// 配列を準備
		String[] array = new String[availables.size()];
		// インデックス準備
		int i = 0;
		// 利用可能文字毎に処理
		for (String available : availables) {
			// ""の場合は空白に変換
			array[i++] = available.equals(MospConst.STR_EMPTY) ? PfNameUtility.blank(mospParams) : available;
		}
		return MospUtility.toSeparatedString(array, PfNameUtility.touten(mospParams));
	}
	
}
