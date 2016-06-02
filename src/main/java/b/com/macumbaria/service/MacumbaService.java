package b.com.macumbaria.service;

import b.com.macumbaria.domain.Macumba;
import b.com.macumbaria.repository.MacumbaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Macumba.
 */
@Service
@Transactional
public class MacumbaService {

    private final Logger log = LoggerFactory.getLogger(MacumbaService.class);
    
    @Inject
    private MacumbaRepository macumbaRepository;
    
    /**
     * Save a macumba.
     * @return the persisted entity
     */
    public Macumba save(Macumba macumba) {
        log.debug("Request to save Macumba : {}", macumba);
        Macumba result = macumbaRepository.save(macumba);
        return result;
    }

    /**
     *  get all the macumbas.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Macumba> findAll(Pageable pageable) {
        log.debug("Request to get all Macumbas");
        Page<Macumba> result = macumbaRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one macumba by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Macumba findOne(Long id) {
        log.debug("Request to get Macumba : {}", id);
        Macumba macumba = macumbaRepository.findOne(id);
        return macumba;
    }

    /**
     *  delete the  macumba by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Macumba : {}", id);
        macumbaRepository.delete(id);
    }
}
