package rental;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
@Stateless
@LocalBean
public class RowerBean {


    @PersistenceContext(unitName = "NewPersistenceUnit")
    EntityManager entityManager;

    public void addRower(RowerEntity rowerEntity)
    {
        entityManager.persist(rowerEntity);
        entityManager.flush();
    }

    public void addNewRower(RowerEntity rowerEntity, PunktEntity punktEntity)
    {
        PunktEntity entity = entityManager.find(PunktEntity.class,punktEntity.getIdPunkt());
        rowerEntity.setPunktEntity(entity);
        entityManager.persist(rowerEntity);
        entityManager.flush();
    }

    public void addNewNaprawa(NaprawyEntity naprawyEntity)
    {
        RowerEntity rowerEntity = entityManager.find(RowerEntity.class,naprawyEntity.getRowerEntity().getIdRower());
        naprawyEntity.setRowerEntity(rowerEntity);
        entityManager.persist(naprawyEntity);
        entityManager.flush();
    }

    public List<RowerEntity> getRowerEntityList()
    {
        List<RowerEntity> entities = entityManager.createQuery("SELECT m FROM Rower m",RowerEntity.class).getResultList();
        return entities;
    }

    public List<NaprawyEntity> getNaprawyList()
    {
        List<NaprawyEntity> entities = entityManager.createQuery("SELECT n FROM Naprawy n, Rower r WHERE n.rowerEntity=r and r.status='Naprawa'",NaprawyEntity.class).getResultList();
        return entities;
    }

    public List<NaprawyEntity> getHistoriaNaprawList()
    {
        List<NaprawyEntity> entities = entityManager.createQuery("SELECT n FROM Naprawy n, Rower r WHERE n.rowerEntity=r",NaprawyEntity.class).getResultList();
        return entities;
    }

    public List<RowerEntity> getWolneRoweryList(PunktEntity punktEntity)
    {
        List<RowerEntity> rowerEntity = entityManager.createQuery("SELECT r FROM Rower r, Punkt p WHERE r.punktEntity=p AND p.idPunkt=?1 and r.status='Wolny'",RowerEntity.class)
                .setParameter(1,punktEntity.getIdPunkt())
                .getResultList();

        return rowerEntity;
    }

    public List<PunktEntity> getPunktEntityList()
    {
        List<PunktEntity> punktEntities = entityManager.createQuery("SELECT p from Punkt p",PunktEntity.class).getResultList();
        return punktEntities;
    }

    public List<PunktEntity> getCountRowerByPunkt()
    {
        List<PunktEntity> entities = entityManager.createQuery("SELECT p.adres,p.ilosc, COUNT(r.idRower) as t FROM Punkt p, Rower r WHERE r.punktEntity=p AND r.status='Wolny' GROUP BY p.adres,p.ilosc",PunktEntity.class).getResultList();
        return entities;
    }

    public List<RowerEntity> getRowerNaprawyStats()
    {
        List<RowerEntity> naprawyStats = entityManager.createQuery("SELECT r.idRower,r.nazwa, COUNT(n.idNaprawa) as q, SUM(n.cena) as e FROM Rower r, Naprawy n WHERE n.rowerEntity=r GROUP BY r.idRower, r.nazwa",RowerEntity.class).getResultList();
        return naprawyStats;
    }

    public List<PunktEntity> getPunktStats()
    {
        List<PunktEntity> punktStats = entityManager.createQuery("SELECT p.idPunkt, p.adres, COUNT(p.idPunkt) as q, AVG(w.czas) as e ,SUM(w.czas) as o, AVG(w.oplata) as r, SUM(w.oplata) as y, MAX(w.oplata) as u, MAX(w.czas) FROM Punkt p, Wypozyczenie w WHERE w.punktEntity=p GROUP BY p.idPunkt,p.adres",PunktEntity.class).getResultList();
        return punktStats;
    }

    public List<RowerEntity> getRowerStats()
    {
        List<RowerEntity> stats = entityManager.createQuery("SELECT r.idRower, r.nazwa, COUNT(r.idRower) as q, SUM(w.czas) as e FROM Wypozyczenie w, Rower r WHERE w.rowerEntity=r GROUP BY r.idRower, r.nazwa",RowerEntity.class).getResultList();
        return stats;
    }

    public List<NaprawyEntity> getNaprListByRower(RowerEntity rowerEntity)
    {
        List<NaprawyEntity> hist = entityManager.createQuery("SELECT n FROM Naprawy n, Rower r WHERE r.idRower=?1 AND n.rowerEntity=r")
                .setParameter(1,rowerEntity.getIdRower())
                .getResultList();
        return hist;
    }


    public List<RowerEntity> getRowerByPunkt(PunktEntity punktEntity)
    {
        List<RowerEntity> rowEntities = entityManager.createQuery("SELECT r FROM Rower r, Punkt p WHERE r.punktEntity=p AND p.idPunkt=?1",RowerEntity.class)
                .setParameter(1,punktEntity.getIdPunkt())
                .getResultList();
        return rowEntities;

    }
/*
    public List<RowerEntity> getRowerEntityListByPunkt(PunktEntity punktEntity)
    {
        List<RowerEntity> rowEntities = entityManager.createQuery("SELECT r FROM Rower r, Punkt P WHERE p.idPunkt=?1 and p")
    }*/

    public void addPunkt(PunktEntity punktEntity)
    {
        entityManager.persist(punktEntity);
        entityManager.flush();
    }

    public void deleteRowerEntity(RowerEntity rowerEntity)
    {
        RowerEntity entity = entityManager.find(RowerEntity.class,rowerEntity.getIdRower());
        entityManager.remove(entity);
    }

    public void setWypStatRower(RowerEntity rowerEntity)
    {
        RowerEntity entity = entityManager.find(RowerEntity.class,rowerEntity.getIdRower());
        entity.setStatus("Wyp");
        entityManager.persist(entity);
    }

    public void setZwrotStatRower(RowerEntity rowerEntity)
    {
        RowerEntity entity = entityManager.find(RowerEntity.class,rowerEntity.getIdRower());
        entity.setStatus("Wolny");
        entityManager.persist(entity);
    }

    public void setNaprawaStatRower(RowerEntity rowerEntity)
    {
        RowerEntity entity = entityManager.find(RowerEntity.class,rowerEntity.getIdRower());
        entity.setStatus("Naprawa");
        entity.setPunktEntity(null);
        entityManager.persist(entity);
    }

    public void setRowerToPunkt(RowerEntity rowerEntity, PunktEntity punktEntity)
    {
        PunktEntity pktEntity = entityManager.find(PunktEntity.class,punktEntity.getIdPunkt());
        RowerEntity rowEntity = entityManager.find(RowerEntity.class,rowerEntity.getIdRower());
        rowEntity.setPunktEntity(pktEntity);
        rowEntity.setStatus("Wolny");
        entityManager.persist(rowEntity);
    }

}
