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
package jp.mosp.time.entity;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.ActivateDtoInterface;
import jp.mosp.time.dto.settings.WorkTypeDtoInterface;
import jp.mosp.time.dto.settings.WorkTypeItemDtoInterface;

/**
 * 勤務形態エンティティインターフェース。<br>
 */
public interface WorkTypeEntityInterface extends ActivateDtoInterface {
	
	/**
	 * 勤務形態情報を取得する。<br>
	 * 勤務形態情報が取得できなかった場合は、nullを返す。<br>
	 * @return 勤務形態情報
	 */
	WorkTypeDtoInterface getWorkType();
	
	/**
	 * 勤務形態情報の設定。
	 * @param workTypeDto セットする勤務形態情報
	 */
	void setWorkTypeDto(WorkTypeDtoInterface workTypeDto);
	
	/**
	 * 勤務形態情報を取得する。<br>
	 * @return 勤務形態情報
	 */
	List<WorkTypeItemDtoInterface> getWorkTypeItemList();
	
	/**
	 * 勤務形態項目情報リストの設定。
	 * @param itemDtoList セットする勤務形態項目情報リスト
	 */
	void setWorkTypeItemList(List<WorkTypeItemDtoInterface> itemDtoList);
	
	/**
	 * 勤務形態情報が存在するかを確認する。<br>
	 * <br>
	 * 勤務形態情報が存在しても、略称がnullである場合は、存在しないと判断する。<br>
	 * <br>
	 * @return 確認結果(true：存在する、false：存在しない)
	 */
	boolean isExist();
	
	/**
	 * 勤務形態略称を取得する。<br>
	 * 勤務形態略称が未設定の場合は、空文字を返す。<br>
	 * @return 勤務形態略称
	 */
	String getWorkTypeAbbr();
	
	/**
	 * 始業時刻を取得する。<br>
	 * @return 始業時刻
	 * @throws MospException 日付の変換に失敗した場合
	 */
	Date getStartWorkTime() throws MospException;
	
	/**
	 * 終業時刻を取得する。<br>
	 * @return 終業時刻
	 * @throws MospException 日付の変換に失敗した場合
	 */
	Date getEndWorkTime() throws MospException;
	
	/**
	 * 勤務時間(分)を取得する。<br>
	 * 規定労働時間(勤務形態の始業時刻と終業時刻の差から規定休憩を除いた時間)
	 * を取得する。<br>
	 * <br>
	 * @return 勤務時間(分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	int getWorkTime() throws MospException;
	
	/**
	 * 休憩時間(分)を取得する。<br>
	 * @return 勤務時間(分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	int getRestTime() throws MospException;
	
	/**
	 * 規定休憩時間間隔群(キー：開始時刻)(キー順)を取得する。<br>
	 * <br>
	 * 休憩1～休憩4を取得する。<br>
	 * <br>
	 * @return 休憩時間間隔群(キー：開始時刻)(キー順)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	Map<Integer, TimeDuration> getRestTimes() throws MospException;
	
	/**
	 * 休憩1時間間隔を取得する。<br>
	 * <br>
	 * @return 休憩1時間間隔
	 * @throws MospException 日付の変換に失敗した場合
	 */
	TimeDuration getRest1Time() throws MospException;
	
	/**
	 * 規定休憩時間間隔群(キー：開始時刻)(キー順)を取得する。<br>
	 * <br>
	 * 1.振出・休出申請(休日出勤)がある場合：空の時間間隔群<br>
	 * 2.全休か後半休の場合：空の時間間隔群<br>
	 * 3.前半休の場合：半休取得時休憩(勤務時間条件を満たさなければ空)<br>
	 * 4.全休でも半休でもない場合：休憩1～休憩4<br>
	 * <br>
	 * @param startTime     始業時刻(勤怠計算上)(0:00からの分)
	 * @param endTime       終業時刻(勤怠計算上)(0:00からの分)
	 * @param requestEntity 申請エンティティ
	 * @param statuses      対象となる承認状況群
	 * @return 休憩時間間隔群(キー：開始時刻)(キー順)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	Map<Integer, TimeDuration> getRestTimes(int startTime, int endTime, RequestEntityInterface requestEntity,
			Set<String> statuses) throws MospException;
	
	/**
	 * 規定休憩時間間隔群(キー：開始時刻)(キー順)を取得する。<br>
	 * <br>
	 * {@link #getRestTimes(int, int, RequestEntityInterface, Set) }を参照。
	 * <br>
	 * @param startTime     始業時刻(勤怠計算上)(0:00からの分)
	 * @param endTime       終業時刻(勤怠計算上)(0:00からの分)
	 * @param requestEntity 申請エンティティ
	 * @param isCompleted   承認済フラグ(true：承認済申請のみ考慮、false：申請済申請を考慮)
	 * @return 休憩時間間隔群(キー：開始時刻)(キー順)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	Map<Integer, TimeDuration> getRestTimes(int startTime, int endTime, RequestEntityInterface requestEntity,
			boolean isCompleted) throws MospException;
	
	/**
	 * 休憩1開始時刻を取得する。<br>
	 * @return 休憩1開始時刻
	 * @throws MospException 日付の変換に失敗した場合
	 */
	Date getRest1StartTime() throws MospException;
	
