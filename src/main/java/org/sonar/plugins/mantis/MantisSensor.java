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

import java.math.BigInteger;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.batch.Sensor;
import org.sonar.api.batch.SensorContext;
import org.sonar.api.measures.Measure;
import org.sonar.api.measures.Metric;
import org.sonar.api.measures.PropertiesBuilder;
import org.sonar.api.resources.Project;
import org.sonar.api.utils.SonarException;
import org.sonar.plugins.mantis.soap.MantisSoapService;

import biz.futureware.mantis.rpc.soap.client.FilterData;
import biz.futureware.mantis.rpc.soap.client.IssueData;

/**
 * @author Jeremie Lagarde
 * @since 0.1
 */
public class MantisSensor implements Sensor {

  private static final Logger LOG = LoggerFactory.getLogger(MantisSensor.class);

  private String serverUrl;
  private String username;
  private String password;
  private String filterName;
  private String projectName;

  public void analyse(Project project, SensorContext context) {
    initParams(project);
    if ( !isMandatoryParametersNotEmpty()) {
      LOG.warn("The server url, the project name, the filter name, the username and the password must not be empty.");
      return;
    }
    try {
      MantisSoapService service = new MantisSoapService(new URL(serverUrl + "/api/soap/mantisconnect.php"));
      service.connect(username, password, projectName);
      analyze(context, service);
      service.disconnect();
    } catch (Exception e) {
      LOG.error("Error accessing Mantis web service, please verify the parameters", e);
    }
  }

  private void analyze(SensorContext context, MantisSoapService service) throws RemoteException {
    FilterData[] filters = service.getFilters();
    FilterData filter = null;
    for (FilterData f : filters) {
      if (filterName.equals(f.getName())) {
        filter = f;
      }
    }

    if (filter == null) {
      throw new SonarException("Unable to find filter '" + filterName + "' in Mantis");
    }

    IssueData[] issues = service.getIssues(filter);
    Map<MantisProperty, Integer> issuesByPriority = new HashMap<MantisProperty, Integer>();
    Map<MantisProperty, Integer> issuesByStatus = new HashMap<MantisProperty, Integer>();
    for (IssueData issue : issues) {
      MantisProperty priority = new MantisProperty(issue.getPriority());
      if ( !issuesByPriority.containsKey(priority)) {
        issuesByPriority.put(priority, 1);
      } else {
        issuesByPriority.put(priority, issuesByPriority.get(priority) + 1);
      }
      MantisProperty status = new MantisProperty(issue.getStatus());
      if ( !issuesByStatus.containsKey(status)) {
        issuesByStatus.put(status, 1);
      } else {
        issuesByStatus.put(status, issuesByStatus.get(status) + 1);
      }
    }
    saveMeasures(context, service.getProjectId(), MantisMetrics.PRIORITIES, issuesByPriority);
    saveMeasures(context, service.getProjectId(), MantisMetrics.STATUS, issuesByStatus);
  }

  private void initParams(Project project) {
    Configuration configuration = project.getConfiguration();
    serverUrl = configuration.getString(MantisPlugin.SERVER_URL_PROPERTY);
    username = configuration.getString(MantisPlugin.USERNAME_PROPERTY);
    password = configuration.getString(MantisPlugin.PASSWORD_PROPERTY);
    filterName = configuration.getString(MantisPlugin.FILTER_PROPERTY);
    projectName = configuration.getString(MantisPlugin.PROJECTNAME_PROPERTY);
  }

  private boolean isMandatoryParametersNotEmpty() {
    return StringUtils.isNotEmpty(serverUrl) && StringUtils.isNotEmpty(filterName) && StringUtils.isNotEmpty(projectName)
        && StringUtils.isNotEmpty(username) && StringUtils.isNotEmpty(password);
  }

  protected void saveMeasures(SensorContext context, BigInteger projectId, Metric metric, Map<MantisProperty, Integer> mesures) {
    double total = 0;
    PropertiesBuilder<MantisProperty, Integer> distribution = new PropertiesBuilder<MantisProperty, Integer>();
    for (Map.Entry<MantisProperty, Integer> entry : mesures.entrySet()) {
      total += entry.getValue();
      distribution.add(entry.getKey(), entry.getValue());
    }
    String url = serverUrl + "/search.php?project_id=" + projectId + "&sticky_issues=on&sortby=property&dir=DESC&hide_status_id=-2";
    Measure issuesMeasure = new Measure(metric, total);
    issuesMeasure.setUrl(url);
    issuesMeasure.setData(distribution.buildData());
    context.saveMeasure(issuesMeasure);
  }

  public boolean shouldExecuteOnProject(Project project) {
    return project.isRoot();
  }

  @Override
  public String toString() {
    return getClass().getSimpleName();
  }
}
