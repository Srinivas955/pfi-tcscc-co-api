package in.koost.pfi_tcscc_co_api.repository;

import in.koost.pfi_tcscc_co_api.model.EDModule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EDModuleRepository extends JpaRepository<EDModule, Integer> {

    EDModule findByCaseNumber(Integer caseNumber);

}


