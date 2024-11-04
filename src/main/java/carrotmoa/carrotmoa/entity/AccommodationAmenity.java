package carrotmoa.carrotmoa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "accommodation_amenity")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccommodationAmenity extends BaseEntity {

    @Column(name = "accommodation_id")
    private Long accommodationId;

    @Column(name = "amenity_id")
    private Long amenityId;
}
