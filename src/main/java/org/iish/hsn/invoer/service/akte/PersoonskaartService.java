package org.iish.hsn.invoer.service.akte;

import org.iish.hsn.invoer.domain.invoer.Byz;
import org.iish.hsn.invoer.domain.invoer.pick.Plaats;
import org.iish.hsn.invoer.domain.reference.Ref_GBH;
import org.iish.hsn.invoer.domain.invoer.pk.*;
import org.iish.hsn.invoer.exception.AkteException;
import org.iish.hsn.invoer.exception.NotFoundException;
import org.iish.hsn.invoer.flow.state.*;
import org.iish.hsn.invoer.repository.invoer.pk.*;
import org.iish.hsn.invoer.service.LookupService;
import org.iish.hsn.invoer.util.InputMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Deals with the preparation and storage of various elements of the 'persoonskaart' (person card).
 */
@Service
public class PersoonskaartService extends AkteService {
    @Autowired private InputMetadata      inputMetadata;
    @Autowired private LookupService      lookupService;
    @Autowired private PkkndRepository    pkkndRepository;
    @Autowired private P7Repository       p7Repository;
    @Autowired private P8Repository       p8Repository;
    @Autowired private PkbrpRepository    pkbrpRepository;
    @Autowired private PkhuwRepository    pkhuwRepository;
    @Autowired private PkadresRepository  pkadresRepository;
    @Autowired private PkeigkndRepository pkeigkndRepository;
    @Autowired private PkbyzRepository    pkbyzRepository;

    /**
     * Creates a new persoonskaart flow state for new input with all required domain objects.
     *
     * @return A new persoonskaart flow state.
     */
    public PersoonskaartFlowState createNewAkteForNewInput() {
        PersoonskaartFlowState persoonskaartFlow = createNewAkte();
        persoonskaartFlow.setCorrection(false);
        persoonskaartFlow.setViewStateHistory(new ViewStateHistory("PS0I", "PS0A"));
        return persoonskaartFlow;
    }

    /**
     * Creates a new persoonskaart flow state for correction with all required domain objects.
     *
     * @return A new persoonskaart flow state.
     */
    public PersoonskaartFlowState createNewAkteForCorrection() {
        PersoonskaartFlowState persoonskaartFlow = createNewAkte();
        persoonskaartFlow.setCorrection(true);
        persoonskaartFlow.setViewStateHistory(new ViewStateHistory("PS0C", "PCOR"));
        return persoonskaartFlow;
    }

    /**
     * Link the OP (onderzoekspersoon, research person) of whom the user has the person card.
     *
     * @param persoonskaartFlow The persoonskaart flow state.
     * @throws AkteException Thrown when the OP was not found in the reference table.
     */
    public void registerOP(PersoonskaartFlowState persoonskaartFlow) throws AkteException {
        try {
            Pkknd pkknd = persoonskaartFlow.getPkknd();

            if (pkknd.getIdnr() < 500000) {
                Ref_GBH refGbh = lookupService.getRefGbh(pkknd.getIdnr(), true);
                persoonskaartFlow.setRefGbh(refGbh);

                pkknd.setGdgperp(refGbh.getDayOfBirth());
                pkknd.setGmdperp(refGbh.getMonthOfBirth());
                pkknd.setGjrperp(refGbh.getYearOfBirth());

                pkknd.setAnmperp(refGbh.getLastName());
                pkknd.setVnm1perp(refGbh.getFirstName1());
                pkknd.setVnm2perp(refGbh.getFirstName2());
                pkknd.setVnm3perp(refGbh.getFirstName3());

                // TODO: Next only if pktype != 7 ?

                pkknd.setGslperp(refGbh.getSex());

                pkknd.setAnmvdrp(refGbh.getLastNameFather());
                pkknd.setVnm1vdrp(refGbh.getFirstName1Father());
                pkknd.setVnm2vdrp(refGbh.getFirstName2Father());
                pkknd.setVnm3vdrp(refGbh.getFirstName3Father());

                pkknd.setAnmmdrp(refGbh.getLastNameMother());
                pkknd.setVnm1mdrp(refGbh.getFirstName1Mother());
                pkknd.setVnm2mdrp(refGbh.getFirstName2Mother());
                pkknd.setVnm3mdrp(refGbh.getFirstName3Mother());

                // TODO: Till here...

                Plaats plaats = lookupService.getPlaats(refGbh.getNumberMunicipality(), false);
                if (plaats != null) {
                    pkknd.setGplperp(plaats.getGemnaam());
                }
            }
        }
        catch (NotFoundException nfe) {
            throw new AkteException(nfe);
        }
    }

