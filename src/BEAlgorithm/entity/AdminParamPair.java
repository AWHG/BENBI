package BEAlgorithm.entity;

/**
 * Created by jessy on 2017/10/21.
 */
public class AdminParamPair {
    private PublicParams pp;
    private MasterKey mk;

    public PublicParams getPp() {
        return pp;
    }

    public void setPp(PublicParams pp) {
        this.pp = pp;
    }

    public MasterKey getMk() {
        return mk;
    }

    public void setMk(MasterKey mk) {
        this.mk = mk;
    }
}
