package com.rjesture.startupkit.database;

import android.app.Activity;

public final class AppSettings extends OSettings {
    public static final String PREFS_MAIN_FILE = "PREFS_Thanks_FILE";
    public static final String loginPath = "loginPath";
    public static final String logRole = "logRole";


    public AppSettings(Activity mActivity) {
        super(mActivity);
    }
}
