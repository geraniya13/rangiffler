package io.student.rangiffler.jupiter.extension;

import io.student.rangiffler.data.DataBases;

public class DatabasesExtension implements SuiteExtension {

    @Override
    public void afterSuite() {
        DataBases.closeAllConnections();
    }
}