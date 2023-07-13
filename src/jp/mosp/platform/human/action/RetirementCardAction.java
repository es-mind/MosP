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
package jp.mosp.platform.human.action;

import java.util.Date;

import jp.mosp.framework.base.BaseVo;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformAction;
import jp.mosp.platform.bean.human.RetirementRegistBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dto.human.RetirementDtoInterface;
import jp.mosp.platform.human.base.PlatformHumanAction;
import jp.mosp.platform.human.constant.PlatformHumanConst;
import jp.mosp.platform.human.vo.RetirementCardVo;
import jp.mosp.platform.utils.InputCheckUtility;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.platform.utils.PfNameUtility;

/**
 * 社員一覧画面で選択した社員の退職情報の登録・更新・削除を行う。<br>
 * <br>
 * 以下のコマンドを扱う。<br>
 * <ul><li>
 * {@link #CMD_SELECT}
 * </li><li>
 * {@link #CMD_SEARCH}
 * </li><li>
 * {@link #CMD_UPDATE}
 * </li><li>
 * {@link #CMD_TRANSFER}
 * </li><li>
 * {@link #CMD_DELETE}
 * </li></ul>
 */
public class RetirementCardAction extends PlatformHumanAction {
	
	/**
	 * 選択表示コマンド。<br>
	 * <br>
	 * 社員一覧画面で選択された社員の個人IDを基に退職情報を表示する。<br>
	 */
	public static final String	CMD_SELECT		= "PF1171";
	
	/**
	 * 検索コマンド。<br>
	 * <br>
	 * 社員コード入力欄に入力された社員コードで検索を行い、該当する社員の退職情報登録画面へ遷移する。<br>
	 * 社員コード順にページを送るボタンがクリックされた場合には
	 * 遷移元の社員一覧リスト検索結果を参照して前後それぞれページ移動を行う。<br>
	 * 入力した社員コードが存在しない、または入力されていない状態で
	 * 「検索ボタン」がクリックされた場合はエラーメッセージにて通知。<br>
	 * 現在表示されている社員の社員コードの前後にデータが存在しない時に
	 * コード順送りボタンをクリックした場合も同様にエラーメッセージにて通知。<br>
	 */
	public static final String	CMD_SEARCH		= "PF1172";
	
	/**
	 * 画面遷移コマンド。<br>
	 * <br>
	 * 必要な情報をMosP処理情報に設定して、連続実行コマンドを設定する。<br>
	 */
	public static final String	CMD_TRANSFER	= "PF1176";
	
	/**
	 * 更新コマンド。<br>
	 * <br>
	 * 情報入力欄の入力内容を基に退職情報テーブルの更新を行う。<br>
	 * レコードが存在しなければ新規登録し、既存のものがあるなら更新の処理を行う。<br>
	 * 入力チェックを行った際に入力必須項目が入力されていない場合はエラーメッセージにて通知。<br>
	 */
	public static final String	CMD_UPDATE		= "PF1178";
	
	/**
	 * 削除コマンド。<br>
	 * <br>
	 * 登録済みの退職情報の削除を行う。<br>
	 */
	public static final String	CMD_DELETE		= "PF1179";
	
	
	/**
	 * {@link PlatformAction#PlatformAction()}を実行する。<br>
	 */
	public RetirementCardAction() {
		super();
	}
	
	@Override
	public void action() throws MospException {
		// コマンド毎の処理
		if (mospParams.getCommand().equals(CMD_SELECT)) {
			// 選択表示
			prepareVo(true, false);
			select();
		} else if (mospParams.getCommand().equals(CMD_SEARCH)) {
			// 検索
			prepareVo(true, false);
			search();
		} else if (mospParams.getCommand().equals(CMD_UPDATE)) {
			// 更新
			prepareVo();
			regist();
		} else if (mospParams.getCommand().equals(CMD_TRANSFER)) {
			// 画面遷移
			prepareVo(true, false);
			transfer();
		} else if (mospParams.getCommand().equals(CMD_DELETE)) {
			// 削除
			prepareVo();
			delete();
		} else {
			throwInvalidCommandException();
		}
	}
	
