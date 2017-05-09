package org.iish.hsn.invoer.service.akte;

import org.iish.hsn.invoer.domain.invoer.Byz;
import org.iish.hsn.invoer.domain.invoer.WorkOrder;
import org.iish.hsn.invoer.domain.invoer.huw.*;
import org.iish.hsn.invoer.domain.reference.Ref_RP;
import org.iish.hsn.invoer.exception.AkteException;
import org.iish.hsn.invoer.exception.NotFoundException;
import org.iish.hsn.invoer.flow.state.AkteFlowState;
import org.iish.hsn.invoer.flow.state.ByzAkteFlowState;
import org.iish.hsn.invoer.flow.state.HuwelijksAkteFlowState;
import org.iish.hsn.invoer.flow.state.ViewStateHistory;
import org.iish.hsn.invoer.repository.invoer.huw.*;
import org.iish.hsn.invoer.service.LookupService;
import org.iish.hsn.invoer.util.InputMetadata;
import org.iish.hsn.invoer.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Deals with the preparation and storage of various elements of the 'huwelijks akte' (marriage certificate).
 */
@Service
public class HuwelijksAkteService extends AkteService {
    @Autowired private InputMetadata      inputMetadata;
    @Autowired private LookupService      lookupService;
    @Autowired private HuwttlRepository   huwttlRepository;
    @Autowired private HuwkndRepository   huwkndRepository;
    @Autowired private HuwafkRepository   huwafkRepository;
    @Autowired private HuweerRepository   huweerRepository;
    @Autowired private HuwvrkndRepository huwvrkndRepository;
    @Autowired private HuwgtgRepository   huwgtgRepository;
    @Autowired private HuwbyzRepository   huwbyzRepository;

    /**
     * Creates a new huwelijks akte flow state for new input with all required domain objects.
     *
     * @return A new huwelijks akte flow state.
     */
    public HuwelijksAkteFlowState createNewAkteForNewInput() {
        HuwelijksAkteFlowState huwelijksAkteFlow = createNewAkte();
        huwelijksAkteFlow.setCorrection(false);
        huwelijksAkteFlow.setViewStateHistory(new ViewStateHistory("HS0I", "HS0A1"));
        return huwelijksAkteFlow;
    }

    /**
     * Creates a new huwelijks akte flow state for correction with all required domain objects.
     *
     * @return A new huwelijks akte flow state.
     */
    public HuwelijksAkteFlowState createNewAkteForCorrection() {
        HuwelijksAkteFlowState huwelijksAkteFlow = createNewAkte();
        huwelijksAkteFlow.setCorrection(true);
        huwelijksAkteFlow.setViewStateHistory(new ViewStateHistory("HS0C", "HCOR"));
        return huwelijksAkteFlow;
    }

