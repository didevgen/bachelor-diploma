package ua.nure.providence.dtos.clubPreferenceDTO;

import ua.nure.providence.dtos.BaseUuidDTO;
import ua.nure.providence.models.ClubPreference;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Игорь on 09.02.2017.
 */
public class ClubPreferenceDTO extends BaseUuidDTO<ClubPreference> {
    private String club;
    private List<String> users;


    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }

    public ClubPreferenceDTO convert(ClubPreference object) {
        this.setClub(object.getClub().getUuid());
        this.setUsers(object.getUsers().stream().map(clubPreference -> clubPreference.getUuid()).collect(Collectors.toList()));
        return this;
    }
}
