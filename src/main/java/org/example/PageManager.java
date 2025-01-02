package org.example;

public class PageManager {
    private static PageManager pageManager;
    private AddPage addPage;
    private MainPage mainPage;
    private SandboxPage sandboxPage;

    private PageManager() {
    }

    public static PageManager getPageManager() {
        if (pageManager == null) {
            pageManager = new PageManager();
        }
        return pageManager;
    }

    public MainPage getMainPage() {
        if (mainPage == null) {
            mainPage = new MainPage();
        }
        return mainPage;
    }

    public SandboxPage getSandboxPage() {
        if (sandboxPage == null) {
            sandboxPage = new SandboxPage();
        }
        return sandboxPage;
    }

    public AddPage getAddPage() {
        if (addPage == null) {
            addPage = new AddPage();
        }
        return addPage;
    }
}
