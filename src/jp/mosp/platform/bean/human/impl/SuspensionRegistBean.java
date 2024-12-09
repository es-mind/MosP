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
package jp.mosp.platform.bean.human.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.bean.human.SuspensionRegistBeanInterface;
import jp.mosp.platform.bean.human.base.PlatformHumanBean;
import jp.mosp.platform.dao.human.SuspensionDaoInterface;
import jp.mosp.platform.dto.human.SuspensionDtoInterface;
import jp.mosp.platform.dto.human.impl.PfaHumanSuspensionDto;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.platform.utils.PfNameUtility;

/**
 * 人事休職情報登録クラス。
 */
public class SuspensionRegistBean extends PlatformHumanBean implements SuspensionRegistBeanInterface {
	
	/**
	 * 人事休職情報DAOクラス。<br>
	 */
	protected SuspensionDaoInterface dao;
	
	
	/**
	 * {@link PlatformHumanBean#PlatformHumanBean()}を実行する。<br>
	 */
	public SuspensionRegistBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// 継承基の処理を実行
		super.initBean();
		// DAOを準備
		dao = createDaoInstance(SuspensionDaoInterface.class);
	}
	
	@Override
	public SuspensionDtoInterface getInitDto() {
		return new PfaHumanSuspensionDto();
	}
	
	@Override
	public List<SuspensionDtoInterface> getInitDtoList(int size) {
		// 登録リスト準備
		List<SuspensionDtoInterface> list = new ArrayList<SuspensionDtoInterface>();
		// DTOの準備
		for (int i = 0; i < size; i++) {
			list.add(getInitDto());
		}
		return list;
	}
	
	@Override
	public void regist(List<SuspensionDtoInterface> list) throws MospException {
		// 登録情報の検証
		checkRegist(list);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 登録
		for (SuspensionDtoInterface dto : list) {
			// レコード識別ID確認
			if (dto.getPfaHumanSuspensionId() == 0) {
				// 登録処理
				insert(dto);
			} else {
				// 更新処理
				update(dto);
			}
		}
	}
	
	@Override
	public void delete(long[] idArray) throws MospException {
		// レコード識別ID配列の妥当性確認
		validateAryId(idArray);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 対象ID配列の中身を削除
		for (long id : idArray) {
			// 排他確認
			checkExclusive(dao, id);
			if (mospParams.hasErrorMessage()) {
				// エラーが存在したら削除処理をしない
				continue;
			}
			// 論理削除
			logicalDelete(dao, id);
		}
	}
	
	/**
	 * 登録処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void insert(SuspensionDtoInterface dto) throws MospException {
		// レコード識別ID最大値をインクリメントしてセットする
		dto.setPfaHumanSuspensionId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	/**
	 * 更新処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void update(SuspensionDtoInterface dto) throws MospException {
		// 更新対象DTO取得
		SuspensionDtoInterface currentDto = (SuspensionDtoInterface)findForKey(dao, dto.getPfaHumanSuspensionId(),
				true);
		// 排他確認
		checkExclusive(currentDto);
		if (mospParams.hasErrorMessage()) {
			// エラーが存在したら登録処理をしない
			return;
		}
		// 更新要否確認
		if (isSameDto(dto, currentDto)) {
			// 値に変更が無ければ更新不要
			return;
		}
		// 論理削除
		logicalDelete(dao, dto.getPfaHumanSuspensionId());
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setPfaHumanSuspensionId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	/**
	 * 登録時の確認処理を行う。<br>
	 * @param list 対象DTOリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkRegist(List<SuspensionDtoInterface> list) throws MospException {
		// 個人ID取得
		String personalId = list.get(0).getPersonalId();
		// 人事入社情報取得
		Date entranceDate = getEntranceDate(personalId);
		// 人事入社情報確認
		if (entranceDate == null) {
			// 社員が入社していない場合のメッセージを追加
			PfMessageUtility.addErrorEmployeeNotJoin(mospParams);
			return;
		}
		// 人事退職情報取得
		Date retirementDate = getRetirementDate(personalId);
		// リストの内容を確認
		for (SuspensionDtoInterface dto : list) {
			// 入社確認(開始日)
			if (checkDateOrder(entranceDate, dto.getStartDate(), true) == false) {
				// エラーメッセージを設定
				PfMessageUtility.addErrorOrderInvalid(mospParams,
						PfNameUtility.suspensionStartDate(mospParams),
						PfNameUtility.entranceDate(mospParams));
			}
			// 退社確認(終了日)
			if (checkDateOrder(dto.getEndDate(), retirementDate, true) == false) {
				// エラーメッセージを設定
				PfMessageUtility.addErrorOrderInvalid(mospParams,
						PfNameUtility.retirementDate(mospParams),
						PfNameUtility.suspensionEndDate(mospParams));
			}
			// 退社確認(終了予定日)
			if (checkDateOrder(dto.getScheduleEndDate(), retirementDate, true) == false) {
				// エラーメッセージを設定
				PfMessageUtility.addErrorOrderInvalid(mospParams,
						PfNameUtility.retirementDate(mospParams),
						PfNameUtility.expectedSuspensionEndDate(mospParams));
			}
			// 開始日-終了日順序
			if (checkDateOrder(dto.getStartDate(), dto.getEndDate(), true) == false) {
				// エラーメッセージを設定
				PfMessageUtility.addErrorOrderInvalid(mospParams,
						PfNameUtility.suspensionEndDate(mospParams),
						PfNameUtility.suspensionStartDate(mospParams));
			}
			// 開始日-終了予定日
			if (checkDateOrder(dto.getStartDate(), dto.getScheduleEndDate(), true) == false) {
				// エラーメッセージを設定
				PfMessageUtility.addErrorOrderInvalid(mospParams,
						PfNameUtility.expectedSuspensionEndDate(mospParams),
						PfNameUtility.suspensionStartDate(mospParams));
			}
		}
		// 期間重複確認
		for (int i = 0; i < list.size(); i++) {
			SuspensionDtoInterface dto1 = list.get(i);
			for (int j = i + 1; j < list.size(); j++) {
				SuspensionDtoInterface dto2 = list.get(j);
				// 重複確認
				if (checkTermDuplicate(dto1.getStartDate(), dto1.getEndDate(), dto2.getStartDate(),
						dto2.getEndDate())) {
					continue;
				}
				// 期間が重複する場合のメッセージを設定
				PfMessageUtility.addErrorTermOverlap(mospParams, PfNameUtility.suspension(mospParams));
			}
		}
	}
	
	/**
	 * DTO1とDTO2を比較し、同じ値を持つかを比較する。<br>
	 * 但し、レコード識別ID、削除フラグ、作成日～更新者は、比較しない。<br>
	 * @param dto1 DTO1
	 * @param dto2 DTO2
	 * @return 比較結果(true：同じDTO、false：異なるDTO)
	 */
	protected boolean isSameDto(SuspensionDtoInterface dto1, SuspensionDtoInterface dto2) {
		// 個人ID
		if (dto1.getPersonalId().equals(dto2.getPersonalId()) == false) {
			return false;
		}
		// 開始日
		if (isSameDate(dto1.getStartDate(), dto2.getStartDate()) == false) {
			return false;
		}
		// 終了予定日
		if (isSameDate(dto1.getScheduleEndDate(), dto2.getScheduleEndDate()) == false) {
			return false;
		}
		// 終了日
		if (isSameDate(dto1.getEndDate(), dto2.getEndDate()) == false) {
			return false;
		}
		// 休職理由
		if (dto1.getSuspensionReason().equals(dto2.getSuspensionReason()) == false) {
			return false;
		}
		// 給与区分
		if (dto1.getAllowanceType().equals(dto2.getAllowanceType()) == false) {
			return false;
		}
		return true;
	}
	
}
