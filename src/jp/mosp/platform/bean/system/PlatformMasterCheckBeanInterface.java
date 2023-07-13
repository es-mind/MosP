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

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;

/**
 * 所属・雇用契約・職位・勤務地マスタに関連する整合性確認インターフェース。
 */
public interface PlatformMasterCheckBeanInterface extends BaseBeanInterface {
	
	/**
	 * 勤務地を取得し、期間内に無効フラグがあるかの確認をする。<br>
	 * @param workPlaceCode 勤務地コード
	 * @param startDate 期間初日
	 * @param endDate 期間最終日
	 * @param row 行インデックス
	 * @return 確認結果(true：期間内全て有効、false：期間内無効有り)
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	boolean isCheckWorkPlace(String workPlaceCode, Date startDate, Date endDate, Integer row) throws MospException;
	
	/**
	 * 職位情報を取得し、期間内に無効フラグがあるかの確認をする。<br>
	 * @param positionCode 職位コード
	 * @param startDate 期間初日
	 * @param endDate 期間終了日
	 * @param row 行インデックス
	 * @return 確認結果(true：期間内全て有効、false：期間内無効有り)
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	boolean isCheckPosition(String positionCode, Date startDate, Date endDate, Integer row) throws MospException;
	
	/**
	 * 所属情報を取得し、期間内に無効フラグがあるかの確認をする。<br>
	 * @param sectionCode 所属コード
	 * @param startDate 期間初日
	 * @param endDate 期間終了日
	 * @param row 行インデックス
	 * @return 確認結果(true：期間内全て有効、false：期間内無効有り)
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	boolean isCheckSection(String sectionCode, Date startDate, Date endDate, Integer row) throws MospException;
	
	/**
	 * 雇用契約を取得し、期間内に無効フラグがあるかの確認をする。<br>
	 * @param employmentContractCode 雇用契約コード
	 * @param startDate 期間初日
	 * @param endDate 期間終了日
	 * @param row 行インデックス
	 * @return 確認結果(true：期間内全て有効、false：期間内無効有り)
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	boolean isCheckEmploymentContract(String employmentContractCode, Date startDate, Date endDate, Integer row)
			throws MospException;
	
	/**
	 * 名称区分マスタを取得し、期間内に無効フラグがあるかの確認をする。<br>
	 * @param namingType 名称区分
	 * @param namingItemCode 名称区分項目コード
	 * @param startDate 期間初日
	 * @param endDate 期間終了日
	 * @param row 行インデックス
	 * @return 確認結果(true：期間内全て有効、false：期間内無効有り)
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	boolean isCheckNaming(String namingType, String namingItemCode, Date startDate, Date endDate, Integer row)
			throws MospException;
	
	/**
	 * 勤務地を取得し、期間内に無効フラグがあるかの確認をする。<br>
	 * @param workPlaceCode 勤務地コード
	 * @param startDate 期間初日
	 * @param endDate 期間最終日
	 * @return 確認結果(true：期間内全て有効、false：期間内無効有り)
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	boolean isCheckWorkPlace(String workPlaceCode, Date startDate, Date endDate) throws MospException;
	
	/**
	 * 職位情報を取得し、期間内に無効フラグがあるかの確認をする。<br>
	 * @param positionCode 職位コード
	 * @param startDate 期間初日
	 * @param endDate 期間終了日
	 * @return 確認結果(true：期間内全て有効、false：期間内無効有り)
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	boolean isCheckPosition(String positionCode, Date startDate, Date endDate) throws MospException;
	
	/**
	 * 所属情報を取得し、期間内に無効フラグがあるかの確認をする。<br>
	 * @param sectionCode 所属コード
	 * @param startDate 期間初日
	 * @param endDate 期間終了日
	 * @return 確認結果(true：期間内全て有効、false：期間内無効有り)
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	boolean isCheckSection(String sectionCode, Date startDate, Date endDate) throws MospException;
	
	/**
	 * 雇用契約を取得し、期間内に無効フラグがあるかの確認をする。<br>
	 * @param employmentContractCode 雇用契約コード
	 * @param startDate 期間初日
	 * @param endDate 期間終了日
	 * @return 確認結果(true：期間内全て有効、false：期間内無効有り)
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	boolean isCheckEmploymentContract(String employmentContractCode, Date startDate, Date endDate) throws MospException;
	
}
