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
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.file.PositionImportBeanInterface;
import jp.mosp.platform.bean.system.impl.PositionRegistBean;
import jp.mosp.platform.dao.system.impl.PfmPositionDao;
import jp.mosp.platform.dao.system.impl.PfmSectionDao;
import jp.mosp.platform.dto.file.ImportDtoInterface;
import jp.mosp.platform.dto.file.ImportFieldDtoInterface;
import jp.mosp.platform.dto.system.PositionDtoInterface;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.platform.utils.PfNameUtility;

/**
 * 職位マスタインポートクラス。
 */
public class PositionImportBean extends PositionRegistBean implements PositionImportBeanInterface {
	
	/**
	 * 職位情報リスト。<br>
	 */
	protected List<PositionDtoInterface> positionList;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public PositionImportBean() {
		super();
	}
	
	@Override
	public int importFile(ImportDtoInterface importDto, InputStream requestedFile) throws MospException {
		// アップロードファイルを登録情報リストに変換
		List<String[]> dataList = getDataList(importDto, requestedFile);
		// 登録情報リストから人事マスタ情報に変換
		getTargetLists(importDto, dataList);
		// エラー確認
		if (mospParams.hasErrorMessage()) {
			return 0;
		}
		// 職位マスタ情報毎に登録
		for (int i = 0; i < positionList.size(); i++) {
			// 職位マスタ情報登録
			registPositionDto(positionList.get(i));
		}
		// 登録件数取得
		return positionList.size();
	}
	
	/**
	 * 登録対象リスト群を取得する。<br>
	 * インポートマスタ情報に従い、登録情報リストから職位マスタ情報リストに変換する。<br>
	 * インポート不能登録情報が存在した場合は、MosP処理情報にエラーメッセージを残す。<br>
	 * @param importDto インポートマスタ情報
	 * @param dataList 登録情報リスト
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	
	protected void getTargetLists(ImportDtoInterface importDto, List<String[]> dataList) throws MospException {
		// 所属マスタリスト準備
		positionList = new ArrayList<PositionDtoInterface>();
		// インポートフィールド情報取得
		List<ImportFieldDtoInterface> fieldList = getImportFieldList(importDto.getImportCode());
		// 登録情報リスト内の各登録情報長を確認
		checkCsvLength(fieldList, dataList);
		// エラー確認
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 登録情報リスト毎に処理
		for (int i = 0; i < dataList.size(); i++) {
			// 登録情報取得
			String[] data = dataList.get(i);
			// 職位情報取得及び確認
			PositionDtoInterface positionDto = getPositionDto(fieldList, data, i);
			if (positionDto != null) {
				// 職位情報リストに追加
				positionList.add(positionDto);
			}
		}
	}
	
	/**
	 * 職位情報を取得する。<br>
	 * インポートフィールド情報リストに従い、登録情報リストから所属情報に変換する。<br>
	 * インポート不能登録情報が存在した場合は、MosP処理情報にエラーメッセージを残す。<br>
	 * インポート不能登録情報の場合は、nullを返す。<br>
	 * @param fieldList インポートフィールド情報リスト
	 * @param data      登録情報
	 * @param row       行インデックス
	 * @return 職位情報
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected PositionDtoInterface getPositionDto(List<ImportFieldDtoInterface> fieldList, String[] data, int row)
			throws MospException {
		String positionCode = getFieldValue(PfmPositionDao.COL_POSITION_CODE, fieldList, data);
		Date activateDate = getDateFieldValue(PfmPositionDao.COL_ACTIVATE_DATE, fieldList, data);
		String positionName = getFieldValue(PfmPositionDao.COL_POSITION_NAME, fieldList, data);
		String positionAbbr = getFieldValue(PfmPositionDao.COL_POSITION_ABBR, fieldList, data);
		int positionGrade = getIntegerFieldValue(PfmPositionDao.COL_POSITION_GRADE, fieldList, data);
		String closeFlag = getFieldValue(PfmSectionDao.COL_CLOSE_FLAG, fieldList, data);
		// 職位コード確認
		if (MospUtility.isEmpty(positionCode)) {
			// エラーメッセージを設定
			PfMessageUtility.addErrorRequired(mospParams, PfNameUtility.positionCode(mospParams), row);
			return null;
		}
		// 有効日確認
		if (MospUtility.isEmpty(activateDate)) {
			// エラーメッセージ追加
			PfMessageUtility.addErrorActivateDateRequired(mospParams, row);
			return null;
		}
		// 職位情報準備
		PositionDtoInterface position = dao.findForInfo(positionCode, activateDate);
		// 職位情報確認
		if (position == null) {
			position = getInitDto();
		}
		// 職位情報に登録情報の内容を設定
		position.setPositionCode(positionCode);
		position.setActivateDate(activateDate);
		if (positionName != null) {
			position.setPositionName(positionName);
		} else if (position.getPositionName() == null) {
			// エラーメッセージを設定
			PfMessageUtility.addErrorRequired(mospParams, PfNameUtility.positionName(mospParams), row);
			return null;
		}
		if (positionAbbr != null) {
			position.setPositionAbbr(positionAbbr);
		} else if (position.getPositionAbbr() == null) {
			// エラーメッセージを設定
			PfMessageUtility.addErrorRequired(mospParams, PfNameUtility.positionAbbreviation(mospParams), row);
			return null;
		}
		position.setPositionGrade(positionGrade);
		position.setInactivateFlag(MospConst.INACTIVATE_FLAG_OFF);
		if (closeFlag != null && !closeFlag.isEmpty()) {
			try {
				position.setInactivateFlag(Integer.parseInt(closeFlag));
			} catch (NumberFormatException e) {
				PfMessageUtility.addErrorActivateOrInactivateInvalid(mospParams, row);
				return null;
			}
		}
		// 入力チェック
		validate(position, Integer.valueOf(row));
		return position;
	}
	
	/**
	 * 職位情報を登録する。<br>
	 * @param position 職位情報
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void registPositionDto(PositionDtoInterface position) throws MospException {
		// 職位情報確認
		if (position == null) {
			return;
		}
		// 職位情報取得及び確認
		List<PositionDtoInterface> list = dao.findForHistory(position.getPositionCode());
		if (list.isEmpty()) {
			// 新規登録
			insert(position);
			return;
		}
		// 職位情報取得及び確認
		PositionDtoInterface dto = dao.findForKey(position.getPositionCode(), position.getActivateDate());
		if (dto == null) {
			// 履歴追加
			add(position);
			return;
		}
		// 履歴更新
		update(position);
	}
	
}
