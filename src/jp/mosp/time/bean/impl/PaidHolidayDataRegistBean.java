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
import java.util.Map;
import java.util.Set;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.dao.human.impl.PfmHumanDao;
import jp.mosp.platform.utils.InputCheckUtility;
import jp.mosp.platform.utils.PfNameUtility;
import jp.mosp.platform.utils.WorkflowUtility;
import jp.mosp.time.bean.HolidayRequestReferenceBeanInterface;
import jp.mosp.time.bean.PaidHolidayDataRegistBeanInterface;
import jp.mosp.time.bean.PaidHolidayTransactionReferenceBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.constant.TimeFileConst;
import jp.mosp.time.dao.settings.PaidHolidayDataDaoInterface;
import jp.mosp.time.dao.settings.impl.TmdPaidHolidayDao;
import jp.mosp.time.dto.settings.HolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.PaidHolidayDataDtoInterface;
import jp.mosp.time.dto.settings.PaidHolidayTransactionDtoInterface;
import jp.mosp.time.dto.settings.impl.TmdPaidHolidayDataDto;
import jp.mosp.time.utils.TimeInputCheckUtility;
import jp.mosp.time.utils.TimeMessageUtility;
import jp.mosp.time.utils.TimeNamingUtility;

/**
 * 有給休暇データ雇用契約マスタ登録クラス。
 */
public class PaidHolidayDataRegistBean extends PlatformBean implements PaidHolidayDataRegistBeanInterface {
	
	/**
	 * 有給休暇データDAOクラス。<br>
	 */
	protected PaidHolidayDataDaoInterface					dao;
	
	/**
	 * 有給休暇トランザクション参照クラス。
	 */
	protected PaidHolidayTransactionReferenceBeanInterface	paidHolidayTransactionReference;
	
