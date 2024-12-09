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
package jp.mosp.platform.message.action;

import java.util.List;

import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.BaseVo;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformAction;
import jp.mosp.platform.bean.message.MessageSearchBeanInterface;
import jp.mosp.platform.comparator.message.MessageNoComparator;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dto.message.MessageDtoInterface;
import jp.mosp.platform.message.vo.MessageListVo;
import jp.mosp.platform.system.base.PlatformSystemAction;
import jp.mosp.platform.utils.PfMessageUtility;

/**
 * ポータル画面に表示する告知欄の内容レコードの検索・管理を行う。<br>
 * <br>
 * 以下のコマンドを扱う。<br>
 * <ul><li>
 * {@link #CMD_SHOW}
 * </li><li>
 * {@link #CMD_SEARCH}
 * </li><li>
 * {@link #CMD_RE_SEARCH}
 * </li><li>
 * {@link #CMD_SORT}
 * </li><li>
 * {@link #CMD_PAGE}
 * </li></ul>
 */
public class MessageListAction extends PlatformSystemAction {
	
	/**
	 * 表示コマンド。<br>
	 * <br>
	 * 初期表示を行う。<br>
	 */
	public static final String	CMD_SHOW		= "PF4110";
	
	/**
	 * 検索コマンド。<br>
	 * <br>
	 * 検索欄に入力された各種情報項目を基に検索を行い、
	 * 条件に沿ったメッセージ設定情報の一覧表示を行う。一覧表示の際には有効日でソートを行う。<br>
	 */
	public static final String	CMD_SEARCH		= "PF4112";
	
	/**
	 * 再表示コマンド。<br>
	 * <br>
	 * この画面よりも奥の階層の画面から再び遷移した際に
	 * 各画面で扱っている情報を最新のものに反映させ、検索結果の一覧表示にも反映させる。<br>
	 */
	public static final String	CMD_RE_SEARCH	= "PF4113";
	
	/**
	 * ソートコマンド。<br>
	 * <br>
	 * それぞれのレコードの値を比較して一覧表示欄の各情報毎に並び替えを行う。
	 * これが実行される度に並び替えが昇順・降順と交互に切り替わる。<br>
	 */
	public static final String	CMD_SORT		= "PF4118";
	
	/**
	 * ページ繰りコマンド。<br>
	 * <br>
	 * 検索処理を行った際に検索結果が100件を超えた場合に分割されるページ間の遷移を行う。<br>
	 */
	public static final String	CMD_PAGE		= "PF4119";
	
	
	/**
	 * {@link PlatformAction#PlatformAction()}を実行する。<br>
	 */
	public MessageListAction() {
		super();
		// パンくずリスト用コマンド設定
		topicPathCommand = CMD_RE_SEARCH;
	}
	
	@Override
	protected BaseVo getSpecificVo() {
		return new MessageListVo();
	}
	
	@Override
	public void action() throws MospException {
		// コマンド毎の処理
		if (mospParams.getCommand().equals(CMD_SHOW)) {
			// 表示
			prepareVo(false, false);
			show();
		} else if (mospParams.getCommand().equals(CMD_SEARCH)) {
			// 検索処理
			prepareVo();
			search();
		} else if (mospParams.getCommand().equals(CMD_RE_SEARCH)) {
			// 再表示
			prepareVo(true, false);
			search();
		} else if (mospParams.getCommand().equals(CMD_SORT)) {
			// ソート
			prepareVo();
			sort();
		} else if (mospParams.getCommand().equals(CMD_PAGE)) {
			// ページ繰り
			prepareVo();
			page();
		}
	}
	
	/**
	 * 初期表示処理を行う。<br>
	 * @throws MospException VOの取得に失敗した場合
	 */
	protected void show() throws MospException {
		// VO取得
		MessageListVo vo = (MessageListVo)mospParams.getVo();
		// 基本設定共通VO初期値設定
		initPlatformSystemVoFields();
		// 初期値設定
		vo.setTxtSearchMessageNo("");
		vo.setPltSearchMessageType("");
		vo.setTxtSearchMessageTitle("");
		vo.setTxtSearchEmployeeName("");
		vo.setPltSearchImportance("");
		// ページ繰り設定
		setPageInfo(CMD_PAGE, getListLength());
		// ソートキー設定
		vo.setComparatorName(MessageNoComparator.class.getName());
	}
	
