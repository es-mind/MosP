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
package jp.mosp.platform.bean.human.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.framework.utils.RoleUtility;
import jp.mosp.platform.bean.human.HumanSubordinateBeanInterface;
import jp.mosp.platform.bean.system.PlatformMasterBeanInterface;
import jp.mosp.platform.bean.workflow.WorkflowIntegrateBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.dto.workflow.RouteApplicationDtoInterface;
import jp.mosp.platform.utils.PlatformUtility;

/**
 * 部下検索処理。<br>
 */
public class HumanSubordinateBean extends HumanSearchBean implements HumanSubordinateBeanInterface {
	
	/**
	 * 検索区分(承認すべき申請者)。<br>
	 */
	protected static final String				TYPE_APPLICANTS_APPROVE	= "1";
	
	/**
	 * 検索区分(部下)。<br>
	 */
	protected static final String				TYPE_SUBORDINATE		= "2";
	
	/**
	 * プラットフォームマスタ参照クラス。<br>
	 */
	protected PlatformMasterBeanInterface		platformMaster;
	
	/**
	 * ワークフロー統合クラス。
	 */
	protected WorkflowIntegrateBeanInterface	workflowIntegrate;
	
	/**
	 * 社員区分。
	 */
	protected String							humanType;
	
	
	/**
	 * {@link HumanSearchBean#HumanSearchBean()}を実行する。<br>
	 */
	public HumanSubordinateBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// 継承基のメソッドを実行
		super.initBean();
		// Beanを準備
		workflowIntegrate = createBeanInstance(WorkflowIntegrateBeanInterface.class);
		platformMaster = createBeanInstance(PlatformMasterBeanInterface.class);
	}
	
	@Override
	public Set<String> getSubordinateIds() throws MospException {
		// 検索条件(部下)設定
		setSubordinateParams();
		// 検索条件設定(検索区分(社員コード))
		setEmployeeCodeType(PlatformConst.SEARCH_FORWARD_MATCH);
		// 部下個人IDセット取得
		return getPersonalIdSet();
	}
	
	@Override
	public Set<HumanDtoInterface> getSubordinates(int workflowType) throws MospException {
		// 人事情報(部下)群を取得
		return new LinkedHashSet<HumanDtoInterface>(getHumanSubordinates(humanType, workflowType));
	}
	
	/**
	 * 人事情報リスト(部下)を取得する。<br>
	 * @param humanType    検索区分
	 * @param workflowType フロー区分
	 * @return 人事情報リスト(部下)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected List<HumanDtoInterface> getHumanSubordinates(String humanType, int workflowType) throws MospException {
		// 検索結果リスト準備
		List<HumanDtoInterface> resultList = new ArrayList<HumanDtoInterface>();
		// 検索条件設定(検索区分(社員コード))
		setEmployeeCodeType(PlatformConst.SEARCH_FORWARD_MATCH);
		// 検索条件(部下)設定
		setSubordinateParams();
		// 検索条件が空白の場合
		if (MospUtility.isEmpty(humanType)) {
			// 部下検索リスト取得
			resultList = search();
			// 検索条件再設定(操作区分)(承認対象者全員が検索対象となるためnullを設定)
			setOperationType(null);
			// 全社員検索リスト取得
			List<HumanDtoInterface> humanList = search();
			// 承認できる社員個人ID群取得
			List<HumanDtoInterface> approvalList = getApprovableForApplicantList(humanList, targetDate, workflowType);
			// 個人IDセットを取得
			Set<String> resultSet = PlatformUtility.getPersonalIdSet(resultList);
			// 部下検索リスト毎に処理
			for (HumanDtoInterface humanDto : approvalList) {
				// 部下検索リストに承認できる社員個人IDが含まれている場合
				if (resultSet.contains(humanDto.getPersonalId())) {
					continue;
				}
				// 検索結果リストに追加
				resultList.add(humanDto);
			}
		}
		// 検索条件が承認すべき申請者の場合
		if (MospUtility.isEqual(humanType, TYPE_APPLICANTS_APPROVE)) {
			// 検索条件再設定(操作区分)(承認対象者全員が検索対象となるためnullを設定)
			setOperationType(null);
			// 検索結果リスト人事情報検索追加
			List<HumanDtoInterface> humanList = search();
			// 承認できる社員個人ID群取得
			resultList = getApprovableForApplicantList(humanList, targetDate, workflowType);
		}
		// 検索条件が部下の場合
		if (MospUtility.isEqual(humanType, TYPE_SUBORDINATE)) {
			// 検索結果リスト人事情報検索追加
			resultList = search();
		}
		// 人事情報リスト(部下)を取得
		return resultList;
	}
	
	/**
	 * 検索条件(部下)を設定する。<br>
	 */
	protected void setSubordinateParams() {
		// 検索条件設定(休退職区分)
		setStateType(PlatformConst.EMPLOYEE_STATE_PRESENCE);
		// 検索条件設定(下位所属要否)
		setNeedLowerSection(true);
		// 検索条件設定(兼務要否)
		setNeedConcurrent(true);
		// 検索条件設定(操作区分)
		setOperationType(MospConst.OPERATION_TYPE_REFER);
	}
	
	/**
	 * ログインユーザが承認できる社員の個人IDセット群を取得する。<br>
	 * 対象人事情報リストから承認ルート適用設定情報を取得し、そのルートコードが
	 * 承認対象ルートリストに含まれるかで、被承認者であるかを確認する。<br>
	 * @param humanList    人事情報リスト
	 * @param targetDate   対象日
	 * @param workflowType フロー区分
	 * @return ログインユーザが承認者の社員個人ID群
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected List<HumanDtoInterface> getApprovableForApplicantList(List<HumanDtoInterface> humanList, Date targetDate,
			int workflowType) throws MospException {
		// スーパーユーザ権限の場合
		if (RoleUtility.isSuper(mospParams)) {
			// 社員リストをそのまま取得(スーパーユーザは全員が承認対象)
			return humanList;
		}
		// 個人IDリスト準備
		List<HumanDtoInterface> list = new ArrayList<HumanDtoInterface>();
		// 承認者がユニットとして登録されているルート群を取得
		Set<String> routeSet = workflowIntegrate.getApproverRouteSet(mospParams.getUser().getPersonalId(), targetDate);
		// 検索人事情報毎に被承認者かどうかを確認して人事情報リストに追加
		for (HumanDtoInterface humanDto : humanList) {
			// ログインユーザが対象者を承認できる場合
			if (isApprovable(humanDto, routeSet, targetDate, workflowType)) {
				// 承認すべき申請者追加
				list.add(humanDto);
			}
		}
		return list;
	}
	
	/**
	 * ログインユーザが対象者を承認できるか確認する。<br>
	 * <br>
	 * 対象者のルート適用情報を取得し、
	 * 承認者(ログインユーザ)がユニットとして登録されているルート群に<br>
	 * 含まれるかを確認する。<br>
	 * <br>
	 * @param humanDto     対象者人事情報
	 * @param routeSet     承認者(ログインユーザ)がユニットとして登録されているルート群
	 * @param targetDate   対象日
	 * @param workflowType フロー区分
	 * @return 確認結果(true：ログインユーザが対象者を承認できる、false：できない)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected boolean isApprovable(HumanDtoInterface humanDto, Set<String> routeSet, Date targetDate, int workflowType)
			throws MospException {
		// 個人ID及び対象日から、適用されている設定を取得
		RouteApplicationDtoInterface dto = platformMaster.getRouteApplication(humanDto, targetDate, workflowType);
		// ルート適用情報が取得できない場合
		if (dto == null) {
			// ログインユーザが対象者を承認でないと判断
			return false;
		}
		// ルート群に含まれるかを確認
		return routeSet.contains(dto.getRouteCode());
	}
	
	@Override
	public void setHumanType(String humanType) {
		this.humanType = humanType;
	}
	
}
