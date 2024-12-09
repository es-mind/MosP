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
package jp.mosp.platform.bean.system.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.system.SectionSearchBeanInterface;
import jp.mosp.platform.dao.system.SectionDaoInterface;
import jp.mosp.platform.dto.system.SectionDtoInterface;

/**
 * 所属マスタ検索クラス。
 */
public class SectionSearchBean extends PlatformBean implements SectionSearchBeanInterface {
	
	/**
	 * 所属マスタDAO。
	 */
	protected SectionDaoInterface	sectionDao;
	
	/**
	 * 対象日。
	 */
	private Date					targetDate;
	
	/**
	 * 所属コード。
	 */
	private String					sectionCode;
	
	/**
	 * 所属名称。
	 */
	private String					sectionName;
	
	/**
	 * 所属略称。
	 */
	private String					sectionAbbr;
	
	/**
	 * 無効フラグ。
	 */
	private String					closeFlag;
	
	/**
	 * 検索対象区分。
	 */
	private String					sectionType;
	
	
	/**
	 * コンストラクタ。
	 */
	public SectionSearchBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// 所属マスタDAO取得
		sectionDao = createDaoInstance(SectionDaoInterface.class);
	}
	
	@Override
	public List<SectionDtoInterface> getSearchList() throws MospException {
		// Mapに検索条件を設定
		Map<String, Object> param = sectionDao.getParamsMap();
		param.put(SectionDaoInterface.SEARCH_TARGET_DATE, targetDate);
		param.put(SectionDaoInterface.SEARCH_SECTION_TYPE, sectionType);
		param.put(SectionDaoInterface.SEARCH_SECTION_CODE, sectionCode);
		param.put(SectionDaoInterface.SEARCH_SECTION_NAME, sectionName);
		param.put(SectionDaoInterface.SEARCH_SECTION_ABBR, sectionAbbr);
		param.put(SectionDaoInterface.SEARCH_CLOSE_FLAG, closeFlag);
		// 検索
		return sectionDao.findForSearch(param);
	}
	
	@Override
	public List<SectionDtoInterface> getExportList() throws MospException {
		// Mapに検索条件を設定
		Map<String, Object> param = sectionDao.getParamsMap();
		param.put(SectionDaoInterface.SEARCH_TARGET_DATE, targetDate);
		param.put(SectionDaoInterface.SEARCH_SECTION_CODE, sectionCode);
		param.put(SectionDaoInterface.SEARCH_CLOSE_FLAG, closeFlag);
		// 検索
		return sectionDao.findForExport(param);
	}
	
	@Override
	public void setSectionDao(SectionDaoInterface sectionDao) {
		this.sectionDao = sectionDao;
	}
	
	@Override
	public void setSectionCode(String sectionCode) {
		this.sectionCode = sectionCode;
	}
	
	@Override
	public void setActivateDate(Date activateDate) {
		targetDate = getDateClone(activateDate);
	}
	
	@Override
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}
	
	@Override
	public void setSectionAbbr(String sectionAbbr) {
		this.sectionAbbr = sectionAbbr;
	}
	
	@Override
	public void setCloseFlag(String closeFlag) {
		this.closeFlag = closeFlag;
	}
	
	@Override
	public void setSectionType(String sectionType) {
		this.sectionType = sectionType;
	}
	
}
