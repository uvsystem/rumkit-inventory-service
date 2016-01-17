package com.dbsys.rs.inventory.test.controller;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ BhpControllerTest.class, ObatControllerTest.class,
		StokEksternalControllerTest.class, StokInternalControllerTest.class,
		StokKembaliControllerTest.class})
public class Tests {

}
