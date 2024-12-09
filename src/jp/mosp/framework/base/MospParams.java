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
package jp.mosp.framework.base;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jp.mosp.framework.constant.ExceptionConst;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.log.LoggerInterface;
import jp.mosp.framework.property.CodeProperty;
import jp.mosp.framework.property.CommandProperty;
import jp.mosp.framework.property.MospProperties;
import jp.mosp.framework.property.RangeProperty;

/**
 * MosPパラメータクラス。<br>
 * <br>
 * ActionとBeanHandlerやBeanの間で、処理情報を共有するためのクラス。<br>
 */
public class MospParams {
	
	/**
	 * MosPアプリケーション設定キー(エラービューパス)
	 */
	protected static final String			APP_PATH_ERROR_VIEW				= "PathErrorView";
	
	/**
	 * MosPアプリケーション設定キー(承認者ユニット利用可否)。
	 */
	public static final String				APP_USE_TARGET_APPROVAL_UNIT	= "UseTargetApprovalUnit";
	
	/**
	 * MosP設定情報。
	 */
	private MospProperties					properties;
	
	/**
	 * ログ出力クラス群。
	 */
	private Map<String, LoggerInterface>	loggers;
	
	/**
	 * MosPセッション保持情報。
	 */
	private MospStoredInfo					storedInfo;
	
	/**
	 * コマンド。
	 */
	private String							command;
	
	/**
	 * 連続実行コマンド。<br>
	 * 連続実行コマンドを設定した場合、{@link Controller}はクライアントに
	 * レスポンスを返さず設定したコマンドが実行される。<br>
	 * 連続実行コマンドを設定した場合、{@link MospParams#url}の設定は無効になる。<br>
	 */
	private String							nextCommand;
	
	/**
	 * 連続実行回数。<br>
	 * 連続実行コマンドを実行する毎にカウントアップされる。<br>
	 */
	private int								nextCount;
	
	/**
	 * VO。
	 */
	private BaseVo							vo;
	
	/**
	 * フォワード先URL。
	 */
	private String							url;
	
	/**
	 * ナビURL(template.jsp用)。
	 */
	private String							naviUrl;
	
	/**
	 * 内容URL(template.jsp用)。
	 */
	private String							articleUrl;
	
	/**
	 * JavaScriptファイルURL。
	 */
	private List<String>					jsFiles;
	
	/**
	 * CSSファイルURL。
	 */
	private List<String>					cssFiles;
	
	/**
	 * 出力ファイルオブジェクト。<br>
	 * これを設定すると、このオブジェクトがレスポンスとして送出される。<br>
	 */
	private Object							file;
	
	/**
	 * 出力ファイル名。<br>
	 * 出力ファイルに名称が必要な場合に設定する。<br>
	 */
	private String							fileName;
	
	/**
	 * リダイレクトURL。<br>
	 * これを設定すると、このリダイレクトとしてがレスポンスとして送出される。<br>
	 */
	private String							redirect;
	
	/**
	 * コントローラにより発行された処理シーケンス。<br>
	 * JSPで利用する。<br>
	 */
	private int								procSeq;
	
	/**
	 * リクエストパラメータマップ。
	 */
	private Map<String, String[]>			requestParamsMap;
	
	/**
	 * リクエストファイルマップ。
	 */
	private Map<String, InputStream>		requestFilesMap;
	
	/**
	 * メッセージリスト。
	 */
	private List<String>					messageList;
	
	/**
	 * エラーメッセージリスト。
	 */
	private List<String>					errorMessageList;
	
	/**
	 * 汎用パラメータマップ。
	 */
	private Map<String, Object>				generalParamsMap;
	
	/**
	 * APIパラメータリスト。
	 */
	private List<String>					apiParams;
	
	
	/**
	 * MosP処理情報を初期化する。<br>
	 * @param properties MosP設定情報
	 * @param loggers    ログ出力クラス群
	 */
	public MospParams(MospProperties properties, Map<String, LoggerInterface> loggers) {
		this.properties = properties;
		this.loggers = loggers;
		storedInfo = new MospStoredInfo();
		storedInfo.setRangeMap(new HashMap<String, RangeProperty>());
		messageList = new ArrayList<String>();
		errorMessageList = new ArrayList<String>();
		generalParamsMap = new HashMap<String, Object>();
		apiParams = new ArrayList<String>();
		jsFiles = new ArrayList<String>();
		cssFiles = new ArrayList<String>();
		nextCount = 0;
	}
	
