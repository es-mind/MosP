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

import java.util.Date;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.entity.RequestEntityInterface;
import jp.mosp.time.entity.TimeSettingEntityInterface;
import jp.mosp.time.entity.WorkTypeEntityInterface;

/**
 * 勤怠計算(日々)関連情報取得処理インターフェース。<br>
 */
public interface AttendCalcReferenceBeanInterface extends BaseBeanInterface {
	
	/**
	 * 勤怠設定エンティティを取得する。<br>
	 * @param personalId 個人ID
	 * @param workDate   勤務日
	 * @return 勤怠設定エンティティ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	TimeSettingEntityInterface getTimeSetting(String personalId, Date workDate) throws MospException;
	
	/**
	 * 勤務形態エンティティを取得する。<br>
	 * <br>
	 * 個人IDと勤務日で勤怠設定エンティティ群から取得できなかった場合、
	 * 勤務形態コードと勤務日でDBから取得する。<br>
	 * <br>
	 * @param personalId   個人ID
	 * @param workDate     勤務日
	 * @param workTypeCode 勤務形態コード
	 * @return 勤務形態エンティティ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	WorkTypeEntityInterface getWorkType(String personalId, Date workDate, String workTypeCode) throws MospException;
	
	/**
	 * 申請エンティティを取得する。<br>
	 * <br>
	 * @param personalId 個人ID
	 * @param workDate   勤務日
	 * @return 申請エンティティ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	RequestEntityInterface getRequest(String personalId, Date workDate) throws MospException;
	
	/**
	 * 勤務形態(翌日)コードを取得する。<br>
	 * <br>
	 * 例えば、引数の勤務日(workDate)が2018/08/01である場合、
	 * 2018/08/02に取ることが想定される勤務形態コードを取得する。<br>
	 * <br>
	 * @param personalId 個人ID
	 * @param workDate   勤務日
	 * @return 申請エンティティ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String getNextWorkType(String personalId, Date workDate) throws MospException;
	
}