	/**
	 * 休憩1終了時刻を取得する。<br>
	 * @return 休憩1終了時刻
	 * @throws MospException 日付の変換に失敗した場合
	 */
	Date getRest1EndTime() throws MospException;
	
	/**
	 * 前半休開始時刻を取得する。<br>
	 * <br>
	 * 後半休時の始業時刻にあたる。<br>
	 * <br>
	 * @return 前半休開始時刻
	 * @throws MospException 日付の変換に失敗した場合
	 */
	Date getFrontStartTime() throws MospException;
	
	/**
	 * 前半休終了時刻を取得する。<br>
	 * <br>
	 * 後半休時の終業時刻にあたる。<br>
	 * <br>
	 * @return 前半休終了時刻
	 * @throws MospException 日付の変換に失敗した場合
	 */
	Date getFrontEndTime() throws MospException;
	
	/**
	 * 後半休開始時刻を取得する。<br>
	 * <br>
	 * 前半休時の始業時刻にあたる。<br>
	 * <br>
	 * @return 後半休開始時刻
	 * @throws MospException 日付の変換に失敗した場合
	 */
	Date getBackStartTime() throws MospException;
	
	/**
	 * 後半休終了時刻を取得する。<br>
	 * <br>
	 * 前半休時の終業時刻にあたる。<br>
	 * <br>
	 * @return 後半休終了時刻
	 * @throws MospException 日付の変換に失敗した場合
	 */
	Date getBackEndTime() throws MospException;
	
	/**
	 * 直行かどうかを確認する。<br>
	 * @return 確認結果(true：直行、false：直行でない)
	 */
	boolean isDirectStart();
	
	/**
	 * 直帰かどうかを確認する。<br>
	 * @return 確認結果(true：直帰、false：直帰でない)
	 */
	boolean isDirectEnd();
	
	/**
	 * 割増休憩除外が有効であるかを確認する。<br>
	 * @return 確認結果(true：割増休憩除外が有効である、false：有効でない)
	 */
	boolean isNightRestExclude();
	
	/**
	 * 時短時間1開始時刻を取得する。<br>
	 * @return 時短時間1開始時刻
	 * @throws MospException 日付の変換に失敗した場合
	 */
	Date getShort1StartTime() throws MospException;
	
	/**
	 * 時短時間1終了時刻を取得する。<br>
	 * @return 時短時間1終了時刻
	 * @throws MospException 日付の変換に失敗した場合
	 */
	Date getShort1EndTime() throws MospException;
	
	/**
	 * 時短時間1給与区分を確認する。<br>
	 * @return 確認結果(true：時短時間1給与区分が有給、false：無給)
	 */
	boolean isShort1TypePay();
	
	/**
	 * 時短時間1が設定されているかを確認する。<br>
	 * @return 確認結果(true：時短時間1が設定されている、false：されていない)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	boolean isShort1TimeSet() throws MospException;
	
	/**
	 * 時短時間2開始時刻を取得する。<br>
	 * @return 時短時間2開始時刻
	 * @throws MospException 日付の変換に失敗した場合
	 */
	Date getShort2StartTime() throws MospException;
	
	/**
	 * 時短時間2終了時刻を取得する。<br>
	 * @return 時短時間2終了時刻
	 * @throws MospException 日付の変換に失敗した場合
	 */
	Date getShort2EndTime() throws MospException;
	
