package BEAlgorithm.entity;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.jpbc.Field;


/**
 * Created by jessy on 2017/10/21.
 */
public class PublicParams {

    private Pairing pairing;
    private Field<Element> G1;
    private Field<Element> GT;
    private Field<Element> Zp;
    private Element g;
    private Element h;

    public Pairing getPairing() {
        return pairing;
    }

    public void setPairing(Pairing pairing) {
        this.pairing = pairing;
    }

    public Field<Element> getG1() {
        return G1;
    }

    public void setG(Element g) {
        this.g = g;
    }

    public Element getG() {
        return g;
    }

    public Element getH() {
        return h;
    }

    public void setH(Element h) {
        this.h = h;
    }

    public void setG1(Field<Element> g1) {
        G1 = g1;
    }

    public Field<Element> getGT() {
        return GT;
    }

    public void setGT(Field<Element> GT) {
        this.GT = GT;
    }

    public Field<Element> getZp() {
        return Zp;
    }

    public void setZp(Field<Element> zp) {
        Zp = zp;
    }
}
