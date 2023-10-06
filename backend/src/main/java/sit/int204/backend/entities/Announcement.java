package sit.int204.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;


@Getter
@Setter
@Entity
@Table(name = "announces")
public class Announcement {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "announcementId", nullable = false)
        private Integer id;
        @Column(name = "announcementTitle", nullable = false, length = 200)
        private String announcementTitle;
        @Column(name = "announcementDescription", nullable = false, length = 10000)
        private String announcementDescription;
        @Column(name = "publishDate", length = 200)
        private Instant publishDate;
        @Column(name = "closeDate", length = 200)
        private Instant closeDate;

        @Enumerated(EnumType.STRING)
        @Column(name = "announcementDisplay", nullable = false, length = 20)
        private AnnouncementDisplayEnum announcementDisplay;

         @JsonIgnore
         @ManyToOne
         @JoinColumn(name = "categoryId")
         private Category categories;


}


