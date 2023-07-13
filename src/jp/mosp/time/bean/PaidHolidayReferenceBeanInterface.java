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

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.PaidHolidayDtoInterface;

/**
 * 有給休暇設定参照インターフェース。
 */
public interface PaidHolidayReferenceBeanInterface extends BaseBeanInterface {
	
	/**
	 * 有給休暇マスタ取得。
	 * <p>
	 * 有給休暇コードと対象日から有給休暇マスタを取得。
	 * </p>
	 * @param paidHolidayCode 有給休暇コード
	 * @param targetDate 対象年月日
	 * @return 有給休暇マスタ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	PaidHolidayDtoInterface getPaidHolidayInfo(String paidHolidayCode, Date targetDate) throws MospException;
	
	/**
	 * 履歴一覧を取得する。<br>
	 * 有給休暇コードから有給休暇マスタリストを取得する。<br>
	 * @param paidHolidayCode 有給休暇コード
	 * @return 有給休暇マスタリスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<PaidHolidayDtoInterface> getPaidHolidayHistory(String paidHolidayCode) throws MospException;
	
	/**
	 * 有休略称を取得する。<br><br>
	 * 対象となる有休情報が存在しない場合は、有休コードを返す。<br>
	 * @param paidHolidayCode 有休コード
	 * @param targetDate 対象年月日
	 * @return 有休略称
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String getPaidHolidayAbbr(String paidHolidayCode, Date targetDate) throws MospException;
	
	/**
	 * プルダウン用配列を取得する。<br>
	 * 対象年月日からプルダウン用配列を取得する。<br>
	 * 表示内容は、コード + 有休名称。<br>
	 * @param targetDate 対象年月日
	 * @param needBlank  空白行要否(true：空白行要、false：空白行不要)
	 * @return プルダウン用配列
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String[][] getCodedSelectArray(Date targetDate, boolean needBlank) throws MospException;
	
	/**
	 * 有給休暇マスタからレコードを取得する。<br>
	 * 有給休暇コード、有効日で合致するレコードが無い場合、nullを返す。<br>
	 * @param paidHolidayCode 有給休暇コード
	 * @param activateDate 有効日
	 * @return 有給休暇マスタDTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	PaidHolidayDtoInterface findForKey(String paidHolidayCode, Date activateDate) throws MospException;
	
	/**
	 * 有給休暇マスタの存在チェック。<br>
	 * @param dto 有給休暇マスタ
	 * @param targetDate メッセージ用の年月日
	 */
	void chkExistPaidHoliday(PaidHolidayDtoInterface dto, Date targetDate);
	
	/**
	 * プルダウン用配列を取得する。<br>
	 * 対象年月日からプルダウン用配列を取得する。<br>
	 * 操作区分に操作範囲が設定されている場合は、取得内容が制限される。<br>
	 * 表示内容は、有休休暇設定の略称。<br>
	 * @param targetDate    対象年月日
	 * @param needBlank     空白行要否(true：空白行要、false：空白行不要)
	 * @return プルダウン用配列
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String[][] getSelectArray(Date targetDate, boolean needBlank) throws MospException;
	
}
