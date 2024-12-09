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
package jp.mosp.platform.human.utils;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.BinaryUtility;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.dto.human.HumanHistoryDtoInterface;
import jp.mosp.platform.dto.human.HumanNormalDtoInterface;
import jp.mosp.platform.dto.human.RetirementDtoInterface;
import jp.mosp.platform.dto.human.SuspensionDtoInterface;
import jp.mosp.platform.dto.system.EmploymentContractDtoInterface;
import jp.mosp.platform.dto.system.PositionDtoInterface;
import jp.mosp.platform.dto.system.SectionDtoInterface;
import jp.mosp.platform.dto.system.WorkPlaceDtoInterface;
import jp.mosp.platform.utils.PfNameUtility;

/**
 * 人事情報における有用なメソッドを提供する。<br>
 */
public class HumanUtility {
	
	/**
	 * 他クラスからのインスタンス化を防止する。<br>
	 */
	private HumanUtility() {
		// 処理無し
	}
	
	/**
	 * 人事情報履歴一覧の中での対象人事履歴DTOのインデックスを取得する。
	 * @param dto 対象DTO
	 * @param list 履歴一覧
	 * @return index 対象DTOのインデックス
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	public static int getHumanTargetIndex(HumanDtoInterface dto, List<HumanDtoInterface> list) throws MospException {
		int index = 0;
		//iでまわす
		for (int i = 0; i < list.size(); i++) {
			if (dto.getPfmHumanId() == (list.get(i).getPfmHumanId())) {
				index = i;
			}
		}
		return index;
	}
	
	/**
	 * 経過月から経過年月を取得する。
	 * 入社勤続年数に表示する。
	 * @param amountMonth 経過月
	 * @param mospParams MosP処理情報
	 * @return ○年○ヶ月
	 */
	public static String getDuration(int amountMonth, MospParams mospParams) {
		// 経過年月取得
		int countYear = amountMonth / 12;
		int countMonth = amountMonth % 12;
		// 年がマイナスの場合
		if (countYear < 0) {
			// 0に変換
			countYear = 0;
		}
		// 月がマイナスの場合
		if (countMonth < 0) {
			// 0に変換
			countMonth = 0;
		}
		// 文字列を取得
		StringBuilder sb = new StringBuilder();
		sb.append(countYear).append(PfNameUtility.year(mospParams));
		sb.append(countMonth).append(PfNameUtility.amountMonth(mospParams));
		return sb.toString();
	}
	
	/**
	 * 誕生日から今日までで何歳かを取得する。
	 * @param birthDate  誕生日
	 * @param targetDate 対象日
	 * @param mospParams MosP処理情報
	 * @return ○歳
	 */
	public static String getHumanOlder(Date birthDate, Date targetDate, MospParams mospParams) {
		// 誕生日か対象日が存在しない場合
		if (MospUtility.isEmpty(birthDate, targetDate)) {
			return MospConst.STR_EMPTY;
		}
		// 経過月を取得
		int amountMonth = DateUtility.getMonthDifference(birthDate, targetDate);
		// 文字列を取得
		StringBuilder sb = new StringBuilder();
		sb.append(amountMonth / 12).append(PfNameUtility.yearsOld(mospParams));
		return PfNameUtility.parentheses(mospParams, sb.toString());
	}
	
