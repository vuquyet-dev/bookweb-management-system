package com.example.bookweb_management.controller;

import com.example.bookweb_management.dto.categorydto.CategoryCreateDTO;
import com.example.bookweb_management.dto.categorydto.CategoryResponseDTO;
import com.example.bookweb_management.dto.categorydto.CategoryUpdateDTO;
import com.example.bookweb_management.service.CategoryService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/all")
    public List<CategoryResponseDTO> getAllCategories()
    {
        return categoryService.getAllCategories();
    }

    @GetMapping("/get/{id}")
    public CategoryResponseDTO getCategory(@PathVariable Long id)
    {
        return categoryService.getCategory(id);
    }

    @PostMapping("/create")
    public CategoryResponseDTO createCategory(@RequestBody CategoryCreateDTO createDTO)
    {
        return categoryService.createCategory(createDTO);
    }

    @PutMapping("/update/{id}")
    public CategoryResponseDTO updateCategory(@PathVariable Long id,@RequestBody CategoryUpdateDTO updateDTO)
    {
        return categoryService.updateCategory(id, updateDTO);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCategory(@PathVariable Long id)
    {
        categoryService.deleteCategory(id);
    }

    @GetMapping("/search")
    public Page<CategoryResponseDTO> search(@RequestParam(defaultValue = "") String keyword,
                                            @RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "5") int size)
    {
        return categoryService.search(keyword, page, size);
    }

    @GetMapping("/excel")
    public void categoriesExcelExport(HttpServletResponse httpServletResponse) throws IOException {
        httpServletResponse.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment;filename=categories-list.xlsx";

        httpServletResponse.setHeader(headerKey, headerValue);

        categoryService.categoriesExcelExport(httpServletResponse);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addBookToCategory(@RequestParam Long categoryId,
                                               @RequestParam Long bookId)
    {
        try{
            categoryService.addBookToCategory(categoryId, bookId);
            return ResponseEntity.ok("Book added to category successfully!");
        }catch (IllegalStateException e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/remove")
    public ResponseEntity<?> removeBookFromCategory(@RequestParam Long categoryId,
                                                    @RequestParam Long bookId)
    {
        try{
            categoryService.removeBookToCategory(categoryId, bookId);
            return ResponseEntity.ok("Book removed from category successfully!");
        }catch (IllegalStateException e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
