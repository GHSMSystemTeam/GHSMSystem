package com.GHSMSystemBE.GHSMSystem.Services;

import com.GHSMSystemBE.GHSMSystem.Models.HealthContent.PostCategory;

import java.util.List;

public interface IHealthPostCategory {
    List<PostCategory> getAll();
    List<PostCategory> getAllActive();
    List<PostCategory> getAllInactive();
    PostCategory addCategory(PostCategory category);
    PostCategory editCategory(Integer id,PostCategory category);
    PostCategory deleteCategory(Integer id);
    PostCategory getById(Integer ID);
}
