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
package jp.mosp.platform.bean.workflow.impl;

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.workflow.RouteApplicationReferenceBeanInterface;
import jp.mosp.platform.dao.workflow.RouteApplicationDaoInterface;
import jp.mosp.platform.dto.workflow.RouteApplicationDtoInterface;

/**
 * ルート適用マスタ参照クラス。
 */
public class RouteApplicationReferenceBean extends PlatformBean implements RouteApplicationReferenceBeanInterface {
	
	/**
	 * ルート適用マスタDAO。
	 */
	protected RouteApplicationDaoInterface dao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public RouteApplicationReferenceBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// DAOを準備
		dao = createDaoInstance(RouteApplicationDaoInterface.class);
	}
	
	@Override
	public RouteApplicationDtoInterface findForKey(String routeApplicationCode, Date activateDate)
			throws MospException {
		return dao.findForKey(routeApplicationCode, activateDate);
	}
	
	@Override
	public RouteApplicationDtoInterface getRouteApplicationInfo(String routeApplicationCode, Date targetDate)
			throws MospException {
		return dao.findForInfo(routeApplicationCode, targetDate);
	}
	
	@Override
	public List<RouteApplicationDtoInterface> getRouteApplicationHistory(String routeApplicationCode)
			throws MospException {
		return dao.findForHistory(routeApplicationCode);
	}
	
	@Override
	public boolean hasPersonalApplication(String personalId, Date startDate, Date endDate) throws MospException {
		// 個人IDが設定されている、有効日の範囲内で情報を取得
		List<RouteApplicationDtoInterface> list = dao.findPersonTerm(personalId, startDate, endDate);
		// リスト確認
		if (list.isEmpty()) {
			return false;
		}
		// 期間内全て適用されていたら
		return true;
	}
	
}
