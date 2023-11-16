package com.example.mobilemandatoryassignmentbirthdaylist.Models

data class DisplayablePerson(
    val id: Int,
    val userId: String,
    val name: String,
    val birthYear: Int,
    val birthMonth: Int,
    val birthDayOfMonth: Int,
    val age: Int?,
    val remarks: String?,
    val pictureUrl: String?,
)
{
    override fun toString(): String {
        return "Id:$userId $name\nBirthdate: $birthMonth/$birthDayOfMonth/$birthYear Remarks: $remarks"
    }


    companion object {
        fun transformFromPerson(person: Person): DisplayablePerson {
            return DisplayablePerson(
                id = person.id,
                userId = person.userId,
                name = person.name,
                birthYear = person.birthYear,
                birthMonth = person.birthMonth,
                birthDayOfMonth = person.birthDayOfMonth,
                age = person.age,
                remarks = person.remarks?: "",
                pictureUrl = person.pictureUrl,
            )
        }
    }
}
