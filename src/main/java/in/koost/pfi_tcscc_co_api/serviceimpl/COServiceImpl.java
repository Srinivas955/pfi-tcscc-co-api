package in.koost.pfi_tcscc_co_api.serviceimpl;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.*;
import in.koost.pfi_tcscc_co_api.model.AppRegistration;
import in.koost.pfi_tcscc_co_api.model.COTriggers;
import in.koost.pfi_tcscc_co_api.model.EDModule;
import in.koost.pfi_tcscc_co_api.repository.AppRegistrationRepository;
import in.koost.pfi_tcscc_co_api.repository.COTriggersRepository;
import in.koost.pfi_tcscc_co_api.repository.EDModuleRepository;
import in.koost.pfi_tcscc_co_api.service.COService;
import in.koost.pfi_tcscc_co_api.utils.EmailSender;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class COServiceImpl implements COService {

    @Autowired
    private COTriggersRepository coTriggersRepository;

    @Autowired
    private EDModuleRepository edModuleRepository;

    @Autowired
    AppRegistrationRepository appRegistrationRepository;

    @Autowired
    private EmailSender emailSender;

    @Override
    public Map<String, Integer> generateNotices() throws IOException {

        Integer successCount = 0;
        Integer failedCount = 0;
        Integer totalCount = 0;

        List<COTriggers> coTriggersList = coTriggersRepository.findByTrgStatus("Pending");
        Map<String, Integer> notices = new HashMap<>();
        for (COTriggers coTriggers : coTriggersList) {
            Integer caseNumber = coTriggers.getCaseNumber();
            try {

                EDModule applicant = edModuleRepository.findByCaseNumber(coTriggers.getCaseNumber());
                AppRegistration appEntity = appRegistrationRepository.findByCaseNumber(caseNumber);
                String email = appEntity.getEmail();

                File file = new File(applicant.getCaseNumber() + ".pdf");
                generatePdf(applicant, file);


                String subject = "Eligibility details";
                String body = "";
                String fileToAttach = "";
                emailSender.sendMailWithAttachment(email, subject, body, fileToAttach);

                byte[] fileData = new byte[1024];
                FileInputStream fis = new FileInputStream(file);
                fis.read(fileData);
                coTriggers.setCoPDF(fileData);
                coTriggers.setTrgStatus("Completed");
                coTriggersRepository.save(coTriggers);

                fis.close();
                successCount++;

            }catch (Exception e) {
                e.printStackTrace();
                failedCount++;
            }
        }

        notices.put("Success Count", successCount);
        notices.put("Failed Count", failedCount);
        notices.put("Total Count", totalCount);
        return notices;
    }


    private void generatePdf(EDModule edModule, File file) throws IOException {

        FileOutputStream fos = new FileOutputStream(new File(edModule.getCaseNumber() + ".pdf"));
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, fos);
        document.open();

        Font font = FontFactory.getFont(FontFactory.HELVETICA, 18, Font.BOLD);
        font.setColor(Color.blue);

        Paragraph paragraph = new Paragraph("List of the Plans", font);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(paragraph);

        PdfPTable table = new PdfPTable(8);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {1.5f, 1.5f, 2.0f, 3.0f, 2.0f, 2.0f, 3.0f, 2.0f});
        table.setSpacingBefore(10);

        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.magenta);
        cell.setPadding(5);

        Font font1 = FontFactory.getFont(FontFactory.HELVETICA, 18, Font.BOLD);
        font.setColor(Color.black);

        cell.setPhrase(new Phrase("Holder Name", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Holder SSN", font));
        table.addCell(cell);


        cell.setPhrase(new Phrase("Plan Name", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Plan Status", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Plan Start Date", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Plan End Date", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Benefit Amount", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Denial Reason", font));
        table.addCell(cell);

        table.addCell(edModule.getHolderName());
        table.addCell(edModule.getHolderSSN());
        table.addCell(edModule.getPlanName());
        table.addCell(edModule.getPlanStatus());
        table.addCell(String.valueOf(edModule.getPlanStartDate()));
        table.addCell(String.valueOf(edModule.getPlanEndDate()));
        table.addCell(String.valueOf(edModule.getBenefitAmount()));
        table.addCell(edModule.getDenialReason());


    document.add(table);
    document.close();
    }
}
