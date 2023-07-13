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
package jp.mosp.platform.base;

import java.util.ArrayList;
import java.util.List;

import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.BaseVo;
import jp.mosp.framework.js.DirectJs;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.constant.PlatformConst;

/**
 * MosPプラットフォームにおける画面の基本情報を格納する。<br>
 */
public class PlatformVo extends BaseVo {
	
	private static final long					serialVersionUID	= -8491195829751058929L;
	
	/**
	 * 編集モード。<br>
	 */
	private String								modeCardEdit;
	
	/**
	 * 有効日モード。<br>
	 */
	private String								modeActivateDate;
	
	/**
	 * ページ繰りコマンド。<br>
	 * 一覧表示時に用いる。<br>
	 */
	private String								pageCommand;
	
	/**
	 * 選択ページ番号。<br>
	 * 一覧表示時に用いる。<br>
	 */
	private String								selectIndex;
	
	/**
	 * 1ページ当たりの表示件数。<br>
	 * 一覧表示時に用いる。<br>
	 */
	private int									dataPerPage;
	
	/**
	 * 昇順降順フラグ。<br>
	 * 一覧表示時に用いる。<br>
	 */
	private boolean								isAscending;
	
	/**
	 * ソート用比較クラス名。<br>
	 * 一覧表示時に用いる。<br>
	 */
	private String								comparatorName;
	
	/**
	 * リスト。<br>
	 * 一覧表示時に用いる。<br>
	 */
	private List<? extends BaseDtoInterface>	list;
	
	/**
	 * 共通承認者プルダウン。<br>
	 */
	private String[][][]						aryApproverInfo;
	
	/**
	 * 共通承認ラベル。<br>
	 */
	private String[]							aryPltLblApproverSetting;
	
	/**
	 * 共通承認者用保存領域1。<br>
	 */
	private String								pltApproverSetting1;
	
	/**
	 * 共通承認者用保存領域2。<br>
	 */
	private String								pltApproverSetting2;
	
	/**
	 * 共通承認者用保存領域3。<br>
	 */
	private String								pltApproverSetting3;
	
	/**
	 * 共通承認者用保存領域4。<br>
	 */
	private String								pltApproverSetting4;
	
	/**
	 * 共通承認者用保存領域5。<br>
	 */
	private String								pltApproverSetting5;
	
	/**
	 * 共通承認者用保存領域6。<br>
	 */
	private String								pltApproverSetting6;
	
	/**
	 * 共通承認者用保存領域7。<br>
	 */
	private String								pltApproverSetting7;
	
	/**
	 * 共通承認者用保存領域8。<br>
	 */
	private String								pltApproverSetting8;
	
	/**
	 * 共通承認者用保存領域9。<br>
	 */
	private String								pltApproverSetting9;
	
	/**
	 * 共通承認者用保存領域10。<br>
	 */
	private String								pltApproverSetting10;
	
	/**
	 * 共通承認プルダウン設定。<br>
	 */
	private String[]							aryPltApproverSetting;
	
	/**
	 * 共通承認者用クラス名。<br>
	 */
	private String[]							pltApproverSetting;
	
	/**
	 * 一覧チェック項目配列。<br>
	 */
	private String[]							ckbSelect;
	
	/**
	 * 追加パラメータ1。<br>
	 * アドオン等でパラメータを追加する際に用いる。<br>
	 */
	private String								prmExtra1;
	
	/**
	 * 追加パラメータ2。<br>
	 * アドオン等でパラメータを追加する際に用いる。<br>
	 */
	private String								prmExtra2;
	
	/**
	 * 勤怠設定追加ロード時JS関数配列。<br>
	 * platform.js#onLoadArrary()で実行される。
	 */
	@DirectJs
	private String[]							aryOnLoadName;
	
	
	/**
	 * VOの初期設定を行う。<br>
	 * <br>
	 */
	public PlatformVo() {
		// 一覧表示用リスト初期化
		list = new ArrayList<PlatformDtoInterface>();
		// 勤怠設定追加ロード時JS関数配列を初期化
		aryOnLoadName = new String[0];
	}
	
	/**
	 * @return modeCardEdit
	 */
	public String getModeCardEdit() {
		return modeCardEdit;
	}
	
	/**
	 * @param modeCardEdit セットする modeCardEdit
	 */
	public void setModeCardEdit(String modeCardEdit) {
		this.modeCardEdit = modeCardEdit;
	}
	
	/**
	 * @return modeActivateDate
	 */
	public String getModeActivateDate() {
		return modeActivateDate;
	}
	
	/**
	 * @param modeActivateDate セットする modeActivateDate
	 */
	public void setModeActivateDate(String modeActivateDate) {
		this.modeActivateDate = modeActivateDate;
	}
	
