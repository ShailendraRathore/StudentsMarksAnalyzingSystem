package Main;
//For Report Card Table
public class Student {

    private int marks;
    private String result;
    private String subject;

    public Student(String Subject, int Marks, String Result)
    {
        this.subject = Subject;
        this.marks = Marks;
        this.result = Result;
    }

    //Getters & Setters
    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
