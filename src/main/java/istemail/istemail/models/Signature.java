package istemail.istemail.models;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "signatures")
public class Signature extends AbstractDomain {
    // HTML
    @Column(name = "html", nullable = false)
    private String html;

    // IS ACTIVE
    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    // LAST SYNCED AT
    @Column(name = "last_synced_at", nullable = false)
    private Date lastSyncedAt;

    // USER
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
