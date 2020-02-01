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
import java.util.*;
import javafx.scene.input.*;
import java.io.File;
import javafx.animation.*;
import javafx.util.Duration;
import javafx.scene.media.AudioClip;
/* lion is 1 , elephant is 2 , giraffe is 3 , chick is 4 , chiken is 5 null is 0*/

public class Main extends Application {
    private final int width = 960;//ウインドウの大きさ
    private final int height = 540;

    static int[][] field = {{0 , 0 , 0},{0 , 0 , 0} , {0 , 0 , 0} , {0 , 0 , 0}};//盤面//最初はコマがnull//逆のコマは負の数がつく
    static int[] king = {1 ,1};//王は普通ライオン1(この変数を変えれば王は変えられる)
    static String[][] animal = {{null,null,null},{null,null,null},{null,null,null},{null,null,null}};
    static int mode = 0;//ゲームモードが0は通常,1は王変更モード

    int mypiecesellectionflag = 0;//持ち駒が選択されているかどうかのフラグ
    int x, y;//持ち駒光らせる座標



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
    static View root = new View();//ゲーム画面のGroup
    Group root2 = new Group();//選択画面のGroup
    AnchorPane pane = new AnchorPane();//タイトル画面のpane

    boolean PieceClicked = false;  //クリックされた
    int first = 0;
    double previousX = 0,previousY = 0;
    static boolean[][] directionCopy = new boolean[4][3];

    static MyPiece mypiece = new MyPiece();//持ち駒クラスのインスタンス変数
    static MyPiece yourpiece = new MyPiece();

    static ImageView lion1 = new ImageView("images/lion.png");//コマの画像
    static ImageView lion2 = new ImageView("images/lion.png");
    static ImageView elephant1 = new ImageView("images/zou.png");
    static ImageView elephant2 = new ImageView("images/zou.png");
    static ImageView giraffe1 = new ImageView("images/kirin.png");
    static ImageView giraffe2 = new ImageView("images/kirin.png");
    static ImageView chick1 = new ImageView("images/hiyoko.png");
    static ImageView chick2 = new ImageView("images/hiyoko.png");
    static ImageView chicken1 = new ImageView("images/niwatori.png");
    static ImageView chicken2 = new ImageView("images/niwatori.png");
    static ImageView yellowback = new ImageView("images/yellowback.png");//黄色の囲み
    ImageView[][] fldtmpredback = new ImageView[4][3];//持ち駒洗濯時における場所を照らす用
    ImageView tmpredback = new ImageView("images/yellowback.png");//持ち駒を囲む用
    ImageView[][] redBack = new ImageView[4][3];  //赤い囲み
    ImageView backImage = new ImageView("images/background.png");//ゲーム画面の背景
    ImageView select1 = new ImageView("images/select1.png");  //王選択画面の背景画像
    ImageView select2 = new ImageView("images/select2.png");
    ImageView normal = new ImageView("images/normal.png");//タイトル画面のボタンの画像
    ImageView selectking = new ImageView("images/selectking.png");
    ImageView modoru = new ImageView("images/return.png");//戻るボタンの画像
    static ImageView win1 = new ImageView("images/win1.png");//勝った場合の画面
    static ImageView win2 = new ImageView("images/win2.png");

    Text p1 = new Text(55, 325 , "PLAYER1");
    Text p2 = new Text(755, 325 , "PLAYER2");
    //boolean putchick = false;
    int flag = 0;
    int select = 0;
    static int yellowDisplayed = 0;//黄色の枠が表示されていれば1,確定したら２


    AudioClip c = new AudioClip(new File("Music/BGM.wav").toURI().toString());
    AudioClip ct = new AudioClip(new File("Music/titlebgm.wav").toURI().toString());
    AudioClip cw = new AudioClip(new File("Music/win.wav").toURI().toString());

    Button btn2 = new Button();//ゲーム中の戻るボタン


