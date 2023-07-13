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
package jp.mosp.platform.utils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;

import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.instance.InstanceFactory;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.MenuUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.base.ActivateDtoInterface;
import jp.mosp.platform.base.CodeDtoInterface;
import jp.mosp.platform.base.PlatformDtoInterface;
import jp.mosp.platform.base.PlatformVo;
import jp.mosp.platform.base.RecordDtoInterface;
import jp.mosp.platform.bean.system.impl.SectionReferenceBean;
import jp.mosp.platform.comparator.base.ActivateDateComparator;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.constant.PlatformFileConst;
import jp.mosp.platform.dto.base.ApplicationMasterDtoInterface;
import jp.mosp.platform.dto.base.PersonalIdDtoInterface;
import jp.mosp.platform.dto.base.WorkflowNumberDtoInterface;
import jp.mosp.platform.dto.file.ExportDtoInterface;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;

/**
 * プラットフォームにおける有用なメソッドを提供する。<br><br>
 */
public class PlatformUtility {
	
	/**
	 * メインメニューキー(承認管理)。<br>
	 */
	public static final String	MAIN_MENU_WORKFLOW_MANAGE	= "menuWorkflowManage";
	
	/**
	 * メニューキー(ユニット一覧)。<br>
	 */
	public static final String	MENU_UNIT_LIST				= "UnitList";
	
	/**
	 * テーブル区分(ユニット情報(所属))。<br>
	 */
	public static final String	TABLE_TYPE_UNIT_SECTION		= "unit_section";
	
	/**
	 * テーブル区分(ユニット情報(個人))。<br>
	 */
	public static final String	TABLE_TYPE_UNIT_PERSON		= "unit_person";
	
	
	/**
	 * 他クラスからのインスタンス化を防止する。<br>
	 */
	private PlatformUtility() {
		// 処理無し
	}
	
	/**
	 * ソートマークを取得する。<br>
	 * ソートキーが{@link PlatformVo#getComparatorName()}と異なる場合、何も表示しない。
	 * @param sortKey    ソートキー
	 * @param mospParams MosP処理情報
	 * @return ソートマーク
	 */
	public static String getSortMark(String sortKey, MospParams mospParams) {
		// ソートマークを取得
		return getSortMark(sortKey, mospParams, false);
	}
	
	/**
	 * ソートマークを取得する。<br>
	 * ソートキーが{@link PlatformVo#getComparatorName()}と異なる場合、何も表示しない。
	 * @param sortKey    ソートキー
	 * @param mospParams MosP処理情報
	 * @param needBlank  空白要否(true：ソートマークの前に半角スペースを配置、false：ソートマークのみ)
	 * @return ソートマーク
	 */
	public static String getSortMark(String sortKey, MospParams mospParams, boolean needBlank) {
		// VO取得
		PlatformVo vo = (PlatformVo)mospParams.getVo();
		// ソートキー比較
		if (sortKey.equals(vo.getComparatorName()) == false) {
			return MospConst.STR_EMPTY;
		}
		// ソートマークを準備
		StringBuilder sb = new StringBuilder();
		// 空白が必要な場合
		if (needBlank) {
			// 半角スペースを配置
			sb.append(MospConst.STR_SB_SPACE);
		}
		// ソートマークを取得
		String sortMark = PfNameUtility.lowerTriangular(mospParams);
		// 昇順である場合
		if (vo.isAscending()) {
			// ソートマークを取得
			sortMark = PfNameUtility.upperTriangular(mospParams);
		}
		// ソートマークを取得
		return sb.append(sortMark).toString();
	}
	
	/**
	 * 対象リストから個人IDセットを取得する。<br>
	 * @param list 対象リスト
	 * @return 個人IDセット
	 */
	public static Set<String> getPersonalIdSet(List<? extends PersonalIdDtoInterface> list) {
		// 個人IDセット準備
		Set<String> set = new LinkedHashSet<String>();
		// 情報毎に処理
		for (PersonalIdDtoInterface dto : list) {
			set.add(dto.getPersonalId());
		}
		return set;
	}
	
	/**
	 * 対象DTO群からDTO群(キー：個人ID、値：DTO群)を取得する。<br>
	 * @param dtos 対象DTO群
	 * @return DTO群(キー：個人ID、値：DTO群)
	 */
	public static <T extends PersonalIdDtoInterface> Map<String, Set<T>> getPersonalIdMap(Collection<T> dtos) {
		// DTO群(キー：個人ID、値：DTO群)を準備
		Map<String, Set<T>> map = new TreeMap<String, Set<T>>();
		// 情報毎に処理
		for (T dto : dtos) {
			// 個人IDを取得
			String personalId = dto.getPersonalId();
			// DTO群(キー：個人ID、値：DTO群)からDTO群を取得
			Set<T> set = map.get(personalId);
			// DTO群(キー：個人ID、値：DTO群)からDTO群を取得できなかった場合
			if (set == null) {
				// DTO群を準備
				set = new LinkedHashSet<T>();
				map.put(personalId, set);
			}
			// DTO群に追加
			set.add(dto);
		}
		// DTO群(キー：個人ID)を取得
		return map;
	}
	
