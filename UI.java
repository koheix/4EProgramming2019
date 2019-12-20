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

/* lion is 0 , elephant is 1 , giraffe is 2 , chick is 3 , chiken is 4*/

public class UI extends Application {
    private final int width = 960;
    private final int height = 540;//ウインドウの大きさ
 
    /*Map<String, Integer>animals = new HashMap<String, Integer>() {
        {
            put("lion", 0);
            put("elephant", 1);
            put("giraffe" , 2);
            put("chick" , 3);
            put("chicken" , 4);
        }
    };*/
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
    }
 
    private void drawField() {//描画のプログラム
        for(int i = 0;i < 4;i++){
            for(int j = 0;j < 3;j++){
                g.strokeRect(
                    (width / 2) - ((height - 20) / 4)*3/2 + ((height - 20) / 4)*j, 10 + ((height - 20) / 4)*i,(height - 20)/4, (height - 20) / 4
                 );//盤面の描画//右上座標が100,20//盤面の描画
                g.strokeRect(

                );

            }
        }
    }

    public void drawChara(String animal) {
        switch(animal){//動物判定
            case "lion":
            //画像を貼る処理
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
}