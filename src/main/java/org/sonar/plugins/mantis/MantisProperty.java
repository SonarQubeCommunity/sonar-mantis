/*
 * Sonar plugin for Mantis
 * Copyright (C) 2011 Jérémie Lagarde
 * mailto: jer AT printstacktrace DOR org
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
