package sit.int204.backend.controllers;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import sit.int204.backend.config.JwtTokenUtil;
import sit.int204.backend.entities.Announcement;
import sit.int204.backend.repositories.AnnouncementRepository;
import sit.int204.backend.utils.ListMapper;
import sit.int204.backend.dtos.*;
import sit.int204.backend.services.AnnouncementService;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


@RestController
@CrossOrigin
@RequestMapping("/api/announcements")
public class AnnouncementController {
    @Autowired
    private AnnouncementService service;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ListMapper listMapper;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private AnnouncementRepository repository;


    //Get All Announcements
    @GetMapping("")
    public List<AnnouncementDTO> getAnnouncements(
            @RequestParam(defaultValue = "admin") String mode, HttpServletRequest request
    ){
        System.out.println("workk");
        String token = request.getHeader("Authorization");
        if (StringUtils.isEmpty(token) || !token.startsWith("Bearer ")) {
//            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            List<Announcement> announcementList = service.getAllAnnouncement(mode);
            return listMapper.mapList(announcementList, AnnouncementDTO.class, modelMapper);


        }
        String role = jwtTokenUtil.getRoleFromToken(token.substring(7));
        if (Objects.equals(role, "admin")) {
            List<Announcement> announcementList = service.getAllAnnouncement(mode);
            return listMapper.mapList(announcementList, AnnouncementDTO.class, modelMapper);
        } else {
            List<Announcement> announcementList = service.getAllAnnouncementByAnnouncer(token.substring(7));
            return listMapper.mapList(announcementList, AnnouncementDTO.class, modelMapper);
        }
    }



    //Get Announcement
    @GetMapping("/{id}")
    public AnnouncementIdDTO getAnnouncement(@PathVariable Integer id, HttpServletRequest request) {
       String token = request.getHeader("Authorization");
        System.out.println(token);
       if(token == null || Objects.equals(jwtTokenUtil.getRoleFromToken(token.substring(7)), "admin")){
            return modelMapper.map(service.getAnnouncement(id), AnnouncementIdDTO.class);
       }else {
           Announcement announcement = service.getAnnouncement(id);
           String role = jwtTokenUtil.getRoleFromToken(token.substring(7));
           String username = jwtTokenUtil.getUsernameFromToken(token.substring(7));
           if(Objects.equals(role, "announcer") && Objects.equals(username, announcement.getUsers().getUsername())){

               return modelMapper.map(announcement, AnnouncementIdDTO.class);

           }else {
               throw new ResponseStatusException(HttpStatus.FORBIDDEN);
           }

       }
    }

    @GetMapping("AnnCatId/{id}")
    public AnnCatIdDTO getCategory(@PathVariable Integer id) {
        return modelMapper.map(service.getAnnouncement(id), AnnCatIdDTO.class);
    }

    //Create Announcement อยู่ใน Category Controller
    @PostMapping("")
    public OutputAnnouncementDTO createAnnouncement(@RequestBody AddAnnouncementDTO announcementDTO,HttpServletRequest request ){
        String token = request.getHeader("Authorization");
        String username = jwtTokenUtil.getUsernameFromToken(token.substring(7));

        if (token != null ) {
            return modelMapper.map(service.createNewAnnouncement(announcementDTO, username), OutputAnnouncementDTO.class);
    }else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }


    //Update Announcement
    @PutMapping("/{id}")
    public OutputAnnouncementDTO updateAnnouncementById(@PathVariable int id,@RequestBody AddAnnouncementDTO addAnnouncementDTO,HttpServletRequest request){
        String token = request.getHeader("Authorization");
        return  service.updateAnnouncement(id , addAnnouncementDTO, token);
    }

    //Delete Announcement
    @DeleteMapping("/{id}")
    public void deleteAnnouncement(@PathVariable Integer id, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        String role = jwtTokenUtil.getRoleFromToken(token.substring(7));
        if (Objects.equals(role, "admin")) {
             service.deleteAnnouncement(id);
        } else {
            if (Objects.equals(role, "announcer")) {
                String username = jwtTokenUtil.getUsernameFromToken(token.substring(7));
                Announcement announcement = repository.getReferenceById(id);
                if(Objects.equals(announcement.getUsers().getUsername(), username)){
                    service.deleteAnnouncement(id);

                }else {
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN);
                }
            } else {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN);
            }
        }
    }
}




