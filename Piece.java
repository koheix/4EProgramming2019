package shogi;

import java.util.HashMap;
import java.util.Map;

public abstract class Piece {
    int directnum = 0;//すすめるマスの個数
    Map<String, Boolean>direction = new HashMap<String, Boolean>() {//すすめるところを書く
        {
            put("fd", false);
            put("bk", false);
            put("right" , false);
            put("left" , false);
            put("rightfd" , false);
            put("rightfd" , false);
            put("leftfd" , false);
            put("rightbk" , false);
            put("rightbk" , false);
        }
    };
    abstract public boolean movable(int x , int y , int player);//コマがクリックされた時に動けるところを判定
    abstract public void move();//選択されたコマが動く処理
    abstract public boolean isKing();//そのコマは王なのか
}

class Lion extends Piece{

    public boolean movable(int x , int y , int player){//正の向きのプレイヤーは1 , 負の向きは2
        int pieceX = (x - 285)/130; //field配列でのコマの位置
        int pieceY = (y - 10)/130;
        if(player == 1){
            if(((pieceY - 1) >= 0) && (Main.field[pieceY - 1][pieceX] <= 0)){//前はすすめる
                direction.put("fd", true);
                directnum++;
            }
        }
        if(directnum >0) return true;//すすめるマスが一つでもあればtrueを返す
        else return false;
    }
    public void move(String direction , int player){
        if(player == 1){//正の向き

        }
    }
    public boolean isKing(){
        return true;
    }
}