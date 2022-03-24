package commons;

import java.util.Objects;

public class QuizzQuestionServerParsed {
    private Question question;
    private long startTime;
    private long questionNum;

    public QuizzQuestionServerParsed() {}

    public QuizzQuestionServerParsed(Question question, long startTime, long questionNum) {
        this.question = question;
        this.startTime = startTime;
        this.questionNum = questionNum;
    }

    public Question getQuestion() {
        return question;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getQuestionNum() {
        return questionNum;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public void setQuestionNum(int questionNum) {
        this.questionNum = questionNum;
    }

    @Override
    public String toString() {
        return "QuizzQuestionServerParsed{" +
                "question=" + question +
                ", startTime=" + startTime +
                ", questionNum=" + questionNum +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuizzQuestionServerParsed that = (QuizzQuestionServerParsed) o;
        return questionNum == that.questionNum && Objects.equals(question, that.question) && Objects.equals(startTime, that.startTime);
    }

}
