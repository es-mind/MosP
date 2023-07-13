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
package jp.mosp.time.bean;

import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.impl.AttendanceListDto;

/**
 * 勤怠一覧追加処理インターフェース。<br>
 * アドオンで勤怠一覧に処理を追加したい場合に用いる。<br>
 */
public interface AttendanceListAddonBeanInterface {
	
	/**
	 * 初期化処理を実行する。<br>
	 * @throws MospException エラーが発生した場合
	 */
	void init() throws MospException;
	
	/**
	 * 勤怠一覧画面VOに値を設定する。<br>
	 * @param list 勤怠一覧情報リスト
	 * @throws MospException エラーが発生した場合
	 */
	void setVoFields(List<AttendanceListDto> list) throws MospException;
	
	/**
	 * 勤怠一覧画面VOにリクエストパラメータを設定する。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void mapping() throws MospException;
	
	/**
	 * 勤怠一覧下書き時追加処理を行う。<br>
	 * @throws MospException エラーが発生した場合
	 */
	void draft() throws MospException;
	
	/**
	 * 勤怠一覧申請時追加処理を行う。<br>
	 * @throws MospException エラーが発生した場合
	 */
	void appli() throws MospException;
	
	/**
	 * 勤怠一覧承認時追加処理を行う。<br>
	 * @throws MospException エラーが発生した場合
	 */
	void approve() throws MospException;
	
	/**
	 * 勤怠一覧集計時追加処理を行う。<br>
	 * @throws MospException エラーが発生した場合
	 */
	void total() throws MospException;
	
}
