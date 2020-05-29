package com.springbootdemo;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class OthelloServiceImpl implements OthelloService {

	// 反転対象探索用のothelloBoad配列内の移動処理
	@Override
	public Map<String, Integer> boadMove(int x, int y, int offsetX, int offsetY, String[][] othelloBoad, String myStone,
			String rivalStone, Boolean reverseMode) {

		// 戻り値で使用するMap
		Map<String, Integer> resultMap = new LinkedHashMap<>();
		// 初期化
		resultMap.put("reverseX", 0);
		resultMap.put("reverseY", 0);
		resultMap.put("checkFlag", 0);

		// 盤上の間繰り返す
		while (0 <= x && x < 8 && 0 <= y && y < 8) {
			// x,yにオフセット値を適用
			x += offsetX;
			y += offsetY;

			// x,yが配列のインデックス範囲から出たらループ終了
			if (x < 0 || y < 0 || x > 7 || y > 7) {
				break;
			}

			if (reverseMode) { // reverseModeオンオフで処理を分岐
				// 反転処理呼び出し
				resultMap = reverse(x, y, resultMap, othelloBoad, myStone, rivalStone);
			} else {
				// 反転可否チェック呼び出し
				resultMap = check(x, y, resultMap, othelloBoad, myStone, rivalStone);
			}

			if (resultMap.get("checkFlag") == 1 || (resultMap.get("reverseX") == 0 && resultMap.get("reverseY") == 0)) {
				break;
			}
		}

		return resultMap;
	}

	// 反転可否チェック
	@Override
	public Map<String, Integer> check(int x, int y, Map<String, Integer> resultMap, String othelloBoad[][],
			String myStone, String rivalStone) {
		int reverseX = resultMap.get("reverseX");
		int reverseY = resultMap.get("reverseY");

		if (othelloBoad[y][x] == rivalStone) {
			// オフセットで移動した先が敵の色の石だったらx座標、y座標を更新する
			resultMap.put("reverseX", x);
			resultMap.put("reverseY", y);
			return resultMap;
		} else if (othelloBoad[y][x] == myStone) {
			// オフセットで移動した先が自分の色の石だったら反転可否の判定へ
			if (reverseX > 0 || reverseY > 0) {
				// 敵の石が見つかっていれば反転可能なのでチェックフラグをtrueにする
				resultMap.put("checkFlag", 1);
			}
			return resultMap;
		} else {
			// オフセットで移動した先に石がなかったら{ reverseX: 0, reverseY: 0, checkFlag: 0 }を返す
			resultMap.put("reverseX", 0);
			resultMap.put("reverseY", 0);
			resultMap.put("checkFlag", 0);
			return resultMap;
		}

	}

	@Override
	public Map<String, Integer> reverse(int x, int y, Map<String, Integer> resultMap, String[][] othelloBoad,
			String myStone, String rivalStone) {
		// 処理継続の為にreverseXとreverseYを初期化、checkFlagはtrueにする
		resultMap.put("reverseX", -1);
		resultMap.put("reverseY", -1);
		resultMap.put("checkFlag", 1);

		System.out.println("---------reverse if前----------");
		System.out.println(othelloBoad[y][x]);
		System.out.println("---------reverse if前----------");
		// 反転処理
		if (othelloBoad[y][x] == rivalStone) {
			othelloBoad[y][x] = myStone;
			resultMap.put("checkFlag", 0);
		}
		System.out.println("---------reverse if後----------");
		System.out.println(othelloBoad[y][x]);
		System.out.println("---------reverse if後----------");
		return resultMap;
	}

}
