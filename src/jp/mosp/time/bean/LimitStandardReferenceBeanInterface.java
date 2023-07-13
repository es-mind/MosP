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
/**
 * 
 */
package jp.mosp.time.bean;

import java.util.Date;
import java.util.Set;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.LimitStandardDtoInterface;

/**
 * 限度基準参照処理インターフェース。<br>
 */
public interface LimitStandardReferenceBeanInterface extends BaseBeanInterface {
	
	/**
	 * 限度基準マスタからレコードを取得する。<br>
	 * 勤怠設定コード、有効日、期間で合致するレコードが無い場合、nullを返す。<br>
	 * @param workSettingCode 勤怠設定コード
	 * @param activateDate 有効日
	 * @param term 期間
	 * @return 限度基準マスタDTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	LimitStandardDtoInterface findForKey(String workSettingCode, Date activateDate, String term) throws MospException;
	
	/**
	 * 限度基準情報群を取得する。<br>
	 * 勤怠設定コードが合致する情報が無い場合は空のセットを取得する。<br>
	 * @param workSettingCode 勤怠設定コード
	 * @return 限度基準情報群
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Set<LimitStandardDtoInterface> getLimitStandards(String workSettingCode) throws MospException;
	
}
