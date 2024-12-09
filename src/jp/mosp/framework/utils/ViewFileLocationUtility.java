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
package jp.mosp.framework.utils;

import java.util.Locale;

import jp.mosp.framework.base.BaseVo;
import jp.mosp.framework.base.MospParams;

/**
 * MosPフレームワーク用ファイルパスユーティリティ
 * <p>	2008/11/17	</p>
 * @author m.yoshida
 * @since		1.1.0
 * @version		0.0.3
 */
public class ViewFileLocationUtility {
	
	/**
	 * jspディレクトリ名
	 */
	private static final String	JSP_DIR			= "/jsp";
	
	/**
	 * jspファイル拡張子
	 */
	private static final String	JSP_FILE		= ".jsp";
	
	/**
	 * cssディレクトリ名
	 */
	private static final String	CSS_DIR			= "/css";
	
	/**
	 * cssファイル拡張子
	 */
	private static final String	CSS_FILE		= ".css";
	
	/**
	 * jsディレクトリ名
	 */
	private static final String	JS_DIR			= "/js";
	
	/**
	 * jsファイル拡張子
	 */
	private static final String	JS_FILE			= ".js";
	
	/**
	 * pubディレクトリ名
	 */
	private static final String	PUB_DIR			= "/pub";
	
	/**
	 * 分割用正規表現
	 */
	private static final String	REG_SPRIT		= "\\.";
	
	/**
	 * MosP標準用名称
	 */
	//private static final String MOSP		= "MosP";
	
	/**
	 * MosPパッケージ用名称
	 */
	private static final String	MOSP_PACKAGE	= "mosp";
	
	/**
	 * Voクラス名称
	 */
	private static final String	VO				= "Vo";
	
	/**
	 * jspファイルパス
	 */
	private String				retUrl;
	
	/**
	 * cssファイルパス
	 */
	private String				extraCss;
	
	/**
	 * jsファイルパス
	 */
	private String				extraJs;
	
	/**
	 * ファイルパス生成用StringBuffer
	 */
	private StringBuffer		sb;
	
	/**
	 * ディレクトリ名
	 */
	private String				dir;
	
	/**
	 * ファイル名
	 */
	private String				file;
	
	
	/**
	 * コンストラクタ
	 */
	public ViewFileLocationUtility() {
		retUrl = "";
		extraJs = "";
		extraCss = "";
		sb = new StringBuffer();
		dir = "";
		file = "";
	}
	
	/**
	 * コンストラクタ
	 * @param classname	対象Voクラス名
	 * <p> コンストラクタのパラメータに対象VOのクラス名{@link BaseVo#getClassName()}をセットし呼び出した後、<br />
	 * {@link MospParams#setArticleUrl(String)}にJSPのファイルパス{@link ViewFileLocationUtility#getRetUrl()}、<br />
	 * {@link MospParams#addCssFile(String)}にCSSのファイルパス{@link ViewFileLocationUtility#getExtraCss()}、<br />
	 * {@link MospParams#addJsFile(String)}にJSのファイルパス{@link ViewFileLocationUtility#getExtraJs()}を<br />
	 * それぞれセットすることで、BaseVoを継承している対象VOにファイルパスを記述しなくても呼び出します。
	 * </p>
	 * <p>
	 * 規約として、対象VOが存在するvoパッケージに最も近いパッケージ
	 * (例:jp.mosp.common.vo.MosPLoginVo.javaの場合はcommon)を<br />
	 * 対象ディレクトリとし、ファイル名はクラス名の頭文字を小文字に変更し、「Vo」を除いたものを利用します。<br />
	 * <s>MosP標準のVoの場合に利用する「MosP」の文字列も除きます。</s><br />
	 * （v1.1.25からMosPの文字列も使用できるように変更いたしました。）<br />
	 * </p>
	 * <p>
	 * (例：jp.mosp.common.vo.MosPLoginVo.javaの場合)<br />
	 * jspファイルパス = <s>"/jsp/<b>common</b>/<b>login</b>.jsp"</s>
	 * →"/jsp/<b>common</b>/<b>mosPLogin</b>.jsp"
	 * <br />
	 * cssファイルパス = <s>"/pub/<b>common</b>/css/<b>login</b>.css"</s>
	 * →"/pub/<b>common</b>/css/<b>mosPLogin</b>.css"
	 * <br />
	 * jsファイルパス = <s>"/pub/<b>common</b>/js/<b>login</b>.js"</s>
	 * →"/pub/<b>common</b>/js/<b>mosPLogin</b>.js"
	 * <br />
	 * </p>
	 * <p>
	 * オプションパッケージ、もしくはカスタマイズパッケージも規約に沿って<br>
	 * ファイルを設置すれば自動的に生成します。
	 * </p>
	 * <p>
	 * (例：jp.mosp.option.test.vo.TestVo.javaの場合)<br />
	 * jspファイルパス = "/jsp/<b>option/test</b>/<b>test</b>.jsp"<br />
	 * cssファイルパス = "/pub/<b>option/test</b>/css/<b>test</b>.css"<br />
	 * jsファイルパス = "/pub/<b>option/test</b>/js/<b>test</b>.js"<br />
	 * </p>
	 */
	public ViewFileLocationUtility(String classname) {
		setFilePath(classname);
	}
	
