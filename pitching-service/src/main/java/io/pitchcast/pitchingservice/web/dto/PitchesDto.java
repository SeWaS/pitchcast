package io.pitchcast.pitchingservice.web.dto;

import java.util.List;

public class PitchesDto {
    private List<PitchDto> pitches;

    public PitchesDto() {
    }

    public List<PitchDto> getPitches() {
        return pitches;
    }

    public void setPitches(List<PitchDto> pitches) {
        this.pitches = pitches;
    }
}
