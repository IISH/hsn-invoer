package org.iish.hsn.invoer.service;

import org.iish.hsn.invoer.domain.invoer.WorkOrder;
import org.iish.hsn.invoer.domain.invoer.pick.*;
import org.iish.hsn.invoer.repository.invoer.pick.*;
import org.iish.hsn.invoer.util.InputMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PicklistService {
    @Autowired private InputMetadata         inputMetadata;
    @Autowired private PlaatsRepository      plaatsRepository;
    @Autowired private BeroepRepository      beroepRepository;
    @Autowired private RelatieRepository     relatieRepository;
    @Autowired private KindRelatieRepository kindRelatieRepository;
    @Autowired private KgRepository          kgRepository;

    public void setPlaats(String plaats) {
        WorkOrder workOrder = inputMetadata.getWorkOrder();
        List<Plaats> plaatsEntities = plaatsRepository.findByGemnaam(plaats.trim(), WorkOrder.EMPTY_WORKORDER, workOrder);
        if (plaatsEntities.isEmpty()) {
            Plaats plaatsEntity = new Plaats();
            plaatsEntity.setGemnaam(plaats.trim());
            plaatsEntity.setNwinlst("j");
            plaatsEntity.setRegio("");
            plaatsEntity.setWorkOrder(workOrder);

            plaatsRepository.save(plaatsEntity);
        }
    }

    public void setBeroep(String beroep) {
        WorkOrder workOrder = inputMetadata.getWorkOrder();
        List<Beroep> beroepEntities = beroepRepository.findByBerpnaam(beroep.trim(), WorkOrder.EMPTY_WORKORDER, workOrder);
        if (beroepEntities.isEmpty()) {
            Beroep beroepEntity = new Beroep();
            beroepEntity.setBerpnaam(beroep.trim());
            beroepEntity.setNwinlst("j");
            beroepEntity.setWorkOrder(workOrder);

            beroepRepository.save(beroepEntity);
        }
    }

    public void setRelatie(String relatie) {
        WorkOrder workOrder = inputMetadata.getWorkOrder();
        List<Relatie> relatieEntities = relatieRepository.findByRelatie(relatie.trim(), WorkOrder.EMPTY_WORKORDER, workOrder);
        if (relatieEntities.isEmpty()) {
            Relatie relatieEntity = new Relatie();
            relatieEntity.setRelatie(relatie.trim());
            relatieEntity.setNwinlst("j");
            relatieEntity.setWorkOrder(workOrder);

            relatieRepository.save(relatieEntity);
        }
    }

    public void setKindRelatie(String relatie) {
        WorkOrder workOrder = inputMetadata.getWorkOrder();
        List<KindRelatie> kindRelatieEntities =
                kindRelatieRepository.findByRelatie(relatie.trim(), WorkOrder.EMPTY_WORKORDER, workOrder);
        if (kindRelatieEntities.isEmpty()) {
            KindRelatie kindRelatieEntity = new KindRelatie();
            kindRelatieEntity.setRelatie(relatie.trim());
            kindRelatieEntity.setNwinlst("j");
            kindRelatieEntity.setWorkOrder(workOrder);

            kindRelatieRepository.save(kindRelatieEntity);
        }
    }

    public void setKg(String kg) {
        WorkOrder workOrder = inputMetadata.getWorkOrder();
        List<Kg> kgEntities = kgRepository.findByKerkgeno(kg.trim(), WorkOrder.EMPTY_WORKORDER, workOrder);
        if (kgEntities.isEmpty()) {
            Kg kgEntity = new Kg();
            kgEntity.setKerkgeno(kg.trim());
            kgEntity.setNwinlst("j");
            kgEntity.setWorkOrder(workOrder);

            kgRepository.save(kgEntity);
        }
    }
}
