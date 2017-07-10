package kz.djunglestones.speechtotext;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.huxq17.swipecardsview.SwipeCardsView;

import kz.djunglestones.speechtotext.Adapter.CardAdapter;
import kz.djunglestones.speechtotext.Model.Model;

public class MainActivity extends Activity {

    private SwipeCardsView swipeCardsView;
    private List<Model> modelList  = new ArrayList<>();

    private  static TextView txtSpeechInput;
    private  static ImageButton btnSpeak;
    private  static final int REQ_CODE_SPEECH_INPUT = 100;
    private  static TextView txtGiven;
    private  static int mCurrentIndex = 0;
    private  static Words[] givenWords;
    private static int score = 0;
    private static TextView txtScore;
    private static Animation scale;
    private static ImageButton btnSound;
    private  static TextToSpeech tts;
    private  static String text;
    private static int WPM;
    private  static int errors;
    private static float seconds;
    private static TextView textViewTongueTwister;
    private static String prononcedSpeech;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSound = (ImageButton)findViewById(R.id.btn_sound);
        btnSpeak = (ImageButton) findViewById(R.id.btnSpeak);

        textViewTongueTwister = (TextView)findViewById(R.id.textViewTongueTwister);
        swipeCardsView = (SwipeCardsView) findViewById(R.id.swipeCardsView);
        swipeCardsView.retainLastCard(false);
        swipeCardsView.enableSwipe(true);

        getData();

        getActionBar().hide();

//        txtSpeechInput = (TextView) findViewById(R.id.txtSpeechInput);
//        txtGiven = (TextView)findViewById(R.id.txtGiven);
//        txtScore = (TextView)findViewById(R.id.score);
//        getActionBar().hide();
//        scale = AnimationUtils.loadAnimation(this,R.anim.mic_btn_animation);

//        TapTargetView.showFor(this,                 // `this` is an Activity
//                TapTarget.forView(findViewById(R.id.btnSpeak), "This is a microphone", "We have the best targets, believe me")
//                        .tintTarget(false).
//                        outerCircleColor(R.color.bg_gradient_end));

//        TapTargetView.showFor(this,TapTarget.forView(findViewById(R.id.txtGiven),"This is given word. Try to pronounce it, using mic")
//                .tintTarget(false)
//                .outerCircleColor(R.color.bg_gradient_start));

//        TapTargetView.showFor(this,
//                TapTarget.forView(findViewById(R.id.btn_sound),"This is sound button","tap this button to listen to the word")
//                        .tintTarget(false)
//                        .targetCircleColor(R.color.white)
//                        .outerCircleColor(R.color.bg_gradient_end));

//        TapTargetView.showFor(this,
//                    TapTarget.forView(findViewById(R.id.score),"This is your score. It will increase after your success")
//                    .tintTarget(false)
//                    .outerCircleColor(R.color.bg_gradient_end));

//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                SharedPreferences getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
//                boolean isFirstStart = getPrefs.getBoolean("firstStart",true);
//                if (isFirstStart){
//                    startActivity(new Intent(MainActivity.this,MyIntro.class));
//                    SharedPreferences.Editor editor = getPrefs.edit();
//                    editor.putBoolean("firstStart",false);
//                    editor.apply();
//                }
//            }
//        });
//
//        thread.start();
//
//
//
//
        tts=new TextToSpeech(MainActivity.this, new TextToSpeech.OnInitListener() {

            @Override
            public void onInit(int status) {
                // TODO Auto-generated method stub
                if(status == TextToSpeech.SUCCESS){
                    int result=tts.setLanguage(Locale.US);
                    if(result==TextToSpeech.LANG_MISSING_DATA ||
                            result==TextToSpeech.LANG_NOT_SUPPORTED){
                        Log.e("error", "This Language is not supported");
                    }
                    else{
                        tts.speak("Hello World", TextToSpeech.QUEUE_FLUSH, null);
                    }
                }
                else
                    Log.e("error", "Initilization Failed!");
            }
        });
//
//
//
//         givenWords= new Words[]{
//                new Words(0,"what noise annoys an oyster"),
//                new Words(1,"black background brown background."),
//                new Words(2,"tie twine to three tree twigs"),
//                new Words(3,"three short sword sheaths"),
//                new Words(4,"a quick-witted cricket critic"),
//                new Words(5,"how can a clam cram in a clean cream can"),
//                 new Words(6,"coy knows pseudonoise codes"),
//                 new Words(7,"clean clams crammed in clean cans"),
//                 new Words(8,"can you can a can as a canner can can a can"),
//                 new Words(9,"Sheena leads, Sheila needs."),
//                 new Words(10,"santa's short suit shrunk"),
//                 new Words(11,"Wayne went to Wales to watch walruses."),
//                 new Words(13,"Eleven benevolent elephants"),
//                 new Words(14,"Willy's real rear wheel"),
//                 new Words(15,"Send toast to ten tense stout saints' ten tall tents"),
//                 new Words(16,"Two tried and true tridents"),
//                 new Words(17,"Green glass globes glow greenly"),
//                 new Words(18,"The queen in green screamed"),
//                 new Words(19,"Six slimy snails sailed silently"),
//
//
//        };
//        String word = givenWords[mCurrentIndex].getWord();
//        txtGiven.setText(word);
//
        btnSpeak.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                promptSpeechInput();
                Toast.makeText(MainActivity.this,"BTNSPEAK CLICKED",Toast.LENGTH_SHORT).show();
            }
        });
