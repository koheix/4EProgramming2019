package shogi;

import java.util.HashMap;
import java.util.Map;

public class Piece {
    int directnum = 0;//すすめるマスの個数
    static boolean[][] direction = {{false , false , false},{false , false , false},{false , false , false}, {false, false , false}};//進めるところをtrueにする

    public boolean movable(int x , int y){//正の向きのプレイヤーは1 , 負の向きは2
        for(int i = 0; i < 4;i++){
          for(int j = 0;j < 3;j++){
            direction[i][j] = false;//リセット
          }
        }
        int pieceX = (x - 285)/130; //field配列でのコマの位置
        int pieceY = (y - 10)/130;
        setMovable(pieceX , pieceY);
        if(directnum >0) return true;//すすめるマスが一つでもあればtrueを返す
        else return false;
    }
    public void move(String direction){

    }//選択されたコマが動く処理
    public boolean isKing(){
        return false;
    }//そのコマは王なのか
    public boolean onBoard(int nextx , int nexty){//0 <= nextx <= 2 , 0 <= nexty <= 3
      if((0 <= nextx) && (nextx <= 2) && (0 <= nexty) && (nexty <= 3)) return true;
      else return false;
    }
    public void setMovable(int pieceX , int pieceY){
      directnum = 0;
      if((onBoard(pieceX , pieceY - 1)) && (Main.field[pieceY - 1][pieceX] <= 0)){//前はすすめる
          direction[pieceY - 1][pieceX] = true;
          directnum++;
      }

      if((onBoard(pieceX + 1 , pieceY - 1)) && (Main.field[pieceY - 1][pieceX + 1] <= 0)){//rightfdは進める
          direction[pieceY - 1][pieceX + 1] = true;
          directnum++;
      }

      if((onBoard(pieceX + 1 , pieceY)) && (Main.field[pieceY][pieceX + 1] <= 0)){//rightは進める
        direction[pieceY][pieceX + 1] = true;
        directnum++;
      }

      if((onBoard(pieceX + 1 , pieceY + 1)) && (Main.field[pieceY + 1][pieceX + 1] <= 0)){//rightbkは進める
        direction[pieceY + 1][pieceX + 1] = true;
        directnum++;
      }

      if((onBoard(pieceX , pieceY + 1)) && (Main.field[pieceY + 1][pieceX] <= 0)){//bkは進める
        direction[pieceY + 1][pieceX] = true;
        directnum++;
      }

      if((onBoard(pieceX - 1 , pieceY + 1)) && (Main.field[pieceY + 1][pieceX - 1] <= 0)){//leftbkは進める
        direction[pieceY + 1][pieceX - 1] = true;
        directnum++;
      }

      if((onBoard(pieceX - 1 , pieceY)) && (Main.field[pieceY][pieceX - 1] <= 0)){//leftは進める
        direction[pieceY][pieceX - 1] = true;
        directnum++;
      }

      if((onBoard(pieceX - 1 , pieceY - 1)) && (Main.field[pieceY - 1][pieceX - 1] <= 0)){//leftfdは進める
        direction[pieceY - 1][pieceX - 1] = true;
        directnum++;
      }

    }
}

class Lion extends Piece{
    @Override
    public boolean isKing(){
        return true;
    }
}

class Giraffe extends Piece{
  @Override
  public void setMovable(int pieceX , int pieceY){
    super.setMovable(pieceX, pieceY);
    direction[pieceY - 1][pieceX + 1] = false;
    direction[pieceY + 1][pieceX + 1] = false;
    direction[pieceY + 1][pieceX - 1] = false;
    direction[pieceY - 1][pieceX - 1] = false;
  }
}
