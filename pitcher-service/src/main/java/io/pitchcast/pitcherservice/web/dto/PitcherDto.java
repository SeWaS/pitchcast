package io.pitchcast.pitcherservice.web.dto;

import javax.validation.constraints.NotNull;

public class PitcherDto {

    @NotNull
    private String name;

    public PitcherDto() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
