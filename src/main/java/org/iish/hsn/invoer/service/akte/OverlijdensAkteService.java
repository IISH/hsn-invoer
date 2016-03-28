package org.iish.hsn.invoer.service.akte;

import org.iish.hsn.invoer.domain.invoer.Byz;
import org.iish.hsn.invoer.domain.invoer.ovl.Ovlagv;
import org.iish.hsn.invoer.domain.invoer.ovl.Ovlbyz;
import org.iish.hsn.invoer.domain.invoer.ovl.Ovlech;
import org.iish.hsn.invoer.domain.invoer.ovl.Ovlknd;
import org.iish.hsn.invoer.domain.reference.Ref_RP;
import org.iish.hsn.invoer.exception.AkteException;
import org.iish.hsn.invoer.exception.NotFoundException;
import org.iish.hsn.invoer.flow.state.*;
import org.iish.hsn.invoer.repository.invoer.ovl.OvlagvRepository;
import org.iish.hsn.invoer.repository.invoer.ovl.OvlbyzRepository;
import org.iish.hsn.invoer.repository.invoer.ovl.OvlechRepository;
import org.iish.hsn.invoer.repository.invoer.ovl.OvlkndRepository;
import org.iish.hsn.invoer.service.LookupService;
import org.iish.hsn.invoer.util.InputMetadata;
import org.iish.hsn.invoer.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Deals with the preparation and storage of various elements of the 'overlijdens akte' (death certificate).
 */
@Service
public class OverlijdensAkteService extends AkteService {
    @Autowired private OverlijdensStart overlijdensStart;
    @Autowired private InputMetadata    inputMetadata;
    @Autowired private LookupService    lookupService;
    @Autowired private OvlkndRepository ovlkndRepository;
    @Autowired private OvlagvRepository ovlagvRepository;
    @Autowired private OvlechRepository ovlechRepository;
    @Autowired private OvlbyzRepository ovlbyzRepository;

    /**
     * Creates a new overlijdens akte flow state for new input with all required domain objects.
     *
     * @return A new overlijdens akte flow state.
     */
    public OverlijdensAkteFlowState createNewAkteForNewInput() {
        OverlijdensAkteFlowState overlijdensAkteFlow = createNewAkte();
        overlijdensAkteFlow.setCorrection(false);
        overlijdensAkteFlow.setLevnloos(overlijdensStart.getLevnloos().equals("j"));
        overlijdensAkteFlow.setViewStateHistory(new ViewStateHistory("OS0I", "levnloosCheck"));
        return overlijdensAkteFlow;
    }

    /**
     * Creates a new overlijdens akte flow state for correction with all required domain objects.
     *
     * @return A new overlijdens akte flow state.
     */
    public OverlijdensAkteFlowState createNewAkteForCorrection() {
        OverlijdensAkteFlowState overlijdensAkteFlow = createNewAkte();
        overlijdensAkteFlow.setCorrection(true);
        overlijdensAkteFlow.setViewStateHistory(new ViewStateHistory("OS0C", "OCOR"));
        return overlijdensAkteFlow;
    }

