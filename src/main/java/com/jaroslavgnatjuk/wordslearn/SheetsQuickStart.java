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
import org.springframework.ui.Model;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;

public class SheetsQuickStart {
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String APPLICATION_NAME = "words-learn";
    private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS);

    private Credential getCredentials(String KEY_FILE_LOCATION) throws IOException, GeneralSecurityException {
        InputStream in = Model.class.getClassLoader().getResourceAsStream(KEY_FILE_LOCATION);

        return GoogleCredential.fromStream(in, GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY).createScoped(SCOPES);
    }

    public List<List<Object>> getData(String spreadsheetId, String range, String KEY_FILE_LOCATION) throws IOException, GeneralSecurityException {
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