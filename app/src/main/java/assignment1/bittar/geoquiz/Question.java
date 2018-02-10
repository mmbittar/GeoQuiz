package assignment1.bittar.geoquiz;

/**
 * Created by Marcelo Bittar on 2/9/2018.
 */

public class Question {
    private int mQuestionResId;
    private boolean mQuestionAnswerTrue;

    public Question(int questionResId, boolean questionAnswerTrue) {
        mQuestionResId = questionResId;
        mQuestionAnswerTrue = questionAnswerTrue;
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
}
