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
package jp.mosp.time.file.action;

import java.util.Date;

import jp.mosp.framework.base.BaseVo;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.file.base.ExportListAction;
import jp.mosp.platform.file.base.ExportListVo;
import jp.mosp.platform.utils.MonthUtility;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.time.base.TimeAction;
import jp.mosp.time.base.TimeReferenceBeanHandlerInterface;
import jp.mosp.time.bean.CutoffReferenceBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.file.vo.TimeExportListVo;
import jp.mosp.time.utils.TimeMessageUtility;
import jp.mosp.time.utils.TimeNamingUtility;

/**
 * 勤怠エクスポートの実行。エクスポートマスタの管理を行う。<br>
 * <br>
 * 以下のコマンドを扱う。<br>
 * <ul><li>
 * {@link #CMD_SHOW}
 * </li><li>
 * {@link #CMD_SEARCH}
 * </li><li>
 * {@link #CMD_RE_SHOW}
 * </li><li>
 * {@link #CMD_SORT}
 * </li><li>
 * {@link #CMD_PAGE}
 * </li><li>
 * {@link #CMD_SET_EXPORT}
 * </li><li>
 * {@link #CMD_SET_ACTIVATION_DATE}
 * </li></ul>
 */
public class TimeExportListAction extends ExportListAction {
	
	/**
	 * 表示コマンド。<br>
	 * <br>
	 * 初期表示を行う。<br>
	 */
	public static final String	CMD_SHOW				= "TM3310";
	
	/**
	 * 検索コマンド。<br>
	 * <br>
	 * 検索欄に入力された各種情報項目を基に検索を行い、条件に沿ったエクスポートマスタ情報の一覧表示を行う。<br>
	 * 一覧表示の際にはエクスポートコードでソートを行う。<br>
	 */
	public static final String	CMD_SEARCH				= "TM3312";
	
	/**
	 * 再表示コマンド。<br>
	 * <br>
	 * この画面よりも奥の階層の画面から再び遷移した際に各画面で扱っている情報を最新のものに反映させ、検索結果の一覧表示にも反映させる。<br>
	 */
	public static final String	CMD_RE_SHOW				= "TM3313";
	
	/**
	 * ソートコマンド。<br>
	 * <br>
	 * それぞれのレコードの値を比較して一覧表示欄の各情報毎に並び替えを行う。
	 * これが実行される度に並び替えが昇順・降順と交互に切り替わる。<br>
	 */
	public static final String	CMD_SORT				= "TM3318";
	
	/**
	 * ページ繰りコマンド。<br>
	 * <br>
	 * 検索処理を行った際に検索結果が100件を超えた場合に分割されるページ間の遷移を行う。<br>
	 */
	public static final String	CMD_PAGE				= "TM3319";
	
	/**
	 * データ区分決定コマンド。<br>
	 * <br>
	 * エクスポートマスタ一覧から選択したレコードのデータ区分の情報を取得し、勤怠関連のテーブルであれば有効日入力欄とその決定ボタンを、<br>
	 * 人事関連のテーブルであれば出力期間入力欄とその決定ボタンをそれぞれ読取専用にする。<br>
	 */
	public static final String	CMD_SET_EXPORT			= "TM3380";
	
	/**
	 * 出力期間決定コマンド。<br>
	 * <br>
	 * 入力されている出力期間日付をチェックし、締日・勤務地・雇用契約・所属・職位の各マスタからその期間内に有効なレコードを検索し、<br>
	 * それぞれのプルダウンに表示する。出力期間決定時は出力期間、有効日共に入力欄が読取専用となる。<br>
	 */
	public static final String	CMD_SET_ACTIVATION_DATE	= "TM3381";
	
	
	/**
	 * {@link ExportListAction#ExportListAction()}を実行する。<br>
	 */
	public TimeExportListAction() {
		super();
		// パンくずリスト用コマンド設定
		topicPathCommand = CMD_RE_SHOW;
	}
	
	@Override
	protected void setViewPath(String className) {
		super.setViewPath(className);
		mospParams.addJsFile(TimeAction.PATH_TIME_JS);
	}
	
	@Override
	protected BaseVo getSpecificVo() {
		return new TimeExportListVo();
	}
	
