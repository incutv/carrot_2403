package carrotmoa.carrotmoa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "authority_service")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorityService extends BaseEntity {

    @Column(name = "authority_id")
    private Long authorityId;

    @Column(name = "service_id")
    private Long serviceId;
}
