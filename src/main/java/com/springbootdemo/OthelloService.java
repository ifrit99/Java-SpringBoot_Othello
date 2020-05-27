package com.springbootdemo;

import java.util.LinkedHashMap;
import java.util.Map;

public interface OthelloService {
	// 戻り値で使用するMap
	Map<String, Integer> resultMap = new LinkedHashMap<>();
	
	// 反転対象探索用の移動処理（x=置いたX座標、ｙ＝置いたY座標、offsetX=オフセット値X、offsetY=オフセット値Y）
	public Map<String,Integer> boadMove(int x, int y, int offsetX, int offsetY);
	
	// 実際の処理を行う抽象メソッド
	public Map<String,Integer> acutualProcessing(int x, int y, Map<String,Integer> resultMap);
}