	/**
	 * 時短時間2給与区分を確認する。<br>
	 * @return 確認結果(true：時短時間2給与区分が有給、false：無給)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	boolean isShort2TypePay() throws MospException;
	
	/**
	 * 時短時間2が設定されているかを確認する。<br>
	 * @return 確認結果(true：時短時間2が設定されている、false：されていない)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	boolean isShort2TimeSet() throws MospException;
	
	/**
	 * 勤務対象勤務形態であるかを確認する。<br>
	 * 未設定(休暇等)、所定休日、法定休日の場合は、勤務対象勤務形態でないと判断する。<br>
	 * @return 確認結果(true：勤務対象勤務形態である、false：そうでない。)
	 */
	boolean isWorkTypeForWork();
	
	/**
	 * 勤務前残業実績登録が有効であるかを確認する。<br>
	 * @return 確認結果(true：勤務前残業実績登録が有効である、false：そうでない)
	 */
	boolean isAutoBeforeOvertimeAvailable();
	
	/**
	 * 規定時間(0:00からの分)を取得する。<br>
	 * <br>
	 * ここでは、次の申請及び設定は、考慮しない。<br>
	 * ・残業申請<br>
	 * ・勤務形態変更申請<br>
	 * ・時差出勤申請<br>
	 * ・振出・休出申請(振替出勤申請)<br>
	 * ・休暇申請(時間単位)<br>
	 * ・時短時間<br>
	 * <br>
	 * 1.全休の場合：<br>
	 * 0-0の(妥当でない)時間間隔を返す。<br>
	 * <br>
	 * 2.前半休の場合：<br>
	 * 前半休時の始業時刻及び終業時刻(0:00からの分)を返す。<br>
	 * <br>
	 * 3.後半休の場合：<br>
	 * 後半休時の始業時刻及び終業時刻(0:00からの分)を返す。<br>
	 * <br>
	 * 4.振出・休出申請(休日出勤)がある場合：<br>
	 * 振出・休出申請(休日出勤)の休出予定時間(0:00からの分)を返す。<br>
	 * <br>
	 * 5.それ以外の場合：<br>
	 * 勤務形態の始業時刻及び終業時刻(0:00からの分)を返す。<br>
	 * <br>
	 * @param requestEntity 申請エンティティ
	 * @param isCompleted   承認済フラグ(true：承認済申請のみ考慮、false：申請済申請を考慮)
	 * @return 規定時間(0:00からの分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	TimeDuration getRegularTime(RequestEntityInterface requestEntity, boolean isCompleted) throws MospException;
	
	/**
	 * 規定時間(時間単位休暇含む)(0:00からの分)を取得する。<br>
	 * <br>
	 * {@link WorkTypeEntityInterface#getRegularTime(RequestEntityInterface, boolean)}
	 * を元に、時間単位休暇を考慮する。<br>
	 * <br>
	 * 規定時間の開始に時間単位休暇が接する場合に、開始時刻を
	 * 接する時間単位休暇の終了時刻とする。<br>
	 * <br>
	 * 規定時間の終了に時間単位休暇が接する場合に、終了時刻を
	 * 接する時間単位休暇の開始時刻とする。<br>
	 * <br>
	 * 時間単位休暇を含めたことで時間間隔が妥当でなくなる場合は、
	 * 時間単位休暇を含まない規定時間を取得する。<br>
	 * 例えば、時間単位休暇を規定時間の開始時刻から終了時刻まで
	 * 連続して取得する場合が、想定される。<br>
	 * <br>
	 * @param requestEntity 申請エンティティ
	 * @param isCompleted   承認済フラグ(true：承認済申請のみ考慮、false：申請済申請を考慮)
	 * @return 規定時間(時間単位休暇含む)(0:00からの分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	TimeDuration getRegularAndHourlyHolidayTime(RequestEntityInterface requestEntity, boolean isCompleted)
			throws MospException;
	
	/**
	 * 規定時間(時短時間含む)(0:00からの分)を取得する。<br>
	 * <br>
	 * {@link WorkTypeEntityInterface#getRegularTime(RequestEntityInterface, boolean)}
	 * を元に、時短時間を考慮する。<br>
	 * <br>
	 * @param requestEntity 申請エンティティ
	 * @param isCompleted   承認済フラグ(true：承認済申請のみ考慮、false：申請済申請を考慮)
	 * @return 規定時間(時間単位休暇含む)(0:00からの分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	TimeDuration getRegularAndShortTime(RequestEntityInterface requestEntity, boolean isCompleted) throws MospException;
	
	/**
	 * 勤務形態の始業時刻及び終業時刻(0:00からの分)を取得する。<br>
	 * @return 勤務形態の始業時刻及び終業時刻(0:00からの分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	TimeDuration getRegularTime() throws MospException;
	
	/**
	 * 時短時間1(有給)の開始時刻及び終了時刻(0:00からの分)を取得する。<br>
	 * ただし、前半休の場合は時短時間1は無いものとして扱う。<br>
	 * <br>
	 * @param requestEntity 申請エンティティ
	 * @param isCompleted   承認済フラグ(true：承認済申請のみ考慮、false：申請済申請を考慮)
	 * @return 時短時間1(有給)の開始時刻及び終了時刻(0:00からの分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	TimeDuration getShort1PayTime(RequestEntityInterface requestEntity, boolean isCompleted) throws MospException;
	
	/**
	 * 時短時間2(有給)の開始時刻及び終了時刻(0:00からの分)を取得する。<br>
	 * ただし、後半休の場合は時短時間2は無いものとして扱う。<br>
	 * <br>
	 * @param requestEntity 申請エンティティ
	 * @param isCompleted   承認済フラグ(true：承認済申請のみ考慮、false：申請済申請を考慮)
	 * @return 時短時間2(有給)の開始時刻及び終了時刻(0:00からの分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	TimeDuration getShort2PayTime(RequestEntityInterface requestEntity, boolean isCompleted) throws MospException;
	
	/**
	 * 時短時間1(無給)の開始時刻及び終了時刻(0:00からの分)を取得する。<br>
	 * ただし、前半休の場合は時短時間1は無いものとして扱う。<br>
	 * <br>
	 * @param requestEntity 申請エンティティ
	 * @param isCompleted   承認済フラグ(true：承認済申請のみ考慮、false：申請済申請を考慮)
	 * @return 時短時間1(無給)の開始時刻及び終了時刻(0:00からの分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	TimeDuration getShort1UnpayTime(RequestEntityInterface requestEntity, boolean isCompleted) throws MospException;
	
	/**
	 * 時短時間2(無給)の開始時刻及び終了時刻(0:00からの分)を取得する。<br>
	 * ただし、後半休の場合は時短時間2は無いものとして扱う。<br>
	 * <br>
	 * @param requestEntity 申請エンティティ
	 * @param isCompleted   承認済フラグ(true：承認済申請のみ考慮、false：申請済申請を考慮)
	 * @return 時短時間2(無給)の開始時刻及び終了時刻(0:00からの分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	TimeDuration getShort2UnpayTime(RequestEntityInterface requestEntity, boolean isCompleted) throws MospException;
	
	/**
	 * 時短時間(無給)群(キー：開始時刻(キー順))を取得する。<br>
	 * 勤怠計算(日々)で無給時短時間を計算する際等に用いられる。<br>
	 * <br>
	 * @return 時短時間(無給)群(キー：開始時刻(キー順))
	 * @throws MospException 日付の変換に失敗した場合
	 */
	Map<Integer, TimeDuration> getShortUnpayTimes() throws MospException;
	