    @Override
    public void start(final Stage stage) {
        for(int i=0;i<4;i++){//赤い枠の初期化
          for(int j=0;j<3;j++){
            redBack[i][j]=new ImageView("images/redBack.png");
            fldtmpredback[i][j] = new ImageView("images/redBack.png");
          }
        }


        //描画用キャンバスノードの作成
        Canvas cvs = new Canvas(width, height);//make a canvas
        root.getChildren().add(cvs);

        this.g = cvs.getGraphicsContext2D();

        Scene title = new Scene(pane, width, height, Color.WHITE);//make a title
        Scene scene = new Scene(root, width, height, Color.WHITE);//make a window its background color is white
        Scene selectScene = new Scene(root2 ,width ,height , Color.WHITE);

        backImage.setFitHeight(540);backImage.setFitWidth(960);//ゲーム画面の背景
        root.getChildren().add(backImage);

        initTitle(stage,title,scene,selectScene,pane);
        initialize(stage,title,root);

        root.getChildren().addAll(lion1,lion2,elephant1,elephant2,giraffe1,giraffe2,chick1,chick2);

        stage.setTitle("どうぶつしょうぎ");
        stage.setScene(title);
        stage.show();


        p1.setFont(Font.font("impact",35));
        p2.setFont(Font.font("impact",35));
        Text mp1 = new Text(80, 360 , "持ち駒");
        mp1.setFont(Font.font("impact",FontWeight.BLACK,30));
        Text mp2 = new Text(780, 360 , "持ち駒");
        mp2.setFont(Font.font("impact",FontWeight.BLACK,30));
        root.getChildren().addAll(p1,p2,mp1,mp2);


        modoru.setFitHeight(50);modoru.setFitWidth(100);//戻るボタンの設定
        btn2.setStyle("-fx-base:#ff9900");
        btn2.setGraphic(modoru);
        root.getChildren().add(btn2);

        select1.setFitHeight(540);select1.setFitWidth(960);
        select2.setFitHeight(540);select2.setFitWidth(960);
        tmpredback.setFitHeight(80);tmpredback.setFitWidth(80);//選択された持ち駒を光らせるやつの大きさ
        yellowback.setFitHeight(128);yellowback.setFitWidth(128);//選択された盤上の駒を光らせるやつの大きさ

        scene.setOnMouseClicked(this::mouseClicked);//イベントハンドラ（画面がクリックされた時）
        scene.setOnMouseMoved(this::mouseMoved);//mausu ga ugoitatoki
        selectScene.setOnKeyPressed((event)->{
          mode = 1;
          if(select==1){//王選択画面
            if((event.getCode()==KeyCode.UP)||(event.getCode()==KeyCode.DOWN)||(event.getCode()==KeyCode.RIGHT)||(event.getCode()==KeyCode.LEFT)){
            root2.getChildren().remove(select1);
            root2.getChildren().add(select2);
            select=2;
            }
            if(event.getCode()==KeyCode.UP){
              System.out.println("上1");
              king[0] = 4;
            }if(event.getCode()==KeyCode.DOWN){
              System.out.println("下1");
              king[0] = 1;
            }if(event.getCode()==KeyCode.RIGHT){
              System.out.println("右1");
              king[0] = 3;
            }if(event.getCode()==KeyCode.LEFT){
              System.out.println("左1");
              king[0] = 2;
            }
          }else if(select==2){
            if((event.getCode()==KeyCode.UP)||(event.getCode()==KeyCode.DOWN)||(event.getCode()==KeyCode.RIGHT)||(event.getCode()==KeyCode.LEFT)){
            root2.getChildren().remove(select2);
            select=0;
            setScene(stage,scene);
            root.play();
            ct.stop();//タイトルBGMの停止
            c.setCycleCount(AudioClip.INDEFINITE);//無限ループ
            c.play();//ゲーム音楽スタート
            }
            if(event.getCode()==KeyCode.UP){
              System.out.println("上2");
              king[1] = 4;
            }if(event.getCode()==KeyCode.DOWN){
              System.out.println("下2");
              king[1] = 1;
            }if(event.getCode()==KeyCode.RIGHT){
              System.out.println("右2");
              king[1] = 3;
            }if(event.getCode()==KeyCode.LEFT){
              System.out.println("左2");
              king[1] = 2;
            }
          }
        });
    }

