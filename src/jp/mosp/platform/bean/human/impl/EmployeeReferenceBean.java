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

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.human.ConcurrentReferenceBeanInterface;
import jp.mosp.platform.bean.human.EmployeeReferenceBeanInterface;
import jp.mosp.platform.bean.human.EntranceReferenceBeanInterface;
import jp.mosp.platform.bean.human.HumanReferenceBeanInterface;
import jp.mosp.platform.bean.human.RetirementReferenceBeanInterface;
import jp.mosp.platform.bean.human.SuspensionReferenceBeanInterface;
import jp.mosp.platform.entity.EmployeeEntity;
import jp.mosp.platform.entity.EmployeeEntityInterface;
import jp.mosp.platform.utils.PfMessageUtility;

/**
 * 社員情報参照クラス。
 */
public class EmployeeReferenceBean extends PlatformBean implements EmployeeReferenceBeanInterface {
	
	/**
	 * 人事情報参照クラス。<br>
	 */
	protected HumanReferenceBeanInterface		humanRefer;
	
	/**
	 * 人事入社情報参照クラス。<br>
	 */
	protected EntranceReferenceBeanInterface	entranceRefer;
	
	/**
	 * 人事兼務情報参照クラス。<br>
	 */
	protected ConcurrentReferenceBeanInterface	concurrentRefer;
	
	/**
	 * 人事休職情報参照クラス。<br>
	 */
	protected SuspensionReferenceBeanInterface	suspensionRefer;
	
	/**
	 * 人事退職情報参照クラス。<br>
	 */
	protected RetirementReferenceBeanInterface	retirementRefer;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public EmployeeReferenceBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// Beanを準備
		humanRefer = createBeanInstance(HumanReferenceBeanInterface.class);
		entranceRefer = createBeanInstance(EntranceReferenceBeanInterface.class);
		suspensionRefer = createBeanInstance(SuspensionReferenceBeanInterface.class);
		retirementRefer = createBeanInstance(RetirementReferenceBeanInterface.class);
		concurrentRefer = createBeanInstance(ConcurrentReferenceBeanInterface.class);
	}
	
	@Override
	public EmployeeEntityInterface getEmployeeEntity(String personalId) throws MospException {
		// 社員エンティティを準備
		EmployeeEntityInterface employeeEntity = new EmployeeEntity(personalId);
		// 人事情報(履歴)を取得及び設定
		employeeEntity.setHumanList(humanRefer.getHistory(personalId));
		// 人事情報(履歴)を確認
		if (employeeEntity.getHumanList().isEmpty()) {
			// エラーメッセージを設定
			PfMessageUtility.addErrorEmployeeNotExist(mospParams);
			return employeeEntity;
		}
		// 各種情報を取得及び設定
		employeeEntity.setEntranceDto(entranceRefer.getEntranceInfo(personalId));
		employeeEntity.setConcurrentList(concurrentRefer.getConcurrentHistory(personalId));
		employeeEntity.setSuspensionList(suspensionRefer.getSuspentionList(personalId));
		employeeEntity.setRetirementDto(retirementRefer.getRetireInfo(personalId));
		return employeeEntity;
	}
	
}
