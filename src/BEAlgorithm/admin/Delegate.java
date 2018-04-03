package BEAlgorithm.admin;

import BEAlgorithm.entity.DelegateID;
import BEAlgorithm.entity.MasterKey;
import BEAlgorithm.entity.PublicParams;
import it.unisa.dia.gas.jpbc.Element;

/**
 * Created by jessy on 2017/10/21.
 */
public class Delegate {

    protected static DelegateID delegate(String identity, PublicParams params, MasterKey key){
        Element g = params.getG().duplicate().getImmutable();

        Element hash1_value = params.getZp().newElement().setFromHash(identity.getBytes(), 0, identity.getBytes().length).duplicate().getImmutable();
        Element mk_r = key.getR().duplicate().getImmutable();

        Element one = params.getZp().newElement(1).duplicate().getImmutable();
        Element pow_value = one.div(mk_r.add(hash1_value).duplicate().getImmutable()).duplicate().getImmutable();

        Element delegate_key = g.powZn(pow_value).duplicate().getImmutable();

        DelegateID delegateID = new DelegateID();
        delegateID.setD(delegate_key);
        return delegateID;

    }


}
