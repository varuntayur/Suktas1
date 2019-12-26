package com.tayur.suktas.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by varuntayur on 7/7/2014.
 */
public class ShlokaDescription implements Serializable {

    @SerializedName("notesList")
    private List<Note> notesList;

    public List<Note> getNotesList() {
        return notesList;
    }

    @Override
    public String toString() {
        return "ShlokaDescription{" +
                "notesList=" + notesList +
                '}';
    }
}
