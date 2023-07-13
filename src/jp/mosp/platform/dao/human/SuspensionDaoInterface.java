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
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.BaseDaoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dto.human.SuspensionDtoInterface;

/**
 * 人事休職情報DAOインターフェース。<br>
 */
public interface SuspensionDaoInterface extends BaseDaoInterface {
	
	/**
	 * 人事休職情報取得。
	 * <p>
	 * 個人IDと休職日から人事休職情報を取得する。
	 * </p>
	 * @param personalId 個人ID
	 * @param suspensionDate 休職日
	 * @return 人事休職情報DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	SuspensionDtoInterface findForInfo(String personalId, Date suspensionDate) throws MospException;
	
	/**
	 * 履歴一覧。
	 * <p>
	 * 個人IDから人事休職情報リストを取得する。
	 * </p>
	 * @param personalId 個人ID
	 * @return 人事休職情報リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<SuspensionDtoInterface> findForHistory(String personalId) throws MospException;
	
	/**
	 * 対象期間に休職期間が含まれる休職情報リストを取得する。<br>
	 * 期間の定めがある場合は
	 * 対象期間に休職期間が含まれる休職情報リストを取得する。<br>
	 * 期間の定めがない場合は
	 * 対象日が休職期間に含まれる休職情報リストを取得する。<br>
	 * @param targetDate 対象日
	 * @param startDate 期間開始日
	 * @param endDate 期間終了日
	 * @return 期間内の存在する情報リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<SuspensionDtoInterface> findForList(Date targetDate, Date startDate, Date endDate) throws MospException;
	
	/**
	 * 休職日に含まれる個人ID配列に該当する休暇情報マップを作成する。<br>
	 * @param personalIds 個人ID配列
	 * @param suspensionDate 休職日
	 * @return 個人ID配列に該当する休暇情報マップ
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	Map<String, SuspensionDtoInterface> findForPersonalIds(String[] personalIds, Date suspensionDate)
			throws MospException;
}
