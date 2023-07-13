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
/**
 * 
 */
package jp.mosp.time.bean;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.SubstituteDtoInterface;

/**
 * 振替休日情報参照処理インターフェース。<br>
 */
public interface SubstituteReferenceBeanInterface extends BaseBeanInterface {
	
	/**
	 * 振替休日データを取得する。<br>
	 * 個人IDと出勤日（振替出勤日）から振替休日データを取得。<br>
	 * 
	 * 次の理由から個人IDと出勤日からは1つの振替休日データしか取得できない。<br>
	 * ・同一振替出勤日に2つの振替出勤をすることはできない。<br>
	 * ・一つの振替出勤からは一つの振替出勤しかできない。<br>
	 * ・取下状態は省く。<br>
	 * @param personalId 個人ID
	 * @param workDate 出勤日
	 * @return 振替休日データ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	SubstituteDtoInterface getSubstituteDto(String personalId, Date workDate) throws MospException;
	
	/**
	 * 一覧取得。
	 * 個人IDと振替日から振替休日データリストを取得。
	 * @param personalId 個人ID
	 * @param substituteDate 振替日
	 * @return 振替休日データリスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<SubstituteDtoInterface> getSubstituteList(String personalId, Date substituteDate) throws MospException;
	
	/**
	 * 一覧取得。
	 * ワークフロー番号から振替休日データリストを取得。
	 * @param workflow ワークフロー番号
	 * @return 振替休日データリスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<SubstituteDtoInterface> getSubstituteList(long workflow) throws MospException;
	
	/**
	 * ワークフロー番号から、振替休日の日を取得する。<br>
	 * 取得に失敗した場合は、nullを返す。<br>
	 * @param workflow ワークフロー番号
	 * @return 振替休日の日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Date getSubstituteDate(long workflow) throws MospException;
	
	/**
	 * 振替休日情報リストを取得する。<br>
	 * 対象個人IDの対象期間における申請を取得する。<br>
	 * @param personalId 対象個人ID
	 * @param firstDate  対象期間初日
	 * @param lastDate   対象期間最終日
	 * @return 振替休日情報リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<SubstituteDtoInterface> getSubstituteList(String personalId, Date firstDate, Date lastDate)
			throws MospException;
	
	/**
	 * 振替休日情報群(キー：個人ID)を取得する。<br>
	 * 対象個人IDの振替日における振替休日情報群を取得する。<br>
	 * 半日振替をした場合に、同一振替日で2つ振替休日情報が作成されることがある。<br>
	 * @param personalIds    個人ID群
	 * @param substituteDate 振替日
	 * @return 振替休日情報群(キー：個人ID)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Map<String, Set<SubstituteDtoInterface>> getSubstitutes(Collection<String> personalIds, Date substituteDate)
			throws MospException;
	
	/**
	 * 振替休日情報群(キー：振替日)を取得する。<br>
	 * 対象個人IDの対象期間(振替日)における振替休日情報群を取得する。<br>
	 * 半日振替をした場合に、同一振替日で2つ振替休日情報が作成されることがある。<br>
	 * @param personalId 対象個人ID
	 * @param firstDate  対象期間初日(振替日)
	 * @param lastDate   対象期間最終日(振替日)
	 * @return 振替休日情報群(キー：振替日)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Map<Date, List<SubstituteDtoInterface>> getSubstitutes(String personalId, Date firstDate, Date lastDate)
			throws MospException;
	
	/**
	 * 振替日群(キー：出勤日)を取得する。<br>
	 * 対象出勤日における振替休日情報群を取得し、振替日群(キー：出勤日)を作成する。<br>
	 * 同一日に振出・休出申請は一つしかできないため、出勤日に対しては振休日は一意に決まる。<br>
	 * @param personalId 個人ID
	 * @param workDates  出勤日群
	 * @return 振替休日情報群(キー：出勤日)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Map<Date, Date> getSubstituteDates(String personalId, Set<Date> workDates) throws MospException;
	
	/**
	 * 基本情報チェック
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void chkBasicInfo(String personalId, Date targetDate) throws MospException;
}
