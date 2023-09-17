package sit.int204.backend.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sit.int204.backend.entities.AnnouncementDisplayEnum;
import sit.int204.backend.entities.Category;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OutputAnnouncementDTO {
    private Integer id;
    private String announcementTitle;
    private String announcementDescription;
    private Instant publishDate;
    private Instant closeDate;
    private AnnouncementDisplayEnum announcementDisplay;

    @JsonIgnore
    private Category categories;

    public Integer getCategoryId(){
        return categories.getCategoryId();
    }
    public String getAnnouncementCategory(){
        return categories.getAnnouncementCategory();
    }

}
