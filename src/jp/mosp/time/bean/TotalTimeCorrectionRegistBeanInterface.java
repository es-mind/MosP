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

import java.util.List;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.TotalAbsenceDtoInterface;
import jp.mosp.time.dto.settings.TotalAllowanceDtoInterface;
import jp.mosp.time.dto.settings.TotalLeaveDtoInterface;
import jp.mosp.time.dto.settings.TotalOtherVacationDtoInterface;
import jp.mosp.time.dto.settings.TotalTimeCorrectionDtoInterface;
import jp.mosp.time.dto.settings.TotalTimeDataDtoInterface;

/**
 * 勤怠集計修正情報登録インターフェース。<br>
 */
public interface TotalTimeCorrectionRegistBeanInterface extends BaseBeanInterface {
	
	/**
	 * 登録用DTOを取得する。<br>
	 * @return 初期DTO
	 */
	TotalTimeCorrectionDtoInterface getInitDto();
	
	/**
	 * 新規登録を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void insert(TotalTimeCorrectionDtoInterface dto) throws MospException;
	
	/**
	 * 履歴更新を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void update(TotalTimeCorrectionDtoInterface dto) throws MospException;
	
	/**
	 * 勤怠集計データの新規登録を行う。<br>
	 * @param dto 対象DTO
	 * @param oldTotalTimeDataDto 変更前勤怠DTO
	 * @param newTotalTimeDataDto 変更予定の勤怠DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void insertTotalTime(TotalTimeCorrectionDtoInterface dto, TotalTimeDataDtoInterface oldTotalTimeDataDto,
			TotalTimeDataDtoInterface newTotalTimeDataDto) throws MospException;
	
	/**
	 * 特別休暇日数集計データの新規登録を行う。<br>
	 * @param dto 対象DTO
	 * @param oldTotalLeaveDto 変更前特別休暇集計DTO
	 * @param newTotalLeaveDto 変更予定の特別休暇集計DTO
	 * @param isDay 確認結果(true：日数を更新、false：日数を更新しない)
	 * @param isHour 確認結果(true：時間数を更新、false：時間数を更新しない)
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void insertLeave(TotalTimeCorrectionDtoInterface dto, TotalLeaveDtoInterface oldTotalLeaveDto,
			TotalLeaveDtoInterface newTotalLeaveDto, boolean isDay, boolean isHour) throws MospException;
	
	/**
	 * その他休暇集計データの新規登録を行う。<br>
	 * @param dto 対象DTO
	 * @param oldTotalOtherVacationDto 変更前その他休暇集計DTO
	 * @param newTotalOtherVacationDto 変更予定のその他休暇集計DTO
	 * @param isDay 確認結果(true：日数を更新、false：日数を更新しない)
	 * @param isHour 確認結果(true：時間数を更新、false：時間数を更新しない)
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void insertOtherVacation(TotalTimeCorrectionDtoInterface dto,
			TotalOtherVacationDtoInterface oldTotalOtherVacationDto,
			TotalOtherVacationDtoInterface newTotalOtherVacationDto, boolean isDay, boolean isHour)
			throws MospException;
	
	/**
	 * 欠勤集計データの新規登録を行う。<br>
	 * @param dto 対象DTO
	 * @param oldTotalAbsenceDto 変更前欠勤集計DTO
	 * @param newTotalAbsenceDto 変更予定の欠勤集計DTO
	 * @param isDay 確認結果(true：日数を更新、false：日数を更新しない)
	 * @param isHour 確認結果(true：時間数を更新、false：時間数を更新しない)
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void insertAbsence(TotalTimeCorrectionDtoInterface dto, TotalAbsenceDtoInterface oldTotalAbsenceDto,
			TotalAbsenceDtoInterface newTotalAbsenceDto, boolean isDay, boolean isHour) throws MospException;
	
	/**
	 * 手当集計データの新規登録を行う。<br>
	 * @param dto 対象DTO
	 * @param oldAllowanceDto 変更前手当集計DTO
	 * @param newAllowanceDto 変更予定の手当集計DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void insertAllowance(TotalTimeCorrectionDtoInterface dto, TotalAllowanceDtoInterface oldAllowanceDto,
			TotalAllowanceDtoInterface newAllowanceDto) throws MospException;
	
	/**
	 * 論理削除を行う。<br>
	 * @param personalIdList 個人IDリスト
	 * @param calculationYear 集計年
	 * @param calculationMonth 集計月
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void delete(List<String> personalIdList, int calculationYear, int calculationMonth) throws MospException;
	
}
