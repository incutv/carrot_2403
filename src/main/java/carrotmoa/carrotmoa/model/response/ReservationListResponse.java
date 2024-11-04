package carrotmoa.carrotmoa.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class ReservationListResponse {
    private Long accommodationId;
    private String title;
    private String imageUrl;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
}
