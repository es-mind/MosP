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

import jp.mosp.framework.base.BaseDaoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.TotalTimeDtoInterface;

/**
 * 勤怠集計管理トランザクションDAOインターフェース。<br>
 */
public interface TotalTimeDaoInterface extends BaseDaoInterface {
	
	/**
	 * 集計年と集計月と締日コードから勤怠集計管理情報を取得する。<br>
	 * 条件と合致する情報が存在しない場合は、nullを返す。<br>
	 * @param calculationYear 集計年
	 * @param calculationMonth 集計月
	 * @param cutoffCode 締日コード
	 * @return 勤怠集計管理トランザクションDTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	TotalTimeDtoInterface findForKey(int calculationYear, int calculationMonth, String cutoffCode) throws MospException;
	
}
