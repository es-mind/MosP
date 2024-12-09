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
package jp.mosp.framework.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.property.RangeProperty;

/**
 * MosPにおけるセッション保持情報を扱う。
 */
public class MospStoredInfo implements Serializable {
	
	private static final long			serialVersionUID	= -4122523260964032127L;
	
	/**
	 * MosPユーザー情報。
	 */
	private MospUser					user;
	
	/**
	 * パンくずリスト。
	 */
	private List<TopicPath>				topicPathList;
	
	/**
	 * 範囲設定。
	 */
	private Map<String, RangeProperty>	rangeMap;
	
	/**
	 * 汎用保持文字列群。
	 */
	private Map<String, String>			generalMap;
	
	
	/**
	 * MosPセッション保持情報を初期化する。
	 */
	public MospStoredInfo() {
		initStoredInfo();
	}
	
	/**
	 * MosPセッション保持情報を初期化する。
	 */
	public void initStoredInfo() {
		user = null;
		topicPathList = new ArrayList<TopicPath>();
		rangeMap = null;
		generalMap = new HashMap<String, String>();
	}
	
	/**
	 * @return user
	 */
	public MospUser getUser() {
		return user;
	}
	
	/**
	 * @param user セットする user
	 */
	public void setUser(MospUser user) {
		this.user = user;
	}
	
	/**
	 * @return topicPathList
	 */
	public List<TopicPath> getTopicPathList() {
		return topicPathList;
	}
	
	/**
	 * @param topicPathList セットする topicPathList
	 */
	public void setTopicPathList(List<TopicPath> topicPathList) {
		this.topicPathList = topicPathList;
	}
	
	/**
	 * @return rangeMap
	 */
	public Map<String, RangeProperty> getRangeMap() {
		return rangeMap;
	}
	
	/**
	 * @param rangeMap セットする rangeMap
	 */
	public void setRangeMap(Map<String, RangeProperty> rangeMap) {
		this.rangeMap = rangeMap;
	}
	
	/**
	 * 汎用保持文字列群に文字列を設定する。
	 * @param key   キー
	 * @param value 値
	 */
	public void putGeneralString(String key, String value) {
		generalMap.put(key, value);
	}
	
	/**
	 * 汎用保持文字列群から保持文字列を取得する。
	 * @param key   キー
	 * @return 保持文字列
	 */
	public String getGeneralString(String key) {
		return generalMap.get(key);
	}
	
}
