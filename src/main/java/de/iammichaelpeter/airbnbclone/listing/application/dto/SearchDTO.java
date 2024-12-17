package de.iammichaelpeter.airbnbclone.listing.application.dto;

import de.iammichaelpeter.airbnbclone.booking.application.dto.BookedDateDTO;
import de.iammichaelpeter.airbnbclone.listing.application.dto.sub.ListingInfoDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

public record SearchDTO(@Valid BookedDateDTO dates,
                        @Valid ListingInfoDTO infos,
                        @NotEmpty String location) {
}
