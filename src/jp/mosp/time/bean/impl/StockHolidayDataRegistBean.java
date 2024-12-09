/*
 * MosP - Mind Open Source Project
 * Copyright (C) esMind, LLC  https://www.e-s-mind.com/
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
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.dao.human.impl.PfmHumanDao;
import jp.mosp.platform.utils.InputCheckUtility;
import jp.mosp.time.bean.StockHolidayDataRegistBeanInterface;
import jp.mosp.time.constant.TimeFileConst;
import jp.mosp.time.dao.settings.StockHolidayDataDaoInterface;
import jp.mosp.time.dao.settings.impl.TmdStockHolidayDao;
import jp.mosp.time.dto.settings.StockHolidayDataDtoInterface;
import jp.mosp.time.dto.settings.impl.TmdStockHolidayDto;
import jp.mosp.time.utils.TimeInputCheckUtility;

/**
 * ストック休暇情報登録処理。<br>
 */
public class StockHolidayDataRegistBean extends PlatformBean implements StockHolidayDataRegistBeanInterface {
	
	/**
	 * ストック休暇データDAOクラス。<br>
	 */
	protected StockHolidayDataDaoInterface dao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public StockHolidayDataRegistBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// DAO準備
		dao = createDaoInstance(StockHolidayDataDaoInterface.class);
	}
	
	@Override
	public StockHolidayDataDtoInterface getInitDto() {
		return new TmdStockHolidayDto();
	}
	
	@Override
	public void insert(StockHolidayDataDtoInterface dto) throws MospException {
		// DTO妥当性確認
		validate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		StockHolidayDataDtoInterface stockHolidayDataDto = dao.findForKey(dto.getPersonalId(), dto.getActivateDate(),
				dto.getAcquisitionDate());
		if (stockHolidayDataDto != null) {
			// DTO妥当性確認
			validate(stockHolidayDataDto);
			// 履歴更新情報の検証
			checkUpdate(stockHolidayDataDto);
			if (mospParams.hasErrorMessage()) {
				return;
			}
			// 論理削除
			logicalDelete(dao, stockHolidayDataDto.getTmdStockHolidayId());
		}
		// 新規登録情報の検証
		checkInsert(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setTmdStockHolidayId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void update(StockHolidayDataDtoInterface dto) throws MospException {
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
		logicalDelete(dao, dto.getTmdStockHolidayId());
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setTmdStockHolidayId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void regist(StockHolidayDataDtoInterface dto, Integer row) throws MospException {
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
		StockHolidayDataDtoInterface registeredDto = dao.findForKey(personalId, activateDate, acquisitionDate);
		// 同じ論理キーの情報が登録されていない場合
		if (MospUtility.isEmpty(registeredDto)) {
			// ストック休暇情報を登録
			insert(dto);
			// 処理終了
			return;
		}
		// ストック休暇情報を更新(同じ論理キーの情報が登録されている場合)
		update(dto);
	}
	
	@Override
	public void delete(StockHolidayDataDtoInterface dto) throws MospException {
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
		logicalDelete(dao, dto.getTmdStockHolidayId());
	}
	
	/**
	 * 新規登録時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkInsert(StockHolidayDataDtoInterface dto) throws MospException {
		// 対象レコードの有効日が重複していないかを確認
		checkDuplicateInsert(dao.findForKey(dto.getPersonalId(), dto.getActivateDate(), dto.getAcquisitionDate()));
	}
	
	/**
	 * 履歴更新時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkUpdate(StockHolidayDataDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getTmdStockHolidayId());
		// 無効フラグ確認
		if (isDtoActivate(dto)) {
			return;
		}
		// 更新元データの無効フラグ確認
		if (!isDtoActivate(dao.findForKey(dto.getTmdStockHolidayId(), true))) {
			// 更新元データが更新前から無効であれば無効期間は発生しない
			return;
		}
	}
	
	/**
	 * 削除時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkDelete(StockHolidayDataDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getTmdStockHolidayId());
	}
	
	/**
	 * 登録情報の妥当性を確認する。<br>
	 * @param dto 対象DTO
	 * @param row 行インデックス
	 */
	protected void validate(StockHolidayDataDtoInterface dto, Integer row) {
		// 名称取得用のコードキーを準備
		String codeKey = TimeFileConst.CODE_IMPORT_TYPE_TMD_STOCK_HOLIDAY;
		// 名称を取得
		String employeeCodeName = getCodeName(PfmHumanDao.COL_EMPLOYEE_CODE, codeKey);
		String activateDateName = getCodeName(TmdStockHolidayDao.COL_ACTIVATE_DATE, codeKey);
		String acquisitionDateName = getCodeName(TmdStockHolidayDao.COL_ACQUISITION_DATE, codeKey);
		String limitDateName = getCodeName(TmdStockHolidayDao.COL_LIMIT_DATE, codeKey);
		String holdDayName = getCodeName(TmdStockHolidayDao.COL_HOLD_DAY, codeKey);
		// 必須入力チェック
		InputCheckUtility.checkRequired(mospParams, dto.getPersonalId(), employeeCodeName);
		InputCheckUtility.checkRequired(mospParams, dto.getActivateDate(), activateDateName);
		InputCheckUtility.checkRequired(mospParams, dto.getAcquisitionDate(), acquisitionDateName);
		InputCheckUtility.checkRequired(mospParams, dto.getAcquisitionDate(), limitDateName);
		// 付与日数を確認
		InputCheckUtility.checDecimal(mospParams, dto.getHoldDay(), 2, 1, holdDayName, row);
		TimeInputCheckUtility.checHolidayTimes(mospParams, dto.getHoldDay(), holdDayName, row);
	}
	
	/**
	 * 登録情報の妥当性を確認する。<br>
	 * @param dto 対象DTO
	 */
	protected void validate(StockHolidayDataDtoInterface dto) {
		// 登録情報の妥当性を確認
		validate(dto, null);
	}
	
}