	/**
	 * ファイルパス生成
	 * @param classname	対象Voクラス名
	 * <p>
	 * オプション機能に対応
	 * </p>
	 */
	public void setFilePath(String classname) {
		String[] aryPKG = classname.split(REG_SPRIT);
		// ディレクトリ名生成
		setDir(aryPKG);
		// クラス名からファイル名を生成
		String tmpfile = aryPKG[aryPKG.length - 1];
		/*
		 * 		2009/06/18	m.yoshida	mosp-base対応
		 */
		/*		
		if (tmpfile.startsWith(MOSP)) {
			tmpfile = tmpfile.split(MOSP)[1];
		}
		 */
		if (tmpfile.lastIndexOf(VO) == tmpfile.length() - 2) {
			tmpfile = tmpfile.substring(0, tmpfile.length() - 2);
		}
		setFile(convHeadUpperCaseToLowerCase(tmpfile));
		createRetUrl();
		createExtraCss();
		createExtraJs();
	}
	
	/**
	 * ディレクトリ名生成
	 * @param aryPKG パッケージ文字列配列
	 */
	private void setDir(String[] aryPKG) {
		sb = new StringBuffer();
		/*
		 * 		2009/05/25	m.yoshida	多段パッケージに対応
		 */
		/*
		// 企業パッケージ
		String bizPKG = aryPKG[aryPKG.length -4];
		// 機能パッケージ
		String functionPKG = aryPKG[aryPKG.length -3];
		// パッケージ判別
		if (!bizPKG.equals(MOSP_PACKAGE)) {
			sb.append(bizPKG);
			sb.append("/");
		}
		sb.append(functionPKG);
		 */
		sb.append(getFunctionDir(aryPKG));
		setDir(sb.toString());
	}
	
	/**
	 * ディレクトリ名生成
	 * <p>	2009/05/25	m.yoshida	多段パッケージに対応	</p>
	 * @param aryPKG パッケージ文字列配列
	 * @return	ディレクトリ名
	 */
	private StringBuffer getFunctionDir(String[] aryPKG) {
		StringBuffer dirName = new StringBuffer();
		// 開始
		int start = 2;
		// 終了
		int end = aryPKG.length - 2;
		// jp.co、jp.mospパッケージ以外の場合
		if (!aryPKG[1].equals("co") && !aryPKG[1].equals(MOSP_PACKAGE)) {
			start = 1;
		}
		for (int i = start; i < end; i++) {
			dirName.append(aryPKG[i]);
			if (i != end - 1) {
				dirName.append("/");
			}
		}
		return dirName;
	}
	
	/**
	 * jspファイルパス生成
	 */
	private void createRetUrl() {
		sb = new StringBuffer();
		sb.append(JSP_DIR);
		sb.append("/");
		sb.append(getDir());
		sb.append("/");
		sb.append(getFile());
		sb.append(JSP_FILE);
		setRetUrl(sb.toString());
	}
	
	/**
	 * cssファイルパス生成
	 */
	private void createExtraCss() {
		sb = new StringBuffer();
		sb.append(PUB_DIR);
		sb.append("/");
		sb.append(getDir());
		sb.append(CSS_DIR);
		sb.append("/");
		sb.append(getFile());
		sb.append(CSS_FILE);
		setExtraCss(sb.toString());
	}
	
	/**
	 * jsファイルパス生成
	 */
	private void createExtraJs() {
		sb = new StringBuffer();
		sb.append(PUB_DIR);
		sb.append("/");
		sb.append(getDir());
		sb.append(JS_DIR);
		sb.append("/");
		sb.append(getFile());
		sb.append(JS_FILE);
		setExtraJs(sb.toString());
	}
	
	/**
	 * jspファイルパスgetter
	 * @return	retUrl	jspファイルパス
	 */
	public String getRetUrl() {
		return retUrl;
	}
	
	/**
	 * jspファイルパスsetter
	 * @param retUrl	jspファイルパス
	 */
	private void setRetUrl(String retUrl) {
		this.retUrl = retUrl;
	}
	
	/**
	 * cssファイルパスgetter
	 * @return	extraCss	cssファイルパス
	 */
	public String getExtraCss() {
		return extraCss;
	}
	
	/**
	 * cssファイルパスsetter
	 * @param extraCss	cssファイルパス
	 */
	private void setExtraCss(String extraCss) {
		this.extraCss = extraCss;
	}
	
	/**
	 * jsファイルパスgetter
	 * @return	extraJs	jsファイルパス
	 */
	public String getExtraJs() {
		return extraJs;
	}
	
	/**
	 * jsファイルパスsetter
	 * @param extraJs	jsファイルパス
	 */
	private void setExtraJs(String extraJs) {
		this.extraJs = extraJs;
	}
	
	/**
	 * ディレクトリ名getter
	 * @return	dir	ディレクトリ名
	 */
	private String getDir() {
		return dir;
	}
	
	/**
	 * ディレクトリ名setter
	 * @param dir	ディレクトリ名
	 */
	private void setDir(String dir) {
		this.dir = dir;
	}
	
	/**
	 * ファイル名getter
	 * @return	file	ファイル名
	 */
	private String getFile() {
		return file;
	}
	
	/**
	 * ファイル名setter
	 * @param file	ファイル名
	 */
	private void setFile(String file) {
		this.file = file;
	}
	
	/**
	 * 先頭文字列変換
	 * @param str 対象文字列
	 * @return 先頭の大文字を小文字に変換後の対象文字列
	 */
	private String convHeadUpperCaseToLowerCase(String str) {
		StringBuffer conv = new StringBuffer(str);
		String head = conv.substring(0, 1);
		conv = conv.delete(0, 1);
		head = head.toLowerCase(Locale.getDefault());
		conv.insert(0, head);
		return conv.toString();
	}
	
}