	/**
	 * 対象DTO群からDTO群(キー：個人ID、値：DTO)を取得する。<br>
	 * 同じ個人IDで複数のDTOが存在する場合は、上書される。<br>
	 * @param dtos 対象DTO群
	 * @return DTO(キー：個人ID、値：DTO)
	 */
	public static <T extends PersonalIdDtoInterface> Map<String, T> getPersonalIdDtoMap(Collection<T> dtos) {
		// DTO群(キー：個人ID、値：DTO群)を準備
		Map<String, T> map = new TreeMap<String, T>();
		// 情報毎に処理
		for (T dto : dtos) {
			// DTO群に追加
			map.put(dto.getPersonalId(), dto);
		}
		// DTO群(キー：個人ID)を取得
		return map;
	}
	
	/**
	 * 対象DTO群からDTO群(キー：個人ID、値：DTO)を取得する。<br>
	 * 同じ個人IDで複数のDTOが存在する場合は、上書される。<br>
	 * 但し、承認情報群に含まれないワークフロー番号を持つ情報は対象外とする。<br>
	 * @param dtos      対象DTO群
	 * @param workflows ワークフロー情報群(キー：ワークフロー番号)
	 * @param statuses  承認状況群
	 * @return DTO(キー：個人ID、値：DTO)
	 */
	public static <T extends PersonalIdDtoInterface & WorkflowNumberDtoInterface> Map<String, T> getPersonalIdDtoMap(
			Collection<T> dtos, Map<Long, WorkflowDtoInterface> workflows, Set<String> statuses) {
		// 対象DTO群からDTO群(キー：個人ID、値：DTO)を取得(承認情報群に含まれないワークフロー番号を持つ情報は対象外)
		return getPersonalIdDtoMap(WorkflowUtility.getStatusMatchedDtos(dtos, workflows, statuses));
	}
	
	/**
	 * 対象DTO群からDTO群(キー：有効日、値：DTO群)を取得する。<br>
	 * @param dtos 対象DTO群
	 * @return DTO群(キー：有効日、値：DTO群)
	 */
	public static <T extends ActivateDtoInterface> Map<Date, Set<T>> getActivateDateMap(Collection<T> dtos) {
		// DTO群(キー：有効日、値：DTO群)を準備
		Map<Date, Set<T>> map = new TreeMap<Date, Set<T>>();
		// 情報毎に処理
		for (T dto : dtos) {
			// 有効日を取得
			Date activateDate = dto.getActivateDate();
			// DTO群(キー：有効日、値：DTO群)からDTO群を取得
			Set<T> set = map.get(activateDate);
			// DTO群(キー：有効日、値：DTO群)からDTO群を取得できなかった場合
			if (set == null) {
				// DTO群を準備
				set = new LinkedHashSet<T>();
				map.put(activateDate, set);
			}
			// DTO群に追加
			set.add(dto);
		}
		// DTO群(キー：有効日)を取得
		return map;
	}
	
	/**
	 * 対象セットから対象日以前で最新の有効日を取得する。<br>
	 * <br>
	 * 有効日昇順を行い対象セットが空である場合、
	 * 対象日以前の情報が含まれない場合は、nullを返す。<br>
	 * <br>
	 * @param set 対象セット
	 * @param targetDate 対象日
	 * @return 対象日以前で最新の有効日
	 */
	public static Date getLatestActivateDate(Set<? extends ActivateDtoInterface> set, Date targetDate) {
		// 日付リスト準備
		List<? extends ActivateDtoInterface> list = new ArrayList<ActivateDtoInterface>(set);
		// ソート
		Collections.sort(list, new ActivateDateComparator());
		// 対象日以前で最新の有効日を取得
		return getLatestActivateDate(list, targetDate);
	}
	
	/**
	 * 対象リスト(有効日昇順)から対象日以前で最新の有効日を取得する。<br>
	 * <br>
	 * 対象リストが空である場合、対象日以前の情報が含まれない場合は、nullを返す。<br>
	 * <br>
	 * @param list       対象リスト(有効日昇順)
	 * @param targetDate 対象日
	 * @return 対象日以前で最新の有効日
	 */
	public static Date getLatestActivateDate(List<? extends ActivateDtoInterface> list, Date targetDate) {
		// 対象日以前で最新の情報を取得
		ActivateDtoInterface latestDto = getLatestDto(list, targetDate);
		// 対象日以前の情報が含まれない場合
		if (latestDto == null) {
			return null;
		}
		// 対象日以前で最新の有効日を取得
		return latestDto.getActivateDate();
	}
	
	/**
	 * 対象リスト(有効日昇順)から対象日以前で最新の情報を取得する。<br>
	 * <br>
	 * 対象リストが空である場合、対象日以前の情報が含まれない場合は、nullを返す。<br>
	 * <br>
	 * @param list       対象リスト(有効日昇順)
	 * @param targetDate 対象日
	 * @return 対象日以前で最新の情報
	 */
	public static <T extends ActivateDtoInterface> T getLatestDto(List<T> list, Date targetDate) {
		// 対象リスト及び対象日を確認
		if (targetDate == null || list == null) {
			return null;
		}
		// 対象日以前で最新の情報を格納する変数を準備
		T latestDto = null;
		// 対象情報毎に処理
		for (T dto : list) {
			// 有効日が対象日以前である場合
			if (!targetDate.before(dto.getActivateDate())) {
				latestDto = dto;
			}
		}
		// 対象日以前で最新の有効日を取得
		return latestDto;
	}
	
