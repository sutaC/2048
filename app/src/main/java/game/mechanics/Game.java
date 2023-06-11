package game.mechanics;

import java.util.Arrays;
import java.util.Random;

public class Game {

    private int[][] gameBoard = new int[][]{{0, 0, 0, 0},{0, 0, 0, 0},{0, 0, 0, 0},{0, 0, 0, 0}};

    private boolean actionTopAvaliable = false;
    private boolean actionRightAvaliable = false;
    private boolean actionBottomAvaliable = false;
    private boolean actionLeftAvaliable = false;

    private int score = 0;

    public Game(){
        generateNewCells(2, 4);
        
        checkActions();
    }

    public Game(int[][] board, int score){
        gameBoard = board;
        this.score = score;

        checkActions();
    }

    // Cell generation

    private void generateNewCells(int min, int max) {

        Random random = new Random();

        int quantity = random.nextInt(max - min) + min;

        int avaliableQuantity = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if(gameBoard[i][j] == 0) {
                    avaliableQuantity++;
                }
            }
        }
        quantity = Math.min(quantity, avaliableQuantity);

        int indexX, indexY;

        for (int i = 0; i < quantity; i++){
            int cell = random.nextInt(2) + 1;
            cell = (int) Math.pow(2, cell);

            do {

                indexX = random.nextInt(4);
                indexY = random.nextInt(4);
            } while (gameBoard[indexX][indexY] > 0);

            gameBoard[indexX][indexY] = (cell);

        }

    }


    // Game State Check
    private boolean checkActionTop(){

        int tmpMoveIndex;

        for (int x = 3; x >= 0; x--){

            for(int y = 0; y < 4; y++){

                if(gameBoard[y][x] == 0){
                    continue;
                }

                tmpMoveIndex = y;

                for (int move = y - 1; move >= 0; move--){
                    if(gameBoard[move][x] == gameBoard[y][x]){
                        return true;
                    } else if (gameBoard[move][x] == 0) {
                        tmpMoveIndex = move;
                    } else {
                        break;
                    }
                }

                if(tmpMoveIndex == y){
                    continue;
                }

                if(gameBoard[tmpMoveIndex][x] == 0){
                    return true;
                }
            }
        }

        return false;
    }

    private boolean checkActionRight(){

        int tmpMoveIndex;

        for (int x = 3; x >= 0; x--){

            for(int y = 0; y < 4; y++){

                if(gameBoard[y][x] == 0){
                    continue;
                }

                tmpMoveIndex = x;

                for (int move = x + 1; move < 4; move++){


                    if(gameBoard[y][move] == gameBoard[y][x]){
                        return true;
                    } else if (gameBoard[y][move] == 0) {
                        tmpMoveIndex = move;
                    } else {
                        break;
                    }
                }

                if(tmpMoveIndex == x){
                    continue;
                }

                if(gameBoard[y][tmpMoveIndex] == 0){
                    return true;
                }

            }
        }

        return false;
    }

    private boolean checkActionBottom(){

        int tmpMoveIndex;

        for (int x = 0; x < 4; x++){

            for(int y = 3; y >= 0; y--){

                if(gameBoard[y][x] == 0){
                    continue;
                }

                tmpMoveIndex = y;

                for (int move = y + 1; move < 4; move++){
                    if(gameBoard[move][x] == gameBoard[y][x]){
                        return true;
                    } else if (gameBoard[move][x] == 0) {
                        tmpMoveIndex = move;
                    } else {
                        break;
                    }
                }

                if(tmpMoveIndex == y){
                    continue;
                }

                if(gameBoard[tmpMoveIndex][x] == 0){
                    return true;
                }
            }
        }

        return false;
    }

    private boolean checkActionLeft(){
        int tmpMoveIndex;

        for (int x = 0; x < 4; x++){

            for(int y = 3; y >= 0; y--){

                if(gameBoard[y][x] == 0){
                    continue;
                }

                tmpMoveIndex = x;

                for (int move = x - 1; move >= 0; move--){
                    if(gameBoard[y][move] == gameBoard[y][x]){
                        return true;
                    } else if (gameBoard[y][move] == 0) {
                        tmpMoveIndex = move;
                    } else {
                        break;
                    }
                }

                if(tmpMoveIndex == x){
                    continue;
                }

                if(gameBoard[y][tmpMoveIndex] == 0){
                    return true;
                }
            }
        }

        return false;
    }

    private boolean checkActions(){

        actionTopAvaliable = checkActionTop(); 
        actionRightAvaliable = checkActionRight(); 
        actionBottomAvaliable = checkActionBottom(); 
        actionLeftAvaliable = checkActionLeft();
        

        return !actionTopAvaliable && !actionRightAvaliable && !actionBottomAvaliable && !actionLeftAvaliable;
    }


    // Game Actions

    private GameState endGameAction(){

        // Check Win
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++) {
                if(gameBoard[i][j] == 2048){
                    // Win
                    return new GameState(
                            false,
                            false,
                            false,
                            false,
                            gameBoard,
                            score,
                            'W'
                    );
                }
        }

        // Create new situation on board
        generateNewCells(1, 2);
        
        // Check Lose
        if(checkActions()){
            // Lose
            return new GameState(
                    false,
                    false,
                    false,
                    false,
                    gameBoard,
                    score,
                    'L'
            );
        }

        // Game continues
        return new GameState(
                actionTopAvaliable,
                actionRightAvaliable,
                actionLeftAvaliable,
                actionBottomAvaliable,
                gameBoard,
                score,
                ' '
        );
    }
    
    
    public GameState gameActionTop() {
        int tmpMoveIndex;

        for (int x = 3; x >= 0; x--){

            for(int y = 0; y < 4; y++){

                if(gameBoard[y][x] == 0){
                    continue;
                }

                tmpMoveIndex = y;

                for (int move = y - 1; move >= 0; move--){
                    if(gameBoard[move][x] == gameBoard[y][x]){
                        tmpMoveIndex = move;
                        break;
                    } else if (gameBoard[move][x] == 0) {
                        tmpMoveIndex = move;
                    } else {
                        break;
                    }
                }

                if(tmpMoveIndex == y){
                    continue;
                }

                if(gameBoard[tmpMoveIndex][x] == 0){
                    gameBoard[tmpMoveIndex][x] = gameBoard[y][x];
                    gameBoard[y][x] = 0;
                } else if (gameBoard[tmpMoveIndex][x] == gameBoard[y][x]){
                    gameBoard[tmpMoveIndex][x] = gameBoard[y][x] * 2;
                    gameBoard[y][x] = 0;

                    score += gameBoard[tmpMoveIndex][x];
                }
            }
        }

        return endGameAction();
    }
    
    public GameState gameActionRight(){

        int tmpMoveIndex;

        for (int x = 3; x >= 0; x--){

            for(int y = 0; y < 4; y++){

                if(gameBoard[y][x] == 0){
                    continue;
                }

                tmpMoveIndex = x;

                for (int move = x + 1; move < 4; move++){


                    if(gameBoard[y][move] == gameBoard[y][x]){
                        tmpMoveIndex = move;
                        break;
                    } else if (gameBoard[y][move] == 0) {
                        tmpMoveIndex = move;
                    } else {
                        break;
                    }
                }

                if(tmpMoveIndex == x){
                    continue;
                }

                if(gameBoard[y][tmpMoveIndex] == 0){
                    gameBoard[y][tmpMoveIndex] = gameBoard[y][x];
                    gameBoard[y][x] = 0;
                } else if (gameBoard[y][tmpMoveIndex] == gameBoard[y][x]){
                    gameBoard[y][tmpMoveIndex] = gameBoard[y][x] * 2;
                    gameBoard[y][x] = 0;

                    score+= gameBoard[y][tmpMoveIndex];
                }
            }
        }
        
        return endGameAction();
    }
    
    public GameState gameActionBottom(){

        int tmpMoveIndex;

        for (int x = 0; x < 4; x++){

            for(int y = 3; y >= 0; y--){

                if(gameBoard[y][x] == 0){
                    continue;
                }

                tmpMoveIndex = y;

                for (int move = y + 1; move < 4; move++){
                    if(gameBoard[move][x] == gameBoard[y][x]){
                        tmpMoveIndex = move;
                        break;
                    } else if (gameBoard[move][x] == 0) {
                        tmpMoveIndex = move;
                    } else {
                        break;
                    }
                }

                if(tmpMoveIndex == y){
                    continue;
                }

                if(gameBoard[tmpMoveIndex][x] == 0){
                    gameBoard[tmpMoveIndex][x] = gameBoard[y][x];
                    gameBoard[y][x] = 0;
                } else if (gameBoard[tmpMoveIndex][x] == gameBoard[y][x]){
                    gameBoard[tmpMoveIndex][x] = gameBoard[y][x] * 2;
                    gameBoard[y][x] = 0;

                    score+= gameBoard[tmpMoveIndex][x];
                }
            }
        }
        
        return endGameAction();
    }
    
    public GameState gameActionLeft(){
        
        int tmpMoveIndex;

        for (int x = 0; x < 4; x++){

            for(int y = 3; y >= 0; y--){

                if(gameBoard[y][x] == 0){
                    continue;
                }

                tmpMoveIndex = x;

                for (int move = x - 1; move >= 0; move--){
                    if(gameBoard[y][move] == gameBoard[y][x]){
                        tmpMoveIndex = move;
                        break;
                    } else if (gameBoard[y][move] == 0) {
                        tmpMoveIndex = move;
                    } else {
                        break;
                    }
                }

                if(tmpMoveIndex == x){
                    continue;
                }

                if(gameBoard[y][tmpMoveIndex] == 0){
                    gameBoard[y][tmpMoveIndex] = gameBoard[y][x];
                    gameBoard[y][x] = 0;
                } else if (gameBoard[y][tmpMoveIndex] == gameBoard[y][x]){
                    gameBoard[y][tmpMoveIndex] = gameBoard[y][x] * 2;
                    gameBoard[y][x] = 0;

                    score += gameBoard[y][tmpMoveIndex];
                }
            }
        }
        
        return endGameAction();
    }
    
    
    public GameState gameActionReset(){
        for (int[] fields : gameBoard) {
            Arrays.fill(fields, 0);
        }
        score = 0;

        generateNewCells(2, 4);
        checkActions();

        return new GameState(
                actionTopAvaliable,
                actionRightAvaliable,
                actionLeftAvaliable,
                actionBottomAvaliable,
                gameBoard,
                score,
                'R'
        );
    }


    public GameState getGameState(){
        // Check Win
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++) {
                if(gameBoard[i][j] == 2048){
                    // Win
                    return new GameState(
                            false,
                            false,
                            false,
                            false,
                            gameBoard,
                            score,
                            'W'
                    );
                }
            }

        // Check Lose
        if(checkActions()){
            // Lose
            return new GameState(
                    false,
                    false,
                    false,
                    false,
                    gameBoard,
                    score,
                    'L'
            );
        }

        // Game continues
        return new GameState(
                actionTopAvaliable,
                actionRightAvaliable,
                actionLeftAvaliable,
                actionBottomAvaliable,
                gameBoard,
                score,
                ' '
        );
    }
}
