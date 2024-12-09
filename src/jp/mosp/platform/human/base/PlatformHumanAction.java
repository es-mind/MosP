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
package jp.mosp.platform.human.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.comparator.IndexComparator;
import jp.mosp.framework.constant.ExceptionConst;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.property.CodeItemProperty;
import jp.mosp.framework.property.CodeProperty;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.framework.utils.RoleUtility;
import jp.mosp.platform.base.PlatformAction;
import jp.mosp.platform.bean.human.HumanSearchBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.human.constant.PlatformHumanConst;
import jp.mosp.platform.portal.action.PortalAction;
import jp.mosp.platform.utils.PfMessageUtility;

/**
 * MosPプラットフォーム人事管理におけるActionの基本機能を提供する。<br>
 */
public abstract class PlatformHumanAction extends PlatformAction {
	
	/**
	 * ファイルパス(MosPプラットフォーム人事管理用JavaScript)。
	 */
	public static final String	PATH_PLATFORM_HUMAN_JS	= "/pub/platform/human/js/platformHuman.js";
	
	/**
	 * ファイルパス(MosPプラットフォーム人事管理用CSS)。
	 */
	public static final String	PATH_PLATFORM_HUMAN_CSS	= "/pub/platform/human/css/platformHuman.css";
	
	
	@Override
	protected void addBaseJsCssFiles() {
		super.addBaseJsCssFiles();
		// MosPプラットフォーム人事管理用JavaScriptファイル追加
		mospParams.addJsFile(PATH_PLATFORM_HUMAN_JS);
		// MosPプラットフォーム人事管理用CSSファイル追加
		mospParams.addCssFile(PATH_PLATFORM_HUMAN_CSS);
	}
	
	/**
	 * 人事管理共通情報の利用設定を行う。
	 * @param searchCmd       各画面の検索コマンド
	 * @param modeHumanLayout 人事管理共通情報表示モード(全て表示、社員コード及び氏名のみ表示、再表示なし)
	 */
	protected void setPlatformHumanSettings(String searchCmd, String modeHumanLayout) {
		// VO取得
		PlatformHumanVo vo = (PlatformHumanVo)mospParams.getVo();
		// 人事管理共通情報利用設定
		vo.setCmdSaerch(searchCmd);
		vo.setModeHumanLayout(modeHumanLayout);
	}
	
	/**
	 * 設定された対象個人ID及び対象日を取得し、人事管理共通情報として設定を行う。<br>
	 * 人事管理共通情報UIに表示する値、前社員、次社員を設定する。<br>
	 * 人事管理系の各Actionでは、ここで設定された社員コード及び有効日を基にして、処理をする。<br>
	 * @throws MospException 社員存在確認に失敗した場合
	 */
	protected void setTargetHumanCommonInfo() throws MospException {
		// VO取得
		PlatformHumanVo vo = (PlatformHumanVo)mospParams.getVo();
		
		// 個人ID取得
		String personalId = getTargetPersonalId();
		// 対象日取得
		Date targetDate = getTargetDate();
		// 人事管理共通情報設定
		setHumanCommonInfo(personalId, targetDate);
		// 人事汎用管理区分参照権限設定
		vo.setJsIsReferenceDivision(RoleUtility.getReferenceDivisionsList(mospParams).contains(getTransferredType()));
		
	}
	
	/**
	 * 人事管理共通情報の設定を行う。<br>
	 * @param employeeCode 社員コード
	 * @param targetDate 対象日
	 * @throws MospException 社員存在確認に失敗した場合
	 */
	protected void setHumanCommonInfoForEmployeeCode(String employeeCode, Date targetDate) throws MospException {
		// 社員情報取得
		HumanDtoInterface dto = reference().human().getHumanInfoForEmployeeCode(employeeCode, targetDate);
		// 人事管理共通情報の設定
		setHumanCommonInfo(dto, targetDate);
	}
	
	/**
	 * 人事管理共通情報の設定を行う。<br>
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @throws MospException 社員存在確認に失敗した場合
	 */
	protected void setHumanCommonInfo(String personalId, Date targetDate) throws MospException {
		// 社員情報取得
		HumanDtoInterface dto = reference().human().getHumanInfo(personalId, targetDate);
		// 人事管理共通情報の設定
		setHumanCommonInfo(dto, targetDate);
	}
	
