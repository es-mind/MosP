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

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.system.PlatformMasterBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.utils.MonthUtility;
import jp.mosp.platform.utils.PlatformUtility;
import jp.mosp.time.bean.CutoffReferenceBeanInterface;
import jp.mosp.time.bean.LimitStandardReferenceBeanInterface;
import jp.mosp.time.bean.TimeMasterBeanInterface;
import jp.mosp.time.bean.WorkTypeReferenceBeanInterface;
import jp.mosp.time.dao.settings.ApplicationDaoInterface;
import jp.mosp.time.dao.settings.HolidayDaoInterface;
import jp.mosp.time.dao.settings.PaidHolidayDaoInterface;
import jp.mosp.time.dao.settings.TimeSettingDaoInterface;
import jp.mosp.time.dto.settings.ApplicationDtoInterface;
import jp.mosp.time.dto.settings.DifferenceRequestDtoInterface;
import jp.mosp.time.dto.settings.HolidayDtoInterface;
import jp.mosp.time.dto.settings.LimitStandardDtoInterface;
import jp.mosp.time.dto.settings.PaidHolidayDtoInterface;
import jp.mosp.time.dto.settings.TimeSettingDtoInterface;
import jp.mosp.time.entity.ApplicationEntity;
import jp.mosp.time.entity.CutoffEntityInterface;
import jp.mosp.time.entity.TimeSettingEntityInterface;
import jp.mosp.time.entity.WorkTypeEntityInterface;
import jp.mosp.time.utils.DifferenceUtility;
import jp.mosp.time.utils.TimeUtility;
import jp.mosp.time.utils.TotalTimeUtility;

/**
 * 勤怠関連マスタ参照処理。<br>
 * <br>
 * 取得したマスタの情報をフィールドに保持しておき、
 * DBにアクセスすることなく再取得できるようにする。<br>
 * <br>
 * 集計や一覧表示で一件ずつ処理する場合等、一度取得した
 * マスタの情報を使い回すことで、パフォーマンスの向上を図る。<br>
 * <br>
 * 一覧表示で件数が少ない場合にパフォーマンスが落ちないように、
 * マスタ全件を一度に取得するような処理は行わない。<br>
 * <br>
 * DBにアクセスする回数が減る分メモリを使うことになるため、
 * 保持する情報の量に応じてメモリを調整する必要がある。<br>
 * <br>
 */
public class TimeMasterBean extends PlatformBean implements TimeMasterBeanInterface {
	
	/**
	 * 設定適用マスタDAOクラス。<br>
	 */
	protected ApplicationDaoInterface						applicationDao;
	
	/**
	 * 勤怠設定管理DAOクラス。<br>
	 */
	protected TimeSettingDaoInterface						timeSettingDao;
	
	/**
	 * 有給休暇設定DAOクラス。<br>
	 */
	protected PaidHolidayDaoInterface						paidHolidayDao;
	
	/**
	 * 休暇種別管理DAOクラス。<br>
	 */
	protected HolidayDaoInterface							holidayDao;
	
	/**
	 * 締日参照処理。<br>
	 */
	protected CutoffReferenceBeanInterface					cutoffRefer;
	
	/**
	 * 限度基準参照処理。<br>
	 */
	protected LimitStandardReferenceBeanInterface			limitStandardRefer;
	
	/**
	 * 勤務形態参照クラス。<br>
	 */
	protected WorkTypeReferenceBeanInterface				workTypeRefer;
	
	/**
	 * プラットフォームマスタ参照処理。<br>
	 */
	protected PlatformMasterBeanInterface					platformMaster;
	
	/**
	 * From日付(設定適用情報有効日群取得範囲)。<br>
	 */
	protected Date											applicationFromDate;
	
	/**
	 * To日付(設定適用情報有効日群取得範囲)。<br>
	 */
	protected Date											applicationToDate;
	
	/**
	 * 設定適用情報有効日群。<br>
	 * <br>
	 * 設定適用情報有効日群取得範囲に有効日を持つ設定適用情報の有効日群。<br>
	 * <br>
	 */
	protected Set<Date>										applicationDateSet;
	
