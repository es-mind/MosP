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

import java.util.Date;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dto.settings.DifferenceRequestDtoInterface;
import jp.mosp.time.dto.settings.WorkTypeDtoInterface;
import jp.mosp.time.dto.settings.WorkTypeItemDtoInterface;
import jp.mosp.time.entity.WorkTypeEntityInterface;

/**
 * 時差出勤ユーティリティ。<br>
 */
public class DifferenceUtility {
	
	/**
	 * 時差(A～D)休憩開始時刻(0:00からの分)。<br>
	 */
	public static final int	REST_START		= TimeConst.CODE_DEFINITION_HOUR * 12;
	
	/**
	 * 時差(S)休憩開始までの時刻(始業時刻からの分)。<br>
	 */
	public static final int	MINUTES_TO_REST	= TimeConst.CODE_DEFINITION_HOUR * 3;
	
	/**
	 * 休憩時間(分)。<br>
	 */
	public static final int	REST_MINUTES	= TimeConst.CODE_DEFINITION_HOUR;
	
	
	/**
	 * 勤務形態エンティティの複製を基に勤務形態エンティティ(時差出勤)を作成する。<br>
	 * 時差出勤申請か勤務形態エンティティが存在しない場合は、空の勤務形態エンティティを取得する。<br>
	 * が存在しない場合は、新規に作成する。<br>
	 * <br>
	 * 次の値を設定する。<br>
	 * ・勤務形態コード<br>
	 * ・勤務形態名称<br>
	 * ・勤務形態略称<br>
	 * ・出勤時刻<br>
	 * ・退勤時刻<br>
	 * ・休憩1開始時刻<br>
	 * ・休憩1終了時刻<br>
	 * <br>
	 * @param mospParams MosP処理情報
	 * @param dto        時差出勤申請情報
	 * @param workType   勤務形態エンティティ
	 * @return 勤務形態エンティティ(時差出勤)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	public static WorkTypeEntityInterface makeDifferenceWorkType(MospParams mospParams,
			DifferenceRequestDtoInterface dto, WorkTypeEntityInterface workType) throws MospException {
		// 時差出勤申請情報か勤務形態エンティティが存在しない場合
		if (MospUtility.isEmpty(dto, workType)) {
			// 空の勤務形態エンティティを取得
			return WorkTypeUtility.emptyWorkType(mospParams);
		}
		// 勤務形態エンティティ(時差出勤)を順義(勤務形態エンティティの複製)
		WorkTypeEntityInterface entity = WorkTypeUtility.clone(mospParams, workType);
		// 時差出勤申請情報から時差出勤区分を取得
		String differenceType = dto.getDifferenceType();
		// 時差出勤名称を取得
		String differenceName = getDifferenceName(mospParams, differenceType);
		// 時差出勤申請情報から時差出勤日と時差出勤開始時刻及び終了時刻を取得
		Date requestDate = dto.getRequestDate();
		int requestStartMinutes = TimeUtility.getDifferenceMinutes(requestDate, dto.getRequestStart());
		int requestEndMinutes = TimeUtility.getDifferenceMinutes(requestDate, dto.getRequestEnd());
		// 休憩開始及び終了時刻を取得
		int restStartMinutes = getRestStart(differenceType, requestStartMinutes);
		int restEndMinutes = restStartMinutes + REST_MINUTES;
		// 勤務形態エンティティから勤務形態情報を取得
		WorkTypeDtoInterface workTypeDto = entity.getWorkType();
		// 勤務形態コードを設定
		workTypeDto.setWorkTypeCode(differenceType);
		// 勤務形態名称及び略称を設定(名称と略称は同じ)
		workTypeDto.setWorkTypeName(differenceName);
		workTypeDto.setWorkTypeAbbr(differenceName);
		// 勤務形態項目情報を取得(出勤時刻及び退勤時刻と休憩1開始時刻及び休憩1終了時刻)
		WorkTypeItemDtoInterface workStart = entity.getWorkTypeItem(TimeConst.CODE_WORKSTART);
		WorkTypeItemDtoInterface workEnd = entity.getWorkTypeItem(TimeConst.CODE_WORKEND);
		WorkTypeItemDtoInterface restStart = entity.getWorkTypeItem(TimeConst.CODE_RESTSTART1);
		WorkTypeItemDtoInterface restEnd = entity.getWorkTypeItem(TimeConst.CODE_RESTEND1);
		// 勤務形態項目情報を取得できなかった場合
		if (MospUtility.isEmpty(workStart, workEnd, restStart, restEnd)) {
			// 空の勤務形態エンティティを取得
			return WorkTypeUtility.emptyWorkType(mospParams);
		}
		// 勤務形態項目情報に時差出勤申請情報の値を設定
		workStart.setWorkTypeItemValue(DateUtility.getTime(requestStartMinutes));
		workEnd.setWorkTypeItemValue(DateUtility.getTime(requestEndMinutes));
		restStart.setWorkTypeItemValue(DateUtility.getTime(restStartMinutes));
		restEnd.setWorkTypeItemValue(DateUtility.getTime(restEndMinutes));
		// 勤務形態エンティティ(時差出勤)を取得
		return entity;
	}
	
	/**
	 * 休憩開始時刻(0:00からの分)を取得する。<br>
	 * 時差出勤区分A～Dである場合は12:00を、
	 * そうでない場合は時差出勤開始時刻の3時間後を取得する。<br>
	 * @param differenceType 時差出勤区分
	 * @param requestStart   時差出勤開始時刻
	 * @return 休憩開始時刻(0:00からの分)
	 */
	public static int getRestStart(String differenceType, int requestStart) {
		// 時差出勤区分A～Dである場合
		if (isDifferenceTypeAtoD(differenceType)) {
			// 時差(A～D)休憩開始時刻(0:00からの分)を取得
			return REST_START;
		}
		// 時差出勤開始時刻の3時間後を取得
		return requestStart + MINUTES_TO_REST;
	}
	
