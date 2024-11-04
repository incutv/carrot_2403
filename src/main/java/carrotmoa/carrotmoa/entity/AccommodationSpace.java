package carrotmoa.carrotmoa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "accommodation_space")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccommodationSpace extends BaseEntity {

    @Column(name = "accommodation_id")
    private Long accommodationId;

    @Column(name = "space_id")
    private Long spaceId;

    @Column(name = "count")
    private Integer count;

    public void updateAccommodationSpace(Integer newCount) {
        this.count = newCount;
    }
}
