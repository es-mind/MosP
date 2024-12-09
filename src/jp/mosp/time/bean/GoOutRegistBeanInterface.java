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
/**
 * 
 */
package jp.mosp.time.bean;

import java.util.Date;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.AttendanceDtoInterface;
import jp.mosp.time.dto.settings.GoOutDtoInterface;
import jp.mosp.time.dto.settings.TimeSettingDtoInterface;

/**
 * 勤怠データ外出情報登録処理インターフェース。<br>
 */
public interface GoOutRegistBeanInterface extends BaseBeanInterface {
	
	/**
	 * 登録用DTOを取得する。<br>
	 * @return 初期DTO
	 */
	GoOutDtoInterface getInitDto();
	
	/**
	 * 登録処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void regist(GoOutDtoInterface dto) throws MospException;
	
	/**
	 * 新規登録を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void insert(GoOutDtoInterface dto) throws MospException;
	
	/**
	 * 履歴更新を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void update(GoOutDtoInterface dto) throws MospException;
	
	/**
	 * 削除処理を行う。<br>
	 * @param personalId 個人ID
	 * @param workDate 勤務日
	 * @param timesWork 勤務回数
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void delete(String personalId, Date workDate, int timesWork) throws MospException;
	
	/**
	 * 削除処理を行う。<br>
	 * @param personalId 個人ID
	 * @param workDate 勤務日
	 * @param timesWork 勤務回数
	 * @param goOutType 外出区分
	 * @param timesGoOut 外出回数
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void delete(String personalId, Date workDate, int timesWork, int goOutType, int timesGoOut) throws MospException;
	
	/**
	 * 公用外出時間を計算する。
	 * 公用外出開始時刻と公用外出終了時刻を丸め、公用外出時間を計算する。
	 * @param goOutStart 公用外出開始時刻
	 * @param goOutEnd 公用外出終了時刻
	 * @param timeSettingDto 勤怠設定情報
	 * @return 丸め計算された公用外出時間
	 */
	int getCalcPublicGoOutTime(Date goOutStart, Date goOutEnd, TimeSettingDtoInterface timeSettingDto);
	
	/**
	 * 私用外出時間を計算する。
	 * 私用外出開始時刻と私用外出終了時刻を丸め、私用外出時間を計算する。
	 * @param goOutStart 私用外出開始時刻
	 * @param goOutEnd 私用外出終了時刻
	 * @param timeSettingDto 勤怠設定情報
	 * @return 丸め計算された休憩私用外出時間
	 */
	int getCalcPrivateGoOutTime(Date goOutStart, Date goOutEnd, TimeSettingDtoInterface timeSettingDto);
	
	/**
	 * 始業終業時刻を元に外出開始時刻と外出終了時刻を設定する。
	 * @param goOutStartTime 外出開始時刻
	 * @param goOutEndTime 外出終了時刻
	 * @param attendanceDto 勤怠データDTO
	 * @param dto 外出データDTO
	 * @throws MospException 例外が発生した場合
	 */
	void setGoOutStartEnd(Date goOutStartTime, Date goOutEndTime, AttendanceDtoInterface attendanceDto,
			GoOutDtoInterface dto) throws MospException;
}
