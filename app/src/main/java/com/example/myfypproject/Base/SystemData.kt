package com.example.myfypproject.Base

import com.example.myfypproject.Activity.LoginActivity

object BaseUrl{
    //val url = "http://192.168.68.101:81/api/"
    val url = "http://192.168.68.101:45455/api/"
}

object PreviousScreen{
    val LoginActivity = 0
    val SwitchProfile = 1
}

object IntentKeyValue{
    val PrevScreen ="prevScreen"
}

enum class Relationship {
    Self,
    Mother,
    Father,
    GrandFather,
    GrandMother,
}

object RelationshipArray{
    val relationshipArray= arrayOf("Self","Mother","Father","GrandFather", "GrandMother")

}

object FragmentName {
    val Home ="Home"
    val Appointment = "Appointment"
    val Notification = "Notification"
    val Profile = "Profile"
    val NotificationPreferences ="Notification Preferences"
    val SwitchProfile = "Switch Profile"
    val EditProfile = "Edit Profile"
    val AppointmentDetail = "Appointment Detail"
    val AddAppointment ="Add Appointment"
    val CheckIn = "Check In"
    val Reschedule = "Reschedule"
}

object FragmentType{
    val InnerFragment ="IF"
}

object ArrayInit{
    val gender= arrayOf("Male","Female")
}
object ApptAction{
    val CheckIn = 0
    val Reschedule =1
    val Cancel = 2
}

object ApptType{
    val Upcoming= 1
    val Past = 2
    val NoShow =3
    val upcomingVal = arrayOf(0)
    val pastVal = arrayOf(4,201)
    val noShowVal = arrayOf(301)
}

object ApptActionType{
    val reschedule = 1
    val cancel =2
    val checkIn = 3
}

object DialogTitle{
    val TimeSlot = "Select Time Slot"
    val Slots = "Select Slot"
    val Service = "Select Service"
    val Gender = "Gender"
}

