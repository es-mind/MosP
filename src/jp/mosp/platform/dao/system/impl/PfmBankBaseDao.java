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
import jp.mosp.platform.dao.system.BankBaseDaoInterface;
import jp.mosp.platform.dto.system.BankBaseDtoInterface;
import jp.mosp.platform.dto.system.impl.PfmBankBaseDto;

/**
 * 銀行マスタDAOインターフェース。<br>
 */
public class PfmBankBaseDao extends PlatformDao implements BankBaseDaoInterface {
	
	/**
	 * テーブル。
	 */
	public static final String	TABLE					= "pfm_bank_base";
	
	/**
	 * レコード識別ID。
	 */
	public static final String	COL_PFM_BANK_BASE_ID	= "pfm_bank_base_id";
	
	/**
	 *  銀行コード。
	 */
	public static final String	COL_BANK_CODE			= "bank_code";
	
	/**
	 *  銀行名。
	 */
	public static final String	COL_BANK_NAME			= "bank_name";
	
	/**
	 *  銀行名カナ。
	 */
	public static final String	COL_BANK_NAME_KANA		= "bank_name_kana";
	
	/**
	 * 無効フラグ。
	 */
	public static final String	COL_INACTIVATE_FLAG		= "inactivate_flag";
	
	/**
	 * キー。
	 */
	public static final String	KEY_1					= COL_PFM_BANK_BASE_ID;
	
	
	/**
	 * コンストラクタ。
	 */
	public PfmBankBaseDao() {
		// 処理無し
	}
	
	@Override
	public void initDao() {
		// 処理無し
	}
	
	@Override
	public BaseDto mapping() throws MospException {
		// DTOの準備
		PfmBankBaseDto dto = new PfmBankBaseDto();
		// ResultSetの中身をDTOに設定
		dto.setPfmBankBaseId(getLong(COL_PFM_BANK_BASE_ID));
		dto.setBankCode(getString(COL_BANK_CODE));
		dto.setBankName(getString(COL_BANK_NAME));
		dto.setBankNameKana(getString(COL_BANK_NAME_KANA));
		dto.setInactivateFlag(getInt(COL_INACTIVATE_FLAG));
		// 共通列情報をDTOに設定
		mappingCommonInfo(dto);
		return dto;
	}
	
	@Override
	public List<BankBaseDtoInterface> mappingAll() throws MospException {
		// DTOのリストを準備
		List<BankBaseDtoInterface> all = new ArrayList<BankBaseDtoInterface>();
		// ResultSetの中身をDTOに設定しリストに追加
		while (next()) {
			all.add((BankBaseDtoInterface)mapping());
		}
		return all;
	}
	
	@Override
	public int update(BaseDtoInterface baseDto) throws MospException {
		try {
			index = 1;
			prepareStatement(getUpdateQuery(getClass()));
			setParams(baseDto, false);
			BankBaseDtoInterface dto = castDto(baseDto);
			setParam(index++, dto.getPfmBankBaseId());
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
			BankBaseDtoInterface dto = castDto(baseDto);
			setParam(index++, dto.getPfmBankBaseId());
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
		BankBaseDtoInterface dto = castDto(baseDto);
		setParam(index++, dto.getPfmBankBaseId());
		setParam(index++, dto.getBankCode());
		setParam(index++, dto.getBankName());
		setParam(index++, dto.getBankNameKana());
		setParam(index++, dto.getInactivateFlag());
		setCommonParams(baseDto, isInsert);
	}
	
	/**
	 * DTOインスタンスのキャストを行う。<br>
	 * @param baseDto 対象DTO
	 * @return キャストされたDTO
	 */
	protected BankBaseDtoInterface castDto(BaseDtoInterface baseDto) {
		return (BankBaseDtoInterface)baseDto;
	}
	
	@Override
	public List<BankBaseDtoInterface> searchBankBaseInfo(String value) throws MospException {
		try {
			index = 1;
			// SQL作成準備(SELECT文追加)
			StringBuffer sb = new StringBuffer(getSelectQuery(getClass()));
			sb.append(where());
			sb.append(deleteFlagOff());
			// 検索条件SQL追加
			sb.append(and());
			sb.append(leftParenthesis());
			sb.append(like(COL_BANK_NAME));
			sb.append(or());
			sb.append(like(COL_BANK_NAME_KANA));
			sb.append(or());
			sb.append(like(COL_BANK_CODE));
			sb.append(rightParenthesis());
			// パラメータインデックス準備
			prepareStatement(sb.toString());
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
	public BankBaseDtoInterface findForKey(String code) throws MospException {
		try {
			index = 1;
			// SQL作成準備(SELECT文追加)
			StringBuffer sb = new StringBuffer(getSelectQuery(getClass()));
			sb.append(where());
			sb.append(deleteFlagOff());
			// 検索条件SQL追加
			sb.append(and());
			sb.append(equal(COL_BANK_CODE));
			prepareStatement(sb.toString());
			setParam(index++, code);
			executeQuery();
			BankBaseDtoInterface dto = null;
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
