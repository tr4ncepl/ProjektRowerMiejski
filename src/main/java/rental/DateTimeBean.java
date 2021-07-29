package rental;


import javax.ejb.Stateless;
import javax.inject.Named;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

@Named("dateTimeBean")
@Stateless
public class DateTimeBean {

   private Timestamp timestamp = new Timestamp(System.currentTimeMillis());

    public Timestamp getTimestamp()
    {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp){this.timestamp=timestamp;}

    private Date currentDate = new Date();

    public Date getCurrentDate() { return currentDate;}

    public void setCurrentDate(Date currentDate) {this.currentDate = currentDate;}
}
