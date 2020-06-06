package com.springbootdemo.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.springbootdemo.entity.Othello;

@Repository
public class OthelloDaoImpl implements OthelloDao {

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public OthelloDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void insertOthello(Othello othello) {
		jdbcTemplate.update(
				"INSERT INTO othello(strBoad, strTurn, myStone, rivalStone, blackCount, whiteCount, created) VALUES(?, ?, ?, ?, ?, ?, ?)",
				othello.getStrBoad(), othello.getStrTurn(), othello.getMyStone(), othello.getRivalStone(),
				othello.getBlackCount(), othello.getWhiteCount(), othello.getCreated());

	}

	@Override
	public List<Othello> getAll() {
		String sql = "SELECT id, strBoad, strTurn, myStone, rivalStone, blackCount, whiteCount, created FROM othello";
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql);
		List<Othello> list = new ArrayList<Othello>();
		for (Map<String, Object> result : resultList) {
			Othello othello = new Othello();
			othello.setId((int) result.get("id"));
			othello.setStrBoad((String) result.get("strBoad"));
			othello.setStrTurn((String) result.get("strTurn"));
			othello.setMyStone((String) result.get("myStone"));
			othello.setRivalStone((String) result.get("rivalStone"));
			othello.setBlackCount((int) result.get("blackCount"));
			othello.setWhiteCount((int) result.get("whiteCount"));
			othello.setCreated(((Timestamp) result.get("created")).toLocalDateTime());
			list.add(othello);
		}
		return list;
	}
}
