package rental;

import javax.persistence.*;

@Entity
@Table(name = "DaneKlient", schema = "dbo", catalog = "Test")
public class DaneKlientEntity {
    private int id;
    private String imie;
    private String nazwisko;
    private String tel;
    private String email;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "imie")
    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    @Basic
    @Column(name = "nazwisko")
    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    @Basic
    @Column(name = "tel")
    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DaneKlientEntity entity = (DaneKlientEntity) o;

        if (id != entity.id) return false;
        if (imie != null ? !imie.equals(entity.imie) : entity.imie != null) return false;
        if (nazwisko != null ? !nazwisko.equals(entity.nazwisko) : entity.nazwisko != null) return false;
        if (tel != null ? !tel.equals(entity.tel) : entity.tel != null) return false;
        if (email != null ? !email.equals(entity.email) : entity.email != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (imie != null ? imie.hashCode() : 0);
        result = 31 * result + (nazwisko != null ? nazwisko.hashCode() : 0);
        result = 31 * result + (tel != null ? tel.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }
}
