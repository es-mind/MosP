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
package jp.mosp.platform.bean.workflow.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.workflow.ApprovalRouteUnitRegistBeanInterface;
import jp.mosp.platform.dao.workflow.ApprovalRouteDaoInterface;
import jp.mosp.platform.dao.workflow.ApprovalRouteUnitDaoInterface;
import jp.mosp.platform.dto.workflow.ApprovalRouteDtoInterface;
import jp.mosp.platform.dto.workflow.ApprovalRouteUnitDtoInterface;
import jp.mosp.platform.dto.workflow.impl.PfaApprovalRouteUnitDto;

/**
 * 承認ルートユニットマスタ登録クラス。<br>
 */
public class ApprovalRouteUnitRegistBean extends PlatformBean implements ApprovalRouteUnitRegistBeanInterface {
	
	/**
	 * 承認ルートユニットマスタDAO。<br>
	 */
	protected ApprovalRouteUnitDaoInterface	dao;
	
	/**
	 * 承認ルートマスタDAO。<br>
	 */
	protected ApprovalRouteDaoInterface		routeDao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public ApprovalRouteUnitRegistBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// DAOを準備
		dao = createDaoInstance(ApprovalRouteUnitDaoInterface.class);
		routeDao = createDaoInstance(ApprovalRouteDaoInterface.class);
	}
	
	@Override
	public ApprovalRouteUnitDtoInterface getInitDto() {
		return new PfaApprovalRouteUnitDto();
		
	}
	
	@Override
	public void add(ApprovalRouteUnitDtoInterface dto) throws MospException {
		// DTOの妥当性確認
		validate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 履歴追加情報の検証
		checkAdd(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setPfaApprovalRouteUnitId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void delete(ApprovalRouteUnitDtoInterface dto) throws MospException {
		// レコード識別IDを取得。
		dto.setPfaApprovalRouteUnitId(getRecordID(dto));
		// 削除対象ユニット情報が使用されていないかを確認
		checkDelete(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 論理削除
		logicalDelete(dao, dto.getPfaApprovalRouteUnitId());
	}
	
	@Override
	public void insert(ApprovalRouteUnitDtoInterface dto) throws MospException {
		// DTOの妥当性確認
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
		dto.setPfaApprovalRouteUnitId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void update(ApprovalRouteUnitDtoInterface dto) throws MospException {
		// レコード識別IDを取得。
		dto.setPfaApprovalRouteUnitId(getRecordID(dto));
		// DTOの妥当性確認
		validate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 履歴更新情報の検証
		checkUpdate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 論理削除
		logicalDelete(dao, dto.getPfaApprovalRouteUnitId());
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setPfaApprovalRouteUnitId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void update(long[] idArray, Date activateDate, int inactivateFlag) throws MospException {
		// 一括更新処理
		for (String code : getCodeList(idArray)) {
			// ルート情報取得
			ApprovalRouteDtoInterface routeDto = routeDao.findForKey(code, activateDate);
			// ルート情報が存在しない場合履歴追加する
			if (routeDto == null) {
				// 対象承認ルート設定情報における有効日以前で最新の情報を取得
				routeDto = routeDao.findForInfo(code, activateDate);
				if (routeDto == null) {
					// 有効日以前で最新の情報も存在しなければ履歴追加処理を行わない
					continue;
				}
				// 承認ルートユニットマスタリスト取得
				List<ApprovalRouteUnitDtoInterface> routeUnitList = dao.findForRouteList(code,
						routeDto.getActivateDate());
				// 承認ルートユニットマスタリスト毎に処理
				for (ApprovalRouteUnitDtoInterface routeUnitDto : routeUnitList) {
					// DTOに有効日、無効フラグを設定
					routeUnitDto.setActivateDate(activateDate);
					routeUnitDto.setInactivateFlag(inactivateFlag);
					// 履歴追加情報の検証
					checkAdd(routeUnitDto);
					if (mospParams.hasErrorMessage()) {
						// エラーが存在したら履歴追加処理をしない
						continue;
					}
					// レコード識別ID最大値をインクリメントしてDTOに設定
					routeUnitDto.setPfaApprovalRouteUnitId(dao.nextRecordId());
					// 登録処理
					dao.insert(routeUnitDto);
				}
			} else {
				// 承認ルートユニットマスタリスト取得
				List<ApprovalRouteUnitDtoInterface> routeUnitList = dao.findForRouteList(code, activateDate);
				// 承認ルートユニットマスタリスト毎に処理
				for (ApprovalRouteUnitDtoInterface routeUnitDto : routeUnitList) {
					// DTOに無効フラグを設定
					routeUnitDto.setInactivateFlag(inactivateFlag);
					// 履歴更新情報の検証
					checkUpdate(routeUnitDto);
					if (mospParams.hasErrorMessage()) {
						// エラーが存在したら履歴更新処理をしない
						continue;
					}
					// 論理削除
					logicalDelete(dao, routeUnitDto.getPfaApprovalRouteUnitId());
					// レコード識別ID最大値をインクリメントしてDTOに設定
					routeUnitDto.setPfaApprovalRouteUnitId(dao.nextRecordId());
					// 登録処理
					dao.insert(routeUnitDto);
				}
			}
		}
	}
	
	/**
	 * 履歴追加時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkAdd(ApprovalRouteUnitDtoInterface dto) throws MospException {
		// 対象レコードの有効日が重複していないかを確認
		checkDuplicateAdd(dao.findForKey(dto.getRouteCode(), dto.getActivateDate(), dto.getApprovalStage()));
		
	}
	
	/**
	 * 登録情報の妥当性を確認する。
	 * @param dto 対象DTO
	 */
	protected void validate(ApprovalRouteUnitDtoInterface dto) {
		// 処理無し
	}
	
	/**
	 * 新規登録時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkInsert(ApprovalRouteUnitDtoInterface dto) throws MospException {
		// 対象レコードの有効日が重複していないかを確認
		checkDuplicateInsert(dao.findForHistory(dto.getRouteCode(), dto.getApprovalStage()));
		
	}
	
	/**
	 * レコード識別IDを取得する。<br>
	 * @param dto 	対象DTO
	 * @return レコード識別ID
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected long getRecordID(ApprovalRouteUnitDtoInterface dto) throws MospException {
		// ユニット情報を取得する。
		ApprovalRouteUnitDtoInterface subDto = dao.findForKey(dto.getRouteCode(), dto.getActivateDate(),
				dto.getApprovalStage());
		// レコード識別IDを返す。
		return subDto.getPfaApprovalRouteUnitId();
		
	}
	
	/**
	 * 削除時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	private void checkDelete(ApprovalRouteUnitDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getPfaApprovalRouteUnitId());
		
	}
	
	/**
	 * 履歴更新時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkUpdate(ApprovalRouteUnitDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getPfaApprovalRouteUnitId());
		
	}
	
	/**
	 * 承認ルート情報リストを取得する。<br>
	 * 一括処理時に承認ルート情報をまず更新するため、排他確認は行わない。<br>
	 * @param idArray レコード識別ID配列
	 * @return 承認ルート情報リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected List<String> getCodeList(long[] idArray) throws MospException {
		// リスト準備
		List<String> list = new ArrayList<String>();
		// レコード識別IDからDTOを取得し、コードをリストへ追加
		for (long id : idArray) {
			// レコード識別IDから対象DTOを取得
			ApprovalRouteDtoInterface dto = (ApprovalRouteDtoInterface)routeDao.findForKey(id, false);
			// 対象コードをリストへ追加
			list.add(dto.getRouteCode());
		}
		return list;
	}
	
}
