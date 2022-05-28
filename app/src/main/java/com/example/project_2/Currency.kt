package com.example.project_2

import java.util.*

//class Currency (var code: String, var rate: Double, var change: Double, var date:Date){
//    fun formatChange():String{
//        val ch=rate-change
//        val sb=StringBuilder()
//
//        if(ch>0)
//            sb.append("+")
//
//        sb.append(String.format("%.4f", ch))
//        return sb.toString()
//    }
//
//    fun percentChange():String{
//        val ch=rate-change
//        var p=(ch/change)*100
//        val sb=java.lang.StringBuilder()
//
//        if(ch>0)
//            sb.append("+")
//
//        sb.append(String.format("%.2f", p))
//        sb.append("%")
//        return sb.toString()
//    }
//}
class Currency (var id: String, var name: String, var pubYear: Int, var image:String){
    fun getGameId(): String {
        return id
    }

    fun getGameName(): String{
        return name
    }

    fun getPubYear(): String {
        return pubYear.toString()
    }
//    fun formatChange():String{
//        val ch=rate-change
//        val sb=StringBuilder()
//
//        if(ch>0)
//            sb.append("+")
//
//        sb.append(String.format("%.4f", ch))
//        return sb.toString()
//    }
//
//    fun percentChange():String{
//        val ch=rate-change
//        var p=(ch/change)*100
//        val sb=java.lang.StringBuilder()
//
//        if(ch>0)
//            sb.append("+")
//
//        sb.append(String.format("%.2f", p))
//        sb.append("%")
//        return sb.toString()
//    }
}