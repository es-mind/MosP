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

import java.util.Date;
import java.util.Set;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.human.PhoneRegistBeanInterface;
import jp.mosp.platform.dao.human.PhoneDaoInterface;
import jp.mosp.platform.dto.human.PhoneDtoInterface;
import jp.mosp.platform.dto.human.impl.PfaPhoneDto;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.platform.utils.PfNameUtility;

/**
 * 電話情報登録クラス
 */
public class PhoneRegistBean extends PlatformBean implements PhoneRegistBeanInterface {
	
	/**
	 * 項目長(保持者ID)。<br>
	 */
	protected static final int	LEN_HOLDER_ID	= 20;
	
	/**
	 * 項目長(電話区分)。<br>
	 */
	protected static final int	LEN_PHONE_TYPE	= 10;
	
	/**
	 * 電話情報DAOクラス。<br>
	 */
	protected PhoneDaoInterface	dao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public PhoneRegistBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// DAOを準備
		dao = createDaoInstance(PhoneDaoInterface.class);
	}
	
	@Override
	public PhoneDtoInterface getInitDto() {
		return new PfaPhoneDto();
	}
	
	@Override
	public void registPersonalPhone(PhoneDtoInterface dto) throws MospException {
		// DTOに電話区分(個人)を設定
		dto.setPhoneType(PhoneReferenceBean.TYPE_PHONE_PERSONAL);
		// DTOの妥当性確認
		validate(dto, null);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 重複DTO取得
		PhoneDtoInterface duplicateDto = dao.findForKey(dto.getHolderId(), dto.getPhoneType(), dto.getActivateDate());
		// 重複DTO確認
		if (duplicateDto != null) {
			// 論理削除
			logicalDelete(dao, duplicateDto.getPfaPhoneId());
		}
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setPfaPhoneId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void deletePhone(String holderId, Date activateDate, String requestType) throws MospException {
		// 電話情報群を取得
		Set<PhoneDtoInterface> set = dao.findForRequestType(holderId, activateDate, requestType);
		// 電話情報毎に処理
		for (PhoneDtoInterface dto : set) {
			// 削除
			logicalDelete(dao, dto.getPfaPhoneId());
		}
	}
	
	@Override
	public void validate(PhoneDtoInterface dto, Integer row) throws MospException {
		// 必須確認(保持者ID)
		checkRequired(dto.getHolderId(), PfNameUtility.id(mospParams), row);
		// 必須確認(電話区分)
		checkRequired(dto.getPhoneType(), PfNameUtility.phoneType(mospParams), row);
		// 必須確認(有効日)
		checkRequired(dto.getActivateDate(), PfNameUtility.activateDate(mospParams), row);
		// 桁数確認(保持者ID)
		checkLength(dto.getHolderId(), LEN_HOLDER_ID, PfNameUtility.id(mospParams), row);
		// 桁数確認(電話区分)
		checkLength(dto.getPhoneType(), LEN_PHONE_TYPE, PfNameUtility.phoneType(mospParams), row);
		// 削除フラグ確認
		checkDeleteFlag(dto.getDeleteFlag(), row);
		// 他申請による電話情報の存在確認
		checkOtherPhoneExist(dto, row);
	}
	
	/**
	 * 他申請による電話情報の存在確認を行う。<br>
	 * <br>
	 * 保持者ID、有効日及び申請区分が合致する情報が存在し
	 * 登録情報と申請区分が異なる場合は、エラーメッセージを設定する。<br>
	 * <br>
	 * @param dto 対象DTO
	 * @param row 行インデックス
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void checkOtherPhoneExist(PhoneDtoInterface dto, Integer row) throws MospException {
		// 有効日を取得
		Date activateDate = dto.getActivateDate();
		// 保持者ID、有効日及び申請区分が合致する情報を取得
		PhoneDtoInterface duplicateDto = dao.findForKey(dto.getHolderId(), dto.getPhoneType(), activateDate);
		// 合致する情報が存在しない場合
		if (duplicateDto == null) {
			return;
		}
		// 申請区分が同じ場合
		if (duplicateDto.getRequestType().equals(dto.getRequestType())) {
			return;
		}
		// エラーメッセージを設定
		PfMessageUtility.addErrorOtherPhoneExist(mospParams, activateDate, row);
	}
	
}
