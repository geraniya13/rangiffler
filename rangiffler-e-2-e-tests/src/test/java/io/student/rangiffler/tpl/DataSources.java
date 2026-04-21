package io.student.rangiffler.tpl;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import org.apache.commons.lang3.StringUtils;

import javax.sql.DataSource;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public class DataSources {
    private DataSources() {}

    private static final Map<String, DataSource> dataSources = new ConcurrentHashMap<>();

    public static DataSource dataSource(String jdbcUrl) {
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
                    dsBean.setPoolSize(3);
                    dsBean.setMaxPoolSize(10);
                    return dsBean;
                }
        );
    }

}
