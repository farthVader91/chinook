package com.vgowda.hasura.chinook.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

@Data
@Entity(name = "invoice_lines")
public class InvoiceLine extends RepresentationModel<InvoiceLine> {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private long id;
  @JsonIgnore
  private Long invoiceId;
  @JsonIgnore
  private Long trackId;
  private String unitPrice;
  private long quantity;
}
