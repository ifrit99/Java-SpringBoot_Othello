package com.springbootdemo;

import java.util.Map;

public class OthelloServiceImpl implements OthelloService{

	@Override
	public Map<String, Integer> boadMove(int x, int y, int offsetX, int offsetY) {
		// 盤上の間繰り返す
		while (0 <= x && x < 8 && 0 <= y && y < 8) {
			// x,yにオフセット値を適用
			x += offsetX;
			y += offsetY;
			
			// x,yが配列のインデックス範囲から出たらループ終了
			if (x < 0 || y < 0 || x > 7 || y > 7) {
				break;
			}
			
			// 実際の処理を行うクラスの関数を呼び出し、返り値としてLinkedHashMapを受け取る
			resultMap = acutualProcessing(x, y, resultMap);
		}
		
		return resultMap;
	}

	@Override
	public Map<String, Integer> acutualProcessing(int x, int y, Map<String, Integer> resultMap) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}


}
