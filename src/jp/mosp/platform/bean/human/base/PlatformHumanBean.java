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
package jp.mosp.platform.bean.human.base;

import java.util.Date;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.bean.file.PlatformFileBean;
import jp.mosp.platform.bean.system.PlatformMasterCheckBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dao.human.EntranceDaoInterface;
import jp.mosp.platform.dao.human.RetirementDaoInterface;
import jp.mosp.platform.dao.system.EmploymentContractDaoInterface;
import jp.mosp.platform.dao.system.NamingDaoInterface;
import jp.mosp.platform.dao.system.PositionDaoInterface;
import jp.mosp.platform.dao.system.SectionDaoInterface;
import jp.mosp.platform.dao.system.WorkPlaceDaoInterface;
import jp.mosp.platform.dto.human.EntranceDtoInterface;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.dto.human.RetirementDtoInterface;
import jp.mosp.platform.dto.system.EmploymentContractDtoInterface;
import jp.mosp.platform.dto.system.NamingDtoInterface;
import jp.mosp.platform.dto.system.PositionDtoInterface;
import jp.mosp.platform.dto.system.SectionDtoInterface;
import jp.mosp.platform.dto.system.WorkPlaceDtoInterface;
import jp.mosp.platform.human.utils.HumanUtility;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.platform.utils.PfNameUtility;

/**
 * MosPプラットフォーム人事管理におけるBeanの基本機能を提供する。<br>
 */
public abstract class PlatformHumanBean extends PlatformFileBean {
	
	/**
	 * 所属・雇用契約・職位・勤務地・名称区分マスタに関連する整合性確認インターフェース。
	 */
	protected PlatformMasterCheckBeanInterface masterCheck;
	
	
	/**
	 * {@link PlatformHumanBean}を生成する。<br>
	 */
	public PlatformHumanBean() {
		// 処理無し
	}
	
	@Override
	public void initBean() throws MospException {
		// Beanを準備
		masterCheck = createBeanInstance(PlatformMasterCheckBeanInterface.class);
	}
	
	/**
	 * 社員名を取得する。<br>
	 * @param dto 人事情報
	 * @return 社員名
	 */
	protected String getHumanName(HumanDtoInterface dto) {
		// 人事情報が存在しない場合
		if (MospUtility.isEmpty(dto)) {
			// 空文字を取得
			return MospConst.STR_EMPTY;
		}
		// 社員名を取得
		return MospUtility.getHumansName(dto.getFirstName(), dto.getLastName());
	}
	
