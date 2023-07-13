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
package jp.mosp.platform.workflow.vo;

import jp.mosp.platform.system.base.PlatformSystemVo;

/**
 * ルート詳細画面の情報を格納する。
 */
public class RouteCardVo extends PlatformSystemVo {
	
	private static final long	serialVersionUID	= 8569528569293504477L;
	
	private String				pltRouteStage;								// 承認階層
	private String				txtRouteCode;								// ルートコード
	private String				txtRouteName;								// ルート名称
	
	private String				pltUnitStage1;								// 1次ユニット名称
	private String				pltUnitStage2;								// 2次ユニット名称
	private String				pltUnitStage3;								// 3次ユニット名称
	private String				pltUnitStage4;								// 4次ユニット名称
	private String				pltUnitStage5;								// 5次ユニット名称
	private String				pltUnitStage6;								// 6次ユニット名称
	private String				pltUnitStage7;								// 7次ユニット名称
	private String				pltUnitStage8;								// 8次ユニット名称
	private String				pltUnitStage9;								// 9次ユニット名称
	private String				pltUnitStage10;								// 10次ユニット名称
	
	private String				lblApproverStage1;							// 1次ユニット承認者
	private String				lblApproverStage2;							// 2次ユニット承認者
	private String				lblApproverStage3;							// 3次ユニット承認者
	private String				lblApproverStage4;							// 4次ユニット承認者
	private String				lblApproverStage5;							// 5次ユニット承認者
	private String				lblApproverStage6;							// 6次ユニット承認者
	private String				lblApproverStage7;							// 7次ユニット承認者
	private String				lblApproverStage8;							// 8次ユニット承認者
	private String				lblApproverStage9;							// 9次ユニット承認者
	private String				lblApproverStage10;							// 10次ユニット承認者
	
	private String[][]			aryPltRouteStage;							// 承認階層プルダウンリスト
	private String[][]			aryPltEditInactivate;						// 無効フラグプルダウンリスト
	private String[][]			aryPltUnitStage;							// ユニットプルダウンリスト
	
	
	/**
	 * @return pltRouteStage
	 */
	public String getPltRouteStage() {
		return pltRouteStage;
	}
	
	/**
	 * @param pltRouteStage セットする pltRouteStage
	 */
	public void setPltRouteStage(String pltRouteStage) {
		this.pltRouteStage = pltRouteStage;
	}
	
	/**
	 * @return txtRouteCode
	 */
	public String getTxtRouteCode() {
		return txtRouteCode;
	}
	
	/**
	 * @param txtRouteCode セットする txtRouteCode
	 */
	public void setTxtRouteCode(String txtRouteCode) {
		this.txtRouteCode = txtRouteCode;
	}
	
	/**
	 * @return txtRouteName
	 */
	public String getTxtRouteName() {
		return txtRouteName;
	}
	
	/**
	 * @param txtRouteName セットする txtRouteName
	 */
	public void setTxtRouteName(String txtRouteName) {
		this.txtRouteName = txtRouteName;
	}
	
	/**
	 * @return pltUnitStage1
	 */
	public String getPltUnitStage1() {
		return pltUnitStage1;
	}
	
	/**
	 * @param pltUnitStage1 セットする pltUnitStage1
	 */
	public void setPltUnitStage1(String pltUnitStage1) {
		this.pltUnitStage1 = pltUnitStage1;
	}
	
	/**
	 * @return pltUnitStage2
	 */
	public String getPltUnitStage2() {
		return pltUnitStage2;
	}
	
	/**
	 * @param pltUnitStage2 セットする pltUnitStage2
	 */
	public void setPltUnitStage2(String pltUnitStage2) {
		this.pltUnitStage2 = pltUnitStage2;
	}
	
	/**
	 * @return pltUnitStage3
	 */
	public String getPltUnitStage3() {
		return pltUnitStage3;
	}
	
	/**
	 * @param pltUnitStage3 セットする pltUnitStage3
	 */
	public void setPltUnitStage3(String pltUnitStage3) {
		this.pltUnitStage3 = pltUnitStage3;
	}
	
	/**
	 * @return pltUnitStage4
	 */
	public String getPltUnitStage4() {
		return pltUnitStage4;
	}
	
	/**
	 * @param pltUnitStage4 セットする pltUnitStage4
	 */
	public void setPltUnitStage4(String pltUnitStage4) {
		this.pltUnitStage4 = pltUnitStage4;
	}
	
	/**
	 * @return pltUnitStage5
	 */
	public String getPltUnitStage5() {
		return pltUnitStage5;
	}
	
	/**
	 * @param pltUnitStage5 セットする pltUnitStage5
	 */
	public void setPltUnitStage5(String pltUnitStage5) {
		this.pltUnitStage5 = pltUnitStage5;
	}
	
	/**
	 * @return pltUnitStage6
	 */
	public String getPltUnitStage6() {
		return pltUnitStage6;
	}
	
	/**
	 * @param pltUnitStage6 セットする pltUnitStage6
	 */
	public void setPltUnitStage6(String pltUnitStage6) {
		this.pltUnitStage6 = pltUnitStage6;
	}
	
	/**
	 * @return pltUnitStage7
	 */
	public String getPltUnitStage7() {
		return pltUnitStage7;
	}
	
	/**
	 * @param pltUnitStage7 セットする pltUnitStage7
	 */
	public void setPltUnitStage7(String pltUnitStage7) {
		this.pltUnitStage7 = pltUnitStage7;
	}
	
	/**
	 * @return pltUnitStage8
	 */
	public String getPltUnitStage8() {
		return pltUnitStage8;
	}
	
