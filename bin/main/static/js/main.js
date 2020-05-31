//for (let j = 0; j < len; j++) {
//  for (let i = 0; i < len2; i++) {
//    boardArray2dim[j][i].addEventListener("click", putStone);
//  }
//}
////この時のputStoneで、
////１．クリックしたｘ、ｙというパラメータ（引数）に、実際の０～７の値を入れる
////２．formをsubmit（サーバーの関数実行）する
////
////ようなイメージ

// .squareセレクタの要素を取得
let squareList = document.querySelectorAll(".square");

// x,yのinputタグ取得
let xInput = document.getElementsByName("x");
let yInput = document.getElementsByName("y");

// ターンチェンジのinputタグ取得
let turnInput = document.getElementsByName("strTurn");

// squareCollectionを一次元配列へ変換
let boardArray = Array.from(squareList);

// 一次元配列から要素を８つずつ分割して二次元配列に変換
let boardArray2dim = divideArrIntoPieces(boardArray, 8);

let scoreArray = []; // スコア用配列

var beforeClickID = ""; // 直前にクリックしたマス目要素のID格納用

// 黒石を置く関数をクリックイベント発火で全マス目に配置
const len = boardArray2dim.length;
const len2 = boardArray2dim[0].length;
for (let j = 0; j < len; j++) {
	for (let i = 0; i < len2; i++) {
		boardArray2dim[j][i].addEventListener("click", putStone);
	}
}

// パスボタン
const passButton = document.getElementById("pass");
passButton.addEventListener("click", function() {
	turnChange(myStone);
});

// 石を置く関数
function putStone() {

	// ID名を取得
	let attrID = this.getAttribute("id");

	// ID名を文字列分割
	let idArray = attrID.split("");

	// クリックした座標をinput valueへ代入する
	yInput[0].value = idArray[0];
	xInput[0].value = idArray[1];

	console.log(beforeClickID);
	if (beforeClickID.length) {
		document.getElementById(beforeClickID).style.backgroundColor = "#0ea14b";
	}

	// クリックしたマス目の色変更
	this.style.background = "pink";

	// クリックした要素をグローバル変数へ格納
	beforeClickID = attrID;
}

// form要素を取得
const formElement = document.querySelector("form");

// 送信イベントを監視する
formElement.addEventListener("submit", handleSubmit);

// 送信イベント発生時
function handleSubmit(event) {

	// フォーム送信前の確認メッセージ
	if (document.activeElement === this[3]) {
		// confirmでユーザーへ確認
		const isYes = confirm("石を置いてもよろしいですか？")

		// 「いいえ」を選択した場合
		if (isYes === false) {
			// 送信イベントをキャンセル
			alert("キャンセルされました");
			event.preventDefault();
		} else if (yInput[0].value === "0" && xInput[0].value === "0") {
			// 送信イベントをキャンセル
			alert("石を置く場所をクリックして下さい！");
			event.preventDefault();
		}
	} else if (document.activeElement === this[4]) {
		// confirmでユーザーへ確認
		const isYes = confirm("リセットしてもよろしいですか？")

		// 「いいえ」を選択した場合
		if (isYes === false) {
			// 送信イベントをキャンセル
			alert("キャンセルされました");
			event.preventDefault();
		}
	} else if (document.activeElement === this[0]) {
		// confirmでユーザーへ確認
		const isYes = confirm("パスしてもよろしいですか？")

		// 「いいえ」を選択した場合
		if (isYes === false) {
			// 送信イベントをキャンセル
			alert("キャンセルされました");
			event.preventDefault();
		}
	}
}

/**
 * 配列 arr を n 個ずつに分けて返す
 */
function divideArrIntoPieces(arr, n) {
	var arrList = [];
	while (0 < arr.length) {
		arrList.push(arr.splice(0, n));
	}
	return arrList;
}