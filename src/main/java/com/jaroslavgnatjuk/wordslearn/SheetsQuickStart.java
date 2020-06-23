package com.jaroslavgnatjuk.wordslearn;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import org.apache.logging.log4j.LogManager;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

public class SheetsQuickStart {
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String SERVICE_ACCOUNT_EMAIL = "words-learn@words-learn.iam.gserviceaccount.com";
    private static final String APPLICATION_NAME = "words-learn";
    private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS);
    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(SheetsQuickStart.class.getName());

    private Credential getCredentials(String KEY_FILE_LOCATION) throws URISyntaxException, IOException, GeneralSecurityException {
        URL fileURL = SheetsQuickStart.class.getClassLoader().getResource(KEY_FILE_LOCATION);
        if (fileURL == null) {
            fileURL = (new File(KEY_FILE_LOCATION)).toURI().toURL();
        }

        HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        return new GoogleCredential.Builder()
                .setTransport(httpTransport)
                .setJsonFactory(JSON_FACTORY)
                .setServiceAccountId(SERVICE_ACCOUNT_EMAIL)
                .setServiceAccountPrivateKeyFromP12File(new File(fileURL.toURI()))
                .setServiceAccountScopes(SCOPES)
                .build();
    }

    public List<List<Object>> getData(String spreadsheetId, String range, String KEY_FILE_LOCATION) throws IOException, GeneralSecurityException, URISyntaxException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(KEY_FILE_LOCATION))
                .setApplicationName(APPLICATION_NAME)
                .build();

        return service.spreadsheets().values()
                .get(spreadsheetId, range)
                .execute()
                .getValues();
    }
}