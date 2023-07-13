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
package jp.mosp.platform.bean.workflow.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.bean.human.base.PlatformHumanBean;
import jp.mosp.platform.bean.system.SectionReferenceBeanInterface;
import jp.mosp.platform.bean.workflow.SubApproverSearchBeanInterface;
import jp.mosp.platform.dao.human.HumanDaoInterface;
import jp.mosp.platform.dao.workflow.SubApproverDaoInterface;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.dto.workflow.SubApproverDtoInterface;
import jp.mosp.platform.dto.workflow.SubApproverListDtoInterface;
import jp.mosp.platform.dto.workflow.impl.SubApproverListDto;
import jp.mosp.platform.utils.MonthUtility;

/**
 * 代理承認者テーブル検索クラス。
 */
public class SubApproverSearchBean extends PlatformHumanBean implements SubApproverSearchBeanInterface {
	
	/**
	 * 代理承認者テーブルDAO。
	 */
	protected SubApproverDaoInterface	dao;
	
	/**
	 * 代理元個人ID。
	 */
	private String						personalId;
	
	/**
	 * 対象年。
	 */
	private int							targetYear;
	
	/**
	 * 対象月。
	 */
	private int							targetMonth;
	
	/**
	 * 代理人所属名。
	 */
	private String						sectionName;
	
	/**
	 * 代理人氏名。
	 */
	private String						employeeName;
	
	/**
	 * 有効無効フラグ。
	 */
	private String						inactivateFlag;
	
	
	/**
	 * {@link PlatformHumanBean#PlatformHumanBean()}を実行する。<br>
	 */
	public SubApproverSearchBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// 継承基の処理を実行
		super.initBean();
		// DAOを準備
		dao = createDaoInstance(SubApproverDaoInterface.class);
	}
	
	@Override
	public List<SubApproverListDtoInterface> getSearchList() throws MospException {
		// Mapに検索条件を設定
		Map<String, Object> param = dao.getParamsMap();
		param.put(SubApproverDaoInterface.SEARCH_PERSONAL_ID, personalId);
		param.put(SubApproverDaoInterface.SEARCH_START_DATE,
				MonthUtility.getYearMonthTermFirstDate(targetYear, targetMonth, mospParams));
		param.put(SubApproverDaoInterface.SEARCH_END_DATE,
				MonthUtility.getYearMonthTermLastDate(targetYear, targetMonth, mospParams));
		param.put(SubApproverDaoInterface.SEARCH_SECTION_NAME, sectionName);
		param.put(SubApproverDaoInterface.SEARCH_EMPLOYEE_NAME, employeeName);
		param.put(SubApproverDaoInterface.SEARCH_INACTIVATE_FLAG, inactivateFlag);
		// 検索
		List<SubApproverDtoInterface> list = dao.findForSearch(param);
		// 検索リスト準備
		List<SubApproverListDtoInterface> searchList = new ArrayList<SubApproverListDtoInterface>();
		// 人事マスタDAO準備
		HumanDaoInterface humanDao = createDaoInstance(HumanDaoInterface.class);
		// 所属名称プルダウン準備
		SectionReferenceBeanInterface section = createBeanInstance(SectionReferenceBeanInterface.class);
		String[][] sectionArray = section
			.getNameSelectArray(MonthUtility.getYearMonthTargetDate(targetYear, targetMonth, mospParams), false, null);
		// 検索リストDTOに変換
		for (SubApproverDtoInterface dto : list) {
			// 検索リストDTO取得
			SubApproverListDtoInterface listDto = getListDto(dto);
			// 人事情報取得
			HumanDtoInterface humanDto = humanDao.findForInfo(dto.getSubApproverId(), dto.getStartDate());
			if (humanDto != null) {
				// 人事情報設定
				listDto.setSubApproverCode(humanDto.getEmployeeCode());
				listDto.setSubApproverName(getHumanName(humanDto));
				listDto.setSubApproverSectionCode(humanDto.getSectionCode());
				// 所属情報取得
				listDto.setSubApproverSectionName(getCodeName(humanDto.getSectionCode(), sectionArray));
			}
			// 検索リストDTOをリストに追加
			searchList.add(listDto);
		}
		return searchList;
	}
	
	/**
	 * 代理承認者テーブル検索リストDTOを取得する。<br>
	 * 代理承認者テーブルDTOの値を引き継ぐ。<br>
	 * @param dto 代理承認者テーブルDTO
	 * @return 代理承認者テーブル検索リストDTO
	 */
	protected SubApproverListDtoInterface getListDto(SubApproverDtoInterface dto) {
		// 検索リストDTO準備
		SubApproverListDtoInterface listDto = new SubApproverListDto();
		// 検索リストDTOに値を設定
		listDto.setSubApproverNo(dto.getSubApproverNo());
		listDto.setPersonalId(dto.getPersonalId());
		listDto.setWorkflowType(dto.getWorkflowType());
		listDto.setStartDate(dto.getStartDate());
		listDto.setEndDate(dto.getEndDate());
		listDto.setSubApproverId(dto.getSubApproverId());
		listDto.setInactivateFlag(dto.getInactivateFlag());
		// 初期化
		listDto.setSubApproverCode("");
		listDto.setSubApproverName("");
		listDto.setSubApproverSectionCode("");
		listDto.setSubApproverSectionName("");
		return listDto;
	}
	
	@Override
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
	
	@Override
	public void setTargetYear(int targetYear) {
		this.targetYear = targetYear;
	}
	
	@Override
	public void setTargetMonth(int targetMonth) {
		this.targetMonth = targetMonth;
	}
	
	@Override
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}
	
	@Override
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	
	@Override
	public void setInactivateFlag(String inactivateFlag) {
		this.inactivateFlag = inactivateFlag;
	}
	
}