	@Override
	public void action() throws MospException {
		// コマンド毎の処理
		if (mospParams.getCommand().equals(CMD_SHOW)) {
			// 表示
			prepareVo(false, false);
			show();
		} else if (mospParams.getCommand().equals(CMD_SEARCH)) {
			// 検索処理
			prepareVo();
			search();
		} else if (mospParams.getCommand().equals(CMD_RE_SHOW)) {
			// 再表示
			prepareVo(true, false);
			search();
		} else if (mospParams.getCommand().equals(CMD_SORT)) {
			// ソート
			prepareVo();
			sort();
		} else if (mospParams.getCommand().equals(CMD_PAGE)) {
			// ページ繰り
			prepareVo();
			page();
		} else if (mospParams.getCommand().equals(CMD_SET_EXPORT)) {
			// データ区分決定
			prepareVo();
			setExport();
		} else if (mospParams.getCommand().equals(CMD_SET_ACTIVATION_DATE)) {
			// 有効日決定
			prepareVo();
			setActivationDate();
		}
	}
	
	/**
	 * エクスポート一覧共通JSP用コマンド及びデータ区分コードキーをVOに設定する。<br>
	 */
	protected void setExportListInfo() {
		// VO(エクスポート一覧共通VO)取得
		ExportListVo vo = (ExportListVo)mospParams.getVo();
		// データ区分コードキー設定(勤怠情報データエクスポート区分)
		vo.setTableTypeCodeKey(TimeConst.CODE_KEY_TIME_EXPORT_TABLE_TYPE);
		// 再表示コマンド設定
		vo.setReShowCommand(CMD_RE_SHOW);
		// 検索コマンド設定
		vo.setSearchCommand(CMD_SEARCH);
		// 並び替えコマンド設定
		vo.setSortCommand(CMD_SORT);
		// データ区分設定コマンド設定
		vo.setSetExportCommand(CMD_SET_EXPORT);
		// ページ繰り設定
		setPageInfo(CMD_PAGE, getListLength());
	}
	
	/**
	 * 初期表示処理を行う。<br>
	 * @throws MospException プルダウンの取得に失敗した場合
	 */
	protected void show() throws MospException {
		// エクスポート一覧共通JSP用コマンド及びデータ区分をVOに設定
		setExportListInfo();
		// エクスポート一覧共通VO初期値設定
		initExportListVoFields();
		// 出力条件を初期化
		initOutputCondition();
	}
	
