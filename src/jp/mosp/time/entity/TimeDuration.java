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

import java.text.NumberFormat;
import java.util.Map;
import java.util.TreeMap;

import jp.mosp.time.utils.TimeUtility;

/**
 * 時間間隔情報。<br>
 * 0:00からの分数で時間間隔を保持する。<br>
 * 開始時刻及び終了時刻が0より小さくなることはない。<br>
 * <br>
 * 開始時刻が終了時刻以降(同じである場合も含める)である場合は、
 * 時間間隔として妥当でないと判断する。<br>
 */
public class TimeDuration implements Cloneable {
	
	/**
	 * 開始時刻(0:00からの分)。<br>
	 */
	private int	startTime;
	
	/**
	 * 終了時刻(0:00からの分)。<br>
	 */
	private int	endTime;
	
	
	/**
	 * コンストラクタ。
	 */
	private TimeDuration() {
		// 処理無し
	}
	
	/**
	 * コンストラクタ。
	 * @param startTime 開始時刻(0:00からの分)
	 * @param endTime   終了時刻(0:00からの分)
	 */
	private TimeDuration(int startTime, int endTime) {
		this.startTime = startTime < 0 ? 0 : startTime;
		this.endTime = endTime < 0 ? 0 : endTime;
	}
	
	/**
	 * 時間間隔を取得する。<br>
	 * <br>
	 * @param startTime 開始時刻(0:00からの分)
	 * @param endTime   終了時刻(0:00からの分)
	 * @return 時間間隔
	 */
	public static TimeDuration getInstance(int startTime, int endTime) {
		// 時間間隔を取得
		TimeDuration duration = new TimeDuration(startTime, endTime);
		// 時間間隔が妥当でない場合
		if (duration.isValid() == false) {
			// 0-0の(妥当でない)時間間隔を取得
			return getInvalid();
		}
		// 時間間隔を取得
		return duration;
	}
	
	/**
	 * 0-0の(妥当でない)時間間隔を取得する。<br>
	 * 時間間隔が存在しない場合等に利用する。<br>
	 * <br>
	 * @return 0-0の(妥当でない)時間間隔
	 */
	public static TimeDuration getInvalid() {
		return new TimeDuration(0, 0);
	}
	
	/**
	 * 時間間隔が妥当であるかを確認する。<br>
	 * <br>
	 * 開始時刻が終了時刻以降(同じである場合も含める)である場合は、
	 * 時間間隔として妥当でないと判断する。<br>
	 * <br>
	 * @return 確認結果(true：妥当である、false：妥当でない)
	 */
	public boolean isValid() {
		return startTime < endTime;
	}
	
	/**
	 * 時間間隔が対象時間間隔と同じであるかを確認する。<br>
	 * <br>
	 * @param duration 対象時間間隔
	 * @return 確認結果(true：時間間隔が対象時間間隔と同じ、false：そうでない)
	 */
	public boolean isEqual(TimeDuration duration) {
		// 対象時間間隔が存在しない場合
		if (duration == null) {
			// 同じでないと判断
			return false;
		}
		// 時間間隔が対象時間間隔と同じであるかを確認
		return startTime == duration.startTime && endTime == duration.getEndTime();
	}
	
	/**
	 * 時間間隔が対象時刻がより前であるかを確認する。<br>
	 * 対象時刻が開始時刻である場合は、前でないと判断する。<br>
	 * <br>
	 * @param time 対象時刻(0:00からの分)
	 * @return 確認結果(true：時間間隔が対象時刻がより前、false：そうでない)
	 */
	public boolean isBefore(int time) {
		// 時間間隔が対象時刻がより前であるかを確認
		return endTime < time;
	}
	
	/**
	 * 時間間隔が対象時刻がより後であるかを確認する。<br>
	 * 対象時刻が終了時刻である場合は、後でないと判断する。<br>
	 * <br>
	 * @param time 対象時刻(0:00からの分)
	 * @return 確認結果(true：時間間隔が対象時刻がより後、false：そうでない)
	 */
	public boolean isAfter(int time) {
		// 時間間隔が対象時刻がより後であるかを確認
		return time < startTime;
	}
	
