package rental;


import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named("loginNamed")
@SessionScoped
public class LoginNamed implements Serializable {

    private static final long serialVersionUID = 1L;

    private boolean isKlient = false;
    private boolean isAdmin = false;
    private boolean isSaldo = false;



    @EJB
    private MessageBean messageBean;

    public String getMessageBean() {return messageBean.getContent();}

    @EJB
    KontoBean kontoBean;

    KlientEntity klientEntity = new KlientEntity();
    AdminEntity adminEntity = new AdminEntity();
    DaneLogowaniaEntity daneLogowaniaEntity = new DaneLogowaniaEntity();
    DaneKlientEntity daneKlientEntity = new DaneKlientEntity();

    public KlientEntity getKlientEntity() {return klientEntity;}
    public void setKlientEntity(KlientEntity klientEntity) {this.klientEntity=klientEntity;}

    public DaneLogowaniaEntity getDaneLogowaniaEntity() {return daneLogowaniaEntity;}
    public void setDaneLogowaniaEntity(DaneLogowaniaEntity daneLogowaniaEntity) {this.daneLogowaniaEntity=daneLogowaniaEntity;}

    public DaneKlientEntity getDaneKlientEntity() {return daneKlientEntity;}
    public void setDaneKlientEntity(DaneKlientEntity daneKlientEntity) {this.daneKlientEntity=daneKlientEntity;}


    public boolean isKlient() {
        return isKlient;
    }

    public void setKlient(boolean klient) {
        isKlient = klient;
    }

    public boolean isSaldo() {return isSaldo;}

    public void setSaldo(boolean saldo) {isSaldo=saldo;}


    public String authenticCredentials()
    {
        isKlient = false;
        isAdmin = false;

        if(daneLogowaniaEntity.getLogin().isEmpty() || daneLogowaniaEntity.getHaslo().isEmpty())
        {
            messageBean.setContent("pola musza byc uzupelnione");
        }

        DaneLogowaniaEntity logowanie2 = new DaneLogowaniaEntity();
        logowanie2.setLogin(daneLogowaniaEntity.getLogin());
        logowanie2.setHaslo(daneLogowaniaEntity.getHaslo());

        KlientEntity klientEntity = kontoBean.getKlientByLogowanie(daneLogowaniaEntity);
        AdminEntity adminEntity = kontoBean.getAdminByLogowanie(logowanie2);
        if(klientEntity == null)
        {
            if(adminEntity != null)
            {
                daneLogowaniaEntity = adminEntity.getDaneLogowaniaEntity();
                daneKlientEntity = adminEntity.getDaneKlientEntity();

                this.adminEntity = adminEntity;
                isKlient=false;
                isAdmin=true;
                return " ";
            }
            else
            {
                messageBean.setContent("niepoprawne dane");
                return " ";
            }
        }
        else
        {
            daneLogowaniaEntity = klientEntity.getDaneLogowaniaEntity();
            daneKlientEntity = klientEntity.getDaneKlientEntity();

            this.klientEntity = klientEntity;
            isKlient=true;
            isAdmin=false;
            return " ";
        }
    }


    public boolean authenticAdminLogowanie()
    {
        if(daneLogowaniaEntity.getLogin().isEmpty() || daneLogowaniaEntity.getHaslo().isEmpty())
        {
            return false;
        }

        AdminEntity adminEntity = kontoBean.getAdminByLogowanie(daneLogowaniaEntity);
        if(adminEntity==null)
        {
            messageBean.setContent("Admin: Dane lub konto nie istnieje");
            return false;
        }

        daneLogowaniaEntity = adminEntity.getDaneLogowaniaEntity();
        daneKlientEntity = adminEntity.getDaneKlientEntity();

        this.adminEntity = adminEntity;
        isKlient=false;
        isAdmin=true;
        return true;
    }

    public boolean autenticSaldo()
    {
        KlientEntity klientEntity = kontoBean.getKlientByLogowanie(daneLogowaniaEntity);
        double x =klientEntity.getSaldo();

        if(x<10)
        {
            messageBean.setContent("Musisz posiadac przynajmniej 10 zl na koncie");
            return false;
        }
        isSaldo=true;
        return true;
    }


    public boolean authenticKlientLogowanie()
    {
        if(daneLogowaniaEntity.getLogin().isEmpty() || daneLogowaniaEntity.getHaslo().isEmpty())
        {
            messageBean.setContent("Pola nie moga byc psute");
            return false;
        }

        KlientEntity klientEntity = kontoBean.getKlientByLogowanie(daneLogowaniaEntity);
        if(klientEntity==null)
        {
            messageBean.setContent("Klient: niepoprawne");
            return false;
        }

        daneLogowaniaEntity = klientEntity.getDaneLogowaniaEntity();
        daneKlientEntity = klientEntity.getDaneKlientEntity();

        this.klientEntity=klientEntity;
        isKlient=true;
        isAdmin=false;

        return true;
    }

    public void clearPermissionFlags()
    {
        isKlient=false;
        isAdmin=false;

    }


    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