	/**
	 * 人事情報履歴と対象勤務地情報から勤務地名と継続月数を取得する。
	 * @param dto 対象人事情報履歴
	 * @param humanList 対象人事情報履歴一覧
	 * @param targetDate 対象日
	 * @param workPlaceDto 勤務地情報
	 * @param retirementDto 退職情報
	 * @param mospParams MosP処理情報
	 * @return 勤務地名(○年○ヶ月)
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	public static String getWorkPlaceStayMonths(HumanDtoInterface dto, List<HumanDtoInterface> humanList,
			Date targetDate, WorkPlaceDtoInterface workPlaceDto, RetirementDtoInterface retirementDto,
			MospParams mospParams) throws MospException {
		// 勤務地情報かつ勤務地コード確認
		if (workPlaceDto == null || dto.getWorkPlaceCode().isEmpty()) {
			return "";
		}
		// 勤務地コード取得
		String workPlaceCode = dto.getWorkPlaceCode();
		// 対象人事履歴DTOのインデックスを取得
		int index = getHumanTargetIndex(dto, humanList);
		// 初日を準備
		Date firstDate = dto.getActivateDate();
		// 人事情報一覧毎に履歴をさかのぼる
		for (int i = index - 1; i >= 0; i--) {
			// 人事情報を取得
			HumanDtoInterface firstDto = humanList.get(i);
			// 勤務地コード比較
			if (firstDto.getWorkPlaceCode().equals(workPlaceCode) == false) {
				break;
			}
			// 初日設定
			firstDate = firstDto.getActivateDate();
		}
		// 経過月取得
		return getDurationName(workPlaceDto.getWorkPlaceName(), targetDate, firstDate, retirementDto, mospParams);
	}
	
	/**
	 * 人事情報履歴と対象所属情報から所属名と継続月数を取得する。
	 * @param dto 対象人事情報履歴
	 * @param humanList 対象人事情報履歴一覧
	 * @param targetDate 対象日
	 * @param sectionDto 対象所属情報
	 * @param retirementDto 退職情報
	 * @param mospParams MosP処理情報
	 * @return 所属名(○年○ヶ月)
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	public static String getSectionStayMonths(HumanDtoInterface dto, List<HumanDtoInterface> humanList, Date targetDate,
			SectionDtoInterface sectionDto, RetirementDtoInterface retirementDto, MospParams mospParams)
			throws MospException {
		// 所属情報かつ所属コード確認
		if (sectionDto == null || dto.getSectionCode().isEmpty()) {
			return "";
		}
		// 所属コード取得
		String sectionCode = dto.getSectionCode();
		// 所属コード確認
		if (sectionCode.isEmpty()) {
			return "";
		}
		// 対象人事履歴DTOのインデックスを取得
		int index = getHumanTargetIndex(dto, humanList);
		// 初日を準備
		Date firstDate = dto.getActivateDate();
		// 人事情報一覧毎に履歴をさかのぼる
		for (int i = index; i >= 0; i--) {
			// 人事情報を取得
			HumanDtoInterface firstDto = humanList.get(i);
			// 所属コード比較
			if (firstDto.getSectionCode().equals(sectionCode) == false) {
				break;
			}
			// 初日設定
			firstDate = firstDto.getActivateDate();
		}
		// 経過月取得
		
		return getDurationName(sectionDto.getSectionName(), targetDate, firstDate, retirementDto, mospParams);
	}
	
	/**
	 * 人事情報履歴と対象職位情報から職位名と継続月数を取得する。
	 * @param dto 対象人事情報履歴
	 * @param humanList 対象人事情報履歴一覧
	 * @param targetDate 対象日
	 * @param mospParams MosP処理情報
	 * @param positionDto 職位情報
	 * @param retirementDto 退職情報
	 * @return 職位名(○年○ヶ月)
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	public static String getPositionStayMonths(HumanDtoInterface dto, List<HumanDtoInterface> humanList,
			Date targetDate, PositionDtoInterface positionDto, RetirementDtoInterface retirementDto,
			MospParams mospParams) throws MospException {
		// 職位情報かつ職位コード確認
		if (positionDto == null || dto.getPositionCode().isEmpty()) {
			return "";
		}
		// 職位コード取得
		String positionCode = dto.getPositionCode();
		// 職位コード確認
		if (positionCode.isEmpty()) {
			return "";
		}
		// 対象人事履歴DTOのインデックスを取得
		int index = getHumanTargetIndex(dto, humanList);
		// 初日を準備
		Date firstDate = dto.getActivateDate();
		// 人事情報一覧毎に履歴をさかのぼる
		for (int i = index; i >= 0; i--) {
			// 人事情報を取得
			HumanDtoInterface firstDto = humanList.get(i);
			// 職位コード比較
			if (firstDto.getPositionCode().equals(positionCode) == false) {
				break;
			}
			// 初日設定
			firstDate = firstDto.getActivateDate();
		}
		// 経過月取得
		return getDurationName(positionDto.getPositionName(), targetDate, firstDate, retirementDto, mospParams);
	}
	
	/**
	 * 人事情報履歴と対象雇用契約情報から雇用契約名と継続月数を取得する。
	 * @param dto 対象人事情報履歴
	 * @param humanList 対象人事情報履歴一覧
	 * @param targetDate 対象日
	 * @param employmentContractDto 雇用契約情報
	 * @param retirementDto 退職情報
	 * @param mospParams MosP処理情報
	 * @return 雇用契約名(○年○ヶ月)
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	public static String getEmploymentStayMonths(HumanDtoInterface dto, List<HumanDtoInterface> humanList,
			Date targetDate, EmploymentContractDtoInterface employmentContractDto, RetirementDtoInterface retirementDto,
			MospParams mospParams) throws MospException {
		// 雇用契約情報かつ雇用契約コード確認
		if (employmentContractDto == null || dto.getEmploymentContractCode().isEmpty()) {
			return "";
		}
		// 雇用契約コード取得
		String employmentCode = dto.getEmploymentContractCode();
		// 対象人事履歴DTOのインデックスを取得
		int index = getHumanTargetIndex(dto, humanList);
		// 初日を準備
		Date firstDate = dto.getActivateDate();
		// 人事情報一覧毎に履歴をさかのぼる
		for (int i = index; i >= 0; i--) {
			// 人事情報を取得
			HumanDtoInterface firstDto = humanList.get(i);
			// 雇用契約コード比較
			if (firstDto.getEmploymentContractCode().equals(employmentCode) == false) {
				break;
			}
			// 初日設定
			firstDate = firstDto.getActivateDate();
		}
		// 経過月取得
		return getDurationName(employmentContractDto.getEmploymentContractName(), targetDate, firstDate, retirementDto,
				mospParams);
	}
	
	/**
	 * 人事情報履歴と対象役職情報から役職名と継続月数を取得する。
	 * @param dto 対象人事情報履歴
	 * @param humanList 対象人事情報履歴一覧
	 * @param targetDate 対象日
	 * @param postList 役職情報リスト
	 * @param postDto 対象役職情報
	 * @param retirementDto 退職情報
	 * @param mospParams MosP処理情報リスト
	 * @param name 役職名称
	 * @return 役職名(○年○ヶ月)
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	public static String getPostStayMonths(HumanDtoInterface dto, List<HumanDtoInterface> humanList, Date targetDate,
			List<HumanHistoryDtoInterface> postList, HumanHistoryDtoInterface postDto,
			RetirementDtoInterface retirementDto, MospParams mospParams, String name) throws MospException {
		// 役職情報かつ役職情報リスト確認
		if (postDto == null || postList.isEmpty()) {
			return "";
		}
		// 役職コード取得
		String postCode = postDto.getHumanItemValue();
		// 対象人事履歴DTOのインデックスを取得
		int index = HumanUtility.getHumanTargetIndex(dto, humanList);
		// 初日を準備
		Date firstDate = dto.getActivateDate();
		// 人事情報一覧毎に履歴をさかのぼる
		for (int i = index - 1; i >= 0; i--) {
			// 人事情報を取得
			HumanDtoInterface firstDto = humanList.get(i);
			// 対象役職情報インデックス取得
			int postIndex = getTargetIndexPost(postList, firstDto.getActivateDate());
			if (postIndex == -1) {
				break;
			}
			// 対象役職情報取得
			HumanHistoryDtoInterface targetPostDto = postList.get(postIndex);
			// 役職情報が違う場合
			if (targetPostDto.getHumanItemValue().equals(postCode) == false) {
				break;
			}
			// 初日設定
			firstDate = firstDto.getActivateDate();
		}
		// 経過月取得
		return getDurationName(name, targetDate, firstDate, retirementDto, mospParams);
		
	}
	
	/**
	 * 役職情報リストの中で同じ有効日情報が存在する場合、<br>
	 * 対象インデックスを取得する。<br>
	 * 有効日情報が存在しない場合は、-1を返す。
	 * @param postList 役職情報リスト
	 * @param activeDate 対象有効日
	 * @return 役職情報対象インデックス
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	public static int getTargetIndexPost(List<HumanHistoryDtoInterface> postList, Date activeDate)
			throws MospException {
		// 役職インデックス取得
		int postIndex = -1;
		// 役職情報リスト毎に処理
		for (int i = 0; i < postList.size(); i++) {
			// 対象役職情報取得
			HumanHistoryDtoInterface dto = postList.get(i);
			// 有効日が同じ場合
			if (dto.getActivateDate().equals(activeDate)) {
				return i;
			}
		}
		return postIndex;
	}
	
	/**
	 * 継続月数を取得する。<br>
	 * 退職日を考慮し、表示日付が退職日より先の場合
	 * 退職日までの継続月数を取得する。<br>
	 * @param showDate 表示日付
	 * @param targetDate 対象情報有効日
	 * @param retirementDto 退職情報
	 * @param mospParams MosP処理情報
	 * @return ○年○ヶ月
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	public static String getContinuationMonthName(Date showDate, Date targetDate, RetirementDtoInterface retirementDto,
			MospParams mospParams) throws MospException {
		// 経過月終了日
		Date lastDate = showDate;
		// 退職情報がある場合
		if (retirementDto != null) {
			// システム日付が退職日より先の場合
			if (lastDate.compareTo(retirementDto.getRetirementDate()) > 0) {
				// 退職日設定
				lastDate = retirementDto.getRetirementDate();
			}
		}
		// 経過月取得
		int amountMonth = DateUtility.getMonthDifference(targetDate, lastDate);
		return getDuration(amountMonth, mospParams);
	}
	
	/**
	 * 名称・継続月数から名称(○年○ヶ月)を取得する。
	 * @param name 名称
	 * @param showDate 表示日付
	 * @param targetDate 対象情報有効日
	 * @param retirementDto 退職情報
	 * @param mospParams MosP処理情報
	 * @return 名称(○年○ヶ月)
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	public static String getDurationName(String name, Date showDate, Date targetDate,
			RetirementDtoInterface retirementDto, MospParams mospParams) throws MospException {
		// 文字列を準備
		StringBuffer sb = new StringBuffer(name);
		sb.append(PfNameUtility.parentheses(mospParams,
				getContinuationMonthName(showDate, targetDate, retirementDto, mospParams)));
		// 文字列を取得
		return sb.toString();
	}
	
	/**
	 * 人事汎用バイナリファイル拡張子コードを取得する。<br>
	 * ファイル名の拡張子から、判断する。<br>
	 * @param fileName ファイル名
	 * @return 人事汎用バイナリファイル拡張子コード
	 */
	public static String getBinaryFileType(String fileName) {
		// 拡張子がgifの場合
		if (BinaryUtility.isExtensionGif(fileName)) {
			return MospConst.CODE_BINARY_FILE_GIF;
		}
		// 拡張子がpngの場合
		if (BinaryUtility.isExtensionPng(fileName)) {
			return MospConst.CODE_BINARY_FILE_PNG;
		}
		// 拡張子がjpg/jpegの場合
		if (BinaryUtility.isExtensionJpg(fileName)) {
			return MospConst.CODE_BINARY_FILE_JPEG;
		}
		// それ以外の場合
		return MospConst.CODE_BINARY_FILE_FILE;
	}
	
