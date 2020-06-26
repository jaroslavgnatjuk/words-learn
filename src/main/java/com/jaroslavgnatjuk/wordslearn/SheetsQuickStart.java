package com.jaroslavgnatjuk.wordslearn;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;
import org.springframework.ui.Model;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SheetsQuickStart {
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String APPLICATION_NAME = "words-learn";
    private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS);
    private static String SPREADSHEET_ID = "1aC4Zbx1VaACoY5tHR9rkZSc5PA5k-HmDGm8vwZ8bqyQ";
    private static String KEY_FILE_LOCATION = "words-learn-4829937c2465.json";

    private Sheets service;

    public SheetsQuickStart() {
        final NetHttpTransport HTTP_TRANSPORT;

        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

            service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(KEY_FILE_LOCATION))
                    .setApplicationName(APPLICATION_NAME)
                    .build();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Credential getCredentials(String KEY_FILE_LOCATION) throws IOException, GeneralSecurityException {
        InputStream in = Model.class.getClassLoader().getResourceAsStream(KEY_FILE_LOCATION);

        return GoogleCredential.fromStream(in, GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY).createScoped(SCOPES);
    }

    public List<List<Object>> getData(String range) throws IOException {
        return service.spreadsheets().values()
                .get(SPREADSHEET_ID, range)
                .execute()
                .getValues();
    }

    public void setData(String range, String data) throws IOException {
        List<List<Object>> values = Arrays.asList(
                Arrays.asList(
                        data
                )
        );

        ValueRange body = new ValueRange()
                .setValues(values);

        service.spreadsheets().values().update(SPREADSHEET_ID, range, body)
                .setValueInputOption("RAW")
                .execute();
    }

}
