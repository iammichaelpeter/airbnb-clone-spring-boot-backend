package de.iammichaelpeter.airbnbclone.listing.application.dto.sub;

import de.iammichaelpeter.airbnbclone.listing.application.dto.vo.BathsVO;
import de.iammichaelpeter.airbnbclone.listing.application.dto.vo.BedroomsVO;
import de.iammichaelpeter.airbnbclone.listing.application.dto.vo.BedsVO;
import de.iammichaelpeter.airbnbclone.listing.application.dto.vo.GuestsVO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record ListingInfoDTO(
        @NotNull @Valid GuestsVO guests,
        @NotNull @Valid BedroomsVO bedrooms,
        @NotNull @Valid BedsVO beds,
        @NotNull @Valid BathsVO baths) {
}
