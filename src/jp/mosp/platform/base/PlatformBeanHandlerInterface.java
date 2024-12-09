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
package jp.mosp.platform.base;

import jp.mosp.framework.base.BaseBeanHandlerInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.bean.file.ExportFieldRegistBeanInterface;
import jp.mosp.platform.bean.file.ExportRegistBeanInterface;
import jp.mosp.platform.bean.file.HumanImportBeanInterface;
import jp.mosp.platform.bean.file.ImportBeanInterface;
import jp.mosp.platform.bean.file.ImportFieldRegistBeanInterface;
import jp.mosp.platform.bean.file.ImportRegistBeanInterface;
import jp.mosp.platform.bean.file.PositionImportBeanInterface;
import jp.mosp.platform.bean.file.SectionImportBeanInterface;
import jp.mosp.platform.bean.file.TemplateOutputBeanInterface;
import jp.mosp.platform.bean.file.UserExtraRoleImportBeanInterface;
import jp.mosp.platform.bean.file.UserImportBeanInterface;
import jp.mosp.platform.bean.file.UserPasswordImportBeanInterface;
import jp.mosp.platform.bean.human.AccountRegistBeanInterface;
import jp.mosp.platform.bean.human.AddressRegistBeanInterface;
import jp.mosp.platform.bean.human.ConcurrentRegistBeanInterface;
import jp.mosp.platform.bean.human.EntranceRegistBeanInterface;
import jp.mosp.platform.bean.human.HistoryBasicDeleteBeanInterface;
import jp.mosp.platform.bean.human.HumanArrayRegistBeanInterface;
import jp.mosp.platform.bean.human.HumanBinaryArrayRegistBeanInterface;
import jp.mosp.platform.bean.human.HumanBinaryHistoryRegistBeanInterface;
import jp.mosp.platform.bean.human.HumanBinaryNormalRegistBeanInterface;
import jp.mosp.platform.bean.human.HumanHistoryRegistBeanInterface;
import jp.mosp.platform.bean.human.HumanNormalRegistBeanInterface;
import jp.mosp.platform.bean.human.HumanRegistBeanInterface;
import jp.mosp.platform.bean.human.PhoneRegistBeanInterface;
import jp.mosp.platform.bean.human.RetirementRegistBeanInterface;
import jp.mosp.platform.bean.human.SuspensionRegistBeanInterface;
import jp.mosp.platform.bean.message.MessageRegistBeanInterface;
import jp.mosp.platform.bean.portal.AuthBeanInterface;
import jp.mosp.platform.bean.portal.MospUserBeanInterface;
import jp.mosp.platform.bean.portal.PasswordCheckBeanInterface;
import jp.mosp.platform.bean.portal.PortalBeanInterface;
import jp.mosp.platform.bean.portal.UserCheckBeanInterface;
import jp.mosp.platform.bean.system.AppPropertyRegistBeanInterface;
import jp.mosp.platform.bean.system.EmploymentContractRegistBeanInterface;
import jp.mosp.platform.bean.system.GeneralRegistBeanInterface;
import jp.mosp.platform.bean.system.IcCardRegistBeanInterface;
import jp.mosp.platform.bean.system.NamingRegistBeanInterface;
import jp.mosp.platform.bean.system.PositionRegistBeanInterface;
import jp.mosp.platform.bean.system.ReceptionIcCardRegistBeanInterface;
import jp.mosp.platform.bean.system.SectionRegistBeanInterface;
import jp.mosp.platform.bean.system.UserAccountRegistBeanInterface;
import jp.mosp.platform.bean.system.WorkPlaceRegistBeanInterface;
import jp.mosp.platform.bean.workflow.ApprovalRouteRegistBeanInterface;
import jp.mosp.platform.bean.workflow.ApprovalRouteUnitRegistBeanInterface;
import jp.mosp.platform.bean.workflow.ApprovalUnitRegistBeanInterface;
import jp.mosp.platform.bean.workflow.RouteApplicationRegistBeanInterface;
import jp.mosp.platform.bean.workflow.SubApproverRegistBeanInterface;
import jp.mosp.platform.bean.workflow.WorkflowCommentRegistBeanInterface;
import jp.mosp.platform.bean.workflow.WorkflowRegistBeanInterface;

/**
 * MosPプラットフォーム用BeanHandlerインターフェース。
 */
public interface PlatformBeanHandlerInterface extends BaseBeanHandlerInterface {
	
