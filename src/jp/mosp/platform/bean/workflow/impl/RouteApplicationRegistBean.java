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
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.human.HumanReferenceBeanInterface;
import jp.mosp.platform.bean.system.PlatformMasterCheckBeanInterface;
import jp.mosp.platform.bean.workflow.RouteApplicationReferenceBeanInterface;
import jp.mosp.platform.bean.workflow.RouteApplicationRegistBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dao.workflow.RouteApplicationDaoInterface;
import jp.mosp.platform.dto.workflow.RouteApplicationDtoInterface;
import jp.mosp.platform.dto.workflow.impl.PfmRouteApplicationDto;
import jp.mosp.platform.utils.PfMessageUtility;

/**
 * ルート適用マスタ登録処理。<br>
 */
public class RouteApplicationRegistBean extends PlatformBean implements RouteApplicationRegistBeanInterface {
	
	/**
	 * ルート適用マスタDAO。<br>
	 */
	protected RouteApplicationDaoInterface				dao;
	
	/**
	 * 所属・雇用契約・職位・勤務地マスタに関連する整合性確認処理。<br>
	 */
	protected PlatformMasterCheckBeanInterface			masterCheck;
	
	/**
	 * 承認ユニットマスタ参照処理。<br>
	 */
	protected RouteApplicationReferenceBeanInterface	routeApplicationReference;
	
