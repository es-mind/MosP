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
package jp.mosp.time.bean.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.human.HumanReferenceBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.time.bean.ApplicationSearchBeanInterface;
import jp.mosp.time.dao.settings.ApplicationDaoInterface;
import jp.mosp.time.dto.settings.ApplicationDtoInterface;

/**
 * 設定適用管理検索クラス。
 */
public class ApplicationSearchBean extends PlatformBean implements ApplicationSearchBeanInterface {
	
	/**
	 * 設定適用マスタDAO。
	 */
	protected ApplicationDaoInterface	dao;
	
	/**
	 * 有効日。
	 */
	private Date						activateDate;
	
	/**
	 * 設定適用コード。
	 */
	private String						applicationCode;
	
	/**
	 * 設定適用区分。
	 */
	private String						applicationType;
	
	/**
	 * 設定適用名称。
	 */
	private String						applicationName;
	
	/**
	 * 設定適用略称。
	 */
	private String						applicationAbbr;
	
	/**
	 * 勤怠設定コード。
	 */
	private String						workSettingCode;
	
	/**
	 * カレンダコード。
	 */
	private String						scheduleCode;
	
	/**
	 * 有給休暇設定コード。
	 */
	private String						paidHolidayCode;
	
	/**
	 * 勤務地コード。
	 */
	private String						workPlaceCode;
	
	/**
	 * 雇用契約コード。
	 */
	private String						employmentCode;
	
	/**
	 * 所属コード。
	 */
	private String						sectionCode;
	
	/**
	 * 職位名称。
	 */
	private String						positionCode;
	
	/**
	 * 社員コード。
	 */
	private String						employeeCode;
	
	/**
	 * 有効無効フラグ。
	 */
	private String						inactivateFlag;
	
	
	/**
	 * コンストラクタ。
	 */
	public ApplicationSearchBean() {
		// 処理無し
	}
	
	@Override
	public void initBean() throws MospException {
		// 設定適用マスタDAO取得
		dao = createDaoInstance(ApplicationDaoInterface.class);
	}
	
	@Override
	public List<ApplicationDtoInterface> getSearchList() throws MospException {
		// Mapに検索条件を設定
		Map<String, Object> param = dao.getParamsMap();
		param.put("activateDate", activateDate);
		param.put("applicationCode", applicationCode);
		param.put("applicationType", getInteger(applicationType));
		param.put("applicationName", applicationName);
		param.put("applicationAbbr", applicationAbbr);
		param.put("workSettingCode", workSettingCode);
		param.put("scheduleCode", scheduleCode);
		param.put("paidHolidayCode", paidHolidayCode);
		param.put("inactivateFlag", inactivateFlag);
		param.put("workPlaceCode", "");
		param.put("employmentCode", "");
		param.put("sectionCode", "");
		param.put("positionCode", "");
		param.put("personalId", new ArrayList<String>());
		// 設定適用区分確認
		if (PlatformConst.APPLICATION_TYPE_MASTER.equals(applicationType)) {
			param.put("workPlaceCode", workPlaceCode);
			param.put("employmentCode", employmentCode);
			param.put("sectionCode", sectionCode);
			param.put("positionCode", positionCode);
		} else {
			// 人事情報参照クラス準備
			HumanReferenceBeanInterface human = (HumanReferenceBeanInterface)createBean(
					HumanReferenceBeanInterface.class);
			// 検索条件設定(個人ID)
			param.put("personalId", human.getPersonalIdList(employeeCode, activateDate));
			// エラー確認
			if (mospParams.hasErrorMessage()) {
				return new ArrayList<ApplicationDtoInterface>();
			}
		}
		// 検索
		return dao.findForSearch(param);
	}
	
	@Override
	public void setActivateDate(Date activateDate) {
		this.activateDate = getDateClone(activateDate);
	}
	
	@Override
	public void setApplicationCode(String applicationCode) {
		this.applicationCode = applicationCode;
	}
	
	@Override
	public void setApplicationType(String applicationType) {
		this.applicationType = applicationType;
	}
	
	@Override
	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}
	
	@Override
	public void setApplicationAbbr(String applicationAbbr) {
		this.applicationAbbr = applicationAbbr;
	}
	
	@Override
	public void setWorkSettingCode(String workSettingCode) {
		this.workSettingCode = workSettingCode;
	}
	
	@Override
	public void setScheduleCode(String scheduleCode) {
		this.scheduleCode = scheduleCode;
	}
	
	@Override
	public void setPaidHolidayCode(String paidHolidayCode) {
		this.paidHolidayCode = paidHolidayCode;
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
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}
	
	@Override
	public void setInactivateFlag(String inactivateFlag) {
		this.inactivateFlag = inactivateFlag;
	}
}
