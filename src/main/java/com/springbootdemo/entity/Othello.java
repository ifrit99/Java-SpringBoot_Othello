package com.springbootdemo.entity;

import java.time.LocalDateTime;

public class Othello {
	private int id;
	private String strBoad;
	private String strTurn;
	private String myStone;
	private String rivalStone;
	private int blackCount;
	private int whiteCount;
	private LocalDateTime created;

	public Othello() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStrBoad() {
		return strBoad;
	}

	public void setStrBoad(String strBoad) {
		this.strBoad = strBoad;
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

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	public int getBlackCount() {
		return blackCount;
	}

	public void setBlackCount(int blackCount) {
		this.blackCount = blackCount;
	}

	public int getWhiteCount() {
		return whiteCount;
	}

	public void setWhiteCount(int whiteCount) {
		this.whiteCount = whiteCount;
	}

}
