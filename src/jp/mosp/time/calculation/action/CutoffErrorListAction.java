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
package jp.mosp.time.calculation.action;

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.BaseVo;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.time.base.TimeAction;
import jp.mosp.time.base.TotalTimeBaseAction;
import jp.mosp.time.calculation.vo.CutoffErrorListVo;
import jp.mosp.time.comparator.settings.CutoffErrorListDateComparator;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.constant.TimeMessageConst;
import jp.mosp.time.dto.settings.CutoffErrorListDtoInterface;

/**
 * 勤怠集計実行時に未申請・未承認のレコードがあった場合の内容を表示する。<br>
 * <br>
 * 以下のコマンドを扱う。<br>
 * <ul><li>
 * {@link #CMD_SHOW}
 * </li><li>
 */
public class CutoffErrorListAction extends TotalTimeBaseAction {
	
	/**
	 * 表示コマンド。<br>
	 * <br>
	 * 勤怠集計管理画面で仮締・確定の処理を実行した際に未申請・未承認情報が集計の対象となるレコードに含まれている場合に自動でこの画面へ遷移する。<br>
	 * どの従業員、どの申請状況が現在どのようになっているのかといったことを一覧で表示して通知を行う。（特にシステム上では対応しない）<br>
	 */
	public static final String	CMD_SHOW	= "TM3140";
	
	/**
	 * ソートコマンド。<br>
	 * <br>
	 * それぞれのレコードの値を比較して一覧表示欄の各情報毎に並び替えを行う。
	 * これが実行される度に並び替えが昇順・降順と交互に切り替わる。<br>
	 */
	public static final String	CMD_SORT	= "TM3148";
	
	/**
	 * ページ繰りコマンド。<br>
	 * <br>
	 * 検索処理を行った際に検索結果が100件を超えた場合に分割されるページ間の遷移を行う。<br>
	 */
	public static final String	CMD_PAGE	= "TM3149";
	
	
	/**
	 * {@link TimeAction#TimeAction()}を実行する。<br>
	 */
	public CutoffErrorListAction() {
		super();
	}
	
	@Override
	protected BaseVo getSpecificVo() {
		return new CutoffErrorListVo();
	}
	
	/**
	 * 各コマンドの処理を実行する。<br>
	 */
	@Override
	public void action() throws MospException {
		// コマンド毎の処理
		if (mospParams.getCommand().equals(CMD_SHOW)) {
			// 表示
			prepareVo(false, false);
			show();
		} else if (mospParams.getCommand().equals(CMD_SORT)) {
			// ソート
			prepareVo();
			sort();
		} else if (mospParams.getCommand().equals(CMD_PAGE)) {
			// ページ繰り
			prepareVo();
			page();
		}
	}
	
	/**
	 * 初期表示処理を行う。<br>
	 * @throws MospException 例外発生時
	 */
	protected void show() throws MospException {
		// VO準備
		CutoffErrorListVo vo = (CutoffErrorListVo)mospParams.getVo();
		// MosP処理情報から値をVOに設定
		getCutoffParams();
		// 締日関連情報を設定
		setCutoffInfo();
		// ページ繰り設定
		setPageInfo(CMD_PAGE, getListLength());
		// エラー情報を取得し、検索結果リスト設定
		vo.setList(getCutoffErrorList());
		// ソートキー設定
		vo.setComparatorName(CutoffErrorListDateComparator.class.getName());
		vo.setAscending(false);
		// エラーメッセージの設定
		mospParams.addErrorMessage(TimeMessageConst.MSG_NOT_APPROVAL);
		// ソート
		sort();
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
	 * @throws MospException 例外発生時
	 */
	protected void page() throws MospException {
		setVoList(pageList());
	}
	
	/**
	 * 検索結果リストの内容をVOに設定する。<br>
	 * @param list 対象リスト
	 * @throws MospException 例外発生時
	 */
	protected void setVoList(List<? extends BaseDtoInterface> list) throws MospException {
		// VO取得
		CutoffErrorListVo vo = (CutoffErrorListVo)mospParams.getVo();
		// データ配列初期化
		String[] aryLblDate = new String[list.size()];
		String[] aryLblEmployeeCode = new String[list.size()];
		String[] aryLblEmployeeName = new String[list.size()];
		String[] aryLblWorkPlace = new String[list.size()];
		String[] aryLblEmployee = new String[list.size()];
		String[] aryLblSection = new String[list.size()];
		String[] aryLblPosition = new String[list.size()];
		String[] aryLblType = new String[list.size()];
		String[] aryLblState = new String[list.size()];
		// 締期間基準日取得
		Date cutoffTermTargetDate = vo.getCutoffTermTargetDate();
		// マスタ情報取得
		String[][] aryWorkPlase = reference().workPlace().getSelectArray(cutoffTermTargetDate, false, null);
		String[][] aryEmployment = reference().employmentContract().getSelectArray(cutoffTermTargetDate, false, null);
		String[][] arySection = reference().section().getSelectArray(cutoffTermTargetDate, false, null);
		String[][] aryPosition = reference().position().getSelectArray(cutoffTermTargetDate, false, null);
		for (int i = 0; i < list.size(); i++) {
			// リストから情報を取得
			CutoffErrorListDtoInterface dto = (CutoffErrorListDtoInterface)list.get(i);
			// 配列に情報を設定
			aryLblDate[i] = DateUtility.getStringDateAndDay(dto.getDate());
			aryLblEmployeeCode[i] = dto.getEmployeeCode();
			aryLblEmployeeName[i] = getEmployeeName(dto.getPersonalId(), dto.getDate());
			aryLblWorkPlace[i] = MospUtility.getCodeName(dto.getWorkPlaceCode(), aryWorkPlase);
			aryLblEmployee[i] = MospUtility.getCodeName(dto.getEmploymentCode(), aryEmployment);
			aryLblSection[i] = MospUtility.getCodeName(dto.getSectionCode(), arySection);
			aryLblPosition[i] = MospUtility.getCodeName(dto.getPositionCode(), aryPosition);
			aryLblType[i] = dto.getType();
			aryLblState[i] = dto.getState();
		}
		// データをVOに設定
		vo.setAryLblDate(aryLblDate);
		vo.setAryLblEmployeeCode(aryLblEmployeeCode);
		vo.setAryLblEmployeeName(aryLblEmployeeName);
		vo.setAryLblWorkPlace(aryLblWorkPlace);
		vo.setAryLblEmployment(aryLblEmployee);
		vo.setAryLblSection(aryLblSection);
		vo.setAryLblPosition(aryLblPosition);
		vo.setAryLblType(aryLblType);
		vo.setAryLblState(aryLblState);
	}
	
	/**
	 * 勤怠集計時エラーリストを取得する。
	 * @return エラーリスト
	 */
	@SuppressWarnings("unchecked")
	protected List<? extends BaseDtoInterface> getCutoffErrorList() {
		return (List<? extends BaseDtoInterface>)mospParams.getGeneralParam(TimeConst.PRM_TOTALTIME_ERROR);
	}
}
