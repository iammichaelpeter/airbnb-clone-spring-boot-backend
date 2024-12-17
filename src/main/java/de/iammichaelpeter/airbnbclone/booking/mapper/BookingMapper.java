package de.iammichaelpeter.airbnbclone.booking.mapper;

import de.iammichaelpeter.airbnbclone.booking.application.dto.BookedDateDTO;
import de.iammichaelpeter.airbnbclone.booking.application.dto.NewBookingDTO;
import de.iammichaelpeter.airbnbclone.booking.domain.Booking;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    Booking newBookingToBooking(NewBookingDTO newBookingDTO);

    BookedDateDTO bookingToCheckAvailability(Booking booking);
}
