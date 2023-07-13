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

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.time.bean.PaidHolidayRemainBeanInterface;
import jp.mosp.time.bean.StockHolidayDataGrantBeanInterface;
import jp.mosp.time.bean.StockHolidayDataReferenceBeanInterface;
import jp.mosp.time.bean.StockHolidayDataRegistBeanInterface;
import jp.mosp.time.bean.StockHolidayReferenceBeanInterface;
import jp.mosp.time.bean.TimeMasterBeanInterface;
import jp.mosp.time.dto.settings.PaidHolidayDtoInterface;
import jp.mosp.time.dto.settings.StockHolidayDataDtoInterface;
import jp.mosp.time.dto.settings.StockHolidayDtoInterface;
import jp.mosp.time.dto.settings.impl.HolidayRemainDto;

/**
 * ストック休暇付与処理。<br>
 */
public class StockHolidayDataGrantBean extends PlatformBean implements StockHolidayDataGrantBeanInterface {
	
	/**
	 * ストック休暇設定参照クラス。
	 */
	protected StockHolidayReferenceBeanInterface		stockHolidayRefer;
	
	/**
	 * ストック休暇データ参照クラス。
	 */
	protected StockHolidayDataReferenceBeanInterface	stockHolidayDataRefer;
	
	/**
	 * ストック休暇データ登録クラス。
	 */
	protected StockHolidayDataRegistBeanInterface		stockHolidayDataRegist;
	
	/**
	 * 有給休暇残数取得処理。<br>
	 */
	protected PaidHolidayRemainBeanInterface			paidHolidayRemain;
	
