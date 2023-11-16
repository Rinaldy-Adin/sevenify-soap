package models;

import lombok.Data;

import java.util.Date;

@Data
public class LoggingModel {
    private int id;
    private String description;
    private String ip;
    private String endpoint;
    private Date timestamp;
}
