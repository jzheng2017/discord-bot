package app.models.jokeapi;

public class Flags {
    private boolean nsfw;
    private boolean religious;
    private boolean political;
    private boolean racist;
    private boolean sexist;

    public boolean isNsfw() {
        return nsfw;
    }

    public void setNsfw(boolean nsfw) {
        this.nsfw = nsfw;
    }


    public boolean isReligious() {
        return religious;
    }

    public void setReligious(boolean religious) {
        this.religious = religious;
    }

    public boolean isPolitical() {
        return political;
    }

    public void setPolitical(boolean political) {
        this.political = political;
    }

    public boolean isRacist() {
        return racist;
    }

    public void setRacist(boolean racist) {
        this.racist = racist;
    }

    public boolean isSexist() {
        return sexist;
    }

    public void setSexist(boolean sexist) {
        this.sexist = sexist;
    }
}