    //マウスが移動した時の処理
    private void mouseMoved(MouseEvent e){
       GUI g = new GUI();
       //System.out.println("yellowDisplayed:"+yellowDisplayed);
        if(g.onMouse(e.getX(),e.getY())){//選択された場所が自分の駒であれば駒を黄色で囲む
          if(yellowDisplayed==0){
            root.getChildren().add(yellowback);
            yellowDisplayed=1;
          }
        }else{
          if((yellowDisplayed!=2)&&(yellowDisplayed!=3)){
            root.getChildren().remove(yellowback);
            yellowDisplayed = 0;
          }
        }

        if(g.onMyMouse(e.getX(),e.getY())){//選択された場所が自分の駒であれば駒を黄色で囲む
          if(yellowDisplayed==0){
            root.getChildren().add(yellowback);
            yellowDisplayed=1;
          }
        }else{
          if((yellowDisplayed!=2)&&(yellowDisplayed!=1)){
            root.getChildren().remove(yellowback);
            yellowDisplayed = 0;
          }
        }
    }

    private void mouseClicked(MouseEvent e){//画面がクリックされた
      if(mypiecesellectionflag == 1){//持ち駒の赤いやつを消す
        root.getChildren().remove(tmpredback);
        //yellowDisplayed = 0;
        for(int i = 0;i < 4;i++){//赤い枠の初期化
          for(int j = 0;j < 3;j++){
            try{
              root.getChildren().remove(fldtmpredback[i][j]);//追加されていれば消す
            }
            catch(Exception ewes){
              //
            }
          }
        }
        mypiecesellectionflag = 0;
      }

      if(Turn.myTurn(1)){//プレイヤー1のターンである
        if(mypiece.isAnimal(e.getX(), e.getY()) && MyPiece.onMyPiece(e.getX(), e.getY())){//持ち駒が押されたなら
          x = mypiece.eleToX(mypiece.MouseToElement(e.getX(), e.getY()));// 赤くする右上の座標
          y = mypiece.eleToY(mypiece.MouseToElement(e.getX(), e.getY()));// 赤くする右上の座標
          tmpredback.setX(x);
          tmpredback.setY(y);// redbackを選択された持ち駒の座標にセット
          Main.root.getChildren().add(tmpredback);
          yellowDisplayed = 3;
          System.out.println("gen");

          for(int i = 0;i < 4;i++){
            for(int j = 0;j < 3;j++){
              if(field[i][j] == 0){//何もおいてないとこなら
                fldtmpredback[i][j].setFitHeight(128);fldtmpredback[i][j].setFitWidth(128);
                fldtmpredback[i][j].setX(286+130*j);fldtmpredback[i][j].setY(11+130*i);
                root.getChildren().add(fldtmpredback[i][j]);
              }
            }
          }
          root.getChildren().remove(yellowback);
          mypiecesellectionflag = 1;//持ち駒のredbackが表示されている
        }
      }
      else if(Turn.myTurn(-1)){//プレイヤー2のターンである
        if(yourpiece.isAnimal(e.getX(), e.getY()) && MyPiece.onMyPiece(e.getX(), e.getY())){//持ち駒が押されたなら
          x = yourpiece.eleToX(yourpiece.MouseToElement(e.getX(), e.getY()));// 赤くする右上の座標
          y = yourpiece.eleToY(yourpiece.MouseToElement(e.getX(), e.getY()));// 赤くする右上の座標
          tmpredback.setX(x);
          tmpredback.setY(y);// redbackを選択された持ち駒の座標にセット
          Main.root.getChildren().add(tmpredback);
          yellowDisplayed = 3;

          for(int i = 0;i < 4;i++){
            for(int j = 0;j < 3;j++){
              if(field[i][j] == 0){//何もおいてないとこなら
                fldtmpredback[i][j].setFitHeight(128);fldtmpredback[i][j].setFitWidth(128);
                fldtmpredback[i][j].setX(286+130*j);fldtmpredback[i][j].setY(11+130*i);
                root.getChildren().add(fldtmpredback[i][j]);
              }
            }
          }

          mypiecesellectionflag = 1;//持ち駒のredbackが表示されている
          root.getChildren().remove(yellowback);
        }
      }

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
        g.directionCheck(e.getX(),e.getY());//選択された駒がどこに進めるかを判定し配列に格納
        if(g.selected(e.getX(),e.getY())){//選択された場所が自分の駒であれば駒を黄色で囲む
          if(yellowDisplayed==1){
            yellowDisplayed = 2;
          }else if(yellowDisplayed==3){
            yellowDisplayed = 0;
            g.onMouse(e.getX(),e.getY());
            yellowDisplayed=2;
          }
        }else if(MyPiece.onMyPiece(e.getX(),e.getY())){

        }else{
          if(yellowDisplayed==2){
            root.getChildren().remove(yellowback);
            yellowback.setFitWidth(128);yellowback.setFitHeight(128);
            yellowDisplayed = 0;
          }
        }

        g.isValid(previousX,previousY,e.getX(),e.getY());//駒が動かせるか判定→駒を動かす
        if(GUI.removeCheck(previousX,previousY,e.getX(),e.getY())){
          Move.doRemove(previousX,previousY,e.getX(),e.getY());
        }
        for(int i=0;i<3;i++){//ひよこ→にわとり
          if((field[0][i] == 4)&&(Turn.turn==-1)){
            /*if(putchick == true){
              putchick = false;
              continue;
            }*/
            if(animal[0][i]=="c1"){
              root.getChildren().add(chicken1);
              root.getChildren().remove(chick1);
              animal[0][i]="ch1";
            }else{
              root.getChildren().add(chicken2);
              root.getChildren().remove(chick2);
              animal[0][i]="ch2";
            }
            if(mode == 1){//もしゲームモードが王変更モードなら
              king[0] = 5;//王を5に変える
            }
            drawChara("chicken",0,i,1);
          }else if((field[3][i] == -4)&&(Turn.turn==1)){
            /*if(putchick == true){
              putchick = false;
              continue;
            }*/
            if(animal[3][i]=="c1"){
              root.getChildren().add(chicken1);
              root.getChildren().remove(chick1);
              animal[3][i]="ch1";
            }else{
              root.getChildren().add(chicken2);
              root.getChildren().remove(chick2);
              animal[3][i]="ch2";
            }
            //root.getChildren().remove(Move.animalToImage(i,3));
            if(mode == 1){//もしゲームモードが王変更モードなら
              king[1] = 5;//王を5に変える
            }
            drawChara("chicken",3,i,-1);
          }
        }

        previousX = e.getX();previousY = e.getY();
        for(int i=0;i<4;i++){
          for(int j=0;j<3;j++){
            if(Piece.direction[i][j]==true){
              redBack[i][j].setFitHeight(128);redBack[i][j].setFitWidth(128);
              redBack[i][j].setX(286+130*j);redBack[i][j].setY(11+130*i);
              root.getChildren().add(redBack[i][j]);
            }
          }
        }
      }

