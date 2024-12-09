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
package jp.mosp.time.entity;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

import jp.mosp.time.dto.settings.AttendanceDtoInterface;
import jp.mosp.time.dto.settings.GoOutDtoInterface;
import jp.mosp.time.dto.settings.RestDtoInterface;

/**
 * 勤怠(日々)エンティティインターフェース。<br>
 */
public interface AttendanceEntityInterface {
	
	/**
	 * 個人を取得する。<br>
	 * 勤怠(日々)情報が存在しない場合は、空文字を返す。<br>
	 * @return 個人
	 */
	String getPersonalId();
	
	/**
	 * 勤務日を取得する。<br>
	 * 勤怠(日々)情報が存在しない場合は、nullを返す。<br>
	 * @return 勤務日
	 */
	Date getWorkDate();
	
	/**
	 * 勤務形態コードを取得する。<br>
	 * 勤怠(日々)情報が存在しない場合は、空文字を返す。<br>
	 * @return 勤務形態コード
	 */
	String getWorkTypeCode();
	
	/**
	 * 始業時刻を取得する。<br>
	 * 勤怠(日々)情報が存在しない場合は、nullを返す。<br>
	 * @return 始業時刻
	 */
	Date getStartTime();
	
	/**
	 * 終業時刻を取得する。<br>
	 * 勤怠(日々)情報が存在しない場合は、nullを返す。<br>
	 * @return 始業時刻
	 */
	Date getEndTime();
	
	/**
	 * 勤怠(日々)情報に直行が設定されているかを確認する。<br>
	 * 勤怠(日々)情報が存在しない場合は、falseを返す。<br>
	 * <br>
	 * @return 確認結果(true：勤怠(日々)情報に直行が設定されている、false：されていない)
	 */
	boolean isAttendanceDirectStart();
	
	/**
	 * 勤怠(日々)情報に直帰が設定されているかを確認する。<br>
	 * 勤怠(日々)情報が存在しない場合は、falseを返す。<br>
	 * <br>
	 * @return 確認結果(true：勤怠(日々)情報に直帰が設定されている、false：されていない)
	 */
	boolean isAttendanceDirectEnd();
	
	/**
	 * 遅刻理由が電車遅延であるかを確認する。<br>
	 * 勤怠(日々)情報が存在しない場合は、falseを返す。<br>
	 * <br>
	 * @return 確認結果(true：遅刻理由が電車遅延である、false：そうでない)
	 */
	boolean isLateReasonTrain();
	
	/**
	 * 遅刻理由が会社指示であるかを確認する。<br>
	 * 勤怠(日々)情報が存在しない場合は、falseを返す。<br>
	 * <br>
	 * @return 確認結果(true：遅刻理由が会社指示である、false：そうでない)
	 */
	boolean isLateReasonCompany();
	
	/**
	 * 早退理由が会社指示であるかを確認する。<br>
	 * 勤怠(日々)情報が存在しない場合は、falseを返す。<br>
	 * <br>
	 * @return 確認結果(true：早退理由が会社指示である、false：そうでない)
	 */
	boolean isLeaveEarlyReasonCompany();
	
	/**
	 * 全入力休憩時間群を取得する。<br>
	 * <br>
	 * 休憩開始時刻及び終了時刻は、勤怠設定により丸められる。<br>
	 * その上で、時間間隔を結合する。<br>
	 * <br>
	 * @param timeSetting 勤怠設定エンティティ
	 * @return 全入力休憩時間群(キー：開始時刻(キー順))
	 */
	Map<Integer, TimeDuration> getRestTimes(TimeSettingEntityInterface timeSetting);
	
	/**
	 * 全外出時間群を取得する。<br>
	 * <br>
	 * 外出開始時刻及び終了時刻は、勤怠設定により丸められる。<br>
	 * その上で、時間間隔を結合する。<br>
	 * <br>
	 * @param timeSetting 勤怠設定エンティティ
	 * @return 全外出時間群(キー：開始時刻(キー順))
	 */
	Map<Integer, TimeDuration> getAllGoOutTimes(TimeSettingEntityInterface timeSetting);
	
	/**
	 * 公用外出時間群を取得する。<br>
	 * <br>
	 * 外出開始時刻及び終了時刻は、勤怠設定により丸められる。<br>
	 * その上で、時間間隔を結合する。<br>
	 * <br>
	 * @param timeSetting 勤怠設定エンティティ
	 * @return 公用外出時間群(キー：開始時刻(キー順))
	 */
	Map<Integer, TimeDuration> getPublicGoOutTimes(TimeSettingEntityInterface timeSetting);
	
	/**
	 * 私用外出時間群を取得する。<br>
	 * <br>
	 * 外出開始時刻及び終了時刻は、勤怠設定により丸められる。<br>
	 * その上で、時間間隔を結合する。<br>
	 * <br>
	 * @param timeSetting 勤怠設定エンティティ
	 * @return 私用外出時間群(キー：開始時刻(キー順))
	 */
	Map<Integer, TimeDuration> getPrivateGoOutTimes(TimeSettingEntityInterface timeSetting);
	
	/**
	 * 勤怠(日々)情報を設定する。<br>
	 * @param attendanceDto 勤怠(日々)情報
	 */
	void setAttendanceDto(AttendanceDtoInterface attendanceDto);
	
	/**
	 * 休憩情報群を設定する。<br>
	 * @param restDtos 休憩情報群
	 */
	void setRestDtos(Collection<RestDtoInterface> restDtos);
	
	/**
	 * 外出情報群を設定する。<br>
	 * @param goOutDtos 外出情報群
	 */
	void setGoOutDtos(Collection<GoOutDtoInterface> goOutDtos);
	
	/**
	 * 勤怠(日々)情報を取得する。<br>
	 * @return 勤怠(日々)情報
	 */
	AttendanceDtoInterface getAttendanceDto();
	
	/**
	 * 休憩情報群を取得する。<br>
	 * @return 休憩情報群
	 */
	Collection<RestDtoInterface> getRestDtos();
	
	/**
	 * 外出情報群を取得する。<br>
	 * @return 外出情報群
	 */
	Collection<GoOutDtoInterface> getGoOutDtos();
	
}
