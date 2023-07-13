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
package jp.mosp.time.dao.settings;

import java.util.Date;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.BaseDaoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.ApplicationDtoInterface;

/**
 * 設定適用管理DAOインターフェース
 */
public interface ApplicationDaoInterface extends BaseDaoInterface {
	
	/**
	 * 設定適用コードと有効日から設定適用情報を取得する。<br>
	 * 条件と合致する情報が存在しない場合は、nullを返す。<br>
	 * @param applicationCode 設定適用コード
	 * @param activateDate 有効日
	 * @return 設定適用マスタDTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	ApplicationDtoInterface findForKey(String applicationCode, Date activateDate) throws MospException;
	
	/**
	 * 履歴一覧。
	 * <p>
	 * 設定適用コードから設定適用マスタリストを取得する。
	 * </p>
	 * @param applicationCode 設定適用コード。
	 * @return 設定適用マスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<ApplicationDtoInterface> findForHistory(String applicationCode) throws MospException;
	
	/**
	 * 条件による検索。
	 * <p>
	 * 検索条件から設定適用マスタリストを取得する。
	 * </p>
	 * @param param 検索条件
	 * @return 設定適用マスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<ApplicationDtoInterface> findForSearch(Map<String, Object> param) throws MospException;
	
	/**
	 * 検索条件取得。
	 * @return 設定適用検索条件マップ
	 */
	Map<String, Object> getParamsMap();
	
	/**
	 * 設定適用コードにつき、有効日より前の情報を取得する。<br>
	 * 有効無効は問わない。<br>
	 * 履歴削除により最新となる情報を取得する場合等に用いる。<br>
	 * @param applicationCode 設定適用コード
	 * @param activateDate    有効日
	 * @return 設定適用マスタ
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	ApplicationDtoInterface findFormerInfo(String applicationCode, Date activateDate) throws MospException;
	
	/**
	 * 設定適用コードにつき、有効日より後の情報を取得する。<br>
	 * 有効無効は問わない。<br>
	 * 対象情報が有効である範囲を取得する場合等に用いる。<br>
	 * @param applicationCode 設定適用コード
	 * @param activateDate    有効日
	 * @return 設定適用マスタ
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	ApplicationDtoInterface findLatterInfo(String applicationCode, Date activateDate) throws MospException;
	
	/**
	 * 個人IDが設定されている、有効日時点で最新の有効な情報を取得する。<br>
	 * @param activateDate 有効日
	 * @param personalId   個人ID
	 * @return 設定適用マスタ
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	ApplicationDtoInterface findForPersonalId(Date activateDate, String personalId) throws MospException;
	
	/**
	 * 個人IDが設定されている、有効日の範囲内で有効な情報を取得する。<br>
	 * 検索結果に、有効日が開始日または終了日の情報は、含まれない。<br>
	 * 設定重複確認等で用いる。<br>
	 * @param startDate  開始日(有効日)
	 * @param endDate    終了日(有効日)
	 * @param personalId 個人ID
	 * @return 設定適用マスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<ApplicationDtoInterface> findPersonDuplicated(Date startDate, Date endDate, String personalId)
			throws MospException;
	
	/**
	 * 個人IDが設定されている、有効日の範囲内で情報を取得する。<br>
	 * 検索結果に、有効日が開始日または終了日の情報も含まる。<br>
	 * 削除時の整合性確認等で用いる。<br>
	 * @param startDate  開始日(有効日)
	 * @param endDate    終了日(有効日)
	 * @param personalId 個人ID
	 * @return ルート適用マスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<ApplicationDtoInterface> findPersonTerm(String personalId, Date startDate, Date endDate) throws MospException;
	
	/**
	 * マスタ組合による適用範囲が設定されている、有効日時点で最新の有効な情報を取得する。<br>
	 * @param activateDate           有効日
	 * @param workPlaceCode          勤務地コード
	 * @param employmentContractCode 雇用契約コード
	 * @param sectionCode            所属コード
	 * @param positionCode           職位コード
	 * @return 設定適用マスタ
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	ApplicationDtoInterface findForMaster(Date activateDate, String workPlaceCode, String employmentContractCode,
			String sectionCode, String positionCode) throws MospException;
	
	/**
	 * マスタ組合による適用範囲が設定されている、有効日より後で有効な情報を取得する。<br>
	 * 検索結果に、有効日が開始日または終了日の情報は、含まれない。<br>
	 * 設定重複確認等で用いる。<br>
	 * @param startDate              開始日(有効日)
	 * @param endDate                終了日(有効日)
	 * @param workPlaceCode          勤務地コード
	 * @param employmentContractCode 雇用契約コード
	 * @param sectionCode            所属コード
	 * @param positionCode           職位コード
	 * @return 設定適用マスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<ApplicationDtoInterface> findMasterDuplicated(Date startDate, Date endDate, String workPlaceCode,
			String employmentContractCode, String sectionCode, String positionCode) throws MospException;
	
	/**
	 * 設定適用マスタリストを取得する。<br>
	 * 削除フラグが立っていないものを対象とする。<br>
	 * 有効日の範囲で検索する。但し、有効日From及び有効日Toは、検索対象に含まれない。<br>
	 * マスタ類無効時の確認等に用いる。<br>
	 * @param fromActivateDate 有効日From
	 * @param toActivateDate   有効日To
	 * @return 設定適用マスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<ApplicationDtoInterface> findForCheckTerm(Date fromActivateDate, Date toActivateDate) throws MospException;
	
	/**
	 * 有効日の範囲内で情報を取得する。<br>
	 * 検索結果に、有効日が開始日または終了日の情報も含まる。<br>
	 * @param startDate  開始日(有効日)
	 * @param endDate    終了日(有効日)
	 * @return ルート適用マスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<ApplicationDtoInterface> findForTerm(Date startDate, Date endDate) throws MospException;
	
	/**
	 * 有効日マスタ一覧。
	 * <p>
	 * 有効日から設定適用マスタリストを取得する。
	 * </p>
	 * @param activateDate 有効日
	 * @return 設定適用マスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<ApplicationDtoInterface> findForActivateDate(Date activateDate) throws MospException;
	
	/**
	 * 条件による検索のための文字列。
	 * <p>
	 * 最大有効日レコードのクエリを取得する。
	 * @return ユニットコードに紐づく有効日が最大であるレコード取得クエリ
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	StringBuffer getQueryForMaxActivateDate() throws MospException;
	
}
