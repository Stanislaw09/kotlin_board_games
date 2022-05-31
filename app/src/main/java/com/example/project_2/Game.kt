package com.example.project_2

class Game{
    var id: Int=0
    var name: String? = null
    var year: Int=0
    var image: String?=null
    var position: Int=0

    constructor(id: Int, name: String?, year: Int,image: String?, position: Int=0 ){
        this.id=id
        this.name=name
        this.year=year
        if (image != null)   this.image=image
        this.position=position
    }

    constructor(name: String?, year: Int, position: Int=0, image: String?){
        this.name=name
        this.year=year
        if (image != null)   this.image=image
        this.position=position

    }
}