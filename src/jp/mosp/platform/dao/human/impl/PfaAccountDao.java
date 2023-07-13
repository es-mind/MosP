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
package jp.mosp.platform.dao.human.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformDao;
import jp.mosp.platform.dao.human.AccountDaoInterface;
import jp.mosp.platform.dto.human.AccountDtoInterface;
import jp.mosp.platform.dto.human.impl.PfaAccountDto;

/**
 * 口座情報DAOクラス。<br>
 */
public class PfaAccountDao extends PlatformDao implements AccountDaoInterface {
	
	/**
	 * 口座情報。<br>
	 */
	public static final String	TABLE				= "pfa_account";
	
	/**
	 * レコード識別ID。<br>
	 */
	public static final String	COL_PFA_ACCOUNT_ID	= "pfa_account_id";
	
	/**
	 * 保持者ID。<br>
	 */
	public static final String	COL_HOLDER_ID		= "holder_id";
	
	/**
	 * 口座区分。<br>
	 */
	public static final String	COL_ACCOUNT_TYPE	= "account_type";
	
	/**
	 * 有効日。<br>
	 */
	public static final String	COL_ACTIVATE_DATE	= "activate_date";
	
	/**
	 * 銀行コード。<br>
	 */
	public static final String	COL_BANK_CODE		= "bank_code";
	
	/**
	 * 銀行名。<br>
	 */
	public static final String	COL_BANK_NAME		= "bank_name";
	
	/**
	 * 支店コード。<br>
	 */
	public static final String	COL_BRANCH_CODE		= "branch_code";
	
	/**
	 * 支店名。<br>
	 */
	public static final String	COL_BRANCH_NAME		= "branch_name";
	
	/**
	 * 口座種別(普通/当座)。<br><br>
	 */
	public static final String	COL_ACCOUNT_CLASS	= "account_class";
	
	/**
	 * 口座番号。<br><br>
	 */
	public static final String	COL_ACCOUNT_NUMBER	= "account_number";
	
	/**
	 * 口座名義。<br>
	 */
	public static final String	COL_ACCOUNT_HOLDER	= "account_holder";
	
	/**
	 * 定額(給与)。<br>
	 */
	public static final String	COL_FIXED_PAYMENT	= "fixed_payment";
	
	/**
	 * 定額(賞与)。<br>
	 */
	public static final String	COL_FIXED_BONUS		= "fixed_bonus";
	
	/**
	 * 申請区分。<br>
	 */
	public static final String	COL_REQUEST_TYPE	= "request_type";
	
	/**
	 * ワークフロー番号。<br>
	 */
	public static final String	COL_WORKFLOW		= "workflow";
	
	/**
	 * レコード識別ID。<br>
	 */
	public static final String	KEY_1				= COL_PFA_ACCOUNT_ID;
	
	
	@Override
	public void initDao() {
		// 処理無し
	}
	
	@Override
	public BaseDto mapping() throws MospException {
		PfaAccountDto dto = new PfaAccountDto();
		dto.setPfaAccountId(getLong(COL_PFA_ACCOUNT_ID));
		dto.setHolderId(getString(COL_HOLDER_ID));
		dto.setAccountType(getString(COL_ACCOUNT_TYPE));
		dto.setActivateDate(getDate(COL_ACTIVATE_DATE));
		dto.setBankCode(getString(COL_BANK_CODE));
		dto.setBankName(getString(COL_BANK_NAME));
		dto.setBranchCode(getString(COL_BRANCH_CODE));
		dto.setBranchName(getString(COL_BRANCH_NAME));
		dto.setAccountClass(getString(COL_ACCOUNT_CLASS));
		dto.setAccountNumber(getString(COL_ACCOUNT_NUMBER));
		dto.setAccountHolder(getString(COL_ACCOUNT_HOLDER));
		dto.setFixedPayment(getInt(COL_FIXED_PAYMENT));
		dto.setFixedBonus(getInt(COL_FIXED_BONUS));
		dto.setRequestType(getString(COL_REQUEST_TYPE));
		dto.setWorkflow(getLong(COL_WORKFLOW));
		mappingCommonInfo(dto);
		return dto;
	}
	
	@Override
	public List<AccountDtoInterface> mappingAll() throws MospException {
		List<AccountDtoInterface> all = new ArrayList<AccountDtoInterface>();
		while (next()) {
			all.add(castDto(mapping()));
		}
		return all;
	}
	
