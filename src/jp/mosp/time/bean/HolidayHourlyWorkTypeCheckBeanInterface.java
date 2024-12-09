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
package jp.mosp.time.bean;

import java.util.Collection;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.HolidayRequestDtoInterface;
import jp.mosp.time.entity.WorkTypeEntityInterface;

/**
 * 時間単位休暇と勤務形態の確認処理インターフェース。<br>
 */
public interface HolidayHourlyWorkTypeCheckBeanInterface extends BaseBeanInterface {
	
	/**
	 * 時間単位休暇と勤務形態の時間を確認する。<bt>
	 * 勤務形態の勤務時間に時間単位休暇の時間が含まれない場合は、エラーメッセージを設定する。<br>
	 * @param dtos     休暇申請情報群
	 * @param workType 勤務形態エンティティ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void checkHourlyHolidayAndWorkType(Collection<HolidayRequestDtoInterface> dtos, WorkTypeEntityInterface workType)
			throws MospException;
	
	/**
	 * 勤怠関連マスタ参照処理を設定する。<br>
	 * 勤怠関連マスタ参照処理を処理間で共有するために用いる。<br>
	 * <br>
	 * @param timeMaster 勤怠関連マスタ参照処理
	 */
	void setTimeMaster(TimeMasterBeanInterface timeMaster);
	
}
