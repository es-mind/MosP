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

import java.util.Date;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.time.base.TimeBean;
import jp.mosp.time.bean.LimitStandardRegistBeanInterface;
import jp.mosp.time.bean.TimeSettingBeanInterface;
import jp.mosp.time.bean.TimeSettingReferenceBeanInterface;
import jp.mosp.time.dto.settings.LimitStandardDtoInterface;
import jp.mosp.time.entity.TimeSettingEntityInterface;
import jp.mosp.time.settings.vo.TimeSettingCardVo;
import jp.mosp.time.utils.TimeUtility;

/**
 * 勤怠設定追加処理基クラス。<br>
 * アドオンで勤怠設定に処理を追加したい場合に用いる。<br>
 */
public abstract class TimeSettingBaseBean extends TimeBean implements TimeSettingBeanInterface {
	
	/**
	 * 勤怠設定参照処理。<br>
	 */
	protected TimeSettingReferenceBeanInterface	refer;
	
	/**
	 * 限度基準登録処理。<br>
	 */
	protected LimitStandardRegistBeanInterface	regist;
	
	
	/**
	 * {@link TimeBean#TimeBean()}を実行する。<br>
	 */
	public TimeSettingBaseBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// Beanを準備
		refer = createBeanInstance(TimeSettingReferenceBeanInterface.class);
		regist = createBeanInstance(LimitStandardRegistBeanInterface.class);
	}
	
	@Override
	public void delete() {
		// 処理無し(限度基準の削除時に纏めて削除されるため)
	}
	
	/**
	 * 勤怠設定エンティティを取得する。<br>
	 * @return 勤怠設定エンティティ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected TimeSettingEntityInterface getTimeSettingEntity() throws MospException {
		// 勤怠設定コード及び有効日を取得
		String workSettingCode = getWorkSettingCode();
		Date activateDate = getActivateDate();
		// 勤怠設定エンティティを取得
		return refer.getEntityForKey(workSettingCode, activateDate);
	}
	
	/**
	 * 限度基準情報を作成する。<br>
	 * @param term          期間
	 * @param limitTime     限度時間
	 * @param attentionTime 注意時間
	 * @param warningTime   警告時間
	 * @return 限度基準情報
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected LimitStandardDtoInterface createLimitStandardDto(String term, int limitTime, int attentionTime,
			int warningTime) throws MospException {
		// 限度基準情報を準備
		LimitStandardDtoInterface dto = regist.getInitDto();
		// 限度基準情報に値を設定
		dto.setWorkSettingCode(getWorkSettingCode());
		dto.setActivateDate(getActivateDate());
		// 期間にコンプライアンス区分を設定
		dto.setTerm(term);
		// 限度時間を設定
		dto.setLimitTime(limitTime);
		// 注意時間を設定
		dto.setAttentionTime(attentionTime);
		// 警告時間を設定
		dto.setWarningTime(warningTime);
		// 限度基準情報を取得
		return dto;
	}
	
	/**
	 * VOに値を設定する。<br>
	 * @param parameterId パラメータID
	 * @param value       値
	 */
	protected void setParam(String parameterId, String value) {
		// VOを準備
		TimeSettingCardVo vo = (TimeSettingCardVo)mospParams.getVo();
		// 値を設定
		vo.putAddonParam(parameterId, value);
	}
	
	/**
	 * VOから入力値を分に変換して取得する。<br>
	 * @param parameterId パラメータID
	 * @return 分
	 */
	protected int getTimeParam(String parameterId) {
		// VOを準備
		TimeSettingCardVo vo = (TimeSettingCardVo)mospParams.getVo();
		// 値を取得
		return TimeUtility.getTime(vo.getAddonParam(parameterId));
	}
	
	/**
	 * VOから入力値をintに変換して取得する。<br>
	 * @param parameterId パラメータID
	 * @return 入力値(int)
	 */
	protected int getParam(String parameterId) {
		// VOを準備
		TimeSettingCardVo vo = (TimeSettingCardVo)mospParams.getVo();
		// 値を取得
		return MospUtility.getInt(vo.getAddonParam(parameterId));
	}
	
	/**
	 * VOから勤怠設定コードを取得する。<br>
	 * @return 勤怠設定コード
	 */
	protected String getWorkSettingCode() {
		// VOを準備
		TimeSettingCardVo vo = (TimeSettingCardVo)mospParams.getVo();
		// 勤怠設定コードを取得
		return vo.getTxtSettingCode();
	}
	
	/**
	 * VOから有効日を取得する。<br>
	 * @return 有効日
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected Date getActivateDate() throws MospException {
		// VOを準備
		TimeSettingCardVo vo = (TimeSettingCardVo)mospParams.getVo();
		// 年月日を取得
		String year = vo.getTxtEditActivateYear();
		String month = vo.getTxtEditActivateMonth();
		String day = vo.getTxtEditActivateDay();
		// 有効日を取得
		return DateUtility.getDate(year, month, day);
	}
	
}
