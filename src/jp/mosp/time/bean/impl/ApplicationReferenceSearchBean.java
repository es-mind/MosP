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

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.bean.human.HumanSearchBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.time.base.TimeBean;
import jp.mosp.time.bean.ApplicationReferenceBeanInterface;
import jp.mosp.time.bean.ApplicationReferenceSearchBeanInterface;
import jp.mosp.time.bean.CutoffReferenceBeanInterface;
import jp.mosp.time.bean.PaidHolidayReferenceBeanInterface;
import jp.mosp.time.bean.ScheduleUtilBeanInterface;
import jp.mosp.time.bean.TimeSettingReferenceBeanInterface;
import jp.mosp.time.dto.settings.ApplicationDtoInterface;
import jp.mosp.time.dto.settings.ApplicationReferenceDtoInterface;
import jp.mosp.time.dto.settings.TimeSettingDtoInterface;
import jp.mosp.time.dto.settings.impl.ApplicationReferenceDto;

/**
 * 設定適用参照画面検索クラス。
 */
public class ApplicationReferenceSearchBean extends TimeBean implements ApplicationReferenceSearchBeanInterface {
	
	/**
	 * 有効日。
	 */
	private Date								activateDate;
	
	/**
	 * 社員コード。
	 */
	private String								employeeCode;
	
	/**
	 * 社員名。
	 */
	private String								employeeName;
	
	/**
	 * 勤務地コード。
	 */
	private String								workPlaceCode;
	
	/**
	 * 雇用契約コード。
	 */
	private String								employmentCode;
	
	/**
	 * 所属名称コード。
	 */
	private String								sectionCode;
	
	/**
	 * 職位コード。
	 */
	private String								positionCode;
	
	/**
	 * 設定適用コード。
	 */
	private String								applicationCode;
	
	/**
	 * 設定適用名。
	 */
	private String								applicationName;
	
	/**
	 * 勤怠設定コード。
	 */
	private String								timeSettingCode;
	
	/**
	 * 締日設定コード。
	 */
	private String								cutoffCode;
	
	/**
	 * カレンダコード。
	 */
	private String								scheduleCode;
	
	/**
	 * 有給休暇設定コード。
	 */
	private String								paidHolidayCode;
	
	/**
	 * 人事検索クラス。
	 */
	private HumanSearchBeanInterface			humanSearch;
	
	/**
	 * 勤怠設定適用クラス。
	 */
	private ApplicationReferenceBeanInterface	applicationRefer;
	
	/**
	 * 勤怠設定クラス。
	 */
	private TimeSettingReferenceBeanInterface	timeReference;
	
	/**
	 * カレンダユーティリティ処理。<br>
	 */
	private ScheduleUtilBeanInterface			scheduleUtil;
	
	/**
	 * 締日設定検索クラス。
	 */
	private CutoffReferenceBeanInterface		cutoffRefer;
	
