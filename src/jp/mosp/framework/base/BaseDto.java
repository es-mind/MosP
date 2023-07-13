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
package jp.mosp.framework.base;

import java.util.Date;

import jp.mosp.framework.utils.CapsuleUtility;

/**
 *  DTOの基本機能を提供する。<br><br>
 *  作成者、作成日、更新者、更新日フィールドとそのアクセサメソッドを有する。<br>
 */
public abstract class BaseDto implements BaseDtoInterface {
	
	private static final long	serialVersionUID	= 5697604395699134118L;
	
	// フィールド
	private int					deleteFlag;
	private Date				insertDate;
	private String				insertUser;
	private Date				updateDate;
	private String				updateUser;
	
	
	@Override
	public Date getInsertDate() {
		return getDateClone(insertDate);
	}
	
	@Override
	public void setInsertDate(Date insertDate) {
		this.insertDate = getDateClone(insertDate);
	}
	
	@Override
	public String getInsertUser() {
		return insertUser;
	}
	
	@Override
	public void setInsertUser(String insertUser) {
		this.insertUser = insertUser;
	}
	
	@Override
	public Date getUpdateDate() {
		return getDateClone(updateDate);
	}
	
	@Override
	public void setUpdateDate(Date updateDate) {
		this.updateDate = getDateClone(updateDate);
	}
	
	@Override
	public String getUpdateUser() {
		return updateUser;
	}
	
	@Override
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	
	@Override
	public int getDeleteFlag() {
		return deleteFlag;
	}
	
	@Override
	public void setDeleteFlag(int deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	
	/**
	 * 日付オブジェクトの複製を取得する。<br>
	 * @param date 対象日付オブジェクト
	 * @return 複製日付オブジェクト
	 */
	protected Date getDateClone(Date date) {
		return CapsuleUtility.getDateClone(date);
	}
	
}
