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
package jp.mosp.time.utils;

import java.util.ArrayList;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.utils.PlatformUtility;
import jp.mosp.time.dto.settings.WorkTypeDtoInterface;
import jp.mosp.time.dto.settings.WorkTypeItemDtoInterface;
import jp.mosp.time.entity.WorkTypeEntityInterface;

/**
 * 勤務形態ユーティリティ。<br>
 */
public class WorkTypeUtility {
	
	/**
	 * 勤務形態エンティティ(空)を作成する。<br>
	 * @param mospParams MosP処理情報
	 * @return 勤務形態エンティティ(空)
	 * @throws MospException オブジェクトの生成に失敗した場合
	 */
	public static WorkTypeEntityInterface emptyWorkType(MospParams mospParams) throws MospException {
		// 勤務形態エンティティ(空)を作成
		WorkTypeEntityInterface entity = MospUtility.createObject(WorkTypeEntityInterface.class, mospParams);
		// 勤務形態エンティティに設定
		entity.setWorkTypeDto(null);
		entity.setWorkTypeItemList(new ArrayList<WorkTypeItemDtoInterface>());
		// 勤務形態エンティティ(空)を取得
		return entity;
	}
	
	/**
	 * 勤務形態エンティティの複製を作成する。<br>
	 * 複製基の勤務形態エンティティが存在しない場合は、空の勤務形態エンティティを取得する。<br>
	 * @param mospParams MosP処理情報
	 * @param entity     勤務形態エンティティ
	 * @return 勤務形態エンティティ(複製)
	 * @throws MospException オブジェクトの生成に失敗した場合
	 */
	public static WorkTypeEntityInterface clone(MospParams mospParams, WorkTypeEntityInterface entity)
			throws MospException {
		// 勤務形態エンティティ(複製)を作成
		WorkTypeEntityInterface clone = emptyWorkType(mospParams);
		// 複製基の勤務形態エンティティが存在しない場合
		if (MospUtility.isEmpty(entity)) {
			// 勤務形態エンティティ(空)を取得
			return clone;
		}
		// 勤務形態情報の複製を作成
		clone.setWorkTypeDto(clone(entity.getWorkType()));
		// 勤務形態項目情報リストの複製を作成
		clone.setWorkTypeItemList(clone(entity.getWorkTypeItemList()));
		// 勤務形態エンティティ(複製)を取得
		return clone;
	}
	
	/**
	 * 勤務形態情報の複製を作成する。<br>
	 * 複製基の勤務形態情報が存在しない場合は、nullを取得する。<br>
	 * @param dto 勤務形態情報
	 * @return 勤務形態情報(複製)
	 * @throws MospException クラスのインスタンスの生成に失敗した場合
	 */
	public static WorkTypeDtoInterface clone(WorkTypeDtoInterface dto) throws MospException {
		// 複製基の勤務形態情報が存在しない場合
		if (MospUtility.isEmpty(dto)) {
			// nullを取得
			return null;
		}
		// 勤務形態情報(複製)を作成
		WorkTypeDtoInterface clone = PlatformUtility.cloneBaseDto(dto);
		clone.setTmmWorkTypeId(dto.getTmmWorkTypeId());
		clone.setWorkTypeCode(dto.getWorkTypeCode());
		clone.setActivateDate(dto.getActivateDate());
		clone.setWorkTypeName(dto.getWorkTypeName());
		clone.setWorkTypeAbbr(dto.getWorkTypeAbbr());
		clone.setInactivateFlag(dto.getInactivateFlag());
		// 勤務形態情報(複製)を取得
		return clone;
	}
	
	/**
	 * 勤務形態項目情報リストの複製を作成する。<br>
	 * 複製基の勤務形態項目情報リストが存在しない場合は、空のリストを取得する。<br>
	 * @param items 勤務形態項目情報リスト
	 * @return 勤務形態項目情報リスト(複製)
	 * @throws MospException クラスのインスタンスの生成に失敗した場合
	 */
	public static List<WorkTypeItemDtoInterface> clone(List<WorkTypeItemDtoInterface> items) throws MospException {
		// 勤務形態項目情報リスト(複製)を作成
		List<WorkTypeItemDtoInterface> clone = new ArrayList<WorkTypeItemDtoInterface>();
		// 複製基の勤務形態項目情報リストが存在しない場合
		if (MospUtility.isEmpty(items)) {
			// 空のリストを取得
			return clone;
		}
		// 勤務形態項目情報毎に処理
		for (WorkTypeItemDtoInterface dto : items) {
			// 勤務形態項目情報の複製を作成しリストに設定
			clone.add(clone(dto));
		}
		// 勤務形態項目情報リスト(複製)を取得
		return clone;
	}
	
	/**
	 * 勤務形態項目情報の複製を作成する。<br>
	 * 複製基の勤務形態項目情報が存在しない場合は、nullを取得する。<br>
	 * @param dto 勤務形態項目情報
	 * @return 勤務形態項目情報(複製)
	 * @throws MospException クラスのインスタンスの生成に失敗した場合
	 */
	public static WorkTypeItemDtoInterface clone(WorkTypeItemDtoInterface dto) throws MospException {
		// 複製基の勤務形態項目情報が存在しない場合
		if (MospUtility.isEmpty(dto)) {
			// nullを取得
			return null;
		}
		// 勤務形態項目情報(複製)を作成
		WorkTypeItemDtoInterface clone = PlatformUtility.cloneBaseDto(dto);
		clone.setTmmWorkTypeItemId(dto.getTmmWorkTypeItemId());
		clone.setWorkTypeCode(dto.getWorkTypeCode());
		clone.setActivateDate(dto.getActivateDate());
		clone.setWorkTypeItemCode(dto.getWorkTypeItemCode());
		clone.setWorkTypeItemValue(dto.getWorkTypeItemValue());
		clone.setPreliminary(dto.getPreliminary());
		clone.setInactivateFlag(dto.getInactivateFlag());
		// 勤務形態項目情報(複製)を取得
		return clone;
	}
	
}