	/**
	 * 時間間隔の中に対象時刻があるかを確認する。<br>
	 * 対象時刻が開始時刻か終了時刻である場合は、時間間隔の中であると判断する。<br>
	 * <br>
	 * @param time 対象時刻(0:00からの分)
	 * @return 確認結果(true：時間間隔の中に対象時刻がある、false：そうでない)
	 */
	public boolean isContain(int time) {
		// 開始時刻及び終了時刻の間に時刻があるかを確認
		return isContain(time, true, true);
	}
	
	/**
	 * 時間間隔の中に対象時刻があるかを確認する。<br>
	 * @param time           対象時刻(0:00からの分)
	 * @param isStartInclude 開始時刻含フラグ(true：開始時刻は時間間隔内とする、false：そうとはしない)
	 * @param isEndInclude   終了時刻含フラグ(true：終了時刻は時間間隔内とする、false：そうとはしない)
	 * @return 確認結果(true：時間間隔の中に対象時刻がある、false：そうでない)
	 */
	public boolean isContain(int time, boolean isStartInclude, boolean isEndInclude) {
		// 時間間隔が妥当でない場合
		if (isValid() == false) {
			// 時間間隔の中に対象時刻がないと判断
			return false;
		}
		// 対象時刻が開始時刻であり開始時刻は時間間隔内とする場合
		if (time == startTime && isStartInclude) {
			// 時間間隔の中に対象時刻があると判断
			return true;
		}
		// 対象時刻が終了時刻であり終了時刻は時間間隔内とする場合
		if (time == endTime && isEndInclude) {
			// 時間間隔の中に対象時刻があると判断
			return true;
		}
		// 開始時刻及び終了時刻の間に時刻があるかを確認
		return startTime < time && time < endTime;
	}
	
	/**
	 * 時間間隔の中に対象時間間隔があるかを確認する。<br>
	 * <br>
	 * 時間間隔と対象時間間隔が一致する場合も含まれると判断する。<br>
	 * <br>
	 * @param duration 対象時間間隔
	 * @return 確認結果(true：時間間隔の中に対象時間間隔がある、false：そうでない)
	 */
	public boolean isContain(TimeDuration duration) {
		// 時間間隔と対象時間間隔の重複分を取得
		TimeDuration overlap = getOverlap(duration);
		// 重複しない場合
		if (overlap.isValid() == false) {
			// 含まれないと判断
			return false;
		}
		// 重複分が対象時間間隔と一致するかを確認
		return overlap.isEqual(duration);
	}
	
	/**
	 * 対象時刻より前の時間間隔を取得する。<br>
	 * <br>
	 * ・時間間隔が対象時刻より前である場合：同じ開始時刻と終了時刻の時間間隔<br>
	 * ・時間間隔中に対象時刻がある場合　　：終了時刻を対象時刻とした時間間隔<br>
	 * ・時間間隔が対象時刻より後である場合：0-0の(妥当でない)時間間隔<br>
	 * <br>
	 * @param time 対象時刻(0:00からの分)
	 * @return 対象時刻より前の時間間隔
	 */
	public TimeDuration getBeforeTime(int time) {
		// 時間間隔が対象時刻より前である場合
		if (isBefore(time)) {
			// 同じ開始時刻と終了時刻の時間間隔を取得
			return getInstance(startTime, endTime);
		}
		// 時間間隔中に対象時刻がある場合
		if (isContain(time)) {
			// 開始時刻を対象時刻とした時間間隔を取得
			return getInstance(startTime, time);
		}
		// 時間間隔が対象時刻より後である場合(0-0の(妥当でない)時間間隔を取得)
		return getInvalid();
	}
	
