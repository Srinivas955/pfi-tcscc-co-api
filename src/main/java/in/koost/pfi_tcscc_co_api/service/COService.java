package in.koost.pfi_tcscc_co_api.service;

import org.springframework.boot.autoconfigure.mail.MailProperties;

import java.io.IOException;
import java.util.Map;

public interface COService {

    public Map<String, Integer> generateNotices() throws IOException;
}
