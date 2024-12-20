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
package jp.mosp.platform.bean.system.impl;

import java.io.InputStream;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.orangesignal.OrangeSignalParams;
import jp.mosp.orangesignal.OrangeSignalUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.file.CsvImportBeanInterface;
import jp.mosp.platform.bean.system.BankBranchRegistBeanInterface;
import jp.mosp.platform.dao.system.BankBranchDaoInterface;
import jp.mosp.platform.dto.system.BankBranchDtoInterface;
import jp.mosp.platform.dto.system.impl.PfmBankBranchDto;

/**
 * 銀行支店登録クラス。
 */
public class BankBranchRegistBean extends PlatformBean
		implements BankBranchRegistBeanInterface, CsvImportBeanInterface {
	
	/**
	 * 郵便番号マスタDAOクラス。<br>
	 */
	protected BankBranchDaoInterface dao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public BankBranchRegistBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// DAO準備
		dao = createDaoInstance(BankBranchDaoInterface.class);
	}
	
	@Override
	public BankBranchDtoInterface getInitDto() {
		return new PfmBankBranchDto();
	}
	
	@Override
	public void insert(BankBranchDtoInterface dto) throws MospException {
		// DTO妥当性確認
		validate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 新規登録情報の検証
		checkInsert(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setPfmBankBranchId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	/**
	 * ファイルの内容をインポートする。<br>
	 * <br>
	 * コードの重複は考慮せず、ファイルの内容をそのまま新規登録する。<br>
	 * <br>
	 * ファイルのフォーマット(CSV、ヘッダ無)は、次の通り。<br>
	 * ・銀行コード<br>
	 * ・支店コード<br>
	 * ・支店カナ<br>
	 * ・支店名<br>
	 * ・支店所在地カナ<br>
	 * ・支店所在地<br>
	 * ・支店電話番号<br>
	 * ・無効フラグ<br>
	 * 9列目以降は登録しない。<br>
	 * ファイルのフォーマットが異なる場合、正しく登録されなかったり、
	 * 実行時例外が発生したりする可能性がある。<br>
	 * <br>
	 */
	@Override
	public int importCsv(InputStream requestedFile) throws MospException {
		// アップロードファイルを登録情報リストに変換
		List<String[]> dataList = OrangeSignalUtility.parse(requestedFile, new OrangeSignalParams());
		// 人事マスタ情報毎に登録
		for (String[] data : dataList) {
			// 登録情報インデックス準備
			int idx = 0;
			// DTO取得
			BankBranchDtoInterface dto = new PfmBankBranchDto();
			// DTOに登録情報を設定
			dto.setBankCode(data[idx++].trim());
			dto.setBranchCode(data[idx++].trim());
			dto.setBranchKana(data[idx++].trim());
			dto.setBranchName(data[idx++].trim());
			dto.setBranchAddressKana(data[idx++].trim());
			dto.setBranchAddressName(data[idx++].trim());
			dto.setBranchPhone(data[idx++].trim());
			dto.setInactivateFlag(getInactivateFlag(data[idx++].trim()));
			dto.setDeleteFlag(MospConst.DELETE_FLAG_OFF);
			// 新規登録
			insert(dto);
		}
		// 登録件数取得
		return dataList.size();
	}
	
	/**
	 * 登録情報から無効フラグを取得する。<br>
	 * <br>
	 * 登録情報が1の場合は0(無効フラグOFF)を、
	 * それ以外の場合は1(無効フラグON)を返す。<br>
	 * <br>
	 * @param data 登録情報
	 * @return 無効フラグ
	 */
	protected int getInactivateFlag(String data) {
		return Integer.parseInt(data.trim()) == MospConst.FLAG_ON ? MospConst.INACTIVATE_FLAG_OFF
				: MospConst.INACTIVATE_FLAG_ON;
	}
	
	/**
	 * 新規登録時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkInsert(BankBranchDtoInterface dto) throws MospException {
		// 処理無し
	}
	
	/**
	 * 登録情報の妥当性を確認する。
	 * @param dto 対象DTO
	 */
	protected void validate(BankBranchDtoInterface dto) {
		// 妥当性確認
	}
	
}
