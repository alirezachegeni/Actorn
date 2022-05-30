package com.mahdi.actorn.utils

import java.util.*

fun calculateAge(
          dateOfBirth : String? ,
) : Int
{
     var age = 0
     val grabYear : Int? = dateOfBirth?.dropLast(6)?.toInt()
     val currentYear : Int = Calendar.getInstance().get(Calendar.YEAR)
     if (grabYear != null)
     {
          age = currentYear - grabYear
     }
     return age
}

fun getPopularity(
          popularity : Double? ,
) : String
{
     if (popularity !=null){
          val formatPopularity = popularity.toString().split(".")
          return formatPopularity[0]
     }else{
        return  "0"
     }
}

fun getPlaceOfBirth(
          location : String ,
) : String
{
     val findKnownLocation = location?.split(",")
     val cityStateCountry = findKnownLocation?.size?.minus(1)
     return cityStateCountry?.let {
          //split haye joda shode ra yek unit dar nazar migire
          findKnownLocation[it].trim()
     }
}