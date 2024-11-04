package carrotmoa.carrotmoa.model.request;

import carrotmoa.carrotmoa.entity.AccommodationSpace;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccommodationSpaceRequest {
    private long accommodationId;
    private long spaceId;
    private int count;

    public AccommodationSpace toAccommodationSpaceEntity() {
        return AccommodationSpace.builder()
                .accommodationId(accommodationId)
                .spaceId(spaceId)
                .count(count)
                .build();
    }
}
