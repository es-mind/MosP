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
import jp.mosp.platform.dto.system.WorkPlaceDtoInterface;

/**
 * 勤務地マスタ参照インターフェース。
 */
public interface WorkPlaceReferenceBeanInterface extends BaseBeanInterface {
	
	/**
	 * 勤務地マスタからレコードを取得する。<br>
	 * 勤務地コード、有効日で合致するレコードが無い場合、nullを返す。<br>
	 * @param workPlaceCode 勤務地コード
	 * @param activateDate           有効日
	 * @return 雇用契約マスタDTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	WorkPlaceDtoInterface findForKey(String workPlaceCode, Date activateDate) throws MospException;
	
	/**
	 * 勤務地マスタ取得。
	 * <p>
	 * 勤務地コードと対象日から勤務地マスタを取得。
	 * </p>
	 * @param workPlaceCode 勤務地コード
	 * @param targetDate 対象年月日
	 * @return 勤務地マスタ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	WorkPlaceDtoInterface getWorkPlaceInfo(String workPlaceCode, Date targetDate) throws MospException;
	
	/**
	 * 履歴一覧取得。
	 * <p>
	 * 勤務地コードから勤務地マスタリストを取得。
	 * </p>
	 * @param workPlaceCode 勤務地コード
	 * @return 勤務地マスタリスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<WorkPlaceDtoInterface> getHistory(String workPlaceCode) throws MospException;
	
	/**
	 * 勤務地名称を取得する。<br><br>
	 * 対象となる勤務地情報が存在しない場合は、空文字列を返す。<br>
	 * @param workPlaceCode 勤務地コード
	 * @param targetDate 対象年月日
	 * @return 勤務地名称
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String getWorkPlaceName(String workPlaceCode, Date targetDate) throws MospException;
	
	/**
	 * 勤務地略称を取得する。<br><br>
	 * 対象となる勤務地情報が存在しない場合は、空文字列を返す。<br>
	 * @param workPlaceCode 勤務地コード
	 * @param targetDate 対象年月日
	 * @return 勤務地略称
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String getWorkPlaceAbbr(String workPlaceCode, Date targetDate) throws MospException;
	
	/**
	 * 対象年月日における勤務地マスタリストを取得する。<br>
	 * 範囲操作権限が設定されている場合は、取得内容が制限される。<br>
	 * @param targetDate    対象日
	 * @param operationType 操作区分
	 * @return 勤務地マスタリスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<WorkPlaceDtoInterface> getWorkPlaceList(Date targetDate, String operationType) throws MospException;
	
	/**
	 * 対象年月日からプルダウン用配列を取得する。<br>
	 * 操作区分に操作範囲が設定されている場合は、取得内容が制限される。<br>
	 * 表示内容は、勤務地略称。<br>
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
	 * 表示内容は、勤務地の名称。<br>
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
	 * 表示内容は、コード + 勤務地名称。<br>
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
	 * 表示内容は、コード + 勤務地略称。<br>
	 * @param targetDate    対象年月日
	 * @param needBlank     空白行要否(true：空白行要、false：空白行不要)
	 * @param operationType 操作区分
	 * @return プルダウン用配列
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String[][] getCodedAbbrSelectArray(Date targetDate, boolean needBlank, String operationType) throws MospException;
	
}
