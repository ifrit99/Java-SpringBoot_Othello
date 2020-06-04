package com.springbootdemo;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.springbootdemo.entity.Othello;
import com.springbootdemo.service.OthelloDbService;

@Controller
@SessionAttributes(names = "reqForm")
public class OthelloController {

	private final OthelloDbService othelloDbService;

	// OthelloDbServiceのインスタンスをDIコンテナから取り出す
	@Autowired
	public OthelloController(OthelloDbService othelloDbService) {
		this.othelloDbService = othelloDbService;
	}

	// OthelloServiceのインスタンスをDIコンテナから取り出す
	@Autowired
	OthelloService othelloService;

	@GetMapping
	public String getOthello(Model model, @ModelAttribute("reqForm") OthelloForm arg_rq) {

		// オセロデータのエンティティを初期化
		String reqBoad[][] = arg_rq.getOthelloBoad();

		// ターン変数のエンティティを初期化
		String reqTurn = arg_rq.getStrTurn();

		// 自分の石のエンティティを初期化
		String reqMyStone = arg_rq.getMyStone();

		// 敵の石のエンティティを初期化
		String reqRivalStone = arg_rq.getRivalStone();

		// 石カウントMapのエンティティを初期化
		Map<String, Integer> reqCountMap = arg_rq.getCountMap();

		// strBoadのエンティティを初期化
		String reqStrBoad = arg_rq.getStrBoad();

		// blackCountのエンティティを初期化
		int reqBlackCount = arg_rq.getBlackCount();

		// whiteCountのエンティティを初期化
		int reqWhiteCount = arg_rq.getWhiteCount();

		// 最初は黒のターンをセット
		reqTurn = "黒の番です";
		arg_rq.setStrTurn(reqTurn);

		// 最初は黒の番なので自分の石は"●"、敵の石は"〇"
		reqMyStone = "●";
		arg_rq.setMyStone(reqMyStone);
		reqRivalStone = "〇";
		arg_rq.setRivalStone(reqRivalStone);

		// 初期配置の中央の石4つを配列へ入れておく
		reqBoad[3][3] = "●";
		reqBoad[4][3] = "〇";
		reqBoad[3][4] = "〇";
		reqBoad[4][4] = "●";

		// 2次元配列reqBoadをListへ変換してからカンマ区切りの1つの文字列へ変換
		List<String> list = Arrays.stream(reqBoad).map(line -> String.join(",", line)).collect(Collectors.toList());
		String str = String.join(",", list);

		// reqStrBoadに格納してセッションへセット
		reqStrBoad = str;
		arg_rq.setStrBoad(reqStrBoad);

		System.out.println("-------------CSV化テスト---------------");
		System.out.println(str);
		System.out.println("-------------CSV化テスト---------------");

		// オセロ盤面配列をセット
		arg_rq.setOthelloBoad(reqBoad);

		// 石をカウントしてセット
		reqCountMap = othelloService.count(reqBoad);
		arg_rq.setCountMap(reqCountMap);

		// 黒石と白石の数をDB格納用の変数へセット
		reqBlackCount = reqCountMap.get("blackStone");
		reqWhiteCount = reqCountMap.get("whiteStone");
		arg_rq.setBlackCount(reqBlackCount);
		arg_rq.setWhiteCount(reqWhiteCount);

		// セッション保存
		setRequestForm(arg_rq);

		// エンティティの中身を確認
//		System.out.println("------------GET------------");
//		System.out.println(reqMyStone);
//		System.out.println(reqBoad[3][3]);
//		System.out.println(reqBoad[4][3]);
//		System.out.println(reqBoad[3][4]);
//		System.out.println(reqBoad[4][4]);
//		System.out.println("------------GET------------");

		// エンティティをThymeleafの変数に設定
		model.addAttribute("title", "オセロ");
		model.addAttribute("othelloBoad", reqBoad);
		model.addAttribute("othelloForm", arg_rq);
		model.addAttribute("strTurn", reqTurn);
		model.addAttribute("blackCount", reqCountMap.get("blackStone"));
		model.addAttribute("whiteCount", reqCountMap.get("whiteStone"));

		return "index";
	}

