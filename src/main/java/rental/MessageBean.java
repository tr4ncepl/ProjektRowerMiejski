package rental;


import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.ejb.Stateless;


@Stateful
public class MessageBean {
    private String content = " ";

    public MessageBean()
    {
    }

    public String getContent() {
        String _content = content;
        content = " ";
        return _content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString()
    {
        return getContent();
    }
}