    /**
     * Link the OP (onderzoekspersoon, research person) of whom the user has the marriage certificate.
     *
     * @param huwelijksAkteFlow The huwelijks akte flow state.
     * @throws AkteException Thrown when the OP was not found in the reference table.
     */
    public void registerOP(HuwelijksAkteFlowState huwelijksAkteFlow) throws AkteException {
        try {
            Huwttl huwttl = huwelijksAkteFlow.getHuwttl();
            Huwknd huwknd = huwelijksAkteFlow.getHuwknd();

            Ref_RP refRp = lookupService.getRefRp(huwttl.getIdnr(), true);

            Huw huw = huwttl.getHuw();

            int gebdag = refRp.getDayOfBirth();
            int gebmnd = refRp.getMonthOfBirth();
            int gebjr = refRp.getYearOfBirth();

            double dayOfBirth = (gebjr * 365.25) + ((gebmnd - 1) * 30.5) + gebdag;
            double dayOfMarriage = (huw.getHjaar() * 365.25) + ((huw.getHmaand() - 1) * 30.5) + huw.getHdag();
            double dayDifference = dayOfMarriage - dayOfBirth;
            double yearsByApprox = dayDifference / 365.25;
            int years = (int) yearsByApprox;

            huwknd.setHuw(huw);
            huwknd.setIdnr(huwttl.getIdnr());
            huwknd.setHuur(huwttl.getHuur());
            huwknd.setHplts(huwttl.getHplts());
            huwknd.setHgemnr(huwttl.getHgemnr());
            huwknd.setHaktenr(huwttl.getHaktenr());

            huwknd.setGebsex(refRp.getSex());
            if (refRp.getSex().equals("m")) {
                huwknd.setAnmhm(refRp.getPrefixLastName());
                String[] firstNames = Utils.getFirstNames(refRp.getFirstName());
                huwknd.setVrn1hm(firstNames[0]);
                huwknd.setVrn2hm(firstNames[1]);
                huwknd.setVrn3hm(firstNames[2]);

                huwknd.setGebplnhm(refRp.getNumberMunicipality());
                huwknd.setGebplhm(refRp.getNameMunicipality());

                huwknd.setAnmvrhm(refRp.getPrefixLastNameFather());
                String[] firstNamesFather = Utils.getFirstNames(refRp.getFirstNameFather());
                huwknd.setVrn1vrhm(firstNamesFather[0]);
                huwknd.setVrn2vrhm(firstNamesFather[1]);
                huwknd.setVrn3vrhm(firstNamesFather[2]);

                huwknd.setAnmmrhm(refRp.getPrefixLastNameMother());
                String[] firstNamesMother = Utils.getFirstNames(refRp.getFirstNameMother());
                huwknd.setVrn1mrhm(firstNamesMother[0]);
                huwknd.setVrn2mrhm(firstNamesMother[1]);
                huwknd.setVrn3mrhm(firstNamesMother[2]);

                huwknd.setLftjhm(years);
            }

            if (refRp.getSex().equals("v")) {
                huwknd.setAnmhv(refRp.getPrefixLastName());
                String[] firstNames = Utils.getFirstNames(refRp.getFirstName());
                huwknd.setVrn1hv(firstNames[0]);
                huwknd.setVrn2hv(firstNames[1]);
                huwknd.setVrn3hv(firstNames[2]);

                huwknd.setGebplnhv(refRp.getNumberMunicipality());
                huwknd.setGebplhv(refRp.getNameMunicipality());

                huwknd.setAnmvrhv(refRp.getPrefixLastNameFather());
                String[] firstNamesFather = Utils.getFirstNames(refRp.getFirstNameFather());
                huwknd.setVrn1vrhv(firstNamesFather[0]);
                huwknd.setVrn2vrhv(firstNamesFather[1]);
                huwknd.setVrn3vrhv(firstNamesFather[2]);

                huwknd.setAnmmrhv(refRp.getPrefixLastNameMother());
                String[] firstNamesMother = Utils.getFirstNames(refRp.getFirstNameMother());
                huwknd.setVrn1mrhv(firstNamesMother[0]);
                huwknd.setVrn2mrhv(firstNamesMother[1]);
                huwknd.setVrn3mrhv(firstNamesMother[2]);

                huwknd.setLftjhv(years);
            }

            // We now also know the number of getuigen already
            if (huwknd.getNgtg() > 0) {
                Huwgtg[] huwgtg = new Huwgtg[huwknd.getNgtg()];
                for (int i = 0; i < huwgtg.length; i++) {
                    huwgtg[i] = new Huwgtg(i + 1);
                }
                huwelijksAkteFlow.setHuwgtg(huwgtg);
                huwelijksAkteFlow.setCurHuwgtgIndex(0);
            }

            huwelijksAkteFlow.setRefRp(refRp);
        }
        catch (NotFoundException nfe) {
            throw new AkteException(nfe);
        }
    }

