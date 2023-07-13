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
import jp.mosp.time.dto.settings.RestDtoInterface;

/**
 * 休憩データDAOインターフェース
 */
public interface RestDaoInterface extends BaseDaoInterface {
	
	/**
	 * 勤怠データ休憩情報取得。
	 * <p>
	 * 個人IDと勤務日と勤務回数と休憩回数から勤怠データ休憩情報を取得。
	 * @param personalId 個人ID
	 * @param workDate 勤務日
	 * @param timesWork 勤務回数
	 * @param rest 休憩回数
	 * @return 勤怠データDTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	RestDtoInterface findForKey(String personalId, Date workDate, int timesWork, int rest) throws MospException;
	
	/**
	 * 勤怠データ休憩情報リスト取得。
	 * <p>
	 * 個人IDと勤務日と勤務回数から勤怠データ休憩情報リストを取得。
	 * </p>
	 * @param personalId 個人ID
	 * @param workDate 勤務日
	 * @param timesWork 勤務回数
	 * @return 勤怠データ休憩情報リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<RestDtoInterface> findForList(String personalId, Date workDate, int timesWork) throws MospException;
	
	/**
	 * 履歴一覧。
	 * <p>
	 * 個人IDと勤務日と休憩開始時刻と休憩終了時刻から勤怠データ休憩情報リストを取得する。
	 * </p>
	 * @param personalId 個人ID
	 * @param workDate 勤務日
	 * @param timesWork 勤務回数
	 * @param restStart 休憩開始時刻
	 * @param restEnd 休憩終了時刻
	 * @return 勤怠データ休憩情報リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<RestDtoInterface> findForHistory(String personalId, Date workDate, int timesWork, Date restStart, Date restEnd)
			throws MospException;
	
	/**
	 * 検索条件取得。
	 * @return 休憩検索条件マップ
	 */
	Map<String, Object> getParamsMap();
	
}