	@PostMapping(params = "postOthello")
	public String postOthello(Model model, @RequestParam("x") int x, @RequestParam("y") int y,
			@ModelAttribute("reqForm") OthelloForm session_rq) {

		// セッションから値を取り出す
		String[][] reqBoad = session_rq.getOthelloBoad();
		int rx = session_rq.getX();
		int ry = session_rq.getY();
		String rTurn = session_rq.getStrTurn();
		String reqMyStone = session_rq.getMyStone();
		String reqRivalStone = session_rq.getRivalStone();
		Map<String, Integer> reqCountMap = session_rq.getCountMap();
		String reqStrBoad = session_rq.getStrBoad();
		int reqBlackCount = session_rq.getBlackCount();
		int reqWhiteCount = session_rq.getWhiteCount();

		// クリックしたマス目が空なら石を置く
		if (reqBoad[ry][rx] == null) {

			// 8方向の反転可否をチェックする(反転モードfalse)
			Map<String, Integer> rightCheckMap = othelloService.boadMove(rx, ry, 1, 0, reqBoad, reqMyStone,
					reqRivalStone, false);
			Map<String, Integer> leftCheckMap = othelloService.boadMove(rx, ry, -1, 0, reqBoad, reqMyStone,
					reqRivalStone, false);
			Map<String, Integer> upCheckMap = othelloService.boadMove(rx, ry, 0, -1, reqBoad, reqMyStone, reqRivalStone,
					false);
			Map<String, Integer> downCheckMap = othelloService.boadMove(rx, ry, 0, 1, reqBoad, reqMyStone,
					reqRivalStone, false);
			Map<String, Integer> rightUpCheckMap = othelloService.boadMove(rx, ry, 1, -1, reqBoad, reqMyStone,
					reqRivalStone, false);
			Map<String, Integer> leftUpCheckMap = othelloService.boadMove(rx, ry, -1, -1, reqBoad, reqMyStone,
					reqRivalStone, false);
			Map<String, Integer> rightDownCheckMap = othelloService.boadMove(rx, ry, 1, 1, reqBoad, reqMyStone,
					reqRivalStone, false);
			Map<String, Integer> leftDownCheckMap = othelloService.boadMove(rx, ry, -1, 1, reqBoad, reqMyStone,
					reqRivalStone, false);

			// 反転対象が見つかったかのフラグを変数へ格納する
			int rightCheckFlag = rightCheckMap.get("checkFlag");
			int leftCheckFlag = leftCheckMap.get("checkFlag");
			int upCheckFlag = upCheckMap.get("checkFlag");
			int downCheckFlag = downCheckMap.get("checkFlag");
			int rightUpCheckFlag = rightUpCheckMap.get("checkFlag");
			int leftUpCheckFlag = leftUpCheckMap.get("checkFlag");
			int rightDownCheckFlag = rightDownCheckMap.get("checkFlag");
			int leftDownCheckFlag = leftDownCheckMap.get("checkFlag");

			if (rightCheckFlag == 1 || leftCheckFlag == 1 || upCheckFlag == 1 || downCheckFlag == 1
					|| rightUpCheckFlag == 1 || leftUpCheckFlag == 1 || rightDownCheckFlag == 1
					|| leftDownCheckFlag == 1) {
				// 反転対象が8方向いずれかにあれば石を置く(反転モードtrue)
				reqBoad[ry][rx] = reqMyStone;

				if (rightCheckFlag == 1) {
					othelloService.boadMove(rx, ry, 1, 0, reqBoad, reqMyStone, reqRivalStone, true);
				}
				if (leftCheckFlag == 1) {
					othelloService.boadMove(rx, ry, -1, 0, reqBoad, reqMyStone, reqRivalStone, true);
				}
				if (upCheckFlag == 1) {
					othelloService.boadMove(rx, ry, 0, -1, reqBoad, reqMyStone, reqRivalStone, true);
				}
				if (downCheckFlag == 1) {
					othelloService.boadMove(rx, ry, 0, 1, reqBoad, reqMyStone, reqRivalStone, true);
				}
				if (rightUpCheckFlag == 1) {
					othelloService.boadMove(rx, ry, 1, -1, reqBoad, reqMyStone, reqRivalStone, true);
				}
				if (leftUpCheckFlag == 1) {
					othelloService.boadMove(rx, ry, -1, -1, reqBoad, reqMyStone, reqRivalStone, true);
				}
				if (rightDownCheckFlag == 1) {
					othelloService.boadMove(rx, ry, 1, 1, reqBoad, reqMyStone, reqRivalStone, true);
				}
				if (leftDownCheckFlag == 1) {
					othelloService.boadMove(rx, ry, -1, 1, reqBoad, reqMyStone, reqRivalStone, true);
				}

				// 黒のターンなら"●"、白のターンなら"〇"をオセロ盤配列へ格納する
				if (rTurn == "黒の番です") {
					// ターンチェンジ
					rTurn = "白の番です";
					reqMyStone = "〇";
					reqRivalStone = "●";
				} else if (rTurn == "白の番です") {
					// ターンチェンジ
					rTurn = "黒の番です";
					reqMyStone = "●";
					reqRivalStone = "〇";
				}

				// 石をカウント
				reqCountMap = othelloService.count(reqBoad);

			} else {
				model.addAttribute("cantput", "エラー：そこには置けません");
			}

		} else {
			model.addAttribute("cantput", "エラー：既に石が置かれています");
		}

		// 自分の石をセット
		session_rq.setMyStone(reqMyStone);

		// 敵の石をセット
		session_rq.setRivalStone(reqRivalStone);

		// ターンをセット
		session_rq.setStrTurn(rTurn);

		// オセロ盤面配列をセット
		session_rq.setOthelloBoad(reqBoad);

		// 石カウントMapをセット
		session_rq.setCountMap(reqCountMap);

		// 黒石と白石の数をDB格納用の変数へセット
		reqBlackCount = reqCountMap.get("blackStone");
		reqWhiteCount = reqCountMap.get("whiteStone");
		session_rq.setBlackCount(reqBlackCount);
		session_rq.setWhiteCount(reqWhiteCount);

		// 勝敗判定
		if (reqCountMap.get("blackStone") + reqCountMap.get("whiteStone") == 64) {
			// 黒勝利時
			if (reqCountMap.get("blackStone") > reqCountMap.get("whiteStone")) {
				model.addAttribute("judge", "ゲームセット！黒の勝利");
				// 白勝利時
			} else if (reqCountMap.get("blackStone") < reqCountMap.get("whiteStone")) {
				model.addAttribute("judge", "ゲームセット！白の勝利");
				// 引き分け時
			} else if (reqCountMap.get("blackStone") == reqCountMap.get("whiteStone")) {
				model.addAttribute("judge", "ゲームセット！ドロー");
			}
		}

		// 2次元配列reqBoadをListへ変換してからカンマ区切りの1つの文字列へ変換
		List<String> list = Arrays.stream(reqBoad).map(line -> String.join(",", line)).collect(Collectors.toList());
		String str = String.join(",", list);

		// reqStrBoadに格納してセッションへセット
		reqStrBoad = str;
		session_rq.setStrBoad(reqStrBoad);

		// セッション保存
		setRequestForm(session_rq);

		model.addAttribute("othelloBoad", reqBoad);
		model.addAttribute("othelloBoad[y][x]", reqBoad[y][x]);
		model.addAttribute("title", "オセロ");
		model.addAttribute("strTurn", rTurn);
		model.addAttribute("blackCount", reqCountMap.get("blackStone"));
		model.addAttribute("whiteCount", reqCountMap.get("whiteStone"));

		// エンティティ詰めなおし
		Othello othello = new Othello();
		othello.setStrBoad(session_rq.getStrBoad());
		othello.setStrTurn(session_rq.getStrTurn());
		othello.setMyStone(session_rq.getMyStone());
		othello.setRivalStone(session_rq.getRivalStone());
		othello.setBlackCount(session_rq.getBlackCount());
		othello.setWhiteCount(session_rq.getWhiteCount());
		othello.setCreated(LocalDateTime.now());

		// H2DBへ保存
		othelloDbService.save(othello);

		return "index";
	}

