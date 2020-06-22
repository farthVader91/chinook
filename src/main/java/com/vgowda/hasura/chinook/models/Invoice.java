package com.vgowda.hasura.chinook.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

@Data
@Entity(name = "invoices")
@EqualsAndHashCode(callSuper = false)
public class Invoice extends RepresentationModel<Invoice> {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private long id;
  @JsonIgnore
  private Long customerId;
  private LocalDateTime invoiceDate;
  private String billingAddress;
  private String billingCity;
  private String billingState;
  private String billingCountry;
  private String billingPostalCode;
  private String total;

}
