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
import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformDao;
import jp.mosp.platform.dao.system.IcCardDaoInterface;
import jp.mosp.platform.dto.system.IcCardDtoInterface;
import jp.mosp.platform.dto.system.impl.PfmIcCardDto;

/**
 * ICカードマスタDAO。
 */
public class PfmIcCardDao extends PlatformDao implements IcCardDaoInterface {
	
	/**
	 * ICカードマスタ。
	 */
	public static final String	TABLE				= "pfm_ic_card";
	
	/**
	 * レコード識別ID。
	 */
	public static final String	COL_PFM_IC_CARD_ID	= "pfm_ic_card_id";
	
	/**
	 * カードID。
	 */
	public static final String	COL_IC_CARD_ID		= "ic_card_id";
	
	/**
	 * 有効日。
	 */
	public static final String	COL_ACTIVATE_DATE	= "activate_date";
	
	/**
	 * 個人ID。
	 */
	public static final String	COL_PERSONAL_ID		= "personal_id";
	
	/**
	 * 無効フラグ。
	 */
	public static final String	COL_INACTIVATE_FLAG	= "inactivate_flag";
	
	/**
	 * キー。
	 */
	public static final String	KEY_1				= COL_PFM_IC_CARD_ID;
	
	
	@Override
	public void initDao() {
		// 処理なし	
	}
	
	@Override
	public BaseDto mapping() throws MospException {
		PfmIcCardDto dto = new PfmIcCardDto();
		dto.setPfmIcCardId(getLong(COL_PFM_IC_CARD_ID));
		dto.setIcCardId(getString(COL_IC_CARD_ID));
		dto.setActivateDate(getDate(COL_ACTIVATE_DATE));
		dto.setPersonalId(getString(COL_PERSONAL_ID));
		dto.setInactivateFlag(getInt(COL_INACTIVATE_FLAG));
		mappingCommonInfo(dto);
		return dto;
	}
	
	@Override
	public List<IcCardDtoInterface> mappingAll() throws MospException {
		List<IcCardDtoInterface> all = new ArrayList<IcCardDtoInterface>();
		while (next()) {
			all.add(castDto(mapping()));
		}
		return all;
	}
	
	@Override
	public void setParams(BaseDtoInterface baseDto, boolean isInsert) throws MospException {
		IcCardDtoInterface dto = castDto(baseDto);
		setParam(index++, dto.getPfmIcCardId());
		setParam(index++, dto.getIcCardId());
		setParam(index++, dto.getActivateDate());
		setParam(index++, dto.getPersonalId());
		setParam(index++, dto.getInactivateFlag());
		setCommonParams(baseDto, isInsert);
	}
	
	@Override
	public int update(BaseDtoInterface baseDto) throws MospException {
		try {
			index = 1;
			prepareStatement(getUpdateQuery(getClass()));
			setParams(baseDto, false);
			IcCardDtoInterface dto = castDto(baseDto);
			setParam(index++, dto.getPfmIcCardId());
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
			IcCardDtoInterface dto = castDto(baseDto);
			setParam(index++, dto.getPfmIcCardId());
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
	protected IcCardDtoInterface castDto(BaseDtoInterface baseDto) {
		return (IcCardDtoInterface)baseDto;
	}
	
	@Override
	public IcCardDtoInterface findForCardIdInfo(String cardId, Date activeDate) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_IC_CARD_ID));
			sb.append(and());
			sb.append(lessEqual(COL_ACTIVATE_DATE));
			sb.append(getOrderByColumnDescLimit1(COL_ACTIVATE_DATE));
			prepareStatement(sb.toString());
			setParam(index++, cardId);
			setParam(index++, activeDate);
			executeQuery();
			IcCardDtoInterface dto = null;
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
	public IcCardDtoInterface findForKey(String cardId, Date activeDate) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_IC_CARD_ID));
			sb.append(and());
			sb.append(equal(COL_ACTIVATE_DATE));
			prepareStatement(sb.toString());
			setParam(index++, cardId);
			setParam(index++, activeDate);
			executeQuery();
			IcCardDtoInterface dto = null;
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
	public List<IcCardDtoInterface> findForActivateDate(Date activateDate) throws MospException {
		try {
			index = 1;
			StringBuffer sb = new StringBuffer();
			// SELECT部追加
			sb.append(getSelectQuery(getClass()));
			// WHERE部追加(対象日以前で削除されていない最新の情報を取得)
			sb.append(getQueryForMaxActivateDate(TABLE, COL_IC_CARD_ID, COL_ACTIVATE_DATE));
			sb.append(where());
			sb.append(deleteFlagOff());
			// ステートメント準備
			prepareStatement(sb.toString());
			// パラメータ設定
			setParam(index++, activateDate);
			// SQL実行
			executeQuery();
			// 結果取得
			return mappingAll();
		} catch (Throwable e) {
			throw new MospException(e);
		} finally {
			releaseResultSet();
			releasePreparedStatement();
		}
	}
	
	@Override
	public List<IcCardDtoInterface> getFindForHumanList(String personalId, Date activeDate) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(getQueryForMaxActivateDate(TABLE, COL_IC_CARD_ID, COL_ACTIVATE_DATE));
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_PERSONAL_ID));
			prepareStatement(sb.toString());
			setParam(index++, activeDate);
			setParam(index++, personalId);
			// SQL実行
			executeQuery();
			// 結果取得
			return mappingAll();
		} catch (Throwable e) {
			throw new MospException(e);
		} finally {
			releaseResultSet();
			releasePreparedStatement();
		}
	}
	
	@Override
	public List<IcCardDtoInterface> findForHistory(String icCardId) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_IC_CARD_ID));
			sb.append(getOrderByColumn(COL_ACTIVATE_DATE));
			prepareStatement(sb.toString());
			setParam(index++, icCardId);
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
