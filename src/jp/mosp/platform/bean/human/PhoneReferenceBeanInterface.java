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

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dto.human.PhoneDtoInterface;

/**
 * 電話情報参照インターフェース
 */
public interface PhoneReferenceBeanInterface extends BaseBeanInterface {
	
	/**
	 * 対象個人IDの電話情報リスト(有効日昇順)を取得する。<br>
	 * @param personalId 個人ID
	 * @return 電話情報リスト(有効日昇順)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<PhoneDtoInterface> getPersonalPhoneList(String personalId) throws MospException;
	
	/**
	 * 対象個人IDの対象日以前で最新の電話情報を取得する。<br>
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @return 個人電話情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	PhoneDtoInterface getLatestPersonalPhone(String personalId, Date targetDate) throws MospException;
	
}
