package sit.int204.backend.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sit.int204.backend.ListMapper;

import sit.int204.backend.dtos.*;
import sit.int204.backend.entities.Announcement;
import sit.int204.backend.services.AnnouncementService;
import java.util.Collections;
import java.util.List;
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

    //Get All Announcements
    @GetMapping("")
    public List<AnnouncementDTO> getAnnouncements(
            @RequestParam(defaultValue = "admin") String mode){
        List<Announcement> announcementList = service.getAllAnnouncement(mode);
        if (announcementList.isEmpty()) {
            return Collections.emptyList();
        } else {
            return listMapper.mapList(announcementList, AnnouncementDTO.class, modelMapper);
        }
    }

    //Get Announcement
    @GetMapping("/{id}")
    public AnnouncementIdDTO getAnnouncement(@PathVariable Integer id) {
        return modelMapper.map(service.getAnnouncement(id), AnnouncementIdDTO.class);
    }

    @GetMapping("AnnCatId/{id}")
    public AnnCatIdDTO getCategory(@PathVariable Integer id) {
        return modelMapper.map(service.getAnnouncement(id), AnnCatIdDTO.class);
    }

    //Create Announcement อยู่ใน Category Controller
    @PostMapping("")
    public OutputAnnouncementDTO createAnnouncement(@RequestBody AddAnnouncementDTO announcementDTO ){
        return modelMapper.map(service.createNewAnnouncement(announcementDTO), OutputAnnouncementDTO.class);
    }

    //Update Announcement
    @PutMapping("/{id}")
    public OutputAnnouncementDTO updateAnnouncementById(@PathVariable int id, @RequestBody AddAnnouncementDTO addAnnouncementDTO){
        return  service.updateAnnouncement(id , addAnnouncementDTO);
    }

    //Delete Announcement
    @DeleteMapping("/{id}")
    public void deleteAnnouncement(@PathVariable Integer id){
        service.deleteAnnouncement(id);
    }

}




