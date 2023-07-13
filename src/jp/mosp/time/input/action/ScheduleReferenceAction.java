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
package jp.mosp.time.input.action;

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseVo;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.time.base.AttendanceListBaseAction;
import jp.mosp.time.base.TimeAction;
import jp.mosp.time.bean.AttendanceListReferenceBeanInterface;
import jp.mosp.time.dto.settings.impl.AttendanceListDto;
import jp.mosp.time.input.vo.ScheduleReferenceVo;

/**
 * 月単位の勤務形態、残業・休暇の申請承認状況について確認を行う。<br>
 * <br>
 * 以下のコマンドを扱う。<br>
 * <ul><li>
 * {@link #CMD_SHOW}
 * </li><li>
 * {@link #CMD_SELECT_SHOW}
 * </li><li>
 * {@link #CMD_SEARCH}
 * </li><li>
 * {@link #CMD_OUTPUT}
 * </li></ul>
 */
public class ScheduleReferenceAction extends AttendanceListBaseAction {
	
	/**
	 * 表示コマンド。<br>
	 * <br>
	 * 現在ログインしているユーザの予定確認画面へ遷移する。<br>
	 * サーバ日付より現在の年月を取得し、その予定を表示する。
	 */
	public static final String	CMD_SHOW		= "TM1300";
	
	/**
	 * 選択表示コマンド。<br>
	 * <br>
	 * 部下一覧画面にて選択したユーザのサーバ日付時点年月の予定情報を表示する。<br>
	 */
	public static final String	CMD_SELECT_SHOW	= "TM1301";
	
	/**
	 * 検索コマンド。<br>
	 * <br>
	 * 操作者の指定にしたがって表示する対象年月を変更する。
	 * 前月・翌月ボタンといったものは現在表示している年月を基に画面遷移を行う。<br>
	 */
	public static final String	CMD_SEARCH		= "TM1302";
	
	/**
	 * 帳票出力コマンド。<br>
	 * <br>
	 * 現在表示しているスケジュール情報を帳票として出力する。<br>
	 */
	public static final String	CMD_OUTPUT		= "TM1396";
	
	
	/**
	 * {@link TimeAction#TimeAction()}を実行する。<br>
	 */
	public ScheduleReferenceAction() {
		super();
	}
	
	@Override
	protected BaseVo getSpecificVo() {
		return new ScheduleReferenceVo();
	}
	
	@Override
	public void action() throws MospException {
		// コマンド毎の処理
		if (mospParams.getCommand().equals(CMD_SHOW)) {
			// 表示
			prepareVo(false, false);
			show();
		} else if (mospParams.getCommand().equals(CMD_SELECT_SHOW)) {
			// 選択表示
			prepareVo(false, false);
			select();
		} else if (mospParams.getCommand().equals(CMD_SEARCH)) {
			// 検索
			prepareVo();
			search();
		} else if (mospParams.getCommand().equals(CMD_OUTPUT)) {
			// 帳票出力
			prepareVo(true, false);
			output();
		} else {
			throwInvalidCommandException();
		}
	}
	
