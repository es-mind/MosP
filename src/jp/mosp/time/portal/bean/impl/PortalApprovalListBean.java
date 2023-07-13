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
package jp.mosp.time.portal.bean.impl;

import java.util.Map;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.RoleUtility;
import jp.mosp.platform.bean.portal.PortalBeanInterface;
import jp.mosp.platform.bean.portal.impl.PortalBean;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;
import jp.mosp.time.bean.ApprovalInfoReferenceBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.management.action.ApprovalListAction;

/**
 * ポータル画面未承認一覧表示クラス。
 * 勤怠管理権限者にしか表示しない。
 * 表示を押下すると検索一覧を表示した未承認管理一覧へ遷移。
 */
public class PortalApprovalListBean extends PortalBean implements PortalBeanInterface {
	
	/**
	 * パス(ポータル用メッセージJSP)。
	 */
	protected static final String					PATH_PORTAL_VIEW							= "/jsp/time/portal/portalApprovalList.jsp";
	
	/**
	 * ポータルパラメータキー(勤怠承認)
	 */
	public static final String						PRM_APPROVAL_LIST_WORK_MANAGE				= "Attendance";
	
	/**
	 * 
	 * ポータルパラメータキー(残業承認)
	 */
	public static final String						PRM_APPROVAL_LIST_OVERTIME_WORK				= "Overtime";
	
	/**
	 * ポータルパラメータキー(休暇承認)
	 */
	public static final String						PRM_APPROVAL_LIST_VACATION					= "Holiday";
	
	/**
	 * ポータルパラメータキー(休日出勤承認)
	 */
	public static final String						PRM_APPROVAL_LIST_HOLIDAY_GOINGWORK			= "WorkOnHoliday";
	
	/**
	 * ポータルパラメータキー(代休承認)
	 */
	public static final String						PRM_APPROVAL_LIST_COMPENSATORY_HOLIDAY		= "SubHoliday";
	
	/**
	 * ポータルパラメータキー(勤務形態変更承認)
	 */
	public static final String						PRM_APPROVAL_LIST_WORK_TYPE_CHANGE			= "WorkTypeChange";
	
	/**
	 * ポータルパラメータキー(時差出勤承認)
	 */
	public static final String						PRM_APPROVAL_LIST_TIME_DIFFERENCE_GOINGWORK	= "ApprovedDifference";
	
	/**
	 * ポータルパラメータキー(全承認)
	 */
	public static final String						PRM_APPROVAL_LIST_ALL_APPROVAL				= "AllApproval";
	
	/**
	 * ポータルパラメータキー(全解除承認)
	 */
	public static final String						PRM_APPROVAL_LIST_ALL_CANCEL				= "AllCancel";
	
