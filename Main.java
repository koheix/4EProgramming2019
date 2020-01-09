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
/* lion is 1 , elephant is 2 , giraffe is 3 , chick is 4 , chiken is 5 null is 0*/

public class Main extends Application {
    private final int width = 960;
    private final int height = 540;//ウインドウの大きさ

    static int[][] field = {{0 , 0 , 0},{0 , 0 , 0} , {0 , 0 , 0} , {0 , 0 , 0}};//盤面//最初はコマがnull//逆のコマは負の数がつく


    static Map<String, Integer>animals = new HashMap<String, Integer>() {
        {
            put("lion", 1);
            put("elephant", 2);
            put("giraffe" , 3);
            put("chick" , 4);
            put("chicken" , 5);
        }
    };
    public static void main(final String... args) {
        launch(args);
    }

    GraphicsContext g;

    @Override
    public void start(final Stage stage) {
        AnchorPane pane = new AnchorPane();
      //  Group rootT = new Group();
        Group root = new Group();

        //描画用キャンバスノードの作成
        Canvas cvs = new Canvas(width, height);//make a canvas
        root.getChildren().add(cvs);

        this.g = cvs.getGraphicsContext2D();

        Scene title = new Scene(pane, width, height, Color.WHITE);//make a title
        Scene scene = new Scene(root, width, height, Color.WHITE);//make a window its background color is white

        initTitle(stage,title,scene,pane);
        initialize(stage,title,root);
        stage.setTitle("どうぶつしょうぎ");
        stage.setScene(title);
        stage.show();

        scene.setOnMouseClicked(this::mouseClicked);//イベントハンドラ（画面がクリックされた時）

    }

    private void mouseClicked(MouseEvent e){//画面がクリックされた
        GUI g = new GUI();
        g.directionCheck(e.getX(),e.getY(),root);
    }


    private void initTitle(Stage stage, Scene title, Scene scene, AnchorPane pane){
      ImageView titleImage = new ImageView("images/title.png");
      titleImage.setFitHeight(540);titleImage.setFitWidth(960);
      pane.getChildren().add(titleImage);
      Button btn = new Button("スタート");
      btn.setPrefSize(100,50);
      btn.setOnMouseClicked(event -> setScene(stage,scene));
      pane.getChildren().add(btn);
      pane.setLeftAnchor(btn,430.);
      pane.setTopAnchor(btn,240.);
      drawField();
    }

    private void initialize(Stage stage, Scene title, Group root){//はじめに実行
        drawChara("lion" , 3 , 1 , root , 1);
        drawChara("chick" , 2 , 1 , root , 1);
        drawChara("elephant" , 3 , 0 , root , 1);
        drawChara("giraffe", 3, 2 , root , 1);
        drawChara("lion" , 0 , 1 , root , -1);
        drawChara("chick" , 1 , 1 , root , -1);
        drawChara("elephant" , 0 , 2 , root , -1);
        drawChara("giraffe", 0, 0 , root , -1);

        Button btn2 = new Button("タイトルへ");
        btn2.setPrefSize(100,50);
        btn2.setOnMouseClicked(event -> setScene(stage,title));
        root.getChildren().add(btn2);
    }

    public static  void setScene(Stage stage,Scene changeScene) {
    stage.setScene(changeScene);
    stage.show();
  }

    private void drawField() {//描画のプログラム
        for(int i = 0;i < 4;i++){
            for(int j = 0;j < 3;j++){
                g.strokeRect(//盤面の左上は(285 , 10)
                    (width / 2) - ((height - 20) / 4)*3/2 + 130*j, 10 + 130*i,130, 130
                 );//盤面の描画//右上座標が100,20//盤面の描画
                g.strokeRect(//もち米の枠
                    10 , 370 , 240 , 160
                );//左
                g.strokeRect(
                    710 , 370 , 240 , 160
                );//右

            }
        }
    }

    public void drawChara(String animal , int x , int y ,Group root ,int player) {//x ,yは盤面の配列の座標
        switch(animal){//動物判定
          //画像を貼る処理
            case "lion":
              if(player==1){
                ImageView lion1 = new ImageView("images/lion.png");
                lion1.setFitHeight(128);lion1.setFitWidth(128);
                lion1.setX(286+130*y);lion1.setY(11+130*x);
                root.getChildren().add(lion1);
                field[x][y] = 1;
              }else{
                ImageView lion2 = new ImageView("images/lion.png");
                lion2.setFitHeight(128);lion2.setFitWidth(128);
                lion2.setX(286+130*y);lion2.setY(11+130*x);
                lion2.setRotate(180);
                root.getChildren().add(lion2);
                field[x][y] = -1;
              }
              break;
            case "elephant":
              if(player==1){
                ImageView elephant1 = new ImageView("images/zou.png");
                elephant1.setFitHeight(128);elephant1.setFitWidth(128);
                elephant1.setX(286+130*y);elephant1.setY(11+130*x);
                root.getChildren().add(elephant1);
                field[x][y] = 2;
              }else{
                ImageView elephant2 = new ImageView("images/zou.png");
                elephant2.setFitHeight(128);elephant2.setFitWidth(128);
                elephant2.setX(286+130*y);elephant2.setY(11+130*x);
                elephant2.setRotate(180);
                root.getChildren().add(elephant2);
                field[x][y] = -2;
              }
                break;
            case "giraffe":
              if(player==1){
                ImageView giraffe1 = new ImageView("images/kirin.png");
                giraffe1.setFitHeight(128);giraffe1.setFitWidth(128);
                giraffe1.setX(286+130*y);giraffe1.setY(11+130*x);
                root.getChildren().add(giraffe1);
                field[x][y] = 3;
              }else{
                ImageView giraffe2 = new ImageView("images/kirin.png");
                giraffe2.setFitHeight(128);giraffe2.setFitWidth(128);
                giraffe2.setX(286+130*y);giraffe2.setY(11+130*x);
                giraffe2.setRotate(180);
                root.getChildren().add(giraffe2);
                field[x][y] = -3;
              }
                break;
            case "chick":
              if(player==1){
                ImageView chick1 = new ImageView("images/hiyoko.png");
                chick1.setFitHeight(128);chick1.setFitWidth(128);
                chick1.setX(286+130*y);chick1.setY(11+130*x);
                root.getChildren().add(chick1);
                field[x][y] = 4;
              }else{
                ImageView chick2 = new ImageView("images/hiyoko.png");
                chick2.setFitHeight(128);chick2.setFitWidth(128);
                chick2.setX(286+130*y);chick2.setY(11+130*x);
                chick2.setRotate(180);
                root.getChildren().add(chick2);
                field[x][y] = -4;
              }
                break;
            case "chicken":
              if(player==1){
                ImageView chicken1 = new ImageView("images/niwatori.png");
                chicken1.setFitHeight(128);chicken1.setFitWidth(128);
                chicken1.setX(286+130*y);chicken1.setY(11+130*x);
                root.getChildren().add(chicken1);
                field[x][y] = 5;
              }else{
                ImageView chicken2 = new ImageView("images/niwatori.png");
                chicken2.setFitHeight(128);chicken2.setFitWidth(128);
                chicken2.setX(286+130*y);chicken2.setY(11+130*x);
                chicken2.setRotate(180);
                root.getChildren().add(chicken2);
                field[x][y] = -5;
              }
                break;
            default:
                break;
        }
    }

}