    /**
     * Link the OP (onderzoekspersoon, research person) of whom the user has to edit the person card.
     *
     * @param persoonskaartFlow The persoonskaart flow state.
     * @throws AkteException Thrown when the OP was not found in the reference table.
     */
    public void editOP(PersoonskaartFlowState persoonskaartFlow) throws AkteException {
        try {
            Pkknd pkknd = persoonskaartFlow.getPkknd();
            int idnr = pkknd.getIdnr();

            persoonskaartFlow.setRefGbh(lookupService.getRefGbh(idnr, true));
            persoonskaartFlow.setPkknd(lookupService.getPkknd(idnr, true));

            P7 p7 = p7Repository.findByIdnrAndWorkOrder(idnr, inputMetadata.getWorkOrder());
            if (p7 != null) {
                persoonskaartFlow.setP7(p7);
            }

            P8 p8Origin = p8Repository.findByIdnrAndP8tpnrAndWorkOrder(idnr, 1, inputMetadata.getWorkOrder());
            if (p8Origin != null) {
                persoonskaartFlow.setP8Origin(p8Origin);
            }

            P8 p8CurWhereabouts = p8Repository.findByIdnrAndP8tpnrAndWorkOrder(idnr, 2, inputMetadata.getWorkOrder());
            if (p8CurWhereabouts != null) {
                persoonskaartFlow.setP8CurWhereabouts(p8CurWhereabouts);
            }

            List<Pkbrp> pkbrp = pkbrpRepository.findByIdnrAndWorkOrder(idnr, inputMetadata.getWorkOrder());
            if (pkbrp != null) {
                persoonskaartFlow.setPkbrp(new ArrayList<>(pkbrp));
            }

            List<Pkhuw> pkhuw = pkhuwRepository.findByIdnrAndWorkOrder(idnr, inputMetadata.getWorkOrder());
            if (pkhuw != null) {
                persoonskaartFlow.setPkhuw(new ArrayList<>(pkhuw));
            }

            List<Pkadres> pkadres = pkadresRepository.findByIdnrAndWorkOrder(idnr, inputMetadata.getWorkOrder());
            if (pkadres != null) {
                persoonskaartFlow.setPkadres(new ArrayList<>(pkadres));
            }

            List<Pkeigknd> pkeigknd = pkeigkndRepository.findByIdnrAndWorkOrder(idnr, inputMetadata.getWorkOrder());
            if (pkeigknd != null) {
                persoonskaartFlow.setPkeigknd(new ArrayList<>(pkeigknd));
            }

            List<Pkbyz> Pkbyz = pkbyzRepository.findByIdnrAndWorkOrder(idnr, inputMetadata.getWorkOrder());
            if (Pkbyz != null) {
                persoonskaartFlow.setPkbyz(new HashSet<>(Pkbyz));
            }

            persoonskaartFlow.setNrOfByzEntered(persoonskaartFlow.getPkbyz().size());
        }
        catch (NotFoundException nfe) {
            throw new AkteException(nfe);
        }
    }

