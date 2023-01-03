package com.garanti.FirstSpringWeb.config;

import com.garanti.FirstSpringWeb.Constants;
import com.garanti.FirstSpringWeb.model.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class BeanFactory
{
    /*public BeanFactory()
    {
        System.err.println("----> Bean factory new yapılıyor");
    }*/
    @Bean(value = "myDS")
    public DataSource getDataSource(){
        DriverManagerDataSource ds = new DriverManagerDataSource(Constants.URL, Constants.USER, Constants.PASSWORD);
        ds.setDriverClassName("oracle.jdbc.OracleDriver");
        return ds;
    }
    @Bean
    public JdbcTemplate getJdbcTemplate()
    {
        return new JdbcTemplate(getDataSource());
    }
    @Bean
    @DependsOn(value = "myDS")
    public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate(DriverManagerDataSource ds){
        return new NamedParameterJdbcTemplate(ds);
    }

    @Bean(name = "person1")
    public Person getPerson()
    {
        return new Person(12, "şerafettin");
    }
}