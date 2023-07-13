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
import jp.mosp.platform.utils.PfNameUtility;
import jp.mosp.time.bean.WorkTypeRegistBeanInterface;
import jp.mosp.time.constant.TimeMessageConst;
import jp.mosp.time.dao.settings.ScheduleDateDaoInterface;
import jp.mosp.time.dao.settings.WorkTypeDaoInterface;
import jp.mosp.time.dto.settings.ScheduleDateDtoInterface;
import jp.mosp.time.dto.settings.WorkTypeDtoInterface;
import jp.mosp.time.dto.settings.impl.TmmWorkTypeDto;

/**
 * 勤務形態マスタ登録クラス。
 */
public class WorkTypeRegistBean extends PlatformBean implements WorkTypeRegistBeanInterface {
	
	/**
	 * 勤務形態マスタDAOクラス。<br>
	 */
	protected WorkTypeDaoInterface	dao;
	
	/**
	 * 項目長(勤務形態コード)。<br>
	 */
	protected static final int		LEN_WORK_TYPE_CODE	= 10;
	
	/**
	 * 項目長(勤務形態名)。<br>
	 */
	protected static final int		LEN_WORK_TYPE_NAME	= 15;
	
	/**
	 * 項目長(勤務形態略称)(バイト数)。<br>
	 */
	protected static final int		LEN_WORK_TYPE_ABBR	= 6;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public WorkTypeRegistBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// DAO準備
		dao = createDaoInstance(WorkTypeDaoInterface.class);
	}
	
	@Override
	public WorkTypeDtoInterface getInitDto() {
		return new TmmWorkTypeDto();
	}
	
	@Override
	public void insert(WorkTypeDtoInterface dto) throws MospException {
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
		dto.setTmmWorkTypeId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void add(WorkTypeDtoInterface dto) throws MospException {
		// DTO妥当性確認
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
		dto.setTmmWorkTypeId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void update(WorkTypeDtoInterface dto) throws MospException {
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
		logicalDelete(dao, dto.getTmmWorkTypeId());
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setTmmWorkTypeId(dao.nextRecordId());
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
			// 対象勤務形態における有効日の情報を取得
			WorkTypeDtoInterface dto = dao.findForKey(code, activateDate);
			// 存在確認(存在しなければ履歴追加、存在すれば履歴更新)
			if (dto == null) {
				// 対象勤務形態における有効日以前で最新の情報を取得
				dto = dao.findForInfo(code, activateDate);
				// 対象勤務形態情報確認
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
				dto.setTmmWorkTypeId(dao.nextRecordId());
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
				logicalDelete(dao, dto.getTmmWorkTypeId());
				// レコード識別ID最大値をインクリメントしてDTOに設定
				dto.setTmmWorkTypeId(dao.nextRecordId());
				// 登録処理
				dao.insert(dto);
			}
		}
	}
	
	@Override
	public void delete(WorkTypeDtoInterface dto) throws MospException {
		// DTO妥当性確認
		validate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 削除対象勤務形態情報が使用されていないかを確認
		checkDelete(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 論理削除
		logicalDelete(dao, dto.getTmmWorkTypeId());
	}
	
	/**
	 * 入力項目の桁数や型を確認する。<br>
	 * @param dto 勤務形態情報
	 */
	public void checkValidate(WorkTypeDtoInterface dto) {
		// 有効日
		checkRequired(dto.getActivateDate(), PfNameUtility.activateDate(mospParams), null);
		// 勤務形態コード
		String workTypeCode = mospParams.getName("Work", "Form", "Code");
		checkRequired(dto.getWorkTypeCode(), workTypeCode, null);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		checkLength(dto.getWorkTypeCode(), LEN_WORK_TYPE_CODE, workTypeCode, null);
		checkTypeCode(dto.getWorkTypeCode(), workTypeCode, null);
		// 勤務形態名称 妥当性チェック(桁数)
		String workTypeName = mospParams.getName("Work", "Form", "Name");
		checkRequired(dto.getWorkTypeName(), workTypeName, null);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		checkLength(dto.getWorkTypeName(), LEN_WORK_TYPE_NAME, workTypeName, null);
		// 勤務形態略称 妥当性チェック(桁数)
		String workTypeAbbr = mospParams.getName("Work", "Form", "Abbreviation");
		checkRequired(dto.getWorkTypeAbbr(), workTypeAbbr, null);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		checkByteLength(dto.getWorkTypeAbbr(), LEN_WORK_TYPE_ABBR, workTypeAbbr, null);
		// 型確認(有効/無効フラグ)
		checkInactivateFlag(dto.getInactivateFlag(), null);
	}
	
	/**
	 * 新規登録時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkInsert(WorkTypeDtoInterface dto) throws MospException {
		// 対象レコードの有効日が重複していないかを確認
		checkDuplicateInsert(dao.findForHistory(dto.getWorkTypeCode()));
	}
	
	/**
	 * 履歴追加時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkAdd(WorkTypeDtoInterface dto) throws MospException {
		// 対象レコードの有効日が重複していないかを確認
		checkDuplicateAdd(dao.findForKey(dto.getWorkTypeCode(), dto.getActivateDate()));
		// 無効フラグ確認
		if (isDtoActivate(dto)) {
			return;
		}
		// 履歴追加対象コードの履歴情報を取得
		List<WorkTypeDtoInterface> list = dao.findForHistory(dto.getWorkTypeCode());
		// 生じる無効期間による履歴追加確認要否を取得
		if (!needCheckTermForAdd(dto, list)) {
			// 無効期間は発生しない
			return;
		}
		// 確認するべきカレンダ日マスタリストを取得
		List<ScheduleDateDtoInterface> scheduleDateList = getScheduleDateListForCheck(dto.getWorkTypeCode(), dto, list);
		// コード使用確認
		checkCodeIsUsed(dto.getWorkTypeCode(), scheduleDateList);
	}
	
	/**
	 * 履歴更新時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkUpdate(WorkTypeDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getTmmWorkTypeId());
		// 無効フラグ確認
		if (isDtoActivate(dto)) {
			return;
		}
		// 更新元データの無効フラグ確認
		if (!isDtoActivate(dao.findForKey(dto.getTmmWorkTypeId(), true))) {
			// 更新元データが更新前から無効であれば無効期間は発生しない
			return;
		}
		// 履歴追加対象コードの履歴情報を取得
		List<WorkTypeDtoInterface> list = dao.findForHistory(dto.getWorkTypeCode());
		// 確認するべきカレンダ日マスタリストを取得
		List<ScheduleDateDtoInterface> scheduleDateList = getScheduleDateListForCheck(dto.getWorkTypeCode(), dto, list);
		// コード使用確認
		checkCodeIsUsed(dto.getWorkTypeCode(), scheduleDateList);
	}
	
	/**
	 * 削除時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkDelete(WorkTypeDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getTmmWorkTypeId());
		// 対象DTOの無効フラグ確認
		if (!isDtoActivate(dao.findForKey(dto.getTmmWorkTypeId(), true))) {
			// 削除対象が無効であれば無効期間は発生しない
			return;
		}
		// 削除対象コードの履歴情報を取得
		List<WorkTypeDtoInterface> list = dao.findForHistory(dto.getWorkTypeCode());
		// 生じる無効期間による削除確認要否を取得
		if (!needCheckTermForDelete(dto, list)) {
			// 無効期間は発生しない
			return;
		}
		// 確認するべきカレンダ日マスタリストを取得
		List<ScheduleDateDtoInterface> scheduleDateList = getScheduleDateListForCheck(dto.getWorkTypeCode(), dto, list);
		// コード使用確認
		checkCodeIsUsed(dto.getWorkTypeCode(), scheduleDateList);
	}
	
	/**
	 * 勤務形態コードリストを取得する。<br>
	 * @param idArray  レコード識別ID配列
	 * @return 勤務形態コードリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected List<String> getCodeList(long[] idArray) throws MospException {
		// リスト準備
		List<String> list = new ArrayList<String>();
		// レコード識別IDからDTOを取得し、コードをリストへ追加
		for (long id : idArray) {
			// レコード識別IDから対象DTOを取得
			WorkTypeDtoInterface dto = (WorkTypeDtoInterface)dao.findForKey(id, false);
			// 対象コードをリストへ追加
			list.add(dto.getWorkTypeCode());
		}
		return list;
	}
	
	/**
	 * 登録情報の妥当性を確認する。
	 * @param dto 対象DTO
	 */
	protected void validate(WorkTypeDtoInterface dto) {
		// 妥当性確認
		checkValidate(dto);
	}
	
	/**
	 * カレンダ日マスタリスト内に対象コードが使用されている情報がないかの確認を行う。<br>
	 * @param code 対象コード
	 * @param list カレンダ日マスタリスト
	 */
	protected void checkCodeIsUsed(String code, List<ScheduleDateDtoInterface> list) {
		// カレンダ日マスタリストの中身を確認
		for (ScheduleDateDtoInterface dto : list) {
			// 対象コード確認
			if (code.equals(dto.getWorkTypeCode())) {
				// メッセージ設定
				addCodeIsUsedMessage(code, dto);
				break;
			}
		}
	}
	
	/**
	 * 該当コードが使用されていた場合の警告メッセージを追加する。
	 * {@link #mospParams}に追加する。<br>
	 * @param code コード
	 * @param dto  カレンダ日マスタDTO
	 */
	protected void addCodeIsUsedMessage(String code, ScheduleDateDtoInterface dto) {
		mospParams.addErrorMessage(TimeMessageConst.MSG_WORK_TYPE_CODE_IS_USED, code, dto.getScheduleCode());
	}
	
	/**
	 * 確認すべきカレンダ日マスタリストを取得する。<br>
	 * 対象DTOの有効日以前で最新のカレンダ日マスタリストと
	 * 対象DTOの有効日～対象DTOの次の履歴の有効日に有効日が含まれる
	 * カレンダ日マスタリストを合わせたリストを取得する。<br>
	 * 対象コード履歴リストは、有効日の昇順で並んでいるものとする。<br>
	 * 各種マスタ操作時に生じる無効期間におけるコード使用確認等で用いられる。<br>
	 * @param code コード
	 * @param dto 	対象DTO
	 * @param list 対象コード履歴リスト
	 * @return カレンダ日マスタリスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected List<ScheduleDateDtoInterface> getScheduleDateListForCheck(String code, PlatformDtoInterface dto,
			List<? extends PlatformDtoInterface> list) throws MospException {
		// 設定適用マスタDAO取得
		ScheduleDateDaoInterface scheduleDateDao = createDaoInstance(ScheduleDateDaoInterface.class);
		// 削除対象の有効日以前で最新の設定適用マスタリストを取得
		List<ScheduleDateDtoInterface> scheduleDateList = scheduleDateDao.findForActivateDate(dto.getActivateDate(),
				code);
		// 無効期間で設定適用マスタ履歴情報を取得(対象DTOの有効日～次の履歴の有効日)
		scheduleDateList.addAll(scheduleDateDao.findForTerm(dto.getActivateDate(),
				getNextActivateDate(dto.getActivateDate(), list), code));
		return scheduleDateList;
	}
}
