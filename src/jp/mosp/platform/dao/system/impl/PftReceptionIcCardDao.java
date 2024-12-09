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
package jp.mosp.platform.dao.system.impl;

import java.util.ArrayList;
import java.util.List;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformDao;
import jp.mosp.platform.dao.system.ReceptionIcCardDaoInterface;
import jp.mosp.platform.dto.system.ReceptionIcCardDtoInterface;
import jp.mosp.platform.dto.system.impl.PftReceptionIcCardDto;

/**
 * カードID受付番号DAO。
 */
public class PftReceptionIcCardDao extends PlatformDao implements ReceptionIcCardDaoInterface {
	
	/**
	 * ICカード受付テーブル。
	 */
	public static final String	TABLE							= "pft_reception_ic_card";
	
	/**
	 * レコード識別ID。
	 */
	public static final String	COL_PFT_RECEPTION_IC_CARD_ID	= "pft_reception_ic_card_id";
	
	/**
	 * カードID。
	 */
	public static final String	COL_IC_CARD_ID					= "ic_card_id";
	
	/**
	 * キー。
	 */
	public static final String	KEY_1							= COL_PFT_RECEPTION_IC_CARD_ID;
	
	
	@Override
	public void initDao() {
		// 処理なし	
	}
	
	@Override
	public BaseDto mapping() throws MospException {
		PftReceptionIcCardDto dto = new PftReceptionIcCardDto();
		dto.setPftReceptionIcCardId(getLong(COL_PFT_RECEPTION_IC_CARD_ID));
		dto.setIcCardId(getString(COL_IC_CARD_ID));
		mappingCommonInfo(dto);
		return dto;
	}
	
	@Override
	public List<?> mappingAll() throws MospException {
		List<ReceptionIcCardDtoInterface> all = new ArrayList<ReceptionIcCardDtoInterface>();
		while (next()) {
			all.add(castDto(mapping()));
		}
		return all;
	}
	
	@Override
	public void setParams(BaseDtoInterface baseDto, boolean isInsert) throws MospException {
		ReceptionIcCardDtoInterface dto = castDto(baseDto);
		setParam(index++, dto.getPftReceptionIcCardId());
		setParam(index++, dto.getIcCardId());
		setCommonParams(dto, isInsert);
	}
	
	@Override
	public int delete(BaseDtoInterface baseDto) {
		// 処理なし
		return 0;
	}
	
	@Override
	public int update(BaseDtoInterface baseDto) {
		// 処理なし
		return 0;
	}
	
	/**
	 * DTOインスタンスのキャストを行う。<br>
	 * @param baseDto 対象DTO
	 * @return キャストされたDTO
	 */
	protected ReceptionIcCardDtoInterface castDto(BaseDtoInterface baseDto) {
		return (ReceptionIcCardDtoInterface)baseDto;
	}
	
	@Override
	public ReceptionIcCardDtoInterface findForKey(long receiptNumber) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_PFT_RECEPTION_IC_CARD_ID));
			prepareStatement(sb.toString());
			setParam(index++, receiptNumber);
			executeQuery();
			ReceptionIcCardDtoInterface dto = null;
			if (next()) {
				dto = (ReceptionIcCardDtoInterface)mapping();
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
	public ReceptionIcCardDtoInterface findForIcCardId(String icCardId) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_IC_CARD_ID));
			prepareStatement(sb.toString());
			setParam(index++, icCardId);
			executeQuery();
			ReceptionIcCardDtoInterface dto = null;
			if (next()) {
				dto = (ReceptionIcCardDtoInterface)mapping();
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
