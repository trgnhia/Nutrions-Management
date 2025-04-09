package com.example.health.data.local.entities.quyen

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.health.data.local.entities.DefaultFood
import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.firestore.PropertyName
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
@IgnoreExtraProperties
@Entity(
    tableName = "eaten_dish",
    foreignKeys = [
        ForeignKey(
            entity = EatenMeal::class,
            parentColumns = ["idDay"],
            childColumns = ["idEatenMeal"]
        ),
        ForeignKey(
            entity = DefaultFood::class,
            parentColumns = ["id"],
            childColumns = ["foodId"]
        )
    ]
)
data class EatenDish(

    @JvmField
    @PropertyName("id")
    @PrimaryKey
    @ColumnInfo(name = "id")
    val Id: String,

    @JvmField
    @PropertyName("foodId")
    @ColumnInfo(name = "foodId", index = true)
    val FoodId: String,

    @JvmField
    @PropertyName("idEatenMeal")
    @ColumnInfo(name = "idEatenMeal", index = true)
    val IdEatenMeal: Date,

    @JvmField
    @PropertyName("dishName")
    @ColumnInfo(name = "dish_name")
    val DishName: String,

    @JvmField
    @PropertyName("calo")
    @ColumnInfo(name = "calo")
    val Calo: Float,

    @JvmField
    @PropertyName("fat")
    @ColumnInfo(name = "fat")
    val Fat: Float,

    @JvmField
    @PropertyName("carb")
    @ColumnInfo(name = "carb")
    val Carb: Float,

    @JvmField
    @PropertyName("protein")
    @ColumnInfo(name = "protein")
    val Protein: Float,

    @JvmField
    @PropertyName("quantityType")
    @ColumnInfo(name = "quantity_type")
    val QuantityType: String,

    @JvmField
    @PropertyName("quantity")
    @ColumnInfo(name = "quantity")
    val Quantity: Float,

    @JvmField
    @PropertyName("urlImage")
    @ColumnInfo(name = "urlImage")
    val UrlImage: String

) : Parcelable {
    constructor() : this("", "", Date(), "", 0f, 0f, 0f, 0f, "", 0f,"")
}
