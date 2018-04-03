package BEAlgorithm.entity;

import it.unisa.dia.gas.jpbc.Element;

/**
 * Created by jessy on 2017/10/21.
 */
public class BroadcastCipher {
    private Element CT1;
    private Element CT2_1;
    private Element CT2_2;

    public Element getCT1() {
        return CT1;
    }

    public void setCT1(Element CT1) {
        this.CT1 = CT1;
    }

    public Element getCT2_1() {
        return CT2_1;
    }

    public void setCT2_1(Element CT2_1) {
        this.CT2_1 = CT2_1;
    }

    public Element getCT2_2() {
        return CT2_2;
    }

    public void setCT2_2(Element CT2_2) {
        this.CT2_2 = CT2_2;
    }
    private Element[] CT3_1 = null;
    private Element[] CT3_2 = null;

    public Element[] getCT3_1() {
        return CT3_1;
    }

    public void setCT3_1(Element[] CT3_1) {
        if(CT3_1 != null){
            this.CT3_1 = new Element[CT3_1.length];
            for(int i=0; i<CT3_1.length; i++){
                this.CT3_1[i] = CT3_1[i];
            }
        }
    }

    public void setCT3_2(Element[] CT3_2) {
        if(CT3_2 != null){
            this.CT3_2 = new Element[CT3_2.length];
            for(int i=0; i<CT3_2.length; i++){
                this.CT3_2[i] = CT3_2[i];
            }
        }
    }

    public Element[] getCT3_2() {
        return this.CT3_2;
    }
}