	/**
	 * 休暇申請参照クラス。
	 */
	protected HolidayRequestReferenceBeanInterface			holidayRequestReference;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public PaidHolidayDataRegistBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// DAO準備
		dao = createDaoInstance(PaidHolidayDataDaoInterface.class);
		paidHolidayTransactionReference = createBeanInstance(PaidHolidayTransactionReferenceBeanInterface.class);
		holidayRequestReference = createBeanInstance(HolidayRequestReferenceBeanInterface.class);
	}
	
	@Override
	public PaidHolidayDataDtoInterface getInitDto() {
		return new TmdPaidHolidayDataDto();
	}
	
	@Override
	public void insert(PaidHolidayDataDtoInterface dto) throws MospException {
		// DTO妥当性確認
		validate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		PaidHolidayDataDtoInterface paidHolidayDataDto = dao.findForKey(dto.getPersonalId(), dto.getActivateDate(),
				dto.getAcquisitionDate());
		if (paidHolidayDataDto != null) {
			// DTO妥当性確認
			validate(paidHolidayDataDto);
			// 履歴更新情報の検証
			checkUpdate(paidHolidayDataDto);
			if (mospParams.hasErrorMessage()) {
				return;
			}
			// 論理削除
			logicalDelete(dao, paidHolidayDataDto.getTmdPaidHolidayId());
		}
		// 新規登録情報の検証
		checkInsert(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setTmdPaidHolidayId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void update(PaidHolidayDataDtoInterface dto) throws MospException {
		// DTO妥当性確認
		validate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 履歴更新情報の検証
		checkUpdate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 論理削除
		logicalDelete(dao, dto.getTmdPaidHolidayId());
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setTmdPaidHolidayId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void regist(PaidHolidayDataDtoInterface dto, Integer row) throws MospException {
		// DTOの妥当性を確認
		validate(dto, row);
		// DTOが妥当でない場合
		if (mospParams.hasErrorMessage()) {
			// 処理終了 
			return;
		}
		// 論理キーを取得
		String personalId = dto.getPersonalId();
		Date activateDate = dto.getActivateDate();
		Date acquisitionDate = dto.getAcquisitionDate();
		// 同じ論理キーの情報をDBから取得
		PaidHolidayDataDtoInterface registeredDto = dao.findForKey(personalId, activateDate, acquisitionDate);
		// 同じ論理キーの情報が登録されていない場合
		if (MospUtility.isEmpty(registeredDto)) {
			// 有給休暇情報を登録
			insert(dto);
			// 処理終了
			return;
		}
		// 有給休暇情報を更新(同じ論理キーの情報が登録されている場合)
		update(dto);
	}
	
	@Override
	public void delete(PaidHolidayDataDtoInterface dto) throws MospException {
		// DTO妥当性確認
		validate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 履歴削除情報の検証
		checkDelete(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 論理削除
		logicalDelete(dao, dto.getTmdPaidHolidayId());
	}
	
	/**
	 * 新規登録時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkInsert(PaidHolidayDataDtoInterface dto) throws MospException {
		// 対象レコードの有効日が重複していないかを確認
		checkDuplicateInsert(dao.findForKey(dto.getPersonalId(), dto.getActivateDate(), dto.getAcquisitionDate()));
	}
	
	/**
	 * 履歴更新時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	
	protected void checkUpdate(PaidHolidayDataDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getTmdPaidHolidayId());
		// 無効フラグ確認
		if (isDtoActivate(dto)) {
			return;
		}
		// 更新元データの無効フラグ確認
		if (!isDtoActivate(dao.findForKey(dto.getTmdPaidHolidayId(), true))) {
			// 更新元データが更新前から無効であれば無効期間は発生しない
			return;
		}
	}
	
	/**
	 * 削除時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkDelete(PaidHolidayDataDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getTmdPaidHolidayId());
	}
	
	@Override
	public void checkModify(PaidHolidayDataDtoInterface dto) throws MospException {
		Date endDate = dto.getLimitDate();
		List<PaidHolidayDataDtoInterface> list = dao.findForHistory(dto.getPersonalId(), dto.getAcquisitionDate());
		for (int i = 0; i < list.size(); i++) {
			PaidHolidayDataDtoInterface paidHolidayDataDto = list.get(i);
			if (!paidHolidayDataDto.getActivateDate().equals(dto.getActivateDate())) {
				// 有効日が異なる場合
				continue;
			}
			if (list.size() > i + 1) {
				endDate = addDay(list.get(i + 1).getActivateDate(), -1);
			}
			break;
		}
		if (dto.getActivateDate().after(endDate)) {
			// 有効日が終了日より後の場合は有効日を終了日とする
			endDate = dto.getActivateDate();
		}
		double givingDay = 0;
		int givingHour = 0;
		double cancelDay = 0;
		int cancelHour = 0;
		List<PaidHolidayTransactionDtoInterface> paidHolidayTransactionList = paidHolidayTransactionReference
			.findForList(dto.getPersonalId(), dto.getAcquisitionDate(), dto.getActivateDate(), endDate);
		for (PaidHolidayTransactionDtoInterface paidHolidayTransactionDto : paidHolidayTransactionList) {
			givingDay += paidHolidayTransactionDto.getGivingDay();
			givingHour += paidHolidayTransactionDto.getGivingHour();
			cancelDay += paidHolidayTransactionDto.getCancelDay();
			cancelHour += paidHolidayTransactionDto.getCancelHour();
		}
		Map<String, Object> map = holidayRequestReference.getRequestDayHour(dto.getPersonalId(),
				dto.getAcquisitionDate(), TimeConst.CODE_HOLIDAYTYPE_HOLIDAY,
				Integer.toString(TimeConst.CODE_HOLIDAYTYPE_HOLIDAY), dto.getActivateDate(), endDate);
		double useDay = ((Double)map.get(TimeConst.CODE_REQUEST_DAY)).doubleValue();
		int useHour = ((Integer)map.get(TimeConst.CODE_REQUEST_HOUR)).intValue();
		double day = dto.getHoldDay() + givingDay - cancelDay - useDay;
		int hour = dto.getHoldHour() + givingHour - cancelHour - useHour;
		if (dto.getDenominatorDayHour() > 0) {
			// 0より大きい場合
			while (day >= 1 && hour < 0) {
				day--;
				hour += dto.getDenominatorDayHour();
			}
		}
		if (day >= 0 && hour >= 0) {
			return;
		}
		// エラーメッセージを設定(休暇残数がマイナスになる場合)
		TimeMessageUtility.addErrorDoAfterRocess(mospParams, TimeNamingUtility.holidayRequest(mospParams),
				PfNameUtility.insert(mospParams));
	}
	
	/**
	 * 登録情報の妥当性を確認する。<br>
	 * @param dto 対象DTO
	 * @param row 行インデックス
	 */
	protected void validate(PaidHolidayDataDtoInterface dto, Integer row) {
		// 名称取得用のコードキーを準備
		String codeKey = TimeFileConst.CODE_IMPORT_TYPE_TMD_PAID_HOLIDAY;
		// 名称を取得
		String employeeCodeName = getCodeName(PfmHumanDao.COL_EMPLOYEE_CODE, codeKey);
		String activateDateName = getCodeName(TmdPaidHolidayDao.COL_ACTIVATE_DATE, codeKey);
		String acquisitionDateName = getCodeName(TmdPaidHolidayDao.COL_ACQUISITION_DATE, codeKey);
		String limitDateName = getCodeName(TmdPaidHolidayDao.COL_LIMIT_DATE, codeKey);
		String holdDayName = getCodeName(TmdPaidHolidayDao.COL_HOLD_DAY, codeKey);
		String holdHourName = getCodeName(TmdPaidHolidayDao.COL_HOLD_HOUR, codeKey);
		// 必須入力チェック
		InputCheckUtility.checkRequired(mospParams, dto.getPersonalId(), employeeCodeName);
		InputCheckUtility.checkRequired(mospParams, dto.getActivateDate(), activateDateName);
		InputCheckUtility.checkRequired(mospParams, dto.getAcquisitionDate(), acquisitionDateName);
		InputCheckUtility.checkRequired(mospParams, dto.getAcquisitionDate(), limitDateName);
		// 付与日数を確認
		InputCheckUtility.checDecimal(mospParams, dto.getHoldDay(), 2, 1, holdDayName, row);
		TimeInputCheckUtility.checHolidayTimes(mospParams, dto.getHoldDay(), holdDayName, row);
		// 付与時間数を確認
		InputCheckUtility.checkMaxDigit(mospParams, dto.getHoldHour(), 2, holdHourName, row);
	}
	
	/**
	 * 登録情報の妥当性を確認する。<br>
	 * @param dto 対象DTO
	 */
	protected void validate(PaidHolidayDataDtoInterface dto) {
		// 登録情報の妥当性を確認
		validate(dto, null);
	}
	
	@Override
	public void checkDeleteConfirm(PaidHolidayDataDtoInterface dto) throws MospException {
		// 個人ID・有給付与日取得
		String personalId = dto.getPersonalId();
		Date acquisitionDate = dto.getAcquisitionDate();
		// 削除確認フラグ
		boolean isDelete = isDleteConfirm(dto);
		// 承認状況群(申請済)を準備
		Set<String> statuses = WorkflowUtility.getAppliedStatuses();
		// 付与日で休暇申請群(有給休暇)を取得
		Set<HolidayRequestDtoInterface> requests = holidayRequestReference.getRequestsForAcquisitionDate(personalId,
				TimeConst.CODE_HOLIDAYTYPE_HOLIDAY, TimeConst.CODE_HOLIDAYTYPE2_PAID, acquisitionDate, statuses);
		// 利用休暇申請一覧毎に処理
		for (HolidayRequestDtoInterface holidayDto : requests) {
			// 削除できない場合
			if (!isDelete) {
				// メッセージ追加
				TimeMessageUtility.addErrorNoDeleteForHolidayRequest(mospParams, acquisitionDate,
						holidayDto.getRequestStartDate());
			}
		}
		// 利用有給休暇手動付与リスト取得
		List<PaidHolidayTransactionDtoInterface> transacList = paidHolidayTransactionReference
			.findForAcquisitionList(personalId, acquisitionDate);
		// 利用有給休暇手動付与リスト毎に処理
		for (PaidHolidayTransactionDtoInterface transacDto : transacList) {
			// 削除できない場合
			if (!isDelete) {
				// メッセージ追加
				TimeMessageUtility.addErrorNoDeleteForPaidHolidayTransaction(mospParams, acquisitionDate,
						transacDto.getActivateDate());
			}
		}
	}
	
	/**
	 * 同じ付与日情報がある場合、削除の妥当性確認を行う。<br>
	 * @param dto 有給休暇情報
	 * @return 確認結果(true：削除できる、false：削除できない)
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected boolean isDleteConfirm(PaidHolidayDataDtoInterface dto) throws MospException {
		// 付与日リスト取得
		List<PaidHolidayDataDtoInterface> list = dao.findForHistory(dto.getPersonalId(), dto.getAcquisitionDate());
		// 同じ有給データが一つでなく有効日と付与日が同じでない場合
		if (list.size() != 1 && dto.getActivateDate().compareTo(dto.getAcquisitionDate()) != 0) {
			return true;
		}
		return false;
	}
}
