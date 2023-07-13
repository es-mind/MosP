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
package jp.mosp.framework.exporter;

import java.io.Serializable;

import jp.mosp.framework.utils.CapsuleUtility;

/**
 * {@link HumanBinaryExporter}で出力する内容を保持するクラス。
 */
public class ImageContents implements Serializable {
	
	private static final long	serialVersionUID	= -5516600612009013133L;
	
	/**
	 * ファイル拡張子。
	 */
	private final String		fileType;
	
	/**
	 * バイナリデータ
	 */
	private final byte[]		binaryData;
	
	
	/**
	 * 
	 * @param fileType ファイル拡張子
	 * @param binaryData バイナリデータ
	 */
	public ImageContents(String fileType, byte[] binaryData) {
		this.fileType = fileType;
		this.binaryData = CapsuleUtility.getByteArrayClone(binaryData);
	}
	
	/**
	 * @return fileType ファイル拡張子
	 */
	public String getFileType() {
		return fileType;
	}
	
	/**
	 * @return binaryData バイナリデータ
	 */
	public byte[] getBinaryData() {
		return CapsuleUtility.getByteArrayClone(binaryData);
	}
	
}