    /**
     * Register the general information, called after screen PS0A.
     *
     * @param persoonskaartFlow The persoonskaart flow state.
     */
    public void registerGeneralInformation(PersoonskaartFlowState persoonskaartFlow) {
        Pkknd pkknd = persoonskaartFlow.getPkknd();
        int pkType = pkknd.getPktype();

        if ((pkType != 3) && (pkType != 5) && (pkType != 7)) {
            pkknd.setEindagpk(-3);
            pkknd.setEinmndpk(-3);
            pkknd.setEinjarpk(-3);
        }

        // Only save during correction, the user might still stop the flow if it is not correction
        if (persoonskaartFlow.isCorrection()) {
            inputMetadata.saveToEntity(pkknd);
            pkknd = pkkndRepository.save(pkknd);
            persoonskaartFlow.setPkknd(pkknd);
        }
    }

    /**
     * Register the PK information to see whether the data has changed, called after screen PS1.
     *
     * @param persoonskaartFlow The persoonskaart flow state.
     */
    public void registerPkInformation(PersoonskaartFlowState persoonskaartFlow) {
        Pkknd pkknd = persoonskaartFlow.getPkknd();
        Ref_GBH refGbh = persoonskaartFlow.getRefGbh();

        // If refGbh is null, the idnr should be bigger than >= 500000
        if ((refGbh == null) || (pkknd.getIdnr() >= 500000)) {
            pkknd.setGegperp("");

            if (!persoonskaartFlow.isCorrection()) {
                pkknd.setAnmvdrp(pkknd.getAnmperp());
            }
        }
        else if (!pkknd.getAnmperp().equals(refGbh.getLastName()) ||
                 !pkknd.getVnm1perp().equals(refGbh.getFirstName1())) {
            pkknd.setGegperp("n");
        }
        else {
            pkknd.setGegperp("j");
        }
    }

    /**
     * Register the ouders to see whether the data has changed, called after screen PS2.
     *
     * @param persoonskaartFlow The persoonskaart flow state.
     */
    public void registerOuders(PersoonskaartFlowState persoonskaartFlow) {
        Pkknd pkknd = persoonskaartFlow.getPkknd();
        Ref_GBH refGbh = persoonskaartFlow.getRefGbh();

        // If refGbh is null, the idnr should be bigger than >= 500000
        if ((refGbh == null) || (pkknd.getIdnr() >= 500000)) {
            pkknd.setGegmdrp("");
            pkknd.setGegvdrp("");
        }
        else {
            if (!pkknd.getAnmmdrp().equals(refGbh.getLastNameMother()) ||
                !pkknd.getVnm1mdrp().equals(refGbh.getFirstName1Mother())) {
                pkknd.setGegmdrp("n");
            }
            else {
                pkknd.setGegmdrp("j");
            }

            if (!pkknd.getAnmvdrp().equals(refGbh.getLastNameFather()) ||
                !pkknd.getVnm1vdrp().equals(refGbh.getFirstName1Father())) {
                pkknd.setGegvdrp("n");
            }
            else {
                pkknd.setGegvdrp("j");
            }
        }
    }

    /**
     * Register and persist the information about the persoonskaart, called at least after screen PS2, and before PS3.
     *
     * @param persoonskaartFlow The persoonskaart flow state.
     */
    public void registerPersoonskaart(PersoonskaartFlowState persoonskaartFlow) {
        Pkknd pkknd = persoonskaartFlow.getPkknd();
        P7 p7 = persoonskaartFlow.getP7();

        inputMetadata.saveToEntity(pkknd);
        pkknd = pkkndRepository.save(pkknd);
        persoonskaartFlow.setPkknd(pkknd);

        if (persoonskaartFlow.isCorrection() && (pkknd.getPktype() != 8) && (pkknd.getPktype() != 10)) {
            p7Repository.delete(p7);
        }

        if ((pkknd.getPktype() == 8) || (pkknd.getPktype() == 10)) {
            if (pkknd.getOplperp().contains("$")) {
                String[] oplperpSplit = pkknd.getOplperp().split("\\$");
                p7.setP7opog(oplperpSplit[0].trim());
                p7.setP7opol(oplperpSplit[1].trim());
            }
            else {
                p7.setP7opog(pkknd.getOplperp());
            }
            pkknd.setOplperp("");

            p7.setIdnr(pkknd.getIdnr());
            inputMetadata.saveToEntity(p7);
            p7 = p7Repository.save(p7);
            persoonskaartFlow.setP7(p7);
        }
    }