    /**
     * Link the OP (onderzoekspersoon, research person) of whom the user has the death certificate.
     *
     * @param overlijdensAkteFlow The overlijdens akte flow state.
     * @throws AkteException Thrown when the OP was not found in the reference table.
     */
    public void registerOP(OverlijdensAkteFlowState overlijdensAkteFlow) throws AkteException {
        try {
            Ovlknd ovlknd = overlijdensAkteFlow.getOvlknd();

            // If the OP has a birth certificate, (not stillborn) then take over data from the birth certificate.
            if (!overlijdensAkteFlow.isLevnloos()) {
                Ref_RP refRp = lookupService.getRefRp(ovlknd.getIdnr(), true);

                // Save data obtained from the reference table.
                ovlknd.setGbpovl(refRp.getNumberMunicipality());
                ovlknd.setOvlsex(refRp.getSex());
                ovlknd.setExtract("n");
                if ((ovlknd.getGgmovl() == null) || ovlknd.getGgmovl().trim().isEmpty()) {
                    ovlknd.setGgmovl(refRp.getNameMunicipality());
                }

                ovlknd.setAnmovl(refRp.getLastName());
                ovlknd.setTusovl(refRp.getPrefixName());

                String[] firstNames = Utils.getFirstNames(refRp.getFirstName());
                ovlknd.setVrn1ovl(firstNames[0]);
                ovlknd.setVrn2ovl(firstNames[1]);
                ovlknd.setVrn3ovl(firstNames[2]);

                ovlknd.setAnmvovl(refRp.getLastNameFather());
                ovlknd.setTusvovl(refRp.getPrefixFather());

                String[] firstNamesFather = Utils.getFirstNames(refRp.getFirstNameFather());
                ovlknd.setVrn1vovl(firstNamesFather[0]);
                ovlknd.setVrn2vovl(firstNamesFather[1]);
                ovlknd.setVrn3vovl(firstNamesFather[2]);

                ovlknd.setAnmmovl(refRp.getLastNameMother());
                ovlknd.setTusmovl(refRp.getPrefixMother());

                String[] firstNamesMother = Utils.getFirstNames(refRp.getFirstNameMother());
                ovlknd.setVrn1movl(firstNamesMother[0]);
                ovlknd.setVrn2movl(firstNamesMother[1]);
                ovlknd.setVrn3movl(firstNamesMother[2]);

                overlijdensAkteFlow.setRefRp(refRp);
            }
            else {
                ovlknd.setLevvovl("j");
                ovlknd.setLevmovl("j");
            }
        }
        catch (NotFoundException nfe) {
            throw new AkteException(nfe);
        }
    }

    /**
     * Link the OP (onderzoekspersoon, research person) of whom the user has to edit the death certificate.
     *
     * @param overlijdensAkteFlow The overlijdens akte flow state.
     * @throws AkteException Thrown when the OP was not found in the reference table.
     */
    public void editOP(OverlijdensAkteFlowState overlijdensAkteFlow) throws AkteException {
        try {
            Ovlknd ovlknd = overlijdensAkteFlow.getOvlknd();
            int idnr = ovlknd.getIdnr();

            Ref_RP refRp = lookupService.getRefRp(idnr, false);
            overlijdensAkteFlow.setRefRp(refRp);

            // TODO: If we edit an OP of which we cannot find a birth certificate, we assume it is a stillborn
            if (refRp == null) {
                overlijdensAkteFlow.setLevnloos(true);
            }

            overlijdensAkteFlow.setOvlknd(lookupService.getOvlknd(idnr, true));

            List<Ovlagv> ovlagvList = ovlagvRepository.findByIdnrAndWorkOrder(idnr, inputMetadata.getWorkOrder());
            if (ovlagvList != null) {
                Ovlagv[] ovlagv = {new Ovlagv(1), new Ovlagv(2)};
                for (Ovlagv curOvlagv : ovlagvList) {
                    ovlagv[curOvlagv.getVlgnrag() - 1] = curOvlagv;
                }
                overlijdensAkteFlow.setOvlagv(ovlagv);
                overlijdensAkteFlow.setCurOvlagvIndex(0);
            }

            List<Ovlech> ovlechList = ovlechRepository.findByIdnrAndWorkOrder(idnr, inputMetadata.getWorkOrder());
            if (ovlechList != null) {
                Ovlech[] ovlech = new Ovlech[ovlechList.size()];
                overlijdensAkteFlow.setOvlech(ovlechList.toArray(ovlech));
                overlijdensAkteFlow.setCurOvlechIndex(0);
            }

            List<Ovlbyz> ovlbyz = ovlbyzRepository.findByIdnrAndWorkOrder(idnr, inputMetadata.getWorkOrder());
            if (ovlbyz != null) {
                overlijdensAkteFlow.setOvlbyz(new HashSet<>(ovlbyz));
            }

            overlijdensAkteFlow.setNrOfByzEntered(overlijdensAkteFlow.getOvlbyz().size());
        }
        catch (NotFoundException nfe) {
            throw new AkteException(nfe);
        }
    }

