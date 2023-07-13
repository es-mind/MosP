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

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.dao.human.HumanDaoInterface;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.utils.MonthUtility;
import jp.mosp.time.base.TimeBean;
import jp.mosp.time.bean.ApplicationReferenceBeanInterface;
import jp.mosp.time.bean.CutoffReferenceBeanInterface;
import jp.mosp.time.bean.PaidHolidayReferenceBeanInterface;
import jp.mosp.time.bean.TimeSettingReferenceBeanInterface;
import jp.mosp.time.dao.settings.ApplicationDaoInterface;
import jp.mosp.time.dto.settings.ApplicationDtoInterface;
import jp.mosp.time.dto.settings.TimeSettingDtoInterface;
import jp.mosp.time.entity.ApplicationEntity;
import jp.mosp.time.entity.CutoffEntityInterface;
import jp.mosp.time.utils.TimeMessageUtility;
import jp.mosp.time.utils.TimeUtility;

/**
 * 設定適用管理参照クラス。
 */
public class ApplicationReferenceBean extends TimeBean implements ApplicationReferenceBeanInterface {
	
	/**
	 * 設定適用マスタDAOクラス。<br>
	 */
	protected ApplicationDaoInterface	dao;
	
	/**
	 * 人事マスタDAOクラス。<br>
	 */
	protected HumanDaoInterface			humanDao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public ApplicationReferenceBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// 設定適用マスタDAOクラス準備
		dao = createDaoInstance(ApplicationDaoInterface.class);
		// 人事マスタDAOクラス準備
		humanDao = createDaoInstance(HumanDaoInterface.class);
	}
	
	@Override
	public List<ApplicationDtoInterface> getApplicationHistory(String applicationCode) throws MospException {
		return dao.findForHistory(applicationCode);
	}
	
	@Override
	public ApplicationDtoInterface findForKey(String applicationCode, Date targetDate) throws MospException {
		ApplicationDtoInterface dto = dao.findForKey(applicationCode, targetDate);
		if (dto != null) {
			return dto;
		}
		return null;
	}
	
	@Override
	public ApplicationDtoInterface findFormerInfo(String applicationCode, Date activateDate) throws MospException {
		return dao.findFormerInfo(applicationCode, activateDate);
	}
	
	/**
	 * 対象日時点における最新の有効な情報から、以下の方法で順番に
	 * 適用されている情報を探していき、最初に見つかった設定適用情報を返す。<br>
	 *  1.個人ID<br>
	 *  2.職位、所属、雇用契約、勤務地<br>
	 *  3.職位、所属、雇用契約<br>
	 *  4.職位、所属<br>
	 *  5.職位<br>
	 *  6.所属、雇用契約、勤務地<br>
	 *  7.所属、雇用契約<br>
	 *  8.所属<br>
	 *  9.雇用契約、勤務地<br>
	 * 10.雇用契約<br>
	 * 11.勤務地<br>
	 * 12.指定無し<br>
	 */
	@Override
	public ApplicationDtoInterface findForPerson(String personalId, Date targetDate) throws MospException {
		// DTO準備
		ApplicationDtoInterface dto = null;
		// 1.個人ID
		dto = dao.findForPersonalId(targetDate, personalId);
		if (dto != null) {
			return dto;
		}
		// 人事情報取得
		HumanDtoInterface humanDto = humanDao.findForInfo(personalId, targetDate);
		if (humanDto == null) {
			return dto;
		}
		String positionCode = humanDto.getPositionCode();
		String sectionCode = humanDto.getSectionCode();
		String employmentContractCode = humanDto.getEmploymentContractCode();
		String workPlaceCode = humanDto.getWorkPlaceCode();
		String blank = "";
		// 2.職位、所属、雇用契約、勤務地
		dto = dao.findForMaster(targetDate, workPlaceCode, employmentContractCode, sectionCode, positionCode);
		if (dto != null) {
			return dto;
		}
		// 3.職位、所属、雇用契約
		dto = dao.findForMaster(targetDate, blank, employmentContractCode, sectionCode, positionCode);
		if (dto != null) {
			return dto;
		}
		// 4.職位、所属
		dto = dao.findForMaster(targetDate, blank, blank, sectionCode, positionCode);
		if (dto != null) {
			return dto;
		}
		// 5.職位
		dto = dao.findForMaster(targetDate, blank, blank, blank, positionCode);
		if (dto != null) {
			return dto;
		}
		// 6.所属、雇用契約、勤務地
		dto = dao.findForMaster(targetDate, workPlaceCode, employmentContractCode, sectionCode, blank);
		if (dto != null) {
			return dto;
		}
		// 7.所属、雇用契約
		dto = dao.findForMaster(targetDate, blank, employmentContractCode, sectionCode, blank);
		if (dto != null) {
			return dto;
		}
		// 8.所属
		dto = dao.findForMaster(targetDate, blank, blank, sectionCode, blank);
		if (dto != null) {
			return dto;
		}
		// 9.雇用契約、勤務地
		dto = dao.findForMaster(targetDate, workPlaceCode, employmentContractCode, blank, blank);
		if (dto != null) {
			return dto;
		}
		// 10.雇用契約
		dto = dao.findForMaster(targetDate, blank, employmentContractCode, blank, blank);
		if (dto != null) {
			return dto;
		}
		// 11.勤務地
		dto = dao.findForMaster(targetDate, workPlaceCode, blank, blank, blank);
		if (dto != null) {
			return dto;
		}
		// 12.指定無し
		dto = dao.findForMaster(targetDate, blank, blank, blank, blank);
		if (dto != null) {
			return dto;
		}
		// 該当無し
		return dto;
	}
	
	@Override
	public void chkExistApplication(ApplicationDtoInterface dto, Date targetDate) {
		if (dto == null) {
			TimeMessageUtility.addErrorApplicationDefect(mospParams, targetDate);
		}
	}
	
	@Override
	public boolean hasPersonalApplication(String personalId, Date startDate, Date endDate) throws MospException {
		// 個人IDが設定されている、有効日の範囲内で情報を取得
		List<ApplicationDtoInterface> list = dao.findPersonTerm(personalId, startDate, endDate);
		// リスト確認
		if (list.isEmpty()) {
			return false;
		}
		// 期間内全て適用されていたら
		return true;
	}
	
	@Override
	public ApplicationEntity getApplicationEntity(String personalId, Date targetDate) throws MospException {
		// 設定適用情報を取得
		ApplicationDtoInterface applicationDto = findForPerson(personalId, targetDate);
		// 設定適用エンティティを準備
		ApplicationEntity applicationEntity = new ApplicationEntity(applicationDto);
		// 設定適用情報が取得できない場合
		if (MospUtility.isEmpty(applicationDto)) {
			// 空の締日エンティティを取得し設定
			applicationEntity.setCutoffEntity(TimeUtility.getBareCutoffEntity(mospParams));
			// 空の設定適用エンティティを取得
			return applicationEntity;
		}
		// 各種参照クラス取得
		TimeSettingReferenceBeanInterface timeSettingRefer = (TimeSettingReferenceBeanInterface)createBean(
				TimeSettingReferenceBeanInterface.class);
		CutoffReferenceBeanInterface cutoffRefer = (CutoffReferenceBeanInterface)createBean(
				CutoffReferenceBeanInterface.class);
		PaidHolidayReferenceBeanInterface paidHolidayRefer = (PaidHolidayReferenceBeanInterface)createBean(
				PaidHolidayReferenceBeanInterface.class);
		// 勤怠設定コード取得
		String workSettingCode = applicationEntity.getWorkSettingCode();
		// 勤怠設定情報取得
		TimeSettingDtoInterface timeSettingDto = timeSettingRefer.getTimeSettingInfo(workSettingCode, targetDate);
		// 設定適用エンティティに勤怠設定情報を設定
		applicationEntity.setTimeSettingDto(timeSettingDto);
		// 締日コード取得
		String cutoffCode = applicationEntity.getCutoffCode();
		// 締日エンティティ取得
		CutoffEntityInterface cutoffEntity = cutoffRefer.getCutoffEntity(cutoffCode, targetDate);
		// 設定適用エンティティに締日エンティティを設定
		applicationEntity.setCutoffEntity(cutoffEntity);
		// 有給休暇設定コードを取得
		String paidHolidayCode = applicationEntity.getPaidHolidayCode();
		// 設定適用エンティティに有給休暇設定情報を設定
		applicationEntity.setPaidHolidayDto(paidHolidayRefer.getPaidHolidayInfo(paidHolidayCode, targetDate));
		// 設定適用エンティティを取得
		return applicationEntity;
	}
	
	@Override
	public ApplicationEntity getApplicationEntity(String personalId, int targetYear, int targetMonth)
			throws MospException {
		// 年月指定時の基準日を取得
		Date targetDate = MonthUtility.getYearMonthTargetDate(targetYear, targetMonth, mospParams);
		// 設定適用エンティティを取得
		return getApplicationEntity(personalId, targetDate);
	}
	
}
