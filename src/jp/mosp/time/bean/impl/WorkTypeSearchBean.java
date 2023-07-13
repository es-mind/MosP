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
import java.util.Map;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.time.bean.WorkTypeSearchBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dao.settings.WorkTypeDaoInterface;
import jp.mosp.time.dao.settings.WorkTypeItemDaoInterface;
import jp.mosp.time.dto.settings.WorkTypeDtoInterface;
import jp.mosp.time.dto.settings.WorkTypeListDtoInterface;
import jp.mosp.time.dto.settings.impl.TmmWorkTypeListDto;

/**
 * 勤務形態マスタ検索クラス。
 */
public class WorkTypeSearchBean extends PlatformBean implements WorkTypeSearchBeanInterface {
	
	/**
	 * 勤務形態マスタDAO。
	 */
	protected WorkTypeDaoInterface		workTypeDao;
	
	/**
	 * 勤務形態項目マスタDAO。
	 */
	protected WorkTypeItemDaoInterface	workTypeItemDao;
	
	/**
	 * 有効日。
	 */
	private Date						activateDate;
	
	/**
	 * 勤務形態コード。
	 */
	private String						workTypeCode;
	
	/**
	 * 勤務形態名称。
	 */
	private String						workTypeName;
	
	/**
	 * 勤務形態略称。
	 */
	private String						workTypeAbbr;
	
	/**
	 * 有効無効フラグ。
	 */
	private String						inactivateFlag;
	
	
	/**
	 * コンストラクタ。
	 */
	public WorkTypeSearchBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// 勤務形態マスタDAO取得
		workTypeDao = createDaoInstance(WorkTypeDaoInterface.class);
		// 勤務形態項目マスタDAO取得
		workTypeItemDao = createDaoInstance(WorkTypeItemDaoInterface.class);
	}
	
	@Override
	public List<WorkTypeListDtoInterface> getSearchList() throws MospException {
		// Mapに検索条件を設定
		Map<String, Object> param = workTypeDao.getParamsMap();
		param.put("activateDate", activateDate);
		param.put("workTypeCode", workTypeCode);
		param.put("workTypeName", workTypeName);
		param.put("workTypeAbbr", workTypeAbbr);
		param.put("inactivateFlag", inactivateFlag);
		// 検索
		List<WorkTypeDtoInterface> list = workTypeDao.findForSearch(param);
		List<WorkTypeListDtoInterface> workTypeList = new ArrayList<WorkTypeListDtoInterface>();
		for (WorkTypeDtoInterface dto : list) {
			String workTypeCode = dto.getWorkTypeCode();
			Date activateDate = dto.getActivateDate();
			// 初期化
			WorkTypeListDtoInterface workTypeListDto = new TmmWorkTypeListDto();
			workTypeListDto.setTmmWorkTypeId(dto.getTmmWorkTypeId());
			workTypeListDto.setWorkTypeCode(workTypeCode);
			workTypeListDto.setActivateDate(activateDate);
			workTypeListDto.setWorkTypeName(dto.getWorkTypeName());
			workTypeListDto.setWorkTypeAbbr(dto.getWorkTypeAbbr());
			workTypeListDto.setInactivateFlag(dto.getInactivateFlag());
			workTypeListDto.setStartTime(workTypeItemDao
				.findForKey(workTypeCode, activateDate, TimeConst.CODE_WORKSTART).getWorkTypeItemValue());
			workTypeListDto.setEndTime(workTypeItemDao.findForKey(workTypeCode, activateDate, TimeConst.CODE_WORKEND)
				.getWorkTypeItemValue());
			workTypeListDto.setWorkTime(workTypeItemDao.findForKey(workTypeCode, activateDate, TimeConst.CODE_WORKTIME)
				.getWorkTypeItemValue());
			workTypeListDto.setRestTime(workTypeItemDao.findForKey(workTypeCode, activateDate, TimeConst.CODE_RESTTIME)
				.getWorkTypeItemValue());
			workTypeListDto.setFrontTime(getTimestamp(getMinute(
					workTypeItemDao.findForKey(workTypeCode, activateDate, TimeConst.CODE_FRONTSTART)
						.getWorkTypeItemValue(),
					workTypeItemDao.findForKey(workTypeCode, activateDate, TimeConst.CODE_FRONTEND)
						.getWorkTypeItemValue())));
			workTypeListDto.setBackTime(getTimestamp(getMinute(
					workTypeItemDao.findForKey(workTypeCode, activateDate, TimeConst.CODE_BACKSTART)
						.getWorkTypeItemValue(),
					workTypeItemDao.findForKey(workTypeCode, activateDate, TimeConst.CODE_BACKEND)
						.getWorkTypeItemValue())));
			workTypeList.add(workTypeListDto);
		}
		return workTypeList;
	}
	
	@Override
	public void setActivateDate(Date activateDate) {
		this.activateDate = getDateClone(activateDate);
	}
	
	@Override
	public void setWorkTypeCode(String workTypeCode) {
		this.workTypeCode = workTypeCode;
	}
	
	@Override
	public void setWorkTypeName(String workTypeName) {
		this.workTypeName = workTypeName;
	}
	
	@Override
	public void setWorkTypeAbbr(String workTypeAbbr) {
		this.workTypeAbbr = workTypeAbbr;
	}
	
	@Override
	public void setInactivateFlag(String inactivateFlag) {
		this.inactivateFlag = inactivateFlag;
	}
	
	/**
	 * 分取得。<br>
	 * @param startTime 開始時刻
	 * @param endTime 終了時刻
	 * @return 分
	 * @throws MospException 例外発生時
	 */
	protected int getMinute(Date startTime, Date endTime) throws MospException {
		Date defaultTime = DateUtility.getDefaultTime();
		return getMinute(
				DateUtility.getHour(startTime, defaultTime) * TimeConst.CODE_DEFINITION_HOUR
						+ DateUtility.getMinute(startTime),
				DateUtility.getHour(endTime, defaultTime) * TimeConst.CODE_DEFINITION_HOUR
						+ DateUtility.getMinute(endTime));
	}
	
	/**
	 * 分取得。<br>
	 * @param startMinute 開始時刻
	 * @param endMinute 終了時刻
	 * @return 分
	 */
	protected int getMinute(int startMinute, int endMinute) {
		int minute = endMinute - startMinute;
		if (minute >= 0) {
			return minute;
		}
		// 0未満の場合は0を返す
		return 0;
	}
	
	/**
	 * 時間取得。<br>
	 * @param minute 分
	 * @return 時間
	 * @throws MospException 例外発生時
	 */
	protected Date getTimestamp(int minute) throws MospException {
		return DateUtility.addMinute(DateUtility.getDefaultTime(), minute);
	}
	
}
