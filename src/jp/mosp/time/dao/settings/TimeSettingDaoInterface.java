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
import jp.mosp.time.dto.settings.TimeSettingDtoInterface;

/**
 * 勤怠設定管理DAOインターフェース
 */
public interface TimeSettingDaoInterface extends BaseDaoInterface {
	
	/**
	 * 勤怠設定コードと有効日から勤怠設定マスタを取得する。<br>
	 * 条件と合致する情報が存在しない場合は、nullを返す。<br>
	 * @param workSettingCode 勤怠設定コード
	 * @param activateDate 有効日
	 * @return 勤怠設定管理DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	TimeSettingDtoInterface findForKey(String workSettingCode, Date activateDate) throws MospException;
	
	/**
	 * 勤怠設定マスタ取得。
	 * <p>
	 * 勤怠設定コードと有効日から勤怠設定マスタを取得する。
	 * </p>
	 * @param workSettingCode 勤怠設定コード
	 * @param activateDate 有効日
	 * @return 勤怠設定管理DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	TimeSettingDtoInterface findForInfo(String workSettingCode, Date activateDate) throws MospException;
	
	/**
	 * 有効日マスタ一覧。
	 * <p>
	 * 有効日から勤怠設定マスタリストを取得する。
	 * </p>
	 * @param activateDate 有効日
	 * @return 勤怠設定マスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<TimeSettingDtoInterface> findForActivateDate(Date activateDate) throws MospException;
	
	/**
	 * 条件による検索。
	 * <p>
	 * 検索条件から勤怠設定マスタリストを取得する。
	 * </p>
	 * @param param 検索条件
	 * @return 勤怠設定マスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<TimeSettingDtoInterface> findForSearch(Map<String, Object> param) throws MospException;
	
	/**
	 * 履歴一覧を取得する。<br>
	 * 勤怠設定コードから勤怠設定マスタリストを取得する。<br>
	 * 取得したリストは、有効日の昇順で並べられる。<br>
	 * @param workSettingCode 勤怠設定コード
	 * @return 勤怠設定マスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<TimeSettingDtoInterface> findForHistory(String workSettingCode) throws MospException;
	
	/**
	 * 条件による検索。
	 * <p>
	 * 検索条件から勤怠設定マスタリストを取得する。
	 * </p>
	 * @param cutoffCode 締日コード
	 * @param activateDate 有効日
	 * @return 勤怠設定マスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	
	List<TimeSettingDtoInterface> findForInfoList(String cutoffCode, Date activateDate) throws MospException;
	
	/**
	 * 検索条件取得。
	 * @return 勤怠設定管理検索条件マップ
	 */
	Map<String, Object> getParamsMap();
	
	/**
	 * 勤怠設定マスタリストを取得する。<br>
	 * 削除フラグが立っていないものを対象とする。<br>
	 * 有効日の範囲で検索する。但し、有効日From及び有効日Toは、検索対象に含まれない。<br>
	 * マスタ類無効時の確認等に用いる。<br>
	 * @param fromActivateDate 有効日From
	 * @param toActivateDate   有効日To
	 * @return 勤怠設定マスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<TimeSettingDtoInterface> findForTerm(Date fromActivateDate, Date toActivateDate) throws MospException;
	
}
