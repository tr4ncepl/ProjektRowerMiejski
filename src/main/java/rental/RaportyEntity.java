package rental;

import javax.persistence.*;

@Entity
@Table(name = "Raporty", schema = "dbo", catalog = "Test")
public class RaportyEntity {
    private int id;
    private String tresc;
    private int wypozyczenie;

    @Id
    @Column(name = "ID")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "tresc")
    public String getTresc() {
        return tresc;
    }

    public void setTresc(String tresc) {
        this.tresc = tresc;
    }

    @Basic
    @Column(name = "wypozyczenie")
    public int getWypozyczenie() {
        return wypozyczenie;
    }

    public void setWypozyczenie(int wypozyczenie) {
        this.wypozyczenie = wypozyczenie;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RaportyEntity that = (RaportyEntity) o;

        if (id != that.id) return false;
        if (wypozyczenie != that.wypozyczenie) return false;
        if (tresc != null ? !tresc.equals(that.tresc) : that.tresc != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (tresc != null ? tresc.hashCode() : 0);
        result = 31 * result + wypozyczenie;
        return result;
    }
}
