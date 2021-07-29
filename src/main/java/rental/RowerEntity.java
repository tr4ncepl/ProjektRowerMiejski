package rental;

import javax.persistence.*;

@Entity(name = "Rower")
@Table(name = "Rower", schema = "dbo", catalog = "Test")
public class RowerEntity {

    private String typ;
    private String nazwa;
    private String status;
    private Integer nrSeryjny;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rower")
    private int idRower;
    public int getIdRower() {
        return idRower;
    }

    public void setIdRower(int idRower) {
        this.idRower = idRower;
    }


    @OneToOne(cascade = CascadeType.PERSIST)
    private PunktEntity punktEntity;

    @Basic
    @Column(name = "typ")
    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }

    @Basic
    @Column(name = "nazwa")
    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    @Basic
    @Column(name = "status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Basic
    @Column(name = "NRSERYJNY")
    public Integer getNrSeryjny() {
        return nrSeryjny;
    }

    public void setNrSeryjny(Integer nrSeryjny) {
        this.nrSeryjny = nrSeryjny;
    }
/*
    @Basic
    @Column(name = "punkt")
    public Integer getPunkt() {
        return punkt;
    }

    public void setPunkt(Integer punkt) {
        this.punkt = punkt;
    }
*/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RowerEntity that = (RowerEntity) o;

        if (idRower != that.idRower) return false;
        if (typ != null ? !typ.equals(that.typ) : that.typ != null) return false;
        if (nazwa != null ? !nazwa.equals(that.nazwa) : that.nazwa != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (nrSeryjny != null ? !nrSeryjny.equals(that.nrSeryjny) : that.nrSeryjny != null) return false;
      //  if (punkt != null ? !punkt.equals(that.punkt) : that.punkt != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idRower;
        result = 31 * result + (typ != null ? typ.hashCode() : 0);
        result = 31 * result + (nazwa != null ? nazwa.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (nrSeryjny != null ? nrSeryjny.hashCode() : 0);
    //    result = 31 * result + (punkt != null ? punkt.hashCode() : 0);
        return result;
    }

    public PunktEntity getPunktEntity() {
        return punktEntity;
    }

    public void setPunktEntity(PunktEntity punktEntity) {
        this.punktEntity = punktEntity;
    }
}
