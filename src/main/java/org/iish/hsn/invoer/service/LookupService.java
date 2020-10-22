package org.iish.hsn.invoer.service;

import org.iish.hsn.invoer.domain.invoer.WorkOrder;
import org.iish.hsn.invoer.domain.invoer.bev.Registration;
import org.iish.hsn.invoer.domain.invoer.bev.RegistrationId;
import org.iish.hsn.invoer.domain.invoer.geb.Gebakte;
import org.iish.hsn.invoer.domain.invoer.geb.Gebknd;
import org.iish.hsn.invoer.domain.invoer.huw.Huw;
import org.iish.hsn.invoer.domain.invoer.huw.Huwttl;
import org.iish.hsn.invoer.domain.invoer.mil.Milition;
import org.iish.hsn.invoer.domain.invoer.ovl.Ovlknd;
import org.iish.hsn.invoer.domain.invoer.pick.*;
import org.iish.hsn.invoer.domain.invoer.pk.Pkknd;
import org.iish.hsn.invoer.domain.reference.Ref_AINB;
import org.iish.hsn.invoer.domain.invoer.geb.Stpb;
import org.iish.hsn.invoer.domain.reference.Ref_RP;
import org.iish.hsn.invoer.exception.NotFoundException;
import org.iish.hsn.invoer.repository.invoer.bev.RegistrationRepository;
import org.iish.hsn.invoer.repository.invoer.geb.GebakteRepository;
import org.iish.hsn.invoer.repository.invoer.geb.GebkndRepository;
import org.iish.hsn.invoer.repository.invoer.huw.HuwttlRepository;
import org.iish.hsn.invoer.repository.invoer.mil.MilitionRepository;
import org.iish.hsn.invoer.repository.invoer.ovl.OvlkndRepository;
import org.iish.hsn.invoer.repository.invoer.pick.*;
import org.iish.hsn.invoer.repository.invoer.pk.PkkndRepository;
import org.iish.hsn.invoer.repository.reference.Ref_AINBRepository;
import org.iish.hsn.invoer.repository.invoer.geb.StpbRepository;
import org.iish.hsn.invoer.repository.reference.Ref_RPRepository;
import org.iish.hsn.invoer.service.scan.MilitionScan;
import org.iish.hsn.invoer.service.scan.ScansService;
import org.iish.hsn.invoer.util.InputMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Lookup service to obtain records from the database.
 */
@Service
public class LookupService {
    @Autowired private InputMetadata          inputMetadata;
    @Autowired private ScansService           scansService;

    @Autowired private StpbRepository         stpbRepository;
    @Autowired private Ref_RPRepository       refRpRepository;
    @Autowired private Ref_AINBRepository     refAinbRepository;
    @Autowired private GebakteRepository      gebakteRepository;
    @Autowired private GebkndRepository       gebkndRepository;
    @Autowired private OvlkndRepository       ovlkndRepository;
    @Autowired private HuwttlRepository       huwttlRepository;
    @Autowired private PkkndRepository        pkkndRepository;
    @Autowired private RegistrationRepository registrationRepository;
    @Autowired private MilitionRepository     militionRepository;
    @Autowired private PlaatsRepository       plaatsRepository;
    @Autowired private BeroepRepository       beroepRepository;
    @Autowired private RelatieRepository      relatieRepository;
    @Autowired private KindRelatieRepository  kindRelatieRepository;
    @Autowired private KgRepository           kgRepository;
    @Autowired private AdrestpRepository      adrestpRepository;

    /**
     * Returns the stpb for the given id number.
     *
     * @param idnr           The id number.
     * @param throwException Whether to throw an exception if not found or to return null.
     * @return The stpb.
     * @throws NotFoundException Thrown if the record was not found.
     */
    public Stpb getStpb(int idnr, boolean throwException) throws NotFoundException {
        Stpb stpb = stpbRepository.findByIdnrAndWorkOrder(idnr, inputMetadata.getWorkOrder());
        if ((stpb == null) && throwException) {
            throw new NotFoundException("Stpb with idnr " + idnr + " could not be found!");
        }
        return stpb;
    }

    /**
     * Returns the ref_rp for the given id number.
     *
     * @param idnr           The id number.
     * @param throwException Whether to throw an exception if not found or to return null.
     * @return The ref_rp.
     * @throws NotFoundException Thrown if the record was not found.
     */
    public Ref_RP getRefRp(int idnr, boolean throwException) throws NotFoundException {
        Ref_RP refRp = refRpRepository.findByIdnr(idnr);
        if ((refRp == null) && throwException) {
            throw new NotFoundException("Ref_RP with idnr " + idnr + " could not be found!");
        }
        return refRp;
    }