	/**
	 * 時差出勤名称を取得する。<br>
	 * @param mospParams     MosP処理情報
	 * @param differenceType 時差出勤区分
	 * @return 時差出勤名称
	 */
	public static String getDifferenceName(MospParams mospParams, String differenceType) {
		// 時差出勤名称を準備
		String name = differenceType;
		// 時差出勤区分Aである場合
		if (isDifferenceTypeA(differenceType)) {
			// 時差Aを取得
			name = TimeNamingUtility.differenceA(mospParams);
		}
		// 時差出勤区分Bである場合
		if (isDifferenceTypeB(differenceType)) {
			// 時差Aを取得
			name = TimeNamingUtility.differenceB(mospParams);
		}
		// 時差出勤区分Cである場合
		if (isDifferenceTypeC(differenceType)) {
			// 時差Aを取得
			name = TimeNamingUtility.differenceC(mospParams);
		}
		// 時差出勤区分Dである場合
		if (isDifferenceTypeD(differenceType)) {
			// 時差Aを取得
			name = TimeNamingUtility.differenceD(mospParams);
		}
		// 時差出勤区分Sである場合
		if (isDifferenceTypeS(differenceType)) {
			// 時差Aを取得
			name = TimeNamingUtility.differenceS(mospParams);
		}
		// 時差出勤名称を取得
		return name;
	}
	
	/**
	 * 時差出勤区分Aであるかを確認する。<br>
	 * @param differenceType 時差出勤区分
	 * @return 確認結果(true：時差出勤区分Aである、false：時差出勤区分Aでない)
	 */
	public static boolean isDifferenceTypeA(String differenceType) {
		// 時差出勤区分Aであるかを確認
		return MospUtility.isEqual(differenceType, TimeConst.CODE_DIFFERENCE_TYPE_A);
	}
	
	/**
	 * 時差出勤区分Bであるかを確認する。<br>
	 * @param differenceType 時差出勤区分
	 * @return 確認結果(true：時差出勤区分Bである、false：時差出勤区分Bでない)
	 */
	public static boolean isDifferenceTypeB(String differenceType) {
		// 時差出勤区分Aであるかを確認
		return MospUtility.isEqual(differenceType, TimeConst.CODE_DIFFERENCE_TYPE_B);
	}
	
	/**
	 * 時差出勤区分Cであるかを確認する。<br>
	 * @param differenceType 時差出勤区分
	 * @return 確認結果(true：時差出勤区分Cである、false：時差出勤区分Cでない)
	 */
	public static boolean isDifferenceTypeC(String differenceType) {
		// 時差出勤区分Aであるかを確認
		return MospUtility.isEqual(differenceType, TimeConst.CODE_DIFFERENCE_TYPE_C);
	}
	
	/**
	 * 時差出勤区分Dであるかを確認する。<br>
	 * @param differenceType 時差出勤区分
	 * @return 確認結果(true：時差出勤区分Dである、false：時差出勤区分Dでない)
	 */
	public static boolean isDifferenceTypeD(String differenceType) {
		// 時差出勤区分Aであるかを確認
		return MospUtility.isEqual(differenceType, TimeConst.CODE_DIFFERENCE_TYPE_D);
	}
	
	/**
	 * 時差出勤区分Sであるかを確認する。<br>
	 * @param differenceType 時差出勤区分
	 * @return 確認結果(true：時差出勤区分Sである、false：時差出勤区分Sでない)
	 */
	public static boolean isDifferenceTypeS(String differenceType) {
		// 時差出勤区分Aであるかを確認
		return MospUtility.isEqual(differenceType, TimeConst.CODE_DIFFERENCE_TYPE_S);
	}
	
	/**
	 * 時差出勤区分A～Dであるかを確認する。<br>
	 * @param differenceType 時差出勤区分
	 * @return 確認結果(true：時差出勤区分A～Dである、false：時差出勤区分A～Dでない)
	 */
	public static boolean isDifferenceTypeAtoD(String differenceType) {
		// 時差出勤区分Aであるかを確認
		boolean isDifferenceTypeAtoD = isDifferenceTypeA(differenceType);
		// 時差出勤区分Bであるかを確認
		isDifferenceTypeAtoD = isDifferenceTypeAtoD || isDifferenceTypeB(differenceType);
		// 時差出勤区分Cであるかを確認
		isDifferenceTypeAtoD = isDifferenceTypeAtoD || isDifferenceTypeC(differenceType);
		// 時差出勤区分Dであるかを確認
		isDifferenceTypeAtoD = isDifferenceTypeAtoD || isDifferenceTypeD(differenceType);
		// 確認結果を取得
		return isDifferenceTypeAtoD;
	}
	
}
