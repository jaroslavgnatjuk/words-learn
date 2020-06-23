package com.jaroslavgnatjuk.wordslearn;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

import static com.jaroslavgnatjuk.wordslearn.SheetsServiceUtil.SPREADSHEET_ID;

@RestController
@RequestMapping("/api/data")
public class DataController {

    @Autowired
    private Sheets sheetsService;

    @GetMapping
    public List<List<Object>> getData() throws IOException {
        ValueRange result = sheetsService.spreadsheets().values()
                .get(SPREADSHEET_ID, "A1:B9999").execute();

        return result.getValues();
    }

}