    /**
     * Update the total number of beroepen.
     *
     * @param persoonskaartFlow The persoonskaart flow state.
     */
    public void updateBeroepen(PersoonskaartFlowState persoonskaartFlow) {
        List<Pkbrp> pkbrp = persoonskaartFlow.getPkbrp();

        // First check how many we need
        int totalNumber = 0;
        for (Pkbrp curPkbrp : pkbrp) {
            if ((curPkbrp.getBeroepp() == null) || curPkbrp.getBeroepp().trim().isEmpty()) {
                break;
            }
            totalNumber++;
        }

        // First add new instances to the list if necessary
        for (int i = pkbrp.size(); i < totalNumber; i++) {
            pkbrp.add(i, new Pkbrp(i + 1));
        }

        // Then remove the instances that fall out of range
        if (pkbrp.size() > totalNumber) {
            List<Pkbrp> toDelete = pkbrp.subList(totalNumber, pkbrp.size());
            pkbrpRepository.delete(toDelete);

            pkbrp = new ArrayList<>(pkbrp.subList(0, totalNumber));
            persoonskaartFlow.setPkbrp(pkbrp);
        }

        // Always add one empty one
        pkbrp.add(totalNumber, new Pkbrp(totalNumber + 1));
    }

    /**
     * Register the beroepen.
     *
     * @param persoonskaartFlow The persoonskaart flow state.
     */
    public void registerBeroepen(PersoonskaartFlowState persoonskaartFlow) {
        Pkknd pkknd = persoonskaartFlow.getPkknd();
        List<Pkbrp> pkbrp = persoonskaartFlow.getPkbrp();

        for (int i = 0; i < (pkbrp.size() - 1); i++) {
            Pkbrp curPkbrp = pkbrp.get(i);
            curPkbrp.setIdnr(pkknd.getIdnr());
            inputMetadata.saveToEntity(curPkbrp);
            curPkbrp = pkbrpRepository.save(curPkbrp);
            pkbrp.set(i, curPkbrp);
        }

        // Make sure to delete the last one
        Pkbrp curPkbrp = pkbrp.get(pkbrp.size() - 1);
        pkbrpRepository.delete(curPkbrp);

        pkbrp = new ArrayList<>(pkbrp.subList(0, pkbrp.size() - 1));
        persoonskaartFlow.setPkbrp(pkbrp);
    }

    /**
     * Deleted the entered marriages, called after screen PS4IN1.
     *
     * @param persoonskaartFlow The persoonskaart flow state.
     */
    public void deleteMarriages(PersoonskaartFlowState persoonskaartFlow) {
        List<Pkhuw> pkhuw = persoonskaartFlow.getPkhuw();
        pkhuwRepository.delete(pkhuw);
        persoonskaartFlow.setPkhuw(new ArrayList<Pkhuw>());
    }

    /**
     * Register a new record about a marriage.
     *
     * @param persoonskaartFlow The persoonskaart flow state.
     */
    public void createHuwelijk(PersoonskaartFlowState persoonskaartFlow) {
        List<Pkhuw> pkhuw = persoonskaartFlow.getPkhuw();
        int pkhuwIndex = persoonskaartFlow.getCurPkhuwIndex() + 1;

        if (!persoonskaartFlow.isCorrection() || (pkhuwIndex >= pkhuw.size())) {
            pkhuw.add(pkhuwIndex, new Pkhuw(pkhuwIndex + 1));
        }

        persoonskaartFlow.setCurPkhuwIndex(pkhuwIndex);
    }

