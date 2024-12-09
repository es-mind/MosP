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
package jp.mosp.platform.dto.system.impl;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.platform.dto.system.ReceptionIcCardDtoInterface;

/**
 * カードID受付番号DTO。
 */
public class PftReceptionIcCardDto extends BaseDto implements ReceptionIcCardDtoInterface {
	
	private static final long	serialVersionUID	= -6885463914508433685L;
	
	/**
	 * レコード識別ID。
	 */
	private long				pftReceptionIcCardId;
	
	/**
	 * ICカードID。
	 */
	private String				icCardId;
	
	
	@Override
	public long getPftReceptionIcCardId() {
		return pftReceptionIcCardId;
	}
	
	@Override
	public String getIcCardId() {
		return icCardId;
	}
	
	@Override
	public void setPftReceptionIcCardId(long pftReceptionIcCardId) {
		this.pftReceptionIcCardId = pftReceptionIcCardId;
	}
	
	@Override
	public void setIcCardId(String icCardId) {
		this.icCardId = icCardId;
	}
	
}
