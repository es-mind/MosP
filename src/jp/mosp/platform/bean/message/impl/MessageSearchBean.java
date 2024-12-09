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
package jp.mosp.platform.bean.message.impl;

import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.message.MessageSearchBeanInterface;
import jp.mosp.platform.dao.message.MessageDaoInterface;
import jp.mosp.platform.dao.workflow.SubApproverDaoInterface;
import jp.mosp.platform.dto.message.MessageDtoInterface;
import jp.mosp.platform.utils.MonthUtility;

/**
 * メッセージテーブル検索クラス。
 *
 */
public class MessageSearchBean extends PlatformBean implements MessageSearchBeanInterface {
	
	/**
	 * メッセージテーブルDAO。
	 */
	private MessageDaoInterface	dao;
	
	/**
	 * メッセージNo。
	 */
	private String				messageNo;
	
	/**
	 * 対象年。
	 */
	private int					targetYear;
	
	/**
	 * 対象月。
	 */
	private int					targetMonth;
	
	/**
	 * メッセージ区分。
	 */
	private String				messageType;
	
	/**
	 * 重要度。
	 */
	private String				messageImportance;
	
	/**
	 * メッセージタイトル。
	 */
	private String				messageTitle;
	
	/**
	 * 登録者氏名。
	 */
	private String				employeeName;
	
	/**
	 * 有効無効フラグ。
	 */
	private String				inactivateFlag;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public MessageSearchBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		dao = createDaoInstance(MessageDaoInterface.class);
	}
	
	@Override
	public List<MessageDtoInterface> getSearchList() throws MospException {
		// Mapに検索条件を設定
		Map<String, Object> param = dao.getParamsMap();
		param.put(MessageDaoInterface.SEARCH_MESSAGE_NO, messageNo);
		param.put(SubApproverDaoInterface.SEARCH_START_DATE,
				MonthUtility.getYearMonthTermFirstDate(targetYear, targetMonth, mospParams));
		param.put(SubApproverDaoInterface.SEARCH_END_DATE,
				MonthUtility.getYearMonthTermLastDate(targetYear, targetMonth, mospParams));
		param.put(MessageDaoInterface.SEARCH_MESSAGE_TYPE, getInteger(messageType));
		param.put(MessageDaoInterface.SEARCH_MESSAGE_IMPORTANCE, getInteger(messageImportance));
		param.put(MessageDaoInterface.SEARCH_MESSAGE_TITLE, messageTitle);
		param.put(MessageDaoInterface.SEARCH_EMPLOYEE_NAME, employeeName);
		param.put(MessageDaoInterface.SEARCH_INACTIVATE_FLAG, getInteger(inactivateFlag));
		// 検索
		return dao.findForSearch(param);
	}
	
	@Override
	public void setMessageNo(String messageNo) {
		this.messageNo = messageNo;
	}
	
	@Override
	public void setTargetYear(int targetYear) {
		this.targetYear = targetYear;
	}
	
	@Override
	public void setTargetMonth(int targetMonth) {
		this.targetMonth = targetMonth;
	}
	
	@Override
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	
	@Override
	public void setMessageImportance(String messageImportance) {
		this.messageImportance = messageImportance;
	}
	
	@Override
	public void setMessageTitle(String messageTitle) {
		this.messageTitle = messageTitle;
	}
	
	@Override
	public void setInactivateFlag(String inactivateFlag) {
		this.inactivateFlag = inactivateFlag;
	}
	
	@Override
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	
}
