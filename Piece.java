public abstract class Piece{
    abstract public boolean movable();//コマがクリックされた時に動けるところを判定
    abstract public void move();//選択されたコマが動く処理
    abstract public boolean isKing();//そのコマは王なのか
}