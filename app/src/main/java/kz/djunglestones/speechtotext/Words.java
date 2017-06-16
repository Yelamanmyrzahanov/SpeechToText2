package kz.djunglestones.speechtotext;

/**
 * Created by yelaman on 07.06.17.
 */

public class Words {
    private int id;
    private String word="";

    public Words(int id, String word) {
        this.id = id;
        this.word = word;
    }

    public Words(){
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }


}
