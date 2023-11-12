package com.example.mobilemandatoryassignmentbirthdaylist.Models

data class DisplayablePerson(
    val userId: String,
    val name: String,
    val birthYear: Int,
    val birthMonth: Int,
    val birthDay: Int,
    val remarks: String
)
{
    override fun toString(): String {
        return "Id:$userId $name\nBirthdate: $birthMonth/$birthDay/$birthYear Remarks: $remarks"
    }


    companion object {
        fun transformFromPerson(person: Person): DisplayablePerson {
            return DisplayablePerson(
                userId = person.userId,
                name = person.name,
                birthYear = person.birthYear,
                birthMonth = person.birthMonth,
                birthDay = person.birthDay,
                remarks = person.remarks?: ""
            )
        }
    }
}
