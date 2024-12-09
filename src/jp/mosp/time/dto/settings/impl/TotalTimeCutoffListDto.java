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
/**
 * 
 */
package jp.mosp.time.dto.settings.impl;

import jp.mosp.time.dto.settings.TotalTimeCutoffListDtoInterface;

/**
 * 勤怠集計管理締日情報一覧DTO。
 */
public class TotalTimeCutoffListDto extends TmmCutoffDto implements TotalTimeCutoffListDtoInterface {
	
	private static final long	serialVersionUID	= 1784072235089155364L;
	
	/**
	 * 締状態。
	 */
	private int					cutoffState;
	
	
	@Override
	public int getCutoffState() {
		return cutoffState;
	}
	
	@Override
	public void setCutoffState(int cutoffState) {
		this.cutoffState = cutoffState;
	}
	
}
