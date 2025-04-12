package br.com.bdk.eventsmanager.core.logging.domain.model;


import br.com.bdk.eventsmanager.admin.user.domain.model.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "request_log")
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class RequestLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 255)
    @Column(name = "url")
    private String url;

    @Size(max = 10)
    @Column(name = "method")
    private String method;

    @Size(max = 128)
    @Column(name = "ip")
    private String ip;

    @Size(max = 255)
    @Column(name = "agent")
    private String agent;

    @Size(max = 255)
    @Column(name = "query_param")
    private String queryParam;

    @Digits(integer = Integer.SIZE, fraction = 0)
    @Column(name = "time_ms")
    private Long timeMs;

    @Digits(integer = Integer.SIZE, fraction = 0)
    @Column(name = "response_code")
    private Integer responseCode;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "requestLog")
    private RequestLogDetail detail;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RequestLog that)) return false;
        return this.getId() != null && this.getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
