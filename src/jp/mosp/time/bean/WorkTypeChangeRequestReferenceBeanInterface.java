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

import java.util.Collection;
import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.WorkTypeChangeRequestDtoInterface;

/**
 * 勤務形態変更申請参照インターフェース。
 */
public interface WorkTypeChangeRequestReferenceBeanInterface extends BaseBeanInterface {
	
	/**
	 * 勤務形態変更申請取得。<br>
	 * @param id レコード識別ID
	 * @return 勤務形態変更申請
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	WorkTypeChangeRequestDtoInterface findForKey(long id) throws MospException;
	
	/**
	 * 勤務形態変更申請取得。<br>
	 * @param personalId 個人ID
	 * @param requestDate 申請日
	 * @return 勤務形態変更申請
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	WorkTypeChangeRequestDtoInterface findForKeyOnWorkflow(String personalId, Date requestDate) throws MospException;
	
	/**
	 * ワークフロー番号からレコードを取得する。<br>
	 * ワークフロー番号で合致するレコードが無い場合、nullを返す。<br>
	 * @param workflow ワークフロー番号
	 * @return 勤務形態変更申請
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	WorkTypeChangeRequestDtoInterface findForWorkflow(long workflow) throws MospException;
	
	/**
	 * 勤務形態変更申請情報リストを取得する。<br>
	 * 対象個人IDの対象期間における申請を取得する。<br>
	 * @param personalId 対象個人ID
	 * @param firstDate  対象期間初日
	 * @param lastDate   対象期間末日
	 * @return 勤務形態変更申請情報リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<WorkTypeChangeRequestDtoInterface> getWorkTypeChangeRequestList(String personalId, Date firstDate,
			Date lastDate) throws MospException;
	
	/**
	 * 勤務形態変更申請情報リストを取得する。<br>
	 * 対象個人ID群の出勤日における申請を取得する。<br>
	 * @param personalIds 対象個人ID群
	 * @param requestDate 出勤日
	 * @return 勤務形態変更申請情報リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<WorkTypeChangeRequestDtoInterface> getWorkTypeChangeRequests(Collection<String> personalIds, Date requestDate)
			throws MospException;
	
}
