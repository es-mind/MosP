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
/**
 * 
 */
package jp.mosp.time.bean;

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.ScheduleDateDtoInterface;

/**
 * カレンダ日登録インターフェース。
 */
public interface ScheduleDateRegistBeanInterface extends BaseBeanInterface {
	
	/**
	 * 登録用DTOを取得する。<br>
	 * @return 初期DTO
	 */
	ScheduleDateDtoInterface getInitDto();
	
	/**
	 * 新規登録を行う。<br>
	 * @param list 対象DTOリスト
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void insert(List<ScheduleDateDtoInterface> list) throws MospException;
	
	/**
	 * 履歴追加を行う。<br>
	 * @param list 対象DTOリスト
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void add(List<ScheduleDateDtoInterface> list) throws MospException;
	
	/**
	 * 履歴更新を行う。<br>
	 * @param list 対象DTOリスト
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void update(List<ScheduleDateDtoInterface> list) throws MospException;
	
	/**
	 * 一括更新処理を行う。<br>
	 * @param idArray 対象レコード識別ID配列
	 * @param activateDate 有効日
	 * @param inactivateFlag 無効フラグ
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void update(long[] idArray, Date activateDate, int inactivateFlag) throws MospException;
	
	/**
	 * 論理削除(履歴)を行う。<br>
	 * @param scheduleCode カレンダコード
	 * @param activateDate 有効日
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void delete(String scheduleCode, Date activateDate) throws MospException;
	
	/**
	 * 登録を行う。<br>
	 * 情報がないものは新規登録、情報があるものは履歴更新を行う。<br>
	 * 年度反映で利用する。<br>
	 * @param list 対象DTOリスト
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void allReflectionRegist(List<ScheduleDateDtoInterface> list) throws MospException;
	
}
