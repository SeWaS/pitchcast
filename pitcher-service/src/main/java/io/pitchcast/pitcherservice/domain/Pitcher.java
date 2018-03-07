package io.pitchcast.pitcherservice.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Pitcher {

    @Id
    @GeneratedValue
    private Long pitcherId;

    @NotNull
    private String name;

    public Pitcher() {}

    public Long getPitcherId() {
        return pitcherId;
    }

    public void setPitcherId(Long pitcherId) {
        this.pitcherId = pitcherId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
