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
package jp.mosp.platform.base;

import jp.mosp.framework.base.BaseBeanHandlerInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.bean.file.ExportFieldReferenceBeanInterface;
import jp.mosp.platform.bean.file.ExportReferenceBeanInterface;
import jp.mosp.platform.bean.file.ExportSearchBeanInterface;
import jp.mosp.platform.bean.file.HumanExportBeanInterface;
import jp.mosp.platform.bean.file.HumanImportBeanInterface;
import jp.mosp.platform.bean.file.ImportFieldReferenceBeanInterface;
import jp.mosp.platform.bean.file.ImportReferenceBeanInterface;
import jp.mosp.platform.bean.file.ImportSearchBeanInterface;
import jp.mosp.platform.bean.file.SectionExportBeanInterface;
import jp.mosp.platform.bean.file.TableTypeBeanInterface;
import jp.mosp.platform.bean.file.UserExportBeanInterface;
import jp.mosp.platform.bean.file.UserExtraRoleExportBeanInterface;
import jp.mosp.platform.bean.human.AccountReferenceBeanInterface;
import jp.mosp.platform.bean.human.ConcurrentReferenceBeanInterface;
import jp.mosp.platform.bean.human.EmployeeNumberingBeanInterface;
import jp.mosp.platform.bean.human.EntranceReferenceBeanInterface;
import jp.mosp.platform.bean.human.HumanArrayReferenceBeanInterface;
import jp.mosp.platform.bean.human.HumanBinaryArrayReferenceBeanInterface;
import jp.mosp.platform.bean.human.HumanBinaryHistoryReferenceBeanInterface;
import jp.mosp.platform.bean.human.HumanBinaryNormalReferenceBeanInterface;
import jp.mosp.platform.bean.human.HumanHistoryReferenceBeanInterface;
import jp.mosp.platform.bean.human.HumanInfoExtraBeanInterface;
import jp.mosp.platform.bean.human.HumanNormalReferenceBeanInterface;
import jp.mosp.platform.bean.human.HumanReferenceBeanInterface;
import jp.mosp.platform.bean.human.HumanSearchBeanInterface;
import jp.mosp.platform.bean.human.HumanSubordinateBeanInterface;
import jp.mosp.platform.bean.human.PhoneReferenceBeanInterface;
import jp.mosp.platform.bean.human.RetirementReferenceBeanInterface;
import jp.mosp.platform.bean.human.SuspensionReferenceBeanInterface;
import jp.mosp.platform.bean.message.MessageMailBeanInterface;
import jp.mosp.platform.bean.message.MessageReferenceBeanInterface;
import jp.mosp.platform.bean.message.MessageSearchBeanInterface;
import jp.mosp.platform.bean.portal.PreActionBeanInterface;
import jp.mosp.platform.bean.system.BankBaseReferenceBeanInterface;
import jp.mosp.platform.bean.system.BankBranchReferenceBeanInterface;
import jp.mosp.platform.bean.system.EmploymentContractReferenceBeanInterface;
import jp.mosp.platform.bean.system.EmploymentContractSearchBeanInterface;
import jp.mosp.platform.bean.system.GeneralReferenceBeanInterface;
import jp.mosp.platform.bean.system.IcCardReferenceBeanInterface;
import jp.mosp.platform.bean.system.IcCardSearchBeanInterface;
import jp.mosp.platform.bean.system.NamingReferenceBeanInterface;
import jp.mosp.platform.bean.system.NamingSearchBeanInterface;
import jp.mosp.platform.bean.system.PlatformMasterBeanInterface;
import jp.mosp.platform.bean.system.PositionReferenceBeanInterface;
import jp.mosp.platform.bean.system.PositionSearchBeanInterface;
import jp.mosp.platform.bean.system.PostalCodeReferenceBeanInterface;
import jp.mosp.platform.bean.system.ReceptionIcCardReferenceBeanInterface;
import jp.mosp.platform.bean.system.RoleReferenceBeanInterface;
import jp.mosp.platform.bean.system.SectionReferenceBeanInterface;
import jp.mosp.platform.bean.system.SectionSearchBeanInterface;
import jp.mosp.platform.bean.system.UserExtraRoleReferenceBeanInterface;
import jp.mosp.platform.bean.system.UserMasterReferenceBeanInterface;
import jp.mosp.platform.bean.system.UserMasterSearchBeanInterface;
import jp.mosp.platform.bean.system.WorkPlaceReferenceBeanInterface;
import jp.mosp.platform.bean.system.WorkPlaceSearchBeanInterface;
import jp.mosp.platform.bean.workflow.ApprovalRouteReferenceBeanInterface;
import jp.mosp.platform.bean.workflow.ApprovalRouteSearchBeanInterface;
import jp.mosp.platform.bean.workflow.ApprovalRouteUnitReferenceBeanInterface;
import jp.mosp.platform.bean.workflow.ApprovalUnitReferenceBeanInterface;
import jp.mosp.platform.bean.workflow.ApprovalUnitSearchBeanInterface;
import jp.mosp.platform.bean.workflow.RouteApplicationReferenceBeanInterface;
import jp.mosp.platform.bean.workflow.RouteApplicationReferenceSearchBeanInterface;
import jp.mosp.platform.bean.workflow.RouteApplicationSearchBeanInterface;
import jp.mosp.platform.bean.workflow.SubApproverReferenceBeanInterface;
import jp.mosp.platform.bean.workflow.SubApproverSearchBeanInterface;
import jp.mosp.platform.bean.workflow.WorkflowCommentReferenceBeanInterface;
import jp.mosp.platform.bean.workflow.WorkflowIntegrateBeanInterface;
import jp.mosp.platform.bean.workflow.WorkflowReferenceBeanInterface;

