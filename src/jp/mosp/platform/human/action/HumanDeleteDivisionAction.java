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
package jp.mosp.platform.human.action;

import java.util.HashSet;
import java.util.Set;

import jp.mosp.framework.base.BaseVo;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospUser;
import jp.mosp.framework.property.ViewConfigProperty;
import jp.mosp.platform.base.PlatformAction;
import jp.mosp.platform.bean.human.HumanArrayRegistBeanInterface;
import jp.mosp.platform.bean.human.HumanBinaryArrayRegistBeanInterface;
import jp.mosp.platform.bean.human.HumanBinaryHistoryRegistBeanInterface;
import jp.mosp.platform.bean.human.HumanBinaryNormalRegistBeanInterface;
import jp.mosp.platform.bean.human.HumanHistoryRegistBeanInterface;
import jp.mosp.platform.bean.human.HumanNormalRegistBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.human.base.PlatformHumanAction;
import jp.mosp.platform.human.constant.PlatformHumanConst;
import jp.mosp.platform.human.vo.HumanHistoryCardVo;

/**
 * 人事汎用管理項目の自動論理削除を行う。<br>
 * 論理削除する項目は、各画面のXMLで未使用の入力項目とする。
 */
public class HumanDeleteDivisionAction extends PlatformHumanAction {
	
	@Override
	protected BaseVo getSpecificVo() {
		return new HumanHistoryCardVo();
	}
	
	/**
	 * {@link PlatformAction#PlatformAction()}を実行する。<br>
	 */
	public HumanDeleteDivisionAction() {
		super();
		
	}
	
	@Override
	public void action() throws MospException {
		// 削除ユーザ情報設定
		mospParams.setUser(new MospUser());
		mospParams.getUser().setUserId("auto delete");
		
		// 画面区分毎に処理
		
		// 通常の場合
		deleteDeadItem(PlatformHumanConst.PRM_HUMAN_DIVISION_TYPE_NORMAL);
		// バイナリ通常の場合
		deleteDeadItem(PlatformHumanConst.PRM_HUMAN_DIVISION_TYPE_BINARY_NORMAL);
		// 一覧の場合
		deleteDeadItem(PlatformHumanConst.PRM_HUMAN_DIVISION_TYPE_ARRAY);
		// バイナリ一覧の場合
		deleteDeadItem(PlatformHumanConst.PRM_HUMAN_DIVISION_TYPE_BINARY_ARRAY);
		// 履歴の場合
		deleteDeadItem(PlatformHumanConst.PRM_HUMAN_DIVISION_TYPE_HISTORY);
		// バイナリ履歴の場合
		deleteDeadItem(PlatformHumanConst.PRM_HUMAN_DIVISION_TYPE_BINARY_HISTORY);
		
	}
	
	private void deleteDeadItem(String itemType) throws MospException {
		// 画面区分配列取得
		String[] aryDivision = mospParams.getApplicationProperties(PlatformConst.APP_HUMAN_GENERAL_DIVISIONS);
		
		// 取得したコードの形式ごとに処理を分ける
		Set<String> divisions = new HashSet<String>();
		
		// 画面区分毎に処理
		for (String division : aryDivision) {
			
			// 人事汎用管理区分設定取得
			ViewConfigProperty viewConfig = mospParams.getProperties().getViewConfigProperties().get(division);
			String type = viewConfig.getType();
			
			if (itemType.equals(type)) {
				divisions.add(division);
			}
		}
		// 人事汎用管理区分未設定
		if (divisions.isEmpty()) {
			return;
		}
		
		// 通常の場合
		if (itemType.equals(PlatformHumanConst.PRM_HUMAN_DIVISION_TYPE_NORMAL)) {
			// 登録クラス取得
			HumanNormalRegistBeanInterface regist = platform().humanNormalRegist();
			// 論理削除
			regist.deleteDeadInputItem(divisions, "NormalCard");
			// コミット
			commit();
			return;
		}
		// バイナリ通常の場合
		if (itemType.equals(PlatformHumanConst.PRM_HUMAN_DIVISION_TYPE_BINARY_NORMAL)) {
			
			// 登録クラス取得
			HumanBinaryNormalRegistBeanInterface regist = platform().humanBinaryNormalRegist();
			// 論理削除
			regist.deleteDeadInputItem(divisions, "BinaryHistoryCard");
			// コミット
			commit();
			return;
		}
		
		// 履歴の場合
		if (itemType.equals(PlatformHumanConst.PRM_HUMAN_DIVISION_TYPE_HISTORY)) {
			
			// 登録クラス取得
			HumanHistoryRegistBeanInterface regist = platform().humanHistoryRegist();
			// 論理削除
			regist.deleteDeadInputItem(divisions, "HistoryCard");
			// コミット
			commit();
			return;
		}
		// バイナリ履歴登録の場合
		if (itemType.equals(PlatformHumanConst.PRM_HUMAN_DIVISION_TYPE_BINARY_HISTORY)) {
			
			// 登録クラス取得
			HumanBinaryHistoryRegistBeanInterface regist = platform().humanBinaryHistoryRegist();
			// 論理削除
			regist.deleteDeadInputItem(divisions, "BinaryHistoryCard");
			// コミット
			commit();
			return;
			
		}
		// 一覧の場合
		if (itemType.equals(PlatformHumanConst.PRM_HUMAN_DIVISION_TYPE_ARRAY)) {
			// 登録クラス取得
			HumanArrayRegistBeanInterface regist = platform().humanArrayRegist();
			// 論理削除
			regist.deleteDeadInputItem(divisions, "ArrayCard");
			// コミット
			commit();
			return;
			
		}
		// バイナリ一覧登録の場合
		if (itemType.equals(PlatformHumanConst.PRM_HUMAN_DIVISION_TYPE_BINARY_ARRAY)) {
			
			// 登録クラス取得
			HumanBinaryArrayRegistBeanInterface regist = platform().humanBinaryArrayRegist();
			// 論理削除
			regist.deleteDeadInputItem(divisions, "BinaryArrayCard");
			// コミット
			commit();
			return;
			
		}
		
	}
	
}
