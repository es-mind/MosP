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
import jp.mosp.platform.bean.human.HumanSearchBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.time.bean.PaidHolidayInfoReferenceBeanInterface;
import jp.mosp.time.bean.PaidHolidayManagementSearchBeanInterface;
import jp.mosp.time.bean.PaidHolidayRemainBeanInterface;
import jp.mosp.time.bean.TimeMasterBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dto.settings.PaidHolidayManagementListDtoInterface;
import jp.mosp.time.dto.settings.impl.PaidHolidayManagementListDto;

/**
 * 有給休暇確認検索クラス。
 */
public class PaidHolidayManagementSearchBean extends PlatformBean implements PaidHolidayManagementSearchBeanInterface {
	
	/**
	 * 人事情報検索クラス。
	 */
	protected HumanSearchBeanInterface				humanSearch;
	
	/**
	 * 有給休暇情報参照。
	 */
	protected PaidHolidayInfoReferenceBeanInterface	paidHolidayInfo;
	
	/**
	 * 有給休暇残数取得処理。<br>
	 */
	protected PaidHolidayRemainBeanInterface		paidHolidayRemain;
	
	/**
	 * 勤怠関連マスタ参照処理。<br>
	 */
	protected TimeMasterBeanInterface				timeMaster;
	
	private Date									activateDate;
	private String									employeeCode;
	private String									employeeName;
	private String									workPlaceCode;
	private String									employmentCode;
	private String									sectionCode;
	private String									positionCode;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public PaidHolidayManagementSearchBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// Beanを準備
		humanSearch = createBeanInstance(HumanSearchBeanInterface.class);
		paidHolidayInfo = createBeanInstance(PaidHolidayInfoReferenceBeanInterface.class);
		paidHolidayRemain = createBeanInstance(PaidHolidayRemainBeanInterface.class);
		// 勤怠関連マスタ参照処理
		timeMaster = createBeanInstance(TimeMasterBeanInterface.class);
		// 休暇残数取得処理に勤怠関連マスタ参照処理を設定
		paidHolidayRemain.setTimeMaster(timeMaster);
	}
	
	@Override
	public List<PaidHolidayManagementListDtoInterface> getSearchList() throws MospException {
		// 人事情報検索クラスに検索条件を設定
		humanSearch.setTargetDate(activateDate);
		humanSearch.setEmployeeCode(employeeCode);
		humanSearch.setEmployeeCodeType(PlatformConst.SEARCH_FORWARD_MATCH);
		humanSearch.setEmployeeName(employeeName);
		humanSearch.setWorkPlaceCode(workPlaceCode);
		humanSearch.setEmploymentContractCode(employmentCode);
		humanSearch.setPositionCode(positionCode);
		humanSearch.setSectionCode(sectionCode);
		humanSearch.setStateType(PlatformConst.EMPLOYEE_STATE_PRESENCE);
		// 人事情報検索
		List<HumanDtoInterface> list = humanSearch.search();
		// 有給休暇確認リスト準備
		List<PaidHolidayManagementListDtoInterface> paidHolidayManagementList = new ArrayList<PaidHolidayManagementListDtoInterface>();
		// 検索結果から有給休暇確認リストを作成
		for (HumanDtoInterface dto : list) {
			String personalId = dto.getPersonalId();
			// 初期化
			PaidHolidayManagementListDtoInterface paidHolidayManagementListDto = new PaidHolidayManagementListDto();
			paidHolidayManagementListDto.setPersonalId(personalId);
			paidHolidayManagementListDto.setEmployeeCode(dto.getEmployeeCode());
			paidHolidayManagementListDto.setLastName(dto.getLastName());
			paidHolidayManagementListDto.setFirstName(dto.getFirstName());
			paidHolidayManagementListDto.setSectionCode(dto.getSectionCode());
			paidHolidayManagementListDto.setFormerDate(0);
			paidHolidayManagementListDto.setFormerTime(0);
			paidHolidayManagementListDto.setDate(0);
			paidHolidayManagementListDto.setTime(0);
			// 有給休暇
			Map<String, Object> map = paidHolidayInfo.getPaidHolidayInfo(personalId, activateDate);
			paidHolidayManagementListDto.setFormerDate(((Double)map.get(TimeConst.CODE_FORMER_YEAR_DAY)).doubleValue());
			paidHolidayManagementListDto.setFormerTime(((Integer)map.get(TimeConst.CODE_FORMER_YEAR_TIME)).intValue());
			paidHolidayManagementListDto.setDate(((Double)map.get(TimeConst.CODE_CURRENT_YEAR_DAY)).doubleValue());
			paidHolidayManagementListDto.setTime(((Integer)map.get(TimeConst.CODE_CURRENT_TIME)).intValue());
			// ストック休暇
			double stock = paidHolidayRemain.getStockHolidayRemainDaysForList(personalId, activateDate);
			paidHolidayManagementListDto.setStockDate(stock);
			paidHolidayManagementList.add(paidHolidayManagementListDto);
		}
		return paidHolidayManagementList;
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
	public void setSectionCode(String sectionCode) {
		this.sectionCode = sectionCode;
	}
	
	@Override
	public void setPositionCode(String positionCode) {
		this.positionCode = positionCode;
	}
	
}
