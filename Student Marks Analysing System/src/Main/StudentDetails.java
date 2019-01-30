// Student Details For Table
package Main;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class StudentDetails {

    private final IntegerProperty rollno;
    private final StringProperty name;
    private final StringProperty english;
    private final StringProperty hindi;
    private final StringProperty mathematics;
    private final StringProperty science;


    //Default constructor
    public StudentDetails() {
            this.rollno = new SimpleIntegerProperty(0);
            this.name = new SimpleStringProperty("0");
            this.english = new SimpleStringProperty("0");
            this.hindi = new SimpleStringProperty("0");
            this.mathematics = new SimpleStringProperty("0");
            this.science = new SimpleStringProperty("0");
        }

    public StudentDetails(Integer rollno, String name , String english, String hindi, String mathematics, String science ) {
            this.rollno = new SimpleIntegerProperty(rollno);
            this.name = new SimpleStringProperty(name);
            this.english = new SimpleStringProperty(english);
            this.hindi = new SimpleStringProperty(hindi);
            this.mathematics = new SimpleStringProperty(mathematics);
            this.science = new SimpleStringProperty(science);
        }

    //Getters
    public String getName() {
        return name.get();
    }

    public Integer getRollno() {
        return rollno.get();
    }

    public String getHindi() {
        return hindi.get();
    }

    public String getMathematics() {
        return mathematics.get();
    }

    public String getScience() {
        return science.get();
    }

    public String getEnglish() {
        return english.get();
    }


    //Setters
    public void setName(String value) {
        name.set(value);
    }

    public void setRollno(Integer value) {
        rollno.set(value);
    }

    public void setEnglish(String value) {
        english.set(value);
    }

    public void setScience(String science) {
        this.science.set(science);
    }

    public void setMathematics(String mathematics) {
        this.mathematics.set(mathematics);
    }

    public void setHindi(String hindi) {
        this.hindi.set(hindi);
    }

    //Property values
    public StringProperty nameProperty() { return name; }

    public IntegerProperty rollnoProperty() {
        return rollno;
    }

    public StringProperty englishProperty() {
        return english;
    }

    public StringProperty hindiProperty() { return hindi; }

    public StringProperty mathematicsProperty() { return mathematics; }

    public StringProperty scienceProperty() { return science; }

}
