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
/**
 * 
 */
package jp.mosp.time.bean;

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.CutoffDtoInterface;
import jp.mosp.time.entity.CutoffEntityInterface;

/**
 * 締日管理参照インターフェース
 */
public interface CutoffReferenceBeanInterface extends BaseBeanInterface {
	
	/**
	 * 締日マスタ取得。
	 * <p>
	 * 締日コードと対象日から締日マスタを取得。
	 * </p>
	 * @param cutoffCode 締日コード
	 * @param targetDate 対象年月日
	 * @return 締日マスタ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	CutoffDtoInterface getCutoffInfo(String cutoffCode, Date targetDate) throws MospException;
	
	/**
	 * 履歴一覧を取得する。<br>
	 * 締日コードから締日マスタリストを取得。
	 * @param cutoffCode 締日コード
	 * @return 締日マスタリスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<CutoffDtoInterface> getCutoffHistory(String cutoffCode) throws MospException;
	
	/**
	 * プルダウン用配列取得。
	 * <p>
	 * 対象年月日からプルダウン用配列を取得。
	 * </p>
	 * @param targetDate 対象年月日
	 * @return プルダウン用配列
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String[][] getSelectArray(Date targetDate) throws MospException;
	
	/**
	 * プルダウン用配列取得。
	 * <p>
	 * 対象年月日から締日コードと締日名称のプルダウン用配列を取得。
	 * </p>
	 * @param targetDate 対象年月日
	 * @return プルダウン用配列
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String[][] getSelectCodeArray(Date targetDate) throws MospException;
	
	/**
	 * 対象年月日からプルダウン用配列を取得する。<br>
	 * 表示内容は、コード + 締日略称。<br>
	 * @param targetDate    対象年月日
	 * @param needBlank     空白行要否(true：空白行要、false：空白行不要)
	 * @return プルダウン用配列
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String[][] getCodedAbbrSelectArray(Date targetDate, boolean needBlank) throws MospException;
	
	/**
	 * 締日マスタからレコードを取得する。<br>
	 * 締日コード、有効日で合致するレコードが無い場合、nullを返す。<br>
	 * @param cutoffCode 締日コード
	 * @param activateDate 有効日
	 * @return 締日マスタDTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	CutoffDtoInterface findForKey(String cutoffCode, Date activateDate) throws MospException;
	
	/**
	 * 締日マスタの存在チェック。<br>
	 * @param dto 対象締日
	 * @param targetDate メッセージ用の年月日
	 */
	void chkExistCutoff(CutoffDtoInterface dto, Date targetDate);
	
	/**
	 * プルダウン用配列を取得する。<br>
	 * 対象年月日からプルダウン用配列を取得する。<br>
	 * 操作区分に操作範囲が設定されている場合は、取得内容が制限される。<br>
	 * 表示内容は、締日の略称。<br>
	 * @param targetDate    対象年月日
	 * @param needBlank     空白行要否(true：空白行要、false：空白行不要)
	 * @return プルダウン用配列
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String[][] getSelectArray(Date targetDate, boolean needBlank) throws MospException;
	
	/**
	 * 対象年月日からプルダウン用配列を取得する。<br>
	 * 操作区分に操作範囲が設定されている場合は、取得内容が制限される。<br>
	 * 表示内容は、コード + 雇用契約名称。<br>
	 * @param targetDate    対象年月日
	 * @param needBlank     空白行要否(true：空白行要、false：空白行不要)
	 * @param operationType 操作区分
	 * @return プルダウン用配列
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String[][] getCodedSelectArray(Date targetDate, boolean needBlank, String operationType) throws MospException;
	
	/**
	 * 対象日における最新の締日エンティティを取得する。<br>
	 * @param cutoffCode 締日コード
	 * @param targetDate 対象日
	 * @return 締日エンティティ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	CutoffEntityInterface getCutoffEntity(String cutoffCode, Date targetDate) throws MospException;
	
}
