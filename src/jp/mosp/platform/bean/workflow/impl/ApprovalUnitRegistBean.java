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
package jp.mosp.platform.bean.workflow.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.base.PlatformDtoInterface;
import jp.mosp.platform.bean.human.base.PlatformHumanBean;
import jp.mosp.platform.bean.workflow.ApprovalUnitReferenceBeanInterface;
import jp.mosp.platform.bean.workflow.ApprovalUnitRegistBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dao.workflow.ApprovalRouteUnitDaoInterface;
import jp.mosp.platform.dao.workflow.ApprovalUnitDaoInterface;
import jp.mosp.platform.dto.workflow.ApprovalRouteUnitDtoInterface;
import jp.mosp.platform.dto.workflow.ApprovalUnitDtoInterface;
import jp.mosp.platform.dto.workflow.impl.PfmApprovalUnitDto;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.platform.utils.PfNameUtility;

/**
 * 承認ユニットマスタ登録クラス。
 */
public class ApprovalUnitRegistBean extends PlatformHumanBean implements ApprovalUnitRegistBeanInterface {
	
	/**
	 * コード項目長。<br>
	 */
	protected static final int						LEN_CODE		= 10;
	
	/**
	 * 名称項目長。<br>
	 */
	protected static final int						LEN_UNIT_NAME	= 50;
	
	/**
	 * 承認ユニットマスタDAO
	 */
	protected ApprovalUnitDaoInterface				dao;
	
