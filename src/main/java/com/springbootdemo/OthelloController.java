package com.springbootdemo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes(names = "reqForm")
public class OthelloController {

	@GetMapping("/")
	public String getOthello(Model model, @ModelAttribute("reqForm") OthelloForm arg_rq) {

		model.addAttribute("title", "オセロ");

		// オセロデータのエンティティを初期化
		String reqBoad[][] = arg_rq.getOthelloBoad();

		// ターン変数のエンティティを初期化
		String reqTurn = arg_rq.getStrTurn();

		// 最初は黒のターンをセット
		reqTurn = "blackStone";
		arg_rq.setStrTurn(reqTurn);

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
		System.out.println("------------GET------------");
		System.out.println(reqTurn);
//		System.out.println(reqBoad[3][3]);
//		System.out.println(reqBoad[4][3]);
//		System.out.println(reqBoad[3][4]);
//		System.out.println(reqBoad[4][4]);
		System.out.println("------------GET------------");

		// エンティティをThymeleafの変数に設定
		model.addAttribute("othelloBoad", reqBoad);
		model.addAttribute("othelloForm", arg_rq);

		return "index";
	}

	@PostMapping("/")
	public String postOthello(Model model, @RequestParam("x") int x, @RequestParam("y") int y,
			@RequestParam("strTurn") String strTurn, @ModelAttribute("reqForm") OthelloForm session_rq) {

		String[][] reqBoad = session_rq.getOthelloBoad();
		int rx = session_rq.getX();
		int ry = session_rq.getY();
		String rTurn = session_rq.getStrTurn();

		// クリックしたマス目が空なら石を置く
		if (reqBoad[ry][rx] == null) {

			// 黒のターンなら"●"、白のターンなら"〇"をオセロ盤配列へ格納する
//			if (rTurn == "blackStone") {
				reqBoad[ry][rx] = "●";
//			} else if (rTurn == "whiteStone") {
//				reqBoad[ry][rx] = "〇";
//			}

		}

		// ターンをセット
		session_rq.setStrTurn(rTurn);

		// オセロ盤面配列をセット
		session_rq.setOthelloBoad(reqBoad);

		// セッション保存
		setRequestForm(session_rq);

		// エンティティの中身を確認
		System.out.println("------------POST-----------");
		System.out.println(rTurn);
//		System.out.println(rx);
//		System.out.println(ry);
//		System.out.println(reqBoad[3][3]);
//		System.out.println(reqBoad[4][3]);
//		System.out.println(reqBoad[3][4]);
//		System.out.println(reqBoad[4][4]);
		System.out.println("------------POST------------");

		model.addAttribute("othelloBoad", reqBoad);
		model.addAttribute("othelloBoad[y][x]", reqBoad[y][x]);
		model.addAttribute("title", "オセロ");
		return "index";
	}

	@ModelAttribute("reqForm")
	public OthelloForm setRequestForm(OthelloForm othelloForm) {
		return othelloForm;
	}

}
