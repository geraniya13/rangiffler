package io.student.rangiffler.data;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.jdbc.AtomikosDataSourceBean;

import jakarta.transaction.SystemException;
import jakarta.transaction.UserTransaction;
import org.apache.commons.lang3.StringUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Function;

public class DataBases {

    private DataBases() {
    }

    private static final Map<String, DataSource> dataSources = new ConcurrentHashMap<>();

    public record XaFunction<T>(Function<Connection, T> function, String jdbcUrl) {
    }

    public record XaConsumer(Consumer<Connection> consumer, String jdbcUrl) {
    }


    private static DataSource dataSource(String jdbcUrl) {
        return dataSources.computeIfAbsent(
                jdbcUrl,
                key -> {
                    AtomikosDataSourceBean dsBean = new AtomikosDataSourceBean();
                    final String uniqId = StringUtils.substringAfter(jdbcUrl, "3306/");
                    dsBean.setUniqueResourceName(uniqId);
                    dsBean.setXaDataSourceClassName("com.mysql.cj.jdbc.MysqlXADataSource");

                    Properties props = new Properties();
                    props.put("URL", jdbcUrl);
                    props.put("user", "root");
                    props.put("password", "secret");

                    dsBean.setXaProperties(props);
                    dsBean.setMaxPoolSize(10);
                    return dsBean;
                }
        );
    }

    private static Connection connection(String jdbcUrl) {
        try {
            return dataSource(jdbcUrl).getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get connection for " + jdbcUrl, e);
        }
    }

    public static <T> T transaction(Function<Connection, T> action, String jdbcUrl, int isolationLevel) {
        Connection connection = null;
        try {
            connection = connection(jdbcUrl);
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(isolationLevel);
            T result = action.apply(connection);
            connection.commit();
            connection.setAutoCommit(true);
            return result;
        } catch (Exception e) {
            try {
                if (connection != null) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                }
                throw e;
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } finally {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
            }
        }
    }

    public static void transaction(Consumer<Connection> consumer, String jdbcUrl, int isolationLevel) {
        Connection connection = null;
        try {
            connection = connection(jdbcUrl);
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(isolationLevel);
            consumer.accept(connection);
            connection.commit();
            connection.setAutoCommit(true);

        } catch (Exception e) {
            try {
                if (connection != null) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                }
                throw e;
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } finally {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
            }
        }
    }

    public static <T> T xaTransaction(int isolationLevel, XaFunction<T>... actions) {
        UserTransaction ut = new UserTransactionImp();
        Map<String, Connection> connections = new HashMap<>();

        try {
            ut.begin();
            T result = null;
            for (XaFunction<T> action : actions) {
                var connection = connections.
                        computeIfAbsent(
                                action.jdbcUrl(),
                                DataBases::connection);

                connection.setTransactionIsolation(isolationLevel);
                result = action.function().apply(connection);
            }
            ut.commit();
            return result;
        } catch (Exception e) {
            try {
                ut.rollback();
            } catch (SystemException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        } finally {
            closeAllConnections(connections);
        }
    }

    public static void xaTransaction(int isolationLevel, XaConsumer... consumers) {
        UserTransaction ut = new UserTransactionImp();
        Map<String, Connection> connections = new HashMap<>();

        try {
            ut.begin();
            for (XaConsumer consumer : consumers) {
                var connection = connections.
                        computeIfAbsent(
                                consumer.jdbcUrl(),
                                DataBases::connection);

                connection.setTransactionIsolation(isolationLevel);
                consumer.consumer.accept(connection);
            }
            ut.commit();
        } catch (Exception e) {
            try {
                ut.rollback();
            } catch (SystemException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        } finally {
            closeAllConnections(connections);
        }
    }


    public static void closeAllConnections(Map<String, Connection> connections) {
        for (Connection connection : connections.values()) {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
            }
        }
    }
}