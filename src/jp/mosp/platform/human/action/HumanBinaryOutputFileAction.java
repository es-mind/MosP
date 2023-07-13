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
package jp.mosp.platform.human.action;

import java.util.Date;

import jp.mosp.framework.base.BaseVo;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.exporter.ImageContents;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.platform.base.PlatformAction;
import jp.mosp.platform.bean.human.HumanBinaryArrayReferenceBeanInterface;
import jp.mosp.platform.bean.human.HumanBinaryHistoryReferenceBeanInterface;
import jp.mosp.platform.bean.human.HumanBinaryNormalReferenceBeanInterface;
import jp.mosp.platform.dto.human.HumanBinaryArrayDtoInterface;
import jp.mosp.platform.dto.human.HumanBinaryHistoryDtoInterface;
import jp.mosp.platform.dto.human.HumanBinaryNormalDtoInterface;
import jp.mosp.platform.human.base.PlatformHumanAction;
import jp.mosp.platform.human.vo.HumanBinaryArrayCardVo;
import jp.mosp.platform.human.vo.HumanBinaryHistoryCardVo;
import jp.mosp.platform.human.vo.HumanBinaryHistoryListVo;
import jp.mosp.platform.human.vo.HumanBinaryNormalCardVo;
import jp.mosp.platform.human.vo.HumanInfoVo;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.platform.utils.PlatformUtility;

/**
 * 人事汎用バイナリデータをファイルとして出力する。
 */
public class HumanBinaryOutputFileAction extends PlatformHumanAction {
	
	/**
	 * 人事汎用通常情報表示コマンド。<br>
	 */
	public static final String	CMD_NORAML_FILE			= "PF1601";
	
	/**
	 * 人事汎用履歴情報表示コマンド。<br>
	 */
	public static final String	CMD_HISTORY_FILE		= "PF1602";
	
	/**
	 * 人事汎用履歴一覧情報表示コマンド。<br>
	 */
	public static final String	CMD_HISTORY_LIST_FILE	= "PF1603";
	
	/**
	 * 人事汎用一覧情報表示コマンド。<br>
	 */
	public static final String	CMD_ARRAY_FILE			= "PF1604";
	
	/**
	 * 人事情報一覧汎用バイナリ通常情報表示コマンド。<br>
	 */
	public static final String	CMD_NORAML_INFO_FILE	= "PF1605";
	
	/**
	 * 人事情報一覧汎用バイナリ履歴情報表示コマンド。<br>
	 */
	public static final String	CMD_HISTORY_INFO_FILE	= "PF1606";
	
	/**
	 * 人事情報一覧汎用バイナリ一覧情報表示コマンド。<br>
	 */
	public static final String	CMD_ARRAY_INFO_FILEE	= "PF1607";
	
	
	/**
	 * {@link PlatformAction#PlatformAction()}を実行する。<br>
	 */
	public HumanBinaryOutputFileAction() {
		super();
	}
	
	@Override
	public void action() throws MospException {
		// コマンド毎の処理
		if (mospParams.getCommand().equals(CMD_NORAML_FILE)) {
			// 通常
			prepareVo(true, false);
			normal();
		} else if (mospParams.getCommand().equals(CMD_HISTORY_FILE)) {
			// 履歴
			prepareVo(true, false);
			history();
		} else if (mospParams.getCommand().equals(CMD_HISTORY_LIST_FILE)) {
			// 履歴一覧
			prepareVo(true, false);
			historyList();
		} else if (mospParams.getCommand().equals(CMD_ARRAY_FILE)) {
			// 一覧
			prepareVo(true, false);
			array();
		} else if (mospParams.getCommand().equals(CMD_NORAML_INFO_FILE)) {
			// 通常情報一覧
			prepareVo(true, false);
			normalInfoFile();
		} else if (mospParams.getCommand().equals(CMD_HISTORY_INFO_FILE)) {
			// 履歴一覧ファイル
			prepareVo(true, false);
			historyInfoFile();
		} else if (mospParams.getCommand().equals(CMD_ARRAY_INFO_FILEE)) {
			// 一覧一覧ファイル
			prepareVo(true, false);
			arrayInfoFile();
		} else {
			throwInvalidCommandException();
		}
	}
	
