package com.spring.mongo.controller;

import com.spring.mongo.entity.Listing;
import com.spring.mongo.repository.ListingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
public class ListingController {

    private ListingRepository repo;

    public ListingController(ListingRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/listing")
    public String getAllListingAndReviews(Model model) {
        model.addAttribute("listings", repo.findAll());
        return  "get-listings";
    }

    @GetMapping("/pageable-listings")
    public String getPaginatedListings(
            @RequestParam(defaultValue = "0") int page,
            Model model) {

        Pageable pageable = PageRequest.of(page, 10);
        Page<Listing> listingPage = repo.findAll(pageable);

        model.addAttribute("listings", listingPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", listingPage.getTotalPages());

        return "pageable-listing";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable String id, Model model) {
        Listing listing = repo.findById(id).orElse(null);
        model.addAttribute("listing", listing);
        log.info("Editing listing with id {}", id);
        return "edit-listing";
    }

    @PostMapping("/update")
    public String updateListing(@ModelAttribute Listing listing) {
        repo.save(listing);
        log.info("Updating listing with details {}",listing);
        return "redirect:/pageable-listings";
    }

    @PostMapping("/delete")
    public String deleteListing(@RequestParam String _id) {
        repo.deleteById(_id);
        log.info("Deleting record {}", _id);
        return "redirect:/pageable-listings";
    }
}
