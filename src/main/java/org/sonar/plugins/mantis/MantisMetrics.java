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

  public static final Metric PRIORITIES = new Metric("issues_priorities", "Mantis Issues Priorities",
      "Number of Mantis Issues by Priorities", Metric.ValueType.INT, Metric.DIRECTION_NONE, false, DOMAIN);

  public static final Metric STATUS = new Metric("issues_status", "Mantis Issues Status", "Number of Mantis Issues by Status",
      Metric.ValueType.INT, Metric.DIRECTION_NONE, false, DOMAIN);

  public List<Metric> getMetrics() {
    return Arrays.asList(PRIORITIES, STATUS);
  }

}
