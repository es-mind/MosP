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
package jp.mosp.platform.bean.human;

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dto.human.SuspensionDtoInterface;

/**
 * 人事休職情報参照インターフェース。<br>
 */
public interface SuspensionReferenceBeanInterface extends BaseBeanInterface {
	
	/**
	 * 休職情報取得。
	 * <p>
	 * 個人IDと対象日から人事休職情報を取得。(休職チェック時使用)
	 * </p>
	 * @param personalId 個人ID
	 * @param targetDate 対象年月日
	 * @return 人事休職情報
	 * @throws MospException インスタンスの取得、SQLの作成及び実行に失敗した場合
	 */
	SuspensionDtoInterface getSuspentionInfo(String personalId, Date targetDate) throws MospException;
	
	/**
	 * 履歴一覧取得。
	 * <p>
	 * 個人IDから人事休職情報リストを取得する。
	 * </p>
	 * @param personalId 個人ID
	 * @return 人事休職情報リスト
	 * @throws MospException インスタンスの取得、SQLの作成及び実行に失敗した場合
	 */
	List<SuspensionDtoInterface> getSuspentionList(String personalId) throws MospException;
	
	/**
	 * 履歴一覧を取得する。<br>
	 * 休職終了日が対象日より前の情報は、取得しない。<br>
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @return 人事休職情報リスト
	 * @throws MospException インスタンスの取得、SQLの作成及び実行に失敗した場合
	 */
	List<SuspensionDtoInterface> getContinuedSuspentionList(String personalId, Date targetDate) throws MospException;
	
	/**
	 * 休職判断。
	 * <p>
	 * 個人IDと対象年月日から休職判断をする。
	 * </p>
	 * @param personalId 個人ID
	 * @param targetDate 対象年月日
	 * @return 対象日に休職している場合true、そうでない場合false。
	 * @throws MospException インスタンスの取得、SQLの作成及び実行に失敗した場合
	 */
	boolean isSuspended(String personalId, Date targetDate) throws MospException;
	
}
