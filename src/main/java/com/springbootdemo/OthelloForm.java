package com.springbootdemo;

public class OthelloForm {

	private int x;

	private int y;

	private String othelloBoad[][] = new String[8][8];
	
	private String strTurn;

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

}
