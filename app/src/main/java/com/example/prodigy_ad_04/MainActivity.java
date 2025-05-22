package com.example.prodigy_ad_04;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button[] buttons = new Button[9];
    private boolean isPlayerXTurn = true;
    private int moveCount = 0;
    private TextView statusText;
    private int[][] winPatterns = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        statusText = findViewById(R.id.statusText);
        GridLayout gridLayout = findViewById(R.id.gridLayout);

        for (int i = 0; i < 9; i++) {
            buttons[i] = (Button) gridLayout.getChildAt(i);
            final int index = i;
            buttons[i].setOnClickListener(v -> handleMove(index));
        }

        findViewById(R.id.resetButton).setOnClickListener(v -> resetGame());
    }

    private void handleMove(int index) {
        if (!buttons[index].getText().toString().equals("")) return;

        buttons[index].setText(isPlayerXTurn ? "X" : "O");
        moveCount++;
        if (checkWinner()) {
            statusText.setText("Player " + (isPlayerXTurn ? "X" : "O") + " Wins!");
            disableButtons();
        } else if (moveCount == 9) {
            statusText.setText("It's a Draw!");
        } else {
            isPlayerXTurn = !isPlayerXTurn;
            statusText.setText("Player " + (isPlayerXTurn ? "X" : "O") + "'s Turn");
        }
    }

    private boolean checkWinner() {
        for (int[] pattern : winPatterns) {
            String a = buttons[pattern[0]].getText().toString();
            String b = buttons[pattern[1]].getText().toString();
            String c = buttons[pattern[2]].getText().toString();
            if (!a.equals("") && a.equals(b) && b.equals(c)) {
                return true;
            }
        }
        return false;
    }

    private void disableButtons() {
        for (Button button : buttons) {
            button.setEnabled(false);
        }
    }

    private void resetGame() {
        for (Button button : buttons) {
            button.setText("");
            button.setEnabled(true);
        }
        moveCount = 0;
        isPlayerXTurn = true;
        statusText.setText("Player X's Turn");
    }
}
