package io.pitchcast.pitcherservice.web;

import io.pitchcast.pitcherservice.domain.Pitcher;
import io.pitchcast.pitcherservice.web.dto.PitcherDto;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class DtoTransformer {

    public static Pitcher pitcherDtoToPitcher(PitcherDto pitcherDto) {
        Pitcher pitcher = new Pitcher();
        BeanUtils.copyProperties(pitcherDto, pitcher);
        return pitcher;
    }

    public static List<PitcherDto> listOfPitcherToListOfPitcherDto(List<Pitcher> pitchers) {
        List<PitcherDto> pitcherDtos = new ArrayList<>();
        for(Pitcher pitcher: pitchers) {
            PitcherDto pitcherDto = new PitcherDto();
            BeanUtils.copyProperties(pitcher, pitcherDto);
            pitcherDtos.add(pitcherDto);
        }
        return pitcherDtos;
    }

}