	/**
	 * 前残業時間(0:00からの分)を取得する。<br>
	 * <br>
	 * 前半休である場合は、勤務形態の前半休及び後半休の間の時間を超えて
	 * 前残業はできない。<br>
	 * <br>
	 * 時短時間1(無給)が設定されている勤務形態であり前半休でない場合は、
	 * 前残業申請があったとしても無かったものとする。<br>
	 * <br>
	 * 勤怠設定の勤務前残業が有効であり勤務形態の勤務前残業実績登録が有効である場合、
	 * 前残業申請の有無に関わらず、前残業時間を0:00からとする。<br>
	 * <br>
	 * @param timeSettingEntity 勤怠設定エンティティ
	 * @param requestEntity     申請エンティティ
	 * @param isCompleted       承認済フラグ(true：承認済申請のみ考慮、false：申請済申請を考慮)
	 * @return 前残業時間(0:00からの分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	TimeDuration getBeforeOvertime(TimeSettingEntityInterface timeSettingEntity, RequestEntityInterface requestEntity,
			boolean isCompleted) throws MospException;
	
	/**
	 * 半休間(前半休と後半休の間)の後残業時間(0:00からの分)を取得する。<br>
	 * <br>
	 * 後半休である場合は、勤務形態の前半休及び後半休の間の時間を超えて
	 * 後残業はできない。<br>
	 * <br>
	 * 後半休であり後残業申請が存在する場合にのみ、
	 * 有効な後残業時間(0:00からの分)を取得する。<br>
	 * <br>
	 * @param requestEntity 申請エンティティ
	 * @param isCompleted   承認済フラグ(true：承認済申請のみ考慮、false：申請済申請を考慮)
	 * @return 半休間(前半休と後半休の間)の後残業時間(0:00からの分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	TimeDuration getBitweenAfterOvertime(RequestEntityInterface requestEntity, boolean isCompleted)
			throws MospException;
	
	/**
	 * 残前休憩時間(分)を取得する。<br>
	 * @return 残前休憩時間(分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	int getOvertimeBeforeRest() throws MospException;
	
	/**
	 * 残前休憩開始までの勤務時間(分)を取得する。<br>
	 * <br>
	 * 規定始業時刻から当時間を超えて勤務(休憩は除く)する場合、
	 * 勤務形態で設定された残前休憩が発生する。<br>
	 * <br>
	 * 前半休の場合は、前半休時の始業時刻及び終業時刻の時間(分)を取得する。<br>
	 * それ以外の場合は、勤務形態の勤務時間(規定労働時間)(分)を取得する。<br>
	 * 但し、最大でも法定労働時間までとする。<br>
	 * <br>
	 * ここでは、後半休は考慮しない。<br>
	 * つまり、後半休取得時は、全日勤務と同じだけ勤務した後に残前休憩が発生する。<br>
	 * <br>
	 * @param requestEntity 申請エンティティ
	 * @param legalWorkTime 法定労働時間
	 * @param isCompleted   承認済フラグ(true：承認済申請のみ考慮、false：申請済申請を考慮)
	 * @return 残業開始までの勤務時間(分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	int getWorkTimeBeforeOvertimeBeforeRest(RequestEntityInterface requestEntity, int legalWorkTime,
			boolean isCompleted) throws MospException;
	
	/**
	 * 残業開始までの勤務時間(分)を取得する。<br>
	 * <br>
	 * 規定始業時刻から当時間を超えて勤務(休憩は除く)する場合、後残業が発生する。<br>
	 * <br>
	 * 前半休の場合は、前半休時の始業時刻及び終業時刻の時間(分)を取得する。<br>
	 * 後半休の場合は、後半休時の始業時刻及び終業時刻の時間(分)を取得する。<br>
	 * それ以外の場合は、勤務形態の勤務時間(規定労働時間)(分)を取得する。<br>
	 * 但し、最大でも法定労働時間までとする。<br>
	 * <br>
	 * @param requestEntity 申請エンティティ
	 * @param legalWorkTime 法定労働時間
	 * @param isCompleted   承認済フラグ(true：承認済申請のみ考慮、false：申請済申請を考慮)
	 * @return 残業開始までの勤務時間(分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	int getWorkTimeBeforeOvertime(RequestEntityInterface requestEntity, int legalWorkTime, boolean isCompleted)
			throws MospException;
	
	/**
	 * 残業休憩時間が有効であるかを確認する。<br>
	 * @return 確認結果(true：残業休憩時間が有効である、false：そうでない)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	boolean isOvertimeRestValid() throws MospException;
	
	/**
	 * 残業休憩時間(毎)(分)を取得する。<br>
	 * @return 残業休憩時間(毎)(分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	int getOvertimeRestPer() throws MospException;
	
	/**
	 * 残業休憩時間(分)を取得する。<br>
	 * @return 残業休憩時間(分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	int getOvertimeRest() throws MospException;
	
	/**
	 * 始業時刻を取得する。<br>
	 * <br>
	 * 勤務形態及び各種申請から、始業時刻を勤務日の時刻として算出する。<br>
	 * 対象となる承認状況群によって考慮する申請を決定する。<br>
	 * ここでは、振出・休出申請(振替出勤)及び勤務形態変更申請は、考慮しない。<br>
	 * また、残業申請及び時差出勤申請は、考慮しない。<br>
	 * <br>
	 * 1.全休の場合：<br>
	 * nullを返す。<br>
	 * <br>
	 * 2.前半休の場合：<br>
	 * 勤務形態の後半休開始時刻を返す。<br>
	 * 但し、残業申請(勤務前残業)がある場合は、そこから残業申請(勤務前残業)の
	 * 申請時間(分)を減算する(前半休の場合は前半休終了時刻が前残業の限度)。<br>
	 * <br>
	 * 3.振出・休出申請(休出申請)がある場合：<br>
	 * 振出・休出申請(休出申請)の出勤予定時刻を返す。<br>
	 * <br>
	 * 4.時短時間1が設定されている場合：<br>
	 * 時短時間1終了時刻と時間単位有給休暇が接する場合は、時間単位有給休暇の終了時刻を返す。<br>
	 * 時短時間1(無給)が設定されている場合は、時短時間1終了時刻を返す。<br>
	 * <br>
	 * 5.勤務形態の始業時刻と時間単位有給休暇が接する場合：<br>
	 * 時間単位有給休暇の終了時刻を返す。<br>
	 * <br>
	 * 6.それ以外の場合：<br>
	 * 勤務形態の始業時刻を返す。<br>
	 * <br>
	 * @param requestEntity 申請エンティティ
	 * @param statuses      対象となる承認状況群
	 * @return 始業時刻
	 * @throws MospException 日付の変換に失敗した場合
	 */
	Date getStartTime(RequestEntityInterface requestEntity, Set<String> statuses) throws MospException;
	
