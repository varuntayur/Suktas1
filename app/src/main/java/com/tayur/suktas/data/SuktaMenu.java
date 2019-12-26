package com.tayur.suktas.data;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.tayur.suktas.data.model.Section;
import com.tayur.suktas.detail.common.BundleArgs;
import com.tayur.suktas.detail.stotra.StotraInOnePageActivity;
import com.tayur.suktas.detail.stotra.StotraSlideBrowseActivity;

import java.io.Serializable;
import java.util.Optional;

/**
 * Created by varuntayur on 6/21/2014.
 */
public enum SuktaMenu {

    DEFAULT("Default") {
        @Override
        public void execute(Activity activity, String item, int position, Language language) {
            Intent intent = null;
            SharedPreferences settings = activity.getSharedPreferences(DataProvider.PREFS_NAME, 0);

            String learningMode = settings.getString(DataProvider.LEARNING_MODE, "");

            if (learningMode.equalsIgnoreCase(YesNo.yes.toString()))
                intent = new Intent(activity, StotraSlideBrowseActivity.class);
            else
                intent = new Intent(activity, StotraInOnePageActivity.class);

            Optional<Section> secEngStotra = DataProvider.getSukta(Language.eng, item).map(sukta -> sukta.getSection(item));

            Optional<Section> secLocalLangStotra = DataProvider.getSukta(language, item).map(sukta -> sukta.getSection(item));

            intent.putExtra(BundleArgs.SECTION_NAME, item);
            intent.putExtra(BundleArgs.PAGE_NUMBER, position);
            intent.putExtra(BundleArgs.ENG_SHLOKA_LIST, (Serializable) secEngStotra.orElseThrow(IllegalStateException::new).getShlokaList());
            intent.putExtra(BundleArgs.LOCAL_LANG_SHLOKA_LIST, (Serializable) secLocalLangStotra.orElseThrow(IllegalStateException::new).getShlokaList());

            Log.d(TAG, "Starting activity with english and " + language);
            activity.startActivity(intent);
        }
    };

    private static final String TAG = "Sukta";
    private String menuDisplayName;

    SuktaMenu(String menu) {
        this.menuDisplayName = menu;
    }

    public static SuktaMenu getEnum(String item) {
        for (SuktaMenu v : values())
            if (v.toString().equalsIgnoreCase(item)) return v;
        return SuktaMenu.DEFAULT;
    }

    @Override
    public String toString() {
        return menuDisplayName;
    }

    public abstract void execute(Activity activity, String item, int position, Language language);
}
