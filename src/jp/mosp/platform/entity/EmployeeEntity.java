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
package jp.mosp.platform.entity;

import java.util.Date;
import java.util.List;

import jp.mosp.framework.utils.DateUtility;
import jp.mosp.platform.dto.human.ConcurrentDtoInterface;
import jp.mosp.platform.dto.human.EntranceDtoInterface;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.dto.human.RetirementDtoInterface;
import jp.mosp.platform.dto.human.SuspensionDtoInterface;
import jp.mosp.platform.human.utils.HumanUtility;

/**
 * 社員エンティティクラス。<br>
 */
public class EmployeeEntity implements EmployeeEntityInterface {
	
	/**
	 * 個人ID。<br>
	 */
	protected String						personalId;
	
	/**
	 * 人事情報(履歴)。<br>
	 */
	protected List<HumanDtoInterface>		humanList;
	
	/**
	 * 人事入社情報。<br>
	 */
	protected EntranceDtoInterface			entranceDto;
	
	/**
	 * 人事退職情報。<br>
	 */
	protected RetirementDtoInterface		retirementDto;
	
	/**
	 * 人事休職情報。<br>
	 */
	protected List<SuspensionDtoInterface>	suspensionList;
	
	/**
	 * 人事兼務情報。<br>
	 */
	protected List<ConcurrentDtoInterface>	concurrentList;
	
	
	/**
	 * コンストラクタ。<br>
	 * @param personalId 個人ID
	 */
	public EmployeeEntity(String personalId) {
		// 個人IDを設定
		this.personalId = personalId;
	}
	
	@Override
	public String getPersonalId() {
		return personalId;
	}
	
	@Override
	public List<HumanDtoInterface> getHumanList() {
		return humanList;
	}
	
	@Override
	public EntranceDtoInterface getEntranceDto() {
		return entranceDto;
	}
	
	@Override
	public List<ConcurrentDtoInterface> getConcurrentList() {
		return concurrentList;
	}
	
	@Override
	public List<SuspensionDtoInterface> getSuspensionList() {
		return suspensionList;
	}
	
	@Override
	public RetirementDtoInterface getRetirementDto() {
		return retirementDto;
	}
	
	@Override
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
	
	@Override
	public void setHumanList(List<HumanDtoInterface> humanList) {
		this.humanList = humanList;
	}
	
	@Override
	public void setEntranceDto(EntranceDtoInterface entranceDto) {
		this.entranceDto = entranceDto;
	}
	
	@Override
	public void setConcurrentList(List<ConcurrentDtoInterface> concurrentList) {
		this.concurrentList = concurrentList;
	}
	
	@Override
	public void setSuspensionList(List<SuspensionDtoInterface> suspensionList) {
		this.suspensionList = suspensionList;
	}
	
	@Override
	public void setRetirementDto(RetirementDtoInterface retirementDto) {
		this.retirementDto = retirementDto;
	}
	
	/**
	 * 対象日における人事情報を取得する。<br>
	 * <br>
	 * 対象日における有効な人事情報が存在しない場合は、nullを返す。<br>
	 * <br>
	 * @param targetDate 対象日
	 * @return 人事情報
	 */
	public HumanDtoInterface getHumenDto(Date targetDate) {
		// 人事情報毎に処理(リストは有効日の昇順)
		for (int i = humanList.size(); i > 0; i--) {
			// 降順で人事情報を取得
			HumanDtoInterface dto = humanList.get(i - 1);
			// 対象日が有効日よりも前である場合
			if (targetDate.before(dto.getActivateDate())) {
				continue;
			}
			return dto;
		}
		return null;
	}
	
	/**
	 * 対象日における人事情報が存在するかを確認する。<br>
	 * <br>
	 * @param targetDate 対象日
	 * @return 確認結果(true：存在する、false：存在しない)
	 */
	public boolean isHumenDtoExist(Date targetDate) {
		// 人事情報を取得
		HumanDtoInterface dto = getHumenDto(targetDate);
		//対象日における人事情報が存在するかを確認
		return dto != null;
	}
	
	/**
	 * 対象日における社員コードを取得する。<br>
	 * <br>
	 * 人事情報の取得に失敗した場合は、nullを返す。<br>
	 * <br>
	 * @param targetDate 対象日
	 * @return 社員コード
	 */
	public String getEmployeeCode(Date targetDate) {
		// 人事情報を取得
		HumanDtoInterface dto = getHumenDto(targetDate);
		// 社員コードを取得
		return dto == null ? "" : dto.getEmployeeCode();
	}
	
	/**
	 * 退職日を取得する。<br>
	 * <br>
	 * 人事退職情報が存在しない場合は、nullを返す。<br>
	 * <br>
	 * @return 退職日
	 */
	public Date getRetirementDate() {
		return HumanUtility.getRetireDate(retirementDto);
	}
	
	/**
	 * 対象日における人事休職情報を取得する。<br>
	 * <br>
	 * 対象日が含まれる人事休職情報が取得できない場合は、nullを返す。<br>
	 * <br>
	 * @param targetDate 対象日
	 * @return 人事休職情報
	 */
	public SuspensionDtoInterface getSuspensionDto(Date targetDate) {
		// 人事休職情報毎に処理
		for (SuspensionDtoInterface dto : suspensionList) {
			// 終了日を取得
			Date endDate = dto.getEndDate();
			// 終了日が設定されていない場合
			if (endDate == null) {
				// 終了予定日を取得
				endDate = dto.getScheduleEndDate();
			}
			// 対象日が休職期間に含まれる場合
			if (DateUtility.isTermContain(targetDate, dto.getStartDate(), endDate)) {
				return dto;
			}
		}
		return null;
	}
	
}
