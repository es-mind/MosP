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
package jp.mosp.time.settings.action;

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.BaseVo;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.platform.comparator.base.EmployeeNameComparator;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.time.base.TimeAction;
import jp.mosp.time.bean.HolidayManagementSearchBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dto.settings.HolidayManagementListDtoInterface;
import jp.mosp.time.settings.vo.OtherHolidayManagementVo;
import jp.mosp.time.utils.TimeUtility;

/**
 * 各従業員のその他休暇保有日数の確認を行う。<br>
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
 * {@link #CMD_SET_ACTIVATION_DATE}
 * </li></ul>
 */
public class OtherHolidayManagementAction extends TimeAction {
	
	/**
	 * 表示コマンド。<br>
	 * <br>
	 * 初期表示を行う。<br>
	 */
	public static final String	CMD_SHOW				= "TM4310";
	
	/**
	 * 検索コマンド。<br>
	 * <br>
	 * 検索欄に入力した情報を基に人事情報の検索を行う。<br>
	 */
	public static final String	CMD_SEARCH				= "TM4312";
	
	/**
	 * 再表示コマンド。<br>
	 * <br>
	 * 新たに休暇の付与・破棄を行った際に検索結果一覧にそれらが反映されるよう再表示を行う。<br>
	 */
	public static final String	CMD_RE_SHOW				= "TM4313";
	
	/**
	 * ソートコマンド。<br>
	 * <br>
	 * それぞれのレコードの値を比較して一覧表示欄の各情報毎に並び替えを行う。<br>
	 * これが実行される度に並び替えが昇順・降順と交互に切り替わる。<br>
	 */
	public static final String	CMD_SORT				= "TM4318";
	
	/**
	 * ページ繰りコマンド。<br>
	 * <br>
	 * 検索処理を行った際に検索結果が100件を超えた場合に分割されるページ間の遷移を行う。<br>
	 */
	public static final String	CMD_PAGE				= "TM4319";
	
	/**
	 * 有効日決定コマンド。<br>
	 * <br>
	 * 決定した有効日時点で有効な勤務地、雇用契約、所属、職位のレコードを取得し、名称を各プルダウンで表示する。<br>
	 */
	public static final String	CMD_SET_ACTIVATION_DATE	= "TM4380";
	
	
	/**
	 * {@link TimeAction#TimeAction()}を実行する。<br>
	 */
	public OtherHolidayManagementAction() {
		super();
		// パンくずリスト用コマンド設定
		topicPathCommand = CMD_RE_SHOW;
	}
	
