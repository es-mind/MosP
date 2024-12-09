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
package jp.mosp.time.bean.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.time.bean.AttendanceCorrectionRegistBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dao.settings.AttendanceCorrectionDaoInterface;
import jp.mosp.time.dto.settings.AllowanceDtoInterface;
import jp.mosp.time.dto.settings.AttendanceCorrectionDtoInterface;
import jp.mosp.time.dto.settings.AttendanceDtoInterface;
import jp.mosp.time.dto.settings.GoOutDtoInterface;
import jp.mosp.time.dto.settings.RestDtoInterface;
import jp.mosp.time.dto.settings.impl.TmdAttendanceCorrectionDto;

/**
 * 勤怠データ修正情報登録クラス。
 */
public class AttendanceCorrectionRegistBean extends PlatformBean implements AttendanceCorrectionRegistBeanInterface {
	
	/**
	 * 勤怠修正データDAOクラス。<br>
	 */
	protected AttendanceCorrectionDaoInterface attendanceCorrectionDao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public AttendanceCorrectionRegistBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		attendanceCorrectionDao = createDaoInstance(AttendanceCorrectionDaoInterface.class);
	}
	
	@Override
	public AttendanceCorrectionDtoInterface getInitDto() {
		return new TmdAttendanceCorrectionDto();
	}
	
	@Override
	public void insert(AttendanceCorrectionDtoInterface dto) throws MospException {
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setTmdAttendanceCorrectionId(attendanceCorrectionDao.nextRecordId());
		// 登録処理
		attendanceCorrectionDao.insert(dto);
	}
	
	@Override
	public void insertAttendance(AttendanceCorrectionDtoInterface dto, AttendanceDtoInterface oldDto,
			AttendanceDtoInterface newDto) throws MospException {
		// DTOの妥当性確認
		validate(dto);
		// 変更部分の検索
		List<AttendanceCorrectionDtoInterface> list = setChangePointAttendance(oldDto, newDto);
		for (AttendanceCorrectionDtoInterface attendanceCorrectionDto : list) {
			if (null == attendanceCorrectionDto) {
				continue;
			}
			// 勤怠情報設定
			setValueAttendanceCorrection(dto, attendanceCorrectionDto);
			// 登録
			insert(dto);
		}
	}
	
	@Override
	public void insertRest(AttendanceCorrectionDtoInterface dto, RestDtoInterface oldRestDto,
			RestDtoInterface newRestDto) throws MospException {
		// DTOの妥当性確認
		validate(dto);
		// 変更部分の検索
		AttendanceCorrectionDtoInterface newAttendanceCorrection = setChangePointRest(dto, oldRestDto, newRestDto);
		if (null == newAttendanceCorrection) {
			return;
		}
		// 勤怠情報設定
		setValueAttendanceCorrection(dto, newAttendanceCorrection);
		// 登録
		insert(dto);
	}
	
	@Override
	public void insertGoOut(AttendanceCorrectionDtoInterface dto, GoOutDtoInterface oldGoOutDto,
			GoOutDtoInterface newGoOutDto) throws MospException {
		// DTOの妥当性確認
		validate(dto);
		// 変更部分の検索
		AttendanceCorrectionDtoInterface newAttendanceCorrection = setChangePointGoOut(dto, oldGoOutDto, newGoOutDto);
		if (null == newAttendanceCorrection) {
			return;
		}
		// 勤怠情報設定
		setValueAttendanceCorrection(dto, newAttendanceCorrection);
		// 登録
		insert(dto);
	}
	
	/**
	 * 登録情報の妥当性を確認する。
	 * @param dto 対象DTO
	 */
	protected void validate(AttendanceCorrectionDtoInterface dto) {
		// 処理無し
	}
	
	/**
	 * 修正前/修正後のDTOを見て修正コメントを作成する。
	 * @param oldDto 修正前データ
	 * @param newDto 修正後データ
	 * @return 修正コメント
	 */
	protected List<AttendanceCorrectionDtoInterface> setChangePointAttendance(AttendanceDtoInterface oldDto,
			AttendanceDtoInterface newDto) {
		final String hyphen = mospParams.getName("Hyphen");
		List<AttendanceCorrectionDtoInterface> list = new ArrayList<AttendanceCorrectionDtoInterface>();
		if (newDto == null || newDto.getWorkTypeCode() == null || newDto.getStartTime() == null
				|| newDto.getEndTime() == null || newDto.getTimeComment() == null || newDto.getLateReason() == null
				|| newDto.getLateComment() == null || newDto.getLeaveEarlyReason() == null
				|| newDto.getLeaveEarlyComment() == null) {
			return list;
		}
		// 修正前データの有無
		boolean noPreviousData = oldDto == null || oldDto.getWorkTypeCode() == null || oldDto.getStartTime() == null
				|| oldDto.getEndTime() == null || oldDto.getActualStartTime() == null
				|| oldDto.getActualEndTime() == null || oldDto.getTimeComment() == null
				|| oldDto.getLateReason() == null || oldDto.getLateComment() == null
				|| oldDto.getLeaveEarlyReason() == null || oldDto.getLeaveEarlyComment() == null;
		if (noPreviousData) {
			// 修正前データが無し
			
			// 勤務形態
			list.add(getAttendanceCorrectionDto(TimeConst.CODE_ATTENDANCE_ITEM_NAME_WORKTYPECODE, hyphen,
					newDto.getWorkTypeCode()));
			// 出勤時刻
			list.add(getAttendanceCorrectionDto(TimeConst.CODE_ATTENDANCE_ITEM_NAME_STARTTIME, hyphen,
					DateUtility.getStringTime(newDto.getStartTime(), newDto.getWorkDate())));
			// 退勤時刻
			list.add(getAttendanceCorrectionDto(TimeConst.CODE_ATTENDANCE_ITEM_NAME_ENDTIME, hyphen,
					DateUtility.getStringTime(newDto.getEndTime(), newDto.getWorkDate())));
			// 実出勤時刻
			list.add(getAttendanceCorrectionDto(TimeConst.CODE_ATTENDANCE_ITEM_NAME_ACTUAL_STARTTIME, hyphen,
					DateUtility.getStringTime(newDto.getActualStartTime(), newDto.getWorkDate())));
			// 実退勤時刻
			list.add(getAttendanceCorrectionDto(TimeConst.CODE_ATTENDANCE_ITEM_NAME_ACTUAL_ENDTIME, hyphen,
					DateUtility.getStringTime(newDto.getActualEndTime(), newDto.getWorkDate())));
			// 直行
			list.add(getAttendanceCorrectionDto(TimeConst.CODE_ATTENDANCE_ITEM_NAME_DIRECTSTART, hyphen,
					String.valueOf(newDto.getDirectStart())));
			// 直帰
			list.add(getAttendanceCorrectionDto(TimeConst.CODE_ATTENDANCE_ITEM_NAME_DIRECTEND, hyphen,
					String.valueOf(newDto.getDirectEnd())));
			// 勤怠コメント
			list.add(getAttendanceCorrectionDto(TimeConst.CODE_ATTENDANCE_ITEM_NAME_TIMECOMMENT, hyphen,
					newDto.getTimeComment()));
			// 遅刻理由
			list.add(getAttendanceCorrectionDto(TimeConst.CODE_ATTENDANCE_ITEM_NAME_LATEREASON, hyphen,
					newDto.getLateReason()));
			// 遅刻証明書
			list.add(getAttendanceCorrectionDto(TimeConst.CODE_ATTENDANCE_ITEM_NAME_LATECERTIFICATE, hyphen,
					newDto.getLateCertificate()));
			// 遅刻コメント
			list.add(getAttendanceCorrectionDto(TimeConst.CODE_ATTENDANCE_ITEM_NAME_LATECOMMENT, hyphen,
					newDto.getLateComment()));
			// 早退理由
			list.add(getAttendanceCorrectionDto(TimeConst.CODE_ATTENDANCE_ITEM_NAME_LEAVEEARLYREASON, hyphen,
					newDto.getLeaveEarlyReason()));
			// 早退証明書
			list.add(getAttendanceCorrectionDto(TimeConst.CODE_ATTENDANCE_ITEM_NAME_LEAVEEARLYCERTIFICATE, hyphen,
					newDto.getLeaveEarlyCertificate()));
			// 早退コメント
			list.add(getAttendanceCorrectionDto(TimeConst.CODE_ATTENDANCE_ITEM_NAME_LEAVEEARLYCOMMENT, hyphen,
					newDto.getLeaveEarlyComment()));
		} else {
			// 勤務形態
			if (!oldDto.getWorkTypeCode().equals(newDto.getWorkTypeCode())) {
				list.add(getAttendanceCorrectionDto(TimeConst.CODE_ATTENDANCE_ITEM_NAME_WORKTYPECODE,
						oldDto.getWorkTypeCode(), newDto.getWorkTypeCode()));
			}
			// 出勤時刻
			if (oldDto.getStartTime().getTime() != newDto.getStartTime().getTime()) {
				list.add(getAttendanceCorrectionDto(TimeConst.CODE_ATTENDANCE_ITEM_NAME_STARTTIME,
						DateUtility.getStringTime(oldDto.getStartTime(), oldDto.getWorkDate()),
						DateUtility.getStringTime(newDto.getStartTime(), newDto.getWorkDate())));
			}
			// 退勤時刻
			if (oldDto.getEndTime().getTime() != newDto.getEndTime().getTime()) {
				list.add(getAttendanceCorrectionDto(TimeConst.CODE_ATTENDANCE_ITEM_NAME_ENDTIME,
						DateUtility.getStringTime(oldDto.getEndTime(), oldDto.getWorkDate()),
						DateUtility.getStringTime(newDto.getEndTime(), newDto.getWorkDate())));
			}
			// 実出勤時刻
			if (!newDto.getActualStartTime().equals(oldDto.getActualStartTime())) {
				list.add(getAttendanceCorrectionDto(TimeConst.CODE_ATTENDANCE_ITEM_NAME_ACTUAL_STARTTIME,
						DateUtility.getStringTime(oldDto.getActualStartTime(), oldDto.getWorkDate()),
						DateUtility.getStringTime(newDto.getActualStartTime(), newDto.getWorkDate())));
			}
			// 実退勤時刻
			if (!newDto.getActualEndTime().equals(oldDto.getActualEndTime())) {
				list.add(getAttendanceCorrectionDto(TimeConst.CODE_ATTENDANCE_ITEM_NAME_ACTUAL_ENDTIME,
						DateUtility.getStringTime(oldDto.getActualEndTime(), oldDto.getWorkDate()),
						DateUtility.getStringTime(newDto.getActualEndTime(), newDto.getWorkDate())));
			}
			// 直行
			if (oldDto.getDirectStart() != newDto.getDirectStart()) {
				list.add(getAttendanceCorrectionDto(TimeConst.CODE_ATTENDANCE_ITEM_NAME_DIRECTSTART,
						String.valueOf(oldDto.getDirectStart()), String.valueOf(newDto.getDirectStart())));
			}
			// 直帰
			if (oldDto.getDirectEnd() != newDto.getDirectEnd()) {
				list.add(getAttendanceCorrectionDto(TimeConst.CODE_ATTENDANCE_ITEM_NAME_DIRECTEND,
						String.valueOf(oldDto.getDirectEnd()), String.valueOf(newDto.getDirectEnd())));
			}
			// 勤怠コメント
			if (!oldDto.getTimeComment().equals(newDto.getTimeComment())) {
				list.add(getAttendanceCorrectionDto(TimeConst.CODE_ATTENDANCE_ITEM_NAME_TIMECOMMENT,
						oldDto.getTimeComment(), newDto.getTimeComment()));
			}
			// 遅刻理由
			if (!oldDto.getLateReason().equals(newDto.getLateReason())) {
				getAttendanceCorrectionDto(TimeConst.CODE_ATTENDANCE_ITEM_NAME_LATEREASON, oldDto.getLateReason(),
						newDto.getLateReason());
			}
			// 遅刻証明書
			if (!oldDto.getLateCertificate().equals(newDto.getLateCertificate())) {
				list.add(getAttendanceCorrectionDto(TimeConst.CODE_ATTENDANCE_ITEM_NAME_LATECERTIFICATE,
						oldDto.getLateCertificate(), newDto.getLateCertificate()));
			}
			// 遅刻コメント
			if (!oldDto.getLateComment().equals(newDto.getLateComment())) {
				list.add(getAttendanceCorrectionDto(TimeConst.CODE_ATTENDANCE_ITEM_NAME_LATECOMMENT,
						oldDto.getLateComment(), newDto.getLateComment()));
			}
			// 早退理由
			if (!oldDto.getLeaveEarlyReason().equals(newDto.getLeaveEarlyReason())) {
				list.add(getAttendanceCorrectionDto(TimeConst.CODE_ATTENDANCE_ITEM_NAME_LEAVEEARLYREASON,
						oldDto.getLeaveEarlyReason(), newDto.getLeaveEarlyReason()));
			}
			// 早退証明書
			if (!oldDto.getLeaveEarlyCertificate().equals(newDto.getLeaveEarlyCertificate())) {
				list.add(getAttendanceCorrectionDto(TimeConst.CODE_ATTENDANCE_ITEM_NAME_LEAVEEARLYCERTIFICATE,
						oldDto.getLeaveEarlyCertificate(), newDto.getLeaveEarlyCertificate()));
			}
			// 早退コメント
			if (!oldDto.getLeaveEarlyComment().equals(newDto.getLeaveEarlyComment())) {
				list.add(getAttendanceCorrectionDto(TimeConst.CODE_ATTENDANCE_ITEM_NAME_LEAVEEARLYCOMMENT,
						oldDto.getLeaveEarlyComment(), newDto.getLeaveEarlyComment()));
			}
		}
		return list;
	}
	
	private AttendanceCorrectionDtoInterface getAttendanceCorrectionDto(String type, String before, String after) {
		// 項目に設定しない項目
		final String noCeck = "0";
		if (TimeConst.CODE_ATTENDANCE_ITEM_NAME_DIRECTSTART.equals(type)
				|| TimeConst.CODE_ATTENDANCE_ITEM_NAME_DIRECTEND.equals(type)) {
			if (noCeck.equals(after)) {
				return null;
			}
		} else {
			if (after.isEmpty()) {
				return null;
			}
		}
		// 項目設定
		AttendanceCorrectionDtoInterface dto = getInitDto();
		dto.setCorrectionType(type);
		dto.setCorrectionBefore(before);
		dto.setCorrectionAfter(after);
		return dto;
	}
	
	/**
	 * 手当て修正情報を設定する。
	 * @param dto 対象DTO
	 * @param oldAllowanceDto 修正前データ
	 * @param newAllowanceDto 修正後データ
	 * @return 修正コメント
	 */
	protected AttendanceCorrectionDtoInterface setChangePointAllowance(AttendanceCorrectionDtoInterface dto,
			AllowanceDtoInterface oldAllowanceDto, AllowanceDtoInterface newAllowanceDto) {
		if (newAllowanceDto.getAllowanceCode().equals(TimeConst.CODE_ALLOWANCE_CODE_INFO1)) {
			// 手当てコード1の場合
			setAllowanceColection(dto, oldAllowanceDto, newAllowanceDto,
					TimeConst.CODE_ATTENDANCE_ITEM_NAME_ALLOWANCE1);
		}
		if (newAllowanceDto.getAllowanceCode().equals(TimeConst.CODE_ALLOWANCE_CODE_INFO2)) {
			// 手当てコード2の場合
			setAllowanceColection(dto, oldAllowanceDto, newAllowanceDto,
					TimeConst.CODE_ATTENDANCE_ITEM_NAME_ALLOWANCE2);
		}
		if (newAllowanceDto.getAllowanceCode().equals(TimeConst.CODE_ALLOWANCE_CODE_INFO3)) {
			// 手当てコード3の場合
			setAllowanceColection(dto, oldAllowanceDto, newAllowanceDto,
					TimeConst.CODE_ATTENDANCE_ITEM_NAME_ALLOWANCE3);
		}
		if (newAllowanceDto.getAllowanceCode().equals(TimeConst.CODE_ALLOWANCE_CODE_INFO4)) {
			// 手当てコード4の場合
			setAllowanceColection(dto, oldAllowanceDto, newAllowanceDto,
					TimeConst.CODE_ATTENDANCE_ITEM_NAME_ALLOWANCE4);
		}
		if (newAllowanceDto.getAllowanceCode().equals(TimeConst.CODE_ALLOWANCE_CODE_INFO5)) {
			// 手当てコード5の場合
			setAllowanceColection(dto, oldAllowanceDto, newAllowanceDto,
					TimeConst.CODE_ATTENDANCE_ITEM_NAME_ALLOWANCE5);
		}
		if (newAllowanceDto.getAllowanceCode().equals(TimeConst.CODE_ALLOWANCE_CODE_INFO6)) {
			// 手当てコード6の場合
			setAllowanceColection(dto, oldAllowanceDto, newAllowanceDto,
					TimeConst.CODE_ATTENDANCE_ITEM_NAME_ALLOWANCE6);
		}
		if (newAllowanceDto.getAllowanceCode().equals(TimeConst.CODE_ALLOWANCE_CODE_INFO7)) {
			// 手当てコード7の場合
			setAllowanceColection(dto, oldAllowanceDto, newAllowanceDto,
					TimeConst.CODE_ATTENDANCE_ITEM_NAME_ALLOWANCE7);
		}
		if (newAllowanceDto.getAllowanceCode().equals(TimeConst.CODE_ALLOWANCE_CODE_INFO8)) {
			// 手当てコード8の場合
			setAllowanceColection(dto, oldAllowanceDto, newAllowanceDto,
					TimeConst.CODE_ATTENDANCE_ITEM_NAME_ALLOWANCE8);
		}
		if (newAllowanceDto.getAllowanceCode().equals(TimeConst.CODE_ALLOWANCE_CODE_INFO9)) {
			// 手当てコード9の場合
			setAllowanceColection(dto, oldAllowanceDto, newAllowanceDto,
					TimeConst.CODE_ATTENDANCE_ITEM_NAME_ALLOWANCE9);
		}
		if (newAllowanceDto.getAllowanceCode().equals(TimeConst.CODE_ALLOWANCE_CODE_INF10)) {
			// 手当てコード10の場合
			setAllowanceColection(dto, oldAllowanceDto, newAllowanceDto,
					TimeConst.CODE_ATTENDANCE_ITEM_NAME_ALLOWANCE10);
		}
		return dto;
	}
	
	/**
	 * 手当ての修正情報を設定する。
	 * @param dto 対象DTO
	 * @param oldAllowanceDto 修正前データ
	 * @param newAllowanceDto 修正後データ
	 * @param allowanceType 手当区分
	 */
	protected void setAllowanceColection(AttendanceCorrectionDtoInterface dto, AllowanceDtoInterface oldAllowanceDto,
			AllowanceDtoInterface newAllowanceDto, String allowanceType) {
		dto.setCorrectionType(allowanceType);
		dto.setCorrectionBefore(mospParams.getName("Hyphen"));
		dto.setCorrectionAfter(String.valueOf(newAllowanceDto.getAllowance()));
		if (oldAllowanceDto != null) {
			if (oldAllowanceDto.getAllowanceCode() != null) {
				dto.setCorrectionBefore(String.valueOf(oldAllowanceDto.getAllowance()));
			}
		}
	}
	
	/**
	 * 登録情報の妥当性を確認する。
	 * @param dto 対象DTO
	 * @param oldDto 修正前データ
	 * @param newDto 修正後データ
	 * @return 修正コメント
	 */
	protected AttendanceCorrectionDtoInterface setChangePointRest(AttendanceCorrectionDtoInterface dto,
			RestDtoInterface oldDto, RestDtoInterface newDto) {
		final String exceptTime = "00:00";
		final String hyphen = mospParams.getName("Hyphen");
		// 項目に設定しない項目
		if (exceptTime.equals(DateUtility.getStringTime(newDto.getRestStart()))
				|| exceptTime.equals(DateUtility.getStringTime(newDto.getRestEnd()))) {
			return null;
		}
		if (newDto.getRest() == 1) {
			dto.setCorrectionType(TimeConst.CODE_ATTENDANCE_ITEM_NAME_BREAK1);
		}
		if (newDto.getRest() == 2) {
			dto.setCorrectionType(TimeConst.CODE_ATTENDANCE_ITEM_NAME_BREAK2);
		}
		if (newDto.getRest() == 3) {
			dto.setCorrectionType(TimeConst.CODE_ATTENDANCE_ITEM_NAME_BREAK3);
		}
		if (newDto.getRest() == 4) {
			dto.setCorrectionType(TimeConst.CODE_ATTENDANCE_ITEM_NAME_BREAK4);
		}
		if (newDto.getRest() == 5) {
			dto.setCorrectionType(TimeConst.CODE_ATTENDANCE_ITEM_NAME_BREAK5);
		}
		if (newDto.getRest() == 6) {
			dto.setCorrectionType(TimeConst.CODE_ATTENDANCE_ITEM_NAME_BREAK6);
		}
		dto.setCorrectionAfter(DateUtility.getStringTime(newDto.getRestStart(), newDto.getWorkDate()) + hyphen
				+ DateUtility.getStringTime(newDto.getRestEnd(), newDto.getWorkDate()));
		
		if (null == oldDto.getRestStart()) {
			dto.setCorrectionBefore(hyphen);
		} else {
			dto.setCorrectionBefore(DateUtility.getStringTime(oldDto.getRestStart(), oldDto.getWorkDate()) + hyphen
					+ DateUtility.getStringTime(oldDto.getRestEnd(), oldDto.getWorkDate()));
		}
		return dto;
	}
	
	/**
	 * 登録情報の妥当性を確認する。
	 * @param dto 対象DTO
	 * @param oldGoOutDto 旧外出データDTO
	 * @param newGoOutDto 新外出データDTO
	 * @return 変更箇所
	 */
	protected AttendanceCorrectionDtoInterface setChangePointGoOut(AttendanceCorrectionDtoInterface dto,
			GoOutDtoInterface oldGoOutDto, GoOutDtoInterface newGoOutDto) {
		final String exceptTime = "00:00";
		final String hyphen = mospParams.getName("Hyphen");
		// 項目に設定しない項目
		if (exceptTime.equals(DateUtility.getStringTime(newGoOutDto.getGoOutStart()))
				|| exceptTime.equals(DateUtility.getStringTime(newGoOutDto.getGoOutEnd()))) {
			return null;
		}
		// 公用
		if (TimeConst.CODE_GO_OUT_PUBLIC == newGoOutDto.getGoOutType()) {
			if (newGoOutDto.getTimesGoOut() == 1) {
				dto.setCorrectionType(TimeConst.CODE_ATTENDANCE_ITEM_NAME_OFFICIAL_GOING_OUT1);
			}
			if (newGoOutDto.getTimesGoOut() == 2) {
				dto.setCorrectionType(TimeConst.CODE_ATTENDANCE_ITEM_NAME_OFFICIAL_GOING_OUT2);
			}
		}
		// 私用
		if (TimeConst.CODE_GO_OUT_PRIVATE == newGoOutDto.getGoOutType()) {
			if (newGoOutDto.getTimesGoOut() == 1) {
				dto.setCorrectionType(TimeConst.CODE_ATTENDANCE_ITEM_NAME_PRIVATE_GOING_OUT1);
			}
			if (newGoOutDto.getTimesGoOut() == 2) {
				dto.setCorrectionType(TimeConst.CODE_ATTENDANCE_ITEM_NAME_PRIVATE_GOING_OUT2);
			}
		}
		if (oldGoOutDto != null) {
			if (oldGoOutDto.getGoOutStart() != null) {
				dto.setCorrectionBefore(
						DateUtility.getStringTime(oldGoOutDto.getGoOutStart(), oldGoOutDto.getWorkDate()) + hyphen
								+ DateUtility.getStringTime(oldGoOutDto.getGoOutEnd(), oldGoOutDto.getWorkDate()));
			} else {
				dto.setCorrectionBefore(hyphen);
			}
		} else {
			dto.setCorrectionBefore(hyphen);
		}
		dto.setCorrectionAfter(DateUtility.getStringTime(newGoOutDto.getGoOutStart(), newGoOutDto.getWorkDate())
				+ hyphen + DateUtility.getStringTime(newGoOutDto.getGoOutEnd(), newGoOutDto.getWorkDate()));
		return dto;
	}
	
	/**
	 * 登録情報の妥当性を確認する。
	 * @param contentsDto 変更内容情報のDTO
	 * @param registerDto 登録用DTO
	 */
	protected void setValueAttendanceCorrection(AttendanceCorrectionDtoInterface contentsDto,
			AttendanceCorrectionDtoInterface registerDto) {
		contentsDto.setCorrectionType(registerDto.getCorrectionType());
		contentsDto.setCorrectionBefore(registerDto.getCorrectionBefore());
		contentsDto.setCorrectionAfter(registerDto.getCorrectionAfter());
	}
	
	@Override
	public void delete(String personalId, Date workDate, int timesWork) throws MospException {
		List<AttendanceCorrectionDtoInterface> list = attendanceCorrectionDao.findForHistory(personalId, workDate,
				timesWork);
		for (AttendanceCorrectionDtoInterface dto : list) {
			// 論理削除
			logicalDelete(attendanceCorrectionDao, dto.getTmdAttendanceCorrectionId());
		}
	}
}