	/**
	 * 承認情報参照インターフェース。
	 */
	protected ApprovalInfoReferenceBeanInterface	approvalInfoRefer;
	
	
	/**
	 * {@link PortalBean#PortalBean()}を実行する。<br>
	 */
	public PortalApprovalListBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// 参照クラス準備
		approvalInfoRefer = createBeanInstance(ApprovalInfoReferenceBeanInterface.class);
	}
	
	/**
	 * 初期表示処理を行う。<br>
	 * @throws MospException 例外処理発生時
	 */
	@Override
	public void show() throws MospException {
		// 承認権限確認
		if (RoleUtility.isApprover(mospParams) == false) {
			return;
		}
		// ポータル用JSPパス追加
		addPortalViewList(PATH_PORTAL_VIEW);
		// ログインユーザの個人IDを取得
		String personalId = mospParams.getUser().getPersonalId();
		// 範囲設定
		setRangeMap(ApprovalListAction.MENU_APPROVAL_LIST);
		// 承認可能ワークフロー情報群(勤怠)を取得
		Map<String, Map<Long, WorkflowDtoInterface>> approvableMap = approvalInfoRefer.getApprovableMap(personalId);
		// 代理承認可能ワークフロー情報群(勤怠)を取得
		Map<String, Map<Long, WorkflowDtoInterface>> subApprovableMap = approvalInfoRefer
			.getSubApprovableMap(personalId, approvableMap);
		// 解除承認可能ワークフロー情報群(勤怠)を取得
		Map<String, Map<Long, WorkflowDtoInterface>> cancelableMap = approvalInfoRefer.getCancelableMap(personalId);
		// 代理解除承認可能ワークフロー情報群(勤怠)を取得
		Map<String, Map<Long, WorkflowDtoInterface>> subCancelableMap = approvalInfoRefer
			.getSubCancelableMap(personalId, cancelableMap);
		// VOに設定
		setVoList(approvableMap, subApprovableMap, cancelableMap, subCancelableMap);
		// 範囲設定除去
		removeRangeMap();
	}
	
	@Override
	public void regist() {
		// 処理なし
	}
	
	/**
	 * 画面上部に出力する各未承認件数の取得を行う。<br>
	 * @param approvableMap    承認可能ワークフロー情報群(勤怠)
	 * @param subApprovableMap 代理承認可能申請情報群(勤怠)
	 * @param cancelableMap    解除承認可能ワークフロー情報群(勤怠)
	 * @param subCancelableMap 代理解除承認可能申請情報群(勤怠)
	 * @throws MospException 未承認情報の取得に失敗した場合
	 */
	protected void setVoList(Map<String, Map<Long, WorkflowDtoInterface>> approvableMap,
			Map<String, Map<Long, WorkflowDtoInterface>> subApprovableMap,
			Map<String, Map<Long, WorkflowDtoInterface>> cancelableMap,
			Map<String, Map<Long, WorkflowDtoInterface>> subCancelableMap) throws MospException {
		// 各承認可能件数の取得
		int attendanceCount = approvableMap.get(TimeConst.CODE_FUNCTION_WORK_MANGE).size();
		int overTimeCount = approvableMap.get(TimeConst.CODE_FUNCTION_OVER_WORK).size();
		int holidayCount = approvableMap.get(TimeConst.CODE_FUNCTION_VACATION).size();
		int workOnHolidayCount = approvableMap.get(TimeConst.CODE_FUNCTION_WORK_HOLIDAY).size();
		int subHolidayCount = approvableMap.get(TimeConst.CODE_FUNCTION_COMPENSATORY_HOLIDAY).size();
		int differenceCount = approvableMap.get(TimeConst.CODE_FUNCTION_DIFFERENCE).size();
		int workTypeChangeCount = approvableMap.get(TimeConst.CODE_FUNCTION_WORK_TYPE_CHANGE).size();
		// 代理分件数追加
		attendanceCount += subApprovableMap.get(TimeConst.CODE_FUNCTION_WORK_MANGE).size();
		overTimeCount += subApprovableMap.get(TimeConst.CODE_FUNCTION_OVER_WORK).size();
		holidayCount += subApprovableMap.get(TimeConst.CODE_FUNCTION_VACATION).size();
		workOnHolidayCount += subApprovableMap.get(TimeConst.CODE_FUNCTION_WORK_HOLIDAY).size();
		subHolidayCount += subApprovableMap.get(TimeConst.CODE_FUNCTION_COMPENSATORY_HOLIDAY).size();
		differenceCount += subApprovableMap.get(TimeConst.CODE_FUNCTION_DIFFERENCE).size();
		workTypeChangeCount += subApprovableMap.get(TimeConst.CODE_FUNCTION_WORK_TYPE_CHANGE).size();
		// データをVOに設定
		putPortalParameter(PRM_APPROVAL_LIST_WORK_MANAGE, String.valueOf(attendanceCount));
		putPortalParameter(PRM_APPROVAL_LIST_OVERTIME_WORK, String.valueOf(overTimeCount));
		putPortalParameter(PRM_APPROVAL_LIST_VACATION, String.valueOf(holidayCount));
		putPortalParameter(PRM_APPROVAL_LIST_HOLIDAY_GOINGWORK, String.valueOf(workOnHolidayCount));
		putPortalParameter(PRM_APPROVAL_LIST_COMPENSATORY_HOLIDAY, String.valueOf(subHolidayCount));
		putPortalParameter(PRM_APPROVAL_LIST_TIME_DIFFERENCE_GOINGWORK, String.valueOf(differenceCount));
		putPortalParameter(PRM_APPROVAL_LIST_WORK_TYPE_CHANGE, String.valueOf(workTypeChangeCount));
		putPortalParameter(PRM_APPROVAL_LIST_ALL_APPROVAL, String.valueOf(attendanceCount + overTimeCount + holidayCount
				+ workOnHolidayCount + subHolidayCount + differenceCount + workTypeChangeCount));
		putPortalParameter(PRM_APPROVAL_LIST_ALL_CANCEL,
				String.valueOf(cancelableMap.get(TimeConst.CODE_FUNCTION_WORK_MANGE).size()
						+ cancelableMap.get(TimeConst.CODE_FUNCTION_OVER_WORK).size()
						+ cancelableMap.get(TimeConst.CODE_FUNCTION_VACATION).size()
						+ cancelableMap.get(TimeConst.CODE_FUNCTION_WORK_HOLIDAY).size()
						+ cancelableMap.get(TimeConst.CODE_FUNCTION_COMPENSATORY_HOLIDAY).size()
						+ cancelableMap.get(TimeConst.CODE_FUNCTION_DIFFERENCE).size()
						+ cancelableMap.get(TimeConst.CODE_FUNCTION_WORK_TYPE_CHANGE).size()
						+ subCancelableMap.get(TimeConst.CODE_FUNCTION_WORK_MANGE).size()
						+ subCancelableMap.get(TimeConst.CODE_FUNCTION_OVER_WORK).size()
						+ subCancelableMap.get(TimeConst.CODE_FUNCTION_VACATION).size()
						+ subCancelableMap.get(TimeConst.CODE_FUNCTION_WORK_HOLIDAY).size()
						+ subCancelableMap.get(TimeConst.CODE_FUNCTION_COMPENSATORY_HOLIDAY).size()
						+ subCancelableMap.get(TimeConst.CODE_FUNCTION_DIFFERENCE).size()
						+ subCancelableMap.get(TimeConst.CODE_FUNCTION_WORK_TYPE_CHANGE).size()));
	}
	
}