	/**
	 * 勤怠関連マスタ参照処理。<br>
	 */
	protected TimeMasterBeanInterface					timeMaster;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public StockHolidayDataGrantBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// Beanを準備
		stockHolidayRefer = createBeanInstance(StockHolidayReferenceBeanInterface.class);
		stockHolidayDataRefer = createBeanInstance(StockHolidayDataReferenceBeanInterface.class);
		stockHolidayDataRegist = createBeanInstance(StockHolidayDataRegistBeanInterface.class);
		paidHolidayRemain = createBeanInstance(PaidHolidayRemainBeanInterface.class);
		// 勤怠関連マスタ参照処理
		timeMaster = createBeanInstance(TimeMasterBeanInterface.class);
		// 休暇残数取得処理に勤怠関連マスタ参照処理を設定
		paidHolidayRemain.setTimeMaster(timeMaster);
	}
	
	@Override
	public void grant(String personalId, Date targetDate) throws MospException {
		grant(personalId, targetDate, true);
	}
	
	@Override
	public void grant(String personalId, Date targetDate, boolean isUpdate) throws MospException {
		grant(create(personalId, targetDate, isUpdate));
	}
	
	/**
	 * ストック休暇データ付与を行う。
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void grant(StockHolidayDataDtoInterface dto) throws MospException {
		if (dto == null) {
			return;
		}
		StockHolidayDataDtoInterface stockHolidayDataDto = stockHolidayDataRefer.findForKey(dto.getPersonalId(),
				dto.getActivateDate(), dto.getAcquisitionDate());
		if (stockHolidayDataDto != null) {
			// 削除
			stockHolidayDataRegist.delete(stockHolidayDataDto);
		}
		// 新規登録
		stockHolidayDataRegist.insert(dto);
	}
	
	/**
	 * ストック休暇データを生成する。
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @param isUpdate 更新する場合true、しない場合false
	 * @return ストック休暇データ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected StockHolidayDataDtoInterface create(String personalId, Date targetDate, boolean isUpdate)
			throws MospException {
		// 有給休暇設定情報を取得
		PaidHolidayDtoInterface paidHolidayDto = timeMaster.getPaidHolidayForPersonalId(personalId, targetDate)
			.orElse(null);
		// 有給休暇情報を取得できなかった場合
		if (MospUtility.isEmpty(paidHolidayDto)) {
			// 処理終了
			return null;
		}
		// ストック休暇管理情報を取得
		StockHolidayDtoInterface stockHolidayDto = stockHolidayRefer.findForKey(paidHolidayDto.getPaidHolidayCode(),
				paidHolidayDto.getActivateDate());
		// ストック休暇管理情報を取得できなかった場合
		// 有給休暇情報を取得できなかった場合
		if (MospUtility.isEmpty(stockHolidayDto)) {
			// 処理終了
			return null;
		}
		// ストック休暇付与処理用に有給休暇残情報を取得
		HolidayRemainDto remain = paidHolidayRemain.getPaidHolidayRemainForStock(personalId, targetDate).orElse(null);
		// ストック休暇付与処理用に有給休暇残情報を取得できなかった場合
		if (MospUtility.isEmpty(remain)) {
			// 処理終了
			return null;
		}
		// ストック休暇の付与日を取得
		Date grantDate = getGrantDate(remain.getHolidayLimitDate());
		// 既にストック休暇情報が存在し更新しない場合
		if (isUpdate == false && stockHolidayDataRefer.findForKey(personalId, grantDate, grantDate) != null) {
			// 処理終了
			return null;
		}
		// ストック休暇情報を作成
		StockHolidayDataDtoInterface dto = stockHolidayDataRegist.getInitDto();
		dto.setPersonalId(personalId);
		dto.setAcquisitionDate(grantDate);
		dto.setActivateDate(grantDate);
		dto.setLimitDate(getExpirationDate(stockHolidayDto, grantDate));
		dto.setHoldDay(getGrantDays(stockHolidayDto, personalId, remain));
		dto.setGivingDay(0);
		dto.setCancelDay(0);
		dto.setUseDay(0);
		// ストック休暇情報を取得
		return dto;
	}
	
	/**
	 * 付与日付を取得する。
	 * @param paidHolidayExpirationDate 有給休暇期限日付
	 * @return 付与日付
	 */
	protected Date getGrantDate(Date paidHolidayExpirationDate) {
		return addDay(paidHolidayExpirationDate, 1);
	}
	
	/**
	 * 期限日付を取得する。
	 * @param dto 対象DTO
	 * @param grantDate 付与日付
	 * @return 期限日付
	 */
	protected Date getExpirationDate(StockHolidayDtoInterface dto, Date grantDate) {
		return addDay(DateUtility.addMonth(grantDate, dto.getStockLimitDate()), -1);
	}
	
	/**
	 * 付与日数を取得する。
	 * @param dto        ストック休暇情報
	 * @param personalId 個人ID
	 * @param remain     有給休暇残情報
	 * @return 付与日数
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected double getGrantDays(StockHolidayDtoInterface dto, String personalId, HolidayRemainDto remain)
			throws MospException {
		// 有給休暇期限日を取得
		Date paidHolidayLimitDate = remain.getHolidayLimitDate();
		// 期限日でストック休暇残日数を取得
		double remainDays = paidHolidayRemain.getStockHolidayRemainDaysForList(personalId, paidHolidayLimitDate);
		// 付与日数を準備
		double grantDays = remain.getRemainDays();
		// 付与日数が最大年間積立日数を超える場合
		if (grantDays > dto.getStockYearAmount()) {
			// 付与日数に最大年間積立日数を設定
			grantDays = dto.getStockYearAmount();
		}
		// 前年度分までの合計積立日数が最大合計積立日数以上の場合
		if (remainDays >= dto.getStockTotalAmount()) {
			// 0を取得
			return 0;
		}
		// 前年度分までの合計積立日数 + 付与日数が最大合計積立日数を超える場合
		if (remainDays + grantDays > dto.getStockTotalAmount()) {
			// 最大合計積立日数 - 前年度分までの合計積立日数を取得
			return dto.getStockTotalAmount() - remainDays;
		}
		// 付与日数を取得
		return grantDays;
	}
	
}
