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
package jp.mosp.platform.bean.human.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.platform.bean.human.HumanReferenceBeanInterface;
import jp.mosp.platform.bean.human.base.PlatformHumanBean;
import jp.mosp.platform.dao.human.HumanDaoInterface;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.platform.utils.PlatformUtility;

/**
 * 人事マスタ参照クラス。
 */
public class HumanReferenceBean extends PlatformHumanBean implements HumanReferenceBeanInterface {
	
	/**
	 * 人事マスタDAO。
	 */
	protected HumanDaoInterface humanDao;
	
	
	/**
	 * {@link PlatformHumanBean#PlatformHumanBean()}を実行する。<br>
	 */
	public HumanReferenceBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// 継承基の処理を実行
		super.initBean();
		// DAOを準備
		humanDao = createDaoInstance(HumanDaoInterface.class);
	}
	
	@Override
	public HumanDtoInterface findForKey(long id) throws MospException {
		BaseDto dto = findForKey(humanDao, id, false);
		if (dto != null) {
			return (HumanDtoInterface)dto;
		}
		return null;
	}
	
	@Override
	public HumanDtoInterface getHumanInfo(String personalId, Date targetDate) throws MospException {
		return super.getHumanInfo(personalId, targetDate);
	}
	
	@Override
	public HumanDtoInterface getHumanInfoForEmployeeCode(String employeeCode, Date targetDate) throws MospException {
		return humanDao.findForEmployeeCode(employeeCode, targetDate);
	}
	
	@Override
	public List<HumanDtoInterface> getHistory(String personalId) throws MospException {
		return humanDao.findForHistory(personalId);
	}
	
	@Override
	public List<HumanDtoInterface> getHumanList(Date targetDate) throws MospException {
		return humanDao.findForActivateDate(targetDate);
	}
	
	@Override
	public String getPersonalId(String employeeCode, Date targetDate) throws MospException {
		// 人事情報取得
		HumanDtoInterface dto = getHumanInfoForEmployeeCode(employeeCode, targetDate);
		// 人事情報確認
		if (dto == null) {
			// エラーメッセージを設定
			PfMessageUtility.addErrorEmployeeHistoryNotExist(mospParams, targetDate, employeeCode);
			return MospConst.STR_EMPTY;
		}
		return dto.getPersonalId();
	}
	
	@Override
	public String getEmployeeCode(String personalId, Date targetDate) throws MospException {
		HumanDtoInterface dto = getHumanInfo(personalId, targetDate);
		if (dto != null) {
			return dto.getEmployeeCode();
		}
		return "";
	}
	
	@Override
	public String getHumanName(String personalId, Date targetDate) throws MospException {
		return getHumanName(getHumanInfo(personalId, targetDate));
	}
	
	@Override
	public List<String> getPersonalIdList(List<String> employeeCodeList, Date targetDate) throws MospException {
		// 個人IDリスト準備
		List<String> personalIdList = new ArrayList<String>();
		// 社員コード毎に処理
		for (String employeeCode : employeeCodeList) {
			personalIdList.add(getPersonalId(employeeCode, targetDate));
		}
		return personalIdList;
	}
	
	@Override
	public List<String> getEmployeeCodeList(List<String> personalIdList, Date targetDate) throws MospException {
		// 個人IDリスト準備
		List<String> employeeCodeList = new ArrayList<String>();
		// 社員コード毎に処理
		for (String personalId : personalIdList) {
			employeeCodeList.add(getEmployeeCode(personalId, targetDate));
		}
		return employeeCodeList;
	}
	
	@Override
	public List<String> getHumanNameList(List<String> personalIdList, Date targetDate) throws MospException {
		// 氏名リスト準備
		List<String> humanNameList = new ArrayList<String>();
		// 社員コード毎に処理
		for (String personalId : personalIdList) {
			humanNameList.add(getHumanName(personalId, targetDate));
		}
		return humanNameList;
	}
	
	@Override
	public String getPersonalIds(String employeeCodes, Date targetDate) throws MospException {
		// 個人IDリストを取得し文字列に変換
		return toSeparatedString(getPersonalIdList(employeeCodes, targetDate), SEPARATOR_DATA);
	}
	
	@Override
	public List<String> getPersonalIdList(String employeeCodes, Date targetDate) throws MospException {
		// 社員コードリスト取得
		List<String> employeeCodeList = asList(employeeCodes, SEPARATOR_DATA);
		// 個人IDリスト取得
		return getPersonalIdList(employeeCodeList, targetDate);
	}
	
	@Override
	public String getEmployeeCodes(String personalIds, Date targetDate) throws MospException {
		// 個人IDリスト取得
		List<String> personalIdList = asList(personalIds, SEPARATOR_DATA);
		// 社員コードリスト取得
		List<String> employeeCodeList = getEmployeeCodeList(personalIdList, targetDate);
		// 社員コードリストを文字列に変換
		return toSeparatedString(employeeCodeList, SEPARATOR_DATA + MospConst.STR_SB_SPACE);
	}
	
	@Override
	public String getHumanNames(List<String> personalIdList, Date targetDate) throws MospException {
		// 氏名リスト取得
		List<String> humanNameList = getHumanNameList(personalIdList, targetDate);
		// 氏名リストを文字列に変換
		return toSeparatedString(humanNameList, SEPARATOR_DATA + MospConst.STR_SB_SPACE);
	}
	
	@Override
	public String getHumanNames(String personalIds, Date targetDate) throws MospException {
		// 個人IDリストを取得し文字列に変換
		return getHumanNames(asList(personalIds, SEPARATOR_DATA), targetDate);
	}
	
	@Override
	public void addHuman(List<HumanDtoInterface> list, String personalId, Date targetDate) throws MospException {
		// 対象個人IDの人事情報が既にリストに含まれる場合
		if (PlatformUtility.getPersonalIdSet(list).contains(personalId)) {
			// 処理無し
			return;
		}
		// 対象個人IDの人事情報を取得し追加
		list.add(getHumanInfo(personalId, targetDate));
	}
	
}
