package com.example.mobilemandatoryassignmentbirthdaylist.Repos

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.mobilemandatoryassignmentbirthdaylist.Models.DisplayablePerson
import com.example.mobilemandatoryassignmentbirthdaylist.Models.Person
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.Calendar

class PersonRepo {
    private val personService: PersonService
    private val apiUrl ="https://birthdaysrest.azurewebsites.net/api/"
    val personLiveData = MutableLiveData<List<DisplayablePerson>>()
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
                    val displayablePersons = persons?.map { DisplayablePerson.transformFromPerson(it) }
                    personLiveData.postValue(displayablePersons!!)
                    errMsgLiveData.postValue("")
                }
                else{
                    val errMsg = response.code().toString() + " " + response.message()
                    errMsgLiveData.postValue(errMsg)
                    Log.d("Error", errMsg)
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
                    val person = response.body()
                    val displayablePerson = DisplayablePerson.transformFromPerson(person!!)
                    Log.d("Persons", "PersonId:" + response.body())
                    updateMsgLiveData.postValue("Added" + displayablePerson)
                }
                else {
                    val message = response.code().toString() + " " + response.message()
                    errMsgLiveData.postValue(message)
                    Log.d("Error", message)
                }
            }

            override fun onFailure(call: Call<Person>, t: Throwable) {
                errMsgLiveData.postValue(t.message)
                Log.d("PersonsFailure", t.message!!)
            }
        }
        )
    }

    fun addPerson(person: Person) {
        personService.addPerson(person).enqueue(object:  Callback<Person> {
            override fun onResponse(call: Call<Person>, response: Response<Person>) {
             if(response.isSuccessful) {
                val person = response.body()
                 val displayPerson = DisplayablePerson.transformFromPerson(person!!)
                 updateMsgLiveData.postValue("added" + displayPerson)
                 Log.d("Persons", "Persons deleted" + displayPerson)
             }
             else {
                 val message = response.code().toString() + " " + response.message()
                 errMsgLiveData.postValue(message)
                 Log.d("Error", message)
             }
            }

            override fun onFailure(call: Call<Person>, t: Throwable) {
                errMsgLiveData.postValue(t.message)
                Log.d("PersonsFailure", t.message!!)
            }


        })
    }
    fun deletePerson(id:Int) {
        personService.deletePerson(id).enqueue(object:  Callback<Person> {
            override fun onResponse(call: Call<Person>, response: Response<Person>) {
                if(response.isSuccessful) {
                    val person = response.body()
                    val displayablePerson = DisplayablePerson.transformFromPerson(person!!)
                    Log.d("Persons", "Persons deleted" + displayablePerson)
                    updateMsgLiveData.postValue("deleted" + displayablePerson)
                }
                else {
                    val message = response.code().toString() + " " + response.message()
                    errMsgLiveData.postValue(message)
                    Log.d("Error", message)
                }
            }

            override fun onFailure(call: Call<Person>, t: Throwable) {
                errMsgLiveData.postValue(t.message)
                Log.d("PersonsFailure", t.message!!)
            }
        }
        )
    }
    fun updatedPerson(Id: Int, Update: Person ) {
        personService.updatePerson(Id,Update).enqueue(object:  Callback<Person> {
            override fun onResponse(call: Call<Person>, response: Response<Person>) {
             if(response.isSuccessful) {
                 val person = response.body()
                 val DisplayablePerson = DisplayablePerson.transformFromPerson(person!!)
                 updateMsgLiveData.postValue("updated" + response.body())
                 Log.d("Persons", "Person updated" + response.body())
             }
             else {
                 val message = response.code().toString() + " " + response.message()
                 errMsgLiveData.postValue(message)
                 Log.d("Error", message)
             }
            }

            override fun onFailure(call: Call<Person>, t: Throwable) {
                errMsgLiveData.postValue(t.message)
                Log.d("PersonsFailure", t.message!!)
            }
        } )
    }

    fun sortByName() {

        personLiveData.value = personLiveData.value?.sortedBy { it.name }
    }
    fun sortByAge() {
        personLiveData.value = personLiveData.value?.sortedBy { it.birthYear }
    }
    fun sortByBirthDay() {
        personLiveData.value = personLiveData.value?.sortedWith(compareBy(

            {
                it.birthDay
            },
            {
                it.birthMonth
            },
            {
                it.birthYear
            }
        ))

    }
    fun filterPersons(name: String = "", age: String = "") {
        if (name.isBlank() && age.isBlank()) {

            getPersons()
        } else {
            val ageInt = age.toIntOrNull()

            if (age.isNotBlank() && ageInt == null) {

                Log.d("AgeFailure", "Invalid Age: $age")

            }

            personLiveData.value?.let { originalList ->
                val filteredList = originalList.filter { person ->
                    val realAge = Calendar.getInstance().get(Calendar.YEAR)
                    val calcAge = realAge - person.birthYear
                    val nameCondition = name.isBlank() || person.name.contains(name, ignoreCase = true)
                    val ageCondition = age.isBlank() || calcAge  == ageInt


                    nameCondition && ageCondition
                }
                personLiveData.value = filteredList
            }
        }
    }
}