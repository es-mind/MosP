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
package jp.mosp.platform.bean.system;

import java.util.Date;
import java.util.List;
import java.util.Set;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.dto.human.RetirementDtoInterface;
import jp.mosp.platform.dto.human.SuspensionDtoInterface;
import jp.mosp.platform.dto.workflow.RouteApplicationDtoInterface;
import jp.mosp.platform.utils.PlatformUtility;

/**
 * プラットフォームマスタ参照処理インターフェース。<br>
 */
public interface PlatformMasterBeanInterface extends BaseBeanInterface {
	
	/**
	 * 対象日時点における最新の有効な情報から、ルート適用情報を取得する。<br>
	 * <br>
	 * {@link PlatformUtility#getApplicationMaster(HumanDtoInterface, Set, Set)}
	 * 参照。<br>
	 * <br>
	 * @param humanDto     人事情報
	 * @param targetDate   対象日
	 * @param workflowType フロー区分
	 * @return 設定適用情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	RouteApplicationDtoInterface getRouteApplication(HumanDtoInterface humanDto, Date targetDate, int workflowType)
			throws MospException;
	
	/**
	 * 対象日時点における最新の有効な情報から、ルート適用情報を取得する。<br>
	 * <br>
	 * {@link PlatformMasterBeanInterface#getRouteApplication(HumanDtoInterface, Date, int)}
	 * 参照。<br>
	 * <br>
	 * @param personalId   個人ID
	 * @param targetDate   対象日
	 * @param workflowType フロー区分
	 * @return 設定適用情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	RouteApplicationDtoInterface getRouteApplication(String personalId, Date targetDate, int workflowType)
			throws MospException;
	
	/**
	 * 人事情報を取得する。<br>
	 * <br>
	 * 対象個人IDにつき、対象日以前で最新の人事情報を取得する。<br>
	 * <br>
	 * {@link PlatformMasterBeanInterface#getHumanHistory(String)}
	 * で人事情報履歴を取得する。<br>
	 * <br>
	 * @param personalId  個人ID
	 * @param targetDate  対象日
	 * @return 人事情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	HumanDtoInterface getHuman(String personalId, Date targetDate) throws MospException;
	
	/**
	 * 年月指定時の基準日で人事情報を取得する。<br>
	 * <br>
	 * {@link PlatformMasterBeanInterface#getHuman(String, Date)}
	 * 参照。<br>
	 * <br>
	 * @param personalId  個人ID
	 * @param targetYear  対象年
	 * @param targetMonth 対象月
	 * @return 人事情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	HumanDtoInterface getHuman(String personalId, int targetYear, int targetMonth) throws MospException;
	
	/**
	 * 人事情報履歴を取得する。<br>
	 * <br>
	 * 保持された人事情報履歴群に対象となる個人IDの履歴情報がある場合は、
	 * それを参照する。<br>
	 * 保持された人事情報履歴群に対象となる個人IDの履歴情報がない場合は、
	 * DBから情報を取得し、保持する。<br>
	 * <br>
	 * @param personalId 個人ID
	 * @return 人事情報履歴
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<HumanDtoInterface> getHumanHistory(String personalId) throws MospException;
	
	/**
	 * 人事退職情報を取得する。<br>
	 * 取得できなかった場合は、nullを取得する。<br>
	 * @param personalId 個人ID
	 * @return 人事退職情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	RetirementDtoInterface getRetirement(String personalId) throws MospException;
	
	/**
	 * 対象個人IDの対象日時点で退職しているかを確認する。
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @return 確認結果(true：退職している、false：退職していない)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	boolean isRetired(String personalId, Date targetDate) throws MospException;
	
	/**
	 * 人事休職情報リストを取得する。<br>
	 * 取得できなかった場合は、空のリストを取得する。<br>
	 * @param personalId 個人ID
	 * @return 人事休職情報リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<SuspensionDtoInterface> getSuspensions(String personalId) throws MospException;
	
	/**
	 * 対象個人IDの対象日が休職中であるかを確認する。
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @return 確認結果(true：休職中である、false：休職中でない)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	boolean isSuspended(String personalId, Date targetDate) throws MospException;
	
	/**
	 * 対象個人IDのメールアドレスを取得する。<br>
	 * 取得できなかった場合は、空文字を取得する。<br>
	 * @param personalId 個人ID
	 * @return メールアドレス
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String getMailAddress(String personalId) throws MospException;
	
	/**
	 * 所属名称を取得する。<br>
	 * @param sectionCode 所属コード
	 * @param targetDate  対象日
	 * @return 所属名称
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String getSectionName(String sectionCode, Date targetDate) throws MospException;
	
	/**
	 * 所属略称を取得する。<br>
	 * @param sectionCode 所属コード
	 * @param targetDate  対象日
	 * @return 所属略称
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String getSectionAbbr(String sectionCode, Date targetDate) throws MospException;
	
	/**
	 * 所属名称か表示名称を取得する。<br>
	 * @param sectionCode 所属コード
	 * @param targetDate  対象日
	 * @param mospParams  MosP処理情報
	 * @return 所属名称
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String getSectionNameOrDisplay(String sectionCode, Date targetDate, MospParams mospParams) throws MospException;
	
	/**
	 * アプリケーション設定値を取得する。<br>
	 * DBから取得できる場合はその値を、
	 * 取得できない場合は設定ファイル(XML)から取得する。<br>
	 * どちらからも取得できない場合は、空文字を返す。<br>
	 * <br>
	 * @param appKey MosPアプリケーション設定キー
	 * @return MosPアプリケーション設定値
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String getAppProperty(String appKey) throws MospException;
	
	/**
	 * アプリケーション設定値を取得する。<br>
	 * DBから取得できる場合はその値を、
	 * 取得できない場合は設定ファイル(XML)から取得する。<br>
	 * どちらからも取得できない場合は、0を返す。<br>
	 * <br>
	 * @param appKey MosPアプリケーション設定キー
	 * @return MosPアプリケーション設定値
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	int getAppPropertyInt(String appKey) throws MospException;
	
}
