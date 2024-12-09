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
package jp.mosp.framework.xml;

/**
 * MosP設定情報要素。<br>
 */
public enum PropertyTag {
	
	/**
	 * アプリケーション要素
	 */
	APPLICATION {
		
		@Override
		public String getName() {
			return "Application";
		}
		
	},
	
	/**
	 * コントローラー要素
	 */
	CONTROLLER {
		
		@Override
		public String getName() {
			return "Controller";
		}
		
	},
	
	/**
	 * モデル要素
	 */
	MODEL {
		
		@Override
		public String getName() {
			return "Model";
		}
		
	},
	
	/**
	 * メッセージ要素
	 */
	MESSAGE {
		
		@Override
		public String getName() {
			return "Message";
		}
		
	},
	
	/**
	 * 文言要素
	 */
	NAMING {
		
		@Override
		public String getName() {
			return "Naming";
		}
		
	},
	
	/**
	 * コード要素
	 */
	CODE {
		
		@Override
		public String getName() {
			return "Code";
		}
		
	},
	
	/**
	 * ロール要素
	 */
	ROLE {
		
		@Override
		public String getName() {
			return "Role";
		}
		
	},
	
	/**
	 * メインメニュー要素
	 */
	MAIN_MENU {
		
		@Override
		public String getName() {
			return "MainMenu";
		}
		
	},
	
	/**
	 * アドオン要素
	 */
	ADD_ON {
		
		@Override
		public String getName() {
			return "Addon";
		}
		
	},
	
	/**
	 * 規約要素
	 */
	CONVENTION {
		
		@Override
		public String getName() {
			return "Convention";
		}
		
	},
	
	/**
	 * 表示設定要素
	 */
	VIEW_CONFIG {
		
		@Override
		public String getName() {
			return "ViewConfig";
		}
	},
	
	/**
	 * 未定義要素
	 */
	UNKNOWN {
		
		@Override
		public String getName() {
			return "";
		}
		
	},
	
	;
	
	/**
	 * @return tagName 要素名
	 */
	public abstract String getName();
	
	/**
	 * MosP設定情報要素を取得する。<br>
	 * @param tagName 要素名
	 * @return MosP設定情報要素
	 */
	public static PropertyTag get(String tagName) {
		for (PropertyTag tag : PropertyTag.values()) {
			if (tag.getName().equals(tagName)) {
				return tag;
			}
		}
		return UNKNOWN;
	}
	
	
	/**
	 * ドキュメントルート要素の要素名。
	 */
	public static final String TAG_DOCUMENT = "MosP";
	
}
