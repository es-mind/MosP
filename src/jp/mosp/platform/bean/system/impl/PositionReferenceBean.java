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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.property.RangeProperty;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.human.ConcurrentReferenceBeanInterface;
import jp.mosp.platform.bean.system.PositionReferenceBeanInterface;
import jp.mosp.platform.comparator.base.PositionCodeComparator;
import jp.mosp.platform.comparator.system.PositionMasterGradeLevelComparator;
import jp.mosp.platform.dao.system.PositionDaoInterface;
import jp.mosp.platform.dto.human.ConcurrentDtoInterface;
import jp.mosp.platform.dto.system.PositionDtoInterface;
import jp.mosp.platform.utils.PfNameUtility;

/**
 * 職位マスタ参照クラス。
 */
public class PositionReferenceBean extends PlatformBean implements PositionReferenceBeanInterface {
	
	/**
	 * 職位マスタDAO。
	 */
	protected PositionDaoInterface dao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public PositionReferenceBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		dao = createDaoInstance(PositionDaoInterface.class);
	}
	
	@Override
	public List<PositionDtoInterface> getPositionHistory(String positionCode) throws MospException {
		return dao.findForHistory(positionCode);
	}
	
	@Override
	public PositionDtoInterface getPositionInfo(String positionCode, Date targetDate) throws MospException {
		return dao.findForInfo(positionCode, targetDate);
	}
	
	@Override
	public String getPositionName(String positionCode, Date targetDate) throws MospException {
		PositionDtoInterface dto = getPositionInfo(positionCode, targetDate);
		if (dto == null) {
			return "";
		}
		return dto.getPositionName();
	}
	
	@Override
	public String getPositionAbbr(String positionCode, Date targetDate) throws MospException {
		PositionDtoInterface dto = getPositionInfo(positionCode, targetDate);
		if (dto == null) {
			return "";
		}
		return dto.getPositionAbbr();
	}
	
	@Override
	public List<PositionDtoInterface> getPositionList(Date targetDate, String operationType) throws MospException {
		// 操作範囲準備
		String[] rangeArray = new String[0];
		// 操作区分確認
		if (operationType != null && operationType.isEmpty() == false) {
			// 操作範囲取得
			rangeArray = getRangePosition(operationType, targetDate);
		}
		// リストを取得
		return dao.findForActivateDate(targetDate, rangeArray);
	}
	
	@Override
	public Map<String, PositionDtoInterface> getPositionMap(Date targetDate, String operationType)
			throws MospException {
		// リストを取得しマップに変換
		return getMap(getPositionList(targetDate, operationType));
	}
	
	@Override
	public PositionDtoInterface findForkey(long id) throws MospException {
		BaseDto dto = findForKey(dao, id, false);
		if (dto != null) {
			return (PositionDtoInterface)dto;
		}
		return null;
	}
	
	@Override
	public boolean hasAdvantage(PositionDtoInterface dto, int grade) {
		return grade > dto.getPositionGrade() == hasLowGradeAdvantage();
	}
	
	@Override
	public boolean hasLowGradeAdvantage() {
		return dao.hasLowGradeAdvantage();
	}
	
	@Override
	public String[][] getSelectArray(Date targetDate, boolean needBlank, String operationType) throws MospException {
		// プルダウン用配列取得(略称)
		return getSelectArray(targetDate, needBlank, operationType, false, false, false);
	}
	
	@Override
	public String[][] getNameSelectArray(Date targetDate, boolean needBlank, String operationType)
			throws MospException {
		// プルダウン用配列取得(名称)
		return getSelectArray(targetDate, needBlank, operationType, true, false, false);
	}
	
	@Override
	public String[][] getCodedSelectArray(Date targetDate, boolean needBlank, String operationType)
			throws MospException {
		// プルダウン用配列取得(コード + 等級 + 名称)
		return getSelectArray(targetDate, needBlank, operationType, true, true, true);
	}
	
	@Override
	public String[][] getCodedAbbrSelectArray(Date targetDate, boolean needBlank, String operationType)
			throws MospException {
		// プルダウン用配列取得(コード + 等級 + 略称)
		return getSelectArray(targetDate, needBlank, operationType, false, true, true);
	}
	
	@Override
	public String[][] getGradedSelectArray(Date targetDate, boolean needBlank, String operationType)
			throws MospException {
		// プルダウン用配列取得(等級+名称)
		return getSelectArray(targetDate, needBlank, operationType, true, false, true);
	}
	
	/**
	 * プルダウン用配列を取得する。<br>
	 * viewCode、viewGrade共にtrueとした場合は、コードのみが表示される。<br>
	 * 操作範囲(職位)に範囲(自身)が含まれている場合、兼務職位を含めた配列を取得する。<br>
	 * @param targetDate    対象年月日
	 * @param needBlank     空白行要否(true：空白行要、false：空白行不要)
	 * @param operationType 操作範囲権限
	 * @param isName        名称表示(true：名称表示、false：略称表示)
	 * @param viewCode      コード表示(true：コード表示、false：コード非表示)
	 * @param viewGrade     等級表示(true：等級表示、false：等級非表示)
	 * @return プルダウン用配列
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected String[][] getSelectArray(Date targetDate, boolean needBlank, String operationType, boolean isName,
			boolean viewCode, boolean viewGrade) throws MospException {
		// 操作範囲(職位)配列取得
		String[] rangeArray = getRangePosition(operationType, targetDate);
		// 操作範囲(職位)配列に兼務所属情報を追加
		rangeArray = addConcurrentPosition(operationType, targetDate, rangeArray);
		// 一覧取得
		List<PositionDtoInterface> list = dao.findForActivateDate(targetDate, rangeArray);
		// 一覧件数確認
		if (list.size() == 0) {
			// 対象データ無し
			return getNoObjectDataPulldown();
		}
		// ソート(コード)
		sortList(list, PositionCodeComparator.class, false);
		// 等級表示確認
		if (viewGrade) {
			// ソート(等級、号数)
			sortList(list, PositionMasterGradeLevelComparator.class, hasLowGradeAdvantage());
		}
		// コード最大長取得
		int codeLength = getMaxCodeLength(list, viewCode);
		int gradeLength = getMaxGradeLength(list, viewGrade);
		// プルダウン用配列及びインデックス準備
		String[][] array = prepareSelectArray(list.size(), needBlank);
		int idx = needBlank ? 1 : 0;
		// プルダウン用配列作成
		for (PositionDtoInterface dto : list) {
			// コード設定
			array[idx][0] = dto.getPositionCode();
			// 表示内容設定
			if (isName && viewCode && viewGrade) {
				// コード + 等級 + 名称
				array[idx++][1] = getCodedName(dto.getPositionCode(), dto.getPositionGrade(), dto.getPositionName(),
						codeLength, gradeLength);
			} else if (isName && viewCode) {
				// コード + 名称
				array[idx++][1] = getCodedName(dto.getPositionCode(), dto.getPositionName(), codeLength);
			} else if (isName && viewGrade) {
				// 等級 + 名称
				array[idx++][1] = getCodedName(String.valueOf(dto.getPositionGrade()), dto.getPositionName(),
						gradeLength);
			} else if (isName) {
				// 名称
				array[idx++][1] = dto.getPositionName();
			} else if (viewCode && viewGrade) {
				// コード + 等級 + 略称
				array[idx++][1] = getCodedName(dto.getPositionCode(), dto.getPositionGrade(), dto.getPositionAbbr(),
						codeLength, gradeLength);
			} else if (viewCode) {
				// コード + 略称
				array[idx++][1] = getCodedName(dto.getPositionCode(), dto.getPositionAbbr(), codeLength);
			} else if (viewGrade) {
				// 等級 + 略称
				array[idx++][1] = getCodedName(String.valueOf(dto.getPositionGrade()), dto.getPositionAbbr(),
						gradeLength);
			} else {
				// 略称
				array[idx++][1] = dto.getPositionAbbr();
			}
		}
		return array;
	}
	
	/**
	 * 操作範囲(職位)配列に兼務所属情報を追加する。<br>
	 * 操作範囲(職位)に範囲(自身)が含まれている場合、兼務職位情報を追加する。<br>
	 * @param operationType 操作区分
	 * @param targetDate    対象日
	 * @param rangeArray    操作範囲(職位)配列
	 * @return 兼務職位情報を追加した操作範囲(職位)配列
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected String[] addConcurrentPosition(String operationType, Date targetDate, String[] rangeArray)
			throws MospException {
		// 操作区分確認
		if (operationType == null) {
			return rangeArray;
		}
		// 操作範囲情報取得及び確認
		RangeProperty range = mospParams.getStoredInfo().getRangeMap().get(operationType);
		if (range == null) {
			return rangeArray;
		}
		// 操作範囲(職位)取得及び確認
		String rangePosition = range.getPosition();
		if (rangePosition == null || rangePosition.isEmpty()) {
			return rangeArray;
		}
		// 操作範囲(職位)をリストに変換
		List<String> rangePositionList = asList(rangePosition, MospConst.APP_PROPERTY_SEPARATOR);
		// 範囲(自身)の確認
		if (rangePositionList.contains(MospConst.RANGE_MYSELF) == false) {
			return rangeArray;
		}
		// 操作範囲(職位)配列をリストに変換(ここに兼務情報を追加する)
		List<String> rangeList = new ArrayList<String>(MospUtility.asList(rangeArray));
		// 人事兼務情報参照クラス取得
		ConcurrentReferenceBeanInterface concurrentRefer = (ConcurrentReferenceBeanInterface)createBean(
				ConcurrentReferenceBeanInterface.class);
		// 対象日時点で終了していない人事兼務情報のリストを取得
		List<ConcurrentDtoInterface> concurrentList = concurrentRefer
			.getConcurrentList(mospParams.getUser().getPersonalId(), targetDate);
		// 人事兼務情報毎に処理
		for (ConcurrentDtoInterface concurrent : concurrentList) {
			// 操作範囲(職位)リストに追加
			rangeList.add(concurrent.getPositionCode());
		}
		return rangeList.toArray(rangeArray);
	}
	
	/**
	 * リスト中のDTOにおけるコード最大文字数を取得する。<br>
	 * @param list     対象リスト
	 * @param viewCode コード表示(true：コード表示、false：コード非表示)
	 * @return リスト中のDTOにおけるコード最大文字数
	 */
	protected int getMaxCodeLength(List<PositionDtoInterface> list, boolean viewCode) {
		// コード表示確認
		if (viewCode == false) {
			return 0;
		}
		// コード最大文字数
		int length = 0;
		// コード最大文字数確認
		for (PositionDtoInterface dto : list) {
			if (dto.getPositionCode().length() > length) {
				length = dto.getPositionCode().length();
			}
		}
		return length;
	}
	
	/**
	 * リスト中のDTOにおける等級最大文字数を取得する。<br>
	 * @param list      対象リスト
	 * @param viewGrade 等級表示(true：等級表示、false：等級非表示)
	 * @return リスト中のDTOにおける等級最大文字数
	 */
	protected int getMaxGradeLength(List<PositionDtoInterface> list, boolean viewGrade) {
		// 等級表示確認
		if (viewGrade == false) {
			return 0;
		}
		// 等級最大文字数
		int length = 0;
		// 等級最大文字数確認
		for (PositionDtoInterface dto : list) {
			if (String.valueOf(dto.getPositionGrade()).length() > length) {
				length = String.valueOf(dto.getPositionGrade()).length();
			}
		}
		return length;
	}
	
	/**
	 * コード及び等級を付加した名称を取得する。<br>
	 * コード及び等級の文字列長は、コード長及び等級長に依る。<br>
	 * @param code   コード等
	 * @param grade  等級
	 * @param name   名称
	 * @param codeLength コード長
	 * @param gradeLength 等級長
	 * @return コード及び等級を付加した名称
	 */
	protected String getCodedName(String code, int grade, String name, int codeLength, int gradeLength) {
		// 文字列準備
		StringBuffer sb = new StringBuffer();
		// 必要なコード用空白数を取得
		int codeDoubleBytes = (codeLength - code.length()) / 2;
		int codeSingleBytes = (codeLength - code.length()) % 2;
		// 必要な空白数を取得(等級と名称の間の空白分1を加算)
		int doubleBytes = (gradeLength - String.valueOf(grade).length() + 1) / 2;
		int singleBytes = (gradeLength - String.valueOf(grade).length() + 1) % 2;
		// コード付加
		sb.append(code);
		// コード用全角空白設定
		for (int i = 0; i < codeDoubleBytes; i++) {
			sb.append(MospConst.STR_DB_SPACE);
		}
		// コード用半角空白設定
		for (int i = 0; i < codeSingleBytes; i++) {
			sb.append(MospConst.STR_SB_SPACE);
		}
		// 等級付加
		sb.append(PfNameUtility.squareParentheses(mospParams, String.valueOf(grade)));
		// 全角空白設定
		for (int i = 0; i < doubleBytes; i++) {
			sb.append(MospConst.STR_DB_SPACE);
		}
		// 半角空白設定
		for (int i = 0; i < singleBytes; i++) {
			sb.append(MospConst.STR_SB_SPACE);
		}
		// 名称付加
		sb.append(name);
		return sb.toString();
	}
	
	/**
	 * 職位マスタリストから職位マスタマップを取得する。<br>
	 * マップのキーは職位コード。<br>
	 * @param list 職位マスタリスト
	 * @return 職位マスタマップ
	 */
	protected Map<String, PositionDtoInterface> getMap(List<PositionDtoInterface> list) {
		// マップ準備
		Map<String, PositionDtoInterface> map = new HashMap<String, PositionDtoInterface>();
		// マスタリストからマスタマップを作成
		for (PositionDtoInterface dto : list) {
			map.put(dto.getPositionCode(), dto);
		}
		return map;
	}
	
}