	/**
	 * 設定適用情報(個人)群(キー：対象日)。<br>
	 * <br>
	 * 対象日における最新の設定適用情報群。<br>
	 * <br>
	 */
	protected Map<Date, Set<ApplicationDtoInterface>>		applicationPersonMap;
	
	/**
	 * 設定適用情報(マスタ)群(キー：対象日)。<br>
	 * <br>
	 * 対象日における最新の設定適用情報群。<br>
	 * <br>
	 */
	protected Map<Date, Set<ApplicationDtoInterface>>		applicationMasterMap;
	
	/**
	 * 勤怠設定情報群(キー：対象日)。<br>
	 * <br>
	 * 対象日における最新の勤怠設定情報群。<br>
	 * <br>
	 */
	protected Map<Date, Set<TimeSettingDtoInterface>>		timeSettingMap;
	
	/**
	 * 有給休暇設定情報群(キー：対象日)。<br>
	 * <br>
	 * 対象日における最新の有給休暇設定情報群。<br>
	 * <br>
	 */
	protected Map<Date, Set<PaidHolidayDtoInterface>>		paidHolidayMap;
	
	/**
	 * 締日エンティティ群(キー：対象日)。<br>
	 * <br>
	 * 対象日における最新の締日エンティティ群。<br>
	 * <br>
	 */
	protected Map<Date, Set<CutoffEntityInterface>>			cutoffMap;
	
	/**
	 * 限度基準情報群(キー：設定適用コード)。<br>
	 * <br>
	 * 設定適用コードに対する全限度基準情報群(複数の有効日が存在し得る)。<br>
	 * <br>
	 */
	protected Map<String, Set<LimitStandardDtoInterface>>	limitStandardMap;
	
	/**
	 * 勤務形態エンティティ群(キー：勤務形態コード)。<br>
	 * <br>
	 * 値は勤務形態エンティティ履歴(有効日昇順)。<br>
	 * <br>
	 */
	protected Map<String, List<WorkTypeEntityInterface>>	workTypeMap;
	
