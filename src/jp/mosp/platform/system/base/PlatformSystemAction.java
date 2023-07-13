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
package jp.mosp.platform.system.base;

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.platform.base.PlatformAction;
import jp.mosp.platform.base.PlatformDtoInterface;
import jp.mosp.platform.constant.PlatformConst;

/**
 * MosPプラットフォーム基本設定におけるActionの基本機能を提供する。<br>
 * <br>
 * <ul><li>
 * 基本設定画面の初期化を行う。<br>
 * </li><li>
 * 各編集モードの設定を行う。<br>
 * </li><li>
 * 次前履歴情報の設定を行う。<br>
 * </li><li>
 * {@link PlatformSystemVo}から有効日(編集、検索、一括更新)の日付を取得する。<br>
 * </li><li>
 * 他、MosPプラットフォーム基本設定におけるActionの基本機能。
 * </li></ul>
 */
public abstract class PlatformSystemAction extends PlatformAction {
	
	/**
	 * ページの先頭へDIV(HTML)のID。<br>
	 * スクロール先として設定するのに、用いる。<br>
	 */
	protected static final String HID_DIV_MOVE_UP = "divMoveUp";
	
	
	/**
	 * {@link PlatformSystemVo}のフィールドへ初期値を設定する。<br>
	 */
	protected void initPlatformSystemVoFields() {
		// VO準備
		PlatformSystemVo vo = (PlatformSystemVo)mospParams.getVo();
		// システム日付取得
		Date date = getSystemDate();
		// 初期値設定
		setSearchActivateDate(date);
		vo.setPltSearchInactivate(String.valueOf(MospConst.DELETE_FLAG_OFF));
		vo.setTxtUpdateActivateYear(getStringYear(date));
		vo.setTxtUpdateActivateMonth(getStringMonth(date));
		vo.setTxtUpdateActivateDay(getStringDay(date));
		vo.setPltUpdateInactivate(String.valueOf(MospConst.DELETE_FLAG_OFF));
	}
	
	/**
	 * 編集モード(新規登録)の設定を行う。<br>
	 * {@link PlatformSystemVo}のフィールドへ新規登録モード初期値を設定する。<br>
	 */
	protected void setEditInsertMode() {
		// VO準備
		PlatformSystemVo vo = (PlatformSystemVo)mospParams.getVo();
		// システム日付取得
		Date date = getSystemDate();
		// 初期値設定
		vo.setRecordId(0);
		vo.setTxtEditActivateYear(getStringYear(date));
		vo.setTxtEditActivateMonth(getStringMonth(date));
		vo.setTxtEditActivateDay(getStringDay(date));
		vo.setPltEditInactivate(String.valueOf(MospConst.DELETE_FLAG_OFF));
		// 編集モード設定
		vo.setModeCardEdit(PlatformConst.MODE_CARD_EDIT_INSERT);
	}
	
	/**
	 * 編集モード(履歴追加)の設定を行う。<br>
	 * {@link PlatformSystemVo}のフィールドへ履歴追加モード初期値を設定する。<br>
	 */
	protected void setEditAddMode() {
		// VO準備
		PlatformSystemVo vo = (PlatformSystemVo)mospParams.getVo();
		// 初期値設定
		vo.setTxtEditActivateYear("");
		vo.setTxtEditActivateMonth("");
		vo.setTxtEditActivateDay("");
		// 無効フラグ設定
		vo.setPltEditInactivate(String.valueOf(MospConst.DELETE_FLAG_OFF));
		// 編集モード設定
		vo.setModeCardEdit(PlatformConst.MODE_CARD_EDIT_ADD);
	}
	
