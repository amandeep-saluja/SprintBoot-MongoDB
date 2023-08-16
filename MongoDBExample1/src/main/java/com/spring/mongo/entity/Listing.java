package com.spring.mongo.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(collection = "listingsAndReviews")
public class Listing {

    @Id
    private String _id;
    private String listing_url;
    private String name;
    private String summary;
    private String space;
    private String description;
    private String neighborhood_overview;
    private String notes;
    private String transit;
    private String access;
    private String interaction;
    private String house_rules;
    private String property_type;
    private String room_type;
    private String bed_type;
    private String minimum_nights;
    private String maximum_nights;
    private String cancellation_policy;
    private Date last_scraped;
    private Date calendar_last_scraped;
    private Date first_review;
    private Date last_review;
    private int accommodates;
    private int bedrooms;
    private int beds;
    private int number_of_reviews;
    private BigDecimal bathrooms;
    private List<String> amenities;
    private BigDecimal price;
    private BigDecimal security_deposit;
    private BigDecimal cleaning_fee;
    private BigDecimal extra_people;
    private int guests_included;
    private Images images;
    private Host host;
    private Address address;
    private List<Review> reviews;



    // Nested classes for complex fields
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class Images {
        private String thumbnail_url;
        private String medium_url;
        private String picture_url;
        private String xl_picture_url;

        // ... Getters and setters
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class Host {
        private String host_id;
        private String host_url;
        private String host_name;
        private String host_location;
        private String host_about;
        private String host_response_time;
        private String host_thumbnail_url;
        private String host_picture_url;
        private String host_neighbourhood;
        private int host_response_rate;
        private boolean host_is_superhost;
        private boolean host_has_profile_pic;
        private boolean host_identity_verified;
        private int host_listings_count;
        private int host_total_listings_count;
        private List<String> host_verifications;

        // ... Getters and setters
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class Address {
        private String street;
        private String suburb;
        private String government_area;
        private String market;
        private String country;
        private String country_code;
        private Location location;

        // ... Getters and setters
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class Location {
        private String type;
        private List<BigDecimal> coordinates;
        private boolean is_location_exact;

        // ... Getters and setters
    }
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class Review {
        private String _id;
        private Date date;
        private String listing_id;
        private String reviewer_id;
        private String reviewer_name;
        private String comments;

        // ... Getters and setters
    }

    private Availability availability;
    private ReviewScores review_scores;

    // ... Getters and setters

    // Nested classes for complex fields
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class Availability {
        private int availability_30;
        private int availability_60;
        private int availability_90;
        private int availability_365;

        // ... Getters and setters
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class ReviewScores {
        private int review_scores_accuracy;
        private int review_scores_cleanliness;
        private int review_scores_checkin;
        private int review_scores_communication;
        private int review_scores_location;
        private int review_scores_value;
        private int review_scores_rating;

        // ... Getters and setters
    }


}
