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
package jp.mosp.time.bean;

import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.WorkTypeDtoInterface;
import jp.mosp.time.dto.settings.WorkTypeItemDtoInterface;

/**
 * 勤務形態設定追加処理インターフェース。<br>
 * アドオンで勤務形態設定に処理を追加したい場合に用いる。<br>
 */
public interface WorkTypeBeanInterface {
	
	/**
	 * 勤務形態設定詳細画面VOに初期値を設定する。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void initVoFields() throws MospException;
	
	/**
	 * 勤務形態設定詳細画面VOに値を設定する。<br>
	 * @param dto 勤務形態管理DTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void setVoFields(WorkTypeDtoInterface dto) throws MospException;
	
	/**
	 * 勤務形態設定詳細画面VOにリクエストパラメータを設定する。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void mapping() throws MospException;
	
	/**
	 * 勤務形態設定登録時追加処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void regist() throws MospException;
	
	/**
	 * 勤務形態設定削除時追加処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void delete() throws MospException;
	
	/**
	 * 追加で処理をする勤務形態項目情報リストを取得する。<br>
	 * @return 追加で処理をする勤務形態項目情報リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<WorkTypeItemDtoInterface> setDtoFieldsAddonItems() throws MospException;
	
	/**
	 * 履歴追加モードで画面を表示する際の追加処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void setEditAddMode() throws MospException;
}
