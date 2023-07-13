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
import java.util.Map;
import java.util.Set;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.OvertimeRequestDtoInterface;

/**
 * 残業申請参照処理インターフェース。<br>
 */
public interface OvertimeRequestReferenceBeanInterface extends BaseBeanInterface {
	
	/**
	 * 残業申請情報リストを取得する。<br>
	 * 対象個人IDの対象期間における申請を取得する。<br>
	 * @param personalId 対象個人ID
	 * @param firstDate  対象期間初日
	 * @param lastDate   対象期間最終日
	 * @return 残業申請情報リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<OvertimeRequestDtoInterface> getOvertimeRequestList(String personalId, Date firstDate, Date lastDate)
			throws MospException;
	
	/**
	 * 残業申請情報群(キー：個人ID)を取得する。<br>
	 * 個人ID群の申請日における申請を取得する。<br>
	 * @param personalIds 個人ID群
	 * @param requestDate 申請日
	 * @return 残業申請情報群(キー：個人ID)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Map<String, Set<OvertimeRequestDtoInterface>> getOvertimeRequests(Collection<String> personalIds, Date requestDate)
			throws MospException;
	
	/**
	 * 残業申請情報群(キー：申請日)を取得する。<br>
	 * 対象個人IDの対象期間における申請を取得する。<br>
	 * @param personalId 対象個人ID
	 * @param firstDate  対象期間初日
	 * @param lastDate   対象期間最終日
	 * @return 残業申請情報群(キー：申請日)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Map<Date, List<OvertimeRequestDtoInterface>> getOvertimeRequests(String personalId, Date firstDate, Date lastDate)
			throws MospException;
	
	/**
	 * 残業申請データからレコードを取得する。<br>
	 * 個人ID、申請日、残業区分で合致するレコードが無い場合、nullを返す。<br>
	 * @param personalId 個人ID
	 * @param requestDate 申請日
	 * @param overtimeType 残業区分
	 * @return 残業申請データDTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	OvertimeRequestDtoInterface findForKeyOnWorkflow(String personalId, Date requestDate, int overtimeType)
			throws MospException;
	
	/**
	 * ワークフロー番号からレコードを取得する。<br>
	 * ワークフロー番号で合致するレコードが無い場合、nullを返す。<br>
	 * @param workflow ワークフロー番号
	 * @return 残業申請データDTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	OvertimeRequestDtoInterface findForWorkflow(long workflow) throws MospException;
	
	/**
	 * 残業申請データ取得。
	 * <p>
	 * レコード識別IDから残業申請データを取得。
	 * </p>
	 * @param id レコード識別ID
	 * @return 残業申請データ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	OvertimeRequestDtoInterface findForKey(long id) throws MospException;
	
	/**
	 * 基本情報チェック
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void chkBasicInfo(String personalId, Date targetDate) throws MospException;
	
}
