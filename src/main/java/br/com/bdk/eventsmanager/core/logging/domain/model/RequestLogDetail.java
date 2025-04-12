package br.com.bdk.eventsmanager.core.logging.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "request_log_detail")
@AllArgsConstructor
@NoArgsConstructor
public class RequestLogDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "request_log_id")
    private RequestLog requestLog;

    @Lob
    @Column(columnDefinition = "text", name = "request_header")
    private String requestHeader;

    @Lob
    @Column(columnDefinition = "text", name = "request_body")
    private String requestBody;

    @Lob
    @Column(columnDefinition = "text", name = "response_body")
    private String responseBody;

    @Lob
    @Column(columnDefinition = "text", name = "response_header")
    private String responseHeader;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RequestLogDetail that)) return false;
        return this.getId() != null && this.getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