    /**
     * Register the date, called after screen OS1B.
     *
     * @param overlijdensAkteFlow The overlijdens akte flow state.
     */
    public void registerDate(OverlijdensAkteFlowState overlijdensAkteFlow) {
        Ovlknd ovlknd = overlijdensAkteFlow.getOvlknd();

        if (ovlknd.getOvljr() == 0) {
            ovlknd.setOvljr(ovlknd.getOaktejr());
        }

        overlijdensAkteFlow.setOvlknd(ovlknd);
    }

    /**
     * Register the date and time, called after screen OS1C.
     *
     * @param overlijdensAkteFlow The overlijdens akte flow state.
     */
    public void registerDateAndTime(OverlijdensAkteFlowState overlijdensAkteFlow) {
        Ovlknd ovlknd = overlijdensAkteFlow.getOvlknd();

        if (!overlijdensAkteFlow.isLevnloos()) {
            ovlknd.setLaaug(-99);
        }

        overlijdensAkteFlow.setOvlknd(ovlknd);
    }

    /**
     * Register the first details, called after screen OS2B.
     *
     * @param overlijdensAkteFlow The overlijdens akte flow state.
     */
    public void registerFirstDetails(OverlijdensAkteFlowState overlijdensAkteFlow) {
        Ovlknd ovlknd = overlijdensAkteFlow.getOvlknd();

        if (overlijdensAkteFlow.isLevnloos()) {
            if ((ovlknd.getAnmvovl() != null) && (!ovlknd.getAnmvovl().trim().isEmpty())) {
                ovlknd.setAnmovl(ovlknd.getAnmvovl());
            }
            else {
                ovlknd.setAnmovl(ovlknd.getAnmmovl());
            }
            ovlknd.setVrn1ovl("NN");
        }

        inputMetadata.saveToEntity(ovlknd);
        ovlknd = ovlkndRepository.save(ovlknd);
        overlijdensAkteFlow.setOvlknd(ovlknd);
    }

    /**
     * Register the aangevers info, called after screen OS3A.
     *
     * @param overlijdensAkteFlow The overlijdens akte flow state.
     */
    public void registerAangeversInfo(OverlijdensAkteFlowState overlijdensAkteFlow) {
        Ovlknd ovlknd = overlijdensAkteFlow.getOvlknd();

        if (overlijdensAkteFlow.isLevnloos()) {
            ovlknd.setExtract("n");
        }

        if (ovlknd.getExtract().equals("j") || (ovlknd.getExtract().equals("n") && !ovlknd.getLevvovl().equals("j"))) {
            ovlknd.setAgvvadr("n");
        }

        // Skip the first aangever, if the father is one of them (we already have the data)
        if (ovlknd.getAgvvadr().equals("j")) {
            overlijdensAkteFlow.setCurOvlagvIndex(1);
        }

        inputMetadata.saveToEntity(ovlknd);
        ovlknd = ovlkndRepository.save(ovlknd);
        overlijdensAkteFlow.setOvlknd(ovlknd);
    }

    /**
     * Register the corrected aangevers info, called after screen OS3A.
     *
     * @param overlijdensAkteFlow The overlijdens akte flow state.
     */
    public void registerCorrectedAangeversInfo(OverlijdensAkteFlowState overlijdensAkteFlow) {
        registerAangeversInfo(overlijdensAkteFlow);

        Ovlknd ovlknd = overlijdensAkteFlow.getOvlknd();
        Ovlagv[] ovlagv = overlijdensAkteFlow.getOvlagv();

        // Now if it is an extract or if the father is one and the only one of the aangevers.
        // Then delete all previously inserted aangevers, if there were any.
        if (ovlknd.getExtract().equals("j") || (ovlknd.getAgvvadr().equals("j") && (ovlknd.getNagvr() == 1))) {
            ovlagvRepository.delete(Arrays.asList(ovlagv));
        }
        // If there are two aangevers and one of them is the father.
        // Then delete the first aangever, if het was previously inserted.
        else if (ovlknd.getAgvvadr().equals("j") && (ovlknd.getNagvr() == 2)) {
            ovlagvRepository.delete(ovlagv[0]);
            overlijdensAkteFlow.setCurOvlagvIndex(1);
        }
        // If there is only one aangever, then delete the second one, if inserted.
        else if (ovlknd.getNagvr() == 1) {
            ovlagvRepository.delete(ovlagv[1]);
        }
    }

