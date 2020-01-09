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

public class GUI extends Application{

    Lion lion1 = new Lion();
    Lion lion2 = new Lion();
    Elephant elephant1 = new Elephant();
    Elephant elephant2 = new Elephant();
    Giraffe giraffe1 = new Giraffe();
    Giraffe giraffe2 = new Giraffe();
    Chick chick1 = new Chick();
    Chick chick2 = new Chick();
    Chicken chicken1 = new Chicken();
    Chicken chicken2 = new Chicken();
    ImageView redBack = new ImageView("images/redBack.png");


  public void directionCheck(int mouseX, int mouseY, Group root){
    int pieceX = (mouseX - 285)/130; //field配列でのコマの位置
    int pieceY = (mouseY - 10)/130;
    switch(Main.field[pieceY][pieceX]){
      case 1:
        lion1.movable(mouseX,mouseY);
        break;
      case 2:
        elephant1.movable(mouseX,mouseY);
        break;
      case 3:
        giraffe1.movable(mouseX,mouseY);
        break;
      case 4:
        chick1.movable(mouseX,mouseY);
        break;
      case 5:
        chicken1.movable(mouseX,mouseY);
        break;
      case -1:
        lion2.movable(mouseX,mouseY);
        break;
      case -2:
        elephant2.movable(mouseX,mouseY);
        break;
      case -3:
        giraffe2.movable(mouseX,mouseY);
        break;
      case -4:
        chick2.movable(mouseX,mouseY);
        break;
      case -5:
        chicken2.movable(mouseX,mouseY);
        break;
      default:
        break;
    }
    for(int i=0;i<4;i++){
      for(int j=0;j<3;j++){
        if(Piece.direction[i][j]==true){
          redBack.setFitHeight(128);redBack.setFitWidth(128);
          redBack.setX(286+130*i);redBack.setY(11+130*j);
          root.getChildren().add(redBack);
        }
      }
    }
  }
}
