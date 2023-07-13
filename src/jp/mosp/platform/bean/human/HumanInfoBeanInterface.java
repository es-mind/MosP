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

import jp.mosp.framework.base.MospException;

/**
 * 人事情報一覧追加処理インターフェース。<br>
 * アドオンで人事情報一覧に処理を追加したい場合に用いる。<br>
 */
public interface HumanInfoBeanInterface {
	/**
	 * VOに設定されている対象社員コード及び有効日で各種人事情報を取得し、
	 * それらをVOに設定する。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void setHumanInfo() throws MospException;
}
