package com.springbootdemo.entity;

import java.time.LocalDateTime;
import java.util.Map;

public class Othello {
	private int id;
	private String othelloBoad[][];
	private String strTurn;
	private String myStone;
	private String rivalStone;
	private Map<String, Integer> countMap;
	private LocalDateTime created;

	public Othello() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String[][] getOthelloBoad() {
		return othelloBoad;
	}

	public void setOthelloBoad(String[][] othelloBoad) {
		this.othelloBoad = othelloBoad;
	}

	public String getStrTurn() {
		return strTurn;
	}

	public void setStrTurn(String strTurn) {
		this.strTurn = strTurn;
	}

	public String getMyStone() {
		return myStone;
	}

	public void setMyStone(String myStone) {
		this.myStone = myStone;
	}

	public String getRivalStone() {
		return rivalStone;
	}

	public void setRivalStone(String rivalStone) {
		this.rivalStone = rivalStone;
	}

	public Map<String, Integer> getCountMap() {
		return countMap;
	}

	public void setCountMap(Map<String, Integer> countMap) {
		this.countMap = countMap;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

}
