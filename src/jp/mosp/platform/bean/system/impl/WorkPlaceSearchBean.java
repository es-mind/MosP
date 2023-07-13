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
package jp.mosp.platform.bean.system.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.system.WorkPlaceSearchBeanInterface;
import jp.mosp.platform.dao.system.WorkPlaceDaoInterface;
import jp.mosp.platform.dto.system.WorkPlaceDtoInterface;

/**
 * 勤務地マスタ検索クラス。
 */
public class WorkPlaceSearchBean extends PlatformBean implements WorkPlaceSearchBeanInterface {
	
	/**
	 * 勤務地マスタDAO
	 */
	protected WorkPlaceDaoInterface	workPlaceDao;
	
	/**
	 * 有効日。
	 */
	private Date					activateDate;
	
	/**
	 * 勤務地コード。
	 */
	private String					workPlaceCode;
	
	/**
	 * 勤務地名称。
	 */
	private String					workPlaceName;
	
	/**
	 * 勤務地名称。
	 */
	private String					workPlaceKana;
	
	/**
	 * 勤務地略称。
	 */
	private String					workPlaceAbbr;
	
	/**
	 * 勤務地郵便番号（3桁）。
	 */
	private String					postalCode1;
	
	/**
	 * 勤務地郵便番号（4桁）。
	 */
	private String					postalCode2;
	
	/**
	 * 勤務地都道府県。
	 */
	private String					prefecture;
	
	/**
	 * 勤務地市区町村。
	 */
	private String					address1;
	
	/**
	 * 勤務地番地。
	 */
	private String					address2;
	
	/**
	 * 勤務地建物情報。
	 */
	private String					address3;
	
	/**
	 * 勤務地電話番号1。
	 */
	private String					phoneNumber1;
	
	/**
	 * 勤務地電話番号2。
	 */
	private String					phoneNumber2;
	
	/**
	 * 勤務地電話番号3。
	 */
	private String					phoneNumber3;
	
	/**
	 * 有効無効フラグ。
	 */
	private String					inactivateFlag;
	
	
	/**
	 * コンストラクタ。
	 */
	public WorkPlaceSearchBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		workPlaceDao = createDaoInstance(WorkPlaceDaoInterface.class);
	}
	
	@Override
	public List<WorkPlaceDtoInterface> getSearchList(WorkPlaceSearchBeanInterface searchParams) throws MospException {
		Map<String, Object> param = workPlaceDao.getParamsMap();
		param.put("activateDate", searchParams.getActivateDate());
		param.put("workPlaceCode", searchParams.getWorkPlaceCode());
		param.put("workPlaceName", searchParams.getWorkPlaceName());
		param.put("workPlaceKana", searchParams.getWorkPlaceKana());
		param.put("workPlaceAbbr", searchParams.getWorkPlaceAbbr());
		param.put("postalCode1", searchParams.getPostalCode1());
		param.put("postalCode2", searchParams.getPostalCode2());
		param.put("prefecture", searchParams.getPrefecture());
		param.put("address1", searchParams.getAddress1());
		param.put("address2", searchParams.getAddress2());
		param.put("address3", searchParams.getAddress3());
		param.put("phoneNumber1", searchParams.getPhoneNumber1());
		param.put("phoneNumber2", searchParams.getPhoneNumber2());
		param.put("phoneNumber3", searchParams.getPhoneNumber3());
		param.put("inactivateFlag", searchParams.getInactivateFlag());
		List<WorkPlaceDtoInterface> list = workPlaceDao.findForSearch(param);
		return list;
	}
	
	@Override
	public Date getActivateDate() {
		return getDateClone(activateDate);
	}
	
	@Override
	public String getAddress1() {
		return address1;
	}
	
	@Override
	public String getAddress2() {
		return address2;
	}
	
	@Override
	public String getAddress3() {
		return address3;
	}
	
	@Override
	public String getInactivateFlag() {
		return inactivateFlag;
	}
	
	@Override
	public String getPhoneNumber1() {
		return phoneNumber1;
	}
	
	@Override
	public String getPhoneNumber2() {
		return phoneNumber2;
	}
	
	@Override
	public String getPhoneNumber3() {
		return phoneNumber3;
	}
	
	@Override
	public String getPostalCode1() {
		return postalCode1;
	}
	
	@Override
	public String getPostalCode2() {
		return postalCode2;
	}
	
	@Override
	public String getPrefecture() {
		return prefecture;
	}
	
	@Override
	public String getWorkPlaceAbbr() {
		return workPlaceAbbr;
	}
	
	@Override
	public String getWorkPlaceCode() {
		return workPlaceCode;
	}
	
	@Override
	public String getWorkPlaceKana() {
		return workPlaceKana;
	}
	
	@Override
	public String getWorkPlaceName() {
		return workPlaceName;
	}
	
	@Override
	public void setActivateDate(Date activateDate) {
		this.activateDate = getDateClone(activateDate);
	}
	
	@Override
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	
	@Override
	public void setAddress2(String address2) {
		this.address2 = address2;
		
	}
	
	@Override
	public void setAddress3(String address3) {
		this.address3 = address3;
	}
	
	@Override
	public void setInactivateFlag(String inactivateFlag) {
		this.inactivateFlag = inactivateFlag;
	}
	
	@Override
	public void setPhoneNumber1(String phoneNumber1) {
		this.phoneNumber1 = phoneNumber1;
	}
	
	@Override
	public void setPhoneNumber2(String phoneNumber2) {
		this.phoneNumber2 = phoneNumber2;
	}
	
	@Override
	public void setPhoneNumber3(String phoneNumber3) {
		this.phoneNumber3 = phoneNumber3;
	}
	
	@Override
	public void setPostalCode1(String postalCode1) {
		this.postalCode1 = postalCode1;
	}
	
	@Override
	public void setPostalCode2(String postalCode2) {
		this.postalCode2 = postalCode2;
	}
	
	@Override
	public void setPrefecture(String prefecture) {
		this.prefecture = prefecture;
	}
	
	@Override
	public void setWorkPlaceCode(String workPlaceCode) {
		this.workPlaceCode = workPlaceCode;
	}
	
	@Override
	public void setWorkPlaceKana(String workPlaceKana) {
		this.workPlaceKana = workPlaceKana;
	}
	
	@Override
	public void setWorkPlaceName(String workPlaceName) {
		this.workPlaceName = workPlaceName;
	}
	
	@Override
	public void setWorkPlaceAbbr(String workPlaceAbbr) {
		this.workPlaceAbbr = workPlaceAbbr;
	}
	
}
