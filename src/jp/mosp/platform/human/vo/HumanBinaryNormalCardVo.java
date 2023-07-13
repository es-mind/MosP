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
package jp.mosp.platform.human.vo;

import jp.mosp.platform.human.base.PlatformHumanVo;

/**
 * 人事汎用バイナリ通常情報登録画面の情報を格納する。
 */
public class HumanBinaryNormalCardVo extends PlatformHumanVo {
	
	private static final long	serialVersionUID	= 2137217320524486011L;
	
	/**
	 * ファイル拡張子(プルダウン)
	 */
	private String[][]			aryPltFileType;
	
	/**
	 * ファイル拡張子
	 */
	private String				pltFileType;
	
	/**
	 * ファイル名
	 */
	private String				fileBinaryNormal;
	
	/**
	 * ファイル備考
	 */
	private String				txtFileRemark;
	
	/**
	 * 登録情報確認フラグ
	 */
	private boolean				isExsistDto;
	
	/**
	 * レコード識別ID
	 */
	private Long				hidRecordId;
	
	
	/**
	 * @param aryPltFileType セットする aryPltFileType
	 */
	public void setAryPltFileType(String[][] aryPltFileType) {
		this.aryPltFileType = getStringArrayClone(aryPltFileType);
	}
	
	/**
	 * @return aryPltFileType
	 */
	public String[][] getAryPltFileType() {
		return getStringArrayClone(aryPltFileType);
	}
	
	/**
	 * @param pltFileType セットする pltFileType
	 */
	public void setPltFileType(String pltFileType) {
		this.pltFileType = pltFileType;
	}
	
	/**
	 * @return pltFileType
	 */
	public String getPltFileType() {
		return pltFileType;
	}
	
	/**
	 * @param fileBinaryNormal セットする fileBinaryNormal
	 */
	public void setFileBinaryNormal(String fileBinaryNormal) {
		this.fileBinaryNormal = fileBinaryNormal;
	}
	
	/**
	 * @return fileBinaryNormal
	 */
	public String getFileBinaryNormal() {
		return fileBinaryNormal;
	}
	
	/**
	 * @param txtFileRemark セットする txtFileRemark
	 */
	public void setTxtFileRemark(String txtFileRemark) {
		this.txtFileRemark = txtFileRemark;
	}
	
	/**
	 * @return txtFileRemark
	 */
	public String getTxtFileRemark() {
		return txtFileRemark;
	}
	
	/**
	 * @param isExsistDto セットする isExsistDto
	 */
	public void setExsistDto(boolean isExsistDto) {
		this.isExsistDto = isExsistDto;
	}
	
	/**
	 * @return isExsistDto
	 */
	public boolean isExsistDto() {
		return isExsistDto;
	}
	
	/**
	 * @return hidRecordId
	 */
	public Long getHidRecordId() {
		return hidRecordId;
	}
	
	/**
	 * @param hidRecordId セットする hidRecordId
	 */
	public void setHidRecordId(Long hidRecordId) {
		this.hidRecordId = hidRecordId;
	}
}
