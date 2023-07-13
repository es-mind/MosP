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
package jp.mosp.setup.constant;

/**
 * セットアップステータスクラス。
 */
public enum SetUpStatus {
	
	/**
	 * DBが存在しない。
	 */
	NULL,
	
	/**
	 * DBは存在しているが、初期アカウントが存在しない。
	 */
	EMPTY,
	
	/**
	 * エラーと判断された場合。
	 */
	ERROR,
	
	/**
	 * セットアップ完了。
	 */
	ALREADY;
	
}
