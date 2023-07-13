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
package jp.mosp.platform.bean.file.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.orangesignal.OrangeSignalUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.file.ExportBeanInterface;
import jp.mosp.platform.bean.file.ExportFieldReferenceBeanInterface;
import jp.mosp.platform.bean.human.HumanSearchBeanInterface;
import jp.mosp.platform.bean.system.PlatformMasterBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.constant.PlatformFileConst;
import jp.mosp.platform.dao.file.ExportDaoInterface;
import jp.mosp.platform.dto.file.ExportDtoInterface;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.platform.utils.PfNameUtility;
import jp.mosp.platform.utils.PlatformUtility;

/**
 * エクスポート処理。<br>
 * エクスポート処理で必要となる機能を提供する。<br>
 */
public abstract class BaseExportBean extends PlatformBean implements ExportBeanInterface {
	
	/**
	 * エクスポートマスタDAO。<br>
	 */
	protected ExportDaoInterface				exportDao;
	
	/**
	 * エクスポートフィールド参照処理。<br>
	 */
	protected ExportFieldReferenceBeanInterface	exportFieldRefer;
	
	/**
	 * 人事情報検索処理。<br>
	 */
	protected HumanSearchBeanInterface			humanSearch;
	
	/**
	 * プラットフォームマスタ参照処理。<br>
	 */
	protected PlatformMasterBeanInterface		platformMaster;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public BaseExportBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// DAO及びBeanを準備
		exportDao = createDaoInstance(ExportDaoInterface.class);
		exportFieldRefer = createBeanInstance(ExportFieldReferenceBeanInterface.class);
		humanSearch = createBeanInstance(HumanSearchBeanInterface.class);
		platformMaster = createBeanInstance(PlatformMasterBeanInterface.class);
	}
	
	@Override
	public void export(String exportCode, Date targetDate, String workPlaceCode, String employmentContractCode,
			String sectionCode, String positionCode) throws MospException {
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
		// CSVデータリストを作成
		List<String[]> csvDataList = makeCsvDataList(fieldList, targetDate, workPlaceCode, employmentContractCode,
				sectionCode, positionCode);
		// 該当するデータが存在しない場合
		if (csvDataList.isEmpty()) {
			// エラーメッセージを設定
			PfMessageUtility.addErrorNoExportInfo(mospParams);
			// 処理終了
			return;
		}
		// CSVデータリストにヘッダを付加
		addHeader(csvDataList, exportDto, fieldList, targetDate);
		// CSVデータリストをMosP処理情報に設定
		mospParams.setFile(OrangeSignalUtility.getOrangeSignalParams(csvDataList));
		// 送出ファイル名をMosP処理情報に設定
		setFileName(exportDto, targetDate);
	}
	
	/**
	 * CSVデータリストを作成する。<br>
	 * <br>
	 * 各エクスポート処理で実装する。<br>
	 * <br>
	 * @param fieldList              エクスポートフィールド名称リスト(フィールド順序昇順)
	 * @param targetDate             対象日
	 * @param workPlaceCode          勤務地コード
	 * @param employmentContractCode 雇用契約コード
	 * @param sectionCode            所属コード
	 * @param positionCode           職位コード
	 * @return CSVデータリスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected List<String[]> makeCsvDataList(List<String> fieldList, Date targetDate, String workPlaceCode,
			String employmentContractCode, String sectionCode, String positionCode) throws MospException {
		// 処理無し(各エクスポート処理で実装)
		return Collections.emptyList();
	}
	
	/**
	 * CSVデータリストにヘッダを付加する。<br>
	 * @param csvDataList CSVデータリスト
	 * @param exportDto   エクスポート情報
	 * @param fieldList   エクスポートフィールド名称リスト(フィールド順序昇順)
	 * @param targetDate  対象日(実装クラスでOverrideする際に用いられることがある)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void addHeader(List<String[]> csvDataList, ExportDtoInterface exportDto, List<String> fieldList,
			Date targetDate) throws MospException {
		// エクスポート情報のヘッダ有無が無である場合
		if (MospUtility.isEqual(exportDto.getHeader(), PlatformFileConst.HEADER_TYPE_NONE)) {
			// 処理無し
			return;
		}
		// ヘッダをCSVリストに追加
		csvDataList.add(0, getHeader(exportDto, fieldList, targetDate));
	}
	
	/**
	 * ヘッダを取得する。<br>
	 * @param exportDto   エクスポート情報
	 * @param fieldList   エクスポートフィールド名称リスト(フィールド順序昇順)
	 * @param targetDate  対象日(実装クラスでOverrideする際に用いられることがある)
	 * @return ヘッダ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected String[] getHeader(ExportDtoInterface exportDto, List<String> fieldList, Date targetDate)
			throws MospException {
		// コードキー(エクスポートフィールド)を準備(データ区分と同じ)
		String codeKey = exportDto.getExportTable();
		// ヘッダ準備
		String[] header = new String[fieldList.size()];
		// インデックスを準備
		int i = 0;
		// フィールド毎にヘッダを付加
		for (String field : fieldList) {
			header[i++] = getCodeName(field, codeKey);
		}
		// ヘッダを取得
		return header;
	}
	
	/**
	 * 送出ファイル名をMosP処理情報に設定する。<br>
	 * @param dto エクスポート情報
	 * @param targetDate 対象日
	 */
	protected void setFileName(ExportDtoInterface dto, Date targetDate) {
		// 送出ファイル名を作成
		StringBuilder sb = new StringBuilder(dto.getExportCode());
		sb.append(PfNameUtility.hyphen(mospParams));
		sb.append(DateUtility.getStringDateNoSeparator(targetDate));
		sb.append(PlatformUtility.getFileExtension(dto));
		// 送出ファイル名設定
		mospParams.setFileName(sb.toString());
	}
	
	/**
	 * 検索条件に基づき人事情報を検索する。<br>
	 * @param targetDate             対象日
	 * @param workPlaceCode          勤務地コード
	 * @param employmentContractCode 雇用契約コード
	 * @param sectionCode            所属コード
	 * @param positionCode           職位コード
	 * @return 人事情報リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected List<HumanDtoInterface> searchHumanList(Date targetDate, String workPlaceCode,
			String employmentContractCode, String sectionCode, String positionCode) throws MospException {
		// 人事情報検索条件設定
		humanSearch.setTargetDate(targetDate);
		humanSearch.setWorkPlaceCode(workPlaceCode);
		humanSearch.setEmploymentContractCode(employmentContractCode);
		humanSearch.setSectionCode(sectionCode);
		humanSearch.setPositionCode(positionCode);
		// 検索条件設定(状態)
		humanSearch.setStateType(PlatformConst.EMPLOYEE_STATE_PRESENCE);
		// 検索条件設定(下位所属要否)
		humanSearch.setNeedLowerSection(true);
		// 検索条件設定(兼務要否)
		humanSearch.setNeedConcurrent(true);
		// 検索条件設定(操作区分)
		humanSearch.setOperationType(MospConst.OPERATION_TYPE_REFER);
		// 人事情報検索
		return humanSearch.search();
	}
	
	/**
	 * 検索条件に基づき人事情報を検索する。<br>
	 * @param targetDate             対象日
	 * @param workPlaceCode          勤務地コード
	 * @param employmentContractCode 雇用契約コード
	 * @param sectionCode            所属コード
	 * @param positionCode           職位コード
	 * @return 人事情報群(キー：個人ID)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected Map<String, HumanDtoInterface> searchHumanData(Date targetDate, String workPlaceCode,
			String employmentContractCode, String sectionCode, String positionCode) throws MospException {
		// 人事情報検索
		return PlatformUtility.getPersonalIdDtoMap(
				searchHumanList(targetDate, workPlaceCode, employmentContractCode, sectionCode, positionCode));
	}
	
	/**
	 * CSVデータリストに人事情報を設定する。<br>
	 * CSVデータリストの人事情報以外のフィールドには空白が設定されるので、実行する順番に注意が必要。<br>
	 * CSVデータリストのデータ順は、人事情報リストのデータ順となる。<br>
	 * @param csvDataList CSVデータリスト
	 * @param fieldList   エクスポートフィールド名称リスト(フィールド順序昇順)
	 * @param humanList   人事情報リスト
	 * @param targetDate  対象日(所属名称の取得に用いる)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void addHumanData(List<String[]> csvDataList, List<String> fieldList, List<HumanDtoInterface> humanList,
			Date targetDate) throws MospException {
		// 人事情報毎に処理
		for (HumanDtoInterface human : humanList) {
			// 人事情報が設定されたCSVデータを取得し設定
			csvDataList.add(getHumanCsvData(fieldList, human, targetDate));
		}
	}
	
	/**
	 * 人事情報が設定されたCSVデータを取得する。<br>
	 * CSVデータの人事情報以外のフィールドには空白が設定される。<br>
	 * @param fieldList  エクスポートフィールド名称リスト(フィールド順序昇順)
	 * @param human      人事情報
	 * @param targetDate 対象日(所属名称の取得に用いる)
	 * @return 人事情報が設定されたCSVデータ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected String[] getHumanCsvData(List<String> fieldList, HumanDtoInterface human, Date targetDate)
			throws MospException {
		// 人事情報が設定されたCSVデータを準備
		String[] csvData = new String[fieldList.size()];
		// インデックスを準備
		int i = 0;
		// フィールド情報毎に処理
		for (String field : fieldList) {
			// CSVデータに人事情報を設定
			csvData[i++] = getHumanData(human, field, targetDate);
		}
		// 人事情報が設定されたCSVデータを取得
		return csvData;
	}
	
	/**
	 * 人事情報からフィールド名に対応する情報を取得する。<br>
	 * @param human      人事情報
	 * @param fieldName  フィールド名
	 * @param targetDate 対象日(所属名称の取得に用いる)
	 * @return フィールド名に対応する情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected String getHumanData(HumanDtoInterface human, String fieldName, Date targetDate) throws MospException {
		// フィールド名が社員コードである場合
		if (MospUtility.isEqual(fieldName, PlatformFileConst.FIELD_EMPLOYEE_CODE)) {
			// 人事情報から社員コードを取得
			return human.getEmployeeCode();
		}
		// フィールド名が有効日である場合
		if (MospUtility.isEqual(fieldName, PlatformFileConst.FIELD_ACTIVATE_DATE)) {
			// 人事情報から有効日を取得
			return DateUtility.getStringDate(human.getActivateDate());
		}
		// フィールド名が姓である場合
		if (MospUtility.isEqual(fieldName, PlatformFileConst.FIELD_LAST_NAME)) {
			// 人事情報から姓を取得
			return human.getLastName();
		}
		// フィールド名が名である場合
		if (MospUtility.isEqual(fieldName, PlatformFileConst.FIELD_FIRST_NAME)) {
			// 人事情報から名を取得
			return human.getFirstName();
		}
		if (MospUtility.isEqual(fieldName, PlatformFileConst.FIELD_FULL_NAME)) {
			// 人事情報から氏名を取得
			return MospUtility.getHumansName(human.getFirstName(), human.getLastName());
		}
		// フィールド名がカナ姓である場合
		if (MospUtility.isEqual(fieldName, PlatformFileConst.FIELD_LAST_KANA)) {
			// 人事情報からカナ姓を取得
			return human.getLastKana();
		}
		// フィールド名がカナ名である場合
		if (MospUtility.isEqual(fieldName, PlatformFileConst.FIELD_FIRST_KANA)) {
			// 人事情報からカナ名を取得
			return human.getFirstKana();
		}
		// フィールド名が勤務地コードである場合
		if (MospUtility.isEqual(fieldName, PlatformFileConst.FIELD_WORK_PLACE_CODE)) {
			// 人事情報から勤務地コードを取得
			return human.getWorkPlaceCode();
		}
		// フィールド名が雇用契約コードである場合
		if (MospUtility.isEqual(fieldName, PlatformFileConst.FIELD_EMPLOYMENT_CONTRACT_CODE)) {
			// 人事情報から雇用契約コードを取得
			return human.getEmploymentContractCode();
		}
		// フィールド名が所属コードである場合
		if (MospUtility.isEqual(fieldName, PlatformFileConst.FIELD_SECTION_CODE)) {
			// 人事情報から所属コードを取得
			return human.getSectionCode();
		}
		// フィールド名が職位コードである場合
		if (MospUtility.isEqual(fieldName, PlatformFileConst.FIELD_POSITION_CODE)) {
			// 人事情報から職位コードを取得
			return human.getPositionCode();
		}
		// フィールド名が所属名称である場合
		if (MospUtility.isEqual(fieldName, PlatformFileConst.FIELD_SECTION_NAME)) {
			// 所属名称を取得
			return platformMaster.getSectionName(human.getSectionCode(), targetDate);
		}
		// フィールド名が所属表示名称である場合
		if (MospUtility.isEqual(fieldName, PlatformFileConst.FIELD_SECTION_DISPLAY)) {
			// 人事情報から所属表示名称を取得(所属表示内容が表示名称である前提)
			return platformMaster.getSectionNameOrDisplay(human.getSectionCode(), targetDate, mospParams);
		}
		// 空文字を取得
		return MospConst.STR_EMPTY;
	}
	
	/**
	 * CSVデータに値を設定する。<br>
	 * @param csvData   CSVデータ(文字列の配列)
	 * @param fieldList エクスポートフィールド名称リスト(フィールド順序昇順)
	 * @param fieldName フィールド名称
	 * @param value     値
	 */
	protected void setCsvValue(String[] csvData, List<String> fieldList, String fieldName, Object value) {
		// CSVデータに値を設定
		PlatformUtility.setCsvValue(csvData, fieldList, fieldName, value);
	}
	
}