	/**
	 * 有休設定検索クラス。
	 */
	private PaidHolidayReferenceBeanInterface	paidHolidayRefer;
	
	
	/**
	 * コンストラクタ。
	 */
	public ApplicationReferenceSearchBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// 取得
		humanSearch = createBeanInstance(HumanSearchBeanInterface.class);
		applicationRefer = createBeanInstance(ApplicationReferenceBeanInterface.class);
		scheduleUtil = createBeanInstance(ScheduleUtilBeanInterface.class);
		cutoffRefer = createBeanInstance(CutoffReferenceBeanInterface.class);
		paidHolidayRefer = createBeanInstance(PaidHolidayReferenceBeanInterface.class);
		timeReference = createBeanInstance(TimeSettingReferenceBeanInterface.class);
	}
	
	@Override
	public List<ApplicationReferenceDtoInterface> getSearchList() throws MospException {
		// 検索結果リスト準備
		List<ApplicationReferenceDtoInterface> list = new ArrayList<ApplicationReferenceDtoInterface>();
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
		List<HumanDtoInterface> humanList = humanSearch.search();
		// 勤怠設定配列
		String[][] aryTimeSet = timeReference.getSelectArray(activateDate, false);
		// カレンダー配列
		String[][] arySchedule = scheduleUtil.getSelectArray(activateDate, false);
		// 締日配列
		String[][] aryCutoff = cutoffRefer.getSelectArray(activateDate, false);
		// 有休休暇配列
		String[][] aryPaidHoliday = paidHolidayRefer.getSelectArray(activateDate, false);
		// 検索結果から人事情報リストを作成
		for (HumanDtoInterface humanDto : humanList) {
			// 個人IDだけ収集
			ApplicationDtoInterface applicationDto = applicationRefer.findForPerson(humanDto.getPersonalId(),
					activateDate);
			// 設定適用検索確認
			if (isApplicaationMatch(applicationDto) == false) {
				continue;
			}
			TimeSettingDtoInterface timeSettingDto = null;
			// 勤怠設定検索
			if (applicationDto != null) {
				timeSettingDto = timeReference.getTimeSettingInfo(applicationDto.getWorkSettingCode(), activateDate);
			}
			// 締日検索確認
			if (isCutoffMatch(timeSettingDto) == false) {
				continue;
			}
			// 設定適用マスタDTO準備
			ApplicationReferenceDtoInterface dto = getApplicationReferenceDto();
			// 検索結果をリストに詰める
			dto.setPersonalId(humanDto.getPersonalId());
			dto.setEmployeeCode(humanDto.getEmployeeCode());
			dto.setFirstName(humanDto.getFirstName());
			dto.setLastName(humanDto.getLastName());
			dto.setEmployeeName(MospUtility.getHumansName(humanDto.getFirstName(), humanDto.getLastName()));
			if (applicationDto != null) {
				dto.setActivateDate(applicationDto.getActivateDate());
				dto.setApplicationCode(applicationDto.getApplicationCode());
				dto.setApplicationName(applicationDto.getApplicationName());
				dto.setScheduleCode(applicationDto.getScheduleCode());
				dto.setScheduleAbbr(getCodeName(applicationDto.getScheduleCode(), arySchedule));
				dto.setPaidHolidayCode(applicationDto.getPaidHolidayCode());
				dto.setPaidHolidayAbbr(getCodeName(applicationDto.getPaidHolidayCode(), aryPaidHoliday));
				dto.setTimeSettingCode(applicationDto.getWorkSettingCode());
				dto.setTimeSettingAbbr(getCodeName(applicationDto.getWorkSettingCode(), aryTimeSet));
				if (timeSettingDto != null) {
					dto.setCutoffAbbr(getCodeName(timeSettingDto.getCutoffCode(), aryCutoff));
					dto.setCutoffCode(timeSettingDto.getCutoffCode());
				}
			}
			list.add(dto);
		}
		// リストを返す
		return list;
	}
	
	/**
	 * 初期化した設定適用参照情報を取得する。
	 * @return 初期化した設定適用参照情報
	 */
	public ApplicationReferenceDto getApplicationReferenceDto() {
		ApplicationReferenceDto dto = new ApplicationReferenceDto();
		dto.setActivateDate(null);
		dto.setEmployeeCode("");
		dto.setEmployeeName("");
		dto.setApplicationName("");
		dto.setApplicationCode("");
		dto.setApplicationAbbr("");
		dto.setCutoff("");
		dto.setCutoffAbbr("");
		dto.setCutoffCode("");
		dto.setCutoffName("");
		dto.setTimeSetting("");
		dto.setTimeSettingCode("");
		dto.setTimeSettingName("");
		dto.setTimeSettingAbbr("");
		dto.setSectionCode("");
		dto.setScheduleName("");
		dto.setScheduleCode("");
		dto.setScheduleAbbr("");
		dto.setSchedule("");
		dto.setPositionCode("");
		dto.setPersonalId("");
		dto.setPaidHolidayName("");
		dto.setPaidHolidayCode("");
		dto.setEmploymentContractCode("");
		dto.setFirstName("");
		dto.setLastName("");
		dto.setWorkPlaceCode("");
		return dto;
	}
	
	/**
	 * 設定適用が検索条件に合致しているか確認。
	 * @param applicationDto 人事情報検索結果
	 * @return true:検索条件合致、false:検索条件合致していない
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected boolean isApplicaationMatch(ApplicationDtoInterface applicationDto) throws MospException {
		// 設定適用コード検索
		if (applicationCode.isEmpty() == false) {
			if (applicationDto == null) {
				return false;
			}
			if (isForwardMatch(applicationCode, applicationDto.getApplicationCode()) == false) {
				return false;
			}
		}
		// 設定適用名検索
		if (applicationName.isEmpty() == false) {
			if (applicationDto == null) {
				return false;
			}
			if (isBroadMatch(applicationName, applicationDto.getApplicationName()) == false) {
				// 設定適用略称検索
				if (isBroadMatch(applicationName, applicationDto.getApplicationAbbr()) == false) {
					return false;
				}
			}
			
		}
		// 勤怠設定コード検索
		if (timeSettingCode.isEmpty() == false) {
			if (applicationDto == null) {
				return false;
			}
			if (isExactMatch(timeSettingCode, applicationDto.getWorkSettingCode()) == false) {
				return false;
			}
		}
		// 有休コード検索
		if (paidHolidayCode.isEmpty() == false) {
			if (applicationDto == null) {
				return false;
			}
			if (isExactMatch(paidHolidayCode, applicationDto.getPaidHolidayCode()) == false) {
				return false;
			}
		}
		// カレンダコード検索
		if (scheduleCode.isEmpty() == false) {
			if (applicationDto == null) {
				return false;
			}
			if (isExactMatch(scheduleCode, applicationDto.getScheduleCode()) == false) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 締日コードが検索条件に合致しているか確認。
	 * @param timeSettingDto 勤怠設定情報
	 * @return true:検索条件合致、false:検索条件合致していない
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected boolean isCutoffMatch(TimeSettingDtoInterface timeSettingDto) throws MospException {
		if (cutoffCode.isEmpty() == false) {
			if (timeSettingDto == null) {
				return false;
			}
			if (isExactMatch(cutoffCode, timeSettingDto.getCutoffCode()) == false) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public void setActivateDate(Date activateDate) {
		this.activateDate = getDateClone(activateDate);
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
	public void setScheduleCode(String scheduleCode) {
		this.scheduleCode = scheduleCode;
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
	public void setApplicationCode(String applicationCode) {
		this.applicationCode = applicationCode;
	}
	
	@Override
	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}
	
	@Override
	public void setTimeSettingCode(String timeSettingCode) {
		this.timeSettingCode = timeSettingCode;
	}
	
	@Override
	public void setCutoffCode(String cutoffCode) {
		this.cutoffCode = cutoffCode;
	}
	
	@Override
	public void setPaidHolidayCode(String paidHolidayCode) {
		this.paidHolidayCode = paidHolidayCode;
	}
	
}
