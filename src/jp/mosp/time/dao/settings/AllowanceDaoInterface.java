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
/**
 * 
 */
package jp.mosp.time.dao.settings;

import java.util.Date;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.BaseDaoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.AllowanceDtoInterface;

/**
 * 手当データDAOインターフェース
 */
public interface AllowanceDaoInterface extends BaseDaoInterface {
	
	/**
	 * 勤怠データ手当情報からレコードを取得する。<br>
	 * 社員コード、勤務日、勤務回数、手当コードで合致するレコードが無い場合、nullを返す。<br>
	 * @param personalId 個人ID
	 * @param workDate 勤務日
	 * @param works 勤務回数
	 * @param allowanceCode 手当コード
	 * @return 勤怠データ手当情報DTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	AllowanceDtoInterface findForKey(String personalId, Date workDate, int works, String allowanceCode)
			throws MospException;
	
	/**
	 * 履歴一覧。
	 * <p>
	 * 社員コード、勤務日、勤務回数、手当コード、手当で合致するレコードが無い場合、nullを返す。<br>
	 * </p>
	 * @param personalId 個人ID
	 * @param workDate 勤務日
	 * @param works 勤務回数
	 * @param allowanceCode 手当コード
	 * @param allowance 手当
	 * @return 勤怠データ手当情報マスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<AllowanceDtoInterface> findForHistory(String personalId, Date workDate, int works, String allowanceCode,
			int allowance) throws MospException;
	
	/**
	 * 個人IDと手当コードと開始年月日と終了年月日から手当情報を取得する。<br>
	 * @param personalId 個人ID
	 * @param allowanceCode 手当コード
	 * @param startDate 開始日
	 * @param endDate 終了日
	 * @return 勤怠データ手当情報マスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<AllowanceDtoInterface> findForList(String personalId, String allowanceCode, Date startDate, Date endDate)
			throws MospException;
	
	/**
	 * 検索条件取得。
	 * @return 設定適用検索条件マップ
	 */
	Map<String, Object> getParamsMap();
	
}