	/**
	 * 始業時刻を取得する。<br>
	 * 申請済申請を対象とする。<br>
	 * <br>
	 * {@link #getStartTime(RequestEntityInterface, Set) }を参照。
	 * <br>
	 * @param requestEntity 申請エンティティ
	 * @return 始業時刻
	 * @throws MospException 日付の変換に失敗した場合
	 */
	Date getStartTime(RequestEntityInterface requestEntity) throws MospException;
	
	/**
	 * 終業時刻を取得する。<br>
	 * <br>
	 * 勤務形態及び各種申請から、終業時刻を勤務日の時刻として算出する。<br>
	 * 対象となる承認状況群によって考慮する申請を決定する。<br>
	 * ここでは、振出・休出申請(振替出勤)及び勤務形態変更申請は、考慮しない。<br>
	 * また、時差出勤申請は、考慮しない。<br>
	 * 全休の場合は、nullを返す。<br>
	 * <br>
	 * 1.全休の場合：<br>
	 * nullを返す。<br>
	 * <br>
	 * 2.後半休の場合：<br>
	 * 勤務形態の前半休終了時刻を返す。<br>
	 * 但し、残業申請(勤務後残業)がある場合は、そこに残業申請(勤務後残業)の
	 * 申請時間(分)を加算する(後半休の場合は後半休開始時刻が後残業の限度)。<br>
	 * <br>
	 * 3.振出・休出申請(休出申請)がある場合：<br>
	 * 振出・休出申請(休出申請)の退勤予定時刻を返す。<br>
	 * <br>
	 * 4.時短時間2が設定されている場合：<br>
	 * 時短時間2開始時刻と時間単位有給休暇が接する場合は、時間単位有給休暇の開始時刻を返す。<br>
	 * 時短時間2(無給)が設定されている場合は、時短時間2開始時刻を返す。<br>
	 * <br>
	 * 5.勤務形態の終業時刻と時間単位有給休暇が接する場合：<br>
	 * 時間単位有給休暇の開始時刻を返す。<br>
	 * <br>
	 * 6.それ以外の場合：<br>
	 * 勤務形態の終業時刻を返す。<br>
	 * <br>
	 * @param requestEntity 申請エンティティ
	 * @param statuses      対象となる承認状況群
	 * @return 終業時刻
	 * @throws MospException 日付の変換に失敗した場合
	 */
	Date getEndTime(RequestEntityInterface requestEntity, Set<String> statuses) throws MospException;
	
