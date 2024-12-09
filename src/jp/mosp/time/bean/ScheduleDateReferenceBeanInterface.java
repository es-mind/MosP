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
package jp.mosp.time.bean;

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.ScheduleDateDtoInterface;

/**
 * カレンダ日参照処理インターフェース。<br>
 */
public interface ScheduleDateReferenceBeanInterface extends BaseBeanInterface {
	
	/**
	 * カレンダ日マスタからレコードを取得する。<br>
	 * カレンダコード、日で合致するレコードが無い場合、nullを返す。<br>
	 * @param scheduleCode カレンダコード
	 * @param scheduleDate 日
	 * @return カレンダ日マスタDTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	ScheduleDateDtoInterface getScheduleDateInfo(String scheduleCode, Date scheduleDate) throws MospException;
	
	/**
	 * カレンダ日マスタからレコードを取得する。<br>
	 * 年度(activateDate)なしで取得する。<br>
	 * @param scheduleCode カレンダコード
	 * @param startDate 開始日
	 * @param endDate 終了日
	 * @return カレンダ日マスタDTOリスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<ScheduleDateDtoInterface> findForList(String scheduleCode, Date startDate, Date endDate) throws MospException;
	
	/**
	 * カレンダコードから勤務形態コードを取得する。<br>
	 * カレンダに登録されている勤務形態コードをリストにして返却する。<br>
	 * @param scheduleCode カレンダコード
	 * @return 勤務形態コードリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<String> findForWorkTypeCode(String scheduleCode) throws MospException;
	
}
