package com.example.yichen.yichen_kwokwing_comp304sec001_lab04.data

class Locations {
    var locations:ArrayList<Location>;
    constructor(){
        locations = ArrayList();
    }

    fun add(location:Location){
        locations.add(location)
    }

    fun getCategory(): ArrayList<String> {
        var result = ArrayList<String>();
        for(i in locations){
            if (!result.contains(i.category)){
                result.add(i.category)
            }
        }
        return result;
    }
}