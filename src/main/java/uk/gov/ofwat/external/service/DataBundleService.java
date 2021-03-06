package uk.gov.ofwat.external.service;

import uk.gov.ofwat.external.domain.DataBundle;
import uk.gov.ofwat.external.domain.DataInput;
import uk.gov.ofwat.external.repository.DataBundleRepository;
import uk.gov.ofwat.external.service.dto.DataBundleDTO;
import uk.gov.ofwat.external.service.mapper.DataBundleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;


/**
 * Service Implementation for managing DataBundle.
 */
@Service
@Transactional
public class DataBundleService {

    private final Logger log = LoggerFactory.getLogger(DataBundleService.class);
    private final DataBundleRepository dataBundleRepository;
    private final DataBundleMapper dataBundleMapper;
    private final DataInputService dataInputService;

    public DataBundleService(DataBundleRepository dataBundleRepository, DataBundleMapper dataBundleMapper, DataInputService dataInputService) {
        this.dataBundleRepository = dataBundleRepository;
        this.dataBundleMapper = dataBundleMapper;
        this.dataInputService = dataInputService;
    }

    /**
     * Save a dataBundle.
     *
     * @param dataBundleDTO the entity to save
     * @return the persisted entity
     */
    public DataBundleDTO save(DataBundleDTO dataBundleDTO) {
        log.debug("Request to save DataBundle : {}", dataBundleDTO);
        DataBundle dataBundle = dataBundleMapper.toEntity(dataBundleDTO);
        dataBundle = dataBundleRepository.save(dataBundle);
        return dataBundleMapper.toDto(dataBundle);
    }

    /**
     *  Get all the dataBundles.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<DataBundleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DataBundles");
        return dataBundleRepository.findAll(pageable).map(dataBundleMapper::toDto);
    }

    /**
     *  Get one dataBundle by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public DataBundleDTO findOne(Long id) {
        log.debug("Request to get DataBundle : {}", id);
        DataBundle dataBundle = dataBundleRepository.findOne(id);
        return dataBundleMapper.toDto(dataBundle);
    }

    /**
     *  Delete the  dataBundle by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete DataBundle : {}", id);
        DataBundle dataBundleToDelete = dataBundleRepository.findOne(id);
        decrementHigherOrderIndexes(dataBundleToDelete);
        deleteDataInputs(dataBundleToDelete);
        dataBundleRepository.delete(id);
    }

    private void decrementHigherOrderIndexes(DataBundle dataBundleToDelete) {
        List<DataBundle> allDataBundles = dataBundleRepository.findAll();
        for (DataBundle db : allDataBundles) {
            if (db.getDataCollection().getId() == dataBundleToDelete.getDataCollection().getId() &&
                db.getOrderIndex() > dataBundleToDelete.getOrderIndex()) {
                dataBundleRepository.updateOrderIndexForId(db.getOrderIndex()-1, db.getId());
            }
        }
    }

    private void deleteDataInputs(DataBundle dataBundle) {
        for (DataInput di : dataBundle.getDataInputs()) {
            dataInputService.delete(di.getId());
        }
    }

    public Long getMaxOrderIndex(Long dataCollectionId) {
        log.debug("Request to get Max OrderIndex from DataBundle");
        return dataBundleRepository.getMaxOrderIndex(dataCollectionId);
    }


}
