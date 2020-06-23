package com.jaroslavgnatjuk.wordslearn;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.BatchGetValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class WordsLearnApplicationTests {
    private static String SPREADSHEET_ID = "1aC4Zbx1VaACoY5tHR9rkZSc5PA5k-HmDGm8vwZ8bqyQ";

    @Autowired
    private Sheets sheetsService;

    @Test
    public void readValues() throws IOException {
        ValueRange result = sheetsService.spreadsheets().values()
                .get(SPREADSHEET_ID, "A1:B900").execute();
        int numRows = result.getValues() != null ? result.getValues().size() : 0;

        System.out.println(result.getValues());
        System.out.printf("%d rows retrieved.", numRows);
/*
        List<String> ranges = Arrays.asList("A1");
        BatchGetValuesResponse readResult = sheetsService.spreadsheets().values()
                .batchGet(SPREADSHEET_ID)
                .setRanges(ranges)
                .execute();

        ValueRange januaryTotal = readResult.getValueRanges().get(0);

        System.out.println(januaryTotal);*/
    }


}
