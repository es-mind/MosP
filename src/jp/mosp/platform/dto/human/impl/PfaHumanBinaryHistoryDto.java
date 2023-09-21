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
package jp.mosp.platform.dto.human.impl;

import java.util.Date;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.framework.utils.CapsuleUtility;
import jp.mosp.platform.dto.human.HumanBinaryHistoryDtoInterface;

/**
 * 人事バイナリ汎用履歴情報DTO。
 */
public class PfaHumanBinaryHistoryDto extends BaseDto implements HumanBinaryHistoryDtoInterface {
	
	private static final long	serialVersionUID	= 8897104234028407368L;
	
	/**
	 * レコード識別ID。
	 */
	private long				pfaHumanBinaryHistoryId;
	
	/**
	 * 個人ID。
	 */
	private String				personalId;
	
	/**
	 * 有効日。
	 */
	private Date				activateDate;
	
	/**
	 * 人事項目区分。
	 */
	private String				humanItemType;
	
	/**
	 * 人事項目値。
	 */
	private byte[]				humanItemBinary;
	
	/**
	 * ファイル拡張子。
	 */
	private String				fileType;
	
	/**
	 * ファイル名。
	 */
	private String				fileName;
	
	/**
	 * ファイル備考。
	 */
	private String				fileRemark;
	/**
	 * 無効フラグ。
	 */
	private int					inactivateFlag;
	
	
	/**
	 * コンストラクタ。
	 */
	public PfaHumanBinaryHistoryDto() {
		// 処理無し
	}
	
	@Override
	public long getPfaHumanBinaryHistoryId() {
		return pfaHumanBinaryHistoryId;
	}
	
	@Override
	public String getPersonalId() {
		return personalId;
	}
	
	@Override
	public String getHumanItemType() {
		return humanItemType;
	}
	
	@Override
	public Date getActivateDate() {
		return getDateClone(activateDate);
	}
	
	@Override
	public byte[] getHumanItemBinary() {
		return CapsuleUtility.getByteArrayClone(humanItemBinary);
	}
	
	@Override
	public String getFileType() {
		return fileType;
	}
	
	@Override
	public String getFileName() {
		return fileName;
	}
	
	@Override
	public String getFileRemark() {
		return fileRemark;
	}
	
	@Override
	public int getInactivateFlag() {
		return inactivateFlag;
	}
	
	@Override
	public void setPfaHumanBinaryHistoryId(long pfaHumanBinaryHistoryId) {
		this.pfaHumanBinaryHistoryId = pfaHumanBinaryHistoryId;
	}
	
	@Override
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
	
	@Override
	public void setHumanItemType(String humanItemType) {
		this.humanItemType = humanItemType;
	}
	
	@Override
	public void setActivateDate(Date activateDate) {
		this.activateDate = getDateClone(activateDate);
	}
	
	@Override
	public void setHumanItemBinary(byte[] humanItemBinary) {
		this.humanItemBinary = CapsuleUtility.getByteArrayClone(humanItemBinary);
	}
	
	@Override
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	
	@Override
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	@Override
	public void setFileRemark(String fileRemark) {
		this.fileRemark = fileRemark;
	}
	
	@Override
	public void setInactivateFlag(int inactivateFlag) {
		this.inactivateFlag = inactivateFlag;
	}
	
}
