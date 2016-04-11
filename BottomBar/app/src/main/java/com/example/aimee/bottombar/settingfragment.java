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
public class settingfragment extends PreferenceFragment {
    private EditTextPreference editTextPreference;
    Handler handler;
    @Override
    public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        handler=new Handler();
        Intent i =getActivity().getIntent();
        Bundle b = i.getExtras();
        String str=b.getString("username");
        editTextPreference = (EditTextPreference) findPreference("username");
        editTextPreference.setSummary(str);
        editTextPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
            //    SharedPreferences shp = PreferenceManager.getDefaultSharedPreferences(getActivity());
               final String username=editTextPreference.getEditText().getText().toString();
           /*     new Thread(new Runnable() {
                    @Override
                    public void run() {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {*/
                                editTextPreference.setSummary(username);
                           /* }
                        });
                    }
                }).start();
*/
                return true;//true则把内容更新
            }
        });


        Preference p=findPreference("out");
        p.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                SharedPreferences shp = PreferenceManager.getDefaultSharedPreferences(getActivity());
                SharedPreferences.Editor editor=shp.edit();
                editor.putString("username","请先登录");
                return false;
            }
        });

    }

}
