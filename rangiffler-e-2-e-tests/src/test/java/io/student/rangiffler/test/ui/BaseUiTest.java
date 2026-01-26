package io.student.rangiffler.test.ui;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import io.student.rangiffler.test.BaseTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

public class BaseUiTest extends BaseTest {
    @BeforeAll
    public static void beforeAll() {
        Configuration.baseUrl = CFG.frontUrl();
    }

    @AfterEach
    public void afterEach() {
        Selenide.closeWebDriver();
    }
}
