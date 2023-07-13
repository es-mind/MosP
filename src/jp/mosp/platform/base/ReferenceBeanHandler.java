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
package jp.mosp.platform.base;

import jp.mosp.framework.base.BaseBeanHandler;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.bean.file.ExportFieldReferenceBeanInterface;
import jp.mosp.platform.bean.file.ExportReferenceBeanInterface;
import jp.mosp.platform.bean.file.ExportSearchBeanInterface;
import jp.mosp.platform.bean.file.HumanExportBeanInterface;
import jp.mosp.platform.bean.file.HumanImportBeanInterface;
import jp.mosp.platform.bean.file.ImportFieldReferenceBeanInterface;
import jp.mosp.platform.bean.file.ImportReferenceBeanInterface;
import jp.mosp.platform.bean.file.ImportSearchBeanInterface;
import jp.mosp.platform.bean.file.SectionExportBeanInterface;
import jp.mosp.platform.bean.file.TableTypeBeanInterface;
import jp.mosp.platform.bean.file.UserExportBeanInterface;
import jp.mosp.platform.bean.file.UserExtraRoleExportBeanInterface;
import jp.mosp.platform.bean.human.AccountReferenceBeanInterface;
import jp.mosp.platform.bean.human.ConcurrentReferenceBeanInterface;
import jp.mosp.platform.bean.human.EmployeeNumberingBeanInterface;
import jp.mosp.platform.bean.human.EntranceReferenceBeanInterface;
import jp.mosp.platform.bean.human.HumanArrayReferenceBeanInterface;
import jp.mosp.platform.bean.human.HumanBinaryArrayReferenceBeanInterface;
import jp.mosp.platform.bean.human.HumanBinaryHistoryReferenceBeanInterface;
import jp.mosp.platform.bean.human.HumanBinaryNormalReferenceBeanInterface;
import jp.mosp.platform.bean.human.HumanHistoryReferenceBeanInterface;
import jp.mosp.platform.bean.human.HumanInfoExtraBeanInterface;
import jp.mosp.platform.bean.human.HumanNormalReferenceBeanInterface;
import jp.mosp.platform.bean.human.HumanReferenceBeanInterface;
import jp.mosp.platform.bean.human.HumanSearchBeanInterface;
import jp.mosp.platform.bean.human.HumanSubordinateBeanInterface;
import jp.mosp.platform.bean.human.PhoneReferenceBeanInterface;
import jp.mosp.platform.bean.human.RetirementReferenceBeanInterface;
import jp.mosp.platform.bean.human.SuspensionReferenceBeanInterface;
import jp.mosp.platform.bean.message.MessageMailBeanInterface;
import jp.mosp.platform.bean.message.MessageReferenceBeanInterface;
import jp.mosp.platform.bean.message.MessageSearchBeanInterface;
import jp.mosp.platform.bean.portal.PreActionBeanInterface;
import jp.mosp.platform.bean.system.BankBaseReferenceBeanInterface;
import jp.mosp.platform.bean.system.BankBranchReferenceBeanInterface;
import jp.mosp.platform.bean.system.EmploymentContractReferenceBeanInterface;
import jp.mosp.platform.bean.system.EmploymentContractSearchBeanInterface;
import jp.mosp.platform.bean.system.GeneralReferenceBeanInterface;
import jp.mosp.platform.bean.system.IcCardReferenceBeanInterface;
import jp.mosp.platform.bean.system.IcCardSearchBeanInterface;
import jp.mosp.platform.bean.system.NamingReferenceBeanInterface;
import jp.mosp.platform.bean.system.NamingSearchBeanInterface;
import jp.mosp.platform.bean.system.PlatformMasterBeanInterface;
import jp.mosp.platform.bean.system.PositionReferenceBeanInterface;
import jp.mosp.platform.bean.system.PositionSearchBeanInterface;
import jp.mosp.platform.bean.system.PostalCodeReferenceBeanInterface;
import jp.mosp.platform.bean.system.ReceptionIcCardReferenceBeanInterface;
import jp.mosp.platform.bean.system.RoleReferenceBeanInterface;
import jp.mosp.platform.bean.system.SectionReferenceBeanInterface;
import jp.mosp.platform.bean.system.SectionSearchBeanInterface;
import jp.mosp.platform.bean.system.UserExtraRoleReferenceBeanInterface;
import jp.mosp.platform.bean.system.UserMasterReferenceBeanInterface;
import jp.mosp.platform.bean.system.UserMasterSearchBeanInterface;
import jp.mosp.platform.bean.system.WorkPlaceReferenceBeanInterface;
import jp.mosp.platform.bean.system.WorkPlaceSearchBeanInterface;
import jp.mosp.platform.bean.workflow.ApprovalRouteReferenceBeanInterface;
import jp.mosp.platform.bean.workflow.ApprovalRouteSearchBeanInterface;
import jp.mosp.platform.bean.workflow.ApprovalRouteUnitReferenceBeanInterface;
import jp.mosp.platform.bean.workflow.ApprovalUnitReferenceBeanInterface;
import jp.mosp.platform.bean.workflow.ApprovalUnitSearchBeanInterface;
import jp.mosp.platform.bean.workflow.RouteApplicationReferenceBeanInterface;
import jp.mosp.platform.bean.workflow.RouteApplicationReferenceSearchBeanInterface;
import jp.mosp.platform.bean.workflow.RouteApplicationSearchBeanInterface;
import jp.mosp.platform.bean.workflow.SubApproverReferenceBeanInterface;
import jp.mosp.platform.bean.workflow.SubApproverSearchBeanInterface;
import jp.mosp.platform.bean.workflow.WorkflowCommentReferenceBeanInterface;
import jp.mosp.platform.bean.workflow.WorkflowIntegrateBeanInterface;
import jp.mosp.platform.bean.workflow.WorkflowReferenceBeanInterface;