	/**
	 * 対象リスト(有効日昇順)から対象日以前で最新の有効な情報を取得する。<br>
	 * <br>
	 * 対象リストが空である場合、対象日以前で有効な情報が含まれない場合は、nullを返す。<br>
	 * <br>
	 * @param list       対象リスト(有効日昇順)
	 * @param targetDate 対象日
	 * @return 対象日以前で最新の情報
	 */
	public static <T extends PlatformDtoInterface> T getLatestActiveDto(List<T> list, Date targetDate) {
		// 対象リスト及び対象日を確認
		if (targetDate == null || list == null) {
			return null;
		}
		// 対象日以前で最新の有効な情報を格納する変数を準備
		T latestActiveDto = null;
		// 対象情報毎に処理
		for (T dto : list) {
			// 有効日が対象日以前であり有効である場合
			if (targetDate.before(dto.getActivateDate()) == false && isDtoActivate(dto)) {
				latestActiveDto = dto;
			}
		}
		// 対象日以前で最新の有効な有効日を取得
		return latestActiveDto;
	}
	
	/**
	 * 対象リストから有効日が合致する情報を取得する。<br>
	 * <br>
	 * 対象リストが空である場合、有効日が合致する情報が含まれない場合は、nullを返す。<br>
	 * <br>
	 * @param list         対象リスト
	 * @param activateDate 有効日
	 * @return 対象日以前で最新の有効日
	 */
	public static ActivateDtoInterface getDto(List<? extends ActivateDtoInterface> list, Date activateDate) {
		// 対象リスト及び対象日を確認
		if (activateDate == null || list == null) {
			return null;
		}
		// 対象情報毎に処理
		for (ActivateDtoInterface dto : list) {
			// 有効日が対象日以前である場合
			if (activateDate.compareTo(dto.getActivateDate()) == 0) {
				return dto;
			}
		}
		return null;
	}
	
	/**
	 * 対象情報群からコードが合致する情報を取得する。<br>
	 * 対象情報群が空である場合、コードが合致する情報が含まれない場合は、nullを返す。<br>
	 * <br>
	 * @param dtos 対象情報群
	 * @param code コード
	 * @return 対象日以前で最新の有効日
	 */
	public static <T extends CodeDtoInterface> T getCodeDto(Collection<T> dtos, String code) {
		// 対象情報群かコードがnullである確認
		if (MospUtility.isEmpty(dtos, code)) {
			// nullを取得
			return null;
		}
		// 対象情報毎に処理
		for (T dto : dtos) {
			// 対象情報が存在しない場合
			if (MospUtility.isEmpty(dto)) {
				// 次の対象情報へ
				continue;
			}
			// コードが合致する場合
			if (MospUtility.isEqual(code, dto.getCode())) {
				// 対象情報を取得
				return dto;
			}
		}
		// nullを取得
		return null;
	}
	
	/**
	 * 対象情報群からコードが合致する情報群を取得する。<br>
	 * <br>
	 * @param dtos 対象情報群
	 * @param code コード
	 * @return 対象日以前で最新の有効日
	 */
	public static <T extends CodeDtoInterface> Set<T> getCodeDtos(Collection<T> dtos, String code) {
		// 情報群を準備
		Set<T> set = new LinkedHashSet<T>();
		// 対象情報群かコードがnullである確認
		if (MospUtility.isEmpty(dtos, code)) {
			// 空の情報群を取得
			return set;
		}
		// 対象情報毎に処理
		for (T dto : dtos) {
			// 対象情報が存在しない場合
			if (MospUtility.isEmpty(dto)) {
				// 次の対象情報へ
				continue;
			}
			// コードが合致する場合
			if (MospUtility.isEqual(code, dto.getCode())) {
				// 対象情報を情報群に設定
				set.add(dto);
			}
		}
		// 情報群を取得
		return set;
	}
	
	/**
	 * 対象情報群から情報群(キー：コード、有効日(昇順))を取得する。<br>
	 * <br>
	 * @param dtos 対象情報群
	 * @return 情報群(キー：コード、有効日(昇順))
	 * @throws MospException オブジェクトの生成に失敗した場合
	 */
	public static <T extends CodeDtoInterface & ActivateDtoInterface> Map<String, Map<Date, T>> getCodeActivateMap(
			Collection<T> dtos) throws MospException {
		// 情報群(キー：コード、有効日(昇順))を準備
		Map<String, Map<Date, T>> codeActivateMap = new TreeMap<String, Map<Date, T>>();
		// 対象情報毎に処理
		for (T dto : dtos) {
			// コードと有効日を取得
			String code = dto.getCode();
			Date activateDate = dto.getActivateDate();
			// コードで情報群(キー：有効日(昇順))を取得
			Map<Date, T> activateMap = MospUtility.getMapValue(codeActivateMap, code, TreeMap.class);
			// 情報群(キー：有効日(昇順))に情報を設定
			activateMap.put(activateDate, dto);
		}
		// 情報群(キー：コード、有効日(昇順))を取得
		return codeActivateMap;
	}
	
	/**
	 * 対象情報群(キー：コード、有効日)から検索条件情報とコード及び有効日が合致する情報群を取得する。<br>
	 * 対象情報群が空である場合、コード及び有効日が合致する情報が含まれない場合は、nullを返す。<br>
	 * <br>
	 * @param dtos 対象情報群(キー：コード、有効日)
	 * @param dto  検索条件情報
	 * @return 情報
	 */
	public static <T extends CodeDtoInterface & ActivateDtoInterface, K extends CodeDtoInterface & ActivateDtoInterface> Optional<Set<T>> getCodeActivateDtos(
			Map<String, Map<Date, Set<T>>> dtos, K dto) {
		// 対象情報群か検索対象情報が存在しない場合
		if (MospUtility.isEmpty(dtos, dto)) {
			// nullを取得
			return Optional.ofNullable(null);
		}
		// 対象情報群(キー：コード、有効日)からコード及び有効日が合致する情報群を取得
		return getCodeActivateDtos(dtos, dto.getCode(), dto.getActivateDate());
	}
	
