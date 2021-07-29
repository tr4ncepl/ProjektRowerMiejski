package rental;


import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Named("wypozyczenieNamed")
@SessionScoped
public class WypozyczenieNamed implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    WypozyczenieBean wypozyczenieBean;

    @EJB
    private MessageBean messageBean;

    WypozyczenieEntity wypozyczenieEntity = new WypozyczenieEntity();
    RowerEntity rowerEntity = new RowerEntity();
    PunktEntity punktEntity = new PunktEntity();
    KlientEntity klientEntity = new KlientEntity();

    public void setWypozyczenieEntity(WypozyczenieEntity wypozyczenieEntity) {this.wypozyczenieEntity=wypozyczenieEntity;}
    public WypozyczenieEntity getWypozyczenieEntity() {return wypozyczenieEntity;}

    public void setRowerEntity(RowerEntity rowerEntity) {this.rowerEntity=rowerEntity;}
    public RowerEntity getRowerEntity() {return rowerEntity;}

    public void setPunktEntity(PunktEntity punktEntity) {this.punktEntity=punktEntity;}
    public PunktEntity getPunktEntity() {return  punktEntity;}

    public void setKlientEntity(KlientEntity klientEntity) {this.klientEntity=klientEntity;}
    public KlientEntity getKlientEntity() {return klientEntity;}


    public String getMessageBean() {return messageBean.getContent();}





    public void addWypozyczenie(KlientEntity klientEntity, Timestamp timestamp, RowerEntity rowerEntity, PunktEntity punktEntity)
    {
        double kwota = klientEntity.getSaldo();
        if(kwota<10)
        {
            messageBean.setContent("Musisz posiadac przynajmniej 10 zl na koncie by moc wypozyczyc");
        }
        else {
            wypozyczenieEntity.setKlientEntity(klientEntity);
            wypozyczenieEntity.setDataWyp(timestamp);
            wypozyczenieEntity.setRowerEntity(rowerEntity);
            wypozyczenieEntity.setPunktEntity(punktEntity);

            wypozyczenieBean.addNewWypozyczenie(wypozyczenieEntity);

            messageBean.setContent("Wypożyczenie pomyślne, twój rower został odblokowany");
        }
    }

    public List<WypozyczenieEntity> getTrwajaceWyp()
    {
        return wypozyczenieBean.getTrwajaceWypz();
    }

    public List<WypozyczenieEntity> getWypList() {return wypozyczenieBean.getWypList();}

    public void reduceBalance(WypozyczenieEntity wypozyczenieEntity, KlientEntity klientEntity)
    {
        wypozyczenieBean.reduceBalance(klientEntity,wypozyczenieEntity);
    }


    public List<WypozyczenieEntity> getWypozyczenieByKlient(KlientEntity klientEntity) {
        return wypozyczenieBean.getWypozyczenieByKlient(klientEntity);
    }

    public List<WypozyczenieEntity> getWiekszeSaldo(KlientEntity klientEntity)
    {
        return wypozyczenieBean.getWiekszeSaldo(klientEntity);
    }


    public List<WypozyczenieEntity> getDzisiejszeWypList()
    {
        return wypozyczenieBean.getDzisiejszeWypList();
    }

    public List<WypozyczenieEntity> getWypByKlient()
    {
        return wypozyczenieBean.getWypByKlient(klientEntity);
    }

    public void updateWypozyczenieEntity(WypozyczenieEntity wypozyczenieEntity,Timestamp timestamp)
    {
        wypozyczenieBean.updateWypozyczenieEntity(wypozyczenieEntity,timestamp );
    }

}
