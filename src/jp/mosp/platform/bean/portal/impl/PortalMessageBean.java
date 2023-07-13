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
package jp.mosp.platform.bean.portal.impl;

import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.HtmlUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.bean.message.MessageReferenceBeanInterface;
import jp.mosp.platform.bean.portal.PortalBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dto.message.MessageDtoInterface;
import jp.mosp.platform.portal.action.PortalAction;

/**
 * ポータル用メッセージ処理クラス。<br>
 */
public class PortalMessageBean extends PortalBean implements PortalBeanInterface {
	
	/**
	 * パス(ポータル用メッセージJSP)。
	 */
	protected static final String	PATH_PORTAL_VIEW						= "/jsp/platform/portal/portalMessage.jsp";
	
	/**
	 * MosPアプリケーション設定キー(ポータルメッセージ表示件数)。
	 */
	protected static final String	APP_PORTAL_MESSAGE_COUNT				= "PortalMessageCount";
	
	/**
	 * MosPアプリケーション設定キー(ポータルメッセージ比較クラス)。
	 */
	protected static final String	APP_PORTAL_MESSAGE_COMPARATORS			= "PortalMessageComparators";
	
	/**
	 * ポータルパラメータキー(メッセージ公開開始日)。
	 */
	public static final String		PRM_ARY_LBL_MESSAGE_DATE				= "aryLblMessageDate";
	
	/**
	 * ポータルパラメータキー(メッセージNo)。
	 */
	public static final String		PRM_ARY_LBL_MESSAGE_NO					= "aryLblMessageNo";
	
	/**
	 * ポータルパラメータキー(メッセージ区分)。
	 */
	public static final String		PRM_ARY_LBL_MESSAGE_TYPE				= "aryLblMessageType";
	
	/**
	 * ポータルパラメータキー(重要度)。
	 */
	public static final String		PRM_ARY_LBL_MESSAGE_IMPORTANCE			= "aryLblMessageImportance";
	
	/**
	 * ポータルパラメータキー(重要度スタイル)。
	 */
	public static final String		PRM_ARY_LBL_MESSAGE_IMPORTANCE_STYLE	= "aryLblMessageImportanceStyle";
	
	/**
	 * ポータルパラメータキー(メッセージタイトル)。
	 */
	public static final String		PRM_ARY_LBL_MESSAGE_TITLE				= "aryLblMessageTitle";
	
	/**
	 * ポータルパラメータキー(メッセージ)。
	 */
	public static final String		PRM_ARY_LBL_MESSAGE						= "aryLblMessage";
	
	/**
	 * ポータルパラメータキー(他メッセージ数)。
	 */
	public static final String		PRM_LBL_MESSAGE_COUNT					= "lblMessageCount";
	
	/**
	 * スタイル文字列(赤)。
	 */
	public static final String		STYLE_RED								= "style=\"color: red\"";
	
	/**
	 * スタイル文字列(山吹)。
	 */
	public static final String		STYLE_YAMABUKI							= "style=\"color: #f8b500\"";
	
	
	/**
	 * {@link PortalBean#PortalBean()}を実行する。<br>
	 */
	public PortalMessageBean() {
		super();
	}
	
	@Override
	public void initBean() {
		// 処理無し
	}
	
	@Override
	public void show() throws MospException {
		// ポータル用JSPパス追加
		addPortalViewList(PATH_PORTAL_VIEW);
		// メッセージリスト未設定あるいは再表示コマンドの場合
		if (getPortalParameter(PRM_LBL_MESSAGE_COUNT) == null
				|| mospParams.getCommand().equals(PortalAction.CMD_RE_SHOW)) {
			// メッセージリストを取得しVOに設定
			setVoList(getMessageList(), true);
		}
	}
	
	@Override
	public void regist() throws MospException {
		// メッセージリストを取得し全件VOに設定
		setVoList(getMessageList(), false);
	}
	
