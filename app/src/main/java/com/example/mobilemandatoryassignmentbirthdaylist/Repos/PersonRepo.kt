package com.example.mobilemandatoryassignmentbirthdaylist.Repos

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.mobilemandatoryassignmentbirthdaylist.Models.Person
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class PersonRepo {
    private val personService: PersonService
    private val apiUrl ="https://birthdaysrest.azurewebsites.net/api/"
    val personLiveData = MutableLiveData<List<Person>?>()
    val errMsgLiveData = MutableLiveData<String>()
    val updateMsgLiveData = MutableLiveData<String>()

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(apiUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        personService = retrofit.create(PersonService::class.java)

    }
    fun getPersons() {
        personService.getAllPersons().enqueue(object : Callback<List<Person>>
        {
            override fun onResponse(call: Call<List<Person>>, response: Response<List<Person>>) {
                if(response.isSuccessful) {
                    val persons: List<Person>? = response.body()
                    personLiveData.postValue(persons!!)
                    errMsgLiveData.postValue("")
                }
                else{
                    val errMsg = response.code().toString() + " " + response.message()
                    errMsgLiveData.postValue(errMsg)
                    Log.d("Persons", errMsg)
                }
            }
            override fun onFailure(call: Call<List<Person>>, t: Throwable) {
                errMsgLiveData.postValue(t.message)
                Log.d("PersonsFailure", t.message!!)
            }
        }
        )
    }
    fun getPersonsById(id:Int) {
        personService.getPersonById(id).enqueue(object: Callback<Person>
        {
            override fun onResponse(call: Call<Person>, response: Response<Person>) {
                if(response.isSuccessful) {

                    Log.d("Persons", "PersonId:" + response.body())
                    updateMsgLiveData.postValue("Added" + response.body())
                }
            }

            override fun onFailure(call: Call<Person>, t: Throwable) {
                errMsgLiveData.postValue(t.message)
                Log.d("PersonsFailure", t.message!!)
            }
        }
        )
    }

    fun sortByName() {

        personLiveData.value = personLiveData.value?.sortedBy { it.name }
    }
    fun sortByAge() {
        personLiveData.value = personLiveData.value?.sortedBy { it.birthYear }
    }
    fun sortByBirthDay() {
        personLiveData.value = personLiveData.value?.sortedBy { it.birthDay }
    }
    fun filtedByName(name:String) {
            if(name.isBlank()) {
                getPersons()
            }
        else{
            personLiveData.value?.let { originalList ->
                val filteredList = originalList?.filter { Person -> Person.name.contains(name, ignoreCase = true) }
                personLiveData.value = filteredList
            }
            }
    }
    fun filtedByAge(Age:String) {
        if(Age.isBlank()) {
            getPersons()
        }
        else{
            val ageInt = Age.toIntOrNull()
            if(ageInt != null) {
                personLiveData.value?.let { originalList ->
                    val filteredList = originalList.filter { person ->
                        person.birthYear == ageInt
            }
                    personLiveData.value = filteredList
            }
        }
            else{

                Log.d("AgeFailure", "Invalid Age: $Age")

            }            }
    }
}