	/**
	 * @param pltUnitStage8 セットする pltUnitStage8
	 */
	public void setPltUnitStage8(String pltUnitStage8) {
		this.pltUnitStage8 = pltUnitStage8;
	}
	
	/**
	 * @return pltUnitStage9
	 */
	public String getPltUnitStage9() {
		return pltUnitStage9;
	}
	
	/**
	 * @param pltUnitStage9 セットする pltUnitStage9
	 */
	public void setPltUnitStage9(String pltUnitStage9) {
		this.pltUnitStage9 = pltUnitStage9;
	}
	
	/**
	 * @return pltUnitStage10
	 */
	public String getPltUnitStage10() {
		return pltUnitStage10;
	}
	
	/**
	 * @param pltUnitStage10 セットする pltUnitStage10
	 */
	public void setPltUnitStage10(String pltUnitStage10) {
		this.pltUnitStage10 = pltUnitStage10;
	}
	
	/**
	 * @return lblApproverStage1
	 */
	public String getLblApproverStage1() {
		return lblApproverStage1;
	}
	
	/**
	 * @param lblApproverStage1 セットする lblApproverStage1
	 */
	public void setLblApproverStage1(String lblApproverStage1) {
		this.lblApproverStage1 = lblApproverStage1;
	}
	
	/**
	 * @return lblApproverStage2
	 */
	public String getLblApproverStage2() {
		return lblApproverStage2;
	}
	
	/**
	 * @param lblApproverStage2 セットする lblApproverStage2
	 */
	public void setLblApproverStage2(String lblApproverStage2) {
		this.lblApproverStage2 = lblApproverStage2;
	}
	
	/**
	 * @return lblApproverStage3
	 */
	public String getLblApproverStage3() {
		return lblApproverStage3;
	}
	
	/**
	 * @param lblApproverStage3 セットする lblApproverStage3
	 */
	public void setLblApproverStage3(String lblApproverStage3) {
		this.lblApproverStage3 = lblApproverStage3;
	}
	
	/**
	 * @return lblApproverStage4
	 */
	public String getLblApproverStage4() {
		return lblApproverStage4;
	}
	
	/**
	 * @param lblApproverStage4 セットする lblApproverStage4
	 */
	public void setLblApproverStage4(String lblApproverStage4) {
		this.lblApproverStage4 = lblApproverStage4;
	}
	
	/**
	 * @return lblApproverStage5
	 */
	public String getLblApproverStage5() {
		return lblApproverStage5;
	}
	
	/**
	 * @param lblApproverStage5 セットする lblApproverStage5
	 */
	public void setLblApproverStage5(String lblApproverStage5) {
		this.lblApproverStage5 = lblApproverStage5;
	}
	
	/**
	 * @return lblApproverStage6
	 */
	public String getLblApproverStage6() {
		return lblApproverStage6;
	}
	
	/**
	 * @param lblApproverStage6 セットする lblApproverStage6
	 */
	public void setLblApproverStage6(String lblApproverStage6) {
		this.lblApproverStage6 = lblApproverStage6;
	}
	
	/**
	 * @return lblApproverStage7
	 */
	public String getLblApproverStage7() {
		return lblApproverStage7;
	}
	
	/**
	 * @param lblApproverStage7 セットする lblApproverStage7
	 */
	public void setLblApproverStage7(String lblApproverStage7) {
		this.lblApproverStage7 = lblApproverStage7;
	}
	
	/**
	 * @return lblApproverStage8
	 */
	public String getLblApproverStage8() {
		return lblApproverStage8;
	}
	
	/**
	 * @param lblApproverStage8 セットする lblApproverStage8
	 */
	public void setLblApproverStage8(String lblApproverStage8) {
		this.lblApproverStage8 = lblApproverStage8;
	}
	
	/**
	 * @return lblApproverStage9
	 */
	public String getLblApproverStage9() {
		return lblApproverStage9;
	}
	
	/**
	 * @param lblApproverStage9 セットする lblApproverStage9
	 */
	public void setLblApproverStage9(String lblApproverStage9) {
		this.lblApproverStage9 = lblApproverStage9;
	}
	
	/**
	 * @return lblApproverStage10
	 */
	public String getLblApproverStage10() {
		return lblApproverStage10;
	}
	
	/**
	 * @param lblApproverStage10 セットする lblApproverStage10
	 */
	public void setLblApproverStage10(String lblApproverStage10) {
		this.lblApproverStage10 = lblApproverStage10;
	}
	
	/**
	 * @return aryPltRouteStage
	 */
	public String[][] getAryPltRouteStage() {
		return getStringArrayClone(aryPltRouteStage);
	}
	
	/**
	 * @param aryPltRouteStage セットする aryPltRouteStage
	 */
	public void setAryPltRouteStage(String[][] aryPltRouteStage) {
		this.aryPltRouteStage = getStringArrayClone(aryPltRouteStage);
	}
	
	/**
	 * @return aryPltEditInactivate
	 */
	public String[][] getAryPltEditInactivate() {
		return getStringArrayClone(aryPltEditInactivate);
	}
	
	/**
	 * @param aryPltEditInactivate セットする aryPltEditInactivate
	 */
	public void setAryPltEditInactivate(String[][] aryPltEditInactivate) {
		this.aryPltEditInactivate = getStringArrayClone(aryPltEditInactivate);
	}
	
	/**
	 * @return aryPltUnitStage
	 */
	public String[][] getAryPltUnitStage() {
		return getStringArrayClone(aryPltUnitStage);
	}
	
	/**
	 * @param aryPltUnitStage セットする aryPltUnitStage
	 */
	public void setAryPltUnitStage(String[][] aryPltUnitStage) {
		this.aryPltUnitStage = getStringArrayClone(aryPltUnitStage);
	}
	
}
