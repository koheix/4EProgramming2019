public class Turn{
    private int turn = 1;
    public void turnChange(){
        this.turn *= -1;
    }
    public boolean myTurn(int player){
        if(player == turn) return true;
        else return false;
    }
}