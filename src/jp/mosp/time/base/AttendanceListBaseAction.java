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
package jp.mosp.time.base;

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.utils.MonthUtility;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dto.settings.SubordinateListDtoInterface;
import jp.mosp.time.dto.settings.impl.AttendanceListDto;
import jp.mosp.time.utils.TimeUtility;

/**
 * 勤怠一覧におけるActionの基本機能を提供する。<br>
 * <br>
 */
public abstract class AttendanceListBaseAction extends TimeAction {
	
	/**
	 * VOに個人IDと対象年月を設定する。<br>
	 * @param personalId  個人ID
	 * @param targetYear  対象年
	 * @param targetMonth 対象月
	 */
	protected void initVoFields(String personalId, int targetYear, int targetMonth) {
		// VOを準備
		AttendanceListBaseVo vo = (AttendanceListBaseVo)mospParams.getVo();
		// VOに個人IDと対象年月を設定
		vo.setPersonalId(personalId);
		vo.setPltSelectYear(MospUtility.getString(targetYear));
		vo.setPltSelectMonth(MospUtility.getString(targetMonth));
	}
	
	/**
	 * 勤怠一覧情報参照クラスから情報を取得し、VOのフィールドを設定する。<br>
	 * @param list 勤怠一覧情報リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setVoFields(List<AttendanceListDto> list) throws MospException {
		// VOを準備
		AttendanceListBaseVo vo = (AttendanceListBaseVo)mospParams.getVo();
		// 個人IDと対象年月と締日をVOから取得
		String personalId = vo.getPersonalId();
		int targetYear = getInt(vo.getPltSelectYear());
		int targetMonth = getInt(vo.getPltSelectMonth());
		int cutoffDate = vo.getCutoffDate();
		// 勤怠一覧情報(最初の要素)を取得
		AttendanceListDto dto = MospUtility.getFirstValue(list);
		// 勤怠一覧情報(最初の要素)を取得を取得できた場合
		if (MospUtility.isEmpty(dto) == false) {
			// 個人IDと対象年月と締日を勤怠一覧情報から取得
			personalId = dto.getPersonalId();
			targetYear = dto.getTargetYear();
			targetMonth = dto.getTargetMonth();
			cutoffDate = dto.getCutoffDate();
		}
		// 対象年月における締期間の基準日を取得
		Date targetDate = TimeUtility.getCutoffTermTargetDate(cutoffDate, targetYear, targetMonth, mospParams);
		// VOの年月フィールドを設定
		setVoYearMonthFields(targetYear, targetMonth);
		// VOの今月及び締日フィールドを設定
		setVoThisMonthFields(cutoffDate);
		// VOの人事情報フィールドを設定
		setVoHumanFields(dto, personalId, targetDate);
		// VOの前社員・次社員情報フィールドを設定
		setVoRollEmployeeFields();
	}
	
	/**
	 * VOの年月フィールドを設定する。<br>
	 * @param targetYear  対象年
	 * @param targetMonth 対象月
	 * @throws MospException 日付操作に失敗した場合
	 */
	protected void setVoYearMonthFields(int targetYear, int targetMonth) throws MospException {
		// VO準備
		AttendanceListBaseVo vo = (AttendanceListBaseVo)mospParams.getVo();
		// 対象年月をVOに設定
		vo.setPltSelectYear(String.valueOf(targetYear));
		vo.setPltSelectMonth(String.valueOf(targetMonth));
		// 年月プルダウン設定
		vo.setAryPltSelectYear(getYearArray(targetYear));
		vo.setAryPltSelectMonth(getMonthArray());
		// 表示対象年月取得(前月、次月用)
		Date current = MonthUtility.getYearMonthTermFirstDate(targetYear, targetMonth, mospParams);
		// 前月、次月取得
		Date next = DateUtility.addMonth(current, 1);
		Date previous = DateUtility.addMonth(current, -1);
		// 前月、次月ボタンの年月を設定
		vo.setNextMonthYear(DateUtility.getYear(next));
		vo.setNextMonthMonth(DateUtility.getMonth(next));
		vo.setPrevMonthYear(DateUtility.getYear(previous));
		vo.setPrevMonthMonth(DateUtility.getMonth(previous));
	}
	
