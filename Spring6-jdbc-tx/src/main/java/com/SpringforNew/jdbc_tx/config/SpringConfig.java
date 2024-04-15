package com.SpringforNew.jdbc_tx.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration  //配置类
@ComponentScan("com.SpringforNew.jdbc_tx")  //组件扫描
@EnableTransactionManagement    //开启事务
public class SpringConfig {
    @Bean(name = "druidDataSource")
    public DataSource getDataSource(){
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUsername("root");
        druidDataSource.setPassword("yty");
        druidDataSource.setUrl("jdbc:mysql://localhost:3306/spring_jdbc_bookshop?serverTimezero=GMT%2b8");
        druidDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        return druidDataSource;
    }
    @Bean(name = "jdbcTemplate")
    public JdbcTemplate getJdbcTemplate(DataSource druidDataSource){
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(druidDataSource);
        return jdbcTemplate;
    }
    @Bean(name = "transactionManager")
    public DataSourceTransactionManager getDataSourceTransactionManager(DataSource druidDataSource){
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(druidDataSource);
        return transactionManager;
    }
}
