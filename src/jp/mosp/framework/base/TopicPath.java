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
package jp.mosp.framework.base;

import java.io.Serializable;

/**
 * パンくずクラス。<br>
 */
public class TopicPath implements Serializable {
	
	private static final long	serialVersionUID	= 2766251502127562975L;
	
	/**
	 * 画面ID(VOのクラス名)。
	 */
	private String				id;
	
	/**
	 * パンくず表示名称。
	 */
	private String				name;
	
	/**
	 * VO。
	 */
	private BaseVo				vo;
	
	/**
	 * コマンド。
	 */
	private String				command;
	
	/**
	 * メニューキー。<br>
	 * メニューから選択された場合のみ付加される。<br>
	 */
	private String				menuKey;
	
	
	/**
	 * @return id
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * @param id セットする id
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @param name セットする name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return vo
	 */
	public BaseVo getVo() {
		return vo;
	}
	
	/**
	 * @param vo セットする vo
	 */
	public void setVo(BaseVo vo) {
		this.vo = vo;
	}
	
	/**
	 * @return command
	 */
	public String getCommand() {
		return command;
	}
	
	/**
	 * @param command セットする command
	 */
	public void setCommand(String command) {
		this.command = command;
	}
	
	/**
	 * @return menuKey
	 */
	public String getMenuKey() {
		return menuKey;
	}
	
	/**
	 * @param menuKey セットする menuKey
	 */
	public void setMenuKey(String menuKey) {
		this.menuKey = menuKey;
	}
	
}
