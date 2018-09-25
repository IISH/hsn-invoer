package org.iish.hsn.invoer.service.akte;

import org.iish.hsn.invoer.domain.invoer.mil.Career;
import org.iish.hsn.invoer.domain.invoer.mil.Milition;
import org.iish.hsn.invoer.domain.invoer.mil.Verdict;
import org.iish.hsn.invoer.domain.reference.Ref_RP;
import org.iish.hsn.invoer.exception.AkteException;
import org.iish.hsn.invoer.exception.NotFoundException;
import org.iish.hsn.invoer.flow.state.MilitieregisterFlowState;
import org.iish.hsn.invoer.repository.invoer.mil.CareerRepository;
import org.iish.hsn.invoer.repository.invoer.mil.MilitionRepository;
import org.iish.hsn.invoer.repository.invoer.mil.VerdictRepository;
import org.iish.hsn.invoer.service.LookupService;
import org.iish.hsn.invoer.service.scan.MilitionScan;
import org.iish.hsn.invoer.service.scan.ScansService;
import org.iish.hsn.invoer.util.InputMetadata;
import org.iish.hsn.invoer.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Deals with the preparation and storage of various elements of the 'militieregister'.
 */
@Service
public class MilitieregisterService {
    @Autowired private InputMetadata inputMetadata;
    @Autowired private LookupService lookupService;
    @Autowired private MilitionRepository militionRepository;
    @Autowired private VerdictRepository verdictRepository;
    @Autowired private CareerRepository careerRepository;
    @Autowired private ScansService scansService;

    /**
     * Creates a new militieregister flow state for new input with all required domain objects.
     *
     * @return A new militieregister flow state.
     */
    public MilitieregisterFlowState createNewAkteForNewInput() {
        MilitieregisterFlowState militieregisterFlow = createNewAkte();
        militieregisterFlow.setCorrection(false);
        return militieregisterFlow;
    }

    /**
     * Creates a new militieregister flow state for correction with all required domain objects.
     *
     * @return A new militieregister flow state.
     */
    public MilitieregisterFlowState createNewAkteForCorrection() {
        MilitieregisterFlowState militieregisterFlow = createNewAkte();
        militieregisterFlow.setCorrection(true);
        return militieregisterFlow;
    }

    /**
     * Link the OP (onderzoekspersoon, research person) of whom the user has the milition register.
     *
     * @param militieregisterFlow The milition register flow state.
     * @throws AkteException Thrown when the OP was not found in the reference table.
     */
    public void registerOP(MilitieregisterFlowState militieregisterFlow) throws AkteException {
        try {
            Milition milition = militieregisterFlow.getMil();

            Ref_RP refRp = lookupService.getRefRp(milition.getIdnr(), true);
            militieregisterFlow.setRefRp(refRp);

            List<Milition> previouslyEntered = lookupService.getMilitions(milition.getIdnr());
            MilitionScan scan = scansService
                    .getMilitionScanRepository()
                    .findScanNotEntered(milition.getIdnr(), previouslyEntered);

            if (scan != null) {
                milition.setScanA((scan.getSideA() != null) ? scan.getSideA().getFileName().toString() : "");
                milition.setScanB((scan.getSideB() != null) ? scan.getSideB().getFileName().toString() : "");

                milition.setType((scan.getType() != null) ? scan.getType() : "N");
                if (scan.getMunicipality() != null)
                    milition.setMunicipality(scan.getMunicipality());
                if (scan.getYear() != null)
                    milition.setYear(scan.getYear());
                if (scan.getNumber() != null)
                    milition.setScanNumber(scan.getNumber());
            }

            milition.setSeq(previouslyEntered.size() + 1);
            milition.setIdnr(milition.getIdnr());
            milition.setSeq(milition.getSeq());

            milition.setDayOfBirth(refRp.getDayOfBirth());
            milition.setMonthOfBirth(refRp.getMonthOfBirth());
            milition.setYearOfBirth(refRp.getYearOfBirth());
            milition.setPlaceOfBirth(refRp.getNameMunicipality());

            milition.setFamilyName(refRp.getPrefixLastName());
            milition.setFirstName(refRp.getFirstName());

            milition.setFamilyNameFather(refRp.getPrefixLastNameFather());
            milition.setFirstNameFather(refRp.getFirstNameFather());

            milition.setFamilyNameMother(refRp.getPrefixLastNameMother());
            milition.setFirstNameMother(refRp.getFirstNameMother());

            String plaats = Utils.getGemeente(refRp, lookupService);
            if (plaats != null) {
                milition.setMunicipality(plaats);
            }

            if (milition.getYear() == 8888) {
                milition.setYearChoice(1);
            }
        }
        catch (NotFoundException | IOException e) {
            throw new AkteException(e);
        }
    }

