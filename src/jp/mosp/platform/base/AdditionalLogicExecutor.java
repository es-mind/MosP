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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.instance.InstanceFactory;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.utils.PlatformUtility;

/**
 * 追加業務処理実行機能を実装した抽象クラス。<br>
 */
public abstract class AdditionalLogicExecutor {
	
	/**
	 * 追加業務処理群(キー：コードキー、値：追加業務処理リスト)。<br>
	 */
	protected Map<String, List<AdditionalLogicInterface>> additionalLogics;
	
	
	/**
	 * 追加業務処理を行う。<br>
	 * 繰り返し処理の際に何度も追加業務処理を取得しなくて済むよう、追加業務処理を保持しておき再利用する。<br>
	 * @param mospParams MosP処理情報
	 * @param objects    追加引数用(最初の一つ目はコードキー(追加業務処理))
	 * @return 追加業務処理実行判定(true：追加業務処理実行、false：追加業務処理不実行)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected boolean doStoredLogics(MospParams mospParams, Object... objects) throws MospException {
		// 追加業務処理の実行判定(true：実行した場合)を準備
		boolean isAdditionalLogicExecution = false;
		// コードキーを取得
		String codeKey = PlatformUtility.castObject(objects[0]);
		// 追加業務処理毎に処理
		for (AdditionalLogicInterface logic : getStoredLogicss(mospParams, codeKey)) {
			// 追加業務ロジック処理を実施
			isAdditionalLogicExecution = logic.doAdditionalLogic(objects) || isAdditionalLogicExecution;
		}
		// 追加業務処理の実行判定を取得
		return isAdditionalLogicExecution;
	}
	
	/**
	 * 追加業務処理のリストを取得する。<br>
	 * @param mospParams MosP処理情報
	 * @param codeKey    コードキー(追加業務処理)
	 * @return 追加業務処理のリスト
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	protected List<AdditionalLogicInterface> getLogics(MospParams mospParams, String codeKey) throws MospException {
		// 業務処理のリストを準備
		List<AdditionalLogicInterface> logics = new ArrayList<AdditionalLogicInterface>();
		// 業務処理クラス名(MosP処理情報からコードキーで取得)毎に処理
		for (String[] array : MospUtility.getCodeArray(mospParams, codeKey, false)) {
			// 業務処理クラス名を取得
			String logicName = array[0];
			// 業務ロジック処理が設定されていない場合
			if (MospUtility.isEmpty(logicName)) {
				// 次の処理へ
				continue;
			}
			// 業務ロジック処理を取得
			AdditionalLogicInterface logic = InstanceFactory.loadInstance(AdditionalLogicInterface.class, logicName);
			// 業務ロジック処理のリストに値を追加
			logics.add(logic);
		}
		// 業務処理のリストを取得
		return logics;
	}
	
	/**
	 * 追加業務処理のリストを取得する。<br>
	 * コードキーの追加業務処理リストが追加業務処理群に存在する場合、再作成せずに追加業務処理群から取得する。<br>
	 * @param mospParams MosP処理情報
	 * @param codeKey    コードキー(追加業務処理)
	 * @return 追加業務処理のリスト
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	protected List<AdditionalLogicInterface> getStoredLogicss(MospParams mospParams, String codeKey)
			throws MospException {
		// 追加業務処理群が存在しない場合
		if (additionalLogics == null) {
			// 追加業務処理群を準備
			additionalLogics = new HashMap<String, List<AdditionalLogicInterface>>();
		}
		// 追加業務処理群からコードキーの追加業務処理リストを取得
		List<AdditionalLogicInterface> list = additionalLogics.get(codeKey);
		// 追加業務処理群からコードキーの追加業務処理リストを取得できた場合
		if (list != null) {
			// 再作成せずに追加業務処理群から取得
			return list;
		}
		// 追加業務処理リストを作成
		list = getLogics(mospParams, codeKey);
		// 追加業務処理群に追加業務処理リストを設定
		additionalLogics.put(codeKey, list);
		// 追加業務処理リストを取得
		return list;
	}
	
}
