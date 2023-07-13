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
/**
 * 
 */
package jp.mosp.time.bean.impl;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.utils.PfNameUtility;
import jp.mosp.time.bean.LimitStandardRegistBeanInterface;
import jp.mosp.time.dao.settings.LimitStandardDaoInterface;
import jp.mosp.time.dto.settings.LimitStandardDtoInterface;
import jp.mosp.time.dto.settings.impl.TmmLimitStandardDto;
import jp.mosp.time.utils.TimeNamingUtility;

/**
 * 限度基準登録処理。<br>
 */
public class LimitStandardRegistBean extends PlatformBean implements LimitStandardRegistBeanInterface {
	
	/**
	 * 限度基準管理DAO
	 */
	protected LimitStandardDaoInterface dao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public LimitStandardRegistBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// DAOを準備
		dao = createDaoInstance(LimitStandardDaoInterface.class);
	}
	
	@Override
	public LimitStandardDtoInterface getInitDto() {
		return new TmmLimitStandardDto();
	}
	
	@Override
	public void regist(Collection<LimitStandardDtoInterface> dtos) throws MospException {
		// 限度基準情報毎に処理
		for (LimitStandardDtoInterface dto : dtos) {
			// 登録
			regist(dto, null);
		}
	}
	
	@Override
	public void copy(String workSettingCode, Date activateDate, Date copiedActivateDate) throws MospException {
		// 限度基準情報群を取得
		List<LimitStandardDtoInterface> dtos = dao.findForSearch(workSettingCode, copiedActivateDate);
		// 限度基準情報毎に処理
		for (LimitStandardDtoInterface dto : dtos) {
			// 限度基準情報の有効日を変更
			dto.setActivateDate(activateDate);
			// 登録
			regist(dto, null);
		}
	}
	
	/**
	 * 登録を行う。<br>
	 * <br>
	 * @param dto 限度基準情報
	 * @param row 行インデックス
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void regist(LimitStandardDtoInterface dto, Integer row) throws MospException {
		// 限度基準情報の妥当性を確認
		validate(dto, row);
		// 限度基準情報が妥当でない場合
		if (mospParams.hasErrorMessage()) {
			// 処理終了
			return;
		}
		// 限度基準情報の値を取得
		String workSettingCode = dto.getWorkSettingCode();
		Date activateDate = dto.getActivateDate();
		String term = dto.getTerm();
		// 登録されている限度基準情報を取得
		LimitStandardDtoInterface current = dao.findForKey(workSettingCode, activateDate, term);
		// 登録されている限度基準情報が存在する場合
		if (current != null) {
			// 登録されている限度基準情報と時間が全て同じである場合
			if (isAllTimeSame(dto, current)) {
				// 処理不要
				return;
			}
			// 登録されている限度基準情報を論理削除
			logicalDelete(dao, current.getTmmLimitStandardId());
		}
		// 限度基準情報の時間が全て0である場合
		if (isAllTimeZero(dto)) {
			// 処理終了
			return;
		}
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setTmmLimitStandardId(dao.nextRecordId());
		// 登録
		dao.insert(dto);
	}
	
	@Override
	public void delete(String workSettingCode, Date activateDate) throws MospException {
		// 限度基準管理情報毎に処理
		for (LimitStandardDtoInterface dto : dao.findForSearch(workSettingCode, activateDate)) {
			// 論理削除
			logicalDelete(dao, dto.getTmmLimitStandardId());
		}
	}
	
	/**
	 * 時間外限度時間と時間外注意時間と時間外警告時間が全て0であるかを確認する。<br>
	 * @param dto 限度基準情報
	 * @return 確認結果(true：全て0である、false：全て0でない)
	 */
	protected boolean isAllTimeZero(LimitStandardDtoInterface dto) {
		// 時間外限度時間と時間外注意時間と時間外警告時間が全て0であるかを確認
		return dto.getLimitTime() == 0 && dto.getAttentionTime() == 0 && dto.getWarningTime() == 0;
	}
	
	/**
	 * 時間外限度時間と時間外注意時間と時間外警告時間が全て同じであるかを確認する。<br>
	 * @param dto1 限度基準情報1
	 * @param dto2 限度基準情報2
	 * @return 確認結果(true：全て同じである、false：全て同じでない)
	 */
	public static boolean isAllTimeSame(LimitStandardDtoInterface dto1, LimitStandardDtoInterface dto2) {
		// 時間外限度時間と時間外注意時間と時間外警告時間が全て同じであるかを確認
		return dto1.getLimitTime() == dto2.getLimitTime() && dto1.getAttentionTime() == dto2.getAttentionTime()
				&& dto1.getWarningTime() == dto2.getWarningTime();
	}
	
	/**
	 * 登録情報の妥当性を確認する。
	 * @param dto 限度基準情報
	 * @param row 行インデックス
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void validate(LimitStandardDtoInterface dto, Integer row) throws MospException {
		// DTOの値を取得
		String workSettingCode = dto.getWorkSettingCode();
		Date activateDate = dto.getActivateDate();
		String term = dto.getTerm();
		int deleteFlag = dto.getDeleteFlag();
		// 名称を取得
		String nameWorkSettingCode = TimeNamingUtility.timeSettingCode(mospParams);
		String nameActivateDate = PfNameUtility.activateDate(mospParams);
		String nameTerm = PfNameUtility.term(mospParams);
		// 必須確認(勤怠設定コード)
		checkRequired(workSettingCode, nameWorkSettingCode, row);
		// 必須確認(通知対象)
		checkRequired(activateDate, nameActivateDate, row);
		// 必須確認(期間)
		checkRequired(term, nameTerm, row);
		// 型確認(勤怠設定コード)
		checkTypeCode(workSettingCode, nameWorkSettingCode, row);
		// 削除フラグ
		checkDeleteFlag(deleteFlag, row);
	}
	
}
