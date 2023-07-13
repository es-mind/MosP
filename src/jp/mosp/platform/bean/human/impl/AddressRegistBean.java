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
package jp.mosp.platform.bean.human.impl;

import java.util.Date;
import java.util.Set;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.human.AddressRegistBeanInterface;
import jp.mosp.platform.dao.human.AddressDaoInterface;
import jp.mosp.platform.dto.human.AddressDtoInterface;
import jp.mosp.platform.dto.human.impl.PfaAddressDto;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.platform.utils.PfNameUtility;

/**
 * 住所情報登録クラス。<br>
 */
public class AddressRegistBean extends PlatformBean implements AddressRegistBeanInterface {
	
	/**
	 * 項目長(保持者ID)。<br>
	 */
	protected static final int		LEN_HOLDER_ID		= 20;
	
	/**
	 * 項目長(住所区分)。<br>
	 */
	protected static final int		LEN_ADDRESS_TYPE	= 10;
	
	/**
	 * 住所情報DAOクラス。<br>
	 */
	protected AddressDaoInterface	dao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public AddressRegistBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// DAOを準備
		dao = createDaoInstance(AddressDaoInterface.class);
	}
	
	@Override
	public AddressDtoInterface getInitDto() {
		return new PfaAddressDto();
	}
	
	@Override
	public void registPersonalAddress(AddressDtoInterface dto) throws MospException {
		// DTOに住所区分(個人)を設定
		dto.setAddressType(AddressReferenceBean.TYPE_ADDRESS_PERSONAL);
		// 住所情報の登録
		regist(dto);
	}
	
	@Override
	public void registLegalAddress(AddressDtoInterface dto) throws MospException {
		// DTOに住所区分(住民票)を設定
		dto.setAddressType(AddressReferenceBean.TYPE_ADDRESS_LEGAL);
		// 住所情報の登録
		regist(dto);
	}
	
	/**
	 * 住所情報の登録を行う。<br>
	 * <br>
	 * 同一個人ID、住所区分及び有効日の情報が存在する場合は、
	 * 論理削除の上、登録する。<br>
	 * <br>
	 * @param dto 対象DTO
	 * @throws MospException  インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void regist(AddressDtoInterface dto) throws MospException {
		// DTOの妥当性確認
		validate(dto, null);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 重複DTO取得
		AddressDtoInterface duplicateDto = dao.findForKey(dto.getHolderId(), dto.getAddressType(),
				dto.getActivateDate());
		// 重複DTOが存在する場合
		if (duplicateDto != null) {
			// 論理削除
			logicalDelete(dao, duplicateDto.getPfaAddressId());
		}
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setPfaAddressId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void deleteAddress(String holderId, Date activateDate, String requestType) throws MospException {
		// 住所情報群を取得
		Set<AddressDtoInterface> set = dao.findForRequestType(holderId, activateDate, requestType);
		// 住所情報毎に処理
		for (AddressDtoInterface dto : set) {
			// 削除
			logicalDelete(dao, dto.getPfaAddressId());
		}
	}
	
	@Override
	public void validate(AddressDtoInterface dto, Integer row) throws MospException {
		// 必須確認(保持者ID)
		checkRequired(dto.getHolderId(), PfNameUtility.id(mospParams), row);
		// 必須確認(住所区分)
		checkRequired(dto.getAddressType(), PfNameUtility.addressType(mospParams), row);
		// 必須確認(有効日)
		checkRequired(dto.getActivateDate(), PfNameUtility.activateDate(mospParams), row);
		// 桁数確認(保持者ID)
		checkLength(dto.getHolderId(), LEN_HOLDER_ID, PfNameUtility.id(mospParams), row);
		// 桁数確認(住所区分)
		checkLength(dto.getAddressType(), LEN_ADDRESS_TYPE, PfNameUtility.addressType(mospParams), row);
		// 削除フラグ確認
		checkDeleteFlag(dto.getDeleteFlag(), row);
		// 他申請による住所情報の存在確認
		checkOtherAddressExist(dto, row);
	}
	
	/**
	 * 他申請による住所情報の存在確認を行う。<br>
	 * <br>
	 * 保持者ID、有効日及び申請区分が合致する情報が存在し
	 * 登録情報と申請区分が異なる場合は、エラーメッセージを設定する。<br>
	 * <br>
	 * @param dto 対象DTO
	 * @param row 行インデックス
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void checkOtherAddressExist(AddressDtoInterface dto, Integer row) throws MospException {
		// 有効日を取得
		Date activateDate = dto.getActivateDate();
		// 保持者ID、有効日及び申請区分が合致する情報を取得
		AddressDtoInterface duplicateDto = dao.findForKey(dto.getHolderId(), dto.getAddressType(), activateDate);
		// 合致する情報が存在しない場合
		if (duplicateDto == null) {
			return;
		}
		// 申請区分が同じ場合
		if (duplicateDto.getRequestType().equals(dto.getRequestType())) {
			return;
		}
		// エラーメッセージを設定
		PfMessageUtility.addErrorOtherAddressExist(mospParams, activateDate, row);
	}
	
}
