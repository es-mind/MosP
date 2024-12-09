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
package jp.mosp.time.bean.impl;

import jp.mosp.framework.base.MospException;
import jp.mosp.time.bean.AfterApplyAttendancesExecuteBeanInterface;
import jp.mosp.time.bean.AttendanceBean;
import jp.mosp.time.dto.settings.AttendanceDtoInterface;

/**
 * 勤怠申請後処理群実行処理。<br>
 */
public class AfterApplyAttendancesExecuteBean extends AttendanceBean
		implements AfterApplyAttendancesExecuteBeanInterface {
	
	/**
	 * {@link AttendanceBean#AttendanceBean()}を実行する。<br>
	 */
	public AfterApplyAttendancesExecuteBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// 継承基のメソッドを実行
		super.initBean();
	}
	
	@Override
	public void execute(AttendanceDtoInterface dto) throws MospException {
		// 勤怠申請後処理群を実行
		afterApplyAttendance(dto);
	}
	
}
