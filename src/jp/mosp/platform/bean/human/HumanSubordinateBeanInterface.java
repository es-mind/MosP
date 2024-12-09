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
package jp.mosp.platform.bean.human;

import java.util.Set;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dto.human.HumanDtoInterface;

/**
 * 部下検索処理インターフェース。<br>
 */
public interface HumanSubordinateBeanInterface extends HumanSearchBeanInterface {
	
	/**
	 * 部下個人ID群を取得する。<br>
	 * 設定された条件で、検索を行う。<br>
	 * <br>
	 * 取得できるのは「部下」の個人ID群であり、
	 * 部下でなく「承認すべき申請者」である個人IDは含まれない。<br>
	 * <br>
	 * @return 部下個人ID群
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	Set<String> getSubordinateIds() throws MospException;
	
	/**
	 * 人事情報(部下)群を取得する。<br>
	 * 設定された条件で、検索を行う。<br>
	 * <br>
	 * フロー区分は、検索区分が空白(部下+承認すべき申請者)か承認すべき申請者である場合に、
	 * 承認すべき申請者を特定するために用いる。<br>
	 * <br>
	 * @param workflowType フロー区分
	 * @return 人事情報(部下)群
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Set<HumanDtoInterface> getSubordinates(int workflowType) throws MospException;
	
	/**
	 * 検索区分(空白or部下or承認すべき申請者)を設定する。<br>
	 * 部下を抽出するための条件。<br>
	 * @param humanType 検索区分(空白or部下or承認すべき申請者)
	 */
	void setHumanType(String humanType);
	
}