/**
 * 参照用BeanHandlerクラス。<br>
 */
public class ReferenceBeanHandler extends BaseBeanHandler implements ReferenceBeanHandlerInterface {
	
	/**
	 * コンストラクタ。<br>
	 */
	public ReferenceBeanHandler() {
		super();
	}
	
	@Override
	public UserMasterReferenceBeanInterface user() throws MospException {
		return createBeanInstance(UserMasterReferenceBeanInterface.class);
	}
	
	@Override
	public UserExtraRoleReferenceBeanInterface userExtraRole() throws MospException {
		return createBeanInstance(UserExtraRoleReferenceBeanInterface.class);
	}
	
	@Override
	public SectionReferenceBeanInterface section() throws MospException {
		return createBeanInstance(SectionReferenceBeanInterface.class);
	}
	
	@Override
	public PositionReferenceBeanInterface position() throws MospException {
		return createBeanInstance(PositionReferenceBeanInterface.class);
	}
	
	@Override
	public EmploymentContractReferenceBeanInterface employmentContract() throws MospException {
		return createBeanInstance(EmploymentContractReferenceBeanInterface.class);
	}
	
	@Override
	public NamingReferenceBeanInterface naming() throws MospException {
		return createBeanInstance(NamingReferenceBeanInterface.class);
	}
	
	@Override
	public PostalCodeReferenceBeanInterface postalCode() throws MospException {
		return createBeanInstance(PostalCodeReferenceBeanInterface.class);
	}
	
	@Override
	public BankBaseReferenceBeanInterface bankBase() throws MospException {
		return createBeanInstance(BankBaseReferenceBeanInterface.class);
	}
	
	@Override
	public BankBranchReferenceBeanInterface bankBranch() throws MospException {
		return createBeanInstance(BankBranchReferenceBeanInterface.class);
	}
	
	@Override
	public HumanSearchBeanInterface humanSearch() throws MospException {
		return createBeanInstance(HumanSearchBeanInterface.class);
	}
	
	@Override
	public HumanSubordinateBeanInterface humanSubordinate() throws MospException {
		return createBeanInstance(HumanSubordinateBeanInterface.class);
	}
	
	@Override
	public HumanReferenceBeanInterface human() throws MospException {
		return createBeanInstance(HumanReferenceBeanInterface.class);
	}
	
	@Override
	public EntranceReferenceBeanInterface entrance() throws MospException {
		return createBeanInstance(EntranceReferenceBeanInterface.class);
	}
	
	@Override
	public SuspensionReferenceBeanInterface suspension() throws MospException {
		return createBeanInstance(SuspensionReferenceBeanInterface.class);
	}
	
	@Override
	public RetirementReferenceBeanInterface retirement() throws MospException {
		return createBeanInstance(RetirementReferenceBeanInterface.class);
	}
	
	@Override
	public ConcurrentReferenceBeanInterface concurrent() throws MospException {
		return createBeanInstance(ConcurrentReferenceBeanInterface.class);
	}
	
	@Override
	public PhoneReferenceBeanInterface phone() throws MospException {
		return createBeanInstance(PhoneReferenceBeanInterface.class);
	}
	
