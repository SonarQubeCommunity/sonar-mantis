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

import org.sonar.api.measures.Metric;
import org.sonar.api.measures.Metrics;

import java.util.Arrays;
import java.util.List;

/**
 * @author Jeremie Lagarde
 * @since 0.1
 */
public class MantisMetrics implements Metrics {

  public static final String DOMAIN = "Issues";

  public static final Metric PRIORITIES = new Metric.Builder(
      "mantis_issues_priorities", "Mantis Issues Priorities",
      Metric.ValueType.DISTRIB)
      .setDescription("Number of Mantis Issues by Priorities")
      .setDirection(Metric.DIRECTION_NONE).setQualitative(false)
      .setDomain(DOMAIN).create();

  public static final Metric STATUS = new Metric.Builder(
      "mantis_issues_status", "Mantis Issues Status",
      Metric.ValueType.DISTRIB)
      .setDescription("Number of Mantis Issues by Status")
      .setDirection(Metric.DIRECTION_NONE).setQualitative(false)
      .setDomain(DOMAIN).create();

  public static final Metric DEVELOPERS = new Metric.Builder(
      "mantis_developers", "Mantis Issues by developer",
      Metric.ValueType.DISTRIB)
      .setDescription("Number of Mantis Issues per developer")
      .setDirection(Metric.DIRECTION_NONE).setQualitative(false)
      .setDomain(DOMAIN).create();

  public List<Metric> getMetrics() {
    return Arrays.asList(PRIORITIES, STATUS, DEVELOPERS);
  }

}