      public static void resetChicken(ImageView animalname1,ImageView animalname2){
        root.getChildren().remove(animalname1);
        root.getChildren().add(animalname2);
      }

    private void initTitle(Stage stage, Scene title, Scene scene,Scene selectScene, AnchorPane pane){
      ct.setCycleCount(AudioClip.INDEFINITE);
      ct.play();
      ImageView titleImage = new ImageView("images/title.png");
      ImageView randomking = new ImageView("images/random.png");
      titleImage.setFitHeight(540);titleImage.setFitWidth(960);
      pane.getChildren().add(titleImage);
      Button btn = new Button();
      Button btn_s1 = new Button();//王選択モードボタン
      normal.setFitHeight(50);normal.setFitWidth(210);//ノーマルボタンの設定
      btn.setStyle("-fx-base:#ff9900");
      btn.setGraphic(normal);
      selectking.setFitHeight(50);selectking.setFitWidth(210);
      randomking.setFitHeight(50);randomking.setFitWidth(210);
      btn_s1.setStyle("-fx-base:#ff9900");
      btn_s1.setGraphic(selectking);
      btn.setPrefSize(100,50);
      btn_s1.setPrefSize(100,50);
      btn.setOnMouseClicked(event -> {
        ct.stop();
        c.setCycleCount(AudioClip.INDEFINITE);
		    c.play();
        root.play();
        setScene(stage,scene);
        stage.setTitle("どうぶつしょうぎ-ノーマルモード");
      });
      btn_s1.setOnMouseClicked(event -> {
        if(event.isShiftDown()){//シフトとボタンが同時に押されていればランダムモードにする
          root.play();
          Random r = new Random();
          king[0] = 1+r.nextInt(4);
          king[1] = 1+r.nextInt(4);
          System.out.println(king[0]+","+king[1]);
          setScene(stage,scene);
          stage.setTitle("どうぶつしょうぎ-ランダムモード");
          ct.stop();
          c.play();
        }
        else{
          setScene(stage,selectScene);
          root2.getChildren().add(select1);
          select = 1;
          stage.setTitle("どうぶつしょうぎ-王選択モード");
        }
      });
      pane.getChildren().add(btn);
      pane.getChildren().add(btn_s1);
      pane.setLeftAnchor(btn,355.);
      pane.setTopAnchor(btn,235.);
      pane.setLeftAnchor(btn_s1,355.);
      pane.setTopAnchor(btn_s1,300.);
      //drawField();
    }


