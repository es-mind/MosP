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
/**
 * 
 */
package jp.mosp.time.bean.impl;

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.platform.utils.PlatformUtility;
import jp.mosp.time.bean.ScheduleDateRegistBeanInterface;
import jp.mosp.time.bean.TimeMasterBeanInterface;
import jp.mosp.time.constant.TimeMessageConst;
import jp.mosp.time.dao.settings.ScheduleDateDaoInterface;
import jp.mosp.time.dto.settings.ScheduleDateDtoInterface;
import jp.mosp.time.dto.settings.impl.TmmScheduleDateDto;
import jp.mosp.time.entity.WorkTypeEntityInterface;
import jp.mosp.time.utils.TimeNamingUtility;
import jp.mosp.time.utils.TimeUtility;

/**
 * カレンダ日登録クラス。
 */
public class ScheduleDateRegistBean extends PlatformBean implements ScheduleDateRegistBeanInterface {
	
	/**
	 * カレンダ日マスタDAOクラス。<br>
	 */
	protected ScheduleDateDaoInterface	dao;
	
	/**
	 * 勤怠関連マスタ参照処理。<br>
	 */
	protected TimeMasterBeanInterface	timeMaster;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public ScheduleDateRegistBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// DAOを準備
		dao = createDaoInstance(ScheduleDateDaoInterface.class);
		// Beanを準備
		timeMaster = createBeanInstance(TimeMasterBeanInterface.class);
	}
	
	@Override
	public ScheduleDateDtoInterface getInitDto() {
		return new TmmScheduleDateDto();
	}
	
	@Override
	public void insert(List<ScheduleDateDtoInterface> list) throws MospException {
		for (ScheduleDateDtoInterface dto : list) {
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
			dto.setTmmScheduleDateId(dao.nextRecordId());
			// 登録処理
			dao.insert(dto);
		}
	}
	
	@Override
	public void add(List<ScheduleDateDtoInterface> list) throws MospException {
		for (ScheduleDateDtoInterface dto : list) {
			// DTO妥当性確認
			validate(dto);
			if (mospParams.hasErrorMessage()) {
				return;
			}
			// 履歴更新情報の検証
			checkAdd(dto);
			if (mospParams.hasErrorMessage()) {
				return;
			}
			// レコード識別ID最大値をインクリメントしてDTOに設定
			dto.setTmmScheduleDateId(dao.nextRecordId());
			// 登録処理
			dao.insert(dto);
		}
	}
	
	@Override
	public void update(List<ScheduleDateDtoInterface> list) throws MospException {
		for (ScheduleDateDtoInterface dto : list) {
			// DTO妥当性確認
			validate(dto);
			if (mospParams.hasErrorMessage()) {
				return;
			}
			// 履歴更新情報の検証
			checkUpdate(dto);
			if (mospParams.hasErrorMessage()) {
				return;
			}
			// 登録済情報取得
			ScheduleDateDtoInterface oldDto = dao.findForKey(dto.getScheduleCode(), dto.getScheduleDate());
			if (oldDto != null) {
				// 入力値を更新しているか確認
				boolean workType = oldDto.getWorkTypeCode().equals(dto.getWorkTypeCode());
				boolean remark = oldDto.getRemark().equals(dto.getRemark());
				// 更新していない場合
				if (workType && remark) {
					continue;
				}
			}
			// 論理削除
			logicalDelete(dao, dto.getTmmScheduleDateId());
			// レコード識別ID最大値をインクリメントしてDTOに設定
			dto.setTmmScheduleDateId(dao.nextRecordId());
			// 登録処理
			dao.insert(dto);
		}
	}
	
	@Override
	public void update(long[] idArray, Date activateDate, int inactivateFlag) {
		// 処理なし
	}
	
	@Override
	public void delete(String scheduleCode, Date activateDate) throws MospException {
		List<ScheduleDateDtoInterface> list = dao.findForList(scheduleCode, activateDate);
		for (ScheduleDateDtoInterface dto : list) {
			// DTO妥当性確認
			validate(dto);
			if (mospParams.hasErrorMessage()) {
				return;
			}
			// 削除時の確認処理
			checkDelete(dto);
			if (mospParams.hasErrorMessage()) {
				continue;
			}
			// 論理削除
			logicalDelete(dao, dto.getTmmScheduleDateId());
		}
	}
	
	/**
	 * 新規登録時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkInsert(ScheduleDateDtoInterface dto) throws MospException {
		// 対象レコードの有効日が重複していないかを確認
		checkDuplicateInsert(dao.findForKey(dto.getScheduleCode(), dto.getScheduleDate()));
	}
	
	/**
	 * 履歴追加時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkAdd(ScheduleDateDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkDuplicateScheduleAdd(dao.findForKey(dto.getScheduleCode(), dto.getScheduleDate()));
	}
	
	/**
	 * 履歴更新時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkUpdate(ScheduleDateDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getTmmScheduleDateId());
		// 無効フラグ確認
		if (isDtoActivate(dto)) {
			return;
		}
		// 更新元データの無効フラグ確認
		if (!isDtoActivate(dao.findForKey(dto.getTmmScheduleDateId(), true))) {
			// 更新元データが更新前から無効であれば無効期間は発生しない
			return;
		}
	}
	
	/**
	 * 削除時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkDelete(ScheduleDateDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getTmmScheduleDateId());
	}
	
	/**
	 * 重複確認(履歴追加用)を行う。<br>
	 * 対象DTOがnullでない場合は、{@link #mospParams}にエラーメッセージを追加する。<br>
	 * @param dto 対象DTO
	 */
	protected void checkDuplicateScheduleAdd(BaseDtoInterface dto) {
		// 対象DTO存在確認
		if (dto != null) {
			// 対象DTOが存在する場合
			mospParams.addErrorMessage(TimeMessageConst.MSG_SCHEDULE_HIST_ALREADY_EXISTED);
		}
	}
	
	/**
	 * 登録情報の妥当性を確認する。
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void validate(ScheduleDateDtoInterface dto) throws MospException {
		// カレンダコード
		String scheduleCodeName = mospParams.getName("Calendar", "Code");
		checkRequired(dto.getScheduleCode(), scheduleCodeName, null);
		// 勤務形態が有効であるかを確認
		checkWorkTypeActive(dto.getWorkTypeCode(), dto.getScheduleDate());
	}
	
	@Override
	public void allReflectionRegist(List<ScheduleDateDtoInterface> list) throws MospException {
		// カレンダ日情報毎に処理
		for (ScheduleDateDtoInterface dto : list) {
			// DTO妥当性確認
			validate(dto);
			if (mospParams.hasErrorMessage()) {
				return;
			}
			// 登録済情報取得
			ScheduleDateDtoInterface oldDto = dao.findForKey(dto.getScheduleCode(), dto.getScheduleDate());
			if (oldDto == null) {
				// 新規登録情報の検証
				checkInsert(dto);
				if (mospParams.hasErrorMessage()) {
					return;
				}
			} else {
				// 履歴更新情報の検証
				checkUpdate(dto);
				if (mospParams.hasErrorMessage()) {
					return;
				}
				// 入力値を更新しているか確認
				boolean workType = oldDto.getWorkTypeCode().equals(dto.getWorkTypeCode());
				boolean remark = oldDto.getRemark().equals(dto.getRemark());
				// 更新していない場合
				if (workType && remark) {
					continue;
				}
				// 論理削除
				logicalDelete(dao, dto.getTmmScheduleDateId());
			}
			// レコード識別ID最大値をインクリメントしてDTOに設定
			dto.setTmmScheduleDateId(dao.nextRecordId());
			// 登録処理
			dao.insert(dto);
		}
	}
	
	/**
	 * 勤務形態が有効であるかを確認する。<br>
	 * 有効でない場合には、エラーメッセージを設定する。<br>
	 * @param workTypeCode  勤務形態コード
	 * @param scheduleDate 日(カレンダ日情報の日)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void checkWorkTypeActive(String workTypeCode, Date scheduleDate) throws MospException {
		// 勤務形態コードが空白である場合
		if (MospUtility.isEmpty(workTypeCode)) {
			// 確認不要
			return;
		}
		// 勤務形態コードが法定休日か所定休日である場合
		if (TimeUtility.isLegalOrPrescribed(workTypeCode)) {
			// 確認不要
			return;
		}
		// 勤務形態エンティティを取得
		WorkTypeEntityInterface workType = timeMaster.getWorkTypeEntity(workTypeCode, scheduleDate);
		// 勤務形態が有効でない場合
		if (PlatformUtility.isDtoActivate(workType.getWorkType()) == false) {
			// エラーメッセージを設定
			String fieldName = TimeNamingUtility.workType(mospParams);
			PfMessageUtility.addErrorInactive(mospParams, fieldName, workTypeCode, scheduleDate);
		}
	}
	
}
