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
package jp.mosp.platform.bean.workflow;

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dto.workflow.ApprovalRouteDtoInterface;

/**
 * 承認ルートマスタ参照処理インターフェース。<br>
 */
public interface ApprovalRouteReferenceBeanInterface extends BaseBeanInterface {
	
	/**
	 * 承認ルートマスタ取得。<br>
	 * ルートコードと対象日から承認ルートマスタを取得。<br>
	 * @param routeCode ルートコード
	 * @param targetDate 対象年月日
	 * @return 承認ルートマスタ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	ApprovalRouteDtoInterface getApprovalRouteInfo(String routeCode, Date targetDate) throws MospException;
	
	/**
	 * 履歴一覧取得。<br>
	 * ルートコードから承認ルートマスタを取得。<br>
	 * @param routeCode ルートコード
	 * @return 承認ルートマスタ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<ApprovalRouteDtoInterface> getApprovalRouteHistory(String routeCode) throws MospException;
	
	/**
	 * プルダウン用配列を取得する。<br>
	 * 対象年月日からプルダウン用配列を取得する。<br>
	 * 表示内容は、コード + ルート名称。<br>
	 * 空白行要の場合、デフォルトとして「自己承認」の選択肢をセットする。<br>
	 * @param targetDate 対象年月日
	 * @param needBlank 空白行要否(true：空白行要、false：空白行不要)
	 * @return プルダウン用配列
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String[][] getCodedSelectArray(Date targetDate, boolean needBlank) throws MospException;
	
	/**
	 * 承認ルートマスタからレコードを取得する。<br>
	 * ルートコード、有効日で合致するレコードが無い場合、nullを返す。<br>
	 * @param routeCode ルートコード
	 * @param activateDate 有効日
	 * @return 承認ルートマスタDTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	ApprovalRouteDtoInterface findForKey(String routeCode, Date activateDate) throws MospException;
	
}
