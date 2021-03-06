
/*
 * Carrot2 project.
 *
 * Copyright (C) 2002-2019, Dawid Weiss, Stanisław Osiński.
 * All rights reserved.
 *
 * Refer to the full license file "carrot2.LICENSE"
 * in the root folder of the repository checkout or at:
 * http://www.carrot2.org/carrot2.LICENSE
 */

package org.carrot2.source.microsoft.v7;

public enum Freshness {
  DAY("Day"),
  WEEK("Week"),
  MONTH("Month");
  
  public final String argName;

  private Freshness(String argName) {
    this.argName = argName;
  }
}
