package com.example.aimee.bottombar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

/**
 * Created by Aimee on 2016/4/6.
 */
//应该可以了，nickname随着更改而变化
public class settingfragment extends PreferenceFragment {
    private EditTextPreference editTextPreference;
    Handler handler;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        handler = new Handler();
        Intent i = getActivity().getIntent();
        Bundle b = i.getExtras();
        String str = b.getString("user_nickname");
        editTextPreference = (EditTextPreference) findPreference("user_nickname");
        editTextPreference.setSummary(str);
        editTextPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                //    SharedPreferences shp = PreferenceManager.getDefaultSharedPreferences(getActivity());
                final String user_nickname = editTextPreference.getEditText().getText().toString();
                editTextPreference.setSummary(user_nickname);
                return true;//true则把内容更新
            }
        });
        Preference p=findPreference("out");
        p.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                SharedPreferences shp = PreferenceManager.getDefaultSharedPreferences(getActivity());
                SharedPreferences.Editor editor=shp.edit();
                editor.putString("user_nickname","请先登录").commit();
                getActivity().onBackPressed();
                return false;
            }
        });

    }

}
