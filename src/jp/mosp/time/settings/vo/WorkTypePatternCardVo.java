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
package jp.mosp.time.settings.vo;

import java.util.List;
import java.util.Map;

import jp.mosp.time.settings.base.TimeSettingVo;

/**
 * 勤務形態パターンの情報を格納する。
 */
public class WorkTypePatternCardVo extends TimeSettingVo {
	
	private static final long		serialVersionUID	= 1280472632092676759L;
	private String					txtPatternCode;
	private String					txtPatternName;
	private String					txtPatternAbbr;
	private String[][]				jsPltSelectTable;
	private String[]				jsPltSelectSelected;
	
	/**
	 * 追加JSPリスト。<br>
	 */
	private List<String>			addonJsps;
	
	/**
	 * 追加パラメータ群(キー：パラメータ名)。<br>
	 */
	private Map<String, String>		addonParams;
	
	/**
	 * 追加パラメータ配列群(キー：パラメータ名)。<br>
	 */
	private Map<String, String[]>	addonArrays;
	
	
	/**
	 * @return txtPatternCode
	 */
	public String getTxtPatternCode() {
		return txtPatternCode;
	}
	
	/**
	 * @param txtPatternCode セットする txtPatternCode
	 */
	public void setTxtPatternCode(String txtPatternCode) {
		this.txtPatternCode = txtPatternCode;
	}
	
	/**
	 * @return txtPatternName
	 */
	public String getTxtPatternName() {
		return txtPatternName;
	}
	
	/**
	 * @param txtPatternName セットする txtPatternName
	 */
	public void setTxtPatternName(String txtPatternName) {
		this.txtPatternName = txtPatternName;
	}
	
	/**
	 * @return txtPatternAbbr
	 */
	public String getTxtPatternAbbr() {
		return txtPatternAbbr;
	}
	
	/**
	 * @param txtPatternAbbr セットする txtPatternAbbr
	 */
	public void setTxtPatternAbbr(String txtPatternAbbr) {
		this.txtPatternAbbr = txtPatternAbbr;
	}
	
	/**
	 * @return jsPltSelectTable
	 */
	public String[][] getJsPltSelectTable() {
		return getStringArrayClone(jsPltSelectTable);
	}
	
	/**
	 * @param jsPltSelectTable セットする jsPltSelectTable
	 */
	public void setJsPltSelectTable(String[][] jsPltSelectTable) {
		this.jsPltSelectTable = getStringArrayClone(jsPltSelectTable);
	}
	
	/**
	 * @return jsPltSelectSelected
	 */
	public String[] getJsPltSelectSelected() {
		return getStringArrayClone(jsPltSelectSelected);
	}
	
	/**
	 * @param jsPltSelectSelected セットする jsPltSelectSelected
	 */
	public void setJsPltSelectSelected(String[] jsPltSelectSelected) {
		this.jsPltSelectSelected = getStringArrayClone(jsPltSelectSelected);
	}
	
	/**
	 * @return addonJsps
	 */
	public List<String> getAddonJsps() {
		return addonJsps;
	}
	
	/**
	 * @param addonJsps セットする addonJsps
	 */
	public void setAddonJsps(List<String> addonJsps) {
		this.addonJsps = addonJsps;
	}
	
	/**
	 * @return addonParams
	 */
	public Map<String, String> getAddonParams() {
		return addonParams;
	}
	
	/**
	 * @param name パラメータ名
	 * @return 勤怠設定追加パラメータ
	 */
	public String getAddonParam(String name) {
		return addonParams.get(name);
	}
	
	/**
	 * @param addonParams セットする addonParams
	 */
	public void setAddonParams(Map<String, String> addonParams) {
		this.addonParams = addonParams;
	}
	
	/**
	 * @param name  パラメータ名
	 * @param value パラメータ値
	 */
	public void putAddonParam(String name, String value) {
		addonParams.put(name, value);
	}
	
	/**
	 * @return addonArrays
	 */
	public Map<String, String[]> getAddonArrays() {
		return addonArrays;
	}
	
	/**
	 * @param name パラメータ名
	 * @return 勤怠設定追加パラメータ配列
	 */
	public String[] getAddonArray(String name) {
		return getStringArrayClone(addonArrays.get(name));
	}
	
	/**
	 * @param addonArrays セットする addonArrays
	 */
	public void setAddonArrays(Map<String, String[]> addonArrays) {
		this.addonArrays = addonArrays;
	}
	
	/**
	 * @param name  パラメータ名
	 * @param value パラメータ値
	 */
	public void putAddonArray(String name, String[] value) {
		addonArrays.put(name, getStringArrayClone(value));
	}
	
}
