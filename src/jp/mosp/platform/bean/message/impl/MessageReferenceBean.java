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

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.message.MessageReferenceBeanInterface;
import jp.mosp.platform.dao.human.HumanDaoInterface;
import jp.mosp.platform.dao.message.MessageDaoInterface;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.dto.message.MessageDtoInterface;

/**
 * メッセージテーブル参照クラス。
 */
public class MessageReferenceBean extends PlatformBean implements MessageReferenceBeanInterface {
	
	/**
	 * メッセージテーブルDAO。
	 */
	private MessageDaoInterface dao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public MessageReferenceBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		dao = createDaoInstance(MessageDaoInterface.class);
	}
	
	@Override
	public MessageDtoInterface findForKey(String messageNo) throws MospException {
		return dao.findForKey(messageNo);
	}
	
	@Override
	public List<MessageDtoInterface> getMessageList(String personalId, Date targetDate) throws MospException {
		// 個人IDでメッセージリストを取得
		List<MessageDtoInterface> list = dao.findForPersonalId(personalId, targetDate);
		// 人事マスタDAO準備
		HumanDaoInterface humanDao = createDaoInstance(HumanDaoInterface.class);
		// 人事情報取得
		HumanDtoInterface humanDto = humanDao.findForInfo(personalId, targetDate);
		// マスタ情報取得
		String place = humanDto.getWorkPlaceCode();
		String contr = humanDto.getEmploymentContractCode();
		String secti = humanDto.getSectionCode();
		String posit = humanDto.getPositionCode();
		String blank = "";
		// マスタの組合でメッセージリストを取得し追加
		addMessage(list, dao.findForMaster(place, contr, secti, posit, targetDate));
		addMessage(list, dao.findForMaster(blank, contr, secti, posit, targetDate));
		addMessage(list, dao.findForMaster(place, blank, secti, posit, targetDate));
		addMessage(list, dao.findForMaster(place, contr, blank, posit, targetDate));
		addMessage(list, dao.findForMaster(place, contr, secti, blank, targetDate));
		addMessage(list, dao.findForMaster(blank, blank, secti, posit, targetDate));
		addMessage(list, dao.findForMaster(blank, contr, blank, posit, targetDate));
		addMessage(list, dao.findForMaster(blank, contr, secti, blank, targetDate));
		addMessage(list, dao.findForMaster(place, blank, blank, posit, targetDate));
		addMessage(list, dao.findForMaster(place, blank, secti, blank, targetDate));
		addMessage(list, dao.findForMaster(place, contr, blank, blank, targetDate));
		addMessage(list, dao.findForMaster(blank, blank, blank, posit, targetDate));
		addMessage(list, dao.findForMaster(blank, blank, secti, blank, targetDate));
		addMessage(list, dao.findForMaster(blank, contr, blank, blank, targetDate));
		addMessage(list, dao.findForMaster(place, blank, blank, blank, targetDate));
		addMessage(list, dao.findForMaster(blank, blank, blank, blank, targetDate));
		return list;
	}
	
	/**
	 * メッセージを追加する。
	 * @param toList   追加先リスト
	 * @param fromList 追加リスト
	 */
	protected void addMessage(List<MessageDtoInterface> toList, List<MessageDtoInterface> fromList) {
		// 追加リストのメッセージ毎に確認
		fromLoop: for (MessageDtoInterface fromDto : fromList) {
			// 追加先リストのメッセージ取得
			for (MessageDtoInterface toDto : toList) {
				// メッセージNo確認
				if (fromDto.getMessageNo().equals(toDto.getMessageNo())) {
					// 既に存在している場合は追加しない
					continue fromLoop;
				}
			}
			// メッセージ追加
			toList.add(fromDto);
		}
	}
}
