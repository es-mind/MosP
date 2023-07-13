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
package jp.mosp.time.file.action;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.file.action.ExportCardAction;
import jp.mosp.platform.file.vo.ExportCardVo;
import jp.mosp.time.base.TimeReferenceBeanHandlerInterface;
import jp.mosp.time.constant.TimeFileConst;

/**
 * エクスポートに用いるマスタの詳細情報の確認、編集を行う。<br>
 */
public class TimeExportCardAction extends ExportCardAction {
	
	/**
	 * MosP勤怠管理参照用BeanHandler。
	 */
	protected TimeReferenceBeanHandlerInterface timeReference;
	
	
	@Override
	protected void setPulldown() throws MospException {
		// VO取得
		ExportCardVo vo = (ExportCardVo)mospParams.getVo();
		super.setPulldown();
		timeReference = (TimeReferenceBeanHandlerInterface)createHandler(TimeReferenceBeanHandlerInterface.class);
		// 有効日フラグ確認(休暇取得データ)
		if (vo.getModeActivateDate().equals(PlatformConst.MODE_ACTIVATE_DATE_FIXED)
				&& TimeFileConst.CODE_EXPORT_TYPE_HOLIDAY_REQUEST_DATA.equals(vo.getPltEditTable())) {
			// プルダウン設定(データ区分を条件にコード配列を取得)
			vo.setJsPltSelectTable(concat(getCodeArray(vo.getPltEditTable(), false),
					timeReference.holiday().getExportArray(getSystemDate())));
		}
	}
	
	/**
	 * 配列を連結する。<br>
	 * @param array1 配列1
	 * @param array2 配列2
	 * @return 連結した配列
	 */
	protected String[][] concat(String[][] array1, String[][] array2) {
		String[][] array = new String[array1.length + array2.length][2];
		System.arraycopy(array1, 0, array, 0, array1.length);
		System.arraycopy(array2, 0, array, array1.length, array2.length);
		return array;
	}
	
}
