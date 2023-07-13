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
import java.util.List;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.ApplicationDtoInterface;
import jp.mosp.time.entity.ApplicationEntity;

/**
 * 設定適用管理参照インターフェース。<br>
 */
public interface ApplicationReferenceBeanInterface extends BaseBeanInterface {
	
	/**
	 * 履歴一覧を取得する。<br>
	 * 設定適用コードから設定適用マスタリストを取得。
	 * @param applicationCode 設定適用コード
	 * @return 設定適用マスタリスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<ApplicationDtoInterface> getApplicationHistory(String applicationCode) throws MospException;
	
	/**
	 * 設定適用マスタからレコードを取得する。<br>
	 * 設定適用コード、有効日で合致するレコードが無い場合、nullを返す。<br>
	 * @param applicationCode 設定適用コード
	 * @param targetDate 有効日
	 * @return 設定適用マスタDTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	ApplicationDtoInterface findForKey(String applicationCode, Date targetDate) throws MospException;
	
	/**
	 * 設定適用コードにつき、有効日より前の情報を取得する。<br>
	 * 有効無効は問わない。<br>
	 * @param applicationCode 設定適用コード
	 * @param activateDate 有効日
	 * @return 設定適用マスタ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	ApplicationDtoInterface findFormerInfo(String applicationCode, Date activateDate) throws MospException;
	
	/**
	 * 個人ID及び対象日から、適用されている設定を取得する。<br>
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @return 設定適用マスタDTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	ApplicationDtoInterface findForPerson(String personalId, Date targetDate) throws MospException;
	
	/**
	 * 設定適用マスタの存在チェック。<br>
	 * @param dto 対象設定適用
	 * @param targetDate メッセージ用の年月日
	 */
	void chkExistApplication(ApplicationDtoInterface dto, Date targetDate);
	
	/**
	 * 期間内に適用されている設定が存在するか確認する。<br>
	 * @param startDate 期間開始日
	 * @param endDate 期間終了日
	 * @param personalId 対象個人ID
	 * @return isExist (true：存在する、false：存在しない)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	boolean hasPersonalApplication(String personalId, Date startDate, Date endDate) throws MospException;
	
	/**
	 * 設定適用エンティティを取得する。<br>
	 * 設定適用情報が取得できない場合は、nullを返す。<br>
	 * @param personalId 対象個人ID
	 * @param targetDate 対象年月日
	 * @return 設定適用エンティティ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	ApplicationEntity getApplicationEntity(String personalId, Date targetDate) throws MospException;
	
	/**
	 * 設定適用エンティティを取得する。<br>
	 * 設定適用情報が取得できない場合は、nullを返す。<br>
	 * <br>
	 * 対象年月の基準日で、情報を取得する。<br>
	 * <br>
	 * @param personalId  対象個人ID
	 * @param targetYear  対象年
	 * @param targetMonth 対象月
	 * @return 設定適用エンティティ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	ApplicationEntity getApplicationEntity(String personalId, int targetYear, int targetMonth) throws MospException;
	
}
