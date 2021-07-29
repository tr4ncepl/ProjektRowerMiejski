package rental;

import javax.persistence.*;

@Entity(name = "Punkt")
@Table(name = "Punkt", schema = "dbo", catalog = "Test")
public class PunktEntity {
    private int idPunkt;
    private String adres;
    private int ilosc;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_punkt")
    public int getIdPunkt() {
        return idPunkt;
    }

    public void setIdPunkt(int idPunkt) {
        this.idPunkt = idPunkt;
    }

    @Basic
    @Column(name = "adres")
    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    @Basic
    @Column(name = "ilosc")
    public int getIlosc() {
        return ilosc;
    }

    public void setIlosc(int ilosc) {
        this.ilosc = ilosc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PunktEntity that = (PunktEntity) o;

        if (idPunkt != that.idPunkt) return false;
        if (ilosc != that.ilosc) return false;
        if (adres != null ? !adres.equals(that.adres) : that.adres != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idPunkt;
        result = 31 * result + (adres != null ? adres.hashCode() : 0);
        result = 31 * result + ilosc;
        return result;
    }
}