    /**
     * Link the OP (onderzoekspersoon, research person) of whom the user has to edit the marriage certificate.
     *
     * @param huwelijksAkteFlow The huwelijks akte flow state.
     * @throws AkteException Thrown when the OP was not found in the reference table.
     */
    public void editOP(HuwelijksAkteFlowState huwelijksAkteFlow) throws AkteException {
        try {
            Huwttl huwttl = huwelijksAkteFlow.getHuwttl();
            int idnr = huwttl.getIdnr();
            Huw huw = huwttl.getHuw();
            WorkOrder workOrder = inputMetadata.getWorkOrder();

            huwelijksAkteFlow.setRefRp(lookupService.getRefRp(idnr, true));
            huwelijksAkteFlow.setHuwttl(lookupService.getHuwttl(idnr, huw, true));

            huwelijksAkteFlow.setHuwknd(huwkndRepository.findByIdnrAndHuwAndWorkOrder(idnr, huw, workOrder));

            List<Huwafk> huwafkList = huwafkRepository.findByIdnrAndHuwAndWorkOrder(idnr, huw, workOrder);
            if (huwafkList != null) {
                huwelijksAkteFlow.setHuwafk(new ArrayList<>(huwafkList));
            }

            List<Huweer> huweerBrideList =
                    huweerRepository.findByIdnrAndHuwAndHuwerAndWorkOrder(idnr, huw, "v", workOrder);
            if (huweerBrideList != null) {
                huwelijksAkteFlow.setHuweerBride(new ArrayList<>(huweerBrideList));
            }

            List<Huweer> huweerGroomList =
                    huweerRepository.findByIdnrAndHuwAndHuwerAndWorkOrder(idnr, huw, "m", workOrder);
            if (huweerGroomList != null) {
                huwelijksAkteFlow.setHuweerGroom(new ArrayList<>(huweerGroomList));
            }

            List<Huwvrknd> huwvrkndList = huwvrkndRepository.findByIdnrAndHuwAndWorkOrder(idnr, huw, workOrder);
            if (huwafkList != null) {
                Huwvrknd[] huwvrknd = new Huwvrknd[huwvrkndList.size()];
                huwelijksAkteFlow.setHuwvrknd(huwvrkndList.toArray(huwvrknd));
                huwelijksAkteFlow.setCurHuwvrkndIndex(0);
            }

            List<Huwgtg> huwgtgList = huwgtgRepository.findByIdnrAndHuwAndWorkOrder(idnr, huw, workOrder);
            if (huwgtgList != null) {
                Huwgtg[] huwgtg = new Huwgtg[huwgtgList.size()];
                huwelijksAkteFlow.setHuwgtg(huwgtgList.toArray(huwgtg));
                huwelijksAkteFlow.setCurHuwgtgIndex(0);
            }

            List<Huwbyz> huwbyz = huwbyzRepository.findByIdnrAndHuwAndWorkOrder(idnr, huw, workOrder);
            if (huwbyz != null) {
                huwelijksAkteFlow.setHuwbyz(new HashSet<>(huwbyz));
            }

            huwelijksAkteFlow.setNrOfByzEntered(huwelijksAkteFlow.getHuwbyz().size());
        }
        catch (NotFoundException nfe) {
            throw new AkteException(nfe);
        }
    }

    /**
     * Register the first information details. (Only during correction)
     *
     * @param huwelijksAkteFlow The huwelijks akte flow state.
     */
    public void registerFirstDetails(HuwelijksAkteFlowState huwelijksAkteFlow) {
        Huwknd huwknd = huwelijksAkteFlow.getHuwknd();
        Huwttl huwttl = huwelijksAkteFlow.getHuwttl();

        huwknd.setHplts(huwttl.getHplts());
        huwknd.setHgemnr(huwttl.getHgemnr());
        huwknd.setHaktenr(huwttl.getHaktenr());

        inputMetadata.saveToEntity(huwttl);
        huwttl = huwttlRepository.save(huwttl);
        huwelijksAkteFlow.setHuwttl(huwttl);

        saveHuwknd(huwelijksAkteFlow);
    }

    /**
     * Register the information from the 'bruid' and the 'bruidegom'.
     *
     * @param huwelijksAkteFlow The huwelijks akte flow state.
     */
    public void registerBruidAndBruidegom(HuwelijksAkteFlowState huwelijksAkteFlow) {
        Huwknd huwknd = huwelijksAkteFlow.getHuwknd();

        if (huwknd.getGebsex().equals("v") && !huwelijksAkteFlow.isCorrection()) {
            huwknd.setAnmvrhm(huwknd.getAnmhm());
        }
        if (huwknd.getGebsex().equals("m") && !huwelijksAkteFlow.isCorrection()) {
            huwknd.setAnmvrhv(huwknd.getAnmhv());
        }
    }

