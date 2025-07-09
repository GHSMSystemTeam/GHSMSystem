package com.GHSMSystemBE.GHSMSystem.Services.impl.HealthContent;

import com.GHSMSystemBE.GHSMSystem.Models.DTO.PostDTO;
import com.GHSMSystemBE.GHSMSystem.Models.HealthContent.Post;
import com.GHSMSystemBE.GHSMSystem.Models.HealthContent.PostCategory;
import com.GHSMSystemBE.GHSMSystem.Models.PostSpecification;
import com.GHSMSystemBE.GHSMSystem.Models.User;
import com.GHSMSystemBE.GHSMSystem.Repos.HealthContentRepo.HealthPostRepo;
import com.GHSMSystemBE.GHSMSystem.Services.IHealthPost;
import com.GHSMSystemBE.GHSMSystem.Services.IHealthPostCategory;
import com.GHSMSystemBE.GHSMSystem.Services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Service
public class PostService implements IHealthPost {
    @Autowired
    private IUserService userService;
    @Autowired
    private IHealthPostCategory postCategoryService;
    @Autowired
    private HealthPostRepo repo;
    @Override
    public List<Post> getAll() {
        return repo.findAll();
    }

    @Override
    public List<Post> getAllActive() {
        return repo.findAll(PostSpecification.hasStatusTrue());
    }

    @Override
    public List<Post> getAllInactive() {
        return repo.findAll(PostSpecification.hasStatusFalse());
    }

    @Override
    public Post getById(String id) {
        UUID uuid = UUID.fromString(id);
        Optional<Post> oPost = repo.findById(uuid);
        if(oPost == null)
        {
            return  null;
        }
        else
        {
            Post post = oPost.get();
            return post;
        }
    }

    @Override
    public List<Post> getByConsultant(String consultantId) {
        User consultant  = userService.getById(consultantId);
        if(consultant == null)
        {
            return null;
        }
     List<Post> list = repo.findAll(PostSpecification.madeByConsultant(consultant));
        return list;
    }

    @Override
    public List<Post> getByCategory(Integer category) {
        PostCategory postCategory = postCategoryService.getById(category);
        if(postCategory == null)
        {
            return  null;
        }
        List<Post> list = repo.findAll(PostSpecification.havePostCategory(postCategory));
        return  list;
    }

    @Override
    public Post addPost(PostDTO dto) {
        Post created =null ;
        User consultant = userService.getById(dto.getConsultantId());
        PostCategory category = postCategoryService.getById(dto.getCategoryId());
        created.setConsultantId(consultant);
        created.setCategoryId(category);
        created.setTitle(dto.getTitle());
        created.setContent(dto.getContent());
        Post saved = repo.save(created);
        return saved;
    }

    @Override
    public Post editPost(String id, PostDTO post) {
        Post found = getById(id);
        PostCategory category = postCategoryService.getById(post.getCategoryId());
        if(found==null)
        {
            return null;
        }
        found.setTitle(post.getTitle());
        found.setContent(post.getContent());
        found.setCategoryId(category);
        return repo.save(found);
    }

    @Override
    public Post deletePost(String id) {
     Post found = getById(id);
     if(found==null)
     {
         return null;
     }
     repo.delete(found);
     return found;
    }

    @Override
    public Post deactivatePost(String id) {
        Post found = getById(id);
        if(found==null)
        {
            return null;
        }
        found.setActive(false);
        return repo.save(found);
    }

    @Override
    public Post activatePost(String id) {
        Post found = getById(id);
        if(found==null)
        {
            return null;
        }
        found.setActive(true);
        return repo.save(found);
    }
}
