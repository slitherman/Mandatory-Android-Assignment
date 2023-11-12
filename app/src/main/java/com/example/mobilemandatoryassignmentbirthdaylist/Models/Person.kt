package com.example.mobilemandatoryassignmentbirthdaylist.Models

data class Person(var id: Int, var userId: String, var name: String, var birthYear: Int, var birthMonth: Int, var birthDay:Int, var remarks:String)
{
    override fun toString(): String {

        return "id: $id userId: $userId name: $name birthYear: $birthYear birthMonth: $birthMonth birthDay: $birthDay remarks: $remarks"
    }
}
