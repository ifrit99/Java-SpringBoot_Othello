package com.springbootdemo;

import java.util.HashMap;

public interface OthelloService {

	// 反転対象探索用の移動処理
	public HashMap<int, int, boolean> arrayMove();
	
	// 実際の処理定義用
	void acutualProcessing();
}