	// リセットボタンでセッション破棄
	@PostMapping(params = "reset")
	public String reset(SessionStatus sessionStatus) {

		// セッション破棄
		sessionStatus.setComplete();

		return "redirect:/";

	}

	// パスボタンで自分のターンをパス
	@PostMapping(params = "pass")
	public String pass(Model model, @ModelAttribute("reqForm") OthelloForm session_rq, @RequestParam("x") int x,
			@RequestParam("y") int y) {

		// セッションから値を取り出す
		String rTurn = session_rq.getStrTurn();
		String reqMyStone = session_rq.getMyStone();
		String reqRivalStone = session_rq.getRivalStone();
		String[][] reqBoad = session_rq.getOthelloBoad();
		Map<String, Integer> reqCountMap = session_rq.getCountMap();
		String reqStrBoad = session_rq.getStrBoad();
		int reqBlackCount = session_rq.getBlackCount();
		int reqWhiteCount = session_rq.getWhiteCount();

		// 黒のターンなら"●"、白のターンなら"〇"をオセロ盤配列へ格納する
		if (rTurn == "黒の番です") {
			// ターンチェンジ
			rTurn = "白の番です";
			reqMyStone = "〇";
			reqRivalStone = "●";
		} else if (rTurn == "白の番です") {
			// ターンチェンジ
			rTurn = "黒の番です";
			reqMyStone = "●";
			reqRivalStone = "〇";
		}

		// 自分の石をセット
		session_rq.setMyStone(reqMyStone);

		// 敵の石をセット
		session_rq.setRivalStone(reqRivalStone);

		// ターンをセット
		session_rq.setStrTurn(rTurn);

		// オセロ盤面配列をセット
		session_rq.setOthelloBoad(reqBoad);

		// 2次元配列reqBoadをListへ変換してからカンマ区切りの1つの文字列へ変換
		List<String> list = Arrays.stream(reqBoad).map(line -> String.join(",", line)).collect(Collectors.toList());
		String str = String.join(",", list);

		// reqStrBoadに格納してセッションへセット
		reqStrBoad = str;
		session_rq.setStrBoad(reqStrBoad);

		// 石カウントMapをセット
		session_rq.setCountMap(reqCountMap);

		// 黒石と白石の数をDB格納用の変数へセット
		reqBlackCount = reqCountMap.get("blackStone");
		reqWhiteCount = reqCountMap.get("whiteStone");
		session_rq.setBlackCount(reqBlackCount);
		session_rq.setWhiteCount(reqWhiteCount);

		// セッション保存
		setRequestForm(session_rq);

		model.addAttribute("othelloBoad", reqBoad);
		model.addAttribute("othelloBoad[y][x]", reqBoad[y][x]);
		model.addAttribute("title", "オセロ");
		model.addAttribute("strTurn", rTurn);
		model.addAttribute("blackCount", reqCountMap.get("blackStone"));
		model.addAttribute("whiteCount", reqCountMap.get("whiteStone"));

		return "index";
	}

	@ModelAttribute("reqForm")
	public OthelloForm setRequestForm(OthelloForm othelloForm) {
		return othelloForm;
	}

}
