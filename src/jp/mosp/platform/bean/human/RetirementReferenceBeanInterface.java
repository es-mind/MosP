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

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dto.human.RetirementDtoInterface;

/**
 * 人事退職情報参照インターフェース。<br>
 */
public interface RetirementReferenceBeanInterface extends BaseBeanInterface {
	
	/**
	 * 退職情報を取得する。<br>
	 * 個人IDから退職情報を取得する。<br>
	 * @param personalId 個人ID
	 * @return 人事退職情報
	 * @throws MospException インスタンスの取得、SQLの作成及び実行に失敗した場合
	 */
	RetirementDtoInterface getRetireInfo(String personalId) throws MospException;
	
	/**
	 * 退職日を取得する。
	 * @param personalId 個人ID
	 * @return 退職日
	 * @throws MospException インスタンスの取得、SQLの作成及び実行に失敗した場合
	 */
	Date getRetireDate(String personalId) throws MospException;
	
	/**
	 * 退職判断。
	 * <p>
	 * 個人IDと対象日から退職を判断する。
	 * </p>
	 * @param personalId 個人ID
	 * @param targetDate 対象年月日
	 * @return 対象日に退職している場合true、そうでない場合false。
	 * @throws MospException インスタンスの取得、SQLの作成及び実行に失敗した場合
	 */
	boolean isRetired(String personalId, Date targetDate) throws MospException;
	
}
