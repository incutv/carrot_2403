package carrotmoa.carrotmoa.model.response;

import carrotmoa.carrotmoa.entity.Reservation;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
public class ReservationResponse {
    private Long id;
    private Long userId;
    private Long accommodationId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private BigDecimal totalPrice;
    private int status;
    private String lotAddress;
    private String detailAddress;
    private String imageUrl;
    private String title;

    public static ReservationResponse fromData(Reservation reservation, Object[] data) {
        return ReservationResponse.builder()
                .id(reservation.getId())
                .userId(reservation.getUserId())
                .accommodationId(reservation.getAccommodationId())
                .status(reservation.getStatus())
                .checkInDate(reservation.getCheckInDate())
                .checkOutDate(reservation.getCheckOutDate())
                .totalPrice(reservation.getTotalPrice())
                .lotAddress((String) data[9])
                .detailAddress((String) data[10])
                .imageUrl((String) data[11])
                .title((String) data[12])
                .build();
    }
}
