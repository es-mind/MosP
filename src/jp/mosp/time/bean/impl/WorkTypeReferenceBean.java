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
import java.util.TreeMap;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.time.bean.WorkTypeReferenceBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dao.settings.WorkTypeDaoInterface;
import jp.mosp.time.dao.settings.WorkTypeItemDaoInterface;
import jp.mosp.time.dto.settings.WorkTypeDtoInterface;
import jp.mosp.time.dto.settings.WorkTypeItemDtoInterface;
import jp.mosp.time.dto.settings.impl.TmmWorkTypeDto;
import jp.mosp.time.entity.WorkTypeEntityInterface;
import jp.mosp.time.utils.TimeNamingUtility;
import jp.mosp.time.utils.TimeUtility;

/**
 * 勤務形態マスタ参照クラス。
 */
public class WorkTypeReferenceBean extends PlatformBean implements WorkTypeReferenceBeanInterface {
	
	/**
	 * 勤務形態マスタDAO。
	 */
	protected WorkTypeDaoInterface		dao;
	
	/**
	 * 勤務形態項目管理DAO。
	 */
	protected WorkTypeItemDaoInterface	workTypeItemDao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public WorkTypeReferenceBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		dao = createDaoInstance(WorkTypeDaoInterface.class);
		workTypeItemDao = createDaoInstance(WorkTypeItemDaoInterface.class);
	}
	
	@Override
	public WorkTypeDtoInterface getWorkTypeInfo(String workTypeCode, Date targetDate) throws MospException {
		// 勤務形態確認(法定休日出勤)
		if (workTypeCode.equals(TimeConst.CODE_WORK_ON_LEGAL_HOLIDAY)) {
			return getWorkOnLegalHolidayWorkType();
		}
		// 勤務形態確認(所定休日出勤)
		if (workTypeCode.equals(TimeConst.CODE_WORK_ON_PRESCRIBED_HOLIDAY)) {
			return getWorkOnPrescribedHolidayWorkType();
		}
		// 勤務形態マスタ情報取得
		return findForInfo(workTypeCode, targetDate);
	}
	
	@Override
	public List<WorkTypeDtoInterface> getWorkTypeHistory(String workTypeCode) throws MospException {
		return dao.findForHistory(workTypeCode);
	}
	
	@Override
	public String getWorkTypeAbbr(String workTypeCode, Date targetDate) throws MospException {
		// 勤務形態確認(所定休日)
		if (workTypeCode.equals(TimeConst.CODE_HOLIDAY_PRESCRIBED_HOLIDAY)) {
			return TimeNamingUtility.prescribedHolidayAbbr(mospParams);
		}
		// 勤務形態確認(法定休日)
		if (workTypeCode.equals(TimeConst.CODE_HOLIDAY_LEGAL_HOLIDAY)) {
			return TimeNamingUtility.legalHolidayAbbr(mospParams);
		}
		// 勤務形態マスタ情報取得及び確認
		WorkTypeDtoInterface dto = getWorkTypeInfo(workTypeCode, targetDate);
		if (dto == null) {
			// 勤務形態マスタ情報が存在しない場合
			return workTypeCode;
		}
		// 勤務形態マスタ情報略称取得
		return dto.getWorkTypeAbbr();
	}
	
	@Override
	public String getParticularWorkTypeName(String workTypeCode) {
		// 法定休日出勤の場合
		if (TimeConst.CODE_WORK_ON_LEGAL_HOLIDAY.equals(workTypeCode)) {
			return getWorkOnLegalHolidayNaming();
		}
		// 所定休日出勤の場合
		if (TimeConst.CODE_WORK_ON_PRESCRIBED_HOLIDAY.equals(workTypeCode)) {
			return getWorkOnPrescribedHolidayNaming();
		}
		// 法定休日の場合
		if (TimeConst.CODE_HOLIDAY_LEGAL_HOLIDAY.equals(workTypeCode)) {
			return TimeNamingUtility.legalHoliday(mospParams);
		}
		// 所定休日の場合
		if (TimeConst.CODE_HOLIDAY_PRESCRIBED_HOLIDAY.equals(workTypeCode)) {
			return TimeNamingUtility.prescribedHoliday(mospParams);
		}
		return null;
	}
	
	@Override
	public String getWorkTypeNameAndTime(String workTypeCode, Date targetDate) throws MospException {
		WorkTypeDtoInterface dto = findForInfo(workTypeCode, targetDate);
		if (dto == null) {
			return workTypeCode;
		}
		StringBuffer sb = new StringBuffer();
		sb.append(dto.getWorkTypeName());
		sb.append(getWorkBeginAndWorkEnd(dto));
		return sb.toString();
	}
	
	@Override
	public String getWorkTypeAbbrAndTime(String workTypeCode, Date targetDate) throws MospException {
		// 勤務形態マスタからレコードを取得
		WorkTypeDtoInterface dto = findForInfo(workTypeCode, targetDate);
		if (dto == null) {
			return workTypeCode;
		}
		StringBuffer sb = new StringBuffer();
		sb.append(dto.getWorkTypeAbbr());
		sb.append(getWorkBeginAndWorkEnd(dto));
		return sb.toString();
	}
	
	@Override
	public String getWorkTypeAbbrAndTime(String workTypeCode, Date targetDate, boolean amHoliday, boolean pmHoliday)
			throws MospException {
		WorkTypeDtoInterface dto = findForInfo(workTypeCode, targetDate);
		if (dto == null) {
			return workTypeCode;
		}
		StringBuffer sb = new StringBuffer();
		sb.append(dto.getWorkTypeAbbr());
		sb.append(getWorkBeginAndWorkEnd(dto, amHoliday, pmHoliday));
		return sb.toString();
	}
	
	@Override
	public String[][] getSelectArray(Date targetDate) throws MospException {
		return getSelectArray(targetDate, true, false, false, false);
	}
	
	@Override
	public String[][] getCodedSelectArray(Date targetDate) throws MospException {
		// 一覧取得
		List<WorkTypeDtoInterface> list = dao.findForActivateDate(targetDate);
		// 一覧件数確認
		if (list.isEmpty()) {
			// 対象データ無し
			return getNoObjectDataPulldown();
		}
		// 配列及びインデックス宣言
		String[][] array = new String[list.size()][2];
		int idx = 0;
		// コードの最大文字数確認
		int codeLength = 0;
		for (WorkTypeDtoInterface dto : list) {
			if (dto.getWorkTypeCode().length() > codeLength) {
				codeLength = dto.getWorkTypeCode().length();
			}
		}
		// 配列作成
		for (WorkTypeDtoInterface dto : list) {
			array[idx][0] = dto.getWorkTypeCode();
			array[idx][1] = getCodedName(dto.getWorkTypeCode(), dto.getWorkTypeAbbr(), codeLength);
			idx++;
		}
		return array;
	}
	
	@Override
	public String[][] getTimeSelectArray(Date targetDate) throws MospException {
		return getSelectArray(targetDate, false, true, false, false);
	}
	
	@Override
	public String[][] getTimeSelectArray(Date targetDate, boolean amHoliday, boolean pmHoliday) throws MospException {
		return getSelectArray(targetDate, false, true, amHoliday, pmHoliday);
	}
	
	@Override
	public String[][] getNameTimeSelectArray(Date targetDate) throws MospException {
		return getSelectArray(targetDate, true, true, false, false);
	}
	
	@Override
	public String[][] getNameTimeSelectArray(Date targetDate, boolean amHoliday, boolean pmHoliday)
			throws MospException {
		return getSelectArray(targetDate, true, true, amHoliday, pmHoliday);
	}
	
	@Override
	public WorkTypeDtoInterface findForKey(String workTypeCode, Date activateDate) throws MospException {
		return dao.findForKey(workTypeCode, activateDate);
	}
	
	@Override
	public String[][] getSelectAbbrArray(Date targetDate) throws MospException {
		return getSelectArray(targetDate, false, false, false, false);
	}
	
	@Override
	public WorkTypeDtoInterface findForInfo(String workTypeCode, Date activateDate) throws MospException {
		return dao.findForInfo(workTypeCode, activateDate);
	}
	
	@Override
	public List<WorkTypeEntityInterface> getWorkTypeEntityHistory(String workTypeCode) throws MospException {
		// 勤務形態エンティティ履歴(有効日昇順)を準備
		List<WorkTypeEntityInterface> history = new ArrayList<WorkTypeEntityInterface>();
		// 勤務形態情報履歴を取得
		List<WorkTypeDtoInterface> list = getWorkTypeHistory(workTypeCode);
		// 勤務形態情報毎に処理
		for (WorkTypeDtoInterface dto : list) {
			// 有効日を取得
			Date activateDate = dto.getActivateDate();
			// 勤務形態項目情報リストを取得
			List<WorkTypeItemDtoInterface> itemList = workTypeItemDao.findForWorkType(workTypeCode, activateDate);
			// 勤務形態エンティティを取得し設定
			history.add(createWorkTypeEntity(dto, itemList));
		}
		// 勤務形態エンティティ履歴(有効日昇順)を取得
		return history;
	}
	
	@Override
	public WorkTypeEntityInterface getWorkTypeEntity(String workTypeCode, Date targetDate) throws MospException {
		// 勤務形態情報取得
		WorkTypeDtoInterface dto = getWorkTypeInfo(workTypeCode, targetDate);
		// 勤務形態情報確認
		if (dto != null) {
			// 勤務形態項目情報リスト取得
			List<WorkTypeItemDtoInterface> list = workTypeItemDao.findForWorkType(workTypeCode, dto.getActivateDate());
			// 勤務形態エンティティを取得
			return createWorkTypeEntity(dto, list);
		}
		// 勤務形態情報(休日及び休出)を取得
		dto = getExtraWorkTypeDto(workTypeCode);
		// 勤務形態エンティティを取得
		return createWorkTypeEntity(dto, new ArrayList<WorkTypeItemDtoInterface>());
	}
	
	/**
	 * 勤務形態情報(休日及び休出)を取得する。<br>
	 * 勤務形態コードが休日及び休出でない場合は、勤務形態コード及び有効日(デフォルト日付)
	 * のみを設定した勤務形態情報を取得する。<br>
	 * @param workTypeCode 勤務形態コード
	 * @return	勤務形態情報(休日及び休出)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected WorkTypeDtoInterface getExtraWorkTypeDto(String workTypeCode) throws MospException {
		// 勤務形態情報を準備
		WorkTypeDtoInterface dto = getInitDto();
		dto.setWorkTypeCode(workTypeCode);
		dto.setWorkTypeAbbr(MospConst.STR_EMPTY);
		// 法定休日出勤の場合
		if (MospUtility.isEqual(workTypeCode, TimeConst.CODE_WORK_ON_LEGAL_HOLIDAY)) {
			// 法定休日出勤勤務形態情報を取得
			dto = getWorkOnLegalHolidayWorkType();
		}
		// 所定休日出勤の場合
		if (MospUtility.isEqual(workTypeCode, TimeConst.CODE_WORK_ON_PRESCRIBED_HOLIDAY)) {
			// 所定休日出勤勤務形態情報を取得
			dto = getWorkOnPrescribedHolidayWorkType();
		}
		// 法定休日の場合
		if (MospUtility.isEqual(workTypeCode, TimeConst.CODE_HOLIDAY_LEGAL_HOLIDAY)) {
			// 法定休日勤務形態情報を取得
			dto = getLegalHolidayWorkType();
		}
		// 所定休日の場合
		if (MospUtility.isEqual(workTypeCode, TimeConst.CODE_HOLIDAY_PRESCRIBED_HOLIDAY)) {
			// 所定休日勤務形態情報を取得
			dto = getPrescribedHolidayWorkType();
		}
		// 勤務形態情報の有効日(デフォルト日付)を設定
		dto.setActivateDate(DateUtility.getDefaultTime());
		// 勤務形態情報を取得
		return dto;
	}
	
	@Override
	public Map<String, List<WorkTypeEntityInterface>> getExtraWorkTypeEntityHistories() throws MospException {
		// 勤務形態エンティティ履歴(休日及び休出)群(キー：勤務形態コード)を準備
		Map<String, List<WorkTypeEntityInterface>> histories = new TreeMap<String, List<WorkTypeEntityInterface>>();
		// 法定休日出勤を設定
		histories.put(TimeConst.CODE_WORK_ON_LEGAL_HOLIDAY,
				getExtraWorkTypeEntityHistory(TimeConst.CODE_WORK_ON_LEGAL_HOLIDAY));
		// 所定休日出勤を設定
		histories.put(TimeConst.CODE_WORK_ON_PRESCRIBED_HOLIDAY,
				getExtraWorkTypeEntityHistory(TimeConst.CODE_WORK_ON_PRESCRIBED_HOLIDAY));
		// 法定休日を設定
		histories.put(TimeConst.CODE_HOLIDAY_LEGAL_HOLIDAY,
				getExtraWorkTypeEntityHistory(TimeConst.CODE_HOLIDAY_LEGAL_HOLIDAY));
		// 所定休日を設定
		histories.put(TimeConst.CODE_HOLIDAY_PRESCRIBED_HOLIDAY,
				getExtraWorkTypeEntityHistory(TimeConst.CODE_HOLIDAY_PRESCRIBED_HOLIDAY));
		// 空を設定
		histories.put(MospConst.STR_EMPTY, getExtraWorkTypeEntityHistory(MospConst.STR_EMPTY));
		// 勤務形態エンティティ履歴(休日及び休出)群(キー：勤務形態コード)を取得
		return histories;
	}
	
	/**
	 * 勤務形態エンティティ履歴(休日及び休出)を取得する。<br>
	 * @param workTypeCode 勤務形態コード
	 * @return	勤務形態エンティティ履歴(休日及び休出)
	 * @throws MospException オブジェクトの生成に失敗した場合
	 */
	protected List<WorkTypeEntityInterface> getExtraWorkTypeEntityHistory(String workTypeCode) throws MospException {
		// 勤務形態エンティティ履歴(休日及び休出)を準備
		List<WorkTypeEntityInterface> history = new ArrayList<WorkTypeEntityInterface>();
		// 勤務形態エンティティ履歴(休日及び休出)に設定
		history.add(createWorkTypeEntity(getExtraWorkTypeDto(workTypeCode), new ArrayList<WorkTypeItemDtoInterface>()));
		// 勤務形態エンティティ履歴(休日及び休出)を取得
		return history;
	}
	
	/**
	 * プルダウン用配列を取得する。<br>
	 * @param targetDate 対象年月日
	 * @param isName 名称表示(true：名称表示、false：略称表示)
	 * @param viewTime 時刻表示(true：時刻表示、false：時刻非表示)
	 * @param amHoliday 午前休の場合true、そうでない場合false
	 * @param pmHoliday 午後休の場合true、そうでない場合false
	 * @return プルダウン用配列
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected String[][] getSelectArray(Date targetDate, boolean isName, boolean viewTime, boolean amHoliday,
			boolean pmHoliday) throws MospException {
		// 一覧取得
		List<WorkTypeDtoInterface> list = dao.findForActivateDate(targetDate);
		// 一覧件数確認
		if (list.isEmpty()) {
			// 対象データ無し
			return getNoObjectDataPulldown();
		}
		// 配列宣言
		String[][] array = prepareSelectArray(list.size(), false);
		// 配列作成
		for (int i = 0; i < list.size(); i++) {
			WorkTypeDtoInterface dto = list.get(i);
			StringBuffer sb = new StringBuffer();
			if (isName) {
				// 名称
				sb.append(dto.getWorkTypeName());
			} else {
				// 略称
				sb.append(dto.getWorkTypeAbbr());
			}
			if (viewTime) {
				// 時刻表示
				sb.append(getWorkBeginAndWorkEnd(dto, amHoliday, pmHoliday));
			}
			array[i][0] = dto.getWorkTypeCode();
			array[i][1] = sb.toString();
		}
		return array;
	}
	
	/**
	 * 【出勤時刻～退勤時刻】を取得する。<br>
	 * @param dto 対象DTO
	 * @return 【出勤時刻～退勤時刻】
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected String getWorkBeginAndWorkEnd(WorkTypeDtoInterface dto) throws MospException {
		WorkTypeItemDtoInterface workBeginDto = workTypeItemDao.findForKey(dto.getWorkTypeCode(), dto.getActivateDate(),
				TimeConst.CODE_WORKSTART);
		WorkTypeItemDtoInterface workEndDto = workTypeItemDao.findForKey(dto.getWorkTypeCode(), dto.getActivateDate(),
				TimeConst.CODE_WORKEND);
		Date defaultTime = DateUtility.getDefaultTime();
		StringBuffer sb = new StringBuffer();
		sb.append(mospParams.getName("FrontWithCornerParentheses"));
		sb.append(DateUtility.getStringTime(workBeginDto.getWorkTypeItemValue(), defaultTime));
		sb.append(mospParams.getName("Wave"));
		sb.append(DateUtility.getStringTime(workEndDto.getWorkTypeItemValue(), defaultTime));
		sb.append(mospParams.getName("BackWithCornerParentheses"));
		return sb.toString();
	}
	
	/**
	 * 【始業時刻～終業時刻】を取得する。<br>
	 * @param dto 対象DTO
	 * @param amHoliday 午前休の場合true、そうでない場合false
	 * @param pmHoliday 午後休の場合true、そうでない場合false
	 * @return 【始業時刻～終業時刻】
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected String getWorkBeginAndWorkEnd(WorkTypeDtoInterface dto, boolean amHoliday, boolean pmHoliday)
			throws MospException {
		Date defaultTime = DateUtility.getDefaultTime();
		WorkTypeEntityInterface workTypeEntity = getWorkTypeEntity(dto.getWorkTypeCode(), dto.getActivateDate());
		if (amHoliday) {
			// 午前休である場合
			StringBuffer sb = new StringBuffer();
			sb.append(mospParams.getName("FrontWithCornerParentheses"));
			sb.append(DateUtility.getStringTime(workTypeEntity.getBackStartTime(), defaultTime));
			sb.append(mospParams.getName("Wave"));
			sb.append(DateUtility.getStringTime(workTypeEntity.getBackEndTime(), defaultTime));
			sb.append(mospParams.getName("BackWithCornerParentheses"));
			return sb.toString();
		}
		if (pmHoliday) {
			// 午後休である場合
			StringBuffer sb = new StringBuffer();
			sb.append(mospParams.getName("FrontWithCornerParentheses"));
			sb.append(DateUtility.getStringTime(workTypeEntity.getFrontStartTime(), defaultTime));
			sb.append(mospParams.getName("Wave"));
			sb.append(DateUtility.getStringTime(workTypeEntity.getFrontEndTime(), defaultTime));
			sb.append(mospParams.getName("BackWithCornerParentheses"));
			return sb.toString();
		}
		// 午前休・午後休でない場合
		return getWorkBeginAndWorkEnd(dto);
	}
	
	/**
	 * 法定休日出勤勤務形態情報を取得する。<br>
	 * @return 法定休日出勤勤務形態情報
	 */
	protected WorkTypeDtoInterface getWorkOnLegalHolidayWorkType() {
		// DTO準備
		WorkTypeDtoInterface dto = getInitDto();
		// 勤務形態コード設定
		dto.setWorkTypeCode(TimeConst.CODE_WORK_ON_LEGAL_HOLIDAY);
		// 勤務形態略称設定
		dto.setWorkTypeAbbr(getWorkOnLegalHolidayAbbrNaming());
		return dto;
	}
	
	/**
	 * 所定休日出勤勤務形態情報を取得する。<br>
	 * @return 所定休日出勤勤務形態情報
	 */
	protected WorkTypeDtoInterface getWorkOnPrescribedHolidayWorkType() {
		// DTO準備
		WorkTypeDtoInterface dto = getInitDto();
		// 勤務形態コード設定
		dto.setWorkTypeCode(TimeConst.CODE_WORK_ON_PRESCRIBED_HOLIDAY);
		// 勤務形態略称設定
		dto.setWorkTypeAbbr(getWorkOnPrescribedHolidayAbbrNaming());
		return dto;
	}
	
	/**
	 * 法定休日勤務形態情報を取得する。<br>
	 * @return 法定休日勤務形態情報
	 */
	protected WorkTypeDtoInterface getLegalHolidayWorkType() {
		// DTO準備
		WorkTypeDtoInterface dto = getInitDto();
		// 勤務形態コード設定
		dto.setWorkTypeCode(TimeConst.CODE_HOLIDAY_LEGAL_HOLIDAY);
		// 勤務形態名称設定
		dto.setWorkTypeName(TimeNamingUtility.legalHoliday(mospParams));
		// 勤務形態略称設定
		dto.setWorkTypeAbbr(TimeNamingUtility.legalHolidayAbbr(mospParams));
		return dto;
	}
	
	/**
	 * 所定休日勤務形態情報を取得する。<br>
	 * @return 所定休日勤務形態情報
	 */
	protected WorkTypeDtoInterface getPrescribedHolidayWorkType() {
		// DTO準備
		WorkTypeDtoInterface dto = getInitDto();
		// 勤務形態コード設定
		dto.setWorkTypeCode(TimeConst.CODE_HOLIDAY_PRESCRIBED_HOLIDAY);
		// 勤務形態名称設定
		dto.setWorkTypeName(TimeNamingUtility.prescribedHoliday(mospParams));
		// 勤務形態略称設定
		dto.setWorkTypeAbbr(TimeNamingUtility.prescribedHolidayAbbr(mospParams));
		return dto;
	}
	
	/**
	 * 初期化された勤務形態情報を取得する。<br>
	 * @return 初期化された勤務形態情報
	 */
	protected WorkTypeDtoInterface getInitDto() {
		// DTO準備
		WorkTypeDtoInterface dto = new TmmWorkTypeDto();
		return dto;
	}
	
	/**
	 * 勤務形態エンティティの作成
	 * @param workTypeDto 勤務形態情報
	 * @param itemDtoList 勤務形態項目情報リスト
	 * @return 勤務形態エンティティ
	 * @throws MospException オブジェクトの生成に失敗した場合
	 */
	protected WorkTypeEntityInterface createWorkTypeEntity(WorkTypeDtoInterface workTypeDto,
			List<WorkTypeItemDtoInterface> itemDtoList) throws MospException {
		WorkTypeEntityInterface entity = TimeUtility.getBareWorkTypeEntity(mospParams);
		entity.setWorkTypeDto(workTypeDto);
		entity.setWorkTypeItemList(itemDtoList);
		return entity;
	}
	
	/**
	 * 所定休出略称を取得する。<br>
	 * @return 所定代休略称
	 */
	protected String getWorkOnPrescribedHolidayAbbrNaming() {
		return mospParams.getName("PrescribedAbbreviation") + mospParams.getName("WorkingHoliday");
	}
	
	/**
	 * 法定休出略称を取得する。<br>
	 * @return 法定代休略称
	 */
	protected String getWorkOnLegalHolidayAbbrNaming() {
		return mospParams.getName("LegalAbbreviation") + mospParams.getName("WorkingHoliday");
	}
	
	/**
	 * 所定休日出勤名称を取得する。<br>
	 * @return 所定休日出勤名称
	 */
	protected String getWorkOnPrescribedHolidayNaming() {
		return mospParams.getName("Prescribed") + mospParams.getName("DayOff") + mospParams.getName("GoingWork");
	}
	
	/**
	 * 法定休日出勤名称を取得する。<br>
	 * @return 法定休日出勤名称
	 */
	protected String getWorkOnLegalHolidayNaming() {
		return mospParams.getName("Legal") + mospParams.getName("DayOff") + mospParams.getName("GoingWork");
	}
	
}
