package com.corereach.communication.common.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @Description: TODO
 * @author ga.zhang
 * @date 2020/2/5 21:42
 * @version V1.0
 **/
@Configuration
public class DataSourceConfig {

    @Value("${wechatservice.datasource.driver-class-name}")
    private String driverName;

    @Value("${wechatservice.datasource.url}")
    private String url;

    @Value("${wechatservice.datasource.username}")
    private String username;

    @Value("${wechatservice.datasource.password}")
    private String password;


    @Bean("sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(
            @Qualifier("druidDataSource") DataSource dataSource) {
        try {
            SqlSessionFactoryBean sqlSessionFactoryBean =
                    new SqlSessionFactoryBean();
            sqlSessionFactoryBean
                    .setTypeAliasesPackage("com.corereach.communication.dal.domain");
            PathMatchingResourcePatternResolver resolver =
                    new PathMatchingResourcePatternResolver();
            Resource[] resources = resolver
                    .getResources("classpath:mapper/*.xml");
            sqlSessionFactoryBean.setMapperLocations(resources);
            sqlSessionFactoryBean.setDataSource(dataSource);
            return sqlSessionFactoryBean.getObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Bean("sqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(
            @Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean("druidDataSource")
    public DataSource druid() {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(url);
        druidDataSource.setDriverClassName(driverName);
        druidDataSource.setUsername(username);
        druidDataSource.setPassword(password);

        druidDataSource.setInitialSize(8);
        druidDataSource.setMinIdle(5);
        druidDataSource.setMaxActive(20);
        druidDataSource.setMaxWait(60000);
        druidDataSource.setTimeBetweenEvictionRunsMillis(60000);
        druidDataSource.setMinEvictableIdleTimeMillis(300000);
        druidDataSource.setValidationQuery("SELECT 1 FROM DUAL");
        druidDataSource.setMinEvictableIdleTimeMillis(300000);
        druidDataSource.setTestWhileIdle(true);
        druidDataSource.setTestOnBorrow(false);
        druidDataSource.setTestOnReturn(false);
        druidDataSource.setPoolPreparedStatements(true);
        druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(20);
        Properties properties = new Properties();
        properties.setProperty("druid.stat.mergeSql", "true");
        properties.setProperty("druid.stat.slowSqlMillis", "500");
        druidDataSource.setConnectProperties(properties);

        return druidDataSource;
    }

}