	/**
	 * パラメータを受け取る。<br><br>
	 * 連続実行時に{@link Controller}で利用される。
	 * @param params 受け取り元MosPパラメータ
	 */
	public void inheritParams(MospParams params) {
		command = params.nextCommand;
		generalParamsMap = params.getGeneralParamsMap();
		apiParams = params.getApiParams();
		nextCount = ++params.nextCount;
		messageList = new ArrayList<String>(params.getMessageList());
		errorMessageList = new ArrayList<String>(params.getErrorMessageList());
	}
	
	/**
	 * @return user
	 */
	public MospUser getUser() {
		return storedInfo.getUser();
	}
	
	/**
	 * @param user セットする user
	 */
	public void setUser(MospUser user) {
		storedInfo.setUser(user);
	}
	
	/**
	 * @return properties
	 */
	public MospProperties getProperties() {
		return properties;
	}
	
	/**
	 * @param properties セットする properties
	 */
	public void setProperties(MospProperties properties) {
		this.properties = properties;
	}
	
	/**
	 * @return storedInfo
	 */
	public MospStoredInfo getStoredInfo() {
		return storedInfo;
	}
	
	/**
	 * @param storedInfo セットする storedInfo
	 */
	public void setStoredInfo(MospStoredInfo storedInfo) {
		this.storedInfo = storedInfo;
	}
	
	/**
	 * @return command
	 */
	public String getCommand() {
		return command;
	}
	
	/**
	 * @param command セットする command
	 */
	public void setCommand(String command) {
		this.command = command;
	}
	
	/**
	 * コマンド設定情報を取得する。
	 * @return コマンド設定情報
	 * @throws MospException MosPコマンド設定情報が取得できなかった場合
	 */
	public CommandProperty getCommandProperty() throws MospException {
		CommandProperty commandProperty = properties.getCommandProperty(command);
		if (commandProperty == null) {
			// エラー画面設定
			setErrorViewUrl();
			// コマンド確認
			if (command == null) {
				command = "";
			}
			// 例外発行
			throw new MospException(ExceptionConst.EX_COMMAND_NONE, command);
		}
		return commandProperty;
	}
	
	/**
	 * @return nextCommand
	 */
	public String getNextCommand() {
		return nextCommand;
	}
	
	/**
	 * @param nextCommand セットする nextCommand
	 */
	public void setNextCommand(String nextCommand) {
		this.nextCommand = nextCommand;
	}
	
	/**
	 * @return nextCount
	 */
	public int getNextCount() {
		return nextCount;
	}
	
	/**
	 * @param nextCount セットする nextCount
	 */
	public void setNextCount(int nextCount) {
		this.nextCount = nextCount;
	}
	
	/**
	 * @return vo
	 */
	public BaseVo getVo() {
		return vo;
	}
	
	/**
	 * @param vo セットする vo
	 */
	public void setVo(BaseVo vo) {
		this.vo = vo;
	}
	
	/**
	 * @return url
	 */
	public String getUrl() {
		return url;
	}
	
	/**
	 * @param url セットする url
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	
	/**
	 * @return naviUrl
	 */
	public String getNaviUrl() {
		return naviUrl;
	}
	
	/**
	 * @param naviUrl セットする naviUrl
	 */
	public void setNaviUrl(String naviUrl) {
		this.naviUrl = naviUrl;
	}
	
	/**
	 * @return articleUrl
	 */
	public String getArticleUrl() {
		return articleUrl;
	}
	
	/**
	 * @param articleUrl セットする articleUrl
	 */
	public void setArticleUrl(String articleUrl) {
		this.articleUrl = articleUrl;
	}
	
	/**
	 * @param requestParamsMap セットする parameterMap
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setRequestParamsMap(Map requestParamsMap) {
		this.requestParamsMap = new HashMap<String, String[]>(requestParamsMap);
	}
	
	/**
	 * @return requestParamsMap
	 */
	public Map<String, String[]> getRequestParamsMap() {
		return requestParamsMap;
	}
	
	/**
	 * @return requestFilesMap
	 */
	public Map<String, InputStream> getRequestFilesMap() {
		return requestFilesMap;
	}
	
	/**
	 * @param requestFilesMap セットする requestFilesMap
	 */
	public void setRequestFilesMap(Map<String, InputStream> requestFilesMap) {
		this.requestFilesMap = requestFilesMap;
	}
	
	/**
	 * @return file
	 */
	public Object getFile() {
		return file;
	}
	
	/**
	 * @param file セットする file
	 */
	public void setFile(Object file) {
		this.file = file;
	}
	
	/**
	 * @return fileName
	 */
	public String getFileName() {
		return fileName;
	}
	
	/**
	 * @param fileName セットする fileName
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	/**
	 * @return redirect
	 */
	public String getRedirect() {
		return redirect;
	}
	
	/**
	 * @param redirect セットする redirect
	 */
	public void setRedirect(String redirect) {
		this.redirect = redirect;
	}
	
