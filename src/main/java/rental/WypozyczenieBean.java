package rental;


import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Timestamp;
import java.util.List;
@Named("wypozyczenieBean")
@Stateless
@LocalBean
public class WypozyczenieBean {

    @PersistenceContext(unitName = "NewPersistenceUnit")
    EntityManager entityManager;

    @EJB
    private MessageBean messageBean;

    public String getMessageBean() {return messageBean.getContent();}



    public List<WypozyczenieEntity> getTrwajaceWypz()
    {
        List<WypozyczenieEntity> entities = entityManager.createQuery("SELECT w From Wypozyczenie w where w.dataZwrot is null ",WypozyczenieEntity.class).getResultList();
        return entities;
    }


    public List<WypozyczenieEntity> getDzisiejszeWypList()
    {
        List<WypozyczenieEntity> dzisiaj = entityManager.createNativeQuery("SELECT w.id, w.datawyp, w.datazwrot, d.imie, d.nazwisko, r.id_rower, r.nazwa, w.czas, w.oplata\n" +
                "FROM Wypozyczenie w, Klient k, DaneKlient d, Rower r\n" +
                "WHERE w.KLIENTENTITY_ID=k.id\n" +
                "AND w.ROWERENTITY_id_rower=r.id_rower\n" +
                "AND k.DANEKLIENTENTITY_ID=d.ID\n" +
                "AND CONVERT(date, w.datawyp) = CONVERT(date, getdate())").getResultList();
        return dzisiaj;
    }

    public List<WypozyczenieEntity> getWypList()
    {
        List<WypozyczenieEntity> entities = entityManager.createQuery("SELECT w From Wypozyczenie w",WypozyczenieEntity.class).getResultList();
        return entities;
    }

    public void addNewWypozyczenie(WypozyczenieEntity wypozyczenieEntity)
    {
        KlientEntity klientEntity = entityManager.find(KlientEntity.class,wypozyczenieEntity.getKlientEntity().getId());


        wypozyczenieEntity.setKlientEntity(klientEntity);
        RowerEntity rowerEntity = entityManager.find(RowerEntity.class,wypozyczenieEntity.getRowerEntity().getIdRower());
        rowerEntity.setStatus("Wyp");
        entityManager.persist(rowerEntity);
        wypozyczenieEntity.setRowerEntity(rowerEntity);
        PunktEntity punktEntity = entityManager.find(PunktEntity.class,wypozyczenieEntity.getPunktEntity().getIdPunkt());
        wypozyczenieEntity.setPunktEntity(punktEntity);
        entityManager.persist(wypozyczenieEntity);
        entityManager.flush();

    }

    public List<WypozyczenieEntity> getWypozyczenieByKlient(KlientEntity klientEntity)
    {
        List<WypozyczenieEntity> entities = entityManager.createQuery("SELECT w FROM Wypozyczenie  w, Klient k WHERE k.id=?1 and w.klientEntity=k and (w.czas is null)")
                .setParameter(1,klientEntity.getId())
                .getResultList();
        return entities;
    }

    public List<WypozyczenieEntity> getWiekszeSaldo(KlientEntity klientEntity)
    {
        List<WypozyczenieEntity> check = entityManager.createNativeQuery("SELECT w.id,d.imie,d.nazwisko, DATEDIFF(MINUTE,w.datawyp,GETDATE()) as czas\n" +
                "FROM Wypozyczenie w, Klient k, DaneKlient d\n" +
                "WHERE w.KLIENTENTITY_ID=k.ID\n" +
                "AND DATEDIFF(MINUTE,w.datawyp,GETDATE())*0.05>k.saldo\n" +
                "AND k.DANEKLIENTENTITY_ID=d.ID\n" +
                "AND w.datazwrot IS NULL\n" +
                "AND k.ID=?1")
                .setParameter(1,klientEntity.getId())
                .getResultList();
        return check;
    }


    public List<WypozyczenieEntity> getWypByKlient(KlientEntity klientEntity)
    {
        List<WypozyczenieEntity> wypList = entityManager.createQuery("SELECT w FROM Wypozyczenie w, Klient k WHERE k.id=?1 and w.klientEntity=k")
                .setParameter(1,klientEntity.getId())
                .getResultList();
        return wypList;
    }


    public void updateWypozyczenieEntity(WypozyczenieEntity wypozyczenieEntity, Timestamp timestamp)
    {
        WypozyczenieEntity entity = entityManager.find(WypozyczenieEntity.class,wypozyczenieEntity.getId());

        entity.setDataZwrot(timestamp);
        Timestamp time1 = entity.getDataWyp();
        Timestamp time2 = entity.getDataZwrot();
        long timeinmills = time1.getTime();
        long timeinmills1 = time2.getTime();

        long differenceInMin = (timeinmills1-timeinmills)/60000;

        entity.setCzas(differenceInMin);

        double cena = differenceInMin * 0.05;

        entity.setOplata(cena);

        entityManager.persist(entity);
        entityManager.flush();
    }

    public void reduceBalance(KlientEntity klientEntity, WypozyczenieEntity wypozyczenieEntity)
    {
        KlientEntity klEntity = entityManager.find(KlientEntity.class,klientEntity.getId());

        WypozyczenieEntity wypEntity = entityManager.find(WypozyczenieEntity.class,wypozyczenieEntity.getId());

        double oplw = wypEntity.getOplata();

        double saldok = klEntity.getSaldo();

        double roznica = saldok-oplw;


        klEntity.setSaldo(roznica);

        entityManager.persist(klEntity);

    }


}
