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
import java.util.Set;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.human.HumanSearchBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.time.bean.HolidayInfoReferenceBeanInterface;
import jp.mosp.time.bean.HolidayManagementSearchBeanInterface;
import jp.mosp.time.dto.settings.HolidayManagementListDtoInterface;
import jp.mosp.time.dto.settings.impl.HolidayManagementListDto;
import jp.mosp.time.dto.settings.impl.HolidayRemainDto;

/**
 * 休暇確認一覧検索処理。<br>
 */
public class HolidayManagementSearchBean extends PlatformBean implements HolidayManagementSearchBeanInterface {
	
	/**
	 * 人事情報検索処理。<br>
	 */
	protected HumanSearchBeanInterface			humanSearch;
	
	/**
	 * 休暇数参照処理。<br>
	 */
	protected HolidayInfoReferenceBeanInterface	refer;
	
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
	 * 所属コード。
	 */
	private String								sectionCode;
	
	/**
	 * 職位コード。
	 */
	private String								positionCode;
	
	
	/**
	 * コンストラクタ。
	 */
	public HolidayManagementSearchBean() {
		// 処理無し
	}
	
	@Override
	public void initBean() throws MospException {
		// Beanを準備
		humanSearch = createBeanInstance(HumanSearchBeanInterface.class);
		refer = createBeanInstance(HolidayInfoReferenceBeanInterface.class);
	}
	
	@Override
	public List<HolidayManagementListDtoInterface> getSearchList(int holidayType) throws MospException {
		// 休暇確認一覧情報リスト準備
		List<HolidayManagementListDtoInterface> list = new ArrayList<HolidayManagementListDtoInterface>();
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
		// 人事情報毎に処理
		for (HumanDtoInterface humanDto : humanSearch.search()) {
			// 休暇確認一覧情報リスト(個人別)を作成し設定
			list.addAll(makeDtos(humanDto, activateDate, holidayType));
		}
		// 休暇確認一覧情報リスト準備
		return list;
	}
	
	/**
	 * 休暇確認一覧情報リスト(個人別)を作成する。<br>
	 * @param humanDto    人事情報
	 * @param targetDate  対象日
	 * @param holidayType 休暇区分(休暇種別1：特別休暇orその他休暇)
	 * @return 休暇確認一覧情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected List<HolidayManagementListDtoInterface> makeDtos(HumanDtoInterface humanDto, Date targetDate,
			int holidayType) throws MospException {
		// 休暇確認一覧情報リスト(個人別)を準備
		List<HolidayManagementListDtoInterface> list = new ArrayList<HolidayManagementListDtoInterface>();
		// 個人IDを取得
		String personalId = humanDto.getPersonalId();
		// 休暇の残日数及び残時間数群を取得
		Set<HolidayRemainDto> holidayRemains = refer.getHolidayRemains(personalId, targetDate, holidayType);
		// 休暇の残日数及び残時間数毎に処理
		for (HolidayRemainDto remain : holidayRemains) {
			// 休暇確認一覧情報を作成
			HolidayManagementListDtoInterface dto = new HolidayManagementListDto();
			dto.setEmployeeCode(humanDto.getEmployeeCode());
			dto.setActivateDate(remain.getAcquisitionDate());
			dto.setLastName(humanDto.getLastName());
			dto.setFirstName(humanDto.getFirstName());
			dto.setSectionCode(humanDto.getSectionCode());
			dto.setHolidayCode(remain.getHolidayCode());
			dto.setHolidayRemainder(remain.getRemainDays());
			dto.setHolidayRemaindHours(remain.getRemainHours());
			dto.setHolidayRemaindMinutes(remain.getRemainMinutes());
			dto.setHolidayLimit(remain.getHolidayLimitDate());
			// 休暇確認一覧情報リスト(個人別)に追加
			list.add(dto);
		}
		// 休暇確認一覧情報リスト(個人別)を取得
		return list;
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
