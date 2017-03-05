package ua.malibu.ostpc.models;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import ua.malibu.ostpc.models.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Holiday extends BaseEntity{

    @Column
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime date;

    @ManyToOne
    @JoinColumn
    private Club club;

    public DateTime getDate() {
        return date;
    }

    public void setDate(DateTime date) {
        this.date = date;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }
}
