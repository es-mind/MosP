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

import java.util.Collection;
import java.util.Date;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.LimitStandardDtoInterface;

/**
 * 限度基準登録処理インターフェース。<br>
 */
public interface LimitStandardRegistBeanInterface extends BaseBeanInterface {
	
	/**
	 * 登録用DTOを取得する。<br>
	 * @return 初期DTO
	 */
	LimitStandardDtoInterface getInitDto();
	
	/**
	 * 登録を行う。<br>
	 * 勤怠設定の登録時にチェックを行うことを想定しており、
	 * 登録情報にレコード識別IDは付加されていない。<br>
	 * <br>
	 * @param dtos 限度基準情報群
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void regist(Collection<LimitStandardDtoInterface> dtos) throws MospException;
	
	/**
	 * 論理削除を行う。<br>
	 * 勤怠設定の削除時にチェックを行うことを想定し、ここでは削除処理のみを行う。<br>
	 * <br>
	 * @param workSettingCode 勤怠設定コード
	 * @param activateDate    有効日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void delete(String workSettingCode, Date activateDate) throws MospException;
	
	/**
	 * 限度基準情報をコピーする。<br>
	 * 勤怠設定コード及びコピー元有効日で限度基準情報群を取得し、有効日を変更して登録する。<br>
	 * @param workSettingCode    勤怠設定コード
	 * @param activateDate       有効日
	 * @param copiedActivateDate コピー元有効日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void copy(String workSettingCode, Date activateDate, Date copiedActivateDate) throws MospException;
	
}
