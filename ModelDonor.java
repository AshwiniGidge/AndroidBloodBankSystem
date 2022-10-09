package in.bbms.donor;

public class ModelDonor {


    public ModelDonor(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBlood() {
        return blood;
    }

    public void setBlood(String blood) {
        this.blood = blood;
    }


    String id, name, blood,srno;

    public ModelDonor(String srno,String id, String name, String blood) {
this.srno=srno;
        this.id = id;
        this.name = name;
        this.blood = blood;
    }
    public String getsrno() {
        return srno;
    }

    public void setsrno(String srno) {
        this.srno = srno;
    }
}
