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
package jp.mosp.platform.bean.file.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.bean.file.ImportBeanInterface;
import jp.mosp.platform.bean.file.PlatformFileBean;
import jp.mosp.platform.bean.workflow.ApprovalUnitRegistBeanInterface;
import jp.mosp.platform.dao.workflow.impl.PfmApprovalUnitDao;
import jp.mosp.platform.dto.file.ImportDtoInterface;
import jp.mosp.platform.dto.file.ImportFieldDtoInterface;
import jp.mosp.platform.dto.workflow.ApprovalUnitDtoInterface;

/**
 * ユニット情報インポートクラス。<br>
 */
public abstract class UnitImportBean extends PlatformFileBean implements ImportBeanInterface {
	
	/**
	 * ユニット情報登録クラス。<br>
	 */
	protected ApprovalUnitRegistBeanInterface regist;
	
	
	/**
	 * {@link PlatformFileBean#PlatformFileBean()}を実行する。<br>
	 */
	public UnitImportBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// Bean準備
		regist = createBeanInstance(ApprovalUnitRegistBeanInterface.class);
	}
	
	@Override
	public int importFile(ImportDtoInterface importDto, InputStream requestedFile) throws MospException {
		// アップロードファイルを登録情報リストに変換
		List<String[]> dataList = getDataList(importDto, requestedFile);
		// インポートフィールド情報リストを取得
		List<ImportFieldDtoInterface> fieldList = getImportFieldList(importDto.getImportCode());
		// インポート処理
		return importFile(fieldList, dataList);
	}
	
	/**
	 * インポート処理を行う。<br>
	 * 登録情報リストを、インポートフィールド情報リストに基づき
	 * ユニット情報に変換し、登録を行う。
	 * <br>
	 * @param fieldList インポートフィールド情報リスト
	 * @param dataList  登録情報リスト
	 * @return 登録件数
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected int importFile(List<ImportFieldDtoInterface> fieldList, List<String[]> dataList) throws MospException {
		// 登録情報リストをユーザパスワード情報(DTO)リストに変換
		List<ApprovalUnitDtoInterface> targetList = getTargetList(fieldList, dataList);
		// エラー確認
		if (mospParams.hasErrorMessage()) {
			return 0;
		}
		// 行インデックス宣言
		int row = 0;
		// ユニット情報(DTO)情報毎に登録
		for (ApprovalUnitDtoInterface dto : targetList) {
			// ユニット情報妥当性確認
			regist.validate(dto, row++);
			// エラー確認
			if (mospParams.hasErrorMessage()) {
				continue;
			}
			// ユニット情報登録
			regist.regist(dto);
		}
		// 登録件数取得
		return targetList.size();
	}
	
	/**
	 * ユニット情報(DTO)リストを取得する。<br>
	 * インポートマスタ情報に従い、登録情報リストの内容をDTOに変換する。<br>
	 * <br>
	 * @param fieldList インポートフィールド情報リスト
	 * @param dataList  登録情報リスト
	 * @return ユニット情報(DTO)リスト
	 * @throws MospException パスワードの作成に失敗した場合
	 */
	protected List<ApprovalUnitDtoInterface> getTargetList(List<ImportFieldDtoInterface> fieldList,
			List<String[]> dataList) throws MospException {
		// ユニット情報(DTO)リスト準備
		List<ApprovalUnitDtoInterface> targetList = new ArrayList<ApprovalUnitDtoInterface>();
		// 登録情報リスト内の各登録情報長を確認
		checkCsvLength(fieldList, dataList);
		// エラー確認
		if (mospParams.hasErrorMessage()) {
			return targetList;
		}
		// 登録情報リスト毎に処理
		for (int i = 0; i < dataList.size(); i++) {
			// 登録情報取得
			String[] data = dataList.get(i);
			// ユニット情報(DTO)取得及び確認
			ApprovalUnitDtoInterface dto = getUnitDto(fieldList, data);
			// ユニット情報(DTO)リストに追加
			targetList.add(dto);
		}
		return targetList;
	}
	
	/**
	 * インポートフィールド情報リストに従い、
	 * 登録情報の内容をユニット情報(DTO)のフィールドに設定する。<br>
	 * @param fieldList インポートフィールド情報リスト
	 * @param data      登録情報
	 * @return ユニット情報(DTO)
	 * @throws MospException 人事情報の取得に失敗した場合
	 */
	protected ApprovalUnitDtoInterface getUnitDto(List<ImportFieldDtoInterface> fieldList, String[] data)
			throws MospException {
		// ユニット情報(DTO)準備
		ApprovalUnitDtoInterface dto = regist.getInitDto();
		// 登録情報の内容を取得(登録情報に含まれない場合は空白(数値の場合は0))
		String unitCode = getFieldValue(PfmApprovalUnitDao.COL_UNIT_CODE, fieldList, data);
		Date activateDate = getDateFieldValue(PfmApprovalUnitDao.COL_ACTIVATE_DATE, fieldList, data);
		String unitName = getFieldValue(PfmApprovalUnitDao.COL_UNIT_NAME, fieldList, data);
		int inactivateFlag = getIntegerFieldValue(PfmApprovalUnitDao.COL_INACTIVATE_FLAG, fieldList, data);
		// ユニット情報(DTO)に登録情報の内容を設定
		dto.setUnitCode(unitCode);
		dto.setActivateDate(activateDate);
		dto.setUnitName(unitName);
		dto.setInactivateFlag(inactivateFlag);
		return dto;
	}
	
}
