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

import java.util.Date;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.WorkTypeDtoInterface;
import jp.mosp.time.entity.WorkTypeEntityInterface;

/**
 * 勤務形態マスタ参照インターフェース。
 */
public interface WorkTypeReferenceBeanInterface extends BaseBeanInterface {
	
	/**
	 * 勤務形態マスタ取得。
	 * <p>
	 * 勤務形態コードと対象日から勤務形態マスタを取得。
	 * </p>
	 * @param workTypeCode 勤務形態コード
	 * @param targetDate 対象年月日
	 * @return 勤務形態マスタ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	WorkTypeDtoInterface getWorkTypeInfo(String workTypeCode, Date targetDate) throws MospException;
	
	/**
	 * 履歴一覧を取得する。<br>
	 * 勤務形態コードから勤務形態マスタリストを取得する。<br>
	 * @param workTypeCode 勤務形態コード
	 * @return 勤務形態マスタリスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<WorkTypeDtoInterface> getWorkTypeHistory(String workTypeCode) throws MospException;
	
	/**
	 * 勤務形態略称を取得する。<br><br>
	 * 対象となる勤務形態情報が存在しない場合は、勤務形態コードを返す。<br>
	 * @param workTypeCode 勤務形態コード
	 * @param targetDate 対象年月日
	 * @return 勤務形態略称
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String getWorkTypeAbbr(String workTypeCode, Date targetDate) throws MospException;
	
	/**
	 * 特殊な勤務形態の名称を取得する。<br>
	 * 法定休日出勤等の特殊な勤務形態の名称を取得する。<br>
	 * 名称を取得できない場合、nullを返す。<br>
	 * @param workTypeCode 勤務形態コード
	 * @return 勤務形態名称
	 */
	String getParticularWorkTypeName(String workTypeCode);
	
