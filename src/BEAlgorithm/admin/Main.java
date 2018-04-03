package BEAlgorithm.admin;

import BEAlgorithm.app.EntityRegisterConfig;
import BEAlgorithm.entity.AdminParamPair;
import BEAlgorithm.entity.BroadEncKey;
import BEAlgorithm.entity.BroadcastCipher;
import BEAlgorithm.entity.DelegateID;
import SEAlgorithm.SEAlgorithm;

import java.util.UUID;

public class Main {

    //TEST
    public static void main(String[] args) {
        SetupParams setupParams = new SetupParams();
        AdminParamPair paramPair = setupParams.setup(160, 512);


        Delegate delegate = new Delegate();
        DelegateID delegateID_1 = delegate.delegate(EntityRegisterConfig.App1.toString(), paramPair.getPp(), paramPair.getMk());
        DelegateID delegateID_2 = delegate.delegate(EntityRegisterConfig.App2.toString(), paramPair.getPp(), paramPair.getMk());
        DelegateID delegateID_3 = delegate.delegate(EntityRegisterConfig.Contr.toString(), paramPair.getPp(), paramPair.getMk());

//      1=745 /cl=303  2=764 /cl=305  3=753  4=748 5=749  6=751  7=748 8=746 308  9= /cl=304   10= /cl=305
        int n=300;
        String[] idList = new String[n];
        /*idList[0]= EntityRegisterConfig.App1.toString();
        idList[1]= EntityRegisterConfig.App2.toString();
        idList[2]= EntityRegisterConfig.Contr.toString();*/
        idList[0] = EntityRegisterConfig.App1.toString();
        idList[1] = EntityRegisterConfig.App2.toString();
        for(int i=2; i<n; i++){
            idList[i] = UUID.randomUUID().toString();
        }

        BroadcastEnc enc = new BroadcastEnc();
        BroadcastCipher cipher = enc.encrypt(idList, paramPair);
        System.out.println(cipher.getCT1().toString().length()
        +cipher.getCT2_1().toString().length()
        +cipher.getCT2_2().toString().length()
        +cipher.getCT3_1().toString().length()
        +cipher.getCT3_2().toString().length());


        Descrypt descrypt = new Descrypt();
        BroadEncKey encKey1 = descrypt.descrypt(paramPair.getPp(), cipher, delegateID_1);
        BroadEncKey encKey2 = descrypt.descrypt(paramPair.getPp(), cipher, delegateID_2);

        System.out.println(encKey1.getK1().toString().length()+encKey1.getK2().toString().length());
        System.out.println(encKey2.getK1().toString().length()+encKey2.getK2().toString().length());



    }
}
