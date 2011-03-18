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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.List;

import org.junit.Test;
import org.sonar.api.measures.Metric;

/**
 * @author Jeremie Lagarde
 * @since 0.1
 */
public class MantisMetricsTest {

  @Test
  public void testGetMetrics() throws Exception {
    List<Metric> metrics = new MantisMetrics().getMetrics();
    assertThat(metrics.size(), is(2));
    for (Metric metric : metrics) {
      assertThat(metric.getDomain(), is(MantisMetrics.DOMAIN));
    }
  }
}