	/**
	 * 対象情報群(キー：コード、有効日)からコード及び有効日が合致する情報を取得する。<br>
	 * 対象情報群が空である場合、コード及び有効日が合致する情報が含まれない場合は、nullを返す。<br>
	 * <br>
	 * @param dtos         対象情報群(キー：コード、有効日)
	 * @param code         コード
	 * @param activateDate 有効日
	 * @return 情報
	 */
	public static <T extends CodeDtoInterface & ActivateDtoInterface> Optional<Set<T>> getCodeActivateDtos(
			Map<String, Map<Date, Set<T>>> dtos, String code, Date activateDate) {
		// コードで対象情報群(キー：有効日)を取得
		Map<Date, Set<T>> activateMap = dtos.get(code);
		// コードで対象情報群(キー：有効日)を取得できなかった場合
		if (MospUtility.isEmpty(activateMap)) {
			// nullを取得
			return Optional.ofNullable(null);
		}
		// 対象情報を取得
		return Optional.ofNullable(activateMap.get(activateDate));
	}
	
	/**
	 * 対象情報群(キー：コード、有効日(昇順))から対象日時点で有効である情報群を取得する。<br>
	 * 同一コードの情報が存在する場合、対象日以前で最新の情報を取得対象とする。<br>
	 * <br>
	 * @param dtos       対象情報群(キー：コード、有効日(昇順))
	 * @param targetDate 対象日
	 * @return 情報群
	 */
	public static <T extends CodeDtoInterface & ActivateDtoInterface> Set<T> getActivateDtos(
			Map<String, Map<Date, T>> dtos, Date targetDate) {
		// 情報群を準備
		Set<T> activeDtos = new LinkedHashSet<T>();
		// 対象情報群(キー：有効日)毎に処理
		for (Map<Date, T> map : dtos.values()) {
			// 対象日以前で最新の情報を取得
			T dto = getLatestDto(new ArrayList<T>(map.values()), targetDate);
			// 対象日以前で最新の情報を取得できた場合
			if (MospUtility.isEmpty(dto) == false) {
				// 情報群に設定
				activeDtos.add(dto);
			}
		}
		// 情報群を取得
		return activeDtos;
	}
	
	/**
	 * 対象情報群(キー：K1、K2)から情報群(Set)を取得する。<br>
	 * <br>
	 * @param dtos 対象情報群(キー：K1、K2)
	 * @return 情報
	 */
	public static <T, K1, K2> Set<T> getDtoSet(Map<K1, Map<K2, T>> dtos) {
		// 情報群を準備
		Set<T> set = new LinkedHashSet<T>();
		// 対象情報群(キー：K2)毎に処理
		for (Map<K2, T> map : dtos.values()) {
			// 対象情報毎に処理
			for (T dto : map.values()) {
				// 対象情報を設定
				set.add(dto);
			}
		}
		// 情報群を取得
		return set;
	}
	
	/**
	 * DTO群(キー：レコード識別ID)を取得する。<br>
	 * @param dtos DTO群
	 * @return DTO群(キー：レコード識別ID)
	 */
	public static <T extends RecordDtoInterface> Map<Long, T> getRecordDtoMap(Collection<T> dtos) {
		// DTO群(キー：レコード識別ID)を準備
		Map<Long, T> map = new LinkedHashMap<Long, T>();
		// DTO毎に処理
		for (T dto : dtos) {
			map.put(dto.getRecordId(), dto);
		}
		// DTO群(キー：レコード識別ID)を取得
		return map;
	}
	
	/**
	 * レコード識別ID配列を取得する。<br>
	 * @param dtos DTO群
	 * @return レコード識別ID配列
	 */
	public static long[] getRecordIds(Collection<? extends RecordDtoInterface> dtos) {
		// レコード識別ID配列を準備
		long[] recordIds = new long[dtos.size()];
		// インデックスを準備
		int idx = 0;
		// DTO毎に処理
		for (RecordDtoInterface dto : dtos) {
			recordIds[idx++] = dto.getRecordId();
		}
		// レコード識別ID配列を取得
		return recordIds;
	}
	
	/**
	 * 次の連番(数値)を取得する。<br>
	 * <br>
	 * 発番対象文字列群からフォーマット文字列に合致する
	 * 最も数値の大きい文字列を特定し、それに1を加えた数値を返す。<br>
	 * <br>
	 * 但し、最も大きな数値が最大と同じ場合は、最大値を返す。<br>
	 * 最も大きな数値が最小値より小さい場合は、最小値を返す。<br>
	 * <br>
	 * 次の発番対象文字列は、対象から外す。<br>
	 * ・フォーマット文字列と文字列長が同じでない<br>
	 * ・フォーマット文字列を使って数値を抽出できない<br>
	 * ・抽出した数値が最小値～最大値に含まれない<br>
	 * <br>
	 * @param format フォーマット文字列
	 * @param min    最小値
	 * @param max    最大値
	 * @param set    発番対象文字列群
	 * @return 次の連番(数値)
	 */
	public static long next(String format, long min, long max, Set<String> set) {
		// 発番対象文字列群をリストに変換
		List<String> list = new ArrayList<String>(set);
		// ソート(降順)
		Collections.sort(list);
		Collections.reverse(list);
		// フォーマット定義準備
		DecimalFormat decimalFormat = new DecimalFormat(format);
		// 発番対象文字列毎に処理
		for (String str : list) {
			// フォーマットと同じ文字列長でない場合
			if (str.length() != format.length()) {
				// 対象外
				continue;
			}
			// 連番準備
			long seq = 0L;
			try {
				// フォーマット文字列で数値を抽出
				seq = decimalFormat.parse(str).longValue();
			} catch (Throwable t) {
				// フォーマット文字列を使って数値を抽出できない場合
				continue;
			}
			// 最大値より大きい場合
			if (max < seq) {
				// 対象外
				continue;
			}
			// 最大値と同じ場合
			if (max == seq) {
				// 最大値を取得
				return max;
			}
			// 最小値より小さい場合
			if (seq < min) {
				// 最小値を取得
				return min;
			}
			// 1を加えた数値を取得
			return seq + 1;
		}
		// 最小値を取得(最も大きい数値が取得できなかった場合)
		return min;
	}
	
