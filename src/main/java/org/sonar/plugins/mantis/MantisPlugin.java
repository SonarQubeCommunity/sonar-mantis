/*
 * Sonar Mantis Plugin
 * Copyright (C) 2011 Jérémie Lagarde
 * dev@sonar.codehaus.org
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */

package org.sonar.plugins.mantis;

import java.util.ArrayList;
import java.util.List;

import org.sonar.api.Extension;
import org.sonar.api.Properties;
import org.sonar.api.Property;
import org.sonar.api.SonarPlugin;

/**
 * @author Jeremie Lagarde
 * @since 0.1
 */
@Properties({
		@Property(key = MantisPlugin.SERVER_URL_PROPERTY, defaultValue = "", name = "Server URL", description = "Example : http://www.mantisbt.org/demo/", global = true, project = true, module = false),
		@Property(key = MantisPlugin.USERNAME_PROPERTY, defaultValue = "", name = "Username", global = true, project = true, module = false),
		@Property(key = MantisPlugin.PASSWORD_PROPERTY, defaultValue = "", name = "Password", global = true, project = true, module = false),
		@Property(key = MantisPlugin.PROJECTNAME_PROPERTY, defaultValue = "", name = "Project name", global = false, project = true, module = true),
		@Property(key = MantisPlugin.FILTER_PROPERTY, defaultValue = "", name = "Filter name", description = "Case sensitive, example : SONAR-current-iteration", global = false, project = true, module = true) })
public class MantisPlugin extends SonarPlugin {

	public final static String SERVER_URL_PROPERTY = "sonar.mantis.url";
	public final static String USERNAME_PROPERTY = "sonar.mantis.login.secured";
	public final static String PASSWORD_PROPERTY = "sonar.mantis.password.secured";
	public final static String FILTER_PROPERTY = "sonar.mantis.filter.param";
	public final static String PROJECTNAME_PROPERTY = "sonar.mantis.project.param";

	public List<Class<? extends Extension>> getExtensions() {
		List<Class<? extends Extension>> list = new ArrayList<Class<? extends Extension>>();
		list.add(MantisMetrics.class);
		list.add(MantisSensor.class);
		list.add(MantisWidget.class);
		list.add(MantisDeveloperWidget.class);
		return list;
	}
}
