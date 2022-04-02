package tests.iOS;

import lib.IOSTestCase;
import lib.ui.WelcomePageObject;
import org.junit.Test;

public class GetStartedTest extends IOSTestCase {

    @Test
    public void testPassThroughWelcome(){
        WelcomePageObject welcomePage = new WelcomePageObject(driver);

        welcomePage.waitForLearnMoreLink();
        welcomePage.clickNextLink();
        welcomePage.waitForNewWaysToExploreText();
        welcomePage.clickNextLink();
        welcomePage.waitForAddOrEditPreferredLanguagesText();
        welcomePage.clickNextLink();
        welcomePage.waitForLearnMoreAboutDataCollectedText();
        welcomePage.clickGetStartedButton();
    }

}
