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

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;

/**
 * 有給休暇取得状況確認情報出力処理インターフェース。<br>
 */
public interface PaidHolidayUsageExportBeanInterface extends BaseBeanInterface {
	
	/**
	 * 有給休暇取得状況確認情報を出力する。<br>
	 * 取得対象年月の末日を検索日付とし、対象期間を決定する。<br>
	 * @param personalIds 取得対象個人ID配列
	 * @param year        取得対象年
	 * @param month       取得対象月
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void export(String[] personalIds, int year, int month) throws MospException;
	
	/**
	 * 有給休暇取得状況確認情報を出力する。<br>
	 * @param personalIds 取得対象個人ID配列
	 * @param targetDate  取得対象日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void export(String[] personalIds, Date targetDate) throws MospException;
	
}
