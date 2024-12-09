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

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.platform.utils.MonthUtility;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.entity.CutoffEntityInterface;

/**
 * 勤怠集計関連の共通機能を提供する。<br>
 */
public abstract class TotalTimeBaseAction extends TimeAction {
	
	/**
	 * 締日関連情報を設定する。<br>
	 * 集計日、締期間における基準日、表示用の名称をVOに設定する。<br>
	 * @throws MospException 締日情報の取得、或いは日付操作に失敗した場合
	 */
	protected void setCutoffInfo() throws MospException {
		// VO準備
		TotalTimeBaseVo vo = (TotalTimeBaseVo)mospParams.getVo();
		// VOから締日コード対象年、対象月を取得
		String cutoffCode = vo.getCutoffCode();
		int targetYear = vo.getTargetYear();
		int targetMonth = vo.getTargetMonth();
		// 締日情報取得(締期間における基準日で取得)
		CutoffEntityInterface cutoff = timeReference().master().getCutoff(cutoffCode, targetYear, targetMonth);
		// 対象年月における締期間の初日を取得しVOに設定
		vo.setCutoffFirstDate(cutoff.getCutoffFirstDate(targetYear, targetMonth, mospParams));
		// 対象年月における締期間の最終日を取得しVOに設定
		vo.setCutoffLastDate(cutoff.getCutoffLastDate(targetYear, targetMonth, mospParams));
		// 対象年月における締期間の集計日を取得しVOに設定
		vo.setCalculationDate(cutoff.getCutoffCalculationDate(targetYear, targetMonth, mospParams));
		// 対象年月における締期間の基準日を取得しVOに設定
		vo.setCutoffTermTargetDate(cutoff.getCutoffTermTargetDate(targetYear, targetMonth, mospParams));
		// 表示用名称設定
		vo.setLblCutoffName(cutoff.getCutoffName());
		vo.setLblYearMonth(DateUtility.getStringYearMonth(MonthUtility.getYearMonthDate(targetYear, targetMonth)));
		vo.setLblCutoffFirstDate(getStringDate(vo.getCutoffFirstDate()));
		vo.setLblCutoffLastDate(getStringDate(vo.getCutoffLastDate()));
	}
	
	/**
	 * 締日関連情報を設定する。<br>
	 * MosP処理情報から締日コード、対象年月をVOに設定する。<br>
	 */
	protected void getCutoffParams() {
		// VO準備
		TotalTimeBaseVo vo = (TotalTimeBaseVo)mospParams.getVo();
		// 譲渡された締日コードを取得
		String cutoffCode = (String)mospParams.getGeneralParam(TimeConst.PRM_TRANSFERRED_GENERIC_CODE);
		// 譲渡された年月を取得
		Integer targetYear = (Integer)mospParams.getGeneralParam(TimeConst.PRM_TRANSFERRED_YEAR);
		Integer targetMonth = (Integer)mospParams.getGeneralParam(TimeConst.PRM_TRANSFERRED_MONTH);
		// 対象年月をVOに設定
		vo.setTargetYear(targetYear);
		vo.setTargetMonth(targetMonth);
		// 締日コードをVOに設定
		vo.setCutoffCode(cutoffCode);
	}
	
	/**
	 * 締日関連情報を設定する。<br>
	 * 譲渡された締日コード、対象年月をVOに設定する。<br>
	 */
	protected void getTransferredCutoffParams() {
		// VO準備
		TotalTimeBaseVo vo = (TotalTimeBaseVo)mospParams.getVo();
		// 対象年月を取得
		int targetYear = getInt(getTransferredYear());
		int targetMonth = getInt(getTransferredMonth());
		// 締日コードを取得
		String cutoffCode = getTransferredCode();
		// 対象年月をVOに設定
		vo.setTargetYear(targetYear);
		vo.setTargetMonth(targetMonth);
		// 締日コードを取得しVOに設定
		vo.setCutoffCode(cutoffCode);
	}
	
	/**
	 * 譲渡締日コードを設定する。
	 * @param cutoffCode 締日コード
	 */
	protected void setTargetCutoffCode(String cutoffCode) {
		mospParams.addGeneralParam(TimeConst.PRM_TRANSFERRED_GENERIC_CODE, cutoffCode);
	}
	
	/**
	 * 譲渡対象年を設定する。
	 * @param targetYear 対象年
	 */
	@Override
	protected void setTargetYear(int targetYear) {
		mospParams.addGeneralParam(TimeConst.PRM_TRANSFERRED_YEAR, targetYear);
	}
	
	/**
	 * 譲渡対象月を設定する。
	 * @param targetMonth 対象月
	 */
	@Override
	protected void setTargetMonth(int targetMonth) {
		mospParams.addGeneralParam(TimeConst.PRM_TRANSFERRED_MONTH, targetMonth);
	}
	
}
