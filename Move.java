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
  public void moveTo(double previousX,double previousY,double mouseX, double mouseY){
    int prePieceX = (int)(previousX - 285)/130; //field配列でのコマの位置
    int prePieceY = (int)(previousY - 10)/130;
    int pieceX = (int)(mouseX - 285)/130; //field配列でのコマの位置
    int pieceY = (int)(mouseY - 10)/130;
    System.out.println(previousX+","+previousY);
    System.out.println(mouseX+","+mouseY);
    if(!Piece.onBoard(prePieceX,prePieceY) || !Piece.onBoard(pieceX,pieceY))return;
    System.out.println(Main.directionCopy[pieceY][pieceX]);
    if(Main.directionCopy[pieceY][pieceX]==true){
      switch(Main.field[prePieceY][prePieceX]){
        case 1:
          Main.lion1.setX(286+130*pieceY);Main.lion1.setY(11+130*pieceX);
          Main.field[prePieceY][prePieceX]=0;
          Main.field[pieceY][pieceX]=1;
          break;
        default:
          break;
      }
      Piece.directionReset();
    }
}
}
