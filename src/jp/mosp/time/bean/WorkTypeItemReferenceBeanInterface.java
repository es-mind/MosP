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
/**
 * 
 */
package jp.mosp.time.bean;

import java.util.Date;
import java.util.HashMap;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.WorkTypeItemDtoInterface;

/**
 * 勤務形態項目参照インターフェース。
 */
public interface WorkTypeItemReferenceBeanInterface extends BaseBeanInterface {
	
	/**
	 * 勤務形態項目取得。
	 * @param workTypeCode 勤務形態コード
	 * @param targetDate 対象年月日
	 * @param workTypeItemCode 勤務形態項目コード
	 * @return 勤務形態項目
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	WorkTypeItemDtoInterface getWorkTypeItemInfo(String workTypeCode, Date targetDate, String workTypeItemCode)
			throws MospException;
	
	/**
	 * 勤務形態項目取得。
	 * @param workTypeCode 勤務形態コード
	 * @param activateDate 有効日
	 * @param workTypeItemCode 勤務形態項目コード
	 * @return 勤務形態項目
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	WorkTypeItemDtoInterface findForKey(String workTypeCode, Date activateDate, String workTypeItemCode)
			throws MospException;
	
	/**
	 * 勤務時間計算
	 * @param startWorkTime 始業時間
	 * @param endWorkTime 終業時間
	 * @param restTime 休憩時間
	 * @return	勤務時間
	 */
	int getWorkTime(Date startWorkTime, Date endWorkTime, int restTime);
	
	/**
	 * 休憩時間計算(総合)
	 * @param rest1 休憩1
	 * @param rest2 休憩2
	 * @param rest3 休憩3
	 * @param rest4 休憩4
	 * @return 休憩時間
	 */
	int getRestTime(int rest1, int rest2, int rest3, int rest4);
	
	/**
	 * 差分時間を取得
	 * @param startTimeHour 開始時間(時)
	 * @param startTimeMinute 開始時間(分)
	 * @param endTimeHour 終了時間(時)
	 * @param endTimeMinute 終了時間(分)
	 * @return 差分(分単位)
	 */
	int getDifferenceTime(String startTimeHour, String startTimeMinute, String endTimeHour, String endTimeMinute);
	
	/**
	 * 勤務形態項目MAP取得
	 * @param workTypeCode 勤務形態コード
	 * @param activateDate 有効日
	 * @return 勤務形態DTOMAP(キー：勤務形態項目ID)
	 * @throws MospException インスタンスの生成或いはSQLの実行に失敗した場合
	 */
	HashMap<String, WorkTypeItemDtoInterface> getWorkTypeItemMap(String workTypeCode, Date activateDate)
			throws MospException;
	
}
