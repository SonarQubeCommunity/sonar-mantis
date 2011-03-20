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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.picocontainer.Characteristics;
import org.picocontainer.MutablePicoContainer;
import org.sonar.api.Extension;
import org.sonar.api.Plugin;
import org.sonar.api.platform.PluginRepository;

/**
 * @author Jeremie Lagarde
 * @since 0.1
 */
public class MantisPluginTest {

  @Test
  public void testRegisterPlugin() {
    MutablePicoContainer container = mock(MutablePicoContainer.class);
    when(container.as(Characteristics.CACHE)).thenReturn(container);
    when(container.addComponent(Class.class, Class.class)).thenReturn(container);
    PluginRepository repository = new PluginRepository() {
    };
    Plugin plugin = new MantisPlugin();
    repository.registerPlugin(container, plugin, Extension.class);
    assertEquals(repository.getPlugin("mantis"), plugin);
    assertNotNull(repository.getPlugin("mantis").getName());
    assertNotNull(repository.getPlugin("mantis").getDescription());
  }

}
