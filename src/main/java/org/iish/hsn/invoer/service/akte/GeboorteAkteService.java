package org.iish.hsn.invoer.service.akte;

import org.iish.hsn.invoer.domain.invoer.Byz;
import org.iish.hsn.invoer.domain.invoer.geb.*;
import org.iish.hsn.invoer.domain.invoer.geb.Stpb;
import org.iish.hsn.invoer.exception.AkteException;
import org.iish.hsn.invoer.exception.NotFoundException;
import org.iish.hsn.invoer.flow.state.*;
import org.iish.hsn.invoer.flow.helper.GeboorteAkteHelper;
import org.iish.hsn.invoer.repository.invoer.geb.*;
import org.iish.hsn.invoer.service.LookupService;
import org.iish.hsn.invoer.util.Cohort;
import org.iish.hsn.invoer.util.InputMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Deals with the preparation and storage of various elements of the 'geboorte akte' (birth certificate).
 */
@Service
public class GeboorteAkteService extends AkteService {
    @Autowired private InputMetadata      inputMetadata;
    @Autowired private GeboorteStart      geboorteStart;
    @Autowired private GeboorteAkteHelper geboorteAkteHelper;
    @Autowired private LookupService      lookupService;

    @Autowired private GebakteRepository  gebakteRepository;
    @Autowired private GebkndRepository   gebkndRepository;
    @Autowired private GebvdrRepository   gebvdrRepository;
    @Autowired private GebkantRepository  gebkantRepository;
    @Autowired private GebgtgRepository   gebgtgRepository;
    @Autowired private GebbyzRepository   gebbyzRepository;

    /**
     * Creates a new geboorte akte flow state for new input with all required domain objects.
     *
     * @return A new geboorte akte flow state.
     */
    public GeboorteAkteFlowState createNewAkteForNewInput() {
        GeboorteAkteFlowState geboorteAkteFlow = createNewAkte();
        geboorteAkteFlow.setCorrection(false);
        geboorteAkteFlow.setViewStateHistory(new ViewStateHistory("GS0I", "checkGebAkte"));
        return geboorteAkteFlow;
    }

    /**
     * Creates a new geboorte akte flow state for correction with all required domain objects.
     *
     * @return A new geboorte akte flow state.
     */
    public GeboorteAkteFlowState createNewAkteForCorrection() {
        GeboorteAkteFlowState geboorteAkteFlow = createNewAkte();
        geboorteAkteFlow.setCorrection(true);
        geboorteAkteFlow.setViewStateHistory(new ViewStateHistory("GS0C", "GCOR"));
        return geboorteAkteFlow;
    }

    /**
     * Link the OP (onderzoekspersoon, research person) of whom the user has the birth certificate.
     *
     * @param geboorteAkteFlow The geboorte akte flow state.
     * @throws AkteException Thrown when the OP was not found in the sample.
     */
    public void registerOP(GeboorteAkteFlowState geboorteAkteFlow) throws AkteException {
        try {
            Gebakte newGebakte = geboorteAkteFlow.getGebakte();
            Gebakte oldGebakte = lookupService.getGebakte(newGebakte.getIdnr(), false);
            Stpb stpb = lookupService.getStpb(newGebakte.getIdnr(), true);

            newGebakte.setGemnr(stpb.getGemnr());
            newGebakte.setJaar(stpb.getJaar());
            newGebakte.setAktenr(stpb.getAktenr());
            newGebakte.setOversamp(geboorteStart.getOversamp());
            if (oldGebakte != null) {
                gebakteRepository.delete(oldGebakte);
            }
            if (newGebakte.getGebkode() != 1) {
                inputMetadata.saveToEntity(newGebakte);
                newGebakte = gebakteRepository.save(newGebakte);
            }
            if ((oldGebakte != null) && (newGebakte.getGebkode() == 1)) {
                List<Gebbyz> gebbyz =
                        gebbyzRepository.findByIdnrAndWorkOrder(newGebakte.getIdnr(), inputMetadata.getWorkOrder());
                gebbyzRepository.delete(gebbyz);
            }

            geboorteAkteFlow.setStpb(stpb);
            geboorteAkteFlow.setGebakte(newGebakte);
        }
        catch (NotFoundException nfe) {
            throw new AkteException(nfe);
        }
    }

