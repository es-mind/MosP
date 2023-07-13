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
/**
 * 
 */
package jp.mosp.time.bean;

import java.util.Date;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.PaidHolidayEntranceDateDtoInterface;

/**
 * 有給休暇自動付与(入社日)登録インターフェース。
 */
public interface PaidHolidayEntranceDateRegistBeanInterface extends BaseBeanInterface {
	
	/**
	 * 登録用DTOを取得する。<br>
	 * @return 初期DTO
	 */
	PaidHolidayEntranceDateDtoInterface getInitDto();
	
	/**
	 * 新規登録を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void insert(PaidHolidayEntranceDateDtoInterface dto) throws MospException;
	
	/**
	 * 論理削除(履歴)を行う。<br>
	 * @param paidHolidayCode 有休コード
	 * @param activateDate 有効日
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void delete(String paidHolidayCode, Date activateDate) throws MospException;
	
}
