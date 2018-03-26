package io.pitchcast.pitcherservice.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Pitcher {

    @Id
    @GeneratedValue
    private Long pitcherId;

    @NotNull
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    private PitcherHand handed;

    private String description;

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

    public PitcherHand getHanded() {
        return handed;
    }

    public void setHanded(PitcherHand handed) {
        this.handed = handed;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