	/**
	 * 検索処理を行う。<br>
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	protected void search() throws MospException {
		// VO取得
		MessageListVo vo = (MessageListVo)mospParams.getVo();
		// 検索クラス取得
		MessageSearchBeanInterface search = reference().messageSearch();
		// VOの値を検索クラスへ設定
		search.setTargetYear(getInt(vo.getTxtSearchActivateYear()));
		search.setTargetMonth(getInt(vo.getTxtSearchActivateMonth()));
		search.setMessageNo(vo.getTxtSearchMessageNo());
		search.setMessageType(vo.getPltSearchMessageType());
		search.setMessageTitle(vo.getTxtSearchMessageTitle());
		search.setEmployeeName(vo.getTxtSearchEmployeeName());
		search.setMessageImportance(vo.getPltSearchImportance());
		search.setInactivateFlag(vo.getPltSearchInactivate());
		// 検索条件をもとに検索クラスからマスタリストを取得
		List<MessageDtoInterface> list = search.getSearchList();
		// 検索結果リスト設定
		vo.setList(list);
		// デフォルトソートキー及びソート順設定
		vo.setComparatorName(MessageNoComparator.class.getName());
		vo.setAscending(false);
		// ソート
		sort();
		// 一覧選択情報初期化
		initCkbSelect();
		// 検索結果確認
		if (list.size() == 0) {
			// データ無しメッセージを設定
			PfMessageUtility.addMessageNoData(mospParams);
		}
	}
	
	/**
	 * 一覧のソート処理を行う。<br>
	 * @throws MospException 比較クラスのインスタンス生成に失敗した場合
	 */
	protected void sort() throws MospException {
		setVoList(sortList(getTransferredSortKey()));
	}
	
	/**
	 * 一覧のページ処理を行う。
	 * @throws MospException リスト内容設定に失敗した場合
	 */
	protected void page() throws MospException {
		setVoList(pageList());
	}
	
	/**
	 * 検索結果リストの内容をVOに設定する。<br>
	 * @param list 対象リスト
	 * @throws MospException 登録者氏名の取得に失敗した場合
	 */
	protected void setVoList(List<? extends BaseDtoInterface> list) throws MospException {
		// VO取得
		MessageListVo vo = (MessageListVo)mospParams.getVo();
		// データ配列初期化
		String[] aryLblStartDate = new String[list.size()];
		String[] aryLblEndDate = new String[list.size()];
		String[] aryLblMessageNo = new String[list.size()];
		String[] aryLblMessageType = new String[list.size()];
		String[] aryLblMessageTitle = new String[list.size()];
		String[] aryLblEmployeeName = new String[list.size()];
		String[] aryLblImportance = new String[list.size()];
		String[] aryLblInactivate = new String[list.size()];
		// データ作成
		for (int i = 0; i < list.size(); i++) {
			// リストから情報を取得
			MessageDtoInterface dto = (MessageDtoInterface)list.get(i);
			// 配列に情報を設定
			aryLblStartDate[i] = getStringDateAndDay(dto.getStartDate());
			aryLblEndDate[i] = getStringDateAndDay(dto.getEndDate());
			aryLblMessageNo[i] = dto.getMessageNo();
			aryLblMessageType[i] = getCodeName(dto.getMessageType(), PlatformConst.CODE_KEY_MESSAGE_TYPE);
			aryLblMessageTitle[i] = dto.getMessageTitle();
			aryLblEmployeeName[i] = getInsertUserName(dto);
			aryLblImportance[i] = getCodeName(dto.getMessageImportance(), PlatformConst.CODE_KEY_MESSAGE_IMPORTANCE);
			aryLblInactivate[i] = getInactivateFlagName(dto.getInactivateFlag());
		}
		// データをVOに設定
		vo.setAryLblStartDate(aryLblStartDate);
		vo.setAryLblEndDate(aryLblEndDate);
		vo.setAryLblMessageNo(aryLblMessageNo);
		vo.setAryLblMessageType(aryLblMessageType);
		vo.setAryLblMessageTitle(aryLblMessageTitle);
		vo.setAryLblEmployeeName(aryLblEmployeeName);
		vo.setAryLblImportance(aryLblImportance);
		vo.setAryLblInactivate(aryLblInactivate);
	}
	
}