	/**
	 * 人事汎用バイナリ通常情報のバイナリデータを表示する。
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	public void normal() throws MospException {
		// VO取得
		HumanBinaryNormalCardVo vo = (HumanBinaryNormalCardVo)mospParams.getVo();
		// 人事汎用バイナリ通常情報参照クラス取得
		HumanBinaryNormalReferenceBeanInterface binaryNormal = reference().humanBinaryNormal();
		// 人事汎用バイナリ通常情報取得
		HumanBinaryNormalDtoInterface normalDto = binaryNormal.findForInfo(vo.getPersonalId(), vo.getDivision());
		// 画像設定
		mospParams.setFile(new ImageContents(normalDto.getFileType(), normalDto.getHumanItemBinary()));
		// ファイル名・ファイル拡張子設定
		String fileName = normalDto.getFileName();
		String fileType = getCodeName(normalDto.getFileType(), "BinaryFileType");
		// 部分一致確認
		boolean isBroadMatch = PlatformUtility.isBroadMatch(fileType, fileName);
		// 確認できない場合
		if (!isBroadMatch) {
			// 拡張子追加
			fileName = fileName + fileType;
		}
		// ファイル名設定
		mospParams.setFileName(fileName);
		// ファイル名設定
		mospParams.setFileName(normalDto.getFileName());
	}
	
	/**
	 * 人事汎用バイナリ履歴情報のバイナリデータを表示する。
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	public void history() throws MospException {
		// VO取得
		HumanBinaryHistoryCardVo vo = (HumanBinaryHistoryCardVo)mospParams.getVo();
		// 人事汎用バイナリ通常情報参照クラス取得
		HumanBinaryHistoryReferenceBeanInterface binaryHistory = reference().humanBinaryHistory();
		// 有効日取得
		String year = mospParams.getRequestParam("historyYear");
		String month = mospParams.getRequestParam("historyMonth");
		String day = mospParams.getRequestParam("historyDay");
		// 人事汎用バイナリ通常情報取得
		HumanBinaryHistoryDtoInterface historyDto = binaryHistory.findForInfo(vo.getPersonalId(), vo.getDivision(),
				DateUtility.getDate(year, month, day));
		// 確認
		if (historyDto == null) {
			// データ無しメッセージを設定
			PfMessageUtility.addMessageNoData(mospParams);
			return;
		}
		// 画像設定
		mospParams.setFile(new ImageContents(historyDto.getFileType(), historyDto.getHumanItemBinary()));
		// ファイル名設定
		mospParams.setFileName(historyDto.getFileName());
	}
	
	/**
	 * 人事汎用バイナリ履歴一覧情報のバイナリデータを表示する。
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	public void historyList() throws MospException {
		// VO取得
		HumanBinaryHistoryListVo vo = (HumanBinaryHistoryListVo)mospParams.getVo();
		// 有効日取得
		Date activeDate = getDate(getTransferredActivateDate());
		// 人事汎用バイナリ通常情報参照クラス取得
		HumanBinaryHistoryReferenceBeanInterface binaryHistory = reference().humanBinaryHistory();
		// 人事汎用バイナリ通常情報取得
		HumanBinaryHistoryDtoInterface historyDto = binaryHistory.findForInfo(vo.getPersonalId(), vo.getDivision(),
				activeDate);
		// 確認
		if (historyDto == null) {
			// データ無しメッセージを設定
			PfMessageUtility.addMessageNoData(mospParams);
			return;
		}
		// 画像設定
		mospParams.setFile(new ImageContents(historyDto.getFileType(), historyDto.getHumanItemBinary()));
		// ファイル名設定
		mospParams.setFileName(historyDto.getFileName());
	}
	
	/**
	 * 人事汎用バイナリ一覧情報のバイナリデータを表示する。
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	public void array() throws MospException {
		// VO取得
		HumanBinaryArrayCardVo vo = (HumanBinaryArrayCardVo)mospParams.getVo();
		// 行ID取得
		int rowId = getTransferredIndex();
		// バイナリ一覧参照クラス取得
		HumanBinaryArrayReferenceBeanInterface binaryArray = reference().humanBinaryArray();
		// バイナリ一覧情報取得
		HumanBinaryArrayDtoInterface arrayDto = binaryArray.findForKey(vo.getPersonalId(), vo.getDivision(), rowId);
		// 情報確認
		if (arrayDto == null) {
			// データ無しメッセージを設定
			PfMessageUtility.addMessageNoData(mospParams);
			return;
		}
		// 画像設定
		mospParams.setFile(new ImageContents(arrayDto.getFileType(), arrayDto.getHumanItemBinary()));
		// ファイル名設定
		mospParams.setFileName(arrayDto.getFileName());
	}
	
	/**
	 * 人事情報一覧画面で
	 * 人事汎用バイナリ通常情報のバイナリファイルを出力をする。
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	public void normalInfoFile() throws MospException {
		// VO取得
		HumanInfoVo vo = (HumanInfoVo)mospParams.getVo();
		// 人事汎用バイナリ通常情報参照クラス取得
		HumanBinaryNormalReferenceBeanInterface binaryNormal = reference().humanBinaryNormal();
		// 人事汎用画面区分取得
		String division = getTransferredType();
		// 人事汎用バイナリ通常情報取得
		HumanBinaryNormalDtoInterface normalDto = binaryNormal.findForInfo(vo.getPersonalId(), division);
		// 確認
		if (normalDto == null) {
			// データ無しメッセージを設定
			PfMessageUtility.addMessageNoData(mospParams);
			return;
		}
		// 画像設定
		mospParams.setFile(new ImageContents(normalDto.getFileType(), normalDto.getHumanItemBinary()));
		// ファイル名設定
		mospParams.setFileName(normalDto.getFileName());
	}
	
	/**
	 * 人事情報一覧画面で
	 * 人事汎用バイナリ一覧情報のバイナリファイルを出力をする。
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	private void historyInfoFile() throws MospException {
		// VO取得
		HumanInfoVo vo = (HumanInfoVo)mospParams.getVo();
		// 人事汎用バイナリ通常情報参照クラス取得
		HumanBinaryHistoryReferenceBeanInterface binaryHistory = reference().humanBinaryHistory();
		// 人事画面区分取得
		String division = getTransferredType();
		// 有効日取得
		Date activeDate = getDate(getTransferredActivateDate());
		// 人事汎用バイナリ通常情報取得
		HumanBinaryHistoryDtoInterface historyDto = binaryHistory.findForInfo(vo.getPersonalId(), division, activeDate);
		// 確認
		if (historyDto == null) {
			// データ無しメッセージを設定
			PfMessageUtility.addMessageNoData(mospParams);
			return;
		}
		// 画像設定
		mospParams.setFile(new ImageContents(historyDto.getFileType(), historyDto.getHumanItemBinary()));
		// ファイル名設定
		mospParams.setFileName(historyDto.getFileName());
	}
	
	/**
	 * 人事情報一覧画面で
	 * 人事汎用バイナリ一覧情報のバイナリファイルを出力をする。
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	public void arrayInfoFile() throws MospException {
		// VO取得
		HumanInfoVo vo = (HumanInfoVo)mospParams.getVo();
		// 人事画面区分取得
		String division = getTransferredType();
		// 行ID取得
		int rowId = getTransferredIndex();
		// バイナリ一覧参照クラス取得
		HumanBinaryArrayReferenceBeanInterface binaryArray = reference().humanBinaryArray();
		// バイナリ一覧情報取得
		HumanBinaryArrayDtoInterface arrayDto = binaryArray.findForKey(vo.getPersonalId(), division, rowId);
		// 情報確認
		if (arrayDto == null) {
			// データ無しメッセージを設定
			PfMessageUtility.addMessageNoData(mospParams);
			return;
		}
		// 画像設定
		mospParams.setFile(new ImageContents(arrayDto.getFileType(), arrayDto.getHumanItemBinary()));
		// ファイル名設定
		mospParams.setFileName(arrayDto.getFileName());
	}
	
	/**
	 * コマンド毎にVOを呼ぶ。
	 */
	@Override
	protected BaseVo getSpecificVo() {
		if (mospParams.getCommand().equals(CMD_NORAML_FILE)) {
			return new HumanBinaryNormalCardVo();
		}
		if (mospParams.getCommand().equals(CMD_HISTORY_FILE)) {
			return new HumanBinaryHistoryCardVo();
		}
		if (mospParams.getCommand().equals(CMD_HISTORY_LIST_FILE)) {
			return new HumanBinaryHistoryListVo();
		}
		if (mospParams.getCommand().equals(CMD_ARRAY_FILE)) {
			return new HumanBinaryArrayCardVo();
		}
		return new HumanInfoVo();
	}
	
}