	@Override
	protected BaseVo getSpecificVo() {
		return new OtherHolidayManagementVo();
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
		} else if (mospParams.getCommand().equals(CMD_SET_ACTIVATION_DATE)) {
			// ページ繰り
			prepareVo();
			setActivationDate();
		}
	}
	
	/**
	 * 初期表示処理を行う。<br>
	 * @throws MospException 例外処理が発生した場合
	 */
	protected void show() throws MospException {
		// VO準備
		OtherHolidayManagementVo vo = (OtherHolidayManagementVo)mospParams.getVo();
		// ページ繰り設定
		setPageInfo(CMD_PAGE, getListLength());
		// 新規登録モード設定
		insertMode();
		// デフォルトソートキー及びソート順設定
		vo.setComparatorName(EmployeeNameComparator.class.getName());
	}
	
	/**
	 * @throws MospException 例外処理が発生した場合
	 */
	public void search() throws MospException {
		// VO準備
		OtherHolidayManagementVo vo = (OtherHolidayManagementVo)mospParams.getVo();
		// 検索クラス取得
		HolidayManagementSearchBeanInterface search = timeReference().holidayManagementSearch();
		// VOの値を検索クラスへ設定
		search.setActivateDate(getSearchActivateDate());
		search.setEmployeeCode(vo.getTxtSearchEmployeeCode());
		search.setEmployeeName(vo.getTxtSearchEmployeeName());
		search.setWorkPlaceCode(vo.getPltSearchWorkPlace());
		search.setEmploymentCode(vo.getPltSearchEmployment());
		search.setSectionCode(vo.getPltSearchSection());
		search.setPositionCode(vo.getPltSearchPosition());
		// 検索条件をもとに検索クラスからマスタリストを取得
		List<HolidayManagementListDtoInterface> list = search.getSearchList(Integer
			.parseInt(mospParams.getProperties().getCodeArray(TimeConst.CODE_KEY_HOLIDAY_TYPE_MASTER, false)[1][0]));
		// 検索結果リスト設定
		vo.setList(list);
		// デフォルトソートキー及びソート順設定
		vo.setComparatorName(EmployeeNameComparator.class.getName());
		vo.setAscending(false);
		// ソート
		sort();
		// 検索結果確認
		if (list.isEmpty()) {
			// データ無しメッセージを設定
			PfMessageUtility.addMessageNoData(mospParams);
		}
	}
	
	/**
	 * 一覧のソート処理を行う。<br>
	 * @throws MospException 比較クラスのインスタンス生成に失敗した場合
	 */
	protected void sort() throws MospException {
		setVoList(sortList(getTransferredSortKey()));
	}
	
	/**
	 * 一覧のページ処理を行う。
	 * @throws MospException 例外処理が発生した場合
	 */
	protected void page() throws MospException {
		setVoList(pageList());
	}
	
	/**
	 * 有効日(編集)設定処理を行う。<br>
	 * 保持有効日モードを確認し、モード及びプルダウンの再設定を行う。<br>
	 * @throws MospException プルダウンの取得に失敗した場合
	 */
	protected void setActivationDate() throws MospException {
		// VO取得
		OtherHolidayManagementVo vo = (OtherHolidayManagementVo)mospParams.getVo();
		// 現在の有効日モードを確認
		if (vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_CHANGING)) {
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
	 * 新規登録モードに設定する。<br>
	 * @throws MospException 例外処理が発生した場合
	 */
	protected void insertMode() throws MospException {
		// VO準備
		OtherHolidayManagementVo vo = (OtherHolidayManagementVo)mospParams.getVo();
		// 初期値設定
		setDefaultValues();
		// 有効日(編集)モード設定
		vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_FIXED);
		// プルダウン設定
		setPulldown();
	}
	
	/**
	 * 初期値を設定する。<br>
	 */
	public void setDefaultValues() {
		// VO準備
		OtherHolidayManagementVo vo = (OtherHolidayManagementVo)mospParams.getVo();
		// システム日付取得
		Date date = getSystemDate();
		// 検索項目設定
		vo.setTxtSearchActivateYear(DateUtility.getStringYear(date));
		vo.setTxtSearchActivateMonth(DateUtility.getStringMonth(date));
		vo.setTxtSearchActivateDay(DateUtility.getStringDay(date));
		vo.setTxtSearchEmployeeCode("");
		vo.setTxtSearchEmployeeName("");
		vo.setPltSearchEmployment("");
		vo.setPltSearchPosition("");
		vo.setPltSearchSection("");
		vo.setPltSearchWorkPlace("");
	}
	
	/**
	 * プルダウン(編集)の設定を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setPulldown() throws MospException {
		// VO取得
		OtherHolidayManagementVo vo = (OtherHolidayManagementVo)mospParams.getVo();
		// 有効日モード確認
		if (vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_CHANGING)) {
			// プルダウン設定
			vo.setAryPltSearchSection(getInputActivateDatePulldown());
			vo.setAryPltSearchPosition(getInputActivateDatePulldown());
			vo.setAryPltSearchEmployment(getInputActivateDatePulldown());
			vo.setAryPltSearchWorkPlace(getInputActivateDatePulldown());
			return;
		}
		// 有効日取得
		Date targetDate = getSearchActivateDate();
		// プルダウン取得及び設定
		vo.setAryPltSearchSection(reference().section().getCodedSelectArray(targetDate, true, null));
		vo.setAryPltSearchPosition(reference().position().getCodedSelectArray(targetDate, true, null));
		vo.setAryPltSearchEmployment(reference().employmentContract().getCodedSelectArray(targetDate, true, null));
		vo.setAryPltSearchWorkPlace(reference().workPlace().getCodedSelectArray(targetDate, true, null));
	}
	
	/**
	 * 検索結果リストの内容をVOに設定する。<br>
	 * @param list 対象リスト
	 * @throws MospException 例外処理が発生した場合
	 */
	protected void setVoList(List<? extends BaseDtoInterface> list) throws MospException {
		// VO取得
		OtherHolidayManagementVo vo = (OtherHolidayManagementVo)mospParams.getVo();
		// データ配列初期化
		String[] aryLblActivateDate = new String[list.size()];
		String[] aryLblEmployeeName = new String[list.size()];
		String[] aryLblEmployeeCode = new String[list.size()];
		String[] aryLblSection = new String[list.size()];
		String[] aryLblHolidayCode = new String[list.size()];
		String[] aryLblHolidayCodeName = new String[list.size()];
		String[] aryLblHolidayRemainder = new String[list.size()];
		String[] aryLblHolidayLimit = new String[list.size()];
		
		// データ作成
		for (int i = 0; i < list.size(); i++) {
			// リストから情報を取得
			HolidayManagementListDtoInterface dto = (HolidayManagementListDtoInterface)list.get(i);
			// 配列に情報を設定
			aryLblActivateDate[i] = getStringDate(dto.getActivateDate());
			aryLblEmployeeName[i] = getLastFirstName(dto.getLastName(), dto.getFirstName());
			aryLblEmployeeCode[i] = dto.getEmployeeCode();
			aryLblSection[i] = reference().section().getSectionAbbr(dto.getSectionCode(), getSearchActivateDate());
			aryLblHolidayCodeName[i] = getHolidayAbbr(dto.getHolidayCode(), getSearchActivateDate(),
					TimeConst.CODE_HOLIDAYTYPE_OTHER);
			aryLblHolidayCode[i] = String.valueOf(dto.getHolidayCode());
			// 取得期限が無期限(無制限)であるかを確認
			if (TimeUtility.isUnlimited(dto.getHolidayLimit())) {
				aryLblHolidayRemainder[i] = mospParams.getName("NoLimit");
				aryLblHolidayLimit[i] = mospParams.getName("NoLimit");
			} else {
				aryLblHolidayRemainder[i] = getFormatDaysHoursMinutes(dto.getHolidayRemainder(),
						dto.getHolidayRemaindHours(), dto.getHolidayRemaindMinutes(), false);
				aryLblHolidayLimit[i] = getStringDate(dto.getHolidayLimit());
			}
		}
		// データをVOに設定
		vo.setAryLblActivateDate(aryLblActivateDate);
		vo.setAryLblEmployeeName(aryLblEmployeeName);
		vo.setAryLblEmployeeCode(aryLblEmployeeCode);
		vo.setAryLblSection(aryLblSection);
		vo.setAryLblHolidayCode(aryLblHolidayCode);
		vo.setAryLblHolidayCodeName(aryLblHolidayCodeName);
		vo.setAryLblHolidayRemainder(aryLblHolidayRemainder);
		vo.setAryLblHolidayLimit(aryLblHolidayLimit);
	}
	
}
