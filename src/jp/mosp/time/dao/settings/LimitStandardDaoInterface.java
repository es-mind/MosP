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
package jp.mosp.time.dao.settings;

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseDaoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.LimitStandardDtoInterface;

/**
 * 限度基準管理DAOインターフェース
 */
public interface LimitStandardDaoInterface extends BaseDaoInterface {
	
	/**
	 * 勤怠設定コードと有効日および期間から限度基準管理マスタを取得する。<br>
	 * 条件と合致する情報が存在しない場合は、nullを返す。<br>
	 * @param workSettingCode 勤怠設定コード
	 * @param activateDate 有効日
	 * @param term 期間
	 * @return 限度基準管理DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	LimitStandardDtoInterface findForKey(String workSettingCode, Date activateDate, String term) throws MospException;
	
	/**
	 * 勤怠設定コードから限度基準getLimitStandardsを取得する。<br>
	 * 条件と合致する情報が存在しない場合は、空のリストを返す。<br>
	 * @param workSettingCode 勤怠設定コード
	 * @return 限度基準情報リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<LimitStandardDtoInterface> findForCode(String workSettingCode) throws MospException;
	
	/**
	 * 条件による検索。
	 * 勤怠設定コードと有効日から限度基準管理リストを取得する。
	 * @param workSettingCode 勤怠設定コード
	 * @param activateDate 有効日
	 * @return 限度基準管理リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<LimitStandardDtoInterface> findForSearch(String workSettingCode, Date activateDate) throws MospException;
	
}
