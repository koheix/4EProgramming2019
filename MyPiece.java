import java.util.*;

public class MyPiece{
    List<Integer> mypiece = new ArrayList<Integer>();//持ち駒を格納
    List<String> myimage = new ArrayList<String>();//持ち駒のImageViewを格納
    private int piecenum = 0;
    static int preanimal;//直前に削除された動物
    static String preimage;//直前削除された動物のイメージ
    public void reset(){//リセットする
        mypiece.clear();
        myimage.clear();
        piecenum = 0;
    }
    public void addPiece(int animal , String animalImage){//もち米が増えた
        mypiece.add(-1 * animal);
        myimage.add(piecenum, animalImage);
        piecenum++;
    }
    public void removePiece(double pieceX, double pieceY){//持ち駒が使われるかも//引数はマウスの値
        int element;
        if(!onMyPiece(pieceX, pieceY)) return;//持ち駒の上を押されてない
        element = MouseToElement(pieceX, pieceY);//取り出される駒が入っている場所のindexを返す
        preanimal = mypiece.get(element);
        mypiece.remove(element);
        preimage = myimage.get(element);
        myimage.remove(element);

        piecenum--;
    }
    public int getPieceNum(){//持ち駒の数を返す
        return piecenum;
    }
    public int returnX(){//駒を獲得した時に、ImageViewを移動させる先の左上のx座標を返す
        if(Turn.myTurn(1)){//プレイヤー1の場合
            if(piecenum < 4) return 10 + ((piecenum - 1) * 80);//持ち駒が3つより少ない
            else return 10 + ((piecenum - 4) * 80);
        }
        else{
            if(piecenum < 4) return 710 + ((piecenum - 1) * 80);
            else return 710 + ((piecenum - 4) * 80);
        }
    }

    public int returnY(){//駒を獲得した時に、ImageViewを移動させる先の左上のy座標を返す
        if(piecenum < 4) return 370;
        else return 370 + 80;
    }
    static boolean onMyPiece(double pieceX , double pieceY){//引数はマウスの値
        if(Turn.myTurn(1)){//プレイヤー1のターンなら
            if((10.0 <= pieceX)&&(pieceX <= 250.0)&&(370.0 <= pieceY)&&(pieceY <= 530.0)) return true;
            else return false;
        }
        else{
            if((710.0 <= pieceX)&&(pieceX <= 950.0)&&(370.0 <= pieceY)&&(pieceY <= 530.0)) return true;
            else return false;
        }
    }
    private int MouseToElement(double pieceX , double pieceY){
        int element = 0;
        if(Turn.myTurn(1)){//プレイヤー1のターンである場合
            element = (int)((pieceX - 10.0) / 80.0);
            if(pieceY >= 450.0) element += 3;//下の段を押された
        }
        else{//プレイヤー2のターンである場合
            element = (int)((pieceX - 710.0) / 80.0);
            if(pieceY >= 450.0) element += 3;
        }
        return element;
    }
    public boolean isAnimal(double mouseX, double mouseY){
        int element;
        element = MouseToElement(mouseX, mouseY);
        if(element < piecenum) return true;
        else return false;
    }
    public int eleToX(int element){//要素数から駒の左上のx座標を返す
        if(piecenum < 4) return 10 + (80 * element);
        else return 10 + (80 * (element - 3));
    }
    public int eleToY(int element){//要素数から駒の左上のy座標を返す
        if(piecenum < 4) return 370;
        else return 370 + 80;
    }
}