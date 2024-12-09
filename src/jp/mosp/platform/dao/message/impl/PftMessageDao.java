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
package jp.mosp.platform.dao.message.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.platform.base.PlatformDao;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dao.message.MessageDaoInterface;
import jp.mosp.platform.dao.system.UserMasterDaoInterface;
import jp.mosp.platform.dto.message.MessageDtoInterface;
import jp.mosp.platform.dto.message.impl.PftMessageDto;

/**
 * メッセージテーブルDAOクラス。<br>
 */
public class PftMessageDao extends PlatformDao implements MessageDaoInterface {
	
	/**
	 * メッセージテーブル
	 */
	public static final String	TABLE							= "pft_message";
	
	/**
	 * レコード識別ID
	 */
	public static final String	COL_PFT_MESSAGE_ID				= "pft_message_id";
	
	/**
	 * メッセージNo
	 */
	public static final String	COL_MESSAGE_NO					= "message_no";
	
	/**
	 *  公開開始日
	 */
	public static final String	COL_START_DATE					= "start_date";
	
	/**
	 *  公開終了日
	 */
	public static final String	COL_END_DATE					= "end_date";
	
	/**
	 * メッセージ区分
	 */
	public static final String	COL_MESSAGE_TYPE				= "message_type";
	
	/**
	 * 重要度
	 */
	public static final String	COL_MESSAGE_IMPORTANCE			= "message_importance";
	
	/**
	 * メッセージタイトル
	 */
	public static final String	COL_MESSAGE_TITLE				= "message_title";
	
	/**
	 * メッセージ本文
	 */
	public static final String	COL_MESSAGE_BODY				= "message_body";
	
	/**
	 * メッセージ適用範囲区分
	 */
	public static final String	COL_APPLICATION_TYPE			= "application_type";
	
	/**
	 * 勤務地コード
	 */
	public static final String	COL_WORK_PLACE_CODE				= "work_place_code";
	
	/**
	 * 雇用契約コード
	 */
	public static final String	COL_EMPLOYMENT_CONTRACT_CODE	= "employment_contract_code";
	
	/**
	 * 所属コード
	 */
	public static final String	COL_SECTION_CODE				= "section_code";
	
	/**
	 * 職位コード
	 */
	public static final String	COL_POSITION_CODE				= "position_code";
	/**
	 * 個人ID
	 */
	public static final String	COL_PERSONAL_IDS				= "personal_ids";
	
	/**
	 * 無効フラグ
	 */
	public static final String	COL_INACTIVATE_FLAG				= "inactivate_flag";
	
	/**
	 * レコード識別ID
	 */
	public static final String	KEY_1							= COL_PFT_MESSAGE_ID;
	
	/**
	 * シーケンス。
	 */
	public static final String	SEQUENCE						= "pft_message_message_no_seq";
	
	
	/**
	 * コンストラクタ。
	 */
	public PftMessageDao() {
		// 処理無し
	}
	
	@Override
	public void initDao() {
		// 処理無し
	}
	
	@Override
	public BaseDto mapping() throws MospException {
		PftMessageDto dto = new PftMessageDto();
		dto.setApplicationType(getInt(COL_APPLICATION_TYPE));
		dto.setEmploymentContractCode(getString(COL_EMPLOYMENT_CONTRACT_CODE));
		dto.setEndDate(getDate(COL_END_DATE));
		dto.setInactivateFlag(getInt(COL_INACTIVATE_FLAG));
		dto.setMessageBody(getString(COL_MESSAGE_BODY));
		dto.setMessageImportance(getInt(COL_MESSAGE_IMPORTANCE));
		dto.setMessageNo(getString(COL_MESSAGE_NO));
		dto.setMessageTitle(getString(COL_MESSAGE_TITLE));
		dto.setMessageType(getInt(COL_MESSAGE_TYPE));
		dto.setPersonalId(getString(COL_PERSONAL_IDS));
		dto.setPftMessageId(getLong(COL_PFT_MESSAGE_ID));
		dto.setPositionCode(getString(COL_POSITION_CODE));
		dto.setSectionCode(getString(COL_SECTION_CODE));
		dto.setStartDate(getDate(COL_START_DATE));
		dto.setWorkPlaceCode(getString(COL_WORK_PLACE_CODE));
		mappingCommonInfo(dto);
		return dto;
	}
	
	@Override
	public List<MessageDtoInterface> mappingAll() throws MospException {
		List<MessageDtoInterface> all = new ArrayList<MessageDtoInterface>();
		while (next()) {
			all.add((MessageDtoInterface)mapping());
		}
		return all;
	}
	