	/**
	 * メッセージリストを取得する。
	 * @return メッセージリスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected List<MessageDtoInterface> getMessageList() throws MospException {
		// メッセージ参照クラス準備
		MessageReferenceBeanInterface message = (MessageReferenceBeanInterface)createBean(
				MessageReferenceBeanInterface.class);
		// メッセージ取得(ログインユーザの個人ID及びシステム日付)
		List<MessageDtoInterface> list = message.getMessageList(mospParams.getUser().getPersonalId(), getSystemDate());
		// ポータルメッセージ比較クラス毎にソート
		for (String comparator : getPortalMessageComparators()) {
			// ソート
			sortList(list, comparator, true);
		}
		return list;
	}
	
	/**
	 * 検索結果リストの内容をVOに設定する。<br>
	 * @param list     対象リスト
	 * @param isCapped 上限フラグ(true：上限有り、false：全件表示)
	 */
	protected void setVoList(List<MessageDtoInterface> list, boolean isCapped) {
		// 表示件数確認
		int count = list.size();
		// 非表示件数準備
		int ohterMessageCount = 0;
		// 上限フラグ確認
		if (isCapped) {
			// ポータルメッセージ表示件数取得及び確認
			int portalMessageCount = mospParams.getApplicationProperty(APP_PORTAL_MESSAGE_COUNT, 0);
			if (portalMessageCount > 0 && count > portalMessageCount) {
				// 非表示件数設定
				ohterMessageCount = count - portalMessageCount;
				// 表示件数再設定
				count = portalMessageCount;
			}
		}
		// データ配列初期化
		String[] aryLblMessageDate = new String[count];
		String[] aryLblMessageNo = new String[count];
		String[] aryLblMessageType = new String[count];
		String[] aryLblMessageImportance = new String[count];
		String[] aryLblMessageImportanceStyle = new String[count];
		String[] aryLblMessageTitle = new String[count];
		String[] aryLblMessage = new String[count];
		// データ作成
		for (int i = 0; i < count; i++) {
			// リストから情報を取得
			MessageDtoInterface dto = list.get(i);
			// 配列に情報を設定
			aryLblMessageDate[i] = DateUtility.getStringDateAndDay(dto.getStartDate());
			aryLblMessageNo[i] = dto.getMessageNo();
			aryLblMessageType[i] = getCodeName(dto.getMessageType(), PlatformConst.CODE_KEY_MESSAGE_TYPE);
			aryLblMessageImportance[i] = getCodeName(dto.getMessageImportance(),
					PlatformConst.CODE_KEY_MESSAGE_IMPORTANCE_MARK);
			aryLblMessageImportanceStyle[i] = "";
			if (dto.getMessageImportance() == 1) {
				aryLblMessageImportanceStyle[i] = STYLE_RED;
			} else if (dto.getMessageImportance() == 3) {
				aryLblMessageImportanceStyle[i] = STYLE_YAMABUKI;
			}
			aryLblMessageTitle[i] = dto.getMessageTitle();
			aryLblMessage[i] = getMessageBody(dto);
		}
		// データをVOに設定
		putPortalParameters(PRM_ARY_LBL_MESSAGE_DATE, aryLblMessageDate);
		putPortalParameters(PRM_ARY_LBL_MESSAGE_NO, aryLblMessageNo);
		putPortalParameters(PRM_ARY_LBL_MESSAGE_TYPE, aryLblMessageType);
		putPortalParameters(PRM_ARY_LBL_MESSAGE_IMPORTANCE, aryLblMessageImportance);
		putPortalParameters(PRM_ARY_LBL_MESSAGE_IMPORTANCE_STYLE, aryLblMessageImportanceStyle);
		putPortalParameters(PRM_ARY_LBL_MESSAGE_TITLE, aryLblMessageTitle);
		putPortalParameters(PRM_ARY_LBL_MESSAGE, aryLblMessage);
		putPortalParameter(PRM_LBL_MESSAGE_COUNT, String.valueOf(ohterMessageCount));
	}
	
	/**
	 * MosP設定情報からポータルメッセージ比較クラス群(クラス名)を取得する。<br>
	 * @return ポータルメッセージ比較クラス群(クラス名)
	 */
	protected String[] getPortalMessageComparators() {
		// ポータルメッセージ比較クラス群(クラス名)取得
		String portalBeans = mospParams.getApplicationProperty(APP_PORTAL_MESSAGE_COMPARATORS);
		// 分割
		return MospUtility.split(portalBeans, MospConst.APP_PROPERTY_SEPARATOR);
	}
	
	/**
	 * メッセージ本文を取得する。<br>
	 * @param dto 対象DTO
	 * @return メッセージ本文
	 */
	protected String getMessageBody(MessageDtoInterface dto) {
		if (dto == null || dto.getMessageBody() == null) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		// 改行で分割
		String[] lines = dto.getMessageBody().split(MospConst.LINE_SEPARATOR);
		for (String line : lines) {
			sb.append(getMessageLine(line));
			sb.append(MospConst.LINE_SEPARATOR);
		}
		return sb.toString();
	}
	
	/**
	 * メッセージ行を取得する。<br>
	 * @param messageLine 対象メッセージ行
	 * @return メッセージ行
	 */
	protected String getMessageLine(String messageLine) {
		StringBuffer sb = new StringBuffer();
		// http(s)://で始まる箇所で分割
		String[] uris = messageLine.split("(?<=.)(?=https?://)");
		for (String target : uris) {
			if (target.startsWith("http://") || target.startsWith("https://")) {
				// http(s)://から始まる場合
				String uri = target.trim();
				sb.append(getAnchor(uri));
				sb.append(HtmlUtility.escapeHTML(target.substring(uri.length())));
			} else {
				sb.append(HtmlUtility.escapeHTML(target));
			}
		}
		return sb.toString();
	}
	
	/**
	 * アンカータグ取得。<br>
	 * @param uri URI
	 * @return アンカータグHTML文字列
	 */
	protected String getAnchor(String uri) {
		if (validateURI(uri)) {
			// URIが妥当である場合
			StringBuffer sb = new StringBuffer();
			sb.append("<a onclick=\"openWindow('");
			sb.append(uri);
			sb.append("')\">");
			sb.append(HtmlUtility.escapeHTML(uri));
			sb.append("</a>");
			return sb.toString();
		}
		// URIが妥当でない場合
		return HtmlUtility.escapeHTML(uri);
	}
	
	/**
	 * URIの妥当性を確認する。<br>
	 * @param uri URI
	 * @return 妥当な場合true、そうでない場合false
	 */
	protected boolean validateURI(String uri) {
		// http(s)://から始まり1文字以上続く場合はtrue
		return uri.matches("^https?://.+");
	}
	
}
