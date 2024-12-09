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
package jp.mosp.platform.bean.human.impl;

import java.util.Set;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.human.EmployeeNumberingBeanInterface;
import jp.mosp.platform.dao.human.HumanDaoInterface;
import jp.mosp.platform.utils.PlatformUtility;

/**
 * 社員コード採番クラス。<br>
 */
public class EmployeeNumberingBean extends PlatformBean implements EmployeeNumberingBeanInterface {
	
	/**
	 * 人事情報DAO。<br>
	 */
	private HumanDaoInterface dao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public EmployeeNumberingBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// DAOを準備
		dao = createDaoInstance(HumanDaoInterface.class);
	}
	
	@Override
	public boolean isEmployeeNumberingAvailable() {
		// MosPアプリケーション設定から社員コード採番フォーマットを取得
		String[] format = getEmployeeNumberingFormat();
		// 社員コード採番フォーマットが設定されていない場合
		if (format.length == 0) {
			// 利用不可
			return false;
		}
		// 利用可能
		return true;
	}
	
	@Override
	public String getNewEmployeeCode() throws MospException {
		// 社員コード採番が利用可能でない場合
		if (isEmployeeNumberingAvailable() == false) {
			// 空白を取得
			return "";
		}
		// 社員コード群を取得
		Set<String> set = dao.findForEmployeeNumbering();
		// 連番を取得
		long seq = PlatformUtility.next(getFormat(), getMin(), getMax(), set);
		// 連番(文字列)をフォーマットの形式で発行
		return issueSequenceNo(seq, getFormat());
	}
	
	/**
	 * MosPアプリケーション設定から社員コード採番フォーマットを取得する。<br>
	 * @return 社員コード採番フォーマット
	 */
	protected String[] getEmployeeNumberingFormat() {
		// MosPアプリケーション設定から社員コード採番フォーマットを取得
		return mospParams.getApplicationProperties(EmployeeNumberingBeanInterface.APP_EMPLOYEE_NUMBERING_FORMAT);
	}
	
	/**
	 * フォーマット(社員コード採番フォーマット)を取得する。<br>
	 * @return フォーマット(社員コード採番フォーマット)
	 */
	protected String getFormat() {
		// 社員コード採番フォーマットの0番目を取得
		return getEmployeeNumberingFormat()[0];
	}
	
	/**
	 * 最小値(社員コード採番フォーマット)を取得する。<br>
	 * @return 最小値(社員コード採番フォーマット)
	 */
	protected long getMin() {
		// 社員コード採番フォーマットの1番目を取得
		return Long.parseLong(getEmployeeNumberingFormat()[1]);
	}
	
	/**
	 * 最大値(社員コード採番フォーマット)を取得する。<br>
	 * @return 最大値(社員コード採番フォーマット)
	 */
	protected long getMax() {
		// 社員コード採番フォーマットの2番目を取得
		return Long.parseLong(getEmployeeNumberingFormat()[2]);
	}
	
}
