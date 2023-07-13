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
package jp.mosp.platform.bean.system.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.human.HumanReferenceBeanInterface;
import jp.mosp.platform.bean.system.IcCardReferenceBeanInterface;
import jp.mosp.platform.bean.system.IcCardSearchBeanInterface;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.dto.system.IcCardDtoInterface;

/**
 * ICカードマスタ検索クラス。
 */
public class IcCardSearchBean extends PlatformBean implements IcCardSearchBeanInterface {
	
	/**
	 * 人事検索クラス。
	 */
	protected HumanReferenceBeanInterface	humanRefer;
	
	/**
	 * ICカード参照クラス。
	 */
	protected IcCardReferenceBeanInterface	icCardRefer;
	
	/**
	 * 有効日。
	 */
	private Date							activateDate;
	
	/**
	 * 社員コード。
	 */
	private String							employeeCode;
	
	/**
	 * 氏名。
	 */
	private String							employeeName;
	
	/**
	 * カードID
	 */
	private String							cardId;
	
	/**
	 * 有効無効フラグ。
	 */
	private String							inactivateFlag;
	
	
	/**
	 * コンストラクタ。
	 */
	public IcCardSearchBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// 取得
		humanRefer = createBeanInstance(HumanReferenceBeanInterface.class);
		icCardRefer = createBeanInstance(IcCardReferenceBeanInterface.class);
	}
	
	@Override
	public List<IcCardDtoInterface> getSearchList() throws MospException {
		// 検索結果リスト準備
		List<IcCardDtoInterface> list = new ArrayList<IcCardDtoInterface>();
		// 有効日のみの場合
		// ICカード情報取得
		List<IcCardDtoInterface> cardList = icCardRefer.findForActivateDate(activateDate);
		// ICカード毎に処理
		for (IcCardDtoInterface cardDto : cardList) {
			// 検索結果が見つかった場合
			if (isIcCardIdMatch(cardDto)) {
				// 検索結果リストに追加
				list.add(cardDto);
			}
		}
		// リストを返す
		return list;
	}
	
	/**
	 * ICカード情報が検索条件に合致しているか確認。
	 * @param icCardDto ICカード情報
	 * @return true:検索条件合致、false:検索条件合致していない
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected boolean isIcCardIdMatch(IcCardDtoInterface icCardDto) throws MospException {
		// ICカードID検索条件の場合
		if (cardId.isEmpty() == false) {
			if (icCardDto == null) {
				return false;
			}
			// 前方一致検索
			if (isForwardMatch(cardId, icCardDto.getIcCardId()) == false) {
				return false;
			}
		}
		// 無効/有効フラグ確認
		if (inactivateFlag.isEmpty() == false) {
			if (inactivateFlag.equals(String.valueOf(icCardDto.getInactivateFlag())) == false) {
				return false;
			}
		}
		// 社員コード、社員名検索条件がない場合
		if (employeeCode.isEmpty() && employeeName.isEmpty()) {
			return true;
		}
		// 人事マスタ情報取得
		HumanDtoInterface humanDto = humanRefer.getHumanInfo(icCardDto.getPersonalId(), activateDate);
		if (humanDto == null) {
			return false;
		}
		// 社員コード検索条件の場合
		if (employeeCode.isEmpty() == false) {
			// 前方一致検索検索
			if (isForwardMatch(employeeCode, humanDto.getEmployeeCode()) == false) {
				return false;
			}
		}
		// 社員名検索条件の場合
		if (employeeName.isEmpty() == false) {
			// 社員名検索
			if (isHumanNameMatch(employeeName, humanDto.getFirstName(), humanDto.getLastName()) == false) {
				return false;
			}
		}
		return true;
		
	}
	
	@Override
	public void setActivateDate(Date activateDate) {
		this.activateDate = getDateClone(activateDate);
	}
	
	@Override
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}
	
	@Override
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	
	@Override
	public void setInactivateFlag(String inactivateFlag) {
		this.inactivateFlag = inactivateFlag;
	}
	
	@Override
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	
}
