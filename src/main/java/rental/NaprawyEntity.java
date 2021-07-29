package rental;

import javax.persistence.*;
import java.util.Date;


@Entity(name = "Naprawy")
@Table(name = "Naprawy", schema = "dbo", catalog = "Test")
public class NaprawyEntity {

    private Date dt;
    private String opis;
    private double cena;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_naprawa")
    private int idNaprawa;

    public int getIdNaprawa() {
        return idNaprawa;
    }

    public void setIdNaprawa(int idNaprawa) {
        this.idNaprawa = idNaprawa;
    }



    @OneToOne(cascade = CascadeType.PERSIST)
    private RowerEntity rowerEntity;


    @Basic
    @Column(name = "dt")
    public Date getDt() {
        return dt;
    }

    public void setDt(Date dt) {
        this.dt = dt;
    }

    @Basic
    @Column(name = "opis")
    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    @Basic
    @Column(name = "cena")
    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NaprawyEntity that = (NaprawyEntity) o;

        if (idNaprawa != that.idNaprawa) return false;
        if (Double.compare(that.cena, cena) != 0) return false;
        if (dt != null ? !dt.equals(that.dt) : that.dt != null) return false;
        if (opis != null ? !opis.equals(that.opis) : that.opis != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = idNaprawa;
        result = 31 * result + (dt != null ? dt.hashCode() : 0);
        result = 31 * result + (opis != null ? opis.hashCode() : 0);
        temp = Double.doubleToLongBits(cena);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    public RowerEntity getRowerEntity() {
        return rowerEntity;
    }

    public void setRowerEntity(RowerEntity rowerEntity) {
        this.rowerEntity = rowerEntity;
    }
}
