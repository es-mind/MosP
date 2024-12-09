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

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.bean.system.PlatformMasterBeanInterface;

/**
 *	カレンダ勤務日数参照インターフェイス。
 */
public interface CalendarWorkingDaysBeanInterface extends BaseBeanInterface {
	
	/**
	 * カレンダ勤務日数を取得する。
	 * 
	 * 対象個人IDの、対象年月の締期間における、カレンダマスタに登録されている勤務日数(※1)をカレンダ勤務日数とする。
	 * ※1．ここでの勤務日数とは、カレンダマスタの所定休日、法定休日、空欄以外が登録されている日を指す。
	 * 		対象期間内に、在籍していない期間(人事情報の最も古い有効日より前の日数、退職日より後の日数)及び
	 * 		休職期間が存在する場合、これらの期間はカレンダ勤務日数にカウントしない。
	 * @param personalId 個人ID
	 * @param cutoffCode 締日コード
	 * @param targetYear 対象年
	 * @param targetMonth 対象月
	 * @return カレンダ勤務日数
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	int getCalendarWorkingDays(String personalId, String cutoffCode, int targetYear, int targetMonth)
			throws MospException;
	
	/**
	 * プラットフォームマスタ参照処理を設定する。<br>
	 * @param platformMaster プラットフォームマスタ参照処理
	 */
	void setPlatformMaster(PlatformMasterBeanInterface platformMaster);
	
	/**
	 * 勤怠関連マスタ参照処理を設定する。<br>
	 * 勤怠関連マスタ参照処理を処理間で共有するために用いる。<br>
	 * @param timeMaster 勤怠関連マスタ参照処理
	 */
	void setTimeMaster(TimeMasterBeanInterface timeMaster);
}
