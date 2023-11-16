package com.example.mobilemandatoryassignmentbirthdaylist.Models

data class Person(var id: Int, var userId: String, var name: String, var birthYear: Int, var birthMonth: Int, var birthDayOfMonth:Int, var age:Int, var remarks:String, var pictureUrl: String?)
{
    override fun toString(): String {

        return "id: $id userId: $userId name: $name birthYear: $birthYear birthMonth: $birthMonth birthDay: $birthDayOfMonth remarks: $remarks"
    }
}
