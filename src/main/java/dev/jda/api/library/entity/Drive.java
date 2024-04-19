package dev.jda.api.library.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "driver")
public class Drive {

    @Id
    @Column(name = "id", updatable = false)
    private String uuid;

    @Column(length = 40)
    private String name;

    @Column(length = 15)
    private String code;

    @PrePersist
    public void prePersist(){
        this.uuid = UUID.randomUUID().toString();

    }
    @OneToMany(mappedBy = "drive", cascade = CascadeType.ALL)
    private List<Disk> disk;
}
