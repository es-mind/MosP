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
package jp.mosp.time.report.bean;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;

/**
 * 出勤簿作成インターフェース。
 */
public interface AttendanceBookBeanInterface extends BaseBeanInterface {
	
	/**
	 * 個人IDと年月及び締日で出勤簿を作成する。<br>
	 * @param personalId 取得対象個人ID
	 * @param year       取得対象年
	 * @param month      取得対象月
	 * @param cutoffDate 締日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void makeAttendanceBook(String personalId, int year, int month, int cutoffDate) throws MospException;
	
	/**
	 * 個人ID及び年月で出勤簿を作成する。<br>
	 * @param personalIds 取得対象個人ID配列
	 * @param year        取得対象年
	 * @param month       取得対象月
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void makeAttendanceBooks(String[] personalIds, int year, int month) throws MospException;
	
	/**
	 * 個人IDと年月及び締日で出勤簿を作成する。<br>
	 * @param personalIds 取得対象個人ID配列
	 * @param year        取得対象年
	 * @param month       取得対象月
	 * @param cutoffDate  締日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void makeAttendanceBooks(String[] personalIds, int year, int month, int cutoffDate) throws MospException;
	
}
