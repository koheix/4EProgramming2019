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
/*    Piece chick1 = new Chick();
    Piece chick2 = new Chick();
    Piece chicken1 = new Chicken();
    Piece chicken2 = new Chicken();*/




  public void directionCheck(double mouseX, double mouseY){
    int pieceX = (int)(mouseX - 285)/130; //field配列でのコマの位置
    int pieceY = (int)(mouseY - 10)/130;
    switch(Main.field[pieceY][pieceX]){
      case 1:
        lion1.movable((int)mouseX,(int)mouseY,1);
        break;
      case 2:
        elephant1.movable((int)mouseX,(int)mouseY,1);
        break;
      case 3:
        System.out.println("kokomade");
        giraffe1.movable((int)mouseX,(int)mouseY,1);
        break;
/*      case 4:
        chick1.movable((int)mouseX,(int)mouseY,1);
        break;
      case 5:
        chicken1.movable((int)mouseX,(int)mouseY,1);
        break;*/
      case -1:
        lion2.movable((int)mouseX,(int)mouseY,-1);
        break;
      case -2:
        elephant2.movable((int)mouseX,(int)mouseY,-1);
        break;
      case -3:
        giraffe2.movable((int)mouseX,(int)mouseY,-1);
        break;
/*      case -4:
        chick2.movable((int)mouseX,(int)mouseY,-1);
        break;
      case -5:
        chicken2.movable((int)mouseX,(int)mouseY,-1);
        break;*/
      default:
        break;
    }
  }
}
