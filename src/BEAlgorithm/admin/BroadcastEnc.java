package BEAlgorithm.admin;

import BEAlgorithm.entity.AdminParamPair;
import BEAlgorithm.entity.BroadcastCipher;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;
import it.unisa.dia.gas.jpbc.Pairing;

/**
 * Created by jessy on 2017/10/21.
 */
public class BroadcastEnc {
    protected static BroadcastCipher encrypt(String[] identies, AdminParamPair paramPair){
        //check identity
        Field<Element> Zp = paramPair.getPp().getZp();
        Element k1 = Zp.newRandomElement().duplicate().getImmutable();
        Element k2 = Zp.newRandomElement().duplicate().getImmutable();

        Field<Element> G = paramPair.getPp().getG1();
        Element g = paramPair.getPp().getG().duplicate().getImmutable();
        Element h = paramPair.getPp().getH().duplicate().getImmutable();

        Pairing pairing =  paramPair.getPp().getPairing();
        Element e =pairing.pairing(g, h).duplicate().getImmutable();
        Element K1 = e.powZn(k1).duplicate().getImmutable();
        Element K2 = e.powZn(k2).duplicate().getImmutable();

        System.out.println("K1 = = = = " + K1);
        System.out.println("K2 = = = = " + K2);

        int id_len = identies.length;
        Element[] hash_identityList = new Element[id_len];
        for(int i=0; i<id_len; i++){
            hash_identityList[i] = Zp.newElement().setFromHash(identies[i].getBytes(), 0, identies[i].getBytes().length);
        }

        Element mul_hash_value = Zp.newElement(1).duplicate().getImmutable();
        for (int i=0; i<id_len; i++){
            mul_hash_value = mul_hash_value.duplicate().getImmutable().mulZn(hash_identityList[i].duplicate().getImmutable()).duplicate().getImmutable();
        }

        //for CT3
        Element key_r = paramPair.getMk().getR().duplicate().getImmutable();
        Element mul_hash_r_value = Zp.newElement(1).duplicate().getImmutable();
        Element temp = null;
        for (int i=0; i<id_len; i++){
            temp = key_r.duplicate().getImmutable().add(hash_identityList[i]).duplicate().getImmutable();
            mul_hash_r_value = mul_hash_r_value.duplicate().getImmutable().mulZn(temp).duplicate().getImmutable();
        }

        Element[] no_i_mul_hash_r_value = new Element[id_len];
        Element temp_no_i = null;
        for(int j=0; j<id_len; j++) {
            no_i_mul_hash_r_value[j] = Zp.newElement(1).duplicate().getImmutable();
            for (int i = 0; i < id_len; i++){
                if(j == i)
                    continue;
                temp_no_i = key_r.duplicate().getImmutable().add(hash_identityList[i]).duplicate().getImmutable();
                no_i_mul_hash_r_value[j] = no_i_mul_hash_r_value[j].duplicate().getImmutable().mulZn(temp_no_i).duplicate().getImmutable();

            }
        }
        Element[] a = new Element[id_len];
        for (int i=0; i<id_len; i++){
            a[i] =
                no_i_mul_hash_r_value[i].duplicate().getImmutable().sub(mul_hash_value).duplicate().getImmutable();
        }

        //CT1
        Element CT1 = Zp.newElement(1).duplicate().getImmutable()
                .div(mul_hash_value);

        //CT2
        Element CT2_1 = h.duplicate().getImmutable()
                .powZn(k1.duplicate().getImmutable().mul(mul_hash_r_value).duplicate().getImmutable())
                .duplicate().getImmutable();

        Element CT2_2 = h.duplicate().getImmutable()
                .powZn(k2.duplicate().getImmutable().mul(mul_hash_r_value).duplicate().getImmutable())
                .duplicate().getImmutable();

        //CT3 k1
        Element zero = Zp.newElement(0).duplicate().getImmutable();
        Element pairing1_e1 = g.duplicate().getImmutable()
                .powZn(key_r.duplicate().getImmutable()
                        .mul(zero.sub(k1.duplicate().getImmutable())))
                .duplicate().getImmutable().duplicate().getImmutable();
        Element one = Zp.newElement(1).duplicate().getImmutable();
        Element[] pairing_e2 = new Element[id_len];
        Element[] CT3_1 = new Element[id_len];
        for (int i=0; i<id_len; i++){
            pairing_e2[i] = h.duplicate().getImmutable()
                .powZn(a[i].duplicate().getImmutable().div(key_r.duplicate().getImmutable())).duplicate().getImmutable();
            CT3_1[i] = pairing.pairing(pairing1_e1, pairing_e2[i]);
        }

        Element pairing2_e1 = g.duplicate().getImmutable()
                .powZn(key_r.duplicate().getImmutable()
                        .mul(zero.sub(k2.duplicate().getImmutable())))
                .duplicate().getImmutable();

        Element[] CT3_2 = new Element[id_len];
        for (int i=0; i<id_len; i++){
            CT3_2[i] = pairing.pairing(pairing2_e1, pairing_e2[i]);
        }


        BroadcastCipher cipher = new BroadcastCipher();
        cipher.setCT1(CT1);
        cipher.setCT2_1(CT2_1);
        cipher.setCT2_2(CT2_2);
        cipher.setCT3_1(CT3_1);
        cipher.setCT3_2(CT3_2);
        return cipher;
    }

}
