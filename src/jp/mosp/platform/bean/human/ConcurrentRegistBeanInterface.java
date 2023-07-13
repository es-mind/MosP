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
package jp.mosp.platform.bean.human;

import java.util.List;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dto.human.ConcurrentDtoInterface;

/**
 * 人事兼務情報登録インターフェース
 */
public interface ConcurrentRegistBeanInterface extends BaseBeanInterface {
	
	/**
	 * 登録用DTOを取得する。
	 * @return 初期DTO
	 */
	ConcurrentDtoInterface getInitDto();
	
	/**
	 * 登録用DTOリストを取得する。
	 * @param size サイズ
	 * @return 初期DTOリスト
	 */
	List<ConcurrentDtoInterface> getInitDtoList(int size);
	
	/**
	 * 登録を行う。<br>
	 * レコード識別IDが0の場合は新規登録、そうでない場合は更新を行う。<br>
	 * 但し、更新対象の情報が変更されていない場合は、何もしない。<br>
	 * @param list 対象DTOリスト
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void regist(List<ConcurrentDtoInterface> list) throws MospException;
	
	/**
	 * 論理削除を行う。<br>
	 * レコード識別IDで取得される情報に対して削除を行う。<br>
	 * @param idArray 対象レコード識別ID配列
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void delete(long[] idArray) throws MospException;
	
}
