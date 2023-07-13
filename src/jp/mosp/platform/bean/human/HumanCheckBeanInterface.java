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

import java.util.Date;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;

/**
 * 人事情報確認処理インターフェース。<br>
 */
public interface HumanCheckBeanInterface extends BaseBeanInterface {
	
	/**
	 * 対象個人IDの人事情報が対象日時点で存在するかを確認する。<br>
	 * 存在しない場合は、MosP処理情報にエラーメッセージを設定する。<br>
	 * <br>
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @param row        行インデックス
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void checkHumanExist(String personalId, Date targetDate, Integer row) throws MospException;
	
}
