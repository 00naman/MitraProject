package com.example.mitraproject.data.local.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "helpline")
public class Helpline {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String name;

    public String phone;

    public Helpline(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }
}