	/**
	 * 始業時刻を取得する。<br>
	 * 申請済申請を対象とする。<br>
	 * <br>
	 * {@link #getEndTime(RequestEntityInterface, Set) }を参照。
	 * <br>
	 * @param requestEntity 申請エンティティ
	 * @return 終業時刻
	 * @throws MospException 日付の変換に失敗した場合
	 */
	Date getEndTime(RequestEntityInterface requestEntity) throws MospException;
	
	/**
	 * 法定休日出勤であるかを確認する。<br>
	 * <br>
	 * @return 確認結果(true：法定休日出勤である、false：そうでない)
	 */
	boolean isWorkOnLegal();
	
	/**
	 * 所定休日出勤であるかを確認する。<br>
	 * <br>
	 * @return 確認結果(true：所定休日出勤である、false：そうでない)
	 */
	boolean isWorkOnPrescribed();
	
	/**
	 * 休日出勤(法定か所定)であるかを確認する。<br>
	 * <br>
	 * @return 確認結果(true：休日出勤(法定か所定)である、false：そうでない)
	 */
	boolean isWorkOnHoliday();
	
	/**
	 * 法定休日(法定休日出勤含む)であるかを確認する。<br>
	 * <br>
	 * @return 確認結果(true：法定休日(法定休日出勤含む)である、false：そうでない)
	 */
	boolean isLegal();
	
