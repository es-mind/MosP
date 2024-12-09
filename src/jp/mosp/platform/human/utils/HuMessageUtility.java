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
package jp.mosp.platform.human.utils;

import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.utils.MessageUtility;

/**
 * メッセージに関するユーティリティクラス。<br>
 * <br>
 * 人事管理においてサーバ側プログラムで作成されるメッセージは、
 * 全てこのクラスを通じて作成される。<br>
 * <br>
 */
public class HuMessageUtility {
	
	/**
	 * メッセージコード(妥当性確認(有効日))。<br>
	 * %1%をインポートするには有効日を必ず入力してください。<br>
	 */
	public static final String	MSG_W_GENERAL_NO_ACTIVATE_DATE	= "HUW0001";
	
	/**
	 * メッセージコード(妥当性確認(入力値))。<br>
	 * %1%の%2%は正しい値を入力してください。<br>
	 */
	public static final String	MSG_W_GENERAL_NOT_CORRECT		= "HUW0002";
	
	/**
	 * メッセージコード(画像注意)。<br>
	 * 画像ファイルとして扱われる(プレビュー表示がされる)のはPNG/JPEG/GIF形式のファイルのみとなります。<br>
	 */
	public static final String	MSG_I_GENERAL_PICTURE_EXTENSION	= "HUI0001";
	
	
	/**
	 * %1%をインポートするには有効日を必ず入力してください。(HGW0001)<br>
	 * <br>
	 * @param mospParams MosP処理情報
	 * @param fieldName  対象フィールド名
	 */
	public static void addErrorNoActivateDate(MospParams mospParams, String fieldName) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_GENERAL_NO_ACTIVATE_DATE, fieldName);
	}
	
	/**
	 * %1%の%2%は正しい値を入力してください。(HGW0002)<br>
	 * <br>
	 * @param mospParams   MosP処理情報
	 * @param divisionName 人事汎用管理区分名
	 * @param fieldName    対象フィールド名
	 */
	public static void addErrorNotCorrect(MospParams mospParams, String divisionName, String fieldName) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_GENERAL_NOT_CORRECT, divisionName, fieldName);
	}
	
	/**
	 * 画像ファイルとして扱われる(プレビュー表示がされる)のはPNG/JPEG/GIF形式のファイルのみとなります。<br>
	 * humnaBinary*Card.jspで利用される。<br>
	 * <br>
	 * @param mospParams  MosP処理情報
	 * @return メッセージ
	 */
	public static String getPictureExtension(MospParams mospParams) {
		return mospParams.getMessage(MSG_I_GENERAL_PICTURE_EXTENSION);
	}
	
}
