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
import java.util.Map;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.AttendanceDtoInterface;
import jp.mosp.time.dto.settings.RestDtoInterface;
import jp.mosp.time.dto.settings.TimeSettingDtoInterface;
import jp.mosp.time.entity.TimeDuration;

/**
 * 勤怠データ休憩情報登録インターフェース。<br>
 */
public interface RestRegistBeanInterface extends BaseBeanInterface {
	
	/**
	 * 登録用DTOを取得する。<br>
	 * @return 初期DTO
	 */
	RestDtoInterface getInitDto();
	
	/**
	 * 登録処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void regist(RestDtoInterface dto) throws MospException;
	
	/**
	 * 新規登録を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void insert(RestDtoInterface dto) throws MospException;
	
	/**
	 * 履歴更新を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void update(RestDtoInterface dto) throws MospException;
	
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
	 * @param rest 休憩回数
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void delete(String personalId, Date workDate, int timesWork, int rest) throws MospException;
	
	/**
	 * 休憩時間を計算する。<br>
	 * 休憩開始時刻と休憩終了時刻を丸め、休憩時間を計算する。
	 * @param restStartTime 休憩開始時刻
	 * @param restEndTime 休憩終了時刻
	 * @param timeSettingDto 勤怠設定情報
	 * @return 丸め計算された休憩時間
	 */
	int getCalcRestTime(Date restStartTime, Date restEndTime, TimeSettingDtoInterface timeSettingDto);
	
	/**
	 * 休憩時刻を調整する。<br>
	 * 始業時刻と終業時刻に合わせて休憩開始時刻と休憩終了時刻を調整する。
	 * @param restStartTime 休憩開始時刻
	 * @param restEndTime 休憩終了時刻
	 * @param attendanceDto 勤怠データDTO
	 * @param restDto 休憩データDTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void setRestStartEndTime(Date restStartTime, Date restEndTime, AttendanceDtoInterface attendanceDto,
			RestDtoInterface restDto) throws MospException;
	
	/**
	 * 休憩情報郡を登録する。<br>
	 * 勤務回数は1とする。<br>
	 * 休憩情報(時間間隔)群は、開始時刻順に並んでいるものとする。<br>
	 * @param personalId  個人ID
	 * @param workDate    勤務日
	 * @param rests       休憩情報(時間間隔)群
	 * @param timeSetting 勤怠設定情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void registRests(String personalId, Date workDate, Map<Integer, TimeDuration> rests,
			TimeSettingDtoInterface timeSetting) throws MospException;
	
}
