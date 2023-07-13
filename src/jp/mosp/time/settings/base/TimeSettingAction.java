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
package jp.mosp.time.settings.base;

import java.util.Date;
import java.util.List;

import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.base.PlatformDtoInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.system.base.PlatformSystemVo;
import jp.mosp.time.base.TimeAction;

/**
 * MosP勤怠管理勤怠設定におけるActionの基本機能を提供する。<br>
 */
public abstract class TimeSettingAction extends TimeAction {
	
	/**
	 * {@link PlatformSystemVo}のフィールドへ初期値を設定する。<br>
	 */
	protected void initTimeSettingVoFields() {
		// VO準備
		TimeSettingVo vo = (TimeSettingVo)mospParams.getVo();
		// システム日付取得
		Date date = getSystemDate();
		// 初期値設定
		setSearchActivateDate(date);
		vo.setPltSearchInactivate(String.valueOf(MospConst.INACTIVATE_FLAG_OFF));
		vo.setTxtUpdateActivateYear(getStringYear(date));
		vo.setTxtUpdateActivateMonth(getStringMonth(date));
		vo.setTxtUpdateActivateDay(getStringDay(date));
		vo.setPltUpdateInactivate(String.valueOf(MospConst.INACTIVATE_FLAG_OFF));
	}
	
	/**
	 * 編集モード(新規登録)の設定を行う。<br>
	 * {@link TimeSettingVo}のフィールドへ新規登録モード初期値を設定する。<br>
	 */
	protected void setEditInsertMode() {
		// VO準備
		TimeSettingVo vo = (TimeSettingVo)mospParams.getVo();
		// システム日付取得
		Date date = getSystemDate();
		// 初期値設定
		vo.setTxtEditActivateYear(DateUtility.getStringYear(date));
		vo.setTxtEditActivateMonth(DateUtility.getStringMonth(date));
		vo.setTxtEditActivateDay(DateUtility.getStringDay(date));
		vo.setPltEditInactivate(String.valueOf(MospConst.INACTIVATE_FLAG_OFF));
		// 編集モード設定
		vo.setModeCardEdit(PlatformConst.MODE_CARD_EDIT_INSERT);
	}
	
	/**
	 * 編集モード(履歴追加)の設定を行う。<br>
	 * {@link TimeSettingVo}のフィールドへ履歴追加モード初期値を設定する。<br>
	 */
	protected void setEditAddMode() {
		// VO準備
		TimeSettingVo vo = (TimeSettingVo)mospParams.getVo();
		// 初期値設定
		vo.setTxtEditActivateYear("");
		vo.setTxtEditActivateMonth("");
		vo.setTxtEditActivateDay("");
		// 無効フラグ設定
		vo.setPltEditInactivate(String.valueOf(MospConst.INACTIVATE_FLAG_OFF));
		// 編集モード設定
		vo.setModeCardEdit(PlatformConst.MODE_CARD_EDIT_ADD);
	}
	
	/**
	 * 編集モード(履歴編集)の設定を行う。<br>
	 * @param list 編集対象履歴リスト
	 */
	protected void setEditUpdateMode(List<? extends PlatformDtoInterface> list) {
		// VO取得
		TimeSettingVo vo = (TimeSettingVo)mospParams.getVo();
		// 次前履歴情報設定
		setHistory(list);
		// 編集モード設定
		vo.setModeCardEdit(PlatformConst.MODE_CARD_EDIT_EDIT);
	}
	
	/**
	 * 編集モード(複製)の設定を行う。<br>
	 * 実際には複製というモードは無く、新規登録モードを設定する。<br>
	 */
	protected void setEditReplicationMode() {
		// VO取得
		TimeSettingVo vo = (TimeSettingVo)mospParams.getVo();
		// 編集モード設定
		vo.setModeCardEdit(PlatformConst.MODE_CARD_EDIT_INSERT);
		// 有効無効フラグ設定
		vo.setPltEditInactivate(String.valueOf(MospConst.INACTIVATE_FLAG_OFF));
	}
	
	/**
	 * 次前履歴情報を設定する。<br>
	 * 履歴編集モードで利用する次前履歴移動のための情報を設定する。<br>
	 * @param list 対象コードの履歴リスト
	 */
	protected void setHistory(List<? extends PlatformDtoInterface> list) {
		// VO準備
		TimeSettingVo vo = (TimeSettingVo)mospParams.getVo();
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
	 * VOからレコード識別IDを取得する。<br>
	 * @return レコード識別ID
	 */
	protected long getRecordId() {
		// VOを準備
		TimeSettingVo vo = (TimeSettingVo)mospParams.getVo();
		// レコード識別IDを取得
		return vo.getRecordId();
	}
	
	/**
	 * プルダウン用配列から、コードに対応する名称を取得する。<br>
	 * {@link MospUtility#getCodeName(String, String[][])}参照。<br>
	 * @param code  対象コード
	 * @param array プルダウン用配列
	 * @return コード名称
	 */
	protected String getCodeName(String code, String[][] array) {
		return MospUtility.getCodeName(code, array);
	}
	
}
