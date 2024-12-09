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
package jp.mosp.platform.base;

import jp.mosp.framework.base.BaseBeanHandler;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.bean.file.ExportFieldRegistBeanInterface;
import jp.mosp.platform.bean.file.ExportRegistBeanInterface;
import jp.mosp.platform.bean.file.HumanImportBeanInterface;
import jp.mosp.platform.bean.file.ImportBeanInterface;
import jp.mosp.platform.bean.file.ImportFieldRegistBeanInterface;
import jp.mosp.platform.bean.file.ImportRegistBeanInterface;
import jp.mosp.platform.bean.file.PositionImportBeanInterface;
import jp.mosp.platform.bean.file.SectionImportBeanInterface;
import jp.mosp.platform.bean.file.TemplateOutputBeanInterface;
import jp.mosp.platform.bean.file.UserExtraRoleImportBeanInterface;
import jp.mosp.platform.bean.file.UserImportBeanInterface;
import jp.mosp.platform.bean.file.UserPasswordImportBeanInterface;
import jp.mosp.platform.bean.file.impl.UnitPersonImportBean;
import jp.mosp.platform.bean.file.impl.UnitSectionImportBean;
import jp.mosp.platform.bean.human.AccountRegistBeanInterface;
import jp.mosp.platform.bean.human.AddressRegistBeanInterface;
import jp.mosp.platform.bean.human.ConcurrentRegistBeanInterface;
import jp.mosp.platform.bean.human.EntranceRegistBeanInterface;
import jp.mosp.platform.bean.human.HistoryBasicDeleteBeanInterface;
import jp.mosp.platform.bean.human.HumanArrayRegistBeanInterface;
import jp.mosp.platform.bean.human.HumanBinaryArrayRegistBeanInterface;
import jp.mosp.platform.bean.human.HumanBinaryHistoryRegistBeanInterface;
import jp.mosp.platform.bean.human.HumanBinaryNormalRegistBeanInterface;
import jp.mosp.platform.bean.human.HumanHistoryRegistBeanInterface;
import jp.mosp.platform.bean.human.HumanNormalRegistBeanInterface;
import jp.mosp.platform.bean.human.HumanRegistBeanInterface;
import jp.mosp.platform.bean.human.PhoneRegistBeanInterface;
import jp.mosp.platform.bean.human.RetirementRegistBeanInterface;
import jp.mosp.platform.bean.human.SuspensionRegistBeanInterface;
import jp.mosp.platform.bean.message.MessageRegistBeanInterface;
import jp.mosp.platform.bean.portal.AuthBeanInterface;
import jp.mosp.platform.bean.portal.MospUserBeanInterface;
import jp.mosp.platform.bean.portal.PasswordCheckBeanInterface;
import jp.mosp.platform.bean.portal.PortalBeanInterface;
import jp.mosp.platform.bean.portal.UserCheckBeanInterface;
import jp.mosp.platform.bean.system.AppPropertyRegistBeanInterface;
import jp.mosp.platform.bean.system.EmploymentContractRegistBeanInterface;
import jp.mosp.platform.bean.system.GeneralRegistBeanInterface;
import jp.mosp.platform.bean.system.IcCardRegistBeanInterface;
import jp.mosp.platform.bean.system.NamingRegistBeanInterface;
import jp.mosp.platform.bean.system.PositionRegistBeanInterface;
import jp.mosp.platform.bean.system.ReceptionIcCardRegistBeanInterface;
import jp.mosp.platform.bean.system.SectionRegistBeanInterface;
import jp.mosp.platform.bean.system.UserAccountRegistBeanInterface;
import jp.mosp.platform.bean.system.WorkPlaceRegistBeanInterface;
import jp.mosp.platform.bean.workflow.ApprovalRouteRegistBeanInterface;
import jp.mosp.platform.bean.workflow.ApprovalRouteUnitRegistBeanInterface;
import jp.mosp.platform.bean.workflow.ApprovalUnitRegistBeanInterface;
import jp.mosp.platform.bean.workflow.RouteApplicationRegistBeanInterface;
import jp.mosp.platform.bean.workflow.SubApproverRegistBeanInterface;
import jp.mosp.platform.bean.workflow.WorkflowCommentRegistBeanInterface;
import jp.mosp.platform.bean.workflow.WorkflowRegistBeanInterface;

/**
 * MosPプラットフォーム用BeanHandler。<br>
 * <br>
 */
public class PlatformBeanHandler extends BaseBeanHandler implements PlatformBeanHandlerInterface {
	