    /**
     * Link the OP (onderzoekspersoon, research person) of whom the user has to edit the birth certificate.
     *
     * @param geboorteAkteFlow The geboorte akte flow state.
     * @throws AkteException Thrown when the OP was not found in the sample.
     */
    public void editOP(GeboorteAkteFlowState geboorteAkteFlow) throws AkteException {
        try {
            Gebakte gebakte = geboorteAkteFlow.getGebakte();
            int idnr = gebakte.getIdnr();

            // Before we overwrite gebknd, make sure that changes to the date are preserved
            Gebknd gebknd = geboorteAkteFlow.getGebknd();
            int dag = gebknd.getAktedag();
            int maand = gebknd.getAktemnd();
            int uur = gebknd.getAkteuur();

            geboorteAkteFlow.setStpb(lookupService.getStpb(idnr, true));

            // Now look up the gebknd record and update the changes to the date
            gebknd = lookupService.getGebknd(idnr, true);
            gebknd.setAktedag(dag);
            gebknd.setAktemnd(maand);
            gebknd.setAkteuur(uur);

            inputMetadata.saveToEntity(gebknd);
            gebknd = gebkndRepository.save(gebknd);
            geboorteAkteFlow.setGebknd(gebknd);

            Gebvdr gebvdr = gebvdrRepository.findByIdnrAndWorkOrder(idnr, inputMetadata.getWorkOrder());
            if (gebvdr != null) {
                geboorteAkteFlow.setGebvdr(gebvdr);
            }

            Gebkant gebkant = gebkantRepository.findByIdnrAndWorkOrder(idnr, inputMetadata.getWorkOrder());
            if (gebkant != null) {
                geboorteAkteFlow.setGebkant(gebkant);
            }

            List<Gebgtg> gebgtgList = gebgtgRepository.findByIdnrAndWorkOrder(idnr, inputMetadata.getWorkOrder());
            if (gebgtgList != null) {
                Gebgtg[] gebgtg = {new Gebgtg(1), new Gebgtg(2)};
                for (Gebgtg curGebgtg : gebgtgList) {
                    gebgtg[curGebgtg.getVlgnrgt() - 1] = curGebgtg;
                }
                geboorteAkteFlow.setGebgtg(gebgtg);
                geboorteAkteFlow.setCurGebgtgIndex(0);
            }

            List<Gebbyz> gebbyz = gebbyzRepository.findByIdnrAndWorkOrder(idnr, inputMetadata.getWorkOrder());
            if (gebbyz != null) {
                geboorteAkteFlow.setGebbyz(new HashSet<>(gebbyz));
            }
            geboorteAkteFlow.setNrOfByzEntered(geboorteAkteFlow.getGebbyz().size());
        }
        catch (NotFoundException nfe) {
            throw new AkteException(nfe);
        }
    }

    /**
     * Register the aangever, called after screen GS1.
     *
     * @param geboorteAkteFlow The geboorte akte flow state.
     */
    public void registerAangever(GeboorteAkteFlowState geboorteAkteFlow) {
        Stpb stpb = geboorteAkteFlow.getStpb();
        Gebknd gebknd = geboorteAkteFlow.getGebknd();

        // Copy some fields from the sample
        if (!geboorteAkteFlow.isCorrection()) {
            Cohort cohort = Cohort.getCohortByYear(gebknd.getJaar());

            gebknd.setIdnr(stpb.getIdnr());
            gebknd.setGemnr(stpb.getGemnr());
            gebknd.setJaar(stpb.getJaar());
            gebknd.setAktenr(stpb.getAktenr());
            gebknd.setCohortnr(cohort.getCohortNumber());

            gebknd.setOversamp(geboorteStart.getOversamp());
            gebknd.setInvbeper(geboorteStart.getInvbeper());

            // Already print the birth year for the user
            gebknd.setGebjr(stpb.getJaar());
        }

        if (gebknd.getInvbeper().equals("j") && gebknd.getAnmag().equals("Xx")) {
            gebknd.setAnmag("Vroedvrouw of verg. persoon");
        }

        // The user can still stop the flow, so only save during correction
        if (geboorteAkteFlow.isCorrection()) {
            inputMetadata.saveToEntity(gebknd);
            gebknd = gebkndRepository.save(gebknd);
            geboorteAkteFlow.setGebknd(gebknd);
        }
    }

