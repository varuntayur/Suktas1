package com.tayur.suktas.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vtayur on 8/19/2014.
 */
public class Sukta implements Serializable {

    @SerializedName("lang")
    private String lang;

    @SerializedName("sections")
    List<com.tayur.suktas.data.model.Section> sections;

    private Map<String, com.tayur.suktas.data.model.Section> mapSecName2Sec = new LinkedHashMap<String, Section>();

    public Sukta() {
    }

    @Override
    public String toString() {
        return "Sukta{" +
                "sections=" + sections +
                '}';
    }


    public Section getSection(String sectionName) {

        buildMap();

        return mapSecName2Sec.get(sectionName);
    }

    public Collection<String> getSectionNames() {

        buildMap();

        return mapSecName2Sec.keySet();
    }

    public String getLang() {
        return lang;
    }


    private void buildMap() {
        if (mapSecName2Sec.keySet().isEmpty())
            for (Section section : sections) {
                mapSecName2Sec.put(section.getName(), section);
            }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sukta sukta = (Sukta) o;

        if (lang != null ? !lang.equals(sukta.lang) : sukta.lang != null) return false;
        return sections != null ? sections.equals(sukta.sections) : sukta.sections == null;
    }

    @Override
    public int hashCode() {
        int result = lang != null ? lang.hashCode() : 0;
        result = 31 * result + (sections != null ? sections.hashCode() : 0);
        return result;
    }
}