	/**
	 * @return 認証
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	AuthBeanInterface auth() throws MospException;
	
	/**
	 * MosPユーザ設定クラスを取得する。<br>
	 * @return MosPユーザ設定クラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	MospUserBeanInterface mospUser() throws MospException;
	
	/**
	 * @return ユーザ確認
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	UserCheckBeanInterface userCheck() throws MospException;
	
	/**
	 * @return パスワード確認
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	PasswordCheckBeanInterface passwordCheck() throws MospException;
	
	/**
	 * ユーザアカウント情報登録処理を取得する。
	 * @return ユーザアカウント情報登録処理
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	UserAccountRegistBeanInterface userAccountRegist() throws MospException;
	
	/**
	 * 雇用契約マスタ登録クラスを取得する。
	 * @return 雇用契約マスタ登録クラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	EmploymentContractRegistBeanInterface employmentContractRegist() throws MospException;
	
	/**
	 * 所属マスタ登録クラスを取得する。
	 * @return 所属マスタ登録クラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	SectionRegistBeanInterface sectionRegist() throws MospException;
	
	/**
	 * 勤務地マスタ登録クラスを取得する。
	 * @return 勤務地マスタ登録クラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	WorkPlaceRegistBeanInterface workPlaceRegist() throws MospException;
	
	/**
	 * 職位マスタ登録クラスを取得する。
	 * @return 職位マスタ登録クラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	PositionRegistBeanInterface positionRegist() throws MospException;
	
	/**
	 * 名称区分マスタ登録クラスを取得する。
	 * @return 名称区分マスタ登録クラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	NamingRegistBeanInterface namingRegist() throws MospException;
	
	/**
	 * 人事マスタ登録クラスを取得する。
	 * @return 人事マスタ登録クラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	HumanRegistBeanInterface humanRegist() throws MospException;
	
	/**
	 * 個人基本情報履歴削除クラスを取得する。
	 * @return 個人基本情報履歴削除マスタ登録クラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	HistoryBasicDeleteBeanInterface historyBasicDelete() throws MospException;
	
	/**
	 * 人事入社情報登録クラスを取得する。
	 * @return 人事入社情報登録クラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	EntranceRegistBeanInterface entranceRegist() throws MospException;
	
	/**
	 * 人事退職情報登録クラスを取得する。
	 * @return 人事退職情報登録クラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	RetirementRegistBeanInterface retirementRegist() throws MospException;
	
	/**
	 * 人事休職情報登録クラスを取得する。
	 * @return 人事休職情報登録クラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	SuspensionRegistBeanInterface suspensionRegist() throws MospException;
	
	/**
	 * 人事兼務情報登録クラスを取得する。
	 * @return 人事兼務情報登録クラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	ConcurrentRegistBeanInterface concurrentRegist() throws MospException;
	
	/**
	 * 住所情報登録クラスを取得する。
	 * @return 住所情報登録クラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	AddressRegistBeanInterface addressRegist() throws MospException;
	
	/**
	 * 電話情報登録クラスを取得する。
	 * @return 電話情報登録クラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	PhoneRegistBeanInterface phoneRegist() throws MospException;
	
	/**
	 * 口座情報登録クラスを取得する。<br>
	 * @return 口座情報登録クラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	AccountRegistBeanInterface accountRegist() throws MospException;
	
	/**
	 * ICカードマスタ情報登録クラスを取得する。
	 * @return ワークフロー登録クラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	IcCardRegistBeanInterface icCardRegist() throws MospException;
	
	/**
	 * カードID受付情報登録クラスを取得する。
	 * @return ワークフロー登録クラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	ReceptionIcCardRegistBeanInterface receptionIcCardRegist() throws MospException;
	
	/**
	 * 人事汎用履歴情報登録クラスを取得する。
	 * @return 人事汎用履歴情報登録クラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	HumanHistoryRegistBeanInterface humanHistoryRegist() throws MospException;
	
	/**
	 * 人事汎用一覧情報登録クラスを取得する。
	 * @return 人事汎用一覧情報登録クラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	HumanArrayRegistBeanInterface humanArrayRegist() throws MospException;
	
	/**
	 * 人事汎用通常情報登録クラスを取得する。
	 * @return 人事汎用通常情報登録クラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	HumanNormalRegistBeanInterface humanNormalRegist() throws MospException;
	
	/**
	 * 人事汎用バイナリ履歴情報登録クラスを取得する。
	 * @return 人事汎用履歴情報登録クラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	HumanBinaryHistoryRegistBeanInterface humanBinaryHistoryRegist() throws MospException;
	
	/**
	 * 人事汎用バイナリ一覧情報登録クラスを取得する。
	 * @return 人事汎用一覧情報登録クラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	HumanBinaryArrayRegistBeanInterface humanBinaryArrayRegist() throws MospException;
	
	/**
	 * 人事汎用バイナリ通常情報登録クラスを取得する。
	 * @return 人事汎用通常情報登録クラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	HumanBinaryNormalRegistBeanInterface humanBinaryNormalRegist() throws MospException;
	
	/**
	 * ワークフロー登録クラスを取得する。
	 * @return ワークフロー登録クラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	WorkflowRegistBeanInterface workflowRegist() throws MospException;
	
	/**
	 * ワークフローコメント登録クラスを取得する。
	 * @return ワークフローコメント登録クラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	WorkflowCommentRegistBeanInterface workflowCommentRegist() throws MospException;
	
	/**
	 * 承認ユニットマスタ登録クラスを取得する。
	 * @return 承認ユニットマスタ登録クラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	ApprovalUnitRegistBeanInterface approvalUnitRegist() throws MospException;
	
	/**
	 * 承認ルートマスタ登録クラスを取得する。
	 * @return 承認ルートマスタ登録クラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	ApprovalRouteRegistBeanInterface approvalRouteRegist() throws MospException;
	
	/**
	 * 承認ルートユニットマスタ登録クラスを取得する。
	 * @return 承認ルートユニットマスタ登録クラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	ApprovalRouteUnitRegistBeanInterface approvalRouteUnitRegist() throws MospException;
	
	/**
	 * ルート適用マスタ登録クラスを取得する。
	 * @return ルート適用マスタ登録クラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	RouteApplicationRegistBeanInterface routeApplicationRegist() throws MospException;
	
	/**
	 * 代理承認者登録クラスを取得する。
	 * @return 代理承認者登録クラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	SubApproverRegistBeanInterface subApproverRegist() throws MospException;
	
	/**
	 * メッセージ登録クラスを取得する。
	 * @return メッセージ登録クラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	MessageRegistBeanInterface messageRegist() throws MospException;
	
	/**
	 * 汎用情報登録クラスを取得する。
	 * @return 汎用情報登録クラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	GeneralRegistBeanInterface generalRegist() throws MospException;
	
	/**
	 * エクスポートマスタ登録クラスを取得する。
	 * @return エクスポートマスタ登録クラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	ExportRegistBeanInterface exportRegist() throws MospException;
	
	/**
	 * エクスポートフィールド情報登録クラスを取得する。
	 * @return エクスポートフィールド情報登録クラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	ExportFieldRegistBeanInterface exportFieldRegist() throws MospException;
	
	/**
	 * テンプレート出力クラスを取得する。
	 * @return テンプレート出力クラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	TemplateOutputBeanInterface templateOutput() throws MospException;
	
	/**
	 * インポートマスタ登録クラスを取得する。
	 * @return インポートマスタ登録クラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	ImportRegistBeanInterface importRegist() throws MospException;
	
	/**
	 * インポートフィールド情報登録クラスを取得する。
	 * @return インポートフィールド情報登録クラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	ImportFieldRegistBeanInterface importFieldRegist() throws MospException;
	
	/**
	 * 人事マスタインポートクラスを取得する。
	 * @return 人事マスタインポートクラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	HumanImportBeanInterface humanImport() throws MospException;
	
	/**
	 * ユーザマスタインポートクラスを取得する。
	 * @return ユーザマスタインポートクラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	UserImportBeanInterface userImport() throws MospException;
	
	/**
	 * ユーザ追加ロールインポート処理を取得する。
	 * @return ユーザ追加ロールインポート処理
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	UserExtraRoleImportBeanInterface userExtraRoleImport() throws MospException;
	
	/**
	 * 所属マスタインポートクラスを取得する。
	 * @return 所属マスタインポートクラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	SectionImportBeanInterface sectionImport() throws MospException;
	
	/**
	 * 職位マスタインポートクラスを取得する。
	 * @return 職位マスタインポートクラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	PositionImportBeanInterface positionImport() throws MospException;
	
	/**
	 * ユーザパスワード情報インポートクラスを取得する。
	 * @return ユーザパスワード情報インポートクラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	UserPasswordImportBeanInterface userPasswordImport() throws MospException;
	
	/**
	 * ユニット情報(所属)インポートクラスを取得する。
	 * @return ユニット情報(所属)インポートクラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	ImportBeanInterface unitSectionImport() throws MospException;
	
	/**
	 * ユニット情報(個人)インポートクラスを取得する。
	 * @return ユニット情報(個人)インポートクラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	ImportBeanInterface unitPersonImport() throws MospException;
	
	/**
	 * ポータル用Beanクラスを取得する。
	 * @param className クラス名
	 * @return ポータル用Beanクラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	PortalBeanInterface portal(String className) throws MospException;
	
	/**
	 * 個人基本情報削除用Beanクラスを取得する。
	 * @param className クラス名
	 * @return 個人基本情報削除用Beanクラス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	HistoryBasicDeleteBeanInterface historyBasicDelete(String className) throws MospException;
	
	/**
	 * アプリケーション設定情報登録処理を取得する。<br>
	 * @return アプリケーション設定情報登録処理
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	AppPropertyRegistBeanInterface appPropertyRegist() throws MospException;
	
}
