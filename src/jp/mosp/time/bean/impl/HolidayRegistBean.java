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

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.base.PlatformDtoInterface;
import jp.mosp.platform.bean.workflow.WorkflowIntegrateBeanInterface;
import jp.mosp.platform.dao.human.HumanDaoInterface;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.time.bean.HolidayRegistBeanInterface;
import jp.mosp.time.constant.TimeMessageConst;
import jp.mosp.time.dao.settings.HolidayDaoInterface;
import jp.mosp.time.dao.settings.HolidayDataDaoInterface;
import jp.mosp.time.dao.settings.HolidayRequestDaoInterface;
import jp.mosp.time.dto.settings.HolidayDataDtoInterface;
import jp.mosp.time.dto.settings.HolidayDtoInterface;
import jp.mosp.time.dto.settings.HolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.impl.TmmHolidayDto;

/**
 * 休暇種別管理登録クラス。
 */
public class HolidayRegistBean extends PlatformBean implements HolidayRegistBeanInterface {
	
	/**
	 * 休暇種別管理DAOクラス。<br>
	 */
	protected HolidayDaoInterface				dao;
	
	/**
	 * 休暇申請DAOクラス。
	 */
	protected HolidayRequestDaoInterface		requestDao;
	
	/**
	 * 人事情報DAOクラス。
	 */
	protected HumanDaoInterface					humanDao;
	