	/**
	 * 対象時刻より後の時間間隔を取得する。<br>
	 * <br>
	 * ・時間間隔が対象時刻より前である場合：0-0の(妥当でない)時間間隔<br>
	 * ・時間間隔中に対象時刻がある場合　　：開始時刻を対象時刻とした時間間隔<br>
	 * ・時間間隔が対象時刻より後である場合：同じ開始時刻と終了時刻の時間間隔<br>
	 * <br>
	 * @param time 対象時刻(0:00からの分)
	 * @return 対象時刻より後の時間間隔
	 */
	public TimeDuration getAfterTime(int time) {
		// 時間間隔が対象時刻より前である場合
		if (isBefore(time)) {
			// 0-0の(妥当でない)時間間隔を取得
			return getInvalid();
		}
		// 時間間隔中に対象時刻がある場合
		if (isContain(time)) {
			// 開始時刻を対象時刻とした時間間隔を取得
			return getInstance(time, endTime);
		}
		// 時間間隔が対象時刻より後である場合(同じ開始時刻と終了時刻の時間間隔を取得)
		return getInstance(startTime, endTime);
	}
	
	/**
	 * 重複している時間間隔を取得する。<br>
	 * @param duration 対象時間間隔
	 * @return 重複している時間間隔
	 */
	public TimeDuration getOverlap(TimeDuration duration) {
		// どちらかが妥当でない場合
		if (isValid() == false || duration.isValid() == false) {
			// 0-0の(妥当でない)時間間隔を取得
			return TimeDuration.getInvalid();
		}
		// 重複している時刻を準備
		int overlapStart = startTime;
		int overlapEnd = endTime;
		// 時間間隔の開始時刻より対象時間間隔の開始時刻の方が後である場合
		if (overlapStart < duration.getStartTime()) {
			// 重複している時刻に対象時間間隔の開始時刻を設定
			overlapStart = duration.getStartTime();
		}
		// 対象時間間隔の終了時刻より時間間隔の終了時刻の方が後である場合
		if (duration.getEndTime() < overlapEnd) {
			// 重複している時刻に対象時間間隔の終了時刻を設定
			overlapEnd = duration.getEndTime();
		}
		// 重複している時間間隔を取得
		return TimeDuration.getInstance(overlapStart, overlapEnd);
	}
	
	/**
	 * 重複しているかどうかを確認する。<br>
	 * @param duration 対象時間間隔
	 * @return 確認結果(true：重複している、false：重複していない)
	 */
	public boolean isOverlap(TimeDuration duration) {
		// 重複しているかどうかを確認
		return getOverlap(duration).isValid();
	}
	
	/**
	 * 重複している時間間隔群を取得する。<br>
	 * <br>
	 * 各時間間隔が重複している場合は、結合される。<br>
	 * <br>
	 * @param durations 時間間隔群(キー：開始時刻)
	 * @return 重複している時間間隔群(キー：開始時刻(キー順))
	 */
	public Map<Integer, TimeDuration> getOverlap(Map<Integer, TimeDuration> durations) {
		// 重複している時間間隔群を準備
		Map<Integer, TimeDuration> overlaps = new TreeMap<Integer, TimeDuration>();
		// 時間間隔毎に処理
		for (TimeDuration duration : TimeUtility.combineDurations(durations).values()) {
			// 重複している時間を取得
			TimeDuration overlap = getOverlap(duration);
			// 重複している時間が妥当でない場合
			if (overlap.isValid() == false) {
				// 次の時間間隔へ
				continue;
			}
			// 重複している時間を時間間隔群に追加
			overlaps.put(overlap.startTime, overlap);
		}
		// 重複している時間間隔群を取得
		return TimeUtility.combineDurations(overlaps);
	}
	
	/**
	 * 重複している時間(分)を取得する。<br>
	 * <br>
	 * 各時間間隔との重複時間を加算して取得する。<br>
	 * ここでは、各時間間隔が重複しているかは、考慮しない。<br>
	 * <br>
	 * @param durations 時間間隔群(キー：開始時刻)
	 * @return 重複している時間(分)
	 */
	public int getOverlapMinutes(Map<Integer, TimeDuration> durations) {
		// 重複している時間(分)を準備
		int overlap = 0;
		// 時間間隔毎に処理
		for (TimeDuration duration : durations.values()) {
			// 重複している時間(分)を加算
			overlap += getOverlap(duration).getMinutes();
		}
		// 重複している時間を取得
		return overlap;
	}
	
