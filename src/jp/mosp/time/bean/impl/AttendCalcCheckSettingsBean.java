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

import java.util.Date;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.time.bean.AttendCalcExtraBeanInterface;
import jp.mosp.time.bean.AttendCalcReferenceBeanInterface;
import jp.mosp.time.entity.AttendanceEntityInterface;
import jp.mosp.time.entity.TimeSettingEntityInterface;
import jp.mosp.time.entity.WorkTypeEntityInterface;
import jp.mosp.time.utils.TimeMessageUtility;

/**
 * 勤怠計算(日々)確認処理。<br>
 * <br>
 * 勤怠計算(日々)に必要な設定が存在することを確認する。<br>
 * <br>
 */
public class AttendCalcCheckSettingsBean extends PlatformBean implements AttendCalcExtraBeanInterface {
	
	/**
	 * 勤怠計算(日々)関連情報取得処理。<br>
	 */
	protected AttendCalcReferenceBeanInterface refer;
	
	
	@Override
	public void initBean() {
		// 処理無し
	}
	
	@Override
	public void setAttendCalcRefer(AttendCalcReferenceBeanInterface refer) {
		// 勤怠計算(日々)関連情報取得処理を設定
		this.refer = refer;
	}
	
	@Override
	public void execute(AttendanceEntityInterface attendance) throws MospException {
		// 個人IDと勤務日と勤務形態コードを取得
		String personalId = attendance.getPersonalId();
		Date workDate = attendance.getWorkDate();
		String workTypeCode = attendance.getWorkTypeCode();
		// 勤怠設定エンティティを取得
		TimeSettingEntityInterface timeSetting = refer.getTimeSetting(personalId, workDate);
		// 勤務形態エンティティを取得
		WorkTypeEntityInterface workType = refer.getWorkType(personalId, workDate, workTypeCode);
		// 勤怠設定が取得できなかった場合
		if (timeSetting.isExist() == false) {
			// エラーメッセージを追加
			TimeMessageUtility.addErrorTimeSettingDefect(mospParams, workDate);
		}
		// 勤務形態が取得できなかった場合
		if (workType.isExist() == false) {
			// エラーメッセージを追加
			TimeMessageUtility.addErrorWorkTypeDefect(mospParams, workDate);
		}
	}
	
}