	/**
	 * コンストラクタ。
	 */
	public PlatformBeanHandler() {
		super();
	}
	
	@Override
	public AuthBeanInterface auth() throws MospException {
		return createBeanInstance(AuthBeanInterface.class);
	}
	
	@Override
	public MospUserBeanInterface mospUser() throws MospException {
		return createBeanInstance(MospUserBeanInterface.class);
	}
	
	@Override
	public UserCheckBeanInterface userCheck() throws MospException {
		return createBeanInstance(UserCheckBeanInterface.class);
	}
	
	@Override
	public PasswordCheckBeanInterface passwordCheck() throws MospException {
		return createBeanInstance(PasswordCheckBeanInterface.class);
	}
	
	@Override
	public UserAccountRegistBeanInterface userAccountRegist() throws MospException {
		return createBeanInstance(UserAccountRegistBeanInterface.class);
	}
	
	@Override
	public EmploymentContractRegistBeanInterface employmentContractRegist() throws MospException {
		return createBeanInstance(EmploymentContractRegistBeanInterface.class);
	}
	
	@Override
	public SectionRegistBeanInterface sectionRegist() throws MospException {
		return createBeanInstance(SectionRegistBeanInterface.class);
	}
	
	@Override
	public WorkPlaceRegistBeanInterface workPlaceRegist() throws MospException {
		return createBeanInstance(WorkPlaceRegistBeanInterface.class);
	}
	
	@Override
	public PositionRegistBeanInterface positionRegist() throws MospException {
		return createBeanInstance(PositionRegistBeanInterface.class);
	}
	
	@Override
	public NamingRegistBeanInterface namingRegist() throws MospException {
		return createBeanInstance(NamingRegistBeanInterface.class);
	}
	
	@Override
	public HumanRegistBeanInterface humanRegist() throws MospException {
		return createBeanInstance(HumanRegistBeanInterface.class);
	}
	
	@Override
	public HistoryBasicDeleteBeanInterface historyBasicDelete() throws MospException {
		return createBeanInstance(HistoryBasicDeleteBeanInterface.class);
	}
	
	@Override
	public EntranceRegistBeanInterface entranceRegist() throws MospException {
		return createBeanInstance(EntranceRegistBeanInterface.class);
	}
	
	@Override
	public RetirementRegistBeanInterface retirementRegist() throws MospException {
		return createBeanInstance(RetirementRegistBeanInterface.class);
	}
	
	@Override
	public SuspensionRegistBeanInterface suspensionRegist() throws MospException {
		return createBeanInstance(SuspensionRegistBeanInterface.class);
	}
	
	@Override
	public ConcurrentRegistBeanInterface concurrentRegist() throws MospException {
		return createBeanInstance(ConcurrentRegistBeanInterface.class);
	}
	
	@Override
	public AddressRegistBeanInterface addressRegist() throws MospException {
		return createBeanInstance(AddressRegistBeanInterface.class);
	}
	
	@Override
	public PhoneRegistBeanInterface phoneRegist() throws MospException {
		return createBeanInstance(PhoneRegistBeanInterface.class);
	}
	
	@Override
	public AccountRegistBeanInterface accountRegist() throws MospException {
		return createBeanInstance(AccountRegistBeanInterface.class);
	}
	
	@Override
	public IcCardRegistBeanInterface icCardRegist() throws MospException {
		return createBeanInstance(IcCardRegistBeanInterface.class);
	}
	
	@Override
	public ReceptionIcCardRegistBeanInterface receptionIcCardRegist() throws MospException {
		return createBeanInstance(ReceptionIcCardRegistBeanInterface.class);
	}
	
	@Override
	public HumanHistoryRegistBeanInterface humanHistoryRegist() throws MospException {
		return createBeanInstance(HumanHistoryRegistBeanInterface.class);
	}
	
	@Override
	public HumanArrayRegistBeanInterface humanArrayRegist() throws MospException {
		return createBeanInstance(HumanArrayRegistBeanInterface.class);
	}
	
	@Override
	public HumanNormalRegistBeanInterface humanNormalRegist() throws MospException {
		return createBeanInstance(HumanNormalRegistBeanInterface.class);
	}
	
	@Override
	public HumanBinaryArrayRegistBeanInterface humanBinaryArrayRegist() throws MospException {
		return createBeanInstance(HumanBinaryArrayRegistBeanInterface.class);
	}
	
	@Override
	public HumanBinaryHistoryRegistBeanInterface humanBinaryHistoryRegist() throws MospException {
		return createBeanInstance(HumanBinaryHistoryRegistBeanInterface.class);
	}
	
