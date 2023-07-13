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
package jp.mosp.framework.property;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * MosP設定情報(モデル)。<br>
 */
public class ModelProperty implements BaseProperty {
	
	/**
	 * キー。
	 */
	private String				key;
	
	/**
	 * モデルクラス名。<br>
	 * 有効日の指定が無い場合に利用するモデルクラス。<br>
	 * また、モデルクラス名群の中に対象日以前の情報が無い場合にも利用される。<br>
	 */
	private String				modelClass;
	
	/**
	 * モデルクラス名群。<br>
	 * モデル有効日をキーとする。<br>
	 * 設定ファイル上で有効日を指定した場合、これに追加される。<br>
	 */
	private Map<Date, String>	modelClassMap;
	
	
	/**
	 * モデル設定情報を生成する。
	 * @param key キー
	 */
	public ModelProperty(String key) {
		this.key = key;
		modelClassMap = new HashMap<Date, String>();
	}
	
	@Override
	public String getKey() {
		return key;
	}
	
	/**
	 * モデルクラス名を取得する。
	 * @return モデルクラス名
	 */
	public String getModelClass() {
		return modelClass;
	}
	
	/**
	 * モデルクラス名群を取得する。
	 * @return モデルクラス名群
	 */
	public Map<Date, String> getModelClassMap() {
		return modelClassMap;
	}
	
	/**
	 * モデルクラス名を設定する。
	 * @param modelClass モデルクラス名
	 */
	public void setModelClass(String modelClass) {
		this.modelClass = modelClass;
	}
	
}
