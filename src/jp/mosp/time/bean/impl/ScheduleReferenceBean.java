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

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.time.bean.ScheduleReferenceBeanInterface;
import jp.mosp.time.constant.TimeMessageConst;
import jp.mosp.time.dao.settings.ScheduleDaoInterface;
import jp.mosp.time.dto.settings.ScheduleDtoInterface;

/**
 * カレンダ管理参照クラス。
 */
public class ScheduleReferenceBean extends PlatformBean implements ScheduleReferenceBeanInterface {
	
	/**
	 * カレンダマスタDAO。
	 */
	protected ScheduleDaoInterface dao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public ScheduleReferenceBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		dao = createDaoInstance(ScheduleDaoInterface.class);
	}
	
	@Override
	public ScheduleDtoInterface getScheduleInfo(String scheduleCode, Date targetDate) throws MospException {
		return dao.findForInfo(scheduleCode, targetDate);
	}
	
	@Override
	public List<ScheduleDtoInterface> getScheduleHistory(String scheduleCode) throws MospException {
		return dao.findForHistory(scheduleCode);
	}
	
	@Override
	public String getScheduleName(String scheduleCode, Date targetDate) throws MospException {
		ScheduleDtoInterface dto = getScheduleInfo(scheduleCode, targetDate);
		if (dto == null) {
			return scheduleCode;
		}
		return dto.getScheduleName();
	}
	
	@Override
	public String getScheduleAbbr(String scheduleCode, Date targetDate) throws MospException {
		ScheduleDtoInterface dto = getScheduleInfo(scheduleCode, targetDate);
		if (dto == null) {
			return scheduleCode;
		}
		return dto.getScheduleAbbr();
	}
	
	@Override
	public String[][] getCodedSelectArray(Date targetDate, boolean needBlank) throws MospException {
		// プルダウン用配列取得(コード+名称)
		return getSelectArray(targetDate, needBlank, true, true);
	}
	
	@Override
	public ScheduleDtoInterface findForKey(String scheduleCode, Date activateDate) throws MospException {
		return dao.findForKey(scheduleCode, activateDate);
	}
	
	/**
	 * プルダウン用配列を取得する。<br>
	 * @param targetDate 対象年月日
	 * @param needBlank 空白行要否(true：空白行要、false：空白行不要)
	 * @param isName 名称表示(true：名称表示、false：略称表示)
	 * @param viewCode コード表示(true：コード表示、false：コード非表示)
	 * @return プルダウン用配列
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected String[][] getSelectArray(Date targetDate, boolean needBlank, boolean isName, boolean viewCode)
			throws MospException {
		// 一覧取得
		List<ScheduleDtoInterface> list = dao.findForActivateDate(targetDate);
		// 一覧件数確認
		if (list.size() == 0) {
			// 対象データ無し
			return getNoObjectDataPulldown();
		}
		// コード最大長取得
		int length = getMaxCodeLength(list, viewCode);
		// プルダウン用配列及びインデックス準備
		String[][] array = prepareSelectArray(list.size(), needBlank);
		int idx = needBlank ? 1 : 0;
		// プルダウン用配列作成
		for (ScheduleDtoInterface dto : list) {
			// コード設定
			array[idx][0] = dto.getScheduleCode();
			// 表示内容設定
			if (isName && viewCode) {
				// コード+名称
				array[idx++][1] = getCodedName(dto.getScheduleCode(), dto.getScheduleName(), length);
			} else if (isName) {
				// 名称
				array[idx++][1] = dto.getScheduleName();
			} else if (viewCode) {
				// コード+略称
				array[idx++][1] = getCodedName(dto.getScheduleCode(), dto.getScheduleAbbr(), length);
			} else {
				// 略称
				array[idx++][1] = dto.getScheduleAbbr();
			}
		}
		return array;
	}
	
	/**
	 * リスト中のDTOにおけるコード最大文字数を取得する。<br>
	 * @param list     対象リスト
	 * @param viewCode コード表示(true：コード表示、false：コード非表示)
	 * @return リスト中のDTOにおけるコード最大文字数
	 */
	protected int getMaxCodeLength(List<ScheduleDtoInterface> list, boolean viewCode) {
		// コード表示確認
		if (viewCode == false) {
			return 0;
		}
		// コード最大文字数
		int length = 0;
		// コード最大文字数確認
		for (ScheduleDtoInterface dto : list) {
			if (dto.getScheduleCode().length() > length) {
				length = dto.getScheduleCode().length();
			}
		}
		return length;
	}
	
	@Override
	public void chkExistSchedule(ScheduleDtoInterface dto, Date targetDate) {
		if (dto == null) {
			String errorMes1 = DateUtility.getStringDate(targetDate);
			String errorMes2 = mospParams.getName("Calendar", "Set", "Information");
			mospParams.addErrorMessage(TimeMessageConst.MSG_SETTING_APPLICATION_DEFECT, errorMes1, errorMes2);
		}
	}
	
	@Override
	public String[][] getSelectArray(Date targetDate, boolean needBlank) throws MospException {
		// プルダウン用配列取得(略称)
		return getSelectArray(targetDate, needBlank, false, false);
	}
}
