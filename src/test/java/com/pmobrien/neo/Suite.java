package com.pmobrien.neo;

import org.testng.annotations.BeforeSuite;

public class Suite {

  @BeforeSuite
  public void beforeSuite() {
    System.out.println("@BeforeSuite Works");
  }
}
