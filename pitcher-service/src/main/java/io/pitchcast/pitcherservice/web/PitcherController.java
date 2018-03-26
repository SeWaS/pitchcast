package io.pitchcast.pitcherservice.web;

import io.pitchcast.pitcherservice.service.PitcherService;
import io.pitchcast.pitcherservice.web.dto.PitcherDto;
import io.pitchcast.pitcherservice.web.dto.PitchersDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/pitcher")
public class PitcherController {

    private PitcherService pitcherService;

    @Autowired
    public PitcherController(PitcherService pitcherService) {
        this.pitcherService = pitcherService;
    }

    @PostMapping("/")
    public ResponseEntity<Long> addNewPitcher(@RequestBody @Validated PitcherDto pitcher) {
        return new ResponseEntity(pitcherService.savePitcher(DtoTransformer.pitcherDtoToPitcher(pitcher)), HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<PitchersDto> getAllPitcher() {
        PitchersDto receivedPitchers = new PitchersDto();
        receivedPitchers.setPitchers(DtoTransformer.listOfPitcherToListOfPitcherDto(pitcherService.getAllPitcher()));
        return ResponseEntity.ok(receivedPitchers);
    }

}
