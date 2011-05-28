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

  public static final Metric PRIORITIES = new Metric("mantis_issues_priorities", "Mantis Issues Priorities",
      "Number of Mantis Issues by Priorities", Metric.ValueType.DISTRIB, Metric.DIRECTION_NONE, false, DOMAIN);

  public static final Metric STATUS = new Metric("mantis_issues_status", "Mantis Issues Status", "Number of Mantis Issues by Status",
      Metric.ValueType.DISTRIB, Metric.DIRECTION_NONE, false, DOMAIN);

  public List<Metric> getMetrics() {
    return Arrays.asList(PRIORITIES, STATUS);
  }

}
