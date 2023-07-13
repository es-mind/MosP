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
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.system.PlatformMasterBeanInterface;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.utils.PlatformUtility;
import jp.mosp.time.bean.HolidayDataReferenceBeanInterface;
import jp.mosp.time.bean.HolidayInfoReferenceBeanInterface;
import jp.mosp.time.bean.HolidayRequestReferenceBeanInterface;
import jp.mosp.time.bean.MinutesOffBeanInterface;
import jp.mosp.time.bean.TimeMasterBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dao.settings.HolidayDataDaoInterface;
import jp.mosp.time.dto.settings.HolidayDataDtoInterface;
import jp.mosp.time.dto.settings.HolidayDtoInterface;
import jp.mosp.time.dto.settings.impl.HolidayRemainDto;
import jp.mosp.time.utils.TimeUtility;

/**
 * 休暇数参照処理。<br>
 */
public class HolidayInfoReferenceBean extends PlatformBean implements HolidayInfoReferenceBeanInterface {
	
	/**
	 * 休暇データDAO。
	 */
	protected HolidayDataDaoInterface				dao;
	
	/**
	 * 休暇付与情報参照処理。<br>
	 */
	protected HolidayDataReferenceBeanInterface		refer;
	
	/**
	 * 休暇申請参照。
	 */
	protected HolidayRequestReferenceBeanInterface	holidayRequest;
	
	/**
	 * プラットフォームマスタ参照処理処理。<br>
	 */
	protected PlatformMasterBeanInterface			master;
	
	/**
	 * 勤怠関連マスタ参照処理。<br>
	 */
	protected TimeMasterBeanInterface				timeMaster;
	
