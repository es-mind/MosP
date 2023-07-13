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
package jp.mosp.time.bean;

import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.bean.human.HumanSearchBeanInterface;
import jp.mosp.time.dto.settings.SubordinateFiscalListDtoInterface;

/**
 * 統計情報検索インターフェース。<br>
 */
public interface SubordinateFiscalSearchBeanInterface extends HumanSearchBeanInterface {
	
	/**
	 * 統計情報一覧画面で検索したリストを取得する。<br><br>
	 * 設定された条件で、検索を行う。
	 * @return 部下リスト
	 * @throws MospException インスタンスの取得、SQLの作成及び実行に失敗した場合
	 */
	List<SubordinateFiscalListDtoInterface> getSubordinateFiscalList() throws MospException;
	
	/**
	 * @param displayYear セットする 表示年度。
	 */
	void setDisplayYear(int displayYear);
	
	/**
	 * @param targetYear セットする 対象年。
	 */
	void setTargetYear(int targetYear);
	
	/**
	 * @param targetMonth セットする 対象月。
	 */
	void setTargetMonth(int targetMonth);
	
	/**
	 * @param humanType セットする 人区分。
	 */
	void setHumanType(String humanType);
	
}
