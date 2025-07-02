package com.GHSMSystemBE.GHSMSystem.Services;

import com.GHSMSystemBE.GHSMSystem.Models.HealthContent.PostCategory;

import java.util.List;

public interface IPostCategoryService {
    List<PostCategory> getAll();
    List<PostCategory> getAllActive();
    List<PostCategory> getAllInactive();
}