	/**
	 * @return 確認結果(true：有効日モードは決定である、false：変更である)
	 */
	public boolean isModeActivateDateFixed() {
		return modeActivateDate.equals(PlatformConst.MODE_ACTIVATE_DATE_FIXED);
	}
	
	/**
	 * @return pageCommand
	 */
	public String getPageCommand() {
		return pageCommand;
	}
	
	/**
	 * @param pageCommand セットする pageCommand
	 */
	public void setPageCommand(String pageCommand) {
		this.pageCommand = pageCommand;
	}
	
	/**
	 * @return selectIndex
	 */
	public String getSelectIndex() {
		return selectIndex;
	}
	
	/**
	 * @param selectIndex セットする selectIndex
	 */
	public void setSelectIndex(String selectIndex) {
		this.selectIndex = selectIndex;
	}
	
	/**
	 * @return dataPerPage
	 */
	public int getDataPerPage() {
		return dataPerPage;
	}
	
	/**
	 * @param dataPerPage セットする dataPerPage
	 */
	public void setDataPerPage(int dataPerPage) {
		this.dataPerPage = dataPerPage;
	}
	
	/**
	 * @return isAscending
	 */
	public boolean isAscending() {
		return isAscending;
	}
	
	/**
	 * @param isAscending セットする isAscending
	 */
	public void setAscending(boolean isAscending) {
		this.isAscending = isAscending;
	}
	
	/**
	 * @return comparatorName
	 */
	public String getComparatorName() {
		return comparatorName;
	}
	
	/**
	 * @param comparatorName セットする comparatorName
	 */
	public void setComparatorName(String comparatorName) {
		this.comparatorName = comparatorName;
	}
	
	/**
	 * @return list
	 */
	public List<? extends BaseDtoInterface> getList() {
		return list;
	}
	
	/**
	 * @param list セットする list
	 */
	public void setList(List<? extends BaseDtoInterface> list) {
		this.list = list;
	}
	
	/**
	 * @return aryAryPltApproverSetting
	 */
	public String[][][] getAryApproverInfo() {
		return getStringArrayClone(aryApproverInfo);
	}
	
	/**
	 * @param aryApproverInfo セットする aryApproverInfo
	 */
	public void setAryApproverInfo(String[][][] aryApproverInfo) {
		this.aryApproverInfo = getStringArrayClone(aryApproverInfo);
	}
	
	/**
	 * @return aryPltLblApproverSetting
	 */
	public String[] getAryPltLblApproverSetting() {
		return getStringArrayClone(aryPltLblApproverSetting);
	}
	
	/**
	 * @param aryPltLblApproverSetting セットする aryPltLblApproverSetting
	 */
	public void setAryPltLblApproverSetting(String[] aryPltLblApproverSetting) {
		this.aryPltLblApproverSetting = getStringArrayClone(aryPltLblApproverSetting);
	}
	
	/**
	 * @return pltApproverSetting1
	 */
	public String getPltApproverSetting1() {
		return pltApproverSetting1;
	}
	
	/**
	 * @param pltApproverSetting1 セットする pltApproverSetting1
	 */
	public void setPltApproverSetting1(String pltApproverSetting1) {
		this.pltApproverSetting1 = pltApproverSetting1;
	}
	
	/**
	 * @return pltApproverSetting2
	 */
	public String getPltApproverSetting2() {
		return pltApproverSetting2;
	}
	
	/**
	 * @param pltApproverSetting2 セットする pltApproverSetting2
	 */
	public void setPltApproverSetting2(String pltApproverSetting2) {
		this.pltApproverSetting2 = pltApproverSetting2;
	}
	
	/**
	 * @return pltApproverSetting3
	 */
	public String getPltApproverSetting3() {
		return pltApproverSetting3;
	}
	
	/**
	 * @param pltApproverSetting3 セットする pltApproverSetting3
	 */
	public void setPltApproverSetting3(String pltApproverSetting3) {
		this.pltApproverSetting3 = pltApproverSetting3;
	}
	
	/**
	 * @return pltApproverSetting4
	 */
	public String getPltApproverSetting4() {
		return pltApproverSetting4;
	}
	
	/**
	 * @param pltApproverSetting4 セットする pltApproverSetting4
	 */
	public void setPltApproverSetting4(String pltApproverSetting4) {
		this.pltApproverSetting4 = pltApproverSetting4;
	}
	
	/**
	 * @return pltApproverSetting5
	 */
	public String getPltApproverSetting5() {
		return pltApproverSetting5;
	}
	
	/**
	 * @param pltApproverSetting5 セットする pltApproverSetting5
	 */
	public void setPltApproverSetting5(String pltApproverSetting5) {
		this.pltApproverSetting5 = pltApproverSetting5;
	}
	
