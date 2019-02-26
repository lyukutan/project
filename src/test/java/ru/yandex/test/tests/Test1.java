package ru.yandex.test.tests;

import org.testng.annotations.Test;

public class Test1 extends TestBase  {

    @Test
    public void test1() throws Exception{
        applicationManager.navigationHelper.gotoMainPage();
        applicationManager.navigationHelper.gotoNotebooks();
        applicationManager.setupFilter();
        applicationManager.printHelper.printSortedContent();
        applicationManager.printHelper.printMap();
        applicationManager.printHelper.printExpAndCheap();
    }

    @Test
    public void test2() throws Exception{
        applicationManager.navigationHelper.openSummary(applicationManager.resultHelper.getAllResults().get(0));
        applicationManager.navigationHelper.gotoPreferences();
        applicationManager.compare2powers();
        applicationManager.printDescriotion();
    }

    @Test
    public void test3() throws Exception{
        applicationManager.findFromJSON();
    }

}
