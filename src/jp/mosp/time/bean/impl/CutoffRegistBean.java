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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.base.PlatformDtoInterface;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.time.bean.CutoffRegistBeanInterface;
import jp.mosp.time.bean.TotalTimeEmployeeTransactionReferenceBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.constant.TimeMessageConst;
import jp.mosp.time.dao.settings.CutoffDaoInterface;
import jp.mosp.time.dao.settings.TimeSettingDaoInterface;
import jp.mosp.time.dto.settings.CutoffDtoInterface;
import jp.mosp.time.dto.settings.TimeSettingDtoInterface;
import jp.mosp.time.dto.settings.TotalTimeEmployeeDtoInterface;
import jp.mosp.time.dto.settings.impl.TmmCutoffDto;

/**
 * 締日管理登録クラス。
 */
public class CutoffRegistBean extends PlatformBean implements CutoffRegistBeanInterface {
	
	/**
	 * 締日管理マスタDAOクラス。<br>
	 */
	protected CutoffDaoInterface									dao;
	
	/**
	 * 社員勤怠集計管理参照クラス。
	 */
	protected TotalTimeEmployeeTransactionReferenceBeanInterface	totalTimeEmployeetransactionReference;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public CutoffRegistBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// DAO準備
		dao = createDaoInstance(CutoffDaoInterface.class);
		totalTimeEmployeetransactionReference = createBeanInstance(
				TotalTimeEmployeeTransactionReferenceBeanInterface.class);
	}
	
	@Override
	public CutoffDtoInterface getInitDto() {
		return new TmmCutoffDto();
	}
	
	@Override
	public void insert(CutoffDtoInterface dto) throws MospException {
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
		dto.setTmmCutoffId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void add(CutoffDtoInterface dto) throws MospException {
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
		dto.setTmmCutoffId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void update(CutoffDtoInterface dto) throws MospException {
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
		logicalDelete(dao, dto.getTmmCutoffId());
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setTmmCutoffId(dao.nextRecordId());
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
			// 対象締日における有効日の情報を取得
			CutoffDtoInterface dto = dao.findForKey(code, activateDate);
			// 存在確認(存在しなければ履歴追加、存在すれば履歴更新)
			if (dto == null) {
				// 対象締日における有効日以前で最新の情報を取得
				dto = dao.findForInfo(code, activateDate);
				// 対象締日情報確認
				if (dto == null) {
					// エラーメッセージを設定
					PfMessageUtility.addErrorCodeNotExistBeforeDate(mospParams, code);
					continue;
				}
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
				dto.setTmmCutoffId(dao.nextRecordId());
				// 登録処理
				dao.insert(dto);
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
				logicalDelete(dao, dto.getTmmCutoffId());
				// レコード識別ID最大値をインクリメントしてDTOに設定
				dto.setTmmCutoffId(dao.nextRecordId());
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
		// 対象締日リストの中身を削除
		for (long id : idArray) {
			checkDelete((CutoffDtoInterface)dao.findForKey(id, true));
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
	protected void checkInsert(CutoffDtoInterface dto) throws MospException {
		// 対象レコードの有効日が重複していないかを確認
		checkDuplicateInsert(dao.findForHistory(dto.getCutoffCode()));
	}
	
	/**
	 * 履歴追加時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkAdd(CutoffDtoInterface dto) throws MospException {
		// 対象レコードの有効日が重複していないかを確認
		checkDuplicateAdd(dao.findForKey(dto.getCutoffCode(), dto.getActivateDate()));
		// 履歴追加対象コードの履歴情報を取得
		List<CutoffDtoInterface> list = dao.findForHistory(dto.getCutoffCode());
		// 無効フラグ確認
		if (isDtoActivate(dto)) {
			return;
		}
		// 生じる無効期間による履歴追加確認要否を取得
		if (needCheckTermForAdd(dto, list) == false) {
			// 無効期間は発生しない
			return;
		}
		// 確認するべき勤怠設定マスタリストを取得
		List<TimeSettingDtoInterface> timeSettingList = getTimeSettingListForCheck(dto, list);
		// コード使用確認
		checkCodeIsUsed(dto.getCutoffCode(), timeSettingList);
		
	}
	
	/**
	 * 履歴更新時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkUpdate(CutoffDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getTmmCutoffId());
		// 履歴追加対象コードの履歴情報を取得
		List<CutoffDtoInterface> list = dao.findForHistory(dto.getCutoffCode());
		// 勤怠集計をしていないか確認
		checkChangeCutoffUpdate(dto, list);
		// 無効フラグ確認
		if (isDtoActivate(dto)) {
			return;
		}
		// 更新元データの無効フラグ確認
		if (!isDtoActivate(dao.findForKey(dto.getTmmCutoffId(), true))) {
			// 更新元データが更新前から無効であれば無効期間は発生しない
			return;
		}
		// 確認するべき勤怠設定マスタリストを取得
		List<TimeSettingDtoInterface> timeSettingList = getTimeSettingListForCheck(dto, list);
		// コード使用確認
		checkCodeIsUsed(dto.getCutoffCode(), timeSettingList);
	}
	
	/**
	 * 削除時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkDelete(CutoffDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getTmmCutoffId());
		// 対象DTOの無効フラグ確認
		if (!isDtoActivate(dto)) {
			// 削除対象が無効であれば無効期間は発生しない
			return;
		}
		// 削除対象コードの履歴情報を取得
		List<CutoffDtoInterface> list = dao.findForHistory(dto.getCutoffCode());
		// 生じる無効期間による削除確認要否を取得
		if (!needCheckTermForDelete(dto, list)) {
			// 無効期間は発生しない
			return;
		}
		// 確認するべき勤怠設定マスタリストを取得
		List<TimeSettingDtoInterface> timeSettingList = getTimeSettingListForCheck(dto, list);
		// コード使用確認
		checkCodeIsUsed(dto.getCutoffCode(), timeSettingList);
	}
	
	/**
	 * 締日コードリストを取得する。<br>
	 * 同時に排他確認を行う。<br>
	 * @param idArray レコード識別ID配列
	 * @return 締日コードリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected List<String> getCodeList(long[] idArray) throws MospException {
		// リスト準備
		List<String> list = new ArrayList<String>();
		// レコード識別IDからDTOを取得し、コードをリストへ追加
		for (long id : idArray) {
			// レコード識別IDから対象DTOを取得
			CutoffDtoInterface dto = (CutoffDtoInterface)dao.findForKey(id, false);
			// 排他確認
			checkExclusive(dto);
			// 対象コードをリストへ追加
			list.add(dto.getCutoffCode());
		}
		return list;
	}
	
	/**
	 * 履歴更新時の締日変更妥当性確認。
	 * 期間内に対象締日で勤怠集計がされている場合は
	 * エラーメッセージを設定する。
	 * @param dto 対象DTO
	 * @param list 対象DTO履歴一覧
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkChangeCutoffUpdate(CutoffDtoInterface dto, List<CutoffDtoInterface> list) throws MospException {
		// インデックス準備
		int index = -1;
		// 履歴情報毎に処理
		for (int i = 0; i < list.size(); i++) {
			// 変更前データの場合
			if (list.get(i).getTmmCutoffId() == dto.getTmmCutoffId()) {
				// 変更対象インデックス取得
				index = i;
				break;
			}
		}
		// 終了日設定
		Date endDate = null;
		// 対象変更前情報取得
		CutoffDtoInterface targetCutoffDto = list.get(index);
		// 締日を変更せず有効の場合
		if (targetCutoffDto.getCutoffDate() == dto.getCutoffDate()
				&& targetCutoffDto.getInactivateFlag() == dto.getInactivateFlag()) {
			// 処理なし
			return;
		}
		// 変更対象が最新でない場合
		if (list.size() - 1 != index) {
			// 対象情報次履歴取得
			CutoffDtoInterface nextCutoffDto = list.get(index + 1);
			// 次履歴がある場合
			if (nextCutoffDto != null) {
				endDate = nextCutoffDto.getActivateDate();
			}
		}
		// 仮締コードから期間内の社員勤怠集計管理情報リストを取得
		List<TotalTimeEmployeeDtoInterface> totalList = totalTimeEmployeetransactionReference
			.isCutoffTermState(dto.getCutoffCode(), targetCutoffDto.getActivateDate(), endDate);
		// リスト確認
		if (totalList.isEmpty()) {
			return;
		}
		// 期間内の集計情報毎に処理
		for (TotalTimeEmployeeDtoInterface totalDto : totalList) {
			// 集計年、集計月取得
			String year = String.valueOf(totalDto.getCalculationYear());
			String month = String.valueOf(totalDto.getCalculationMonth());
			// 仮締状態の場合
			if (totalDto.getCutoffState() == TimeConst.CODE_CUTOFF_STATE_TEMP_TIGHT) {
				mospParams.addErrorMessage(TimeMessageConst.MSG_ALREADY_CALC_IS_USED, year, month);
				break;
			}
			// 確定状態の場合
			if (totalDto.getCutoffState() == TimeConst.CODE_CUTOFF_STATE_TIGHTENED) {
				mospParams.addErrorMessage(TimeMessageConst.MSG_ALREADY_CALC_IS_USED, year, month);
				break;
			}
		}
	}
	
	/**
	 * 登録情報の妥当性を確認確認する。
	 * @param dto 対象DTO
	 */
	protected void validate(CutoffDtoInterface dto) {
		// 処理無し
	}
	
	/**
	 * 勤怠設定マスタリスト内に対象コードが使用されている情報がないかの確認を行う。<br>
	 * @param code 対象コード
	 * @param list 勤怠設定マスタリスト
	 */
	protected void checkCodeIsUsed(String code, List<TimeSettingDtoInterface> list) {
		// 同一の勤怠設定コードのメッセージは出力しない。
		String codeAdded = "";
		// 勤怠設定マスタリストの中身を確認
		for (TimeSettingDtoInterface dto : list) {
			// 対象コード確認
			if ((code.equals(dto.getCutoffCode())) && (isDtoActivate(dto))) {
				// 同一の勤怠設定コードのメッセージは出力しない。
				if (!codeAdded.equals(dto.getWorkSettingCode())) {
					// メッセージ設定
					addCutoffCodeIsUsedMessage(code, dto);
					// メッセージに設定した勤怠設定コードを保持
					codeAdded = dto.getWorkSettingCode();
				}
			}
		}
	}
	
	/**
	 * 確認すべき勤怠設定マスタリストを取得する。<br>
	 * 対象DTOの有効日以前で最新の承勤怠設定マスタリストと
	 * 対象DTOの有効日～対象DTOの次の履歴の有効日に有効日が含まれる
	 * 勤怠設定マスタリストを合わせたリストを取得する。<br>
	 * 対象コード履歴リストは、有効日の昇順で並んでいるものとする。<br>
	 * 各種マスタ操作時に生じる無効期間におけるコード使用確認等で用いられる。<br>
	 * @param dto 	対象DTO
	 * @param list 対象コード履歴リスト
	 * @return 勤怠設定マスタリスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected List<TimeSettingDtoInterface> getTimeSettingListForCheck(PlatformDtoInterface dto,
			List<? extends PlatformDtoInterface> list) throws MospException {
		// 勤怠設定マスタDAO取得
		TimeSettingDaoInterface timeSettingDao = createDaoInstance(TimeSettingDaoInterface.class);
		// 削除対象の有効日以前で最新の勤怠設定マスタ情報を取得
		List<TimeSettingDtoInterface> timeSettingList = timeSettingDao.findForActivateDate(dto.getActivateDate());
		// 無効期間で勤怠設定マスタ履歴情報を取得(対象DTOの有効日～次の履歴の有効日)
		timeSettingList.addAll(
				timeSettingDao.findForTerm(dto.getActivateDate(), getNextActivateDate(dto.getActivateDate(), list)));
		return timeSettingList;
	}
	
	/**
	 * 該当コードが使用されていた場合の警告メッセージを追加する。
	 * {@link #mospParams}に追加する。<br>
	 * @param code コード
	 * @param dto  勤怠設定マスタDTO
	 */
	protected void addCutoffCodeIsUsedMessage(String code, TimeSettingDtoInterface dto) {
		mospParams.addErrorMessage(TimeMessageConst.MSG_CUTOFF_CODE_IS_USED, code, dto.getWorkSettingCode());
	}
	
}