    /**
     * Register the information from the 'bruid' and the 'bruidegom' and their parents.
     *
     * @param huwelijksAkteFlow The huwelijks akte flow state.
     * @param viewStateId       The view state id calling this method.
     */
    public void registerBruidBruidegomAndParents(HuwelijksAkteFlowState huwelijksAkteFlow, String viewStateId) {
        Ref_RP refRp = huwelijksAkteFlow.getRefRp();
        Huwknd huwknd = huwelijksAkteFlow.getHuwknd();

        // Update the address of the mother with the address filled in for the father
        if (viewStateId.equals("HS2Mv") && !huwelijksAkteFlow.isCorrection()) {
            huwknd.setAdrmrhm(huwknd.getAdrvrhm());
        }
        if (viewStateId.equals("HS2Vv") && !huwelijksAkteFlow.isCorrection()) {
            huwknd.setAdrmrhv(huwknd.getAdrvrhv());
        }

        // Compare entered names with those from the birth certificate
        if (viewStateId.equals("HS2Vm")) {
            if (huwknd.getGebsex().equals("m")) {
                String[] firstNames = Utils.getFirstNames(refRp.getFirstName());
                if (!huwknd.getAnmhm().equals(refRp.getLastName()) ||
                    !huwknd.getVrn1hm().equals(firstNames[0])) {
                    huwknd.setGeghuw("n");
                }
                else {
                    huwknd.setGeghuw("j");
                }

                String[] firstNamesFather = Utils.getFirstNames(refRp.getFirstNameFather());
                if (!huwknd.getAnmvrhm().equals(refRp.getLastNameFather()) ||
                    !huwknd.getVrn1vrhm().equals(firstNamesFather[0])) {
                    huwknd.setGegvr("n");
                }
                else {
                    huwknd.setGegvr("j");
                }

                String[] firstNamesMother = Utils.getFirstNames(refRp.getFirstNameMother());
                if (!huwknd.getAnmmrhm().equals(refRp.getLastNameMother()) ||
                    !huwknd.getVrn1mrhm().equals(firstNamesMother[0])) {
                    huwknd.setGegmr("n");
                }
                else {
                    huwknd.setGegmr("j");
                }
            }
            else if (huwknd.getGebsex().equals("v")) {
                String[] firstNames = Utils.getFirstNames(refRp.getFirstName());
                if (!huwknd.getAnmhv().equals(refRp.getLastName()) ||
                    !huwknd.getVrn1hv().equals(firstNames[0])) {
                    huwknd.setGeghuw("n");
                }
                else {
                    huwknd.setGeghuw("j");
                }

                String[] firstNamesFather = Utils.getFirstNames(refRp.getFirstNameFather());
                if (!huwknd.getAnmvrhv().equals(refRp.getLastNameFather()) ||
                    !huwknd.getVrn1vrhv().equals(firstNamesFather[0])) {
                    huwknd.setGegvr("n");
                }
                else {
                    huwknd.setGegvr("j");
                }

                String[] firstNamesMother = Utils.getFirstNames(refRp.getFirstNameMother());
                if (!huwknd.getAnmmrhv().equals(refRp.getLastNameMother()) ||
                    !huwknd.getVrn1mrhv().equals(firstNamesMother[0])) {
                    huwknd.setGegmr("n");
                }
                else {
                    huwknd.setGegmr("j");
                }
            }

            Huwttl huwttl = huwelijksAkteFlow.getHuwttl();
            inputMetadata.saveToEntity(huwttl);
            huwttl = huwttlRepository.save(huwttl);
            huwelijksAkteFlow.setHuwttl(huwttl);

            inputMetadata.saveToEntity(huwknd);
            huwknd = huwkndRepository.save(huwknd);
            huwelijksAkteFlow.setHuwknd(huwknd);
        }
    }

    /**
     * Register the additional information about the 'bruid' and the 'bruidegom'.
     *
     * @param huwelijksAkteFlow The huwelijks akte flow state.
     */
    public void registerOverigeGegevens(HuwelijksAkteFlowState huwelijksAkteFlow) {
        saveHuwknd(huwelijksAkteFlow);
    }

    /**
     * Update the total number of huwelijks afkondigingen.
     *
     * @param huwelijksAkteFlow The huwelijks akte flow state.
     * @param totalNumber       The total number of huwelijks afkondigingen.
     */
    public void updateHuwelijksAfkondigingen(HuwelijksAkteFlowState huwelijksAkteFlow, int totalNumber) {
        List<Huwafk> huwafk = huwelijksAkteFlow.getHuwafk();

        // First add new instances to the list if necessary
        for (int i = huwafk.size(); i < totalNumber; i++) {
            huwafk.add(i, new Huwafk(i + 1));
        }

        // Then remove the instances that fall out of range
        if (huwafk.size() > totalNumber) {
            List<Huwafk> toDelete = huwafk.subList(totalNumber, huwafk.size());
            huwafkRepository.delete(toDelete);

            huwafk = new ArrayList<>(huwafk.subList(0, totalNumber));
        }

        huwelijksAkteFlow.setHuwafk(huwafk);
    }

