package fpt.adtrue.horoscope.model

data class DataSignItem(
    val astrologyIcon: String,
    val color: String,
    val dates: String,
    val days: MutableList<String>,
    val description: MutableList<String>,
    val element: String,
    val icon: String,
    val modality: String,
    val name: String,
    val phrase: String,
    val planets: MutableList<String>,
    val polarity: String,
    val preview: String,
    val symbol: String,
    val type: String
)