	/**
	 * 承認ユニットマスタ参照インターフェース。
	 */
	protected ApprovalUnitReferenceBeanInterface	unitReference;
	
	
	/**
	 * {@link PlatformHumanBean#PlatformHumanBean()}を実行する。<br>
	 */
	public ApprovalUnitRegistBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// 継承基の処理を実行
		super.initBean();
		// DAOを準備
		dao = createDaoInstance(ApprovalUnitDaoInterface.class);
		unitReference = createBeanInstance(ApprovalUnitReferenceBeanInterface.class);
	}
	
	@Override
	public ApprovalUnitDtoInterface getInitDto() {
		return new PfmApprovalUnitDto();
	}
	
	@Override
	public void add(ApprovalUnitDtoInterface dto) throws MospException {
		// DTOの妥当性確認
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
		dto.setPfmApprovalUnitId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void delete(ApprovalUnitDtoInterface dto) throws MospException {
		// レコード識別IDを取得。
		dto.setPfmApprovalUnitId(getRecordID(dto));
		// 削除対象ユニット情報が使用されていないかを確認
		checkDelete(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 論理削除
		logicalDelete(dao, dto.getPfmApprovalUnitId());
	}
	
	@Override
	public void insert(ApprovalUnitDtoInterface dto) throws MospException {
		// DTOの妥当性確認
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
		dto.setPfmApprovalUnitId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void update(ApprovalUnitDtoInterface dto) throws MospException {
		// レコード識別IDを取得。
		dto.setPfmApprovalUnitId(getRecordID(dto));
		// DTOの妥当性確認
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
		logicalDelete(dao, dto.getPfmApprovalUnitId());
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setPfmApprovalUnitId(dao.nextRecordId());
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
			// 対象ユニット情報における有効日の情報を取得
			ApprovalUnitDtoInterface dto = dao.findForKey(code, activateDate);
			// 存在確認(存在しなければ履歴追加、存在すれば履歴更新)
			if (dto == null) {
				// 対象ユニット情報における有効日以前で最新の情報を取得
				dto = dao.findForInfo(code, activateDate);
				// 対象ユニット情報確認
				if (dto == null) {
					// エラーメッセージを設定
					PfMessageUtility.addErrorCodeNotExistBeforeDate(mospParams, code);
					continue;
				}
				// DTOに有効日、無効フラグを設定
				dto.setActivateDate(activateDate);
				dto.setInactivateFlag(inactivateFlag);
				// DTOの妥当性確認
				validate(dto, null);
				// 履歴追加情報の検証
				checkAdd(dto);
				if (mospParams.hasErrorMessage()) {
					// エラーが存在したら履歴追加処理をしない
					continue;
				}
				// レコード識別ID最大値をインクリメントしてDTOに設定
				dto.setPfmApprovalUnitId(dao.nextRecordId());
				// 登録処理
				dao.insert(dto);
			} else {
				// DTOに無効フラグを設定
				dto.setInactivateFlag(inactivateFlag);
				// DTOの妥当性確認
				validate(dto, null);
				// 履歴更新情報の検証
				checkUpdate(dto);
				if (mospParams.hasErrorMessage()) {
					// エラーが存在したら履歴更新処理をしない
					continue;
				}
				// 論理削除
				logicalDelete(dao, dto.getPfmApprovalUnitId());
				// レコード識別ID最大値をインクリメントしてDTOに設定
				dto.setPfmApprovalUnitId(dao.nextRecordId());
				// 登録処理
				dao.insert(dto);
			}
		}
	}
	
	@Override
	public void regist(ApprovalUnitDtoInterface dto) throws MospException {
		// 履歴一覧取得
		List<ApprovalUnitDtoInterface> list = unitReference.getApprovalUnitHistory(dto.getUnitCode());
		// 履歴一覧確認
		if (list.isEmpty()) {
			// 新規登録
			insert(dto);
			return;
		}
		// ユニット情報毎に処理
		for (ApprovalUnitDtoInterface approvalDto : list) {
			// 有効日確認
			if (dto.getActivateDate().compareTo(approvalDto.getActivateDate()) == 0) {
				// 履歴更新
				update(dto);
				return;
			}
		}
		// 履歴追加
		add(dto);
	}
	
	@Override
	public void validate(ApprovalUnitDtoInterface dto, Integer row) throws MospException {
		// 必須確認(ユニットコード)
		checkRequired(dto.getUnitCode(), PfNameUtility.unitCode(mospParams), row);
		// 必須確認(有効日)
		checkRequired(dto.getActivateDate(), PfNameUtility.activateDate(mospParams), row);
		// 必須確認(ユニット区分)
		checkRequired(dto.getUnitType(), PfNameUtility.unitType(mospParams), row);
		// 桁数確認(ユニットコード)
		checkLength(dto.getUnitCode(), LEN_CODE, PfNameUtility.unitCode(mospParams), row);
		// 桁数確認(ユニット名称)
		checkLength(dto.getUnitName(), LEN_UNIT_NAME, PfNameUtility.unitName(mospParams), row);
		// 桁数確認(承認者所属コード)
		checkLength(dto.getApproverSectionCode(), LEN_CODE, PfNameUtility.approverSection(mospParams), row);
		// 桁数確認(承認者職位コード)
		checkLength(dto.getApproverPositionCode(), LEN_CODE, PfNameUtility.approverPosition(mospParams), row);
		// 桁数確認(承認者職位等級範囲)
		checkLength(dto.getApproverPositionGrade(), LEN_CODE, PfNameUtility.approverPositionGrade(mospParams),
				row);
		// 型確認(ユニットコード)
		checkTypeCode(dto.getUnitCode(), PfNameUtility.unitCode(mospParams), row);
		// 型確認(無効フラグ)
		checkInactivateFlag(dto.getInactivateFlag(), row);
		// 利用可能文字列確認(承認者職位等級範囲)
		checkAvailableChars(dto.getApproverPositionGrade(),
				MospUtility.getCodeList(mospParams, PlatformConst.CODE_KEY_UNIT_POSITION_GRADE_RANGE, true),
				PfNameUtility.approverPositionGrade(mospParams), row);
		// 無効フラグ確認
		if (isDtoActivate(dto) == false) {
			// 妥当性確認終了
			return;
		}
		// エラー確認
		if (mospParams.hasErrorMessage()) {
			// 妥当性確認終了
			return;
		}
		// ユニット区分確認(所属の場合)
		if (dto.getUnitType().equals(PlatformConst.UNIT_TYPE_SECTION)) {
			// 必須確認(承認者所属コード)
			checkRequired(dto.getApproverSectionCode(), PfNameUtility.approverSection(mospParams), row);
			// 必須確認(承認者職位コード)
			checkRequired(dto.getApproverPositionCode(), PfNameUtility.approverPosition(mospParams), row);
			// 所属及び職位の存在確認
			checkMaster(dto, row);
		}
		// ユニット区分確認(個人の場合)
		if (dto.getUnitType().equals(PlatformConst.UNIT_TYPE_PERSON)) {
			// 必須確認(承認者個人ID)
			checkRequired(dto.getApproverPersonalId(), PfNameUtility.approverEmployeeCode(mospParams), row);
		}
		// 必須確認(ユニット名称)
		checkRequired(dto.getUnitName(), PfNameUtility.unitName(mospParams), row);
	}
	
	/**
	 * 所属及び職位の存在確認を行う。<br>
	 * @param dto 対象DTO
	 * @param row 行インデックス
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void checkMaster(ApprovalUnitDtoInterface dto, Integer row) throws MospException {
		// 履歴一覧取得
		List<ApprovalUnitDtoInterface> list = unitReference.getApprovalUnitHistory(dto.getUnitCode());
		// 期間取得
		Date startDate = dto.getActivateDate();
		Date endDate = getEffectiveLastDate(dto.getActivateDate(), list);
		// 所属存在確認
		checkSection(dto.getApproverSectionCode(), startDate, endDate, row);
		// 職位存在確認
		checkPosition(dto.getApproverPositionCode(), startDate, endDate, row);
	}
	
	/**
	 * ユニットコードリストを取得する。<br>
	 * 同時に排他確認を行う。<br>
	 * @param idArray レコード識別ID配列
	 * @return ユニットコードリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected List<String> getCodeList(long[] idArray) throws MospException {
		// リスト準備
		List<String> list = new ArrayList<String>();
		// レコード識別IDからDTOを取得し、コードをリストへ追加
		for (long id : idArray) {
			// レコード識別IDから対象DTOを取得
			ApprovalUnitDtoInterface dto = (ApprovalUnitDtoInterface)dao.findForKey(id, false);
			// 排他確認
			checkExclusive(dto);
			// 対象コードをリストへ追加
			list.add(dto.getUnitCode());
		}
		return list;
		
	}
	
	/**
	 * 履歴追加時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkAdd(ApprovalUnitDtoInterface dto) throws MospException {
		// 対象レコードの有効日が重複していないかを確認
		checkDuplicateAdd(dao.findForKey(dto.getUnitCode(), dto.getActivateDate()));
		// 無効フラグ確認
		if (isDtoActivate(dto)) {
			return;
		}
		// 履歴追加対象コードの履歴情報を取得
		List<ApprovalUnitDtoInterface> list = dao.findForHistory(dto.getUnitCode());
		// 生じる無効期間による履歴追加確認要否を取得
		if (needCheckTermForAdd(dto, list) == false) {
			// 無効期間は発生しない
			return;
		}
		// 確認するべき承認ルートユニットマスタリストを取得
		List<ApprovalRouteUnitDtoInterface> routeList = getApprovalRouteListForCheck(dto, list);
		// コード使用確認
		checkCodeIsUsed(dto.getUnitCode(), routeList);
		
	}
	
	/**
	 * 削除時の確認処理を行う。<br>
	 * 削除対象ユニット情報を設定している設定適用管理情報がないかの確認を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkDelete(ApprovalUnitDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getPfmApprovalUnitId());
		// 削除時の所属・職位での整合性確認
		checkMasterDelete(dto);
		// 削除元データの無効フラグ確認
		// 画面上の無効フラグは変更可能であるため確認しない。
		if (isDtoActivate(dao.findForKey(dto.getPfmApprovalUnitId(), true)) == false) {
			// 削除元データが無効であれば無効期間は発生しない
			return;
		}
		// 削除対象コードの履歴情報を取得
		List<ApprovalUnitDtoInterface> list = dao.findForHistory(dto.getUnitCode());
		// 生じる無効期間による削除確認要否を取得
		if (needCheckTermForDelete(dto, list) == false) {
			// 無効期間は発生しない
			return;
		}
		// 確認するべき承認ルートユニットマスタリストを取得
		List<ApprovalRouteUnitDtoInterface> routeUnitList = getApprovalRouteListForCheck(dto, list);
		// コード使用確認
		checkCodeIsUsed(dto.getUnitCode(), routeUnitList);
		
	}
	
	/**
	 * 削除時の所属・職位整合性を確認する。<br>
	 * 削除により一つ前の履歴が活きてくる場合、
	 * 影響が及ぶ期間に対して、その所属～勤務地の存在(有効)確認を行う。<br>
	 * @param dto 削除対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkMasterDelete(ApprovalUnitDtoInterface dto) throws MospException {
		// 一つ前の履歴取得
		ApprovalUnitDtoInterface beforeDto = unitReference.getApprovalUnitInfo(dto.getUnitCode(),
				DateUtility.addDay(dto.getActivateDate(), -1));
		// 一つ前の履歴が存在しないか無効である場合
		if (beforeDto == null || beforeDto.getInactivateFlag() == MospConst.INACTIVATE_FLAG_ON) {
			// 確認不要
			return;
		}
		// 対象コードの履歴を取得
		List<ApprovalUnitDtoInterface> list = unitReference.getApprovalUnitHistory(dto.getUnitCode());
		// 情報が影響を及ぼす期間を取得
		Date startDate = beforeDto.getActivateDate();
		Date endDate = getEffectiveLastDate(dto.getActivateDate(), list);
		// 所属存在確認
		masterCheck.isCheckSection(beforeDto.getApproverSectionCode(), startDate, endDate);
		// 職位存在確認
		masterCheck.isCheckPosition(beforeDto.getApproverPositionCode(), startDate, endDate);
		
	}
	
	/**
	 * 新規登録時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkInsert(ApprovalUnitDtoInterface dto) throws MospException {
		// 対象レコードの有効日が重複していないかを確認
		checkDuplicateInsert(dao.findForHistory(dto.getUnitCode()));
		
	}
	
	/**
	 * 履歴更新時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkUpdate(ApprovalUnitDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getPfmApprovalUnitId());
		// 無効フラグ確認
		if (isDtoActivate(dto)) {
			return;
		}
		// 更新元データの無効フラグ確認
		if (isDtoActivate(dao.findForKey(dto.getPfmApprovalUnitId(), true)) == false) {
			// 更新元データが更新前から無効であれば無効期間は発生しない
			return;
		}
		// 履歴追加対象コードの履歴情報を取得
		List<ApprovalUnitDtoInterface> list = dao.findForHistory(dto.getUnitCode());
		// 確認するべき承認ルートユニットマスタリストを取得
		List<ApprovalRouteUnitDtoInterface> routeUnitList = getApprovalRouteListForCheck(dto, list);
		// コード使用確認
		checkCodeIsUsed(dto.getUnitCode(), routeUnitList);
		
	}
	
	/**
	 * レコード識別IDを取得する。<br>
	 * @param dto 	対象DTO
	 * @return レコード識別ID
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected long getRecordID(ApprovalUnitDtoInterface dto) throws MospException {
		// ユニット情報を取得する。
		ApprovalUnitDtoInterface subDto = dao.findForKey(dto.getUnitCode(), dto.getActivateDate());
		// レコード識別IDを返す。
		return subDto.getPfmApprovalUnitId();
		
	}
	
	/**
	 * 承認ルートユニットマスタリスト内に対象コードが使用されている情報がないかの確認を行う。<br>
	 * @param code 対象コード
	 * @param list 承認ルートユニットマスタリスト
	 */
	protected void checkCodeIsUsed(String code, List<ApprovalRouteUnitDtoInterface> list) {
		// 同一のルートコードのメッセージは出力しない。
		String codeAdded = "";
		// 承認ルートユニットマスタリストの中身を確認
		for (ApprovalRouteUnitDtoInterface dto : list) {
			// 対象コード確認
			if ((code.equals(dto.getUnitCode())) && (isDtoActivate(dto))) {
				// 同一のルートコードのメッセージは出力しない。
				if (!codeAdded.equals(dto.getRouteCode())) {
					// エラーメッセージを設定
					PfMessageUtility.addErrorUnitIsUsed(mospParams, code, dto.getRouteCode());
					// メッセージに設定したルートコードを保持
					codeAdded = dto.getRouteCode();
				}
			}
		}
	}
	
	/**
	 * 確認すべき承認ルートユニットマスタリストを取得する。<br>
	 * 対象DTOの有効日以前で最新の承認ルートユニットマスタリストと
	 * 対象DTOの有効日～対象DTOの次の履歴の有効日に有効日が含まれる
	 * 承認ルートユニットマスタリストを合わせたリストを取得する。<br>
	 * 対象コード履歴リストは、有効日の昇順で並んでいるものとする。<br>
	 * 各種マスタ操作時に生じる無効期間におけるコード使用確認等で用いられる。<br>
	 * @param dto 	対象DTO
	 * @param list 対象コード履歴リスト
	 * @return 承認ルートマスタリスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected List<ApprovalRouteUnitDtoInterface> getApprovalRouteListForCheck(PlatformDtoInterface dto,
			List<? extends PlatformDtoInterface> list) throws MospException {
		// 承認ルートユニットマスタDAO取得
		ApprovalRouteUnitDaoInterface routeUnitDao = createDaoInstance(ApprovalRouteUnitDaoInterface.class);
		// 削除対象の有効日以前で最新の承認ルートユニットマスタ情報を取得
		List<ApprovalRouteUnitDtoInterface> routeUnitList = routeUnitDao.findForActivateDate(dto.getActivateDate());
		// 無効期間で承認ルートユニットマスタ履歴情報を取得(対象DTOの有効日～次の履歴の有効日)
		routeUnitList
			.addAll(routeUnitDao.findForTerm(dto.getActivateDate(), getNextActivateDate(dto.getActivateDate(), list)));
		return routeUnitList;
	}
	
}