	/**
	 * @return procSeq
	 */
	public int getProcSeq() {
		return procSeq;
	}
	
	/**
	 * @param procSeq セットする procSeq
	 */
	public void setProcSeq(int procSeq) {
		this.procSeq = procSeq;
	}
	
	/**
	 * @return messageList
	 */
	public List<String> getMessageList() {
		return messageList;
	}
	
	/**
	 * メッセージを取得する。
	 * @param key 対象キー
	 * @param replacements 置換文字列
	 * @return メッセージ
	 */
	public String getMessage(String key, String... replacements) {
		return properties.getMessage(key, replacements);
	}
	
	/**
	 * メッセージ(メッセージコード付き)を取得する。
	 * @param key          対象キー
	 * @param replacements 置換文字列
	 * @return メッセージ
	 */
	public String getCodedMessage(String key, String... replacements) {
		String message = properties.getMessage(key, replacements);
		if (message.isEmpty()) {
			return message;
		}
		if (message.isEmpty()) {
			return message;
		}
		return message + MospConst.MESSAGE_L_PARENTHSIS + key + MospConst.MESSAGE_R_PARENTHSIS;
	}
	
	/**
	 * メッセージを追加する。
	 * @param key          メッセージ設定キー
	 * @param replacements 置換文字列
	 */
	public void addMessage(String key, String... replacements) {
		messageList.add(getMessage(key, replacements));
	}
	
	/**
	 * @return errorMessageList
	 */
	public List<String> getErrorMessageList() {
		return errorMessageList;
	}
	
	/**
	 * エラーメッセージを追加する。
	 * @param key          メッセージ設定キー
	 * @param replacements 置換文字列
	 */
	public void addErrorMessage(String key, String... replacements) {
		// メッセージを取得
		String messege = getCodedMessage(key, replacements);
		// エラーメッセージリストに含まれていない場合
		if (errorMessageList.contains(messege) == false) {
			// エラーメッセージリストに追加
			errorMessageList.add(messege);
		}
	}
	
	/**
	 * エラーメッセージの有無を確認する。
	 * @return エラーメッセージ有無(true：エラーメッセージ有り、false：無し)
	 */
	public boolean hasErrorMessage() {
		return errorMessageList.size() > 0;
	}
	
	/**
	 * 汎用パラメータマップを取得する。
	 * @return 汎用パラメータマップ
	 */
	public Map<String, Object> getGeneralParamsMap() {
		return generalParamsMap;
	}
	
	/**
	 * 汎用パラメータマップにパラメータを追加する。
	 * @param key   キー
	 * @param value 値
	 */
	public void addGeneralParam(String key, Object value) {
		generalParamsMap.put(key, value);
	}
	
	/**
	 * 汎用パラメータマップから値を取得する。
	 * @param key キー
	 * @return 値
	 */
	public Object getGeneralParam(String key) {
		return generalParamsMap.get(key);
	}
	
	/**
	 * 汎用パラメータマップからキーセットを取得する。
	 * @return 汎用パラメータマップキーセット
	 */
	public Set<String> getGeneralParamKeySet() {
		return generalParamsMap.keySet();
	}
	
	/**
	 * APIパラメータリストにパラメータを追加する。
	 * @param list APIパラメータリスト
	 */
	public void addApiParams(List<String> list) {
		apiParams.addAll(list);
	}
	
	/**
	 * APIパラメータリストを取得する。
	 * @return APIパラメータリスト
	 */
	public List<String> getApiParams() {
		return apiParams;
	}
	
	/**
	 * URLにエラービューパスを設定する。
	 */
	public void setErrorViewUrl() {
		url = properties.getApplicationProperty(APP_PATH_ERROR_VIEW);
	}
	
	/**
	 * URLがエラービューパスかどうかを確認する。
	 * @return true：エラービュー、false：エラービューでない
	 */
	public boolean isUrlErrorView() {
		if (url == null) {
			return false;
		}
		return url.equals(properties.getApplicationProperty(APP_PATH_ERROR_VIEW));
	}
	
	/**
	 * ユニット承認者対象設定の有無を取得する。
	 * @return 確認結果(true：承認者がユニット単位、false：承認者が個人ID単位)
	 */
	public boolean isTargetApprovalUnit() {
		return properties.getApplicationPropertyBool(APP_USE_TARGET_APPROVAL_UNIT);
	}
	
	/**
	 * @return jsFiles
	 */
	public List<String> getJsFiles() {
		return jsFiles;
	}
	
	/**
	 * @return cssFiles
	 */
	public List<String> getCssFiles() {
		return cssFiles;
	}
	
	/**
	 * CSSファイルパスを追加する。
	 * @param cssFile CSSファイルパス
	 */
	public void addCssFile(String cssFile) {
		cssFiles.add(cssFile);
	}
	
