package ca.nait.dmit.domain;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.*;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
@SuiteDisplayName("JUnit 5 Suite Demo")
//@SelectPackages("ca.nait.dmit.domain")
@SelectClasses({CircleTest.class, RectangleTest.class})

public class AllTest {

}
