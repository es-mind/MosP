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
import jp.mosp.time.dto.settings.WorkTypePatternItemDtoInterface;

/**
 * 勤務形態パターン項目DAOインターフェース。<br>
 */
public interface WorkTypePatternItemDaoInterface extends BaseDaoInterface {
	
	/**
	 * パターンコードと有効日と勤務形態コードから勤務形態パターン項目情報を取得する。<br>
	 * 条件と合致する情報が存在しない場合は、nullを返す。<br>
	 * @param patternCode パターンコード
	 * @param activateDate 有効日
	 * @param workTypeCode 勤務形態コード
	 * @return 勤務形態パターン項目DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	WorkTypePatternItemDtoInterface findForKey(String patternCode, Date activateDate, String workTypeCode)
			throws MospException;
	
	/**
	 * 勤務形態パターン項目リストを取得する。<br>
	 * @param patternCode パターンコード
	 * @param activateDate 有効日
	 * @return 勤務形態パターン項目リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<WorkTypePatternItemDtoInterface> findForList(String patternCode, Date activateDate) throws MospException;
	
}