    /**
     * Register the information about all the 'huwelijken' (marriages), called after screen PS4.
     *
     * @param persoonskaartFlow The persoonskaart flow state.
     */
    public void registerHuwelijken(PersoonskaartFlowState persoonskaartFlow) {
        List<Pkhuw> pkhuw = persoonskaartFlow.getPkhuw();
        int pkhuwIndex = persoonskaartFlow.getCurPkhuwIndex() + 1;

        if ((pkhuwIndex > 0) && (pkhuwIndex < pkhuw.size())) {
            pkhuwRepository.delete(pkhuw.subList(pkhuwIndex, pkhuw.size()));
        }
    }

    /**
     * Register the information from the current 'huwelijk' (marriage), called after screen PS4.
     *
     * @param persoonskaartFlow The persoonskaart flow state.
     */
    public void registerHuwelijk(PersoonskaartFlowState persoonskaartFlow) {
        Pkhuw curPkhuw = persoonskaartFlow.getCurPkhuw();
        curPkhuw.setIdnr(persoonskaartFlow.getPkknd().getIdnr());

        inputMetadata.saveToEntity(curPkhuw);
        curPkhuw = pkhuwRepository.save(curPkhuw);

        List<Pkhuw> pkhuw = persoonskaartFlow.getPkhuw();
        pkhuw.set(persoonskaartFlow.getCurPkhuwIndex(), curPkhuw);
    }

    /**
     * Register the information from the adresses (for a PL, persoons lijsten), called after screen PS5PL.
     *
     * @param persoonskaartFlow The persoonskaart flow state.
     */
    public void registerPlAdressen(PersoonskaartFlowState persoonskaartFlow) {
        Pkknd pkknd = persoonskaartFlow.getPkknd();
        P8 p8Origin = persoonskaartFlow.getP8Origin();
        P8 p8CurWhereabouts = persoonskaartFlow.getP8CurWhereabouts();

        p8Origin.setIdnr(pkknd.getIdnr());
        p8CurWhereabouts.setIdnr(pkknd.getIdnr());

        inputMetadata.saveToEntity(p8Origin);
        inputMetadata.saveToEntity(p8CurWhereabouts);

        p8Origin = p8Repository.save(p8Origin);
        p8CurWhereabouts = p8Repository.save(p8CurWhereabouts);

        persoonskaartFlow.setP8Origin(p8Origin);
        persoonskaartFlow.setP8CurWhereabouts(p8CurWhereabouts);
    }

    /**
     * Update the total number of adressen.
     *
     * @param persoonskaartFlow The persoonskaart flow state.
     */
    public void updateAdressen(PersoonskaartFlowState persoonskaartFlow) {
        List<Pkadres> pkadres = persoonskaartFlow.getPkadres();

        // First check how many we need
        int totalNumber = 0;
        for (Pkadres curPkadres : pkadres) {
            if (((curPkadres.getDgadrp() == -1) && (curPkadres.getMdadrp() == -1) && (curPkadres.getJradrp() == -1)) ||
                ((curPkadres.getDgadrp() == 0)  && (curPkadres.getMdadrp() == 0)  && (curPkadres.getJradrp() == 0))) {
                break;
            }
            totalNumber++;
        }

        // First add new instances to the list if necessary
        for (int i = pkadres.size(); i < totalNumber; i++) {
            pkadres.add(i, new Pkadres(i + 1));
        }

        // Then remove the instances that fall out of range
        if (pkadres.size() > totalNumber) {
            List<Pkadres> toDelete = pkadres.subList(totalNumber, pkadres.size());
            pkadresRepository.delete(toDelete);

            pkadres = new ArrayList<>(pkadres.subList(0, totalNumber));
            persoonskaartFlow.setPkadres(pkadres);
        }

        // Always add one empty one
        pkadres.add(totalNumber, new Pkadres(totalNumber + 1));
    }