	/**
	 * 適用情報群から対象人事情報が適用される適用情報を取得する。<br>
	 * <br>
	 * 対象日時点における最新の有効な情報から、以下の方法で順番に
	 * 適用されている情報を探していき、最初に見つかった適用情報を返す。<br>
	 *  1.個人ID<br>
	 *  2.職位、所属、雇用契約、勤務地<br>
	 *  3.職位、所属、雇用契約<br>
	 *  4.職位、所属<br>
	 *  5.職位<br>
	 *  6.所属、雇用契約、勤務地<br>
	 *  7.所属、雇用契約<br>
	 *  8.所属<br>
	 *  9.雇用契約、勤務地<br>
	 * 10.雇用契約<br>
	 * 11.勤務地<br>
	 * 12.指定無し<br>
	 * <br>
	 * @param humanDto  人事情報
	 * @param personSet 適用情報(個人)群
	 * @param masterSet 適用情報(マスタ)群
	 * @return 適用情報
	 */
	public static ApplicationMasterDtoInterface getApplicationMaster(HumanDtoInterface humanDto,
			Set<? extends ApplicationMasterDtoInterface> personSet,
			Set<? extends ApplicationMasterDtoInterface> masterSet) {
		// DTO準備
		ApplicationMasterDtoInterface dto = null;
		// 人事情報確認
		if (humanDto == null) {
			return dto;
		}
		// 変数準備
		String personalId = humanDto.getPersonalId();
		String positionCode = humanDto.getPositionCode();
		String sectionCode = humanDto.getSectionCode();
		String employmentContractCode = humanDto.getEmploymentContractCode();
		String workPlaceCode = humanDto.getWorkPlaceCode();
		String blank = "";
		// 1.個人
		dto = getApplicationPerson(personSet, personalId);
		if (dto != null) {
			return dto;
		}
		// 2.職位、所属、雇用契約、勤務地
		dto = getApplicationMaster(masterSet, workPlaceCode, employmentContractCode, sectionCode, positionCode);
		if (dto != null) {
			return dto;
		}
		// 3.職位、所属、雇用契約
		dto = getApplicationMaster(masterSet, blank, employmentContractCode, sectionCode, positionCode);
		if (dto != null) {
			return dto;
		}
		// 4.職位、所属
		dto = getApplicationMaster(masterSet, blank, blank, sectionCode, positionCode);
		if (dto != null) {
			return dto;
		}
		// 5.職位
		dto = getApplicationMaster(masterSet, blank, blank, blank, positionCode);
		if (dto != null) {
			return dto;
		}
		// 6.所属、雇用契約、勤務地
		dto = getApplicationMaster(masterSet, workPlaceCode, employmentContractCode, sectionCode, blank);
		if (dto != null) {
			return dto;
		}
		// 7.所属、雇用契約
		dto = getApplicationMaster(masterSet, blank, employmentContractCode, sectionCode, blank);
		if (dto != null) {
			return dto;
		}
		// 8.所属
		dto = getApplicationMaster(masterSet, blank, blank, sectionCode, blank);
		if (dto != null) {
			return dto;
		}
		// 9.雇用契約、勤務地
		dto = getApplicationMaster(masterSet, workPlaceCode, employmentContractCode, blank, blank);
		if (dto != null) {
			return dto;
		}
		// 10.雇用契約
		dto = getApplicationMaster(masterSet, blank, employmentContractCode, blank, blank);
		if (dto != null) {
			return dto;
		}
		// 11.勤務地
		dto = getApplicationMaster(masterSet, workPlaceCode, blank, blank, blank);
		if (dto != null) {
			return dto;
		}
		// 12.指定無し
		dto = getApplicationMaster(masterSet, blank, blank, blank, blank);
		if (dto != null) {
			return dto;
		}
		// 該当無し
		return dto;
	}
	
	/**
	 * 適用情報(個人)群から対象となる適用情報を取得する。<br>
	 * <br>
	 * @param personSet  適用情報(個人)群
	 * @param personalId 個人ID
	 * @return 適用情報
	 */
	protected static ApplicationMasterDtoInterface getApplicationPerson(
			Set<? extends ApplicationMasterDtoInterface> personSet, String personalId) {
		// 適用情報(個人)毎に処理
		for (ApplicationMasterDtoInterface dto : personSet) {
			// 設定されている個人ID(カンマ区切)を取得
			String personalIds = dto.getPersonalIds();
			// 対象個人IDが含まれている場合(個人IDは固定長)
			if (personalIds.contains(personalId)) {
				// 適用情報を取得
				return dto;
			}
		}
		// 取得できなかった場合
		return null;
	}
	
