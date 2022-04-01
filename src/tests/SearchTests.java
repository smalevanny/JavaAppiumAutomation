package tests;

import lib.CoreTestCase;
import lib.ui.SearchPageObject;
import org.junit.Test;

public class SearchTests extends CoreTestCase {

    @Test
    public void testSearch(){
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.waitForSearchResult("Object-oriented programming language");
    }

    @Test
    public void testCancelSearch(){
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.waitForCancelButtonToAppear();
        searchPageObject.clickCancelSearch();
        searchPageObject.waitForCancelButtonToDisappear();
    }

    @Test
    public void testAmountOfElementsInSearchResult(){
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        String search_input = "Linkin Park Discography";
        searchPageObject.typeSearchLine(search_input);
        assertTrue("we found too few results", searchPageObject.getAmountOfFoundArticles() > 0);
    }

    @Test
    public void testAmountOfElementsInEmptySearchResult(){
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        String search_input = "hcgvsacghvashcvsdcgh";
        searchPageObject.typeSearchLine(search_input);
        searchPageObject.waitForEmptyResultsLabel();
        searchPageObject.assertThereIsNoResultOfSearch();
    }

    @Test
    public void testCheckSearchAndCancelSearch(){
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        assertTrue("we found too few results", searchPageObject.getAmountOfFoundArticles() > 0);
        searchPageObject.clickCancelSearch();
        searchPageObject.waitForSearchResultsToDisappear();
    }
}
