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
package jp.mosp.platform.comparator.message;

import java.util.Comparator;

import jp.mosp.platform.dto.message.MessageDtoInterface;

/**
 * メッセージNoによる比較クラス。<br>
 */
public class MessageNoComparator implements Comparator<MessageDtoInterface> {
	
	@Override
	public int compare(MessageDtoInterface dto1, MessageDtoInterface dto2) {
		return dto1.getMessageNo().compareTo(dto2.getMessageNo());
	}
	
}
