package sit.int204.backend.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;
import sit.int204.backend.dtos.AddAnnouncementDTO;
import sit.int204.backend.dtos.OutputAnnouncementDTO;
import sit.int204.backend.entities.Announcement;
import sit.int204.backend.entities.AnnouncementDisplayEnum;
import sit.int204.backend.entities.Category;
import sit.int204.backend.exception.ResourceNotFoundException;
import sit.int204.backend.repositories.AnnouncementRepository;
import sit.int204.backend.repositories.CategoryRepository;



import java.time.Instant;
import java.util.List;


@Service
public class AnnouncementService {
    @Autowired
    private AnnouncementRepository repository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ModelMapper modelMapper;

    //Get All Announcements
    public List<Announcement> getAllAnnouncement(String mode){
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        if (mode.equals("admin")) {
            return repository.findAll(sort);
        } else if (mode.equals("active")) {
            return repository.findAnnouncementsActive(Instant.now());
        } else if (mode.equals("close")){
            return repository.findAnnouncementsClose(Instant.now());
        }else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "not exits!!");
        }
    }

    //Get 1 Announcement

    public Announcement getAnnouncement(Integer id) {
            return repository.findById(id).orElseThrow(() ->
                    new ResourceNotFoundException(id + " does not exist' to include 'Announcement id' "));
        }


    //Create Announcement
    public OutputAnnouncementDTO createNewAnnouncement(AddAnnouncementDTO announcementDTO){
        Category category = categoryService.getCategoryById(announcementDTO.getCategoryId());
        Announcement newAnnouncement = new Announcement();
        newAnnouncement.setAnnouncementTitle(announcementDTO.getAnnouncementTitle());
        newAnnouncement.setAnnouncementDisplay(announcementDTO.getAnnouncementDisplay());
        newAnnouncement.setPublishDate(announcementDTO.getPublishDate());
        newAnnouncement.setCloseDate(announcementDTO.getCloseDate());
        newAnnouncement.setAnnouncementDescription(announcementDTO.getAnnouncementDescription());
        newAnnouncement.setCategories(category);
        if(announcementDTO.getAnnouncementDisplay() == null){
            announcementDTO.setAnnouncementDisplay(AnnouncementDisplayEnum.N);
        }
        return modelMapper.map(repository.saveAndFlush(newAnnouncement), OutputAnnouncementDTO.class);
    }

    //Update Announcement
    public OutputAnnouncementDTO updateAnnouncement(int id, AddAnnouncementDTO addAnnouncementDTO){
        Announcement upDateAnnouncement = repository.findById((id))
                .orElseThrow(() -> new ResourceNotFoundException("Announcement id" + id + " does not exist! "));
        upDateAnnouncement.setAnnouncementTitle(addAnnouncementDTO.getAnnouncementTitle());
        upDateAnnouncement.setAnnouncementDescription(addAnnouncementDTO.getAnnouncementDescription());
        upDateAnnouncement.setPublishDate(addAnnouncementDTO.getPublishDate());
        upDateAnnouncement.setCloseDate(addAnnouncementDTO.getCloseDate());
        upDateAnnouncement.setAnnouncementDisplay(addAnnouncementDTO.getAnnouncementDisplay());
        Category category = categoryRepository.findById((addAnnouncementDTO.getCategoryId()))
                .orElseThrow(()-> new ResourceNotFoundException("Announcement id " + addAnnouncementDTO.getCategoryId() + " does not exist!"));
        upDateAnnouncement.setCategories(category);
        return modelMapper.map(repository.saveAndFlush(upDateAnnouncement), OutputAnnouncementDTO.class);
    }


    //Delete Announcement อยู่ใน Category Controller
    public void deleteAnnouncement(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND,
                    "does not exist");
        }
    }


}
