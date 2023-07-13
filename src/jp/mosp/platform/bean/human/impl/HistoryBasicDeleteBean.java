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
package jp.mosp.platform.bean.human.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.platform.bean.human.ConcurrentReferenceBeanInterface;
import jp.mosp.platform.bean.human.ConcurrentRegistBeanInterface;
import jp.mosp.platform.bean.human.EntranceReferenceBeanInterface;
import jp.mosp.platform.bean.human.EntranceRegistBeanInterface;
import jp.mosp.platform.bean.human.ExtraHumanDeleteBeanInterface;
import jp.mosp.platform.bean.human.HistoryBasicDeleteBeanInterface;
import jp.mosp.platform.bean.human.HumanHistoryReferenceBeanInterface;
import jp.mosp.platform.bean.human.HumanHistoryRegistBeanInterface;
import jp.mosp.platform.bean.human.HumanReferenceBeanInterface;
import jp.mosp.platform.bean.human.HumanRegistBeanInterface;
import jp.mosp.platform.bean.human.RetirementReferenceBeanInterface;
import jp.mosp.platform.bean.human.RetirementRegistBeanInterface;
import jp.mosp.platform.bean.human.SuspensionReferenceBeanInterface;
import jp.mosp.platform.bean.human.SuspensionRegistBeanInterface;
import jp.mosp.platform.bean.system.UserAccountRegistBeanInterface;
import jp.mosp.platform.bean.system.UserMasterReferenceBeanInterface;
import jp.mosp.platform.bean.workflow.ApprovalUnitReferenceBeanInterface;
import jp.mosp.platform.bean.workflow.RouteApplicationReferenceBeanInterface;
import jp.mosp.platform.bean.workflow.SubApproverReferenceBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dto.human.ConcurrentDtoInterface;
import jp.mosp.platform.dto.human.EntranceDtoInterface;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.dto.human.HumanHistoryDtoInterface;
import jp.mosp.platform.dto.human.RetirementDtoInterface;
import jp.mosp.platform.dto.human.SuspensionDtoInterface;
import jp.mosp.platform.dto.system.UserMasterDtoInterface;
import jp.mosp.platform.human.utils.HumanUtility;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.platform.utils.PfNameUtility;

/**
 * 人事基本情報履歴削除クラス。
 */
public class HistoryBasicDeleteBean extends HumanRegistBean implements HistoryBasicDeleteBeanInterface {
	
	/**
	 * MosPアプリケーション設定キー(人事基本情報削除追加クラス群)。
	 */
	protected static final String					APP_KEY_BASIC_DELETE_BEANS	= "HistoryBasicDeleteBeans";
	
