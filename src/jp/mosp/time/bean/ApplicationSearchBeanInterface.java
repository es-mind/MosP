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
package jp.mosp.time.bean;

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.ApplicationDtoInterface;

/**
 * 設定適用管理検索インターフェース。
 */
public interface ApplicationSearchBeanInterface extends BaseBeanInterface {
	
	/**
	 * 検索条件から設定適用リストを取得する。<br><br>
	 * {@link #setActivateDate(Date)}等で設定された条件で、検索を行う。
	 * @return 設定適用リスト
	 * @throws MospException インスタンスの取得、SQLの作成及び実行に失敗した場合
	 */
	List<ApplicationDtoInterface> getSearchList() throws MospException;
	
	/**
	 * @param activateDate セットする 有効日。
	 */
	void setActivateDate(Date activateDate);
	
	/**
	 * @param applicationCode セットする 設定適用コード。
	 */
	void setApplicationCode(String applicationCode);
	
	/**
	 * @param applicationType セットする 設定適用区分。
	 */
	void setApplicationType(String applicationType);
	
	/**
	 * @param applicationName セットする 設定適用名称。
	 */
	void setApplicationName(String applicationName);
	
	/**
	 * @param applicationAbbr セットする 設定適用略称。
	 */
	void setApplicationAbbr(String applicationAbbr);
	
	/**
	 * @param workSettingCode セットする 勤怠設定コード。
	 */
	void setWorkSettingCode(String workSettingCode);
	
	/**
	 * @param scheduleCode セットする カレンダコード。
	 */
	void setScheduleCode(String scheduleCode);
	
	/**
	 * @param paidHolidayCode セットする 有休コード。
	 */
	void setPaidHolidayCode(String paidHolidayCode);
	
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
	 * @param employeeCode セットする 社員コード。
	 */
	void setEmployeeCode(String employeeCode);
	
	/**
	 * @param inactivateFlag セットする 有効無効フラグ。
	 */
	void setInactivateFlag(String inactivateFlag);
	
}