/**
 * 参照用BeanHandlerインターフェース。
 */
public interface ReferenceBeanHandlerInterface extends BaseBeanHandlerInterface {
	
	/**
	 * @return ユーザマスタ参照
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	UserMasterReferenceBeanInterface user() throws MospException;
	
	/**
	 * ユーザ追加ロール参照処理を取得する。<br>
	 * @return ユーザ追加ロール参照処理
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	UserExtraRoleReferenceBeanInterface userExtraRole() throws MospException;
	
	/**
	 * @return 所属マスタ参照
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	SectionReferenceBeanInterface section() throws MospException;
	
	/**
	 * @return 職位マスタ参照
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	PositionReferenceBeanInterface position() throws MospException;
	
	/**
	 * @return 職位マスタ参照
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	PositionSearchBeanInterface positionSearch() throws MospException;
	
	/**
	 * @return 雇用契約マスタ参照
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	EmploymentContractReferenceBeanInterface employmentContract() throws MospException;
	
	/**
	 * @return ICカードマスタ参照
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	IcCardReferenceBeanInterface icCard() throws MospException;
	
	/**
	 * @return ICカード受付参照
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	ReceptionIcCardReferenceBeanInterface receptionIcCard() throws MospException;
	
	/**
	 * @return 名称区分マスタ参照
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	NamingReferenceBeanInterface naming() throws MospException;
	
	/**
	 * @return 郵便番号マスタ参照
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	PostalCodeReferenceBeanInterface postalCode() throws MospException;
	
	/**
	 * @return 銀行マスタ参照
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	BankBaseReferenceBeanInterface bankBase() throws MospException;
	
	/**
	 * @return 銀行支店マスタ参照
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	BankBranchReferenceBeanInterface bankBranch() throws MospException;
	
	/**
	 * 人事マスタ検索インスタンスを取得する。
	 * @return 人事マスタ検索インスタンス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	HumanSearchBeanInterface humanSearch() throws MospException;
	
	/**
	 * @return 部下検索処理インスタンス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	HumanSubordinateBeanInterface humanSubordinate() throws MospException;
	
	/**
	 * @return 人事マスタ参照
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	HumanReferenceBeanInterface human() throws MospException;
	
	/**
	 * @return 人事入社情報参照
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	EntranceReferenceBeanInterface entrance() throws MospException;
	
	/**
	 * @return 人事休職情報参照
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	SuspensionReferenceBeanInterface suspension() throws MospException;
	
	/**
	 * @return 人事退職情報参照
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	RetirementReferenceBeanInterface retirement() throws MospException;
	
	/**
	 * @return 人事兼務情報参照
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	ConcurrentReferenceBeanInterface concurrent() throws MospException;
	
	/**
	 * 電話情報参照クラスを取得する。
	 * @return 電話情報参照クラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	PhoneReferenceBeanInterface phone() throws MospException;
	
	/**
	 * 口座情報参照クラスを取得する。<br>
	 * @return 口座情報参照クラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	AccountReferenceBeanInterface account() throws MospException;
	
	/**
	 * @return 人事汎用履歴情報参照
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	HumanHistoryReferenceBeanInterface humanHistory() throws MospException;
	
	/**
	 * @return 人事汎用通常情報参照
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	HumanNormalReferenceBeanInterface humanNormal() throws MospException;
	
	/**
	 * @return 人事汎用一覧情報参照
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	HumanArrayReferenceBeanInterface humanArray() throws MospException;
	
	/**
	 * @return 人事汎用履歴情報参照
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	HumanBinaryHistoryReferenceBeanInterface humanBinaryHistory() throws MospException;
	
	/**
	 * @return 人事汎用バイナリ通常情報参照
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	HumanBinaryNormalReferenceBeanInterface humanBinaryNormal() throws MospException;
	
	/**
	 * @return 人事汎用バイナリ一覧情報参照
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	HumanBinaryArrayReferenceBeanInterface humanBinaryArray() throws MospException;
	
	/**
	 * @return 勤務地マスタ参照
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	WorkPlaceReferenceBeanInterface workPlace() throws MospException;
	
	/**
	 * @return 勤務地マスタ参照
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	WorkPlaceSearchBeanInterface workPlaceSearch() throws MospException;
	
	/**
	 * ロール参照インスタンスを取得する。
	 * @return ロール参照インスタンス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	RoleReferenceBeanInterface role() throws MospException;
	
	/**
	 * 雇用契約検索インスタンスを取得する。
	 * @return 雇用契約検索インスタンス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	EmploymentContractSearchBeanInterface employmentContractSearch() throws MospException;
	
	/**
	 * 所属検索インスタンスを取得する。
	 * @return 所属契約検索インスタンス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	SectionSearchBeanInterface sectionSearch() throws MospException;
	
	/**
	 * ICカード検索インスタンスを取得する。
	 * @return ICカードマスタ検索
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	IcCardSearchBeanInterface icCardSearch() throws MospException;
	
	/**
	 * 名称区分検索インスタンスを取得する。
	 * @return 名称区分検索インスタンス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	NamingSearchBeanInterface namingSearch() throws MospException;
	
	/**
	 * ユーザーマスタ検索インスタンスを取得する。
	 * @return ユーザーマスタ検索インスタンス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	UserMasterSearchBeanInterface userMasterSearch() throws MospException;
	
	/**
	 * @return ワークフロー統括
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	WorkflowIntegrateBeanInterface workflowIntegrate() throws MospException;
	
	/**
	 * @return ワークフロー参照
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	WorkflowReferenceBeanInterface workflow() throws MospException;
	
	/**
	 * @return ワークフローコメント参照
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	WorkflowCommentReferenceBeanInterface workflowComment() throws MospException;
	
	/**
	 * @return 承認ユニットマスタ参照インスタンス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	ApprovalUnitReferenceBeanInterface approvalUnit() throws MospException;
	
	/**
	 * @return 承認ユニットマスタ検索インスタンス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	ApprovalUnitSearchBeanInterface approvalUnitSearch() throws MospException;
	
	/**
	 * @return 承認ルートマスタ参照インスタンス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	ApprovalRouteReferenceBeanInterface approvalRoute() throws MospException;
	
	/**
	 * @return 承認ルートマスタ検索インスタンス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	ApprovalRouteSearchBeanInterface approvalRouteSearch() throws MospException;
	
	/**
	 * @return 承認ルートユニットマスタ参照インスタンス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	ApprovalRouteUnitReferenceBeanInterface approvalRouteUnit() throws MospException;
	
	/**
	 * @return 承認ルートマスタ参照インスタンス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	RouteApplicationReferenceBeanInterface routeApplication() throws MospException;
	
	/**
	 * @return 承認ルートマスタ検索インスタンス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	RouteApplicationSearchBeanInterface routeApplicationSearch() throws MospException;
	
	/**
	 * @return 承認ルート参照検索インスタンス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	RouteApplicationReferenceSearchBeanInterface routeApplicationReferenceSearch() throws MospException;
	
	/**
	 * @return 代理承認者参照クラスインスタンス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	SubApproverReferenceBeanInterface subApprover() throws MospException;
	
	/**
	 * @return 代理承認者検索クラスインスタンス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	SubApproverSearchBeanInterface subApproverSearch() throws MospException;
	
	/**
	 * @return メッセージ参照インスタンス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	MessageReferenceBeanInterface message() throws MospException;
	
	/**
	 * @return メッセージ検索インスタンス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	MessageSearchBeanInterface messageSearch() throws MospException;
	
	/**
	 * @return メール送信処理(メッセージ用)
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	MessageMailBeanInterface messageMail() throws MospException;
	
	/**
	 * @return 汎用マスタ参照インスタンス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	GeneralReferenceBeanInterface generalReference() throws MospException;
	
	/**
	 * @return エクスポートマスタ検索インスタンス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	ExportSearchBeanInterface exportSearch() throws MospException;
	
	/**
	 * @return エクスポートマスタ参照インスタンス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	ExportReferenceBeanInterface export() throws MospException;
	
	/**
	 * @return エクスポートフィールド参照インスタンス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	ExportFieldReferenceBeanInterface exportField() throws MospException;
	
	/**
	 * @return インポートマスタ検索インスタンス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	ImportSearchBeanInterface importSearch() throws MospException;
	
	/**
	 * @return インポートマスタ参照インスタンス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	ImportReferenceBeanInterface importRefer() throws MospException;
	
	/**
	 * @return インポートフィールド参照インスタンス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	ImportFieldReferenceBeanInterface importField() throws MospException;
	
	/**
	 * 人事マスタエクスポートクラスを取得する。
	 * @return 人事マスタエクスポートクラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	HumanExportBeanInterface humanExport() throws MospException;
	
	/**
	 * 人事マスタインポートクラスを取得する。
	 * @return 人事マスタインポートクラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	HumanImportBeanInterface humanImport() throws MospException;
	
	/**
	 * ユーザマスタエクスポートクラスを取得する。
	 * @return ユーザマスタエクスポートクラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	UserExportBeanInterface userExport() throws MospException;
	
	/**
	 * ユーザ追加ロール情報エクスポート処理を取得する。
	 * @return ユーザ追加ロール情報エクスポート処理
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	UserExtraRoleExportBeanInterface userExtraRoleExport() throws MospException;
	
	/**
	 * 所属マスタエクスポートクラスを取得する。
	 * @return 所属マスタエクスポートクラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	SectionExportBeanInterface sectionExport() throws MospException;
	
	/**
	 * 社員コード採番クラスを取得する。<br>
	 * @return 社員コード採番クラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	EmployeeNumberingBeanInterface employeeNumbering() throws MospException;
	
	/**
	 * 人事情報一覧画面追加情報用Beanクラスを取得する。
	 * @param className クラス名
	 * @return 人事情報一覧画面追加情報用Beanクラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	HumanInfoExtraBeanInterface humanInfoExtra(String className) throws MospException;
	
	/**
	 * テーブル区分配列(インポート及びエクスポート)取得処理処理を取得する。<br>
	 * @return テーブル区分配列(インポート及びエクスポート)取得処理
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	TableTypeBeanInterface tableType() throws MospException;
	
	/**
	 * アクション前処理クラスを取得する。<br>
	 * @return アクション前処理クラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	PreActionBeanInterface preAction() throws MospException;
	
	/**
	 * プラットフォームマスタ参照処理を取得する。<br>
	 * @return プラットフォームマスタ参照処理
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	PlatformMasterBeanInterface master() throws MospException;
	
}
