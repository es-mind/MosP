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
package jp.mosp.time.bean;

import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dto.file.ImportDtoInterface;
import jp.mosp.platform.dto.file.ImportFieldDtoInterface;
import jp.mosp.time.dto.settings.WorkTypeDtoInterface;
import jp.mosp.time.dto.settings.WorkTypeItemDtoInterface;

/**
 * 勤務形態インポート追加処理インターフェース。<br>
 * アドオンで勤務形態インポートに処理を追加したい場合に用いる。<br>
 */
public interface WorkTypeImportAddonBeanInterface {
	
	/**
	 * 追加処理の初期化処理を実行する。<br>
	 * @param importDto インポート情報
	 * @param importFieldDtoList インポートフィールド情報
	 * @param list 対象リスト
	 * @throws MospException エラーが発生した場合
	 */
	void initAddonBean(ImportDtoInterface importDto, List<ImportFieldDtoInterface> importFieldDtoList,
			List<String[]> list) throws MospException;
	
	/**
	 * 勤務形態項目情報か確認する。<br>
	 * @param fieldName 項目名
	 * @return 確認結果（true：合致、false：合致しない）
	 * @throws MospException エラーが発生した場合
	 */
	boolean isWorkTypeItemInfo(String fieldName) throws MospException;
	
	/**
	 * 勤務形態項目を取得する。<br>
	 * @param fieldName 項目名
	 * @param cnt インデックス
	 * @param value 項目値
	 * @param worktypeDto 勤務形態情報
	 * @param index 対象行数
	 * @return 勤務形態項目情報
	 * @throws MospException エラーが発生した場合
	 */
	WorkTypeItemDtoInterface setWorktypeItemDto(String fieldName, Integer cnt, String value,
			WorkTypeDtoInterface worktypeDto, int index) throws MospException;
	
}