	/**
	 * 入社日を取得する。<br>
	 * 入社情報が存在しない場合はnullを返す。<br>
	 * @param personalId 個人ID
	 * @return 入社日
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected Date getEntranceDate(String personalId) throws MospException {
		// 人事入社情報DAO準備
		EntranceDaoInterface entranceDao = createDaoInstance(EntranceDaoInterface.class);
		EntranceDtoInterface dto = entranceDao.findForInfo(personalId);
		if (dto != null) {
			return dto.getEntranceDate();
		}
		return null;
	}
	
	/**
	 * 退職日を取得する。<br>
	 * 退職情報が存在しない場合はnullを返す。<br>
	 * @param personalId 個人ID
	 * @return 退職日
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected Date getRetirementDate(String personalId) throws MospException {
		// 人事退職情報DAO準備
		RetirementDaoInterface retirementDao = createDaoInstance(RetirementDaoInterface.class);
		RetirementDtoInterface dto = retirementDao.findForInfo(personalId);
		return HumanUtility.getRetireDate(dto);
	}
	
	/**
	 * 勤務地存在確認を行う。<br>
	* @param workPlaceCode 勤務地コード
	 * @param startDate 期間初日
	 * @param endDate 期間終了日
	 * @param row 行インデックス
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkWorkPlace(String workPlaceCode, Date startDate, Date endDate, Integer row)
			throws MospException {
		// 勤務地確認
		if (MospUtility.isEmpty(workPlaceCode)) {
			return;
		}
		// 勤務地マスタDAO取得
		WorkPlaceDaoInterface workPlaceDao = createDaoInstance(WorkPlaceDaoInterface.class);
		// 勤務地情報取得
		WorkPlaceDtoInterface dto = workPlaceDao.findForInfo(workPlaceCode, startDate);
		// 勤務地情報確認
		if (MospUtility.isEmpty(dto)) {
			// 勤務地が存在しない場合のメッセージを追加
			String fieldName = PfNameUtility.workPlace(mospParams);
			PfMessageUtility.addErrorNotExist(mospParams, fieldName, workPlaceCode, row);
			// 処理終了
			return;
		}
		// 登録する期間無効にならないか確認
		masterCheck.isCheckWorkPlace(workPlaceCode, startDate, endDate, row);
	}
	
	/**
	 * 雇用契約存在確認を行う。<br>
	 * @param employmentContractCode 雇用契約コード
	 * @param startDate 期間初日
	 * @param endDate 期間終了日
	 * @param row 行インデックス
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkEmploymentContract(String employmentContractCode, Date startDate, Date endDate, Integer row)
			throws MospException {
		// 雇用契約確認
		if (MospUtility.isEmpty(employmentContractCode)) {
			return;
		}
		// 雇用契約マスタDAO取得
		EmploymentContractDaoInterface employmentContractDao;
		employmentContractDao = createDaoInstance(EmploymentContractDaoInterface.class);
		// 雇用契約情報取得
		EmploymentContractDtoInterface dto = employmentContractDao.findForInfo(employmentContractCode, startDate);
		// 雇用契約情報確認
		if (MospUtility.isEmpty(dto)) {
			// 雇用契約が存在しない場合のメッセージを追加
			String fieldName = PfNameUtility.employmentContract(mospParams);
			PfMessageUtility.addErrorNotExist(mospParams, fieldName, employmentContractCode, row);
			// 処理終了
			return;
		}
		// 登録する期間無効にならないか確認
		masterCheck.isCheckEmploymentContract(employmentContractCode, startDate, endDate, row);
	}
	
	/**
	 * 所属存在確認を行う。<br>
	 * @param sectionCode 所属コード
	 * @param startDate  期間開始日
	 * @param endDate 期間終了日
	 * @param row         行インデックス
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkSection(String sectionCode, Date startDate, Date endDate, Integer row) throws MospException {
		// 所属確認
		if (MospUtility.isEmpty(sectionCode)) {
			return;
		}
		// 所属マスタDAO取得
		SectionDaoInterface sectionDao = createDaoInstance(SectionDaoInterface.class);
		// 所属情報取得
		SectionDtoInterface dto = sectionDao.findForInfo(sectionCode, startDate);
		// 所属情報確認
		if (MospUtility.isEmpty(dto)) {
			// 所属が存在しない場合のメッセージを追加
			String fieldName = PfNameUtility.section(mospParams);
			PfMessageUtility.addErrorNotExist(mospParams, fieldName, sectionCode, row);
			// 処理終了
			return;
		}
		// 登録する期間内に無効がないか確認
		masterCheck.isCheckSection(sectionCode, startDate, endDate, row);
	}
	
	/**
	 * 職位存在確認を行う。<br>
	 * @param positionCode 職位コード
	 * @param startDate 期間開始日
	 * @param endDate 期間終了日
	 * @param row          行インデックス
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkPosition(String positionCode, Date startDate, Date endDate, Integer row) throws MospException {
		// 職位確認
		if (MospUtility.isEmpty(positionCode)) {
			return;
		}
		// 職位マスタDAO取得
		PositionDaoInterface positionDao = createDaoInstance(PositionDaoInterface.class);
		// 職位情報取得
		PositionDtoInterface dto = positionDao.findForInfo(positionCode, startDate);
		// 職位情報確認
		if (MospUtility.isEmpty(dto)) {
			// 職位が存在しない場合のメッセージを追加
			String fieldName = PfNameUtility.position(mospParams);
			PfMessageUtility.addErrorNotExist(mospParams, fieldName, positionCode, row);
			// 処理終了
			return;
		}
		// 登録する期間内に無効がないか確認
		masterCheck.isCheckPosition(positionCode, startDate, endDate, row);
	}
	
	/**
	 * 名称区分存在確認を行う。<br>
	 * @param namingType 名称区分
	 * @param namingItemCode 名称項目コード
	 * @param startDate 期間開始日
	 * @param endDate 期間終了日
	 * @param row          行インデックス
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkNaming(String namingType, String namingItemCode, Date startDate, Date endDate, Integer row)
			throws MospException {
		// 名称項目コード確認
		if (MospUtility.isEmpty(namingItemCode)) {
			return;
		}
		// 名称区分マスタDAO取得
		NamingDaoInterface namingDao = createDaoInstance(NamingDaoInterface.class);
		// 名称区分情報取得
		NamingDtoInterface dto = namingDao.findForInfo(namingType, namingItemCode, startDate);
		// 名称区分情報確認
		if (MospUtility.isEmpty(dto)) {
			// 名称区分が存在しない場合のメッセージを追加
			String fieldName = MospUtility.getCodeName(mospParams, namingType, PlatformConst.CODE_KEY_NAMING_TYPE);
			PfMessageUtility.addErrorNotExist(mospParams, fieldName, namingItemCode, row);
			// 処理終了
			return;
		}
		// 登録する期間内に無効がないか確認
		masterCheck.isCheckNaming(namingType, namingItemCode, startDate, endDate, row);
	}
	
}
