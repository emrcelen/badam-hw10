package com.garanti.FirstSpringWeb.repo;

import com.garanti.FirstSpringWeb.model.Ogrenci;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@AllArgsConstructor
public class OgrenciRepo {
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<Ogrenci> getAll() {
        RowMapper<Ogrenci> rowMapper = new RowMapper<Ogrenci>() {
            @Override
            public Ogrenci mapRow(ResultSet result, int rowNum) throws SQLException {
                return new Ogrenci(result.getInt("ID"), result.getString("NAME"), result.getInt("OGR_NUMBER"),result.getInt("YEAR"));
            }
        };
        return jdbcTemplate.query("select * from BILGE.OGRENCI", rowMapper);
    }

    public Ogrenci getById(int id) {

        Ogrenci ogrenci;
        String sql = "select * from BILGE.OGRENCI where ID = :ID";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("ID", id);
        ogrenci = namedParameterJdbcTemplate.queryForObject(sql, paramMap, BeanPropertyRowMapper.newInstance(Ogrenci.class));
        return ogrenci;
    }

    public boolean deleteById(int id) {
        String sql = "delete from BILGE.OGRENCI where ID = :ID";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("ID", id);
        return namedParameterJdbcTemplate.update(sql, paramMap) == 1;
    }

    public boolean save(Ogrenci ogrenci) {
        String sql = "INSERT INTO BILGE.OGRENCI(NAME, OGR_NUMBER, YEAR) VALUES (:NAME,:OGR_NUMBER, :YEAR)";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("NAME",ogrenci.getNAME());
        paramMap.put("OGR_NUMBER",ogrenci.getOGR_NUMBER());
        paramMap.put("YEAR", ogrenci.getYEAR());
        return namedParameterJdbcTemplate.update(sql,paramMap) == 1;
    }

    public List<Ogrenci> getAllLike(String name) {
        String sql = "select * from BILGE.OGRENCI where NAME LIKE :NAME";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("NAME", "%" + name + "%");
        return namedParameterJdbcTemplate.query(sql, paramMap, BeanPropertyRowMapper.newInstance(Ogrenci.class));

    }


}
