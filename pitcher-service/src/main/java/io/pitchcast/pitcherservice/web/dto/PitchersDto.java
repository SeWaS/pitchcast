package io.pitchcast.pitcherservice.web.dto;

import java.util.List;

public class PitchersDto {

    private List<PitcherDto> pitchers;

    public PitchersDto() {
    }

    public List<PitcherDto> getPitchers() {
        return pitchers;
    }

    public void setPitchers(List<PitcherDto> pitchers) {
        this.pitchers = pitchers;
    }
}
