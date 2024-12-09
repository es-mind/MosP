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
/**
 * 
 */
package jp.mosp.time.dao.settings;

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseDaoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.ScheduleDateDtoInterface;

/**
 * カレンダ日マスタDAOインターフェース
 */
public interface ScheduleDateDaoInterface extends BaseDaoInterface {
	
	/**
	 * カレンダコードと対象日からカレンダ日情報を取得する。<br>
	 * 条件と合致する情報が存在しない場合は、nullを返す。<br>
	 * @param scheduleCode カレンダコード
	 * @param scheduleDate 対象日
	 * @return カレンダ日マスタDTO
	 * @throws MospException MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	ScheduleDateDtoInterface findForKey(String scheduleCode, Date scheduleDate) throws MospException;
	
	/**
	 * カレンダコードと有効日からカレンダ日マスタリストを取得する。<br>
	 * @param scheduleCode カレンダコード
	 * @param activateDate 有効日
	 * @return カレンダ日マスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<ScheduleDateDtoInterface> findForList(String scheduleCode, Date activateDate) throws MospException;
	
	/**
	 * カレンダコードと開始日と終了日からカレンダ日マスタリストを取得する。<br>
	 * @param scheduleCode カレンダコード
	 * @param startDate 開始日
	 * @param endDate 終了日
	 * @return カレンダ日マスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<ScheduleDateDtoInterface> findForList(String scheduleCode, Date startDate, Date endDate) throws MospException;
	
	/**
	 * カレンダ日マスタリストを取得する。<br>
	 * 有効日の範囲で検索する。但し、有効日Toは検索対象に含まれない。<br>
	 * マスタ類無効時の確認等に用いる。<br>
	 * @param fromActivateDate 有効日From
	 * @param toActivateDate   有効日To
	 * @param targetCode 勤務形態コード
	 * @return カレンダ日マスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<ScheduleDateDtoInterface> findForTerm(Date fromActivateDate, Date toActivateDate, String targetCode)
			throws MospException;
	
	/**
	 * カレンダコードから勤務形態コードを取得する。<br>
	 * カレンダに登録されている勤務形態コードをリストにして返却する。<br>
	 * @param scheduleCode カレンダコード
	 * @return 勤務形態コードリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<String> findForWorkTypeCode(String scheduleCode) throws MospException;
}
