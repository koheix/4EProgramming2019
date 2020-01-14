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

public class Move{
  private ImageView animalToImage(int x,int y){
  switch(Main.animal[y][x]){
    case "l1":
      System.out.println("hellobbbbbbbb");
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

  public void movemove(int prePieceX, int prePieceY, int pieceX, int pieceY){

      System.out.println(pieceX+","+pieceY+","+Main.field[pieceY][pieceX]+"kokode?");

    if(Main.field[pieceY][pieceX]!=0){
      if(Turn.turn==1){
        animalToImage(pieceX,pieceY).setX(0);
        animalToImage(pieceX,pieceY).setY(0);
      }else{
        animalToImage(pieceX,pieceY).setX(800);
        animalToImage(pieceX,pieceY).setY(0);
      }
    }

      animalToImage(prePieceX,prePieceY).setX(286+130*pieceX);
      animalToImage(prePieceX,prePieceY).setY(11+130*pieceY);

      Main.field[pieceY][pieceX]=Main.field[prePieceY][prePieceX];
      Main.field[prePieceY][prePieceX]=0;
      Main.animal[pieceY][pieceX]=Main.animal[prePieceY][prePieceX];
      Main.animal[prePieceY][prePieceX]=null;
      //Main.player=(-1)*Main.player;

    }
}