//
//

    }

    public void btnSoundClicked(View view){
        doRightOut();
        convertTextToSpeech();
        Toast.makeText(MainActivity.this,"Clicked btnsound",Toast.LENGTH_SHORT).show();;
    }


    private void getData() {
        modelList.add(new Model("what noise annoys an oyster",R.mipmap.ic_launcher));
        modelList.add(new Model("Spider man",R.mipmap.ic_launcher));
        modelList.add(new Model("Spider mansome link to image",R.mipmap.ic_launcher));
        modelList.add(new Model("black background brown background",R.mipmap.ic_launcher));

        CardAdapter cardAdapter = new CardAdapter(modelList,this);
        swipeCardsView.setAdapter(cardAdapter);
        swipeCardsView.enableSwipe(false);
    }


    public void doLeftOut(){
        swipeCardsView.enableSwipe(true);
        swipeCardsView.getNextFocusDownId();
        swipeCardsView.enableSwipe(false);
        swipeCardsView.slideCardOut(SwipeCardsView.SlideType.LEFT);
    }

    public void doRightOut(){
        swipeCardsView.setNextFocusDownId(swipeCardsView.getNextFocusForwardId());
//        swipeCardsView.slideCardOut(SwipeCardsView.SlideType.RIGHT);
        swipeCardsView.enableSwipe(false);
        Toast.makeText(MainActivity.this,"doRightOut() clicked",Toast.LENGTH_SHORT).show();
    }

//    @Override
//    protected void onPause() {
//        // TODO Auto-generated method stub
//
//        if(tts != null){
//
//            tts.stop();
//            tts.shutdown();
//        }
//        super.onPause();
//    }

    /**
     * Showing google speech input dialog
     * */

    private void convertTextToSpeech() {
        text =Model.getTitle();
        if(text==null || "".equals(text))
        {
            text = "Content not available";
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }else
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }


//
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                Model.getTitle());
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }
//
//    /**
//     * Receiving speech input
//     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
//                    txtSpeechInput.setText(result.get(0));
                    prononcedSpeech = result.get(0);
                    Toast.makeText(MainActivity.this,prononcedSpeech,Toast.LENGTH_LONG);

                }
                break;
            }

        }
        if((prononcedSpeech.toLowerCase()).equals(textViewTongueTwister.getText().toString().toLowerCase())){
//            txtSpeechInput.append(String.valueOf(" "+txtSpeechInput.getText().toString().split(" ").length));
            Toast toast = Toast.makeText(getApplicationContext(), "Accepted", Toast.LENGTH_SHORT);
            toast.show();
//            updateWord();

        }
        else if ((prononcedSpeech.toLowerCase()).equals("next")){
//            updateWord();
        }
//        else if((txtSpeechInput.getText().toString().toLowerCase()).equals("previous")){
//
//        }
        else{

            Toast toastLost = Toast.makeText(getApplicationContext(), "LOST", Toast.LENGTH_SHORT);
            toastLost.show();
            Toast toastAdvice = Toast.makeText(getApplicationContext(), "Advice", Toast.LENGTH_SHORT);
            toastAdvice.show();
        }
    }
//
//    private void wpmCount() {
//        float minute = (float)seconds/60;
//        errors = numberOfIncorrectCharacters();
//        int wordsCount = (int)(txtSpeechInput.getText().toString().split(" ").length);
//        int newWPM = (int)(wordsCount/minute)-(errors*2);
//        if(newWPM>=0){
//            WPM= newWPM;
//        }
//    }
//
//    private int numberOfIncorrectCharacters() {
//        return 0;
//    }
//
//
//    public void updateImageBtn(){
//        btnSpeak.setImageResource(R.drawable.ico_mic64);
//    }

//    public void updateWord() {
//        score += 1;
//        swipeCardsView.getNextFocusDownId();
//    }
//        txtScore.setVisibility(View.VISIBLE);
//        btnSound.setVisibility(View.VISIBLE);
//        mCurrentIndex = (mCurrentIndex + 1) % givenWords.length;
//        String word = givenWords[mCurrentIndex+1].getWord();
//        txtGiven.setText(word);
//        txtScore.setText("Score: "+String.valueOf(score));
//
//
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//



    
}