    /**
     * Register the huwelijks afkondigingen.
     *
     * @param huwelijksAkteFlow The huwelijks akte flow state.
     */
    public void registerHuwelijksAfkondigingen(HuwelijksAkteFlowState huwelijksAkteFlow) {
        Huwttl huwttl = huwelijksAkteFlow.getHuwttl();
        List<Huwafk> huwafk = huwelijksAkteFlow.getHuwafk();

        for (int i = 0; i < huwafk.size(); i++) {
            Huwafk curHuwafk = huwafk.get(i);

            curHuwafk.setIdnr(huwttl.getIdnr());
            curHuwafk.setHuw(huwttl.getHuw());

            inputMetadata.saveToEntity(curHuwafk);
            curHuwafk = huwafkRepository.save(curHuwafk);
            huwafk.set(i, curHuwafk);
        }
    }

    /**
     * Delete the eerdere huwelijken, called after screen HS4ACOR1.
     *
     * @param huwelijksAkteFlow The huwelijks akte flow state.
     */
    public void registeEerdereHuwelijken(HuwelijksAkteFlowState huwelijksAkteFlow) {
        List<Huweer> huweerGroom = huwelijksAkteFlow.getHuweerGroom();
        int huweerGroomIndex = huwelijksAkteFlow.getCurHuweerGroomIndex() + 1;

        // If no previous marriages, delete everything, else delete all that has not been corrected after correction
        if (huwelijksAkteFlow.getHuwknd().getBsthm().equals("1")) {
            huweerRepository.delete(huweerGroom);
        }
        else if ((huweerGroomIndex > 0) && (huweerGroomIndex < huweerGroom.size())) {
            huweerRepository.delete(huweerGroom.subList(huweerGroomIndex, huweerGroom.size()));
        }

        List<Huweer> huweerBride = huwelijksAkteFlow.getHuweerBride();
        int huweerBrideIndex = huwelijksAkteFlow.getCurHuweerBrideIndex() + 1;

        // Same for the bride
        if (huwelijksAkteFlow.getHuwknd().getBsthv().equals("1")) {
            huweerRepository.delete(huweerBride);
        }
        else if ((huweerBrideIndex > 0) && (huweerBrideIndex < huweerBride.size())) {
            huweerRepository.delete(huweerBride.subList(huweerBrideIndex, huweerBride.size()));
        }
    }

    /**
     * Register a new record about previous marriages.
     *
     * @param huwelijksAkteFlow The huwelijks akte flow state.
     * @param huwer             Either 'm' or 'v' to denote the groom or the bride.
     *                          ('m' = man, groom; 'v' = vrouw, bride)
     */
    public void createEerderHuwelijk(HuwelijksAkteFlowState huwelijksAkteFlow, String huwer) {
        if (huwer.equals("m")) {
            List<Huweer> huweerGroom = huwelijksAkteFlow.getHuweerGroom();
            int huweerGroomIndex = huwelijksAkteFlow.getCurHuweerGroomIndex() + 1;

            if (!huwelijksAkteFlow.isCorrection() || (huweerGroomIndex >= huweerGroom.size())) {
                huweerGroom.add(huweerGroomIndex, new Huweer(huweerGroomIndex + 1, huwer));
            }

            huwelijksAkteFlow.setCurHuweerGroomIndex(huweerGroomIndex);
        }

        if (huwer.equals("v")) {
            List<Huweer> huweerBride = huwelijksAkteFlow.getHuweerBride();
            int huweerBrideIndex = huwelijksAkteFlow.getCurHuweerBrideIndex() + 1;

            if (!huwelijksAkteFlow.isCorrection() || (huweerBrideIndex >= huweerBride.size())) {
                huweerBride.add(huweerBrideIndex, new Huweer(huweerBrideIndex + 1, huwer));
            }

            huwelijksAkteFlow.setCurHuweerBrideIndex(huweerBrideIndex);
        }
    }

