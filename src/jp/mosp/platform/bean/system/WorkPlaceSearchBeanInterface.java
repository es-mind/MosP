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
package jp.mosp.platform.bean.system;

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dto.system.WorkPlaceDtoInterface;

/**
 * 勤務地マスタ検索インターフェース。
 */
public interface WorkPlaceSearchBeanInterface extends BaseBeanInterface {
	
	/**
	 * @return 有効日。
	 */
	Date getActivateDate();
	
	/**
	 * @return 勤務地コード。 
	 * 
	 */
	String getWorkPlaceCode();
	
	/**
	 * @return 勤務地名称。
	 * 
	 */
	String getWorkPlaceName();
	
	/**
	 * @return 勤務地名称(カナ)。 
	 * 
	 */
	String getWorkPlaceKana();
	
	/**
	 * @return 勤務地略称。 
	 * 
	 */
	String getWorkPlaceAbbr();
	
	/**
	 * @return 勤務地郵便番号（3桁）。 
	 * 
	 */
	String getPostalCode1();
	
	/**
	 * @return 勤務地郵便番号（4桁）。 
	 * 
	 */
	String getPostalCode2();
	
	/**
	 * @return 都道府県。 
	 * 
	 */
	String getPrefecture();
	
	/**
	 * @return 市区町村。 
	 * 
	 */
	String getAddress1();
	
	/**
	 * @return 都道府県。 
	 * 
	 */
	String getAddress2();
	
	/**
	 * @return 都道府県。 
	 * 
	 */
	String getAddress3();
	
	/**
	 * @return 勤務地電話番号1。
	 * 
	 */
	String getPhoneNumber1();
	
	/**
	 * @return 勤務地電話番号2。 
	 * 
	 */
	String getPhoneNumber2();
	
	/**
	 * @return 勤務地電話番号3。 
	 * 
	 */
	String getPhoneNumber3();
	
	/**
	 * @return 有効無効。
	 */
	String getInactivateFlag();
	
	/**
	 * @param activateDate セットする 有効日。
	 */
	void setActivateDate(Date activateDate);
	
	/**
	 * @param workPlaceCode セットする 勤務地コード。 
	 */
	void setWorkPlaceCode(String workPlaceCode);
	
	/**
	 * @param workPlaceName セットする 勤務地名称。
	 */
	void setWorkPlaceName(String workPlaceName);
	
	/**
	 * @param workPlaceKana セットする 勤務地名称(カナ)。 
	 */
	void setWorkPlaceKana(String workPlaceKana);
	
	/**
	 * @param workPlaceAbbr セットする 勤務地略称。 
	 */
	void setWorkPlaceAbbr(String workPlaceAbbr);
	
	/**
	 * @param postalCode1 セットする 勤務地郵便番号（3桁）。 
	 */
	void setPostalCode1(String postalCode1);
	
	/**
	 * @param postalCode2 セットする 勤務地郵便番号（4桁）。 
	 */
	void setPostalCode2(String postalCode2);
	
	/**
	 * @param prefecture セットする 都道府県。 
	 */
	void setPrefecture(String prefecture);
	
	/**
	 * @param address1 セットする 市区町村。 
	 */
	void setAddress1(String address1);
	
	/**
	 * @param address2 セットする 道府県。 
	 * 
	 */
	void setAddress2(String address2);
	
	/**
	 * @param address3 セットする 都道府県。 
	 */
	void setAddress3(String address3);
	
	/**
	 * @param phoneNumber1 セットする 勤務地電話番号1。 
	 */
	void setPhoneNumber1(String phoneNumber1);
	
	/**
	 * @param phoneNumber2 セットする 勤務地電話番号2。 
	 */
	void setPhoneNumber2(String phoneNumber2);
	
	/**
	 * @param phoneNumber3 セットする 勤務地電話番号3。 
	 */
	void setPhoneNumber3(String phoneNumber3);
	
	/**
	 * @param inactivateFlag セットする 有効無効フラグ。
	 */
	void setInactivateFlag(String inactivateFlag);
	
	/**
	 * 条件による検索。
	 * <p>
	 * 検索条件から勤務地マスタ一覧を取得。
	 * </p>
	 * @param searchParams 勤務地マスタ検索条件
	 * @return 勤務地マスタリスト
	 * @throws MospException 例外処理が発生した場合。
	 */
	List<WorkPlaceDtoInterface> getSearchList(WorkPlaceSearchBeanInterface searchParams) throws MospException;
	
}
