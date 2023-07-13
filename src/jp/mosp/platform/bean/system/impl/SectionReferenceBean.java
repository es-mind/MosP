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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.property.RangeProperty;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.human.ConcurrentReferenceBeanInterface;
import jp.mosp.platform.bean.system.SectionReferenceBeanInterface;
import jp.mosp.platform.dao.system.SectionDaoInterface;
import jp.mosp.platform.dto.human.ConcurrentDtoInterface;
import jp.mosp.platform.dto.system.SectionDtoInterface;
import jp.mosp.platform.system.constant.PlatformSystemConst;
import jp.mosp.platform.utils.PfNameUtility;
import jp.mosp.platform.utils.PlatformUtility;

/**
 * 所属マスタ参照クラス。
 */
public class SectionReferenceBean extends PlatformBean implements SectionReferenceBeanInterface {
	
	/**
	 * MosPアプリケーション設定キー(所属プルダウン表示内容)。
	 */
	public static final String		APP_SECTION_PULLDOWN_NAME		= "SectionPulldownName";
	
	/**
	 * 所属プルダウン表示内容(所属表示名称)。
	 */
	public static final String		SECTION_PULLDOWN_NAME_DISPLAY	= "Display";
	
	/**
	 * 所属経路接続文字。<br>
	 * 一覧やプルダウンに表示される経路の接続文字。<br>
	 */
	protected static final String	CONCAT_CLASS_ROUTE				= ">";
	
	/**
	 * 所属経路上位所属文字。<br>
	 * プルダウン等で表示する際の階層を表す文字。<br>
	 */
	protected static final String	STR_SECTION_LEVEL				= "-";
	
