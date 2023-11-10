package com.example.mobilemandatoryassignmentbirthdaylist.Repos

import android.telecom.Call
import com.example.mobilemandatoryassignmentbirthdaylist.Models.Person
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PersonService {

    @GET("Persons")
    fun getAllPersons(): retrofit2.Call<List<Person>>
    @GET("Persons/{id)")
    fun getPersonById(@Path("id") id:Int): retrofit2.Call<Person>
    @POST("Persons")
    fun postPerson(@Body person: Person): retrofit2.Call<Person>
    @DELETE("Person/{id}")
    fun deletePerson(@Path("id") id: Int): retrofit2.Call<Person>
    @PUT("Person/{id}")
    fun updatePerson(@Path("id") id:Int, @Body person: Person): retrofit2.Call<Person>
}