	/**
	 * 所定休日(所定休日出勤含む)であるかを確認する。<br>
	 * <br>
	 * @return 確認結果(true：所定休日(所定休日出勤含む)である、false：そうでない)
	 */
	boolean isPrescribed();
	
	/**
	 * 法定休日(法定休日出勤含む)か所定休日(所定休日出勤含む)であるかを確認する。<br>
	 * <br>
	 * @return 確認結果(true：法定休日か所定休日である、false：そうでない)
	 */
	boolean isLegalOrPrescribed();
	
	/**
	 * 平日であるかを確認する。<br>
	 * <br>
	 * {@link WorkTypeEntityInterface#isLegalOrPrescribed()}
	 * でなければ平日とする。<br>
	 * <br>
	 * @return 確認結果(true：平日である、false：そうでない)
	 */
	boolean isWorkDay();
	
	/**
	 * 勤務形態項目値を取得する。<br>
	 * 勤務形態項目情報が取得できなかった場合は、デフォルト時刻を返す。<br>
	 * @param workTypeItemCode 勤務形態項目コード
	 * @return 勤務形態項目値
	 * @throws MospException 日付の変換に失敗した場合
	 */
	Date getItemValue(String workTypeItemCode) throws MospException;
	
	/**
	 * 勤務形態項目値を取得する。<br>
	 * 勤務形態項目情報が取得できなかった場合は、デフォルト時刻かnullを返す。<br>
	 * @param workTypeItemCode 勤務形態項目コード
	 * @param isNullAvailable  null可フラグ(true：取得できなかった場合にnullを取得、false：デフォルト日時を取得)
	 * @return 勤務形態項目値
	 * @throws MospException 日付の変換に失敗した場合
	 */
	Date getItemValue(String workTypeItemCode, boolean isNullAvailable) throws MospException;
	
	/**
	 * 勤務形態項目値(予備)を取得する。<br>
	 * 勤務形態項目情報が取得できなかった場合は、空文字列を返す。<br>
	 * @param workTypeItemCode 勤務形態項目コード
	 * @return 勤務形態項目値(予備)
	 */
	String getItemPreliminary(String workTypeItemCode);
	
	/**
	 * 勤務形態項目情報を取得する。<br>
	 * 勤務形態項目情報が取得できなかった場合は、nullを返す。<br>
	 * @param workTypeItemCode 勤務形態項目コード
	 * @return 勤務形態項目情報
	 */
	WorkTypeItemDtoInterface getWorkTypeItem(String workTypeItemCode);
	
	/**
	 * 時間間隔(0:00からの分)を取得する。<br>
	 * <br>
	 * 妥当な時間間隔が取得できなかった場合は、0-0の(妥当でない)時間間隔を返す。<br>
	 * <br>
	 * @param startItemCode 勤務形態項目コード(開始時刻)
	 * @param endItemCode   勤務形態項目コード(終了時刻)
	 * @return 時間間隔(0:00からの分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	TimeDuration getTimeDuration(String startItemCode, String endItemCode) throws MospException;
	
}