	/**
	 * 所属マスタDAO。<br>
	 */
	protected SectionDaoInterface	dao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public SectionReferenceBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		dao = createDaoInstance(SectionDaoInterface.class);
	}
	
	@Override
	public List<SectionDtoInterface> getHigherSectionList(String sectionCode, Date targetDate) throws MospException {
		SectionDtoInterface dto = getSectionInfo(sectionCode, targetDate);
		// 上位所属リスト
		List<SectionDtoInterface> higherList = new ArrayList<SectionDtoInterface>();
		if (dto != null) {
			// 所属マスタから階層経路を取得
			String[] aryClassRoute = getClassRouteArray(dto.getClassRoute());
			for (int i = aryClassRoute.length - 1; i >= 0; i--) {
				SectionDtoInterface sectionDto = getSectionInfo(aryClassRoute[i], targetDate);
				if (sectionDto != null) {
					higherList.add(sectionDto);
				}
			}
		}
		return higherList;
	}
	
	@Override
	public List<SectionDtoInterface> getSectionHistory(String sectionCode) throws MospException {
		return dao.findForHistory(sectionCode);
	}
	
	@Override
	public SectionDtoInterface getSectionInfo(String sectionCode, Date targetDate) throws MospException {
		return dao.findForInfo(sectionCode, targetDate);
	}
	
	@Override
	public String getSectionName(String sectionCode, Date targetDate) throws MospException {
		SectionDtoInterface dto = getSectionInfo(sectionCode, targetDate);
		if (dto == null) {
			return sectionCode;
		}
		return dto.getSectionName();
	}
	
	@Override
	public String getSectionAbbr(String sectionCode, Date targetDate) throws MospException {
		SectionDtoInterface dto = getSectionInfo(sectionCode, targetDate);
		if (dto == null) {
			return sectionCode;
		}
		return dto.getSectionAbbr();
	}
	
	@Override
	public String getSectionDisplay(String sectionCode, Date targetDate) throws MospException {
		SectionDtoInterface dto = getSectionInfo(sectionCode, targetDate);
		if (dto == null) {
			return sectionCode;
		}
		return dto.getSectionDisplay();
	}
	
	@Override
	public String[][] getSelectArray(Date targetDate, boolean needBlank, String operationType) throws MospException {
		// プルダウン用配列取得(略称)
		return getSelectArray(targetDate, needBlank, false, false, operationType);
	}
	
	@Override
	public String[][] getNameSelectArray(Date targetDate, boolean needBlank, String operationType)
			throws MospException {
		// プルダウン用配列取得(名称)
		return getSelectArray(targetDate, needBlank, true, false, operationType);
	}
	
	@Override
	public String[][] getCodedAbbrSelectArray(Date targetDate, boolean needBlank, String operationType)
			throws MospException {
		// プルダウン用配列取得(コード+略称)
		return getSelectArray(targetDate, needBlank, false, true, operationType);
	}
	
	@Override
	public String[][] getCodedSelectArray(Date targetDate, boolean needBlank, String operationType)
			throws MospException {
		// プルダウン用配列取得(コード+名称)
		return getSelectArray(targetDate, needBlank, true, true, operationType);
	}
	
	@Override
	public String[][] getSelectArrayForMaintenance(Date targetDate) throws MospException {
		// 一覧取得
		List<SectionDtoInterface> list = dao.findForActivateDate(targetDate, getRangeSection(null, targetDate));
		// 配列宣言
		String[][] array = new String[list.size() + 1][2];
		int idx = 0;
		// 一行目設定
		array[idx][0] = PlatformSystemConst.SEPARATOR_CLASS_ROUTE;
		array[idx++][1] = PfNameUtility.registAsGreatest(mospParams);
		// 配列作成
		for (SectionDtoInterface dto : list) {
			// 経路設定
			array[idx][0] = dto.getClassRoute() + dto.getSectionCode() + PlatformSystemConst.SEPARATOR_CLASS_ROUTE;
			// 経路表示内容設定
			array[idx++][1] = getLeveledSectionName(dto);
		}
		return array;
	}
	
	@Override
	public String getClassRouteAbbr(SectionDtoInterface dto, Date targetDate) throws MospException {
		// 経路表示内容準備
		StringBuffer sb = new StringBuffer();
		// 経路から所属コードの配列を取得
		String[] sectionArray = getClassRouteArray(dto.getClassRoute());
		// 経路表示内容作成
		for (String sectionCode : sectionArray) {
			sb.append(getSectionAbbr(sectionCode, targetDate));
			sb.append(CONCAT_CLASS_ROUTE);
		}
		// 自所属略称追加
		sb.append(dto.getSectionAbbr());
		return sb.toString();
	}
	
	@Override
	public String[] getClassRouteNameArray(String sectionCode, Date targetDate) throws MospException {
		// 所属情報取得
		SectionDtoInterface dto = dao.findForInfo(sectionCode, targetDate);
		// 所属情報確認
		if (dto == null) {
			return new String[0];
		}
		// 経路から所属コードの配列を取得
		String[] sectionArray = getClassRouteArray(dto.getClassRoute());
		// 経路名称配列準備
		String[] nameArray = new String[sectionArray.length + 1];
		// 経路名称配列作成
		for (int i = 0; i < sectionArray.length; i++) {
			// 経路名称設定
			nameArray[i] = getSectionName(sectionArray[i], targetDate);
		}
		// 自所属名称設定
		nameArray[nameArray.length - 1] = dto.getSectionName();
		return nameArray;
	}
	
	/**
	 * 階層情報を付加した所属名称を取得する。<br>
	 * @param dto 対象DTO
	 * @return 階層情報を付加した所属名称
	 */
	protected String getLeveledSectionName(SectionDtoInterface dto) {
		// 経路表示内容準備
		StringBuffer sb = new StringBuffer();
		// 経路から所属コードの配列を取得
		String[] sectionArray = getClassRouteArray(dto.getClassRoute());
		// 階層数を取得
		int level = sectionArray.length;
		// 階層分の文字列を追加
		for (int i = 0; i < level; i++) {
			sb.append(STR_SECTION_LEVEL);
		}
		// 自所属略称追加
		sb.append(dto.getSectionName());
		return sb.toString();
	}
	
	@Override
	public String[] getClassRouteArray(SectionDtoInterface dto) {
		if (dto == null || dto.getClassRoute() == null) {
			return new String[0];
		}
		return getClassRouteArray(dto.getClassRoute());
	}
	
	/**
	 * 経路文字列から所属コードの配列を取得する。
	 * @param classRoute 経路
	 * @return 所属コードの配列
	 */
	protected String[] getClassRouteArray(String classRoute) {
		// 最初の,を除く
		String classRouteForArray = classRoute.substring(1);
		// 最上位部署確認
		if (classRouteForArray.length() == 0) {
			return new String[0];
		}
		// 最後の,を除く
		classRouteForArray = classRouteForArray.substring(0, classRouteForArray.length() - 1);
		// ,で分割
		return classRouteForArray.split(PlatformSystemConst.SEPARATOR_CLASS_ROUTE);
	}
	
	@Override
	public SectionDtoInterface findForKey(String sectionCode, Date activateDate) throws MospException {
		return dao.findForKey(sectionCode, activateDate);
	}
	
	@Override
	public SectionDtoInterface findForkey(long id) throws MospException {
		BaseDto dto = findForKey(dao, id, false);
		if (dto != null) {
			return (SectionDtoInterface)dto;
		}
		return null;
	}
	
	/**
	 * プルダウン用配列を取得する。<br>
	 * 操作範囲(所属)に範囲(自身)が含まれている場合、兼務所属を含めた配列を取得する。<br>
	 * @param targetDate    対象年月日
	 * @param needBlank     空白行要否(true：空白行要、false：空白行不要)
	 * @param isName        名称表示(true：名称表示、false：略称表示)
	 * @param viewCode      コード表示(true：コード表示、false：コード非表示)
	 * @param operationType 操作区分
	 * @return プルダウン用配列
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected String[][] getSelectArray(Date targetDate, boolean needBlank, boolean isName, boolean viewCode,
			String operationType) throws MospException {
		// 操作範囲(所属)配列取得
		String[] rangeArray = getRangeSection(operationType, targetDate);
		// 操作範囲(所属)配列に兼務所属情報を追加
		rangeArray = addConcurrentSection(operationType, targetDate, rangeArray);
		// 一覧取得
		List<SectionDtoInterface> list = dao.findForActivateDate(targetDate, rangeArray);
		// 一覧件数確認
		if (list.size() == 0) {
			// 対象データ無し
			return getNoObjectDataPulldown();
		}
		// コード最大長取得
		int length = getMaxCodeLength(list, viewCode);
		// 所属プルダウン表示内容設定取得
		boolean useDisplayName = useDisplayName();
		// プルダウン用配列及びインデックス準備
		String[][] array = prepareSelectArray(list.size(), needBlank);
		int idx = needBlank ? 1 : 0;
		// プルダウン用配列作成
		for (SectionDtoInterface dto : list) {
			// 名称取得
			String name = "";
			if (isName) {
				if (useDisplayName) {
					name = dto.getSectionDisplay();
				} else {
					name = dto.getSectionName();
				}
			} else {
				name = dto.getSectionAbbr();
			}
			// コード設定
			array[idx][0] = dto.getSectionCode();
			// 表示内容設定
			if (viewCode) {
				// コード+名称
				array[idx++][1] = getCodedName(dto.getSectionCode(), name, length);
			} else {
				// 名称
				array[idx++][1] = name;
			}
		}
		return array;
	}
	
	/**
	 * 操作範囲(所属)配列に兼務所属情報を追加する。<br>
	 * 操作範囲(所属)に範囲(自身)が含まれている場合、兼務所属情報を追加する。<br>
	 * @param operationType 操作区分
	 * @param targetDate    対象日
	 * @param rangeArray    操作範囲(所属)配列
	 * @return 兼務所属情報を追加した操作範囲(所属)配列
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected String[] addConcurrentSection(String operationType, Date targetDate, String[] rangeArray)
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
		// 操作範囲(所属)取得及び確認
		String rangeSection = range.getSection();
		if (rangeSection == null || rangeSection.isEmpty()) {
			return rangeArray;
		}
		// 操作範囲(所属)をリストに変換
		List<String> rangeSectionList = asList(rangeSection, MospConst.APP_PROPERTY_SEPARATOR);
		// 範囲(自身)の確認
		if (rangeSectionList.contains(MospConst.RANGE_MYSELF) == false) {
			return rangeArray;
		}
		// 操作範囲(所属)配列をリストに変換(ここに兼務情報を追加する)
		List<String> rangeList = new ArrayList<String>(MospUtility.asList(rangeArray));
		// 人事兼務情報参照クラス取得
		ConcurrentReferenceBeanInterface concurrentRefer = (ConcurrentReferenceBeanInterface)createBean(
				ConcurrentReferenceBeanInterface.class);
		// 対象日時点で終了していない人事兼務情報のリストを取得
		List<ConcurrentDtoInterface> concurrentList = concurrentRefer
			.getConcurrentList(mospParams.getUser().getPersonalId(), targetDate);
		// 人事兼務情報毎に処理
		for (ConcurrentDtoInterface concurrent : concurrentList) {
			// 操作範囲(所属)リストに追加
			rangeList.add(concurrent.getSectionCode());
		}
		return rangeList.toArray(rangeArray);
	}
	
	@Override
	public boolean useDisplayName() {
		// 所属表示内容が表示名称かを確認
		return PlatformUtility.isSectionDisplayName(mospParams);
	}
	
	/**
	 * リスト中のDTOにおけるコード最大文字数を取得する。<br>
	 * @param list     対象リスト
	 * @param viewCode コード表示(true：コード表示、false：コード非表示)
	 * @return リスト中のDTOにおけるコード最大文字数
	 */
	protected int getMaxCodeLength(List<SectionDtoInterface> list, boolean viewCode) {
		// コード表示確認
		if (viewCode == false) {
			return 0;
		}
		// コード最大文字数
		int length = 0;
		// コード最大文字数確認
		for (SectionDtoInterface dto : list) {
			if (dto.getSectionCode().length() > length) {
				length = dto.getSectionCode().length();
			}
		}
		return length;
	}
	
	@Override
	public int getMaxLevel(Date targetDate) throws MospException {
		// 所属情報リスト取得
		Map<String, Object> param = dao.getParamsMap();
		param.put(SectionDaoInterface.SEARCH_TARGET_DATE, targetDate);
		param.put(SectionDaoInterface.SEARCH_SECTION_TYPE, "");
		param.put(SectionDaoInterface.SEARCH_SECTION_CODE, "");
		param.put(SectionDaoInterface.SEARCH_SECTION_NAME, "");
		param.put(SectionDaoInterface.SEARCH_SECTION_ABBR, "");
		param.put(SectionDaoInterface.SEARCH_CLOSE_FLAG, MospConst.DELETE_FLAG_OFF);
		List<SectionDtoInterface> list = dao.findForSearch(param);
		// 最大階層数準備
		int maxLevel = 0;
		// 所属毎に処理
		for (SectionDtoInterface dto : list) {
			// 経路文字列から所属コードの配列を取得
			String[] route = getClassRouteArray(dto.getClassRoute());
			// 階層数確認
			maxLevel = maxLevel < route.length ? route.length : maxLevel;
		}
		return maxLevel;
	}
	
	@Override
	public String getHigherSectionCode(String sectionCode, Date targetDate, int level) throws MospException {
		// 対象階層の所属コードを準備
		String higherSectionCode = "";
		// 対象所属情報取得
		SectionDtoInterface dto = getSectionInfo(sectionCode, targetDate);
		// 対象所属情報確認
		if (dto == null) {
			return higherSectionCode;
		}
		// 経路文字列から所属コードの配列を取得
		String[] route = getClassRouteArray(dto.getClassRoute());
		// 階層数確認
		if (route.length > level) {
			// 対象階層の所属コードを取得
			higherSectionCode = route[level];
		}
		return higherSectionCode;
	}
	
	/**
	 * 所属コード群を取得する。<br>
	 * <br>
	 * 所属コードFromと所属コードToで、所属コード群を取得する。<br>
	 * <br>
	 * @param sectionCodeFrom 所属コードFrom
	 * @param sectionCodeTo   所属コードTo
	 * @param activateDate    有効日
	 * @return 所属コード群
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	@Override
	public Set<String> getSectionCodeSetForCodeRange(String sectionCodeFrom, String sectionCodeTo, Date activateDate)
			throws MospException {
		// 所属コードFromと所属コードToで所属コードリストを取得
		List<SectionDtoInterface> list = dao.findForCodeRange(sectionCodeFrom, sectionCodeTo, activateDate);
		// 所属情報リストから所属コード群を取得
		return getSectionCodeSet(list);
	}
	
	/**
	 * 所属情報リストから所属コード群を取得する。<br>
	 * <br>
	 * @param list 所属情報リスト
	 * @return 所属コード群
	 */
	protected Set<String> getSectionCodeSet(List<SectionDtoInterface> list) {
		// 所属コード群を準備
		Set<String> set = new HashSet<String>();
		// 所属情報毎に処理
		for (SectionDtoInterface dto : list) {
			// 所属コード群に追加
			set.add(dto.getSectionCode());
		}
		// 所属コード群を取得
		return set;
	}
	
}
