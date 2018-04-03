package BEAlgorithm.admin;

import BEAlgorithm.entity.AdminParamPair;
import BEAlgorithm.entity.MasterKey;
import BEAlgorithm.entity.PublicParams;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.jpbc.PairingParameters;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;
import it.unisa.dia.gas.plaf.jpbc.pairing.e.TypeECurveGenerator;

/**
 * Created by jessy on 2017/10/21.
 */
public class SetupParams {

    protected static AdminParamPair setup(int rBits, int qBits){
        TypeECurveGenerator pg = new TypeECurveGenerator(rBits, qBits);
        PairingParameters pairingParameters = pg.generate();
        Pairing pairing = PairingFactory.getPairing(pairingParameters);

        PublicParams params = new PublicParams();
        params.setPairing(pairing);
        params.setG1(pairing.getG1());
        params.setGT(pairing.getGT());
        params.setZp(pairing.getZr());

        Element g = params.getG1().newRandomElement().duplicate().getImmutable();
        Element h = params.getG1().newRandomElement().duplicate().getImmutable();

        params.setG(g);
        params.setH(h);

        MasterKey mk = new MasterKey();
        Element r = params.getZp().newRandomElement().duplicate().getImmutable();
        mk.setR(r);

        AdminParamPair paramPair = new AdminParamPair();
        paramPair.setPp(params);
        paramPair.setMk(mk);

        return paramPair;
    }

}
