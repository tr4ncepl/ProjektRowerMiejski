package rental;


import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Stateless
@LocalBean
public class KontoBean {

    public KontoBean(){

    }

    @PersistenceContext(unitName = "NewPersistenceUnit")
    EntityManager entityManager;

    private String encryptString(String text) throws NoSuchAlgorithmException
    {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.update(text.getBytes());
        byte[] bytes = messageDigest.digest();

        StringBuilder sb = new StringBuilder();
        for(int i=0; i< bytes.length ;i++)
        {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        text = sb.toString();


        return text;
    }

    public void addNewKlient(DaneLogowaniaEntity daneLogowaniaEntity, DaneKlientEntity daneKlientEntity, KlientEntity klientEntity) throws NoSuchAlgorithmException {

        daneLogowaniaEntity.setHaslo(encryptString(daneLogowaniaEntity.getHaslo()));

        entityManager.persist(daneLogowaniaEntity);
        entityManager.persist(daneKlientEntity);
        entityManager.persist(klientEntity);
        entityManager.flush();
    }

    public void addNewAdmin(DaneLogowaniaEntity daneLogowaniaEntity, DaneKlientEntity daneKlientEntity, AdminEntity adminEntity) throws NoSuchAlgorithmException {

        daneLogowaniaEntity.setHaslo(encryptString(daneLogowaniaEntity.getHaslo()));

        entityManager.persist(daneLogowaniaEntity);

        entityManager.persist(daneKlientEntity);

        entityManager.persist(adminEntity);

        entityManager.flush();
    }


    public void updateDaneKlientEntity(DaneKlientEntity daneKlientEntity)
    {
        DaneKlientEntity entity = entityManager.find(DaneKlientEntity.class,daneKlientEntity.getId());
        entity.setTel(daneKlientEntity.getTel());
        entity.setEmail(daneKlientEntity.getEmail());
        entityManager.persist(entity);
    }

    public List<DaneLogowaniaEntity> getLoginEntity()
    {
        List<DaneLogowaniaEntity> login = entityManager.createQuery("SELECT l FROM Logowanie l",DaneLogowaniaEntity.class).getResultList();
        return login;
    }



    public List<KlientEntity> getCzasByKlient(KlientEntity klientEntity)
    {
        List<KlientEntity> czas = entityManager.createNativeQuery("SELECT d.imie, d.nazwisko,k.saldo, k.saldo/0.05 as CZAS\n" +
                "FROM Klient k, DaneKlient d\n" +
                "WHERE k.DANEKLIENTENTITY_ID=d.ID\n" +
                "AND k.ID=?1\n" +
                "AND k.saldo>=10")
                .setParameter(1,klientEntity.getId())
                .getResultList();

        return czas;
    }


    public List<KlientEntity> getKlientEntityList()
    {
        List<KlientEntity> klientEntityList = entityManager.createQuery("SELECT c FROM Klient c",KlientEntity.class).getResultList();
        return klientEntityList;
    }


    public List<KlientEntity> getKlientStats()
    {
        List<KlientEntity> klientStats = entityManager.createQuery("SELECT k.id, d.imie, d.nazwisko, COUNT(w.id) as q,SUM(w.czas) as e, SUM(w.oplata) as t FROM Klient k, Wypozyczenie w, DaneKlientEntity d WHERE w.klientEntity=k AND k.daneKlientEntity=d GROUP BY k.id,d.imie,d.nazwisko",KlientEntity.class).getResultList();
        return klientStats;
    }

    public KlientEntity getKlientByLogowanie(DaneLogowaniaEntity daneLogowaniaEntity)
    {
        try {
            daneLogowaniaEntity.setHaslo(encryptString(daneLogowaniaEntity.getHaslo()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        KlientEntity klientEntity = null;

        try {
            klientEntity = entityManager.createQuery("SELECT k FROM Klient k, Logowanie d WHERE k.daneLogowaniaEntity=d AND d.login=?1 AND d.haslo=?2",KlientEntity.class)
                    .setParameter(1,daneLogowaniaEntity.getLogin())
                    .setParameter(2,daneLogowaniaEntity.getHaslo())
                    .getSingleResult();
        }
        catch (NoResultException e)
        {
            e.printStackTrace();
            return null;
        }

        return klientEntity;
    }

    public AdminEntity getAdminByLogowanie(DaneLogowaniaEntity daneLogowaniaEntity)
    {
        try {
            daneLogowaniaEntity.setHaslo(encryptString(daneLogowaniaEntity.getHaslo()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        AdminEntity adminEntity = null;

        try {
            adminEntity = entityManager.createQuery("SELECT a FROM AdminEntity a, Logowanie d WHERE a.daneLogowaniaEntity=d AND d.login=?1 AND d.haslo=?2",AdminEntity.class)
                    .setParameter(1,daneLogowaniaEntity.getLogin())
                    .setParameter(2,daneLogowaniaEntity.getHaslo())
                    .getSingleResult();
        } catch (NoResultException e)
        {
            e.printStackTrace();
            return null;
        }

        return adminEntity;
    }


    public void doladowanieKonta(KlientEntity klientEntity)
    {
        KlientEntity entity = entityManager.find(KlientEntity.class,klientEntity.getId());
        entity.setSaldo(klientEntity.getSaldo());
        entityManager.persist(entity);

    }




}
