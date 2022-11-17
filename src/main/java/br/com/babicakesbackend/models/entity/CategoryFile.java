package br.com.babicakesbackend.models.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_category_file")
public class CategoryFile {

    @Id
    @Column(name = "id_file")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "categoryFile", fetch = FetchType.LAZY)
    private Category category;

    @Column(name = "file_name")
    private String name;

    @Column(name = "file_type")
    private String type;

    @Lob
    @Column(name = "file_byte", columnDefinition = "BLOB")
    private byte[] photo;
}
