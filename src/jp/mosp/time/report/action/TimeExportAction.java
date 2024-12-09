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
package jp.mosp.time.report.action;

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseVo;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.orangesignal.OrangeSignalUtility;
import jp.mosp.platform.dto.file.ExportDtoInterface;
import jp.mosp.platform.utils.MonthUtility;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.time.base.TimeAction;
import jp.mosp.time.bean.ExportTableReferenceBeanInterface;
import jp.mosp.time.bean.TimeExportBeanInterface;
import jp.mosp.time.entity.CutoffEntityInterface;
import jp.mosp.time.file.vo.TimeExportListVo;
import jp.mosp.time.utils.TimeUtility;

/**
 * CSV出力を行う。<br>
 * <br>
 * 以下のコマンドを扱う。<br>
 * <ul><li>
 * {@link #CMD_EXECUTION}
 * </li></ul>
 */
public class TimeExportAction extends TimeAction {
	
	/**
	 * 実行コマンド。<br>
	 * <br>
	 * エクスポートを実行する。<br>
	 * 実行時にエクスポートマスタが決定していない、対象ファイルが選択されていない場合は
	 * エラーメッセージにて通知し、処理は実行されない。<br>
	 */
	public static final String		CMD_EXECUTION				= "TM3315";
	
	/**
	 * コードキー(勤怠エクスポート処理)。
	 */
	protected static final String	CODE_KEY_TIME_EXPORT_BEAN	= "TimeExportBean";
	
	
	/**
	 * {@link TimeAction#TimeAction()}を実行する。<br>
	 */
	public TimeExportAction() {
		super();
	}
	
	@Override
	protected BaseVo getSpecificVo() {
		return new TimeExportListVo();
	}
	
	@Override
	public void action() throws MospException {
		if (mospParams.getCommand().equals(CMD_EXECUTION)) {
			// 実行
			prepareVo();
			execution();
		}
	}
	
	/**
	 * エクスポートの実行を行う。<br>
	 * @throws MospException 比較クラスのインスタンス生成に失敗した場合
	 */
	protected void execution() throws MospException {
		// VO取得
		TimeExportListVo vo = (TimeExportListVo)mospParams.getVo();
		ExportDtoInterface dto = reference().export().findForKey(vo.getRadSelect());
		if (dto == null) {
			// データ無しメッセージを設定
			PfMessageUtility.addMessageNoData(mospParams);
			return;
		}
		// エクスポートコード及びデータ区分を取得
		String exportCode = dto.getExportCode();
		String exportTable = dto.getExportTable();
		// 勤怠エクスポート処理(クラス名)を取得
		String modelClass = MospUtility.getCodeName(mospParams, exportTable, CODE_KEY_TIME_EXPORT_BEAN);
		// 出力条件を取得
		int startYear = getInt(vo.getTxtStartYear());
		int startMonth = getInt(vo.getTxtStartMonth());
		int endYear = getInt(vo.getTxtEndYear());
		int endMonth = getInt(vo.getTxtEndMonth());
		String cutoffCode = vo.getPltCutoff();
		String workPlaceCode = vo.getPltWorkPlace();
		String employmentContractCode = vo.getPltEmployment();
		String sectionCode = vo.getPltSection();
		String positionCode = vo.getPltPosition();
		// 下位所属含むチェックボックス読込
		String ckbNeedLowerSection = vo.getCkbNeedLowerSection();
		// 勤怠エクスポート処理(クラス名)を取得できた場合
		if (MospUtility.isEqual(modelClass, exportTable) == false) {
			// 勤怠エクスポート処理を取得
			TimeExportBeanInterface exportBean = reference().createBean(TimeExportBeanInterface.class, modelClass);
			// 勤怠エクスポート処理を実行
			exportBean.export(exportCode, startYear, startMonth, endYear, endMonth, cutoffCode, workPlaceCode,
					employmentContractCode, sectionCode, MospUtility.isChecked(ckbNeedLowerSection), positionCode);
			// 処理終了
			return;
		}
		// 検索クラス取得
		ExportTableReferenceBeanInterface exportTableRefer = timeReference().exportTable();
		exportTableRefer.setExportCode(vo.getRadSelect());
		exportTableRefer.setStartYear(startYear);
		exportTableRefer.setStartMonth(startMonth);
		exportTableRefer.setEndYear(endYear);
		exportTableRefer.setEndMonth(endMonth);
		exportTableRefer.setCutoffCode(cutoffCode);
		exportTableRefer.setWorkPlaceCode(workPlaceCode);
		exportTableRefer.setEmploymentCode(employmentContractCode);
		exportTableRefer.setSectionCode(sectionCode);
		// 下位所属含むチェックボックス設定
		exportTableRefer.setCkbNeedLowerSection(getInt(ckbNeedLowerSection));
		exportTableRefer.setPositionCode(positionCode);
		// CSVデータリスト取得
		List<String[]> csvDataList = exportTableRefer.export();
		if (mospParams.hasErrorMessage()) {
			// エラー発生時はチェックボタンの選択状態を初期化する
			vo.setRadSelect(MospConst.STR_EMPTY);
			return;
		}
		// 該当するエクスポート情報が存在しない場合
		if (csvDataList.isEmpty()) {
			// エラーメッセージを設定
			PfMessageUtility.addErrorNoExportInfo(mospParams);
			// エラー発生時はチェックボタンの選択状態を初期化する
			vo.setRadSelect(MospConst.STR_EMPTY);
			return;
		}
		// CSVデータリストをMosP処理情報に設定
		mospParams.setFile(OrangeSignalUtility.getOrangeSignalParams(csvDataList));
		// 送出ファイル名をMosP処理情報に設定
		setFileName(dto);
	}
	
	/**
	 * 送出ファイル名をMosP処理情報に設定する。<br>
	 * @param dto エクスポート情報
	 * @throws MospException 例外発生時
	 */
	protected void setFileName(ExportDtoInterface dto) throws MospException {
		// VO取得
		TimeExportListVo vo = (TimeExportListVo)mospParams.getVo();
		int startYear = getInt(vo.getTxtStartYear());
		int startMonth = getInt(vo.getTxtStartMonth());
		Date targetDate = MonthUtility.getYearMonthTargetDate(startYear, startMonth, mospParams);
		// 締日エンティティを取得
		CutoffEntityInterface cutoff = timeReference().master().getCutoff(vo.getPltCutoff(), targetDate);
		// 締日情報が取得できたかを確認
		timeReference().cutoff().chkExistCutoff(cutoff.getCutoffDto(), targetDate);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 出力期間を取得
		Date firstDate = cutoff.getCutoffFirstDate(startYear, startMonth, mospParams);
		Date lastDate = cutoff.getCutoffLastDate(getInt(vo.getTxtEndYear()), getInt(vo.getTxtEndMonth()), mospParams);
		// 送出ファイル名を設定
		mospParams.setFileName(TimeUtility.getExportFileName(mospParams, dto, firstDate, lastDate));
	}
	
}