	/**
	 * VOの今月及び締日フィールドを設定する。<br>
	 * @param cutoffDate 締日
	 * @throws MospException 日付操作に失敗した場合
	 */
	protected void setVoThisMonthFields(int cutoffDate) throws MospException {
		// VOを準備
		AttendanceListBaseVo vo = (AttendanceListBaseVo)mospParams.getVo();
		// VOに締日を設定
		vo.setCutoffDate(cutoffDate);
		// システム日付を取得
		Date systemDate = getSystemDate();
		// 対象日付及び締日から対象年月日が含まれる締月を取得
		Date cutoffMonth = TimeUtility.getCutoffMonth(cutoffDate, systemDate, mospParams);
		// VOの今月フィールドを設定
		vo.setThisMonthYear(DateUtility.getYear(cutoffMonth));
		vo.setThisMonthMonth(DateUtility.getMonth(cutoffMonth));
	}
	
	/**
	 * VOの人事情報フィールドを設定する。<br>
	 * @param dto        勤怠一覧情報
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @throws MospException 人事情報の取得に失敗した場合
	 */
	protected void setVoHumanFields(AttendanceListDto dto, String personalId, Date targetDate) throws MospException {
		// VOを準備
		TimeVo vo = (TimeVo)mospParams.getVo();
		// 勤怠一覧情報が存在する場合
		if (MospUtility.isEmpty(dto) == false && MospUtility.isEmpty(dto.getEmployeeCode()) == false) {
			// VOに個人IDを設定
			vo.setPersonalId(dto.getPersonalId());
			// VOに社員コードを設定
			vo.setLblEmployeeCode(dto.getEmployeeCode());
			// VOに氏名を設定
			vo.setLblEmployeeName(dto.getEmployeeName());
			// 所属情報取得及び設定
			vo.setLblSectionName(dto.getSectionName());
			// 処理終了(勤怠一覧情報から値を取得)
			return;
		}
		// 人事マスタ情報取得
		HumanDtoInterface humanDto = reference().human().getHumanInfo(personalId, targetDate);
		// 人事マスタ情報確認
		if (humanDto == null) {
			// VOに個人IDを設定
			vo.setPersonalId(personalId);
			// VOに社員コードを設定
			vo.setLblEmployeeCode(MospConst.STR_EMPTY);
			// VOに氏名を設定
			vo.setLblEmployeeName(MospConst.STR_EMPTY);
			// 所属情報取得及び設定
			vo.setLblSectionName(MospConst.STR_EMPTY);
			return;
		}
		// VOに個人IDを設定
		vo.setPersonalId(humanDto.getPersonalId());
		// VOに社員コードを設定
		vo.setLblEmployeeCode(humanDto.getEmployeeCode());
		// VOに氏名を設定
		vo.setLblEmployeeName(getLastFirstName(humanDto.getLastName(), humanDto.getFirstName()));
		// 所属情報取得及び設定
		vo.setLblSectionName(reference().section().getSectionName(humanDto.getSectionCode(), targetDate));
		if (reference().section().useDisplayName()) {
			// 所属表示名称を設定
			vo.setLblSectionName(reference().section().getSectionDisplay(humanDto.getSectionCode(), targetDate));
		}
	}
	
	/**
	 * VOの前社員・次社員情報フィールドを設定する。<br>
	 */
	protected void setVoRollEmployeeFields() {
		// VO取得
		AttendanceListBaseVo vo = (AttendanceListBaseVo)mospParams.getVo();
		Object object = mospParams.getGeneralParam(TimeConst.PRM_ROLL_ARRAY);
		if (object != null) {
			vo.setRollArray((BaseDtoInterface[])object);
		}
		if (vo.getRollArray() == null || vo.getRollArray().length == 0) {
			return;
		}
		int i = 0;
		for (BaseDtoInterface baseDto : vo.getRollArray()) {
			SubordinateListDtoInterface dto = (SubordinateListDtoInterface)baseDto;
			if (vo.getPersonalId().equals(dto.getPersonalId())) {
				break;
			}
			i++;
		}
		// 前社員設定
		vo.setLblPrevEmployeeCode("");
		vo.setPrevPersonalId("");
		if (i > 0) {
			BaseDtoInterface baseDto = vo.getRollArray()[i - 1];
			SubordinateListDtoInterface dto = (SubordinateListDtoInterface)baseDto;
			vo.setLblPrevEmployeeCode(dto.getEmployeeCode());
			vo.setPrevPersonalId(dto.getPersonalId());
		}
		// 次社員設定
		vo.setLblNextEmployeeCode("");
		vo.setNextPersonalId("");
		if (i + 1 < vo.getRollArray().length) {
			BaseDtoInterface baseDto = vo.getRollArray()[i + 1];
			SubordinateListDtoInterface dto = (SubordinateListDtoInterface)baseDto;
			vo.setLblNextEmployeeCode(dto.getEmployeeCode());
			vo.setNextPersonalId(dto.getPersonalId());
		}
	}
	
