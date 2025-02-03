package com.hita.shifttracker.model.system;

import org.springframework.stereotype.Component;

@Component
public class Poruka {

    private String poruka;

    public Poruka(){
        this.poruka = "";
    }

    public void addToMsg(String msg) {
        this.poruka += msg;
    }

    public String getPoruka() {
        return poruka;
    }

    public void clearPoruka(){
        this.poruka = "";
    }
}
