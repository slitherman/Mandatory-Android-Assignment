package com.example.mobilemandatoryassignmentbirthdaylist.Models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mobilemandatoryassignmentbirthdaylist.Repos.PersonRepo

class PersonsViewModel:ViewModel() {
    private val repo = PersonRepo()

val personLiveData: LiveData<List<DisplayablePerson>> = repo.personLiveData
    val errMsgLiveData: LiveData<String> = repo.errMsgLiveData

  val _friendDetails = MutableLiveData<DisplayablePerson>()


    private val isEditModeLiveData = MutableLiveData<Boolean>()





    init {
        isEditModeLiveData.value = false
    }

    fun setEditMode(isEditMode: Boolean) {
        isEditModeLiveData.value = isEditMode
    }


    fun isEditMode(): Boolean {
        return isEditModeLiveData.value ?: false
    }
    fun setFriendDetails(friend: DisplayablePerson) {
        _friendDetails.value = friend
    }
    fun reload() {
        repo.getPersons()
    }
    operator fun get(index: Int): DisplayablePerson? {
        return personLiveData.value?.get(index)
    }
    fun delete(id: Int?) {
        if (id != null) {
            repo.deletePerson(id)
        }
    }
    fun addPerson(person: DisplayablePerson) {
        repo.addPerson(person)
    }
    fun updatePeron(Id: Int?, Update: DisplayablePerson) {
        if (Id != null) {
            repo.updatedPerson(Id,Update)
        }
    }
    fun sortByAge() {
        repo.sortByAge()
    }
    fun sortByName() {
        repo.sortByName()
    }
    fun sortByBirthday() {
        repo.sortByBirthDay()
    }
    fun filterNameAndAge(Name: String, Age: String)  {
        repo.filterPersons(Name,Age)
    }

}