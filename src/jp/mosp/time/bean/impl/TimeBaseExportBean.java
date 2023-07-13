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
package jp.mosp.time.bean.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.orangesignal.OrangeSignalUtility;
import jp.mosp.platform.bean.file.impl.BaseExportBean;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dto.file.ExportDtoInterface;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.time.bean.TimeExportBeanInterface;
import jp.mosp.time.bean.TimeMasterBeanInterface;
import jp.mosp.time.entity.ApplicationEntity;
import jp.mosp.time.entity.CutoffEntityInterface;
import jp.mosp.time.utils.TimeUtility;

/**
 * エクスポート処理(勤怠管理)。<br>
 * エクスポート処理で必要となる機能を提供する。<br>
 */
public abstract class TimeBaseExportBean extends BaseExportBean implements TimeExportBeanInterface {
	
	/**
	 * 勤怠関連マスタ参照処理。<br>
	 */
	protected TimeMasterBeanInterface timeMaster;
	
	
	@Override
	public void initBean() throws MospException {
		// 継承基のメソッドを実行
		super.initBean();
		// Beanの準備
		timeMaster = createBeanInstance(TimeMasterBeanInterface.class);
		// プラットフォームマスタ参照処理を設定
		timeMaster.setPlatformMaster(platformMaster);
	}
	
	@Override
	public void export(String exportCode, int startYear, int startMonth, int endYear, int endMonth, String cutoffCode,
			String workPlaceCode, String employmentContractCode, String sectionCode, boolean needLowerSection,
			String positionCode) throws MospException {
		// エクスポートマスタ情報を取得
		ExportDtoInterface exportDto = exportDao.findForKey(exportCode);
		// エクスポートマスタ情報が取得できなかった場合
		if (exportDto == null) {
			// エラーメッセージを設定
			PfMessageUtility.addErrorNoExportInfo(mospParams);
			// 処理終了
			return;
		}
		// エクスポートフィールド名称リスト(フィールド順序昇順)を取得
		List<String> fieldList = exportFieldRefer.getExportFieldNameList(exportCode);
		// 締期間初日及び最終日(出力期間初日及び最終日)を取得
		Date firstDate = getCutoffFirstDate(cutoffCode, startYear, startMonth);
		Date lastDate = getCutoffLastDate(cutoffCode, endYear, endMonth);
		// CSVデータリストを作成
		List<String[]> csvDataList = makeCsvDataList(fieldList, firstDate, lastDate, cutoffCode, workPlaceCode,
				employmentContractCode, sectionCode, needLowerSection, positionCode);
		// 該当するデータが存在しない場合
		if (csvDataList.isEmpty()) {
			// エラーメッセージを設定
			PfMessageUtility.addErrorNoExportInfo(mospParams);
			// 処理終了
			return;
		}
		// CSVデータリストにヘッダを付加
		addHeader(csvDataList, exportDto, fieldList, lastDate);
		// CSVデータリストをMosP処理情報に設定
		mospParams.setFile(OrangeSignalUtility.getOrangeSignalParams(csvDataList));
		// ファイル名をMosP処理情報に設定
		mospParams.setFileName(getFileName(exportDto, firstDate, lastDate));
	}
	
	/**
	 * 送出ファイル名を取得する。<br>
	 * @param dto        エクスポート情報
	 * @param firstDate  出力期間初日
	 * @param lastDate   出力期間最終日
	 * @return 送出ファイル名
	 */
	protected String getFileName(ExportDtoInterface dto, Date firstDate, Date lastDate) {
		// 送出ファイル名を取得
		return TimeUtility.getExportFileName(mospParams, dto, firstDate, lastDate);
	}
	
