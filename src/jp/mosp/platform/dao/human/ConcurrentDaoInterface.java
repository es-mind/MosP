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
package jp.mosp.platform.dao.human;

import java.sql.PreparedStatement;
import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseDaoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dto.human.ConcurrentDtoInterface;

/**
 * 人事兼務情報DAOインターフェース。
 */
public interface ConcurrentDaoInterface extends BaseDaoInterface {
	
	/**
	 * 人事兼務情報一覧を取得する。<br>
	 * <br>
	 * 削除フラグが立っていないものを対象とする。<br>
	 * 兼務期間に対象期間がかかっているものを検索する。但し、対象期間終了日は検索対象に含めない。<br>
	 * マスタ類無効時の確認等に用いる。<br>
	 * <br>
	 * @param fromDate 対象期間開始日
	 * @param toDate   対象期間終了日
	 * @return 人事兼務情報リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<ConcurrentDtoInterface> findForTerm(Date fromDate, Date toDate) throws MospException;
	
	/**
	 * 人事兼務情報一覧取得。
	 * <p>
	 * 個人IDと有効日から人事兼務情報リストを取得する。
	 * </p>
	 * @param personalId 個人ID
	 * @param activateDate 有効日
	 * @return 人事兼務情報リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<ConcurrentDtoInterface> findForList(String personalId, Date activateDate) throws MospException;
	
	/**
	 * 履歴一覧。
	 * <p>
	 * 個人IDから人事兼務情報リストを取得する。
	 * </p>
	 * @param personalId 個人ID
	 * @return 人事兼務情報リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<ConcurrentDtoInterface> findForHistory(String personalId) throws MospException;
	
	/**
	 * 兼務所属及び対象日で個人IDを抽出するSQLを取得する。<br>
	 * 社員一覧を検索する際等に用いる。<br>
	 * @param targetColumn 検索対象個人ID列名
	 * @param needLowerSection 下位所属要否
	 * @return 兼務所属及び対象日で個人IDを抽出するSQL
	 * @throws MospException 所属サブクエリの取得に失敗した場合
	 */
	String getQueryForSection(String targetColumn, boolean needLowerSection) throws MospException;
	
	/**
	 * 兼務所属及び対象日で個人IDを抽出する条件のパラメータを設定する。<br>
	 * 設定したパラメータの数だけ、パラメータインデックスが加算される。<br>
	 * @param index            パラメータインデックス
	 * @param sectionCode      所属コード
	 * @param targetDate       対象日
	 * @param needLowerSection 下位所属要否
	 * @param ps               ステートメント
	 * @return 加算されたパラメータインデックス
	 * @throws MospException SQL例外が発生した場合
	 */
	int setParamsForSection(int index, String sectionCode, Date targetDate, boolean needLowerSection,
			PreparedStatement ps) throws MospException;
	
	/**
	 * 兼務職位及び対象日で個人IDを抽出するSQLを取得する。<br>
	 * 社員一覧を検索する際等に用いる。<br>
	 * @param targetColumn 検索対象個人ID列名
	 * @return 兼務職位及び対象日で個人IDを抽出するSQL
	 */
	String getQueryForPosition(String targetColumn);
	
	/**
	 * 兼務職位及び対象日で個人IDを抽出する条件のパラメータを設定する。<br>
	 * 設定したパラメータの数だけ、パラメータインデックスが加算される。<br>
	 * @param index        パラメータインデックス
	 * @param positionCode 職位コード
	 * @param targetDate   対象日
	 * @param ps           ステートメント
	 * @return 加算されたパラメータインデックス
	 * @throws MospException SQL例外が発生した場合
	 */
	int setParamsForPosition(int index, String positionCode, Date targetDate, PreparedStatement ps)
			throws MospException;
	
	/**
	 * 兼務職位等級及び対象日で個人IDを抽出するSQLを取得する。<br>
	 * 社員一覧を検索する際等に用いる。<br>
	 * @param greaterEqual 以上の場合true、以下の場合false
	 * @param targetColumn 検索対象個人ID列名
	 * @return 兼務職位等級及び対象日で個人IDを抽出するSQL
	 */
	String getQueryForPositionGrade(boolean greaterEqual, String targetColumn);
	
	/**
	 * 兼務職位等級及び対象日で個人IDを抽出する条件のパラメータを設定する。<br>
	 * 設定したパラメータの数だけ、パラメータインデックスが加算される。<br>
	 * @param index        パラメータインデックス
	 * @param positionCode 職位コード
	 * @param targetDate   対象日
	 * @param ps           ステートメント
	 * @return 加算されたパラメータインデックス
	 * @throws MospException SQL例外が発生した場合
	 */
	int setParamsForPositionGrade(int index, String positionCode, Date targetDate, PreparedStatement ps)
			throws MospException;
	
}