	/**
	 * ワークフロー統括クラス。
	 */
	protected WorkflowIntegrateBeanInterface	workflow;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public HolidayRegistBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// DAOを準備
		dao = createDaoInstance(HolidayDaoInterface.class);
		requestDao = createDaoInstance(HolidayRequestDaoInterface.class);
		humanDao = createDaoInstance(HumanDaoInterface.class);
		// Beanを準備
		workflow = createBeanInstance(WorkflowIntegrateBeanInterface.class);
	}
	
	@Override
	public HolidayDtoInterface getInitDto() {
		return new TmmHolidayDto();
	}
	
	@Override
	public void insert(HolidayDtoInterface dto) throws MospException {
		// DTO妥当性確認
		validate(dto);
		checkRegister(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 新規登録情報の検証
		checkInsert(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setTmmHolidayId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void add(HolidayDtoInterface dto) throws MospException {
		// DTO妥当性確認
		validate(dto);
		checkRegister(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 履歴追加情報の検証
		checkAdd(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setTmmHolidayId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void update(HolidayDtoInterface dto) throws MospException {
		// DTO妥当性確認
		validate(dto);
		checkRegister(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 履歴更新情報の検証
		checkUpdate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 論理削除
		logicalDelete(dao, dto.getTmmHolidayId());
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setTmmHolidayId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void update(long[] idArray, Date activateDate, int inactivateFlag) throws MospException {
		// レコード識別ID配列の妥当性確認
		validateAryId(idArray);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 一括更新処理
		for (String[] codeType : getCodeList(idArray)) {
			// 対象休暇種別における有効日の情報を取得
			HolidayDtoInterface dto = dao.findForKey(codeType[0], activateDate, Integer.parseInt(codeType[1]));
			// 存在確認(存在しなければ履歴追加、存在すれば履歴更新)
			if (dto == null) {
				// 対象休暇種別における有効日以前で最新の情報を取得
				dto = dao.findForInfo(codeType[0], activateDate, Integer.parseInt(codeType[1]));
				// 対象休暇種別情報確認
				if (dto == null) {
					// エラーメッセージを設定
					PfMessageUtility.addErrorCodeNotExistBeforeDate(mospParams, codeType[0]);
					continue;
				}
				// DTOに有効日、無効フラグを設定
				dto.setActivateDate(activateDate);
				dto.setInactivateFlag(inactivateFlag);
				// DTO妥当性確認
				validate(dto);
				// 履歴追加情報の検証
				checkAdd(dto);
				if (mospParams.hasErrorMessage()) {
					// エラーが存在したら履歴追加処理をしない
					continue;
				}
				// レコード識別ID最大値をインクリメントしてDTOに設定
				dto.setTmmHolidayId(dao.nextRecordId());
				// 登録処理
				dao.insert(dto);
			} else {
				// DTOに無効フラグを設定
				dto.setInactivateFlag(inactivateFlag);
				// DTO妥当性確認
				validate(dto);
				// 履歴更新情報の検証
				checkUpdate(dto);
				if (mospParams.hasErrorMessage()) {
					// エラーが存在したら履歴更新処理をしない
					continue;
				}
				// 論理削除
				logicalDelete(dao, dto.getTmmHolidayId());
				// レコード識別ID最大値をインクリメントしてDTOに設定
				dto.setTmmHolidayId(dao.nextRecordId());
				// 登録処理
				dao.insert(dto);
			}
		}
	}
	
	@Override
	public void delete(long[] idArray) throws MospException {
		// レコード識別ID配列の妥当性確認
		validateAryId(idArray);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 対象雇用契約リストの中身を削除
		for (long id : idArray) {
			// 削除対象休暇種別を設定している社員がいないかを確認
			checkDelete((HolidayDtoInterface)dao.findForKey(id, true));
			if (mospParams.hasErrorMessage()) {
				// エラーが存在したら履歴削除処理をしない
				continue;
			}
			// 論理削除
			logicalDelete(dao, id);
		}
	}
	
	/**
	 * 新規登録時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkInsert(HolidayDtoInterface dto) throws MospException {
		// 対象レコードの有効日が重複していないかを確認
		checkDuplicateInsert(dao.findForHistory(dto.getHolidayCode(), dto.getHolidayType()));
	}
	
	/**
	 * 履歴追加時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkAdd(HolidayDtoInterface dto) throws MospException {
		// 対象レコードの有効日が重複していないかを確認
		checkDuplicateAdd(dao.findForKey(dto.getHolidayCode(), dto.getActivateDate(), dto.getHolidayType()));
		// 無効フラグ確認
		if (isDtoActivate(dto)) {
			return;
		}
		// 履歴追加対象コードの履歴情報を取得
		List<HolidayDtoInterface> list = dao.findForHistory(dto.getHolidayCode(), dto.getHolidayType());
		// 生じる無効期間による履歴追加確認要否を取得
		if (needCheckTermForAdd(dto, list) == false) {
			// 無効期間は発生しない
			return;
		}
		// 確認するべき休暇データリストを取得
		List<HolidayDataDtoInterface> holidayListList = getHolidayDataListForCheck(dto.getHolidayCode(),
				dto.getHolidayType(), dto, list);
		// コード使用確認
		checkCodeIsUsed(dto.getHolidayCode(), dto.getHolidayType(), dto.getActivateDate(), holidayListList);
		// 対象有効履歴最終日取得
		Date lastDate = getEffectiveLastDate(dto.getActivateDate(), list);
		// 休暇申請使用確認
		checkRequestHoliday(dto, lastDate);
	}
	
	/**
	 * 履歴更新時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkUpdate(HolidayDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getTmmHolidayId());
		// 無効フラグ確認
		if (isDtoActivate(dto)) {
			return;
		}
		// 更新元データの無効フラグ確認
		if (isDtoActivate(dao.findForKey(dto.getTmmHolidayId(), true)) == false) {
			// 更新元データが更新前から無効であれば無効期間は発生しない
			return;
		}
		// 履歴追加対象コードの履歴情報を取得
		List<HolidayDtoInterface> list = dao.findForHistory(dto.getHolidayCode(), dto.getHolidayType());
		// 確認するべき休暇データリストを取得
		List<HolidayDataDtoInterface> holidayListList = getHolidayDataListForCheck(dto.getHolidayCode(),
				dto.getHolidayType(), dto, list);
		// コード使用確認
		checkCodeIsUsed(dto.getHolidayCode(), dto.getHolidayType(), dto.getActivateDate(), holidayListList);
		// 対象有効履歴最終日取得
		Date lastDate = getEffectiveLastDate(dto.getActivateDate(), list);
		// 休暇申請使用確認
		checkRequestHoliday(dto, lastDate);
	}
	
	/**
	 * 削除時の確認処理を行う。<br>
	 * 削除対象休暇種別を設定している社員がいないかの確認を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkDelete(HolidayDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getTmmHolidayId());
		// 対象DTOの無効フラグ確認
		if (isDtoActivate(dto) == false) {
			// 削除対象が無効であれば無効期間は発生しない
			return;
		}
		// 削除対象コードの履歴情報を取得
		List<HolidayDtoInterface> list = dao.findForHistory(dto.getHolidayCode(), dto.getHolidayType());
		// 生じる無効期間による削除確認要否を取得
		if (needCheckTermForDelete(dto, list) == false) {
			// 無効期間は発生しない
			return;
		}
		// 確認するべき休暇データリストを取得
		List<HolidayDataDtoInterface> holidayListList = getHolidayDataListForCheck(dto.getHolidayCode(),
				dto.getHolidayType(), dto, list);
		// コード使用確認
		checkCodeIsUsed(dto.getHolidayCode(), dto.getHolidayType(), dto.getActivateDate(), holidayListList);
		// 対象有効履歴最終日取得
		Date lastDate = getEffectiveLastDate(dto.getActivateDate(), list);
		// 休暇申請使用確認
		checkRequestHoliday(dto, lastDate);
	}
	
	/**
	 * 休暇種別コードリストを取得する。<br>
	 * @param idArray レコード識別ID配列
	 * @return 休暇種別コードリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected List<String[]> getCodeList(long[] idArray) throws MospException {
		// リスト準備
		List<String[]> list = new ArrayList<String[]>();
		// レコード識別IDからDTOを取得し、コードをリストへ追加
		for (long id : idArray) {
			// 格納用配列
			String[] strAry = new String[2];
			// レコード識別IDから対象DTOを取得
			HolidayDtoInterface dto = (HolidayDtoInterface)dao.findForKey(id, false);
			// 対象コードと休暇種別を格納用配列へ追加
			strAry[0] = dto.getHolidayCode();
			strAry[1] = String.valueOf(dto.getHolidayType());
			// 対象コードをリストへ追加
			list.add(strAry);
		}
		return list;
	}
	
	/**
	 * 登録情報の妥当性を確認確認する。
	 * @param dto 対象DTO
	 */
	protected void validate(HolidayDtoInterface dto) {
		// 処理無し
	}
	
	/**
	 * 休暇データリスト内に対象コードが使用されている情報がないかの確認を行う。<br>
	 * @param code 対象コード
	 * @param holidayType 休暇区分
	 * @param list 休暇データリスト
	 * @param activateDate 有効日
	 * @throws MospException 警告メッセージ出力時に例外処理が発生した場合
	 */
	protected void checkCodeIsUsed(String code, int holidayType, Date activateDate, List<HolidayDataDtoInterface> list)
			throws MospException {
		// 休暇データリストの中身を確認
		for (HolidayDataDtoInterface dto : list) {
			// 対象コード確認
			if (code.equals(dto.getHolidayCode()) && holidayType == dto.getHolidayType()) {
				// メッセージ設定
				addCodeIsUsedMessage(code, activateDate, dto);
				break;
			}
		}
	}
	
	/**
	 * 削除対象休暇データが休暇申請されていないか確認する。
	 * @param dto 削除対象休暇情報
	 * @param lastDate 削除対象休暇情報履歴最終有効日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void checkRequestHoliday(HolidayDtoInterface dto, Date lastDate) throws MospException {
		// 削除対象休暇データ使用休暇申請リストを取得
		List<HolidayRequestDtoInterface> requestList = requestDao.findForRequestList(dto.getHolidayType(),
				dto.getHolidayCode(), dto.getActivateDate(), lastDate);
		for (HolidayRequestDtoInterface requestDto : requestList) {
			if (workflow.isWithDrawn(requestDto.getWorkflow())) {
				continue;
			}
			// 対象人事データ取得
			HumanDtoInterface humanDto = humanDao.findForInfo(requestDto.getPersonalId(), dto.getActivateDate());
			if (humanDto == null) {
				humanDto = humanDao.findForInfo(requestDto.getPersonalId(), DateUtility.getSystemDate());
			}
			// 警告メッセージ出力
			mospParams.addErrorMessage(TimeMessageConst.MSG_WARNING_TARGET_CODE_VACATION_GRANT_DELETE,
					dto.getHolidayCode(), humanDto.getEmployeeCode(),
					mospParams.getName("Holiday") + mospParams.getName("Application"),
					mospParams.getName("Holiday") + mospParams.getName("Application"));
		}
	}
	
	/**
	 * 該当コードが使用されていた場合の警告メッセージを追加する。
	 * {@link #mospParams}に追加する。<br>
	 * @param code コード
	 * @param dto  休暇データDTO
	 * @param activateDate 有効日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void addCodeIsUsedMessage(String code, Date activateDate, HolidayDataDtoInterface dto)
			throws MospException {
		// 人事データ取得
		HumanDtoInterface humanDto = humanDao.findForInfo(dto.getPersonalId(), activateDate);
		if (humanDto == null) {
			humanDto = humanDao.findForInfo(dto.getPersonalId(), DateUtility.getSystemDate());
		}
		// 警告メッセージ出力
		mospParams.addErrorMessage(TimeMessageConst.MSG_WARNING_TARGET_CODE_VACATION_GRANT_DELETE, code,
				humanDto.getEmployeeCode(), mospParams.getName("Giving"),
				mospParams.getName("Holiday") + mospParams.getName("Giving") + mospParams.getName("Information"));
	}
	
	/**
	 * 確認すべき休暇データリストを取得する。<br>
	 * 対象DTOの有効日～対象DTOの次の履歴の有効日に有効期限が含まれる
	 * 休暇データリストを取得する。<br>
	 * 対象コード履歴リストは、有効日の昇順で並んでいるものとする。<br>
	 * 各種マスタ操作時に生じる無効期間におけるコード使用確認等で用いられる。<br>
	 * @param code コード
	 * @param holidayType 休暇区分
	 * @param dto 	対象DTO
	 * @param list 対象コード履歴リスト
	 * @return 休暇データリスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected List<HolidayDataDtoInterface> getHolidayDataListForCheck(String code, int holidayType,
			PlatformDtoInterface dto, List<? extends PlatformDtoInterface> list) throws MospException {
		// 休暇データDAO取得
		HolidayDataDaoInterface holidayDataDao = createDaoInstance(HolidayDataDaoInterface.class);
		// 無効期間で休暇データ情報を取得(対象DTOの有効日～次の履歴の有効日)
		return holidayDataDao.findForTerm(dto.getActivateDate(), getNextActivateDate(dto.getActivateDate(), list), code,
				holidayType);
	}
	
	/**
	 * @param dto 対象DTO
	 */
	protected void checkRegister(HolidayDtoInterface dto) {
		// 標準付与日数
		checkHolidayGiving(dto);
		// 取得期限
		checkHolidayLimit(dto);
	}
	
	/**
	 * 標準付与日数の年月に0が設定されている場合
	 * @param dto 対象DTO
	 */
	protected void checkHolidayGiving(HolidayDtoInterface dto) {
		// 付与日数無制限にチェックを入れていない場合
		if (0 == dto.getNoLimit() && 0 == Double.compare(dto.getHolidayGiving(), 0.0)) {
			String mes1 = mospParams.getName("Standard", "Giving", "Days");
			String mes2 = mes1 + mospParams.getName("Is", "No0", "Period", "No5", "Over");
			mospParams.addErrorMessage(TimeMessageConst.MSG_HOLIDAY_ITEM_ZERO, mes1, mes2);
		}
	}
	
	/**
	 * 取得期限の年月に0が設定されている場合
	 * @param dto 対象DTO
	 */
	protected void checkHolidayLimit(HolidayDtoInterface dto) {
		// 付与日数無制限にチェックを入れていない場合
		if (0 == dto.getNoLimit() && 0 == dto.getHolidayLimitDay() && 0 == dto.getHolidayLimitMonth()) {
			String mes1 = mospParams.getName("Acquisition", "TimeLimit");
			String mes2 = mes1 + mospParams.getName("Is", "No1", "Day", "Over");
			mospParams.addErrorMessage(TimeMessageConst.MSG_HOLIDAY_ITEM_ZERO, mes1, mes2);
		}
	}
}
