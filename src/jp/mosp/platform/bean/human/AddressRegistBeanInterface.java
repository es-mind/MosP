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
package jp.mosp.platform.bean.human;

import java.util.Date;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dto.human.AddressDtoInterface;

/**
 * 住所情報登録インターフェース。<br>
 */
public interface AddressRegistBeanInterface extends BaseBeanInterface {
	
	/**
	 * 登録用DTOを取得する。<br>
	 * @return 初期DTO
	 */
	AddressDtoInterface getInitDto();
	
	/**
	 * 人事住所情報の登録を行う。<br>
	 * <br>
	 * 同一保持者ID、住所区分及び有効日の情報が存在する場合は、
	 * 論理削除の上、登録する。<br>
	 * <br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void registPersonalAddress(AddressDtoInterface dto) throws MospException;
	
	/**
	 * 住民票住所情報の登録を行う。<br>
	 * <br>
	 * 同一保持者ID、住所区分及び有効日の情報が存在する場合は、
	 * 論理削除の上、登録する。<br>
	 * <br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void registLegalAddress(AddressDtoInterface dto) throws MospException;
	
	/**
	 * 住所情報の削除を行う。<br>
	 * <br>
	 * 保持者ID、有効日及び申請区分が合致する情報を削除する。<br>
	 * 合致する情報が存在しない場合は、何もしない。<br>
	 * <br>
	 * @param holderId     保持者ID
	 * @param activateDate 有効日
	 * @param requestType  申請区分
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void deleteAddress(String holderId, Date activateDate, String requestType) throws MospException;
	
	/**
	 * 登録情報の妥当性を確認確認する。<br>
	 * 行インデックスがnullでない場合、エラーメッセージに行番号が加えられる。<br>
	 * <br>
	 * 保持者ID、有効日及び申請区分が合致する情報が存在し
	 * 登録情報と申請区分が異なる場合は、エラーメッセージを設定する。<br>
	 * <br>
	 * @param dto 対象DTO
	 * @param row 行インデックス
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void validate(AddressDtoInterface dto, Integer row) throws MospException;
	
}
