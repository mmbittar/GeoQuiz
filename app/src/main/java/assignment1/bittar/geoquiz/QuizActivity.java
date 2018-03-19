/**
 * Created by Marcelo Bittar on 2/9/2018.
 * Main Activity for the GeoQuiz app.
 */

package assignment1.bittar.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


public class QuizActivity extends AppCompatActivity {

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mCheatButton;
    private ImageButton mNextButton;
    private ImageButton mBackButton;
    private TextView mQuestionText;
    private TextView mQuestAns;
    private boolean mIsCheater;


    private static final String KEY_INDEX = "index";
    private static final String TOTAL_ANSWERED = "answered";
    private static final String CORRECT_ANSWERS = "correct";
    private static final int REQUEST_CODE_CHEAT = 0;


    /**
     *  Array of Question objects containing all questions and answers of our quiz game
     */
    private Question [] mQuestionBank = new Question[]{
            new Question(R.string.question_australia, true),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true)
    };

    //Current index of our Questions array
    private int mCurrentIndex = 0;
    //Current number of correct answers. Used to calculate grade
    private int mCorrectAnswers = 0;
    //Total questions answered. Used to calculate grade
    private int mTotalAnswered = 0;
    private boolean cheatArray[] = new boolean[mQuestionBank.length];



    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        //Saves current question index
        savedInstanceState.putInt(KEY_INDEX,mCurrentIndex);
        //Saves total number of questions answered
        savedInstanceState.putInt(TOTAL_ANSWERED,mTotalAnswered);
        //Saves how many correct answers so far
        savedInstanceState.putInt(CORRECT_ANSWERS,mCorrectAnswers);
        //Saves what questions have been answered
        for(int i = 0;i<mQuestionBank.length;i++){
            savedInstanceState.putBoolean("q"+i+1,mQuestionBank[i].isQuestionIsAnswered());
        }
        savedInstanceState.putBoolean("didUserCheat",mIsCheater);
        for(int i = 0;i<mQuestionBank.length;i++){
            savedInstanceState.putBoolean("cheat"+i+1,mQuestionBank[i].isQuestionCheat());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        if(savedInstanceState != null){
            //Loads current question index
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX,0);
            //Loads how many correct answers
            mCorrectAnswers = savedInstanceState.getInt(CORRECT_ANSWERS,0);
            //Loads total number of questions answered
            mTotalAnswered = savedInstanceState.getInt(TOTAL_ANSWERED,0);
            //Loads what questions have been answered or not
            for(int i = 0;i<mQuestionBank.length;i++){
             mQuestionBank[i].setQuestionIsAnswered(savedInstanceState.getBoolean("q"+i+1));
             mQuestionBank[i].setQuestionCheat(savedInstanceState.getBoolean("cheat"+i+1));
            }
            mIsCheater = savedInstanceState.getBoolean("didUserCheat",false);

        }
        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(true);
            }
        });

        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(false);
            }
        });

        mQuestionText = (TextView)findViewById(R.id.question_text_view);

        mQuestAns =(TextView)findViewById(R.id.tvQuestAns);

        int question = mQuestionBank[mCurrentIndex].getQuestionResId();

        mQuestionText.setText(question);

        mQuestionText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });

        mNextButton = (ImageButton) findViewById(R.id.next_button);

        // Loops questions in question bank. Limits mCurrentIndex to the the length of array.
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                mIsCheater = false;
                updateQuestion();
            }
        });
        mBackButton = (ImageButton) findViewById(R.id.back_button);

        // Loops questions in question bank. Limits mCurrentIndex to the the length of array.
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mCurrentIndex == 0){
                    mCurrentIndex = mQuestionBank.length;
                }
                mCurrentIndex = (mCurrentIndex - 1) % mQuestionBank.length;
                updateQuestion();
            }
        });
        mCheatButton = (Button)findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(QuizActivity.this,CheatActivity.class);
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isQuestionAnswerTrue();
                Intent intent = CheatActivity.newIntent(QuizActivity.this,answerIsTrue);
                startActivityForResult(intent,REQUEST_CODE_CHEAT);
            }
        });


        updateQuestion();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE_CHEAT) {
            if (data == null) {
                return;
            }
            mIsCheater = CheatActivity.wasAnswerShown(data);
            mQuestionBank[mCurrentIndex].setQuestionCheat(mIsCheater);
        }
    }

        /**
         * Updates TextView with question stored in mQuestionBank array
         * If question has been answered once, True and False button are hidden and text
         * saying question has been answered shows up
         */
    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getQuestionResId();
        mQuestionText.setText(question);
        if(mQuestionBank[mCurrentIndex].isQuestionIsAnswered()){
            mFalseButton.setVisibility(View.GONE);
            mTrueButton.setVisibility(View.GONE);
            mQuestAns.setVisibility(View.VISIBLE);
        }
        else{
            mFalseButton.setVisibility(View.VISIBLE);
            mTrueButton.setVisibility(View.VISIBLE);
            mQuestAns.setVisibility(View.GONE);

        }
    }

    /**
     * If user presses true and answer to question is true Toast shows Correct! Otherwise toast will
     * show Incorrect!
     * @param userPressedTrue
     */
    private void checkAnswer(boolean userPressedTrue){

        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isQuestionAnswerTrue();
        mIsCheater = mQuestionBank[mCurrentIndex].isQuestionCheat();
        int messageResId = 0;
        if(mIsCheater) {
            messageResId = R.string.judgment_toast;
        }
        else {


            if (userPressedTrue == answerIsTrue) {
                messageResId = R.string.correct_toast;
                mCorrectAnswers++;
            } else {
                messageResId = R.string.incorrect_toast;
            }
        }
        /**
         * Challenge 1 - Make toast show up on top
         */
        Toast toast = Toast.makeText(this,messageResId,Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP,0,200);
        toast.show();

        mQuestionBank[mCurrentIndex].setQuestionIsAnswered(true);
        //Hides True and False button after question was answered. Displays message showing question was answered
        mFalseButton.setVisibility(View.GONE);
        mTrueButton.setVisibility(View.GONE);
        mQuestAns.setVisibility(View.VISIBLE);
        mTotalAnswered++;
        float grade = ((float)mCorrectAnswers/(float)mQuestionBank.length)*100;

        //If last question was answered displays the grade for the quiz
        if(mTotalAnswered == mQuestionBank.length){
            toast = Toast.makeText(this,"Your grade:"+grade+"%",Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP,0,200);
            toast.show();
        }
    }

}
