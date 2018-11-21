package com.goteam.gohomerepairservicesapp;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ServiceProvider extends User {

    private String address, phoneNumber, companyName, description;
    private boolean licenced;
    private boolean account_finalized;
    private List services;
    //Service[] returnServices;


    @SuppressWarnings("unused") // for Firebase usage
    public ServiceProvider() {
        super("provider");
        account_finalized=false;
        services= new ArrayList<Service>();
        services.add(new Service("default", 11));

    }

    public ServiceProvider(boolean licensed) {
        super("provider");
        account_finalized=false;
        this.licenced = false;
    }

    public ServiceProvider(String address) {
        super("provider");
        account_finalized=false;
        this.address = address;
    }

    public void addService(Service service){
        services.add(service);
    }

    public void removeService(Service service){
        services.remove(service);
    }

    public List getServices(){
        return services;
    }

   // public Service[] getServices(){
     //   Service[] returnServices=new Service[services.size()];
    //    for(int i=0; i<services.size();i++){
   //         returnServices[i]=services.get(i);
   //     }
 //       return returnServices;
   // }

   // public void loadServices(){
  //      for(int i=0; i<returnServices.length;i++){
   //         services.add(returnServices[i]);
   //     }
   // }

    @Override
    public String getRoleName() {
        return "Service Provider";
    }


    public String getAddress(){return address;}
    public String getPhoneNumber(){return phoneNumber;}
    public String getCompanyName(){return companyName;}
    public String getDescription(){return description;}
    public boolean getLicensed(){return licenced;}
    public boolean getAccount_finalized(){return account_finalized;}

}
