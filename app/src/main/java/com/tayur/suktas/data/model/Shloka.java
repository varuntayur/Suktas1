package com.tayur.suktas.data.model;

import com.google.gson.annotations.SerializedName;

import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * Created by varuntayur on 4/5/2014.
 */
@Root
public class Shloka implements Serializable {


    public static final String EMPTY_STRING = "";

    @SerializedName("num")
    private String num;

    @SerializedName("text")
    private String text;

    @SerializedName("explanation")
    private ShlokaDescription explanation;

    public Shloka() {
    }

    @Override
    public String toString() {
        return "Shloka{" +
                "num='" + num + '\'' +
                ", text='" + text + '\'' +
                ", explanation=" + explanation +
                '}';
    }

    public String getText() {
        if (text != null) {

            StringBuilder sbuilder = new StringBuilder();
            String[] split = text.split("\n");
            for (String splitStr : split) {
                sbuilder.append(splitStr.trim());
                sbuilder.append("\n");
            }
            return sbuilder.toString().trim();
        }
        return EMPTY_STRING;
    }


    public String getFormattedExplanation() {

        StringBuilder stringBuilder = new StringBuilder();
        for (Note note : explanation.getNotesList()) {
            stringBuilder.append(note.getFormattedNote());
        }
        return stringBuilder.toString();
    }

}