    /**
     * Register the aangever, called after screen OS3B.
     *
     * @param overlijdensAkteFlow The overlijdens akte flow state.
     */
    public void registerAangever(OverlijdensAkteFlowState overlijdensAkteFlow) {
        Ovlagv ovlagvCur = overlijdensAkteFlow.getCurOvlagv();
        ovlagvCur.setIdnr(overlijdensAkteFlow.getOvlknd().getIdnr());

        inputMetadata.saveToEntity(ovlagvCur);
        ovlagvCur = ovlagvRepository.save(ovlagvCur);

        Ovlagv[] ovlagv = overlijdensAkteFlow.getOvlagv();
        ovlagv[overlijdensAkteFlow.getCurOvlagvIndex()] = ovlagvCur;
        overlijdensAkteFlow.setOvlagv(ovlagv);
    }

    /**
     * Register the other details, called after screen OS4.
     *
     * @param overlijdensAkteFlow The overlijdens akte flow state.
     */
    public void registerOtherDetails(OverlijdensAkteFlowState overlijdensAkteFlow) {
        saveOvlknd(overlijdensAkteFlow);
    }

    /**
     * Register the new burgelijke staat after correction, called after screen OS5COR1.
     *
     * @param overlijdensAkteFlow The overlijdens akte flow state.
     */
    public void registerNewBurgelijkeStaat(OverlijdensAkteFlowState overlijdensAkteFlow) {
        saveOvlknd(overlijdensAkteFlow);
    }

    /**
     * Register the confirmed new burgelijke staat after correction, called after screen OS5COR2.
     * This holds that all previous registered echtgenotes will be deleted.
     *
     * @param overlijdensAkteFlow The overlijdens akte flow state.
     */
    public void registerConfirmedNewBurgelijkeStaat(OverlijdensAkteFlowState overlijdensAkteFlow) {
        // We will delete all echtgenotes, so reset the counter back to 0.
        overlijdensAkteFlow.getOvlknd().setMreovl(0);
        saveOvlknd(overlijdensAkteFlow);

        Ovlech[] ovlech = overlijdensAkteFlow.getOvlech();
        ovlechRepository.delete(Arrays.asList(ovlech));

        // Initialize one new echtgenoot if later echtgenotes will be added again.
        Ovlech[] newOvlech = {new Ovlech(1)};
        overlijdensAkteFlow.setOvlech(newOvlech);
        overlijdensAkteFlow.setCurOvlechIndex(0);
    }

    /**
     * Register the echtgenoot, called after screen OS5.
     *
     * @param overlijdensAkteFlow The overlijdens akte flow state.
     */
    public void registerEchtgenoot(OverlijdensAkteFlowState overlijdensAkteFlow) {
        Ovlknd ovlknd = overlijdensAkteFlow.getOvlknd();
        Ovlech[] ovlech = overlijdensAkteFlow.getOvlech();

        Ovlech ovlechCur = overlijdensAkteFlow.getCurOvlech();
        ovlechCur.setIdnr(ovlknd.getIdnr());

        inputMetadata.saveToEntity(ovlechCur);
        ovlechCur = ovlechRepository.save(ovlechCur);

        // If this is the first one entered, then we also now know how many we can expect
        int curOvlechIndex = overlijdensAkteFlow.getCurOvlechIndex();
        if (curOvlechIndex == 0) {
            Ovlech[] newOvlech = new Ovlech[ovlknd.getMreovl()];
            for (int i = 0; i < newOvlech.length; i++) {
                newOvlech[i] = new Ovlech(i + 1);
            }

            // If we already have entered echtgenoten, fit into the new list
            if (overlijdensAkteFlow.isCorrection()) {
                for (int i = 0; i < ovlech.length; i++) {
                    if (i < newOvlech.length) {
                        newOvlech[i] = ovlech[i];
                    }
                    else {
                        ovlechRepository.delete(ovlech[i]);
                    }
                }
            }

            ovlech = newOvlech;
            saveOvlknd(overlijdensAkteFlow);
        }

        ovlech[curOvlechIndex] = ovlechCur;
        overlijdensAkteFlow.setOvlech(ovlech);
    }

