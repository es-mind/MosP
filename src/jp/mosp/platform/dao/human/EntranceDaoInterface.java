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
package jp.mosp.platform.dao.human;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import jp.mosp.framework.base.BaseDaoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dto.human.EntranceDtoInterface;

/**
 * 人事入社情報DAOインターフェース。<br>
 */
public interface EntranceDaoInterface extends BaseDaoInterface {
	
	/**
	 * 人事入社情報を取得する。<br>
	 * 個人IDから人事入社情報を取得する。<br>
	 * @param personalId 個人ID
	 * @return 人事入社情報DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	EntranceDtoInterface findForInfo(String personalId) throws MospException;
	
	/**
	 * 対象期間における入社済の個人IDセットを取得する。<br>
	 * 期間の定めがある場合は
	 * 入社日が期間終了日以前に設定されている個人IDを取得する。<br>
	 * 期間の定めがない場合は
	 * 入社日が対象日以前に設定されている個人IDを取得する。<br>
	 * @param targetDate 対象日
	 * @param startDate 対象期間開始日
	 * @param endDate 対象期間終了日
	 * @return 入社済の個人IDセット
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	Set<String> findForEntrancedPersonalIdSet(Date targetDate, Date startDate, Date endDate) throws MospException;
	
	/**
	 * 個人ID配列に該当する入社情報マップを作成する。<br>
	 * @param personalIds 個人ID配列
	 * @return 個人ID配列に該当する入社情報マップ
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	Map<String, EntranceDtoInterface> findForPersonalIds(String[] personalIds) throws MospException;
}
