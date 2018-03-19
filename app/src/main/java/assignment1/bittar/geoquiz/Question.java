package assignment1.bittar.geoquiz;

/**
 * Created by Marcelo Bittar on 2/9/2018.
 * Question object contains two attributes.
 * int questionResId is the Resource Id of the question stored in strings.xml
 * boolean questionAnswerTrue is the answer to the question
 */

public class Question {
    private int mQuestionResId;
    private boolean mQuestionAnswerTrue;
    private boolean mQuestionIsAnswered;
    private boolean mQuestionCheat;

    public Question(int questionResId, boolean questionAnswerTrue) {
        mQuestionResId = questionResId;
        mQuestionAnswerTrue = questionAnswerTrue;
        mQuestionIsAnswered = false;
        mQuestionCheat = false;


    }

    public int getQuestionResId() {
        return mQuestionResId;
    }

    public void setQuestionResId(int questionResId) {
        mQuestionResId = questionResId;
    }

    public boolean isQuestionAnswerTrue() {
        return mQuestionAnswerTrue;
    }

    public void setQuestionAnswerTrue(boolean questionAnswerTrue) {
        mQuestionAnswerTrue = questionAnswerTrue;
    }

    public boolean isQuestionIsAnswered() {
        return mQuestionIsAnswered;
    }

    public void setQuestionIsAnswered(boolean questionIsAnswered) {
        mQuestionIsAnswered = questionIsAnswered;
    }

    public boolean isQuestionCheat() {
        return mQuestionCheat;
    }

    public void setQuestionCheat(boolean questionCheat) {
        mQuestionCheat = questionCheat;
    }
}
