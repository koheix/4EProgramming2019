package shogi;

import java.util.HashMap;
import java.util.Map;

public class Piece {
    int directnum = 0;//すすめるマスの個数
    Map<String, Boolean>direction = new HashMap<String, Boolean>() {//すすめるところを書く
        {
            //どうぶつの向きからみてその方向が行けるかどうかを格納してある。
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
    public boolean movable(int x , int y , int player){//正の向きのプレイヤーは1 , 負の向きは2
        int pieceX = (x - 285)/130; //field配列でのコマの位置
        int pieceY = (y - 10)/130;
        setMovable(pieceX , pieceY , player);
        if(directnum >0) return true;//すすめるマスが一つでもあればtrueを返す
        else return false;
    }
    public void move(String direction , int player){
      if(player == 1){//正の向き

      }
    };//選択されたコマが動く処理
    public boolean isKing(){};//そのコマは王なのか
    public boolean onBoard(int nextx , int nexty){
      if((0 <= nextx) && (nextx <= 2) && (0 <= nexty) && (nexty <= 3)) return true;
      else return false;
    }
    public void setMovable(int pieceX , int pieceY , int player){
      directnum = 0;
      if((onBoard(pieceX , pieceY - 1)) && (Main.field[pieceY - 1][pieceX] <= 0)){//前はすすめる
          if(player == 1) direction.put("fd", true);
          else direction.put("bk" , true);
          directnum++;
      }
      else{
        if(player == 1) direction.put("fd", false);
        else direction.put("bk" , false);
      }
      if((onBoard(pieceX + 1 , pieceY - 1)) && (Main.field[pieceY - 1][pieceX + 1] <= 0)){//rightfdは進める
        if(player == 1) direction.put("rightfd" , true);
        else direction.put("leftbk" , true);
        directnum++;
      }
      else{
        if(player == 1) direction.put("rightfd" , false);
        else direction.put("leftbk" , false);
      }
      if((onBoard(pieceX + 1 , pieceY)) && (Main.field[pieceY][pieceX + 1] <= 0)){//rightは進める
        if(player == 1) direction.put("right" , true);
        else direction.put("left" , true);
        directnum++;
      }
      else{
        if(player == 1) direction.put("right" , false);
        else direction.put("left" , false);
      }
      if((onBoard(pieceX + 1 , pieceY + 1)) && (Main.field[pieceY + 1][pieceX + 1] <= 0)){//rightbkは進める
        if(player == 1) direction.put("rightbk" , true);
        else direction.put("leftfd" , true);
        directnum++;
      }
      else{
        if(player == 1) direction.put("rightbk" , false);
        else direction.put("leftfd" , false);
      }
      if((onBoard(pieceX , pieceY + 1)) && (Main.field[pieceY + 1][pieceX] <= 0)){//bkは進める
        if(player == 1) direction.put("bk" , true);
        else direction.put("fd" , true);
        directnum++;
      }
      else{
        if(player == 1) direction.put("bk" , false);
        else direction.put("fd" , false);
      }
      if((onBoard(pieceX - 1 , pieceY + 1)) && (Main.field[pieceY + 1][pieceX - 1] <= 0)){//leftbkは進める
        if(player == 1) direction.put("leftbk" , true);
        else direction.put("rightfd" , true);
        directnum++;
      }
      else{
        if(player == 1) direction.put("leftbk" , false);
        else direction.put("rightfd" , false);
      }
      if((onBoard(pieceX - 1 , pieceY)) && (Main.field[pieceY][pieceX - 1] <= 0)){//leftは進める
        if(player == 1) direction.put("left" , true);
        else direction.put("right" , true);
        directnum++;
      }
      else{
        if(player == 1) direction.put("left" , false);
        else direction.put("right" , false);
      }
      if((onBoard(pieceX - 1 , pieceY - 1)) && (Main.field[pieceY - 1][pieceX - 1] <= 0)){//leftfdは進める
        if(player == 1) direction.put("leftfd" , true);
        else direction.put("rightbk" , true);
        directnum++;
      }
      else{
        if(player == 1) direction.put("leftfd" , false);
        else direction.put("rightbk" , false);
      }
    }
}

class Lion extends Piece{
    @Override
    public boolean isKing(){
        return true;
    }
}
