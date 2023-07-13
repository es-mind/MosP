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
package jp.mosp.platform.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.constant.MospConst;

/**
 * IPアドレスに関する有用なメソッドを提供する。<br>
 */
public class IpAddressUtility {
	
	/**
	 * MosPアプリケーション設定キー(利用可能CIDR)。<br>
	 * カンマ区切で複数設定可能。<br>
	 */
	protected static final String	APP_AVAILABLE_CIDRS	= "AvailableCidrs";
	
	/**
	 * 正規表現(IPアドレス)。<br>
	 */
	protected static final String	REG_IP_ADDRESS		= "(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})";
	
	/**
	 * 正規表現(CIDR)。<br>
	 */
	protected static final String	REG_CIDR			= REG_IP_ADDRESS + "/(\\d{1,2})";
	
	/**
	 * 最大値(ドットアドレス)。<br>
	 */
	protected static final int		MAX_DOT_ADDRESS		= 255;
	
	/**
	 * 最小値(ドットアドレス)。<br>
	 */
	protected static final int		MIN_DOT_ADDRESS		= 0;
	
	/**
	 * 最大値(CIDR部)。<br>
	 */
	protected static final int		MAX_CIDR_PART		= 32;
	
	/**
	 * 最小値(CIDR部)。<br>
	 */
	protected static final int		MIN_CIDR_PART		= 0;
	
	
	/**
	 * IPアドレスが利用可能であるかを確認する。<br>
	 * <br>
	 * 確認対象のIPアドレスは、MosP処理情報(REMOTE-ADDR)から取得する。<br>
	 * <br>
	 * attAvailableCidrs
	 * に設定されているCIDRにIPアドレスが含まれている場合、
	 * 利用可能であると判断する。<br>
	 * <br>
	 * attAvailableCidrs
	 * が設定されていない場合、利用可能であると判断する。<br>
	 * <br>
	 * attAvailableCidrs
	 * が設定されているが空白である場合、利用不能であると判断する。<br>
	 * <br>
	 * attAvailableCidrs
	 * が不正に設定されている場合、例外を発行する。<br>
	 * <br>
	 * @param mospParams        MosP処理情報
	 * @param attAvailableCidrs MosPアプリケーション設定キー(利用可能CIDR)
	 * @return 確認結果(true：利用可能、false：利用不能)
	 * @throws MospException CIDRの形式が不正である場合
	 */
	public static boolean isAddressAvailable(MospParams mospParams, String attAvailableCidrs) throws MospException {
		// IPアドレス取得
		String ipAddress = String.valueOf(mospParams.getGeneralParam(MospConst.ATT_REMOTE_ADDR));
		// 利用可能CIDRが設定されていない場合
		if (mospParams.getApplicationProperty(attAvailableCidrs) == null) {
			// IPアドレスが利用可能であると判断
			return true;
		}
		// IPアドレスが妥当でない場合
		if (isIpAddressValid(ipAddress) == false) {
			// IPアドレスがCIDRで表されるサブネットに含まれないと判断
			return false;
		}
		// 利用可能CIDR配列取得
		String[] cidrs = mospParams.getApplicationProperties(attAvailableCidrs);
		// 利用可能CIDR毎に処理
		for (String cidr : cidrs) {
			// CIDRが妥当でない場合
			if (isCidrValid(cidr) == false) {
				// 例外を発行
				mospParams.setErrorViewUrl();
				throw new MospException(PfMessageUtility.MSG_W_REQUIRED, attAvailableCidrs);
			}
			// IPアドレスがCIDRで表されるサブネットに含まれる場合
			if (isAddressInRange(ipAddress, cidr)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * IPアドレスが利用可能であるかを確認する。<br>
	 * <br>
	 * 確認対象のIPアドレスは、MosP処理情報(REMOTE-ADDR)から取得する。<br>
	 * <br>
	 * attAvailableCidrsとして{@link IpAddressUtility#APP_AVAILABLE_CIDRS}を
	 * 用いる。<br>
	 * <br>
	 * @param mospParams MosP処理情報
	 * @return 確認結果(true：利用可能、false：利用不能)
	 * @throws MospException CIDRの形式が不正である場合
	 */
	public static boolean isAddressAvailable(MospParams mospParams) throws MospException {
		return isAddressAvailable(mospParams, APP_AVAILABLE_CIDRS);
	}
	
	/**
	 * IPアドレスがCIDRで表されるサブネットに含まれるかを確認する。<br>
	 * <br>
	 * IPアドレスが妥当でない場合、
	 * IPアドレスがCIDRで表されるサブネットに含まれないと判断する。<br>
	 * <br>
	 * @param ipAddress IPアドレス
	 * @param cidr      CIDR
	 * @return 確認結果(true：含まれる、false：含まれない)
	 */
	protected static boolean isAddressInRange(String ipAddress, String cidr) {
		// IPアドレスを数値で取得
		int address = getIntAddress(ipAddress);
		// CIDRのIPアドレス部を数値で取得
		int addressPart = getIntAddressPart(cidr);
		// ネットマスクを数値で取得
		int netmask = getNetMask(cidr);
		// ネットマスクが0である場合
		if (netmask == 0) {
			// IPアドレスがCIDRで表されるサブネットに含まれると判断
			return true;
		}
		// CIDRで表される最小値を取得
		int minAddress = addressPart & netmask;
		// CIDRで表される最大値を取得
		int maxAddress = minAddress | ~netmask;
		// IPアドレスがCIDRで表されるサブネットに含まれるかを確認
		return isValueInRange(address, minAddress, maxAddress);
	}
	
	/**
	 * IPアドレスの妥当性を確認する。<br>
	 * @param ipAddress IPアドレス
	 * @return 確認結果(true：妥当である、false：妥当でない)
	 */
	protected static boolean isIpAddressValid(String ipAddress) {
		// IPアドレスがnullである場合
		if (ipAddress == null) {
			return false;
		}
		// 正規表現エンジン(IPアドレス)を準備
		Matcher matcher = Pattern.compile(REG_IP_ADDRESS).matcher(ipAddress);
		// IPアドレスが正規表現パターンと一致しない場合
		if (matcher.matches() == false) {
			return false;
		}
		// ドットで区切られた数字(4つ)毎に処理
		for (int i = 0; i < 4; ++i) {
			// 数字を取得
			int dotAddress = Integer.parseInt(matcher.group(i + 1));
			// 0から255に含まれない場合
			if (isValueInRange(dotAddress, MIN_DOT_ADDRESS, MAX_DOT_ADDRESS) == false) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * CIDRの妥当性を確認する。<br>
	 * @param cidr      CIDR
	 * @return 確認結果(true：妥当である、false：妥当でない)
	 */
	protected static boolean isCidrValid(String cidr) {
		// CIDRがnullである場合
		if (cidr == null) {
			return false;
		}
		// 正規表現エンジン(CIDR)を準備
		Matcher matcher = Pattern.compile(REG_CIDR).matcher(cidr);
		// CIDRが正規表現パターンと一致しない場合
		if (matcher.matches() == false) {
			return false;
		}
		// ドットで区切られた数字(4つ)毎に処理
		for (int i = 0; i < 4; ++i) {
			// 数字を取得
			int dotAddress = Integer.parseInt(matcher.group(i + 1));
			// 0から255に含まれない場合
			if (isValueInRange(dotAddress, MIN_DOT_ADDRESS, MAX_DOT_ADDRESS) == false) {
				return false;
			}
		}
		// CIDR部の取得
		int cidrPart = Integer.parseInt(matcher.group(5));
		// 1から32に含まれない場合
		if (isValueInRange(cidrPart, MIN_CIDR_PART, MAX_CIDR_PART) == false) {
			return false;
		}
		return true;
	}
	
	/**
	 * IPアドレスを数値で取得する。<br>
	 * <br>
	 * IPアドレスは妥当でなくてはならない。<br>
	 * <br>
	 * @param ipAddress IPアドレス
	 * @return IPアドレス(数値)
	 */
	protected static int getIntAddress(String ipAddress) {
		// IPアドレスを数値で取得
		return getIntAddress(Pattern.compile(REG_IP_ADDRESS).matcher(ipAddress));
	}
	
	/**
	 * CIDRのIPアドレス部を数値で取得する。<br>
	 * <br>
	 * CIDRは妥当でなくてはならない。<br>
	 * <br>
	 * @param cidr CIDR
	 * @return IPアドレス(数値)
	 */
	protected static int getIntAddressPart(String cidr) {
		// IPアドレスを数値で取得
		return getIntAddress(Pattern.compile(REG_CIDR).matcher(cidr));
	}
	
	/**
	 * IPアドレスを数値で取得する。<br>
	 * @param matcher 正規表現エンジン
	 * @return IPアドレス部(数値)
	 */
	protected static int getIntAddress(Matcher matcher) {
		// IPアドレスが妥当でない場合
		if (matcher.matches() == false) {
			return 0;
		}
		// 数値アドレスを準備
		int address = 0;
		// ドットで区切られた数字(4つ)毎に処理
		for (int i = 0; i < 4; ++i) {
			// 各グループの数値を取得
			int dotAddress = Integer.parseInt(matcher.group(i + 1));
			// 0xff(16進数：255)で論理積(ビット演算するにあたり符号等を調整)
			dotAddress = dotAddress & 0xff;
			// グループ毎に8ビットシフトして数値アドレスに論理和
			address |= dotAddress << 8 * (4 - i - 1);
		}
		return address;
	}
	
	/**
	 * ネットマスクを数値で取得する。<br>
	 * <br>
	 * CIDRは妥当でなくてはならない。<br>
	 * <br>
	 * @param cidr CIDR
	 * @return ネットマスク(数値)
	 */
	protected static int getNetMask(String cidr) {
		// 正規表現エンジン(CIDR)を準備
		Matcher matcher = Pattern.compile(REG_CIDR).matcher(cidr);
		// CIDRが妥当でない場合
		if (matcher.matches() == false) {
			return 0;
		}
		// CIDR部(ビット数)の取得
		int cidrPart = Integer.parseInt(matcher.group(5));
		// ネットマスクの準備
		int netMask = 0;
		// CIDR部の数値(ビット数)分の処理を実施
		for (int i = 0; i < cidrPart; ++i) {
			// 1ビットずつシフトしながらネットマスクに論理和
			netMask |= 1 << 31 - i;
		}
		return netMask;
	}
	
	/**
	 * 値が最大値と最小値の間に含まれるかを確認する。<br>
	 * <br>
	 * 値が最大値や最小値と同値の場合は、含まれると判断する。<br>
	 * <br>
	 * @param value 値
	 * @param min   最小値
	 * @param max   最大値
	 * @return 確認結果(true：含まれる、false：含まれない)
	 */
	protected static boolean isValueInRange(int value, int min, int max) {
		// 最大値よりも大きい場合
		if (max < value) {
			return false;
		}
		// 最小値よりも小さい場合
		if (value < min) {
			return false;
		}
		return true;
	}
	
}
