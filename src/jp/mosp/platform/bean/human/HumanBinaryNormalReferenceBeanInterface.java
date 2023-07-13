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

import java.util.List;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dto.human.HumanBinaryNormalDtoInterface;

/**
 * 人事汎用バイナリ通常情報参照インターフェース。
 */
public interface HumanBinaryNormalReferenceBeanInterface extends BaseBeanInterface {
	
	/**
	 * 変数に共通情報を設定する。
	 * 人事汎用項目区分設定情報、人事汎用項目情報リストを取得し、
	 * 人事通常情報汎用マップを初期化する。
	 * @param division 人事汎用管理区分
	 * @param viewKey 人事汎用管理表示区分
	 */
	void setCommounInfo(String division, String viewKey);
	
	/**
	 * 人事汎用バイナリ通常情報取得。
	 * <p>
	 * 人事汎用管理区分・個人IDより情報を生成する。
	 * </p>
	 * @param itemName 人事汎用管理項目
	 * @param personalId 個人ID
	 * @return 人事汎用通常情報DTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	HumanBinaryNormalDtoInterface findForInfo(String personalId, String itemName) throws MospException;
	
	/**
	 * 人事汎用バイナリ通常情報一覧取得。
	 * @param personalId 個人ID
	 * @return 人事汎用バイナリ通常情報一覧
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<HumanBinaryNormalDtoInterface> findForList(String personalId) throws MospException;
	
	/**
	 * 人事汎用バイナリ通常情報取得
	 * @param pfaHumanBinaryNormalId レコード識別ID
	 * @param isUpdate FOR UPDATE 使用有無
	 * @return 人事汎用バイナリ通常DTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	HumanBinaryNormalDtoInterface findForKey(Long pfaHumanBinaryNormalId, boolean isUpdate) throws MospException;
	
}
