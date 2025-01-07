package in.koost.pfi_tcscc_co_api.repository;

import in.koost.pfi_tcscc_co_api.model.COTriggers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface COTriggersRepository extends JpaRepository<COTriggers, Integer> {

    List<COTriggers> findByTrgStatus(String status);
}
