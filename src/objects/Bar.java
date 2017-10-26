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

    public List<Note> getNotes() { return notes; }

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
    private Pair<Integer,Integer> parseTimeSignature() throws ParseException {
        int noteType = 0, numberOfNotes = 0;
        String[] parsedTimeSignature = timeSignature.split("/");
        if (parsedTimeSignature.length != 2) {
            throw new ParseException("Bar.java",62);
        } else {
            try {
                numberOfNotes = Integer.parseInt(parsedTimeSignature[0]);
                noteType = Integer.parseInt(parsedTimeSignature[1]);
            } catch (NumberFormatException ne) {
                System.out.println("Error - Time signature not an integer");
            }
            return new Pair<>(numberOfNotes, noteType);
        }
    }

    public double getDuration() throws ParseException{
        double duration = 0;
        for (Note note : notes) {
            duration += note.getDuration();
        }
        return duration;
    }

    /*
        Whole Note: Represents by number  1 -- Takes 4 beats
         Half Note: Represents by number  2 -- Takes 2 beats
      Quarter Note: Represents by number  4 -- Takes 1 beat
       Eighth Note: Represents by number  8 -- Takes 1/2 beat
    Sixteenth Note: Represents by number 16 -- Takes 1/4 beat
    */
    
    public boolean hasMoreRoom() throws ParseException{
        int numberOfNotes = parseTimeSignature().getKey();
        int noteType = parseTimeSignature().getValue();
        double barDuration = (4.0 / noteType) * numberOfNotes;
        return getDuration() < barDuration;
    }

    // Check if the beats exceeds the maximum amount
    public boolean exceedsBarDuration() throws ParseException{
        int numberOfNotes = parseTimeSignature().getKey();
        int noteType = parseTimeSignature().getValue();
        double barDuration = (4.0 / noteType) * numberOfNotes;
        return getDuration() > barDuration;
    }
}
