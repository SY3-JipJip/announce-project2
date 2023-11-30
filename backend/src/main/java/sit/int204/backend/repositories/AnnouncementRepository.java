package sit.int204.backend.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import sit.int204.backend.entities.Announcement;
import sit.int204.backend.entities.User;

import java.time.Instant;
import java.util.List;


public interface AnnouncementRepository extends JpaRepository<Announcement,Integer> {

    @Query("SELECT a FROM Announcement a WHERE a.announcementDisplay = 'Y' AND ("
            + "(a.publishDate IS NULL AND a.closeDate IS NULL) "
            + "OR (a.publishDate <= :now AND a.closeDate IS NULL) "
            + "OR (a.publishDate <= :now AND a.closeDate > :now)) "
            + "OR (a.publishDate IS NULL AND a.closeDate >= :now) "
            + "ORDER BY a.id DESC ")
    List<Announcement> findAnnouncementsActive(Instant now);



    @Query("SELECT a FROM Announcement a WHERE a.announcementDisplay = 'Y' AND a.closeDate <= :now " + "ORDER BY a.id DESC " )
    List<Announcement> findAnnouncementsClose(Instant now);

    List<Announcement> findAnnouncementsByUsersOrderByIdDesc(User user);
    List<Announcement> findAnnouncementsByUsers(User user);
    @Modifying
    @Transactional
    @Query(value = "UPDATE Announcement a SET a.users = :user ,a.announcementOwner = :annOwner WHERE a.users = :oldUser")
    void changeOwnerWhenDelete(User user, User oldUser, String annOwner);

}
