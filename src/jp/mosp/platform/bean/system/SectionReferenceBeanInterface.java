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
package jp.mosp.platform.bean.system;

import java.util.Date;
import java.util.List;
import java.util.Set;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dto.system.SectionDtoInterface;

/**
 * 所属マスタ参照インターフェース。
 */
public interface SectionReferenceBeanInterface extends BaseBeanInterface {
	
	/**
	 * 履歴一覧取得。
	 * <p>
	 * 所属コードから所属マスタリストを取得。
	 * </p>
	 * @param sectionCode 所属コード
	 * @return 所属マスタリスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<SectionDtoInterface> getSectionHistory(String sectionCode) throws MospException;
	
	/**
	 * 上位所属一覧を取得する。<br>
	 * 所属コードと対象年月日から所属マスタリストを取得する。<br>
	 * 階層順に並んでおり、最上位の所属が最後のレコードとなる。<br>
	 * 引数の所属コードは、リストには含まれない。<br>
	 * @param sectionCode 所属コード
	 * @param targetDate  対象年月日
	 * @return 所属マスタリスト(階層順)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<SectionDtoInterface> getHigherSectionList(String sectionCode, Date targetDate) throws MospException;
	
	/**
	 * 所属マスタ取得。
	 * <p>
	 * 所属コードと対象日から所属マスタを取得。
	 * </p>
	 * @param sectionCode 所属コード
	 * @param targetDate 対象年月日
	 * @return 所属マスタ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	SectionDtoInterface getSectionInfo(String sectionCode, Date targetDate) throws MospException;
	
	/**
	 * 所属名称を取得する。<br><br>
	 * 対象となる所属情報が存在しない場合は、所属コードを返す。<br>
	 * @param sectionCode 所属コード
	 * @param targetDate 対象年月日
	 * @return 所属名称
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String getSectionName(String sectionCode, Date targetDate) throws MospException;
	
	/**
	 * 所属略称を取得する。<br><br>
	 * 対象となる所属情報が存在しない場合は、所属コードを返す。<br>
	 * @param sectionCode 所属コード
	 * @param targetDate 対象年月日
	 * @return 所属略称
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String getSectionAbbr(String sectionCode, Date targetDate) throws MospException;
	
	/**
	 * 所属表示名称を取得する。<br><br>
	 * 対象となる所属情報が存在しない場合は、所属コードを返す。<br>
	 * @param sectionCode 所属コード
	 * @param targetDate 対象年月日
	 * @return 所属表示名称
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String getSectionDisplay(String sectionCode, Date targetDate) throws MospException;
	
	/**
	 * 経路表示内容を取得する。<br>
	 * @param dto        経路表示対象DTO
	 * @param targetDate 略称取得対象日付
	 * @return 経路表示内容
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String getClassRouteAbbr(SectionDtoInterface dto, Date targetDate) throws MospException;
	
	/**
	 * 経路名称配列を取得する。<br>
	 * @param sectionCode 対象所属コード
	 * @param targetDate  対象日付
	 * @return 経路名称配列
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String[] getClassRouteNameArray(String sectionCode, Date targetDate) throws MospException;
	
	/**
	 * 所属プルダウン表示内容設定から、所属表示名称を利用するかどうかを取得する。<br>
	 * @return 所属表示名称利用設定(true：利用、false：利用しない)
	 */
	boolean useDisplayName();
	
