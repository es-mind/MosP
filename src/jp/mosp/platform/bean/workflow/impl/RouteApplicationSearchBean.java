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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.human.HumanReferenceBeanInterface;
import jp.mosp.platform.bean.workflow.RouteApplicationSearchBeanInterface;
import jp.mosp.platform.bean.workflow.WorkflowIntegrateBeanInterface;
import jp.mosp.platform.dao.workflow.RouteApplicationDaoInterface;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.dto.workflow.RouteApplicationDtoInterface;

/**
 * ルート適用マスタ検索クラス。
 */
public class RouteApplicationSearchBean extends PlatformBean implements RouteApplicationSearchBeanInterface {
	
	/**
	 * ルート適用マスタDAO。
	 */
	protected RouteApplicationDaoInterface	dao;
	
	/**
	 * 有効日。
	 */
	private Date							activateDate;
	
	/**
	 * ルート適用コード
	 */
	private String							routeApplicationCode;
	
	/**
	 * ルート適用名称。
	 */
	private String							routeApplicationName;
	
	/**
	 * フロー区分。
	 */
	private String							workflowType;
	
	/**
	 * ルートコード
	 */
	private String							routeCode;
	
	/**
	 * ルート名称。
	 */
	private String							routeName;
	
	/**
	 * 被承認者社員コード。
	 */
	private String							employeeCode;
	
	/**
	 * 承認者社員コード。
	 */
	private String							approverEmployeeCode;
	
	/**
	 * 有効無効フラグ。
	 */
	private String							inactivateFlag;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public RouteApplicationSearchBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// DAOを準備
		dao = createDaoInstance(RouteApplicationDaoInterface.class);
		
	}
	
	@Override
	public List<RouteApplicationDtoInterface> getSearchList() throws MospException {
		// Mapに検索条件を設定
		Map<String, Object> param = dao.getParamsMap();
		param.put("activateDate", activateDate);
		param.put("routeApplicationCode", routeApplicationCode);
		param.put("routeApplicationName", routeApplicationName);
		param.put("workflowType", workflowType);
		param.put("routeCode", routeCode);
		param.put("routeName", routeName);
		param.put("inactivateFlag", inactivateFlag);
		param.put("employeeCode", employeeCode);
		param.put("approverEmployeeCode", approverEmployeeCode);
		// 検索
		List<RouteApplicationDtoInterface> list = dao.findForSearch(param);
		// 被承認者社員コード及び承認者社員コード条件確認
		if (employeeCode.isEmpty() && approverEmployeeCode.isEmpty()) {
			return list;
		}
		// 人事マスタ参照クラス準備
		HumanReferenceBeanInterface humanReference = (HumanReferenceBeanInterface)createBean(
				HumanReferenceBeanInterface.class);
		// ワークフロークラス準備
		WorkflowIntegrateBeanInterface workflow;
		workflow = (WorkflowIntegrateBeanInterface)createBean(WorkflowIntegrateBeanInterface.class);
		// 人事マスタリスト取得
		List<HumanDtoInterface> humanList = humanReference.getHumanList(activateDate);
		
		// 被承認者社員コードが入力されている場合
		if (employeeCode.isEmpty() == false) {
			List<RouteApplicationDtoInterface> listTmp = new ArrayList<RouteApplicationDtoInterface>();
			List<String> listPersonId = new ArrayList<String>();
			// 人事マスタリストより検索条件に合致する個人IDを抽出
			for (int i = 0; i < humanList.size(); i++) {
				// リストから情報を取得
				HumanDtoInterface humanDto = humanList.get(i);
				// 社員コード確認
				if (humanDto.getEmployeeCode().equals(employeeCode)) {
					// 個人IDリストに追加
					listPersonId.add(humanDto.getPersonalId());
				}
			}
			for (RouteApplicationDtoInterface routeApplicationDto : list) {
				for (String personalId : listPersonId) {
					if (routeApplicationDto.getPersonalIds().contains(personalId)) {
						// 指定された被承認者社員コードに合致する個人IDを含む
						// ルート適用マスタデータを抽出する。
						// 抽出済みのデータはセットしない。
						if (!listTmp.contains(routeApplicationDto)) {
							listTmp.add(routeApplicationDto);
						}
					}
				}
			}
			// ルート適用マスタリストをクリアする。
			list.clear();
			// 条件に従って抽出したデータリストをルート適用マスタリストにセットする。
			if (listTmp.size() != 0) {
				list.addAll(listTmp);
			}
		}
		
		// 承認者社員コードが入力されている場合
		if (approverEmployeeCode.isEmpty() == false) {
			List<RouteApplicationDtoInterface> listTmp = new ArrayList<RouteApplicationDtoInterface>();
			List<String> listPersonId = new ArrayList<String>();
			// 人事マスタリストより検索条件に合致する個人IDを抽出
			for (int i = 0; i < humanList.size(); i++) {
				// リストから情報を取得
				HumanDtoInterface humanDto = humanList.get(i);
				if (humanDto.getEmployeeCode().equals(approverEmployeeCode)) {
					listPersonId.add(humanDto.getPersonalId());
				}
			}
			for (RouteApplicationDtoInterface routeApplicationDto : list) {
				for (String personalId : listPersonId) {
					// 対象個人IDが属する承認ルートコード毎に処理
					for (String routeCode : workflow.getApproverRouteSet(personalId, activateDate)) {
						if (routeCode.contains(routeApplicationDto.getRouteCode())) {
							// ルートコードが合致するルート適用マスタデータを抽出する。
							// 抽出済みのデータはセットしない。
							if (!listTmp.contains(routeApplicationDto)) {
								listTmp.add(routeApplicationDto);
							}
						}
					}
				}
			}
			// ルート適用マスタリストをクリアする。
			list.clear();
			// 条件に従って抽出したデータリストをルート適用マスタリストにセットする。
			if (listTmp.size() != 0) {
				list.addAll(listTmp);
			}
		}
		return list;
	}
	
	@Override
	public void setActivateDate(Date activateDate) {
		this.activateDate = getDateClone(activateDate);
		
	}
	
	@Override
	public void setApproverEmployeeCode(String approverEmployeeCode) {
		this.approverEmployeeCode = approverEmployeeCode;
		
	}
	
	@Override
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
		
	}
	
	@Override
	public void setInactivateFlag(String inactivateFlag) {
		this.inactivateFlag = inactivateFlag;
		
	}
	
	@Override
	public void setRouteApplicationCode(String routeApplicationCode) {
		this.routeApplicationCode = routeApplicationCode;
		
	}
	
	@Override
	public void setRouteApplicationName(String routeApplicationName) {
		this.routeApplicationName = routeApplicationName;
		
	}
	
	@Override
	public void setRouteCode(String routeCode) {
		this.routeCode = routeCode;
		
	}
	
	@Override
	public void setRouteName(String routeName) {
		this.routeName = routeName;
		
	}
	
	@Override
	public void setWorkflowType(String workflowType) {
		this.workflowType = workflowType;
		
	}
	
}
