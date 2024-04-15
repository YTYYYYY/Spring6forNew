package com.SpringforNew.jdbc_template;

import com.alibaba.druid.pool.DruidDataSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.sql.SQLClientInfoException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SpringJUnitConfig(locations = "classpath:bean-jdbc.xml")
public class JdbcTemplateTe {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void te2(){
        //添加数据
        String sqlInsert = "INSERT INTO t_emp (name,age,sex) VALUES (?,?,?)";
        Object[] params1 = {"李四",21,"女"};
        //int updateTimes = jdbcTemplate.update(sqlInsert, params1);

        //修改数据
        String sqlUpdate = "UPDATE t_emp SET age=? where id=?";
        Object[] params2 = {222,1};
        //int updateTimes = jdbcTemplate.update(sqlUpdate, params2);

        //删除数据
        String sqlDelete = "DELETE FROM t_emp WHERE id=?";
        //int updateTimes = jdbcTemplate.update(sqlDelete, 2);

        //查询数据
        String sqlSelect1 = "SELECT * FROM t_emp WHERE id=?";
        /*Emp empObj = jdbcTemplate.queryForObject(sqlSelect1,(resultSet,mapRow)->{
            Emp emp = new Emp();
            emp.setId(resultSet.getInt("id"));
            emp.setName(resultSet.getString("name"));
            emp.setAge(resultSet.getInt("age"));
            emp.setSex(resultSet.getString("sex"));
            return emp;
        },1);*/

        /*Emp empObj = jdbcTemplate.queryForObject(sqlSelect1,
                            new BeanPropertyRowMapper<>(Emp.class),
                            1);
        System.out.println(empObj);*/


        String sqlSelect2 = "SELECT * FROM t_emp";
        /*List<Map<String, Object>> maps = jdbcTemplate.queryForList(sqlSelect2);
        System.out.println(maps);

        List<Emp> query = jdbcTemplate.query(sqlSelect2, new BeanPropertyRowMapper<>(Emp.class));
        System.out.println(query);*/

        String sqlSelect3 = "SELECT name FROM t_emp WHERE id=?";
        String empName = jdbcTemplate.queryForObject(sqlSelect3, String.class, 1);
        System.out.println(empName);

    }


    @Test
    public void te1(){
        System.out.println(jdbcTemplate);
    }
}