	/**
	 * CSVデータリストを作成する。<br>
	 * <br>
	 * 各エクスポート処理で実装する。<br>
	 * <br>
	 * @param fieldList              エクスポートフィールド名称リスト(フィールド順序昇順)
	 * @param firstDate              出力期間初日
	 * @param lastDate               出力期間最終日
	 * @param cutoffCode             締日コード
	 * @param workPlaceCode          勤務地コード
	 * @param employmentContractCode 雇用契約コード
	 * @param sectionCode            所属コード
	 * @param needLowerSection       下位所属含む
	 * @param positionCode           職位コード
	 * @return CSVデータリスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected List<String[]> makeCsvDataList(List<String> fieldList, Date firstDate, Date lastDate, String cutoffCode,
			String workPlaceCode, String employmentContractCode, String sectionCode, boolean needLowerSection,
			String positionCode) throws MospException {
		// 処理無し(各エクスポート処理で実装)
		return Collections.emptyList();
	}
	
	/**
	 * 対象年月及び締日から締期間初日を取得する。<br>
	 * @param cutoffCode  締日コード
	 * @param targetYear  対象年
	 * @param targetMonth 対象月
	 * @return 締期間初日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected Date getCutoffFirstDate(String cutoffCode, int targetYear, int targetMonth) throws MospException {
		// 締日エンティティを取得
		CutoffEntityInterface cutoff = timeMaster.getCutoff(cutoffCode, targetYear, targetMonth);
		// 締期間初日を取得
		return cutoff.getCutoffFirstDate(targetYear, targetMonth, mospParams);
	}
	
	/**
	 * 対象年月及び締日から締期間最終日を取得する。<br>
	 * @param cutoffCode  締日コード
	 * @param targetYear  対象年
	 * @param targetMonth 対象月
	 * @return 締期間最終日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected Date getCutoffLastDate(String cutoffCode, int targetYear, int targetMonth) throws MospException {
		// 締日エンティティを取得
		CutoffEntityInterface cutoff = timeMaster.getCutoff(cutoffCode, targetYear, targetMonth);
		// 締期間最終日を取得
		return cutoff.getCutoffLastDate(targetYear, targetMonth, mospParams);
	}
	
	/**
	 * 対象人事情報の対象日における締日コードが合致するかを確認する。<br>
	 * @param human      人事情報
	 * @param targetDate 対象日
	 * @param cutoffCode 締日コード
	 * @return 確認結果(true：対象人事情報の対象日における締日コードが合致する、false：合致しない)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected boolean isTheCutoff(HumanDtoInterface human, Date targetDate, String cutoffCode) throws MospException {
		// 設定適用エンティティを取得
		ApplicationEntity application = timeMaster.getApplicationEntity(human, targetDate);
		// 設定適用エンティティが有効でない場合
		if (application.isValid() == false) {
			// 合致しないと判断
			return false;
		}
		// 対象人事情報の対象日における締日コードが合致するかを確認
		return MospUtility.isEqual(cutoffCode, application.getCutoffCode());
	}
	
	/**
	 * 対象日における締日コードが合致する人事情報リストを取得する。<br>
	 * 並び順は、引数の人事情報リストのまま。<br>
	 * @param dtos       人事情報リスト
	 * @param targetDate 対象日
	 * @param cutoffCode 締日コード
	 * @return 対象日における締日コードが合致する人事情報群(キー：個人ID)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected List<HumanDtoInterface> getCutoffHumans(List<HumanDtoInterface> dtos, Date targetDate, String cutoffCode)
			throws MospException {
		// 人事情報リストを準備
		List<HumanDtoInterface> cutoffHumans = new ArrayList<HumanDtoInterface>();
		// 人事情報毎に処理
		for (HumanDtoInterface dto : dtos) {
			// 対象人事情報の対象日における締日コードが合致する場合
			if (isTheCutoff(dto, targetDate, cutoffCode)) {
				// 人事情報リストに設定
				cutoffHumans.add(dto);
			}
		}
		// 人事情報リストを取得
		return cutoffHumans;
	}
	
	/**
	 * 人事情報リスト(在職者社員コード順+休職者社員コード順)を取得する。<br>
	 * 在職者及び休職者を対象とする。<br>
	 * @param firstDate              出力期間初日
	 * @param lastDate               出力期間最終日
	 * @param cutoffCode             締日コード
	 * @param workPlaceCode          勤務地コード
	 * @param employmentContractCode 雇用契約コード
	 * @param sectionCode            所属コード
	 * @param needLowerSection       下位所属含む
	 * @param positionCode           職位コード
	 * @return 人事情報群(キー：個人ID)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected List<HumanDtoInterface> getHumanList(Date firstDate, Date lastDate, String cutoffCode,
			String workPlaceCode, String employmentContractCode, String sectionCode, boolean needLowerSection,
			String positionCode) throws MospException {
		// 人事情報リストを準備(在職者及び休職者)
		List<HumanDtoInterface> dtos = new ArrayList<HumanDtoInterface>();
		// 人事情報検索条件を設定
		humanSearch.setStartDate(firstDate);
		humanSearch.setEndDate(lastDate);
		humanSearch.setTargetDate(lastDate);
		humanSearch.setWorkPlaceCode(workPlaceCode);
		humanSearch.setEmploymentContractCode(employmentContractCode);
		humanSearch.setSectionCode(sectionCode);
		humanSearch.setNeedLowerSection(needLowerSection);
		humanSearch.setPositionCode(positionCode);
		// 検索条件設定(兼務要否)
		humanSearch.setNeedConcurrent(true);
		// 検索条件設定(操作区分)
		humanSearch.setOperationType(MospConst.OPERATION_TYPE_REFER);
		// 検索条件設定(状態：在職)
		humanSearch.setStateType(PlatformConst.EMPLOYEE_STATE_PRESENCE);
		// 人事情報を取得(在職者)
		dtos.addAll(humanSearch.search());
		// 検索条件設定(状態：休職)
		humanSearch.setStateType(PlatformConst.EMPLOYEE_STATE_SUSPEND);
		// 人事情報を取得(休職者)
		dtos.addAll(humanSearch.search());
		// 人事情報群(キー：個人ID)を取得
		return getCutoffHumans(dtos, lastDate, cutoffCode);
	}
	
	/**
	 * 人事情報リスト(社員コード順)を取得する。<br>
	 * 人事情報を取得するにあたり、期間は指定せず、退職者を対象に含める。<br>
	 * @param targetDate             対象日
	 * @param cutoffCode             締日コード
	 * @param workPlaceCode          勤務地コード
	 * @param employmentContractCode 雇用契約コード
	 * @param sectionCode            所属コード
	 * @param needLowerSection       下位所属含む
	 * @param positionCode           職位コード
	 * @return 人事情報群(キー：個人ID)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected List<HumanDtoInterface> getHumanList(Date targetDate, String cutoffCode, String workPlaceCode,
			String employmentContractCode, String sectionCode, boolean needLowerSection, String positionCode)
			throws MospException {
		// 人事情報検索条件を設定
		humanSearch.setTargetDate(targetDate);
		humanSearch.setWorkPlaceCode(workPlaceCode);
		humanSearch.setEmploymentContractCode(employmentContractCode);
		humanSearch.setSectionCode(sectionCode);
		humanSearch.setNeedLowerSection(needLowerSection);
		humanSearch.setPositionCode(positionCode);
		// 検索条件設定(状態：指定なし(退職者を含む))
		humanSearch.setStateType(MospConst.STR_EMPTY);
		// 検索条件設定(兼務要否)
		humanSearch.setNeedConcurrent(true);
		// 検索条件設定(操作区分)
		humanSearch.setOperationType(MospConst.OPERATION_TYPE_REFER);
		// 人事情報群(キー：個人ID)を取得
		return getCutoffHumans(humanSearch.search(), targetDate, cutoffCode);
	}
	
}