	/**
	 * ユーザアカウント情報登録処理。
	 */
	private UserAccountRegistBeanInterface			userAccountRegist;
	/**
	 * 人事マスタ登録クラス。
	 */
	private HumanRegistBeanInterface				humanRegist;
	/**
	 * 人事汎用履歴情報参照クラス。
	 */
	private HumanHistoryRegistBeanInterface			humanHistoryRegist;
	/**
	 * 人事入社情報登録クラス。
	 */
	private EntranceRegistBeanInterface				entranceRegist;
	/**
	 * 人事退職情報登録クラス。
	 */
	private RetirementRegistBeanInterface			retirementRegist;
	/**
	 * 人事休職情報登録クラス。
	 */
	private SuspensionRegistBeanInterface			suspensionRegist;
	/**
	 * 人事兼務情報登録クラス。
	 */
	private ConcurrentRegistBeanInterface			concurrentRegist;
	/**
	 * ユーザマスタ参照クラス。
	 */
	private UserMasterReferenceBeanInterface		userMasterReference;
	/**
	 * 人事マスタ参照クラス。
	 */
	private HumanReferenceBeanInterface				humanReference;
	/**
	 * 人事汎用履歴情報参照クラス。
	 */
	private HumanHistoryReferenceBeanInterface		humanHistoryReference;
	/**
	 * 人事入社情報参照クラス。
	 */
	private EntranceReferenceBeanInterface			entranceReference;
	/**
	 * 人事休職情報参照クラス。
	 */
	private SuspensionReferenceBeanInterface		suspensionReference;
	/**
	 * 人事退職情報参照クラス。
	 */
	private RetirementReferenceBeanInterface		retirementReference;
	/**
	 * 人事退職情報参照クラス。
	 */
	private ConcurrentReferenceBeanInterface		concurrentReference;
	/**
	 * 承認ユニットマスタ参照クラス。
	 */
	private ApprovalUnitReferenceBeanInterface		approvalUnitReference;
	/**
	 * ルート適用マスタ参照クラス。
	 */
	private RouteApplicationReferenceBeanInterface	routeApplicationReference;
	/**
	 * 代理承認者テーブル参照クラス。
	 */
	private SubApproverReferenceBeanInterface		subApproverReference;
	
	
	/**
	 * {@link HumanRegistBean#HumanRegistBean()}を実行する。<br>
	 */
	public HistoryBasicDeleteBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// 継承基の処理を実行
		super.initBean();
		// Beanを準備
		userAccountRegist = createBeanInstance(UserAccountRegistBeanInterface.class);
		humanRegist = createBeanInstance(HumanRegistBeanInterface.class);
		humanHistoryRegist = createBeanInstance(HumanHistoryRegistBeanInterface.class);
		entranceRegist = createBeanInstance(EntranceRegistBeanInterface.class);
		retirementRegist = createBeanInstance(RetirementRegistBeanInterface.class);
		suspensionRegist = createBeanInstance(SuspensionRegistBeanInterface.class);
		concurrentRegist = createBeanInstance(ConcurrentRegistBeanInterface.class);
		userMasterReference = createBeanInstance(UserMasterReferenceBeanInterface.class);
		humanReference = createBeanInstance(HumanReferenceBeanInterface.class);
		humanHistoryReference = createBeanInstance(HumanHistoryReferenceBeanInterface.class);
		entranceReference = createBeanInstance(EntranceReferenceBeanInterface.class);
		suspensionReference = createBeanInstance(SuspensionReferenceBeanInterface.class);
		retirementReference = createBeanInstance(RetirementReferenceBeanInterface.class);
		concurrentReference = createBeanInstance(ConcurrentReferenceBeanInterface.class);
		approvalUnitReference = createBeanInstance(ApprovalUnitReferenceBeanInterface.class);
		routeApplicationReference = createBeanInstance(RouteApplicationReferenceBeanInterface.class);
		subApproverReference = createBeanInstance(SubApproverReferenceBeanInterface.class);
	}
	
	@Override
	public void delete(long pfmHumanId, boolean isAllDelete) throws MospException {
		// 設定ファイルに記載された追加クラスのインスタンスを生成
		List<ExtraHumanDeleteBeanInterface> extraList = getExtraHumanDelete();
		// 削除対象DTO取得
		HumanDtoInterface dto = humanReference.findForKey(pfmHumanId);
		// 排他確認
		checkExclusive(dto);
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 削除可否確認
		checkDelete(extraList, dto, isAllDelete);
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 削除処理
		humanDelete(extraList, dto, isAllDelete);
	}
	
	/**
	 * 設定ファイルに記載された追加クラスのインスタンスを生成する。
	 * @return Beanのリスト
	 * @throws MospException インスタンスの生成に失敗した場合
	 */
	protected List<ExtraHumanDeleteBeanInterface> getExtraHumanDelete() throws MospException {
		// Beanのリストを準備する
		List<ExtraHumanDeleteBeanInterface> extraList = new ArrayList<ExtraHumanDeleteBeanInterface>();
		// 設定ファイルから人事基本履歴削除クラス名群を取得
		String[] extraBeans = mospParams.getApplicationProperties(APP_KEY_BASIC_DELETE_BEANS);
		// 人事基本情報クラス名毎に処理
		for (String className : extraBeans) {
			// インスタンスの生成
			ExtraHumanDeleteBeanInterface extraHumanDeleteBean = (ExtraHumanDeleteBeanInterface)createBean(className);
			// リストに追加
			extraList.add(extraHumanDeleteBean);
		}
		return extraList;
	}
	
	/**
	 * 人事マスタの履歴削除時の整合性チェックを行う。
	 * 人事履歴一覧情報を取得し、削除ケース毎に処理。
	 * @param extraList Beanのリスト
	 * @param deleateHumanDto 削除対象人事情報
	 * @param isAllDelete 対象社員情報全削除フラグ
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkDelete(List<ExtraHumanDeleteBeanInterface> extraList, HumanDtoInterface deleateHumanDto,
			boolean isAllDelete) throws MospException {
		// 削除対象個人履歴リスト取得
		List<HumanDtoInterface> list = humanReference.getHistory(deleateHumanDto.getPersonalId());
		// 削除対象履歴のリストのインデックス取得
		int deleateIndex = HumanUtility.getHumanTargetIndex(deleateHumanDto, list);
		// リストサイズ取得
		int listSize = list.size();
		// 対象社員情報全削除フラグを確認
		if ((listSize == 1 && isAllDelete == false) || (listSize > 1 && isAllDelete)) {
			// エラーメッセージを設定
			PfMessageUtility.addErrorExclusive(mospParams);
			return;
		}
		// 削除ケースを取得
		int deleteCase = getDeleteCase(list, deleateIndex);
		switch (deleteCase) {
			case CASE_ONLY:
				// 履歴が1件だけの場合
				// ルート適用・ユニットが存在確認
				oneDelete(deleateHumanDto);
				break;
			case CASE_OLDEST:
				// 消去対象がリストの中で一番古い履歴の場合
				// ルート適用・ユニット・アカウント情報確認
				oldDelete(deleateIndex, list);
				break;
			case CASE_BETWEEN:
				// 前後の履歴が存在する場合
				// 所属・勤務地・職位・雇用確認
				betweenDelete(deleateIndex, list);
				break;
			case CASE_NEWEST:
				// 消去対象有効日が履歴一覧の中で一番最新の場合
				// 所属・勤務地・職位・雇用確認
				newDelete(deleateIndex, list);
				break;
			default:
				break;
		}
		// 追加クラス毎に処理
		for (ExtraHumanDeleteBeanInterface extraBean : extraList) {
			// 追加クラスで確認するべきメソッドの追加
			extraBean.checkDelete(list, deleateIndex, deleteCase);
		}
	}
	
	/**
	 * 削除ケースを取得する。
	 * @param list 削除対象履歴一覧
	 * @param deleateIndex 削除対象インデックス
	 * @return 削除ケース
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 * 
	 */
	protected int getDeleteCase(List<HumanDtoInterface> list, int deleateIndex) throws MospException {
		int listSize = list.size();
		// 履歴が1件だけの場合
		if (listSize == 1) {
			return CASE_ONLY;
		}
		// 消去対象がリストの中で一番古い履歴の場合
		if (deleateIndex == 0) {
			return CASE_OLDEST;
		}
		// 前後の履歴が存在する場合
		if (deleateIndex != listSize - 1) {
			return CASE_BETWEEN;
		}
		// 消去対象有効日が履歴一覧の中で一番最新の場合
		if (deleateIndex == listSize - 1) {
			return CASE_NEWEST;
		}
		// エラーメッセージを設定
		PfMessageUtility.addErrorExclusive(mospParams);
		return 0;
	}
	
	/**
	 * 個人情報履歴の削除処理を行う。
	 * @param extraList Beanリスト
	 * @param dto 削除対象DTO
	 * @param isAllDelete 対象社員情報全削除フラグ
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void humanDelete(List<ExtraHumanDeleteBeanInterface> extraList, HumanDtoInterface dto,
			boolean isAllDelete) throws MospException {
		// 人事マスタ削除
		humanRegist.delete(dto);
		// 追加クラス毎に処理
		for (ExtraHumanDeleteBeanInterface extraBean : extraList) {
			// 個人情報履歴の削除処理
			extraBean.humanDelete(dto, isAllDelete);
		}
		// 役職情報削除
		deletePost(dto);
		// 全削除フラグがfalseの場合
		if (isAllDelete == false) {
			return;
		}
		// 人事退社情報削除
		deleteRetirement(dto);
		// 人事休職情報削除削除
		deleteSuspension(dto);
		// 人事兼務情報削除
		deleteConcurrent(dto);
		// 人事入社情報削除
		deleteEntrance(dto);
		// ユーザマスタ削除
		userAccountRegist.delete(dto.getPersonalId());
	}
	
	/**
	 * ケース：1 履歴が1件だけの場合。<br>
	 * ルート適用・ユニット・代理登録にデータ存在するか確認をする。<br>
	 * @param deleteHumanDto 削除対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void oneDelete(HumanDtoInterface deleteHumanDto) throws MospException {
		// ルート適用・ユニットの存在確認
		checkWorkflow(deleteHumanDto.getPersonalId(), null, deleteHumanDto);
		// 代理承認者情報存在確認
		checkSubApprover(deleteHumanDto, null);
	}
	
	/**
	 * ケース：2 消去対象がリストの中で一番古い履歴の場合。
	 * ルート適用・ユニット・アカウント・代理登録情報が、
	 * 削除対象有効日からひとつ未来の有効日との期間内にデータが存在するか確認。
	 * @param deleateIndex 削除DTOのi番目
	 * @param list 削除対象人事情報履歴
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void oldDelete(int deleateIndex, List<HumanDtoInterface> list) throws MospException {
		// 削除対象DTO
		HumanDtoInterface deleateHumanDto = list.get(deleateIndex);
		// 削除対象一つ最新DTO
		HumanDtoInterface afterHumanDto = list.get(deleateIndex + 1);
		// 次履歴の有効日前日を取得
		Date endDate = DateUtility.addDay(afterHumanDto.getActivateDate(), -1);
		// ルート適用・ユニットの存在確認
		checkWorkflow(deleateHumanDto.getPersonalId(), endDate, deleateHumanDto);
		// アカウントマスタの存在確認
		checkAccount(deleateHumanDto.getPersonalId(), endDate, deleateHumanDto);
		// 代理承認者情報存在確認
		checkSubApprover(deleateHumanDto, endDate);
	}
	
	/**
	 * ケース：3 消去対象DTO履歴が一覧の中で前後の履歴も存在する時の確認。
	 * 選択社員の所属・職位・雇用契約区分・勤務地情報が、
	 * 削除対象有効日からひとつ未来の有効日との期間内に無効データが存在するか確認。
	 * @param deleateIndex 削除DTOのi番目
	 * @param list 削除対象人事情報履歴
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void betweenDelete(int deleateIndex, List<HumanDtoInterface> list) throws MospException {
		// 削除対象履歴とその前後の履歴を取得
		HumanDtoInterface beforeHumanDto = list.get(deleateIndex - 1);
		HumanDtoInterface deleteHumanDto = list.get(deleateIndex);
		HumanDtoInterface afterHumanDto = list.get(deleateIndex + 1);
		// 次履歴の有効日前日を取得
		Date endDate = DateUtility.addDay(afterHumanDto.getActivateDate(), -1);
		checkMaster(endDate, deleteHumanDto, beforeHumanDto);
	}
	
	/**
	 * ケース：4 消去対象有効日がリストの中で一番最新の場合。
	 * 選択社員の所属・職位・雇用契約区分・勤務地情報が、
	 * 既に無効であるか確認、また削除対象有効日から以降のデータで無効データが存在するか確認。
	 * @param deleateIndex 削除DTOのi番目
	 * @param list 削除対象人事情報履歴
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void newDelete(int deleateIndex, List<HumanDtoInterface> list) throws MospException {
		// 削除対象履歴とその前の履歴を取得
		HumanDtoInterface beforeHumanDto = list.get(deleateIndex - 1);
		HumanDtoInterface deleteHumanDto = list.get(deleateIndex);
		// 所属・職位・雇用契約区分・勤務地確認
		checkMaster(null, deleteHumanDto, beforeHumanDto);
	}
	
	/**
	 * 選択社員の所属・職位・雇用契約区分・勤務地情報の存在確認。
	 * @param endDate 期間終了日
	 * @param deleteDto 削除対象DTO
	 * @param beforeDto 削除対象一つ前のDTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 * 
	 */
	protected void checkMaster(Date endDate, HumanDtoInterface deleteDto, HumanDtoInterface beforeDto)
			throws MospException {
		// 確認期間開始日取得
		Date startDate = deleteDto.getActivateDate();
		// 所属存在確認
		checkSection(beforeDto.getSectionCode(), startDate, endDate, null);
		// 職位存在確認
		checkPosition(beforeDto.getPositionCode(), startDate, endDate, null);
		// 雇用契約存在確認
		checkEmploymentContract(beforeDto.getEmploymentContractCode(), startDate, endDate, null);
		// 勤務地存在確認
		checkWorkPlace(beforeDto.getWorkPlaceCode(), startDate, endDate, null);
		// 役職機能が有効の場合
		if (mospParams.getApplicationPropertyBool(PlatformConst.APP_ADD_USE_POST)) {
			// 役職存在確認
			checkPostMaster(startDate, endDate, deleteDto, beforeDto);
		}
	}
	
	/**
	 * 役職機能が有効の場合、
	 * 役職情報の存在確認を行う。
	 * @param startDate 期間開始日
	 * @param endDate 期間終了日
	 * @param deleteDto 削除対象DTO
	 * @param beforeDto 削除対象一つ前のDTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkPostMaster(Date startDate, Date endDate, HumanDtoInterface deleteDto,
			HumanDtoInterface beforeDto) throws MospException {
		// 削除対象日を元に役職情報リスト取得
		List<HumanHistoryDtoInterface> postList = humanHistoryReference.findForHistory(deleteDto.getPersonalId(),
				PlatformConst.NAMING_TYPE_POST);
		// 役職情報がない又は役職履歴情報が一つしかない場合
		if (postList.isEmpty() || postList.size() == 1) {
			return;
		}
		// 一番古い役職情報の場合
		if (postList.get(0).getActivateDate().equals(deleteDto.getActivateDate())) {
			return;
		}
		// 削除対象有効日の役職履歴リストインデックスを取得
		int targetPostIndex = HumanUtility.getTargetIndexPost(postList, deleteDto.getActivateDate());
		// 役職情報が存在しない場合
		if (targetPostIndex == -1) {
			return;
		}
		// 役職情報前履歴取得
		HumanHistoryDtoInterface humanHistoryDto = postList.get(targetPostIndex - 1);
		// 役職情報が削除対象一つ前人事履歴情報でない場合
		if (humanHistoryDto.getActivateDate().equals(beforeDto.getActivateDate()) == false) {
			return;
		}
		// 役職存在確認
		checkNaming(PlatformConst.NAMING_TYPE_POST, humanHistoryDto.getHumanItemValue(), startDate, endDate, null);
	}
	
	/**
	 * ルート適用・ユニットの存在確認。
	 * @param personalId 対象個人ID
	 * @param endDate 期間終了日
	 * @param deleteDto 削除対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 * 
	 */
	protected void checkWorkflow(String personalId, Date endDate, HumanDtoInterface deleteDto) throws MospException {
		// ルート適用情報存在確認(勤怠)
		if (routeApplicationReference.hasPersonalApplication(personalId, deleteDto.getActivateDate(),
				endDate) == true) {
			// 期間内に適用されている設定が存在する場合消去しない
			String employeeCode = deleteDto.getEmployeeCode();
			String fieldName = PfNameUtility.routeApplication(mospParams);
			PfMessageUtility.addErrorHumanCanNotDelete(mospParams, employeeCode, fieldName);
		}
		// ユニット情報存在確認
		if (approvalUnitReference.hasPersonalUnit(deleteDto.getPersonalId(), deleteDto.getActivateDate(),
				endDate) == true) {
			// 期間内にユニットが設定されている場合消去しない
			String employeeCode = deleteDto.getEmployeeCode();
			String fieldName = PfNameUtility.unit(mospParams);
			PfMessageUtility.addErrorHumanCanNotDelete(mospParams, employeeCode, fieldName);
		}
	}
	
	/**
	 * アカウント情報存在確認。
	 * @param personalId 対象個人ID
	 * @param endDate 期間終了日
	 * @param deleteDto 削除対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 * 
	 */
	protected void checkAccount(String personalId, Date endDate, HumanDtoInterface deleteDto) throws MospException {
		// 期間開始日取得
		Date startDate = deleteDto.getActivateDate();
		// 現在の有効日の履歴一覧取得
		List<UserMasterDtoInterface> userMasterList = userMasterReference.getUserHistoryForPersonalId(personalId);
		// 履歴一覧後に処理
		for (UserMasterDtoInterface userMasterDto : userMasterList) {
			// 期間に対象日が含まれているか確認
			if (DateUtility.isTermContain(userMasterDto.getActivateDate(), startDate, endDate) == true) {
				// データが存在する場合
				String employeeCode = deleteDto.getEmployeeCode();
				String fieldName = PfNameUtility.account(mospParams);
				PfMessageUtility.addErrorHumanCanNotDelete(mospParams, employeeCode, fieldName);
			}
		}
	}
	
	/**
	 * 代理承認者に対象社員が適用されているか確認する。<br>
	 * @param endDate 期間終了日
	 * @param deleteDto 削除対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkSubApprover(HumanDtoInterface deleteDto, Date endDate) throws MospException {
		// 期間初日取得
		Date startDate = deleteDto.getActivateDate();
		// 代理承認情報存在確認
		if (subApproverReference.hasSubApprover(deleteDto.getPersonalId(), startDate, endDate,
				PlatformConst.WORKFLOW_TYPE_TIME) == true) {
			// データが存在する場合
			String employeeCode = deleteDto.getEmployeeCode();
			String fieldName = PfNameUtility.substituteApprover(mospParams);
			PfMessageUtility.addErrorHumanCanNotDelete(mospParams, employeeCode, fieldName);
		}
	}
	
	/**
	 * 役職情報を削除する。
	 * @param humanDto 人事マスタDTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void deletePost(HumanDtoInterface humanDto) throws MospException {
		// 削除対象役職情報取得
		HumanHistoryDtoInterface postDto = humanHistoryReference.findForKey(humanDto.getPersonalId(),
				PlatformConst.NAMING_TYPE_POST, humanDto.getActivateDate());
		if (postDto != null) {
			// 役職情報削除
			humanHistoryRegist.delete(postDto);
		}
	}
	
	/**
	 * 人事退社情報を削除する。
	 * @param humanDto 人事マスタDTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void deleteRetirement(HumanDtoInterface humanDto) throws MospException {
		// 退社情報DTOの準備
		RetirementDtoInterface dto = retirementReference.getRetireInfo(humanDto.getPersonalId());
		if (dto != null) {
			// 退社情報削除処理
			retirementRegist.delete(dto);
		}
	}
	
	/**
	 * 人事入社情報を削除する。
	 * @param humanDto 人事マスタDTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void deleteEntrance(HumanDtoInterface humanDto) throws MospException {
		// 入社情報DTOの準備
		EntranceDtoInterface dto = entranceReference.getEntranceInfo(humanDto.getPersonalId());
		// 入社情報が存在する場合
		if (dto != null) {
			// 入社情報削除処理
			entranceRegist.delete(dto);
		}
	}
	
	/**
	 * 休職情報を削除する。
	 * @param humanDto 人事マスタDTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void deleteSuspension(HumanDtoInterface humanDto) throws MospException {
		// 休職情報DTOの準備
		long[] array = setDtoFieldsForSuspension(humanDto);
		// 休職情報DTOに値を設定
		if (array.length == 0) {
			return;
		}
		// 休職情報登録処理
		suspensionRegist.delete(array);
	}
	
	/**
	 * VO(編集項目)の値をDTO(休職情報)に設定する。<br>
	 * @param humanDto 人事マスタDTO
	 * @return long[] 対象識別ID
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected long[] setDtoFieldsForSuspension(HumanDtoInterface humanDto) throws MospException {
		// 選択されたユーザーの履歴を取得する
		List<SuspensionDtoInterface> list = suspensionReference.getSuspentionList(humanDto.getPersonalId());
		// 情報が存在しない場合
		if (list.size() == 0) {
			return new long[0];
		}
		// 識別ID配列を作成
		int idx = 0;
		long[] arySuspension = new long[list.size()];
		for (SuspensionDtoInterface dto : list) {
			arySuspension[idx++] = dto.getPfaHumanSuspensionId();
		}
		return arySuspension;
	}
	
	/**
	 * 人事兼務情報を削除する。
	 * @param humanDto 人事マスタDTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void deleteConcurrent(HumanDtoInterface humanDto) throws MospException {
		// 人事兼務情報DTOの準備
		long[] array = setDtoFieldsForConcurrent(humanDto);
		// 人事兼務情報DTOに値を設定
		if (array.length == 0) {
			return;
		}
		// 人事兼務情報登録処理
		concurrentRegist.delete(array);
	}
	
	/**
	 * VO(編集項目)の値をDTO(人事兼務情報)に設定する。<br>
	 * @param humanDto 対象DTO
	 * @return long[] 対象識別ID
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected long[] setDtoFieldsForConcurrent(HumanDtoInterface humanDto) throws MospException {
		// 選択されたユーザーの履歴を取得する
		List<ConcurrentDtoInterface> list = concurrentReference.getConcurrentHistory(humanDto.getPersonalId());
		// 情報が存在しない場合
		if (list.size() == 0) {
			return new long[0];
		}
		// 識別ID配列を作成
		int idx = 0;
		long[] aryConcurrent = new long[list.size()];
		for (ConcurrentDtoInterface dto : list) {
			aryConcurrent[idx++] = dto.getPfaHumanConcurrentId();
		}
		return aryConcurrent;
	}
	
}
