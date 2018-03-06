package io.pitchcast.pitcherservice.web;

import io.pitchcast.pitcherservice.domain.Pitcher;
import io.pitchcast.pitcherservice.web.dto.PitcherDto;
import org.springframework.beans.BeanUtils;

public class DtoTransformer {

    public static Pitcher pitcherDtoToPitcher(PitcherDto pitcherDto) {
        Pitcher pitcher = new Pitcher();
        BeanUtils.copyProperties(pitcherDto, pitcher);
        return pitcher;
    }

}
