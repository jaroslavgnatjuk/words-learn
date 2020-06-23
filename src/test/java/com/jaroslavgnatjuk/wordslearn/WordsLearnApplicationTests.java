package com.jaroslavgnatjuk.wordslearn;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.BatchGetValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class WordsLearnApplicationTests {
    private final String GOOGLE_SHEETS = "google_sheets";
    private String KEY_FILE_LOCATION = "words-learn-731e42ed6b13.p12";
    private static String SPREADSHEET_ID = "1aC4Zbx1VaACoY5tHR9rkZSc5PA5k-HmDGm8vwZ8bqyQ";

    @Test
    public void getSheetData() throws IOException, GeneralSecurityException, URISyntaxException {
        SheetsQuickStart sheetsQuickStart = new SheetsQuickStart();
        List rows = sheetsQuickStart.getData(SPREADSHEET_ID, "A1:B9999", KEY_FILE_LOCATION);

        System.out.println(rows);

    }

}
