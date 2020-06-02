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
				"INSERT INTO othello(othelloBoad[][], strTurn, myStone, rivalStone, countMap, created VALUES(?, ?, ?, ?, ?, ?)",
				othello.getOthelloBoad(), othello.getStrTurn(), othello.getMyStone(), othello.getRivalStone(),
				othello.getCountMap(), othello.getCreated());

	}

	@Override
	public List<Othello> getAll() {
		String sql = "SELECT id, othelloBoad[][], strTurn, myStone, rivalStone, countMap, created FROM othello";
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql);
		List<Othello> list = new ArrayList<Othello>();
		for (Map<String, Object> result : resultList) {
			Othello othello = new Othello();
			othello.setId((int) result.get("id"));
			othello.setOthelloBoad((String[][]) result.get("othelloBoad[][]"));
			othello.setStrTurn((String) result.get("strTurn"));
			othello.setMyStone((String) result.get("myStone"));
			othello.setRivalStone((String) result.get("rivalStone"));
			othello.setCountMap(automaticCast(result.get("countMap")));
			othello.setCreated(((Timestamp) result.get("created")).toLocalDateTime());
			list.add(othello);
		}
		return list;
	}

	// オブジェクト型から呼び出し元の型へ自動キャスト
	@SuppressWarnings("unchecked")
	public static <T> T automaticCast(Object src) {
		T castedObject = (T) src;
		return castedObject;
	}
}