    /**
     * Register the check whether to add byz, called after screen OS6.
     *
     * @param overlijdensAkteFlow The overlijdens akte flow state.
     */
    public void registerByzCheck(OverlijdensAkteFlowState overlijdensAkteFlow) {
        saveOvlknd(overlijdensAkteFlow);
    }

    /**
     * Deletes the current overlijdens akte.
     *
     * @param akteFlow The akte flow state.
     */
    @Override
    public void deleteAkte(AkteFlowState akteFlow) {
        OverlijdensAkteFlowState overlijdensAkteFlow = (OverlijdensAkteFlowState) akteFlow;

        ovlkndRepository.delete(overlijdensAkteFlow.getOvlknd());
        ovlagvRepository.delete(Arrays.asList(overlijdensAkteFlow.getOvlagv()));
        ovlechRepository.delete(Arrays.asList(overlijdensAkteFlow.getOvlech()));
        ovlbyzRepository.delete(overlijdensAkteFlow.getOvlbyz());
    }

    /**
     * Save the given byz field.
     *
     * @param byzAkteFlowState Akte flow state.
     * @param byz              The byz.
     */
    @Override
    protected void saveByz(ByzAkteFlowState byzAkteFlowState, Byz byz) {
        OverlijdensAkteFlowState overlijdensAkteFlowState = (OverlijdensAkteFlowState) byzAkteFlowState;
        Ovlbyz ovlbyz = (Ovlbyz) byz;

        ovlbyz.setIdnr(overlijdensAkteFlowState.getOvlknd().getIdnr());
        inputMetadata.saveToEntity(ovlbyz);
        ovlbyz = ovlbyzRepository.save(ovlbyz);

        overlijdensAkteFlowState.addToOvlbyz(ovlbyz);
    }

    /**
     * Delete the given byz field.
     *
     * @param byzAkteFlowState Akte flow state.
     * @param byz              The byz.
     */
    @Override
    protected void deleteByz(ByzAkteFlowState byzAkteFlowState, Byz byz) {
        OverlijdensAkteFlowState overlijdensAkteFlowState = (OverlijdensAkteFlowState) byzAkteFlowState;
        Ovlbyz ovlbyz = (Ovlbyz) byz;

        ovlbyzRepository.delete(ovlbyz);

        overlijdensAkteFlowState.removeFromOvlbyz(ovlbyz);
    }

    /**
     * Creates a new overlijdens akte flow state with all required domain objects.
     *
     * @return A new overlijdens akte flow state.
     */
    private OverlijdensAkteFlowState createNewAkte() {
        Ovlknd ovlknd = new Ovlknd();
        Ovlagv[] ovlagv = {new Ovlagv(1), new Ovlagv(2)}; // At most two aangevers
        Ovlech[] ovlech = {new Ovlech(1)}; // Start with one echtgenoot
        Set<Ovlbyz> ovlbyz = new HashSet<>();

        return new OverlijdensAkteFlowState(ovlknd, ovlagv, ovlech, ovlbyz);
    }

    /**
     * Persists the ovlknd record (main death certificate record) to the database.
     *
     * @param overlijdensAkteFlow The overlijdens akte flow state.
     */
    private void saveOvlknd(OverlijdensAkteFlowState overlijdensAkteFlow) {
        Ovlknd ovlknd = overlijdensAkteFlow.getOvlknd();
        inputMetadata.saveToEntity(ovlknd);
        ovlknd = ovlkndRepository.save(ovlknd);
        overlijdensAkteFlow.setOvlknd(ovlknd);
    }
}