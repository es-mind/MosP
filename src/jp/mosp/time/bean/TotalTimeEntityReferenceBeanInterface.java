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
package jp.mosp.time.bean;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.bean.system.PlatformMasterBeanInterface;
import jp.mosp.time.entity.CutoffEntityInterface;
import jp.mosp.time.entity.RequestDetectEntityInterface;
import jp.mosp.time.entity.TotalTimeEntityInterface;

/**
 * 勤怠集計関連エンティティ取得処理インターフェース。<br>
 */
public interface TotalTimeEntityReferenceBeanInterface extends BaseBeanInterface {
	
	/**
	 * 勤怠集計エンティティを取得する。<br>
	 * <br>
	 * @param personalId  個人ID
	 * @param targetYear  対象年
	 * @param targetMonth 対象月
	 * @param cutoff      締日エンティティ
	 * @return 勤怠集計エンティティ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	TotalTimeEntityInterface getTotalTimeEntity(String personalId, int targetYear, int targetMonth,
			CutoffEntityInterface cutoff) throws MospException;
	
	/**
	 * 申請検出エンティティを取得する。<br>
	 * <br>
	 * 勤怠集計エンティティから必要な情報を取得する。<br>
	 * {@link TotalTimeEntityInterface#total()}
	 * を実施すると承認済でない申請が除去されるため、
	 * その前に取得する必要がある。<br>
	 * <br>
	 * @param totalTimeEntity 勤怠集計エンティティ
	 * @return 申請検出エンティティ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	RequestDetectEntityInterface getRequestDetectEntity(TotalTimeEntityInterface totalTimeEntity) throws MospException;
	
	/**
	 * 申請検出エンティティを取得する。<br>
	 * <br>
	 * @param personalId  個人ID
	 * @param targetYear  対象年
	 * @param targetMonth 対象月
	 * @param cutoffDate  締日
	 * @return 申請検出エンティティ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	RequestDetectEntityInterface getRequestDetectEntity(String personalId, int targetYear, int targetMonth,
			int cutoffDate) throws MospException;
	
	/**
	 * 勤怠関連マスタ参照クラスを設定する。<br>
	 * <br>
	 * 勤怠関連マスタ参照クラスはinitBeanでは生成せず、
	 * 別のBeanから設定される。<br>
	 * より多くのBeanで勤怠関連マスタ参照クラスを共有することで、
	 * パフォーマンスの向上を図る。<br>
	 * <br>
	 * @param timeMaster 勤怠関連マスタ参照クラス
	 */
	void setTimeMasterBean(TimeMasterBeanInterface timeMaster);
	
	/**
	 * プラットフォームマスタ参照クラスを設定する。<br>
	 * <br>
	 * 人事情報参照クラスはinitBeanでは生成せず、
	 * 別のBeanから設定される。<br>
	 * より多くのBeanで人事情報参照クラスを共有することで、
	 * パフォーマンスの向上を図る。<br>
	 * <br>
	 * @param platformMaster プラットフォームマスタ参照クラス
	 */
	void setPlatformMasterBean(PlatformMasterBeanInterface platformMaster);
	
}
