package in.koost.pfi_tcscc_co_api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
//@Table(name = "AR_APP_REG")
public class AppRegistration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer caseNumber;
    private String fullname;
    private String email;
    private String phone;
    private String dob;
    private String gender;
    private String ssn;
    private Integer age;




}
