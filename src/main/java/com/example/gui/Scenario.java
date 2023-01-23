package com.example.gui;

import lombok.*;

import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Setter
public class Scenario {
    @NonNull
    private String name;
    private boolean isSuccess = false;
    private Date date;

    public void setSuccess(){
        this.isSuccess = true;
        this.date = new Date();
    }
}
