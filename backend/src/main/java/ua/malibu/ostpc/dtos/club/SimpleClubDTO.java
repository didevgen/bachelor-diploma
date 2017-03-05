package ua.malibu.ostpc.dtos.club;

import ua.malibu.ostpc.dtos.BaseUuidDTO;
import ua.malibu.ostpc.models.Club;

/**
 * Created by simar on 2/9/2017.
 */
public class SimpleClubDTO extends BaseUuidDTO<Club> {

    public SimpleClubDTO() {}

    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public SimpleClubDTO convert(Club object){
        this.setUuid(object.getUuid());
        this.setTitle(object.getTitle());
        return this;
    }
}