	/**
	 * @return pltApproverSetting6
	 */
	public String getPltApproverSetting6() {
		return pltApproverSetting6;
	}
	
	/**
	 * @param pltApproverSetting6 セットする pltApproverSetting6
	 */
	public void setPltApproverSetting6(String pltApproverSetting6) {
		this.pltApproverSetting6 = pltApproverSetting6;
	}
	
	/**
	 * @return pltApproverSetting7
	 */
	public String getPltApproverSetting7() {
		return pltApproverSetting7;
	}
	
	/**
	 * @param pltApproverSetting7 セットする pltApproverSetting7
	 */
	public void setPltApproverSetting7(String pltApproverSetting7) {
		this.pltApproverSetting7 = pltApproverSetting7;
	}
	
	/**
	 * @return pltApproverSetting8
	 */
	public String getPltApproverSetting8() {
		return pltApproverSetting8;
	}
	
	/**
	 * @param pltApproverSetting8 セットする pltApproverSetting8
	 */
	public void setPltApproverSetting8(String pltApproverSetting8) {
		this.pltApproverSetting8 = pltApproverSetting8;
	}
	
	/**
	 * @return pltApproverSetting9
	 */
	public String getPltApproverSetting9() {
		return pltApproverSetting9;
	}
	
	/**
	 * @param pltApproverSetting9 セットする pltApproverSetting9
	 */
	public void setPltApproverSetting9(String pltApproverSetting9) {
		this.pltApproverSetting9 = pltApproverSetting9;
	}
	
	/**
	 * @return pltApproverSetting10
	 */
	public String getPltApproverSetting10() {
		return pltApproverSetting10;
	}
	
	/**
	 * @param pltApproverSetting10 セットする pltApproverSetting10
	 */
	public void setPltApproverSetting10(String pltApproverSetting10) {
		this.pltApproverSetting10 = pltApproverSetting10;
	}
	
	/**
	 * @return aryPltApproverSetting
	 */
	public String[] getAryPltApproverSetting() {
		return getStringArrayClone(aryPltApproverSetting);
	}
	
	/**
	 * @param aryPltApproverSetting セットする aryPltApproverSetting
	 */
	public void setAryPltApproverSetting(String[] aryPltApproverSetting) {
		this.aryPltApproverSetting = getStringArrayClone(aryPltApproverSetting);
	}
	
	/**
	 * @return pltApproverSetting
	 */
	public String[] getPltApproverSetting() {
		return getStringArrayClone(pltApproverSetting);
	}
	
	/**
	 * @param pltApproverSetting セットする pltApproverSetting
	 */
	public void setPltApproverSetting(String[] pltApproverSetting) {
		this.pltApproverSetting = getStringArrayClone(pltApproverSetting);
	}
	
	/**
	 * @param idx インデックス
	 * @return ckbSelect
	 */
	public String getCkbSelect(int idx) {
		return ckbSelect[idx];
	}
	
	/**
	 * @return ckbSelect
	 */
	public String[] getCkbSelect() {
		return getStringArrayClone(ckbSelect);
	}
	
	/**
	 * @param ckbSelect セットする ckbSelect
	 */
	public void setCkbSelect(String[] ckbSelect) {
		this.ckbSelect = getStringArrayClone(ckbSelect);
	}
	
	/**
	 * @return prmExtra1
	 */
	public String getPrmExtra1() {
		return prmExtra1;
	}
	
	/**
	 * @param prmExtra1 セットする prmExtra1
	 */
	public void setPrmExtra1(String prmExtra1) {
		this.prmExtra1 = prmExtra1;
	}
	
	/**
	 * @return prmExtra2
	 */
	public String getPrmExtra2() {
		return prmExtra2;
	}
	
	/**
	 * @param prmExtra2 セットする prmExtra2
	 */
	public void setPrmExtra2(String prmExtra2) {
		this.prmExtra2 = prmExtra2;
	}
	
	/**
	 * 勤怠設定追加ロード時JS関数配列に勤怠設定追加ロード時JS関数を追加する。<br>
	 * @param addonOnload 勤怠設定追加ロード時JS関数
	 */
	public void addAddonOnloadFunction(String addonOnload) {
		// 勤怠設定追加ロード時JS関数リストを準備
		List<String> list = MospUtility.asList(aryOnLoadName);
		// 勤怠設定追加ロード時JS関数が既に設定されている場合
		if (list.contains(addonOnload)) {
			// 処理無し
			return;
		}
		// 勤怠設定追加ロード時JS関数リストに追加
		list.add(addonOnload);
		// 勤怠設定追加ロード時JS関数配列を設定
		aryOnLoadName = MospUtility.toArray(list);
	}
	
}