	@Override
	public MessageDtoInterface findForKey(String messageNo) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_MESSAGE_NO));
			prepareStatement(sb.toString());
			setParam(index++, messageNo);
			executeQuery();
			MessageDtoInterface dto = null;
			if (next()) {
				dto = (MessageDtoInterface)mapping();
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
	public long getMaxMessageNo() throws MospException {
		return nextValue(SEQUENCE);
	}
	
	@Override
	public List<MessageDtoInterface> findForMaster(String workPlaceCode, String employmentContractCode,
			String sectionCode, String positionCode, Date targetDate) throws MospException {
		try {
			// パラメータインデックス準備
			index = 1;
			// SQL作成準備
			StringBuffer sb = getSelectQuery(getClass());
			// WHERE部追加
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_INACTIVATE_FLAG, MospConst.INACTIVATE_FLAG_OFF));
			// 検索条件SQL追加(対象日)
			sb.append(and());
			sb.append(lessEqual(COL_START_DATE));
			sb.append(and());
			sb.append(greaterEqual(COL_END_DATE));
			// 検索条件SQL追加(マスタ)
			sb.append(and());
			sb.append(equal(COL_WORK_PLACE_CODE));
			sb.append(and());
			sb.append(equal(COL_EMPLOYMENT_CONTRACT_CODE));
			sb.append(and());
			sb.append(equal(COL_SECTION_CODE));
			sb.append(and());
			sb.append(equal(COL_POSITION_CODE));
			// 検索条件SQL追加(メッセージ適用範囲区分)
			sb.append(and());
			sb.append(equal(COL_APPLICATION_TYPE, Integer.parseInt(PlatformConst.APPLICATION_TYPE_MASTER)));
			// ステートメント生成
			prepareStatement(sb.toString());
			// パラメータ設定
			setParam(index++, targetDate);
			setParam(index++, targetDate);
			setParam(index++, workPlaceCode);
			setParam(index++, employmentContractCode);
			setParam(index++, sectionCode);
			setParam(index++, positionCode);
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
	public List<MessageDtoInterface> findForPersonalId(String personalId, Date targetDate) throws MospException {
		try {
			// パラメータインデックス準備
			index = 1;
			// SQL作成準備
			StringBuffer sb = getSelectQuery(getClass());
			// WHERE部追加
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_INACTIVATE_FLAG, MospConst.INACTIVATE_FLAG_OFF));
			// 検索条件SQL追加(対象日)
			sb.append(and());
			sb.append(lessEqual(COL_START_DATE));
			sb.append(and());
			sb.append(greaterEqual(COL_END_DATE));
			// 検索条件SQL追加(個人ID)
			sb.append(and());
			sb.append(like(COL_PERSONAL_IDS));
			// 検索条件SQL追加(メッセージ適用範囲区分)
			sb.append(and());
			sb.append(equal(COL_APPLICATION_TYPE, Integer.parseInt(PlatformConst.APPLICATION_TYPE_PERSON)));
			// ステートメント生成
			prepareStatement(sb.toString());
			// パラメータ設定
			setParam(index++, targetDate);
			setParam(index++, targetDate);
			setParam(index++, containsParam(personalId));
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
	public Map<String, Object> getParamsMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		return map;
	}
	
	/**
	 * 以下のパラメータは、必須とする。<br>
	 * <ul><li>
	 * {@link MessageDaoInterface#SEARCH_START_DATE}
	 * </li><li>
	 * {@link MessageDaoInterface#SEARCH_END_DATE}
	 * </li></ul>
	 */
	@Override
	public List<MessageDtoInterface> findForSearch(Map<String, Object> param) throws MospException {
		try {
			// 検索条件準備
			String messageNo = getSearchParam(param, SEARCH_MESSAGE_NO);
			Date startDate = (Date)param.get(SEARCH_START_DATE);
			Date endDate = (Date)param.get(SEARCH_END_DATE);
			Integer messageType = (Integer)param.get(SEARCH_MESSAGE_TYPE);
			Integer messageImportance = (Integer)param.get(SEARCH_MESSAGE_IMPORTANCE);
			String messageTitle = getSearchParam(param, SEARCH_MESSAGE_TITLE);
			String employeeName = getSearchParam(param, SEARCH_EMPLOYEE_NAME);
			Integer inactivateFlag = (Integer)param.get(SEARCH_INACTIVATE_FLAG);
			// パラメータインデックス準備
			index = 1;
			// SQL作成準備
			StringBuffer sb = getSelectQuery(getClass());
			// WHERE部追加
			sb.append(where());
			sb.append(deleteFlagOff());
			// 検索条件SQL追加(公開開始日及び公開終了日)
			sb.append(and());
			sb.append(lessEqual(COL_START_DATE));
			sb.append(and());
			sb.append(greaterEqual(COL_END_DATE));
			// 検索条件SQL追加(メッセージID)
			sb.append(and());
			sb.append(like(COL_MESSAGE_NO));
			// 検索条件SQL追加(メッセージ区分)
			if (messageType != null) {
				sb.append(and());
				sb.append(equal(COL_MESSAGE_TYPE));
			}
			// 検索条件SQL追加(メッセージ重要度)
			if (messageImportance != null) {
				sb.append(and());
				sb.append(equal(COL_MESSAGE_IMPORTANCE));
			}
			// 検索条件SQL追加(社員名)
			if (employeeName.isEmpty() == false) {
				// ユーザマスタDAO準備(サブクエリ取得用)
				UserMasterDaoInterface userDao = (UserMasterDaoInterface)loadDao(UserMasterDaoInterface.class);
				// 検索条件SQL追加(社員名)
				sb.append(and());
				sb.append(colInsertUser);
				sb.append(in());
				sb.append(leftParenthesis());
				sb.append(userDao.getQueryForEmployeeName());
				sb.append(rightParenthesis());
			}
			// 検索条件SQL追加(メッセージタイトル)
			sb.append(and());
			sb.append(like(COL_MESSAGE_TITLE));
			// 検索条件SQL追加(無効フラグ)
			if (inactivateFlag != null) {
				sb.append(and());
				sb.append(equal(COL_INACTIVATE_FLAG));
			}
			// ステートメント生成
			prepareStatement(sb.toString());
			// 検索条件設定(公開開始日及び公開終了日)
			setParam(index++, endDate);
			setParam(index++, startDate);
			// 検索条件設定(メッセージNo) 
			setParam(index++, startWithParam(messageNo));
			// 検索条件設定(メッセージ区分)
			if (messageType != null) {
				setParam(index++, messageType.intValue());
			}
			// 検索条件設定(メッセージ重要度)
			if (messageImportance != null) {
				setParam(index++, messageImportance.intValue());
			}
			// 検索条件設定(登録者)
			if (employeeName.isEmpty() == false) {
				setParam(index++, endDate);
				setParam(index++, endDate);
				setParam(index++, containsParam(employeeName));
				setParam(index++, containsParam(employeeName));
				setParam(index++, containsParam(employeeName));
				setParam(index++, containsParam(employeeName));
			}
			// 検索条件設定(メッセージタイトル) 
			setParam(index++, containsParam(messageTitle));
			// 検索条件設定(無効フラグ)
			if (inactivateFlag != null) {
				setParam(index++, inactivateFlag.intValue());
			}
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
	public int update(BaseDtoInterface baseDto) throws MospException {
		try {
			index = 1;
			prepareStatement(getUpdateQuery(getClass()));
			setParams(baseDto, false);
			MessageDtoInterface dto = castDto(baseDto);
			setParam(index++, dto.getPftMessageId());
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
			MessageDtoInterface dto = castDto(baseDto);
			setParam(index++, dto.getPftMessageId());
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
		MessageDtoInterface dto = castDto(baseDto);
		setParam(index++, dto.getPftMessageId());
		setParam(index++, dto.getMessageNo());
		setParam(index++, dto.getStartDate());
		setParam(index++, dto.getEndDate());
		setParam(index++, dto.getMessageType());
		setParam(index++, dto.getMessageImportance());
		setParam(index++, dto.getMessageTitle());
		setParam(index++, dto.getMessageBody());
		setParam(index++, dto.getApplicationType());
		setParam(index++, dto.getWorkPlaceCode());
		setParam(index++, dto.getEmploymentContractCode());
		setParam(index++, dto.getSectionCode());
		setParam(index++, dto.getPositionCode());
		setParam(index++, dto.getPersonalId());
		setParam(index++, dto.getInactivateFlag());
		setCommonParams(baseDto, isInsert);
	}
	
	/**
	 * DTOのインスタンスのキャストを行う。<br>
	 * @param baseDto 対象DTO
	 * @return キャストされたDTO
	 */
	protected MessageDtoInterface castDto(BaseDtoInterface baseDto) {
		return (MessageDtoInterface)baseDto;
	}
	
}
