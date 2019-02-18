package cc.project.busapp.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tickets {

    @Id
    @GeneratedValue
    private long ticketId;

    @NotNull
    private String route;


    @Temporal(TemporalType.TIME)
    Date busDate;

    @NotNull
    private float price;

    private int quantity;



}
