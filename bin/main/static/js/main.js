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

let myStone = "blackStone"; // 自分の石(初期値：黒石)
let rivalStone = "whiteStone"; // 敵の石(初期値：白石)

let scoreArray = []; // スコア用配列

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

// 白と黒のターン切替
function turnChange(stoneColor) {
	let turnPlate = document.getElementById("turnPlate");
	if (stoneColor === "blackStone") {
		myStone = "whiteStone";
		rivalStone = "blackStone";
		turnPlate.innerHTML = "白の番";
		turnPlate.style.backgroundColor = "black";
		turnPlate.style.color = "white";
	} else {
		myStone = "blackStone";
		rivalStone = "whiteStone";
		turnPlate.innerHTML = "黒の番";
		turnPlate.style.backgroundColor = "white";
		turnPlate.style.color = "black";
	}
	// 切り替わったmyStoneをvalueにセット
	turnInput[0].value = myStone;
}

// 石を置く関数
function putStone() {

	// ID名を取得
	let attrID = this.getAttribute("id");

	// ID名を文字列分割
	let idArray = attrID.split("");

	// クリックした座標をinput valueへ代入する
	yInput[0].value = idArray[0];
	xInput[0].value = idArray[1];
	
	// ターンチェンジ関数呼び出し
	turnChange(myStone);

}

// 送信前チェック


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