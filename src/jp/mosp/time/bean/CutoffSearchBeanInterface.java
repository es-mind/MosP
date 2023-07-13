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
import java.util.List;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.CutoffDtoInterface;

/**
 * 締日管理検索インターフェース。
 */
public interface CutoffSearchBeanInterface extends BaseBeanInterface {
	
	/**
	 * 条件による検索。
	 * <p>
	 * 検索条件から締日リストを取得。
	 * </p>
	 * @return 締日リスト
	 * @throws MospException インスタンスの取得、SQLの作成及び実行に失敗した場合
	 */
	List<CutoffDtoInterface> getSearchList() throws MospException;
	
	/**
	 * @param activateDate セットする 有効日。
	 */
	void setActivateDate(Date activateDate);
	
	/**
	 * @param cutoffCode セットする 締日コード。
	 */
	void setCutoffCode(String cutoffCode);
	
	/**
	 * @param cutoffName セットする 締日名称。
	 */
	void setCutoffName(String cutoffName);
	
	/**
	 * @param cutoffAbbr セットする 締日略称。
	 */
	void setCutoffAbbr(String cutoffAbbr);
	
	/**
	 * @param cutoffDate セットする 締日。
	 */
	void setCutoffDate(String cutoffDate);
	
	/**
	 * @param noApproval セットする 未承認仮締。
	 */
	void setNoApproval(String noApproval);
	
	/**
	 * @param selfTightening セットする 自己月締。
	 */
	void setSelfTightening(String selfTightening);
	
	/**
	 * @param inactivateFlag セットする 有効無効フラグ。
	 */
	void setInactivateFlag(String inactivateFlag);
	
}
