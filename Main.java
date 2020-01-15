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
import javafx.scene.text.*;
/* lion is 1 , elephant is 2 , giraffe is 3 , chick is 4 , chiken is 5 null is 0*/

public class Main extends Application {
    private final int width = 960;
    private final int height = 540;//ウインドウの大きさ

    static int[][] field = {{0 , 0 , 0},{0 , 0 , 0} , {0 , 0 , 0} , {0 , 0 , 0}};//盤面//最初はコマがnull//逆のコマは負の数がつく
    static int[] king = {1 ,1};//王は普通ライオン1(この変数を変えれば王は変えられる)
    static String[][] animal = {{null,null,null},{null,null,null},{null,null,null},{null,null,null}};
    //static String[][] animalp1 = {{null,null,null},{null,null,null},{null,null,null},{null,null,null}};
    //static String[][] animalp2 = {{null,null,null},{null,null,null},{null,null,null},{null,null,null}};


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
    Group root = new Group();
    ImageView[][] redBack = new ImageView[4][3];  //赤い背景
    boolean PieceClicked = false;  //クリックされた
    int first = 0;
    double previousX = 0,previousY = 0;
    static boolean[][] directionCopy = new boolean[4][3];

    static MyPiece mypiece = new MyPiece();
    static MyPiece yourpiece = new MyPiece();

    static ImageView lion1 = new ImageView("images/lion.png");
    static ImageView lion2 = new ImageView("images/lion.png");
    static ImageView elephant1 = new ImageView("images/zou.png");
    static ImageView elephant2 = new ImageView("images/zou.png");
    static ImageView giraffe1 = new ImageView("images/kirin.png");
    static ImageView giraffe2 = new ImageView("images/kirin.png");
    static ImageView chick1 = new ImageView("images/hiyoko.png");
    static ImageView chick2 = new ImageView("images/hiyoko.png");
    static ImageView chicken1 = new ImageView("images/niwatori.png");
    static ImageView chicken2 = new ImageView("images/niwatori.png");

    static Text t1 = new Text(10, 250 , "あなたのターン");
    static Text t2 = new Text(700, 250 , "あいてのターン");
    Button btn2 = new Button("タイトルへ");//ゲーム中の戻るボタン

    @Override
    public void start(final Stage stage) {
        for(int i=0;i<4;i++){
          for(int j=0;j<3;j++){
            redBack[i][j]=new ImageView("images/redBack.png");
          }
        }
        AnchorPane pane = new AnchorPane();
      //  Group rootT = new Group();
      //  Group root = new Group();

        //描画用キャンバスノードの作成
        Canvas cvs = new Canvas(width, height);//make a canvas
        root.getChildren().add(cvs);

        this.g = cvs.getGraphicsContext2D();

        Scene title = new Scene(pane, width, height, Color.WHITE);//make a title
        Scene scene = new Scene(root, width, height, Color.WHITE);//make a window its background color is white

        initTitle(stage,title,scene,pane);
        initialize(stage,title,root);

        root.getChildren().add(lion1);
        root.getChildren().add(lion2);
        root.getChildren().add(elephant1);
        root.getChildren().add(elephant2);
        root.getChildren().add(giraffe1);
        root.getChildren().add(giraffe2);
        root.getChildren().add(chick1);
        root.getChildren().add(chick2);

        stage.setTitle("どうぶつしょうぎ");
        stage.setScene(title);
        stage.show();
        final Text p1 = new Text(10, 350 , "プレイヤー1");
        p1.setFont(new Font(35));
        root.getChildren().add(p1);

        final Text p2 = new Text(700, 350 , "プレイヤー2");
        p2.setFont(new Font(35));
        root.getChildren().add(p2);

        t1.setFont(new Font(35));
        root.getChildren().add(t1);

        t2.setFont(new Font(35));
        root.getChildren().add(t2);

        btn2.setPrefSize(100,50);
        root.getChildren().add(btn2);

        scene.setOnMouseClicked(this::mouseClicked);//イベントハンドラ（画面がクリックされた時）

    }