    /**
     * Register the adressen.
     *
     * @param persoonskaartFlow The persoonskaart flow state.
     */
    public void registerAdressen(PersoonskaartFlowState persoonskaartFlow) {
        Pkknd pkknd = persoonskaartFlow.getPkknd();
        List<Pkadres> pkadres = persoonskaartFlow.getPkadres();

        for (int i = 0; i < (pkadres.size() - 1); i++) {
            Pkadres curPkadres = pkadres.get(i);
            curPkadres.setIdnr(pkknd.getIdnr());

            inputMetadata.saveToEntity(curPkadres);
            curPkadres = pkadresRepository.save(curPkadres);
            pkadres.set(i, curPkadres);
        }

        // Make sure to delete the last one
        Pkadres curPkadres = pkadres.get(pkadres.size() - 1);
        pkadresRepository.delete(curPkadres);

        pkadres = new ArrayList<>(pkadres.subList(0, pkadres.size() - 1));
        persoonskaartFlow.setPkadres(pkadres);
    }

    /**
     * Delete the entered children, called after screen PS6IN1.
     *
     * @param persoonskaartFlow The persoonskaart flow state.
     */
    public void deleteKinderen(PersoonskaartFlowState persoonskaartFlow) {
        List<Pkeigknd> pkeigknd = persoonskaartFlow.getPkeigknd();
        pkeigkndRepository.delete(pkeigknd);
        persoonskaartFlow.setPkeigknd(new ArrayList<Pkeigknd>());
    }

    /**
     * Register a new record about a child.
     *
     * @param persoonskaartFlow The persoonskaart flow state.
     */
    public void createKind(PersoonskaartFlowState persoonskaartFlow) {
        List<Pkeigknd> pkeigknd = persoonskaartFlow.getPkeigknd();
        int pkeigkndIndex = persoonskaartFlow.getCurPkeigkndIndex() + 1;

        if (!persoonskaartFlow.isCorrection() || (pkeigkndIndex >= pkeigknd.size())) {
            Pkknd pkknd = persoonskaartFlow.getPkknd();
            Pkeigknd curPkeigknd = new Pkeigknd(pkeigkndIndex + 1);

            if (!persoonskaartFlow.isCorrection() && pkknd.getGslperp().equals("m")) {
                curPkeigknd.setAnmkndp(pkknd.getAnmperp());
            }

            pkeigknd.add(pkeigkndIndex, curPkeigknd);
        }

        persoonskaartFlow.setCurPkeigkndIndex(pkeigkndIndex);
    }

    /**
     * Register the information about all the 'kinderen' (children), called after screen PS6.
     *
     * @param persoonskaartFlow The persoonskaart flow state.
     */
    public void registerKinderen(PersoonskaartFlowState persoonskaartFlow) {
        List<Pkeigknd> pkeigknd = persoonskaartFlow.getPkeigknd();
        int pkeigkndIndex = persoonskaartFlow.getCurPkeigkndIndex() + 1;

        if ((pkeigkndIndex > 0) && (pkeigkndIndex < pkeigknd.size())) {
            pkeigkndRepository.delete(pkeigknd.subList(pkeigkndIndex, pkeigknd.size()));
        }
    }

    /**
     * Register the information from the current 'kind' (child), called after screen PS6.
     *
     * @param persoonskaartFlow The persoonskaart flow state.
     */
    public void registerKind(PersoonskaartFlowState persoonskaartFlow) {
        Pkeigknd curPkeigknd = persoonskaartFlow.getCurPkeigknd();
        curPkeigknd.setIdnr(persoonskaartFlow.getPkknd().getIdnr());

        inputMetadata.saveToEntity(curPkeigknd);
        curPkeigknd = pkeigkndRepository.save(curPkeigknd);

        List<Pkeigknd> pkeigknd = persoonskaartFlow.getPkeigknd();
        pkeigknd.set(persoonskaartFlow.getCurPkeigkndIndex(), curPkeigknd);
    }

