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

import jp.mosp.framework.base.BaseVo;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.NameUtility;
import jp.mosp.framework.utils.TopicPathUtility;
import jp.mosp.platform.base.PlatformAction;
import jp.mosp.platform.bean.human.HumanNormalReferenceBeanInterface;
import jp.mosp.platform.bean.human.HumanNormalRegistBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.human.base.PlatformHumanAction;
import jp.mosp.platform.human.constant.PlatformHumanConst;
import jp.mosp.platform.human.vo.HumanNormalCardVo;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.platform.utils.PfNameUtility;

/**
 * 人事汎用管理形式の通常で表示・登録・更新を行う。<br>
 * <br>
 * 以下のコマンドを扱う。<br>
 * <ul><li>
 * {@link #CMD_SELECT}
 * </li><li>
 * {@link #CMD_SEARCH}
 * </li><li>
 * {@link #CMD_REGIST}
 * </li><li>
 * {@link #CMD_DELETE}
 * </li><li>
 * {@link #CMD_TRANSFER}
 * </li></ul>
 */
public class HumanNormalCardAction extends PlatformHumanAction {
	
	/**
	 * 選択表示コマンド。<br>
	 * <br>
	 * 人事汎用通常情報を表示する。<br>
	 */
	public static final String	CMD_SELECT				= "PF1511";
	
	/**
	 * 検索コマンド。<br>
	 * <br>
	 * 社員コード入力欄に入力された社員コードで検索を行い、
	 * 該当する社員の人事汎用通常情報登録画面へ遷移する。<br>
	 * 社員コード順にページを送るボタンがクリックされた場合には
	 * 遷移元の社員一覧リスト検索結果を参照して前後それぞれページ移動を行う。<br>
	 * 入力した社員コードが存在しない、または入力されていない状態で
	 * 「検索ボタン」がクリックされた場合はエラーメッセージにて通知。<br>
	 * 現在表示されている社員の社員コードの前後にデータが存在しない時に
	 * コード順送りボタンをクリックした場合も同様にエラーメッセージにて通知。<br>
	 */
	public static final String	CMD_SEARCH				= "PF1512";
	
	/**
	 * 登録・更新コマンド。<br>
	 * <br>
	 * 情報入力欄の入力内容を基に人事汎用通常情報テーブルの登録・更新を行う。<br>
	 * レコードが存在しなければ新規登録し、既存のものがあるなら更新の処理を行う。<br>
	 * 入力チェックを行った際に入力必須項目が入力されていない場合はエラーメッセージにて通知。<br>
	 */
	public static final String	CMD_REGIST				= "PF1516";
	
	/**
	 * 削除コマンド。<br>
	 * <br>
	 * 登録済みの人事汎用通常情報を削除する。<br>
	 */
	public static final String	CMD_DELETE				= "PF1517";
	
	/**
	 * 画面遷移コマンド。<br>
	 * <br>
	 * 必要な情報をMosP処理情報に設定して、連続実行コマンドを設定する。<br>
	 */
	public static final String	CMD_TRANSFER			= "PF1519";
	
	/**
	 * 人事汎用管理表示区分(通常)。<br>
	 */
	public static final String	KEY_VIEW_NORMAL_CARD	= "NormalCard";
	
	
	/**
	 * {@link PlatformAction#PlatformAction()}を実行する。<br>
	 */
	public HumanNormalCardAction() {
		super();
	}
	
	@Override
	public void action() throws MospException {
		// コマンド毎の処理
		if (mospParams.getCommand().equals(CMD_SELECT)) {
			// 選択
			prepareVo(true, false);
			select();
		} else if (mospParams.getCommand().equals(CMD_SEARCH)) {
			// 検索
			prepareVo(true, false);
			search();
		} else if (mospParams.getCommand().equals(CMD_REGIST)) {
			// 登録
			prepareVo();
			regist();
		} else if (mospParams.getCommand().equals(CMD_DELETE)) {
			// 削除
			prepareVo();
			delete();
		} else if (mospParams.getCommand().equals(CMD_TRANSFER)) {
			// 画面遷移
			prepareVo(true, false);
			transfer();
		} else {
			throwInvalidCommandException();
		}
	}
	
	@Override
	protected BaseVo getSpecificVo() {
		return new HumanNormalCardVo();
	}
	
