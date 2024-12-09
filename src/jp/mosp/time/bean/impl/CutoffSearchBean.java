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
/**
 * 
 */
package jp.mosp.time.bean.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.time.bean.CutoffSearchBeanInterface;
import jp.mosp.time.dao.settings.CutoffDaoInterface;
import jp.mosp.time.dto.settings.CutoffDtoInterface;

/**
 * 締日管理検索処理。
 */
public class CutoffSearchBean extends PlatformBean implements CutoffSearchBeanInterface {
	
	/**
	 * 締日管理DAO。
	 */
	protected CutoffDaoInterface	cutoffDao;
	
	/**
	 * 有効日。
	 */
	private Date					activateDate;
	
	/**
	 * 締日コード
	 */
	private String					cutoffCode;
	
	/**
	 * 締日名称。
	 */
	private String					cutoffName;
	
	/**
	 * 締日略称。
	 */
	private String					cutoffAbbr;
	
	/**
	 * 締日。
	 */
	private String					cutoffDate;
	
	/**
	 * 未承認仮締。
	 */
	private String					noApproval;
	
	/**
	 * 自己月締。
	 */
	private String					selfTightening;
	
	/**
	 * 有効無効フラグ。
	 */
	private String					inactivateFlag;
	
	
	/**
	 * コンストラクタ。
	 */
	public CutoffSearchBean() {
		// 処理無し
	}
	
	@Override
	public void initBean() throws MospException {
		// 締日管理マスタDAO取得
		cutoffDao = createDaoInstance(CutoffDaoInterface.class);
	}
	
	@Override
	public List<CutoffDtoInterface> getSearchList() throws MospException {
		// Mapに検索条件を設定
		Map<String, Object> param = cutoffDao.getParamsMap();
		param.put(CutoffDaoInterface.SEARCH_TARGET_DATE, activateDate);
		param.put(CutoffDaoInterface.SEARCH_CUTOFF_CODE, cutoffCode);
		param.put(CutoffDaoInterface.SEARCH_CUTOFF_NAME, cutoffName);
		param.put(CutoffDaoInterface.SEARCH_CUTOFF_ABBR, cutoffAbbr);
		param.put(CutoffDaoInterface.SEARCH_CUTOFF_DATE, cutoffDate);
		param.put(CutoffDaoInterface.SEARCH_NO_APPROVAL, noApproval);
		param.put(CutoffDaoInterface.SEARCH_SELF_TIGHTENING, selfTightening);
		param.put(CutoffDaoInterface.SEARCH_INACTIVATE_FLAG, inactivateFlag);
		// 検索
		return cutoffDao.findForSearch(param);
	}
	
	@Override
	public void setActivateDate(Date activateDate) {
		this.activateDate = getDateClone(activateDate);
	}
	
	@Override
	public void setCutoffCode(String cutoffCode) {
		this.cutoffCode = cutoffCode;
	}
	
	@Override
	public void setCutoffName(String cutoffName) {
		this.cutoffName = cutoffName;
	}
	
	@Override
	public void setCutoffAbbr(String cutoffAbbr) {
		this.cutoffAbbr = cutoffAbbr;
	}
	
	@Override
	public void setCutoffDate(String cutoffDate) {
		this.cutoffDate = cutoffDate;
	}
	
	@Override
	public void setInactivateFlag(String inactivateFlag) {
		this.inactivateFlag = inactivateFlag;
	}
	
	@Override
	public void setNoApproval(String noApproval) {
		this.noApproval = noApproval;
	}
	
	@Override
	public void setSelfTightening(String selfTightening) {
		this.selfTightening = selfTightening;
	}
	
}
