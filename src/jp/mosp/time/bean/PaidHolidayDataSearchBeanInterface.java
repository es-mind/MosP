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
package jp.mosp.time.bean;

import java.util.Date;
import java.util.List;
import java.util.Set;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.PaidHolidayDataGrantListDtoInterface;

/**
 * 有給休暇データ検索インターフェース。
 */
public interface PaidHolidayDataSearchBeanInterface extends BaseBeanInterface {
	
	/**
	 * 検索条件から有給休暇データリストを取得する。<br>
	 * @return 有給休暇データリスト
	 * @throws MospException インスタンスの取得、SQLの作成及び実行に失敗した場合
	 */
	List<PaidHolidayDataGrantListDtoInterface> getSearchList() throws MospException;
	
	/**
	 * 開始日を取得する。<br>
	 * @param personalId 個人ID
	 * @param grantTimes 有給休暇付与回数
	 * @param grantDate 付与日
	 * @return 開始日
	 * @throws MospException インスタンスの取得、SQLの作成及び実行に失敗した場合
	 */
	Date getStartDate(String personalId, int grantTimes, Date grantDate) throws MospException;
	
	/**
	 * 終了日を取得する。
	 * @param startDate 開始日
	 * @param grantTimes 有給休暇付与回数
	 * @return 終了日
	 */
	Date getEndDate(Date startDate, int grantTimes);
	
	/**
	 * @param activateDate セットする 有効日。
	 */
	void setActivateDate(Date activateDate);
	
	/**
	 * @param entranceFromDate セットする 入社日期間自。
	 */
	void setEntranceFromDate(Date entranceFromDate);
	
	/**
	 * @param entranceToDate セットする 入社日期間至。
	 */
	void setEntranceToDate(Date entranceToDate);
	
	/**
	 * @param employeeCode セットする 社員コード。
	 */
	void setEmployeeCode(String employeeCode);
	
	/**
	 * @param employeeName セットする 社員名。
	 */
	void setEmployeeName(String employeeName);
	
	/**
	 * @param workPlaceCode セットする 勤務地コード。
	 */
	void setWorkPlaceCode(String workPlaceCode);
	
	/**
	 * @param employmentCode セットする 雇用契約コード。
	 */
	void setEmploymentCode(String employmentCode);
	
	/**
	 * @param sectionCode セットする 所属コード。
	 */
	void setSectionCode(String sectionCode);
	
	/**
	 * @param positionCode セットする 職位コード。
	 */
	void setPositionCode(String positionCode);
	
	/**
	 * @param paidHolidayCode セットする 有給休暇設定コード。
	 */
	void setPaidHolidayCode(String paidHolidayCode);
	
	/**
	 * @param grant セットする 付与状態。
	 */
	void setGrant(String grant);
	
	/**
	 * @param calcAttendanceRate セットする 出勤率計算。
	 */
	void setCalcAttendanceRate(boolean calcAttendanceRate);
	
	/**
	 * @param personalIdSet セットする個人IDセット。
	 */
	void setPersonalIdSet(Set<String> personalIdSet);
	
}
