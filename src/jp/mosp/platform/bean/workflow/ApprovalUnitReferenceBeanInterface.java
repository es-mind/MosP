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
package jp.mosp.platform.bean.workflow;

import java.util.Date;
import java.util.List;
import java.util.Set;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dto.workflow.ApprovalUnitDtoInterface;

/**
 * 承認ユニットマスタ参照インターフェース。
 */
public interface ApprovalUnitReferenceBeanInterface extends BaseBeanInterface {
	
	/**
	 * 承認ユニットマスタ取得。
	 * <p>
	 * ユニットコードと対象日から承認ユニットマスタを取得。
	 * </p>
	 * @param unitCode ユニットコード
	 * @param targetDate 対象年月日
	 * @return 承認ユニットマスタ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	ApprovalUnitDtoInterface getApprovalUnitInfo(String unitCode, Date targetDate) throws MospException;
	
	/**
	 * 履歴一覧取得。
	 * <p>
	 * ユニットコードから承認ユニットマスタを取得。
	 * </p>
	 * @param unitCode ユニットコード
	 * @return 承認ユニットマスタ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<ApprovalUnitDtoInterface> getApprovalUnitHistory(String unitCode) throws MospException;
	
	/**
	 * ユニット名称を取得する。<br><br>
	 * 対象となる承認ユニットマスタ情報が存在しない場合は、ユニットコードを返す。<br>
	 * @param unitCode ユニットコード
	 * @param targetDate 対象年月日
	 * @return ユニット名称
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String getUnitName(String unitCode, Date targetDate) throws MospException;
	
	/**
	 * プルダウン用配列を取得する。<br>
	 * 対象年月日からプルダウン用配列を取得する。<br>
	 * 表示内容は、コード + ユニット名称。<br>
	 * @param targetDate 対象年月日
	 * @param needBlank 空白行要否(true：空白行要、false：空白行不要)
	 * @return プルダウン用配列
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String[][] getCodedSelectArray(Date targetDate, boolean needBlank) throws MospException;
	
	/**
	 * 承認ユニットマスタからレコードを取得する。<br>
	 * ユニットコード、有効日で合致するレコードが無い場合、nullを返す。<br>
	 * @param unitCode ユニットコード
	 * @param activateDate 有効日
	 * @return 承認ユニットマスタDTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	ApprovalUnitDtoInterface findForKey(String unitCode, Date activateDate) throws MospException;
	
	/**
	 * 承認者個人IDから承認ユニットコード群を取得する。<br>
	 * @param personalId 承認者個人ID
	 * @param targetDate 対象日
	 * @return 承認ユニットコード群
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	Set<String> getUnitSetForPersonalId(String personalId, Date targetDate) throws MospException;
	
	/**
	 * 承認者所属コード、承認者職位コードから承認ユニットコード群を取得する。<br>
	 * @param sectionCode  承認者所属コード
	 * @param positionCode 承認者職位コード
	 * @param targetDate   対象日
	 * @return 承認ユニットコード群
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	Set<String> getUnitSetForMaster(String sectionCode, String positionCode, Date targetDate) throws MospException;
	
	/**
	 * 期間内に個人で設定されている設定が存在するか確認する。<br>
	 * @param startDate 期間開始日
	 * @param endDate 期間終了日
	 * @param personalId 対象個人ID
	 * @return isExist (true：存在する、false：存在しない)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	boolean hasPersonalUnit(String personalId, Date startDate, Date endDate) throws MospException;
	
}
