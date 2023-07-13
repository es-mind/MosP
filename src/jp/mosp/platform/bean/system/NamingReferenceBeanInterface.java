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
package jp.mosp.platform.bean.system;

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dto.system.NamingDtoInterface;

/**
 * 名称区分マスタ参照インターフェース。
 */
public interface NamingReferenceBeanInterface extends BaseBeanInterface {
	
	/**
	 * 名称区分マスタ取得。
	 * <p>
	 * 名称区分・名称項目コードと対象日から名称区分マスタを取得。
	 * </p>
	 * @param namingType 名称区分
	 * @param namingItemCode 名称項目コード
	 * @param targetDate 対象年月日
	 * @return 名称区分マスタ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	NamingDtoInterface getNamingItemInfo(String namingType, String namingItemCode, Date targetDate)
			throws MospException;
	
	/**
	 * 名称項目名を取得する。<br><br>
	 * 対象となる名称区分情報が存在しない場合は、空文字を返す。<br>
	 * @param namingType 名称区分
	 * @param namingItemCode 名称項目コード
	 * @param targetDate 対象年月日
	 * @return 名称項目名
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String getNamingItemName(String namingType, String namingItemCode, Date targetDate) throws MospException;
	
	/**
	 * 名称項目略称を取得する。<br><br>
	 * 対象となる名称区分・名称項目情報が存在しない場合は、名称項目コードを返す。<br>
	 * @param namingType 名称区分
	 * @param namingItemCode 名称項目コード
	 * @param targetDate 対象年月日
	 * @return 名称項目略称
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String getNamingItemAbbr(String namingType, String namingItemCode, Date targetDate) throws MospException;
	
	/**
	 * 名称項目一覧取得。
	 * <p>
	 * 名称区分・名称項目コードから名称区分マスタを取得。
	 * </p>
	 * @param namingType 名称区分
	 * @param namingItemCode 名称項目コード
	 * @return 名称区分マスタリスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<NamingDtoInterface> getNamingItemHistory(String namingType, String namingItemCode) throws MospException;
	
	/**
	 * 名称区分マスタからレコードを取得する。<br>
	 * 名称区分・名称項目コード、有効日で合致するレコードが無い場合、nullを返す。<br>
	 * @param namingType 名称区分
	 * @param namingItemCode 名称項目コード
	 * @param activateDate           有効日
	 * @return 名称区分マスタ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	NamingDtoInterface findForKey(String namingType, String namingItemCode, Date activateDate) throws MospException;
	
	/**
	 * プルダウン用配列を取得する。<br>
	 * 対象年月日からプルダウン用配列を取得する。<br>
	 * 表示内容は、コード + 名称区分名称。<br>
	 * @param namingType 名称区分
	 * @param targetDate 対象年月日
	 * @param needBlank 空白行要否(true：空白行要、false：空白行不要)
	 * @return プルダウン用配列
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String[][] getCodedSelectArray(String namingType, Date targetDate, boolean needBlank) throws MospException;
	
	/**
	 * プルダウン用配列を取得する。<br>
	 * 対象年月日からプルダウン用配列を取得する。<br>
	 * 表示内容は、名称区分名称。<br>
	 * <br>
	 * @param namingType 名称区分
	 * @param targetDate 対象年月日
	 * @param needBlank  空白行要否(true：空白行要、false：空白行不要)
	 * @return プルダウン用配列
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String[][] getSelectArray(String namingType, Date targetDate, boolean needBlank) throws MospException;
	
}
