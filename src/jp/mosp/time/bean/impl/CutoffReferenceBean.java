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
package jp.mosp.time.bean.impl;

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.time.bean.CutoffReferenceBeanInterface;
import jp.mosp.time.constant.TimeMessageConst;
import jp.mosp.time.dao.settings.CutoffDaoInterface;
import jp.mosp.time.dto.settings.CutoffDtoInterface;
import jp.mosp.time.entity.CutoffEntityInterface;
import jp.mosp.time.utils.TimeUtility;

/**
 * 締日管理参照クラス。
 */
public class CutoffReferenceBean extends PlatformBean implements CutoffReferenceBeanInterface {
	
	/**
	 * 締日管理DAO
	 */
	private CutoffDaoInterface dao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public CutoffReferenceBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		dao = createDaoInstance(CutoffDaoInterface.class);
	}
	
	@Override
	public CutoffDtoInterface getCutoffInfo(String cutoffCode, Date targetDate) throws MospException {
		return dao.findForInfo(cutoffCode, targetDate);
	}
	
	@Override
	public List<CutoffDtoInterface> getCutoffHistory(String cutoffCode) throws MospException {
		return dao.findForHistory(cutoffCode);
	}
	
	@Override
	public String[][] getSelectArray(Date targetDate) throws MospException {
		// 一覧取得
		List<CutoffDtoInterface> list = dao.findForActivateDate(targetDate);
		// 一覧件数確認
		if (list.size() == 0) {
			// 対象データ無し
			return getNoObjectDataPulldown();
		}
		// 配列及びインデックス宣言
		String[][] array = new String[list.size()][2];
		int i = 0;
		// 配列作成
		for (CutoffDtoInterface dto : list) {
			array[i][0] = dto.getCutoffCode();
			array[i++][1] = dto.getCutoffAbbr();
		}
		return array;
	}
	
	@Override
	public String[][] getSelectCodeArray(Date targetDate) throws MospException {
		// 一覧取得
		List<CutoffDtoInterface> list = dao.findForActivateDate(targetDate);
		// 一覧件数確認
		if (list.size() == 0) {
			// 対象データ無し
			return getNoObjectDataPulldown();
		}
		// 配列及びインデックス宣言
		String[][] array = new String[list.size()][2];
		int i = 0;
		// 配列作成
		for (CutoffDtoInterface dto : list) {
			array[i][0] = dto.getCutoffCode();
			array[i++][1] = dto.getCutoffCode() + " " + dto.getCutoffName();
		}
		return array;
	}
	
	@Override
	public String[][] getCodedAbbrSelectArray(Date targetDate, boolean needBlank) throws MospException {
		// プルダウン用配列取得(コード+略称)
		return getSelectArray(targetDate, needBlank, false, true);
	}
	
	@Override
	public CutoffDtoInterface findForKey(String cutoffCode, Date activateDate) throws MospException {
		return dao.findForKey(cutoffCode, activateDate);
	}
	
	@Override
	public void chkExistCutoff(CutoffDtoInterface dto, Date targetDate) {
		if (dto == null) {
			String errorMes1 = "";
			if (targetDate == null) {
				errorMes1 = DateUtility.getStringDate(DateUtility.getSystemDate());
			} else {
				errorMes1 = DateUtility.getStringDate(targetDate);
			}
			String[] aryRep = { errorMes1, mospParams.getName("CutoffDate", "Information"),
				mospParams.getName("CutoffDate", "Information") };
			mospParams.addErrorMessage(TimeMessageConst.MSG_SETTING_APPLICATION_DEFECT_2, aryRep);
		}
	}
	
	/**
	 * プルダウン用配列を取得する。<br>
	 * @param targetDate 対象年月日
	 * @param needBlank  空白行要否(true：空白行要、false：空白行不要)
	 * @param isName     名称表示(true：名称表示、false：略称表示)
	 * @param viewCode   コード表示(true：コード表示、false：コード非表示)
	 * @return プルダウン用配列
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected String[][] getSelectArray(Date targetDate, boolean needBlank, boolean isName, boolean viewCode)
			throws MospException {
		// 一覧取得
		List<CutoffDtoInterface> list = dao.findForActivateDate(targetDate);
		// 一覧件数確認
		if (list.size() == 0) {
			// 対象データ無し
			return getNoObjectDataPulldown();
		}
		// コード最大長取得
		int length = getMaxCodeLength(list, viewCode);
		// プルダウン用配列及びインデックス準備
		String[][] array = prepareSelectArray(list.size(), needBlank);
		int idx = needBlank ? 1 : 0;
		// プルダウン用配列作成
		for (CutoffDtoInterface dto : list) {
			// コード設定
			array[idx][0] = dto.getCutoffCode();
			// 表示内容設定
			if (isName && viewCode) {
				// コード+名称
				array[idx++][1] = getCodedName(dto.getCutoffCode(), dto.getCutoffName(), length);
			} else if (isName) {
				// 名称
				array[idx++][1] = dto.getCutoffName();
			} else if (viewCode) {
				// コード+略称
				array[idx++][1] = getCodedName(dto.getCutoffCode(), dto.getCutoffAbbr(), length);
			} else {
				// 略称
				array[idx++][1] = dto.getCutoffAbbr();
			}
		}
		return array;
	}
	
	/**
	 * リスト中のDTOにおけるコード最大文字数を取得する。<br>
	 * @param list     対象リスト
	 * @param viewCode コード表示(true：コード表示、false：コード非表示)
	 * @return リスト中のDTOにおけるコード最大文字数
	 */
	protected int getMaxCodeLength(List<CutoffDtoInterface> list, boolean viewCode) {
		// コード表示確認
		if (viewCode == false) {
			return 0;
		}
		// コード最大文字数
		int length = 0;
		// コード最大文字数確認
		for (CutoffDtoInterface dto : list) {
			if (dto.getCutoffCode().length() > length) {
				length = dto.getCutoffCode().length();
			}
		}
		return length;
	}
	
	@Override
	public String[][] getSelectArray(Date targetDate, boolean needBlank) throws MospException {
		// プルダウン用配列取得(略称)
		return getSelectArray(targetDate, needBlank, false, false);
	}
	
	@Override
	public String[][] getCodedSelectArray(Date targetDate, boolean needBlank, String operationType)
			throws MospException {
		// プルダウン用配列取得(コード+名称)
		return getSelectArray(targetDate, needBlank, true, true);
	}
	
	@Override
	public CutoffEntityInterface getCutoffEntity(String cutoffCode, Date targetDate) throws MospException {
		// 締日エンティティを準備
		CutoffEntityInterface entity = TimeUtility.getBareCutoffEntity(mospParams);
		// 締日エンティティに締日情報を設定
		entity.setCutoffDto(getCutoffInfo(cutoffCode, targetDate));
		// 締日エンティティを取得
		return entity;
	}
	
}
