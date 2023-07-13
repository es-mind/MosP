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

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.ApplicationReferenceDtoInterface;

/**
 * 設定適用参照検索インターフェース。
 */
public interface ApplicationReferenceSearchBeanInterface extends BaseBeanInterface {
	
	/**
	 * 検索条件から設定適用リストを取得する。<br><br>
	 * {@link #setActivateDate(Date)}等で設定された条件で、検索を行う。
	 * @return 設定適用リスト
	 * @throws MospException インスタンスの取得、SQLの作成及び実行に失敗した場合
	 */
	List<ApplicationReferenceDtoInterface> getSearchList() throws MospException;
	
	/**
	 * @param activateDate セットする 有効日
	 */
	void setActivateDate(Date activateDate);
	
	/**
	 * @param employeeCode セットする 社員コード
	 */
	void setEmployeeCode(String employeeCode);
	
	/**
	 * @param employeeName セットする 社員名
	 */
	void setEmployeeName(String employeeName);
	
	/**
	 * @param workPlaceCode セットする 勤務地コード
	 */
	void setWorkPlaceCode(String workPlaceCode);
	
	/**
	 * @param employmentCode セットする 雇用契約コード
	 */
	void setEmploymentCode(String employmentCode);
	
	/**
	 * @param sectionCode セットする 所属コード
	 */
	void setSectionCode(String sectionCode);
	
	/**
	 * @param positionCode セットする 職位コード
	 */
	void setPositionCode(String positionCode);
	
	/**
	 * @param applicationCode セットする 設定適用コード
	 */
	void setApplicationCode(String applicationCode);
	
	/**
	 * @param applicationName セットする 設定適用名称
	 */
	void setApplicationName(String applicationName);
	
	/**
	 * @param timeSettingCode セットする 勤怠設定コード
	 */
	void setTimeSettingCode(String timeSettingCode);
	
	/**
	 * @param cutoffCode セットする 締日コード
	 */
	void setCutoffCode(String cutoffCode);
	
	/**
	 * @param scheduleCode セットする カレンダコード
	 */
	void setScheduleCode(String scheduleCode);
	
	/**
	 * @param paidHolidayCode セットする 有休設定コード
	 */
	void setPaidHolidayCode(String paidHolidayCode);
	
}
