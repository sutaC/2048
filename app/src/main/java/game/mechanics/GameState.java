package game.mechanics;

public class GameState {

    public boolean actionTopAvaliable;
    public boolean actionRightAvaliable;
    public boolean actionLeftAvaliable;
    public boolean actionBottomAvaliable;

    public int[][] gameBoard;

    public int score;

    public char gameEnd;

    public GameState(
                boolean actionTopAvaliable,
                boolean actionRightAvaliable,
                boolean actionLeftAvaliable,
                boolean actionBottomAvaliable,
                int[][] gameBoard,
                int score,
                char gameEnd
    ){


       this.actionTopAvaliable = actionTopAvaliable;
       this.actionRightAvaliable = actionRightAvaliable;
       this.actionBottomAvaliable = actionBottomAvaliable;
       this.actionLeftAvaliable = actionLeftAvaliable;

       this.gameBoard = gameBoard;

       this.score = score;

       this.gameEnd = gameEnd;
    }
}
