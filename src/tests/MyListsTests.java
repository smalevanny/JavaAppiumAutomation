package tests;

import lib.CoreTestCase;
import lib.ui.*;
import org.junit.Test;

public class MyListsTests extends CoreTestCase {
    @Test
    public void testSaveFirstArticleToMyListAndDeleteIt(){
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject articlePageObject = new ArticlePageObject(driver);
        articlePageObject.waitForTitleElement();
        String article_title = articlePageObject.getArticleTitle();

        String folder_name = "Learning programming";

        articlePageObject.addArticleToMyList(folder_name);
        articlePageObject.closeArticle();

        NavigationUI navigationUI = new NavigationUI(driver);
        navigationUI.clickMyLists();

        MyListsPageObject myListsPageObject = new MyListsPageObject(driver);
        myListsPageObject.openFolderByName(folder_name);
        myListsPageObject.swipeArticleToDelete(article_title);
    }

    @Test
    public void testSaveTwoArticlesToMyListAndDeleteSecondOne(){
        String search_input_for_first_article   = "Java";
        String search_input_for_second_article  = "Python";
        String search_text_for_first_article    = "Object-oriented programming language";
        String search_text_for_second_article   = "General-purpose programming language";
        String folder_name = "Learning programming";

        //search for the first article
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(search_input_for_first_article);
        searchPageObject.clickByArticleWithSubstring(search_text_for_first_article);

        //get the first article's title
        ArticlePageObject articlePageObject = new ArticlePageObject(driver);
        articlePageObject.waitForTitleElement();
        String title_for_the_first_article = articlePageObject.getArticleTitle();

        //add first article to new reading list
        articlePageObject.addArticleToMyList(folder_name);
        articlePageObject.closeArticle();

        //search for the second article
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(search_input_for_second_article);
        searchPageObject.clickByArticleWithSubstring(search_text_for_second_article);

        //get the second article's title
        articlePageObject.waitForTitleElement();
        String title_for_the_second_article = articlePageObject.getArticleTitle();

        //add second article to existing reading list
        articlePageObject.addArticleToMyList(folder_name, false);
        articlePageObject.closeArticle();

        //open existing reading list and delete second article
        NavigationUI navigationUI = new NavigationUI(driver);
        navigationUI.clickMyLists();
        MyListsPageObject myListsPageObject = new MyListsPageObject(driver);
        myListsPageObject.openFolderByName(folder_name);

        myListsPageObject.swipeArticleToDelete(title_for_the_second_article);

        //check that first article is still present
        myListsPageObject.clickArticleByTitle(title_for_the_first_article);
        articlePageObject.waitForTitleElement();
        String article_title = articlePageObject.getArticleTitle();
        assertEquals(
                "We see unexpected text",
                title_for_the_first_article,
                article_title);
    }
}