	@Override
	public HumanBinaryNormalRegistBeanInterface humanBinaryNormalRegist() throws MospException {
		return createBeanInstance(HumanBinaryNormalRegistBeanInterface.class);
	}
	
	@Override
	public HistoryBasicDeleteBeanInterface historyBasicDelete(String className) throws MospException {
		return (HistoryBasicDeleteBeanInterface)createBean(className);
	}
	
	@Override
	public WorkflowRegistBeanInterface workflowRegist() throws MospException {
		return createBeanInstance(WorkflowRegistBeanInterface.class);
	}
	
	@Override
	public WorkflowCommentRegistBeanInterface workflowCommentRegist() throws MospException {
		return createBeanInstance(WorkflowCommentRegistBeanInterface.class);
	}
	
	@Override
	public ApprovalUnitRegistBeanInterface approvalUnitRegist() throws MospException {
		return createBeanInstance(ApprovalUnitRegistBeanInterface.class);
	}
	
	@Override
	public ApprovalRouteRegistBeanInterface approvalRouteRegist() throws MospException {
		return createBeanInstance(ApprovalRouteRegistBeanInterface.class);
	}
	
	@Override
	public ApprovalRouteUnitRegistBeanInterface approvalRouteUnitRegist() throws MospException {
		return createBeanInstance(ApprovalRouteUnitRegistBeanInterface.class);
	}
	
	@Override
	public RouteApplicationRegistBeanInterface routeApplicationRegist() throws MospException {
		return createBeanInstance(RouteApplicationRegistBeanInterface.class);
	}
	
	@Override
	public SubApproverRegistBeanInterface subApproverRegist() throws MospException {
		return createBeanInstance(SubApproverRegistBeanInterface.class);
	}
	
	@Override
	public MessageRegistBeanInterface messageRegist() throws MospException {
		return createBeanInstance(MessageRegistBeanInterface.class);
	}
	
	@Override
	public GeneralRegistBeanInterface generalRegist() throws MospException {
		return createBeanInstance(GeneralRegistBeanInterface.class);
	}
	
	@Override
	public ExportRegistBeanInterface exportRegist() throws MospException {
		return createBeanInstance(ExportRegistBeanInterface.class);
	}
	
	@Override
	public ExportFieldRegistBeanInterface exportFieldRegist() throws MospException {
		return createBeanInstance(ExportFieldRegistBeanInterface.class);
	}
	
	@Override
	public TemplateOutputBeanInterface templateOutput() throws MospException {
		return createBeanInstance(TemplateOutputBeanInterface.class);
	}
	
	@Override
	public ImportRegistBeanInterface importRegist() throws MospException {
		return createBeanInstance(ImportRegistBeanInterface.class);
	}
	
	@Override
	public ImportFieldRegistBeanInterface importFieldRegist() throws MospException {
		return createBeanInstance(ImportFieldRegistBeanInterface.class);
	}
	
	@Override
	public HumanImportBeanInterface humanImport() throws MospException {
		return createBeanInstance(HumanImportBeanInterface.class);
	}
	
	@Override
	public UserImportBeanInterface userImport() throws MospException {
		return createBeanInstance(UserImportBeanInterface.class);
	}
	
	@Override
	public UserExtraRoleImportBeanInterface userExtraRoleImport() throws MospException {
		return createBeanInstance(UserExtraRoleImportBeanInterface.class);
	}
	
	@Override
	public SectionImportBeanInterface sectionImport() throws MospException {
		return createBeanInstance(SectionImportBeanInterface.class);
	}
	
	@Override
	public PositionImportBeanInterface positionImport() throws MospException {
		return createBeanInstance(PositionImportBeanInterface.class);
	}
	
	@Override
	public ImportBeanInterface unitSectionImport() throws MospException {
		return createBeanInstance(UnitSectionImportBean.class);
	}
	
	@Override
	public ImportBeanInterface unitPersonImport() throws MospException {
		return createBeanInstance(UnitPersonImportBean.class);
	}
	
	@Override
	public UserPasswordImportBeanInterface userPasswordImport() throws MospException {
		return createBeanInstance(UserPasswordImportBeanInterface.class);
	}
	
	@Override
	public PortalBeanInterface portal(String className) throws MospException {
		return (PortalBeanInterface)createBean(className);
	}
	
	@Override
	public AppPropertyRegistBeanInterface appPropertyRegist() throws MospException {
		return createBeanInstance(AppPropertyRegistBeanInterface.class);
	}
	
}
