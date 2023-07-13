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
package jp.mosp.platform.bean.system.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.file.PlatformFileBean;
import jp.mosp.platform.bean.human.RetirementReferenceBeanInterface;
import jp.mosp.platform.bean.system.PositionRegistBeanInterface;
import jp.mosp.platform.dao.system.PositionDaoInterface;
import jp.mosp.platform.dto.human.ConcurrentDtoInterface;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.dto.system.PositionDtoInterface;
import jp.mosp.platform.dto.system.impl.PfmPositionDto;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.platform.utils.PfNameUtility;

/**
 * 職位マスタ登録クラス。
 */
public class PositionRegistBean extends PlatformFileBean implements PositionRegistBeanInterface {
	
	/**
	 * 職位コード項目長。<br>
	 */
	protected static final int		LEN_POSITION_CODE	= 10;
	
	/**
	 * 職位名称項目長。<br>
	 */
	protected static final int		LEN_POSITION_NAME	= 30;
	
	/**
	 * 職位略称項目長(バイト数)。<br>
	 */
	protected static final int		LEN_POSITION_ABBR	= 6;
	
	/**
	 * 職位ランク項目長。<br>
	 */
	protected static final int		LEN_POSITION_GRADE	= 2;
	
	/**
	 * 職位マスタDAOクラス。<br>
	 */
	protected PositionDaoInterface	dao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public PositionRegistBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// DAO準備
		dao = createDaoInstance(PositionDaoInterface.class);
	}
	
	@Override
	public PositionDtoInterface getInitDto() {
		return new PfmPositionDto();
	}
	
	@Override
	public void insert(PositionDtoInterface dto) throws MospException {
		// DTO妥当性確認
		validate(dto, null);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 新規登録情報の検証
		checkInsert(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setPfmPositionId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void add(PositionDtoInterface dto) throws MospException {
		// DTO妥当性確認
		validate(dto, null);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 履歴追加情報の検証
		checkAdd(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setPfmPositionId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void update(PositionDtoInterface dto) throws MospException {
		// DTO妥当性確認
		validate(dto, null);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 履歴更新情報の検証
		checkUpdate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 論理削除
		logicalDelete(dao, dto.getPfmPositionId());
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setPfmPositionId(dao.nextRecordId());
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
			// 対象職位における有効日の情報を取得
			PositionDtoInterface dto = dao.findForKey(code, activateDate);
			// 存在確認(存在しなければ履歴追加、存在すれば履歴更新)
			if (dto == null) {
				// 対象勤務地における有効日以前で最新の情報を取得
				dto = dao.findForInfo(code, activateDate);
				// 対象職位情報確認
				if (dto == null) {
					// エラーメッセージを設定
					PfMessageUtility.addErrorCodeNotExistBeforeDate(mospParams, code);
					continue;
				}
				// DTOに有効日、無効フラグを設定
				dto.setActivateDate(activateDate);
				dto.setInactivateFlag(inactivateFlag);
				// DTO妥当性確認
				validate(dto, null);
				// 履歴追加情報の検証
				checkAdd(dto);
				if (mospParams.hasErrorMessage()) {
					// エラーが存在したら履歴追加処理をしない
					continue;
				}
				// レコード識別ID最大値をインクリメントしてDTOに設定
				dto.setPfmPositionId(dao.nextRecordId());
				// 登録処理
				dao.insert(dto);
			} else {
				// DTOに無効フラグを設定
				dto.setInactivateFlag(inactivateFlag);
				// DTO妥当性確認
				validate(dto, null);
				// 履歴更新情報の検証
				checkUpdate(dto);
				if (mospParams.hasErrorMessage()) {
					// エラーが存在したら履歴更新処理をしない
					continue;
				}
				// 論理削除
				logicalDelete(dao, dto.getPfmPositionId());
				// レコード識別ID最大値をインクリメントしてDTOに設定
				dto.setPfmPositionId(dao.nextRecordId());
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
		// 対象勤務地リストの中身を削除
		for (long id : idArray) {
			// 削除対象勤務地を設定している社員がいないかを確認
			checkDelete((PositionDtoInterface)dao.findForKey(id, true));
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
	protected void checkInsert(PositionDtoInterface dto) throws MospException {
		// 対象レコードの有効日が重複していないかを確認
		checkDuplicateInsert(dao.findForHistory(dto.getPositionCode()));
	}
	
	/**
	 * 履歴追加時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkAdd(PositionDtoInterface dto) throws MospException {
		// 対象レコードの有効日が重複していないかを確認
		checkDuplicateAdd(dao.findForKey(dto.getPositionCode(), dto.getActivateDate()));
		// 無効フラグ確認
		if (isDtoActivate(dto)) {
			return;
		}
		// 履歴追加対象コードの履歴情報を取得
		List<PositionDtoInterface> list = dao.findForHistory(dto.getPositionCode());
		// 生じる無効期間による履歴追加確認要否を取得
		if (needCheckTermForAdd(dto, list) == false) {
			// 無効期間は発生しない
			return;
		}
		// 確認するべき人事マスタリストを取得
		List<HumanDtoInterface> humanList = getHumanListForCheck(dto, list);
		// 確認するべき兼務情報を取得
		List<ConcurrentDtoInterface> concurrentList = getConcurrentListForCheck(dto, list);
		// コード使用確認
		checkCodeIsUsed(dto, humanList);
		// コード使用確認(兼務情報)
		checkConcurrentCodeIsUsed(dto.getPositionCode(), concurrentList);
	}
	
	/**
	 * 履歴更新時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkUpdate(PositionDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getPfmPositionId());
		// 無効フラグ確認
		if (isDtoActivate(dto)) {
			return;
		}
		// 更新元データの無効フラグ確認
		if (isDtoActivate(dao.findForKey(dto.getPfmPositionId(), true)) == false) {
			// 更新元データが更新前から無効であれば無効期間は発生しない
			return;
		}
		// 更新対象コードの履歴情報を取得
		List<PositionDtoInterface> list = dao.findForHistory(dto.getPositionCode());
		// 確認するべき人事マスタリストを取得
		List<HumanDtoInterface> humanList = getHumanListForCheck(dto, list);
		// 確認するべき兼務情報を取得
		List<ConcurrentDtoInterface> concurrentList = getConcurrentListForCheck(dto, list);
		// コード使用確認
		checkCodeIsUsed(dto, humanList);
		// コード使用確認(兼務情報)
		checkConcurrentCodeIsUsed(dto.getPositionCode(), concurrentList);
	}
	
	/**
	 * 削除時の確認処理を行う。<br>
	 * 削除対象勤務地を設定している社員がいないかの確認を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkDelete(PositionDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getPfmPositionId());
		// 対象DTOの無効フラグ確認
		if (isDtoActivate(dto) == false) {
			// 削除対象が無効であれば無効期間は発生しない
			return;
		}
		// 削除対象コードの履歴情報を取得
		List<PositionDtoInterface> list = dao.findForHistory(dto.getPositionCode());
		// 生じる無効期間による削除確認要否を取得
		if (needCheckTermForDelete(dto, list) == false) {
			// 無効期間は発生しない
			return;
		}
		// 確認するべき人事マスタリストを取得
		List<HumanDtoInterface> humanList = getHumanListForCheck(dto, list);
		// 確認するべき兼務情報を取得
		List<ConcurrentDtoInterface> concurrentList = getConcurrentListForCheck(dto, list);
		// コード使用確認
		checkCodeIsUsed(dto, humanList);
		// コード使用確認(兼務情報)
		checkConcurrentCodeIsUsed(dto.getPositionCode(), concurrentList);
	}
	
	/**
	 * 勤務地コードリストを取得する。<br>
	 * @param idArray レコード識別ID配列
	 * @return 勤務地コードリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected List<String> getCodeList(long[] idArray) throws MospException {
		// リスト準備
		List<String> list = new ArrayList<String>();
		// レコード識別IDからDTOを取得し、コードをリストへ追加
		for (long id : idArray) {
			// レコード識別IDから対象DTOを取得
			PositionDtoInterface dto = (PositionDtoInterface)dao.findForKey(id, false);
			// 対象コードをリストへ追加
			list.add(dto.getPositionCode());
		}
		return list;
	}
	
	/**
	 * 人事マスタリスト内に対象コードが使用されている情報がないかの確認を行う。<br>
	 * @param positionDto 職位情報
	 * @param list 人事情報リスト	
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkCodeIsUsed(PositionDtoInterface positionDto, List<HumanDtoInterface> list)
			throws MospException {
		// 人事・退職情報参照クラスを取得
		RetirementReferenceBeanInterface refer = (RetirementReferenceBeanInterface)createBean(
				RetirementReferenceBeanInterface.class);
		// 職位コードを取得
		String positionCode = positionDto.getPositionCode();
		// 人事マスタリストの中身を確認
		for (HumanDtoInterface humanDto : list) {
			// 対象コード確認
			if (humanDto.getPositionCode().equals(positionCode)
					&& refer.isRetired(humanDto.getPersonalId(), positionDto.getActivateDate()) == false) {
				// エラーメッセージを設定
				PfMessageUtility.addErrorCodeIsUsed(mospParams, positionCode, humanDto.getEmployeeCode());
			}
		}
	}
	
	/**
	 * 兼務情報内に職位コードが使用されている情報がないかの確認をする。<br>
	 * @param code 職位コード
	 * @param list 人事マスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkConcurrentCodeIsUsed(String code, List<ConcurrentDtoInterface> list) throws MospException {
		// 兼務情報を確認
		for (ConcurrentDtoInterface concurrentDto : list) {
			// 職位コード確認
			if (code.equals(concurrentDto.getPositionCode())) {
				HumanDtoInterface humanDto = getHumanInfo(concurrentDto.getPersonalId(), concurrentDto.getStartDate());
				// エラーメッセージを設定
				PfMessageUtility.addErrorCodeIsUsed(mospParams, code, humanDto.getEmployeeCode());
			}
		}
	}
	
	/**
	 * 登録情報の妥当性を確認する。
	 * @param dto 対象DTO
	 * @param row 行インデックス
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void validate(PositionDtoInterface dto, Integer row) throws MospException {
		// 必須確認(職位コード)
		checkRequired(dto.getPositionCode(), PfNameUtility.positionCode(mospParams), row);
		// 必須確認(有効日)
		checkRequired(dto.getActivateDate(), PfNameUtility.activateDate(mospParams), row);
		// 必須確認(職位名称)
		checkRequired(dto.getPositionName(), PfNameUtility.positionName(mospParams), row);
		// 必須確認(職位ランク)
		checkRequired(dto.getPositionGrade(), PfNameUtility.positionGrade(mospParams), row);
		// 必須確認(職位略称)
		checkRequired(dto.getPositionAbbr(), PfNameUtility.positionAbbreviation(mospParams), row);
		// 桁数確認(職位コード)
		checkLength(dto.getPositionCode(), LEN_POSITION_CODE, PfNameUtility.positionCode(mospParams), row);
		// 桁数確認(職位名称)
		checkLength(dto.getPositionName(), LEN_POSITION_NAME, PfNameUtility.positionName(mospParams), row);
		// 桁数確認(職位ランク)
		checkLength(dto.getPositionGrade(), LEN_POSITION_GRADE, PfNameUtility.positionGrade(mospParams), row);
		// バイト数(表示上)確認(職位略称)
		checkByteLength(dto.getPositionAbbr(), LEN_POSITION_ABBR, PfNameUtility.positionAbbreviation(mospParams), row);
		// 型確認(職位コード)
		checkTypeCode(dto.getPositionCode(), PfNameUtility.positionCode(mospParams), row);
		// 型確認(職位ランク)
		checkTypeNumber(String.valueOf(dto.getPositionGrade()), PfNameUtility.positionGrade(mospParams), row);
		// 型確認(有効/無効フラグ)
		checkInactivateFlag(dto.getInactivateFlag(), row);
		// 無効フラグ確認
		if (isDtoActivate(dto) == false) {
			// 妥当性確認終了
			return;
		}
	}
	
}