	/**
	 * 有効日設定処理を行う。<br>
	 * 保持有効日モードを確認し、プルダウンの再設定を行う。<br>
	 * @throws MospException プルダウンの取得に失敗した場合
	 */
	protected void setActivationDate() throws MospException {
		// VO取得
		TimeExportListVo vo = (TimeExportListVo)mospParams.getVo();
		// 現在の有効日モードを確認
		if (vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_CHANGING)) {
			// 開始年月と終了年月の比較
			if (isActivationDateValid() == false) {
				
				return;
			}
			// 有効日モード設定
			vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_FIXED);
		} else {
			// 有効日モード設定
			vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
		}
		// プルダウン設定
		setPulldown();
	}
	
	/**
	 * プルダウンの設定を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setPulldown() throws MospException {
		// VO取得
		TimeExportListVo vo = (TimeExportListVo)mospParams.getVo();
		// プルダウン初期化
		vo.setAryPltWorkPlace(getInputActivateDatePulldown());
		vo.setAryPltEmployment(getInputActivateDatePulldown());
		vo.setAryPltSection(getInputActivateDatePulldown());
		vo.setAryPltPosition(getInputActivateDatePulldown());
		vo.setAryPltCutoff(getInputActivateDatePulldown());
		// 有効日フラグ確認
		if (vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_CHANGING)) {
			return;
		}
		// プルダウン対象日取得
		Date targetDate = getPulldownTargetDate();
		// 勤怠管理参照用BeanHandler取得(ExportListActionでは扱わないためクラスを指定して取得)
		TimeReferenceBeanHandlerInterface timeReference = (TimeReferenceBeanHandlerInterface)createHandler(
				TimeReferenceBeanHandlerInterface.class);
		// 締日参照クラス取得
		CutoffReferenceBeanInterface cutoffReference = timeReference.cutoff();
		// 締日プルダウン取得
		String[][] aryPltCutoff = cutoffReference.getSelectCodeArray(targetDate);
		// 締日存在確認(締日は出力の際の必須条件)
		if (aryPltCutoff[0][0].isEmpty()) {
			// 有効日モード設定
			vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
			// エラーメッセージを設定
			PfMessageUtility.addErrorNoItem(mospParams, TimeNamingUtility.cutoffDate(mospParams));
			return;
		}
		// 勤務地プルダウン
		String[][] aryPltWorkPlace = reference().workPlace().getCodedAbbrSelectArray(targetDate, true, null);
		// 雇用契約プルダウン
		String[][] aryPltEmployment = reference().employmentContract().getCodedAbbrSelectArray(targetDate, true, null);
		// 所属プルダウン
		String[][] aryPltSection = reference().section().getCodedSelectArray(targetDate, true, null);
		// 職位プルダウン
		String[][] aryPltPosition = reference().position().getCodedSelectArray(targetDate, true, null);
		// 勤務地設定
		vo.setAryPltWorkPlace(aryPltWorkPlace);
		// 雇用契約設定
		vo.setAryPltEmployment(aryPltEmployment);
		// 所属設定
		vo.setAryPltSection(aryPltSection);
		// 職位設定
		vo.setAryPltPosition(aryPltPosition);
		// 締日プルダウン設定
		vo.setAryPltCutoff(aryPltCutoff);
	}
	
	/**
	 * プルダウン対象日を取得する。<br>
	 * @return プルダウン対象日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected Date getPulldownTargetDate() throws MospException {
		// VO取得
		TimeExportListVo vo = (TimeExportListVo)mospParams.getVo();
		// プルダウン対象日取得
		return MonthUtility.getYearMonthTargetDate(getInt(vo.getTxtStartYear()), getInt(vo.getTxtStartMonth()),
				mospParams);
	}
	
	/**
	 * 出力条件を初期化する。<br>
	 * @throws MospException プルダウンの取得に失敗した場合
	 */
	protected void initOutputCondition() throws MospException {
		// VO取得
		TimeExportListVo vo = (TimeExportListVo)mospParams.getVo();
		// 年月指定時の基準日を取得
		Date yearMonthTargetDate = MonthUtility.getTargetYearMonth(getSystemDate(), mospParams);
		// 有効日プルダウン設定
		vo.setTxtStartYear(getStringYear(yearMonthTargetDate));
		vo.setTxtStartMonth(getStringMonth(yearMonthTargetDate));
		vo.setTxtEndYear(getStringYear(yearMonthTargetDate));
		vo.setTxtEndMonth(getStringMonth(yearMonthTargetDate));
		// 有効日モード設定
		vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
		// プルダウン設定
		setPulldown();
	}
	
	/**
	 * 有効日期間の開始日を取得する。<br>
	 * 有効日決定ボタンを押下時の有効日期間前後チェックでのみ用いられる。<br>
	 * @return 有効日期間の開始日
	 * @throws MospException 日付操作に失敗した場合
	 */
	protected Date getEditStartDate() throws MospException {
		// VO取得
		TimeExportListVo vo = (TimeExportListVo)mospParams.getVo();
		return MonthUtility.getYearMonthTermFirstDate(getInt(vo.getTxtStartYear()), getInt(vo.getTxtStartMonth()),
				mospParams);
	}
	
	/**
	 * 有効日期間の終了日を取得する。<br>
	 * 有効日決定ボタンを押下時の有効日期間前後チェックでのみ用いられる。<br>
	 * @return 有効日期間の終了日
	 * @throws MospException 日付操作に失敗した場合
	 */
	protected Date getEditEndDate() throws MospException {
		// VO取得
		TimeExportListVo vo = (TimeExportListVo)mospParams.getVo();
		return MonthUtility.getYearMonthTermLastDate(getInt(vo.getTxtEndYear()), getInt(vo.getTxtEndMonth()),
				mospParams);
	}
	
	/**
	 * 有効日が妥当であるかを確認する。<br>
	 * 有効日期間の開始日が有効日期間の終了日よりも後の場合、妥当でないと判断する。<br>
	 * @return trueは年月が超えている。falseの年月が超えていない。
	 * @throws MospException 例外発生時
	 */
	protected boolean isActivationDateValid() throws MospException {
		// 有効日期間の開始日が有効日期間の終了日よりも後の場合
		if (getEditStartDate().after(getEditEndDate())) {
			// エラーメッセージを設定
			TimeMessageUtility.addErrorActivateDateEndBeforeStart(mospParams);
			return false;
		}
		// 有効日が妥当である場合
		return true;
		
	}
	
}
