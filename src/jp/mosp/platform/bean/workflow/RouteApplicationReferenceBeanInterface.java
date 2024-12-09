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

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dto.workflow.RouteApplicationDtoInterface;

/**
 * ルート適用マスタ参照インターフェース。
 */
public interface RouteApplicationReferenceBeanInterface extends BaseBeanInterface {
	
	/**
	 * ルート適用マスタ取得。
	 * <p>
	 * ルート適用コードと対象日からルート適用マスタを取得。
	 * </p>
	 * @param routeApplicationCode ルート適用コード
	 * @param targetDate 対象年月日
	 * @return ルート適用マスタ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	RouteApplicationDtoInterface getRouteApplicationInfo(String routeApplicationCode, Date targetDate)
			throws MospException;
	
	/**
	 * 履歴一覧取得。
	 * <p>
	 * ルート適用コードからルート適用マスタを取得。
	 * </p>
	 * @param routeApplicationCode ルート適用コード
	 * @return ルート適用マスタ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<RouteApplicationDtoInterface> getRouteApplicationHistory(String routeApplicationCode) throws MospException;
	
	/**
	 * ルート適用マスタからレコードを取得する。<br>
	 * ルート適用コード、有効日で合致するレコードが無い場合、nullを返す。<br>
	 * @param routeApplicationCode ルート適用コード
	 * @param activateDate 有効日
	 * @return ルート適用マスタDTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	RouteApplicationDtoInterface findForKey(String routeApplicationCode, Date activateDate) throws MospException;
	
	/**
	 * 期間内に適用されている設定が存在するか確認する。<br>
	 * @param startDate 期間開始日
	 * @param endDate 期間終了日
	 * @param personalId 対象個人ID
	 * @return isExist (true：存在する、false：存在しない)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	boolean hasPersonalApplication(String personalId, Date startDate, Date endDate) throws MospException;
	
}
