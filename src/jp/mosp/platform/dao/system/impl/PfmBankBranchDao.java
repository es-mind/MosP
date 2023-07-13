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
package jp.mosp.platform.dao.system.impl;

import java.util.ArrayList;
import java.util.List;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformDao;
import jp.mosp.platform.dao.system.BankBranchDaoInterface;
import jp.mosp.platform.dto.system.BankBranchDtoInterface;
import jp.mosp.platform.dto.system.impl.PfmBankBranchDto;

/**
 * 銀行支店マスタDAOクラス。
 */
public class PfmBankBranchDao extends PlatformDao implements BankBranchDaoInterface {
	
	/**
	 * テーブル。
	 */
	public static final String	TABLE					= "pfm_bank_branch";
	
	/**
	 * レコード識別ID。
	 */
	public static final String	COL_PFM_BANK_BRANCH_ID	= "pfm_bank_branch_id";
	
	/**
	 *  銀行コード。
	 */
	public static final String	COL_BANK_CODE			= "bank_code";
	
	/**
	 * 支店コード。
	 */
	public static final String	COL_BRANCH_CODE			= "branch_code";
	
	/**
	 * 支店名。
	 */
	public static final String	COL_BRANCH_NAME			= "branch_name";
	
	/**
	 * 支店名カナ。
	 */
	public static final String	COL_BRANCH_KANA			= "branch_kana";
	
	/**
	 * 支店所在地。
	 */
	public static final String	COL_BRANCH_ADDRESS_NAME	= "branch_address_name";
	
	/**
	 * 支店所在地カナ。
	 */
	public static final String	COL_BRANCH_ADDRESS_KANA	= "branch_address_kana";
	
	/**
	 * 支店電話番号。
	 */
	public static final String	COL_BRANCH_PHONE		= "branch_phone";
	
	/**
	 * 無効フラグ。
	 */
	public static final String	COL_INACTIVATE_FLAG		= "inactivate_flag";
	
	/**
	 * キー。
	 */
	public static final String	KEY_1					= COL_PFM_BANK_BRANCH_ID;
	
	
	/**
	 * コンストラクタ。
	 */
	public PfmBankBranchDao() {
		// 処理無し
	}
	
	@Override
	public void initDao() {
		// 処理無し
	}
	
	@Override
	public BaseDto mapping() throws MospException {
		// DTOの準備
		PfmBankBranchDto dto = new PfmBankBranchDto();
		// ResultSetの中身をDTOに設定
		dto.setPfmBankBranchId(getLong(COL_PFM_BANK_BRANCH_ID));
		dto.setBankCode(getString(COL_BANK_CODE));
		dto.setBranchCode(getString(COL_BRANCH_CODE));
		dto.setBranchName(getString(COL_BRANCH_NAME));
		dto.setBranchKana(getString(COL_BRANCH_KANA));
		dto.setBranchAddressName(getString(COL_BRANCH_ADDRESS_NAME));
		dto.setBranchAddressKana(getString(COL_BRANCH_ADDRESS_KANA));
		dto.setBranchPhone(getString(COL_BRANCH_PHONE));
		dto.setInactivateFlag(getInt(COL_INACTIVATE_FLAG));
		// 共通列情報をDTOに設定
		mappingCommonInfo(dto);
		return dto;
	}
	
	@Override
	public List<BankBranchDtoInterface> mappingAll() throws MospException {
		// DTOのリストを準備
		List<BankBranchDtoInterface> all = new ArrayList<BankBranchDtoInterface>();
		// ResultSetの中身をDTOに設定しリストに追加
		while (next()) {
			all.add((BankBranchDtoInterface)mapping());
		}
		return all;
	}
	
	@Override
	public int update(BaseDtoInterface baseDto) throws MospException {
		try {
			index = 1;
			prepareStatement(getUpdateQuery(getClass()));
			setParams(baseDto, false);
			BankBranchDtoInterface dto = castDto(baseDto);
			setParam(index++, dto.getPfmBankBranchId());
			executeUpdate();
			chkUpdate(1);
			return cnt;
		} catch (Throwable e) {
			throw new MospException(e);
		} finally {
			releaseResultSet();
			releasePreparedStatement();
		}
	}
	
	@Override
	public int delete(BaseDtoInterface baseDto) throws MospException {
		try {
			index = 1;
			prepareStatement(getDeleteQuery(getClass()));
			BankBranchDtoInterface dto = castDto(baseDto);
			setParam(index++, dto.getPfmBankBranchId());
			executeUpdate();
			chkDelete(1);
			return cnt;
		} catch (Throwable e) {
			throw new MospException(e);
		} finally {
			releaseResultSet();
			releasePreparedStatement();
		}
	}
	
	@Override
	public void setParams(BaseDtoInterface baseDto, boolean isInsert) throws MospException {
		BankBranchDtoInterface dto = castDto(baseDto);
		setParam(index++, dto.getPfmBankBranchId());
		setParam(index++, dto.getBankCode());
		setParam(index++, dto.getBranchCode());
		setParam(index++, dto.getBranchName());
		setParam(index++, dto.getBranchKana());
		setParam(index++, dto.getBranchAddressName());
		setParam(index++, dto.getBranchAddressKana());
		setParam(index++, dto.getBranchPhone());
		setParam(index++, dto.getInactivateFlag());
		setCommonParams(baseDto, isInsert);
	}
	
	/**
	 * DTOインスタンスのキャストを行う。<br>
	 * @param baseDto 対象DTO
	 * @return キャストされたDTO
	 */
	protected BankBranchDtoInterface castDto(BaseDtoInterface baseDto) {
		return (BankBranchDtoInterface)baseDto;
	}
	
	@Override
	public List<BankBranchDtoInterface> searchBankBranchInfo(String bankCode, String value) throws MospException {
		try {
			index = 1;
			// SQL作成準備(SELECT文追加)
			StringBuffer sb = new StringBuffer(getSelectQuery(getClass()));
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_BANK_CODE));
			// 検索条件SQL追加
			sb.append(and());
			sb.append(leftParenthesis());
			sb.append(like(COL_BRANCH_CODE));
			sb.append(or());
			sb.append(like(COL_BRANCH_KANA));
			sb.append(or());
			sb.append(like(COL_BRANCH_NAME));
			sb.append(rightParenthesis());
			// パラメータインデックス準備
			prepareStatement(sb.toString());
			setParam(index++, bankCode);
			setParam(index++, containsParam(value));
			setParam(index++, containsParam(value));
			setParam(index++, containsParam(value));
			// SQL実行
			executeQuery();
			// 検索結果取得
			return mappingAll();
		} catch (Throwable e) {
			throw new MospException(e);
		} finally {
			releaseResultSet();
			releasePreparedStatement();
		}
	}
	
	@Override
	public BankBranchDtoInterface findForKey(String bankCode, String branchCode) throws MospException {
		try {
			index = 1;
			// SQL作成準備(SELECT文追加)
			StringBuffer sb = new StringBuffer(getSelectQuery(getClass()));
			sb.append(where());
			sb.append(deleteFlagOff());
			// 検索条件SQL追加
			sb.append(and());
			sb.append(equal(COL_BANK_CODE));
			sb.append(and());
			sb.append(equal(COL_BRANCH_CODE));
			prepareStatement(sb.toString());
			setParam(index++, bankCode);
			setParam(index++, branchCode);
			executeQuery();
			BankBranchDtoInterface dto = null;
			if (rs.next()) {
				dto = castDto(mapping());
			}
			return dto;
		} catch (Throwable e) {
			throw new MospException(e);
		} finally {
			releaseResultSet();
			releasePreparedStatement();
		}
	}
}
