package com.GHSMSystemBE.GHSMSystem.Services;

import com.GHSMSystemBE.GHSMSystem.Models.DTO.PostDTO;
import com.GHSMSystemBE.GHSMSystem.Models.HealthContent.Post;
import com.GHSMSystemBE.GHSMSystem.Models.HealthContent.PostCategory;

import java.util.List;

public interface IHealthPost {
    List<Post> getAll();
    List<Post> getAllActive();
    List<Post> getAllInactive();
    Post getById(String id);
    List<Post> getByConsultant(String consultantId);
    List<Post> getByCategory(Integer category);
    Post addPost(PostDTO post);
    Post editPost( String id,PostDTO post);
    Post deletePost(String id);
    Post deactivatePost(String id);
    Post activatePost(String id);
}
