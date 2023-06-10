package com.example.a2048;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView[] board = new TextView[16];
    int[][] boardInt = new int[][]{{0, 0, 0, 0},{0, 0, 0, 0},{0, 0, 0, 0},{0, 0, 0, 0}};

    String[] colorPalet = new String[]{"#FFFFFF", "#B32821", "#D84B20", "#606E8C", "#E1CC4F", "#6A5F31", "#3D642D", "#008F39", "#6C7059", "#B8B799", "#D36E70"};
    TextView tvGameState;
    Button btnTop, btnRight, btnBottom, btnLeft;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        board[0] = findViewById(R.id.textView1);
        board[1] = findViewById(R.id.textView2);
        board[2] = findViewById(R.id.textView3);
        board[3] = findViewById(R.id.textView4);

        board[4] = findViewById(R.id.textView5);
        board[5] = findViewById(R.id.textView6);
        board[6] = findViewById(R.id.textView7);
        board[7] = findViewById(R.id.textView8);

        board[8] = findViewById(R.id.textView9);
        board[9] = findViewById(R.id.textView10);
        board[10] = findViewById(R.id.textView11);
        board[11] = findViewById(R.id.textView12);

        board[12] = findViewById(R.id.textView13);
        board[13] = findViewById(R.id.textView14);
        board[14] = findViewById(R.id.textView15);
        board[15] = findViewById(R.id.textView16);

        btnTop = findViewById(R.id.btnTop);
        btnRight = findViewById(R.id.btnRight);
        btnBottom = findViewById(R.id.btnBottom);
        btnLeft = findViewById(R.id.btnLeft);

        tvGameState = findViewById(R.id.tvGameState);
    }

    // Game functions
    private int log2(int num){
        int result = 0;

        while(num > 1){
            num /= 2;
            result++;
        }

        return result;
    }

    private void displayFiles(){
        int index = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                board[index].setText(boardInt[i][j] == 0 ? "-" : String.valueOf(boardInt[i][j]));
                int colorIndex = log2(boardInt[i][j]);
                board[index].setBackgroundColor(Color.parseColor(colorPalet[colorIndex]));
                index++;
            }
        }
    }

    private int checkSpaceAvaliable() {
        int quantity = 0;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if(boardInt[i][j] == 0) {
                    quantity++;
                }
            }
        }

        return quantity;
    }

    private void generateNewCells(int min, int max) {

        Random random = new Random();

        int quantity = random.nextInt(max - min) + min;
        quantity = Math.min(quantity, checkSpaceAvaliable());

        int indexX, indexY;

        for (int i = 0; i < quantity; i++){
            int cell = random.nextInt(2) + 1;
            cell = (int) Math.pow(2, cell);


                do {

                    indexX = random.nextInt(4);
                    indexY = random.nextInt(4);
                } while (boardInt[indexX][indexY] > 0);

                boardInt[indexX][indexY] = (cell);

            }
        }

    private boolean checkEnd(){
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if(boardInt[i][j] == 2048){

                    // Win

                    btnTop.setEnabled(false);
                    btnRight.setEnabled(false);
                    btnBottom.setEnabled(false);
                    btnLeft.setEnabled(false);

                    tvGameState.setText(R.string.you_won);

                    displayFiles();

                    return true;
                }
            }
        }

        return false;
    }


    // Check game actions

    public boolean checkActionTop(){

        int tmpMoveIndex;

        for (int x = 3; x >= 0; x--){

            for(int y = 0; y < 4; y++){

                if(boardInt[y][x] == 0){
                    continue;
                }

                tmpMoveIndex = y;

                for (int move = y - 1; move >= 0; move--){
                    if(boardInt[move][x] == boardInt[y][x]){
                        return true;
                    } else if (boardInt[move][x] == 0) {
                        tmpMoveIndex = move;
                    } else {
                        break;
                    }
                }

                if(tmpMoveIndex == y){
                    continue;
                }

                if(boardInt[tmpMoveIndex][x] == 0){
                    return true;
                }
            }
        }

        return false;
    }

    public boolean checkActionRight(){

        int tmpMoveIndex;

        for (int x = 3; x >= 0; x--){

            for(int y = 0; y < 4; y++){

                if(boardInt[y][x] == 0){
                    continue;
                }

                tmpMoveIndex = x;

                for (int move = x + 1; move < 4; move++){


                    if(boardInt[y][move] == boardInt[y][x]){
                        return true;
                    } else if (boardInt[y][move] == 0) {
                        tmpMoveIndex = move;
                    } else {
                        break;
                    }
                }

                if(tmpMoveIndex == x){
                    continue;
                }

                if(boardInt[y][tmpMoveIndex] == 0){
                    return true;
                }

            }
        }

        return false;
    }

    public boolean checkActionBottom(){

        int tmpMoveIndex;

        for (int x = 0; x < 4; x++){

            for(int y = 3; y >= 0; y--){

                if(boardInt[y][x] == 0){
                    continue;
                }

                tmpMoveIndex = y;

                for (int move = y + 1; move < 4; move++){
                    if(boardInt[move][x] == boardInt[y][x]){
                        return true;
                    } else if (boardInt[move][x] == 0) {
                        tmpMoveIndex = move;
                    } else {
                        break;
                    }
                }

                if(tmpMoveIndex == y){
                    continue;
                }

                if(boardInt[tmpMoveIndex][x] == 0){
                    return true;
                }
            }
        }

        return false;
    }

    public boolean checkActionLeft(){
        int tmpMoveIndex;

        for (int x = 0; x < 4; x++){

            for(int y = 3; y >= 0; y--){

                if(boardInt[y][x] == 0){
                    continue;
                }

                tmpMoveIndex = x;

                for (int move = x - 1; move >= 0; move--){
                    if(boardInt[y][move] == boardInt[y][x]){
                        return true;
                    } else if (boardInt[y][move] == 0) {
                        tmpMoveIndex = move;
                    } else {
                        break;
                    }
                }

                if(tmpMoveIndex == x){
                    continue;
                }

                if(boardInt[y][tmpMoveIndex] == 0){
                    return true;
                }
            }
        }

        return false;
    }

    public boolean checkActions(){

        boolean top = checkActionTop(), right = checkActionRight(), bottom = checkActionBottom(), left = checkActionLeft();

        btnTop.setEnabled(top);
        btnRight.setEnabled(right);
        btnBottom.setEnabled(bottom);
        btnLeft.setEnabled(left);

        return !top && !right && !bottom && !left;
    }

    // Game Actions
    public void gameActionTop(View view) {

        int tmpMoveIndex;

        for (int x = 3; x >= 0; x--){

            for(int y = 0; y < 4; y++){

                if(boardInt[y][x] == 0){
                    continue;
                }

                tmpMoveIndex = y;

                for (int move = y - 1; move >= 0; move--){
                    if(boardInt[move][x] == boardInt[y][x]){
                        tmpMoveIndex = move;
                        break;
                    } else if (boardInt[move][x] == 0) {
                        tmpMoveIndex = move;
                    } else {
                        break;
                    }
                }

                if(tmpMoveIndex == y){
                    continue;
                }

                if(boardInt[tmpMoveIndex][x] == 0){
                    boardInt[tmpMoveIndex][x] = boardInt[y][x];
                    boardInt[y][x] = 0;
                } else if (boardInt[tmpMoveIndex][x] == boardInt[y][x]){
                    boardInt[tmpMoveIndex][x] = boardInt[y][x] * 2;
                    boardInt[y][x] = 0;
                }
            }
        }


        if(checkEnd()){
            return;
        }

        generateNewCells(1, 2);
        displayFiles();

        if(checkActions()){
            // Lose
            tvGameState.setText(R.string.game_over);
        }
    }

    public void gameActionRight(View view) {
        int tmpMoveIndex;

        for (int x = 3; x >= 0; x--){

            for(int y = 0; y < 4; y++){

                if(boardInt[y][x] == 0){
                    continue;
                }

                tmpMoveIndex = x;

                for (int move = x + 1; move < 4; move++){


                    if(boardInt[y][move] == boardInt[y][x]){
                        tmpMoveIndex = move;
                        break;
                    } else if (boardInt[y][move] == 0) {
                        tmpMoveIndex = move;
                    } else {
                        break;
                    }
                }

                if(tmpMoveIndex == x){
                    continue;
                }

                if(boardInt[y][tmpMoveIndex] == 0){
                    boardInt[y][tmpMoveIndex] = boardInt[y][x];
                    boardInt[y][x] = 0;
                } else if (boardInt[y][tmpMoveIndex] == boardInt[y][x]){
                    boardInt[y][tmpMoveIndex] = boardInt[y][x] * 2;
                    boardInt[y][x] = 0;
                }
            }
        }

        if(checkEnd()){
            return;
        }

        generateNewCells(1, 2);
        displayFiles();

        if(checkActions()){
            // Lose
            tvGameState.setText(R.string.game_over);
        }
    }

    public void gameActionBottom(View view) {
        int tmpMoveIndex;

        for (int x = 0; x < 4; x++){

            for(int y = 3; y >= 0; y--){

                if(boardInt[y][x] == 0){
                    continue;
                }

                tmpMoveIndex = y;

                for (int move = y + 1; move < 4; move++){
                    if(boardInt[move][x] == boardInt[y][x]){
                        tmpMoveIndex = move;
                        break;
                    } else if (boardInt[move][x] == 0) {
                        tmpMoveIndex = move;
                    } else {
                        break;
                    }
                }

                if(tmpMoveIndex == y){
                    continue;
                }

                if(boardInt[tmpMoveIndex][x] == 0){
                    boardInt[tmpMoveIndex][x] = boardInt[y][x];
                    boardInt[y][x] = 0;
                } else if (boardInt[tmpMoveIndex][x] == boardInt[y][x]){
                    boardInt[tmpMoveIndex][x] = boardInt[y][x] * 2;
                    boardInt[y][x] = 0;
                }
            }
        }


        if(checkEnd()){
            return;
        }

        generateNewCells(1, 2);
        displayFiles();

        if(checkActions()){
            // Lose
            tvGameState.setText(R.string.game_over);
        }
    }

    public void gameActionLeft(View view) {
        int tmpMoveIndex;

        for (int x = 0; x < 4; x++){

            for(int y = 3; y >= 0; y--){

                if(boardInt[y][x] == 0){
                    continue;
                }

                tmpMoveIndex = x;

                for (int move = x - 1; move >= 0; move--){
                    if(boardInt[y][move] == boardInt[y][x]){
                        tmpMoveIndex = move;
                        break;
                    } else if (boardInt[y][move] == 0) {
                        tmpMoveIndex = move;
                    } else {
                        break;
                    }
                }

                if(tmpMoveIndex == x){
                    continue;
                }

                if(boardInt[y][tmpMoveIndex] == 0){
                    boardInt[y][tmpMoveIndex] = boardInt[y][x];
                    boardInt[y][x] = 0;
                } else if (boardInt[y][tmpMoveIndex] == boardInt[y][x]){
                    boardInt[y][tmpMoveIndex] = boardInt[y][x] * 2;
                    boardInt[y][x] = 0;
                }
            }
        }


        if(checkEnd()){
            return;
        }

        generateNewCells(1, 2);
        displayFiles();

        if(checkActions()){
            // Lose
            tvGameState.setText(R.string.game_over);
        }
    }

    public void gameReset(View view) {
        for (int[] ints : boardInt) {

            Arrays.fill(ints, 0);
        }
        generateNewCells(2, 4);
        displayFiles();

        btnTop.setEnabled(true);
        btnRight.setEnabled(true);
        btnBottom.setEnabled(true);
        btnLeft.setEnabled(true);

        tvGameState.setText("---");
    }
}