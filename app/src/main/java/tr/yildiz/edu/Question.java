package tr.yildiz.edu;

public class Question {
    private String id;
    private String question;
    private String A;
    private String B;
    private String C;
    private String D;
    private String E;
    private String answer;
    private boolean isChecked = false;
    public Question(String id, String question, String a, String b, String c, String d, String e, String answer) {
        this.id = id;
        this.question = question;
        this.A = a;
        this.B = b;
        this.C = c;
        this.D = d;
        this.E = e;
        this.answer = answer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getA() {
        return A;
    }

    public void setA(String a) {
        A = a;
    }

    public String getB() {
        return B;
    }

    public void setB(String b) {
        B = b;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getC() {
        return C;
    }

    public void setC(String c) {
        C = c;
    }

    public String getD() {
        return D;
    }

    public void setD(String d) {
        D = d;
    }

    public String getE() {
        return E;
    }

    public void setE(String e) {
        E = e;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }


    Boolean isChecked(){
        return isChecked;
    }


}
