package com.springbootdemo;

import java.util.Map;

public class OthelloForm {

	private int x; // オセロ盤インデックスx

	private int y; // オセロ盤インデックスy

	private String othelloBoad[][] = new String[9][9]; // オセロ盤用配列

	private String strTurn; // 黒白ターン切替用

	private String myStone; // 自分の石

	private String rivalStone; // 敵の石
	
	private Map<String,Integer> countMap;	// 石カウント用Map

	public OthelloForm() {

	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
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

	public Map<String,Integer> getCountMap() {
		return countMap;
	}

	public void setCountMap(Map<String,Integer> countMap) {
		this.countMap = countMap;
	}

}
