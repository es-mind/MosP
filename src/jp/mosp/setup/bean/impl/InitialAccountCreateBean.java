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
package jp.mosp.setup.bean.impl;

import java.util.Date;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.human.EntranceRegistBeanInterface;
import jp.mosp.platform.bean.human.HumanRegistBeanInterface;
import jp.mosp.platform.bean.system.UserAccountRegistBeanInterface;
import jp.mosp.platform.dto.human.EntranceDtoInterface;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.dto.system.UserMasterDtoInterface;
import jp.mosp.setup.bean.InitialAccountCreateBeanInterface;
import jp.mosp.setup.dto.InitialAccountParameterInterface;
import jp.mosp.setup.dto.impl.InitialAccountParameter;

/**
 * 初期アカウント登録処理。<br>
 */
public class InitialAccountCreateBean extends PlatformBean implements InitialAccountCreateBeanInterface {
	
	/**
	 * ユーザアカウント情報登録処理。<br>
	 */
	protected UserAccountRegistBeanInterface	userAccountRegist;
	
	/**
	 * 人事情報登録処理。<br>
	 */
	protected HumanRegistBeanInterface			humanRegist;
	
	/**
	 * 人事入社情報登録処理。<br>
	 */
	protected EntranceRegistBeanInterface		entranceRegist;
	
	
	@Override
	public void initBean() throws MospException {
		// Beanを準備
		userAccountRegist = createBeanInstance(UserAccountRegistBeanInterface.class);
		humanRegist = createBeanInstance(HumanRegistBeanInterface.class);
		entranceRegist = createBeanInstance(EntranceRegistBeanInterface.class);
	}
	
	@Override
	public InitialAccountParameterInterface getInitParameter() {
		return new InitialAccountParameter();
	}
	
	@Override
	public void execute(InitialAccountParameterInterface parameter) throws MospException {
		// 人事マスタ登録
		HumanDtoInterface dto = insertHuman(parameter);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 個人IDを取得
		String personalId = dto.getPersonalId();
		// 入社情報登録
		insertEntrance(parameter, personalId);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// ユーザマスタ登録
		insertUser(parameter, personalId);
		if (mospParams.hasErrorMessage()) {
			return;
		}
	}
	
	/**
	 * 人事情報を登録する。<br>
	 * @param parameter パラメータ
	 * @return 登録した人事情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected HumanDtoInterface insertHuman(InitialAccountParameterInterface parameter) throws MospException {
		// DTOに設定
		HumanDtoInterface dto = humanRegist.getInitDto();
		dto.setActivateDate(getActivateDate(parameter));
		dto.setEmployeeCode(parameter.getEmployeeCode());
		dto.setLastName(parameter.getLastName());
		dto.setFirstName(parameter.getFirstName());
		dto.setLastKana(parameter.getLastKana());
		dto.setFirstKana(parameter.getFirstKana());
		dto.setWorkPlaceCode(MospConst.STR_EMPTY);
		dto.setEmploymentContractCode(MospConst.STR_EMPTY);
		dto.setSectionCode(MospConst.STR_EMPTY);
		dto.setPositionCode(MospConst.STR_EMPTY);
		// メールアドレスは未使用
		dto.setMail(MospConst.STR_EMPTY);
		// 登録
		humanRegist.insert(dto);
		// 登録した人事情報を取得
		return dto;
	}
	
	/**
	 * 人事入社情報を登録する。<br>
	 * @param parameter パラメータ
	 * @param personalId 個人ID
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void insertEntrance(InitialAccountParameterInterface parameter, String personalId) throws MospException {
		EntranceDtoInterface dto = entranceRegist.getInitDto();
		// DTOに値をセットする。
		dto.setEntranceDate(parameter.getEntranceDate());
		dto.setPersonalId(personalId);
		// 登録
		entranceRegist.insert(dto);
	}
	
	/**
	 * ユーザアカウント情報を登録する。<br>
	 * @param parameter パラメータ
	 * @param personalId 個人ID
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void insertUser(InitialAccountParameterInterface parameter, String personalId) throws MospException {
		// ユーザ情報を準備
		UserMasterDtoInterface dto = userAccountRegist.getInitUserDto();
		// ユーザID
		String userId = parameter.getUserId();
		dto.setUserId(userId);
		dto.setActivateDate(getActivateDate(parameter));
		// 社員コードから個人IDを取得して設定
		dto.setPersonalId(personalId);
		// 初期ロールコード(セットアップ)設定
		dto.setRoleCode(parameter.getRoleCode());
		// 無効フラグ設定(有効)
		dto.setInactivateFlag(MospConst.DELETE_FLAG_OFF);
		// 新規登録(デフォルトユーザ追加ロール情報は登録しない)
		userAccountRegist.insert(dto, false);
	}
	
	/**
	 * 有効日を取得する。<br>
	 * 有効日が入力されている場合、その日付。<br>
	 * nullの場合、入社日が未来の場合、本日の日付を返す。<br>
	 * それ以外の場合は入社日を返す。<br>
	 * @param parameter パラメータ
	 * @return 有効日
	 */
	protected Date getActivateDate(InitialAccountParameterInterface parameter) {
		// 各日付を準備
		Date entranceDate = parameter.getEntranceDate();
		Date systemDate = DateUtility.getSystemDate();
		Date activateDate = parameter.getActivateDate();
		// 有効日が入力されている場合
		if (MospUtility.isEmpty(activateDate) == false) {
			// 入社日を取得
			return activateDate;
		}
		// 入社日が未来の場合
		if (MospUtility.compare(systemDate, entranceDate) < 0) {
			// 本日を取得
			return systemDate;
		}
		// 入社日を取得
		return entranceDate;
	}
	
}