    private void mouseClicked(MouseEvent e){//画面がクリックされた
        if(first==0){ //1回目クリックされたら実行
            first=1;
        }else{
          for(int i=0;i<4;i++){//赤枠を消去
            for(int j=0;j<3;j++){
              if(Piece.direction[i][j]==true){
                root.getChildren().remove(redBack[i][j]);
              }
              }
            }
        }
        for(int i=0;i<4;i++){
          for(int j=0;j<3;j++){
            directionCopy[i][j]=Piece.direction[i][j];
          }
        }
        GUI g = new GUI();
        g.directionCheck(e.getX(),e.getY());//選択された駒がどこに進めるかを表示
        g.isValid(previousX,previousY,e.getX(),e.getY());//駒が動かせるか判定→駒を動かす
        if(GUI.removeCheck(previousX,previousY,e.getX(),e.getY())){
          Move.doRemove(previousX,previousY,e.getX(),e.getY());
        }
        for(int i=0;i<3;i++){//ひよこ→にわとり
          if((field[0][i] == 4)&&(Turn.turn==-1)){
            root.getChildren().remove(Move.animalToImage(i,0));
            root.getChildren().add(chicken1);
            field[0][i] = 5;
            drawChara("chicken",0,i,1);
          }else if((field[3][i] == -4)&&(Turn.turn==1)){
            root.getChildren().remove(Move.animalToImage(i,3));
            root.getChildren().add(chicken2);
            field[3][i] = -5;
            drawChara("chicken",3,i,-1);
          }
        }

        previousX = e.getX();previousY = e.getY();
        for(int i=0;i<4;i++){
          for(int j=0;j<3;j++){
            if(Piece.direction[i][j]==true){
          //    redBack[i][j]=new ImageView("images/redBack.png");
              redBack[i][j].setFitHeight(128);redBack[i][j].setFitWidth(128);
              redBack[i][j].setX(286+130*j);redBack[i][j].setY(11+130*i);
              root.getChildren().add(redBack[i][j]);
            }
          }
        }
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
      for(int i=0;i<4;i++){//鶏が盤面にあればひよこに戻す
        for(int j=0;j<3;j++){
          if(animal[i][j]=="ch1"){
            root.getChildren().remove(chicken1);
            root.getChildren().add(chick1);
          }else if(animal[i][j]=="ch2"){
            root.getChildren().remove(chicken2);
            root.getChildren().add(chick2);
          }
        }
      }

        mypiece.removePiece();    //持ち駒のリセット
        yourpiece.removePiece();

        for(int i = 0;i < 4;i++){//fieldの初期化を行う
          for(int j = 0;j < 3;j++){
            field[i][j] = 0;
            animal[i][j] = null;
          }
        }
        Piece.directionReset();
        for(int i=0;i<4;i++){
          for(int j=0;j<3;j++){
              root.getChildren().remove(redBack[i][j]);
            }
          }

        Turn.resetGame();//ターンの初期化を行う
        t1.setText("あなたのターン");
        t2.setText("あいてのターン");
        t1.setUnderline(false);
        t2.setUnderline(false);
        drawChara("lion" , 3 , 1 , 1);
        drawChara("chick" , 2 , 1 , 1);
        drawChara("elephant" , 3 , 0 , 1);
        drawChara("giraffe", 3, 2 , 1);
        drawChara("lion" , 0 , 1 , -1);
        drawChara("chick" , 1 , 1 , -1);
        drawChara("elephant" , 0 , 2 , -1);
        drawChara("giraffe", 0, 0 , -1);

        btn2.setOnMouseClicked(event -> {
          setScene(stage,title);
          initialize(stage, title, root);
        });
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

    public void drawChara(String animalname , int x , int y ,int player) {//x ,yは盤面の配列の座標
        switch(animalname){//動物判定
          //画像を貼る処理
            case "lion":
              if(player==1){
                lion1.setFitHeight(128);lion1.setFitWidth(128);
                lion1.setX(286+130*y);lion1.setY(11+130*x);
                //root.getChildren().add(lion1);
                field[x][y] = 1;animal[x][y] = "l1";
              }else{
                lion2.setFitHeight(128);lion2.setFitWidth(128);
                lion2.setX(286+130*y);lion2.setY(11+130*x);
                lion2.setRotate(180);
                //root.getChildren().add(lion2);
                field[x][y] = -1;animal[x][y] = "l2";
              }
              break;
            case "elephant":
              if(player==1){
                elephant1.setFitHeight(128);elephant1.setFitWidth(128);
                elephant1.setX(286+130*y);elephant1.setY(11+130*x);
                //root.getChildren().add(elephant1);
                field[x][y] = 2;animal[x][y] = "e1";
              }else{
                elephant2.setFitHeight(128);elephant2.setFitWidth(128);
                elephant2.setX(286+130*y);elephant2.setY(11+130*x);
                elephant2.setRotate(180);
                //root.getChildren().add(elephant2);
                field[x][y] = -2;animal[x][y] = "e2";
              }
                break;
            case "giraffe":
              if(player==1){
                giraffe1.setFitHeight(128);giraffe1.setFitWidth(128);
                giraffe1.setX(286+130*y);giraffe1.setY(11+130*x);
                //root.getChildren().add(giraffe1);
                field[x][y] = 3;animal[x][y] = "g1";
              }else{
                giraffe2.setFitHeight(128);giraffe2.setFitWidth(128);
                giraffe2.setX(286+130*y);giraffe2.setY(11+130*x);
                giraffe2.setRotate(180);
                //root.getChildren().add(giraffe2);
                field[x][y] = -3;animal[x][y] = "g2";
              }
                break;
            case "chick":
              if(player==1){
                chick1.setFitHeight(128);chick1.setFitWidth(128);
                chick1.setX(286+130*y);chick1.setY(11+130*x);
                //root.getChildren().add(chick1);
                field[x][y] = 4;animal[x][y] = "c1";
              }else{
                chick2.setFitHeight(128);chick2.setFitWidth(128);
                chick2.setX(286+130*y);chick2.setY(11+130*x);
                chick2.setRotate(180);
                //root.getChildren().add(chick2);
                field[x][y] = -4;animal[x][y] = "c2";
              }
                break;
            case "chicken":
              if(player==1){
                chicken1.setFitHeight(128);chicken1.setFitWidth(128);
                chicken1.setX(286+130*y);chicken1.setY(11+130*x);
                //root.getChildren().add(chicken1);
                field[x][y] = 5;animal[x][y] = "ch1";
              }else{
                chicken2.setFitHeight(128);chicken2.setFitWidth(128);
                chicken2.setX(286+130*y);chicken2.setY(11+130*x);
                chicken2.setRotate(180);
                //root.getChildren().add(chicken2);
                field[x][y] = -5;animal[x][y] = "ch2";
              }
                break;
            default:
                break;
        }
    }

}
