/*
 * Sonar, open source software quality management tool.
 * Copyright (C) 2009 SonarSource
 * mailto:contact AT sonarsource DOT com
 *
 * Sonar is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * Sonar is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Sonar; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */

package org.sonar.plugins.mantis;

import biz.futureware.mantis.rpc.soap.client.ObjectRef;

/**
 * @author Jeremie Lagarde
 * @since 0.1
 */
public class MantisProperty implements Comparable<MantisProperty> {

  private final ObjectRef ref;

  public MantisProperty(ObjectRef ref) {
    this.ref = ref;
  }

  public int compareTo(MantisProperty o) {
    return ref.getId().compareTo(o.ref.getId());
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof MantisProperty) {
      return this.ref.equals(((MantisProperty) obj).ref);
    }
    return super.equals(obj);
  }

  @Override
  public int hashCode() {
    return ref.getId().hashCode();
  }

  @Override
  public String toString() {
    return ref.getName();
  }
}
