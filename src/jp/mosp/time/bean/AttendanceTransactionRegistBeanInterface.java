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
import java.util.Map;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.AttendanceDtoInterface;
import jp.mosp.time.dto.settings.HolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.SubHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.SubstituteDtoInterface;

/**
 * 勤怠トランザクション登録処理インターフェース。
 */
public interface AttendanceTransactionRegistBeanInterface extends BaseBeanInterface {
	
	/**
	 * 登録を行う。<br>
	 * @param personalId 個人ID
	 * @param workDate 勤務日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void regist(String personalId, Date workDate) throws MospException;
	
	/**
	 * 登録を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void regist(AttendanceDtoInterface dto) throws MospException;
	
	/**
	 * 登録を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void regist(HolidayRequestDtoInterface dto) throws MospException;
	
	/**
	 * 登録を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void regist(SubHolidayRequestDtoInterface dto) throws MospException;
	
	/**
	 * 登録を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void regist(SubstituteDtoInterface dto) throws MospException;
	
	/**
	 * 勤怠トランザクションの登録を行う。<br>
	 * <br>
	 * 勤怠集計によって作成された勤怠トランザクション登録判定情報群を基に、
	 * 休職と法定休日と所定休日の日につき、勤怠トランザクションの登録を行う。<br>
	 * <br>
	 * @param personalId    個人ID
	 * @param attendanceMap 勤怠トランザクション登録判定情報群
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void regist(String personalId, Map<Date, String> attendanceMap) throws MospException;
	
	/**
	 * 勤怠関連マスタ参照処理を設定する。<br>
	 * 勤怠関連マスタ参照処理を処理間で共有するために用いる。<br>
	 * <br>
	 * @param timeMaster 勤怠関連マスタ参照処理
	 */
	void setTimeMaster(TimeMasterBeanInterface timeMaster);
	
}