    private void initialize(Stage stage, Scene title, Group root){//はじめに実行
      //p1.setUnderline(true);
      //p2.setUnderline(false);
      mode = 0;
      for(int i=0;i<4;i++){//鶏が盤面にあればひよこに戻す
        for(int j=0;j<3;j++){
          if(animal[i][j]=="ch1"){
            root.getChildren().remove(chicken1);
            root.getChildren().add(chick1);
          }
          if(animal[i][j]=="ch2"){
            root.getChildren().remove(chicken2);
            root.getChildren().add(chick2);
          }
        }
      }
        //持ち駒のやつreset
          try{
            root.getChildren().remove(tmpredback);//追加されていれば消す
          }
          catch(Exception ewes){
            //
          }

        first = 0;
        mypiece.reset();    //持ち駒のリセット
        yourpiece.reset();

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
        /*t1.setText("PLAYER1のターン");
        t2.setText("");
        t1.setUnderline(false);
        t2.setUnderline(false);*/
        drawChara("lion" , 3 , 1 , 1);
        drawChara("chick" , 2 , 1 , 1);
        drawChara("elephant" , 3 , 0 , 1);
        drawChara("giraffe", 3, 2 , 1);
        drawChara("lion" , 0 , 1 , -1);
        drawChara("chick" , 1 , 1 , -1);
        drawChara("elephant" , 0 , 2 , -1);
        drawChara("giraffe", 0, 0 , -1);

        btn2.setOnMouseClicked(event -> {
          c.stop();
          deleteEndImage(Turn.turnPlayer());//"●●が勝ち！！！"を消す
          king[0] = 1;king[1] = 1;
          setScene(stage,title);
          initialize(stage, title, root);
          c.stop();
          ct.play();
          stage.setTitle("どうぶつしょうぎ");
        });
    }

    //勝ったプレイヤーの画像を表示
    public void endImage(int winPlayer){
      c.stop();
      cw.setVolume(0.2);
      cw.play();
      win1.setFitHeight(540);win1.setFitWidth(960);
      win2.setFitHeight(540);win2.setFitWidth(960);
      if(winPlayer==1){
        root.getChildren().add(win1);
      }else{
        root.getChildren().add(win2);
      }
    }

    //勝ったプレイヤーの画像を消す
    public void deleteEndImage(int winPlayer){
      System.out.println("現在のプレイヤーは："+winPlayer);
      root.getChildren().remove(win1);
      root.getChildren().remove(win2);
    }

    public static  void setScene(Stage stage,Scene changeScene) {
    stage.setScene(changeScene);
    stage.show();
  }

