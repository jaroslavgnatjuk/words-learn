package com.jaroslavgnatjuk.wordslearn;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.List;

@RestController
@RequestMapping("/api/data")
public class DataController {
    private static String SPREADSHEET_ID = "1aC4Zbx1VaACoY5tHR9rkZSc5PA5k-HmDGm8vwZ8bqyQ";
    private static String KEY_FILE_LOCATION = "words-learn-731e42ed6b13.p12";

    @GetMapping
    public List<List<Object>> getSheetData() throws IOException, GeneralSecurityException, URISyntaxException {
        SheetsQuickStart sheetsQuickStart = new SheetsQuickStart();

        return sheetsQuickStart.getData(SPREADSHEET_ID, "A1:B9999", KEY_FILE_LOCATION);
    }

}
