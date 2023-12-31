package hu.ait.stayhard.ui.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import hu.ait.stayhard.R
import java.io.Serializable

@Entity(tableName = "habittable")
data class HabitItem(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "category") var category: Category,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "description") var description: String,
    @ColumnInfo(name = "estimatedprice") var estimatedPrice: Double,
    @ColumnInfo(name = "status") var status: Status,
) : Serializable

enum class Category {
    DRUGS {
        override fun getIcon(): Int = R.drawable.drug_icon
    },
    GYM {
        override fun getIcon(): Int = R.drawable.gym_icon
    },
    FINANCE {
        override fun getIcon(): Int = R.drawable.finance_icon
    },
    ;

    abstract fun getIcon(): Int

    fun getCategoryIcon(): Int {
        return this.getIcon()
    }
}

enum class Status {
    ACCOMPLISHED,
    NOT_ACCOMPLISHED;

    fun getStatusIcon(): Int {
        return if (this == Status.ACCOMPLISHED) R.drawable.green_accomplished else R.drawable.not_accomplished_icon
    }
}
