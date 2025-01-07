package in.koost.pfi_tcscc_co_api.repository;

import in.koost.pfi_tcscc_co_api.model.AppRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRegistrationRepository extends JpaRepository<AppRegistration, Integer> {
    AppRegistration findByCaseNumber(Integer caseNumber);
}
