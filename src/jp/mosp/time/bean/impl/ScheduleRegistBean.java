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

import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.base.PlatformDtoInterface;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.time.bean.ScheduleRegistBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.constant.TimeMessageConst;
import jp.mosp.time.dao.settings.ApplicationDaoInterface;
import jp.mosp.time.dao.settings.ScheduleDaoInterface;
import jp.mosp.time.dto.settings.ApplicationDtoInterface;
import jp.mosp.time.dto.settings.ScheduleDtoInterface;
import jp.mosp.time.dto.settings.impl.TmmScheduleDto;

/**
 * カレンダ管理登録クラス。
 */
public class ScheduleRegistBean extends PlatformBean implements ScheduleRegistBeanInterface {
	
	/**
	 * カレンダコード項目長。<br>
	 */
	protected static final int		LEN_CALENDER_CODE	= 10;
	
	/**
	 * カレンダ名称項目長。<br>
	 */
	protected static final int		LEN_CALENDER_NAME	= 15;
	
	/**
	 * カレンダ略称項目長(バイト数)。<br>
	 */
	protected static final int		LEN_CALENDER_ABBR	= 6;
	
	/**
	 * カレンダマスタDAOクラス。<br>
	 */
	protected ScheduleDaoInterface	dao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public ScheduleRegistBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// DAO準備
		dao = createDaoInstance(ScheduleDaoInterface.class);
	}
	
	@Override
	public ScheduleDtoInterface getInitDto() {
		return new TmmScheduleDto();
	}
	
	@Override
	public void insert(ScheduleDtoInterface dto) throws MospException {
		// DTO妥当性確認
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
		dto.setTmmScheduleId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void add(ScheduleDtoInterface dto) throws MospException {
		// DTO妥当性確認
		validate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 履歴更新情報の検証
		checkAdd(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setTmmScheduleId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void update(ScheduleDtoInterface dto) throws MospException {
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
		logicalDelete(dao, dto.getTmmScheduleId());
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setTmmScheduleId(dao.nextRecordId());
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
			// 対象カレンダにおける有効日の情報を取得
			ScheduleDtoInterface dto = dao.findForKey(code, activateDate);
			// 存在確認(存在しなければ履歴追加、存在すれば履歴更新)
			if (dto == null) {
				// 対象カレンダにおける有効日以前で最新の情報を取得
				dto = dao.findForInfo(code, activateDate);
				// 対象雇用契約情報確認
				if (dto == null) {
					// エラーメッセージを設定
					PfMessageUtility.addErrorCodeNotExistBeforeDate(mospParams, code);
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
				dto.setTmmScheduleId(dao.nextRecordId());
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
				logicalDelete(dao, dto.getTmmScheduleId());
				// レコード識別ID最大値をインクリメントしてDTOに設定
				dto.setTmmScheduleId(dao.nextRecordId());
				// 登録処理
				dao.insert(dto);
			}
		}
	}
	
	@Override
	public void delete(ScheduleDtoInterface dto) throws MospException {
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
		logicalDelete(dao, dto.getTmmScheduleId());
	}
	
	/**
	 * 新規登録時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkInsert(ScheduleDtoInterface dto) throws MospException {
		// 対象レコードの有効日が重複していないかを確認
		checkDuplicateInsert(dao.findForHistory(dto.getScheduleCode()));
	}
	
	/**
	 * 履歴追加時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkAdd(ScheduleDtoInterface dto) throws MospException {
		// 対象レコードの有効日が重複していないかを確認
		checkDuplicateScheduleAdd(dao.findForKey(dto.getScheduleCode(), dto.getActivateDate()));
		// 無効フラグ確認
		if (isDtoActivate(dto)) {
			return;
		}
		// 履歴追加対象コードの履歴情報を取得
		List<ScheduleDtoInterface> list = dao.findForHistory(dto.getScheduleCode());
		// 生じる無効期間による履歴追加確認要否を取得
		if (!needCheckTermForAdd(dto, list)) {
			// 無効期間は発生しない
			return;
		}
		// 確認するべき設定適用マスタリストを取得
		List<ApplicationDtoInterface> appList = getApplicationListForCheck(dto, list);
		// コード使用確認
		checkCodeIsUsed(dto.getScheduleCode(), appList);
	}
	
	/**
	 * 履歴更新時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkUpdate(ScheduleDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getTmmScheduleId());
		// 無効フラグ確認
		if (isDtoActivate(dto)) {
			return;
		}
		// 更新元データの無効フラグ確認
		if (!isDtoActivate(dao.findForKey(dto.getTmmScheduleId(), true))) {
			// 更新元データが更新前から無効であれば無効期間は発生しない
			return;
		}
		// 履歴追加対象コードの履歴情報を取得
		List<ScheduleDtoInterface> list = dao.findForHistory(dto.getScheduleCode());
		// 確認するべき設定適用マスタリストを取得
		List<ApplicationDtoInterface> appList = getApplicationListForCheck(dto, list);
		// コード使用確認
		checkCodeIsUsed(dto.getScheduleCode(), appList);
	}
	
	/**
	 * 削除時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkDelete(ScheduleDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getTmmScheduleId());
		// 対象DTOの無効フラグ確認
		// 画面上の無効フラグは変更可能であるため確認しない。
		if (!isDtoActivate(dao.findForKey(dto.getTmmScheduleId(), true))) {
			// 削除対象が無効であれば無効期間は発生しない
			return;
		}
		// 削除対象コードの履歴情報を取得
		List<ScheduleDtoInterface> list = dao.findForHistory(dto.getScheduleCode());
		// 生じる無効期間による削除確認要否を取得
		if (!needCheckTermForDelete(dto, list)) {
			// 無効期間は発生しない
			return;
		}
		// 確認するべき設定適用マスタリストを取得
		List<ApplicationDtoInterface> appList = getApplicationListForCheck(dto, list);
		// コード使用確認
		checkCodeIsUsed(dto.getScheduleCode(), appList);
	}
	
	/**
	 * カレンダコードリストを取得する。<br>
	 * @param idArray レコード識別ID配列
	 * @return カレンダコードリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected List<String> getCodeList(long[] idArray) throws MospException {
		// リスト準備
		List<String> list = new ArrayList<String>();
		// レコード識別IDからDTOを取得し、コードをリストへ追加
		for (long id : idArray) {
			// レコード識別IDから対象DTOを取得
			ScheduleDtoInterface dto = (ScheduleDtoInterface)dao.findForKey(id, false);
			// 対象コードをリストへ追加
			list.add(dto.getScheduleCode());
		}
		return list;
	}
	
	/**
	 * 登録情報の妥当性を確認する。
	 * @param dto 対象DTO
	 * @throws MospException エラーが発生した場合。
	 */
	protected void validate(ScheduleDtoInterface dto) throws MospException {
		// カレンダコード
		String scheduleCodeName = mospParams.getName("Calendar", "Code");
		checkRequired(dto.getScheduleCode(), scheduleCodeName, null);
		checkLength(dto.getScheduleCode(), LEN_CALENDER_CODE, scheduleCodeName, null);
		// カレンダ名称
		String calenderName = mospParams.getName("Calendar", "Name");
		checkRequired(dto.getScheduleName(), calenderName, null);
		checkLength(dto.getScheduleName(), LEN_CALENDER_NAME, calenderName, null);
		// カレンダ略称
		String calendarAbbr = mospParams.getName("Calendar", "Abbreviation");
		checkRequired(dto.getScheduleAbbr(), calendarAbbr, null);
		// バイト数(表示上)確認(所属略称)
		checkByteLength(dto.getScheduleAbbr(), LEN_CALENDER_ABBR, calendarAbbr, null);
		// 追加業務ロジック処理を行う
		doAdditionalLogic(TimeConst.CODE_KEY_ADD_SCHEDULEREGISTBEAN_VALIDATE, dto);
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
			if ((code.equals(dto.getScheduleCode())) && (isDtoActivate(dto))) {
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
	 * 該当コードが使用されていた場合の警告メッセージを追加する。
	 * {@link #mospParams}に追加する。<br>
	 * @param code コード
	 * @param dto  設定適用マスタDTO
	 */
	protected void addCodeIsUsedMessage(String code, ApplicationDtoInterface dto) {
		String columnName = mospParams.getName("Calendar");
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
	
	/**
	 * 重複確認(履歴追加用)を行う。<br>
	 * 対象DTOがnullでない場合は、{@link #mospParams}にエラーメッセージを追加する。<br>
	 * @param dto 対象DTO
	 */
	protected void checkDuplicateScheduleAdd(BaseDtoInterface dto) {
		// 対象DTO存在確認
		if (dto != null) {
			// 対象DTOが存在する場合
			mospParams.addErrorMessage(TimeMessageConst.MSG_SCHEDULE_HIST_ALREADY_EXISTED);
		}
	}
	
}