	@Override
	public AccountReferenceBeanInterface account() throws MospException {
		return createBeanInstance(AccountReferenceBeanInterface.class);
	}
	
	@Override
	public HumanHistoryReferenceBeanInterface humanHistory() throws MospException {
		return createBeanInstance(HumanHistoryReferenceBeanInterface.class);
	}
	
	@Override
	public HumanArrayReferenceBeanInterface humanArray() throws MospException {
		return createBeanInstance(HumanArrayReferenceBeanInterface.class);
	}
	
	@Override
	public HumanNormalReferenceBeanInterface humanNormal() throws MospException {
		return createBeanInstance(HumanNormalReferenceBeanInterface.class);
	}
	
	@Override
	public HumanBinaryArrayReferenceBeanInterface humanBinaryArray() throws MospException {
		return createBeanInstance(HumanBinaryArrayReferenceBeanInterface.class);
	}
	
	@Override
	public HumanBinaryHistoryReferenceBeanInterface humanBinaryHistory() throws MospException {
		return createBeanInstance(HumanBinaryHistoryReferenceBeanInterface.class);
	}
	
	@Override
	public HumanBinaryNormalReferenceBeanInterface humanBinaryNormal() throws MospException {
		return createBeanInstance(HumanBinaryNormalReferenceBeanInterface.class);
	}
	
	@Override
	public WorkPlaceReferenceBeanInterface workPlace() throws MospException {
		return createBeanInstance(WorkPlaceReferenceBeanInterface.class);
	}
	
	@Override
	public WorkPlaceSearchBeanInterface workPlaceSearch() throws MospException {
		return createBeanInstance(WorkPlaceSearchBeanInterface.class);
	}
	
	@Override
	public RoleReferenceBeanInterface role() throws MospException {
		return createBeanInstance(RoleReferenceBeanInterface.class);
	}
	
	@Override
	public EmploymentContractSearchBeanInterface employmentContractSearch() throws MospException {
		return createBeanInstance(EmploymentContractSearchBeanInterface.class);
	}
	
	@Override
	public SectionSearchBeanInterface sectionSearch() throws MospException {
		return (SectionSearchBeanInterface)createBean(SectionSearchBeanInterface.class);
	}
	
	@Override
	public PositionSearchBeanInterface positionSearch() throws MospException {
		return createBeanInstance(PositionSearchBeanInterface.class);
	}
	
	@Override
	public NamingSearchBeanInterface namingSearch() throws MospException {
		return createBeanInstance(NamingSearchBeanInterface.class);
	}
	
	@Override
	public UserMasterSearchBeanInterface userMasterSearch() throws MospException {
		return createBeanInstance(UserMasterSearchBeanInterface.class);
	}
	
	@Override
	public WorkflowIntegrateBeanInterface workflowIntegrate() throws MospException {
		return createBeanInstance(WorkflowIntegrateBeanInterface.class);
	}
	
	@Override
	public WorkflowReferenceBeanInterface workflow() throws MospException {
		return createBeanInstance(WorkflowReferenceBeanInterface.class);
	}
	
	@Override
	public WorkflowCommentReferenceBeanInterface workflowComment() throws MospException {
		return createBeanInstance(WorkflowCommentReferenceBeanInterface.class);
	}
	
	@Override
	public ApprovalUnitReferenceBeanInterface approvalUnit() throws MospException {
		return createBeanInstance(ApprovalUnitReferenceBeanInterface.class);
	}
	
	@Override
	public ApprovalUnitSearchBeanInterface approvalUnitSearch() throws MospException {
		return createBeanInstance(ApprovalUnitSearchBeanInterface.class);
	}
	
	@Override
	public ApprovalRouteReferenceBeanInterface approvalRoute() throws MospException {
		return createBeanInstance(ApprovalRouteReferenceBeanInterface.class);
	}
	
	@Override
	public ApprovalRouteSearchBeanInterface approvalRouteSearch() throws MospException {
		return createBeanInstance(ApprovalRouteSearchBeanInterface.class);
	}
	
	@Override
	public ApprovalRouteUnitReferenceBeanInterface approvalRouteUnit() throws MospException {
		return createBeanInstance(ApprovalRouteUnitReferenceBeanInterface.class);
	}
	
	@Override
	public RouteApplicationReferenceBeanInterface routeApplication() throws MospException {
		return createBeanInstance(RouteApplicationReferenceBeanInterface.class);
	}
	
