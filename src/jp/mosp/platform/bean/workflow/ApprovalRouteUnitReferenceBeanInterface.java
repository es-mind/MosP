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
package jp.mosp.platform.bean.workflow;

import java.util.Date;
import java.util.List;
import java.util.Set;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dto.workflow.ApprovalRouteUnitDtoInterface;

/**
 * 承認ルートユニットマスタ参照インターフェース。
 */
public interface ApprovalRouteUnitReferenceBeanInterface extends BaseBeanInterface {
	
	/**
	 * 承認ルートユニットマスタ取得。
	 * <p>
	 * ルートコードと対象日、承認段階から承認ルートマスタを取得。
	 * </p>
	 * @param routeCode ルートコード
	 * @param targetDate 対象年月日
	 * @param routeStage 承認段階
	 * @return 承認ルートユニットマスタ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	ApprovalRouteUnitDtoInterface getApprovalRouteUnitInfo(String routeCode, Date targetDate, int routeStage)
			throws MospException;
	
	/**
	 * 承認ルートユニットマスタリスト取得。
	 * <p>
	 * ルートコードと対象日から承認ルートユニットマスタリストを取得。
	 * </p>
	 * @param routeCode ルートコード
	 * @param targetDate 対象年月日
	 * @return 承認ルートユニットマスタ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<ApprovalRouteUnitDtoInterface> getApprovalRouteUnitList(String routeCode, Date targetDate)
			throws MospException;
	
	/**
	 * 承認ルートユニットマスタからレコードを取得する。<br>
	 * ルートコード、有効日、承認段階で合致するレコードが無い場合、nullを返す。<br>
	 * @param routeCode ルートコード
	 * @param activateDate 有効日
	 * @param routeStage 承認段階
	 * @return 承認ルートユニットマスタDTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	ApprovalRouteUnitDtoInterface findForKey(String routeCode, Date activateDate, int routeStage) throws MospException;
	
	/**
	 * ユニットコードが設定されている承認ルートコード群を取得する。<br>
	 * <br>
	 * @param unitCode   ユニットコード
	 * @param targetDate 対象年月日
	 * @return 承認ルートコード群
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	Set<String> getRouteSetForUnit(String unitCode, Date targetDate) throws MospException;
	
}
