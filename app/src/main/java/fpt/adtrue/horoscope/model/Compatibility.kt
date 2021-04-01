package fpt.adtrue.horoscope.model

data class Compatibility(
    val categories: MutableList<Category>,
    val description: String,
    val love_description: String,
    val overall_description: String,
    val percent: Int,
    val values_description: String
)