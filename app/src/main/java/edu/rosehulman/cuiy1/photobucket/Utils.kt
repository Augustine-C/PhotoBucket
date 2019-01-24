package edu.rosehulman.cuiy1.photobucket

object Utils {
    fun randomImageUrl(): String {
        val urls = arrayOf(
            "https://upload.wikimedia.org/wikipedia/commons/1/1a/Dszpics1.jpg",
            "https://pixabay.com/static/uploads/photo/2015/09/14/15/52/lightning-939737_960_720.jpg",
            "https://upload.wikimedia.org/wikipedia/commons/6/61/Hurricane_Hugo_15_September_1989_1105z.png",
            "https://upload.wikimedia.org/wikipedia/commons/d/dc/P5med.jpg",
            "https://www.eschoolnews.com/files/2017/07/computer-science-800x533.jpg"
        )
        return urls[(Math.random() * urls.size).toInt()]
    }

}
