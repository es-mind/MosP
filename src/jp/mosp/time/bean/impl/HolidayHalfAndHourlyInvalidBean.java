package jp.mosp.time.bean.impl;

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.base.AdditionalLogicBeanInterface;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.time.bean.HolidayRequestReferenceBeanInterface;
import jp.mosp.time.dto.settings.HolidayRequestDtoInterface;
import jp.mosp.time.utils.TimeMessageUtility;
import jp.mosp.time.utils.TimeRequestUtility;

/**
 * 半日休暇と時間単位休暇の併用を不可とする処理。<br>
 */
public class HolidayHalfAndHourlyInvalidBean extends PlatformBean implements AdditionalLogicBeanInterface {
	
	/**
	 * 休暇申請参照処理。<br>
	 */
	protected HolidayRequestReferenceBeanInterface holidayRequestRefer;
	
	
	@Override
	public void initBean() throws MospException {
		// Beanの準備
		holidayRequestRefer = createBeanInstance(HolidayRequestReferenceBeanInterface.class);
	}
	
	@Override
	public boolean doAdditionalLogic(Object... objects) throws MospException {
		// 休暇申請情報と行インデックスを取得
		HolidayRequestDtoInterface holidayRequestDto = MospUtility.castObject(objects[1]);
		Integer row = MospUtility.castObject(objects[2]);
		// 時間単位休暇の重複を確認
		checkHalfAndHourlyHoliday(holidayRequestDto, row);
		// 追加処理有り
		return true;
	}
	
	/**
	 * 半日休暇と時間単位休暇の併用を確認する。<bt>
	 * @param holidayRequestDto 対象休暇申請情報
	 * @param row               行インデックス
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void checkHalfAndHourlyHoliday(HolidayRequestDtoInterface holidayRequestDto, Integer row)
			throws MospException {
		// 全休である場合
		if (TimeRequestUtility.isHolidayRangeAll(holidayRequestDto)) {
			// 確認不要
			return;
		}
		// 個人IDと対象日(半日休暇と時間単位休暇は日の範囲指定が不可)を取得
		String personalId = holidayRequestDto.getPersonalId();
		Date targetDate = holidayRequestDto.getRequestStartDate();
		// 休暇申請情報群を取得
		List<HolidayRequestDtoInterface> dtos = holidayRequestRefer.getHolidayRequestListOnWorkflow(personalId,
				targetDate, targetDate);
		// 休暇申請情報群にある同一ワークフロー番号の情報を入れ替え
		TimeRequestUtility.replaceWorkflowDto(dtos, holidayRequestDto);
		// 半日休暇と時間単位休暇が併用されている場合
		if (TimeRequestUtility.hasHolidayRangeHalf(dtos) && TimeRequestUtility.hasHolidayRangeHour(dtos)) {
			// エラーメッセージを設定
			TimeMessageUtility.addErrorHalfAndHourlyHoliday(mospParams, row);
		}
		
	}
	
}
