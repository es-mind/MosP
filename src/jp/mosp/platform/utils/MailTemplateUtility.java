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

import java.io.StringWriter;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.log.AvalonLogSystem;
import org.apache.velocity.runtime.resource.loader.FileResourceLoader;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.platform.constant.PlatformMailConst;

/**
 * メールテンプレートユーティリティ。<br>
 * <br>
 * {@link MailTemplateUtility}を利用する場合、
 * ビルドパスに以下のライブラリを設定する必要がある。<br>
 * ・velocity-1.x.x.x.jar<br>
 * ・commons-collections-3.x.x.jar<br>
 * ・commons-lang-2.x.x.x.jar<br>
 */
public class MailTemplateUtility {
	
	/**
	 * テンプレートと情報から文字列を作成する。<br>
	 * @param mospParams   MosP処理情報
	 * @param templatePath テンプレートパス(例：/template/xx/xx/xx.vm)
	 * @param dto          テンプレート情報(フィールドとgetteとsetterだけのクラスのオブジェクト)
	 * @return テンプレートと情報から作成した文字列
	 * @throws MospException 文字列の作成に失敗した場合
	 */
	public static String makeText(MospParams mospParams, String templatePath, Object dto) throws MospException {
		// プロパティを準備
		Properties vp = getProperties(mospParams.getApplicationProperty(MospConst.APP_DOCBASE));
		// エンジンを準備
		VelocityEngine engine = new VelocityEngine();
		engine.init(vp);
		// テンプレートを準備
		Template template = engine.getTemplate(templatePath, PlatformMailConst.ENCODING);
		// コンテキストを準備
		VelocityContext context = new VelocityContext();
		context.put(PlatformMailConst.KEY_DTO, dto);
		// 文字列を準備
		String text = MospConst.STR_EMPTY;
		// マージ(テンプレートと情報から文字列を作成)
		try (StringWriter sw = new StringWriter()) {
			template.merge(context, sw);
			text = sw.toString();
			sw.flush();
		} catch (Throwable t) {
			throw new MospException(t);
		}
		// 文字列を取得
		return text;
	}
	
	/**
	 * VelocityEngineのプロパティを取得する。<br>
	 * @param templatePath テンプレートファイルパス
	 * @return VelocityEngineのプロパティ
	 */
	private static Properties getProperties(String templatePath) {
		Properties props = new Properties();
		props.setProperty(PlatformMailConst.RESOURCE_LOADER, PlatformMailConst.FILE);
		props.setProperty(PlatformMailConst.RESOURCE_LOADER_DESCRIPTION, "Velocity File Resource Loader");
		props.setProperty(PlatformMailConst.RESOURCE_LOADER_CLASS, FileResourceLoader.class.getCanonicalName());
		props.setProperty(PlatformMailConst.RESOURCE_LOADER_PATH, templatePath);
		props.setProperty(PlatformMailConst.RESOURCE_LOADER_CACHE, Boolean.toString(true));
		props.setProperty(PlatformMailConst.RESOURCE_LOADER_INTERVAL, "0");
		props.setProperty(PlatformMailConst.INPUT_ENCODING, PlatformMailConst.ENCODING);
		props.setProperty(PlatformMailConst.OUTPUT_ENCODING, PlatformMailConst.ENCODING);
		props.setProperty(PlatformMailConst.RUNTIME_LOG_LOGSYSTEM_CLASS, AvalonLogSystem.class.getCanonicalName());
		return props;
	}
	
}