    /**
     * Link the OP (onderzoekspersoon, research person) of whom the user has to edit the milition register.
     *
     * @param militieregisterFlow The milition register flow state.
     * @throws AkteException Thrown when the OP was not found in the reference table.
     */
    public void editOP(MilitieregisterFlowState militieregisterFlow) throws AkteException {
        try {
            Milition milition = militieregisterFlow.getMil();

            // If no sequence number is given, always pick the first one
            if (milition.getSeq() == 0)
                milition.setSeq(1);

            militieregisterFlow.setRefRp(lookupService.getRefRp(milition.getIdnr(), true));
            militieregisterFlow.setMil(lookupService.getMilition(milition.getIdnr(), milition.getSeq(), true));

            milition = militionRepository.findByIdnrAndSeqAndWorkOrder(
                    milition.getIdnr(), milition.getSeq(), inputMetadata.getWorkOrder());
            if (milition != null) {
                militieregisterFlow.setMil(milition);
            }

            List<Verdict> verdicts = verdictRepository.findByIdnrAndSeqAndWorkOrder(
                    milition.getIdnr(), milition.getSeq(), inputMetadata.getWorkOrder());
            if (verdicts != null) {
                for (Verdict verdict : verdicts) {
                    militieregisterFlow.getVerdict().put(Verdict.Type.getType(verdict.getType()), verdict);
                }
            }

            List<Career> professions = careerRepository.findByIdnrAndSeqAndTypeAndWorkOrder(
                    milition.getIdnr(), milition.getSeq(), Career.Type.BEROEP.getType(), inputMetadata.getWorkOrder());
            if (professions != null) {
                militieregisterFlow.setProfessions(new ArrayList<>(professions));
            }

            List<Career> educations = careerRepository.findByIdnrAndSeqAndTypeAndWorkOrder(
                    milition.getIdnr(), milition.getSeq(), Career.Type.ONDERWIJS.getType(), inputMetadata.getWorkOrder());
            if (educations != null) {
                militieregisterFlow.setEducations(new ArrayList<>(educations));
            }
        }
        catch (NotFoundException nfe) {
            throw new AkteException(nfe);
        }
    }

    /**
     * Registers the metadata of the milition register.
     *
     * @param militieregisterFlow The milition register flow state.
     */
    public void registerMetadata(MilitieregisterFlowState militieregisterFlow) {
        Milition mil = militieregisterFlow.getMil();
        String type = mil.getType();

        if (type.equals("F") || type.equals("M") || type.equals("D")) {
            mil.setYearChoice(5);
        }

        saveMil(militieregisterFlow);
    }

    /**
     * Persists the milition record to the database.
     *
     * @param militieregisterFlow The milition register flow state.
     */
    public void saveMil(MilitieregisterFlowState militieregisterFlow) {
        Milition mil = militieregisterFlow.getMil();
        inputMetadata.saveToEntity(mil);
        mil = militionRepository.save(mil);
        militieregisterFlow.setMil(mil);
    }

    /**
     * Process the verdicts, called after view state MS6.
     *
     * @param militieregisterFlow The militie register flow state.
     */
    public void processVerdicts(MilitieregisterFlowState militieregisterFlow) {
        for (Verdict verdict : militieregisterFlow.getVerdict().values()) {
            if ((verdict.getDayOfVerdict() == 0) && (verdict.getMonthOfVerdict() == 0)
                    && (verdict.getYearOfVerdict() == 0)) {
                verdictRepository.delete(verdict);
            }
            else {
                verdict.setIdnr(militieregisterFlow.getMil().getIdnr());
                verdict.setSeq(militieregisterFlow.getMil().getSeq());

                inputMetadata.saveToEntity(verdict);
                verdict = verdictRepository.save(verdict);
                militieregisterFlow.getVerdict().put(Verdict.Type.getType(verdict.getType()), verdict);
            }
        }

        saveMil(militieregisterFlow);
    }

