package rental;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

@Entity(name="Wypozyczenie")
@Table(name = "Wypozyczenie", schema = "dbo", catalog = "Test")
public class WypozyczenieEntity {

    private Timestamp dataWyp;
    private Timestamp dataZwrot;
    private Long czas;
    private Double oplata;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @OneToOne(cascade = CascadeType.PERSIST)
    private KlientEntity klientEntity;

    @OneToOne(cascade = CascadeType.PERSIST)
    private PunktEntity punktEntity;

    @OneToOne(cascade = CascadeType.PERSIST)
    private RowerEntity rowerEntity;


    @Basic
    @Column(name = "datawyp")
    public Timestamp getDataWyp() {
        return dataWyp;
    }

    public void setDataWyp(Timestamp dataWyp) {
        this.dataWyp = dataWyp;
    }

    @Basic
    @Column(name = "datazwrot")
    public Timestamp getDataZwrot() {
        return dataZwrot;
    }

    public void setDataZwrot(Timestamp dataZwrot) {
        this.dataZwrot = dataZwrot;
    }

    @Basic
    @Column(name = "czas")
    public Long getCzas() {
        return czas;
    }

    public void setCzas(Long czas) {
        this.czas = czas;
    }

    @Basic
    @Column(name = "oplata")
    public Double getOplata() {
        return oplata;
    }

    public void setOplata(Double oplata) {
        this.oplata = oplata;
    }
/*
    @Basic
    @Column(name = "punkt")
    public int getPunkt() {
        return punkt;
    }

    public void setPunkt(int punkt) {
        this.punkt = punkt;
    }

    @Basic
    @Column(name = "klient")
    public int getKlient() {
        return klient;
    }

    public void setKlient(int klient) {
        this.klient = klient;
    }
    @Basic
    @Column(name = "rower")
    public int getRower() {return rower;}

    public void setRower(int rower) {this.rower=rower;}

*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WypozyczenieEntity that = (WypozyczenieEntity) o;

        if (id!= that.id) return false;
        if (dataWyp != null ? !dataWyp.equals(that.dataWyp) : that.dataWyp != null) return false;
        if (dataZwrot != null ? !dataZwrot.equals(that.dataZwrot) : that.dataZwrot != null) return false;
        if (czas != null ? !czas.equals(that.czas) : that.czas != null) return false;
        if (oplata != null ? !oplata.equals(that.oplata) : that.oplata != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (dataWyp != null ? dataWyp.hashCode() : 0);
        result = 31 * result + (dataZwrot != null ? dataZwrot.hashCode() : 0);
        result = 31 * result + (czas != null ? czas.hashCode() : 0);
        result = 31 * result + (oplata != null ? oplata.hashCode() : 0);
        return result;
    }



    public KlientEntity getKlientEntity() {
        return klientEntity;
    }

    public void setKlientEntity(KlientEntity klientEntity) {
        this.klientEntity = klientEntity;
    }

    public PunktEntity getPunktEntity() {
        return punktEntity;
    }

    public void setPunktEntity(PunktEntity punktEntity) {
        this.punktEntity = punktEntity;
    }


    public RowerEntity getRowerEntity() {
        return rowerEntity;
    }

    public void setRowerEntity(RowerEntity rowerEntity) {
        this.rowerEntity = rowerEntity;
    }
}