    /**
     * Returns the ref_ainb for the given key to source register.
     *
     * @param keyToSourceRegister The key to source register.
     * @param throwException      Whether to throw an exception if not found or to return null.
     * @return The ref_ainb.
     * @throws NotFoundException Thrown if the record was not found.
     */
    public Ref_AINB getRefAinb(int keyToSourceRegister, boolean throwException) throws NotFoundException {
        Ref_AINB refAinb = refAinbRepository.findByKeyToSourceRegister(keyToSourceRegister);
        if ((refAinb == null) && throwException) {
            throw new NotFoundException("Ref_AINB with key to source register " +
                                        keyToSourceRegister + " could not be found!");
        }
        return refAinb;
    }

    /**
     * Returns the gebakte for the given id number.
     *
     * @param idnr           The id number.
     * @param throwException Whether to throw an exception if not found or to return null.
     * @return The gebakte.
     * @throws NotFoundException Thrown if the record was not found.
     */
    public Gebakte getGebakte(int idnr, boolean throwException) throws NotFoundException {
        Gebakte gebakte = gebakteRepository.findByIdnrAndWorkOrder(idnr, inputMetadata.getWorkOrder());
        if ((gebakte == null) && throwException) {
            throw new NotFoundException("Gebakte with idnr " + idnr + " could not be found!");
        }
        return gebakte;
    }

    /**
     * Returns the gebknd for the given id number.
     *
     * @param idnr           The id number.
     * @param throwException Whether to throw an exception if not found or to return null.
     * @return The gebknd.
     * @throws NotFoundException Thrown if the record was not found.
     */
    public Gebknd getGebknd(int idnr, boolean throwException) throws NotFoundException {
        Gebknd gebknd = gebkndRepository.findByIdnr(idnr);
        if ((gebknd == null) && throwException) {
            throw new NotFoundException("Gebknd with idnr " + idnr + " could not be found!");
        }
        return gebknd;
    }

    /**
     * Returns the ovlknd for the given id number.
     *
     * @param idnr           The id number.
     * @param throwException Whether to throw an exception if not found or to return null.
     * @return The ovlknd.
     * @throws NotFoundException Thrown if the record was not found.
     */
    public Ovlknd getOvlknd(int idnr, boolean throwException) throws NotFoundException {
        Ovlknd ovlknd = ovlkndRepository.findByIdnrAndWorkOrder(idnr, inputMetadata.getWorkOrder());
        if ((ovlknd == null) && throwException) {
            throw new NotFoundException("Ovlknd with idnr " + idnr + " could not be found!");
        }
        return ovlknd;
    }

    /**
     * Returns the huwttl for the given id number and marriage date.
     *
     * @param idnr           The id number.
     * @param huw            Information about the marriage.
     * @param throwException Whether to throw an exception if not found or to return null.
     * @return The huwttl.
     * @throws NotFoundException Thrown if the record was not found.
     */
    public Huwttl getHuwttl(int idnr, Huw huw, boolean throwException) throws NotFoundException {
        Huwttl huwttl = huwttlRepository.findByIdnrAndHuwAndWorkOrder(idnr, huw, inputMetadata.getWorkOrder());
        if ((huwttl == null) && throwException) {
            throw new NotFoundException("Huwttl with idnr " + idnr + " could not be found!");
        }
        return huwttl;
    }

    /**
     * Returns the pkknd for the given id number.
     *
     * @param idnr           The id number.
     * @param throwException Whether to throw an exception if not found or to return null.
     * @return The pkknd.
     * @throws NotFoundException Thrown if the record was not found.
     */
    public Pkknd getPkknd(int idnr, boolean throwException) throws NotFoundException {
        Pkknd pkknd = pkkndRepository.findByIdnrAndWorkOrder(idnr, inputMetadata.getWorkOrder());
        if ((pkknd == null) && throwException) {
            throw new NotFoundException("Pkknd with idnr " + idnr + " could not be found!");
        }
        return pkknd;
    }

    /**
     * Returns the registration (b4) for the given registration id.
     *
     * @param registrationId The registration id.
     * @param throwException Whether to throw an exception if not found or to return null.
     * @return The registration (b4).
     * @throws NotFoundException Thrown if the record was not found.
     */
    public Registration getRegistration(RegistrationId registrationId, boolean throwException)
            throws NotFoundException {
        Registration registration =
                registrationRepository.findByRegistrationIdAndWorkOrder(registrationId, inputMetadata.getWorkOrder());
        if ((registration == null) && throwException) {
            throw new NotFoundException("Registration with id " + registrationId + " could not be found!");
        }
        return registration;
    }

    /**
     * Returns the registration (b4) for the given OP and date.
     *
     * @param idnr           The id number.
     * @param day            The day.
     * @param month          The month.
     * @param year           The year.
     * @param throwException Whether to throw an exception if not found or to return null.
     * @return The registration (b4).
     * @throws NotFoundException Thrown if the record was not found.
     */
    public Registration getRegistrationOfOp(int idnr, int day, int month, int year, boolean throwException)
            throws NotFoundException {
        Registration registration =
                registrationRepository.findByOPDatum(idnr, day, month, year, inputMetadata.getWorkOrder());
        if ((registration == null) && throwException) {
            throw new NotFoundException("Registration for OP " + idnr + " with date " +
                                        day + "-" + month + "-" + year + " could not be found!");
        }
        return registration;
    }

