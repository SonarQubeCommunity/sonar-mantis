/*
 * Sonar plugin for Mantis
 * Copyright (C) 2011 Jérémie Lagarde
 * mailto: jer AT printstacktrace DOT org
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

import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigInteger;
import java.net.MalformedURLException;
import java.rmi.RemoteException;

import org.junit.Before;
import org.junit.Test;
import org.sonar.api.batch.SensorContext;
import org.sonar.api.resources.Project;
import org.sonar.plugins.mantis.soap.MantisSoapService;

import biz.futureware.mantis.rpc.soap.client.FilterData;
import biz.futureware.mantis.rpc.soap.client.IssueData;
import biz.futureware.mantis.rpc.soap.client.MantisConnectLocator;
import biz.futureware.mantis.rpc.soap.client.MantisConnectPortType;
import biz.futureware.mantis.rpc.soap.client.ObjectRef;

/**
 * @author Jeremie Lagarde
 * @since 0.1
 */
public class MantisSensorTest {

  private MantisSensor sensor;

  @Before
  public void setUp() throws Exception {
    final MantisSoapService service = new MantisSoapService(null) {

      @Override
      protected MantisConnectLocator createMantisConnectLocator() {
        MantisConnectLocator locator = mock(MantisConnectLocator.class);
        MantisConnectPortType portType = mock(MantisConnectPortType.class);
        try {
          IssueData[] issues = new IssueData[5];
          issues[0] = new IssueData();
          issues[0].setPriority(new ObjectRef(BigInteger.valueOf(1), "normal"));
          issues[0].setStatus(new ObjectRef(BigInteger.valueOf(1), "assigned"));
          issues[1] = new IssueData();
          issues[1].setPriority(new ObjectRef(BigInteger.valueOf(2), "high"));
          issues[1].setStatus(new ObjectRef(BigInteger.valueOf(2), "resolved"));
          issues[2] = new IssueData();
          issues[2].setPriority(new ObjectRef(BigInteger.valueOf(1), "normal"));
          issues[2].setStatus(new ObjectRef(BigInteger.valueOf(1), "assigned"));
          issues[3] = new IssueData();
          issues[3].setPriority(new ObjectRef(BigInteger.valueOf(1), "normal"));
          issues[3].setStatus(new ObjectRef(BigInteger.valueOf(3), "closed"));
          issues[4] = new IssueData();
          issues[4].setPriority(new ObjectRef(BigInteger.valueOf(3), "urgent"));
          issues[4].setStatus(new ObjectRef(BigInteger.valueOf(1), "assigned"));
          FilterData filter = new FilterData(BigInteger.ONE, null, BigInteger.ONE, true, "", "");
          when(locator.getMantisConnectPort()).thenReturn(portType);
          when(portType.mc_project_get_id_from_name(null, null, null)).thenReturn(BigInteger.ONE);
          when(portType.mc_filter_get(null, null, BigInteger.ONE)).thenReturn(new FilterData[] { filter });
          when(portType.mc_filter_get_issues(null, null, BigInteger.ONE, filter.getId(), BigInteger.ONE, BigInteger.valueOf(100))).thenReturn(
              issues);
        } catch (Exception e) {
          fail();
        }
        return locator;
      }
    };

    sensor = new MantisSensor() {

      protected boolean isMandatoryParametersNotEmpty() {
        return true;
      }

      protected void initParams(Project project) {
      };

      public String getFilterName() {
        return "";
      };

      protected MantisSoapService createMantisSoapService() throws RemoteException, MalformedURLException {
        return service;
      }
    };
  }


  @Test
  public void testAnalyse() {
    SensorContext context = mock(SensorContext.class);
    Project project = mock(Project.class);
    sensor.analyse(project, context);
  }
}
