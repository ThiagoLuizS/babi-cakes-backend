package br.com.babicakesbackend.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryView {
    private Long id;
    private CategoryFileView categoryFileView;
    private String name;
    private String description;
    private boolean show;
    private List<ProductView> productViews;
}
