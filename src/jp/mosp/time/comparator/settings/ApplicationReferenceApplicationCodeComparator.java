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
package jp.mosp.time.comparator.settings;

import java.util.Comparator;

import jp.mosp.time.dto.settings.ApplicationReferenceDtoInterface;

/**
 * 設定適用参照クラスを設定適用コードで比較するクラス。<br>
 */
public class ApplicationReferenceApplicationCodeComparator implements Comparator<ApplicationReferenceDtoInterface> {
	
	@Override
	public int compare(ApplicationReferenceDtoInterface dto, ApplicationReferenceDtoInterface dto1) {
		return dto.getApplicationCode().compareTo(dto1.getApplicationCode());
	}
}
