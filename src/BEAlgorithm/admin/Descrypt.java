package BEAlgorithm.admin;

import BEAlgorithm.entity.BroadEncKey;
import BEAlgorithm.entity.BroadcastCipher;
import BEAlgorithm.entity.DelegateID;
import BEAlgorithm.entity.PublicParams;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;

/**
 * Created by jessy on 2017/10/21.
 */
public class Descrypt {
    protected static BroadEncKey descrypt(PublicParams params,BroadcastCipher cipher, DelegateID d){
        Element[] CT3_1 = cipher.getCT3_1();
        Element[] CT3_2 = cipher.getCT3_2();

        Element CT2_1 = cipher.getCT2_1();
        Element CT2_2 = cipher.getCT2_2();

        Element CT1 = cipher.getCT1();

        Pairing pairing = params.getPairing();
        Element pairing_part_1 = pairing.pairing(CT2_1.duplicate().getImmutable(), d.getD().duplicate().getImmutable());
        Element K1 = (CT3_1[0].duplicate().getImmutable()
                .mul(pairing_part_1)).duplicate().getImmutable().powZn(CT1.duplicate().getImmutable());

        Element pairing_part_2 = pairing.pairing(CT2_2.duplicate().getImmutable(), d.getD().duplicate().getImmutable());
        Element K2 = (CT3_2[0].duplicate().getImmutable()
                .mul(pairing_part_2))
                .duplicate().getImmutable().powZn(CT1.duplicate().getImmutable());

        System.out.println("K1 = " + K1);
        System.out.println("K2 = " + K2);
        BroadEncKey encKey = new BroadEncKey();
        encKey.setK1(K1);
        encKey.setK2(K2);
        return encKey;
    }
}
