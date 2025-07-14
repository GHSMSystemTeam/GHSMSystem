package com.GHSMSystemBE.GHSMSystem.Services.impl.HealthContent;

import com.GHSMSystemBE.GHSMSystem.Models.HealthContent.PostCategory;
import com.GHSMSystemBE.GHSMSystem.Models.ModelSpecification.PostCategorySpecification;
import com.GHSMSystemBE.GHSMSystem.Repos.HealthContentRepo.PostCategoryRepo;
import com.GHSMSystemBE.GHSMSystem.Services.IHealthPostCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostCategoryService implements IHealthPostCategory {

    @Autowired
    private PostCategoryRepo repo;

    @Override
    public List<PostCategory> getAll() {
        return repo.findAll();
    }

    @Override
    public List<PostCategory> getAllActive() {
        return repo.findAll(PostCategorySpecification.hasStatusTrue());
    }

    @Override
    public List<PostCategory> getAllInactive() {
        return repo.findAll(PostCategorySpecification.hasStatusFalse());
    }

    @Override
    public PostCategory getById(String ID) {
        // Simplified error handling - doesn't double-wrap in Optional
        Optional<PostCategory> found = repo.findById(ID);
        if (found.isPresent()) {
            return found.get();
        } else {
            // You could throw a custom exception here instead
            throw new RuntimeException("PostCategory not found with ID: " + ID);
        }
    }

    // Safe version that doesn't throw exceptions
    public Optional<PostCategory> findById(String ID) {
        return repo.findById(ID);
    }

    @Override
    public PostCategory deactivate(String ID) {
        Optional<PostCategory> foundOptional = repo.findById(ID);
        if (foundOptional.isPresent()) {
            PostCategory found = foundOptional.get();
            found.setIsActive(false);
            return repo.save(found);
        }
        return null;
    }

    @Override
    public PostCategory activate(String ID) {
        Optional<PostCategory> foundOptional = repo.findById(ID);
        if (foundOptional.isPresent()) {
            PostCategory found = foundOptional.get();
            found.setIsActive(true);
            return repo.save(found);
        }
        return null;
    }

    @Override
    public PostCategory addCategory(PostCategory category) {
        return repo.save(category);
    }

    @Override
    public PostCategory editCategory(String id, PostCategory category) {
        Optional<PostCategory> foundOptional = repo.findById(id);
        if (foundOptional.isPresent()) {
            PostCategory found = foundOptional.get();
            found.setIsActive(category.getIsActive());
            return repo.save(found);
        }
        return null;
    }

    @Override
    public PostCategory deleteCategory(String ID) {
        Optional<PostCategory> foundOptional = repo.findById(ID);
        if (foundOptional.isPresent()) {
            PostCategory found = foundOptional.get();
            repo.delete(found);  // Actually delete the category
            return found;
        }
        return null;
    }
}