	@Override
	protected BaseVo getSpecificVo() {
		return new RetirementCardVo();
	}
	
	/**
	 * 編集画面選択表示処理を行う。<br>
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void select() throws MospException {
		// 人事管理共通情報利用設定
		setPlatformHumanSettings(CMD_SEARCH, PlatformHumanConst.MODE_HUMAN_NO_ACTIVATE_DATE);
		// 人事管理共通情報設定
		setTargetHumanCommonInfo();
		// 初期値設定
		setDefaultValues();
		// 退職情報を取得しVOに設定
		setRetirementInfo();
		// プルダウン設定
		setPulldown();
	}
	
	/**
	 * 検索処理を行う。<br>
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	protected void search() throws MospException {
		// 人事管理共通情報の検索
		searchHumanCommonInfo();
		// 項目初期化
		setDefaultValues();
		// 退職情報を取得しVOに設定
		setRetirementInfo();
	}
	
	/**
	 * 対象個人ID、対象日等をMosP処理情報に設定し、
	 * 譲渡Actionクラス名に応じて連続実行コマンドを設定する。<br>
	 */
	protected void transfer() {
		// VO取得
		RetirementCardVo vo = (RetirementCardVo)mospParams.getVo();
		// 譲渡Actionクラス名取得
		String actionName = getTransferredAction();
		// MosP処理情報に対象個人IDを設定
		setTargetPersonalId(vo.getPersonalId());
		// MosP処理情報に対象日を設定
		setTargetDate(vo.getTargetDate());
		// 譲渡Actionクラス名毎に処理
		if (actionName.equals(HumanInfoAction.class.getName())) {
			// 社員の人事情報をまとめて表示
			mospParams.setNextCommand(HumanInfoAction.CMD_SELECT);
		}
	}
	
	/**
	 * 登録処理を行う。<br>
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void regist() throws MospException {
		// 入力値チェック
		validate();
		if (mospParams.hasErrorMessage()) {
			// 登録失敗メッセージを設定
			PfMessageUtility.addMessageInsertFailed(mospParams);
			return;
		}
		// 登録クラス取得
		RetirementRegistBeanInterface regist = platform().retirementRegist();
		// DTOの準備
		RetirementDtoInterface dto = regist.getInitDto();
		// DTOに値を設定
		setDtoFields(dto);
		// 更新処理
		regist.regist(dto);
		// 更新結果確認
		if (mospParams.hasErrorMessage()) {
			// 登録失敗メッセージを設定
			PfMessageUtility.addMessageInsertFailed(mospParams);
			return;
		}
		// コミット
		commit();
		// 登録成功メッセージを設定
		PfMessageUtility.addMessageInsertSucceed(mospParams);
		// 退職情報再設定
		setRetirementInfo();
	}
	
	/**
	 * 削除処理を行う。<br>
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合 
	 */
	protected void delete() throws MospException {
		// 登録クラス取得
		RetirementRegistBeanInterface regist = platform().retirementRegist();
		// DTOの準備
		RetirementDtoInterface dto = regist.getInitDto();
		// DTOに値を設定
		setDtoFields(dto);
		// 削除処理
		regist.delete(dto);
		// 削除結果確認
		if (mospParams.hasErrorMessage()) {
			// 削除失敗メッセージを設定
			PfMessageUtility.addMessageDeleteFailed(mospParams);
			return;
		}
		// コミット
		commit();
		// 削除成功メッセージを設定
		PfMessageUtility.addMessageDeleteSucceed(mospParams);
		// 初期値設定
		setDefaultValues();
	}
	
	/**
	 * 退職情報を取得し、VOに設定する。
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void setRetirementInfo() throws MospException {
		// VO取得
		RetirementCardVo vo = (RetirementCardVo)mospParams.getVo();
		// 退職情報を取得しVOに設定
		setVoFields(reference().retirement().getRetireInfo(vo.getPersonalId()));
	}
	
	/**
	 * プルダウンの設定を行う。<br>
	 */
	protected void setPulldown() {
		// VO取得
		RetirementCardVo vo = (RetirementCardVo)mospParams.getVo();
		// プルダウン設定
		vo.setAryPltRetirementReason(mospParams.getProperties().getCodeArray(PlatformConst.CODE_KEY_RETIREMENT, false));
	}
	
