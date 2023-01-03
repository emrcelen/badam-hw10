package com.garanti.FirstSpringWeb.repo;

import com.garanti.FirstSpringWeb.model.Ders_Ogrenci;
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
public class Ders_OgrenciRepo {
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<Ders_Ogrenci> getAll() {
        RowMapper<Ders_Ogrenci> rowMapper = new RowMapper<Ders_Ogrenci>() {
            @Override
            public Ders_Ogrenci mapRow(ResultSet result, int rowNum) throws SQLException {
                return new Ders_Ogrenci(result.getInt("ID"), result.getInt("DERS_ID"), result.getInt("OGRENCI_ID"), result.getInt("NOTE"), result.getInt("DEVAMSIZLIK"));
            }
        };
        return jdbcTemplate.query("select * from BILGE.DERS_OGRENCI", rowMapper);
    }

    public Ders_Ogrenci getById(int id) {

        Ders_Ogrenci dogr;
        String sql = "select * from BILGE.DERS_OGRENCI where ID = :ID";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("ID", id);
        dogr = namedParameterJdbcTemplate.queryForObject(sql, paramMap, BeanPropertyRowMapper.newInstance(Ders_Ogrenci.class));
        return dogr;
    }

    public boolean deleteById(int id) {
        String sql = "delete from BILGE.DERS_OGRENCI where ID = :ID";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("ID", id);
        return namedParameterJdbcTemplate.update(sql, paramMap) == 1;
    }

    public boolean save(Ders_Ogrenci dogr) {
        String sql = "INSERT INTO BILGE.DERS_OGRENCI(DERS_ID,OGRENCI_ID) VALUES (:DERS_ID, :OGRENCI_ID)";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("DERS_ID",dogr.getDERS_ID());
        paramMap.put("OGRENCI_ID",dogr.getOGRENCI_ID());
        return namedParameterJdbcTemplate.update(sql,paramMap) == 1;
    }




}
