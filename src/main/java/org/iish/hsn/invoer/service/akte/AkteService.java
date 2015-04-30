package org.iish.hsn.invoer.service.akte;

import org.iish.hsn.invoer.domain.invoer.Byz;
import org.iish.hsn.invoer.flow.state.AkteFlowState;
import org.iish.hsn.invoer.flow.state.ByzAkteFlowState;
import org.iish.hsn.invoer.flow.state.HuwelijksAkteFlowState;

import java.util.Arrays;

/**
 * Deals primarily with the storage of the 'bijzonderheden'.
 */
public abstract class AkteService {
    /**
     * Prepares a new set of five byz fields.
     *
     * @param byzAkteFlowState Akte flow state.
     */
    public void prepareByz(ByzAkteFlowState byzAkteFlowState) {
        Byz[] curByz = byzAkteFlowState.createNewByz();
        byzAkteFlowState.setByz(curByz);
    }

    /**
     * Prepares a new set of five byz fields for correction.
     *
     * @param byzAkteFlowState Akte flow state.
     */
    public void prepareByzForCorrection(ByzAkteFlowState byzAkteFlowState) {
        Byz[] curByz = byzAkteFlowState.selectNewByzForCorrection();
        byzAkteFlowState.setByz(curByz);
    }

    /**
     * Saves the current set of five byz fields.
     *
     * @param byzAkteFlowState Akte flow state.
     */
    public void saveByz(ByzAkteFlowState byzAkteFlowState) {
        int byzRound = byzAkteFlowState.getLastByzRound();

        int byzNr = 1;
        for (Byz byz : byzAkteFlowState.getByz()) {
            String trimmedByz = byz.getByz().trim();
            if (!trimmedByz.isEmpty()) {
                byz.setByz(trimmedByz);
                byz.setByznr(byzNr + (byzRound * 5));
                byz.setScherm(byzAkteFlowState.getViewStateHistory().getPrevViewStateId());

                saveByz(byzAkteFlowState, byz);
            }
            else {
                deleteByz(byzAkteFlowState, byz);
            }

            byzNr++;
        }

        byzAkteFlowState.setLastByzRound(byzRound + 1);
    }

    /**
     * Deletes the current akte.
     *
     * @param akteFlow The akte flow state.
     * @param bewust   Confirmation of the user to delete the akte.
     */
    public void deleteAkte(AkteFlowState akteFlow, String bewust) {
        if ((bewust != null) && bewust.trim().equalsIgnoreCase("bewust")) {
            deleteAkte(akteFlow);
        }
    }

    /**
     * Deletes the current akte.
     *
     * @param akteFlow The akte flow state.
     */
    public abstract void deleteAkte(AkteFlowState akteFlow);

    /**
     * Save the given byz field.
     *
     * @param byzAkteFlowState Akte flow state.
     * @param byz              The byz.
     */
    protected abstract void saveByz(ByzAkteFlowState byzAkteFlowState, Byz byz);

    /**
     * Delete the given byz field.
     *
     * @param byzAkteFlowState Akte flow state.
     * @param byz              The byz.
     */
    protected abstract void deleteByz(ByzAkteFlowState byzAkteFlowState, Byz byz);
}
