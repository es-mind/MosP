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
package jp.mosp.platform.dao.human;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import jp.mosp.framework.base.BaseDaoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dto.human.RetirementDtoInterface;

/**
 * 人事退職情報DAOインターフェース。
 */
public interface RetirementDaoInterface extends BaseDaoInterface {
	
	/**
	 * 人事退職情報を取得する。<br>
	 * 個人IDと退職日から人事退職情報を取得する。<br>
	 * 但し、退職日が引数より後の場合は、nullを返す。<br>
	 * @param personalId 個人ID
	 * @param retirementDate 退職日
	 * @return 人事退職情報DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	RetirementDtoInterface findForInfo(String personalId, Date retirementDate) throws MospException;
	
	/**
	 * 人事退職情報を取得する。<br>
	 * 個人IDから人事退職情報を取得する。<br>
	 * @param personalId 個人ID
	 * @return 人事退職情報DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	RetirementDtoInterface findForInfo(String personalId) throws MospException;
	
	/**
	 * 対象期間終了日における退職者の個人IDセットを取得する。<br>
	 * 期間の定めがある場合は
	 * 退職日が期間開始日より前に設定されている個人IDを取得する。<br>
	 * 期間の定めがない場合は
	 * 退職日が対象日より前に設定されている個人IDを取得する。<br>
	 * @param targetDate 対象日
	 * @param startDate 対象期間開始日
	 * @param endDate 対象期間終了日
	 * @return 退職者の個人IDセット
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	Set<String> findForRetiredPersonalIdSet(Date targetDate, Date startDate, Date endDate) throws MospException;
	
	/**
	 * パーソナルID配列に該当する退職情報マップを作成する
	 * @param personalIds パーソナルID配列
	 * @return パーソナルID配列に該当する退職情報マップ
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	public Map<String, RetirementDtoInterface> findForPersonalIds(String[] personalIds) throws MospException;
	
	/**
	 * ユーザマスタ(pfa_user)に人事退職情報を付加するSQLを取得する。<br>
	 * @param personalIdColumn 個人ID列名
	 * @return ユーザマスタ(pfa_user)に追加ロールコードを付加するSQL
	 */
	String getQueryForJoinUser(String personalIdColumn);
	
	/**
	 * ユーザマスタ(pfa_user)に人事退職情報を付加するSQLの列名(retirement_date)を取得する。<br>
	 * @return SQLの条件文用列名
	 */
	String getRetirementDateColumnForJoinUser();
	
}
