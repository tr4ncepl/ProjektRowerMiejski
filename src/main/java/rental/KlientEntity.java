package rental;

import javax.enterprise.context.SessionScoped;
import javax.persistence.*;
import java.util.Objects;

@Entity(name = "Klient")
@Table(name = "Klient", schema = "dbo", catalog = "Test")
public class KlientEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    private double saldo;


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


    @Basic
    @Column(name = "saldo")
    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
}
