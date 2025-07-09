package com.GHSMSystemBE.GHSMSystem.Models.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {
    public String consultantId;
    public Integer categoryId;
    public String title;
    public String content;
}
