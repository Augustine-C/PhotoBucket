package edu.rosehulman.cuiy1.photobucket

object Util {
    fun randomImageUrl(): String {
        val urls = arrayOf(
            "https://upload.wikimedia.org/wikipedia/commons/1/1a/Dszpics1.jpg",
            "https://pixabay.com/static/uploads/photo/2015/09/14/15/52/lightning-939737_960_720.jpg",
            "http://s0.geograph.org.uk/geophotos/01/56/26/1562673_9088a4c9.jpg",
            "https://upload.wikimedia.org/wikipedia/commons/6/61/Hurricane_Hugo_15_September_1989_1105z.png",
            "https://upload.wikimedia.org/wikipedia/commons/d/dc/P5med.jpg"
        )
        return urls[(Math.random() * urls.size).toInt()]
    }
}