    /**
     * Register the check whether to add byz, called after screen PS7.
     *
     * @param persoonskaartFlow The persoonskaart flow state.
     */
    public void registerByzCheck(PersoonskaartFlowState persoonskaartFlow) {
        Pkknd pkknd = persoonskaartFlow.getPkknd();
        inputMetadata.saveToEntity(pkknd);
        pkknd = pkkndRepository.save(pkknd);
        persoonskaartFlow.setPkknd(pkknd);
    }

    /**
     * Deletes the current persoonskaart.
     *
     * @param akteFlow The akte flow state.
     */
    @Override
    public void deleteAkte(AkteFlowState akteFlow) {
        PersoonskaartFlowState persoonskaartFlow = (PersoonskaartFlowState) akteFlow;

        pkkndRepository.delete(persoonskaartFlow.getPkknd());
        p7Repository.delete(persoonskaartFlow.getP7());
        p8Repository.delete(persoonskaartFlow.getP8Origin());
        p8Repository.delete(persoonskaartFlow.getP8CurWhereabouts());
        pkbrpRepository.delete(persoonskaartFlow.getPkbrp());
        pkhuwRepository.delete(persoonskaartFlow.getPkhuw());
        pkadresRepository.delete(persoonskaartFlow.getPkadres());
        pkeigkndRepository.delete(persoonskaartFlow.getPkeigknd());
        pkbyzRepository.delete(persoonskaartFlow.getPkbyz());
    }

    /**
     * Save the given byz field.
     *
     * @param byzAkteFlowState Akte flow state.
     * @param byz              The byz.
     */
    @Override
    protected void saveByz(ByzAkteFlowState byzAkteFlowState, Byz byz) {
        PersoonskaartFlowState persoonskaartFlow = (PersoonskaartFlowState) byzAkteFlowState;
        Pkbyz pkbyz = (Pkbyz) byz;

        pkbyz.setIdnr(persoonskaartFlow.getPkknd().getIdnr());

        inputMetadata.saveToEntity(pkbyz);
        pkbyz = pkbyzRepository.save(pkbyz);
        persoonskaartFlow.addToPkbyz(pkbyz);
    }

    /**
     * Delete the given byz field.
     *
     * @param byzAkteFlowState Akte flow state.
     * @param byz              The byz.
     */
    @Override
    protected void deleteByz(ByzAkteFlowState byzAkteFlowState, Byz byz) {
        PersoonskaartFlowState persoonskaartFlow = (PersoonskaartFlowState) byzAkteFlowState;
        Pkbyz pkbyz = (Pkbyz) byz;

        pkbyzRepository.delete(pkbyz);

        persoonskaartFlow.removeFromPkbyz(pkbyz);
    }

    /**
     * Creates a new persoonskaart flow state with all required domain objects.
     *
     * @return A new persoonskaart flow state.
     */
    private PersoonskaartFlowState createNewAkte() {
        Pkknd pkknd = new Pkknd();
        P7 p7 = new P7();
        P8 p8Origin = new P8(1);
        P8 p8CurWhereabouts = new P8(2);
        List<Pkbrp> pkbrp = new ArrayList<>();
        List<Pkhuw> pkhuw = new ArrayList<>();
        List<Pkadres> pkadres = new ArrayList<>();
        List<Pkeigknd> pkeigknd = new ArrayList<>();
        Set<Pkbyz> pkbyz = new HashSet<>();

        return new PersoonskaartFlowState(pkknd, p7, p8Origin, p8CurWhereabouts, pkbrp, pkhuw, pkadres, pkeigknd,
                                          pkbyz);
    }
}