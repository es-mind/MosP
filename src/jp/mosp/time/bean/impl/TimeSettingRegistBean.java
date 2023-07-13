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
/**
 * 
 */
package jp.mosp.time.bean.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.base.PlatformDtoInterface;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.time.bean.LimitStandardRegistBeanInterface;
import jp.mosp.time.bean.TimeSettingRegistBeanInterface;
import jp.mosp.time.constant.TimeMessageConst;
import jp.mosp.time.dao.settings.ApplicationDaoInterface;
import jp.mosp.time.dao.settings.TimeSettingDaoInterface;
import jp.mosp.time.dto.settings.ApplicationDtoInterface;
import jp.mosp.time.dto.settings.TimeSettingDtoInterface;
import jp.mosp.time.dto.settings.impl.TmmTimeSettingDto;

/**
 * 勤怠設定登録処理。<br>
 */
public class TimeSettingRegistBean extends PlatformBean implements TimeSettingRegistBeanInterface {
	
	/**
	 * 勤怠設定管理DAO。<br>
	 */
	protected TimeSettingDaoInterface			dao;
	
	/**
	 * 限度基準登録処理。<br>
	 */
	protected LimitStandardRegistBeanInterface	limitStandardRegist;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public TimeSettingRegistBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// DAOを準備
		dao = createDaoInstance(TimeSettingDaoInterface.class);
		// Beanを準備
		limitStandardRegist = createBeanInstance(LimitStandardRegistBeanInterface.class);
	}
	
	@Override
	public TimeSettingDtoInterface getInitDto() {
		return new TmmTimeSettingDto();
	}
	
	@Override
	public void insert(TimeSettingDtoInterface dto) throws MospException {
		// DTOの妥当性確認
		validate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 新規登録情報の検証
		checkInsert(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setTmmTimeSettingId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void add(TimeSettingDtoInterface dto) throws MospException {
		// DTOの妥当性確認
		validate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 履歴追加情報の検証
		checkAdd(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setTmmTimeSettingId(dao.nextRecordId());
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
		for (String code : getCodeList(idArray)) {
			// 対象勤怠設定情報における有効日の情報を取得
			TimeSettingDtoInterface dto = dao.findForKey(code, activateDate);
			// 存在確認(存在しなければ履歴追加、存在すれば履歴更新)
			if (dto == null) {
				// 対象勤怠設定情報における有効日以前で最新の情報を取得
				dto = dao.findForInfo(code, activateDate);
				// 対象勤怠設定情報確認
				if (dto == null) {
					// エラーメッセージを設定
					PfMessageUtility.addErrorCodeNotExistBeforeDate(mospParams, code);
					continue;
				}
				// コピー元勤怠設定情報の有効日を取得
				Date copiedActivateDate = dto.getActivateDate();
				// DTOに有効日、無効フラグを設定
				dto.setActivateDate(activateDate);
				dto.setInactivateFlag(inactivateFlag);
				// 履歴追加情報の検証
				checkAdd(dto);
				if (mospParams.hasErrorMessage()) {
					// エラーが存在したら履歴追加処理をしない
					continue;
				}
				// レコード識別ID最大値をインクリメントしてDTOに設定
				dto.setTmmTimeSettingId(dao.nextRecordId());
				// 登録処理
				dao.insert(dto);
				// 限度基準情報をコピー
				limitStandardRegist.copy(dto.getWorkSettingCode(), activateDate, copiedActivateDate);
			} else {
				// DTOに無効フラグを設定
				dto.setInactivateFlag(inactivateFlag);
				// 履歴更新情報の検証
				checkUpdate(dto);
				if (mospParams.hasErrorMessage()) {
					// エラーが存在したら履歴更新処理をしない
					continue;
				}
				// 論理削除
				logicalDelete(dao, dto.getTmmTimeSettingId());
				// レコード識別ID最大値をインクリメントしてDTOに設定
				dto.setTmmTimeSettingId(dao.nextRecordId());
				// 登録処理
				dao.insert(dto);
			}
		}
	}
	
	@Override
	public void update(TimeSettingDtoInterface dto) throws MospException {
		// DTOの妥当性確認
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
		logicalDelete(dao, dto.getTmmTimeSettingId());
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setTmmTimeSettingId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void delete(long recordId) throws MospException {
		// 削除対象勤怠設定情報を取得
		TimeSettingDtoInterface dto = (TimeSettingDtoInterface)dao.findForKey(recordId, true);
		// 削除対象勤怠設定情報が使用されていないかを確認
		checkDelete(dto);
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 論理削除
		logicalDelete(dao, recordId);
	}
	
	/**
	 * 新規登録時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkInsert(TimeSettingDtoInterface dto) throws MospException {
		// 対象レコードの有効日が重複していないかを確認
		checkDuplicateInsert(dao.findForHistory(dto.getWorkSettingCode()));
	}
	
	/**
	 * 履歴追加時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkAdd(TimeSettingDtoInterface dto) throws MospException {
		// 対象レコードの有効日が重複していないかを確認
		checkDuplicateAdd(dao.findForKey(dto.getWorkSettingCode(), dto.getActivateDate()));
		// 無効フラグ確認
		if (isDtoActivate(dto)) {
			return;
		}
		// 履歴追加対象コードの履歴情報を取得
		List<TimeSettingDtoInterface> list = dao.findForHistory(dto.getWorkSettingCode());
		// 生じる無効期間による履歴追加確認要否を取得
		if (needCheckTermForAdd(dto, list) == false) {
			// 無効期間は発生しない
			return;
		}
		// 確認するべき設定適用マスタリストを取得
		List<ApplicationDtoInterface> appList = getApplicationListForCheck(dto, list);
		// コード使用確認
		checkCodeIsUsed(dto.getWorkSettingCode(), appList);
	}
	
	/**
	 * 履歴更新時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkUpdate(TimeSettingDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getTmmTimeSettingId());
		// 無効フラグ確認
		if (isDtoActivate(dto)) {
			return;
		}
		// 更新元データの無効フラグ確認
		if (isDtoActivate(dao.findForKey(dto.getTmmTimeSettingId(), true)) == false) {
			// 更新元データが更新前から無効であれば無効期間は発生しない
			return;
		}
		// 履歴追加対象コードの履歴情報を取得
		List<TimeSettingDtoInterface> list = dao.findForHistory(dto.getWorkSettingCode());
		// 確認するべき設定適用マスタリストを取得
		List<ApplicationDtoInterface> appList = getApplicationListForCheck(dto, list);
		// コード使用確認
		checkCodeIsUsed(dto.getWorkSettingCode(), appList);
	}
	
	/**
	 * 削除時の確認処理を行う。<br>
	 * 削除対象勤怠設定情報を設定している設定適用管理情報がないかの確認を行う。<br>
	 * @param dto 勤怠設定情報
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkDelete(TimeSettingDtoInterface dto) throws MospException {
		// 削除対象勤怠設定情報が削除されていないかを確認
		checkExclusive(dto);
		// 削除対象勤怠設定情報が無効である場合
		if (isDtoActivate(dto) == false) {
			// 無効期間は発生しないため処理終了
			return;
		}
		// 削除対象コードの履歴情報を取得
		List<TimeSettingDtoInterface> list = dao.findForHistory(dto.getWorkSettingCode());
		// 生じる無効期間による削除確認要否を取得
		if (needCheckTermForDelete(dto, list) == false) {
			// 無効期間は発生しない
			return;
		}
		// 確認するべき設定適用マスタリストを取得
		List<ApplicationDtoInterface> appList = getApplicationListForCheck(dto, list);
		// コード使用確認
		checkCodeIsUsed(dto.getWorkSettingCode(), appList);
	}
	
	/**
	 * 勤怠設定情報リストを取得する。<br>
	 * 同時に排他確認を行う。<br>
	 * @param idArray レコード識別ID配列
	 * @return 勤怠設定情報リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected List<String> getCodeList(long[] idArray) throws MospException {
		// リスト準備
		List<String> list = new ArrayList<String>();
		// レコード識別IDからDTOを取得し、コードをリストへ追加
		for (long id : idArray) {
			// レコード識別IDから対象DTOを取得
			TimeSettingDtoInterface dto = (TimeSettingDtoInterface)dao.findForKey(id, false);
			// 排他確認
			checkExclusive(dto);
			// 対象コードをリストへ追加
			list.add(dto.getWorkSettingCode());
		}
		return list;
	}
	
	/**
	 * 設定適用マスタリスト内に対象コードが使用されている情報がないかの確認を行う。<br>
	 * @param code 対象コード
	 * @param list 設定適用マスタリスト
	 */
	protected void checkCodeIsUsed(String code, List<ApplicationDtoInterface> list) {
		// 同一の設定適用コードのメッセージは出力しない。
		String codeAdded = "";
		// 設定適用マスタリストの中身を確認
		for (ApplicationDtoInterface dto : list) {
			// 対象コード確認
			if ((code.equals(dto.getWorkSettingCode())) && (isDtoActivate(dto))) {
				// 同一の設定適用コードのメッセージは出力しない。
				if (!codeAdded.equals(dto.getApplicationCode())) {
					// メッセージ設定
					addCodeIsUsedMessage(code, dto);
					// メッセージに設定した設定適用コードを保持
					codeAdded = dto.getApplicationCode();
				}
			}
		}
	}
	
	/**
	 * 登録情報の妥当性を確認する。
	 * @param dto 対象DTO
	 */
	protected void validate(TimeSettingDtoInterface dto) {
		// 妥当性確認
	}
	
	/**
	 * 該当コードが使用されていた場合の警告メッセージを追加する。
	 * {@link #mospParams}に追加する。<br>
	 * @param code コード
	 * @param dto  設定適用マスタDTO
	 */
	protected void addCodeIsUsedMessage(String code, ApplicationDtoInterface dto) {
		String columnName = mospParams.getName("WorkManage") + mospParams.getName("Set");
		String[] aryRep = { code, dto.getApplicationCode(), columnName };
		mospParams.addErrorMessage(TimeMessageConst.MSG_SELECTED_CODE_IS_USED, aryRep);
	}
	
	/**
	 * 確認すべき設定適用マスタリストを取得する。<br>
	 * 対象DTOの有効日以前で最新の設定適用マスタリストと
	 * 対象DTOの有効日～対象DTOの次の履歴の有効日に有効日が含まれる
	 * 設定適用マスタリストを合わせたリストを取得する。<br>
	 * 対象コード履歴リストは、有効日の昇順で並んでいるものとする。<br>
	 * 各種マスタ操作時に生じる無効期間におけるコード使用確認等で用いられる。<br>
	 * @param dto 	対象DTO
	 * @param list 対象コード履歴リスト
	 * @return 設定適用マスタリスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected List<ApplicationDtoInterface> getApplicationListForCheck(PlatformDtoInterface dto,
			List<? extends PlatformDtoInterface> list) throws MospException {
		// 設定適用マスタDAO取得
		ApplicationDaoInterface appDao = createDaoInstance(ApplicationDaoInterface.class);
		// 削除対象の有効日以前で最新の設定適用マスタリストを取得
		List<ApplicationDtoInterface> appList = appDao.findForActivateDate(dto.getActivateDate());
		// 無効期間で設定適用マスタ履歴情報を取得(対象DTOの有効日～次の履歴の有効日)
		appList
			.addAll(appDao.findForCheckTerm(dto.getActivateDate(), getNextActivateDate(dto.getActivateDate(), list)));
		return appList;
	}
	
}
