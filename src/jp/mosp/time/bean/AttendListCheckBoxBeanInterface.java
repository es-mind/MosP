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
package jp.mosp.time.bean;

import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.impl.AttendanceListDto;
import jp.mosp.time.entity.AttendListEntityInterface;

/**
 * 勤怠一覧チェックボックス要否設定処理インターフェース。<br>
 */
public interface AttendListCheckBoxBeanInterface extends BaseBeanInterface {
	
	/**
	 * 勤怠一覧情報に勤怠一覧チェックボックス要否を設定する。<br>
	 * @param attendList 勤怠一覧情報リスト
	 * @param entity     勤怠一覧エンティティ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void setCheckBox(List<AttendanceListDto> attendList, AttendListEntityInterface entity) throws MospException;
	
	/**
	 * 勤怠一覧情報に勤怠一覧チェックボックス要否を設定する。<br>
	 * 勤怠一覧区分は勤怠であることを前提とする。<br>
	 * @param dtos     勤怠一覧情報群(キー：個人ID)
	 * @param entities 勤怠一覧エンティティ(キー：個人ID)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void setCheckBox(Map<String, AttendanceListDto> dtos, Map<String, AttendListEntityInterface> entities)
			throws MospException;
	
	/**
	 * 勤怠関連マスタ参照処理を設定する。<br>
	 * @param timeMaster 勤怠関連マスタ参照処理
	 */
	void setTimeMaster(TimeMasterBeanInterface timeMaster);
	
}
