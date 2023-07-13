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
package jp.mosp.platform.bean.system.impl;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.framework.utils.RoleUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.system.CheckAfterRegistUserBeanInterface;
import jp.mosp.platform.bean.system.RoleReferenceBeanInterface;
import jp.mosp.platform.bean.system.UserAccountRegistBeanInterface;
import jp.mosp.platform.bean.system.UserExtraRoleRegistBeanInterface;
import jp.mosp.platform.bean.system.UserMasterReferenceBeanInterface;
import jp.mosp.platform.bean.system.UserMasterRegistBeanInterface;
import jp.mosp.platform.bean.system.UserPasswordRegistBeanInterface;
import jp.mosp.platform.dto.system.UserExtraRoleDtoInterface;
import jp.mosp.platform.dto.system.UserMasterDtoInterface;
import jp.mosp.platform.dto.system.UserPasswordDtoInterface;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.platform.utils.PlatformUtility;

/**
 * ユーザアカウント情報登録処理。<br>
 * ユーザ情報、ユーザ追加ロール情報、ユーザパスワード情報の登録処理を行う。<br>
 */
public class UserAccountRegistBean extends PlatformBean implements UserAccountRegistBeanInterface {
	
	/**
	 * MosPアプリケーション設定キー(ユーザアカウント情報登録後の確認Beanクラス群)。
	 */
	protected static final String				APP_BEANS	= "BeansCheckAfterRegistUser";
	
	/**
	 * ユーザ情報登録処理。<br>
	 */
	protected UserMasterRegistBeanInterface		userRegist;
	
	/**
	 * ユーザ追加ロール情報登録処理。<br>
	 */
	protected UserExtraRoleRegistBeanInterface	extraRoleRegist;
	
	/**
	 * ユーザパスワード情報登録処理。<br>
	 */
	protected UserPasswordRegistBeanInterface	passwordRegist;
	
	/**
	 * ユーザ情報参照処理。<br>
	 */
	protected UserMasterReferenceBeanInterface	userRefer;
	