	/**
	 * VOに初期値を設定する。<br>
	 */
	public void setDefaultValues() {
		// VO取得
		RetirementCardVo vo = (RetirementCardVo)mospParams.getVo();
		// 初期値設定
		vo.setRecordId(0L);
		vo.setTxtRetirementDetail("");
		vo.setTxtRetirementYear("");
		vo.setTxtRetirementMonth("");
		vo.setTxtRetirementDay("");
		vo.setPltRetirementReason("");
	}
	
	/**
	 * DTOの値をVO(編集項目)に設定する。<br>
	 * @param dto 対象DTO
	 */
	public void setVoFields(RetirementDtoInterface dto) {
		// DTO確認
		if (dto == null) {
			return;
		}
		// VO取得
		RetirementCardVo vo = (RetirementCardVo)mospParams.getVo();
		// VOにDTOの値を設定
		vo.setRecordId(dto.getPfaHumanRetirementId());
		vo.setPersonalId(dto.getPersonalId());
		// 退職理由
		vo.setPltRetirementReason(dto.getRetirementReason());
		// 退職日
		vo.setTxtRetirementYear(getStringYear(dto.getRetirementDate()));
		vo.setTxtRetirementMonth(getStringMonth(dto.getRetirementDate()));
		vo.setTxtRetirementDay(getStringDay(dto.getRetirementDate()));
		// 詳細
		vo.setTxtRetirementDetail(dto.getRetirementDetail());
	}
	
	/**
	 * VO(編集項目)の値をDTOに設定する。<br>
	 * @param dto 退職情報DTO
	 */
	protected void setDtoFields(RetirementDtoInterface dto) {
		// VO取得
		RetirementCardVo vo = (RetirementCardVo)mospParams.getVo();
		// 識別ID
		dto.setPfaHumanRetirementId(vo.getRecordId());
		// 個人ID
		dto.setPersonalId(vo.getPersonalId());
		// 退職日
		dto.setRetirementDate(getRetirementDate());
		// 詳細
		dto.setRetirementDetail(vo.getTxtRetirementDetail());
		// 理由
		dto.setRetirementReason(vo.getPltRetirementReason());
	}
	
	/**
	 * VOから退職日を取得する。<br>
	 * @return 退職日
	 */
	protected Date getRetirementDate() {
		// VO取得
		RetirementCardVo vo = (RetirementCardVo)mospParams.getVo();
		// 入社日取得
		return getDate(vo.getTxtRetirementYear(), vo.getTxtRetirementMonth(), vo.getTxtRetirementDay());
	}
	
	/**
	 * 入力値妥当性確認を行う。<br>
	 */
	public void validate() {
		// VO準備
		RetirementCardVo vo = (RetirementCardVo)mospParams.getVo();
		// メッセージ置換文字配列
		String yearName = PfNameUtility.year(mospParams);
		String monthName = PfNameUtility.month(mospParams);
		String dayName = PfNameUtility.day(mospParams);
		// 退職日必須
		InputCheckUtility.checkRequired(mospParams, vo.getTxtRetirementYear(), yearName);
		InputCheckUtility.checkRequired(mospParams, vo.getTxtRetirementMonth(), monthName);
		InputCheckUtility.checkRequired(mospParams, vo.getTxtRetirementDay(), dayName);
		// 退職日日付妥当性
		InputCheckUtility.checkNumber(mospParams, vo.getTxtRetirementYear(), yearName);
		InputCheckUtility.checkNumber(mospParams, vo.getTxtRetirementMonth(), monthName);
		InputCheckUtility.checkNumber(mospParams, vo.getTxtRetirementDay(), dayName);
		InputCheckUtility.checkDate(mospParams, vo.getTxtRetirementYear(), vo.getTxtRetirementMonth(),
				vo.getTxtRetirementDay(), PfNameUtility.retirementDate(mospParams));
	}
	
}
