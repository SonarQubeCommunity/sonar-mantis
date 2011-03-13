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

import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.sonar.api.batch.SensorContext;
import org.sonar.api.test.IsMeasure;

import biz.futureware.mantis.rpc.soap.client.ObjectRef;

/**
 * @author Jeremie Lagarde
 * @since 0.1
 */
public class MantisSensorTest {
  private MantisSensor sensor;

  @Before
  public void setUp() {
    sensor = new MantisSensor();
  }

  @Test
  public void testSavePrioritiesMeasures() {
    SensorContext context = mock(SensorContext.class);
    Map<MantisProperty, Integer> issuesByPriority = new HashMap<MantisProperty, Integer>(); 
    issuesByPriority.put(new MantisProperty(new ObjectRef(BigInteger.valueOf(3),"urgent")), Integer.valueOf(3));
    issuesByPriority.put(new MantisProperty(new ObjectRef(BigInteger.valueOf(2),"high")), Integer.valueOf(2));
    issuesByPriority.put(new MantisProperty(new ObjectRef(BigInteger.valueOf(1),"normal")), Integer.valueOf(1));
    sensor.saveMeasures(context, BigInteger.ONE, MantisMetrics.PRIORITIES, issuesByPriority);
    verify(context).saveMeasure(argThat(new IsMeasure(MantisMetrics.PRIORITIES, 6.0, "normal=1;high=2;urgent=3")));
    verifyNoMoreInteractions(context);
  }

  @Test
  public void testSaveStatusMeasures() {
    SensorContext context = mock(SensorContext.class);
    Map<MantisProperty, Integer> issuesByStatus = new HashMap<MantisProperty, Integer>();
    issuesByStatus.put(new MantisProperty(new ObjectRef(BigInteger.valueOf(1),"assigned")), Integer.valueOf(4));
    issuesByStatus.put(new MantisProperty(new ObjectRef(BigInteger.valueOf(2),"resolved")), Integer.valueOf(5));
    issuesByStatus.put(new MantisProperty(new ObjectRef(BigInteger.valueOf(3),"closed")), Integer.valueOf(6));
    sensor.saveMeasures(context, BigInteger.ONE, MantisMetrics.STATUS, issuesByStatus);
    verify(context).saveMeasure(argThat(new IsMeasure(MantisMetrics.STATUS, 15.0, "assigned=4;resolved=5;closed=6")));
    verifyNoMoreInteractions(context);
  }
}