    /**
     * Register the information from the current 'eerdere huwelijk' (previous marriage), called after screen HS4A.
     *
     * @param huwelijksAkteFlow The huwelijks akte flow state.
     */
    public void registerEerderHuwelijk(HuwelijksAkteFlowState huwelijksAkteFlow) {
        Huweer curHuweer = huwelijksAkteFlow.getCurHuweer();

        curHuweer.setIdnr(huwelijksAkteFlow.getHuwknd().getIdnr());
        curHuweer.setHuw(huwelijksAkteFlow.getHuwknd().getHuw());

        inputMetadata.saveToEntity(curHuweer);
        curHuweer = huweerRepository.save(curHuweer);

        if (curHuweer.getHuwer().equals("m")) {
            List<Huweer> huweerGroom = huwelijksAkteFlow.getHuweerGroom();
            huweerGroom.set(huwelijksAkteFlow.getCurHuweerGroomIndex(), curHuweer);
        }

        if (curHuweer.getHuwer().equals("v")) {
            List<Huweer> huweerBride = huwelijksAkteFlow.getHuweerBride();
            huweerBride.set(huwelijksAkteFlow.getCurHuweerBrideIndex(), curHuweer);
        }
    }

    /**
     * Register the voorkinderen, called after screen HS4BCOR1.
     *
     * @param huwelijksAkteFlow  The huwelijks akte flow state.
     * @param voorkinderenAction What action to follow on the registered voorkinderen.
     */
    public void registerVoorkinderen(HuwelijksAkteFlowState huwelijksAkteFlow, Integer voorkinderenAction) {
        Huwknd huwknd = huwelijksAkteFlow.getHuwknd();
        if (voorkinderenAction == 1) {
            huwknd.setErken("j");
        }
        else if (voorkinderenAction == 2) {
            huwknd.setErken("n");
        }

        inputMetadata.saveToEntity(huwknd);
        huwknd = huwkndRepository.save(huwknd);
        huwelijksAkteFlow.setHuwknd(huwknd);
    }

    /**
     * Delete the voorkinderen, called after screen HS4BCOR2.
     *
     * @param huwelijksAkteFlow  The huwelijks akte flow state.
     * @param deleteVoorkinderen Whether to delete the registered voorkinderen.
     */
    public void deleteVoorkinderen(HuwelijksAkteFlowState huwelijksAkteFlow, String deleteVoorkinderen) {
        Huwknd huwknd = huwelijksAkteFlow.getHuwknd();

        if (deleteVoorkinderen.equals("j")) {
            huwknd.setErken("n");
            huwvrkndRepository.delete(Arrays.asList(huwelijksAkteFlow.getHuwvrknd()));
        }
        else if (deleteVoorkinderen.equals("n")) {
            huwknd.setErken("j");
        }

        inputMetadata.saveToEntity(huwknd);
        huwknd = huwkndRepository.save(huwknd);
        huwelijksAkteFlow.setHuwknd(huwknd);
    }

    /**
     * Register the voorkind, called after screen HS4B.
     *
     * @param huwelijksAkteFlow The huwelijks akte flow state.
     * @param totalSize         The total number of voorkinderen to be registered.
     */
    public void registerVoorkind(HuwelijksAkteFlowState huwelijksAkteFlow, Integer totalSize) {
        Huwttl huwttl = huwelijksAkteFlow.getHuwttl();
        Huwvrknd[] huwvrknd = huwelijksAkteFlow.getHuwvrknd();

        Huwvrknd curHuwvrknd = huwelijksAkteFlow.getCurHuwvrknd();
        curHuwvrknd.setIdnr(huwttl.getIdnr());
        curHuwvrknd.setHuw(huwttl.getHuw());

        // Make sure to copy data from mother to father if they acknowledge the children at the same time
        if (curHuwvrknd.getErvkwie().equals("3")) {
            curHuwvrknd.setVekdgvk(curHuwvrknd.getMekdgvk());
            curHuwvrknd.setVekmdvk(curHuwvrknd.getMekmdvk());
            curHuwvrknd.setVekjrvk(curHuwvrknd.getMekjrvk());
            curHuwvrknd.setVekplvk(curHuwvrknd.getMekplvk());
        }

        inputMetadata.saveToEntity(curHuwvrknd);
        curHuwvrknd = huwvrkndRepository.save(curHuwvrknd);

        // If this is the first one entered, then we also now know how many we can expect
        int curHuwvrkndIndex = huwelijksAkteFlow.getCurHuwvrkndIndex();
        if (curHuwvrkndIndex == 0) {
            Huwvrknd[] newHuwvrknd = new Huwvrknd[totalSize];
            for (int i = 0; i < newHuwvrknd.length; i++) {
                newHuwvrknd[i] = new Huwvrknd(i + 1);
            }

            // If we already have entered voorkinderen, fit into the new list
            if (huwelijksAkteFlow.isCorrection()) {
                for (int i = 0; i < huwvrknd.length; i++) {
                    if (i < newHuwvrknd.length) {
                        newHuwvrknd[i] = huwvrknd[i];
                    }
                    else {
                        huwvrkndRepository.delete(huwvrknd[i]);
                    }
                }
            }

            huwvrknd = newHuwvrknd;
        }

        huwvrknd[curHuwvrkndIndex] = curHuwvrknd;
        huwelijksAkteFlow.setHuwvrknd(huwvrknd);
    }

