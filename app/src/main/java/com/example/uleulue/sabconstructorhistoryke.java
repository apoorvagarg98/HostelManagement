package com.example.uleulue;

public class sabconstructorhistoryke {
    private String adress,entryDate,entryTime,exitDate,exitTime,name,realentrydateandtime,realexitdateandtime;
    public sabconstructorhistoryke(){}
    public  sabconstructorhistoryke(String adress,String entryDate,String entryTime,String exitDate,String exitTime,String name,String realentrydateandtime,String realexitdateandtime)
    {


        this.adress=adress;
        this.entryDate=entryDate;
        this.entryTime=entryTime;
    this.exitDate=exitDate;
        this.exitTime=exitTime;
        this.name=name;
        this.realentrydateandtime=realentrydateandtime;
    this.realexitdateandtime=realexitdateandtime;
    }

    public String getEntryTime(){return entryTime;}
    public String getExitDate(){return exitDate;}
    public String getExitTime(){return exitTime;}
    public String getname(){return name;}
    public String getAdress(){return adress;}
    public String getEntrydate(){return entryDate;}
    public String getRealentrydateandtime(){return realentrydateandtime;}
    public String getRealexitdateandtime(){return realexitdateandtime;}

}
