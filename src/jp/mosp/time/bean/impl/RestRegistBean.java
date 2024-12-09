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
package jp.mosp.time.bean.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.time.base.TimeBean;
import jp.mosp.time.bean.RestRegistBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dao.settings.RestDaoInterface;
import jp.mosp.time.dto.settings.AttendanceDtoInterface;
import jp.mosp.time.dto.settings.RestDtoInterface;
import jp.mosp.time.dto.settings.TimeSettingDtoInterface;
import jp.mosp.time.dto.settings.impl.TmdRestDto;
import jp.mosp.time.entity.TimeDuration;
import jp.mosp.time.utils.TimeUtility;

/**
 * 勤怠データ休憩情報登録クラス。
 */
public class RestRegistBean extends TimeBean implements RestRegistBeanInterface {
	
	/**
	 * 上限値(休憩回数)。<br>
	 */
	public static final int		MAX_REST_TIMES	= 6;
	
	/**
	 * 休憩マスタDAOクラス。<br>
	 */
	protected RestDaoInterface	dao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public RestRegistBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		dao = createDaoInstance(RestDaoInterface.class);
	}
	
	@Override
	public RestDtoInterface getInitDto() {
		return new TmdRestDto();
	}
	
	@Override
	public void regist(RestDtoInterface dto) throws MospException {
		if (dao.findForKey(dto.getPersonalId(), dto.getWorkDate(), dto.getTimesWork(), dto.getRest()) == null) {
			// 新規登録
			insert(dto);
		} else {
			// 履歴追加
			update(dto);
		}
	}
	
	@Override
	public void insert(RestDtoInterface dto) throws MospException {
		// DTOの妥当性確認
		validate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 新規登録情報の検証
		checkInsert(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setTmdRestId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void update(RestDtoInterface dto) throws MospException {
		// DTOの妥当性確認
		validate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 履歴更新情報の検証
		checkUpdate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 論理削除
		logicalDelete(dao, dto.getTmdRestId());
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setTmdRestId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void delete(String personalId, Date workDate, int timesWork) throws MospException {
		List<RestDtoInterface> list = dao.findForList(personalId, workDate, timesWork);
		for (RestDtoInterface dto : list) {
			checkDelete((RestDtoInterface)dao.findForKey(dto.getTmdRestId(), true));
			if (mospParams.hasErrorMessage()) {
				// エラーが存在したら削除処理をしない
				continue;
			}
			// 論理削除
			logicalDelete(dao, dto.getTmdRestId());
		}
	}
	
	@Override
	public void delete(String personalId, Date workDate, int timesWork, int rest) throws MospException {
		RestDtoInterface dto = dao.findForKey(personalId, workDate, timesWork, rest);
		checkDelete((RestDtoInterface)dao.findForKey(dto.getTmdRestId(), true));
		if (mospParams.hasErrorMessage()) {
			// エラーが存在したら削除処理をしない
			return;
		}
		// 論理削除
		logicalDelete(dao, dto.getTmdRestId());
	}
	
	/**
	 * 新規登録時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkInsert(RestDtoInterface dto) throws MospException {
		// 対象レコードの有効日が重複していないかを確認
		checkDuplicateInsert(dao.findForHistory(dto.getPersonalId(), dto.getWorkDate(), dto.getRest(),
				dto.getRestStart(), dto.getRestEnd()));
	}
	
	/**
	 * 履歴更新時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkUpdate(RestDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getTmdRestId());
	}
	
	/**
	 * 削除時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkDelete(RestDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getTmdRestId());
	}
	
	/**
	 * 登録情報の妥当性を確認する。
	 * @param dto 対象DTO
	 */
	protected void validate(RestDtoInterface dto) {
		// 処理無し
	}
	
	@Override
	public int getCalcRestTime(Date restStartTime, Date restEndTime, TimeSettingDtoInterface timeSettingDto) {
		// 休憩開始時刻、休憩終了時刻、勤怠設定情報がない場合
		if (restStartTime == null || restEndTime == null || timeSettingDto == null) {
			return 0;
		}
		// 休憩開始時刻丸めた時間を取得
		Date roundRestStartTime = TimeUtility.getRoundMinute(restStartTime, timeSettingDto.getRoundDailyRestStart(),
				timeSettingDto.getRoundDailyRestStartUnit());
		// 休憩終了時刻丸めた時間を取得
		Date roundRestEndTime = TimeUtility.getRoundMinute(restEndTime, timeSettingDto.getRoundDailyRestEnd(),
				timeSettingDto.getRoundDailyRestEndUnit());
		// 休憩時間を丸めた時刻を取得
		return TimeUtility.getRoundMinute(TimeUtility.getDifferenceMinutes(roundRestStartTime, roundRestEndTime),
				timeSettingDto.getRoundDailyRestTime(), timeSettingDto.getRoundDailyRestTimeUnit());
	}
	
	@Override
	public void setRestStartEndTime(Date restStartTime, Date restEndTime, AttendanceDtoInterface attendanceDto,
			RestDtoInterface restDto) throws MospException {
		// 追加業務ロジック処理を行った場合
		if (doAdditionalLogic(TimeConst.CODE_KEY_ADD_RESTREGISTBEAN_SETRESTSTARTENDTIME, restStartTime, restEndTime,
				attendanceDto, restDto)) {
			// 通常の処理は行わない
			return;
		}
		// 休憩開始があり休憩終了がある場合
		if (restStartTime != null && restEndTime != null) {
			// 始業時刻が休憩開始時刻より後の場合
			if (attendanceDto.getStartTime() != null && attendanceDto.getStartTime().after(restStartTime)) {
				// 始業時刻を休憩開始時刻とする
				restStartTime = attendanceDto.getStartTime();
			}
			// 終業時刻が休憩終了時刻より前の場合
			if (attendanceDto.getEndTime() != null && attendanceDto.getEndTime().before(restEndTime)) {
				// 終業時刻を休憩終了時刻とする
				restEndTime = attendanceDto.getEndTime();
			}
			// 休憩終了時刻が休憩開始時刻より前の場合
			if (restEndTime.before(restStartTime)) {
				// 休憩開始時刻を休憩終了時刻とする
				restEndTime = restStartTime;
			}
			if (!restStartTime.equals(restEndTime)) {
				// 休憩開始時刻
				restDto.setRestStart(restStartTime);
				// 休憩終了時刻
				restDto.setRestEnd(restEndTime);
			}
		}
	}
	
	@Override
	public void registRests(String personalId, Date workDate, Map<Integer, TimeDuration> rests,
			TimeSettingDtoInterface timeSetting) throws MospException {
		// 登録されている休憩情報のリストを取得
		List<RestDtoInterface> currentDtos = dao.findForList(personalId, workDate, TIMES_WORK_DEFAULT);
		// 休憩回数毎に処理
		for (int i = 0; i < MAX_REST_TIMES; i++) {
			// 休憩回数を準備
			int rest = i + 1;
			// 休憩情報(時間間隔)群から休憩回数の休憩情報(時間間隔)を取得
			TimeDuration duration = getDuration(rests, rest);
			// 登録されている休憩情報を取得
			RestDtoInterface currentDto = getRest(currentDtos, rest);
			// 登録されている休憩情報が存在しない場合
			if (currentDto == null) {
				// 休憩情報を新規登録
				insertRest(personalId, workDate, rest, duration, timeSetting);
				// 次の休憩回数へ
				continue;
			}
			// 更新が必要無い場合
			if (isUpdateNeeded(currentDto, duration, timeSetting) == false) {
				// 次の休憩回数へ
				continue;
			}
			// 休憩情報に時間間隔の休憩を設定
			setRestDuration(currentDto, duration, timeSetting);
			// 更新
			update(currentDto);
		}
	}
	
	/**
	 * 休憩情報を新規登録する。<br>
	 * @param personalId  個人ID
	 * @param workDate    勤務日
	 * @param rest        休憩回数
	 * @param duration    休憩情報(時間間隔)
	 * @param timeSetting 勤怠設定情報(丸め用)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void insertRest(String personalId, Date workDate, int rest, TimeDuration duration,
			TimeSettingDtoInterface timeSetting) throws MospException {
		// 休憩情報を準備
		RestDtoInterface dto = getInitDto();
		// 休憩情報に情報を設定
		dto.setPersonalId(personalId);
		dto.setWorkDate(workDate);
		dto.setTimesWork(TIMES_WORK_DEFAULT);
		dto.setRest(rest);
		// 休憩情報に時間間隔の休憩を設定
		setRestDuration(dto, duration, timeSetting);
		// 新規登録
		insert(dto);
	}
	
	/**
	 * 休憩情報の更新が必要であるかを確認する。<br>
	 * @param dto         休憩情報
	 * @param duration    休憩情報(時間間隔)
	 * @param timeSetting 勤怠設定情報(丸め用)
	 * @return 確認結果(true：更新が必要である、false：そうでない)
	 */
	protected boolean isUpdateNeeded(RestDtoInterface dto, TimeDuration duration, TimeSettingDtoInterface timeSetting) {
		// 勤務日を取得
		Date workDate = dto.getWorkDate();
		// 休憩情報(時間間隔用)を準備
		RestDtoInterface newDto = getInitDto();
		// 休憩情報(時間間隔用)に勤務日を設定
		newDto.setWorkDate(workDate);
		// 休憩情報に時間間隔の休憩を設定
		setRestDuration(newDto, duration, timeSetting);
		// 休憩情報と時間間隔で休憩開始時刻が異なる場合
		if (DateUtility.isSame(dto.getRestStart(), newDto.getRestStart()) == false) {
			// 更新が必要であると判断
			return true;
		}
		// 休憩情報と時間間隔で休憩修了時刻が異なる場合
		if (DateUtility.isSame(dto.getRestEnd(), newDto.getRestEnd()) == false) {
			// 更新が必要であると判断
			return true;
		}
		// 休憩情報と時間間隔で休憩時間が異なる場合
		if (dto.getRestTime() != newDto.getRestTime()) {
			// 更新が必要であると判断
			return true;
		}
		// 更新が不要であると判断
		return false;
	}
	
	/**
	 * 休憩情報に時間間隔の休憩を設定する。<br>
	 * <br>
	 * 設定する項目は次の通り。<br>
	 * ・休憩開始時刻<br>
	 * ・休憩終了時刻<br>
	 * ・休憩時間<br>
	 * <br>
	 * @param dto         休憩情報
	 * @param duration    休憩情報(時間間隔)
	 * @param timeSetting 勤怠設定情報(丸め用)
	 */
	protected void setRestDuration(RestDtoInterface dto, TimeDuration duration, TimeSettingDtoInterface timeSetting) {
		// 勤務日を取得
		Date workDate = dto.getWorkDate();
		// 休憩開始時刻と休憩修了時刻と休憩時間を準備
		Date restStart = DateUtility.addMinute(workDate, duration.getStartTime());
		Date restEnd = DateUtility.addMinute(workDate, duration.getEndTime());
		int restTime = getCalcRestTime(restStart, restEnd, timeSetting);
		// 休憩情報に設定
		dto.setRestStart(restStart);
		dto.setRestEnd(restEnd);
		dto.setRestTime(restTime);
	}
	
	/**
	 * 休憩情報(時間間隔)群から休憩回数の休憩情報(時間間隔)を取得する。<br>
	 * <br>
	 * 存在しない場合は妥当でない時間間隔を取得する。<br>
	 * <br>
	 * @param rests 休憩情報(時間間隔)群
	 * @param rest  休憩回数
	 * @return 休憩回数の休憩情報(時間間隔)
	 */
	protected TimeDuration getDuration(Map<Integer, TimeDuration> rests, int rest) {
		// 休憩回数の休憩情報(時間間隔)が無い場合
		if (rests.size() < rest) {
			// 妥当でない時間間隔を取得
			return TimeDuration.getInvalid();
		}
		// 休憩情報(時間間隔)群から休憩回数の休憩情報(時間間隔)を取得
		return new ArrayList<TimeDuration>(rests.values()).get(rest - 1);
	}
	
	/**
	 * 休憩情報群から休憩回数の休憩情報を取得する。<br>
	 * <br>
	 * 存在しない場合はnullを取得する。<br>
	 * <br>
	 * @param dtos 休憩情報群
	 * @param rest 休憩回数
	 * @return 休憩回数の休憩情報
	 */
	protected RestDtoInterface getRest(Collection<RestDtoInterface> dtos, int rest) {
		// 休憩情報毎に処理
		for (RestDtoInterface dto : dtos) {
			// 休憩回数が同じである場合
			if (dto.getRest() == rest) {
				// 休憩回数の休憩情報を取得
				return dto;
			}
		}
		// nullを取得(存在しない場合)
		return null;
	}
	
}
