package in.koost.pfi_tcscc_co_api.controller;

import in.koost.pfi_tcscc_co_api.service.COService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
public class COController {

    @Autowired
    private COService coService;

    @GetMapping("/generateNotices")
    public ResponseEntity<Map<String, Integer>> generatePdf() throws IOException {
        return new ResponseEntity<>(coService.generateNotices(), HttpStatus.CREATED);


    }


}