	/**
	 * 検索結果リストの内容をVOに設定する。<br>
	 * @param list 対象リスト
	 */
	protected void setVoList(List<AttendanceListDto> list) {
		// VO準備
		AttendanceListBaseVo vo = (AttendanceListBaseVo)mospParams.getVo();
		// 設定項目準備
		String[] aryLblDate = new String[list.size()];
		String[] aryLblWeek = new String[list.size()];
		String[] aryWorkDayOfWeekStyle = new String[list.size()];
		String[] aryLblWorkType = new String[list.size()];
		String[] aryLblStartTime = new String[list.size()];
		String[] aryStartTimeStyle = new String[list.size()];
		String[] aryLblEndTime = new String[list.size()];
		String[] aryEndTimeStyle = new String[list.size()];
		String[] aryLblWorkTime = new String[list.size()];
		String[] aryLblRestTime = new String[list.size()];
		String[] aryLblRemark = new String[list.size()];
		// 設定項目作成
		for (int i = 0; i < list.size(); i++) {
			AttendanceListDto dto = list.get(i);
			aryLblDate[i] = dto.getWorkDateString();
			aryLblWeek[i] = dto.getWorkDayOfWeek();
			aryWorkDayOfWeekStyle[i] = dto.getWorkDayOfWeekStyle();
			aryLblWorkType[i] = dto.getWorkTypeAbbr();
			aryLblStartTime[i] = dto.getStartTimeString();
			aryStartTimeStyle[i] = dto.getStartTimeStyle();
			aryLblEndTime[i] = dto.getEndTimeString();
			aryEndTimeStyle[i] = dto.getEndTimeStyle();
			aryLblWorkTime[i] = dto.getWorkTimeString();
			aryLblRestTime[i] = dto.getRestTimeString();
			aryLblRemark[i] = MospUtility.concat(dto.getRemark(), dto.getTimeComment());
		}
		// VOに項目を設定
		vo.setAryLblDate(aryLblDate);
		vo.setAryLblWeek(aryLblWeek);
		vo.setAryWorkDayOfWeekStyle(aryWorkDayOfWeekStyle);
		vo.setAryLblWorkType(aryLblWorkType);
		vo.setAryLblStartTime(aryLblStartTime);
		vo.setAryStartTimeStyle(aryStartTimeStyle);
		vo.setAryLblEndTime(aryLblEndTime);
		vo.setAryEndTimeStyle(aryEndTimeStyle);
		vo.setAryLblWorkTime(aryLblWorkTime);
		vo.setAryLblRestTime(aryLblRestTime);
		vo.setAryLblRemark(aryLblRemark);
		// 合計値取得確認
		if (list.isEmpty()) {
			return;
		}
		// 勤怠一覧情報リスト最終レコード取得
		AttendanceListDto dto = list.get(list.size() - 1);
		// 合計値設定
		vo.setLblTotalWork(dto.getWorkTimeTotalString());
		vo.setLblTotalRest(dto.getRestTimeTotalString());
		vo.setLblTimesWork(dto.getWorkDaysString());
		vo.setLblTimesPrescribedHoliday(dto.getPrescribedHolidaysString());
		vo.setLblTimesLegalHoliday(dto.getLegalHolidaysString());
		vo.setLblTimesHoliday(dto.getHolidayString());
		// 一覧選択情報を初期化
		initCkbSelect();
	}
	
}
