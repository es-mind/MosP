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

import java.util.Date;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.WorkTypePatternItemDtoInterface;

/**
 * 勤務形態パターン項目登録インターフェース
 */
public interface WorkTypePatternItemRegistBeanInterface extends BaseBeanInterface {
	
	/**
	 * 登録用DTOを取得する。<br>
	 * @return 初期DTO
	 */
	WorkTypePatternItemDtoInterface getInitDto();
	
	/**
	 * 新規登録を行う。<br>
	 * @param patternCode パターンコード
	 * @param activateDate 有効日
	 * @param inactivateFlag 無効フラグ
	 * @param workTypeCodeArray 勤務形態コード配列
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void insert(String patternCode, Date activateDate, int inactivateFlag, String[] workTypeCodeArray)
			throws MospException;
	
	/**
	 * 履歴追加を行う。<br>
	 * @param patternCode パターンコード
	 * @param activateDate 有効日
	 * @param inactivateFlag 無効フラグ
	 * @param workTypeCodeArray 勤務形態コード配列
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void add(String patternCode, Date activateDate, int inactivateFlag, String[] workTypeCodeArray)
			throws MospException;
	
	/**
	 * 履歴更新を行う。<br>
	 * @param patternCode パターンコード
	 * @param activateDate 有効日
	 * @param inactivateFlag 無効フラグ
	 * @param workTypeCodeArray 勤務形態コード配列
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void update(String patternCode, Date activateDate, int inactivateFlag, String[] workTypeCodeArray)
			throws MospException;
	
	/**
	 * 論理削除(履歴)を行う。<br>
	 * @param patternCode パターンコード
	 * @param activateDate 有効日
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void delete(String patternCode, Date activateDate) throws MospException;
	
}
