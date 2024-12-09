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
import jp.mosp.platform.dao.human.EntranceDaoInterface;
import jp.mosp.platform.dto.human.EntranceDtoInterface;
import jp.mosp.platform.dto.human.impl.PfaHumanEntranceDto;

/**
 * 人事入社情報DAOクラス。
 */
public class PfaHumanEntranceDao extends PlatformDao implements EntranceDaoInterface {
	
	/**
	 * 人事入社情報。
	 */
	public static final String	TABLE						= "pfa_human_entrance";
	
	/**
	 * レコード識別ID。
	 */
	public static final String	COL_PFA_HUMAN_ENTRANCE_ID	= "pfa_human_entrance_id";
	
	/**
	 * 個人ID。
	 */
	public static final String	COL_PERSONAL_ID				= "personal_id";
	
	/**
	 * 入社日。
	 */
	public static final String	COL_ENTRANCE_DATE			= "entrance_date";
	
	/**
	 * キー。
	 */
	public static final String	KEY_1						= COL_PFA_HUMAN_ENTRANCE_ID;
	
	
	/**
	 * コンストラクタ。
	 */
	public PfaHumanEntranceDao() {
		// 処理無し
	}
	
	@Override
	public void initDao() {
		// 処理無し
	}
	
	@Override
	public BaseDto mapping() throws MospException {
		PfaHumanEntranceDto dto = new PfaHumanEntranceDto();
		dto.setPfaHumanEntranceId(getLong(COL_PFA_HUMAN_ENTRANCE_ID));
		dto.setPersonalId(getString(COL_PERSONAL_ID));
		dto.setEntranceDate(getDate(COL_ENTRANCE_DATE));
		mappingCommonInfo(dto);
		return dto;
	}
	
	@Override
	public List<?> mappingAll() throws MospException {
		List<EntranceDtoInterface> all = new ArrayList<EntranceDtoInterface>();
		while (next()) {
			all.add(castDto(mapping()));
		}
		return all;
	}
	
	@Override
	public EntranceDtoInterface findForInfo(String personalId) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_PERSONAL_ID));
			prepareStatement(sb.toString());
			setParam(index++, personalId);
			executeQuery();
			EntranceDtoInterface dto = null;
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
			EntranceDtoInterface dto = castDto(baseDto);
			setParam(index++, dto.getPfaHumanEntranceId());
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
			EntranceDtoInterface dto = castDto(baseDto);
			setParam(index++, dto.getPfaHumanEntranceId());
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
		EntranceDtoInterface dto = castDto(baseDto);
		setParam(index++, dto.getPfaHumanEntranceId());
		setParam(index++, dto.getPersonalId());
		setParam(index++, dto.getEntranceDate());
		setCommonParams(baseDto, isInsert);
	}
	
	/**
	 * DTOインスタンスのキャストを行う。<br>
	 * @param baseDto 対象DTO
	 * @return キャストされたDTO
	 */
	protected EntranceDtoInterface castDto(BaseDtoInterface baseDto) {
		return (EntranceDtoInterface)baseDto;
	}
	
	@Override
	public Set<String> findForEntrancedPersonalIdSet(Date targetDate, Date startDate, Date endDate)
			throws MospException {
		try {
			index = 1;
			StringBuffer sb = new StringBuffer();
			sb.append(select());
			sb.append(COL_PERSONAL_ID);
			sb.append(from(TABLE));
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(lessEqual(COL_ENTRANCE_DATE));
			prepareStatement(sb.toString());
			if (startDate != null && endDate != null) {
				setParam(index++, endDate);
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
	public Map<String, EntranceDtoInterface> findForPersonalIds(String[] personalIds) throws MospException {
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
			Map<String, EntranceDtoInterface> allmap = new HashMap<String, EntranceDtoInterface>();
			while (next()) {
				EntranceDtoInterface dto = castDto(mapping());
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
}
