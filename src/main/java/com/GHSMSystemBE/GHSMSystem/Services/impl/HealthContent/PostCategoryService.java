package com.GHSMSystemBE.GHSMSystem.Services.impl.HealthContent;

import com.GHSMSystemBE.GHSMSystem.Models.HealthContent.PostCategory;
import com.GHSMSystemBE.GHSMSystem.Models.PostCategorySpecification;
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
    public PostCategory getById(Integer ID) {
        Optional<PostCategory> oFound = Optional.of(repo.findById(ID).orElseThrow());
        PostCategory found = oFound.get();
        return found;
    }

    @Override
    public PostCategory addCategory(PostCategory category) {
        return repo.save(category);
    }

    @Override
    public PostCategory editCategory(Integer id,PostCategory category) {
       PostCategory found  = getById(id);
       if(found == null)
       {
           return  null;
       }
       else
       {
           found.setName(category.getName());
           found.setIsActive(category.getIsActive());
           return repo.save(found);
       }
    }

    @Override
    public PostCategory deleteCategory(Integer ID) {
        PostCategory found = getById(ID);
       return found;
    }
}
