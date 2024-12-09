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
package jp.mosp.setup.bean;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.setup.dto.InitialAccountParameterInterface;

/**
 * 初期アカウント登録処理インターフェース。<br>
 */
public interface InitialAccountCreateBeanInterface extends BaseBeanInterface {
	
	/**
	 * 初期アカウント登録パラメータインスタンス(空)を取得する。<br>
	 * @return 初期アカウント登録パラメータインスタンス(空)
	 */
	InitialAccountParameterInterface getInitParameter();
	
	/**
	 * 初期アカウントを登録する。<br>
	 * @param parameter パラメータ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void execute(InitialAccountParameterInterface parameter) throws MospException;
	
}
