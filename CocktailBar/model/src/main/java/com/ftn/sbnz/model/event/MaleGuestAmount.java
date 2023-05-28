package com.ftn.sbnz.model.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaleGuestAmount {
    private Long eventId;
    private int amount;

}
