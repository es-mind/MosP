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
import jp.mosp.platform.bean.human.HumanReferenceBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.time.bean.ApplicationRegistBeanInterface;
import jp.mosp.time.dao.settings.ApplicationDaoInterface;
import jp.mosp.time.dto.settings.ApplicationDtoInterface;
import jp.mosp.time.dto.settings.impl.TmmApplicationDto;

/**
 * 設定適用管理登録クラス。
 */
public class ApplicationRegistBean extends PlatformBean implements ApplicationRegistBeanInterface {
	
	/**
	 * 設定適用マスタDAOクラス。<br>
	 */
	ApplicationDaoInterface		dao;
	
	/**
	 * 人事マスタ参照クラス。<br>
	 */
	HumanReferenceBeanInterface	humanRefer;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public ApplicationRegistBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// DAOを準備
		dao = createDaoInstance(ApplicationDaoInterface.class);
		// Beanを準備
		humanRefer = createBeanInstance(HumanReferenceBeanInterface.class);
	}
	
	@Override
	public ApplicationDtoInterface getInitDto() {
		return new TmmApplicationDto();
	}
	
	@Override
	public void insert(ApplicationDtoInterface dto) throws MospException {
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
		dto.setTmmApplicationId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void add(ApplicationDtoInterface dto) throws MospException {
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
		dto.setTmmApplicationId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void update(ApplicationDtoInterface dto) throws MospException {
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
		logicalDelete(dao, dto.getTmmApplicationId());
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setTmmApplicationId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void delete(ApplicationDtoInterface dto) throws MospException {
		// 削除情報の検証
		checkDelete(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 論理削除
		logicalDelete(dao, dto.getTmmApplicationId());
	}
	
	@Override
	public void update(long[] idArray, Date activateDate, int inactivateFlag) {
		// 一括処理は実装見送り
	}
	
	@Override
	public void delete(long[] idArray) {
		// 一括処理は実装見送り
	}
	
	/**
	 * 設定適用コードリストを取得する。<br>
	 * 同時に排他確認を行う。<br>
	 * @param idArray レコード識別ID配列
	 * @return 設定適用コードリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected List<String> getCodeList(long[] idArray) throws MospException {
		// リスト準備
		List<String> list = new ArrayList<String>();
		// レコード識別IDからDTOを取得し、コードをリストへ追加
		for (long id : idArray) {
			// レコード識別IDから対象DTOを取得
			ApplicationDtoInterface dto = (ApplicationDtoInterface)dao.findForKey(id, false);
			// 排他確認
			checkExclusive(dto);
			// 対象コードをリストへ追加
			list.add(dto.getApplicationCode());
		}
		return list;
	}
	
	/**
	 * 新規登録時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkInsert(ApplicationDtoInterface dto) throws MospException {
		// 対象レコードの有効日が重複していないかを確認
		checkDuplicateInsert(dao.findForHistory(dto.getApplicationCode()));
		// 設定の重複を確認
		checkSettingDuplicate(dto, false);
	}
	
	/**
	 * 履歴追加時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkAdd(ApplicationDtoInterface dto) throws MospException {
		// 対象レコードの有効日が重複していないかを確認
		checkDuplicateAdd(dao.findForKey(dto.getApplicationCode(), dto.getActivateDate()));
		// 設定の重複を確認
		checkSettingDuplicate(dto, false);
	}
	
	/**
	 * 履歴更新時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkUpdate(ApplicationDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getTmmApplicationId());
		// 設定の重複を確認
		checkSettingDuplicate(dto, false);
	}
	
	/**
	 * 削除時の確認処理を行う。<br>
	 * 排他確認を行う。<br>
	 * 削除対象一つ前の情報が有効であれば、その情報に対する重複確認を行う。<br>
	 * 重複確認範囲は、削除対象一つ前の有効日から削除対象一つ後の有効日。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkDelete(ApplicationDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getTmmApplicationId());
		// 設定の重複を確認
		checkSettingDuplicate(dto, true);
	}
	
	/**
	 * 登録情報の妥当性を確認する。
	 * @param dto 対象DTO
	 */
	protected void validate(ApplicationDtoInterface dto) {
		// 個人IDの重複を削除
		revisionPersonalIds(dto);
	}
	
	/**
	 * 個人IDの重複を削除する。<br>
	 * @param dto 対象設定適用情報
	 */
	protected void revisionPersonalIds(ApplicationDtoInterface dto) {
		// マスタ組み合わせの場合
		if (dto.getApplicationType() == Integer.parseInt(PlatformConst.APPLICATION_TYPE_MASTER)) {
			// 処理なし
			return;
		}
		// 個人ID再設定
		dto.setPersonalIds(overlapValue(dto.getPersonalIds(), SEPARATOR_DATA));
	}
	
	/**
	 * 設定の重複を確認する。<br>
	 * 削除時確認の場合は、確認対象が対象DTOの一つ前の情報になる。<br>
	 * 設定適用区分を確認し、それぞれの確認を行う。<br>
	 * @param dto           対象DTO
	 * @param isCheckDelete 削除時確認処理フラグ
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkSettingDuplicate(ApplicationDtoInterface dto, boolean isCheckDelete) throws MospException {
		// 重複確認範囲終了日準備
		Date endDate = null;
		// 確認対象一つ後の情報を取得
		ApplicationDtoInterface latterDto = dao.findLatterInfo(dto.getApplicationCode(), dto.getActivateDate());
		// 重複確認範囲終了日設定
		if (latterDto != null) {
			endDate = latterDto.getActivateDate();
		}
		// 確認対象DTO準備
		ApplicationDtoInterface targetDto = dto;
		// 削除時確認の場合
		if (isCheckDelete) {
			// 確認対象が対象DTOの一つ前になる
			targetDto = dao.findFormerInfo(dto.getApplicationCode(), dto.getActivateDate());
		}
		// 確認対象DTOが存在しないか無効である場合
		if (targetDto == null || isDtoActivate(targetDto) == false) {
			// 重複確認不要
			return;
		}
		// 設定適用区分確認
		if (targetDto.getApplicationType() == Integer.parseInt(PlatformConst.APPLICATION_TYPE_MASTER)) {
			// マスタ組合による適用範囲の重複を確認
			checkMasterDuplicate(targetDto, endDate);
		} else {
			// 個人ID毎に確認
			for (String personalId : asList(targetDto.getPersonalIds(), SEPARATOR_DATA)) {
				// 個人設定の重複を確認
				checkPersonDuplicate(targetDto.getApplicationCode(), targetDto.getActivateDate(), personalId, endDate);
			}
		}
	}
	
	/**
	 * マスタ組合による適用範囲の重複を確認する。<br>
	 * 有効日以前で最新の情報が存在して設定適用コードと異なる場合に、エラーメッセージを設定する。<br>
	 * また、重複確認範囲に有効な自コード以外の情報が存在する場合に、エラーメッセージを設定する。<br>
	 * 重複確認範囲は、対象DTOの有効日から重複確認範囲終了日
	 * @param dto     対象DTO
	 * @param endDate 重複確認範囲終了日
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkMasterDuplicate(ApplicationDtoInterface dto, Date endDate) throws MospException {
		// 有効日以前で最新の情報を取得
		ApplicationDtoInterface currentDto = dao.findForMaster(dto.getActivateDate(), dto.getWorkPlaceCode(),
				dto.getEmploymentContractCode(), dto.getSectionCode(), dto.getPositionCode());
		// 情報が存在し設定適用コードが異なる場合
		if (currentDto != null && dto.getApplicationCode().equals(currentDto.getApplicationCode()) == false) {
			// エラーメッセージを設定
			PfMessageUtility.addErrorApplicationDuplicate(mospParams, currentDto.getApplicationCode());
			return;
		}
		// 有効日より後で有効な情報を取得
		List<ApplicationDtoInterface> list = dao.findMasterDuplicated(dto.getActivateDate(), endDate,
				dto.getWorkPlaceCode(), dto.getEmploymentContractCode(), dto.getSectionCode(), dto.getPositionCode());
		// 自コード以外の情報が存在するかを確認
		for (ApplicationDtoInterface targetDto : list) {
			if (dto.getApplicationCode().equals(targetDto.getApplicationCode()) == false) {
				// エラーメッセージを設定
				PfMessageUtility.addErrorApplicationDuplicate(mospParams, targetDto.getApplicationCode());
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
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkPersonDuplicate(String applicationCode, Date activateDate, String personalId, Date endDate)
			throws MospException {
		// 有効日以前で最新の情報を取得
		ApplicationDtoInterface currentDto = dao.findForPersonalId(activateDate, personalId);
		// 情報が存在し設定適用コードが異なる場合
		if (currentDto != null && applicationCode.equals(currentDto.getApplicationCode()) == false) {
			// メッセージ設定
			addPersonDuplicateMessage(currentDto.getApplicationCode(), currentDto.getActivateDate(), personalId);
			return;
		}
		// 有効日より後で有効な情報を取得
		List<ApplicationDtoInterface> list = dao.findPersonDuplicated(activateDate, endDate, personalId);
		// 自コード以外の情報が存在するかを確認
		for (ApplicationDtoInterface latterDto : list) {
			if (applicationCode.equals(latterDto.getApplicationCode()) == false) {
				// メッセージ設定
				addPersonDuplicateMessage(latterDto.getApplicationCode(), latterDto.getActivateDate(), personalId);
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
		// 社員コードを取得
		String employeeCode = humanRefer.getEmployeeCode(personalId, activateDate);
		// エラーメッセージを設定
		PfMessageUtility.addErrorEmployeeDuplicate(mospParams, employeeCode, applicationCode);
	}
	
}
