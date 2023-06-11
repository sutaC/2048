package com.example.a2048;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import game.mechanics.Game;
import game.mechanics.GameState;

public class MainActivity extends AppCompatActivity {

    // Android objects
    ConstraintLayout layoutMain;
    TextView[] board = new TextView[16];
    TextView tvGameState, tvScore, tvBestScore;
    Button btnReset;

    // Objects

    Game game;
    Storage storage;
    SwipeListener swipeListener;

    // Data

    boolean actionTopAvaliable, actionRightAvaliable, actionBottomAvaliable, actionLeftAvaliable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Android Element */ {
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

            btnReset = findViewById(R.id.btnReset);

            tvGameState = findViewById(R.id.tvGameState);
            tvScore = findViewById(R.id.tvScore);
            tvBestScore = findViewById(R.id.tvBestScore);

            layoutMain = findViewById(R.id.layoutMain);
        }

        /* Game setup */ {
            storage = new Storage();

            int[][] savedBoardData = storage.loadBoardData();
            if (savedBoardData == null) {
                game = new Game();
            } else {
                game = new Game(savedBoardData, storage.loadScore());
            }

            displayGameState(game.getGameState());
        }

        /* Action listeners */ {
            swipeListener = new SwipeListener(layoutMain);
            btnReset.setOnClickListener(v -> displayGameState(game.gameActionReset()));
        }
    }

    // Display
    @SuppressLint("SetTextI18n")
    private void displayGameState(GameState state){

        // Set buttons availability
        actionTopAvaliable = state.actionTopAvaliable;
        actionRightAvaliable = state.actionRightAvaliable;
        actionBottomAvaliable = state.actionBottomAvaliable;
        actionLeftAvaliable = state.actionLeftAvaliable;
//        btnTop.setEnabled(state.actionTopAvaliable);
//        btnRight.setEnabled(state.actionRightAvaliable);
//        btnBottom.setEnabled(state.actionBottomAvaliable);
//        btnLeft.setEnabled(state.actionLeftAvaliable);

        // Display game state text
        switch (state.gameEnd){
            case ' ':
                break;
            case 'R':
                tvGameState.setText(R.string.empty);
                break;
            case 'W':
                tvGameState.setText(R.string.you_won);
                break;
            case 'L':
                tvGameState.setText(R.string.game_over);
                break;
        }

        // Display score
        tvScore.setText("Score: " + state.score);

        // Display board
        String[] colorPalet = new String[]{
                "#FFFFFF",
                "#B32821",
                "#D84B20",
                "#606E8C",
                "#E1CC4F",
                "#6A5F31",
                "#3D642D",
                "#008F39",
                "#6C7059",
                "#B8B799",
                "#D36E70"
        };
        int index = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                // Set field value
                board[index].setText(state.gameBoard[i][j] == 0 ? "-" : String.valueOf(state.gameBoard[i][j]));

                // Set field color
                int colorIndex = 0, tmpFieldValue = state.gameBoard[i][j];

                while(tmpFieldValue > 1){
                    tmpFieldValue /= 2;
                    colorIndex++;
                }

                board[index].setBackgroundColor(Color.parseColor(colorPalet[colorIndex]));
                index++;
            }
        }

        // Sets Best Score
        int bestScore = storage.loadBestScore();

        if(bestScore < state.score){
            storage.saveBestScore(state.score);
            tvBestScore.setText("Best Score: " + state.score);
        } else {
            tvBestScore.setText("Best Score: " + bestScore);
        }

        // Saves game state
        storage.saveBoardData(state.gameBoard);
        storage.saveScore(state.score);

    }


    // Inner classes
    private final class Storage {


        /* Saving game data */
        private static final String SHARED_PREFS = "sharedPrefs";
        private static final String BOARD = "board";
        private static final String SCORE = "score";
        private static final String BEST_SCORE = "bestScore";

        // Board
        public void saveBoardData(int[][] boardData) {
            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();


            StringBuilder boardToSave = new StringBuilder();
            for (int[] fieldsY : boardData) {
                for (int fieldsX : fieldsY) {
                    boardToSave.append(fieldsX).append("/");
                }
            }


            editor.putString(BOARD, boardToSave.toString());
            editor.apply();
        }

        public int[][] loadBoardData() {
            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
            String boardData = sharedPreferences.getString(BOARD, null);

            if (boardData == null) {
                return null;
            }

            int[][] newBoard = new int[][]{{0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}};
            String[] values = boardData.split(getString(R.string.Devider));

            int iterator = 0;
            for (int i = 0; i < newBoard.length; i++) {
                for (int j = 0; j < newBoard[i].length; j++) {
                    newBoard[i][j] = Integer.parseInt(values[iterator]);
                    iterator++;
                }
            }

            return newBoard;
        }

        // Score
        public void saveScore(int score) {
            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(SCORE, score);
            editor.apply();
        }

        public int loadScore() {
            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
            return sharedPreferences.getInt(SCORE, 0);
        }

        // Best Score
        public void saveBestScore(int bestScore) {
            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(BEST_SCORE, bestScore);
            editor.apply();
        }

        public int loadBestScore() {
            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
            return sharedPreferences.getInt(BEST_SCORE, 0);
        }
    }

    private final class SwipeListener implements View.OnTouchListener {
        // Initialization
        GestureDetector gestureDetector;

        @SuppressWarnings("deprecation")
        public SwipeListener(View view){
            int threshold = 100;
            int velocity_threshold = 100;

            GestureDetector.SimpleOnGestureListener listener =
                new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onDown(@NonNull MotionEvent e) {
                        return true;
                    }

                    @Override
                    public boolean onFling(@NonNull MotionEvent e1, @NonNull MotionEvent e2, float velocityX, float velocityY) {
                        float xDiff = e2.getX() - e1.getX();
                        float yDiff = e2.getY() - e1.getY();

                        try {
                            if (Math.abs(xDiff) > Math.abs((yDiff))){
                                if(Math.abs(xDiff) > threshold && Math.abs(velocityX) > velocity_threshold){
                                    if(xDiff > 0){
                                        // Swipe right
                                        if(actionRightAvaliable){
                                            displayGameState(game.gameActionRight());
                                        }
                                    } else {
                                        // Swipe left
                                        if(actionLeftAvaliable){
                                            displayGameState(game.gameActionLeft());
                                        }
                                    }
                                    return true;
                                }
                            } else {
                                if (Math.abs(yDiff) > threshold && Math.abs(velocityY) > velocity_threshold) {
                                    if (yDiff > 0) {
                                        // Swipe down
                                        if(actionBottomAvaliable){
                                            displayGameState(game.gameActionBottom());
                                        }
                                    } else {
                                        // Swipe up
                                        if(actionTopAvaliable){
                                            displayGameState(game.gameActionTop());
                                        }
                                    }
                                    return true;
                                }
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        return false;
                    }
                };

            gestureDetector = new GestureDetector(listener);
            view.setOnTouchListener(this);
        }
        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return gestureDetector.onTouchEvent(event);
        }

    }
}