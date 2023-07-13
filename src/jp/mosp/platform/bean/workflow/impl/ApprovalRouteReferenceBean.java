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
package jp.mosp.platform.bean.workflow.impl;

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.workflow.ApprovalRouteReferenceBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dao.workflow.ApprovalRouteDaoInterface;
import jp.mosp.platform.dto.workflow.ApprovalRouteDtoInterface;
import jp.mosp.platform.utils.PfNameUtility;

/**
 * 承認ルートマスタ参照処理。<br>
 */
public class ApprovalRouteReferenceBean extends PlatformBean implements ApprovalRouteReferenceBeanInterface {
	
	/**
	 * 承認ルートマスタDAO。<br>
	 */
	protected ApprovalRouteDaoInterface dao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public ApprovalRouteReferenceBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		dao = createDaoInstance(ApprovalRouteDaoInterface.class);
	}
	
	@Override
	public ApprovalRouteDtoInterface findForKey(String routeCode, Date activateDate) throws MospException {
		return dao.findForKey(routeCode, activateDate);
		
	}
	
	@Override
	public List<ApprovalRouteDtoInterface> getApprovalRouteHistory(String routeCode) throws MospException {
		return dao.findForHistory(routeCode);
	}
	
	@Override
	public ApprovalRouteDtoInterface getApprovalRouteInfo(String routeCode, Date targetDate) throws MospException {
		return dao.findForInfo(routeCode, targetDate);
		
	}
	
	@Override
	public String[][] getCodedSelectArray(Date targetDate, boolean needBlank) throws MospException {
		// 一覧取得
		List<ApprovalRouteDtoInterface> list = dao.findForActivateDate(targetDate);
		// 一覧件数確認
		if (list.size() == 0) {
			if (needBlank) {
				// デフォルトとして「自己承認」をセットする。
				return getDefaultRoutePulldown();
			} else {
				// 対象データ無し
				return getNoObjectDataPulldown();
			}
		}
		// プルダウン用配列及びインデックス準備
		String[][] array = prepareSelectArray(list.size(), needBlank);
		// デフォルトとして「自己承認」をセットする。
		if (needBlank) {
			// コード
			array[0][0] = PlatformConst.APPROVAL_ROUTE_SELF;
			// 名称：「自己承認」
			array[0][1] = PfNameUtility.selfApproval(mospParams);
		}
		int idx = needBlank ? 1 : 0;
		// コードの最大文字数確認
		int codeLength = 0;
		for (ApprovalRouteDtoInterface dto : list) {
			if (dto.getRouteCode().length() > codeLength) {
				codeLength = dto.getRouteCode().length();
			}
		}
		// 配列作成
		for (ApprovalRouteDtoInterface dto : list) {
			array[idx][0] = dto.getRouteCode();
			array[idx][1] = getCodedName(dto.getRouteCode(), dto.getRouteName(), codeLength);
			idx++;
		}
		return array;
	}
	
	/**
	 * ルート選択用プルダウンに「自己承認」の選択肢をセットする。<br>
	 * @return ルート選択プルダウン
	 */
	protected String[][] getDefaultRoutePulldown() {
		String[][] aryPulldown = { { PlatformConst.APPROVAL_ROUTE_SELF, PfNameUtility.selfApproval(mospParams) } };
		return aryPulldown;
	}
	
}