	/**
	 * ロール参照処理。<br>
	 */
	protected RoleReferenceBeanInterface		roleRefer;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public UserAccountRegistBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// Beanを準備
		userRegist = createBeanInstance(UserMasterRegistBeanInterface.class);
		extraRoleRegist = createBeanInstance(UserExtraRoleRegistBeanInterface.class);
		passwordRegist = createBeanInstance(UserPasswordRegistBeanInterface.class);
		userRefer = createBeanInstance(UserMasterReferenceBeanInterface.class);
		roleRefer = createBeanInstance(RoleReferenceBeanInterface.class);
	}
	
	@Override
	public UserMasterDtoInterface getInitUserDto() {
		// 初期ユーザ情報を取得
		return userRegist.getInitDto();
	}
	
	@Override
	public Set<UserExtraRoleDtoInterface> getInitExtraRoleDtos(int number) {
		// 初期ユーザ追加ロール情報群を取得
		return extraRoleRegist.getInitDtos(number);
	}
	
	@Override
	public UserExtraRoleDtoInterface getInitExtraRoleDto() {
		// 初期ユーザ追加ロール情報を取得
		return extraRoleRegist.getInitDto();
	}
	
	@Override
	public void insert(UserMasterDtoInterface userDto, boolean needExtraRoles) throws MospException {
		// ユーザ情報を新規登録
		insert(userDto);
		// 処理結果を確認
		if (mospParams.hasErrorMessage()) {
			// 処理終了
			return;
		}
		// ユーザID及び有効日を取得
		String userId = userDto.getUserId();
		Date activateDate = userDto.getActivateDate();
		// デフォルトユーザ追加ロール情報が必要である場合
		if (needExtraRoles) {
			// デフォルトユーザ追加ロール情報群を登録
			extraRoleRegist.regist(getDefaultExtraRoleDtos(userId, activateDate));
		}
		// ユーザアカウント情報登録後の確認
		checkAfterRegist();
	}
	
	@Override
	public void insert(UserMasterDtoInterface userDto, Set<UserExtraRoleDtoInterface> extraRoleDtos)
			throws MospException {
		// ユーザ情報を新規登録
		insert(userDto);
		// 処理結果を確認
		if (mospParams.hasErrorMessage()) {
			// 処理終了
			return;
		}
		// ユーザ追加ロール情報群を登録
		extraRoleRegist.regist(extraRoleDtos);
		// ユーザアカウント情報登録後の確認
		checkAfterRegist();
	}
	
	@Override
	public void insert(UserMasterDtoInterface userDto, Set<UserExtraRoleDtoInterface> extraRoleDtos, String password)
			throws MospException {
		// ユーザ情報を新規登録
		userRegist.insert(userDto);
		// 処理結果を確認
		if (mospParams.hasErrorMessage()) {
			// 処理終了
			return;
		}
		// ユーザID及び有効日を取得
		String userId = userDto.getUserId();
		Date activateDate = userDto.getActivateDate();
		// ユーザパスワード情報を登録
		passwordRegist.regist(userId, getChangeDate(activateDate), password);
		// ユーザ追加ロール情報群を登録
		extraRoleRegist.regist(extraRoleDtos);
		// ユーザアカウント情報登録後の確認
		checkAfterRegist();
	}
	
	@Override
	public void add(UserMasterDtoInterface userDto, Set<UserExtraRoleDtoInterface> extraRoleDtos) throws MospException {
		// ユーザ情報を履歴追加
		userRegist.add(userDto);
		// ユーザ追加ロール情報群を登録
		extraRoleRegist.regist(extraRoleDtos);
		// 処理結果を確認
		if (mospParams.hasErrorMessage()) {
			// 処理終了
			return;
		}
		// ユーザアカウント情報登録後の確認
		checkAfterRegist();
	}
	
	@Override
	public void update(UserMasterDtoInterface userDto, Set<UserExtraRoleDtoInterface> extraRoleDtos)
			throws MospException {
		// ユーザ情報を履歴更新
		userRegist.update(userDto);
		// ユーザ追加ロール情報群を登録
		extraRoleRegist.regist(extraRoleDtos);
		// 処理結果を確認
		if (mospParams.hasErrorMessage()) {
			// 処理終了
			return;
		}
		// ユーザアカウント情報登録後の確認
		checkAfterRegist();
	}
	
	@Override
	public void update(long[] idArray, Date activateDate, int inactivateFlag) throws MospException {
		// レコード識別ID配列の妥当性を確認
		validateAryId(idArray);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 更新処理
		for (String userId : getUserIds(idArray)) {
			// 対象ユーザ情報のロールコードを更新
			update(userId, activateDate, inactivateFlag);
		}
		// ユーザアカウント情報登録後の確認
		checkAfterRegist();
	}
	
	@Override
	public void update(long[] idArray, Date activateDate, String roleCode) throws MospException {
		// レコード識別ID配列の妥当性を確認
		validateAryId(idArray);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 更新処理
		for (String userId : getUserIds(idArray)) {
			// 対象ユーザ情報のロールコードを更新
			update(userId, activateDate, roleCode);
		}
		// ユーザアカウント情報登録後の確認
		checkAfterRegist();
	}
	
	@Override
	public void delete(long[] idArray) throws MospException {
		// レコード識別ID配列の妥当性を確認
		validateAryId(idArray);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// ユーザ情報及びユーザ追加ロール情報を論理削除
		delete(getUserDtos(idArray));
	}
	
	@Override
	public void delete(String personalId) throws MospException {
		// ユーザ情報及びユーザ追加ロール情報を論理削除
		delete(userRefer.getUserHistoryForPersonalId(personalId));
	}
	
	@Override
	public void regist(Collection<UserMasterDtoInterface> userDtos) throws MospException {
		// ユーザ情報毎に処理
		for (UserMasterDtoInterface userDto : userDtos) {
			// ユーザ情報が存在しない場合
			if (userDto == null) {
				// 次のユーザ情報へ
				continue;
			}
			// ユーザ情報を登録する
			regist(userDto);
		}
		// ユーザアカウント情報登録後の確認
		checkAfterRegist();
	}
	
	@Override
	public void initPassword(long[] idArray) throws MospException {
		// パスワードを初期化
		passwordRegist.initPassword(getUserIds(idArray));
	}
	
	@Override
	public void updatePassword(String userId, String password) throws MospException {
		// ユーザパスワード情報を準備
		UserPasswordDtoInterface dto = passwordRegist.getInitDto();
		// ユーザパスワード情報に値を設定
		dto.setUserId(userId);
		dto.setPassword(password);
		dto.setChangeDate(getSystemDate());
		// ユーザパスワード情報を更新
		passwordRegist.regist(dto);
	}
	
	@Override
	public void validate(UserMasterDtoInterface userDto, Integer row) throws MospException {
		// 登録情報の妥当性を確認
		userRegist.validate(userDto, row);
	}
	
	@Override
	public void checkAfterRegist() throws MospException {
		// ユーザアカウント情報登録後確認Beanクラス群(クラス名)取得
		String[] classNames = mospParams.getApplicationProperties(APP_BEANS);
		// Beanクラス毎に処理
		for (String className : classNames) {
			// Beanクラスを取得
			CheckAfterRegistUserBeanInterface bean = (CheckAfterRegistUserBeanInterface)createBean(className);
			// 確認処理
			bean.check();
		}
	}
	
	/**
	 * ユーザ情報を新規登録する。<br>
	 * ユーザ追加ロール情報は登録しない。<br>
	 * @param userDto ユーザ情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void insert(UserMasterDtoInterface userDto) throws MospException {
		// ユーザ情報を新規登録
		userRegist.insert(userDto);
		// 処理結果を確認
		if (mospParams.hasErrorMessage()) {
			// 処理終了
			return;
		}
		// ユーザID及び有効日を取得
		String userId = userDto.getUserId();
		Date activateDate = userDto.getActivateDate();
		// 初期パスワードを登録
		passwordRegist.initPassword(userId, getChangeDate(activateDate));
	}
	
	/**
	 * ユーザ情報を登録する。<br>
	 * @param userDto ユーザ情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void regist(UserMasterDtoInterface userDto) throws MospException {
		// ユーザID及び有効日を取得
		String userId = userDto.getUserId();
		Date activateDate = userDto.getActivateDate();
		// 対象ユーザIDの履歴(有効日昇順)を取得
		List<UserMasterDtoInterface> list = userRefer.getUserHistory(userId);
		// ユーザ情報が存在しない場合
		if (list.isEmpty()) {
			// 新規登録
			insert(userDto);
			// 処理終了
			return;
		}
		// 対象リスト(有効日昇順)から対象日以前で最新の有効日を取得
		Date latestActivateDate = PlatformUtility.getLatestActivateDate(list, activateDate);
		// 有効日が同じユーザ情報が存在する場合
		if (DateUtility.isSame(activateDate, latestActivateDate)) {
			// ユーザ情報を履歴更新
			userRegist.update(userDto);
			// 処理終了
			return;
		}
		// ユーザ情報を履歴追加
		userRegist.add(userDto);
		// 有効日以前のユーザ情報が存在する場合
		if (latestActivateDate != null) {
			// ユーザ追加ロール情報をコピー(履歴追加)
			extraRoleRegist.copy(userId, latestActivateDate, activateDate);
		}
	}
	
	/**
	 * 対象ユーザ情報を更新する。<br>
	 * <br>
	 * 更新する内容は、更新パラメータに依る。<br>
	 * 文字列であればロールコードを、数値であれば無効フラグを更新する。<br>
	 * <br>
	 * 対象ユーザにおける有効日の情報が存在すれば履歴更新を、
	 * 存在しなければ履歴追加を行う。<br>
	 * <br>
	 * @param userId         ユーザID
	 * @param activateDate   有効日
	 * @param object         更新パラメータ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void update(String userId, Date activateDate, Object object) throws MospException {
		// 対象ユーザにおける有効日以前で最新の情報を取得
		UserMasterDtoInterface userDto = userRefer.getUserInfo(userId, activateDate);
		// 対象ユーザにおける有効日以前で最新の情報が取得できなかった場合
		if (userDto == null) {
			// エラーメッセージを設定
			PfMessageUtility.addErrorCodeNotExistBeforeDate(mospParams, userId);
			// 処理終了
			return;
		}
		// ユーザ情報に更新パラメータを設定
		setUpdateParam(userDto, object);
		// 対象ユーザにおける有効日の情報が存在する場合
		if (DateUtility.isSame(activateDate, userDto.getActivateDate())) {
			// 履歴更新
			userRegist.update(userDto);
			// 処理終了
			return;
		}
		// ユーザ追加ロール情報コピー元有効日を取得
		Date fromActivateDate = userDto.getActivateDate();
		// ユーザ情報に有効日を設定
		userDto.setActivateDate(activateDate);
		// 履歴追加
		userRegist.add(userDto);
		// ユーザ追加ロール情報をコピー(履歴追加)
		extraRoleRegist.copy(userId, fromActivateDate, activateDate);
	}
	
	/**
	 * ユーザ情報に更新パラメータを設定する。<br>
	 * 文字列であればロールコードを、数値であれば無効フラグを設定する。<br>
	 * <br>
	 * @param userDto ユーザ情報
	 * @param object  更新パラメータ
	 */
	protected void setUpdateParam(UserMasterDtoInterface userDto, Object object) {
		// 更新パラメータを文字列として取得
		String param = String.valueOf(object);
		// 更新パラメータが文字列である場合
		if (object instanceof String) {
			// ロールコードを設定
			userDto.setRoleCode(param);
		}
		// 更新パラメータが数値である場合
		if (object instanceof Integer) {
			// 無効フラグを設定
			userDto.setInactivateFlag(MospUtility.getInt(param));
		}
	}
	
	/**
	 * ユーザ情報及びユーザ追加ロール情報を論理削除する。<br>
	 * 対象ユーザIDのユーザ情報履歴が全て削除された場合、
	 * ユーザパスワード情報も削除する。<br>
	 * <br>
	 * @param userDtos ユーザ情報群
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void delete(Collection<UserMasterDtoInterface> userDtos) throws MospException {
		// ユーザ情報毎に削除
		for (UserMasterDtoInterface dto : userDtos) {
			// ユーザ情報及びユーザ追加ロール情報を論理削除
			delete(dto);
		}
		// 処理結果を確認
		if (mospParams.hasErrorMessage()) {
			// 処理終了
			return;
		}
		// ユーザアカウント情報登録後の確認
		checkAfterRegist();
	}
	
	/**
	 * ユーザ情報及びユーザ追加ロール情報を論理削除する。<br>
	 * 対象ユーザIDのユーザ情報履歴が全て削除された場合、
	 * ユーザパスワード情報も削除する。<br>
	 * <br>
	 * @param userDto ユーザ情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void delete(UserMasterDtoInterface userDto) throws MospException {
		// ユーザID及び有効日を取得
		String userId = userDto.getUserId();
		Date activateDate = userDto.getActivateDate();
		// ユーザ情報を論理削除
		userRegist.delete(userDto);
		// 登録されているユーザ追加ロール情報を論理削除
		extraRoleRegist.delete(userId, activateDate);
		// 対象ユーザIDのユーザ情報履歴が全て削除された場合
		if (userRefer.getUserHistory(userId).isEmpty()) {
			// ユーザパスワード情報を論理削除
			passwordRegist.delete(userId);
		}
	}
	
	/**
	 * 変更日を取得する。<br>
	 * 変更日は、ユーザ情報の有効日かシステム日付の新しい方を設定する。<br>
	 * @param activateDate 有効日
	 * @return 変更日
	 */
	protected Date getChangeDate(Date activateDate) {
		// 変更日(システム日付)を準備
		Date changeDate = getSystemDate();
		// 有効日がシステム日付よりも後である場合
		if (activateDate.after(changeDate)) {
			// 変更日を再設定(有効日)
			changeDate = activateDate;
		}
		// 変更日を取得
		return changeDate;
	}
	
	/**
	 * デフォルトユーザ追加ロール情報群を作成する。<br>
	 * @param userId       ユーザID
	 * @param activateDate 有効日
	 * @return ユーザ追加ロール情報群
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected Set<UserExtraRoleDtoInterface> getDefaultExtraRoleDtos(String userId, Date activateDate)
			throws MospException {
		// ユーザ追加ロール情報群を準備
		Set<UserExtraRoleDtoInterface> dtos = new LinkedHashSet<UserExtraRoleDtoInterface>();
		// 利用可能ロール区分リストを取得
		List<String> roleTypes = roleRefer.getAvailableRoleTypes(activateDate);
		// ロール区分毎に処理
		for (String roleType : roleTypes) {
			// 対象ロール区分のデフォルトロールコードを取得
			String roleCode = RoleUtility.getDefaultRole(mospParams, roleType);
			// デフォルトロールコードを取得できなかった場合
			if (MospUtility.isEmpty(roleCode)) {
				// 次のロール区分へ
				continue;
			}
			// ユーザ追加ロール情報を準備
			UserExtraRoleDtoInterface dto = getInitExtraRoleDto();
			// ユーザ追加ロール情報に値を設定
			dto.setUserId(userId);
			dto.setActivateDate(activateDate);
			dto.setRoleType(roleType);
			dto.setRoleCode(roleCode);
			// ユーザ追加ロール情報をユーザ追加ロール情報群に追加
			dtos.add(dto);
		}
		// ユーザ追加ロール情報群を取得
		return dtos;
	}
	
	/**
	 * レコード識別ID配列からユーザ情報群を取得する。<br>
	 * @param recordIdArray レコード識別ID配列
	 * @return ユーザ情報群
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected Set<UserMasterDtoInterface> getUserDtos(long[] recordIdArray) throws MospException {
		// ユーザ情報群を準備
		Set<UserMasterDtoInterface> set = new LinkedHashSet<UserMasterDtoInterface>();
		// レコード識別ID毎に処理
		for (long recordId : recordIdArray) {
			// レコード識別IDからDTOを取得しユーザ情報群へ追加
			set.add(userRefer.findForkey(recordId));
		}
		// nullを除去(レコード識別IDからユーザ情報を取得できなかった場合)
		set.remove(null);
		// ユーザID群を取得
		return set;
		
	}
	
	/**
	 * レコード識別ID配列からユーザID群を取得する。<br>
	 * 同時に排他確認を行う。<br>
	 * @param recordIdArray レコード識別ID配列
	 * @return ユーザID群
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected Set<String> getUserIds(long[] recordIdArray) throws MospException {
		// ユーザID群を準備
		Set<String> set = new LinkedHashSet<String>();
		// レコード識別ID配列からユーザ情報群を取得
		Set<UserMasterDtoInterface> dtos = getUserDtos(recordIdArray);
		// ユーザ情報毎に処理
		for (UserMasterDtoInterface dto : dtos) {
			// ユーザIDをユーザID群へ追加
			set.add(dto.getUserId());
		}
		// ユーザID群を取得
		return set;
	}
	
}
