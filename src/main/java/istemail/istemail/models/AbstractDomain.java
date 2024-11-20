package istemail.istemail.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
import java.util.UUID;

@Setter
@Getter
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class AbstractDomain {
    @Id
    @GeneratedValue(
            generator = "UUID"
    )
    @Column(
            name = "ID",
            nullable = false,
            updatable = false,
            columnDefinition = "UUID"
    )
    private UUID id;
    @CreationTimestamp
    @Column(
            name = "created_at",
            updatable = false
    )
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    private Date createdAt;
    @UpdateTimestamp
    @Column(
            name = "updated_at"
    )
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    private Date updatedAt;
}
