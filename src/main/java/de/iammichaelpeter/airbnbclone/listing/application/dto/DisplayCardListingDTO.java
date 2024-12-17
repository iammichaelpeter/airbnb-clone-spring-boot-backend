package de.iammichaelpeter.airbnbclone.listing.application.dto;

import de.iammichaelpeter.airbnbclone.listing.application.dto.sub.PictureDTO;
import de.iammichaelpeter.airbnbclone.listing.application.dto.vo.PriceVO;
import de.iammichaelpeter.airbnbclone.listing.domain.BookingCategory;

import java.util.UUID;

public record DisplayCardListingDTO(PriceVO price,
                                    String location,
                                    PictureDTO cover,
                                    BookingCategory bookingCategory,
                                    UUID publicId) {
}
