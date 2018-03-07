package io.pitchcast.pitcherservice.web.dto;

import javax.validation.constraints.NotNull;

public class PitcherDto {

    @NotNull
    private String name;

    @NotNull
    private PitcherHandDto handed;

    private String description;

    public PitcherDto() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PitcherHandDto getHanded() {
        return handed;
    }

    public void setHanded(PitcherHandDto handed) {
        this.handed = handed;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
