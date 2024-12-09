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
package jp.mosp.platform.bean.system.impl;

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.system.EmploymentContractReferenceBeanInterface;
import jp.mosp.platform.bean.system.NamingReferenceBeanInterface;
import jp.mosp.platform.bean.system.PlatformMasterCheckBeanInterface;
import jp.mosp.platform.bean.system.PositionReferenceBeanInterface;
import jp.mosp.platform.bean.system.SectionReferenceBeanInterface;
import jp.mosp.platform.bean.system.WorkPlaceReferenceBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dto.system.EmploymentContractDtoInterface;
import jp.mosp.platform.dto.system.NamingDtoInterface;
import jp.mosp.platform.dto.system.PositionDtoInterface;
import jp.mosp.platform.dto.system.SectionDtoInterface;
import jp.mosp.platform.dto.system.WorkPlaceDtoInterface;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.platform.utils.PfNameUtility;

/**
 * 所属・雇用契約・職位・勤務地・名称区分マスタに関連する整合性確認をする。
 */
public class PlatformMasterCheckBean extends PlatformBean implements PlatformMasterCheckBeanInterface {
	
	/**
	 * 所属マスタ参照クラス。<br>
	 */
	protected SectionReferenceBeanInterface				sectionReference;
	
	/**
	 * 職位マスタ参照クラス。<br>
	 */
	protected PositionReferenceBeanInterface			positionReference;
	
	/**
	 * 雇用契約マスタ参照クラス。<br>
	 */
	protected EmploymentContractReferenceBeanInterface	employmentContractReference;
	
	/**
	 * 勤務地マスタ参照クラス。<br>
	 */
	protected WorkPlaceReferenceBeanInterface			workPlaceReference;
	