	/**
	 * 初期表示処理を行う。<br>
	 * 対象個人IDは、ログインユーザ情報から取得する。<br>
	 * 対象日は、システム日付から取得する。<br>
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void show() throws MospException {
		// 個人ID取得(ログインユーザ情報から)
		String personalId = mospParams.getUser().getPersonalId();
		// 対象日取得(システム日付)
		Date targetDate = getSystemDate();
		// VOに個人IDと対象年月を設定(対象年月は仮)
		initVoFields(personalId, DateUtility.getYear(targetDate), DateUtility.getMonth(targetDate));
		// 勤怠一覧情報参照クラス取得
		AttendanceListReferenceBeanInterface attendanceListReference = timeReference().attendanceList();
		// 勤怠一覧情報リスト(予定)を取得
		List<AttendanceListDto> list = attendanceListReference.getScheduleList(personalId, targetDate);
		// 勤怠一覧リストの内容をVOに設定
		setVoList(list);
		// 勤怠一覧リストの基本情報をVOのフィールドに設定
		setVoFields(list);
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			// データ無しメッセージを設定
			PfMessageUtility.addMessageNoData(mospParams);
		}
	}
	
	/**
	 * 選択表示処理を行う。<br>
	 * MosP処理情報から対象年月及び個人IDを取得し、検索する。<br>
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void select() throws MospException {
		// 対象個人ID取得
		String personalId = getTargetPersonalId();
		/// 対象年月取得
		int targetYear = getTargetYear();
		int targetMonth = getTargetMonth();
		// VOに個人IDと対象年月を設定
		initVoFields(personalId, targetYear, targetMonth);
		// 勤怠一覧情報参照クラス取得
		AttendanceListReferenceBeanInterface attendanceListReference = timeReference().attendanceList();
		// 勤怠一覧情報リスト(予定)を取得
		List<AttendanceListDto> list = attendanceListReference.getScheduleList(personalId, targetYear, targetMonth);
		// 勤怠一覧リストの内容をVOに設定
		setVoList(list);
		// 勤怠一覧リストの基本情報をVOのフィールドに設定
		setVoFields(list);
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			// データ無しメッセージを設定
			PfMessageUtility.addMessageNoData(mospParams);
		}
	}
	
	/**
	 * 年月による検索を行う。<br>
	 * 対象個人IDはVOに保持されているものを用いる。<br>
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void search() throws MospException {
		// VO準備
		ScheduleReferenceVo vo = (ScheduleReferenceVo)mospParams.getVo();
		// 個人ID取得(VOから)
		String personalId = vo.getPersonalId();
		// パラメータ受取(対象年月)
		String year = getTransferredYear();
		String month = getTransferredMonth();
		// 年月確認
		if (year == null || month == null) {
			// VOから年月を取得
			year = vo.getPltSelectYear();
			month = vo.getPltSelectMonth();
		}
		// 対象年月と締日を取得
		int targetYear = getInt(year);
		int targetMonth = getInt(month);
		int cutoffDate = vo.getCutoffDate();
		// VOに個人IDと対象年月を設定
		initVoFields(personalId, targetYear, targetMonth);
		// 勤怠一覧情報参照クラス取得
		AttendanceListReferenceBeanInterface bean = timeReference().attendanceList();
		// 勤怠一覧情報リスト(予定)を取得
		List<AttendanceListDto> list = bean.getScheduleList(personalId, targetYear, targetMonth, cutoffDate);
		// 勤怠一覧リストの内容をVOに設定
		setVoList(list);
		// 勤怠一覧リストの基本情報をVOのフィールドに設定
		setVoFields(list);
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			// データ無しメッセージを設定
			PfMessageUtility.addMessageNoData(mospParams);
		}
	}
	
	/**
	 * 帳票を作成し、送出ファイルとして設定する。<br>
	 * 対象個人IDはVOに保持されているものを用いる。<br>
	 * 対象年月は、VOに保持されているものを用いる。<br>
	 * @throws MospException 帳票の作成に失敗した場合
	 */
	protected void output() throws MospException {
		// VO準備
		ScheduleReferenceVo vo = (ScheduleReferenceVo)mospParams.getVo();
		// 個人ID取得(VOから)
		String personalId = vo.getPersonalId();
		// 対象年月取得(VOから)
		int year = getInt(vo.getPltSelectYear());
		int month = getInt(vo.getPltSelectMonth());
		// 年月で予定簿を作成
		timeReference().scheduleBook().makeScheduleBook(personalId, year, month);
		// エラー確認
		if (mospParams.hasErrorMessage()) {
			// データ無しメッセージを設定
			PfMessageUtility.addMessageNoData(mospParams);
		}
	}
	
	/**
	 * 継承元のメソッドを実行した後、VOにカレンダー名称を設定する。<br>
	 */
	@Override
	protected void setVoFields(List<AttendanceListDto> list) throws MospException {
		// 継承元のメソッド実行
		super.setVoFields(list);
		// VO準備
		ScheduleReferenceVo vo = (ScheduleReferenceVo)mospParams.getVo();
		// VOに値を設定
		vo.setLblEndRecordTime(false);
		vo.setLblStartRecordTime(false);
		// 勤怠一覧情報(最後の要素)を取得
		AttendanceListDto dto = MospUtility.getLastValue(list);
		// 勤怠一覧情報(最後の要素)を取得を取得できなかった場合
		if (MospUtility.isEmpty(dto)) {
			// 処理終了
			return;
		}
		// カレンダコード対象締期間最終日を取得
		String scheduleCode = dto.getScheduleCode();
		Date lastDate = dto.getWorkDate();
		// カレンダ名称を取得
		String scheculeName = timeReference().scheduleUtil().getScheduleName(scheduleCode, lastDate);
		// VOのカレンダー名称を設定
		vo.setLblApplicationSchedule(scheculeName);
	}
	
}
