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

import java.util.List;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.AttendanceDtoInterface;
import jp.mosp.time.dto.settings.GoOutDtoInterface;
import jp.mosp.time.dto.settings.RestDtoInterface;

/**
 * 自動計算インターフェース。
 */
public interface AttendanceCalcBeanInterface extends BaseBeanInterface {
	
	/**
	 * 日々の自動計算処理を行う。<br>
	 * @param attendanceDto 勤怠データ
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void attendanceCalc(AttendanceDtoInterface attendanceDto) throws MospException;
	
	/**
	 * 日々の自動計算処理を行う。<br>
	 * @param attendanceDto 勤怠データ
	 * @param restList 休憩リスト
	 * @param publicGoOutList 公用外出リスト
	 * @param privateGoOutList 私用外出リスト
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void attendanceCalc(AttendanceDtoInterface attendanceDto, List<RestDtoInterface> restList,
			List<GoOutDtoInterface> publicGoOutList, List<GoOutDtoInterface> privateGoOutList) throws MospException;
	
	/**
	 * 始業時刻及び終業時刻の自動計算処理を行う。<br>
	 * @param attendanceDto 勤怠データ
	 * @param useBetweenTime 前半休と後半休の間の時間を使う場合true、そうでない場合false
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void calcStartEndTime(AttendanceDtoInterface attendanceDto, boolean useBetweenTime) throws MospException;
	
}
