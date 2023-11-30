package sit.int204.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import sit.int204.backend.entities.AnnouncementDisplayEnum;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddAnnouncementDTO {
    private String announcementTitle;
    private String announcementDescription;
    private Instant publishDate;
    private Instant closeDate;
    private AnnouncementDisplayEnum announcementDisplay;
    private Integer categoryId;
    private String announcementOwner;

    public void setAnnouncementTitle(String title){
        if (title.length()>200){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "WRONG REQUEST FORMAT");
        }else{
            announcementTitle = title;
        }
    }

    public void setAnnouncementDescription(String description){
        if (description.length()>10000){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "WRONG REQUEST FORMAT");
        }else{
            announcementDescription = description;
        }
    }
}