    /**
     * Register the geboorte (birth) and the moeder (mother), called after screen GS2.
     *
     * @param geboorteAkteFlow The geboorte akte flow state.
     */
    public void registerGeboorteAndMoeder(GeboorteAkteFlowState geboorteAkteFlow) {
        saveGebknd(geboorteAkteFlow);
    }

    /**
     * Register the moeder (mother) and the kind (child), called after screen GS3.
     *
     * @param geboorteAkteFlow The geboorte akte flow state.
     */
    public void registerMoederAndKind(GeboorteAkteFlowState geboorteAkteFlow) {
        saveGebknd(geboorteAkteFlow);

        // Already set the last name of the father for the users convenience.
        String anmgeb = geboorteAkteFlow.getGebknd().getAnmgeb();
        String anmmr = geboorteAkteFlow.getGebknd().getAnmmr();
        if (!geboorteAkteFlow.isCorrection() && (anmgeb != null) && !anmgeb.equals(anmmr)) {
            geboorteAkteFlow.getGebvdr().setAnmvr(anmgeb);
        }

        // Delete the information about the father if during correction the father happens to be the declarant as well
        String brgstmr = geboorteAkteFlow.getGebknd().getBrgstmr();
        if (geboorteAkteFlow.isCorrection() && Arrays.asList(5, 7, 8).contains(new Integer(brgstmr))) {
            gebvdrRepository.delete(geboorteAkteFlow.getGebvdr());
        }
    }

    /**
     * Register the vader (father), called after screen GS4.
     *
     * @param geboorteAkteFlow The geboorte akte flow state.
     */
    public void registerVader(GeboorteAkteFlowState geboorteAkteFlow) {
        Gebvdr gebvdr = geboorteAkteFlow.getGebvdr();

        // Simply delete the previous record if there is no information to make sure we have a clean record again
        if (geboorteAkteFlow.isCorrection() && !gebvdr.getGegvr().equals("j")) {
            gebvdrRepository.delete(gebvdr);
            gebvdr = new Gebvdr();
            gebvdr.setGegvr("n");
        }

        // Make sure certain fields are emptied (difference between a previous husband due to a divorce or due to death)
        String brgstmr = geboorteAkteFlow.getGebknd().getBrgstmr();
        if (new Integer(brgstmr) != 2) {
            gebvdr.setG5vood(0);
            gebvdr.setG5voom(0);
            gebvdr.setG5vooj(0);
            gebvdr.setG5vogo("");
        }
        if (new Integer(brgstmr) != 3) {
            gebvdr.setG5oosd(0);
            gebvdr.setG5oosm(0);
            gebvdr.setG5oosj(0);
            gebvdr.setG5oogs("");
        }

        gebvdr.setIdnr(geboorteAkteFlow.getGebknd().getIdnr());
        inputMetadata.saveToEntity(gebvdr);
        gebvdr = gebvdrRepository.save(gebvdr);
        geboorteAkteFlow.setGebvdr(gebvdr);
    }

    /**
     * Register the getuige (witness), called after screen GS5.
     *
     * @param geboorteAkteFlow The geboorte akte flow state.
     */
    public void registerGetuige(GeboorteAkteFlowState geboorteAkteFlow) {
        Gebgtg gebgtgCur = geboorteAkteFlow.getCurGebgtg();
        gebgtgCur.setIdnr(geboorteAkteFlow.getGebknd().getIdnr());

        inputMetadata.saveToEntity(gebgtgCur);
        gebgtgCur = gebgtgRepository.save(gebgtgCur);

        Gebgtg[] gebgtg = geboorteAkteFlow.getGebgtg();
        gebgtg[geboorteAkteFlow.getCurGebgtgIndex()] = gebgtgCur;
        geboorteAkteFlow.setGebgtg(gebgtg);
    }

