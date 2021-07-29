package rental;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Named("kontoNamed")
@SessionScoped
public class KontoNamed implements Serializable {

    private static final long serialVersionUID = 1L;

    private String verificationPassword;

    private boolean reg;

    private boolean rodo;




    @EJB
    private MessageBean messageBean;

    public String getMessageBean() {return messageBean.getContent();}

    @EJB
    KontoBean kontoBean;


    DaneLogowaniaEntity daneLogowaniaEntity = new DaneLogowaniaEntity();
    DaneKlientEntity daneKlientEntity = new DaneKlientEntity();
    KlientEntity klientEntity = new KlientEntity();
    AdminEntity adminEntity = new AdminEntity();

    @PostConstruct
    public void init()
    {
        klientEntity = new KlientEntity();
        klientEntity.setDaneLogowaniaEntity(new DaneLogowaniaEntity());
        klientEntity.setDaneKlientEntity(new DaneKlientEntity());
    }
    public KlientEntity getKlientEntity() {return klientEntity;}
    public void setKlientEntity(KlientEntity klientEntity) {this.klientEntity=klientEntity;}

    public DaneKlientEntity getDaneKlientEntity() {return daneKlientEntity;}
    public void setDaneKlientEntity(DaneKlientEntity daneKlientEntity) {this.daneKlientEntity=daneKlientEntity;}

    public DaneLogowaniaEntity getDaneLogowaniaEntity() {return daneLogowaniaEntity;}
    public void setDaneLogowaniaEntity(DaneLogowaniaEntity daneLogowaniaEntity) {this.daneLogowaniaEntity=daneLogowaniaEntity;}

    public void addNewKlientEntity()
    {


        if(daneLogowaniaEntity.getLogin().isEmpty()
                ||daneLogowaniaEntity.getHaslo().isEmpty()
                ||daneKlientEntity.getImie().isEmpty()
                ||daneKlientEntity.getNazwisko().isEmpty())
        {
            messageBean.setContent("Wszystkie pola muszą być uzupełnione");
            return;
        }

        if (daneLogowaniaEntity.getHaslo().equals(verificationPassword)) {
            klientEntity.setDaneKlientEntity(daneKlientEntity);
            klientEntity.setDaneLogowaniaEntity(daneLogowaniaEntity);

            for(DaneLogowaniaEntity d: kontoBean.getLoginEntity())
            {
                if(d.getLogin().equals(daneLogowaniaEntity.getLogin()))
                {
                    messageBean.setContent("Taki login istnieje, proszę wybrać inny");
                    return;
                }
            }

            if(reg == true && rodo == true) {
            try {
                kontoBean.addNewKlient(daneLogowaniaEntity,daneKlientEntity,klientEntity);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                messageBean.setContent("Blad");
                return;
            }
            messageBean.setContent("Rejestracja przebiegła pomyślnie");
            }
            else
            {
                messageBean.setContent("Musisz akceptować regulamin oraz rodo");
                return;
            }
        }
        else
        {
            messageBean.setContent("niepasujo ");
        }
    }

   public void addAdminEntity()
   {


       if(daneLogowaniaEntity.getHaslo().equals(verificationPassword)) {
           adminEntity.setDaneKlientEntity(daneKlientEntity);
           adminEntity.setDaneLogowaniaEntity(daneLogowaniaEntity);




           for(DaneLogowaniaEntity d: kontoBean.getLoginEntity())
           {
               if(d.getLogin().equals(daneLogowaniaEntity.getLogin()))
               {
                   messageBean.setContent("Taki login istnieje");
                   return;
               }
           }

           if(reg == true && rodo == true) {
               try {
                   kontoBean.addNewAdmin(daneLogowaniaEntity, daneKlientEntity, adminEntity);
               } catch (NoSuchAlgorithmException e) {
                   e.printStackTrace();
                   messageBean.setContent("Blad");
                   return;
               }
               messageBean.setContent("udalo sie");
         }
           else
           {
               messageBean.setContent("Musisz akceptowac regulamin oraz warunki rodo");
               return;
           }
       }
       else
       {
           messageBean.setContent("nie pasujo");
       }
   }

   public void reset()
   {
       setDaneKlientEntity(null);
       setDaneLogowaniaEntity(null);
   }

    public void updateDaneKlientaEntity()
    {
        kontoBean.updateDaneKlientEntity(daneKlientEntity);
    }

    public String getVerificationPassword() {
        return verificationPassword;
    }

    public void setVerificationPassword(String verificationPassword) {
        this.verificationPassword = verificationPassword;
    }


    public List<DaneLogowaniaEntity> getLoginEntity()
    {
        return kontoBean.getLoginEntity();
    }

    public List<KlientEntity> getKlientEntityList() {return kontoBean.getKlientEntityList();}

    public List<KlientEntity> getCzasByKlient(KlientEntity klientEntity) {return  kontoBean.getCzasByKlient(klientEntity);}


    public List<KlientEntity> getKlientStats()
    {
        return kontoBean.getKlientStats();
    }

    public void doladowanieKonta()
    {
        kontoBean.doladowanieKonta(klientEntity);
    }

    public boolean isReg() {
        return reg;
    }

    public void setReg(boolean reg) {
        this.reg = reg;
    }

    public boolean isRodo() {
        return rodo;
    }

    public void setRodo(boolean rodo) {
        this.rodo = rodo;
    }
}