	/**
	 * 対象日が休職中であるかを確認する。<br>
	 * <br>
	 * @param suspensions 休職情報群
	 * @param targetDate  対象日
	 * @return 確認結果(true：休職中である、false：休職中でない)
	 */
	public static boolean isSuspension(Collection<SuspensionDtoInterface> suspensions, Date targetDate) {
		// 対象日が休職中であるかを確認
		return MospUtility.isEmpty(getSuspension(suspensions, targetDate)) == false;
	}
	
	/**
	 * 休職理由を取得する。<br>
	 * <br>
	 * @param suspensions 休職情報群
	 * @param targetDate  対象日
	 * @return 確認結果(true：休職中である、false：休職中でない)
	 */
	public static String getSuspensionReason(Collection<SuspensionDtoInterface> suspensions, Date targetDate) {
		// 対象日が含まれる休職情報を取得
		SuspensionDtoInterface dto = getSuspension(suspensions, targetDate);
		// 休職中でない場合
		if (MospUtility.isEmpty(dto)) {
			// 空文字を取得
			return MospConst.STR_EMPTY;
		}
		// 休職理由を取得
		return dto.getSuspensionReason();
	}
	
	/**
	 * 対象日が含まれる休職情報を取得する。<br>
	 * 休職中でない場合は、nullを取得する。<br>
	 * @param suspensions 休職情報群
	 * @param targetDate  対象日
	 * @return 対象日が含まれる休職情報
	 */
	protected static SuspensionDtoInterface getSuspension(Collection<SuspensionDtoInterface> suspensions,
			Date targetDate) {
		// 休職リストがない場合
		if (MospUtility.isEmpty(suspensions)) {
			// nullを取得
			return null;
		}
		// 休職情報毎に処理
		for (SuspensionDtoInterface suspensionDto : suspensions) {
			// 期間開始日終了日取得
			Date startDate = suspensionDto.getStartDate();
			Date endDate = suspensionDto.getEndDate();
			// 期間終了日がない場合
			if (endDate == null) {
				// 休職予定終了日設定
				endDate = suspensionDto.getScheduleEndDate();
			}
			// 休職期間に含まれている場合
			if (DateUtility.isTermContain(targetDate, startDate, endDate)) {
				// 休職情報を取得
				return suspensionDto;
			}
		}
		// nullを取得
		return null;
	}
	
