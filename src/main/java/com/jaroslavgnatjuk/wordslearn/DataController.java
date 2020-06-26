package com.jaroslavgnatjuk.wordslearn;

import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/data")
public class DataController {

    private SheetsQuickStart sheetsQuickStart = new SheetsQuickStart();

    @GetMapping
    public List<List<Object>> getSheetData() throws IOException {
        return sheetsQuickStart.getData("A1:C9999");
    }

    @PutMapping
    public void setSheetData(@RequestParam("range") String range,
                             @RequestParam("data") String data) throws IOException {
        sheetsQuickStart.setData(range, data);
    }

}
