package com.besmainfo.bihealthscan.data;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HealthHistoryManager {
    private static final String PREF_NAME = "health_history";
    private static final String KEY_HISTORY = "health_records";
    private static HealthHistoryManager instance;
    private SharedPreferences prefs;
    private Gson gson;

    public static HealthHistoryManager getInstance(Context context) {
        if (instance == null) {
            instance = new HealthHistoryManager(context);
        }
        return instance;
    }

    private HealthHistoryManager(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    public void saveRecord(HealthRecord record) {
        List<HealthRecord> records = getAllRecords();
        records.add(0, record); // Ajouter au début pour avoir les plus récents en premier
        
        // Limiter à 50 enregistrements maximum
        if (records.size() > 50) {
            records = records.subList(0, 50);
        }
        
        String json = gson.toJson(records);
        prefs.edit().putString(KEY_HISTORY, json).apply();
    }

    public List<HealthRecord> getAllRecords() {
        String json = prefs.getString(KEY_HISTORY, "[]");
        Type type = new TypeToken<List<HealthRecord>>(){}.getType();
        List<HealthRecord> records = gson.fromJson(json, type);
        return records != null ? records : new ArrayList<>();
    }

    // NOUVELLE MÉTHODE : Supprimer un enregistrement par son ID
    public boolean removeRecord(String recordId) {
        List<HealthRecord> records = getAllRecords();
        Iterator<HealthRecord> iterator = records.iterator();
        boolean removed = false;
        
        while (iterator.hasNext()) {
            HealthRecord record = iterator.next();
            if (record.getId().equals(recordId)) {
                iterator.remove();
                removed = true;
                break;
            }
        }
        
        if (removed) {
            String json = gson.toJson(records);
            prefs.edit().putString(KEY_HISTORY, json).apply();
        }
        
        return removed;
    }

    public void clearHistory() {
        prefs.edit().remove(KEY_HISTORY).apply();
    }
}