	/**
	 * 分単位休暇取得処理リスト。<br>
	 */
	protected List<MinutesOffBeanInterface>			minutesBeans;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public HolidayInfoReferenceBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// DAOを準備
		dao = createDaoInstance(HolidayDataDaoInterface.class);
		// Beanを準備
		refer = createBeanInstance(HolidayDataReferenceBeanInterface.class);
		holidayRequest = createBeanInstance(HolidayRequestReferenceBeanInterface.class);
		master = createBeanInstance(PlatformMasterBeanInterface.class);
		timeMaster = createBeanInstance(TimeMasterBeanInterface.class);
		// プラットフォームマスタ参照処理を設定
		timeMaster.setPlatformMaster(master);
	}
	
	@Override
	public Set<HolidayRemainDto> getRemainHolidays(String personalId, Date targetDate, int holidayType)
			throws MospException {
		// 休暇残情報群を取得
		Set<HolidayRemainDto> dtos = getHolidayRemains(personalId, targetDate, holidayType);
		// 残数が残っている休暇残情報群を取得
		return TimeUtility.getRemainHolidays(dtos, false);
	}
	
	@Override
	public Set<HolidayRemainDto> getRemainHolidays(String personalId, Date targetDate) throws MospException {
		// 残数のある休暇残情報群を準備
		Set<HolidayRemainDto> remains = new LinkedHashSet<HolidayRemainDto>();
		// 残数のある特別休暇残情報群(有効日昇順)を設定
		remains.addAll(getRemainHolidays(personalId, targetDate, TimeConst.CODE_HOLIDAYTYPE_SPECIAL));
		// 残数のあるその他休暇残情報群(有効日昇順)を設定
		remains.addAll(getRemainHolidays(personalId, targetDate, TimeConst.CODE_HOLIDAYTYPE_OTHER));
		// 残数のある休暇残情報群を取得
		return remains;
	}
	
	@Override
	public String[][] getRemainArray(String personalId, Date targetDate, int holidayType) throws MospException {
		// 残数のある休暇残情報群を取得
		Set<HolidayRemainDto> remains = getRemainHolidays(personalId, targetDate, holidayType);
		// 休暇プルダウン用配列(残数のある休暇)を取得
		return getRemainArray(remains, true, false);
	}
	
	@Override
	public String[][] getRemainArray(Set<HolidayRemainDto> remains, boolean isAbbr, boolean needBlank) {
		// 残数のある休暇残情報が無い場合
		if (MospUtility.isEmpty(remains)) {
			// 対象データ無しの配列を取得
			return getNoObjectDataPulldown();
		}
		// 休暇残情報群(キー：休暇コード、値：休暇名称か略称)を作成(コードの重複解消及びソート(コード昇順)のため)
		Map<String, String> map = new TreeMap<String, String>();
		// 空白行要である場合
		if (needBlank) {
			// 空白行を設定
			map.put(MospConst.STR_EMPTY, MospConst.STR_EMPTY);
		}
		// 残数のある休暇残情報毎に処理
		for (HolidayRemainDto dto : remains) {
			// 休暇残情報群(キー：休暇コード、値：休暇名称か略称)に設定
			map.put(dto.getHolidayCode(), isAbbr ? dto.getHolidayAbbr() : dto.getHolidayName());
		}
		// 休暇プルダウン用配列(残数のある休暇)を取得
		return MospUtility.toArray(map);
	}
	
	@Override
	public HolidayRemainDto getAppliableHoliday(String personalId, Date targetDate, String holidayCode, int holidayType)
			throws MospException {
		// 休暇付与情報リスト(有効日昇順)を取得
		List<HolidayDataDtoInterface> dtos = getHolidays(personalId, targetDate, holidayCode, holidayType);
		// 休暇残情報群(有効日昇順)を取得
		Set<HolidayRemainDto> remains = getHolidayRemains(personalId, targetDate, dtos);
		// 休暇残情報毎に処理
		for (HolidayRemainDto remain : remains) {
			// 日が残っている場合
			if (remain.getRemainDays() > 0) {
				// 申請可能であると判断
				return remain;
			}
		}
		// 休暇残情報毎に処理
		for (HolidayRemainDto remain : remains) {
			// 時間が残っている場合
			if (remain.getRemainHours() > 0) {
				// 申請可能であると判断
				return remain;
			}
		}
		// 休暇残情報毎に処理
		for (HolidayRemainDto remain : remains) {
			// 分が残っている場合
			if (remain.getRemainMinutes() > 0) {
				// 申請可能であると判断
				return remain;
			}
		}
		// 最も有効日が遅い休暇残情報を取得
		return MospUtility.getLastValue(remains);
	}
	
	@Override
	public Set<HolidayRemainDto> getHolidayRemains(String personalId, Date targetDate, int holidayType)
			throws MospException {
		// 対象日時点で有効な休暇付与情報リスト(有効日昇順)を取得
		List<HolidayDataDtoInterface> dtos = refer.getActiveList(personalId, targetDate, holidayType);
		// 休暇残情報群(有効日昇順)を取得
		return getHolidayRemains(personalId, targetDate, dtos);
	}
	
	@Override
	public Set<HolidayRemainDto> getHolidayRemains(String personalId, Date targetDate,
			Collection<HolidayDataDtoInterface> dtos) throws MospException {
		// 人事情報を取得
		HumanDtoInterface humanDto = master.getHuman(personalId, targetDate);
		// 休暇残情報群を準備
		Set<HolidayRemainDto> remains = new LinkedHashSet<HolidayRemainDto>();
		// 有休時間取得限度時間(1日の時間数)を取得
		int hoursPerDay = timeMaster.getPaidHolidayHoursPerDay(humanDto, targetDate);
		// 1日の分数を取得
		int minutesPerDay = getMinutesPerDay(humanDto, targetDate);
		// 休暇付与情報毎に処理
		for (HolidayDataDtoInterface dto : dtos) {
			// 休暇種別情報を取得
			HolidayDtoInterface holiday = timeMaster.getHoliday(dto.getHolidayCode(), dto.getHolidayType(), targetDate);
			// 休暇種別情報が無効である場合
			if (PlatformUtility.isDtoActivate(holiday) == false) {
				// 次の休暇付与情報へ
				continue;
			}
			// 休暇残情報を作成し休暇残情報群に設定
			remains.add(getHolidayRemain(dto, holiday, hoursPerDay, minutesPerDay));
		}
		// 休暇残情報群(キー：休暇コード、値：休暇残情報)を取得
		return remains;
	}
	
	@Override
	public boolean hasPersonalApplication(String personalId, Date startDate, Date endDate, int holidayType)
			throws MospException {
		// 個人IDが設定されている、有効日の範囲内で情報を取得
		List<HolidayDataDtoInterface> list = dao.findPersonTerm(personalId, startDate, endDate, holidayType);
		// リスト確認
		if (list.isEmpty()) {
			return false;
		}
		// 期間内全て適用されていたら
		return true;
	}
	
	/**
	 * 休暇情報に対する休暇残情報を取得する。<br>
	 * @param dto           休暇情報
	 * @param holiday       休暇種別情報
	 * @param hoursPerDay   有休時間取得限度時間(1日の時間数)
	 * @param minutesPerDay 1日の分数
	 * @return 休暇残情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected HolidayRemainDto getHolidayRemain(HolidayDataDtoInterface dto, HolidayDtoInterface holiday,
			int hoursPerDay, int minutesPerDay) throws MospException {
		// 対象休暇付与情報が無期限(無制限)である場合
		if (TimeUtility.isUnlimited(dto)) {
			// 休暇残情報を取得(残数は不要(0))
			return TimeUtility.getHolidayRemains(dto, holiday, 0D, 0, 0, hoursPerDay, minutesPerDay);
		}
		// 休暇付与情報から値を取得
		String personalId = dto.getPersonalId();
		Date acquisitionDate = dto.getActivateDate();
		Date limitDate = dto.getHolidayLimitDate();
		int holidayType = dto.getHolidayType();
		String holidayCode = dto.getHolidayCode();
		// 休暇付与情報に対する利用日数及び利用時間数を取得
		Map<String, Object> map = holidayRequest.getRequestDayHour(personalId, acquisitionDate, holidayType,
				holidayCode, acquisitionDate, limitDate);
		double days = (Double)map.get(TimeConst.CODE_REQUEST_DAY);
		int hours = MospUtility.getInt(map.get(TimeConst.CODE_REQUEST_HOUR));
		int minutes = getUseMinutes(dto);
		// 残日数及び残時間数を取得
		return TimeUtility.getHolidayRemains(dto, holiday, days, hours, minutes, hoursPerDay, minutesPerDay);
	}
	
	@Override
	public int getUseMinutes(HolidayDataDtoInterface dto) throws MospException {
		// 休暇情報から値を取得
		String personalId = dto.getPersonalId();
		int holidayType = dto.getHolidayType();
		String holidayCode = dto.getHolidayCode();
		Date acquisitionDate = dto.getActivateDate();
		// 利用分数を準備
		int useMinutes = 0;
		// 分単位休暇取得処理毎に処理
		for (MinutesOffBeanInterface bean : getMinutesBeans()) {
			// 利用分数を取得し加算
			useMinutes += bean.getUseMinutes(personalId, holidayType, holidayCode, acquisitionDate);
		}
		// 利用分数を取得
		return useMinutes;
	}
	
	@Override
	public int getMinutesPerDay(String personalId, Date targetDate) throws MospException {
		// 1日の分数を取得
		return getMinutesPerDay(master.getHuman(personalId, targetDate), targetDate);
	}
	
	/**
	 * 1日の分数を取得する。<br>
	 * {@link #getMinutesPerDay(String, Date)}を参照。
	 * @param humanDto   人事情報
	 * @param targetDate 対象日
	 * @return 1日の分数
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected int getMinutesPerDay(HumanDtoInterface humanDto, Date targetDate) throws MospException {
		// 1日の分数を準備
		int minutesPerDay = 0;
		// 分単位休暇取得処理毎に処理
		for (MinutesOffBeanInterface bean : getMinutesBeans()) {
			// 1日の分数を取得
			int value = bean.getMinutesPerDay(humanDto.getPersonalId(), targetDate);
			// 分単位休暇取得処理で1日の分数として0を取得した場合
			if (value == 0) {
				// 次の分単位休暇取得処理へ
				continue;
			}
			// 1日の分数を上書
			minutesPerDay = value;
		}
		// 1日の分数を取得
		return minutesPerDay;
	}
	
	/**
	 * 分単位休暇取得処理リストを取得する。<br>
	 * @return 分単位休暇取得処理リスト
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	protected List<MinutesOffBeanInterface> getMinutesBeans() throws MospException {
		// 分単位休暇取得処理リストが設定されている場合
		if (MospUtility.isEmpty(minutesBeans) == false) {
			// 分単位休暇取得処理リストを取得
			return minutesBeans;
		}
		// 分単位休暇取得処理リストを準備
		minutesBeans = new ArrayList<MinutesOffBeanInterface>();
		// クラス名毎に処理
		for (String[] addon : MospUtility.getCodeArray(mospParams, TimeConst.CODE_KEY_MINUTES_OFF, false)) {
			// Beanを生成
			MinutesOffBeanInterface bean = (MinutesOffBeanInterface)createBean(addon[0]);
			// 参照処理を設定
			bean.setMaster(master);
			bean.setTimeMaster(timeMaster);
			// 分単位休暇取得処理リストに追加
			minutesBeans.add(bean);
		}
		// 分単位休暇取得処理リストを取得
		return minutesBeans;
	}
	
	@Override
	public List<HolidayDataDtoInterface> getHolidays(String personalId, Date targetDate, String holidayCode,
			int holidayType) throws MospException {
		// 休暇情報リスト(有効日昇順)を取得
		return dao.findForEarliestList(personalId, targetDate, holidayCode, holidayType);
	}
	
	@Override
	public void setTimeMaster(TimeMasterBeanInterface timeMaster) {
		// 勤怠関連マスタ参照処理を設定
		this.timeMaster = timeMaster;
	}
	
}
