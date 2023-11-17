package com.example.mobilemandatoryassignmentbirthdaylist.Repos

import com.example.mobilemandatoryassignmentbirthdaylist.Models.DisplayablePerson
import com.example.mobilemandatoryassignmentbirthdaylist.Models.Person
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface PersonService {

    @GET("Persons")
    fun getAllPersons( @Query("user_id") userId:String): retrofit2.Call<List<Person>>
    @GET("Persons/{id)")
    fun getPersonById(@Path("id") id:Int): retrofit2.Call<Person>
    @POST("Persons")
    fun addPerson(@Body person: DisplayablePerson): retrofit2.Call<Person>
    @DELETE("Persons/{id}")
    fun deletePerson(@Path("id") id: Int): retrofit2.Call<Person>
    @PUT("Persons/{id}")
    fun updatePerson(@Path("id") id:Int, @Body displayablePerson: DisplayablePerson): retrofit2.Call<Person>
}