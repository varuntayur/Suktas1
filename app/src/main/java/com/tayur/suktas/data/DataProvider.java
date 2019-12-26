package com.tayur.suktas.data;

import android.content.res.AssetManager;
import android.util.Log;

import com.google.gson.Gson;
import com.tayur.suktas.R;
import com.tayur.suktas.data.model.Sukta;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by vtayur on 8/19/2014.
 */
public class DataProvider {

    private static final String TAG = "DataProvider";

    public static final String PREFS_NAME = "Sukta";
    public static final String SHLOKA_DISP_LANGUAGE = "localLanguage";
    public static final String LEARNING_MODE = "learningMode";
    public static final String REPEAT_SHLOKA = "repeatShlokaCount";
    public static final String REPEAT_SHLOKA_DEFAULT = "3";

    private static final Gson gson = new Gson();
    // map data from a language -> list-of-suktas {durga-sukta, narayana-sukta etc.}
    private static final Map<String, List<Sukta>> lang2Sukta = new ConcurrentHashMap<>();


    private static List<Integer> mBackgroundColors = new ArrayList<Integer>() {
        {
            add(R.color.orange);
            add(R.color.green);
            add(R.color.blue);
            add(R.color.yellow);
            add(R.color.grey);
            add(R.color.lblue);
            add(R.color.slateblue);
            add(R.color.cyan);
            add(R.color.silver);
        }
    };

    public static List<Integer> getBackgroundColorList() {
        return Collections.unmodifiableList(mBackgroundColors);
    }

    public static List<String> getMenuNames() {

        List<String> menuNames = new ArrayList<>();

        String anyResource = lang2Sukta.keySet().iterator().next();

        List<Sukta> suktas = DataProvider.getSukta(Language.getLanguageEnum(anyResource));

        for (Sukta sukta : suktas) {
            menuNames.addAll(sukta.getSectionNames());
        }
        menuNames.remove("Introduction");
        menuNames.add(0, "Introduction");
        return menuNames;
    }

    public static int getBackgroundColor(int location) {
        return mBackgroundColors.get(location % mBackgroundColors.size());
    }

    public static void init(AssetManager am) {

        loadSukta(am, "db/durga_eng.json");
        loadSukta(am, "db/durga_kan.json");
        loadSukta(am, "db/durga_san.json");

        loadSukta(am, "db/introduction_eng.json");
        loadSukta(am, "db/introduction_kan.json");
        loadSukta(am, "db/introduction_san.json");

        loadSukta(am, "db/narayana_eng.json");
        loadSukta(am, "db/narayana_kan.json");
        loadSukta(am, "db/narayana_san.json");

        loadSukta(am, "db/purusha_eng.json");
        loadSukta(am, "db/purusha_kan.json");
        loadSukta(am, "db/purusha_san.json");

        loadSukta(am, "db/srisukta_eng.json");
        loadSukta(am, "db/srisukta_kan.json");
        loadSukta(am, "db/srisukta_san.json");

    }

    private static void loadSukta(AssetManager am, String srcPath) {

        try {

            InputStream open = am.open(srcPath);

            Sukta sukta = gson.fromJson(new BufferedReader(new InputStreamReader(open, "UTF-8")),
                    Sukta.class);

            if (lang2Sukta.containsKey(sukta.getLang())) { // entry already exists in the map

                List<Sukta> existingSuktas = lang2Sukta.get(sukta.getLang());

                //validate before adding
                Optional<Sukta> existingSukta = getSukta(Language.getLanguageEnum(sukta.getLang()), sukta.getSectionNames().stream().findFirst().get());

                if (!existingSukta.isPresent()) {
                    existingSuktas.add(sukta);
                }

            } else {

                List<Sukta> existingSuktas = new ArrayList<>();

                existingSuktas.add(sukta);

                lang2Sukta.put(sukta.getLang(), existingSuktas);
            }

            Log.d(TAG, String.format("* Finished de-serializing the file - %s *", srcPath));

        } catch (IOException e) {
            Log.e(TAG, String.format("* Error while de-serializing the file - %s *", srcPath), e);
        }
    }

    public static List<Sukta> getSukta(Language lang) {
        return lang2Sukta.get(lang.toString());
    }

    public static Optional<Sukta> getSukta(Language lang, String suktaName) {
        List<Sukta> allSuktas = lang2Sukta.get(lang.toString());

//        return allSuktas.stream().filter(sukta -> sukta.getSectionNames().contains(suktaName)).findFirst();

        for (Sukta sukta : allSuktas) {
            if (sukta.getSectionNames().contains(suktaName))
                return Optional.of(sukta);
        }
        return Optional.empty();
    }


}
