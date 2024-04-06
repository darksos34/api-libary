package dev.jda.api.library.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "demos")
public class Demo {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private String uuid;

    @Column(length = 40, nullable = false)
    private String name;

    @Column(length = 15, nullable = false)
    private String code;

    @PrePersist
    public void prePersist(){
        this.uuid = UUID.randomUUID().toString();

    }
}