	@Override
	public RouteApplicationSearchBeanInterface routeApplicationSearch() throws MospException {
		return createBeanInstance(RouteApplicationSearchBeanInterface.class);
	}
	
	@Override
	public RouteApplicationReferenceSearchBeanInterface routeApplicationReferenceSearch() throws MospException {
		return createBeanInstance(RouteApplicationReferenceSearchBeanInterface.class);
	}
	
	@Override
	public SubApproverReferenceBeanInterface subApprover() throws MospException {
		return createBeanInstance(SubApproverReferenceBeanInterface.class);
	}
	
	@Override
	public SubApproverSearchBeanInterface subApproverSearch() throws MospException {
		return createBeanInstance(SubApproverSearchBeanInterface.class);
	}
	
	@Override
	public MessageReferenceBeanInterface message() throws MospException {
		return createBeanInstance(MessageReferenceBeanInterface.class);
	}
	
	@Override
	public MessageSearchBeanInterface messageSearch() throws MospException {
		return createBeanInstance(MessageSearchBeanInterface.class);
	}
	
	@Override
	public MessageMailBeanInterface messageMail() throws MospException {
		return createBeanInstance(MessageMailBeanInterface.class);
	}
	
	@Override
	public ExportSearchBeanInterface exportSearch() throws MospException {
		return createBeanInstance(ExportSearchBeanInterface.class);
	}
	
	@Override
	public ExportReferenceBeanInterface export() throws MospException {
		return createBeanInstance(ExportReferenceBeanInterface.class);
	}
	
	@Override
	public ExportFieldReferenceBeanInterface exportField() throws MospException {
		return createBeanInstance(ExportFieldReferenceBeanInterface.class);
	}
	
	@Override
	public ImportSearchBeanInterface importSearch() throws MospException {
		return createBeanInstance(ImportSearchBeanInterface.class);
	}
	
	@Override
	public ImportReferenceBeanInterface importRefer() throws MospException {
		return createBeanInstance(ImportReferenceBeanInterface.class);
	}
	
	@Override
	public ImportFieldReferenceBeanInterface importField() throws MospException {
		return createBeanInstance(ImportFieldReferenceBeanInterface.class);
	}
	
	@Override
	public HumanExportBeanInterface humanExport() throws MospException {
		return createBeanInstance(HumanExportBeanInterface.class);
	}
	
	@Override
	public UserExportBeanInterface userExport() throws MospException {
		return createBeanInstance(UserExportBeanInterface.class);
	}
	
	@Override
	public UserExtraRoleExportBeanInterface userExtraRoleExport() throws MospException {
		return createBeanInstance(UserExtraRoleExportBeanInterface.class);
	}
	
	@Override
	public SectionExportBeanInterface sectionExport() throws MospException {
		return createBeanInstance(SectionExportBeanInterface.class);
	}
	
	@Override
	public GeneralReferenceBeanInterface generalReference() throws MospException {
		return createBeanInstance(GeneralReferenceBeanInterface.class);
	}
	
	@Override
	public IcCardReferenceBeanInterface icCard() throws MospException {
		return createBeanInstance(IcCardReferenceBeanInterface.class);
	}
	
	@Override
	public IcCardSearchBeanInterface icCardSearch() throws MospException {
		return createBeanInstance(IcCardSearchBeanInterface.class);
	}
	
	@Override
	public ReceptionIcCardReferenceBeanInterface receptionIcCard() throws MospException {
		return createBeanInstance(ReceptionIcCardReferenceBeanInterface.class);
	}
	
	@Override
	public EmployeeNumberingBeanInterface employeeNumbering() throws MospException {
		return createBeanInstance(EmployeeNumberingBeanInterface.class);
	}
	
	@Override
	public HumanInfoExtraBeanInterface humanInfoExtra(String className) throws MospException {
		return (HumanInfoExtraBeanInterface)createBean(className);
	}
	
	@Override
	public HumanImportBeanInterface humanImport() throws MospException {
		return createBeanInstance(HumanImportBeanInterface.class);
	}
	
	@Override
	public TableTypeBeanInterface tableType() throws MospException {
		return createBeanInstance(TableTypeBeanInterface.class);
	}
	
	@Override
	public PreActionBeanInterface preAction() throws MospException {
		return createBeanInstance(PreActionBeanInterface.class);
	}
	
	@Override
	public PlatformMasterBeanInterface master() throws MospException {
		return createBeanInstance(PlatformMasterBeanInterface.class);
	}
	
}