	/**
	 * 人事マスタ参照処理。<br>
	 */
	protected HumanReferenceBeanInterface				humanRefer;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public RouteApplicationRegistBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// DAOを準備
		dao = createDaoInstance(RouteApplicationDaoInterface.class);
		// Beanを準備
		masterCheck = createBeanInstance(PlatformMasterCheckBeanInterface.class);
		routeApplicationReference = createBeanInstance(RouteApplicationReferenceBeanInterface.class);
		humanRefer = createBeanInstance(HumanReferenceBeanInterface.class);
		
	}
	
	@Override
	public RouteApplicationDtoInterface getInitDto() {
		return new PfmRouteApplicationDto();
	}
	
	@Override
	public void add(RouteApplicationDtoInterface dto) throws MospException {
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
		dto.setPfmRouteApplicationId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void delete(RouteApplicationDtoInterface dto) throws MospException {
		// レコード識別IDを取得。
		dto.setPfmRouteApplicationId(getRecordID(dto));
		// 削除対象ユニット情報が使用されていないかを確認
		checkDelete(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 論理削除
		logicalDelete(dao, dto.getPfmRouteApplicationId());
	}
	
	@Override
	public void insert(RouteApplicationDtoInterface dto) throws MospException {
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
		dto.setPfmRouteApplicationId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void update(RouteApplicationDtoInterface dto) throws MospException {
		// レコード識別IDを取得。
		dto.setPfmRouteApplicationId(getRecordID(dto));
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
		logicalDelete(dao, dto.getPfmRouteApplicationId());
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setPfmRouteApplicationId(dao.nextRecordId());
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
			// 対象ルート適用情報における有効日の情報を取得
			RouteApplicationDtoInterface dto = dao.findForKey(code, activateDate);
			// 存在確認(存在しなければ履歴追加、存在すれば履歴更新)
			if (dto == null) {
				// 対象ルート適用情報における有効日以前で最新の情報を取得
				dto = dao.findForInfo(code, activateDate);
				// 対象ルート適用情報確認
				if (dto == null) {
					// エラーメッセージを設定
					PfMessageUtility.addErrorCodeNotExistBeforeDate(mospParams, code);
					continue;
				}
				// DTOに有効日、無効フラグを設定
				dto.setActivateDate(activateDate);
				dto.setInactivateFlag(inactivateFlag);
				// DTOの妥当性確認
				validate(dto);
				// 履歴追加情報の検証
				checkAdd(dto);
				if (mospParams.hasErrorMessage()) {
					// エラーが存在したら履歴追加処理をしない
					continue;
				}
				// レコード識別ID最大値をインクリメントしてDTOに設定
				dto.setPfmRouteApplicationId(dao.nextRecordId());
				// 登録処理
				dao.insert(dto);
			} else {
				// DTOに無効フラグを設定
				dto.setInactivateFlag(inactivateFlag);
				// DTOの妥当性確認
				validate(dto);
				// 履歴更新情報の検証
				checkUpdate(dto);
				if (mospParams.hasErrorMessage()) {
					// エラーが存在したら履歴更新処理をしない
					continue;
				}
				// 論理削除
				logicalDelete(dao, dto.getPfmRouteApplicationId());
				// レコード識別ID最大値をインクリメントしてDTOに設定
				dto.setPfmRouteApplicationId(dao.nextRecordId());
				// 登録処理
				dao.insert(dto);
			}
		}
	}
	
	/**
	 * ルート適用コードリストを取得する。<br>
	 * 同時に排他確認を行う。<br>
	 * @param idArray レコード識別ID配列
	 * @return ルート適用コードリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected List<String> getCodeList(long[] idArray) throws MospException {
		// リスト準備
		List<String> list = new ArrayList<String>();
		// レコード識別IDからDTOを取得し、コードをリストへ追加
		for (long id : idArray) {
			// レコード識別IDから対象DTOを取得
			RouteApplicationDtoInterface dto = (RouteApplicationDtoInterface)dao.findForKey(id, false);
			// 排他確認
			checkExclusive(dto);
			// 対象コードをリストへ追加
			list.add(dto.getRouteApplicationCode());
		}
		return list;
		
	}
	
	/**
	 * 履歴追加時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	private void checkAdd(RouteApplicationDtoInterface dto) throws MospException {
		// 対象レコードの有効日が重複していないかを確認
		checkDuplicateAdd(dao.findForKey(dto.getRouteApplicationCode(), dto.getActivateDate()));
		// 設定の重複を確認
		checkSettingDuplicate(dto, false);
		// 無効フラグ確認
		if (isDtoActivate(dto)) {
			return;
		}
		// 履歴追加対象コードの履歴情報を取得
		List<RouteApplicationDtoInterface> list = dao.findForHistory(dto.getRouteApplicationCode());
		// 生じる無効期間による履歴追加確認要否を取得
		if (needCheckTermForAdd(dto, list) == false) {
			// 無効期間は発生しない
			return;
		}
	}
	
	/**
	 * 登録情報の妥当性を確認する。
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	private void validate(RouteApplicationDtoInterface dto) throws MospException {
		// 個人IDの重複を修正
		revisionPersonalIds(dto);
		// 無効の場合
		if (dto.getInactivateFlag() == MospConst.INACTIVATE_FLAG_ON) {
			// 確認なし
			return;
		}
		// 履歴一覧取得
		List<RouteApplicationDtoInterface> list = routeApplicationReference
			.getRouteApplicationHistory(dto.getRouteApplicationCode());
		// 確認期間開始日取得
		Date startDate = dto.getActivateDate();
		Date endDate = getEffectiveLastDate(dto.getActivateDate(), list);
		// 所属存在確認
		masterCheck.isCheckSection(dto.getSectionCode(), startDate, endDate);
		// 職位存在確認
		masterCheck.isCheckPosition(dto.getPositionCode(), startDate, endDate);
		// 雇用契約確認
		masterCheck.isCheckEmploymentContract(dto.getEmploymentContractCode(), startDate, endDate);
		// 勤務地
		masterCheck.isCheckWorkPlace(dto.getWorkPlaceCode(), startDate, endDate);
	}
	
	/**
	 * 個人IDの重複を削除する。<br>
	 * @param dto 対象設定適用情報
	 */
	protected void revisionPersonalIds(RouteApplicationDtoInterface dto) {
		// マスタ組み合わせの場合
		if (dto.getRouteApplicationType() == Integer.parseInt(PlatformConst.APPLICATION_TYPE_MASTER)) {
			// 処理なし
			return;
		}
		// 個人ID再設定
		dto.setPersonalIds(overlapValue(dto.getPersonalIds(), SEPARATOR_DATA));
	}
	
	/**
	 * 削除時の確認処理を行う。<br>
	 * 削除対象ユニット情報を設定している設定適用管理情報がないかの確認を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	private void checkDelete(RouteApplicationDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getPfmRouteApplicationId());
		// 削除時のマスタ整合性確認
		checkMasterDelete(dto);
		// 設定の重複を確認
		checkSettingDuplicate(dto, true);
		// 削除元データの無効フラグ確認
		// 画面上の無効フラグは変更可能であるため確認しない。
		if (isDtoActivate(dao.findForKey(dto.getPfmRouteApplicationId(), true)) == false) {
			// 削除元データが無効であれば無効期間は発生しない
			return;
		}
		// 削除対象コードの履歴情報を取得
		List<RouteApplicationDtoInterface> list = dao.findForHistory(dto.getRouteApplicationCode());
		// 生じる無効期間による削除確認要否を取得
		if (needCheckTermForDelete(dto, list) == false) {
			// 無効期間は発生しない
			return;
		}
	}
	
	/**
	 * 削除時のマスタ整合性を確認する。<br>
	 * 削除により一つ前の履歴が活きてくる場合、
	 * 影響が及ぶ期間に対して、その所属～勤務地の存在(有効)確認を行う。<br>
	 * @param dto 削除対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	private void checkMasterDelete(RouteApplicationDtoInterface dto) throws MospException {
		// 一つ前の履歴取得
		RouteApplicationDtoInterface beforeDto = routeApplicationReference
			.getRouteApplicationInfo(dto.getRouteApplicationCode(), DateUtility.addDay(dto.getActivateDate(), -1));
		// 一つ前の履歴が存在しないか無効である場合
		if (beforeDto == null || beforeDto.getInactivateFlag() == MospConst.INACTIVATE_FLAG_ON) {
			// 確認不要
			return;
		}
		// 対象コードの履歴を取得
		List<RouteApplicationDtoInterface> list = routeApplicationReference
			.getRouteApplicationHistory(dto.getRouteApplicationCode());
		// 情報が影響を及ぼす期間を取得
		Date startDate = beforeDto.getActivateDate();
		Date endDate = getEffectiveLastDate(dto.getActivateDate(), list);
		// 所属存在確認
		masterCheck.isCheckSection(beforeDto.getSectionCode(), startDate, endDate);
		// 職位存在確認
		masterCheck.isCheckPosition(beforeDto.getPositionCode(), startDate, endDate);
		// 雇用契約存在確認
		masterCheck.isCheckEmploymentContract(beforeDto.getEmploymentContractCode(), startDate, endDate);
		// 勤務地存在確認
		masterCheck.isCheckWorkPlace(beforeDto.getWorkPlaceCode(), startDate, endDate);
	}
	
	/**
	 * 新規登録時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	private void checkInsert(RouteApplicationDtoInterface dto) throws MospException {
		// 対象レコードの有効日が重複していないかを確認
		checkDuplicateInsert(dao.findForHistory(dto.getRouteApplicationCode()));
		// 設定の重複を確認
		checkSettingDuplicate(dto, false);
		
	}
	
	/**
	 * 履歴更新時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	private void checkUpdate(RouteApplicationDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getPfmRouteApplicationId());
		// 設定の重複を確認
		checkSettingDuplicate(dto, false);
		// 無効フラグ確認
		if (isDtoActivate(dto)) {
			return;
		}
		// 更新元データの無効フラグ確認
		if (isDtoActivate(dao.findForKey(dto.getPfmRouteApplicationId(), true)) == false) {
			// 更新元データが更新前から無効であれば無効期間は発生しない
			return;
		}
	}
	
	/**
	 * レコード識別IDを取得する。<br>
	 * @param dto 	対象DTO
	 * @return レコード識別ID
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	private long getRecordID(RouteApplicationDtoInterface dto) throws MospException {
		// ユニット情報を取得する。
		RouteApplicationDtoInterface subDto = dao.findForKey(dto.getRouteApplicationCode(), dto.getActivateDate());
		// レコード識別IDを返す。
		return subDto.getPfmRouteApplicationId();
		
	}
	
	/**
	 * 設定の重複を確認する。<br>
	 * 削除時確認の場合は、確認対象が対象DTOの一つ前の情報になる。<br>
	 * 設定適用区分を確認し、それぞれの確認を行う。<br>
	 * @param dto           対象DTO
	 * @param isCheckDelete 削除時確認処理フラグ
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkSettingDuplicate(RouteApplicationDtoInterface dto, boolean isCheckDelete) throws MospException {
		// 重複確認範囲終了日準備
		Date endDate = null;
		// 確認対象一つ後の情報を取得
		RouteApplicationDtoInterface latterDto = dao.findLatterInfo(dto.getRouteApplicationCode(),
				dto.getActivateDate());
		// 重複確認範囲終了日設定
		if (latterDto != null) {
			endDate = latterDto.getActivateDate();
		}
		// 確認対象DTO準備
		RouteApplicationDtoInterface targetDto = dto;
		// 削除時確認の場合
		if (isCheckDelete) {
			// 確認対象が対象DTOの一つ前になる
			targetDto = dao.findFormerInfo(dto.getRouteApplicationCode(), dto.getActivateDate());
		}
		// 確認対象DTOが存在しないか無効である場合
		if (targetDto == null || isDtoActivate(targetDto) == false) {
			// 重複確認不要
			return;
		}
		// 設定適用区分確認
		if (targetDto.getRouteApplicationType() == Integer.parseInt(PlatformConst.APPLICATION_TYPE_MASTER)) {
			// マスタ組合による適用範囲の重複を確認
			checkMasterDuplicate(targetDto, endDate);
		} else {
			// 個人ID毎に確認
			for (String personalId : asList(targetDto.getPersonalIds(), SEPARATOR_DATA)) {
				// 個人設定の重複を確認
				checkPersonDuplicate(targetDto.getRouteApplicationCode(), targetDto.getActivateDate(), personalId,
						endDate, targetDto.getWorkflowType());
			}
		}
	}
	
	/**
	 * マスタ組合せによる適用範囲の重複を確認する。<br>
	 * 有効日以前で最新の情報が存在して設定適用コードと異なる場合に、エラーメッセージを設定する。<br>
	 * また、重複確認範囲に有効な自コード以外の情報が存在する場合に、エラーメッセージを設定する。<br>
	 * 重複確認範囲は、対象DTOの有効日から重複確認範囲終了日
	 * @param dto     対象DTO
	 * @param endDate 重複確認範囲終了日
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkMasterDuplicate(RouteApplicationDtoInterface dto, Date endDate) throws MospException {
		// 有効日以前で最新の情報を取得
		RouteApplicationDtoInterface currentDto = dao.findForMaster(dto.getActivateDate(), dto.getWorkflowType(),
				dto.getWorkPlaceCode(), dto.getEmploymentContractCode(), dto.getSectionCode(), dto.getPositionCode());
		// 情報が存在し設定適用コードが異なる場合
		if (currentDto != null && dto.getRouteApplicationCode().equals(currentDto.getRouteApplicationCode()) == false) {
			// エラーメッセージを設定
			PfMessageUtility.addErrorApplicationDuplicate(mospParams, currentDto.getRouteApplicationCode());
			return;
		}
		// 有効日より後で有効な情報を取得
		List<RouteApplicationDtoInterface> list = dao.findMasterDuplicated(dto.getActivateDate(), endDate,
				dto.getWorkPlaceCode(), dto.getEmploymentContractCode(), dto.getSectionCode(), dto.getPositionCode(),
				dto.getWorkflowType());
		// 自コード以外の情報が存在するかを確認
		for (RouteApplicationDtoInterface targetDto : list) {
			if (dto.getRouteApplicationCode().equals(targetDto.getRouteApplicationCode()) == false) {
				// エラーメッセージを設定
				PfMessageUtility.addErrorApplicationDuplicate(mospParams, targetDto.getRouteApplicationCode());
				return;
			}
		}
	}
	
	/**
	 * 個人設定の重複を確認する。<br>
	 * 有効日以前で最新の情報が存在して設定適用コードと異なる場合に、エラーメッセージを設定する。<br>
	 * また、重複確認範囲に有効な自コード以外の情報が存在する場合に、エラーメッセージを設定する。<br>
	 * @param applicationCode 設定適用コード
	 * @param activateDate    有効日(重複確認範囲開始日)
	 * @param personalId      個人ID
	 * @param endDate         重複確認範囲終了日
	 * @param workflowType    フロー区分
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkPersonDuplicate(String applicationCode, Date activateDate, String personalId, Date endDate,
			int workflowType) throws MospException {
		// 有効日以前で最新の情報を取得
		RouteApplicationDtoInterface currentDto = dao.findForPersonalId(personalId, activateDate, workflowType);
		// 情報が存在し設定適用コードが異なる場合
		if (currentDto != null && applicationCode.equals(currentDto.getRouteApplicationCode()) == false) {
			// メッセージ設定
			addPersonDuplicateMessage(currentDto.getRouteApplicationCode(), currentDto.getActivateDate(), personalId);
			return;
		}
		// 有効日より後で有効な情報を取得
		List<RouteApplicationDtoInterface> list = dao.findPersonDuplicated(activateDate, endDate, personalId,
				workflowType);
		// 自コード以外の情報が存在するかを確認
		for (RouteApplicationDtoInterface latterDto : list) {
			if (applicationCode.equals(latterDto.getRouteApplicationCode()) == false) {
				// メッセージ設定
				addPersonDuplicateMessage(latterDto.getRouteApplicationCode(), latterDto.getActivateDate(), personalId);
				return;
			}
		}
	}
	
	/**
	 * 個人設定が重複する際のエラーメッセージを設定する。<br>
	 * @param applicationCode 設定適用コード
	 * @param activateDate    有効日
	 * @param personalId      個人ID
	 * @throws MospException 人事情報の取得に失敗した場合
	 */
	protected void addPersonDuplicateMessage(String applicationCode, Date activateDate, String personalId)
			throws MospException {
		// 社員コード取得
		String employeeCode = humanRefer.getEmployeeCode(personalId, activateDate);
		// エラーメッセージを設定
		PfMessageUtility.addErrorEmployeeDuplicate(mospParams, employeeCode, applicationCode);
	}
	
}
