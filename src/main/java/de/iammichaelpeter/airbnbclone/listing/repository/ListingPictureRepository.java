package de.iammichaelpeter.airbnbclone.listing.repository;

import de.iammichaelpeter.airbnbclone.listing.domain.ListingPicture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListingPictureRepository extends JpaRepository<ListingPicture, Long> {
}
