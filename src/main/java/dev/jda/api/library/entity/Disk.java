package dev.jda.api.library.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "disks")
public class Disk {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private String uuid;

    @Column(length = 40)
    private String name;

    @Column(length = 15)
    private String code;

    @Column(length = 15)
    private String size;

    @Column(length = 15)
    private String type;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Drive> drives;

    @PrePersist
    public void prePersist(){
        this.uuid = UUID.randomUUID().toString();

    }
    @ManyToOne
    @JoinColumn(name = "drive_id")
    private Drive drive;
}
