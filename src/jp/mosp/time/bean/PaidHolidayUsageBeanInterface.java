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

import java.util.Collection;
import java.util.Date;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.impl.PaidHolidayUsageDto;

/**
 * 有給休暇取得状況確認情報作成処理インターフェース。<br>
 */
public interface PaidHolidayUsageBeanInterface extends BaseBeanInterface {
	
	/**
	 * 有給休暇取得状況確認情報群を作成する。<br>
	 * 一人当たり複数のレコードが存在し得る。<br>
	 * 有給休暇情報が存在しない場合は、空の有給休暇取得状況確認情報群を返す。<br>
	 * 有給休暇取得状況確認情報群は取得日の昇順となっている。<br>
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @return 有給休暇取得状況確認情報群
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Collection<PaidHolidayUsageDto> makePaidHolidayUsages(String personalId, Date targetDate) throws MospException;
	
}