    public void drawChara(String animalname , int x , int y ,int player) {//x ,yは盤面の配列の座標
        switch(animalname){//動物判定
          //画像を貼る処理
            case "lion":
              if(player==1){
                lion1.setFitHeight(128);lion1.setFitWidth(128);
                lion1.setX(286+130*y);lion1.setY(11+130*x);
                field[x][y] = 1;animal[x][y] = "l1";
                lion1.setRotate(0);
              }else{
                lion2.setFitHeight(128);lion2.setFitWidth(128);
                lion2.setX(286+130*y);lion2.setY(11+130*x);
                lion2.setRotate(180);
                field[x][y] = -1;animal[x][y] = "l2";
              }
              break;
            case "elephant":
              if(player==1){
                elephant1.setFitHeight(128);elephant1.setFitWidth(128);
                elephant1.setX(286+130*y);elephant1.setY(11+130*x);
                elephant1.setRotate(0);
                field[x][y] = 2;animal[x][y] = "e1";
              }else{
                elephant2.setFitHeight(128);elephant2.setFitWidth(128);
                elephant2.setX(286+130*y);elephant2.setY(11+130*x);
                elephant2.setRotate(180);
                field[x][y] = -2;animal[x][y] = "e2";
              }
                break;
            case "giraffe":
              if(player==1){
                giraffe1.setFitHeight(128);giraffe1.setFitWidth(128);
                giraffe1.setX(286+130*y);giraffe1.setY(11+130*x);
                giraffe1.setRotate(0);
                field[x][y] = 3;animal[x][y] = "g1";
              }else{
                giraffe2.setFitHeight(128);giraffe2.setFitWidth(128);
                giraffe2.setX(286+130*y);giraffe2.setY(11+130*x);
                giraffe2.setRotate(180);
                field[x][y] = -3;animal[x][y] = "g2";
              }
                break;
            case "chick":
              if(player==1){
                chick1.setFitHeight(128);chick1.setFitWidth(128);
                chick1.setX(286+130*y);chick1.setY(11+130*x);
                chick1.setRotate(0);
                field[x][y] = 4;animal[x][y] = "c1";
              }else{
                chick2.setFitHeight(128);chick2.setFitWidth(128);
                chick2.setX(286+130*y);chick2.setY(11+130*x);
                chick2.setRotate(180);
                field[x][y] = -4;animal[x][y] = "c2";
              }
                break;
            case "chicken":
              if(player==1){
                Move.animalToImage(y,x).setFitHeight(128);Move.animalToImage(y,x).setFitWidth(128);
                Move.animalToImage(y,x).setX(286+130*y);Move.animalToImage(y,x).setY(11+130*x);
                Move.animalToImage(y,x).setRotate(0);
                field[x][y] = 5;
              }else{
                Move.animalToImage(y,x).setFitHeight(128);Move.animalToImage(y,x).setFitWidth(128);
                Move.animalToImage(y,x).setX(286+130*y);Move.animalToImage(y,x).setY(11+130*x);
                Move.animalToImage(y,x).setRotate(180);
                field[x][y] = -5;
              }
                break;
            default:
                break;
        }
    }

}

//次のターン表示のアニメーション
class View extends Group{
  public View(){
  }

  public void play(){
    if(Turn.turn==-1225)return;
    int y = 250;
    Text text1 = new Text(0,0, "PLAYER"+Turn.turnPlayer()+"のターン");
    text1.setFont(Font.font("impact",40));
    text1.setFill(Color.BLACK);
    getChildren().add(text1);
    TranslateTransition animation1 = new TranslateTransition(Duration.seconds(0.4),text1);
    TranslateTransition animation2 = new TranslateTransition(Duration.seconds(0.8),text1);
    TranslateTransition animation3 = new TranslateTransition(Duration.seconds(0.4),text1);
    animation1.setFromY(y);
    animation1.setToY(y);
    animation1.setFromX(960);
    animation1.setToX(340);
    animation2.setFromY(y);
    animation2.setToY(y);
    animation2.setFromX(340);
    animation2.setToX(340);
    animation3.setFromY(y);
    animation3.setToY(y);
    animation3.setFromX(340);
    animation3.setToX(-320);

    SequentialTransition animation = new SequentialTransition(animation1,animation2,animation3);
    animation.play();
  }
}