	/**
	 * ResultSetの内容を、DTOにマッピングし、セットとして返す。<br>
	 * <br>
	 * @return 検索結果(DTOのSet)。
	 * @throws MospException SQL例外が発生した場合
	 */
	protected Set<AccountDtoInterface> mappingAllSet() throws MospException {
		Set<AccountDtoInterface> all = new HashSet<AccountDtoInterface>();
		while (next()) {
			all.add(castDto(mapping()));
		}
		return all;
	}
	
	@Override
	public void setParams(BaseDtoInterface baseDto, boolean isInsert) throws MospException {
		AccountDtoInterface dto = castDto(baseDto);
		setParam(index++, dto.getPfaAccountId());
		setParam(index++, dto.getHolderId());
		setParam(index++, dto.getAccountType());
		setParam(index++, dto.getActivateDate());
		setParam(index++, dto.getBankCode());
		setParam(index++, dto.getBankName());
		setParam(index++, dto.getBranchCode());
		setParam(index++, dto.getBranchName());
		setParam(index++, dto.getAccountClass());
		setParam(index++, dto.getAccountNumber());
		setParam(index++, dto.getAccountHolder());
		setParam(index++, dto.getFixedPayment());
		setParam(index++, dto.getFixedBonus());
		setParam(index++, dto.getRequestType());
		setParam(index++, dto.getWorkflow());
		setCommonParams(baseDto, isInsert);
	}
	
	@Override
	public int update(BaseDtoInterface baseDto) throws MospException {
		try {
			index = 1;
			prepareStatement(getUpdateQuery(getClass()));
			setParams(baseDto, false);
			AccountDtoInterface dto = castDto(baseDto);
			setParam(index++, dto.getPfaAccountId());
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
			AccountDtoInterface dto = castDto(baseDto);
			setParam(index++, dto.getPfaAccountId());
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
	
	/**
	 * DTOインスタンスのキャストを行う。<br>
	 * @param baseDto 対象DTO
	 * @return キャストされたDTO
	 */
	protected AccountDtoInterface castDto(BaseDtoInterface baseDto) {
		return (AccountDtoInterface)baseDto;
	}
	
	@Override
	public AccountDtoInterface findForKey(String holderId, String accountType, Date activateDate) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_HOLDER_ID));
			sb.append(and());
			sb.append(equal(COL_ACCOUNT_TYPE));
			sb.append(and());
			sb.append(equal(COL_ACTIVATE_DATE));
			prepareStatement(sb.toString());
			setParam(index++, holderId);
			setParam(index++, accountType);
			setParam(index++, activateDate);
			executeQuery();
			AccountDtoInterface dto = null;
			if (next()) {
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
	
	@Override
	public AccountDtoInterface findForInfo(String holderId, String accountType, Date targetDate) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_HOLDER_ID));
			sb.append(and());
			sb.append(equal(COL_ACCOUNT_TYPE));
			sb.append(and());
			sb.append(lessEqual(COL_ACTIVATE_DATE));
			sb.append(getOrderByColumnDescLimit1(COL_ACTIVATE_DATE));
			prepareStatement(sb.toString());
			setParam(index++, holderId);
			setParam(index++, accountType);
			setParam(index++, targetDate);
			executeQuery();
			AccountDtoInterface dto = null;
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
	
	@Override
	public AccountDtoInterface findForWorkflow(long workflow) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_WORKFLOW));
			prepareStatement(sb.toString());
			setParam(index++, workflow);
			executeQuery();
			AccountDtoInterface dto = null;
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
	
	@Override
	public List<AccountDtoInterface> findForHolder(String holderId, String accountType) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_HOLDER_ID));
			sb.append(and());
			sb.append(equal(COL_ACCOUNT_TYPE));
			sb.append(getOrderByColumn(COL_ACTIVATE_DATE));
			prepareStatement(sb.toString());
			setParam(index++, holderId);
			setParam(index++, accountType);
			executeQuery();
			return mappingAll();
		} catch (Throwable e) {
			throw new MospException(e);
		} finally {
			releaseResultSet();
			releasePreparedStatement();
		}
	}
	
	@Override
	public List<AccountDtoInterface> findForAll(String personalId) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_HOLDER_ID));
			sb.append(getOrderByColumn(COL_ACTIVATE_DATE));
			prepareStatement(sb.toString());
			setParam(index++, personalId);
			executeQuery();
			return mappingAll();
		} catch (Throwable e) {
			throw new MospException(e);
		} finally {
			releaseResultSet();
			releasePreparedStatement();
		}
	}
	
}
