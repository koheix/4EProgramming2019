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
import java.util.*;

public class Move{
  public static ImageView animalToImage(int x,int y){
  switch(Main.animal[y][x]){
    case "l1":
      return Main.lion1;
    case "l2":
      return Main.lion2;
    case "e1":
      return Main.elephant1;
    case "e2":
      return Main.elephant2;
    case "g1":
      return Main.giraffe1;
    case "g2":
      return Main.giraffe2;
    case "c1":
      return Main.chick1;
    case "c2":
      return Main.chick2;
    case "ch1":
      return Main.chicken1;
    case "ch2":
      return Main.chicken2;
    default:
      return null;
  }
}

public static ImageView animalnameToImage(String animalname){
  switch(animalname){
    case "l1":
      return Main.lion1;
    case "l2":
      return Main.lion2;
    case "e1":
      return Main.elephant1;
    case "e2":
      return Main.elephant2;
    case "g1":
      return Main.giraffe1;
    case "g2":
      return Main.giraffe2;
    case "c1":
      return Main.chick1;
    case "c2":
      return Main.chick2;
    case "ch1":
      return Main.chicken1;
    case "ch2":
      return Main.chicken2;
    default:
      return null;
  }
}

  public void movemove(int prePieceX, int prePieceY, int pieceX, int pieceY){//盤面の駒の移動
    if(Main.field[pieceY][pieceX]!=0){//画像を持ち駒の位置に移動
      if(Turn.myTurn(1)){
        if(Main.field[pieceY][pieceX]==-5){
          if(Main.animal[pieceY][pieceX]=="ch1"){
            Main.mypiece.addPiece(-4,"c1");
            Main.resetChicken(Main.chicken1,Main.chick1);
            Main.animal[pieceY][pieceX]="c1";
          }else{
            Main.mypiece.addPiece(-4,"c2");
            Main.resetChicken(Main.chicken2,Main.chick2);
            Main.animal[pieceY][pieceX]="c2";
          }
        }else{
          Main.mypiece.addPiece(Main.field[pieceY][pieceX],Main.animal[pieceY][pieceX]);
        }
        System.out.println("移動先："+Main.mypiece.returnX()+","+Main.mypiece.returnY());
        animalToImage(pieceX,pieceY).setX(Main.mypiece.returnX());
        animalToImage(pieceX,pieceY).setY(Main.mypiece.returnY());
      }else{
        if(Main.field[pieceY][pieceX]==5){
          if(Main.animal[pieceY][pieceX]=="ch1"){
          System.out.println("ok");
            Main.yourpiece.addPiece(4,"c1");
            Main.resetChicken(Main.chicken1,Main.chick1);
            Main.animal[pieceY][pieceX]="c1";
          }else{
            Main.yourpiece.addPiece(4,"c2");
            Main.resetChicken(Main.chicken2,Main.chick2);
            Main.animal[pieceY][pieceX]="c2";
          }
          }else{
            Main.yourpiece.addPiece(Main.field[pieceY][pieceX],Main.animal[pieceY][pieceX]);
          }
        System.out.println("移動先："+Main.yourpiece.returnX()+","+Main.yourpiece.returnY());
        animalToImage(pieceX,pieceY).setX(Main.yourpiece.returnX());
        animalToImage(pieceX,pieceY).setY(Main.yourpiece.returnY());
      }
      animalToImage(pieceX,pieceY).setFitHeight(80);animalToImage(pieceX,pieceY).setFitWidth(80);
      animalToImage(pieceX,pieceY).setRotate(0);
      }


      animalToImage(prePieceX,prePieceY).setX(286+130*pieceX);
      animalToImage(prePieceX,prePieceY).setY(11+130*pieceY);

      Main.field[pieceY][pieceX]=Main.field[prePieceY][prePieceX];
      Main.field[prePieceY][prePieceX]=0;
      Main.animal[pieceY][pieceX]=Main.animal[prePieceY][prePieceX];
      Main.animal[prePieceY][prePieceX]=null;
    }

    public static void doRemove(double previousX,double previousY,double mouseX, double mouseY){//持ち駒を盤面に追加
      int pieceX, pieceY;
      if(mouseX < 285) pieceX = -1222; //field配列でのコマの位置
      else pieceX = (int)(mouseX - 285)/130;
      if(mouseY < 10) pieceY = -560;
      else pieceY = (int)(mouseY - 10)/130;
      if(Turn.myTurn(1)){
        Main.mypiece.removePiece(previousX,previousY);
      }else if(Turn.myTurn(-1)){
        Main.yourpiece.removePiece(previousX,previousY);
      }
      System.out.println("追加先："+(286+130*pieceX)+","+(11+130*pieceY));
      animalnameToImage(MyPiece.preimage).setX(286+130*pieceX);
      animalnameToImage(MyPiece.preimage).setY(11+130*pieceY);
      animalnameToImage(MyPiece.preimage).setFitHeight(128);
      animalnameToImage(MyPiece.preimage).setFitWidth(128);
      if(MyPiece.preimage == "c1" || MyPiece.preimage == "c2"){//ひよこが盤面に置かれたら
        Main.putchick = true;//直前にひよっこが置かれた
      }
      if(MyPiece.preanimal<0){
        animalnameToImage(MyPiece.preimage).setRotate(180);
      }
      Main.field[pieceY][pieceX] = MyPiece.preanimal;
      Main.animal[pieceY][pieceX] = MyPiece.preimage;
      Move.moveMyPiece();
      Turn.turnChange();
      Main.root.play();
    }

    public static void moveMyPiece(){//持ち駒を左上に詰める処理
      if(Turn.myTurn(1)){
        for(int i=0;i<Main.mypiece.getPieceNum();i++){
        System.out.println(Main.mypiece.eleToX(i));
        animalnameToImage(Main.mypiece.myimage.get(i)).setX(Main.mypiece.eleToX(i));
        animalnameToImage(Main.mypiece.myimage.get(i)).setY(Main.mypiece.eleToY(i));
      }
      }else if(Turn.myTurn(-1)){
        for(int i=0;i<Main.yourpiece.getPieceNum();i++){
          System.out.println("2:"+Main.yourpiece.eleToX(i));
          animalnameToImage(Main.yourpiece.myimage.get(i)).setX(Main.yourpiece.eleToX(i));
          animalnameToImage(Main.yourpiece.myimage.get(i)).setY(Main.yourpiece.eleToY(i));
        }
      }
    }
}