	/**
	 * 重複していない時間間隔群(キー：開始時刻(キー順))を取得する。<br>
	 * <br>
	 * 当時間間隔のうち、対象時間間隔と重複していない部分を取得する。<br>
	 * 当時間間隔に含まれる時間間隔が対象時間間隔に含まれる場合、
	 * 重複していない時間間隔が前後に分割されるため、戻り値は時間間隔群とする。<br>
	 * <br>
	 * @param durations 対象時間間隔群(キー：開始時刻)
	 * @return 重複していない時間間隔群(キー：開始時刻(キー順))
	 */
	public Map<Integer, TimeDuration> getNotOverlap(Map<Integer, TimeDuration> durations) {
		// 重複していない時間間隔群を準備
		Map<Integer, TimeDuration> notOverlaps = new TreeMap<Integer, TimeDuration>();
		// 現在の開始時刻を準備
		int currentStart = startTime;
		// 対象時間間隔(結合したもの)毎に処理
		for (TimeDuration duration : TimeUtility.combineDurations(durations).values()) {
			// 対象時間間隔の開始時刻及び終了時刻を取得
			int targetStart = duration.getStartTime();
			int targetEnd = duration.getEndTime();
			// 重複しない場合
			if (targetEnd <= currentStart || endTime <= targetStart) {
				// 次の時間間隔へ
				continue;
			}
			// 現在の時間間隔が対象時間間隔に含まれる場合
			if (targetStart <= currentStart && endTime <= targetEnd) {
				// 現在の開始時刻を調整
				currentStart = targetEnd;
				// 処理終了
				break;
			}
			// 対象時間間隔が現在の時間間隔の前方にかかる場合
			if (targetStart <= currentStart && targetEnd < endTime) {
				// 現在の開始時刻を調整
				currentStart = targetEnd;
				// 次の時間間隔へ
				continue;
			}
			// 対象時間間隔が現在の時間間隔に含まれる場合
			if (currentStart < targetStart && targetEnd < endTime) {
				// 前後に分割された前の部分を追加
				notOverlaps.put(currentStart, TimeDuration.getInstance(currentStart, targetStart));
				// 現在の開始時刻を調整
				currentStart = targetEnd;
				// 次の時間間隔へ
				continue;
			}
			// 対象時間間隔が現在の時間間隔の後方にかかる場合
			if (currentStart < targetStart && endTime <= targetEnd) {
				// 重複していない部分を追加
				notOverlaps.put(currentStart, TimeDuration.getInstance(currentStart, targetStart));
				// 現在の開始時刻を調整
				currentStart = targetEnd;
				// 処理終了
				break;
			}
		}
		// 残った部分を追加(残った部分が無い場合は妥当でない時間間隔として除外される)
		notOverlaps.put(currentStart, TimeDuration.getInstance(currentStart, endTime));
		// 重複していない時間間隔群を取得
		return TimeUtility.combineDurations(notOverlaps);
	}
	
	/**
	 * 重複していない時間間隔群(キー：開始時刻(キー順))を取得する。<br>
	 * <br>
	 * {@link TimeDuration#getNotOverlap(Map)}を参照。<br>
	 * <br>
	 * @param duration 対象時間間隔
	 * @return 重複していない時間間隔群(キー：開始時刻(キー順))
	 */
	public Map<Integer, TimeDuration> getNotOverlap(TimeDuration duration) {
		// 時間間隔を時間間隔群として取得
		Map<Integer, TimeDuration> durations = new TreeMap<Integer, TimeDuration>();
		durations.put(duration.getStartTime(), duration);
		// 重複していない時間間隔群を取得
		return getNotOverlap(durations);
	}
	
	/**
	 * 時間間隔が接しているかを確認する。<br>
	 * 重複している場合は接していないと判断する。<br>
	 * <br>
	 * @param duration 時間間隔
	 * @return 確認結果(true：接している、false：接していない)
	 */
	public boolean isLink(TimeDuration duration) {
		// 接しているかを確認
		return startTime == duration.endTime || endTime == duration.startTime;
	}
	
