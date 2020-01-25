import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.image.*;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class GUI{

    Piece lion1 = new Lion();
    Piece lion2 = new Lion();
    Piece elephant1 = new Elephant();
    Piece elephant2 = new Elephant();
    Piece giraffe1 = new Giraffe();
    Piece giraffe2 = new Giraffe();
    Piece chick1 = new Chick();
    Piece chick2 = new Chick();
    Piece chicken1 = new Chicken();
    Piece chicken2 = new Chicken();



  //クリックされた駒が進める位置を判定
  public void directionCheck(double mouseX, double mouseY){
    int pieceX, pieceY;
    if(mouseX < 285) pieceX = -1222; //field配列でのコマの位置
    else pieceX = (int)(mouseX - 285)/130;
    if(mouseY < 10) pieceY = -560;
    else pieceY = (int)(mouseY - 10)/130;
    if(!Piece.onBoard(pieceX,pieceY)){//クリックされた場所が盤上以外
      Piece.directionReset();
      return;
    }
    switch(Main.field[pieceY][pieceX]){
      case 1:
        lion1.movable((int)mouseX,(int)mouseY,1);
        break;
      case 2:
        elephant1.movable((int)mouseX,(int)mouseY,1);
        break;
      case 3:
        giraffe1.movable((int)mouseX,(int)mouseY,1);
        break;
      case 4:
        chick1.movable((int)mouseX,(int)mouseY,1);
        break;
      case 5:
        chicken1.movable((int)mouseX,(int)mouseY,1);
        break;
      case -1:
        lion2.movable((int)mouseX,(int)mouseY,-1);
        break;
      case -2:
        elephant2.movable((int)mouseX,(int)mouseY,-1);
        break;
      case -3:
        giraffe2.movable((int)mouseX,(int)mouseY,-1);
        break;
      case -4:
        chick2.movable((int)mouseX,(int)mouseY,-1);
        break;
      case -5:
        chicken2.movable((int)mouseX,(int)mouseY,-1);
        break;
      default:
        Piece.directionReset();
        break;
    }
  }

  //選択された持ち駒が盤面に追加可能かどうかの判定
  public static boolean removeCheck(double previousX,double previousY,double mouseX, double mouseY){
    int pieceX, pieceY;
    if(mouseX < 285) pieceX = -1222; //field配列でのコマの位置
    else pieceX = (int)(mouseX - 285)/130;
    if(mouseY < 10) pieceY = -560;
    else pieceY = (int)(mouseY - 10)/130;
    if(!Piece.onBoard(pieceX,pieceY)){//クリックされた場所が盤上以外
      Piece.directionReset();
      return false;
    }
    if(!MyPiece.onMyPiece(previousX,previousY))return false;
    if(Turn.myTurn(1)){
      if((Main.mypiece.isAnimal(previousX,previousY))&&(Main.field[pieceY][pieceX]==0)){
        return true;
      }
    }else if(Turn.myTurn(-1)){
      if((Main.yourpiece.isAnimal(previousX,previousY))&&(Main.field[pieceY][pieceX]==0)){
        return true;
      }
    }
    return false;

  }

  //クリックされた場所に移動可能かどうか判定
  public void isValid(double previousX,double previousY,double mouseX, double mouseY){//
    int prePieceX,prePieceY,pieceX,pieceY;
    if(previousX < 285) prePieceX = -1222;
    else prePieceX = (int)(previousX - 285)/130;
    if(previousY < 10) prePieceY = -560;
    else prePieceY = (int)(previousY - 10)/130;
    if(mouseX < 285) pieceX = -1222;
    else pieceX = (int)(mouseX - 285)/130;
    if(mouseY < 10) pieceY = -560;
    else pieceY = (int)(mouseY - 10)/130;
    System.out.println(previousX+","+previousY);//一回前にクリックした座標
    System.out.println(mouseX+","+mouseY);//最後にクリックした座標
    if(!Piece.onBoard(prePieceX,prePieceY) || !Piece.onBoard(pieceX,pieceY))return;//最後にクリックされた２回が盤面以外であれば強制送還
    System.out.println(Main.directionCopy[pieceY][pieceX]);
    if(Main.directionCopy[pieceY][pieceX]){//そこへ動く
      if(Turn.myTurn(1)){//プレイヤー1のターンがこの手でおわる//文字盤の変更
        Main.t1.setText("");
        Main.t2.setText("PLAYER2のターン");
      }
      else{
        Main.t1.setText("PLAYER1のターン");
        Main.t2.setText("");
      }
      if(Math.abs(Main.field[pieceY][pieceX]) == Main.king[1-(Turn.turnPlayer() - 1)]){//もし王が取られたらゲームオーバー
        System.out.println("プレイヤー"+Turn.turnPlayer()+"の勝ちです。");
        if(Turn.turnPlayer() == 1){//プレイヤー1の勝ち
          Main.t1.setUnderline(true);
          Main.t1.setText("あなたの勝利！");
          Main.t2.setText("あなたの負け...");
        }
        else{//プレイヤー2の勝ち
          Main.t2.setText("あなたの勝利！");
          Main.t2.setUnderline(true);
          Main.t1.setText("あなたの負け...");
        }
        Turn.gameOver();
      }
      Move m = new Move();
      m.movemove(prePieceX,prePieceY,pieceX,pieceY);
      Piece.directionReset();
      Turn.turnChange();//turnをchange & ASKA
    }
}

//クリックされた場所が動かせる駒ならTrueを返す
public boolean selected(double mouseX,double mouseY){
  int pieceX,pieceY;
  if(mouseX < 285) pieceX = -1222;
    else pieceX = (int)(mouseX - 285)/130;
    if(mouseY < 10) pieceY = -560;
    else pieceY = (int)(mouseY - 10)/130;
    if(Piece.onBoard(pieceX,pieceY)){//選択された場所が盤面上
          if(Main.field[pieceY][pieceX]*Turn.turn <= 0) return false;//自分の駒じゃない、もしくは空白をクリックしたときはfalse
          System.out.println("黄色:"+pieceY+","+pieceX);
          Main.yellowback.setX(mouseX-(mouseX-285)%130);//黄色の枠の位置を設定
          Main.yellowback.setY(mouseY-(mouseY-10)%130);
          return true;
        }else{
          return false;
        }
}
}
