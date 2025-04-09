package com.example.health.data.local.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.health.data.local.entities.quyen.BurnOutCaloPerDay
import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.firestore.PropertyName
import kotlinx.android.parcel.Parcelize
import java.util.Date


@Parcelize
@IgnoreExtraProperties
@Entity(
    tableName = "exercise_log",
    foreignKeys = [
        ForeignKey(
            entity = DefaultExercise::class,
            parentColumns = ["id"],
            childColumns = ["idExercise"]
        ),
        ForeignKey(
            entity = BurnOutCaloPerDay::class,
            parentColumns = ["dateTime"],
            childColumns = ["dateTime"]
        )
    ]
)
data class ExerciseLog(
    @JvmField
    @PropertyName("practiceTime")
    @ColumnInfo(name = "practiceTime") val PracticeTime: Date,

    @JvmField
    @PropertyName("idExercise")
    @PrimaryKey
    @ColumnInfo(name = "idExercise", index = true) val IdExercise: String,

    @JvmField
    @PropertyName("caloBurn")
    @ColumnInfo(name = "caloBurn") val CaloBurn: Int,

    @JvmField
    @PropertyName("unitType")
    @ColumnInfo(name = "unitType")
    val UnitType: String,

    @JvmField
    @PropertyName("unit")
    @ColumnInfo(name = "unit")
    val Unit: Int,

    @JvmField
    @PropertyName("name")
    @ColumnInfo(name = "name")
    val Name: String,


    @JvmField
    @PropertyName("dateTime")
    @ColumnInfo(name = "dateTime", index = true) val DateTime: Date
) : Parcelable {
    constructor() : this(Date(), "",  0,"", 0 , " ", Date())
}
