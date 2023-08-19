package tr.com.erenkaynar.library.earthquake.models;

import java.io.Serializable;
import java.time.Instant;

public class Revised implements Serializable {

    private int number;
    private Instant datetime;

    public Revised() {
    }

    public Revised(int number, Instant datetime) {
        this.number = number;
        this.datetime = datetime;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Instant getDatetime() {
        return datetime;
    }

    public void setDatetime(Instant datetime) {
        this.datetime = datetime;
    }
}
