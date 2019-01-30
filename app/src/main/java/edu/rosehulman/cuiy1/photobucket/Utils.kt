package edu.rosehulman.cuiy1.photobucket

object Utils {
    lateinit var title : String
    fun randomImageUrl(): String {
        val urls = arrayOf(
            "http://b218.photo.store.qq.com/psb?/598748a4-68db-4a41-8cf2-0db9b2375c34/sQBfc6f3vvrEfD5yu3REjzJ51KqjFF2hWSkWRLJBano!/b/dNoAAAAAAAAA&bo=YAlABmAVQA4RByQ!&rf=viewer_4",
            "http://b224.photo.store.qq.com/psb?/598748a4-68db-4a41-8cf2-0db9b2375c34/oPDFFzGV*fFEXXTJOhpLHGuzJiFswrfJJmBVcnnrVGI!/b/dOAAAAAAAAAA&bo=YAlABh4VFA4RNz4!&rf=viewer_4",
            "http://b244.photo.store.qq.com/psb?/598748a4-68db-4a41-8cf2-0db9b2375c34/YWn0hGttyA3uC91dbIa2AYmv8O3kI3pGrEsZT8pKEX0!/b/dPQAAAAAAAAA&bo=YAlABmAVQA4RByQ!&rf=viewer_4",
            "http://m.qpic.cn/psb?/598748a4-68db-4a41-8cf2-0db9b2375c34/.OABYWcACWnwOLm0x5I4q5vNCVdXhz92CjUW.WYjrok!/b/dAsBAAAAAAAA&bo=9AlABkgVXA0RB4c!&rf=viewer_4",
            "http://m.qpic.cn/psb?/598748a4-68db-4a41-8cf2-0db9b2375c34/dFcJmVVAD3DYQ9spzDbyAmcnLuW*aW.2w4455juNsxo!/b/dN4AAAAAAAAA&bo=YAlABmAVQA4RByQ!&rf=viewer_4"
        )
        return urls[(Math.random() * urls.size).toInt()]
    }

}
