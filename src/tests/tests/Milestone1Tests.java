package tests.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({
        TestCardGroup.class,
        TestDeck.class,
        TestHand.class,
        TestTurnHistory.class,
        TestRobotImpl.class
        })
public class Milestone1Tests {}

