package objects;

public class Note {
    private String name;
    private int pitch;
    private int duration;

    public Note(String name, int pitch, int duration) {
        this.name = name;
        this.pitch = pitch;
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPitch() {
        return pitch;
    }

    public void setPitch(int pitch) {
        this.pitch = pitch;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