	/**
	 * 退職日を取得する。<br>
	 * 取得でなかった場合は、nullを取得する。<br>
	 * @param dto 人事退職情報
	 * @return 退職日
	 */
	public static Date getRetireDate(RetirementDtoInterface dto) {
		// 人事退職情報がnullである場合
		if (MospUtility.isEmpty(dto)) {
			// nullを取得
			return null;
		}
		// 退職日を取得
		return dto.getRetirementDate();
	}
	
	/**
	 * 対象日時点で退職しているかを確認する。<br>
	 * <br>
	 * @param dto        人事退職情報
	 * @param targetDate 対象日
	 * @return 確認結果(true：退職している、false：退職していない)
	 */
	public static boolean isRetired(RetirementDtoInterface dto, Date targetDate) {
		// 対象日時点で退職しているかを確認
		return isRetired(getRetireDate(dto), targetDate);
	}
	
	/**
	 * 対象日時点で退職しているかを確認する。<br>
	 * <br>
	 * @param retireDate 退職日
	 * @param targetDate 対象日
	 * @return 確認結果(true：退職している、false：退職していない)
	 */
	public static boolean isRetired(Date retireDate, Date targetDate) {
		// 人事退職情報か対象日がnullである場合
		if (MospUtility.isEmpty(retireDate, targetDate)) {
			// 退職していないと判断
			return false;
		}
		// 対象日時点で退職しているかを確認
		return targetDate.compareTo(retireDate) > 0;
	}
	
	/**
	 * 人事汎用通常情報から人事汎用通常項目値を取得する。<br>
	 * 人事汎用通常情報が存在しない場合、空文字を取得する。<br>
	 * @param dto 人事汎用通常情報
	 * @return 人事汎用通常項目値
	 */
	public static String getHumanItemValue(HumanNormalDtoInterface dto) {
		// 人事汎用通常項目値を取得
		return MospUtility.isEmpty(dto) ? MospConst.STR_EMPTY : dto.getHumanItemValue();
	}
}
