package io.pitchcast.pitcherservice.web;

import io.pitchcast.pitcherservice.service.PitcherService;
import io.pitchcast.pitcherservice.web.dto.PitcherDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/pitcher")
public class PitcherController {

    private PitcherService pitcherService;

    @Autowired
    public PitcherController(PitcherService pitcherService) {
        this.pitcherService = pitcherService;
    }

    @PostMapping("/add")
    public ResponseEntity<Long> addNewPitcher(@RequestBody @Validated PitcherDto pitcher) {
        return new ResponseEntity(pitcherService.savePitcher(DtoTransformer.pitcherDtoToPitcher(pitcher)), HttpStatus.CREATED);
    }

}
