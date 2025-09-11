package com.example.bookweb_management.service.Impl;

import com.example.bookweb_management.dto.categorydto.CategoryCreateDTO;
import com.example.bookweb_management.dto.categorydto.CategoryResponseDTO;
import com.example.bookweb_management.dto.categorydto.CategoryUpdateDTO;
import com.example.bookweb_management.entity.Category;
import com.example.bookweb_management.exception.ResourceNotFoundException;
import com.example.bookweb_management.exception.category_exception.DuplicateNameException;
import com.example.bookweb_management.mapper.CategoryMapper;
import com.example.bookweb_management.repository.CategoryRepository;
import com.example.bookweb_management.service.CategoryService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<CategoryResponseDTO> getAllCategories() {
        return categoryMapper.toResponseDTOs(categoryRepository.findAll());
    }

    @Override
    public CategoryResponseDTO getCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found Category with id: " + id));
        return categoryMapper.toResponseDTO(category);
    }

    @Override
    public CategoryResponseDTO createCategory(CategoryCreateDTO createDTO) {
        if(categoryRepository.existsByName(createDTO.getName()))
        {
            throw new DuplicateNameException("Category " + createDTO.getName() + " already existed");
        }
        Category category = categoryMapper.toEntity(createDTO);
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.toResponseDTO(savedCategory);
    }

    @Override
    public CategoryResponseDTO updateCategory(Long id, CategoryUpdateDTO updateDTO) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found category with id: " + id));
        category.setName(updateDTO.getName());
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.toResponseDTO(savedCategory);
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public Page<CategoryResponseDTO> search(String keyword, int page, int size) {
        return null;
    }

    @Override
    public void categoriesExcelExport(HttpServletResponse httpServletResponse) throws IOException {
        List<Category> categories = categoryRepository.findAll();

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Categories Info");

        XSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("ID");
        row.createCell(1).setCellValue("Name");

        int dataRowIndex = 1;

        for(Category category : categories)
        {
            XSSFRow dataRow = sheet.createRow(dataRowIndex);
            dataRow.createCell(0).setCellValue(category.getId());
            dataRow.createCell(1).setCellValue(category.getName());
            dataRowIndex++;
        }

        for(int i = 0; i < 2; i++)
        {
            sheet.autoSizeColumn(i);
            sheet.setColumnWidth(i, sheet.getColumnWidth(i) + 1000);
        }

        ServletOutputStream sos = httpServletResponse.getOutputStream();
        workbook.write(sos);
        workbook.close();
        sos.close();
    }


}
