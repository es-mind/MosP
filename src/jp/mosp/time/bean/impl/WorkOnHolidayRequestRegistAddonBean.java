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
package jp.mosp.time.bean.impl;

import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.base.AdditionalLogicBeanInterface;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.utils.PlatformUtility;
import jp.mosp.platform.utils.WorkflowUtility;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dto.settings.SubstituteDtoInterface;
import jp.mosp.time.dto.settings.WorkOnHolidayRequestDtoInterface;
import jp.mosp.time.entity.RequestEntityInterface;
import jp.mosp.time.utils.TimeMessageUtility;
import jp.mosp.time.utils.TimeRequestUtility;

/**
 * 振出・休出申請登録追加処理。<br>
 */
public class WorkOnHolidayRequestRegistAddonBean extends PlatformBean implements AdditionalLogicBeanInterface {
	
	/**
	 * MosPアプリケーション設定キー(振替の振替禁止)。<br>
	 */
	protected static final String APP_PROHIBIT_SUBSTITUTE_ITERATE = "ProhibitSubstituteIterate";
	
	
	@Override
	public void initBean() throws MospException {
		// 処理無し
	}
	
	@Override
	public boolean doAdditionalLogic(Object... objects) throws MospException {
		// コードキーを取得
		String key = (String)objects[0];
		// コードキーが追加業務ロジック：振出・休出申請登録追加処理である場合
		if (MospUtility.isEqual(key, TimeConst.CODE_KEY_WORK_ON_HOLIDAY_REQUEST_REGIST_ADDONS)) {
			// 振出・休出申請情報及び申請エンティティ(出勤日で作成したもの)を取得
			WorkOnHolidayRequestDtoInterface dto = PlatformUtility.castObject(objects[1]);
			RequestEntityInterface request = PlatformUtility.castObject(objects[2]);
			// 振出・休出申請及び下書時の確認処理
			check(dto, request);
			// 追加処理有り
			return true;
		}
		// 追加処理無し
		return false;
	}
	
	/**
	 * 振出・休出申請及び下書時の確認処理を行う。<br>
	 * @param dto     振出・休出申請情報
	 * @param request 申請エンティティ(出勤日で作成したもの)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void check(WorkOnHolidayRequestDtoInterface dto, RequestEntityInterface request) throws MospException {
		// 振替の振替が禁止されている場合
		if (mospParams.getApplicationPropertyBool(APP_PROHIBIT_SUBSTITUTE_ITERATE)) {
			// 振出・休出申請情報が振替の振替でないことを確認
			checkSubstituteIterate(dto, request);
		}
	}
	
	/**
	 * 振出・休出申請情報が振替の振替でないことを確認する。<br>
	 * 但し、全日の振替休日に対しては半日の振替出勤を可能とする。<br>
	 * @param dto     振出・休出申請情報
	 * @param request 申請エンティティ(出勤日で作成したもの)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void checkSubstituteIterate(WorkOnHolidayRequestDtoInterface dto, RequestEntityInterface request)
			throws MospException {
		// 振出・休出申請が休日出勤(振替申請しない)である場合
		if (TimeRequestUtility.isWorkOnHolidaySubstituteOff(dto)) {
			// 処理無し
			return;
		}
		// 振替休日情報(申請済(一次戻と下書と取下以外)を取得
		List<SubstituteDtoInterface> substitutes = request.getSubstituteList(WorkflowUtility.getAppliedStatuses());
		// 振替休日情報(申請済(一次戻と下書と取下以外)が存在しない場合
		if (MospUtility.isEmpty(substitutes)) {
			// 処理無し(振替の振替でないと判断)
			return;
		}
		// 全休の振替休日情報があり振出・休出申請が半日振替出勤である場合
		if (TimeRequestUtility.hasHolidayRangeAll(substitutes)
				&& TimeRequestUtility.isWorkOnHolidayHalfSubstitute(dto)) {
			// 処理無し(全日の振替休日に対しては半日の振替出勤を可能とする)
			return;
		}
		// エラーメッセージを設定
		TimeMessageUtility.addErrorSubstituteIterate(mospParams, dto.getRequestDate());
	}
	
}
