package com.springbootdemo;

import java.util.Map;

public interface OthelloService {
	
	// 反転対象探索用の移動処理（x=置いたX座標、ｙ＝置いたY座標、offsetX=オフセット値X、offsetY=オフセット値Y、othelloBoad=オセロ盤配列、reverseMode=反転モードフラグ）
	public Map<String,Integer> boadMove(int x, int y, int offsetX, int offsetY, String othelloBoad[][], String myStone, String rivalStone, Boolean reverseMode);
	
	// 反転可否チェックメソッド
	public Map<String,Integer> check(int x, int y, Map<String,Integer> resultMap, String othelloBoad[][], String myStone, String rivalStone);
	
	// 反転メソッド
	public Map<String,Integer> reverse(int x, int y, Map<String,Integer> resultMap, String othelloBoad[][], String myStone, String rivalStone);
	
}
