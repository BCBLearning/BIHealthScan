package com.besmainfo.bihealthscan.data;

import android.content.Context;
import android.os.Environment;
import android.os.Bundle;
import android.print.PrintManager;
import android.util.Log;
import com.besmainfo.bihealthscan.data.HealthRecord;
import com.besmainfo.bihealthscan.utils.AnalyticsManager;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DataExporter {
    private static final String TAG = "DataExporter";
    
    public static File exportToCSV(Context context, List<HealthRecord> records) {
        try {
            // 🆕 Log analytics - Début export
            AnalyticsManager analyticsManager = AnalyticsManager.getInstance(context);
            Bundle params = new Bundle();
            params.putString("format", "csv");
            params.putInt("record_count", records.size());
            analyticsManager.logEvent("export_started", params);
            
            long startTime = System.currentTimeMillis();
            
            // Créer le dossier d'export si nécessaire
            File exportDir = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "BIHealthScan/Exports");
            if (!exportDir.exists()) {
                exportDir.mkdirs();
            }
            
            // Créer le fichier avec timestamp
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
            File csvFile = new File(exportDir, "health_data_" + timestamp + ".csv");
            
            FileWriter writer = new FileWriter(csvFile);
            
            // En-tête CSV
            writer.write("Date,Type d'analyse,Condition,Confiance,Recommandation,Emoji,Rythme cardiaque,Statut cardiaque\n");
            
            // Données
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
            for (HealthRecord record : records) {
                writer.write(String.format(Locale.getDefault(), "\"%s\",\"%s\",\"%s\",%.2f,\"%s\",\"%s\",%s,\"%s\"\n",
                    dateFormat.format(record.getDate()),
                    record.getAnalysisType(),
                    record.getCondition(),
                    record.getConfidence(),
                    record.getRecommendation().replace("\"", "\"\""),
                    record.getEmoji(),
                    record.getHeartRate() != null ? record.getHeartRate().toString() : "N/A",
                    record.getHeartRateStatus() != null ? record.getHeartRateStatus() : "N/A"
                ));
            }
            
            writer.flush();
            writer.close();
            
            long exportTime = System.currentTimeMillis() - startTime;
            
            // 🆕 Log analytics - Performance d'export
            analyticsManager.logPerformanceMetric(context, "csv_export_time", exportTime);
            
            Log.d(TAG, "CSV exporté: " + csvFile.getAbsolutePath());
            return csvFile;
            
        } catch (IOException e) {
            // 🆕 Log analytics - Erreur d'export
            AnalyticsManager analyticsManager = AnalyticsManager.getInstance(context);
            analyticsManager.logError(context, "csv_export_error", e.getMessage());
            
            Log.e(TAG, "Erreur export CSV", e);
            return null;
        }
    }
    
    public static File exportToPDF(Context context, List<HealthRecord> records) {
        try {
            // 🆕 Log analytics - Début export
            AnalyticsManager analyticsManager = AnalyticsManager.getInstance(context);
            Bundle params = new Bundle();
            params.putString("format", "pdf");
            params.putInt("record_count", records.size());
            analyticsManager.logEvent("export_started", params);
            
            long startTime = System.currentTimeMillis();
            
            File exportDir = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "BIHealthScan/Exports");
            if (!exportDir.exists()) {
                exportDir.mkdirs();
            }
            
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
            File pdfFile = new File(exportDir, "health_report_" + timestamp + ".html"); // HTML pour simuler PDF
            
            FileWriter writer = new FileWriter(pdfFile);
            
            // Générer un rapport HTML formaté (simulation PDF)
            writer.write(generateHTMLReport(records));
            
            writer.flush();
            writer.close();
            
            long exportTime = System.currentTimeMillis() - startTime;
            
            // 🆕 Log analytics - Performance d'export
            analyticsManager.logPerformanceMetric(context, "pdf_export_time", exportTime);
            
            Log.d(TAG, "Rapport généré: " + pdfFile.getAbsolutePath());
            return pdfFile;
            
        } catch (IOException e) {
            // 🆕 Log analytics - Erreur d'export
            AnalyticsManager analyticsManager = AnalyticsManager.getInstance(context);
            analyticsManager.logError(context, "pdf_export_error", e.getMessage());
            
            Log.e(TAG, "Erreur génération rapport", e);
            return null;
        }
    }
    
    private static String generateHTMLReport(List<HealthRecord> records) {
        StringBuilder html = new StringBuilder();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        SimpleDateFormat reportDateFormat = new SimpleDateFormat("dd/MM/yyyy à HH:mm", Locale.getDefault());
        
        html.append("<!DOCTYPE html>")
            .append("<html><head>")
            .append("<meta charset=\"UTF-8\">")
            .append("<title>Rapport Santé BIHealthScan</title>")
            .append("<style>")
            .append("body { font-family: Arial, sans-serif; margin: 20px; }")
            .append(".header { text-align: center; border-bottom: 2px solid #0B63D6; padding-bottom: 10px; }")
            .append(".record { border: 1px solid #ddd; margin: 10px 0; padding: 15px; border-radius: 8px; }")
            .append(".normal { background-color: #E8F5E8; }")
            .append(".warning { background-color: #FFF9E6; }")
            .append(".alert { background-color: #FFEBEE; }")
            .append(".emoji { font-size: 24px; }")
            .append("</style>")
            .append("</head><body>")
            .append("<div class=\"header\">")
            .append("<h1>🏥 BIHealthScan - Rapport Santé</h1>")
            .append("<p>Généré le ").append(reportDateFormat.format(new Date())).append("</p>")
            .append("<p>Total d'analyses: ").append(records.size()).append("</p>")
            .append("</div>");
        
        for (HealthRecord record : records) {
            String recordClass = "normal";
            if (record.getEmoji().equals("🟡")) recordClass = "warning";
            else if (record.getEmoji().equals("🔴")) recordClass = "alert";
            
            html.append("<div class=\"record ").append(recordClass).append("\">")
                .append("<div class=\"emoji\">").append(record.getEmoji()).append("</div>")
                .append("<h3>").append(record.getAnalysisType()).append("</h3>")
                .append("<p><strong>Date:</strong> ").append(dateFormat.format(record.getDate())).append("</p>")
                .append("<p><strong>Condition:</strong> ").append(record.getCondition()).append("</p>");
            
            if (record.isHeartRateRecord()) {
                html.append("<p><strong>Rythme cardiaque:</strong> ").append(record.getHeartRate()).append(" BPM</p>")
                    .append("<p><strong>Statut:</strong> ").append(record.getHeartRateStatus()).append("</p>");
            } else {
                html.append("<p><strong>Confiance:</strong> ").append(String.format("%.0f%%", record.getConfidence() * 100)).append("</p>");
            }
            
            html.append("<p><strong>Recommandation:</strong><br>").append(record.getRecommendation().replace("\n", "<br>")).append("</p>")
                .append("</div>");
        }
        
        html.append("<div style=\"margin-top: 30px; padding: 15px; background: #f5f5f5; border-radius: 8px;\">")
            .append("<p><strong>Note importante:</strong> Ce rapport est généré par l'application BIHealthScan et ne remplace pas un avis médical professionnel. Consultez toujours un médecin pour des problèmes de santé.</p>")
            .append("</div>")
            .append("</body></html>");
        
        return html.toString();
    }
}