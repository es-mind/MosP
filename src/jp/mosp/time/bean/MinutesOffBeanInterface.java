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

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.bean.system.PlatformMasterBeanInterface;

/**
 * 分単位休暇取得処理。<br>
 */
public interface MinutesOffBeanInterface extends BaseBeanInterface {
	
	/**
	 * 利用分数を取得する。<br>
	 * @param personalId      個人ID
	 * @param holidayType     休暇区分
	 * @param holidayCode     休暇コード
	 * @param acquisitionDate 休暇取得日
	 * @return 利用分数
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	int getUseMinutes(String personalId, int holidayType, String holidayCode, Date acquisitionDate)
			throws MospException;
	
	/**
	 * 1日の分数を取得する。<br>
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @return 1日の分数
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	int getMinutesPerDay(String personalId, Date targetDate) throws MospException;
	
	/**
	 * プラットフォームマスタ参照処理を設定する。<br>
	 * @param master プラットフォームマスタ参照処理
	 */
	void setMaster(PlatformMasterBeanInterface master);
	
	/**
	 * 勤怠関連マスタ参照処理を設定する。<br>
	 * @param timeMaster 勤怠関連マスタ参照処理
	 */
	void setTimeMaster(TimeMasterBeanInterface timeMaster);
	
}