	/**
	 * 勤務形態名称を取得する。<br><br>
	 * 対象となる勤務形態情報が存在しない場合は、勤務形態コードを返す。<br>
	 * 表示内容は、勤務形態名称【始業時刻～終業時刻】。<br>
	 * @param workTypeCode 勤務形態コード
	 * @param targetDate 対象年月日
	 * @return 勤務形態名称
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String getWorkTypeNameAndTime(String workTypeCode, Date targetDate) throws MospException;
	
	/**
	 * 勤務形態略称を取得する。<br><br>
	 * 対象となる勤務形態情報が存在しない場合は、勤務形態コードを返す。<br>
	 * 表示内容は、勤務形態略称【始業時刻～終業時刻】。<br>
	 * @param workTypeCode 勤務形態コード
	 * @param targetDate 対象年月日
	 * @return 勤務形態略称
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String getWorkTypeAbbrAndTime(String workTypeCode, Date targetDate) throws MospException;
	
	/**
	 * 勤務形態略称を取得する。<br><br>
	 * 対象となる勤務形態情報が存在しない場合は、勤務形態コードを返す。<br>
	 * 表示内容は、勤務形態略称【始業時刻～終業時刻】。<br>
	 * @param workTypeCode 勤務形態コード
	 * @param targetDate 対象年月日
	 * @param amHoliday 午前休の場合true、そうでない場合false
	 * @param pmHoliday 午後休の場合true、そうでない場合false
	 * @return 勤務形態略称
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String getWorkTypeAbbrAndTime(String workTypeCode, Date targetDate, boolean amHoliday, boolean pmHoliday)
			throws MospException;
	
	/**
	 * プルダウン用配列取得。
	 * <p>
	 * 対象年月日からプルダウン用配列を取得。
	 * </p>
	 * @param targetDate 対象年月日
	 * @return プルダウン用配列
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String[][] getSelectArray(Date targetDate) throws MospException;
	
	/**
	 * 略称プルダウン用配列取得。
	 * <p>
	 * 対象年月日から略称プルダウン用配列を取得。
	 * </p>
	 * @param targetDate 対象年月日
	 * @return プルダウン用配列
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String[][] getSelectAbbrArray(Date targetDate) throws MospException;
	
	/**
	 * プルダウン用配列を取得する。<br>
	 * 対象年月日からプルダウン用配列を取得する。<br>
	 * 表示内容は、コード + 勤務形態略称。<br>
	 * @param targetDate 対象年月日
	 * @return プルダウン用配列
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String[][] getCodedSelectArray(Date targetDate) throws MospException;
	
	/**
	 * プルダウン用配列を取得する。<br>
	 * 対象年月日からプルダウン用配列を取得する。<br>
	 * 表示内容は、勤務形態略称【始業時刻～終業時刻】。<br>
	 * @param targetDate 対象年月日
	 * @return プルダウン用配列
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String[][] getTimeSelectArray(Date targetDate) throws MospException;
	
	/**
	 * プルダウン用配列を取得する。<br>
	 * 対象年月日からプルダウン用配列を取得する。<br>
	 * 表示内容は、勤務形態略称【始業時刻～終業時刻】。<br>
	 * @param targetDate 対象年月日
	 * @param amHoliday 午前休の場合true、そうでない場合false
	 * @param pmHoliday 午後休の場合true、そうでない場合false
	 * @return プルダウン用配列
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String[][] getTimeSelectArray(Date targetDate, boolean amHoliday, boolean pmHoliday) throws MospException;
	
	/**
	 * プルダウン用配列を取得する。<br>
	 * 対象年月日からプルダウン用配列を取得する。<br>
	 * 表示内容は、勤務形態名称【始業時刻～終業時刻】。<br>
	 * @param targetDate 対象年月日
	 * @return プルダウン用配列
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String[][] getNameTimeSelectArray(Date targetDate) throws MospException;
	
	/**
	 * プルダウン用配列を取得する。<br>
	 * 対象年月日からプルダウン用配列を取得する。<br>
	 * 表示内容は、勤務形態名称【始業時刻～終業時刻】。<br>
	 * @param targetDate 対象年月日
	 * @param amHoliday 午前休の場合true、そうでない場合false
	 * @param pmHoliday 午後休の場合true、そうでない場合false
	 * @return プルダウン用配列
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String[][] getNameTimeSelectArray(Date targetDate, boolean amHoliday, boolean pmHoliday) throws MospException;
	
	/**
	 * 勤務形態マスタからレコードを取得する。<br>
	 * 勤務形態コード、有効日で合致するレコードが無い場合、nullを返す。<br>
	 * @param workTypeCode 勤務形態コード
	 * @param activateDate 有効日
	 * @return 勤務形態マスタDTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	WorkTypeDtoInterface findForKey(String workTypeCode, Date activateDate) throws MospException;
	
	/**
	 * 勤務形態マスタからレコードを取得する。<br>
	 * 勤務形態コード、有効日で合致するレコードが無い場合、nullを返す。<br>
	 * @param workTypeCode 勤務形態コード
	 * @param activateDate 有効日
	 * @return 勤務形態マスタDTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	WorkTypeDtoInterface findForInfo(String workTypeCode, Date activateDate) throws MospException;
	
	/**
	 * 勤務形態エンティティを取得する。<br>
	 * 勤務形態情報が取得できない場合は、フィールドが空のエンティティを返す。<br>
	 * @param workTypeCode 勤務形態コード
	 * @param targetDate 対象年月日
	 * @return 勤務形態エンティティ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	WorkTypeEntityInterface getWorkTypeEntity(String workTypeCode, Date targetDate) throws MospException;
	
	/**
	 * 勤務形態エンティティ履歴を取得する。<br>
	 * <br>
	 * 履歴は、有効日昇順に並んでいる。<br>
	 * <br>
	 * @param workTypeCode 勤務形態コード
	 * @return 勤務形態エンティティ履歴
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<WorkTypeEntityInterface> getWorkTypeEntityHistory(String workTypeCode) throws MospException;
	
	/**
	 * 勤務形態エンティティ履歴(休日及び休出)群(キー：勤務形態コード)を取得する。<br>
	 * @return 勤務形態エンティティ履歴(休日及び休出)群(キー：勤務形態コード)
	 * @throws MospException オブジェクトの生成に失敗した場合
	 */
	Map<String, List<WorkTypeEntityInterface>> getExtraWorkTypeEntityHistories() throws MospException;
	
}
