import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.List;

public class FirstTest {

    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception{
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "AndroidTestDevice");
        capabilities.setCapability("platformVersion", "8.0");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", ".main.MainActivity");
        capabilities.setCapability("app", "/Users/smalevannyi/IdeaProjects/JavaAppiumAutomation/apks/org.wikipedia.apk");

        driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void firstTest(){
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find Search Wikipedia input",
                5);

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find search input line",
                5);

        waitForElementPresent(
                By.xpath("//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' topic searching by 'Java'",
                15);
    }

    @Test
    public void cancelSearch(){
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find Search Wikipedia input",
                5);

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find search input line",
                5);

        waitForElementAndClear(
                By.id("org.wikipedia:id/search_src_text"),
                "Cannot find search field",
                5
        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find Close button",
                5);

        waitForElementNotPresent(
                By.id("org.wikipedia:id/search_close_btn"),
                "Close button is still present on the page",
                5);
    }

    @Test
    public void compareArticleTitle(){

        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find Search Wikipedia input",
                5);

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find search input line",
                5);

        waitForElementAndClick(
                By.xpath("//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' topic searching by 'Java'",
                5);

        WebElement title = waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                15);

        String article_title = title.getAttribute("text");

        Assert.assertEquals(
                "We see unexpected text",
                "Java (programming language)",
                article_title);
    }

    @Test
    public void swipeArticle(){

        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find Search Wikipedia input",
                5);

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Appium",
                "Cannot find search input line",
                5);

        waitForElementAndClick(
                By.xpath("//*[@text='Automation for Apps']"),
                "Cannot find 'Automation for Apps' topic searching by 'Appium'",
                5);

        waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                15);

        swipeUpToElement(
                By.xpath("//*[@text='View page in browser']"),
                "Cannot find the end of the article",
                20
        );

    }

    @Test
    public void checkSearchFieldText(){

        assertElementHasText(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Search Wikipedia",
                "Search Field text is not 'Search Wikipedia'"
        );
    }

    @Test
    public void checkSearchAndCancelSearch(){

        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find Search Wikipedia input",
                5);

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find search input line",
                5);

        WebElement search_result =   waitForElementPresent(
                By.id("org.wikipedia:id/search_results_list"),
                "Cannot find Search Results",
                15);

        Assert.assertTrue(search_result.findElements(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']")).size() > 1);

        waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find Close button",
                5);

        waitForElementNotPresent(
                By.id("org.wikipedia:id/search_results_list"),
                "Search results are still present on the page",
                5);
    }

    @Test
    public void saveFirstArticleToMyListAndDeleteIt(){
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find Search Wikipedia input",
                5);

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find search input line",
                5);

        waitForElementAndClick(
                By.xpath("//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' topic searching by 'Java'",
                5);

        waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                15);

        waitForElementAndClick(
                By.id("More options"),
                "Cannot find button to open article options",
                5);

        waitForElementPresent(
                By.xpath("//android.widget.ListView"),
                "Cannot find article title",
                15);

        waitForElementAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "Cannot find option to add article to reading list",
                5);

        waitForElementAndClick(
                By.id("org.wikipedia:id/onboarding_button"),
                "Cannot find 'GOT IT' tip overlay",
                5);

        waitForElementAndClear(
                By.id("org.wikipedia:id/text_input"),
                "Cannot find input to set name articles folder",
                5);

        String folder_name = "Learning programming";

        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                folder_name,
                "Cannot put text into articles folder name input",
                5);

        waitForElementAndClick(
                By.xpath("//*[@text='OK']"),
                "Cannot find 'OK' button",
                5);

        waitForElementAndClick(
                By.id("Navigate up"),
                "Cannot find X button",
                5);

        waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Cannot find 'My lists' button",
                5);

        waitForElementAndClick(
                By.xpath("//*[@text='" + folder_name + "']"),
                "Cannot find List element",
                5);

        waitForElementPresent(
                By.xpath("//*[@text='Java (programming language)']"),
                "Cannot find saved article",
                5);

        swipeElementToLeft(
                By.xpath("//*[@text='Java (programming language)']"),
                "Cannot find saved article");

        waitForElementNotPresent(
                By.xpath("//*[@text='Java (programming language)']"),
                "Cannot delete saved article",
                5);
    }

    @Test
    public void saveTwoArticlesToMyListAndDeleteSecondOne(){
        String search_input_for_first_article   = "Java";
        String search_input_for_second_article  = "Python";
        String search_text_for_first_article    = "Object-oriented programming language";
        String search_text_for_second_article   = "General-purpose programming language";
        String title_for_the_first_article      = "Java (programming language)";
        String title_for_the_second_article     = "Python (programming language)";

        //search for the first article
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find Search Wikipedia input",
                5);

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                search_input_for_first_article,
                "Cannot find search input line",
                5);

        waitForElementAndClick(
                By.xpath("//*[@text='" + search_text_for_first_article + "']"),
                "Cannot find '" + search_text_for_first_article + "' topic searching by '" + search_input_for_first_article + "'",
                5);

        waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                15);

        //Add first article to new reading list
        waitForElementAndClick(
                By.id("More options"),
                "Cannot find button to open article options",
                5);

        waitForElementPresent(
                By.xpath("//android.widget.ListView"),
                "Cannot find dropdown menu",
                5);

        waitForElementAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "Cannot find option to add article to reading list",
                5);

        waitForElementAndClick(
                By.id("org.wikipedia:id/onboarding_button"),
                "Cannot find 'GOT IT' tip overlay",
                5);

        waitForElementAndClear(
                By.id("org.wikipedia:id/text_input"),
                "Cannot find input to set name articles folder",
                5);

        String folder_name = "Learning programming";

        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                folder_name,
                "Cannot put text into articles folder name input",
                5);

        waitForElementAndClick(
                By.xpath("//*[@text='OK']"),
                "Cannot find 'OK' button",
                5);

        waitForElementAndClick(
                By.id("Navigate up"),
                "Cannot find X button",
                5);

        //search for the second article
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find Search Wikipedia input",
                5);

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                search_input_for_second_article,
                "Cannot find search input line",
                5);

        waitForElementAndClick(
                By.xpath("//*[@text='" + search_text_for_second_article + "']"),
                "Cannot find '" + search_text_for_second_article + "' topic searching by '" + search_input_for_second_article + "'",
                5);

        waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                15);

        //Add second article to existing reading list
        waitForElementAndClick(
                By.id("More options"),
                "Cannot find button to open article options",
                5);

        waitForElementPresent(
                By.xpath("//android.widget.ListView"),
                "Cannot find dropdown menu",
                5);

        waitForElementAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "Cannot find option to add article to reading list",
                5);

        waitForElementAndClick(
                By.xpath("//*[@text='" + folder_name + "']"),
                "Cannot find option to add article to reading list",
                5);

        waitForElementAndClick(
                By.id("Navigate up"),
                "Cannot find X button",
                5);

        //Open Reading List and delete second article
        waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Cannot find 'My lists' button",
                5);

        waitForElementAndClick(
                By.xpath("//*[@text='" + folder_name + "']"),
                "Cannot find List element",
                5);

        waitForElementPresent(
                By.xpath("//*[@text='" + title_for_the_second_article + "']"),
                "Cannot find second article in the reading list",
                5);

        swipeElementToLeft(
                By.xpath("//*[@text='" + title_for_the_second_article + "']"),
                "Cannot find saved article");

        waitForElementAndClick(
                By.xpath("//*[@text='" + title_for_the_first_article + "']"),
                "Cannot find List element",
                5);

        WebElement title = waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                15);

        String article_title = title.getAttribute("text");

        Assert.assertEquals(
                "We see unexpected text",
                title_for_the_first_article,
                article_title);
    }

    @Test
    public void amountOfElementsInSearchResult(){
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find Search Wikipedia input",
                5);

        String search_input = "Linkin Park Discography";

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                search_input,
                "Cannot find search input line",
                5);

        String search_result_locator = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_image]";

        waitForElementPresent(
                By.xpath(search_result_locator),
                "Cannot find anything by the request " + search_input,
                15);

        int amount_of_search_results = getAmountOfElements(By.xpath(search_result_locator));

        Assert.assertTrue("we found too few results", amount_of_search_results > 0);
    }

    @Test
    public void amountOfElementsInEmptySearchResult(){
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find Search Wikipedia input",
                5);

        String search_input = "hcgvsacghvashcvsdcgh";

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                search_input,
                "Cannot find search input line",
                5);

        String search_result_locator = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_image']";
        String empty_result_label = "//*[@text='No results found']";

        waitForElementPresent(
                By.xpath(empty_result_label),
                "Cannot find anything by the request " + search_input,
                15);

       assertElementNotPresent(
               By.xpath(search_result_locator),
               "We found some results by request" + search_input);
    }

    @Test
    public void changeScreenOrientationSearchResults(){
        String search_input = "Java";

        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find Search Wikipedia input",
                5);

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                search_input,
                "Cannot find search input line",
                5);

        waitForElementAndClick(
                By.xpath("//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' topic searching by " + search_input,
                15);

        String title_before_rotation = waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Cannot find title of article",
                15);

        driver.rotate(ScreenOrientation.LANDSCAPE);

        String title_after_rotation = waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Cannot find title of article",
                15);

        Assert.assertEquals(
                "Article title has been changed after rotation",
                title_before_rotation,
                title_after_rotation);

        driver.rotate(ScreenOrientation.PORTRAIT);

        String title_after_second_rotation = waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Cannot find title of article",
                15);

        Assert.assertEquals(
                "Article title has been changed after rotation",
                title_before_rotation,
                title_after_second_rotation);
    }

    @Test
    public void searchArticleInBackground(){
        String search_input = "Java";

        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find Search Wikipedia input",
                5);

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                search_input,
                "Cannot find search input line",
                5);

        waitForElementPresent(
                By.xpath("//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' topic searching by " + search_input,
                5);

        driver.runAppInBackground(2);

        waitForElementPresent(
                By.xpath("//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' topic searching by " + search_input + " after returning from background",
                5);
    }



    private WebElement waitForElementPresent(By by, String error_message, long timeout_in_seconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeout_in_seconds);
        wait.withMessage(error_message + "\n");
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    private WebElement waitForElementPresent(By by, String error_message) {
        return waitForElementPresent(by, error_message, 5);
    }

    private WebElement waitForElementAndClick(By by, String error_message, long timeout_in_seconds) {
        WebElement element = waitForElementPresent(by, error_message, timeout_in_seconds);
        element.click();
        return element;
    }

    private WebElement waitForElementAndSendKeys(By by, String value, String error_message, long timeout_in_seconds) {
        WebElement element = waitForElementPresent(by, error_message, timeout_in_seconds);
        element.sendKeys(value);
        return element;
    }

    private boolean waitForElementNotPresent(By by, String error_message, long timeout_in_seconds){
        WebDriverWait wait = new WebDriverWait(driver, timeout_in_seconds);
        wait.withMessage(error_message + "\n");
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    private WebElement waitForElementAndClear(By by, String error_message, long timeout_in_seconds){
        WebElement element = waitForElementPresent(by, error_message, timeout_in_seconds);
        element.clear();
        return element;
    }

    private void assertElementHasText(By by, String text, String error_message){
        WebElement element = waitForElementPresent(by, error_message);
        String element_text = element.getAttribute("text");
        Assert.assertEquals(
                error_message,
                text,
                element_text);
    }

    protected void swipeUp(int time_of_swipe){
        TouchAction action = new TouchAction(driver);
        Dimension size = driver.manage().window().getSize();
        int x = size.width / 2;
        int start_y = (int) (size.height * 0.8);
        int end_y = (int) (size.height * 0.2);
        action
                .press(x, start_y)
                .waitAction(time_of_swipe)
                .moveTo(x, end_y)
                .release().perform();
    }

    protected void swipeUpQuick(){
        swipeUp(200);
    }

    protected void swipeUpToElement(By by, String error_message, int max_swipes){
        int already_swiped = 0;
        while (driver.findElements(by).size() == 0) {
            if (already_swiped > max_swipes) {
                waitForElementPresent(by, "Cannot find element by swiping up. \n" + error_message, 0);
                return;
            }
            swipeUpQuick();
            ++already_swiped;
        }
    }

    private void swipeElementToLeft(By by, String error_message){
        WebElement element = waitForElementPresent(by, error_message, 10);
        int left_x = element.getLocation().getX();
        int right_x = left_x + element.getSize().getWidth();
        int upper_y = element.getLocation().getY();
        int lower_y = upper_y + element.getSize().getHeight();
        int middle_y = (upper_y + lower_y) / 2;

        TouchAction action = new TouchAction(driver);
        action
                .press(right_x, middle_y)
                .waitAction(500)
                .moveTo(left_x, middle_y)
                .release()
                .perform();
    }

    private int getAmountOfElements(By by){
        List elements = driver.findElements(by);
        return elements.size();
    }

    private void assertElementNotPresent(By by, String error_message){
        int amount_of_elements = getAmountOfElements(by);
        if (amount_of_elements > 0) {
            String default_message = "An element '" + by.toString() + "' supposed to be not present";
            throw new AssertionError(default_message + " " + error_message);
        }
    }

    private String waitForElementAndGetAttribute(By by, String attribute, String error_message, long timeout_in_seconds){
        WebElement element = waitForElementPresent(by, error_message, timeout_in_seconds);
        return element.getAttribute(attribute);
    }

}