	/**
	 * 名称区分マスタ参照クラス。<br>
	 */
	protected NamingReferenceBeanInterface				namingReference;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public PlatformMasterCheckBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		sectionReference = createBeanInstance(SectionReferenceBeanInterface.class);
		positionReference = createBeanInstance(PositionReferenceBeanInterface.class);
		employmentContractReference = createBeanInstance(EmploymentContractReferenceBeanInterface.class);
		workPlaceReference = createBeanInstance(WorkPlaceReferenceBeanInterface.class);
		namingReference = createBeanInstance(NamingReferenceBeanInterface.class);
	}
	
	@Override
	public boolean isCheckEmploymentContract(String employmentContractCode, Date startDate, Date endDate, Integer row)
			throws MospException {
		// 期間内に無効がある場合
		if (isTermEmploymentContract(employmentContractCode, startDate, endDate) == false) {
			return false;
		}
		// 期間内全て有効
		return true;
	}
	
	@Override
	public boolean isCheckPosition(String positionCode, Date startDate, Date endDate, Integer row)
			throws MospException {
		// 期間内に無効がある場合
		if (isTermPosition(positionCode, startDate, endDate) == false) {
			return false;
		}
		// 期間内全て有効
		return true;
	}
	
	@Override
	public boolean isCheckSection(String sectionCode, Date startDate, Date endDate, Integer row) throws MospException {
		// 期間内に無効がある場合
		if (isTermSection(sectionCode, startDate, endDate) == false) {
			return false;
		}
		// 期間内全て有効
		return true;
	}
	
	@Override
	public boolean isCheckWorkPlace(String workPlaceCode, Date startDate, Date endDate, Integer row)
			throws MospException {
		// 期間内に無効がある場合
		if (isTermWorkPlace(workPlaceCode, startDate, endDate) == false) {
			return false;
		}
		// 期間内全て有効
		return true;
	}
	
	@Override
	public boolean isCheckNaming(String namingType, String namingItemCode, Date startDate, Date endDate, Integer row)
			throws MospException {
		// 期間内に無効がある場合
		if (isTermNaming(namingType, namingItemCode, startDate, endDate) == false) {
			return false;
		}
		// 期間内全て有効
		return true;
	}
	
	@Override
	public boolean isCheckEmploymentContract(String employmentContractCode, Date startDate, Date endDate)
			throws MospException {
		// 期間内に無効がある場合
		if (isTermEmploymentContract(employmentContractCode, startDate, endDate) == false) {
			return false;
		}
		// 期間内全て有効
		return true;
	}
	
	@Override
	public boolean isCheckPosition(String positionCode, Date startDate, Date endDate) throws MospException {
		// 期間内に無効がある場合
		if (isTermPosition(positionCode, startDate, endDate) == false) {
			return false;
		}
		// 期間内全て有効
		return true;
	}
	
	@Override
	public boolean isCheckSection(String sectionCode, Date startDate, Date endDate) throws MospException {
		// 期間内に無効がある場合
		if (isTermSection(sectionCode, startDate, endDate) == false) {
			return false;
		}
		// 期間内全て有効
		return true;
	}
	
	@Override
	public boolean isCheckWorkPlace(String workPlaceCode, Date startDate, Date endDate) throws MospException {
		// 期間内に無効がある場合
		if (isTermWorkPlace(workPlaceCode, startDate, endDate) == false) {
			return false;
		}
		// 期間内全て有効
		return true;
	}
	
	/**
	 * 雇用契約を取得し、期間内に無効フラグがあるかの確認をする。<br>
	 * @param employmentContractCode 雇用契約コード
	 * @param startDate 期間初日
	 * @param endDate 期間最終日
	 * @return 確認結果(true：期間内全て有効、false：期間内無効有り)
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	public boolean isTermEmploymentContract(String employmentContractCode, Date startDate, Date endDate)
			throws MospException {
		// 履歴一覧後に処理
		// 雇用契約：現在の有効日の履歴一覧取得
		List<EmploymentContractDtoInterface> employList = employmentContractReference
			.getContractHistory(employmentContractCode);
		// 履歴一覧後に処理
		for (EmploymentContractDtoInterface employDto : employList) {
			// 期間に対象日が含まれているか確認
			if (DateUtility.isTermContain(employDto.getActivateDate(), startDate, endDate) == false) {
				continue;
			}
			// 無効の履歴がある場合
			if (employDto.getInactivateFlag() == MospConst.INACTIVATE_FLAG_ON) {
				// エラーメッセージを設定
				String fieldName = PfNameUtility.employmentContract(mospParams);
				Date targetDate = employDto.getActivateDate();
				PfMessageUtility.addErrorInactive(mospParams, fieldName, employmentContractCode, targetDate);
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 職位を取得し、期間内に無効フラグがあるかの確認をする。<br>
	 * @param positionCode 職位コード
	 * @param startDate 期間初日
	 * @param endDate 期間最終日
	 * @return 確認結果(true：期間内全て有効、false：期間内無効有り)
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	public boolean isTermPosition(String positionCode, Date startDate, Date endDate) throws MospException {
		// 履歴一覧後に処理
		// 職位：現在の有効日の履歴一覧取得
		List<PositionDtoInterface> positionList = positionReference.getPositionHistory(positionCode);
		// 履歴一覧後に処理
		for (PositionDtoInterface positionDto : positionList) {
			// 期間に対象日が含まれているか確認
			if (DateUtility.isTermContain(positionDto.getActivateDate(), startDate, endDate) == false) {
				continue;
			}
			// 無効の履歴がある場合
			if (positionDto.getInactivateFlag() == MospConst.INACTIVATE_FLAG_ON) {
				// エラーメッセージを設定
				String fieldName = PfNameUtility.position(mospParams);
				Date targetDate = positionDto.getActivateDate();
				PfMessageUtility.addErrorInactive(mospParams, fieldName, positionCode, targetDate);
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 所属を取得し、期間内に無効フラグがあるかの確認をする。<br>
	 * @param sectionCode 所属コード
	 * @param startDate 期間初日
	 * @param endDate 期間最終日
	 * @return 確認結果(true：期間内全て有効、false：期間内無効有り)
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	public boolean isTermSection(String sectionCode, Date startDate, Date endDate) throws MospException {
		// 履歴一覧後に処理
		// 所属：現在の有効日の履歴一覧取得
		List<SectionDtoInterface> sectionList = sectionReference.getSectionHistory(sectionCode);
		// 履歴一覧後に処理
		for (SectionDtoInterface sectionDto : sectionList) {
			// 期間に対象日が含まれていない場合
			if (DateUtility.isTermContain(sectionDto.getActivateDate(), startDate, endDate) == false) {
				continue;
			}
			// 無効の履歴がある場合
			if (sectionDto.getInactivateFlag() == MospConst.INACTIVATE_FLAG_ON) {
				// エラーメッセージを設定
				String fieldName = PfNameUtility.section(mospParams);
				Date targetDate = sectionDto.getActivateDate();
				PfMessageUtility.addErrorInactive(mospParams, fieldName, sectionCode, targetDate);
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 勤務地を取得し、期間内に無効フラグがあるかの確認をする。<br>
	 * @param workPlaceCode 勤務地コード
	 * @param startDate 期間初日
	 * @param endDate 期間最終日
	 * @return 確認結果(true：期間内全て有効、false：期間内無効有り)
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	public boolean isTermWorkPlace(String workPlaceCode, Date startDate, Date endDate) throws MospException {
		// 履歴一覧後に処理
		// 勤務地：現在の有効日の履歴一覧取得
		List<WorkPlaceDtoInterface> workPlaceList = workPlaceReference.getHistory(workPlaceCode);
		// 履歴一覧後に処理
		for (WorkPlaceDtoInterface workPlaceDto : workPlaceList) {
			// 期間に対象日が含まれているか確認
			if (DateUtility.isTermContain(workPlaceDto.getActivateDate(), startDate, endDate) == false) {
				continue;
			}
			// 無効の履歴がある場合
			if (workPlaceDto.getInactivateFlag() == MospConst.INACTIVATE_FLAG_ON) {
				// エラーメッセージを設定
				String fieldName = PfNameUtility.workPlace(mospParams);
				Date targetDate = workPlaceDto.getActivateDate();
				PfMessageUtility.addErrorInactive(mospParams, fieldName, workPlaceCode, targetDate);
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 名称区分を取得し、期間内に無効フラグがあるかの確認をする。<br>
	 * @param namingType 名称区分
	 * @param namingItemCode 名称項目コード
	 * @param startDate 期間初日
	 * @param endDate 期間最終日
	 * @return 確認結果(true：期間内全て有効、false：期間内無効有り)
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected boolean isTermNaming(String namingType, String namingItemCode, Date startDate, Date endDate)
			throws MospException {
		// 履歴一覧後に処理
		// 名称区分：現在の有効日の履歴一覧取得
		List<NamingDtoInterface> namingList = namingReference.getNamingItemHistory(namingType, namingItemCode);
		// 履歴一覧後に処理
		for (NamingDtoInterface namingDto : namingList) {
			// 期間に対象日が含まれているか確認
			if (DateUtility.isTermContain(namingDto.getActivateDate(), startDate, endDate) == false) {
				continue;
			}
			// 無効の履歴がある場合
			if (namingDto.getInactivateFlag() == MospConst.INACTIVATE_FLAG_ON) {
				// エラーメッセージを設定
				String fieldName = MospUtility.getCodeName(mospParams, PlatformConst.CODE_KEY_NAMING_TYPE, namingType);
				Date targetDate = namingDto.getActivateDate();
				PfMessageUtility.addErrorInactive(mospParams, fieldName, namingItemCode, targetDate);
				return false;
			}
		}
		return true;
	}
	
}
