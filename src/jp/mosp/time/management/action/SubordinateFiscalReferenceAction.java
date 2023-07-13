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
package jp.mosp.time.management.action;

import java.util.Date;

import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.BaseVo;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.utils.MonthUtility;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.time.base.TimeAction;
import jp.mosp.time.base.TimeVo;
import jp.mosp.time.bean.AttendanceTotalInfoBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dto.settings.SubordinateFiscalListDtoInterface;
import jp.mosp.time.management.vo.SubordinateFiscalReferenceVo;

/**
 * 勤怠統計情報年詳細クラス。<br>
 */
public class SubordinateFiscalReferenceAction extends TimeAction {
	
	/**
	 * 選択表示コマンド。<br>
	 * <br>
	 * 選択表示コマンド。有給休暇手動付与画面で選択した従業員のサーバ日付時点から1年間の有給休暇に関する詳細情報を表示する。<br>
	 */
	public static final String	CMD_SELECT_SHOW	= "TM2521";
	
	/**
	 * 検索コマンド。<br>
	 * <br>
	 * 操作者の指定にしたがって表示する対象年月を変更する。
	 * 前月・翌月ボタンといったものは現在表示している年月を基に画面遷移を行う。<br>
	 */
	public static final String	CMD_SEARCH		= "TM2522";
	
	/**
	 * 再表示コマンド。<br>
	 * <br>
	 * 個別有給休暇確認画面の再表示を行う。<br>
	 */
	public static final String	CMD_RE_SHOW		= "TM2523";
	
	
	/**
	 * {@link TimeAction#TimeAction()}を実行する。<br>
	 */
	public SubordinateFiscalReferenceAction() {
		super();
		// パンくずリスト用コマンド設定
		topicPathCommand = CMD_RE_SHOW;
	}
	
	@Override
	protected BaseVo getSpecificVo() {
		return new SubordinateFiscalReferenceVo();
	}
	
	@Override
	public void action() throws MospException {
		// コマンド毎の処理
		if (mospParams.getCommand().equals(CMD_SELECT_SHOW)) {
			prepareVo(false, false);
			// 選択表示
			show();
		} else if (mospParams.getCommand().equals(CMD_SEARCH)) {
			// 検索
			prepareVo();
			search();
		} else if (mospParams.getCommand().equals(CMD_RE_SHOW)) {
			prepareVo(true, false);
			// 再表示
			show();
		}
	}
	
	/**
	 * 初期表示処理を行う。<br>
	 * @throws MospException 例外処理発生時
	 */
	protected void show() throws MospException {
		// VO準備
		SubordinateFiscalReferenceVo vo = (SubordinateFiscalReferenceVo)mospParams.getVo();
		// 個人ID取得
		String personalId = getTargetPersonalId();
		// システム日付取得
		Date targetDate = getTargetDate();
		int fiscalYear = getTargetYear();
		vo.setFiscalYear(fiscalYear);
		// 社員情報の設定
		if (personalId != null && personalId.isEmpty() == false) {
			// 個人ID取得
			setEmployeeInfo(personalId, targetDate);
		} else {
			// 個人ID取得
			setEmployeeInfo(vo.getPersonalId(), targetDate);
		}
		// 情報設定
		setList(personalId, fiscalYear);
	}
	