	/**
	 * 人事管理共通情報UIに表示する値、前社員、次社員を設定する。<br>
	 * 人事管理系の各Actionでは、ここで設定された社員コード及び有効日を基にして、処理をする。<br>
	 * @param dto 人事マスタ
	 * @param targetDate 対象日
	 * @throws MospException 社員存在確認に失敗した場合
	 */
	protected void setHumanCommonInfo(HumanDtoInterface dto, Date targetDate) throws MospException {
		// VO取得
		PlatformHumanVo vo = (PlatformHumanVo)mospParams.getVo();
		// 社員存在確認
		if (MospUtility.isEmpty(dto)) {
			// エラーメッセージを設定(社員が存在しない場合)
			PfMessageUtility.addErrorEmployeeNotExist(mospParams);
			return;
		}
		// 社員コードを取得
		String employeeCode = dto.getEmployeeCode();
		// 社員参照可能確認
		if (mospParams.hasErrorMessage() == false && isNotEmployeeReferable(employeeCode, targetDate)) {
			// エラーメッセージを設定(社員の参照権限が無い場合)
			PfMessageUtility.addErrorNoAuthorityToRefer(mospParams, targetDate, employeeCode);
		}
		// エラーメッセージ確認
		if (mospParams.hasErrorMessage()) {
			// 表示情報存在確認
			if (MospUtility.isEmpty(vo.getEmployeeCode())) {
				// 連続実行コマンド設定(ポータル画面表示)
				mospParams.setNextCommand(PortalAction.CMD_SHOW);
			}
			// 例外発行
			throw new MospException(ExceptionConst.EX_NO_DATA);
		}
		// 対象社員コード及び有効日設定
		vo.setEmployeeCode(employeeCode);
		vo.setTargetDate(targetDate);
		// 対象個人ID設定
		vo.setPersonalId(dto.getPersonalId());
		// 対象社員情報設定
		vo.setLblEmployeeName(MospUtility.getHumansName(dto.getFirstName(), dto.getLastName()));
		// VOの有効日(年、月、日)を設定
		vo.setTxtSearchActivateYear(String.valueOf(DateUtility.getYear(targetDate)));
		vo.setTxtSearchActivateMonth(String.valueOf(DateUtility.getMonth(targetDate)));
		vo.setTxtSearchActivateDay(String.valueOf(DateUtility.getDay(targetDate)));
		// 検索社員コード初期化
		vo.setTxtSearchEmployeeCode(MospConst.STR_EMPTY);
		// 前社員、次社員情報設定
		setRollEmployee();
	}
	
	/**
	 * 社員が参照可能であるかを確認する。<br>
	 * @param employeeCode 社員コード
	 * @param targetDate   対象日
	 * @return 確認結果(true：参照不可、false：参照可)
	 * @throws MospException 社員リストの取得に失敗した場合
	 */
	protected boolean isNotEmployeeReferable(String employeeCode, Date targetDate) throws MospException {
		// 検索クラス取得
		HumanSearchBeanInterface humanSearch = reference().humanSearch();
		// 検索条件設定(対象日及び社員コード完全一致)
		humanSearch.setTargetDate(targetDate);
		humanSearch.setEmployeeCode(employeeCode);
		humanSearch.setEmployeeCodeType(PlatformConst.SEARCH_EXACT_MATCH);
		humanSearch.setOperationType(MospConst.OPERATION_TYPE_REFER);
		// 検索
		List<HumanDtoInterface> list = humanSearch.search();
		return list.isEmpty();
		
	}
	
	/**
	 * 前社員、次社員を設定する。<br>
	 * @throws MospException 社員リストの取得に失敗した場合
	 */
	protected void setRollEmployee() throws MospException {
		// VO準備
		PlatformHumanVo vo = (PlatformHumanVo)mospParams.getVo();
		// 初期化
		vo.setLblNextEmployeeCode("");
		vo.setLblBackEmployeeCode("");
		// 初期化終了
		// 検索クラス取得
		HumanSearchBeanInterface humanSearch = reference().humanSearch();
		// 検索条件設定
		humanSearch.setTargetDate(vo.getTargetDate());
		humanSearch.setOperationType(MospConst.OPERATION_TYPE_REFER);
		// 検索
		List<HumanDtoInterface> list = humanSearch.search();
		// 社員リスト確認
		for (int i = 0; i < list.size(); i++) {
			// 社員情報取得
			HumanDtoInterface dto = list.get(i);
			// 社員コード確認
			if (dto.getEmployeeCode().equals(vo.getEmployeeCode()) == false) {
				// 社員コードが異なる場合
				continue;
			}
			// 次社員設定
			if (i + 1 < list.size()) {
				vo.setLblNextEmployeeCode(list.get(i + 1).getEmployeeCode());
				vo.setNextPersonalId(list.get(i + 1).getPersonalId());
			}
			// 前社員設定
			if (i > 0) {
				vo.setLblBackEmployeeCode(list.get(i - 1).getEmployeeCode());
				vo.setBackPersonalId(list.get(i - 1).getPersonalId());
			}
			// 設定完了
			break;
		}
	}
	
