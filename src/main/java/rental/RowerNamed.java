package rental;


import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Named("rowerNamed")
@SessionScoped
public class RowerNamed implements Serializable {

    public static final long serialVersionUID = 1L;


    @EJB
    RowerBean rowerBean;

    @EJB
    private MessageBean messageBean;

    RowerEntity rowerEntity = new RowerEntity();

    PunktEntity punktEntity = new PunktEntity();

    NaprawyEntity naprawyEntity = new NaprawyEntity();


    public void setRowerEntity(RowerEntity rowerEntity) {this.rowerEntity=rowerEntity;}
    public RowerEntity getRowerEntity() { return rowerEntity;}

    public void setPunktEntity(PunktEntity punktEntity) {this.punktEntity=punktEntity;}
    public PunktEntity getPunktEntity() {return punktEntity;}

    public void setNaprawyEntity(NaprawyEntity naprawyEntity) {this.naprawyEntity=naprawyEntity;}
    public NaprawyEntity getNaprawyEntity() {return naprawyEntity;}


    public String getMessageBean() {return messageBean.getContent();}

    public List<RowerEntity> getRowerEntityList() {return rowerBean.getRowerEntityList();}

    public List<PunktEntity> getPunktEntityList() {return rowerBean.getPunktEntityList();}

    public List<RowerEntity> getWolneRoweryList() {return rowerBean.getWolneRoweryList(punktEntity);}
/*
    public List<RowerEntity> getRowerEntityListByPunkt(PunktEntity punktEntity)
    {
        return rowerBean.getRowerEntityListByPunkt(punktEntity);
    }
*/
    public void resetRowerEntity() {rowerEntity = new RowerEntity();}

    public void resetNaprawyEntity() {naprawyEntity = new NaprawyEntity();}

    public List<RowerEntity> getRowerByPunkt() {return rowerBean.getRowerByPunkt(punktEntity);}

    public List<NaprawyEntity> getNaprawyList() {return rowerBean.getNaprawyList();}

    public List<NaprawyEntity> getNaprListByRower() {return rowerBean.getNaprListByRower(rowerEntity);}

    public List<NaprawyEntity> getHistoriaNaprawList() {return  rowerBean.getHistoriaNaprawList();}

    public List<PunktEntity> getCountRowerByPunkt()
    {
        return rowerBean.getCountRowerByPunkt();
    }

    public List<RowerEntity> getRowerStats()
    {
        return rowerBean.getRowerStats();
    }

    public List<PunktEntity> getPunktStats(){
        return rowerBean.getPunktStats();
    }

    public List<RowerEntity> getRowerNaprawyStats()
    {
        return rowerBean.getRowerNaprawyStats();
    }


    public void addNewRower()
    {
        rowerBean.addNewRower(rowerEntity,punktEntity);
    }

    public void setRowerToPunkt() {rowerBean.setRowerToPunkt(rowerEntity,punktEntity);}

    public void addRower()
    {
        if(rowerEntity.getTyp().isEmpty()
        ||rowerEntity.getNazwa().isEmpty())
        {
            messageBean.setContent("pola musza byc uzupelnione");
        }rowerBean.addRower(rowerEntity);
    }

    public void addPunkt()
    {
        if(punktEntity.getAdres().isEmpty())
        {
            messageBean.setContent("Pole nie moze byc puste");
        }

        rowerBean.addPunkt(punktEntity);

    }

    public void addNewNaprawa(RowerEntity rowerEntity, Date date)
    {
        naprawyEntity.setRowerEntity(rowerEntity);
        naprawyEntity.setDt(date);

        rowerBean.addNewNaprawa(naprawyEntity);
    }

    public void deleteRower(RowerEntity rowerEntity)
    {
        rowerBean.deleteRowerEntity(rowerEntity);
        messageBean.setContent("Rower"+rowerEntity.getNazwa()+"zostal usuniety");
    }

    public void setWypRower(RowerEntity rowerEntity)
    {
        rowerBean.setWypStatRower(rowerEntity);
    }


    public void setZwrotRower(RowerEntity rowerEntity) {
        rowerBean.setZwrotStatRower(rowerEntity);
    }

    public void setNaprawaRower(RowerEntity rowerEntity) {rowerBean.setNaprawaStatRower(rowerEntity);}




}