    /**
     * Update the total number of career instances.
     *
     * @param militieregisterFlow The militie register flow state.
     * @param type                The career type to update.
     */
    public void updateCareer(MilitieregisterFlowState militieregisterFlow, Career.Type type) {
        List<Career> careerHistory = militieregisterFlow.getCareerForType(type);

        // First check how many we need
        int totalNumber = 0;
        for (Career career : careerHistory) {
            if ((career.getValue() == null) || career.getValue().trim().isEmpty()) {
                break;
            }
            totalNumber++;
        }

        // First add new instances to the list if necessary
        for (int i = careerHistory.size(); i < totalNumber; i++) {
            careerHistory.add(i, new Career(type.getType(), i + 1));
        }

        // Then remove the instances that fall out of range
        if (careerHistory.size() > totalNumber) {
            List<Career> toDelete = careerHistory.subList(totalNumber, careerHistory.size());
            careerRepository.delete(toDelete);

            careerHistory = new ArrayList<>(careerHistory.subList(0, totalNumber));
            militieregisterFlow.setCareerForType(type, careerHistory);
        }

        // Always add one empty one
        careerHistory.add(totalNumber, new Career(type.getType(), totalNumber + 1));
    }

    /**
     * Register the career history.
     *
     * @param militieregisterFlow The militie register flow state.
     * @param type                The career type to update.
     */
    public void registerCareer(MilitieregisterFlowState militieregisterFlow, Career.Type type) {
        Milition mil = militieregisterFlow.getMil();
        List<Career> careerHistory = militieregisterFlow.getCareerForType(type);

        for (int i = 0; i < (careerHistory.size() - 1); i++) {
            Career career = careerHistory.get(i);
            career.setIdnr(mil.getIdnr());
            career.setSeq(mil.getSeq());
            inputMetadata.saveToEntity(career);
            career = careerRepository.save(career);
            careerHistory.set(i, career);
        }

        // Make sure to delete the last one
        Career career = careerHistory.get(careerHistory.size() - 1);
        careerRepository.delete(career);

        careerHistory = new ArrayList<>(careerHistory.subList(0, careerHistory.size() - 1));
        militieregisterFlow.setCareerForType(type, careerHistory);
    }

    /**
     * Deletes the current militie register.
     *
     * @param militieregisterFlow The militie register flow state.
     */
    public void deleteAkte(MilitieregisterFlowState militieregisterFlow) {
        int idnr = militieregisterFlow.getMil().getIdnr();

        if (militieregisterFlow.getMil().getId() != null) {
            militionRepository.delete(militieregisterFlow.getMil());
        }
        verdictRepository.delete(militieregisterFlow.getVerdict().values());
        careerRepository.delete(militieregisterFlow.getProfessions());
        careerRepository.delete(militieregisterFlow.getEducations());

        // Renumber the sequences
        int seq = 1;
        for (Milition milition : lookupService.getMilitions(idnr)) {
            milition.setSeq(seq++);
            militionRepository.save(milition);
        }
    }

    /**
     * Deletes the current militie register.
     *
     * @param militieregisterFlow The militie register flow state.
     * @param bewust              Confirmation of the user to delete the akte.
     */
    public void deleteAkte(MilitieregisterFlowState militieregisterFlow, String bewust) {
        if ((bewust != null) && bewust.trim().equalsIgnoreCase("bewust")) {
            deleteAkte(militieregisterFlow);
        }
    }

    /**
     * Creates a new militieregister flow state with all required domain objects.
     *
     * @return A new militieregister flow state.
     */
    private MilitieregisterFlowState createNewAkte() {
        Milition milition = new Milition();

        Map<Verdict.Type, Verdict> verdict = new HashMap<>();
        for (Verdict.Type type : Verdict.Type.values()) {
            verdict.put(type, new Verdict(type.getType()));
        }

        List<Career> professions = new ArrayList<>();
        List<Career> educations = new ArrayList<>();

        return new MilitieregisterFlowState(milition, verdict, professions, educations);
    }
}