	/**
	 * リクエストされた社員コードを取得する。
	 * @return 社員コード
	 */
	protected String getSearchEmployeeCode() {
		return mospParams.getRequestParam(PlatformHumanConst.PRM_TXT_EMPLOYEE_CODE);
	}
	
	/**
	 * リクエストされた対象日を取得する。
	 * @return 対象日
	 */
	protected Date getSearchTargetDate() {
		String year = mospParams.getRequestParam(PlatformHumanConst.PRM_TXT_ACTIVATE_YEAR);
		String month = mospParams.getRequestParam(PlatformHumanConst.PRM_TXT_ACTIVATE_MONTH);
		String day = mospParams.getRequestParam(PlatformHumanConst.PRM_TXT_ACTIVATE_DAY);
		return getDate(year, month, day);
	}
	
	/**
	 * 人事管理共通情報UIからリクエストされた検索モードに基づき、
	 * 人事管理共通情報の検索を行う。<br>
	 * @throws MospException 人事情報の取得に失敗した場合
	 */
	protected void searchHumanCommonInfo() throws MospException {
		// VO準備
		PlatformHumanVo vo = (PlatformHumanVo)mospParams.getVo();
		// リクエストされた検索モードを取得
		String searchMode = getTransferredSearchMode();
		// 検索モード()
		if (PlatformHumanConst.SEARCH_BACK.equals(searchMode)) {
			// 個人ID[前]
			setHumanCommonInfo(vo.getBackPersonalId(), vo.getTargetDate());
		} else if (PlatformHumanConst.SEARCH_NEXT.equals(searchMode)) {
			// 個人ID[後]
			setHumanCommonInfo(vo.getNextPersonalId(), vo.getTargetDate());
		} else if (PlatformHumanConst.SEARCH_EMPLOYEE_CODE.equals(searchMode)) {
			// 社員コード
			setHumanCommonInfoForEmployeeCode(getSearchEmployeeCode(), vo.getTargetDate());
		} else if (PlatformHumanConst.SEARCH_TARGET_DATE.equals(searchMode)) {
			// 対象日
			setHumanCommonInfo(vo.getPersonalId(), getSearchTargetDate());
		}
	}
	
	/**
	 * リクエストされた検索モードを取得する。
	 * @return 譲渡Actionクラス名
	 */
	protected String getTransferredSearchMode() {
		return mospParams.getRequestParam(PlatformHumanConst.PRM_TRANSFER_SEARCH_MODE);
	}
	
	/**
	 * コード配列を取得する。（人事汎用機能のフリーワード検索用コード配列取得）
	 * @param codeKey   コードキー
	 * @param needBlank 空白行要否(true：空白行要、false：空白行不要)
	 * @return コード配列
	 */
	protected String[][] getCodeArrayForHumanGeneral(String codeKey, boolean needBlank) {
		// コード設定情報取得
		CodeProperty codeProperty = mospParams.getCodeProperty().get(codeKey);
		// コード設定情報確認
		if (codeProperty == null) {
			// コード設定情報無し
			return new String[0][0];
		}
		// コード項目リスト取得
		List<CodeItemProperty> list = new ArrayList<CodeItemProperty>(codeProperty.getCodeItemMap().values());
		// コード項目リストソート
		Collections.sort(list, new IndexComparator());
		// 非表示項目除去
		for (int i = list.size() - 1; i >= 0; i--) {
			CodeItemProperty codeItemProperty = list.get(i);
			if (codeItemProperty.getViewFlag() == MospConst.VIEW_OFF) {
				list.remove(i);
			}
			// ロールで非表示設定された人事汎用管理区分が存在した場合は表示対象としない
			if (RoleUtility.getHiddenDivisionsList(mospParams).contains(codeItemProperty.getKey())) {
				list.remove(i);
			}
			
		}
		// 配列及びインデックス宣言
		String[][] array;
		int idx = 0;
		// 空白行設定
		if (needBlank) {
			array = new String[list.size() + 1][2];
			array[0][0] = "";
			array[0][1] = "";
			idx++;
		} else {
			array = new String[list.size()][2];
		}
		// 配列作成
		for (CodeItemProperty codeItemProperty : list) {
			array[idx][0] = codeItemProperty.getKey();
			array[idx][1] = codeItemProperty.getItemName();
			idx++;
		}
		return array;
	}
	
}