	/**
	 * プルダウン用配列を取得する。<br>
	 * 対象年月日からプルダウン用配列を取得する。<br>
	 * 操作区分に操作範囲が設定されている場合は、取得内容が制限される。<br>
	 * 表示内容は、所属の略称。<br>
	 * @param targetDate    対象年月日
	 * @param needBlank     空白行要否(true：空白行要、false：空白行不要)
	 * @param operationType 操作区分
	 * @return プルダウン用配列
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String[][] getSelectArray(Date targetDate, boolean needBlank, String operationType) throws MospException;
	
	/**
	 * プルダウン用配列を取得する。<br>
	 * 対象年月日からプルダウン用配列を取得する。<br>
	 * 操作区分に操作範囲が設定されている場合は、取得内容が制限される。<br>
	 * 表示内容は、所属の名称。<br>
	 * @param targetDate    対象年月日
	 * @param needBlank     空白行要否(true：空白行要、false：空白行不要)
	 * @param operationType 操作区分
	 * @return プルダウン用配列
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String[][] getNameSelectArray(Date targetDate, boolean needBlank, String operationType) throws MospException;
	
	/**
	 * プルダウン用配列を取得する。<br>
	 * 対象年月日からプルダウン用配列を取得する。<br>
	 * 操作区分に操作範囲が設定されている場合は、取得内容が制限される。<br>
	 * 表示内容は、コード + 所属名称。<br>
	 * @param targetDate    対象年月日
	 * @param needBlank     空白行要否(true：空白行要、false：空白行不要)
	 * @param operationType 操作区分
	 * @return プルダウン用配列
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String[][] getCodedSelectArray(Date targetDate, boolean needBlank, String operationType) throws MospException;
	
	/**
	 * プルダウン用配列を取得する。<br>
	 * 対象年月日からプルダウン用配列を取得する。<br>
	 * 操作区分に操作範囲が設定されている場合は、取得内容が制限される。<br>
	 * 表示内容は、コード + 所属略称。<br>
	 * @param targetDate    対象年月日
	 * @param needBlank     空白行要否(true：空白行要、false：空白行不要)
	 * @param operationType 操作区分
	 * @return プルダウン用配列
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String[][] getCodedAbbrSelectArray(Date targetDate, boolean needBlank, String operationType) throws MospException;
	
	/**
	 * プルダウン(経路表示)用配列を取得する。<br>
	 * 対象年月日からプルダウン用配列を取得する。<br>
	 * @param targetDate 対象年月日
	 * @return プルダウン用配列
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String[][] getSelectArrayForMaintenance(Date targetDate) throws MospException;
	
	/**
	 * 所属マスタからレコードを取得する。<br>
	 * 所属コード、有効日で合致するレコードが無い場合、nullを返す。<br>
	 * @param sectionCode  所属コード
	 * @param activateDate 有効日
	 * @return 所属マスタDTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	SectionDtoInterface findForKey(String sectionCode, Date activateDate) throws MospException;
	
	/**
	 * 所属マスタ取得。
	 * <p>
	 * レコード識別IDから所属マスタを取得。
	 * </p>
	 * @param id レコード識別ID
	 * @return 所属マスタ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	SectionDtoInterface findForkey(long id) throws MospException;
	
	/**
	 * 最も階層の多い所属の階層数を取得する。<br>
	 * 対象年月日における所属マスタ内で有効な所属のみを、対象とする。<br>
	 * @param targetDate 対象年月日
	 * @return 最も階層の多い所属の階層数
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	int getMaxLevel(Date targetDate) throws MospException;
	
	/**
	 * 対象所属コードの経路情報から、対象階層の所属コードを取得する。<br>
	 * 対象階層の所属コードが存在しない場合は、空白を返す。<br>
	 * @param sectionCode 対象所属コード
	 * @param targetDate  対象年月日
	 * @param level       対象階層
	 * @return 所属コード
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String getHigherSectionCode(String sectionCode, Date targetDate, int level) throws MospException;
	
	/**
	 * 所属コード群を取得する。<br>
	 * <br>
	 * 所属コードFromと所属コードToで、所属コード群を取得する。<br>
	 * <br>
	 * @param sectionCodeFrom 所属コードFrom
	 * @param sectionCodeTo   所属コードTo
	 * @param activateDate    有効日
	 * @return 所属コード群
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Set<String> getSectionCodeSetForCodeRange(String sectionCodeFrom, String sectionCodeTo, Date activateDate)
			throws MospException;
	
	/**
	 * 経路文字列から所属コードの配列を取得する。
	 * @param dto 対象DTO
	 * @return 所属コードの配列
	 */
	String[] getClassRouteArray(SectionDtoInterface dto);
	
}
