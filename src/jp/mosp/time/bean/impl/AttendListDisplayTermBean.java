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

import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.base.AdditionalLogicBeanInterface;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.utils.PlatformUtility;
import jp.mosp.platform.utils.TransStringUtility;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dto.settings.impl.AttendanceListDto;
import jp.mosp.time.entity.AttendListEntityInterface;

/**
 * 勤怠一覧参照追加処理(期間表示)。<br>
 */
public class AttendListDisplayTermBean extends PlatformBean implements AdditionalLogicBeanInterface {
	
	@Override
	public void initBean() throws MospException {
		// 処理無し
	}
	
	@Override
	public boolean doAdditionalLogic(Object... objects) throws MospException {
		// コードキーを取得
		String codeKey = (String)objects[0];
		// 追加業務ロジック：勤怠一覧参照処理：出勤簿取得である場合
		if (MospUtility.isEqual(codeKey, TimeConst.CODE_KEY_ADD_ATTENDANCELISTREFERENCEBEAN_GETACTUALLIST)) {
			// 勤怠一覧情報リストを取得
			List<AttendanceListDto> attendanceList = PlatformUtility.castObject(objects[2]);
			// 勤怠一覧情報に期間表示を設定
			setTerm(attendanceList);
			// 処理有りと判断
			return true;
		}
		// 追加業務ロジック：勤怠一覧情報後処理である場合
		if (MospUtility.isEqual(codeKey, TimeConst.CODE_KEY_ADD_ATTEND_LIST_AFTER)) {
			// 勤怠一覧エンティティを取得
			AttendListEntityInterface entity = PlatformUtility.castObject(objects[1]);
			// 勤怠一覧情報に期間表示を設定
			setTerm(entity.getAttendList());
			// 処理有りと判断
			return true;
		}
		// 処理無しと判断
		return false;
	}
	
	/**
	 * 勤怠一覧情報に期間表示を設定する。<br>
	 * @param attendanceList 勤怠一覧情報リスト
	 */
	protected void setTerm(List<AttendanceListDto> attendanceList) {
		// 最初と最後の勤怠一覧情報を取得
		AttendanceListDto firstDto = MospUtility.getFirstValue(attendanceList);
		AttendanceListDto lastDto = MospUtility.getLastValue(attendanceList);
		// 期間表示文字列を作成
		String term = TransStringUtility.getJapaneseDateTerm(mospParams, firstDto.getWorkDate(), lastDto.getWorkDate());
		// 期間表示文字列を最初の勤怠一覧情報に設定
		firstDto.setExtra(term);
	}
	
}
