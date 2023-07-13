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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.time.bean.CutoffUtilBeanInterface;
import jp.mosp.time.bean.SubordinateSearchBeanInterface;
import jp.mosp.time.bean.TotalTimeSearchBeanInterface;
import jp.mosp.time.dao.settings.TotalTimeDataDaoInterface;
import jp.mosp.time.dto.settings.SubordinateListDtoInterface;
import jp.mosp.time.dto.settings.TotalTimeDataDtoInterface;
import jp.mosp.time.entity.CutoffEntityInterface;

/**
 * 勤怠集計結果検索クラス。
 */
public class TotalTimeSearchBean extends SubordinateSearchBean implements TotalTimeSearchBeanInterface {
	
	/**
	 * 勤怠集計データDAO。
	 */
	private TotalTimeDataDaoInterface		totalTimeDataDao;
	
	/**
	 * 締日ユーティリティクラス。
	 */
	private CutoffUtilBeanInterface			cutoffUtil;
	
	/**
	 * 部下一覧検索クラス。
	 */
	private SubordinateSearchBeanInterface	subordinateSearch;
	
	/**
	 * 締日コード。
	 */
	protected String						cutoffCode;
	
	
	/**
	 * {@link SubordinateSearchBean#SubordinateSearchBean()}を実行する。<br>
	 */
	public TotalTimeSearchBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// 継承元の処理を実施
		super.initBean();
		// 勤怠集計データDAO取得
		totalTimeDataDao = createDaoInstance(TotalTimeDataDaoInterface.class);
		// 締日ユーティリティクラス
		cutoffUtil = createBeanInstance(CutoffUtilBeanInterface.class);
		// 部下検索クラス
		subordinateSearch = createBeanInstance(SubordinateSearchBeanInterface.class);
	}
	
	@Override
	public List<SubordinateListDtoInterface> getSearchList() throws MospException {
		// 勤怠集計結果リスト準備
		List<SubordinateListDtoInterface> list = new ArrayList<SubordinateListDtoInterface>();
		// 対象年月において対象締日コードが適用されている個人IDのセットを取得
		Set<String> personalIdSet = cutoffUtil.getCutoffPersonalIdSet(cutoffCode, targetYear, targetMonth);
		// 締日エンティティを取得
		CutoffEntityInterface cutoff = timeMaster.getCutoff(cutoffCode, targetYear, targetMonth);
		// 人事マスタ情報検索条件設定(対象日：締期間における基準日、期間：締期間)
		setTargetDate(cutoff.getCutoffTermTargetDate(targetYear, targetMonth, mospParams));
		setStartDate(cutoff.getCutoffFirstDate(targetYear, targetMonth, mospParams));
		setEndDate(cutoff.getCutoffLastDate(targetYear, targetMonth, mospParams));
		// 人事マスタ情報検索条件設定
		setEmployeeCodeType(PlatformConst.SEARCH_FORWARD_MATCH);
		setStateType(PlatformConst.EMPLOYEE_STATE_PRESENCE);
		// 前日までフラグ(承認状態取得用)を取得
		boolean searchBeforeDay = approvalBeforeDay.equals(MospConst.CHECKBOX_ON);
		// 人事マスタ情報検索条件から締日の対象となる人事情報のリストを取得
		List<HumanDtoInterface> humanList = search();
		// 人事情報毎に処理
		for (HumanDtoInterface human : humanList) {
			// 個人IDを取得
			String personalId = human.getPersonalId();
			// 締日コードが適用されている個人IDセットに含まれているか確認
			if (personalIdSet.contains(personalId) == false) {
				continue;
			}
			// 勤怠集計情報取得
			TotalTimeDataDtoInterface totalTimeDto = totalTimeDataDao.findForKey(personalId, targetYear, targetMonth);
			// 部下一覧情報取得
			SubordinateListDtoInterface dto = subordinateSearch.getSubordinateListDto(human, targetYear, targetMonth,
					totalTimeDto, searchBeforeDay);
			// 部下一覧情報が検索条件に合致する場合
			if (isApprovalConditionMatch(dto) && isCalcConditionMatch(dto)) {
				// 部下一覧情報リストに追加
				list.add(dto);
			}
		}
		return list;
	}
	
	@Override
	public void setCutoffCode(String cutoffCode) {
		this.cutoffCode = cutoffCode;
	}
	
}
