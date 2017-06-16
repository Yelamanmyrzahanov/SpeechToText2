package kz.djunglestones.speechtotext;

import java.util.ArrayList;
import java.util.Locale;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private TextView txtSpeechInput;
    private ImageButton btnSpeak;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private TextView txtGiven;
    int mCurrentIndex = 0;
    Words[] givenWords;
    int score = 0;
    private TextView txtScore;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtSpeechInput = (TextView) findViewById(R.id.txtSpeechInput);
        btnSpeak = (ImageButton) findViewById(R.id.btnSpeak);
        txtGiven = (TextView)findViewById(R.id.txtGiven);
        txtScore = (TextView)findViewById(R.id.score);

        // hide the action bar
        getActionBar().hide();




         givenWords= new Words[]{
                new Words(0,"hello"),
                new Words(1,"cat"),
                new Words(2,"key"),
                new Words(3,"hello"),
                new Words(4,"pizza"),
                new Words(5,"monkey"),
                 new Words(6,"apple"),
                 new Words(7,"rock"),
                 new Words(8,"well"),
                 new Words(9,"hug"),
                 new Words(10,"super"),
                 new Words(11,"run"),


        };
        String word = givenWords[mCurrentIndex].getWord();
        txtGiven.setText(word);

        btnSpeak.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                btnSpeak.setImageResource(R.drawable.ico_mic128);
                promptSpeechInput();
            }




        });

    }

    /**
     * Showing google speech input dialog
     * */
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Receiving speech input
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    txtSpeechInput.setText(result.get(0));
                }
                break;
            }

        }
        if((txtSpeechInput.getText().toString().toLowerCase()).equals(txtGiven.getText().toString().toLowerCase())){
            Toast toast = Toast.makeText(getApplicationContext(), "Accepted", Toast.LENGTH_SHORT);
            toast.show();
            updateWord();

        }
        else{
            Toast toastLost = Toast.makeText(getApplicationContext(), "LOST", Toast.LENGTH_SHORT);
            toastLost.show();
            Toast toastAdvice = Toast.makeText(getApplicationContext(), "Advice", Toast.LENGTH_SHORT);
            toastAdvice.show();
            updateImageBtn();
        }
    }
    public void updateImageBtn(){
        btnSpeak.setImageResource(R.drawable.ico_mic64);
    }
    public void updateWord(){
        score+=1;
        txtSpeechInput.setText("");
        mCurrentIndex = (mCurrentIndex + 1) % givenWords.length;
        String word = givenWords[mCurrentIndex+1].getWord();
        txtGiven.setText(word);
        txtScore.setText("Score: "+String.valueOf(score));
        updateImageBtn();

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