    /**
     * Returns the registrations (b4) for the given OP.
     *
     * @param idnr The id number.
     * @return The registrations (b4).
     */
    public List<Registration> getRegistrationsOfOp(int idnr) {
        return registrationRepository.findByOP(idnr, inputMetadata.getWorkOrder());
    }

    /**
     * Returns the milition (m0) for the given milition id.
     *
     * @param idnr The idnr.
     * @param seq The sequence number.
     * @param throwException Whether to throw an exception if not found or to return null.
     * @return The milition (m0).
     * @throws NotFoundException Thrown if the record was not found.
     */
    public Milition getMilition(int idnr, int seq, boolean throwException) throws NotFoundException {
        Milition milition =
                militionRepository.findByIdnrAndSeqAndWorkOrder(idnr, seq, inputMetadata.getWorkOrder());
        if ((milition == null) && throwException) {
            throw new NotFoundException("Milition with idnr " + idnr + " and seq " + seq + " could not be found!");
        }
        return milition;
    }

    /**
     * Returns the militions (m0) for the given milition id.
     *
     * @param idnr The idnr.
     * @return The militions (m0).
     */
    public List<Milition> getMilitions(int idnr) {
        return militionRepository.findByIdnrAndWorkOrder(idnr, inputMetadata.getWorkOrder());
    }

    /**
     * Returns the milition scans for the given milition id.
     *
     * @param idnr The idnr.
     * @return The militions scans.
     * @throws IOException Thrown on I/O errors.
     */
    public Set<MilitionScan> getMilitionScans(int idnr) throws IOException {
        return scansService.getMilitionScanRepository().findScans(idnr);
    }

    /**
     * Returns the plaats for the given gemeente number.
     *
     * @param gemnr          The gemeente number.
     * @param throwException Whether to throw an exception if not found or to return null.
     * @return The plaats.
     * @throws NotFoundException Thrown if the record was not found.
     */
    public Plaats getPlaats(int gemnr, boolean throwException) throws NotFoundException {
        Plaats plaats = plaatsRepository.findByGemnr(gemnr);
        if ((plaats == null) && throwException) {
            throw new NotFoundException("Plaats with gemnr " + gemnr + " could not be found!");
        }
        return plaats;
    }

    /**
     * Returns the matching plaatsen for the given gemeente naam.
     *
     * @param gemnaam The gemeente naam.
     * @param gemnr   The gemeente number.
     * @return The matching plaatsen.
     */
    public List<Plaats> findMatchingPlaatsen(String gemnaam, Integer gemnr) {
        if (gemnr != null) {
            return Arrays.asList(plaatsRepository.findByGemnr(gemnr));
        }
        return plaatsRepository
                .findByGemnaamLike(gemnaam + "%", WorkOrder.EMPTY_WORKORDER, inputMetadata.getWorkOrder());
    }

    /**
     * Returns the matching kerkgenootschappen for the given kerkgeno.
     *
     * @param kerkgeno The kerkgeno.
     * @return The matching kerkgenootschappen.
     */
    public List<Kg> findMatchingKg(String kerkgeno) {
        return kgRepository
                .findByKerkgenoLike(kerkgeno + "%", WorkOrder.EMPTY_WORKORDER, inputMetadata.getWorkOrder());
    }

    /**
     * Returns the matching relaties for the given relatie.
     *
     * @param relatie The relatie.
     * @param relkode The relatie code.
     * @return The matching relaties.
     */
    public List<Relatie> findMatchingRelaties(String relatie, Integer relkode) {
        if (relkode != null) {
            return Arrays.asList(relatieRepository.findByRelkode(relkode));
        }
        return relatieRepository
                .findByRelatieLike(relatie + "%", WorkOrder.EMPTY_WORKORDER, inputMetadata.getWorkOrder());
    }

    /**
     * Returns the matching kind relaties for the given relatie.
     *
     * @param relatie The relatie.
     * @return The matching kind relaties.
     */
    public List<KindRelatie> findMatchingKindRelaties(String relatie) {
        return kindRelatieRepository
                .findByRelatieLike(relatie + "%", WorkOrder.EMPTY_WORKORDER, inputMetadata.getWorkOrder());
    }

    /**
     * Returns the matching beroepen for the given beroep naam.
     *
     * @param berpnaam The beroep naam.
     * @return The matching beroepen.
     */
    public List<Beroep> findMatchingBeroepen(String berpnaam) {
        return beroepRepository
                .findByBerpnaamLike(berpnaam + "%", WorkOrder.EMPTY_WORKORDER, inputMetadata.getWorkOrder());
    }

    /**
     * Returns the matching adres types for the given type.
     *
     * @param type The adres type or code.
     * @return The matching adres types.
     */
    public List<Adrestp> findMatchingAdrestypes(String type) {
        return adrestpRepository
                .findByCodeOrTypeLike(type + "%", WorkOrder.EMPTY_WORKORDER, inputMetadata.getWorkOrder());
    }
}
