package com.scoutzknifez.weatherappv2.structures.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class APIResult {
    @SerializedName("result")
    @Expose
    private String result;
}
