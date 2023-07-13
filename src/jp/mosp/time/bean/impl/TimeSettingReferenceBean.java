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
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.time.base.TimeBean;
import jp.mosp.time.bean.ApplicationReferenceBeanInterface;
import jp.mosp.time.bean.TimeSettingReferenceBeanInterface;
import jp.mosp.time.constant.TimeMessageConst;
import jp.mosp.time.dao.settings.LimitStandardDaoInterface;
import jp.mosp.time.dao.settings.TimeSettingDaoInterface;
import jp.mosp.time.dto.settings.ApplicationDtoInterface;
import jp.mosp.time.dto.settings.LimitStandardDtoInterface;
import jp.mosp.time.dto.settings.TimeSettingDtoInterface;
import jp.mosp.time.entity.TimeSettingEntityInterface;
import jp.mosp.time.utils.TimeUtility;

/**
 * 勤怠設定参照クラス。<br>
 */
public class TimeSettingReferenceBean extends TimeBean implements TimeSettingReferenceBeanInterface {
	
	/**
	 * 勤怠設定管理DAO。<br>
	 */
	protected TimeSettingDaoInterface			dao;
	
	/**
	 * 限度基準管理DAO。<br>
	 */
	protected LimitStandardDaoInterface			limitStandardDao;
	
	/**
	 * 設定適用管理参照処理。<br>
	 */
	protected ApplicationReferenceBeanInterface	applicationReference;
	
	
	/**
	 * {@link TimeBean#TimeBean()}を実行する。<br>
	 */
	public TimeSettingReferenceBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		dao = createDaoInstance(TimeSettingDaoInterface.class);
		limitStandardDao = createDaoInstance(LimitStandardDaoInterface.class);
		applicationReference = createBeanInstance(ApplicationReferenceBeanInterface.class);
	}
	
	@Override
	public TimeSettingDtoInterface getTimeSettingInfo(String workSettingCode, Date targetDate) throws MospException {
		return dao.findForInfo(workSettingCode, targetDate);
	}
	
	@Override
	public List<TimeSettingDtoInterface> getTimeSettingHistory(String timeSettingCode) throws MospException {
		return dao.findForHistory(timeSettingCode);
	}
	
	@Override
	public String getTimeSettingAbbr(String workSettingCode, Date targetDate) throws MospException {
		TimeSettingDtoInterface dto = getTimeSettingInfo(workSettingCode, targetDate);
		if (dto == null) {
			return workSettingCode;
		}
		return dto.getWorkSettingAbbr();
	}
	
	@Override
	public String[][] getCodedSelectArray(Date targetDate, boolean needBlank) throws MospException {
		// プルダウン用配列取得(コード+名称)
		return getSelectArray(targetDate, needBlank, true, true);
	}
	
	@Override
	public TimeSettingDtoInterface findForKey(String workSettingCode, Date activateDate) throws MospException {
		return dao.findForKey(workSettingCode, activateDate);
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
		List<TimeSettingDtoInterface> list = dao.findForActivateDate(targetDate);
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
		for (TimeSettingDtoInterface dto : list) {
			// コード設定
			array[idx][0] = dto.getWorkSettingCode();
			// 表示内容設定
			if (isName && viewCode) {
				// コード+名称
				array[idx++][1] = getCodedName(dto.getWorkSettingCode(), dto.getWorkSettingName(), length);
			} else if (isName) {
				// 名称
				array[idx++][1] = dto.getWorkSettingName();
			} else if (viewCode) {
				// コード+略称
				array[idx++][1] = getCodedName(dto.getWorkSettingCode(), dto.getWorkSettingAbbr(), length);
			} else {
				// 略称
				array[idx++][1] = dto.getWorkSettingAbbr();
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
	protected int getMaxCodeLength(List<TimeSettingDtoInterface> list, boolean viewCode) {
		// コード表示確認
		if (viewCode == false) {
			return 0;
		}
		// コード最大文字数
		int length = 0;
		// コード最大文字数確認
		for (TimeSettingDtoInterface dto : list) {
			if (dto.getWorkSettingCode().length() > length) {
				length = dto.getWorkSettingCode().length();
			}
		}
		return length;
	}
	
	@Override
	public List<String> getWorkSettingCode(String cutoffcode, Date activateDate) throws MospException {
		// 勤怠設定コードリスト準備
		List<String> workSettingCodeList = new ArrayList<String>();
		// 対象の勤怠設定管理DTOを取得
		List<TimeSettingDtoInterface> list = dao.findForInfoList(cutoffcode, activateDate);
		// データ存在チェック
		if (list.isEmpty()) {
			return workSettingCodeList;
		}
		// 勤怠設定コードを取得する
		for (int i = 0; i < list.size(); i++) {
			workSettingCodeList.add(list.get(i).getWorkSettingCode());
		}
		return workSettingCodeList;
	}
	
	@Override
	public int getGeneralWorkHour(String workSettingCode, Date targetDate) throws MospException {
		TimeSettingDtoInterface dto = getTimeSettingInfo(workSettingCode, targetDate);
		if (dto == null) {
			return 8;
		}
		int hour = DateUtility.getHour(dto.getGeneralWorkTime());
		if (DateUtility.getMinute(dto.getGeneralWorkTime()) == 0) {
			return hour;
		}
		return hour + 1;
	}
	
	@Override
	public void chkExistTimeSetting(TimeSettingDtoInterface dto, Date targetDate) {
		if (dto != null) {
			return;
		}
		String stringDate = getStringDate(targetDate);
		if (stringDate == null || stringDate.isEmpty()) {
			stringDate = getStringDate(getSystemDate());
		}
		StringBuffer sb = new StringBuffer();
		sb.append(mospParams.getName("WorkManage"));
		sb.append(mospParams.getName("Set"));
		sb.append(mospParams.getName("Information"));
		mospParams.addErrorMessage(TimeMessageConst.MSG_SETTING_APPLICATION_DEFECT, stringDate, sb.toString());
	}
	
	@Override
	public Integer getBeforeOvertimeFlag(String personalId, Date targetDate) throws MospException {
		ApplicationDtoInterface applicationDto = applicationReference.findForPerson(personalId, targetDate);
		applicationReference.chkExistApplication(applicationDto, targetDate);
		if (mospParams.hasErrorMessage()) {
			return null;
		}
		TimeSettingDtoInterface dto = dao.findForInfo(applicationDto.getWorkSettingCode(), targetDate);
		chkExistTimeSetting(dto, targetDate);
		if (mospParams.hasErrorMessage()) {
			return null;
		}
		return dto.getBeforeOvertimeFlag();
	}
	
	@Override
	public String[][] getSelectArray(Date targetDate, boolean needBlank) throws MospException {
		// プルダウン用配列取得(略称)
		return getSelectArray(targetDate, needBlank, false, false);
	}
	
	@Override
	public boolean isProspectsMonth(int targetMonth, String prospectsMonths) {
		// 未設定
		if (prospectsMonths == null) {
			return false;
			
		}
		// 見込月配列
		String[] aryMonths = MospUtility.split(prospectsMonths, MospConst.APP_PROPERTY_SEPARATOR);
		for (String month : aryMonths) {
			// 見込月であるか判定
			if (month.equals(String.valueOf(targetMonth))) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String getPerformanceInputModeString() {
		return mospParams.getName("Performance", "Input", "Medium");
	}
	
	@Override
	public TimeSettingEntityInterface getEntity(String workSettingCode, Date targetDate) throws MospException {
		//  勤怠設定エンティティ
		return getEntity(dao.findForInfo(workSettingCode, targetDate));
	}
	
	@Override
	public TimeSettingEntityInterface getEntityForKey(String workSettingCode, Date activateDate) throws MospException {
		//  勤怠設定エンティティ
		return getEntity(dao.findForKey(workSettingCode, activateDate));
	}
	
	@Override
	public TimeSettingEntityInterface getEntity(TimeSettingDtoInterface dto) throws MospException {
		// 勤怠設定エンティティを準備
		TimeSettingEntityInterface entity = TimeUtility.getBareTimeSettingEntity(mospParams);
		// 勤怠設定エンティティに勤怠設定情報を設定
		entity.setTimeSettingDto(dto);
		// 勤怠設定情報が存在しない場合
		if (entity.isExist() == false) {
			// 空のエンティティを取得
			return entity;
		}
		// 勤怠設定コード及び有効日を取得
		String workSettingCode = dto.getWorkSettingCode();
		Date activateDate = dto.getActivateDate();
		// 勤怠設定エンティティに限度基準情報群を設定
		entity.setLimitStandardDtos(getLimitStandards(workSettingCode, activateDate));
		// 勤怠設定エンティティを取得
		return entity;
	}
	
	/**
	 * 限度基準情報群(キー：期間)を取得する。<br>
	 * @param workSettingCode 勤怠設定コード
	 * @param activateDate    有効日
	 * @return 限度基準情報群(キー：期間)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected Map<String, LimitStandardDtoInterface> getLimitStandards(String workSettingCode, Date activateDate)
			throws MospException {
		// 限度基準情報群(キー：期間)を取得
		return TimeUtility.getLimitStandards(limitStandardDao.findForSearch(workSettingCode, activateDate));
	}
	
}
