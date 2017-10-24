package objects;

import javafx.util.Pair;

import java.text.ParseException;
import java.util.List;

public class Bar {
    private int num;
    private String tone;
    private String timeSignature;
    private List<Note> notes;

    public Bar(int num, String tone, String timeSignature, List<Note> notes) {
        this.num = num;
        this.tone = tone;
        this.timeSignature = timeSignature;
        this.notes = notes;
    }

    public List<Note> getNotes() {

        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getTone() {
        return tone;
    }

    public void setTone(String tone) {
        this.tone = tone;
    }

    public String getTimeSignature() {
        return timeSignature;
    }

    public void setTimeSignature(String timeSignature) {
        this.timeSignature = timeSignature;
    }

    // This method returns a pair of integers <A,B> which represents
    // There are A number of B notes in the bar/measure
    // eg. 3/4 is 3 quarter-notes per bar
    //     4/4 is 4 quarter-notes per bar
    public Pair<Integer,Integer> parseTimeSignature () throws ParseException {
        int noteType, numberOfNote;
        String[] parsedTimeSignature = timeSignature.split("/");
        if (parsedTimeSignature.length != 2) {
            throw new ParseException("Bar.java",62);
        }

    }

    public boolean hasMoreRoom() {
        int durationForTheBar;
        for (Note note : notes) {

        }

        return false;
    }
}
