package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class SearchPageObject extends MainPageObject{

    private static final String
            SEARCH_INIT_ELEMENT             = "//*[contains(@text, 'Search Wikipedia')]",
            SEARCH_INPUT                    = "//*[contains(@text, 'Search…')]",
            SEARCH_CANCEL_BUTTON            = "org.wikipedia:id/search_close_btn",
            SEARCH_RESULT_BY_SUBSTRING_TPL  = "//*[@text='{SUBSTRING}']",
            SEARCH_RESULTS_ELEMENT          = "org.wikipedia:id/search_results_list",
            EMPTY_RESULT_LABEL              = "//*[@text='No results found']";

    public SearchPageObject(AppiumDriver driver){
        super(driver);
    }

    /* Template methods */
    private static String getResultSearchElement(String substring){
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }
    /* Template methods */

    public void initSearchInput(){
        this.waitForElementPresent(By.xpath(SEARCH_INIT_ELEMENT), "Cannot find init search element");
        this.waitForElementAndClick(By.xpath(SEARCH_INIT_ELEMENT), "Cannot find and click init search element", 5);
    }

    public void typeSearchLine(String search_line){
        this.waitForElementAndSendKeys(By.xpath(SEARCH_INPUT), search_line, "Cannot find and type into search input", 5);
    }

    public void waitForSearchResult(String substring){
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementPresent(By.xpath(search_result_xpath), "Cannot find search result with substring " + substring);
    }

    public void clickByArticleWithSubstring(String substring){
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementAndClick(By.xpath(search_result_xpath), "Cannot find and click search result with substring " + substring, 10);
    }

    public void waitForCancelButtonToAppear(){
        this.waitForElementPresent(By.id(SEARCH_CANCEL_BUTTON), "Cannot find search cancel button", 5);
    }

    public void waitForCancelButtonToDisappear(){
        this.waitForElementNotPresent(By.id(SEARCH_CANCEL_BUTTON), "Search cancel button is still present", 5);
    }

    public void waitForSearchResultsToDisappear(){
        this.waitForElementNotPresent(By.id(SEARCH_RESULTS_ELEMENT), "Search results are still present", 5);
    }

    public void clickCancelSearch(){
        this.waitForElementAndClick(By.id(SEARCH_CANCEL_BUTTON), "Cannot find and click search cancel button", 5);
    }

    public int getAmountOfFoundArticles(){
        this.waitForElementPresent(
                By.id(SEARCH_RESULTS_ELEMENT),
                "Cannot find anything by the request ",
                15);

        return this.getAmountOfElements(By.id(SEARCH_RESULTS_ELEMENT));
    }

    public void waitForEmptyResultsLabel(){
        this.waitForElementPresent(
                By.xpath(EMPTY_RESULT_LABEL),
                "Cannot find empty result label",
                15);
    }

    public void assertThereIsNoResultOfSearch(){
        this.assertElementNotPresent(By.id(SEARCH_RESULTS_ELEMENT), "We supposed not to find any results");
    }
}
