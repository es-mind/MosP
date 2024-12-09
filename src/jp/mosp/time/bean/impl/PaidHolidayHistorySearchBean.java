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
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.platform.bean.human.HumanSearchBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.time.base.TimeApplicationBean;
import jp.mosp.time.bean.PaidHolidayHistorySearchBeanInterface;
import jp.mosp.time.dao.settings.PaidHolidayTransactionDaoInterface;
import jp.mosp.time.dao.settings.StockHolidayTransactionDaoInterface;
import jp.mosp.time.dto.settings.PaidHolidayHistoryListDtoInterface;
import jp.mosp.time.dto.settings.PaidHolidayTransactionDtoInterface;
import jp.mosp.time.dto.settings.StockHolidayTransactionDtoInterface;
import jp.mosp.time.dto.settings.impl.PaidHolidayHistoryListDto;

/**
 * 有給休暇手動付与検索クラス。
 */
public class PaidHolidayHistorySearchBean extends TimeApplicationBean implements PaidHolidayHistorySearchBeanInterface {
	
	/**
	 * 人事情報検索クラス。
	 */
	private HumanSearchBeanInterface			humanSearch;
	
	/**
	 * 有給休暇トランザクションDAO。
	 */
	private PaidHolidayTransactionDaoInterface	paidHolidayTransactionDao;
	
	/**
	 * ストック休暇トランザクションDAO。
	 */
	private StockHolidayTransactionDaoInterface	stockHolidayTransactionDao;
	
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
	 * 無効フラグ。
	 */
	private String								inactivateFlag;
	
	
	/**
	 * コンストラクタ。
	 */
	public PaidHolidayHistorySearchBean() {
		// 処理無し
	}
	
	@Override
	public void initBean() throws MospException {
		// 継承元のBean初期化処理を実施
		super.initBean();
		// 人事情報検索クラス取得
		humanSearch = createBeanInstance(HumanSearchBeanInterface.class);
		// 有給休暇トランザクションDAO取得
		paidHolidayTransactionDao = createDaoInstance(PaidHolidayTransactionDaoInterface.class);
		// ストック休暇トランザクションDAO取得
		stockHolidayTransactionDao = createDaoInstance(StockHolidayTransactionDaoInterface.class);
	}
	
	@Override
	public List<PaidHolidayHistoryListDtoInterface> getSearchList() throws MospException {
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
		// 有給休暇手動付与リスト準備
		List<PaidHolidayHistoryListDtoInterface> paidHolidayHistoryList = new ArrayList<PaidHolidayHistoryListDtoInterface>();
		// 検索結果から有給休暇手動付与リストを作成
		for (HumanDtoInterface dto : list) {
			String personalId = dto.getPersonalId();
			// 初期化
			PaidHolidayHistoryListDtoInterface paidHolidayHistoryListDto = new PaidHolidayHistoryListDto();
			paidHolidayHistoryListDto.setEmployeeCode(dto.getEmployeeCode());
			paidHolidayHistoryListDto.setPersonalId(dto.getPersonalId());
			paidHolidayHistoryListDto.setLastName(dto.getLastName());
			paidHolidayHistoryListDto.setFirstName(dto.getFirstName());
			paidHolidayHistoryListDto.setSectionCode(dto.getSectionCode());
			paidHolidayHistoryListDto.setFormerDate(0);
			paidHolidayHistoryListDto.setFormerTime(0);
			paidHolidayHistoryListDto.setDate(0);
			paidHolidayHistoryListDto.setTime(0);
			Date previousAcquisitionDate = null;
			Date currentAcquisitionDate = null;
			Date nextAcquisitionDate = null;
			if (hasPaidHolidaySettings(personalId, activateDate)) {
				currentAcquisitionDate = DateUtility.getDate(DateUtility.getYear(activateDate),
						paidHolidayDto.getPointDateMonth(), paidHolidayDto.getPointDateDay());
				nextAcquisitionDate = DateUtility.addYear(currentAcquisitionDate, 1);
				previousAcquisitionDate = DateUtility.addYear(currentAcquisitionDate, -1);
				if (activateDate.before(currentAcquisitionDate)) {
					nextAcquisitionDate = currentAcquisitionDate;
					currentAcquisitionDate = previousAcquisitionDate;
					previousAcquisitionDate = DateUtility.addYear(previousAcquisitionDate, -1);
				}
			}
			if (currentAcquisitionDate == null || nextAcquisitionDate == null || previousAcquisitionDate == null) {
				continue;
			}
			// 有給休暇トランザクション取得
			List<PaidHolidayTransactionDtoInterface> paidHolidayTransactionDtoList = paidHolidayTransactionDao
				.findForInfoList(personalId, activateDate, inactivateFlag);
			// 有給休暇トランザクション毎に処理
			for (PaidHolidayTransactionDtoInterface paidHolidayTransactionDto : paidHolidayTransactionDtoList) {
				// 有休取得日を取得
				Date acquisitionDate = paidHolidayTransactionDto.getAcquisitionDate();
				// DTOに設定
				paidHolidayHistoryListDto.setActivateDate(paidHolidayTransactionDto.getActivateDate());
				paidHolidayHistoryListDto.setInactivateFlag(paidHolidayTransactionDto.getInactivateFlag());
				if (!acquisitionDate.before(currentAcquisitionDate) && acquisitionDate.before(nextAcquisitionDate)) {
					// 今年度
					paidHolidayHistoryListDto
						.setDate(paidHolidayTransactionDto.getGivingDay() - paidHolidayTransactionDto.getCancelDay());
					paidHolidayHistoryListDto
						.setTime(paidHolidayTransactionDto.getGivingHour() - paidHolidayTransactionDto.getCancelHour());
				} else if (acquisitionDate.before(currentAcquisitionDate)
						&& !acquisitionDate.before(previousAcquisitionDate)) {
					// 前年度
					paidHolidayHistoryListDto.setFormerDate(
							paidHolidayTransactionDto.getGivingDay() - paidHolidayTransactionDto.getCancelDay());
					paidHolidayHistoryListDto.setFormerTime(
							paidHolidayTransactionDto.getGivingHour() - paidHolidayTransactionDto.getCancelHour());
				}
			}
			double givingDay = 0;
			double cancelDay = 0;
			StockHolidayTransactionDtoInterface stockHolidayTransactionDto = stockHolidayTransactionDao
				.findForKey(personalId, activateDate, inactivateFlag);
			if (null != stockHolidayTransactionDto) {
				givingDay += stockHolidayTransactionDto.getGivingDay();
				cancelDay += stockHolidayTransactionDto.getCancelDay();
				paidHolidayHistoryListDto.setStockDate(givingDay - cancelDay);
				if (paidHolidayTransactionDtoList.size() > 0) {
					paidHolidayHistoryList.add(paidHolidayHistoryListDto);
				}
			}
		}
		return paidHolidayHistoryList;
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
