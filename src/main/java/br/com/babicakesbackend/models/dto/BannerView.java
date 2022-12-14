package br.com.babicakesbackend.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BannerView {
    private Long id;
    private ProductView product;
    private CategoryView category;
    private BannerFileView file;
    private String title;
}
