package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class MyListsPageObject extends MainPageObject{

    private static final String
        FOLDER_BY_NAME_TPL      = "//*[@text='{FOLDER_NAME}']",
        ARTICLE_BY_TITLE_TPL    = "//*[@text='{TITLE}']";

    public MyListsPageObject(AppiumDriver driver){
        super(driver);
    }

    /* Template methods */
    private static String getFolderXpathByName(String folder_name){
        return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}", folder_name);
    }

    private static String getdArticleXpathByTitle(String article_title){
        return ARTICLE_BY_TITLE_TPL.replace("{TITLE}", article_title);
    }
    /* Template methods */

    public void opeFolderByName(String folder_name){
        this.waitForElementAndClick(
                By.xpath(getFolderXpathByName(folder_name)),
                "Cannot find folder by name" + folder_name,
                5);
    }

    public void swipeArticleToDelete(String article_title){
        this.waitFotArticleToAppearByTitle(article_title);
        this.swipeElementToLeft(
                By.xpath(getdArticleXpathByTitle(article_title)),
                "Cannot find saved article");
        this.waitFotArticleToDisappearByTitle(article_title);
    }

    public void waitFotArticleToAppearByTitle(String article_title){
        this.waitForElementPresent(
                By.xpath(getdArticleXpathByTitle(article_title)),
                "Cannot find saved article with title " + article_title,
                5);
    }

    public void waitFotArticleToDisappearByTitle(String article_title){
        this.waitForElementNotPresent(
                By.xpath(getdArticleXpathByTitle(article_title)),
                "Saved article still present with title " + article_title,
                15);
    }
    public void clickArticleByTitle(String article_title){
        this.waitForElementAndClick(
                By.xpath(getdArticleXpathByTitle(article_title)),
                "Cannot find article with title " + article_title,
                5);
    }
}