    /**
     * Register the kantmelding (side message) type, called after screen GS6.
     *
     * @param geboorteAkteFlow The geboorte akte flow state.
     */
    public void registerKanttype(GeboorteAkteFlowState geboorteAkteFlow) {
        Gebknd gebknd = geboorteAkteFlow.getGebknd();
        Gebkant gebkant = geboorteAkteFlow.getGebkant();

        gebkant.setIdnr(gebknd.getIdnr());
        if (!geboorteAkteFlow.isCorrection()) {
            gebkant.setKhuwanr("N");
        }

        inputMetadata.saveToEntity(gebkant);
        gebkant = gebkantRepository.save(gebkant);
        geboorteAkteFlow.setGebkant(gebkant);
    }

    /**
     * Register the kantmeldingen (side messages), called after screens GS7a, GS7b, GS7c and GS7d.
     *
     * @param geboorteAkteFlow The geboorte akte flow state.
     */
    public void registerKantmeldingen(GeboorteAkteFlowState geboorteAkteFlow) {
        Gebkant gebkant = geboorteAkteFlow.getGebkant();

        // Clean up all differences changes between the four different screens
        if (gebkant.getKanttype() == 1) {
            gebkant.setKanmgeb(gebkant.getKanmvad());
        }
        if (Arrays.asList(2, 3, 4).contains(gebkant.getKanttype())) {
            if (!geboorteAkteHelper.getKantmeldingDatum(gebkant).equals("j")) {
                gebkant.setKantdag(-1);
                gebkant.setKantmnd(-1);
                gebkant.setKantjr(-1);
            }
            if (!geboorteAkteHelper.getKantmeldingBesluitDatum(gebkant).equals("j")) {
                gebkant.setKwyzdag(-1);
                gebkant.setKwyzmnd(-1);
                gebkant.setKwyzjr(-1);
                gebkant.setKwyzkb(-1);
                gebkant.setKwyzstbl(-1);
            }
        }

        // Also clean up after correction
        if (geboorteAkteFlow.isCorrection() && Arrays.asList(1, 5).contains(gebkant.getKanttype())) {
            gebkant.setKwyzdag(0);
            gebkant.setKwyzmnd(0);
            gebkant.setKwyzjr(0);
            gebkant.setKwyzkb(0);
            gebkant.setKwyzstbl(0);

            gebkant.setKanmgeb("");
            gebkant.setKvrn1geb("");
            gebkant.setKvrn2geb("");
            gebkant.setKvrn3geb("");
            gebkant.setKsexgeb("");
            gebkant.setKgmrb("");
            gebkant.setKgmerk("");
            gebkant.setKwgmmr("");
            gebkant.setKbrpmr("");
        }
        if (geboorteAkteFlow.isCorrection() && Arrays.asList(2, 5).contains(gebkant.getKanttype())) {
            gebkant.setKhuwdag(0);
            gebkant.setKhuwmnd(0);
            gebkant.setKhuwjr(0);

            gebkant.setKhuwgem("");
            gebkant.setKhuwanr("");
            gebkant.setKanmvad("");
            gebkant.setKvrn1vad("");
            gebkant.setKvrn2vad("");
            gebkant.setKvrn3vad("");
            gebkant.setKgmrb("");
            gebkant.setKgmerk("");
            gebkant.setKwgmmr("");
            gebkant.setKbrpmr("");
        }
        if (geboorteAkteFlow.isCorrection() && Arrays.asList(3, 5).contains(gebkant.getKanttype())) {
            gebkant.setKhuwdag(0);
            gebkant.setKhuwmnd(0);
            gebkant.setKhuwjr(0);

            gebkant.setKhuwgem("");
            gebkant.setKhuwanr("");
            gebkant.setKanmvad("");
            gebkant.setKvrn1vad("");
            gebkant.setKvrn2vad("");
            gebkant.setKvrn3vad("");

            gebkant.setKwyzkb(0);
            gebkant.setKwyzstbl(0);

            gebkant.setKgmerk("");
            gebkant.setKwgmmr("");
            gebkant.setKbrpmr("");
        }
        if (geboorteAkteFlow.isCorrection() && Arrays.asList(4, 5).contains(gebkant.getKanttype())) {
            gebkant.setKhuwdag(0);
            gebkant.setKhuwmnd(0);
            gebkant.setKhuwjr(0);

            gebkant.setKhuwgem("");
            gebkant.setKhuwanr("");
            gebkant.setKanmvad("");
            gebkant.setKvrn1vad("");
            gebkant.setKvrn2vad("");
            gebkant.setKvrn3vad("");

            gebkant.setKwyzkb(0);
            gebkant.setKwyzstbl(0);

            gebkant.setKanmgeb("");
            gebkant.setKvrn1geb("");
            gebkant.setKvrn2geb("");
            gebkant.setKvrn3geb("");
            gebkant.setKsexgeb("");
            gebkant.setKgmrb("");
        }
        if (geboorteAkteFlow.isCorrection() && (gebkant.getKanttype() == 5)) {
            gebkant.setKantdag(0);
            gebkant.setKantmnd(0);
            gebkant.setKantjr(0);
        }

        inputMetadata.saveToEntity(gebkant);
        gebkant = gebkantRepository.save(gebkant);
        geboorteAkteFlow.setGebkant(gebkant);
    }

