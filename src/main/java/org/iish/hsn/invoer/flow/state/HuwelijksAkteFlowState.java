package org.iish.hsn.invoer.flow.state;

import org.iish.hsn.invoer.domain.invoer.Byz;
import org.iish.hsn.invoer.domain.invoer.huw.*;
import org.iish.hsn.invoer.domain.reference.Ref_GBH;
import org.iish.hsn.invoer.domain.reference.Ref_RP;
import org.iish.hsn.invoer.flow.helper.HuwelijksAkteViewNames;
import org.iish.hsn.invoer.flow.helper.ViewNames;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * Holds the state of a 'huwelijks akte' (marriage certificate) during the flow.
 */
public class HuwelijksAkteFlowState extends ByzAkteFlowState implements Serializable {
    //private Ref_GBH refGbh;
    private Ref_RP refRp;
    private Huwttl huwttl;
    private Huwknd huwknd;

    private List<Huwafk> huwafk;
    private List<Huweer> huweerGroom;
    private List<Huweer> huweerBride;
    private Huwvrknd[]   huwvrknd;
    private Huwgtg[]     huwgtg;

    private Set<Huwbyz> huwbyz;

    private Huweer curHuweer;
    private int curHuweerGroomIndex = -1;
    private int curHuweerBrideIndex = -1;

    private Huwvrknd curHuwvrknd;
    private int curHuwvrkndIndex = 0;

    private Huwgtg curHuwgtg;
    private int curHuwgtgIndex = -1;

    private String moreOfGroom = "j";
    private String moreOfBride = "j";

    public HuwelijksAkteFlowState(Huwttl huwttl, Huwknd huwknd, List<Huwafk> huwafk, List<Huweer> huweerGroom,
                                  List<Huweer> huweerBride, Huwvrknd[] huwvrknd, Huwgtg[] huwgtg, Set<Huwbyz> huwbyz) {
        this.huwttl = huwttl;
        this.huwknd = huwknd;
        this.huwafk = huwafk;
        this.huweerGroom = huweerGroom;
        this.huweerBride = huweerBride;
        this.huwvrknd = huwvrknd;
        this.huwgtg = huwgtg;
        this.huwbyz = huwbyz;

        this.curHuwvrknd = this.huwvrknd[this.curHuwvrkndIndex];
    }

    /*public Ref_GBH getRefGbh() {
        return refGbh;
    }

    public void setRefGbh(Ref_GBH refGbh) {
        this.refGbh = refGbh;
    }*/

    public Ref_RP getRefRp() {
        return refRp;
    }

    public void setRefRp(Ref_RP refRp) {
        this.refRp = refRp;
    }

    public Huwttl getHuwttl() {
        return huwttl;
    }

    public void setHuwttl(Huwttl huwttl) {
        this.huwttl = huwttl;
    }

    public Huwknd getHuwknd() {
        return huwknd;
    }

    public void setHuwknd(Huwknd huwknd) {
        this.huwknd = huwknd;
    }

    public List<Huwafk> getHuwafk() {
        return huwafk;
    }

    public void setHuwafk(List<Huwafk> huwafk) {
        this.huwafk = huwafk;
    }

    public List<Huweer> getHuweerGroom() {
        return huweerGroom;
    }

    public void setHuweerGroom(List<Huweer> huweerGroom) {
        this.huweerGroom = huweerGroom;
    }

    public List<Huweer> getHuweerBride() {
        return huweerBride;
    }

    public void setHuweerBride(List<Huweer> huweerBride) {
        this.huweerBride = huweerBride;
    }

    public Huwvrknd[] getHuwvrknd() {
        return huwvrknd;
    }

    public void setHuwvrknd(Huwvrknd[] huwvrknd) {
        this.huwvrknd = huwvrknd;
    }

    public Huwgtg[] getHuwgtg() {
        return huwgtg;
    }

    public void setHuwgtg(Huwgtg[] huwgtg) {
        this.huwgtg = huwgtg;
    }

    public Set<Huwbyz> getHuwbyz() {
        return huwbyz;
    }

    public void setHuwbyz(Set<Huwbyz> huwbyz) {
        this.huwbyz = huwbyz;
    }

    public void addToHuwbyz(Huwbyz instance) {
        huwbyz.add(instance);
    }

    public void removeFromHuwbyz(Huwbyz instance) {
        huwbyz.remove(instance);
    }

    public Huweer getCurHuweer() {
        return curHuweer;
    }

    public int getCurHuweerGroomIndex() {
        return curHuweerGroomIndex;
    }

    public void setCurHuweerGroomIndex(int curHuweerGroomIndex) {
        if (curHuweerGroomIndex < this.huweerGroom.size()) {
            this.curHuweerGroomIndex = curHuweerGroomIndex;
            this.curHuweer = this.huweerGroom.get(curHuweerGroomIndex);
        }
    }

    public int getCurHuweerBrideIndex() {
        return curHuweerBrideIndex;
    }

    public void setCurHuweerBrideIndex(int curHuweerBrideIndex) {
        if (curHuweerBrideIndex < this.huweerBride.size()) {
            this.curHuweerBrideIndex = curHuweerBrideIndex;
            this.curHuweer = this.huweerBride.get(curHuweerBrideIndex);
        }
    }

    public String getMoreOfGroom() {
        return moreOfGroom;
    }

    public void setMoreOfGroom(String moreOfGroom) {
        this.moreOfGroom = moreOfGroom;
    }

    public String getMoreOfBride() {
        return moreOfBride;
    }

    public void setMoreOfBride(String moreOfBride) {
        this.moreOfBride = moreOfBride;
    }

    public Huwvrknd getCurHuwvrknd() {
        return curHuwvrknd;
    }

    public int getCurHuwvrkndIndex() {
        return curHuwvrkndIndex;
    }

    public void setCurHuwvrkndIndex(int curHuwvrkndIndex) {
        if (curHuwvrkndIndex < this.huwvrknd.length) {
            this.curHuwvrkndIndex = curHuwvrkndIndex;
            this.curHuwvrknd = huwvrknd[curHuwvrkndIndex];
        }
    }

    public Huwgtg getCurHuwgtg() {
        return curHuwgtg;
    }

    public int getCurHuwgtgIndex() {
        return curHuwgtgIndex;
    }

    public void setCurHuwgtgIndex(int curHuwgtgIndex) {
        if (curHuwgtgIndex < this.huwgtg.length) {
            this.curHuwgtgIndex = curHuwgtgIndex;
            this.curHuwgtg = this.huwgtg[curHuwgtgIndex];
        }
    }

    @Override
    public Byz[] createNewByz() {
        Byz[] newByz = new Byz[5];
        for (int i = 0; i < 5; i++) {
            Huwbyz byz = new Huwbyz();
            if (i == 0) {
                ViewNames viewNames = new HuwelijksAkteViewNames(getViewStateHistory(), getCurHuwgtgIndex());
                byz.setByz(viewNames.getPrevByzViewName() + ": ");
            }
            newByz[i] = byz;
        }
        return newByz;
    }

    @Override
    public Byz[] selectNewByzForCorrection() {
        Byz[] newByz = new Byz[5];
        for (int i = 0; i < 5; i++) {
            Huwbyz byz = new Huwbyz();

            // See if we have an existing byz with the correct byzNr
            for (Huwbyz curHuwbyz : this.huwbyz) {
                if (curHuwbyz.getByznr() == ((i + 1) + (getLastByzRound() * 5))) {
                    byz = curHuwbyz;
                }
            }

            newByz[i] = byz;
        }
        return newByz;
    }
}
