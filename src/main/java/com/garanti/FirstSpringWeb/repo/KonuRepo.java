package com.garanti.FirstSpringWeb.repo;

import com.garanti.FirstSpringWeb.model.Konu;
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
public class KonuRepo {
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<Konu> getAll() {
        RowMapper<Konu> rowMapper = new RowMapper<Konu>() {
            @Override
            public Konu mapRow(ResultSet result, int rowNum) throws SQLException {
                return new Konu(result.getInt("ID"), result.getString("NAME"));
            }
        };
        return jdbcTemplate.query("select * from BILGE.KONU", rowMapper);
    }

    public Konu getById(int id) {

        Konu konu;
        String sql = "select * from BILGE.KONU where ID = :ID";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("ID", id);
        konu = namedParameterJdbcTemplate.queryForObject(sql, paramMap, BeanPropertyRowMapper.newInstance(Konu.class));
        return konu;
    }

    public boolean deleteById(int id) {
        String sql = "delete from BILGE.KONU where ID = :ID";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("ID", id);
        return namedParameterJdbcTemplate.update(sql, paramMap) == 1;
    }

    public boolean save(Konu konu) {
        String sql = "INSERT INTO BILGE.KONU(NAME) VALUES (:NAME)";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("NAME",konu.getNAME());
        return namedParameterJdbcTemplate.update(sql,paramMap) == 1;
    }

    public List<Konu> getAllLike(String name) {
        String sql = "select * from BILGE.KONU where NAME LIKE :NAME";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("NAME", "%" + name + "%");
        return namedParameterJdbcTemplate.query(sql, paramMap, BeanPropertyRowMapper.newInstance(Konu.class));

    }


}
