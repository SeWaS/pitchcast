package io.pitchcast.pitchingservice.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Pitch {

    @Id
    @GeneratedValue
    private Long pitchId;

    @NotNull
    private int x;

    @NotNull
    private int y;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(length = 24)
    private PitchResult pitchResult;

    @Enumerated(EnumType.STRING)
    @Column(length = 24)
    private PitchType pitchType;

    @NotNull
    private String pitcherName;

    @NotNull
    private Long pitcherId;

    public Pitch() {
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Long getPitchId() {
        return pitchId;
    }

    public void setPitchId(Long pitchId) {
        this.pitchId = pitchId;
    }

    public PitchResult getPitchResult() {
        return pitchResult;
    }

    public void setPitchResult(PitchResult pitchResult) {
        this.pitchResult = pitchResult;
    }

    public PitchType getPitchType() {
        return pitchType;
    }

    public void setPitchType(PitchType pitchType) {
        this.pitchType = pitchType;
    }

    public String getPitcherName() {
        return pitcherName;
    }

    public void setPitcherName(String pitcherName) {
        this.pitcherName = pitcherName;
    }

    public Long getPitcherId() {
        return pitcherId;
    }

    public void setPitcherId(Long pitcherId) {
        this.pitcherId = pitcherId;
    }
}
