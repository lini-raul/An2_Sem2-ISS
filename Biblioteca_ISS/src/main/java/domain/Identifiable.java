package domain;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Identifiable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    public int getId()
    {
        return this.id;
    };
    public void setId(int id)
    {
        this.id=id;
    };
}