    /**
     * Delete all the getuigen or set a new number, called after screen HS5COR1.
     *
     * @param huwelijksAkteFlow The huwelijks akte flow state.
     */
    public void registerGetuigen(HuwelijksAkteFlowState huwelijksAkteFlow) {
        Huwgtg[] huwgtg = huwelijksAkteFlow.getHuwgtg();

        int nGtg = huwelijksAkteFlow.getHuwknd().getNgtg();
        if (nGtg == -5) {
            huwgtgRepository.delete(Arrays.asList(huwgtg));
        }
        else if (nGtg == 0) {
            huwelijksAkteFlow.getHuwknd().setNgtg(huwgtg.length);
        }
        else {
            Huwgtg[] newHuwgtg = new Huwgtg[nGtg];

            for (int i = 0; i < newHuwgtg.length; i++) {
                if (i < huwgtg.length) {
                    newHuwgtg[i] = huwgtg[i];
                }
                else {
                    newHuwgtg[i] = new Huwgtg(i + 1);
                }
            }

            if (huwgtg.length >= newHuwgtg.length) {
                List<Huwgtg> toDelete = Arrays.asList(huwgtg).subList(newHuwgtg.length, huwgtg.length);
                huwgtgRepository.delete(toDelete);
            }

            huwelijksAkteFlow.setHuwgtg(newHuwgtg);
            huwelijksAkteFlow.setCurHuwgtgIndex(0);
        }
    }

    /**
     * Register the getuige, called after screen HS5.
     *
     * @param huwelijksAkteFlow The huwelijks akte flow state.
     */
    public void registerGetuige(HuwelijksAkteFlowState huwelijksAkteFlow) {
        Huwttl huwttl = huwelijksAkteFlow.getHuwttl();
        Huwgtg[] huwgtg = huwelijksAkteFlow.getHuwgtg();

        Huwgtg curHuwgtg = huwelijksAkteFlow.getCurHuwgtg();
        curHuwgtg.setIdnr(huwttl.getIdnr());
        curHuwgtg.setHuw(huwttl.getHuw());

        inputMetadata.saveToEntity(curHuwgtg);
        curHuwgtg = huwgtgRepository.save(curHuwgtg);

        huwgtg[huwelijksAkteFlow.getCurHuwgtgIndex()] = curHuwgtg;
        huwelijksAkteFlow.setHuwgtg(huwgtg);
    }

    /**
     * Register the bijlagen, called after screen HS6.
     *
     * @param huwelijksAkteFlow The huwelijks akte flow state.
     */
    public void registerBijlagen(HuwelijksAkteFlowState huwelijksAkteFlow, String bijlagen) {
        Huwknd huwknd = huwelijksAkteFlow.getHuwknd();

        // Set all bijlagen to 'n' if the user indicated there were no bijlagen.
        if (bijlagen.equals("n")) {
            huwknd.setUgebhuw("n");
            huwknd.setUovloud("n");
            huwknd.setUovlech("n");
            huwknd.setCertnatm("n");
            huwknd.setToestnot("n");
            huwknd.setAktebek("n");
            huwknd.setOnvermgn("n");
            huwknd.setCommand("n");
            huwknd.setToestvgd("n");
        }

        inputMetadata.saveToEntity(huwknd);
        huwknd = huwkndRepository.save(huwknd);
        huwelijksAkteFlow.setHuwknd(huwknd);
    }

