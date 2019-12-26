package com.tayur.suktas.data.model;

import com.google.gson.annotations.SerializedName;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by varuntayur on 4/5/2014.
 */
@Root
public class Section implements Serializable {


    @SerializedName("name")
    private String name;

    @SerializedName("shlokaList")
    private List<Shloka> shlokaList;

    @SerializedName("num")
    private String num;

    public Section() {
    }

    @Override
    public String toString() {
        return "Section{" +
                "name='" + name + '\'' +
                ", shlokaList=" + shlokaList +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Shloka> getShlokaList() {
        return shlokaList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Section section = (Section) o;

        if (name != null ? !name.equals(section.name) : section.name != null) return false;
        return num != null ? num.equals(section.num) : section.num == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (num != null ? num.hashCode() : 0);
        return result;
    }
}
