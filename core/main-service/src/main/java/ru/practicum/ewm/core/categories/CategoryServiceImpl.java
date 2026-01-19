package ru.practicum.ewm.core.categories;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.core.categories.dto.CategoryDto;
import ru.practicum.ewm.core.categories.interfaces.CategoryMapper;
import ru.practicum.ewm.core.categories.interfaces.CategoryService;
import ru.practicum.ewm.core.events.EventRepository;
import ru.practicum.ewm.core.exceptions.DataIntegrityViolation;
import ru.practicum.ewm.core.exceptions.NotFoundException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper mapper;
    private final EventRepository eventRepository;

    @Override
    public List<CategoryDto> findAll(int from, int size) {
        log.info("Find all Categories from:{}, size:{}", from, size);
        PageRequest page = PageRequest.of(from, size);
        return categoryRepository.findAll(page).stream().map(mapper::toDto).toList();
    }

    @Override
    public CategoryDto findById(Long catId) throws NotFoundException {
        log.info("Find Category by id:{}", catId);
        Category category = categoryRepository.findById(catId)
                .orElseThrow(() -> new NotFoundException("Category not found ID:" + catId));
        return mapper.toDto(category);
    }

    @Override
    @Transactional
    public CategoryDto addNewCategory(CategoryDto category) throws DataIntegrityViolation {
        log.info("Create new Category with name:{}", category.getName());
        if (categoryRepository.existsByName(category.getName())) {
            throw new DataIntegrityViolation("Category already exists with NAME:" + category.getName());
        }
        Category newCategory = new Category();
        newCategory.setName(category.getName());
        return mapper.toDto(categoryRepository.save(newCategory));
    }

    @Override
    @Transactional
    public void deleteCategoryById(Long catId) throws NotFoundException, DataIntegrityViolation {
        log.info("Delete Category with id:{}", catId);
        Category category = categoryRepository.findById(catId)
                .orElseThrow(() -> new NotFoundException("Category not found ID:" + catId));
        if (eventRepository.existsByCategory(category)) {
            throw new DataIntegrityViolation("Event depends from category ID:" + catId);
        }
        categoryRepository.delete(category);
    }

    @Override
    @Transactional
    public CategoryDto updateCategory(Long catId, CategoryDto category) throws NotFoundException, DataIntegrityViolation {
        log.info("Update Category id:{}, newName:{}", catId, category.getName());
        Category updatedCategory = categoryRepository.findById(catId)
                .orElseThrow(() -> new NotFoundException("Category not found ID:" + catId));
        if (categoryRepository.existsByName(category.getName()) && !updatedCategory.getName().equals(category.getName())) {
            throw new DataIntegrityViolation("Category already exists with NAME:" + category.getName());
        }
        updatedCategory.setName(category.getName());
        return mapper.toDto(categoryRepository.save(updatedCategory));
    }
}