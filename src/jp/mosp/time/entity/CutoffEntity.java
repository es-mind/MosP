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
package jp.mosp.time.entity;

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dto.settings.CutoffDtoInterface;
import jp.mosp.time.utils.TimeUtility;

/**
 * 締日エンティティ。<br>
 */
public class CutoffEntity implements CutoffEntityInterface {
	
	/**
	 * 締日情報。<br>
	 */
	protected CutoffDtoInterface dto;
	
	
	@Override
	public CutoffDtoInterface getCutoffDto() {
		// 締日情報を取得
		return dto;
	}
	
	@Override
	public void setCutoffDto(CutoffDtoInterface dto) {
		// 締日情報を設定
		this.dto = dto;
	}
	
	@Override
	public boolean isExist() {
		// 締日情報が存在するかを確認
		return dto != null;
	}
	
	@Override
	public String getCode() {
		// 締日情報が存在しない場合
		if (isExist() == false) {
			// 空文字を取得
			return MospConst.STR_EMPTY;
		}
		// 締日コードを取得
		return dto.getCutoffCode();
	}
	
	@Override
	public String getCutoffName() {
		// 締日情報が存在しない場合
		if (isExist() == false) {
			// 空文字を取得
			return MospConst.STR_EMPTY;
		}
		// 締日名称を取得
		return dto.getCutoffName();
	}
	
	@Override
	public int getCutoffDate() {
		// 締日情報が存在しない場合
		if (isExist() == false) {
			// 0(月末締)を取得
			return TimeConst.CUTOFF_DATE_LAST_DAY;
		}
		// 締日を取得
		return dto.getCutoffDate();
	}
	
	@Override
	public int getNoApproval() {
		// 締日情報が存在しない場合
		if (isExist() == false) {
			// 0(有効)を取得
			return TimeConst.CODE_NO_APPROVAL_VALID;
		}
		// 未承認仮締を取得
		return dto.getNoApproval();
	}
	
	@Override
	public boolean isSelfTightening() {
		// 締日情報が存在しない場合
		if (isExist() == false) {
			// false(無効)を取得
			return false;
		}
		// 自己月締設定を取得
		return dto.getSelfTightening() == MospConst.INACTIVATE_FLAG_OFF;
	}
	
	@Override
	public Date getCutoffFirstDate(int targetYear, int targetMonth, MospParams mospParams) throws MospException {
		// 対象年月及び締日から締期間初日を取得
		return TimeUtility.getCutoffFirstDate(getCutoffDate(), targetYear, targetMonth, mospParams);
	}
	
	@Override
	public Date getCutoffFirstDate(Date targetDate, MospParams mospParams) throws MospException {
		// 対象日及び締日から締期間初日を取得
		return TimeUtility.getCutoffFirstDate(getCutoffDate(), targetDate, mospParams);
	}
	
	@Override
	public Date getCutoffLastDate(int targetYear, int targetMonth, MospParams mospParams) throws MospException {
		// 対象年月及び締日から締期間最終日を取得
		return TimeUtility.getCutoffLastDate(getCutoffDate(), targetYear, targetMonth, mospParams);
	}
	
	@Override
	public Date getCutoffLastDate(Date targetDate, MospParams mospParams) throws MospException {
		// 対象日及び締日から締期間最終日を取得
		return TimeUtility.getCutoffLastDate(getCutoffDate(), targetDate, mospParams);
	}
	
	@Override
	public Date getCutoffTermTargetDate(int targetYear, int targetMonth, MospParams mospParams) throws MospException {
		// 対象年月における締期間の基準日を取得
		return TimeUtility.getCutoffTermTargetDate(getCutoffDate(), targetYear, targetMonth, mospParams);
	}
	
	@Override
	public Date getCutoffCalculationDate(int targetYear, int targetMonth, MospParams mospParams) throws MospException {
		// 対象年月における締期間の集計日を取得
		return TimeUtility.getCutoffCalculationDate(getCutoffDate(), targetYear, targetMonth, mospParams);
	}
	
	@Override
	public Date getCutoffMonth(Date targetDate, MospParams mospParams) throws MospException {
		// 対象日が含まれる締月(年月の初日)を取得
		return TimeUtility.getCutoffMonth(getCutoffDate(), targetDate, mospParams);
	}
	
	@Override
	public List<Date> getCutoffTerm(int targetYear, int targetMonth, MospParams mospParams) throws MospException {
		// 対象年月及び締日から対象年月日が含まれる締月の期間を取得
		return TimeUtility.getCutoffTerm(getCutoffDate(), targetYear, targetMonth, mospParams);
	}
	
	@Override
	public List<Date> getCutoffTerm(Date targetDate, MospParams mospParams) throws MospException {
		// 対象日付及び締日から対象年月日が含まれる締月の期間を取得
		return TimeUtility.getCutoffTerm(getCutoffDate(), targetDate, mospParams);
	}
	
}