	/**
	 * 休暇種別情報群(キー：対象日)。<br>
	 * <br>
	 * 対象日における最新の休暇種別情報群。<br>
	 * <br>
	 */
	protected Map<Date, Set<HolidayDtoInterface>>			holidayMap;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public TimeMasterBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// DAOを準備
		applicationDao = createDaoInstance(ApplicationDaoInterface.class);
		timeSettingDao = createDaoInstance(TimeSettingDaoInterface.class);
		paidHolidayDao = createDaoInstance(PaidHolidayDaoInterface.class);
		holidayDao = createDaoInstance(HolidayDaoInterface.class);
		// Beanを準備
		cutoffRefer = createBeanInstance(CutoffReferenceBeanInterface.class);
		limitStandardRefer = createBeanInstance(LimitStandardReferenceBeanInterface.class);
		workTypeRefer = createBeanInstance(WorkTypeReferenceBeanInterface.class);
		platformMaster = createBeanInstance(PlatformMasterBeanInterface.class);
		// フィールドの初期化
		applicationDateSet = new HashSet<Date>();
		applicationPersonMap = new HashMap<Date, Set<ApplicationDtoInterface>>();
		applicationMasterMap = new HashMap<Date, Set<ApplicationDtoInterface>>();
		timeSettingMap = new HashMap<Date, Set<TimeSettingDtoInterface>>();
		paidHolidayMap = new HashMap<Date, Set<PaidHolidayDtoInterface>>();
		cutoffMap = new HashMap<Date, Set<CutoffEntityInterface>>();
		limitStandardMap = new HashMap<String, Set<LimitStandardDtoInterface>>();
		workTypeMap = new HashMap<String, List<WorkTypeEntityInterface>>();
		holidayMap = new HashMap<Date, Set<HolidayDtoInterface>>();
	}
	
	@Override
	public void setPlatformMaster(PlatformMasterBeanInterface platformMaster) {
		// プラットフォームマスタ参照処理を設定
		this.platformMaster = platformMaster;
	}
	
	@Override
	public ApplicationDtoInterface getApplication(String personalId, Date targetDate) throws MospException {
		// 人事情報を取得
		HumanDtoInterface humanDto = platformMaster.getHuman(personalId, targetDate);
		// 対象日時点における最新の有効な情報から設定適用情報を取得
		return getApplication(humanDto, targetDate);
	}
	
	@Override
	public ApplicationDtoInterface getApplication(HumanDtoInterface humanDto, Date targetDate) throws MospException {
		// 設定適用情報群取得
		Set<ApplicationDtoInterface> personSet = getApplicationPersonSet(targetDate);
		Set<ApplicationDtoInterface> masterSet = getApplicationMasterSet(targetDate);
		// 適用情報群から対象人事情報が適用される適用情報を取得
		return (ApplicationDtoInterface)PlatformUtility.getApplicationMaster(humanDto, personSet, masterSet);
	}
	
	@Override
	public PaidHolidayDtoInterface getPaidHoliday(HumanDtoInterface humanDto, Date targetDate) throws MospException {
		// 対象人事情報が適用される適用情報を取得
		ApplicationDtoInterface application = getApplication(humanDto, targetDate);
		// 適用情報が取得できなかった場合
		if (application == null) {
			// nullを取得
			return null;
		}
		// 有給休暇設定情報を取得
		return getPaidHoliday(application.getPaidHolidayCode(), targetDate);
	}
	
	@Override
	public Map<Date, ApplicationDtoInterface> getApplicationMap(String personalId, Date firstDate, Date lastDate)
			throws MospException {
		// 設定適用郡準備
		Map<Date, ApplicationDtoInterface> map = new HashMap<Date, ApplicationDtoInterface>();
		// 人事情報履歴(有効日昇順)を取得
		List<HumanDtoInterface> humanList = platformMaster.getHumanHistory(personalId);
		// 有効日リスト取得
		Set<Date> activateDateSet = getApplicationDateSet(firstDate, lastDate);
		// 人事情報毎に処理
		for (HumanDtoInterface humanDto : humanList) {
			// 有効日追加
			activateDateSet.add(humanDto.getActivateDate());
		}
		// 期間開始日を追加
		activateDateSet.add(firstDate);
		// 設定適用準備
		ApplicationDtoInterface applicationDto = null;
		// 期間開始日日毎に設定
		for (Date targetDate : TimeUtility.getDateList(firstDate, lastDate)) {
			// 有効日セットに含まれている場合
			if (activateDateSet.contains(targetDate)) {
				// 対象日以前で最新の人事情報を取得
				HumanDtoInterface humanDto = (HumanDtoInterface)PlatformUtility.getLatestDto(humanList, targetDate);
				// 人事情報が存在する場合
				if (humanDto != null) {
					// 設定適用を再設定
					applicationDto = getApplication(humanDto, targetDate);
				}
			}
			// 設定適用追加
			map.put(targetDate, applicationDto);
		}
		return map;
	}
	
	@Override
	public ApplicationEntity getApplicationEntity(HumanDtoInterface humanDto, int targetYear, int targetMonth)
			throws MospException {
		// 年月指定時の基準日を取得
		Date targetDate = MonthUtility.getYearMonthTargetDate(targetYear, targetMonth, mospParams);
		// 設定適用エンティティを取得
		return getApplicationEntity(humanDto, targetDate);
	}
	
	@Override
	public ApplicationEntity getApplicationEntity(HumanDtoInterface humanDto, Date targetDate) throws MospException {
		// 設定適用情報を取得
		ApplicationDtoInterface applicationDto = getApplication(humanDto, targetDate);
		// 設定適用エンティティを準備
		ApplicationEntity applicationEntity = new ApplicationEntity(applicationDto);
		applicationEntity.setTargetDate(targetDate);
		// 設定適用情報が取得できない場合
		if (MospUtility.isEmpty(applicationDto)) {
			// 空の締日エンティティを取得し設定
			applicationEntity.setCutoffEntity(TimeUtility.getBareCutoffEntity(mospParams));
			// 空の設定適用エンティティを取得
			return applicationEntity;
		}
		// 勤怠設定コード取得
		String workSettingCode = applicationEntity.getWorkSettingCode();
		// 設定適用エンティティに勤怠設定情報を設定
		applicationEntity.setTimeSettingDto(getTimeSetting(workSettingCode, targetDate));
		// 締日コード取得
		String cutoffCode = applicationEntity.getCutoffCode();
		// 設定適用エンティティに締日情報を設定
		applicationEntity.setCutoffEntity(getCutoff(cutoffCode, targetDate));
		// 有給休暇設定コードを取得
		String paidHolidayCode = applicationEntity.getPaidHolidayCode();
		// 設定適用エンティティに有給休暇設定情報を設定
		applicationEntity.setPaidHolidayDto(getPaidHoliday(paidHolidayCode, targetDate));
		// 設定適用エンティティを取得
		return applicationEntity;
	}
	
	@Override
	public ApplicationEntity getApplicationEntity(String personalId, Date targetDate) throws MospException {
		// 設定適用エンティティを取得
		return getApplicationEntity(platformMaster.getHuman(personalId, targetDate), targetDate);
	}
	
	@Override
	public ApplicationEntity getApplicationEntity(String personalId, int targetYear, int targetMonth)
			throws MospException {
		// 年月指定時の基準日を取得
		Date targetDate = MonthUtility.getYearMonthTargetDate(targetYear, targetMonth, mospParams);
		// 設定適用エンティティを取得
		return getApplicationEntity(personalId, targetDate);
	}
	
	@Override
	public Map<Date, TimeSettingDtoInterface> getTimeSettingMap(Map<Date, ApplicationDtoInterface> applicationMap)
			throws MospException {
		// 勤怠設定情報群を準備
		Map<Date, TimeSettingDtoInterface> map = new HashMap<Date, TimeSettingDtoInterface>();
		// 設定適用情報毎に処理
		for (Entry<Date, ApplicationDtoInterface> entry : applicationMap.entrySet()) {
			// 対象日を取得
			Date targetDate = entry.getKey();
			// 設定適用情報を取得
			ApplicationDtoInterface applicationDto = entry.getValue();
			// 設定適用情報が取得できない場合
			if (applicationDto == null) {
				// 勤怠設定情報群にnullを設定
				map.put(targetDate, null);
				continue;
			}
			// 設定適用情報を取得し勤怠設定情報群に設定
			map.put(targetDate, getTimeSetting(applicationDto.getWorkSettingCode(), targetDate));
		}
		// 勤怠設定情報群を取得
		return map;
	}
	
	@Override
	public CutoffEntityInterface getCutoff(String cutoffCode, int targetYear, int targetMonth) throws MospException {
		// 年月指定時の基準日を取得
		Date targetDate = MonthUtility.getYearMonthTargetDate(targetYear, targetMonth, mospParams);
		// 締日情報を取得
		return getCutoff(cutoffCode, targetDate);
	}
	
	@Override
	public CutoffEntityInterface getCutoff(String cutoffCode, Date targetDate) throws MospException {
		// フィールドから締日理エンティティ群を取得
		Set<CutoffEntityInterface> cutoffs = cutoffMap.get(targetDate);
		// フィールドから締日エンティティ群を取得できなかった場合
		if (cutoffs == null) {
			// 締日エンティティ群を準備しフィールドに設定
			cutoffs = new HashSet<CutoffEntityInterface>();
			cutoffMap.put(targetDate, cutoffs);
		}
		// 締日エンティティ群からコードが合致する締日エンティティを取得
		CutoffEntityInterface cutoff = PlatformUtility.getCodeDto(cutoffs, cutoffCode);
		// 締日エンティティを取得できなかった場合
		if (MospUtility.isEmpty(cutoff)) {
			// DBから締日情報を取得し締日エンティティを作成
			cutoff = cutoffRefer.getCutoffEntity(cutoffCode, targetDate);
			// フィールドに設定
			cutoffs.add(cutoff);
		}
		// 締日管理エンティティを取得
		return cutoff;
	}
	
	@Override
	public CutoffEntityInterface getCutoffForPersonalId(String personalId, Date targetDate) throws MospException {
		// 設定適用情報を取得
		ApplicationDtoInterface dto = getApplication(personalId, targetDate);
		// 締日エンティティを取得
		return getCutoff(dto, targetDate);
	}
	
	@Override
	public CutoffEntityInterface getCutoff(ApplicationDtoInterface dto, Date targetDate) throws MospException {
		// 適用情報が取得できなかった場合
		if (MospUtility.isEmpty(dto)) {
			// 空の締日エンティティを取得
			return TimeUtility.getBareCutoffEntity(mospParams);
		}
		// 勤怠設定情報を取得
		TimeSettingDtoInterface timeSetting = getTimeSetting(dto.getWorkSettingCode(), targetDate);
		// 勤怠設定情報が取得できなかった場合
		if (MospUtility.isEmpty(timeSetting)) {
			// 空の締日エンティティを取得
			return TimeUtility.getBareCutoffEntity(mospParams);
		}
		// 締日エンティティを取得
		return getCutoff(timeSetting.getCutoffCode(), targetDate);
	}
	
	@Override
	public Set<HolidayDtoInterface> getHolidaySet(Date targetDate) throws MospException {
		// フィールドから休暇種別情報群を取得
		Set<HolidayDtoInterface> set = holidayMap.get(targetDate);
		// フィールドから休暇種別情報群を取得できなかった場合
		if (set == null) {
			// 休暇種別情報群をDBから準備しフィールドに設定
			set = holidayDao.findForActivateDate(targetDate);
			holidayMap.put(targetDate, set);
		}
		// 休暇種別情報群を取得
		return set;
	}
	
	@Override
	public HolidayDtoInterface getHoliday(String holidayCode, int holidayType, Date targetDate) throws MospException {
		// 対象日における最新の休暇種別情報を取得
		return TimeUtility.getHolidayDto(getHolidaySet(targetDate), holidayCode, holidayType);
	}
	
	@Override
	public String getHolidayName(String holidayCode, int holidayType, Date targetDate) throws MospException {
		// 対象日における最新の休暇種別情報を取得
		HolidayDtoInterface dto = getHoliday(holidayCode, holidayType, targetDate);
		// 休暇種別情報を取得できなかった場合
		if (MospUtility.isEmpty(dto)) {
			// 休暇コードを取得
			return holidayCode;
		}
		// 対象日における最新の休暇種別名称を取得
		return dto.getHolidayName();
	}
	
	@Override
	public String getHolidayAbbr(String holidayCode, int holidayType, Date targetDate) throws MospException {
		// 対象日における最新の休暇種別情報を取得
		HolidayDtoInterface dto = getHoliday(holidayCode, holidayType, targetDate);
		// 休暇種別情報を取得できなかった場合
		if (MospUtility.isEmpty(dto)) {
			// 休暇コードを取得
			return holidayCode;
		}
		// 対象日における最新の休暇種別略称を取得
		return dto.getHolidayAbbr();
	}
	
	/**
	 * 設定適用情報(個人)群(キー：対象日)を取得する。<br>
	 * <br>
	 * フィールドから設定適用情報(個人)群を取得できなかった場合は、
	 * 設定適用情報群をDBから取得しフィールドに設定した後、
	 * フィールドから設定適用情報(個人)群を再取得する。<br>
	 * <br>
	 * @param targetDate 対象日
	 * @return 設定適用情報(個人)群(キー：対象日)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected Set<ApplicationDtoInterface> getApplicationPersonSet(Date targetDate) throws MospException {
		// フィールドから設定適用情報(個人)群を取得できなかった場合
		if (applicationPersonMap.get(targetDate) == null) {
			// 設定適用情報群をDBから取得しフィールドに設定
			addApplicationSet(targetDate);
		}
		// フィールドから設定適用情報(個人)群を再取得
		return applicationPersonMap.get(targetDate);
	}
	
	/**
	 * 設定適用情報(マスタ)群(キー：対象日)を取得する。<br>
	 * <br>
	 * フィールドから設定適用情報(マスタ)群を取得できなかった場合は、
	 * 設定適用情報群をDBから取得しフィールドに設定した後、
	 * フィールドから設定適用情報(マスタ)群を再取得する。<br>
	 * <br>
	 * @param targetDate 対象日
	 * @return 設定適用情報(マスタ)群(キー：対象日)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected Set<ApplicationDtoInterface> getApplicationMasterSet(Date targetDate) throws MospException {
		// フィールドから設定適用情報(マスタ)群を取得できなかった場合
		if (applicationMasterMap.get(targetDate) == null) {
			// 設定適用情報群をDBから取得しフィールドに設定
			addApplicationSet(targetDate);
		}
		// フィールドから設定適用情報(マスタ)群を再取得
		return applicationMasterMap.get(targetDate);
	}
	
	/**
	 * 設定適用情報群をDBから取得しフィールドに設定する。<br>
	 * <br>
	 * @param targetDate 対象日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void addApplicationSet(Date targetDate) throws MospException {
		// 設定適用情報群を準備
		Set<ApplicationDtoInterface> personSet = new HashSet<ApplicationDtoInterface>();
		Set<ApplicationDtoInterface> masterSet = new HashSet<ApplicationDtoInterface>();
		// フィールドに設定
		applicationPersonMap.put(targetDate, personSet);
		applicationMasterMap.put(targetDate, masterSet);
		// 適用範囲区分(比較用)を準備
		int person = Integer.parseInt(PlatformConst.APPLICATION_TYPE_PERSON);
		int master = Integer.parseInt(PlatformConst.APPLICATION_TYPE_MASTER);
		// 設定適用情報リストをDBから取得
		List<ApplicationDtoInterface> list = applicationDao.findForActivateDate(targetDate);
		// 設定適用情報毎に処理
		for (ApplicationDtoInterface dto : list) {
			// 適用範囲区分が個人指定の場合
			if (dto.getApplicationType() == person) {
				// 設定適用情報(個人)群に追加
				personSet.add(dto);
			}
			// 適用範囲区分がマスタ指定の場合
			if (dto.getApplicationType() == master) {
				// 設定適用情報(マスタ)群に追加
				masterSet.add(dto);
			}
		}
	}
	
	/**
	 * 設定適用情報有効日群を取得する。<br>
	 * <br>
	 * 期間が設定適用情報有効日群取得範囲外である場合、
	 * 設定適用情報リストを再取得しフィールドに設定する。<br>
	 * <br>
	 * @param firstDate  期間開始日
	 * @param lastDate   期間終了日
	 * @return 設定適用情報有効日群
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected Set<Date> getApplicationDateSet(Date firstDate, Date lastDate) throws MospException {
		// 設定適用情報有効日群取得範囲外フラグ準備
		boolean isOutOfTerm = false;
		// 期間開始日がFrom日付(設定適用情報有効日群取得範囲)より前の場合
		if (applicationFromDate == null || firstDate.before(applicationFromDate)) {
			// 設定適用情報有効日群取得範囲外フラグ設定
			isOutOfTerm = true;
			// From日付(設定適用情報有効日群取得範囲)を設定
			applicationFromDate = firstDate;
		}
		// 期間終了日がTo日付(設定適用情報有効日群取得範囲)より前の場合
		if (applicationToDate == null || lastDate.after(applicationToDate)) {
			// 設定適用情報有効日群取得範囲外フラグ設定
			isOutOfTerm = true;
			// To日付(設定適用情報有効日群取得範囲)を設定
			applicationToDate = lastDate;
		}
		// 設定適用情報有効日群取得範囲外フラグ確認
		if (isOutOfTerm) {
			// 設定適用情報有効日群準備
			applicationDateSet = new HashSet<Date>();
			// 設定適用情報取得
			List<ApplicationDtoInterface> list = applicationDao.findForTerm(applicationFromDate, applicationToDate);
			// 設定適用情報リスト毎に処理
			for (ApplicationDtoInterface dto : list) {
				// 有効日リストに追加
				applicationDateSet.add(dto.getActivateDate());
			}
		}
		// 設定適用情報有効日群を取得
		return new HashSet<Date>(applicationDateSet);
	}
	
	@Override
	public TimeSettingDtoInterface getTimeSetting(String workSettingCode, Date targetDate) throws MospException {
		// フィールドから勤怠設定情報群を取得
		Set<TimeSettingDtoInterface> set = timeSettingMap.get(targetDate);
		// フィールドから勤怠設定情報群を取得できなかった場合
		if (set == null) {
			// 勤怠設定情報群を準備しフィールドに設定
			set = new HashSet<TimeSettingDtoInterface>();
			timeSettingMap.put(targetDate, set);
		}
		// 勤怠設定情報毎に処理
		for (TimeSettingDtoInterface dto : set) {
			// コードが一致する場合
			if (dto.getWorkSettingCode().equals(workSettingCode)) {
				// 勤怠設定情報を取得
				return dto;
			}
		}
		// DBから勤怠設定情報を取得(フィールドから勤怠設定情報を取得できなかった場合)
		TimeSettingDtoInterface dto = timeSettingDao.findForInfo(workSettingCode, targetDate);
		// フィールドに設定(nullであればnullを設定)
		set.add(dto);
		// 勤怠設定情報を取得
		return dto;
	}
	
	@Override
	public TimeSettingEntityInterface getTimeSetting(TimeSettingDtoInterface dto) throws MospException {
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
	
	@Override
	public TimeSettingEntityInterface getTimeSettingForPersonalId(String personalId, Date targetDate)
			throws MospException {
		// 設定適用情報を取得
		ApplicationDtoInterface application = getApplication(personalId, targetDate);
		// 設定適用情報が取得できなかった場合
		if (MospUtility.isEmpty(application)) {
			// 空の勤怠設定エンティティを取得
			return TimeUtility.getBareTimeSettingEntity(mospParams);
		}
		// 勤怠設定情報を取得
		TimeSettingDtoInterface dto = getTimeSetting(application.getWorkSettingCode(), targetDate);
		// 勤怠設定エンティティを取得
		return getTimeSetting(dto);
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
		// フィールドから限度基準情報群を取得
		Set<LimitStandardDtoInterface> dtos = limitStandardMap.get(workSettingCode);
		// フィールドから限度基準情報群を取得できなかった場合
		if (dtos == null) {
			// DBから限度基準情報群を取得
			dtos = limitStandardRefer.getLimitStandards(workSettingCode);
			// フィールドに設定
			limitStandardMap.put(workSettingCode, dtos);
		}
		// 対象DTO群からDTO群(キー：有効日、値：DTO群)を取得
		Map<Date, Set<LimitStandardDtoInterface>> map = PlatformUtility.getActivateDateMap(dtos);
		// 限度基準情報群(キー：期間)を取得
		return TimeUtility.getLimitStandards(map.get(activateDate));
	}
	
	@Override
	public PaidHolidayDtoInterface getPaidHoliday(String paidHolidayCode, Date targetDate) throws MospException {
		// フィールドから有給休暇設定情報群を取得
		Set<PaidHolidayDtoInterface> set = paidHolidayMap.get(targetDate);
		// フィールドから勤怠設定情報群を取得できなかった場合
		if (set == null) {
			// 有給休暇設定情報群を準備しフィールドに設定
			set = new HashSet<PaidHolidayDtoInterface>();
			paidHolidayMap.put(targetDate, set);
		}
		// 有給休暇設定情報毎に処理
		for (PaidHolidayDtoInterface dto : set) {
			// コードが一致する場合
			if (dto.getPaidHolidayCode().equals(paidHolidayCode)) {
				// 有給休暇設定情報を取得
				return dto;
			}
		}
		// DBから勤怠設定情報を取得(フィールドから勤怠設定情報を取得できなかった場合)
		PaidHolidayDtoInterface dto = paidHolidayDao.findForInfo(paidHolidayCode, targetDate);
		// フィールドに設定(nullであればnullを設定)
		set.add(dto);
		// 勤怠設定情報を取得
		return dto;
	}
	
	@Override
	public Optional<PaidHolidayDtoInterface> getPaidHolidayForPersonalId(String personalId, Date targetDate)
			throws MospException {
		// 有給休暇設定情報を取得
		return Optional.ofNullable(getPaidHoliday(platformMaster.getHuman(personalId, targetDate), targetDate));
	}
	
	@Override
	public int getPaidHolidayHoursPerDay(String personalId, Date targetDate) throws MospException {
		// 有休時間取得限度時間を取得
		return getPaidHolidayHoursPerDay(platformMaster.getHuman(personalId, targetDate), targetDate);
	}
	
	@Override
	public int getPaidHolidayHoursPerDay(HumanDtoInterface humanDto, Date targetDate) throws MospException {
		// 有給休暇設定情報を取得
		PaidHolidayDtoInterface dto = getPaidHoliday(humanDto, targetDate);
		// 有給休暇設定情報が取得できなかった場合
		if (dto == null) {
			// 0を取得
			return 0;
		}
		// 有休時間取得限度時間を取得
		return dto.getTimeAcquisitionLimitTimes();
	}
	
	@Override
	public WorkTypeEntityInterface getWorkTypeEntity(String workTypeCode, Date targetDate) throws MospException {
		// 勤務形態エンティティ履歴を取得
		getWorkTypeEntityHistory(workTypeCode);
		// 勤務形態エンティティを取得
		WorkTypeEntityInterface entity = TotalTimeUtility.getLatestWorkTypeEntity(workTypeMap, workTypeCode,
				targetDate);
		// 勤務形態エンティティを取得できなかった場合
		if (MospUtility.isEmpty(entity)) {
			// 空の勤務形態エンティティを取得
			entity = TimeUtility.getBareWorkTypeEntity(mospParams);
		}
		// 勤務形態エンティティを取得
		return entity;
	}
	
	@Override
	public List<WorkTypeEntityInterface> getWorkTypeEntityHistory(String workTypeCode) throws MospException {
		// 勤務形態エンティティ履歴(有効日昇順)を取得
		List<WorkTypeEntityInterface> history = workTypeMap.get(workTypeCode);
		// 勤務形態エンティティ履歴が取得できた場合
		if (history != null) {
			// 勤務形態エンティティ履歴(有効日昇順)を取得
			return history;
		}
		// 勤務形態エンティティ履歴(有効日昇順)を準備
		history = workTypeRefer.getWorkTypeEntityHistory(workTypeCode);
		// 勤務形態エンティティ群に追加
		workTypeMap.put(workTypeCode, history);
		// 勤務形態エンティティ履歴(有効日昇順)を取得
		return history;
	}
	
	@Override
	public Map<String, List<WorkTypeEntityInterface>> getWorkTypeEntities(Collection<String> workTypeCodes)
			throws MospException {
		// 勤務形態エンティティ群(キー：勤務形態コード)を準備
		Map<String, List<WorkTypeEntityInterface>> entities = new TreeMap<String, List<WorkTypeEntityInterface>>();
		// 勤務形態毎に処理
		for (String workTypeCode : workTypeCodes) {
			// 勤務形態エンティティ履歴を取得
			entities.put(workTypeCode, getWorkTypeEntityHistory(workTypeCode));
		}
		// 勤怠設定エンティティ履歴(休日及び休出)群(キー：勤務形態コード)を取得し設定
		entities.putAll(workTypeRefer.getExtraWorkTypeEntityHistories());
		// 勤務形態エンティティ群(キー：勤務形態コード)を取得
		return entities;
	}
	
	@Override
	public WorkTypeEntityInterface getWorkTypeEntity(String workTypeCode, Date targetDate,
			DifferenceRequestDtoInterface differenceRequest) throws MospException {
		// 勤務形態エンティティ(勤務日以前で最新)を取得
		WorkTypeEntityInterface workType = getWorkTypeEntity(workTypeCode, targetDate);
		// 時差出勤申請情報が存在する場合
		if (MospUtility.isEmpty(differenceRequest) == false) {
			// 時差出勤申請から勤務形態コードを取得
			String differenceWorkTypeCode = differenceRequest.getWorkTypeCode();
			// 時差出勤申請の勤務形態で勤務形態エンティティ(勤務日以前で最新)を再取得
			workType = getWorkTypeEntity(differenceWorkTypeCode, targetDate);
			// 時差出勤申請を取得し勤務形態に設定
			workType = DifferenceUtility.makeDifferenceWorkType(mospParams, differenceRequest, workType);
		}
		// 勤務形態エンティティを取得
		return workType;
	}
	
}
