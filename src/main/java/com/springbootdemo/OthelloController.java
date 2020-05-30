package com.springbootdemo;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@SessionAttributes(names = "reqForm")
public class OthelloController {

	// OthelloServiceのインスタンスをDIコンテナから取り出す
	@Autowired
	OthelloService othelloservice;

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

		// 最初は黒のターンをセット
		reqTurn = "blackStone";
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

		// オセロ盤面配列をセット
		arg_rq.setOthelloBoad(reqBoad);

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

		// クリックしたマス目が空なら石を置く
		if (reqBoad[ry][rx] == null) {

			// 8方向の反転可否をチェックする(反転モードfalse)
			Map<String, Integer> rightCheckMap = othelloservice.boadMove(rx, ry, 1, 0, reqBoad, reqMyStone,
					reqRivalStone, false);
			Map<String, Integer> leftCheckMap = othelloservice.boadMove(rx, ry, -1, 0, reqBoad, reqMyStone,
					reqRivalStone, false);
			Map<String, Integer> upCheckMap = othelloservice.boadMove(rx, ry, 0, -1, reqBoad, reqMyStone, reqRivalStone,
					false);
			Map<String, Integer> downCheckMap = othelloservice.boadMove(rx, ry, 0, 1, reqBoad, reqMyStone,
					reqRivalStone, false);
			Map<String, Integer> rightUpCheckMap = othelloservice.boadMove(rx, ry, 1, -1, reqBoad, reqMyStone,
					reqRivalStone, false);
			Map<String, Integer> leftUpCheckMap = othelloservice.boadMove(rx, ry, -1, -1, reqBoad, reqMyStone,
					reqRivalStone, false);
			Map<String, Integer> rightDownCheckMap = othelloservice.boadMove(rx, ry, 1, 1, reqBoad, reqMyStone,
					reqRivalStone, false);
			Map<String, Integer> leftDownCheckMap = othelloservice.boadMove(rx, ry, -1, 1, reqBoad, reqMyStone,
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

			// デバッグ用変数
//			int reverseXtest = rightCheckMap.get("reverseX");
//			int reverseYtest = rightCheckMap.get("reverseY");

			// エンティティの中身を確認
//			System.out.println("------------POST-----------");
//			System.out.println(rightCheckMap.get("checkFlag"));
//			System.out.println(reverseXtest);
//			System.out.println(reverseYtest);
//			System.out.println(reqBoad[3][3]);
//			System.out.println(reqBoad[4][3]);
//			System.out.println(reqBoad[3][4]);
//			System.out.println(reqBoad[4][4]);
//			System.out.println("------------POST------------");

			if (rightCheckFlag == 1 || leftCheckFlag == 1 || upCheckFlag == 1 || downCheckFlag == 1
					|| rightUpCheckFlag == 1 || leftUpCheckFlag == 1 || rightDownCheckFlag == 1
					|| leftDownCheckFlag == 1) {
				// 反転対象が8方向いずれかにあれば石を置く(反転モードtrue)
				reqBoad[ry][rx] = reqMyStone;

				if (rightCheckFlag == 1) {
					othelloservice.boadMove(rx, ry, 1, 0, reqBoad, reqMyStone, reqRivalStone, true);
				}
				if (leftCheckFlag == 1) {
					othelloservice.boadMove(rx, ry, -1, 0, reqBoad, reqMyStone, reqRivalStone, true);
				}
				if (upCheckFlag == 1) {
					othelloservice.boadMove(rx, ry, 0, -1, reqBoad, reqMyStone, reqRivalStone, true);
				}
				if (downCheckFlag == 1) {
					othelloservice.boadMove(rx, ry, 0, 1, reqBoad, reqMyStone, reqRivalStone, true);
				}
				if (rightUpCheckFlag == 1) {
					othelloservice.boadMove(rx, ry, 1, -1, reqBoad, reqMyStone, reqRivalStone, true);
				}
				if (leftUpCheckFlag == 1) {
					othelloservice.boadMove(rx, ry, -1, -1, reqBoad, reqMyStone, reqRivalStone, true);
				}
				if (rightDownCheckFlag == 1) {
					othelloservice.boadMove(rx, ry, 1, 1, reqBoad, reqMyStone, reqRivalStone, true);
				}
				if (leftDownCheckFlag == 1) {
					othelloservice.boadMove(rx, ry, -1, 1, reqBoad, reqMyStone, reqRivalStone, true);
				}

				// 黒のターンなら"●"、白のターンなら"〇"をオセロ盤配列へ格納する
				if (rTurn == "blackStone") {
					// ターンチェンジ
					rTurn = "whiteStone";
					reqMyStone = "〇";
					reqRivalStone = "●";
				} else if (rTurn == "whiteStone") {
					// ターンチェンジ
					rTurn = "blackStone";
					reqMyStone = "●";
					reqRivalStone = "〇";
				}

			} else {
				model.addAttribute("cantput", "エラー：そこには置けません");
			}

		}

		// 自分の石をセット
		session_rq.setMyStone(reqMyStone);

		// 敵の石をセット
		session_rq.setRivalStone(reqRivalStone);

		// ターンをセット
		session_rq.setStrTurn(rTurn);

		// オセロ盤面配列をセット
		session_rq.setOthelloBoad(reqBoad);

		// セッション保存
		setRequestForm(session_rq);

		model.addAttribute("othelloBoad", reqBoad);
		model.addAttribute("othelloBoad[y][x]", reqBoad[y][x]);
		model.addAttribute("title", "オセロ");
		model.addAttribute("strTurn", rTurn);
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
	public String pass(Model model, @ModelAttribute("reqForm") OthelloForm session_rq, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			// セッションから値を取り出す
			String rTurn = session_rq.getStrTurn();
			String reqMyStone = session_rq.getMyStone();
			String reqRivalStone = session_rq.getRivalStone();

			// 黒のターンなら"●"、白のターンなら"〇"をオセロ盤配列へ格納する
			if (rTurn == "blackStone") {
				// ターンチェンジ
				rTurn = "whiteStone";
				reqMyStone = "〇";
				reqRivalStone = "●";
			} else if (rTurn == "whiteStone") {
				// ターンチェンジ
				rTurn = "blackStone";
				reqMyStone = "●";
				reqRivalStone = "〇";
			}

			// 自分の石をセット
			session_rq.setMyStone(reqMyStone);

			// 敵の石をセット
			session_rq.setRivalStone(reqRivalStone);

			// ターンをセット
			session_rq.setStrTurn(rTurn);

			// セッション保存
			setRequestForm(session_rq);

			model.addAttribute("title", "オセロ");
			model.addAttribute("strTurn", rTurn);

		}
		return "index";
	}

	@ModelAttribute("reqForm")
	public OthelloForm setRequestForm(OthelloForm othelloForm) {
		return othelloForm;
	}

}