	/**
	 * 編集モード(履歴編集)の設定を行う。<br>
	 * @param list 編集対象履歴リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setEditUpdateMode(List<? extends PlatformDtoInterface> list) throws MospException {
		// VO取得
		PlatformSystemVo vo = (PlatformSystemVo)mospParams.getVo();
		// 次前履歴情報設定
		setHistory(list);
		// 編集モード設定
		vo.setModeCardEdit(PlatformConst.MODE_CARD_EDIT_EDIT);
	}
	
	/**
	 * 次前履歴情報を設定する。<br>
	 * 履歴編集モードで利用する次前履歴移動のための情報を設定する。<br>
	 * @param list 対象コードの履歴リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setHistory(List<? extends PlatformDtoInterface> list) throws MospException {
		// VO準備
		PlatformSystemVo vo = (PlatformSystemVo)mospParams.getVo();
		// 次前履歴情報初期化
		vo.setLblNextActivateDate("");
		vo.setLblBackActivateDate("");
		// 編集対象有効日確認
		for (int i = 0; i < list.size(); i++) {
			// DTO取得
			PlatformDtoInterface dto = list.get(i);
			// 有効日確認
			if (dto.getActivateDate().compareTo(getEditActivateDate()) != 0) {
				// 有効日が異なる場合
				continue;
			}
			// 次履歴情報設定
			if (i + 1 < list.size()) {
				vo.setLblNextActivateDate(getStringDate(list.get(i + 1).getActivateDate()));
			}
			// 前履歴情報設定
			if (i > 0) {
				vo.setLblBackActivateDate(getStringDate(list.get(i - 1).getActivateDate()));
			}
			// 総履歴件数、編集中履歴順設定
			vo.setCountHistory(list.size());
			vo.setCurrentHistory(list.size() - i);
			// 設定完了
			break;
		}
	}
	
	/**
	 * 編集モード(複製)の設定を行う。<br>
	 * 実際には複製というモードは無く、新規登録モードを設定する。<br>
	 */
	protected void setEditReplicationMode() {
		// VO取得
		PlatformSystemVo vo = (PlatformSystemVo)mospParams.getVo();
		// 編集モード設定
		vo.setModeCardEdit(PlatformConst.MODE_CARD_EDIT_INSERT);
		// 有効無効フラグ設定
		vo.setPltEditInactivate(String.valueOf(MospConst.DELETE_FLAG_OFF));
	}
	
	/**
	 * VOから有効日(編集)を取得する。<br>
	 * @return 有効日(編集)
	 */
	protected Date getEditActivateDate() {
		// VO取得
		PlatformSystemVo vo = (PlatformSystemVo)mospParams.getVo();
		// 有効日取得
		return getDate(vo.getTxtEditActivateYear(), vo.getTxtEditActivateMonth(), vo.getTxtEditActivateDay());
	}
	
	/**
	 * VOから有効日(検索)を取得する。<br>
	 * @return 有効日(検索)
	 */
	protected Date getSearchActivateDate() {
		// VO準備
		PlatformSystemVo vo = (PlatformSystemVo)mospParams.getVo();
		// 有効日取得
		return getDate(vo.getTxtSearchActivateYear(), vo.getTxtSearchActivateMonth(), vo.getTxtSearchActivateDay());
	}
	
	/**
	 * VOから有効日(一括更新)を取得する。<br>
	 * @return 有効日(検索)
	 */
	protected Date getUpdateActivateDate() {
		// VO準備
		PlatformSystemVo vo = (PlatformSystemVo)mospParams.getVo();
		// 有効日取得
		return getDate(vo.getTxtUpdateActivateYear(), vo.getTxtUpdateActivateMonth(), vo.getTxtUpdateActivateDay());
	}
	
	/**
	 * 検索有効日を設定する。<br>
	 * @param date 設定する日付
	 */
	protected void setSearchActivateDate(Date date) {
		// VO取得
		PlatformSystemVo vo = (PlatformSystemVo)mospParams.getVo();
		// 設定日付確認
		if (date == null) {
			// nullの場合
			vo.setTxtSearchActivateYear("");
			vo.setTxtSearchActivateMonth("");
			vo.setTxtSearchActivateDay("");
		}
		// 検索有効日設定
		vo.setTxtSearchActivateYear(getStringYear(date));
		vo.setTxtSearchActivateMonth(getStringMonth(date));
		vo.setTxtSearchActivateDay(getStringDay(date));
	}
	
}
