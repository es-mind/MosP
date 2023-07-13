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
package jp.mosp.time.bean.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.time.bean.WorkTypePatternItemReferenceBeanInterface;
import jp.mosp.time.bean.WorkTypeReferenceBeanInterface;
import jp.mosp.time.dao.settings.WorkTypePatternDaoInterface;
import jp.mosp.time.dao.settings.WorkTypePatternItemDaoInterface;
import jp.mosp.time.dto.settings.WorkTypeDtoInterface;
import jp.mosp.time.dto.settings.WorkTypePatternDtoInterface;
import jp.mosp.time.dto.settings.WorkTypePatternItemDtoInterface;

/**
 * 勤務形態パターン項目情報参照クラス。
 */
public class WorkTypePatternItemReferenceBean extends PlatformBean
		implements WorkTypePatternItemReferenceBeanInterface {
	
	/**
	 * 勤務形態パターン項目情報DAO。
	 */
	protected WorkTypePatternItemDaoInterface	dao;
	
	/**
	 * 勤務形態パターンマスタDAO。
	 */
	protected WorkTypePatternDaoInterface		workTypePatternDao;
	
	/**
	 * 勤務形態マスタ参照クラス。
	 */
	protected WorkTypeReferenceBeanInterface	workTypeReference;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public WorkTypePatternItemReferenceBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		dao = createDaoInstance(WorkTypePatternItemDaoInterface.class);
		workTypePatternDao = createDaoInstance(WorkTypePatternDaoInterface.class);
		workTypeReference = createBeanInstance(WorkTypeReferenceBeanInterface.class);
	}
	
	@Override
	public List<WorkTypePatternItemDtoInterface> getWorkTypePatternItemList(String patternCode, Date activateDate)
			throws MospException {
		return dao.findForList(patternCode, activateDate);
	}
	
	@Override
	public String[][] getSelectArray(String patternCode, Date targetDate) throws MospException {
		return getSelectArray(patternCode, targetDate, false, false, false, false);
	}
	
	@Override
	public String[][] getNameSelectArray(String patternCode, Date targetDate) throws MospException {
		return getSelectArray(patternCode, targetDate, true, false, false, false);
	}
	
	@Override
	public String[][] getTimeSelectArray(String patternCode, Date targetDate, boolean amHoliday, boolean pmHoliday)
			throws MospException {
		return getSelectArray(patternCode, targetDate, false, true, amHoliday, pmHoliday);
	}
	
	@Override
	public String[][] getNameTimeSelectArray(String patternCode, Date targetDate, boolean amHoliday, boolean pmHoliday)
			throws MospException {
		return getSelectArray(patternCode, targetDate, true, true, amHoliday, pmHoliday);
	}
	
	/**
	 * 勤務形態プルダウン用配列を取得する。<br>
	 * @param patternCode パターンコード
	 * @param targetDate 対象年月日
	 * @param isName 名称表示(true：名称表示、false：略称表示)
	 * @param viewTime 時刻表示(true：時刻表示、false：時刻非表示)
	 * @param amHoliday 午前休の場合true、そうでない場合false
	 * @param pmHoliday 午後休の場合true、そうでない場合false
	 * @return 勤務形態プルダウン用配列
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected String[][] getSelectArray(String patternCode, Date targetDate, boolean isName, boolean viewTime,
			boolean amHoliday, boolean pmHoliday) throws MospException {
		WorkTypePatternDtoInterface workTypePatternDto = workTypePatternDao.findForInfo(patternCode, targetDate);
		if (workTypePatternDto == null) {
			// 対象データ無し
			return getNoObjectDataPulldown();
		}
		List<WorkTypePatternItemDtoInterface> list = dao.findForList(workTypePatternDto.getPatternCode(),
				workTypePatternDto.getActivateDate());
		if (list.isEmpty()) {
			// 対象データ無し
			return getNoObjectDataPulldown();
		}
		List<WorkTypeDtoInterface> workTypeList = new ArrayList<WorkTypeDtoInterface>();
		for (WorkTypePatternItemDtoInterface dto : list) {
			WorkTypeDtoInterface workTypeDto = workTypeReference.findForInfo(dto.getWorkTypeCode(), targetDate);
			if (workTypeDto == null) {
				continue;
			}
			if (workTypeDto.getInactivateFlag() == MospConst.INACTIVATE_FLAG_OFF) {
				// 無効でない場合
				workTypeList.add(workTypeDto);
			}
		}
		String[][] array = prepareSelectArray(workTypeList.size(), false);
		for (int i = 0; i < workTypeList.size(); i++) {
			WorkTypeDtoInterface workTypeDto = workTypeList.get(i);
			array[i][0] = workTypeDto.getWorkTypeCode();
			array[i][1] = workTypeDto.getWorkTypeAbbr();
			if (isName && viewTime) {
				// 名称・時刻表示
				array[i][1] = workTypeReference.getWorkTypeNameAndTime(workTypeDto.getWorkTypeCode(),
						workTypeDto.getActivateDate());
			} else if (!isName && viewTime) {
				// 略称・時刻表示
				array[i][1] = workTypeReference.getWorkTypeAbbrAndTime(workTypeDto.getWorkTypeCode(),
						workTypeDto.getActivateDate(), amHoliday, pmHoliday);
			} else if (isName && !viewTime) {
				// 名称
				array[i][1] = workTypeDto.getWorkTypeName();
			}
		}
		return array;
	}
}