	/**
	 * JavaScriptファイルパスを追加する。
	 * @param jsFile JavaScriptファイルパス
	 */
	public void addJsFile(String jsFile) {
		jsFiles.add(jsFile);
	}
	
	/**
	 * リクエストパラメータを取得する。
	 * @param key キー
	 * @return リクエストパラメータ
	 */
	public String getRequestParam(String key) {
		// パラメータ取得
		String[] requestParams = getRequestParams(key);
		// パラメータ確認
		if (requestParams == null) {
			return null;
		}
		// パラメータ取得
		return requestParams[0];
	}
	
	/**
	 * リクエストパラメータを取得する。
	 * @param key キー
	 * @return リクエストパラメータ
	 */
	public String[] getRequestParams(String key) {
		// リクエストパラメータマップ確認
		if (requestParamsMap == null) {
			return null;
		}
		// パラメータ取得
		return requestParamsMap.get(key);
	}
	
	/**
	 * リクエストファイルを取得する。
	 * @param key キー
	 * @return リクエストファイル
	 */
	public InputStream getRequestFile(String key) {
		// リクエストファイルマップ確認
		if (requestFilesMap == null) {
			return null;
		}
		// ファイル取得
		return requestFilesMap.get(key);
	}
	
	/**
	 * 文言を取得する。
	 * @param key 対象キー
	 * @return 文言
	 */
	public String getName(String key) {
		return properties.getName(key);
	}
	
	/**
	 * 文言を取得する。
	 * @param key 対象キー
	 * @param keys 結合対象キー
	 * @return 結合された文言
	 */
	public String getName(String key, String... keys) {
		StringBuffer sb = new StringBuffer(getName(key));
		if (keys == null) {
			return sb.toString();
		}
		for (String concatKey : keys) {
			sb.append(getName(concatKey));
		}
		return sb.toString();
	}
	
	/**
	 * MosPアプリケーション設定情報を取得する。
	 * @param key 対象キー
	 * @return MosPアプリケーション設定情報
	 */
	public String getApplicationProperty(String key) {
		return properties.getApplicationProperty(key);
	}
	
	/**
	 * MosPアプリケーション設定情報配列を取得する。
	 * @param key 対象キー
	 * @return MosPアプリケーション設定情報配列
	 */
	public String[] getApplicationProperties(String key) {
		return properties.getApplicationProperties(key);
	}
	
	/**
	 * アプリケーション設定情報を数値で取得する。
	 * @param key 対象キー
	 * @param defaultValue 取得できなかった場合の値
	 * @return MosPアプリケーション設定情報
	 */
	public int getApplicationProperty(String key, int defaultValue) {
		return properties.getApplicationProperty(key, defaultValue);
	}
	
	/**
	 * アプリケーション設定情報を真偽値で取得する。
	 * @param key 対象キー
	 * @return 設定情報
	 */
	public boolean getApplicationPropertyBool(String key) {
		return properties.getApplicationPropertyBool(key);
	}
	
	/**
	 * ログ出力クラスを取得する。<br>
	 * @param loggerName ログ出力クラス名
	 * @return ログ出力クラス
	 */
	public LoggerInterface getLogger(String loggerName) {
		return loggers.get(loggerName);
	}
	
	/**
	 * ログ出力クラスを配置する。<br>
	 * @param loggerName 配置するログ出力クラス名
	 * @param logger     配置するログ出力クラス
	 */
	public void putLogger(String loggerName, LoggerInterface logger) {
		loggers.put(loggerName, logger);
	}
	
	/**
	 * {@link MospStoredInfo#getTopicPathList()}から保存VOを取得する。
	 * @param key キー
	 * @return VO
	 */
	public BaseVo getStoredVo(String key) {
		// パンくずリスト取得
		List<TopicPath> list = getTopicPathList();
		// パンくずリスト確認
		if (list == null) {
			return null;
		}
		// 戻値準備
		BaseVo baseVo = null;
		// 戻値確認
		for (TopicPath topicPath : list) {
			if (topicPath.getId().equals(key)) {
				// より新しいパンくずのVOで上書き
				baseVo = topicPath.getVo();
			}
		}
		return baseVo;
	}
	
	/**
	 * @return topicPath
	 */
	public List<TopicPath> getTopicPathList() {
		return storedInfo.getTopicPathList();
	}
	
	/**
	 * @param topicPathList セットする topicPathList
	 */
	public void setTopicPathList(List<TopicPath> topicPathList) {
		storedInfo.setTopicPathList(topicPathList);
	}
	
	/**
	 * コード設定情報群取得
	 * @return コード設定情報群
	 */
	public Map<String, CodeProperty> getCodeProperty() {
		return properties.getCodeProperty();
	}
	
}
