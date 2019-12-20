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
        Group root = new Group();
 
        //描画用キャンバスノードの作成
        Canvas cvs = new Canvas(width, height);//make a canvas
        root.getChildren().add(cvs);
 
        this.g = cvs.getGraphicsContext2D();
        Scene scene = new Scene(root, width, height, Color.WHITE);//make a window its background color is white
        stage.setScene(scene);
        stage.show();//ウィンドウの表示
 
        drawField();
        initialize(root);
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

    public void drawChara(String animal , int x , int y ,Group root) {//x ,yは盤面の配列の座標
        switch(animal){//動物判定
            case "lion":
            //画像を貼る処理
            ImageView lion = new ImageView("images/lion.png");
            lion.setFitHeight(128);
            lion.setFitWidth(128);
            root.getChildren().add(lion);
                break;
            case "elephant":
                break;
            case "giraffe":
                break;
            case "chick":
                break;
            case "chicken":
                break;
            default:
                break;
        }
    }
    private void initialize(Group root){//はじめに実行
        drawChara("lion" , 3 , 1 , root);
        drawChara("chick" , 2 , 1 , root);
        drawChara("elephant" , 3 , 0 , root);
        drawChara("giraffe", 3, 2 , root);
    }
}