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

import java.util.Set;

import jp.mosp.framework.base.BaseBean;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.base.AdditionalLogicBeanInterface;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;
import jp.mosp.platform.utils.PfNameUtility;
import jp.mosp.platform.utils.PlatformUtility;
import jp.mosp.platform.utils.WorkflowUtility;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dto.settings.impl.AttendanceListDto;
import jp.mosp.time.entity.AttendListEntity;
import jp.mosp.time.entity.AttendListEntityInterface;
import jp.mosp.time.utils.AttendanceUtility;

/**
 * 勤怠一覧情報備考設定処理(基)。<br>
 * 勤怠一覧情報備考設定処理を作成する場合の基となるクラス。<br>
 */
public abstract class AttendListReferRemarkBaseBean extends BaseBean implements AdditionalLogicBeanInterface {
	
	@Override
	public void initBean() throws MospException {
		// 処理無し
	}
	
	@Override
	public boolean doAdditionalLogic(Object... objects) throws MospException {
		// コードキーを取得
		String key = (String)objects[0];
		// コードキーが勤怠一覧情報備考設定でない場合
		if (MospUtility.isEqual(key, TimeConst.CODE_KEY_ADD_ATTEND_LIST_REMARKS) == false) {
			// 処理終了(追加処理無し)
			return false;
		}
		// 引数配列から勤怠一覧エンティティを取得
		AttendListEntityInterface entity = getAttendListEntity(objects);
		// 勤怠一覧情報毎に処理
		for (AttendanceListDto dto : entity.getAttendList()) {
			// 備考を設定
			AttendanceUtility.addRemark(dto, getRemark(entity, dto));
		}
		// 追加処理有り
		return true;
	}
	
	/**
	 * 引数配列から勤怠一覧エンティティを取得する。<br>
	 * @param objects 引数配列
	 * @return 勤怠一覧エンティティ
	 */
	protected AttendListEntity getAttendListEntity(Object... objects) {
		// 引数の2つ目を取得
		return PlatformUtility.castObject(objects[1]);
	}
	
	/**
	 * 勤怠一覧用の備考を取得する。<br>
	 * @param entity 勤怠一覧エンティティ
	 * @param dto    勤怠一覧情報
	 * @return 勤怠一覧用の備考
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected String getRemark(AttendListEntityInterface entity, AttendanceListDto dto) throws MospException {
		// 継承するクラスで実装
		return getEmpty();
	}
	
	/**
	 * 対象となる承認状況群を取得する。<br>
	 * 各種申請の備考を設定する際に、用いる。<br>
	 * @param dto 勤怠一覧情報
	 * @return 対象となる承認状況群
	 */
	protected Set<String> getWorkflowStatuses(AttendanceListDto dto) {
		// 実績か予定の場合
		if (AttendanceUtility.isTheListType(dto, AttendanceUtility.TYPE_LIST_ACTUAL,
				AttendanceUtility.TYPE_LIST_SCHEDULE)) {
			// 対象となる承認状況群(承認済)を取得
			return WorkflowUtility.getCompletedStatuses();
		}
		// 対象となる承認状況群(取下以外)を取得
		return WorkflowUtility.getStatusesExceptWithDrawn();
	}
	
	/**
	 * 備考用各種申請承認状態文字列を取得する。<br>
	 * 各種申請の備考を設定する際に、用いる。<br>
	 * @param prefix 接頭辞(申請を表す略称)
	 * @param dto    ワークフロー情報
	 * @return 備考用各種申請承認状態文字列
	 */
	protected String getRequestWorkflowStatusRemark(String prefix, WorkflowDtoInterface dto) {
		//備考用各種申請承認状態文字列を準備
		StringBuilder sb = new StringBuilder(getWorkflowStatusRemark(dto));
		// 備考用承認状態文字列を取得できなかった場合
		if (sb.length() == 0) {
			// 空文字を取得
			return getEmpty();
		}
		// 接頭辞を設定
		sb.insert(0, prefix);
		// 備考用各種申請承認状態文字列を取得
		return sb.toString();
	}
	
	/**
	 * 備考用承認状態文字列を取得する。<br>
	 * 各種申請の備考を設定する際に用いる。<br>
	 * @param dto ワークフロー情報
	 * @return 備考用承認状態文字列
	 */
	protected String getWorkflowStatusRemark(WorkflowDtoInterface dto) {
		// 承認済である場合
		if (WorkflowUtility.isCompleted(dto)) {
			// 済を取得
			return PfNameUtility.completedAbbr(mospParams);
		}
		// 一次戻である場合
		if (WorkflowUtility.isFirstReverted(dto)) {
			// 戻を取得
			return PfNameUtility.revertedAbbr(mospParams);
		}
		// 下書である場合
		if (WorkflowUtility.isDraft(dto)) {
			// 下を取得
			return PfNameUtility.draftAbbr(mospParams);
		}
		// 申請済(上記以外で)である場合
		if (WorkflowUtility.isApplied(dto)) {
			// 申を取得
			return PfNameUtility.appliedAbbr(mospParams);
		}
		// それ以外の場合
		return getEmpty();
	}
	
	/**
	 * 備考を設定する。<br>
	 * 各備考間には半角スペースが入る。<br>
	 * 継承するクラスで用いることを想定している。<br>
	 * @param remark  備考
	 * @param remarks 備考に設定する文字列配列
	 */
	protected void addRemark(StringBuilder remark, String... remarks) {
		// 文字配列を半角スペースで連結
		MospUtility.concat(remark, remarks);
	}
	
	/**
	 * 備考を取得する。<br>
	 * 各備考間には半角スペースが入る。<br>
	 * 継承するクラスで用いることを想定している。<br>
	 * @param remarks 備考に設定する文字列配列群
	 * @return 備考
	 */
	protected String getRemark(Set<String> remarks) {
		// 備考を準備
		StringBuilder remark = new StringBuilder();
		// 文字配列を半角スペースで連結
		MospUtility.concat(remark, MospUtility.toArray(remarks));
		// 備考を取得
		return remark.toString();
	}
	
	/**
	 * 空文字を取得する。<br>
	 * @return 空文字
	 */
	protected String getEmpty() {
		// 空文字を取得
		return MospConst.STR_EMPTY;
	}
	
}
