package com.example.todo_app_backend.dtos;

public class MetricsDTO {
    private String generalAverage;
    private String lowAverage;
    private String mediumAverage; 
    private String highAverage; 
    
    public MetricsDTO(String generalAverage, String lowAverage, String mediumAverage, String highAverage){
        this.generalAverage = generalAverage;
        this.lowAverage = lowAverage; 
        this.mediumAverage = mediumAverage; 
        this.highAverage = highAverage;
    }

    public String getGeneralAverage(){
        return generalAverage; 
    }

    public String getLowAverage(){
        return lowAverage; 
    }

    public String getMediumAverage(){
        return mediumAverage; 
    }

    public String getHighAverage(){
        return highAverage; 
    }

    public void setGeneralAverage(String generalAverage){
        this.generalAverage = generalAverage; 
    }

    public void setLowAverage(String lowAverage){
        this.lowAverage = lowAverage; 
    }

    public void setMediumAverage(String mediumAverage){
        this.mediumAverage = mediumAverage; 
    }

    public void setHighAverage(String highAverage){
        this.highAverage = highAverage; 
    }

}
