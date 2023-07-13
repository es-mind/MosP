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
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.platform.bean.human.HumanSearchBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.utils.MonthUtility;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.time.base.TimeBean;
import jp.mosp.time.bean.CutoffReferenceBeanInterface;
import jp.mosp.time.bean.CutoffUtilBeanInterface;
import jp.mosp.time.bean.TimeMasterBeanInterface;
import jp.mosp.time.bean.TotalTimeEmployeeTransactionReferenceBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.entity.ApplicationEntity;
import jp.mosp.time.entity.CutoffEntityInterface;

/**
 * 締日ユーティリティクラス。<br>
 *
 */
public class CutoffUtilBean extends TimeBean implements CutoffUtilBeanInterface {
	
	/**
	 * 締日情報参照処理。<br>
	 */
	protected CutoffReferenceBeanInterface							cutoffRefer;
	
	/**
	 * 人事情報検索クラス。<br>
	 */
	protected HumanSearchBeanInterface								humanSearch;
	
	/**
	 * 社員勤怠集計管理参照クラス。<br>
	 */
	protected TotalTimeEmployeeTransactionReferenceBeanInterface	totalTimeEmployeeRefer;
	
	/**
	 * 勤怠関連マスタ参照処理。<br>
	 */
	protected TimeMasterBeanInterface								timeMaster;
	
	
	/**
	 * {@link TimeBean#TimeBean()}を実行する。<br>
	 */
	public CutoffUtilBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// 各種クラスの取得
		cutoffRefer = createBeanInstance(CutoffReferenceBeanInterface.class);
		humanSearch = createBeanInstance(HumanSearchBeanInterface.class);
		totalTimeEmployeeRefer = createBeanInstance(TotalTimeEmployeeTransactionReferenceBeanInterface.class);
		// 勤怠関連マスタ参照処理を設定
		setTimeMaster(createBeanInstance(TimeMasterBeanInterface.class));
	}
	
	@Override
	public Set<String> getCutoffPersonalIdSet(String cutoffCode, int targetYear, int targetMonth) throws MospException {
		// 個人IDセットの準備
		Set<String> idSet = new LinkedHashSet<String>();
		// 年月を指定して締日エンティティを取得
		CutoffEntityInterface cutoff = getCutoff(cutoffCode, targetYear, targetMonth);
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return idSet;
		}
		// 対象年月における締期間の基準日及び初日と最終日を取得
		Date cutoffTermTargetDate = cutoff.getCutoffTermTargetDate(targetYear, targetMonth, mospParams);
		Date firstDate = cutoff.getCutoffFirstDate(targetYear, targetMonth, mospParams);
		Date lastDate = cutoff.getCutoffLastDate(targetYear, targetMonth, mospParams);
		// 有効な個人IDセット取得(対象日は締期間の基準日)
		Map<String, HumanDtoInterface> activateMap = getActivateHumans(cutoffTermTargetDate, firstDate, lastDate);
		// 人事情報毎に処理
		for (HumanDtoInterface humanDto : activateMap.values()) {
			// 設定適用エンティティを取得
			ApplicationEntity entity = timeMaster.getApplicationEntity(humanDto, cutoffTermTargetDate);
			// 締日が同じ場合
			if (entity.getCutoffCode().equals(cutoffCode)) {
				// 有効な個人IDセットに
				idSet.add(humanDto.getPersonalId());
			}
		}
		// 個人IDセット確認
		if (idSet.isEmpty()) {
			// 登録失敗メッセージを設定
			PfMessageUtility.addErrorEmployeeNotExist(mospParams);
		}
		return idSet;
	}
	
	/**
	 * 締日コードと年月を指定して締日エンティティを取得する。<br>
	 * @param cutoffCode  締日コード
	 * @param targetYear  対象年
	 * @param targetMonth 対象月
	 * @return 締日エンティティ
	 * @throws MospException 締日情報の取得に失敗した場合
	 */
	protected CutoffEntityInterface getCutoff(String cutoffCode, int targetYear, int targetMonth) throws MospException {
		// 年月指定時の基準日を取得
		Date targetDate = MonthUtility.getYearMonthTargetDate(targetYear, targetMonth, mospParams);
		// 締日コードと対象日から締日情報を取得
		return getCutoff(cutoffCode, targetDate);
	}
	
	/**
	 * 締日コードと対象日から締日情報を取得する。<br>
	 * @param cutoffCode 締日コード
	 * @param targetDate 対象日
	 * @return 締日情報
	 * @throws MospException 締日情報の取得に失敗した場合
	 */
	protected CutoffEntityInterface getCutoff(String cutoffCode, Date targetDate) throws MospException {
		// 締日コードと対象日から締日エンティティを取得
		CutoffEntityInterface cutoff = timeMaster.getCutoff(cutoffCode, targetDate);
		// 締日情報確認
		cutoffRefer.chkExistCutoff(cutoff.getCutoffDto(), targetDate);
		// 締日エンティティを取得
		return cutoff;
	}
	
	@Override
	public boolean checkTighten(String personalId, Date targetDate, String targetName) throws MospException {
		// 対象個人ID及び対象日付から対象年月日が含まれる締月を取得
		Date cutoffDate = getCutoffMonth(personalId, targetDate);
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return false;
		}
		// 年月取得
		int year = DateUtility.getYear(cutoffDate);
		int month = DateUtility.getMonth(cutoffDate);
		// 締状態確認
		boolean isNotTighten = isNotTighten(personalId, year, month);
		// 未締でない場合
		if (isNotTighten == false) {
			// メッセージ追加
			addMonthlyTreatmentErrorMessage(year, month, targetName);
		}
		return isNotTighten;
	}
	
	@Override
	public boolean isNotTighten(String personalId, Date targetDate) throws MospException {
		// 対象個人ID及び対象日付で設定適用エンティティを取得
		ApplicationEntity application = timeMaster.getApplicationEntity(personalId, targetDate);
		// 設定適用エンティティが有効でない場合
		if (application.isValid() == false) {
			return false;
		}
		// 対象日付及び締日から対象年月日が含まれる締月を取得
		Date cutoffMonth = application.getCutoffEntity().getCutoffMonth(targetDate, mospParams);
		// 締状態確認
		return isNotTighten(personalId, DateUtility.getYear(cutoffMonth), DateUtility.getMonth(cutoffMonth));
	}
	
	@Override
	public boolean isNotTighten(String personalId, int targetYear, int targetMonth) throws MospException {
		// 社員勤怠集計管理から締状態を取得
		Integer state = totalTimeEmployeeRefer.getCutoffState(personalId, targetYear, targetMonth);
		// 締状態確認
		if (state == null || state == TimeConst.CODE_CUTOFF_STATE_NOT_TIGHT) {
			return true;
		}
		// 未締でない場合
		return false;
	}
	
	@Override
	public Date getCutoffMonth(String personalId, Date targetDate) throws MospException {
		// 締日エンティティを取得
		CutoffEntityInterface cutoff = timeMaster.getCutoffForPersonalId(personalId, targetDate);
		// 締日情報が存在しない場合
		if (cutoff.isExist() == false) {
			// 日付指定時の基準年月を取得
			return MonthUtility.getTargetYearMonth(targetDate, mospParams);
		}
		// 対象日付が含まれる締月を取得
		return cutoff.getCutoffMonth(targetDate, mospParams);
	}
	
	/**
	 * 有効な個人IDセットを取得する。<br>
	 * @param targetDate 対象日付
	 * @param staDate 期間開始日
	 * @param endDate 期間終了日
	 * @return 有効な個人IDセット
	 * @throws MospException 人事情報の取得に失敗した場合
	 */
	protected Map<String, HumanDtoInterface> getActivateHumans(Date targetDate, Date staDate, Date endDate)
			throws MospException {
		// 条件の設定
		humanSearch.setTargetDate(targetDate);
		humanSearch.setStartDate(staDate);
		humanSearch.setEndDate(endDate);
		humanSearch.setStateType(PlatformConst.EMPLOYEE_STATE_PRESENCE);
		// 個人IDセット取得
		return humanSearch.getHumanDtoMap();
	}
	
	@Override
	public void setTimeMaster(TimeMasterBeanInterface timeMaster) {
		this.timeMaster = timeMaster;
	}
	
}
