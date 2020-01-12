public class Turn{
    public static int turn = 1;
    static public void turnChange() {
        turn *= -1;
    }
    static public boolean myTurn(int player){
        if(player == turn) return true;
        else return false;
    }
}