	/**
	 * 編集画面選択表示処理を行う。<br>
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void select() throws MospException {
		// VO取得
		HumanNormalCardVo vo = (HumanNormalCardVo)mospParams.getVo();
		// 人事汎用管理区分設定
		vo.setDivision(getTransferredType());
		// パンくず名準備
		StringBuilder name = new StringBuilder(NameUtility.getName(mospParams, vo.getDivision()));
		name.append(PfNameUtility.information(mospParams));
		// パンくず名設定
		TopicPathUtility.setTopicPathName(mospParams, vo.getClassName(), name.toString());
		// 人事管理共通情報利用設定
		setPlatformHumanSettings(CMD_SEARCH, PlatformHumanConst.MODE_HUMAN_NO_ACTIVATE_DATE);
		// 人事管理共通情報設定
		setTargetHumanCommonInfo();
		// 初期値設定
		setDefaultValues();
		// 人事汎用通常情報設定
		setNormalInfo(false);
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
		// 人事汎用通常情報設定
		setNormalInfo(false);
	}
	
	/**
	 * 登録処理を行う。<br>
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void regist() throws MospException {
		// VO取得
		HumanNormalCardVo vo = (HumanNormalCardVo)mospParams.getVo();
		// 登録クラス取得
		HumanNormalRegistBeanInterface regist = platform().humanNormalRegist();
		// 更新処理
		regist.regist(vo.getDivision(), KEY_VIEW_NORMAL_CARD, vo.getPersonalId(), vo.getAryRecordIdMap());
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
		// 人事汎用通常情報設定
		setNormalInfo(false);
	}
	
	/**
	 * 削除処理を行う。<br>
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合 
	 */
	protected void delete() throws MospException {
		// VO取得
		HumanNormalCardVo vo = (HumanNormalCardVo)mospParams.getVo();
		// 登録クラス取得
		HumanNormalRegistBeanInterface regist = platform().humanNormalRegist();
		// 削除処理
		regist.delete(vo.getDivision(), KEY_VIEW_NORMAL_CARD, vo.getPersonalId(), vo.getAryRecordIdMap());
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
	 * 対象個人ID、対象日等をMosP処理情報に設定し、
	 * 譲渡Actionクラス名に応じて連続実行コマンドを設定する。<br>
	 */
	protected void transfer() {
		// VO取得
		HumanNormalCardVo vo = (HumanNormalCardVo)mospParams.getVo();
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
	 * VOに初期値を設定する。<br>
	 */
	protected void setDefaultValues() {
		// VO取得
		HumanNormalCardVo vo = (HumanNormalCardVo)mospParams.getVo();
		// 初期値設定
		vo.getHumanNormalMap().clear();
		// レコード識別ID初期化
		vo.getAryRecordIdMap().clear();
	}
	
	/**
	 * 人事汎用通常情報を設定する。
	 * @param isPulldownName プルダウン名要否確認(true：プルダウン名称、false：プルダウンコード)
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合 
	 */
	protected void setNormalInfo(boolean isPulldownName) throws MospException {
		// VO取得
		HumanNormalCardVo vo = (HumanNormalCardVo)mospParams.getVo();
		// 人事汎用管理区分取得
		String division = vo.getDivision();
		// 人事汎用通常情報参照クラス取得
		HumanNormalReferenceBeanInterface normalReference = reference().humanNormal();
		// プルダウンを取得
		vo.putPltItem(normalReference.getHumanGeneralPulldown(division, KEY_VIEW_NORMAL_CARD, getSystemDate()));
		
		// 表示情報のMaPを取得
		normalReference.getHumanNormalRecordMapInfo(division, KEY_VIEW_NORMAL_CARD, vo.getPersonalId(), getSystemDate(),
				isPulldownName);
		
		vo.setModeCardEdit(PlatformConst.MODE_CARD_EDIT_INSERT);
		if (!normalReference.getRecordsMap().isEmpty()) {
			// 編集モード確認
			vo.setModeCardEdit(PlatformConst.MODE_CARD_EDIT_EDIT);
		}
		
		// レコード識別IDマップ取得
		vo.setAryRecordIdMap(normalReference.getRecordsMap());
		// 登録情報を取得しVOに設定
		vo.putNormalItem(division, normalReference.getNormalMap());
	}
}
