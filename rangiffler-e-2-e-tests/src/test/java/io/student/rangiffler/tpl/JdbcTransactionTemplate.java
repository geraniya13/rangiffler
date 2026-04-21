package io.student.rangiffler.tpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.function.Supplier;

public class JdbcTransactionTemplate {
    private final JdbcConnectionHolder connectionHolder;
    private final AtomicBoolean closeAfterAction = new AtomicBoolean(true);

    public JdbcTransactionTemplate(String jdbcUrl) {
        this.connectionHolder = Connections.holder(jdbcUrl);
    }

    public JdbcTransactionTemplate holdConnectionAfterAction() {
        this.closeAfterAction.set(false);
        return this;
    }

    public <T> T transaction(Supplier<T> action, int isolationLevel) {
        Connection connection = null;
        try {
            connection = connectionHolder.connection();
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(isolationLevel);
            T result = action.get();
            connection.commit();
            connection.setAutoCommit(true);
            return result;
        } catch (Exception e) {
            if (connection != null) {
                try {
                    connection.rollback();
                    connection.setAutoCommit(true);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
            throw new RuntimeException(e);
        } finally {
            if (closeAfterAction.get()) {
                connectionHolder.close();

            }
        }
    }
}
