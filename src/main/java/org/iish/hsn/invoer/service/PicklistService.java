package org.iish.hsn.invoer.service;

import org.iish.hsn.invoer.domain.invoer.WorkOrder;
import org.iish.hsn.invoer.domain.invoer.pick.Beroep;
import org.iish.hsn.invoer.domain.invoer.pick.Kg;
import org.iish.hsn.invoer.domain.invoer.pick.Plaats;
import org.iish.hsn.invoer.domain.invoer.pick.Relatie;
import org.iish.hsn.invoer.repository.invoer.pick.BeroepRepository;
import org.iish.hsn.invoer.repository.invoer.pick.KgRepository;
import org.iish.hsn.invoer.repository.invoer.pick.PlaatsRepository;
import org.iish.hsn.invoer.repository.invoer.pick.RelatieRepository;
import org.iish.hsn.invoer.util.InputMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PicklistService {
    @Autowired private InputMetadata     inputMetadata;
    @Autowired private PlaatsRepository  plaatsRepository;
    @Autowired private BeroepRepository  beroepRepository;
    @Autowired private RelatieRepository relatieRepository;
    @Autowired private KgRepository      kgRepository;

    public void setPlaats(String plaats) {
        WorkOrder workOrder = inputMetadata.getWorkOrder();
        Plaats plaatsEntity = plaatsRepository.findByGemnaam(plaats.trim(), WorkOrder.EMPTY_WORKORDER, workOrder);
        if (plaatsEntity == null) {
            plaatsEntity = new Plaats();
            plaatsEntity.setGemnaam(plaats.trim());
            plaatsEntity.setNwinlst("j");
            plaatsEntity.setWorkOrder(workOrder);

            plaatsRepository.save(plaatsEntity);
        }
    }

    public void setBeroep(String beroep) {
        WorkOrder workOrder = inputMetadata.getWorkOrder();
        Beroep beroepEntity = beroepRepository.findByBerpnaam(beroep.trim(), WorkOrder.EMPTY_WORKORDER, workOrder);
        if (beroepEntity == null) {
            beroepEntity = new Beroep();
            beroepEntity.setBerpnaam(beroep.trim());
            beroepEntity.setNwinlst("j");
            beroepEntity.setWorkOrder(workOrder);

            beroepRepository.save(beroepEntity);
        }
    }

    public void setRelatie(String relatie) {
        WorkOrder workOrder = inputMetadata.getWorkOrder();
        Relatie relatieEntity = relatieRepository.findByRelatie(relatie.trim(), WorkOrder.EMPTY_WORKORDER, workOrder);
        if (relatieEntity == null) {
            relatieEntity = new Relatie();
            relatieEntity.setRelatie(relatie.trim());
            relatieEntity.setNwinlst("j");
            relatieEntity.setWorkOrder(workOrder);

            relatieRepository.save(relatieEntity);
        }
    }

    public void setKg(String kg) {
        WorkOrder workOrder = inputMetadata.getWorkOrder();
        Kg kgEntity = kgRepository.findByKerkgeno(kg.trim(), WorkOrder.EMPTY_WORKORDER, workOrder);
        if (kgEntity == null) {
            kgEntity = new Kg();
            kgEntity.setKerkgeno(kg.trim());
            kgEntity.setNwinlst("j");
            kgEntity.setWorkOrder(workOrder);

            kgRepository.save(kgEntity);
        }
    }
}
