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
package jp.mosp.time.bean.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.human.HumanSearchBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.time.bean.HolidayHistorySearchBeanInterface;
import jp.mosp.time.dao.settings.HolidayDataDaoInterface;
import jp.mosp.time.dto.settings.HolidayDataDtoInterface;
import jp.mosp.time.dto.settings.HolidayHistoryListDtoInterface;
import jp.mosp.time.dto.settings.impl.HolidayHistoryListDto;

/**
 * 休暇付与検索クラス。
 */
public class HolidayHistorySearchBean extends PlatformBean implements HolidayHistorySearchBeanInterface {
	
	/**
	 * 人事情報検索クラス。
	 */
	protected HumanSearchBeanInterface	humanSearch;
	
	/**
	 * 休暇データDAO。
	 */
	protected HolidayDataDaoInterface	holidayDataDao;
	
	/**
	 * 有効日。
	 */
	private Date						activateDate;
	
	/**
	 * 社員コード。
	 */
	private String						employeeCode;
	
	/**
	 * 社員名。
	 */
	private String						employeeName;
	
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
	 * 職位コード。
	 */
	private String						positionCode;
	
	/**
	 * 無効フラグ。
	 */
	private String						inactivateFlag;
	
	
	/**
	 * コンストラクタ。
	 */
	public HolidayHistorySearchBean() {
		// 処理無し
	}
	
	@Override
	public void initBean() throws MospException {
		// 人事情報検索クラス取得
		humanSearch = createBeanInstance(HumanSearchBeanInterface.class);
		// 休暇データDAO取得
		holidayDataDao = createDaoInstance(HolidayDataDaoInterface.class);
	}
	
	@Override
	public List<HolidayHistoryListDtoInterface> getSearchList(int holidayType) throws MospException {
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
		// 休暇手動付与リスト準備
		List<HolidayHistoryListDtoInterface> holidayHistoryList = new ArrayList<HolidayHistoryListDtoInterface>();
		// 検索結果から休暇手動付与リストを作成
		for (HumanDtoInterface dto : list) {
			List<HolidayDataDtoInterface> holidayDataDtoList = holidayDataDao.findForInfoList(dto.getPersonalId(),
					activateDate, inactivateFlag, holidayType);
			for (HolidayDataDtoInterface holidayDataDto : holidayDataDtoList) {
				// 初期化
				HolidayHistoryListDtoInterface holidayHistoryListDto = new HolidayHistoryListDto();
				holidayHistoryListDto.setEmployeeCode(dto.getEmployeeCode());
				holidayHistoryListDto.setLastName(dto.getLastName());
				holidayHistoryListDto.setFirstName(dto.getFirstName());
				holidayHistoryListDto.setSectionCode(dto.getSectionCode());
				holidayHistoryListDto.setActivateDate(holidayDataDto.getActivateDate());
				holidayHistoryListDto.setHolidayCode(holidayDataDto.getHolidayCode());
				holidayHistoryListDto.setHolidayGiving(holidayDataDto.getGivingDay());
				holidayHistoryListDto.setHolidayLimit(holidayDataDto.getHolidayLimitDate());
				holidayHistoryListDto.setInactivateFlag(holidayDataDto.getInactivateFlag());
				holidayHistoryList.add(holidayHistoryListDto);
			}
		}
		return holidayHistoryList;
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
	
	@Override
	public void setInactivateFlag(String inactivateFlag) {
		this.inactivateFlag = inactivateFlag;
	}
	
}
