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
package jp.mosp.platform.dao.human.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformDao;
import jp.mosp.platform.dao.human.RetirementDaoInterface;
import jp.mosp.platform.dto.human.RetirementDtoInterface;
import jp.mosp.platform.dto.human.impl.PfaHumanRetirementDto;

/**
 * 人事退職情報DAOクラス。
 */
public class PfaHumanRetirementDao extends PlatformDao implements RetirementDaoInterface {
	
	/**
	 * 人事退職情報。
	 */
	public static final String	TABLE						= "pfa_human_retirement";
	
	/**
	 * レコード識別ID。
	 */
	public static final String	COL_PFA_HUMAN_RETIREMENT_ID	= "pfa_human_retirement_id";
	
	/**
	 * 個人ID。
	 */
	public static final String	COL_PERSONAL_ID				= "personal_id";
	
	/**
	 * 退職日。
	 */
	public static final String	COL_RETIREMENT_DATE			= "retirement_date";
	
	/**
	 * 退職理由。
	 */
	public static final String	COL_RETIREMENT_REASON		= "retirement_reason";
	
	/**
	 * 詳細。
	 */
	public static final String	COL_RETIREMENT_DETAIL		= "retirement_detail";
	
	/**
	 * キー。
	 */
	public static final String	KEY_1						= COL_PFA_HUMAN_RETIREMENT_ID;
	
	
	/**
	 * コンストラクタ。
	 */
	public PfaHumanRetirementDao() {
		// 処理無し
	}
	
	/**
	 * @throws MospException アドオンで例外が発生した場合
	 */
	@Override
	public void initDao() throws MospException {
		// 処理無し(アドオンで実装)
	}
	
	@Override
	public BaseDto mapping() throws MospException {
		PfaHumanRetirementDto dto = new PfaHumanRetirementDto();
		dto.setPfaHumanRetirementId(getLong(COL_PFA_HUMAN_RETIREMENT_ID));
		dto.setPersonalId(getString(COL_PERSONAL_ID));
		dto.setRetirementDate(getDate(COL_RETIREMENT_DATE));
		dto.setRetirementReason(getString(COL_RETIREMENT_REASON));
		dto.setRetirementDetail(getString(COL_RETIREMENT_DETAIL));
		mappingCommonInfo(dto);
		return dto;
	}
	
	@Override
	public List<RetirementDtoInterface> mappingAll() throws MospException {
		List<RetirementDtoInterface> all = new ArrayList<RetirementDtoInterface>();
		while (next()) {
			all.add(castDto(mapping()));
		}
		return all;
	}
	
	@Override
	public RetirementDtoInterface findForInfo(String personalId, Date retirementDate) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_PERSONAL_ID));
			sb.append(and());
			sb.append(lessEqual(COL_RETIREMENT_DATE));
			prepareStatement(sb.toString());
			setParam(index++, personalId);
			setParam(index++, retirementDate);
			executeQuery();
			RetirementDtoInterface dto = null;
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
	public RetirementDtoInterface findForInfo(String personalId) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_PERSONAL_ID));
			sb.append(getOrderByColumnDescLimit1(COL_RETIREMENT_DATE));
			prepareStatement(sb.toString());
			setParam(index++, personalId);
			executeQuery();
			RetirementDtoInterface dto = null;
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
	public int update(BaseDtoInterface baseDto) throws MospException {
		try {
			index = 1;
			prepareStatement(getUpdateQuery(getClass()));
			setParams(baseDto, false);
			RetirementDtoInterface dto = castDto(baseDto);
			setParam(index++, dto.getPfaHumanRetirementId());
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
			RetirementDtoInterface dto = castDto(baseDto);
			setParam(index++, dto.getPfaHumanRetirementId());
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
		RetirementDtoInterface dto = castDto(baseDto);
		setParam(index++, dto.getPfaHumanRetirementId());
		setParam(index++, dto.getPersonalId());
		setParam(index++, dto.getRetirementDate());
		setParam(index++, dto.getRetirementReason());
		setParam(index++, dto.getRetirementDetail());
		setCommonParams(baseDto, isInsert);
	}
	
	@Override
	public Set<String> findForRetiredPersonalIdSet(Date targetDate, Date startDate, Date endDate) throws MospException {
		try {
			index = 1;
			StringBuffer sb = new StringBuffer();
			sb.append(select());
			sb.append(COL_PERSONAL_ID);
			sb.append(from(TABLE));
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(less(COL_RETIREMENT_DATE));
			prepareStatement(sb.toString());
			// 退職
			if (startDate != null && endDate != null) {
				setParam(index++, startDate);
			} else {
				setParam(index++, targetDate);
			}
			executeQuery();
			return getResultAsSet(COL_PERSONAL_ID);
		} catch (Throwable e) {
			throw new MospException(e);
		} finally {
			releaseResultSet();
			releasePreparedStatement();
		}
	}
	
	@Override
	public Map<String, RetirementDtoInterface> findForPersonalIds(String[] personalIds) throws MospException {
		try {
			// パラメータインデックス準備
			index = 1;
			// SQL作成
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(in(COL_PERSONAL_ID, personalIds.length));
			// ステートメント生成
			prepareStatement(sb.toString());
			setParamsIn(personalIds);
			// SQL実行
			executeQuery();
			// 結果取得
			Map<String, RetirementDtoInterface> allmap = new HashMap<String, RetirementDtoInterface>();
			while (next()) {
				RetirementDtoInterface dto = castDto(mapping());
				allmap.put(dto.getPersonalId(), dto);
			}
			return allmap;
		} catch (Throwable e) {
			throw new MospException(e);
		} finally {
			releaseResultSet();
			releasePreparedStatement();
		}
	}
	
	@Override
	public String getQueryForJoinUser(String personalIdColumn) {
		// SQL作成準備
		StringBuffer sb = new StringBuffer();
		sb.append(leftJoin());
		sb.append(leftParenthesis());
		sb.append(select());
		sb.append(COL_PERSONAL_ID);
		sb.append(asTmpTable(COL_PERSONAL_ID));
		sb.append(comma());
		sb.append(COL_RETIREMENT_DATE);
		sb.append(asTmpTable(COL_RETIREMENT_DATE));
		sb.append(from(TABLE));
		sb.append(where());
		sb.append(deleteFlagOff());
		sb.append(rightParenthesis());
		sb.append(asTmpTable(TABLE));
		sb.append(on());
		sb.append(personalIdColumn);
		sb.append(equal());
		sb.append(getExplicitTableColumn(getTmpTable(TABLE), getTmpColumn(COL_PERSONAL_ID)));
		// SQLを取得
		return sb.toString();
	}
	
	@Override
	public String getRetirementDateColumnForJoinUser() {
		return getTmpColumn(COL_RETIREMENT_DATE);
	}
	
	/**
	 * DTOインスタンスのキャストを行う。<br>
	 * @param baseDto 対象DTO
	 * @return キャストされたDTO
	 */
	protected RetirementDtoInterface castDto(BaseDtoInterface baseDto) {
		return (RetirementDtoInterface)baseDto;
	}
	
}
