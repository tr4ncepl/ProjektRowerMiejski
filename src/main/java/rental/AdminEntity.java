package rental;

import javax.persistence.*;

@Entity
@Table(name = "Admin", schema = "dbo", catalog = "Test")
public class AdminEntity {

    @Id
    @Column(name = "ID")
    private int id;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @OneToOne(cascade = CascadeType.PERSIST)
    private DaneKlientEntity daneKlientEntity;

    @OneToOne(cascade = CascadeType.PERSIST)
    private DaneLogowaniaEntity daneLogowaniaEntity;


    public DaneKlientEntity getDaneKlientEntity() {
        return daneKlientEntity;
    }

    public void setDaneKlientEntity(DaneKlientEntity daneKlientEntity) {
        this.daneKlientEntity = daneKlientEntity;
    }

    public DaneLogowaniaEntity getDaneLogowaniaEntity() {
        return daneLogowaniaEntity;
    }

    public void setDaneLogowaniaEntity(DaneLogowaniaEntity daneLogowaniaEntity) {
        this.daneLogowaniaEntity = daneLogowaniaEntity;
    }
}
