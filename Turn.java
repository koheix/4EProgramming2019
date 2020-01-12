public class Turn{
    public static int turn = 1;
    static public void turnChange() {//ターンを変更する
        turn *= -1;
    }
    static public boolean myTurn(int player){//自分のターンであるかを返す
        if(player == turn) return true;
        else return false;
    }
    static public void gameOver(){//どちらかの王が取られたらゲームを終了させる
        turn = 1225;
    }
    static public int turnPlayer(){//今ターンの人の番号を返す
        if(turn == 1) return 1;
        else return 2;
    }
}