	/**
	 * 適用情報(マスタ)群から対象となる適用情報を取得する。<br>
	 * <br>
	 * @param masterSet              適用情報(マスタ)群
	 * @param workPlaceCode          勤務地コード
	 * @param employmentContractCode 雇用契約コード
	 * @param sectionCode            所属コード
	 * @param positionCode           職位コード
	 * @return 適用情報
	 */
	protected static ApplicationMasterDtoInterface getApplicationMaster(
			Set<? extends ApplicationMasterDtoInterface> masterSet, String workPlaceCode, String employmentContractCode,
			String sectionCode, String positionCode) {
		// 適用情報(マスタ)毎に処理
		for (ApplicationMasterDtoInterface dto : masterSet) {
			// 勤務地コードが合致しない場合
			if (dto.getWorkPlaceCode().equals(workPlaceCode) == false) {
				continue;
			}
			// 雇用契約コードが合致しない場合
			if (dto.getEmploymentContractCode().equals(employmentContractCode) == false) {
				continue;
			}
			// 所属コードが合致しない場合
			if (dto.getSectionCode().equals(sectionCode) == false) {
				continue;
			}
			// 職位コードが合致しない場合
			if (dto.getPositionCode().equals(positionCode) == false) {
				continue;
			}
			// 適用情報を取得(全て合致する場合)
			return dto;
		}
		// 取得できなかった場合
		return null;
	}
	
	/**
	 * 所属表示内容が表示名称かを確認する。<br>
	 * @param mospParams MosP処理情報
	 * @return 確認結果(true：所属表示内容が表示名称、false：所属表示内容が名称)
	 */
	public static boolean isSectionDisplayName(MospParams mospParams) {
		// 所属プルダウン表示内容設定取得
		String useDisplayName = mospParams.getApplicationProperty(SectionReferenceBean.APP_SECTION_PULLDOWN_NAME);
		// 所属プルダウン表示内容設定確認
		return MospUtility.isEqual(useDisplayName, SectionReferenceBean.SECTION_PULLDOWN_NAME_DISPLAY);
	}
	
	/**
	 * テーブル区分配列を取得する。<br>
	 * @param mospParams       MosP処理情報
	 * @param tableTypeCodeKey テーブル区分
	 * @param needBlank        空白行要否(true：空白行要、false：空白行不要)
	 * @return テーブル区分配列
	 */
	public static String[][] getTableTypeArray(MospParams mospParams, String tableTypeCodeKey, boolean needBlank) {
		// コードキーに対応するプルダウン用配列を取得
		String[][] codeArray = MospUtility.getCodeArray(mospParams, tableTypeCodeKey, needBlank);
		// テーブル区分配列リストを準備
		List<String[]> list = new ArrayList<String[]>();
		// プルダウン用配列毎に処理
		for (String[] array : codeArray) {
			// テーブル区分が利用可能である場合
			if (isTheTableTypeAvailable(mospParams, array[0])) {
				// テーブル区分配列リストに追加
				list.add(array);
			}
		}
		// テーブル区分配列を取得
		return list.toArray(new String[list.size()][]);
	}
	
	/**
	 * テーブル区分が利用可能であるかを確認する。<br>
	 * @param mospParams MosP処理情報
	 * @param tableType  テーブル区分
	 * @return 確認結果(true：利用可能、false：利用不可)
	 */
	protected static boolean isTheTableTypeAvailable(MospParams mospParams, String tableType) {
		// テーブル区分がnullである場合(空白は可)
		if (tableType == null) {
			// 利用不可であると判断
			return false;
		}
		// テーブル区分がユニット情報(所属)かユニット情報(個人)である場合
		if (tableType.equals(TABLE_TYPE_UNIT_SECTION) || tableType.equals(TABLE_TYPE_UNIT_PERSON)) {
			// 承認管理-ユニット一覧が利用できない場合
			if (isUnitListAvailable(mospParams) == false) {
				// 利用不可であると判断
				return false;
			}
		}
		// 利用可能であると判断
		return true;
	}
	
	/**
	 * 承認管理-ユニット一覧が利用できるかを確認する。<br>
	 * 対象メニューが有効であり、
	 * MosP処理情報に設定されたユーザが対象メニューを利用できるかで、判断する。<br>
	 * @param mospParams MosP処理情報
	 * @return 確認結果(true：承認管理-ユニット一覧利用可、false：不可)
	 */
	protected static boolean isUnitListAvailable(MospParams mospParams) {
		return MenuUtility.isTheMenuAvailable(mospParams, MAIN_MENU_WORKFLOW_MANAGE, MENU_UNIT_LIST);
	}
	
	/**
	 * 汎用フラグがOFFであるかを確認する。<br>
	 * @param flag 汎用フラグ
	 * @return 確認結果(true：汎用フラグがOFFである、false：OFFでない)
	 */
	public static boolean isFlagOff(int flag) {
		return flag == MospConst.FLAG_OFF;
	}
	
	/**
	 * 汎用フラグがONであるかを確認する。<br>
	 * @param flag 汎用フラグ
	 * @return 確認結果(true：汎用フラグがONである、false：ONでない)
	 */
	public static boolean isFlagOn(int flag) {
		return flag == MospConst.FLAG_ON;
	}
	
	/**
	 * 無効フラグが無効であるかを確認する。<br>
	 * @param inactivateFlag 無効フラグ
	 * @return 確認結果(true：無効フラグが無効である、false：そうでない)
	 */
	public static boolean isInactivate(int inactivateFlag) {
		return inactivateFlag == MospConst.INACTIVATE_FLAG_ON;
	}
	