    /**
     * Register the check whether to add byz, called after screen OS6.
     *
     * @param huwelijksAkteFlow The huwelijks akte flow state.
     */
    public void registerByzCheck(HuwelijksAkteFlowState huwelijksAkteFlow) {
        saveHuwknd(huwelijksAkteFlow);
    }

    /**
     * Deletes the current huwelijks akte.
     *
     * @param akteFlow The akte flow state.
     */
    @Override
    public void deleteAkte(AkteFlowState akteFlow) {
        HuwelijksAkteFlowState huwelijksAkteFlow = (HuwelijksAkteFlowState) akteFlow;

        if (huwelijksAkteFlow.getHuwttl().getId() != null) {
            huwttlRepository.delete(huwelijksAkteFlow.getHuwttl());
        }
        huwkndRepository.delete(huwelijksAkteFlow.getHuwknd());
        huwafkRepository.delete(huwelijksAkteFlow.getHuwafk());
        huweerRepository.delete(huwelijksAkteFlow.getHuweerGroom());
        huweerRepository.delete(huwelijksAkteFlow.getHuweerBride());
        huwvrkndRepository.delete(Arrays.asList(huwelijksAkteFlow.getHuwvrknd()));
        huwgtgRepository.delete(Arrays.asList(huwelijksAkteFlow.getHuwgtg()));
        huwbyzRepository.delete(huwelijksAkteFlow.getHuwbyz());
    }

    /**
     * Save the given byz field.
     *
     * @param byzAkteFlowState Akte flow state.
     * @param byz              The byz.
     */
    @Override
    protected void saveByz(ByzAkteFlowState byzAkteFlowState, Byz byz) {
        HuwelijksAkteFlowState huwelijksAkteFlow = (HuwelijksAkteFlowState) byzAkteFlowState;
        Huwbyz huwbyz = (Huwbyz) byz;

        huwbyz.setIdnr(huwelijksAkteFlow.getHuwknd().getIdnr());
        huwbyz.setHuw(huwelijksAkteFlow.getHuwknd().getHuw());

        inputMetadata.saveToEntity(huwbyz);
        huwbyz = huwbyzRepository.save(huwbyz);
        huwelijksAkteFlow.addToHuwbyz(huwbyz);
    }

    /**
     * Delete the given byz field.
     *
     * @param byzAkteFlowState Akte flow state.
     * @param byz              The byz.
     */
    @Override
    protected void deleteByz(ByzAkteFlowState byzAkteFlowState, Byz byz) {
        HuwelijksAkteFlowState huwelijksAkteFlow = (HuwelijksAkteFlowState) byzAkteFlowState;
        Huwbyz huwbyz = (Huwbyz) byz;

        huwbyzRepository.delete(huwbyz);

        huwelijksAkteFlow.removeFromHuwbyz(huwbyz);
    }

    /**
     * Creates a new huwelijks akte flow state with all required domain objects.
     *
     * @return A new huwelijks akte flow state.
     */
    private HuwelijksAkteFlowState createNewAkte() {
        Huwttl huwttl = new Huwttl();
        Huwknd huwknd = new Huwknd();
        List<Huwafk> huwafk = new ArrayList<>();
        List<Huweer> huweerGroom = new ArrayList<>();
        List<Huweer> huweerBride = new ArrayList<>();
        Huwvrknd[] huwvrknd = {new Huwvrknd(1)};
        Huwgtg[] huwgtg = {};
        Set<Huwbyz> huwbyz = new HashSet<>();

        return new HuwelijksAkteFlowState(huwttl, huwknd, huwafk, huweerGroom, huweerBride, huwvrknd, huwgtg, huwbyz);
    }

    /**
     * Persists the huwknd record (main marriage certificate record) to the database.
     *
     * @param huwelijksAkteFlow The huwelijks akte flow state.
     */
    private void saveHuwknd(HuwelijksAkteFlowState huwelijksAkteFlow) {
        Huwknd huwknd = huwelijksAkteFlow.getHuwknd();
        inputMetadata.saveToEntity(huwknd);
        huwknd = huwkndRepository.save(huwknd);
        huwelijksAkteFlow.setHuwknd(huwknd);
    }
}