    /**
     * Register the check whether to add byz, called after screen GS8.
     *
     * @param geboorteAkteFlow The geboorte akte flow state.
     */
    public void registerByzCheck(GeboorteAkteFlowState geboorteAkteFlow) {
        saveGebknd(geboorteAkteFlow);
    }

    /**
     * Deletes the current geboorte akte.
     *
     * @param akteFlow The akte flow state.
     */
    @Override
    public void deleteAkte(AkteFlowState akteFlow) {
        GeboorteAkteFlowState geboorteAkteFlow = (GeboorteAkteFlowState) akteFlow;

        gebakteRepository.delete(geboorteAkteFlow.getGebakte());
        gebkndRepository.delete(geboorteAkteFlow.getGebknd());
        gebvdrRepository.delete(geboorteAkteFlow.getGebvdr());
        gebkantRepository.delete(geboorteAkteFlow.getGebkant());
        gebgtgRepository.delete(Arrays.asList(geboorteAkteFlow.getGebgtg()));
        gebbyzRepository.delete(geboorteAkteFlow.getGebbyz());
    }

    /**
     * Save the given byz field.
     *
     * @param byzAkteFlowState Akte flow state.
     * @param byz              The byz.
     */
    @Override
    protected void saveByz(ByzAkteFlowState byzAkteFlowState, Byz byz) {
        GeboorteAkteFlowState geboorteAkteFlowState = (GeboorteAkteFlowState) byzAkteFlowState;
        Gebbyz gebbyz = (Gebbyz) byz;

        gebbyz.setIdnr(geboorteAkteFlowState.getGebknd().getIdnr());
        inputMetadata.saveToEntity(gebbyz);
        gebbyz = gebbyzRepository.save(gebbyz);

        geboorteAkteFlowState.addToGebbyz(gebbyz);
    }

    @Override
    protected void deleteByz(ByzAkteFlowState byzAkteFlowState, Byz byz) {
        GeboorteAkteFlowState geboorteAkteFlowState = (GeboorteAkteFlowState) byzAkteFlowState;
        Gebbyz gebbyz = (Gebbyz) byz;

        gebbyzRepository.delete(gebbyz);

        geboorteAkteFlowState.removeFromGebbyz(gebbyz);
    }

    /**
     * Creates a new geboorte akte flow state with all required domain objects.
     *
     * @return A new geboorte akte flow state.
     */
    private GeboorteAkteFlowState createNewAkte() {
        Gebakte gebakte = new Gebakte();
        Gebknd gebknd = new Gebknd();
        Gebvdr gebvdr = new Gebvdr();
        Gebkant gebkant = new Gebkant();
        Gebgtg[] gebgtg = {new Gebgtg(1), new Gebgtg(2)}; // Always two witnesses
        Set<Gebbyz> gebbyz = new HashSet<>();

        return new GeboorteAkteFlowState(gebakte, gebknd, gebvdr, gebkant, gebgtg, gebbyz);
    }

    /**
     * Persists the gebknd record (main birth certificate record) to the database.
     *
     * @param geboorteAkteFlow The geboorte akte flow state.
     */
    private void saveGebknd(GeboorteAkteFlowState geboorteAkteFlow) {
        Gebknd gebknd = geboorteAkteFlow.getGebknd();
        inputMetadata.saveToEntity(gebknd);
        gebknd = gebkndRepository.save(gebknd);
        geboorteAkteFlow.setGebknd(gebknd);
    }
}