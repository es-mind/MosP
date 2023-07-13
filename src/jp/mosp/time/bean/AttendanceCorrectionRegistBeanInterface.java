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

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.AttendanceCorrectionDtoInterface;
import jp.mosp.time.dto.settings.AttendanceDtoInterface;
import jp.mosp.time.dto.settings.GoOutDtoInterface;
import jp.mosp.time.dto.settings.RestDtoInterface;

/**
 * 勤怠データ修正情報登録インターフェース。<br>
 */
public interface AttendanceCorrectionRegistBeanInterface extends BaseBeanInterface {
	
	/**
	 * 登録用DTOを取得する。<br>
	 * @return 初期DTO
	 */
	AttendanceCorrectionDtoInterface getInitDto();
	
	/**
	 * 新規登録を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void insert(AttendanceCorrectionDtoInterface dto) throws MospException;
	
	/**
	 * 新規登録を行う。<br>
	 * @param dto 対象DTO
	 * @param oldAttendanceDto 変更前勤怠DTO
	 * @param newAttendanceDto 変更予定の勤怠DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void insertAttendance(AttendanceCorrectionDtoInterface dto, AttendanceDtoInterface oldAttendanceDto,
			AttendanceDtoInterface newAttendanceDto) throws MospException;
	
	/**
	 * 新規登録を行う。<br>
	 * @param dto 対象DTO
	 * @param oldRestDto 変更前休憩DTO
	 * @param newRestDto 変更予定の休憩DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void insertRest(AttendanceCorrectionDtoInterface dto, RestDtoInterface oldRestDto, RestDtoInterface newRestDto)
			throws MospException;
	
	/**
	 * 新規登録を行う。<br>
	 * @param dto 対象DTO
	 * @param oldGoOutDto 変更前外出DTO
	 * @param newGoOutDto 変更予定の外出DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void insertGoOut(AttendanceCorrectionDtoInterface dto, GoOutDtoInterface oldGoOutDto, GoOutDtoInterface newGoOutDto)
			throws MospException;
	
	/**
	 * 削除処理を行う。<br>
	 * @param personalId 個人ID
	 * @param workDate 勤務日
	 * @param timesWork 勤務回数
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void delete(String personalId, Date workDate, int timesWork) throws MospException;
}