	/**
	 * 無効フラグが有効であるかを確認する。<br>
	 * @param inactivateFlag 無効フラグ
	 * @return 確認結果(true：無効フラグが有効である、false：そうでない)
	 */
	public static boolean isActivate(int inactivateFlag) {
		return inactivateFlag == MospConst.INACTIVATE_FLAG_OFF;
	}
	
	/**
	 * 有効日モードが変更であるかを確認する。<br>
	 * @param modeActivateDate 有効日モード
	 * @return 確認結果(true：有効日モードが変更である、false：そうでない)
	 */
	public static boolean isActivateDateChanging(String modeActivateDate) {
		// 有効日モードが決定であるかを確認
		return MospUtility.isEqual(modeActivateDate, PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
	}
	
	/**
	 * ワークフロー情報がその機能であるかを確認する。<br>
	 * @param dto          ワークフロー情報
	 * @param functionCode 機能コード
	 * @return 確認結果(true：その機能である、false：そうでない)
	 */
	public static boolean isTheFunction(WorkflowDtoInterface dto, String functionCode) {
		// ワークフロー情報が存在しない場合
		if (MospUtility.isEmpty(dto)) {
			// ワークフロー情報がその機能でないと判断
			return false;
		}
		// ワークフロー情報がその機能であるかを確認
		return MospUtility.isEqual(dto.getFunctionCode(), functionCode);
	}
	
	/**
	 * DTOの無効フラグが有効に設定されているかを確認する。<br>
	 * @param dto 確認対象DTO
	 * @return 確認結果(true：有効、false：無効)
	 */
	public static boolean isDtoActivate(PlatformDtoInterface dto) {
		// 確認対象DTOが存在しない場合
		if (MospUtility.isEmpty(dto)) {
			// 無効であると判断
			return false;
		}
		// 無効フラグが有効に設定されているかを確認
		return dto.getInactivateFlag() == MospConst.INACTIVATE_FLAG_OFF;
	}
	
	/**
	 * DTOの削除フラグが削除に設定されているかを確認する。<br>
	 * @param dto 確認対象DTO
	 * @return 確認結果(true：削除されている、false：削除されていない)
	 */
	public static boolean isDtoDeleted(BaseDtoInterface dto) {
		// 確認対象DTOが存在しない場合
		if (MospUtility.isEmpty(dto)) {
			// 削除されていると判断
			return true;
		}
		// 削除フラグが削除に設定されているかを確認
		return dto.getDeleteFlag() == MospConst.DELETE_FLAG_ON;
	}
	
	/**
	 * 検索（前方一致）。
	 * @param condition 条件
	 * @param value 値
	 * @return 確認結果(true：前方一致、false：前方一致でない)
	 */
	public static boolean isForwardMatch(String condition, String value) {
		return value.startsWith(condition);
	}
	
	/**
	 * 検索（後方一致）。
	 * @param condition 条件
	 * @param value 値
	 * @return 確認結果(true：前方一致、false：前方一致でない)
	 */
	public static boolean isBackwardMatch(String condition, String value) {
		return value.endsWith(condition);
	}
	
	/**
	 * 検索（部分一致）。
	 * @param condition 条件
	 * @param value 値
	 * @return 確認結果(true：部分一致、false：部分一致でない)
	 */
	public static boolean isBroadMatch(String condition, String value) {
		return value.indexOf(condition) >= 0;
	}
	
	/**
	 * 検索（完全一致）。
	 * @param condition 条件
	 * @param value 値
	 * @return 確認結果(true：完全一致、false：完全一致でない)
	 */
	public static boolean isExactMatch(String condition, String value) {
		return value.equals(condition);
	}
	
	/**
	 * ファイル名(yyyymmdd-ファイル名)を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @param fileName   ファイル名
	 * @param targetDate 出力対象日
	 * @param isDate     年月日フラグ(true：出力対象日の年月日をファイル名に含める、false：日を含めない)
	 * @return ファイル名
	 */
	public static String getExportFileName(MospParams mospParams, String fileName, Date targetDate, boolean isDate) {
		// ファイル名を準備
		StringBuilder sb = new StringBuilder();
		// 出力対象日の年月を追加
		sb.append(DateUtility.getStringYear(targetDate)).append(DateUtility.getStringMonth(targetDate));
		// 出力対象日の年月日をファイル名に含める場合
		if (isDate) {
			// 出力対象日の日を追加
			sb.append(DateUtility.getStringDay(targetDate));
		}
		// ファイル名を作成
		sb.append(PfNameUtility.hyphen(mospParams));
		sb.append(fileName);
		// ファイル名を取得
		return sb.toString();
	}
	
	/**
	 * 出力ファイルの拡張子を取得する。<br>
	 * @param dto        エクスポート情報
	 * @return 出力ファイルの拡張子
	 */
	public static String getFileExtension(ExportDtoInterface dto) {
		// エクスポート情報が存在しない場合
		if (MospUtility.isEmpty(dto)) {
			// 空文字を取得
			return MospConst.STR_EMPTY;
		}
		// データ型がCSVである場合
		if (MospUtility.isEqual(PlatformFileConst.FILE_TYPE_CSV, dto.getType())) {
			// 拡張子(エクスポートファイル)を取得
			return PlatformFileConst.EXT_EXPORT_FILE;
		}
		// 空文字を取得
		return MospConst.STR_EMPTY;
	}
	
	/**
	 * CSVデータに値を設定する。<br>
	 * エクスポートフィールド名称リスト(フィールド順序昇順)からフィールド名称のインデックスを取得し、
	 * CSVデータの対象インデックスの箇所に値を設定する。<br>
	 * インデックスが取得できない場合は、値を設定しない。<br>
	 * @param csvData   CSVデータ(文字列の配列)
	 * @param fieldList エクスポートフィールド名称リスト(フィールド順序昇順)
	 * @param fieldName フィールド名称
	 * @param value     値
	 */
	public static void setCsvValue(String[] csvData, List<String> fieldList, String fieldName, Object value) {
		// 引数を確認
		if (MospUtility.isEmpty(csvData, fieldList, fieldName, value)) {
			// 処理無し
			return;
		}
		// エクスポートフィールド名称リスト(フィールド順序昇順)からフィールド名称のインデックスを取得
		int idx = fieldList.indexOf(fieldName);
		// インデックスが妥当でない場合
		if (MospUtility.isIndexValid(idx, csvData) == false) {
			// 処理無し
			return;
		}
		// CSVデータに値を設定
		csvData[idx] = MospUtility.getString(value);
	}
	
	/**
	 * エクスポートフィールド名称リストにフィールド名称が一つでも含まれているかを確認する。。<br>
	 * @param fieldList  エクスポートフィールド名称リスト(フィールド順序昇順)
	 * @param fieldNames フィールド名称(配列)
	 * @return 確認結果(true：一つでも含まれている、false：一つも含まれていない)
	 */
	public static boolean isTheFieldExit(List<String> fieldList, String... fieldNames) {
		// 引数を確認
		if (MospUtility.isEmpty(fieldList)) {
			// 一つも含まれていないと判断
			return false;
		}
		// フィールド名称毎に処理
		for (String fieldName : fieldNames) {
			// エクスポートフィールド名称リストからフィールド名称のインデックスを取得
			int idx = fieldList.indexOf(fieldName);
			// インデックスが妥当である場合
			if (MospUtility.isIndexValid(idx)) {
				// 一つでも含まれていると判断
				return true;
			}
		}
		// 一つも含まれていないと判断
		return false;
	}
	
	/**
	 * 有効日編集中のプルダウンを取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 有効日編集中プルダウン
	 */
	public static String[][] getInputActivateDatePulldown(MospParams mospParams) {
		String[][] aryPulldown = { { MospConst.STR_EMPTY, PfNameUtility.inputActiveDate(mospParams) } };
		return aryPulldown;
	}
	
	/**
	 * クラス名(CSS)を取得する。<br>
	 * どちらもtrueの場合は、赤字となる。<br>
	 * @param isBlue 青字フラグ(true：青字、false：青字でない)
	 * @param isRed  赤字フラグ(true：赤字、false：赤字でない)
	 * @return クラス名(CSS)
	 */
	public static String getRedOrBlueClassName(boolean isBlue, boolean isRed) {
		// 赤字である場合
		if (isRed) {
			// クラス(CSS)文字列(フォント：赤)を取得
			return PlatformConst.CLS_FONT_RED;
		}
		// 青字である場合
		if (isBlue) {
			// クラス(CSS)文字列(フォント：青)を取得
			return PlatformConst.CLS_FONT_BLUE;
		}
		// 空文字を取得
		return MospConst.STR_EMPTY;
	}
	
	/**
	 * DTOを複製する。<br>
	 * 複製基のDTOが存在しない場合は、nullを取得する。<br>
	 * <br>
	 * 複製基のDTOから、次の値を引き継ぐ。<br>
	 * ・削除フラグ<br>
	 * ・登録日<br>
	 * ・登録者<br>
	 * ・更新日<br>
	 * ・更新者<br>
	 * <br>
	 * @param dto 複製基のDTO
	 * @return DTO(複製)
	 * @throws MospException クラスのインスタンスの生成に失敗した場合
	 */
	public static <T extends BaseDtoInterface> T cloneBaseDto(T dto) throws MospException {
		// 複製基のDTOが存在しない場合
		if (MospUtility.isEmpty(dto)) {
			// nullを取得
			return null;
		}
		// DTO(複製)を作成
		T clone = castObject(InstanceFactory.getNewInstance(dto.getClass()));
		clone.setDeleteFlag(dto.getDeleteFlag());
		clone.setInsertDate(dto.getInsertDate());
		clone.setInsertUser(dto.getInsertUser());
		clone.setUpdateDate(dto.getUpdateDate());
		clone.setUpdateUser(dto.getUpdateUser());
		return clone;
	}
	
	/**
	 * 戻り値の型に合わせてキャストする。<br>
	 * 呼び出し元からアドオンへ値を引き継きつぐ時に使用することを想定
	 * @param obj オブジェクト
	 * @return castObject オブジェクトをキャストしたもの
	 */
	public static <T> T castObject(Object obj) {
		// 戻り値の型に合わせてキャスト
		return MospUtility.castObject(obj);
	}
	
	/**
	 * 戻り値の型に合わせてキャストする。<br>
	 * 呼び出し元からアドオンへ値を引き継きつぐ時に使用することを想定
	 * @param list リスト
	 * @return castObject オブジェクトをキャストしたもの
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> castList(List<?> list) {
		// 新しいリストを準備
		List<T> newList = new ArrayList<T>();
		// VOに設定されている要素毎に処理
		for (Object obj : list) {
			// キャストしてリストに追加
			newList.add((T)obj);
		}
		// リストを取得
		return newList;
	}
	
}
