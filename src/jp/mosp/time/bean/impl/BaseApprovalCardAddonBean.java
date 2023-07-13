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

package jp.mosp.time.bean.impl;

import jp.mosp.framework.base.MospException;
import jp.mosp.time.bean.ApprovalCardAddonBeanInterface;
import jp.mosp.time.bean.AttendanceBean;

/**
 * 承認確認追加処理ベースクラス。<br>
 */
public abstract class BaseApprovalCardAddonBean extends AttendanceBean implements ApprovalCardAddonBeanInterface {
	
	/**
	 * {@link AttendanceBean#AttendanceBean()}を実行する。<br>
	 */
	public BaseApprovalCardAddonBean() {
		super();
	}
	
	/**
	 * @throws MospException 警告の回避
	 */
	@Override
	public void mapping() throws MospException {
		// 何もしない
	}
	
	/**
	 * @throws MospException 警告の回避
	 */
	@Override
	public void initVoFields() throws MospException {
		// 何もしない
	}
	
	/**
	 * @throws MospException 警告の回避
	 */
	@Override
	public void setVoFields() throws MospException {
		// 何もしない
	}
	
}
