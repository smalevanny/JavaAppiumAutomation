package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;

public class ArticlePageObject extends MainPageObject{


    public static final String
            TITLE                       = "id:org.wikipedia:id/view_page_title_text",
            FOOTER_ELEMENT              = "xpath://*[@text='View page in browser']",
            OPTIONS_BUTTON              = "id:More options",
            OPTIONS_MENU                = "xpath://android.widget.ListView",
            ADD_TO_MY_LIST_MENU_ITEM    = "xpath://*[@text='Add to reading list']",
            ADD_TO_MY_LIST_OVERLAY      = "id:org.wikipedia:id/onboarding_button",
            MY_LIST_NAME_INPUT          = "id:org.wikipedia:id/text_input",
            MY_LIST_OK_BUTTON           = "xpath://*[@text='OK']",
            CLOSE_ARTICLE_BUTTON        = "id:Navigate up",
            EXISTING_FOLDER_ELEMENT_TPL = "xpath://*[@text='{FOLDER_NAME}']";

    /* Template methods */
    private static String getExistingFolderElement(String folder_name){
        return EXISTING_FOLDER_ELEMENT_TPL.replace("{FOLDER_NAME}", folder_name);
    }
    /* Template methods */

    public ArticlePageObject(AppiumDriver driver){
        super(driver);
    }

    public WebElement waitForTitleElement(){
        return waitForElementPresent(TITLE, "Cannot find article title on page", 15);
    }

    public String getArticleTitle(){
        WebElement element = waitForTitleElement();
        return element.getAttribute("text");
    }

    public void swipeToFooter(){
        this.swipeUpToElement(FOOTER_ELEMENT, "Cannot find the end of article", 20);
    }

    public void addArticleToMyList(String folder_name, Boolean newReadingList){
        this.waitForElementAndClick(
                OPTIONS_BUTTON,
                "Cannot find button to open article options",
                5);

        this.waitForElementPresent(
                OPTIONS_MENU,
                "Cannot find options menu",
                15);

        this.waitForElementAndClick(
                ADD_TO_MY_LIST_MENU_ITEM,
                "Cannot find option to add article to reading list",
                5);

        if (newReadingList) {
            this.waitForElementAndClick(
                    ADD_TO_MY_LIST_OVERLAY,
                    "Cannot find 'GOT IT' tip overlay",
                    5);

            this.waitForElementAndClear(
                    MY_LIST_NAME_INPUT,
                    "Cannot find input to set name articles folder",
                    5);

            this.waitForElementAndSendKeys(
                    MY_LIST_NAME_INPUT,
                    folder_name,
                    "Cannot put text into articles folder name input",
                    5);

            this.waitForElementAndClick(
                    MY_LIST_OK_BUTTON,
                    "Cannot find 'OK' button",
                    5);
        } else {
            this.waitForElementAndClick(
                    getExistingFolderElement(folder_name),
                    "Cannot find option to add article to reading list",
                    5);
        }
    }

    public void addArticleToMyList(String folder_name){
        addArticleToMyList(folder_name, true);
    }



    public void closeArticle(){
        this.waitForElementAndClick(
                CLOSE_ARTICLE_BUTTON,
                "Cannot find X button",
                5);
    }
}
