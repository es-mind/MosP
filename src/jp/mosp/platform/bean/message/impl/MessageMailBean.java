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
package jp.mosp.platform.bean.message.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.human.HumanSearchBeanInterface;
import jp.mosp.platform.bean.mail.MailStartTlsBeanInterface;
import jp.mosp.platform.bean.message.MessageMailBeanInterface;
import jp.mosp.platform.bean.message.MessageReferenceBeanInterface;
import jp.mosp.platform.bean.system.PlatformMasterBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.dto.message.MessageDtoInterface;

/**
 * メール送信処理(メッセージ用)。<br>
 */
public class MessageMailBean extends PlatformBean implements MessageMailBeanInterface {
	
	/**
	 * メール送信処理。<br>
	 */
	protected MailStartTlsBeanInterface		mail;
	
	/**
	 * メッセージ参照処理。<br>
	 */
	protected MessageReferenceBeanInterface	refer;
	
	/**
	 * 人事情報参照処理。<br>
	 */
	protected HumanSearchBeanInterface		humanSearch;
	
	/**
	 * プラットフォームマスタ参照処理。<br>
	 */
	protected PlatformMasterBeanInterface	platformMaster;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public MessageMailBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// Beanを準備
		mail = createBeanInstance(MailStartTlsBeanInterface.class);
		refer = createBeanInstance(MessageReferenceBeanInterface.class);
		humanSearch = createBeanInstance(HumanSearchBeanInterface.class);
		platformMaster = createBeanInstance(PlatformMasterBeanInterface.class);
	}
	
	@Override
	public int send(String messageNo) throws MospException {
		// メッセージ情報を取得
		MessageDtoInterface dto = refer.findForKey(messageNo);
		// recipients 受信者メールアドレスリストを取得
		List<String> recipients = getMailAddresses(dto);
		// メールを送信
		mail.send(recipients, dto.getMessageTitle(), dto.getMessageBody());
		// 送信件数(受信者メールアドレスリストの数)を取得
		return recipients.size();
	}
	
	/**
	 * メールアドレスリストを取得する。<br>
	 * @param dto メッセージ情報
	 * @return メールアドレスリスト
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected List<String> getMailAddresses(MessageDtoInterface dto) throws MospException {
		// メールアドレスリストを準備
		List<String> addresses = new ArrayList<String>();
		// 対象日(システム日付)を取得
		Date targetDate = getSystemDate();
		// 適用範囲を取得
		int applicationType = dto.getApplicationType();
		// 適用範囲がマスタ組み合わせ指定である場合
		if (applicationType == MospUtility.getInt(PlatformConst.APPLICATION_TYPE_MASTER)) {
			// 勤務地・雇用契約・所属・職位
			// 検索条件設定
			humanSearch.setTargetDate(targetDate);
			humanSearch.setWorkPlaceCode(dto.getWorkPlaceCode());
			humanSearch.setSectionCode(dto.getSectionCode());
			humanSearch.setPositionCode(dto.getPositionCode());
			humanSearch.setEmploymentContractCode(dto.getEmploymentContractCode());
			// 検索条件設定(状態)
			humanSearch.setStateType(PlatformConst.EMPLOYEE_STATE_PRESENCE);
			// 検索条件設定(下位所属要否)
			humanSearch.setNeedLowerSection(true);
			// 検索条件設定(兼務要否)
			humanSearch.setNeedConcurrent(true);
			// 検索条件設定(操作区分)
			humanSearch.setOperationType(MospConst.OPERATION_TYPE_REFER);
			// 人事情報検索
			List<HumanDtoInterface> list = humanSearch.search();
			// 人事情報毎に処理
			for (HumanDtoInterface humanDto : list) {
				// メールアドレスを取得
				addresses.add(platformMaster.getMailAddress(humanDto.getPersonalId()));
			}
		}
		// 適用範囲が個人指定である場合
		if (applicationType == MospUtility.getInt(PlatformConst.APPLICATION_TYPE_PERSON)) {
			// 個人ID配列を取得
			String[] aryPersonalId = split(dto.getPersonalId(), SEPARATOR_DATA);
			// 個人ID毎に処理
			for (String personalId : aryPersonalId) {
				addresses.add(platformMaster.getMailAddress(platformMaster.getMailAddress(personalId)));
			}
		}
		// メールアドレスリストを取得
		return addresses;
	}
	
}
