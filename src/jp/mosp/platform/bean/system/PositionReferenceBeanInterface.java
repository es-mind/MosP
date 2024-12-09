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
import java.util.Map;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dto.system.PositionDtoInterface;

/**
 * 職位マスタ参照インターフェース
 */
public interface PositionReferenceBeanInterface extends BaseBeanInterface {
	
	/**
	 * 履歴一覧取得。
	 * <p>
	 * 職位コードから職位マスタリストを取得。
	 * </p>
	 * @param positionCode 職位コード
	 * @return 職位マスタリスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<PositionDtoInterface> getPositionHistory(String positionCode) throws MospException;
	
	/**
	 * 対象年月日における職位マスタリストを取得する。<br>
	 * 範囲操作権限が設定されている場合は、取得内容が制限される。<br>
	 * @param targetDate    対象日
	 * @param operationType 操作区分
	 * @return 職位マスタリスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<PositionDtoInterface> getPositionList(Date targetDate, String operationType) throws MospException;
	
	/**
	 * 対象年月日における職位マスタマップを取得する。<br>
	 * 操作区分に操作範囲が設定されている場合は、取得内容が制限される。<br>
	 * 有効な情報のみを取得する。<br>
	 * マップのキーは職位コード。<br>
	 * @param targetDate    対象日
	 * @param operationType 操作区分
	 * @return 職位マスタマップ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Map<String, PositionDtoInterface> getPositionMap(Date targetDate, String operationType) throws MospException;
	
	/**
	 * 職位マスタ取得。
	 * <p>
	 * 職位コードと対象年月日から職位マスタを取得。
	 * </p>
	 * @param positionCode 職位コード
	 * @param targetDate 対象年月日
	 * @return 職位マスタ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	PositionDtoInterface getPositionInfo(String positionCode, Date targetDate) throws MospException;
	
	/**
	 * 職位名称を取得する。<br><br>
	 * 対象となる職位情報が存在しない場合は、空文字列を返す。<br>
	 * @param positionCode 職位コード
	 * @param targetDate 対象年月日
	 * @return 職位名称
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String getPositionName(String positionCode, Date targetDate) throws MospException;
	
	/**
	 * 職位略称を取得する。<br><br>
	 * 対象となる職位情報が存在しない場合は、空文字列を返す。<br>
	 * @param positionCode 職位コード
	 * @param targetDate 対象年月日
	 * @return 職位略称
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String getPositionAbbr(String positionCode, Date targetDate) throws MospException;
	
	/**
	 * 職位マスタ取得。
	 * <p>
	 * レコード識別IDから職位マスタを取得。
	 * </p>
	 * @param id レコード識別ID
	 * @return 職位マスタ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	PositionDtoInterface findForkey(long id) throws MospException;
	
	/**
	 * 確認対象職位が基準等級に比べて優れているかを確認する。<br>
	 * @param dto   確認対象職位情報
	 * @param grade 基準等級
	 * @return 確認結果(true：優れている、false：優れていない(同等含む))
	 */
	boolean hasAdvantage(PositionDtoInterface dto, int grade);
	
	/**
	 * 等級の低い方が優れた職位であるかを確認する。
	 * @return 確認結果(true：等級の小さい方が優、false：等級の大きい方が優)
	 */
	boolean hasLowGradeAdvantage();
	
	/**
	 * 対象年月日からプルダウン用配列を取得する。<br>
	 * 操作区分に操作範囲が設定されている場合は、取得内容が制限される。<br>
	 * 表示内容は、職位略称。<br>
	 * @param targetDate    対象年月日
	 * @param needBlank     空白行要否(true：空白行要、false：空白行不要)
	 * @param operationType 操作区分
	 * @return プルダウン用配列
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String[][] getSelectArray(Date targetDate, boolean needBlank, String operationType) throws MospException;
	
	/**
	 * 対象年月日からプルダウン用配列を取得する。<br>
	 * 操作区分に操作範囲が設定されている場合は、取得内容が制限される。<br>
	 * 表示内容は、職位名称。<br>
	 * @param targetDate    対象年月日
	 * @param needBlank     空白行要否(true：空白行要、false：空白行不要)
	 * @param operationType 操作区分
	 * @return プルダウン用配列
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String[][] getNameSelectArray(Date targetDate, boolean needBlank, String operationType) throws MospException;
	
	/**
	 * 対象年月日からプルダウン用配列を取得する。<br>
	 * 操作区分に操作範囲が設定されている場合は、取得内容が制限される。<br>
	 * 表示内容は、コード + 等級 + 職位名称。<br>
	 * @param targetDate    対象年月日
	 * @param needBlank     空白行要否(true：空白行要、false：空白行不要)
	 * @param operationType 操作区分
	 * @return プルダウン用配列
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String[][] getCodedSelectArray(Date targetDate, boolean needBlank, String operationType) throws MospException;
	
	/**
	 * 対象年月日からプルダウン用配列を取得する。<br>
	 * 操作区分に操作範囲が設定されている場合は、取得内容が制限される。<br>
	 * 表示内容は、コード + 等級 + 職位略称。<br>
	 * @param targetDate    対象年月日
	 * @param needBlank     空白行要否(true：空白行要、false：空白行不要)
	 * @param operationType 操作区分
	 * @return プルダウン用配列
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String[][] getCodedAbbrSelectArray(Date targetDate, boolean needBlank, String operationType) throws MospException;
	
	/**
	 * 対象年月日からプルダウン用配列を取得する。<br>
	 * 操作区分に操作範囲が設定されている場合は、取得内容が制限される。<br>
	 * 表示内容は、等級 + 職位名称。<br>
	 * @param targetDate    対象年月日
	 * @param needBlank     空白行要否(true：空白行要、false：空白行不要)
	 * @param operationType 操作区分
	 * @return プルダウン用配列
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String[][] getGradedSelectArray(Date targetDate, boolean needBlank, String operationType) throws MospException;
	
}
