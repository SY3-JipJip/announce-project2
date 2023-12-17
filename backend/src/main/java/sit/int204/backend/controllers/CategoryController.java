package sit.int204.backend.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sit.int204.backend.utils.ListMapper;
import sit.int204.backend.dtos.CategoryDTO;
import sit.int204.backend.entities.Announcement;
import sit.int204.backend.entities.Category;
import sit.int204.backend.services.CategoryService;

import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin(origins = {"http://localhost:5173/","http://127.0.0.1:5173/","https://intproj22.sit.kmutt.ac.th/"})
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    private CategoryService service;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ListMapper listMapper;

    //Get Categories
    @GetMapping("")
    public List<CategoryDTO> getCategoryDTO(){
        List<Category> categoryList = service.getAllCategories();
        return listMapper.mapList(categoryList, CategoryDTO.class,modelMapper);
    }

    //Get 1 Category
    @GetMapping("/{id}")
    public CategoryDTO getCategoryDTO(@PathVariable Integer id) {
        return modelMapper.map(service.getCategoryById(id), CategoryDTO.class);
    }

    //Get Category has Announcements
    @GetMapping("/{categoryId}/announcements")
    public Set<Announcement> getCategoryAnnouncements (@PathVariable Integer categoryId){
        return service.getCategoryAnnouncements (categoryId);
    }

    //Create Category
    @PostMapping("")
    public Category createCategory(@RequestBody Category category){
        return (Category) service.createCategory(category);
    }

    //Update Category
    @PutMapping("/{categoryId}")
    public void updateCategory(@RequestBody Category category, @PathVariable Integer categoryId){
        service.updateCategory(categoryId,category);
    }

    //Delete Category
    @DeleteMapping("/{categoryId}")
    public void deleteCategory(@PathVariable Integer categoryId){
        service.deleteCategory(categoryId);
    }


}


