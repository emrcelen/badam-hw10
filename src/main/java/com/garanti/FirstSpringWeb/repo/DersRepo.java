package com.garanti.FirstSpringWeb.repo;

import com.garanti.FirstSpringWeb.model.Ders;
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
public class DersRepo {
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<Ders> getAll() {
        RowMapper<Ders> rowMapper = new RowMapper<Ders>() {
            @Override
            public Ders mapRow(ResultSet result, int rowNum) throws SQLException {
                return new Ders(result.getInt("ID"), result.getInt("OGR_ID"), result.getInt("KONU_ID"));
            }
        };
        return jdbcTemplate.query("select * from BILGE.DERS", rowMapper);
    }

    public Ders getById(int id) {

        Ders ders;
        String sql = "select * from BILGE.DERS where ID = :ID";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("ID", id);
        ders = namedParameterJdbcTemplate.queryForObject(sql, paramMap, BeanPropertyRowMapper.newInstance(Ders.class));
        return ders;
    }

    public boolean deleteById(int id) {
        String sql = "delete from BILGE.DERS where ID = :ID";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("ID", id);
        return namedParameterJdbcTemplate.update(sql, paramMap) == 1;
    }

    public boolean save(Ders ders) {
        String sql = "INSERT INTO BILGE.DERS(OGR_ID,KONU_ID) VALUES (:OGR_ID, :KONU_ID)";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("OGR_ID",ders.getOGR_ID());
        paramMap.put("KONU_ID",ders.getKONU_ID());
        return namedParameterJdbcTemplate.update(sql,paramMap) == 1;
    }




}
