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
package jp.mosp.platform.bean.file.impl;

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.bean.human.HumanReferenceBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dao.workflow.impl.PfmApprovalUnitDao;
import jp.mosp.platform.dto.file.ImportFieldDtoInterface;
import jp.mosp.platform.dto.workflow.ApprovalUnitDtoInterface;

/**
 * ユニット情報(個人)インポートクラス。<br>
 */
public class UnitPersonImportBean extends UnitImportBean {
	
	/**
	 * 区切文字(承認者社員コード)。<br>
	 * 入力の際の区切文字として用いる。<br>
	 */
	protected static final String SEPARATOR_EMPLOYEE_CODES = ";";
	
	
	/**
	 * {@link UnitImportBean#UnitImportBean()}を実行する。<br>
	 */
	public UnitPersonImportBean() {
		super();
	}
	
	/**
	 * ユニット区分は個人で設定する。<br>
	 */
	@Override
	protected ApprovalUnitDtoInterface getUnitDto(List<ImportFieldDtoInterface> fieldList, String[] data)
			throws MospException {
		// 継承基のメソッドを実行
		ApprovalUnitDtoInterface dto = super.getUnitDto(fieldList, data);
		// 登録情報の内容を取得(登録情報に含まれない場合は空白)
		String employeeCodes = getFieldValue(PfmApprovalUnitDao.COL_APPROVER_PERSONAL_ID, fieldList, data);
		// 承認者個人ID(カンマ区切)を取得
		String approverPersonalId = getApproverPersonalId(employeeCodes, dto.getActivateDate());
		// ユニット区分設定
		dto.setUnitType(PlatformConst.UNIT_TYPE_PERSON);
		// 承認者所属及び職位設定(空白)
		dto.setApproverSectionCode("");
		dto.setApproverPositionCode("");
		dto.setApproverPositionGrade("");
		// 承認者個人ID設定
		dto.setApproverPersonalId(approverPersonalId);
		return dto;
	}
	
	/**
	 * 承認者個人ID(カンマ区切)を取得する。<br>
	 * @param employeeCodes 承認者社員コード(セミコロン(;)区切)
	 * @param targetDate    対象日
	 * @return 承認者個人ID(カンマ区切)
	 * @throws MospException 人事情報の取得に失敗した場合
	 */
	protected String getApproverPersonalId(String employeeCodes, Date targetDate) throws MospException {
		// 承認者社員コード(セミコロン(;)区切)及び対象日を確認
		if (employeeCodes == null || targetDate == null) {
			return "";
		}
		// 区切文字を変換(セミコロン→カンマ)
		String csvEmployeeCodes = employeeCodes.replaceAll(SEPARATOR_EMPLOYEE_CODES, SEPARATOR_DATA);
		// 人事情報参照クラス取得
		HumanReferenceBeanInterface humanRefer = (HumanReferenceBeanInterface)createBean(
				HumanReferenceBeanInterface.class);
		// 承認者個人ID(カンマ区切)を取得
		return humanRefer.getPersonalIds(csvEmployeeCodes, targetDate);
	}
	
}