	/**
	 * 重複してる箇所を後へずらした時間間隔群(キー：開始時刻(キー順))を取得する。<br>
	 * <br>
	 * @param durations 対象時間間隔群(キー：開始時刻)
	 * @return 重複してる箇所を後へずらした時間間隔群(キー：開始時刻(キー順))
	 */
	public Map<Integer, TimeDuration> getPostponed(Map<Integer, TimeDuration> durations) {
		// 後へずらした時間間隔群を準備
		Map<Integer, TimeDuration> postponed = new TreeMap<Integer, TimeDuration>();
		// 対象時間間隔群と重複しない時間を取得して追加
		postponed.putAll(getNotOverlap(durations));
		// 割り当てられていない時間を取得
		int remainTime = getMinutes() - TimeUtility.getMinutes(postponed);
		// 全て割り当てられた場合
		if (remainTime == 0) {
			// 後へずらした(重複していないのでずらしていないが)時間間隔群を取得
			return postponed;
		}
		// 対象時間間隔群と重複する時間を取得
		int overlap = TimeUtility.getMinutes(getOverlap(durations));
		// 対象時間間隔群の最後の時刻を取得
		int durationsEndTime = TimeUtility.getDuration(durations).getEndTime();
		// 対象時間間隔群の最後の時刻が終了時刻より前である場合
		if (durationsEndTime < endTime) {
			// 未割当の時間を後へずらして追加した時間間隔群を取得
			return TimeUtility.mergeDurations(postponed, getInstance(endTime, endTime + remainTime));
		}
		// 対象時間間隔群のすき間を取得
		Map<Integer, TimeDuration> gaps = TimeUtility.getGap(durations);
		// 対象時間間隔群のすき間から重複する時間までの時間を取得し追加
		postponed.putAll(TimeUtility.getReachTimes(gaps, overlap));
		// 割り当てられていない時間を再取得
		remainTime = getMinutes() - TimeUtility.getMinutes(postponed);
		// 全て割り当てられた場合
		if (remainTime == 0) {
			// 後へずらした時間間隔群を取得
			return postponed;
		}
		// 割り当てられていない時間を割り当て(対象時間間隔群の最後の時刻から)
		int startTime = TimeUtility.getDuration(durations).getEndTime();
		int endTime = startTime + remainTime;
		postponed.put(startTime, TimeDuration.getInstance(startTime, endTime));
		// 後へずらした時間間隔群を取得
		return postponed;
	}
	
	/**
	 * 終了時刻と開始時刻の差(分)を取得する。<br>
	 * @return 終了時刻と開始時刻の差(分)
	 */
	public int getMinutes() {
		// 時間間隔が妥当でない場合
		if (isValid() == false) {
			// 0を取得
			return 0;
		}
		// 終了時刻と開始時刻の差を取得
		return endTime - startTime;
	}
	
	/**
	 * @return startTime
	 */
	public int getStartTime() {
		return startTime;
	}
	
	/**
	 * @return endTime
	 */
	public int getEndTime() {
		return endTime;
	}
	
	@Override
	public TimeDuration clone() {
		// 時間間隔の複製を準備
		TimeDuration clone = null;
		// 時間間隔の複製を作成
		try {
			clone = (TimeDuration)super.clone();
		} catch (CloneNotSupportedException e) {
			clone = getInstance(startTime, endTime);
		}
		// 時間間隔の複製を取得
		return clone;
	}
	
	@Override
	public String toString() {
		// 数値フォーマットを取得
		NumberFormat format = NumberFormat.getNumberInstance();
		// 整数部分最小桁数を設定
		format.setMinimumIntegerDigits(2);
		// 文字列を作成
		StringBuilder sb = new StringBuilder();
		sb.append(format.format(TimeUtility.getHours(startTime)));
		sb.append(":");
		sb.append(format.format(TimeUtility.getMinutes(startTime)));
		sb.append("-");
		sb.append(format.format(TimeUtility.getHours(endTime)));
		sb.append(":");
		sb.append(format.format(TimeUtility.getMinutes(endTime)));
		// 文字列を取得
		return sb.toString();
	}
	
}
