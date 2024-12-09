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

import java.util.Date;
import java.util.List;
import java.util.Optional;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.impl.HolidayRemainDto;

/**
 * 有給休暇残数取得処理インターフェース。<br>
 */
public interface PaidHolidayRemainBeanInterface extends BaseBeanInterface {
	
	/**
	 * 対象日で有給休暇残情報リスト(取得日昇順)を取得する。<br>
	 * 休暇申請画面とインポート(休暇申請データ)で、有給休暇の残数確認に用いられる。<br>
	 * <br>
	 * 対象となる有給休暇情報は、次の全ての条件を満たす情報。<br>
	 * 1.有効日が対象日以前で最新(同一取得日別有効日の情報が存在する場合は1つのみを対象とする)<br>
	 * 2.取得日が対象日以前<br>
	 * 3.期限日が対象日以降<br>
	 * <br>
	 * 対象となる有給休暇トランザクション(手動付与)情報は、次の全ての条件を満たす情報。<br>
	 * 1.取得日が対象となる有給休暇情報の取得日と同一<br>
	 * 2.有効日が対象日以前<br>
	 * <br>
	 * 対象となる休暇申請情報は、次の全ての条件を満たす情報。<br>
	 * 1.申請済承認状況群(一次戻と下書と取下以外)である有給休暇申請<br>
	 * 2.休暇取得日が対象となる有給休暇情報の取得日と同一<br>
	 * <br>
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @return 休暇残情報リスト(取得日昇順)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<HolidayRemainDto> getPaidHolidayRemainsForRequest(String personalId, Date targetDate) throws MospException;
	
	/**
	 * 対象日で有給休暇残情報リスト(取得日昇順)を取得する。<br>
	 * 休暇申請画面で有給休暇情報の表示に用いられる。<br>
	 * <br>
	 * 対象となる有給休暇情報は、次の全ての条件を満たす情報。<br>
	 * 1.有効日が対象日以前で最新(同一取得日別有効日の情報が存在する場合は1つのみを対象とする)<br>
	 * 2.取得日が対象日以前<br>
	 * 3.期限日が対象日以降<br>
	 * <br>
	 * もし、取得日が対象日よりも後の有給休暇情報が存在する場合、これも対象とする(次年度付与分)。<br>
	 * (同一取得日別有効日の情報が存在する場合は、有効日が対象日以前で最も古いの情報のみを対象とする。)<br>
	 * <br>
	 * 対象となる有給休暇トランザクション(手動付与)情報は、次の全ての条件を満たす情報。<br>
	 * 1.取得日が対象となる有給休暇情報の取得日と同一<br>
	 * 2.有効日が対象日以前<br>
	 * <br>
	 * 対象となる休暇申請情報は、次の全ての条件を満たす情報。<br>
	 * 1.申請済承認状況群(一次戻と下書と取下以外)である有給休暇申請<br>
	 * 2.休暇取得日が対象となる有給休暇情報の取得日と同一<br>
	 * <br>
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @return 休暇残情報リスト(取得日昇順)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<HolidayRemainDto> getPaidHolidayRemainsForView(String personalId, Date targetDate) throws MospException;
	
	/**
	 * 対象日で有給休暇残情報リスト(取得日昇順)を取得する。<br>
	 * 個別有給休暇確認画面と有給休暇確認画面で用いられる。<br>
	 * <br>
	 * 対象となる有給休暇情報は、次の全ての条件を満たす情報。<br>
	 * 1.有効日が対象日以前で最新(同一取得日別有効日の情報が存在する場合は1つのみを対象とする)<br>
	 * 2.取得日が対象日以前<br>
	 * 3.期限日が対象日以降<br>
	 * <br>
	 * 対象となる有給休暇トランザクション(手動付与)情報は、次の全ての条件を満たす情報。<br>
	 * 1.取得日が対象となる有給休暇情報の取得日と同一<br>
	 * 2.有効日が対象日以前<br>
	 * <br>
	 * 対象となる休暇申請情報は、次の全ての条件を満たす情報。<br>
	 * 1.申請済承認状況群(一次戻と下書と取下以外)である有給休暇申請<br>
	 * 2.休暇取得日が対象となる有給休暇情報の取得日と同一<br>
	 * 3.申請日が対象日以前<br>
	 * <br>
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @return 休暇残情報リスト(取得日昇順)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<HolidayRemainDto> getPaidHolidayRemainsForList(String personalId, Date targetDate) throws MospException;
	
	/**
	 * 年度で有給休暇残情報を取得する。<br>
	 * 統計情報一覧画面で用いられる。<br>
	 * <br>
	 * 休暇残情報の
	 * 付与日数(及び付与時間数)には対象年度初日時点における残日数(及び残時間数)を、
	 * 残日数(及び残時間数)には対象日における残日数(及び残時間数)を、設定する。<br>
	 * 但し、残日数の計算に用いる申請は対象年度最終日までを対象とする。<br>
	 * <br>
	 * 対象日は、対象年度初日と対象年度最終日の間にあることを前提とする。<br>
	 * 取得する有給休暇残情報の休暇取得日と取得期限には、対象年度初日と対象日を設定する。<br>
	 * <br>
	 * 対象となる有給休暇情報は、次の全ての条件を満たす情報。<br>
	 * 1.有効日が対象日以前で最新(同一取得日別有効日の情報が存在する場合は1つのみを対象とする)<br>
	 * 2.取得日が対象日以前<br>
	 * 3.期限日が対象年度初日以降<br>
	 * <br>
	 * 対象となる有給休暇トランザクション(手動付与)情報は、次の全ての条件を満たす情報。<br>
	 * 1.取得日が対象となる有給休暇情報の取得日と同一<br>
	 * 2.有効日が対象日以前<br>
	 * <br>
	 * 対象となる休暇申請情報は、次の全ての条件を満たす情報。<br>
	 * 1.有効な承認状況群(下書と取下以外)である有給休暇申請<br>
	 * 2.休暇取得日が対象となる有給休暇情報の取得日と同一<br>
	 * 3.申請日が対象年度最終日以前<br>
	 * <br>
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @param targetYear 対象年度
	 * @return 休暇残情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	HolidayRemainDto getPaidHolidayRemainForYear(String personalId, Date targetDate, int targetYear)
			throws MospException;
	
	/**
	 * ストック休暇付与処理用に有給休暇残情報を取得する。<br>
	 * 有給休暇付与画面と勤怠集計管理画面で用いられる。<br>
	 * <br>
	 * まず、対象となる期限日を決定する(以下の方法で)。<br>
	 * 対象日以前に付与されており、対象日時点で期限切れとなっている有給休暇情報のうち、<br>
	 * 最も期限日が大きい有給休暇情報の期限日を取得する。<br>
	 * (同一取得日別有効日の情報が存在する場合は、有効日が対象日以前で最新の有給休暇情報のみを対象とする。)<br>
	 * 期限日が取得できなかった場合は、nullを返す。<br>
	 * <br>
	 * 有給休暇残情報の取得期限には、上記方法で決定された期限日が設定される。<br>
	 * <br>
	 * 対象となる有給休暇情報は、次の全ての条件を満たす情報。<br>
	 * 1.有効日が期限日以前で最新(同一取得日別有効日の情報が存在する場合は1つのみを対象とする)<br>
	 * 2.取得日が期限日以前<br>
	 * 3.期限日が期限日<br>
	 * <br>
	 * 対象となる有給休暇トランザクション(手動付与)情報は、次の全ての条件を満たす情報。<br>
	 * 1.取得日が対象となる有給休暇情報の取得日と同一<br>
	 * 2.有効日が期限日以前<br>
	 * <br>
	 * 対象となる休暇申請情報は、次の全ての条件を満たす情報。<br>
	 * 1.申請済承認状況群(一次戻と下書と取下以外)である有給休暇申請<br>
	 * 2.休暇取得日が対象となる有給休暇情報の取得日と同一<br>
	 * <br>
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @return 休暇残情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Optional<HolidayRemainDto> getPaidHolidayRemainForStock(String personalId, Date targetDate) throws MospException;
	
	/**
	 * 前年度有給休暇残日数を取得する。<br>
	 * 承認済休暇申請を対象とする。<br>
	 * エクスポート(有給休暇データ)で用いられる。<br>
	 * @param personalId      個人ID
	 * @param targetDate      対象日
	 * @param acquisitionDate 付与日(今年度に付与された有給休暇情報の付与日)
	 * @return 前年度有給休暇残日数
	 * @throws MospException
	 */
	double getCarryoverDays(String personalId, Date targetDate, Date acquisitionDate) throws MospException;
	
