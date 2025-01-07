package in.koost.pfi_tcscc_co_api.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class COTriggers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer coTrgId;
    private Integer caseNumber;
    @Lob
    private byte[] coPDF;
    private String trgStatus;
}
