import java.util.*;

public class MyPiece{
    List<Integer> mypiece = new ArrayList<Integer>();//持ち駒を格納
    private int piecenum = 0;
    public void addPiece(int animal){//もち米が増えた
        mypiece.add(animal);
        piecenum++;
    }
    public void removePiece(int animal){//持ち駒が使われた
        mypiece.remove(animal);
        piecenum--;
    }
    public int getPieceNum(){//持ち駒の数を返す
        return piecenum;
    }
}