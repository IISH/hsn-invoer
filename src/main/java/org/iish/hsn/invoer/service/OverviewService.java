package org.iish.hsn.invoer.service;

import org.iish.hsn.invoer.domain.invoer.bev.Person;
import org.iish.hsn.invoer.domain.invoer.bev.Registration;
import org.iish.hsn.invoer.domain.invoer.bev.RegistrationId;
import org.iish.hsn.invoer.domain.invoer.geb.Geb;
import org.iish.hsn.invoer.domain.invoer.huw.Huwknd;
import org.iish.hsn.invoer.domain.invoer.mil.MilitionRegistration;
import org.iish.hsn.invoer.domain.invoer.ovl.Ovlknd;
import org.iish.hsn.invoer.domain.invoer.pick.Plaats;
import org.iish.hsn.invoer.domain.invoer.pk.Pkknd;
import org.iish.hsn.invoer.param.OverviewParams;
import org.iish.hsn.invoer.repository.invoer.bev.PersonRepository;
import org.iish.hsn.invoer.repository.invoer.bev.RegistrationRepository;
import org.iish.hsn.invoer.repository.invoer.geb.GebRepository;
import org.iish.hsn.invoer.repository.invoer.huw.HuwkndRepository;
import org.iish.hsn.invoer.repository.invoer.mil.MilitionRegistrationRepository;
import org.iish.hsn.invoer.repository.invoer.ovl.OvlkndRepository;
import org.iish.hsn.invoer.repository.invoer.pick.PlaatsRepository;
import org.iish.hsn.invoer.param.GeboorteOverviewParams;
import org.iish.hsn.invoer.repository.invoer.pk.PkkndRepository;
import org.iish.hsn.invoer.specification.*;
import org.iish.hsn.invoer.util.InputMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@SuppressWarnings("unchecked")
public class OverviewService {
    @Autowired private InputMetadata                  inputMetadata;
    @Autowired private PlaatsRepository               plaatsRepository;
    @Autowired private GebRepository                  gebRepository;
    @Autowired private OvlkndRepository               ovlkndRepository;
    @Autowired private HuwkndRepository               huwkndRepository;
    @Autowired private PkkndRepository                pkkndRepository;
    @Autowired private MilitionRegistrationRepository militionRegistrationRepository;
    @Autowired private RegistrationRepository         registrationRepository;
    @Autowired private PersonRepository               personRepository;

    public List<Geb> getBirthOverview(GeboorteOverviewParams params) {
        GeboorteSpecifications geb = new GeboorteSpecifications();
        Specifications<Geb> spec = geb.getSpec(inputMetadata.getWorkOrder());
        spec = getSpecForParams(params, spec, geb);

        if ((params.getGemeente() != null) && !params.getGemeente().trim().isEmpty()) {
            Plaats plaats = plaatsRepository.findByGemnaamLikeIgnoreCase(params.getGemeente().trim());
            if (plaats != null) {
                spec = spec.and(geb.isInGemeente(plaats.getGemnr()));
            }
        }

        if ((params.getCohort() != null) && (params.getCohort() > 0)) {
            spec = spec.and(geb.isInCohort(params.getCohort()));
        }

        return gebRepository.findAll(spec);
    }

    public List<Ovlknd> getDeathOverview(OverviewParams params) {
        OverlijdensSpecifications ovl = new OverlijdensSpecifications();
        Specifications<Ovlknd> spec = ovl.getSpec(inputMetadata.getWorkOrder());
        spec = getSpecForParams(params, spec, ovl);
        return ovlkndRepository.findAll(spec);
    }

    public List<Huwknd> getMarriageOverview(OverviewParams params) {
        HuwelijksSpecifications huw = new HuwelijksSpecifications();
        Specifications<Huwknd> spec = huw.getSpec(inputMetadata.getWorkOrder());
        spec = getSpecForParams(params, spec, huw);
        return huwkndRepository.findAll(spec);
    }

    public List<Pkknd> getPersonCardOverview(OverviewParams params) {
        PersoonskaartSpecifications pk = new PersoonskaartSpecifications();
        Specifications<Pkknd> spec = pk.getSpec(inputMetadata.getWorkOrder());
        spec = getSpecForParams(params, spec, pk);
        return pkkndRepository.findAll(spec);
    }

    public List<MilitionRegistration> getMilitionOverview(OverviewParams params) {
        MilitieSpecifications mil = new MilitieSpecifications();
        Specifications<MilitionRegistration> spec = mil.getSpec(inputMetadata.getWorkOrder());
        spec = getSpecForParams(params, spec, mil);
        return militionRegistrationRepository.findAll(spec);
    }

    public List<Registration> getPopulationRegisterRegistrationOverview() {
        return getPopulationRegisterRegistrationOverview(null);
    }

    public List<Registration> getPopulationRegisterRegistrationOverview(Integer idnr) {
        if ((idnr == null) || (idnr == 0)) {
            return registrationRepository.findByWorkOrder(inputMetadata.getWorkOrder());
        }
        return registrationRepository.findByKeyToRPAndWorkOrder(idnr, inputMetadata.getWorkOrder());
    }

    public List<Person> getPopulationRegisterRegistrationPersonsOverview(RegistrationId registrationId) {
        return personRepository.findByRegistrationIdAndWorkOrder(registrationId, inputMetadata.getWorkOrder());
    }

    private Specifications getSpecForParams(OverviewParams params, Specifications spec, AkteSpecifications akteSpecs) {
        if ((params.getLaagsteIdnr() != null) && (params.getHoogsteIdnr() != null) &&
            (params.getLaagsteIdnr() > 0) && (params.getHoogsteIdnr() > 0)) {
            spec = spec.and(akteSpecs.isBetweenLowestAndHighestIdnr(params.getLaagsteIdnr(), params.getHoogsteIdnr()));
        }

        if ((params.getIdnr() != null) && (params.getIdnr() > 0)) {
            spec = spec.and(akteSpecs.hasIdnr(params.getIdnr()));
        }

        return spec;
    }
}
