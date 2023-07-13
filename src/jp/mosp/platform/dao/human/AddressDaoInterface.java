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
import java.util.Set;

import jp.mosp.framework.base.BaseDaoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dto.human.AddressDtoInterface;

/**
 * 住所情報DAOインターフェース。
 */
public interface AddressDaoInterface extends BaseDaoInterface {
	
	/**
	 * 住所情報を取得する。<br>
	 * <br>
	 * 保持者ID、住所区分及び有効日が合致する情報が存在しない場合は、nullを返す。<br>
	 * <br>
	 * @param holderId     保持者ID
	 * @param addressType  住所区分
	 * @param activateDate 有効日
	 * @return 住所情報
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	AddressDtoInterface findForKey(String holderId, String addressType, Date activateDate) throws MospException;
	
	/**
	 * 住所情報を取得する。<br>
	 * <br>
	 * 保持者ID及び住所区分が合致する対象日以前で最新の情報を取得する。<br>
	 * 情報が存在しない場合は、nullを返す。<br>
	 * <br>
	 * <br>
	 * @param holderId    保持者ID
	 * @param addressType 住所区分
	 * @param targetDate  対象日
	 * @return 住所情報
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	AddressDtoInterface findForInfo(String holderId, String addressType, Date targetDate) throws MospException;
	
	/**
	 * 住所情報リスト(有効日昇順)を取得する。<br>
	 * @param holderId    保持者ID
	 * @param addressType 住所区分
	 * @return 住所情報リスト(有効日昇順)
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<AddressDtoInterface> findForHolder(String holderId, String addressType) throws MospException;
	
	/**
	 * 住所情報群を取得する。<br>
	 * <br>
	 * 保持者ID、有効日及び申請区分が合致する情報を取得する。<br>
	 * <br>
	 * @param holderId     保持者ID
	 * @param activateDate 有効日
	 * @param requestType  申請区分
	 * @return 住所情報リスト(有効日昇順)
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	Set<AddressDtoInterface> findForRequestType(String holderId, Date activateDate, String requestType)
			throws MospException;
	
}
