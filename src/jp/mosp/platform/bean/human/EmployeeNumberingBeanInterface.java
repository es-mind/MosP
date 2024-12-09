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
package jp.mosp.platform.bean.human;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;

/**
 * 社員コード採番インターフェース。<br>
 */
public interface EmployeeNumberingBeanInterface extends BaseBeanInterface {
	
	/**
	 * MosPアプリケーション設定キー(社員コード採番フォーマット)。<br>
	 * <br>
	 * 社員コード採番フォーマット設定値には、次の3つをカンマ区切で設定する。<br>
	 * ・フォーマット<br>
	 * ・最小値(数値)<br>
	 * ・最大値(数値)<br>
	 * <br>
	 * フォーマットには、次の制限がある。<br>
	 * ・10文字以内。<br>
	 * ・英数字。<br>
	 * ・数値となる箇所には0を指定。<br>
	 * ・頭にアルファベットを付けることができる。<br>
	 * <br>
	 * 例：MosP000000,0,100<br>
	 * この場合、MosP000000から順に～MosP000100まで採番できる。<br>
	 * <br>
	 */
	public static final String APP_EMPLOYEE_NUMBERING_FORMAT = "EmployeeNumeringFormat";
	
	
	/**
	 * 社員コード採番が利用可能であるかを確認する。<br>
	 * <br>
	 * MosPアプリケーション設定に社員コード採番フォーマットが設定されている場合、
	 * 利用可能であると判断する。<br>
	 * 設定されていないか空白である場合は、利用不可と判断する。<br>
	 * <br>
	 * @return 確認結果(true：利用可能、false：利用不可)
	 */
	boolean isEmployeeNumberingAvailable();
	
	/**
	 * 採番した新しい社員コードを取得する。<br>
	 * <br>
	 * 社員コード採番フォーマットに基づき、採番する。<br>
	 * <br>
	 * @return 採番した新しい社員コード
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String getNewEmployeeCode() throws MospException;
	
}