	/**
	 * 前年度有給休暇残時間数を取得する。<br>
	 * 承認済休暇申請を対象とする。<br>
	 * エクスポート(有給休暇データ)で用いられる。<br>
	 * @param personalId      個人ID
	 * @param targetDate      対象日
	 * @param acquisitionDate 付与日(今年度に付与された有給休暇情報の付与日)
	 * @return 前年度有給休暇残日数
	 * @throws MospException
	 */
	int getCarryoverHours(String personalId, Date targetDate, Date acquisitionDate) throws MospException;
	
	/**
	 * 対象日でストック休暇残情報リスト(取得日昇順)を取得する。<br>
	 * <br>
	 * 対象となるストック休暇情報は、次の全ての条件を満たす情報。<br>
	 * 1.有効日が対象日以前で最新(同一取得日別有効日の情報が存在する場合は1つのみを対象とする)<br>
	 * 2.取得日が対象日以前<br>
	 * 3.期限日が対象日以降<br>
	 * <br>
	 * 対象となるストック休暇トランザクション(手動付与)情報は、次の全ての条件を満たす情報。<br>
	 * 1.取得日が対象となる有給休暇情報の取得日と同一<br>
	 * 2.有効日が対象日以前<br>
	 * <br>
	 * 対象となる休暇申請情報は、次の全ての条件を満たす情報。<br>
	 * 1.申請済承認状況群(一次戻と下書と取下以外)であるストック休暇申請<br>
	 * 2.休暇取得日が対象となるストック休暇情報の取得日と同一<br>
	 * <br>
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @return 休暇残情報リスト(取得日昇順)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<HolidayRemainDto> getStockHolidayRemainsForRequest(String personalId, Date targetDate) throws MospException;
	
	/**
	 * 対象日でストック休暇残日数を取得する。<br>
	 * 休暇申請画面でストック休暇情報の表示に用いられる。<br>
	 * <br>
	 * {@link #getStockHolidayRemainsForRequest(String, Date)}
	 * で取得した休暇残情報の残日数を合算して、ストック休暇残日数を算出する。<br>
	 * <br>
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @return 休暇残情報リスト(取得日昇順)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	double getStockHolidayRemainDaysForView(String personalId, Date targetDate) throws MospException;
	
	/**
	 * 対象日でストック休暇残日数を取得する。<br>
	 * 有給休暇確認画面とストック休暇付与処理で用いられる。<br>
	 * <br>
	 * 対象となるストック休暇情報は、次の全ての条件を満たす情報。<br>
	 * 1.有効日が対象日以前で最新(同一取得日別有効日の情報が存在する場合は1つのみを対象とする)<br>
	 * 2.取得日が対象日以前<br>
	 * 3.期限日が対象日以降<br>
	 * <br>
	 * 対象となるストック休暇手動付与情報は、次の全ての条件を満たす情報。<br>
	 * 1.取得日が対象となる有給休暇情報の取得日と同一<br>
	 * 2.有効日が対象日以前<br>
	 * <br>
	 * 対象となる休暇申請情報は、次の全ての条件を満たす情報。<br>
	 * 1.申請済承認状況群(一次戻と下書と取下以外)であるストック休暇申請<br>
	 * 2.休暇取得日が対象となるストック休暇情報の取得日と同一<br>
	 * 3.申請日が対象日以前<br>
	 * <br>
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @return ストック休暇残日数
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	double getStockHolidayRemainDaysForList(String personalId, Date targetDate) throws MospException;
	
	/**
	 * 年度でストック休暇残情報を取得する。<br>
	 * 統計情報一覧画面で用いられる。<br>
	 * <br>
	 * 休暇残情報の
	 * 付与日数には対象年度初日時点における残日数を、
	 * 残日数には対象日における残日数を、設定する。<br>
	 * 但し、残日数の計算に用いる申請は対象年度最終日までを対象とする。<br>
	 * <br>
	 * 対象日は、対象年度初日と対象年度最終日の間にあることを前提とする。<br>
	 * 取得するストック休暇残情報の休暇取得日と取得期限には、対象年度初日と対象日を設定する。<br>
	 * <br>
	 * 対象となるストック休暇情報は、次の全ての条件を満たす情報。<br>
	 * 1.有効日が対象日以前で最新(同一取得日別有効日の情報が存在する場合は1つのみを対象とする)<br>
	 * 2.取得日が対象日以前<br>
	 * 3.期限日が対象年度初日以降<br>
	 * <br>
	 * 対象となるストック休暇手動付与情報は、次の全ての条件を満たす情報。<br>
	 * 1.取得日が対象となる有給休暇情報の取得日と同一<br>
	 * 2.有効日が対象日以前<br>
	 * <br>
	 * 対象となる休暇申請情報は、次の全ての条件を満たす情報。<br>
	 * 1.有効な承認状況群(下書と取下以外)であるストック休暇申請<br>
	 * 2.休暇取得日が対象となるストック休暇情報の取得日と同一<br>
	 * 3.申請日が対象年度最終日以前<br>
	 * <br>
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @param targetYear 対象年度
	 * @return 休暇残情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	HolidayRemainDto getStockHolidayRemainForYear(String personalId, Date targetDate, int targetYear)
			throws MospException;
	
	/**
	 * 勤怠関連マスタ参照処理を設定する。<br>
	 * @param timeMaster 勤怠関連マスタ参照処理
	 */
	void setTimeMaster(TimeMasterBeanInterface timeMaster);
	
}
