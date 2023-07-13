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
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.CapsuleUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.human.HumanSearchBeanInterface;
import jp.mosp.platform.bean.system.PlatformMasterBeanInterface;
import jp.mosp.platform.bean.workflow.ApprovalRouteReferenceBeanInterface;
import jp.mosp.platform.bean.workflow.ApprovalRouteRegistBeanInterface;
import jp.mosp.platform.bean.workflow.ApprovalRouteUnitReferenceBeanInterface;
import jp.mosp.platform.bean.workflow.RouteApplicationReferenceSearchBeanInterface;
import jp.mosp.platform.bean.workflow.WorkflowIntegrateBeanInterface;
import jp.mosp.platform.comparator.human.HumanPositionGradeComparator;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.dto.workflow.ApprovalRouteDtoInterface;
import jp.mosp.platform.dto.workflow.ApprovalRouteUnitDtoInterface;
import jp.mosp.platform.dto.workflow.RouteApplicationDtoInterface;
import jp.mosp.platform.dto.workflow.RouteApplicationReferenceDtoInterface;
import jp.mosp.platform.dto.workflow.impl.RouteApplicationReferenceDto;
import jp.mosp.platform.utils.PfNameUtility;

/**
 * ルート適用情報検索クラス。<br>
 */
public class RouteApplicationReferenceSearchBean extends PlatformBean
		implements RouteApplicationReferenceSearchBeanInterface {
	
	/**
	 * 有効日。
	 */
	private Date										activateDate;
	
	/**
	 * 社員コード。
	 */
	private String										employeeCode;
	
	/**
	 * 氏名。
	 */
	private String										employeeName;
	
	/**
	 * 勤務地コード。
	 */
	private String										workPlaceCode;
	
	/**
	 * 雇用契約コード。
	 */
	private String										employmentCode;
	
	/**
	 * 所属名称コード。
	 */
	private String										sectionCode;
	
	/**
	 * 職位コード。
	 */
	private String										positionCode;
	
	/**
	 * フロー区分。
	 */
	private int											workflowType;
	
	/**
	 * ルート適用コード。
	 */
	private String										routeApplicationCode;
	
	/**
	 * ルート適用名称。
	 */
	private String										routeApplicationName;
	
	/**
	 * ルートコード。
	 */
	private String										routeCode;
	
	/**
	 * ルート名称。
	 */
	private String										routeName;
	
	/**
	 * 承認者社員コード。
	 */
	private String										approverCode;
	
	/**
	 * 承認者氏名
	 */
	private String										approverName;
	
	/**
	 * 人事検索クラス。
	 */
	protected HumanSearchBeanInterface					humanSearch;
	
	/**
	 * プラットフォームマスタ参照クラス。<br>
	 */
	protected PlatformMasterBeanInterface				platformMaster;
	
	/**
	 * ルート参照クラス。
	 */
	protected ApprovalRouteReferenceBeanInterface		routeRefer;
	
	/**
	 * ルート登録クラス。
	 */
	protected ApprovalRouteRegistBeanInterface			routeRegist;
	
	/**
	 * ユニット参照クラス。
	 */
	protected ApprovalRouteUnitReferenceBeanInterface	approvalUnitRefer;
	
	/**
	 * ワークフロー統括クラス。
	 */
	protected WorkflowIntegrateBeanInterface			workflowIntegrate;
	
	
	@Override
	public void initBean() throws MospException {
		// Beanを準備
		humanSearch = createBeanInstance(HumanSearchBeanInterface.class);
		approvalUnitRefer = createBeanInstance(ApprovalRouteUnitReferenceBeanInterface.class);
		routeRefer = createBeanInstance(ApprovalRouteReferenceBeanInterface.class);
		routeRegist = createBeanInstance(ApprovalRouteRegistBeanInterface.class);
		workflowIntegrate = createBeanInstance(WorkflowIntegrateBeanInterface.class);
		platformMaster = createBeanInstance(PlatformMasterBeanInterface.class);
	}
	
	@Override
	public List<RouteApplicationReferenceDtoInterface> getSearchList() throws MospException {
		// 検索結果リスト準備
		List<RouteApplicationReferenceDtoInterface> list = new ArrayList<RouteApplicationReferenceDtoInterface>();
		// 検索されたルート適用参照情報群
		Map<String, RouteApplicationReferenceDtoInterface> seachedMap = new HashMap<String, RouteApplicationReferenceDtoInterface>();
		// 職位ランクによる比較クラスを取得
		HumanPositionGradeComparator comparator = workflowIntegrate.getPositionGradeComparator(activateDate);
		// 人事情報検索
		List<HumanDtoInterface> humanList = getHumanList();
		// 社員毎に処理
		for (HumanDtoInterface humanDto : humanList) {
			// ルート適用情報の取得
			RouteApplicationDtoInterface routeApplicationDto = platformMaster.getRouteApplication(humanDto,
					activateDate, workflowType);
			// ルート適用検索確認
			if (isRouteApplicationMatch(routeApplicationDto) == false) {
				continue;
			}
			// ルート適用情報が検索済みか確認
			if (isApplicationSearched(seachedMap, routeApplicationDto)) {
				// 検索済みだったらコピーを詰める
				list.add(copyRouteApplicationDto(humanDto, routeApplicationDto, seachedMap));
				continue;
			}
			// ルート情報の取得
			ApprovalRouteDtoInterface routeDto = getApprovalRouteDto(routeApplicationDto);
			// ルート情報の検索確認
			if (isRouteMatch(routeDto) == false) {
				continue;
			}
			// ルート承認者リスト準備
			List<List<HumanDtoInterface>> routeApproverList = getRouteApproverList(routeDto, comparator);
			// 承認者情報検索確認
			if (isApproverMatch(routeApproverList) == false) {
				continue;
			}
			// ルート適用参照DTO準備
			RouteApplicationReferenceDtoInterface dto = getRouteApplicationReferenceDto();
			// 検索結果をリストに詰める
			setRouteApplicationReferenceDto(dto, humanDto, routeApplicationDto, routeDto);
			// 承認者情報を設定
			setApproverName(dto, routeApproverList);
			// リストに追加
			list.add(dto);
			// 検索された情報をマップに追加
			seachedMap.put(dto.getRouteApplicationCode(), dto);
		}
		// リストを返す
		return list;
	}
	
	/**
	 * 人事情報リストを取得する。<br>
	 * @return 人事情報リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected List<HumanDtoInterface> getHumanList() throws MospException {
		// 検索条件設定
		humanSearch.setTargetDate(activateDate);
		humanSearch.setEmployeeCode(employeeCode);
		humanSearch.setEmployeeName(employeeName);
		humanSearch.setWorkPlaceCode(workPlaceCode);
		humanSearch.setSectionCode(sectionCode);
		humanSearch.setPositionCode(positionCode);
		humanSearch.setEmploymentContractCode(employmentCode);
		humanSearch.setEmployeeCodeType(PlatformConst.SEARCH_FORWARD_MATCH);
		// 検索条件設定(下位所属要否)
		humanSearch.setNeedLowerSection(true);
		// 検索条件設定(兼務要否)
		humanSearch.setNeedConcurrent(true);
		// 検索条件設定(休退職区分)
		humanSearch.setStateType(PlatformConst.EMPLOYEE_STATE_PRESENCE);
		// 検索
		return humanSearch.search();
	}
	
	/**
	 * ルート適用情報に設定されている承認ルート情報を取得する。<br>
	 * @param routeApplicationDto ルート適用情報
	 * @return 承認ルート情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected ApprovalRouteDtoInterface getApprovalRouteDto(RouteApplicationDtoInterface routeApplicationDto)
			throws MospException {
		// ルート情報の準備
		ApprovalRouteDtoInterface routeDto = null;
		// ルート適用情報確認
		if (routeApplicationDto == null) {
			return routeDto;
		}
		// 自己承認の場合
		if (routeApplicationDto.getRouteCode().equals(PlatformConst.APPROVAL_ROUTE_SELF)) {
			// ルート情報の準備
			routeDto = routeRegist.getInitDto();
			routeDto.setRouteCode(MospConst.STR_EMPTY);
			routeDto.setRouteName(PfNameUtility.selfApproval(mospParams));
			routeDto.setApprovalCount(0);
		} else {
			// DBからルート情報を持ってくる
			routeDto = routeRefer.getApprovalRouteInfo(routeApplicationDto.getRouteCode(), activateDate);
		}
		return routeDto;
	}
	
	/**
	 * 承認ルート情報に設定されているルート承認者リストを取得する。<br>
	 * @param approvalRouteDto 承認ルート情報
	 * @param comparator       職位ランクによる比較クラス
	 * @return ルート承認者リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected List<List<HumanDtoInterface>> getRouteApproverList(ApprovalRouteDtoInterface approvalRouteDto,
			HumanPositionGradeComparator comparator) throws MospException {
		// ルート承認者リスト準備
		List<List<HumanDtoInterface>> routeApproverList = new ArrayList<List<HumanDtoInterface>>();
		// 承認ルート情報確認
		if (approvalRouteDto == null || approvalRouteDto.getRouteCode().isEmpty()) {
			return routeApproverList;
		}
		// ルートユニット情報(リスト)の取得(階層毎にユニットを取得)
		List<ApprovalRouteUnitDtoInterface> routeUnitList = approvalUnitRefer
			.getApprovalRouteUnitList(approvalRouteDto.getRouteCode(), activateDate);
		// ユニット毎に承認者を設定
		for (ApprovalRouteUnitDtoInterface routeUnitDto : routeUnitList) {
			// ユニット承認者リスト取得
			List<HumanDtoInterface> unitApproverList = workflowIntegrate.getUnitApproverList(routeUnitDto.getUnitCode(),
					activateDate);
			// 職位等級でソート
			Collections.sort(unitApproverList, comparator);
			// ルート承認者リストに追加
			routeApproverList.add(unitApproverList);
		}
		return routeApproverList;
	}
	
	/**
	 * ルート適用参照情報に、人事情報、ルート適用情報、承認ルート情報を設定する。<br>
	 * @param dto                 ルート適用参照情報
	 * @param humanDto            人事情報
	 * @param routeApplicationDto ルート適用情報
	 * @param approvalRouteDto    承認ルート情報
	 */
	protected void setRouteApplicationReferenceDto(RouteApplicationReferenceDtoInterface dto,
			HumanDtoInterface humanDto, RouteApplicationDtoInterface routeApplicationDto,
			ApprovalRouteDtoInterface approvalRouteDto) {
		// 検索結果をリストに詰める
		dto.setPersonalId(humanDto.getPersonalId());
		dto.setEmployeeCode(humanDto.getEmployeeCode());
		dto.setFirstName(humanDto.getFirstName());
		dto.setLastName(humanDto.getLastName());
		dto.setEmployeeName(MospUtility.getHumansName(humanDto.getFirstName(), humanDto.getLastName()));
		if (routeApplicationDto != null) {
			dto.setActivateDate(routeApplicationDto.getActivateDate());
			dto.setRouteApplicationCode(routeApplicationDto.getRouteApplicationCode());
			dto.setRouteApplicationName(routeApplicationDto.getRouteApplicationName());
		}
		if (approvalRouteDto != null) {
			dto.setRouteCode(approvalRouteDto.getRouteCode());
			dto.setRouteName(approvalRouteDto.getRouteName());
			dto.setRouteStage(String.valueOf(approvalRouteDto.getApprovalCount()));
		}
	}
	
	/**
	 * 一次承認者と最終承認者をルート適用参照情報に設定する。
	 * @param dto ルート適用参照情報
	 * @param routeApproverList ルート承認者リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setApproverName(RouteApplicationReferenceDtoInterface dto,
			List<List<HumanDtoInterface>> routeApproverList) throws MospException {
		// リスト確認
		if (routeApproverList.isEmpty()) {
			return;
		}
		// 一次承認ユニットの承認者リストを取得
		List<HumanDtoInterface> firstApproverList = routeApproverList.get(0);
		// 存在確認
		if (firstApproverList.isEmpty() == false) {
			HumanDtoInterface firstApprover = firstApproverList.get(0);
			// 名前取得
			dto.setFirstApprovalName(
					MospUtility.getHumansName(firstApprover.getFirstName(), firstApprover.getLastName()));
		}
		// 最終承認ユニットの承認者リストを取得
		List<HumanDtoInterface> endApproverList = routeApproverList.get(routeApproverList.size() - 1);
		// 存在確認
		if (endApproverList.isEmpty() == false) {
			HumanDtoInterface lastApprover = endApproverList.get(0);
			// 名前取得
			dto.setEndApprovalName(MospUtility.getHumansName(lastApprover.getFirstName(), lastApprover.getLastName()));
		}
	}
	
	/**
	 * ルート情報が検索条件に合致しているか確認。
	 * @param routeDto ルート情報検索結果
	 * @return true:検索条件合致、false:検索条件合致していない
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected boolean isRouteMatch(ApprovalRouteDtoInterface routeDto) throws MospException {
		// ルートコード検索
		if (routeCode.isEmpty() == false) {
			if (routeDto == null) {
				return false;
			}
			if (isForwardMatch(routeCode, routeDto.getRouteCode()) == false) {
				return false;
			}
		}
		// ルート名称検索
		if (routeName.isEmpty() == false) {
			if (routeDto == null) {
				return false;
			}
			if (isBroadMatch(routeName, routeDto.getRouteName()) == false) {
				return false;
			}
			
		}
		return true;
	}
	
	/**
	 * ルート適用情報が検索条件に合致しているか確認。
	 * @param routeApplicationDto ルート適用情報検索結果
	 * @return true:検索条件合致、false:検索条件合致していない
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected boolean isRouteApplicationMatch(RouteApplicationDtoInterface routeApplicationDto) throws MospException {
		// ルート設定適用コード検索
		if (routeApplicationCode.isEmpty() == false) {
			if (routeApplicationDto == null) {
				return false;
			}
			if (isForwardMatch(routeApplicationCode, routeApplicationDto.getRouteApplicationCode()) == false) {
				return false;
			}
		}
		// ルート設定適用名称検索
		if (routeApplicationName.isEmpty() == false) {
			if (routeApplicationDto == null) {
				return false;
			}
			if (isBroadMatch(routeApplicationName, routeApplicationDto.getRouteApplicationName()) == false) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 承認者が検索条件に合致しているか確認する。
	 * @param routeApproverList ルート承認者リスト
	 * @return 確認結果(true：合致する、false：合致しない)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected boolean isApproverMatch(List<List<HumanDtoInterface>> routeApproverList) throws MospException {
		// 検索承認者氏名・承認者コードが空だったら
		if (approverName.isEmpty() && approverCode.isEmpty()) {
			return true;
		}
		// ルート承認者(階層)毎に処理
		for (List<HumanDtoInterface> approverList : routeApproverList) {
			// ユニットの承認者情報毎に処理
			for (HumanDtoInterface approverDto : approverList) {
				// ユニットの承認者の検索
				if (isApproverMatch(approverDto)) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 承認者情報が検索条件に合致しているか確認する。
	 * @param approverDto 承認者情報検索結果
	 * @return 確認結果(true:検索条件合致、false:検索条件合致していない)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected boolean isApproverMatch(HumanDtoInterface approverDto) throws MospException {
		// 承認者コード検索
		if (approverCode.isEmpty() == false) {
			if (approverDto == null) {
				return false;
			}
			if (isForwardMatch(approverCode, approverDto.getEmployeeCode()) == false) {
				return false;
			}
		}
		// 承認者氏名検索
		if (approverName.isEmpty() == false) {
			if (approverDto == null) {
				return false;
			}
			if (isHumanNameMatch(approverName, approverDto.getFirstName(), approverDto.getLastName()) == false) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * ルート適用情報が既に検索済みか確認する。
	 * @param seachedMap 検索されたルート適用参照情報群
	 * @param routeApplicationDto ルート適用参照情報
	 * @return 確認結果(true:検索済み、false:検索されていない)
	 */
	protected boolean isApplicationSearched(Map<String, RouteApplicationReferenceDtoInterface> seachedMap,
			RouteApplicationDtoInterface routeApplicationDto) {
		// 初期値設定
		String routeApplicationCode = "";
		if (routeApplicationDto != null) {
			routeApplicationCode = routeApplicationDto.getRouteApplicationCode();
		}
		return seachedMap.containsKey(routeApplicationCode);
	}
	
	/**
	 * 使用済みのルート適用参照情報を使って、新しいルート適用参照情報を作成する。
	 * @param humanDto 人事情報参照情報
	 * @param routeApplicationDto ルート適用参照情報
	 * @param seachedMap 検索されたルート適用参照情報群
	 * @return 確認結果(true:検索条件合致、false:検索条件合致していない)
	 */
	protected RouteApplicationReferenceDtoInterface copyRouteApplicationDto(HumanDtoInterface humanDto,
			RouteApplicationDtoInterface routeApplicationDto,
			Map<String, RouteApplicationReferenceDtoInterface> seachedMap) {
		// 初期値設定
		String routeApplicationCode = "";
		if (routeApplicationDto != null) {
			routeApplicationCode = routeApplicationDto.getRouteApplicationCode();
		}
		RouteApplicationReferenceDtoInterface seachedDto = seachedMap.get(routeApplicationCode);
		// 新しいルート適用参照
		RouteApplicationReferenceDtoInterface newDto = getRouteApplicationReferenceDto();
		newDto.setRouteApplicationCode(seachedDto.getRouteApplicationCode());
		newDto.setActivateDate(seachedDto.getActivateDate());
		newDto.setRouteApplicationCode(seachedDto.getRouteApplicationCode());
		newDto.setRouteApplicationName(seachedDto.getRouteApplicationName());
		newDto.setRouteCode(seachedDto.getRouteCode());
		newDto.setRouteName(seachedDto.getRouteName());
		newDto.setRouteStage(seachedDto.getRouteStage());
		newDto.setFirstApprovalName(seachedDto.getFirstApprovalName());
		newDto.setEndApprovalName(seachedDto.getEndApprovalName());
		// 人事情報設定
		newDto.setPersonalId(humanDto.getPersonalId());
		newDto.setEmployeeCode(humanDto.getEmployeeCode());
		newDto.setFirstName(humanDto.getFirstName());
		newDto.setLastName(humanDto.getLastName());
		newDto.setEmployeeName(MospUtility.getHumansName(humanDto.getFirstName(), humanDto.getLastName()));
		// DTO返却
		return newDto;
	}
	
	/**
	 * 初期化した設定適用参照情報を取得する。
	 * @return 初期化した設定適用参照情報
	 */
	protected RouteApplicationReferenceDto getRouteApplicationReferenceDto() {
		RouteApplicationReferenceDto dto = new RouteApplicationReferenceDto();
		dto.setActivateDate(null);
		dto.setEmployeeCode("");
		dto.setEmployeeName("");
		dto.setWorkflowType(0);
		dto.setRouteApplicationName("");
		dto.setRouteApplicationCode("");
		dto.setRouteCode("");
		dto.setRouteName("");
		dto.setRouteStage("");
		dto.setFirstApprovalName("");
		dto.setEndApprovalName("");
		dto.setApproverCode("");
		dto.setApproverName("");
		dto.setSectionCode("");
		dto.setPositionCode("");
		dto.setPersonalId("");
		dto.setEmploymentContractCode("");
		dto.setFirstName("");
		dto.setLastName("");
		dto.setWorkPlaceCode("");
		return dto;
	}
	
	@Override
	public void setActivateDate(Date activateDate) {
		this.activateDate = CapsuleUtility.getDateClone(activateDate);
	}
	
	@Override
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}
	
	@Override
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	
	@Override
	public void setWorkPlaceCode(String workPlaceCode) {
		this.workPlaceCode = workPlaceCode;
	}
	
	@Override
	public void setEmploymentCode(String employmentCode) {
		this.employmentCode = employmentCode;
	}
	
	@Override
	public void setSectionCode(String sectionCode) {
		this.sectionCode = sectionCode;
	}
	
	@Override
	public void setPositionCode(String positionCode) {
		this.positionCode = positionCode;
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
	public void setApproverCode(String approverCode) {
		this.approverCode = approverCode;
	}
	
	@Override
	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}
	
	@Override
	public void setWorkflowType(int workflowType) {
		this.workflowType = workflowType;
		
	}
	
}
