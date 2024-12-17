package de.iammichaelpeter.airbnbclone.listing.application.dto;

import de.iammichaelpeter.airbnbclone.listing.application.dto.vo.PriceVO;

import java.util.UUID;

public record ListingCreateBookingDTO(
        UUID listingPublicId, PriceVO price) {
}