	/**
	 * 年月による検索を行う。<br>
	 * 対象個人IDはVOに保持されているものを用いる。<br>
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void search() throws MospException {
		// VO準備
		SubordinateFiscalReferenceVo vo = (SubordinateFiscalReferenceVo)mospParams.getVo();
		// 個人ID取得(VOから)
		String personalId = vo.getPersonalId();
		String searchMode = mospParams.getRequestParam(TimeConst.PRM_TRANSFER_SEARCH_MODE);
		if (TimeConst.SEARCH_BACK.equals(searchMode)) {
			// 前の場合
			personalId = vo.getPrevPersonalId();
		} else if (TimeConst.SEARCH_NEXT.equals(searchMode)) {
			// 次の場合
			personalId = vo.getNextPersonalId();
		}
		// 対象日取得
		Date targetDate = vo.getTargetDate();
		// 年月指定時の基準日取得
		Date yearMonthTargetDate = MonthUtility.getYearMonthTargetDate(DateUtility.getYear(targetDate),
				DateUtility.getMonth(targetDate), mospParams);
		// 基本情報チェック
		timeReference().holidayRequest().chkBasicInfo(personalId, yearMonthTargetDate);
		
		// 情報設定
		setList(personalId, vo.getFiscalYear());
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			// データ無しメッセージを設定
			PfMessageUtility.addMessageNoData(mospParams);
			return;
		}
		
	}
	
	/**
	 * 年度勤怠集計情報を設定する。<br>
	 * @param personalId 個人ID
	 * @param fiscalYear 対象年度
	 * @throws MospException 例外発生時
	 */
	public void setList(String personalId, int fiscalYear) throws MospException {
		// VO準備
		SubordinateFiscalReferenceVo vo = (SubordinateFiscalReferenceVo)mospParams.getVo();
		// クラス準備
		AttendanceTotalInfoBeanInterface info = timeReference().attendanceTotalInfo();
		// VOの人事情報フィールドを設定
		setVoHumanFields(personalId, vo.getTargetDate());
		// VOの前社員・次社員情報フィールドを設定
		setVoRollEmployeeFields();
		// 設定
		vo.setVoList(info.setFiscalYearAttendanceTotalList(personalId, fiscalYear));
	}
	
	/**
	 * VOの人事情報フィールドを設定する。<br>
	 * @param personalId 対象年
	 * @param targetDate 対象月
	 * @throws MospException 人事情報の取得に失敗した場合
	 */
	protected void setVoHumanFields(String personalId, Date targetDate) throws MospException {
		// VO準備
		TimeVo vo = (TimeVo)mospParams.getVo();
		// 人事マスタ情報取得
		HumanDtoInterface humanDto = reference().human().getHumanInfo(personalId, targetDate);
		// 人事マスタ情報確認
		if (humanDto == null) {
			return;
		}
		// VOに個人IDを設定
		vo.setPersonalId(humanDto.getPersonalId());
		// VOに社員コードを設定
		vo.setLblEmployeeCode(humanDto.getEmployeeCode());
		// VOに氏名を設定
		vo.setLblEmployeeName(getLastFirstName(humanDto.getLastName(), humanDto.getFirstName()));
		// 所属情報取得及び設定
		vo.setLblSectionName(reference().section().getSectionName(humanDto.getSectionCode(), targetDate));
		if (reference().section().useDisplayName()) {
			// 所属表示名称を設定
			vo.setLblSectionName(reference().section().getSectionDisplay(humanDto.getSectionCode(), targetDate));
		}
	}
	
	/**
	 * VOの前社員・次社員情報フィールドを設定する。<br>
	 */
	protected void setVoRollEmployeeFields() {
		// VO取得
		SubordinateFiscalReferenceVo vo = (SubordinateFiscalReferenceVo)mospParams.getVo();
		Object object = mospParams.getGeneralParam(TimeConst.PRM_ROLL_ARRAY);
		if (object != null) {
			vo.setRollArray((BaseDtoInterface[])object);
		}
		if (vo.getRollArray() == null || vo.getRollArray().length == 0) {
			return;
		}
		int i = 0;
		for (BaseDtoInterface baseDto : vo.getRollArray()) {
			SubordinateFiscalListDtoInterface dto = (SubordinateFiscalListDtoInterface)baseDto;
			if (vo.getPersonalId().equals(dto.getPersonalId())) {
				break;
			}
			i++;
		}
		// 前社員設定
		vo.setLblPrevEmployeeCode("");
		vo.setPrevPersonalId("");
		if (i > 0) {
			BaseDtoInterface baseDto = vo.getRollArray()[i - 1];
			SubordinateFiscalListDtoInterface dto = (SubordinateFiscalListDtoInterface)baseDto;
			vo.setLblPrevEmployeeCode(dto.getEmployeeCode());
			vo.setPrevPersonalId(dto.getPersonalId());
		}
		// 次社員設定
		vo.setLblNextEmployeeCode("");
		vo.setNextPersonalId("");
		if (i + 1 < vo.getRollArray().length) {
			BaseDtoInterface baseDto = vo.getRollArray()[i + 1];
			SubordinateFiscalListDtoInterface dto = (SubordinateFiscalListDtoInterface)baseDto;
			vo.setLblNextEmployeeCode(dto.getEmployeeCode());
			vo.setNextPersonalId(dto.getPersonalId());
		}
	}
}
