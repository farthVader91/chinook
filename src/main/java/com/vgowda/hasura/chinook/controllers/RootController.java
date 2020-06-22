package com.vgowda.hasura.chinook.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RootController {
  @GetMapping("/api")
  @ResponseBody
  public EntityModel<String> getApiRoot() {
    return EntityModel.of("Welcome to my HATEOAS spec'd API",
        linkTo(ArtistController.class).withRel("artists"),
        linkTo(AlbumController.class).withRel("albums"),
        linkTo(CustomerController.class).withRel("customers"),
        linkTo(EmployeeController.class).withRel("employees"),
        linkTo(InvoiceController.class).withRel("invoices"),
        linkTo(InvoiceLineController.class).withRel("invoice-lines"),
        linkTo(PlaylistController.class).withRel("playlists"),
        linkTo(TrackController.class).withRel("tracks"));
  }

  @GetMapping("/**")
  public String getIndex() {
    return "index";
  }

}
